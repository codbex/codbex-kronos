/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.hdb.synchronizer;

import com.codbex.kronos.commons.StringUtils;
import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.domain.HDBProcedure;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBProcedureCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBProcedureDropProcessor;
import com.codbex.kronos.engine.hdb.service.HDBProcedureService;
import com.codbex.kronos.exceptions.ArtifactParserException;
import org.eclipse.dirigible.components.api.platform.ProblemsFacade;
import org.eclipse.dirigible.components.base.artefact.ArtefactLifecycle;
import org.eclipse.dirigible.components.base.artefact.ArtefactPhase;
import org.eclipse.dirigible.components.base.artefact.topology.TopologyWrapper;
import org.eclipse.dirigible.components.base.synchronizer.BaseSynchronizer;
import org.eclipse.dirigible.components.base.synchronizer.SynchronizerCallback;
import org.eclipse.dirigible.components.data.sources.manager.DataSourcesManager;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * The Class HDBProceduresSynchronizer.
 */
@Component
@Order(220)
public class HDBProceduresSynchronizer extends BaseSynchronizer<HDBProcedure, Long> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HDBProceduresSynchronizer.class);

    /**
     * The Constant FILE_EXTENSION_HDBPROCEDURE.
     */
    private static final String FILE_EXTENSION_HDBPROCEDURE = ".hdbprocedure";

    /**
     * The procedure service.
     */
    private final HDBProcedureService procedureService;

    /**
     * The datasources manager.
     */
    private final DataSourcesManager datasourcesManager;

    /**
     * The synchronization callback.
     */
    private SynchronizerCallback callback;

    /**
     * Instantiates a new procedure synchronizer.
     *
     * @param procedureService the procedure service
     * @param datasourcesManager the datasources manager
     */
    @Autowired
    public HDBProceduresSynchronizer(HDBProcedureService procedureService, DataSourcesManager datasourcesManager) {
        this.procedureService = procedureService;
        this.datasourcesManager = datasourcesManager;
    }

    /**
     * Gets the service.
     *
     * @return the service
     */
    @Override
    public HDBProcedureService getService() {
        return procedureService;
    }

    /**
     * Checks if is accepted.
     *
     * @param file the file
     * @param attrs the attrs
     * @return true, if is accepted
     */
    @Override
    public boolean isAccepted(Path file, BasicFileAttributes attrs) {
        return file.toString()
                   .endsWith(getFileExtension());
    }

    /**
     * Checks if is accepted.
     *
     * @param type the type
     * @return true, if is accepted
     */
    @Override
    public boolean isAccepted(String type) {
        return HDBProcedure.ARTEFACT_TYPE.equals(type);
    }

    /**
     * Load.
     *
     * @param location the location
     * @param content the content
     * @return the list
     * @throws ParseException the parse exception
     */
    @Override
    protected List<HDBProcedure> parseImpl(String location, byte[] content) throws ParseException {
        HDBProcedure procedure;
        try {
            procedure = HDBDataStructureModelFactory.parseProcedure(location, content);
        } catch (DataStructuresException | ArtifactParserException | IOException e) {
            logger.error("Failed to parse [{}]. Content [{}]", location, StringUtils.toString(content), e);
            throw new ParseException(e.getMessage(), 0);
        }

        // Configuration.configureObject(procedure);
        procedure.setLocation(location);
        procedure.setType(HDBProcedure.ARTEFACT_TYPE);
        procedure.updateKey();

        try {
            HDBProcedure maybe = getService().findByKey(procedure.getKey());
            if (maybe != null) {
                procedure.setId(maybe.getId());
            }
            getService().save(procedure);
        } catch (Exception e) {
            logger.error("Failed to parse [{}]. Content [{}]", location, StringUtils.toString(content), e);
            throw new ParseException(e.getMessage(), 0);
        }
        return List.of(procedure);
    }

    /**
     * Retrieve.
     *
     * @param location the location
     * @return the list
     */
    @Override
    public List<HDBProcedure> retrieve(String location) {
        return getService().getAll();
    }

    /**
     * Sets the status.
     *
     * @param artefact the artefact
     * @param lifecycle the lifecycle
     * @param error the error
     */
    @Override
    public void setStatus(HDBProcedure artefact, ArtefactLifecycle lifecycle, String error) {
        artefact.setLifecycle(lifecycle);
        artefact.setError(error);
        getService().save(artefact);
    }

    /**
     * Complete.
     *
     * @param wrapper the wrapper
     * @param flow the flow
     * @return true, if successful
     */
    @Override
    public boolean completeImpl(TopologyWrapper<HDBProcedure> wrapper, ArtefactPhase flow) {

        try (Connection connection = datasourcesManager.getDefaultDataSource()
                                                       .getConnection()) {

            HDBProcedure procedure = wrapper.getArtefact();

            switch (flow) {
                case CREATE:
                    if (ArtefactLifecycle.NEW.equals(procedure.getLifecycle())) {
                        if (!SqlFactory.getNative(connection)
                                       .exists(connection, procedure.getName(), DatabaseArtifactTypes.PROCEDURE)) {
                            executeProcedureCreate(connection, procedure);
                            callback.registerState(this, wrapper, ArtefactLifecycle.CREATED);
                        } else {
                            logger.warn("HDBProcedure [{}] already exists during the update process", procedure.getName());
                            executeProcedureUpdate(connection, procedure);
                            callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED);
                        }
                    } else if (ArtefactLifecycle.FAILED.equals(procedure.getLifecycle())) {
                        if (!SqlFactory.getNative(connection)
                                       .exists(connection, procedure.getName(), DatabaseArtifactTypes.PROCEDURE)) {
                            executeProcedureCreate(connection, procedure);
                            callback.registerState(this, wrapper, ArtefactLifecycle.CREATED);
                            ProblemsFacade.deleteArtefactSynchronizationProblem(procedure);
                        }
                    }
                    break;
                case UPDATE:
                    if (ArtefactLifecycle.MODIFIED.equals(procedure.getLifecycle())) {
                        executeProcedureUpdate(connection, procedure);
                        callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED);
                        ProblemsFacade.deleteArtefactSynchronizationProblem(procedure);
                    }
                    break;
                case DELETE:
                    if (ArtefactLifecycle.CREATED.equals(procedure.getLifecycle())) {
                        if (SqlFactory.getNative(connection)
                                      .exists(connection, procedure.getName(), DatabaseArtifactTypes.PROCEDURE)) {
                            executeProcedureDrop(connection, procedure);
                        }
                        callback.registerState(this, wrapper, ArtefactLifecycle.DELETED);
                    }
                    break;
                case START:
                case STOP:
            }

            return true;
        } catch (Exception e) {
            String errorMessage = String.format("Error occurred while processing [%s]: %s", wrapper.getArtefact()
                                                                                                   .getLocation(),
                    e.getMessage());
            callback.addError(errorMessage);
            callback.registerState(this, wrapper, ArtefactLifecycle.FAILED, errorMessage, e);
            ProblemsFacade.upsertArtefactSynchronizationProblem(wrapper.getArtefact(), errorMessage);
            return false;
        }
    }

    /**
     * Cleanup.
     *
     * @param procedure the Procedure
     */
    @Override
    public void cleanupImpl(HDBProcedure procedure) {
        try {
            getService().delete(procedure);
            callback.registerState(this, procedure, ArtefactLifecycle.DELETED);
        } catch (Exception e) {
            callback.addError(e.getMessage());
            callback.registerState(this, procedure, ArtefactLifecycle.FAILED, e);
        }
    }

    /**
     * Sets the callback.
     *
     * @param callback the new callback
     */
    @Override
    public void setCallback(SynchronizerCallback callback) {
        this.callback = callback;
    }

    /**
     * Execute procedure update.
     *
     * @param connection the connection
     * @param procedureModel the Procedure model
     * @throws SQLException the SQL exception
     */
    public void executeProcedureUpdate(Connection connection, HDBProcedure procedureModel) throws SQLException {
        if (logger.isInfoEnabled()) {
            logger.info("Processing Update Procedure: " + procedureModel.getName());
        }
        if (SqlFactory.getNative(connection)
                      .exists(connection, procedureModel.getSchema(), procedureModel.getName(), DatabaseArtifactTypes.PROCEDURE)) {
            executeProcedureDrop(connection, procedureModel);
            executeProcedureCreate(connection, procedureModel);
        } else {
            executeProcedureCreate(connection, procedureModel);
        }
    }

    /**
     * Execute Procedure create.
     *
     * @param connection the connection
     * @param procedureModel the Procedure model
     * @throws SQLException the SQL exception
     */
    public void executeProcedureCreate(Connection connection, HDBProcedure procedureModel) throws SQLException {
        new HDBProcedureCreateProcessor().execute(connection, procedureModel);
    }

    /**
     * Execute Procedure drop.
     *
     * @param connection the connection
     * @param procedureModel the Procedure model
     * @throws SQLException the SQL exception
     */
    public void executeProcedureDrop(Connection connection, HDBProcedure procedureModel) throws SQLException {
        new HDBProcedureDropProcessor().execute(connection, procedureModel);
    }

    /**
     * Gets the file extension.
     *
     * @return the file extension
     */
    @Override
    public String getFileExtension() {
        return FILE_EXTENSION_HDBPROCEDURE;
    }

    /**
     * Gets the artefact type.
     *
     * @return the artefact type
     */
    @Override
    public String getArtefactType() {
        return HDBProcedure.ARTEFACT_TYPE;
    }

}
