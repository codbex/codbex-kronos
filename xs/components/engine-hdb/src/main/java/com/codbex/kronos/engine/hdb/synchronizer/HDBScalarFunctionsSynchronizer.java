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
import com.codbex.kronos.engine.hdb.domain.HDBScalarFunction;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBScalarFunctionCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBScalarFunctionDropProcessor;
import com.codbex.kronos.engine.hdb.service.HDBScalarFunctionService;
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
 * The Class HDBScalarFunctionsSynchronizer.
 */
@Component
@Order(220)
public class HDBScalarFunctionsSynchronizer extends BaseSynchronizer<HDBScalarFunction, Long> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HDBScalarFunctionsSynchronizer.class);

    /**
     * The Constant FILE_EXTENSION_HDBSCALARFUNCTION.
     */
    private static final String FILE_EXTENSION_HDBSCALARFUNCTION = ".hdbscalarfunction";

    /**
     * The ScalarFunction service.
     */
    private final HDBScalarFunctionService scalarfunctionService;

    /**
     * The datasources manager.
     */
    private final DataSourcesManager datasourcesManager;

    /**
     * The synchronization callback.
     */
    private SynchronizerCallback callback;

    /**
     * Instantiates a new scalarfunction synchronizer.
     *
     * @param scalarfunctionService the scalarfunction service
     * @param datasourcesManager the datasources manager
     */
    @Autowired
    public HDBScalarFunctionsSynchronizer(HDBScalarFunctionService scalarfunctionService, DataSourcesManager datasourcesManager) {
        this.scalarfunctionService = scalarfunctionService;
        this.datasourcesManager = datasourcesManager;
    }

    /**
     * Gets the service.
     *
     * @return the service
     */
    @Override
    public HDBScalarFunctionService getService() {
        return scalarfunctionService;
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
        return HDBScalarFunction.ARTEFACT_TYPE.equals(type);
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
    protected List<HDBScalarFunction> parseImpl(String location, byte[] content) throws ParseException {
        HDBScalarFunction scalarfunction;
        try {
            scalarfunction = HDBDataStructureModelFactory.parseScalarFunction(location, content);
        } catch (DataStructuresException | ArtifactParserException | IOException e) {
            logger.error("Failed to parse [{}]. Content [{}]", location, StringUtils.toString(content), e);
            throw new ParseException(e.getMessage(), 0);
        }

        // Configuration.configureObject(scalarfunction);
        scalarfunction.setLocation(location);
        scalarfunction.setType(HDBScalarFunction.ARTEFACT_TYPE);
        scalarfunction.updateKey();

        try {
            HDBScalarFunction maybe = getService().findByKey(scalarfunction.getKey());
            if (maybe != null) {
                scalarfunction.setId(maybe.getId());
            }
            getService().save(scalarfunction);
        } catch (Exception e) {
            logger.error("Failed to parse [{}]. Content [{}]", location, StringUtils.toString(content), e);
            throw new ParseException(e.getMessage(), 0);
        }
        return List.of(scalarfunction);
    }

    /**
     * Retrieve.
     *
     * @param location the location
     * @return the list
     */
    @Override
    public List<HDBScalarFunction> retrieve(String location) {
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
    public void setStatus(HDBScalarFunction artefact, ArtefactLifecycle lifecycle, String error) {
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
    public boolean completeImpl(TopologyWrapper<HDBScalarFunction> wrapper, ArtefactPhase flow) {

        try (Connection connection = datasourcesManager.getDefaultDataSource()
                                                       .getConnection()) {

            HDBScalarFunction scalarfunction = wrapper.getArtefact();

            switch (flow) {
                case CREATE:
                    if (ArtefactLifecycle.NEW.equals(scalarfunction.getLifecycle())) {
                        if (!SqlFactory.getNative(connection)
                                       .exists(connection, scalarfunction.getName(), DatabaseArtifactTypes.FUNCTION)) {
                            executeScalarFunctionCreate(connection, scalarfunction);
                            callback.registerState(this, wrapper, ArtefactLifecycle.CREATED);
                        } else {
                            logger.warn("HDBScalarFunction [{}] already exists during the update process", scalarfunction.getName());
                            executeScalarFunctionUpdate(connection, scalarfunction);
                            callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED);
                        }
                    } else if (ArtefactLifecycle.FAILED.equals(scalarfunction.getLifecycle())) {
                        if (!SqlFactory.getNative(connection)
                                       .exists(connection, scalarfunction.getName(), DatabaseArtifactTypes.FUNCTION)) {
                            executeScalarFunctionCreate(connection, scalarfunction);
                            callback.registerState(this, wrapper, ArtefactLifecycle.CREATED);
                            ProblemsFacade.deleteArtefactSynchronizationProblem(scalarfunction);
                        }
                    }
                    break;
                case UPDATE:
                    if (ArtefactLifecycle.MODIFIED.equals(scalarfunction.getLifecycle())) {
                        executeScalarFunctionUpdate(connection, scalarfunction);
                        callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED);
                        ProblemsFacade.deleteArtefactSynchronizationProblem(scalarfunction);
                    }
                    break;
                case DELETE:
                    if (ArtefactLifecycle.CREATED.equals(scalarfunction.getLifecycle())) {
                        if (SqlFactory.getNative(connection)
                                      .exists(connection, scalarfunction.getName(), DatabaseArtifactTypes.FUNCTION)) {
                            executeScalarFunctionDrop(connection, scalarfunction);
                            callback.registerState(this, wrapper, ArtefactLifecycle.DELETED);
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
     * @param scalarfunction the ScalarFunction
     */
    @Override
    public void cleanupImpl(HDBScalarFunction scalarfunction) {
        try {
            getService().delete(scalarfunction);
            callback.registerState(this, scalarfunction, ArtefactLifecycle.DELETED);
        } catch (Exception e) {
            callback.addError(e.getMessage());
            callback.registerState(this, scalarfunction, ArtefactLifecycle.FAILED, e);
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
     * Execute scalarfunction update.
     *
     * @param connection the connection
     * @param scalarfunctionModel the ScalarFunction model
     * @throws SQLException the SQL exception
     */
    public void executeScalarFunctionUpdate(Connection connection, HDBScalarFunction scalarfunctionModel) throws SQLException {
        if (logger.isInfoEnabled()) {
            logger.info("Processing Update ScalarFunction: " + scalarfunctionModel.getName());
        }
        if (SqlFactory.getNative(connection)
                      .exists(connection, scalarfunctionModel.getSchema(), scalarfunctionModel.getName(), DatabaseArtifactTypes.FUNCTION)) {
            executeScalarFunctionDrop(connection, scalarfunctionModel);
            executeScalarFunctionCreate(connection, scalarfunctionModel);
        } else {
            executeScalarFunctionCreate(connection, scalarfunctionModel);
        }
    }

    /**
     * Execute ScalarFunction create.
     *
     * @param connection the connection
     * @param scalarfunctionModel the ScalarFunction model
     * @throws SQLException the SQL exception
     */
    public void executeScalarFunctionCreate(Connection connection, HDBScalarFunction scalarfunctionModel) throws SQLException {
        new HDBScalarFunctionCreateProcessor().execute(connection, scalarfunctionModel);
    }

    /**
     * Execute ScalarFunction drop.
     *
     * @param connection the connection
     * @param scalarfunctionModel the ScalarFunction model
     * @throws SQLException the SQL exception
     */
    public void executeScalarFunctionDrop(Connection connection, HDBScalarFunction scalarfunctionModel) throws SQLException {
        new HDBScalarFunctionDropProcessor().execute(connection, scalarfunctionModel);
    }

    /**
     * Gets the file extension.
     *
     * @return the file extension
     */
    @Override
    public String getFileExtension() {
        return FILE_EXTENSION_HDBSCALARFUNCTION;
    }

    /**
     * Gets the artefact type.
     *
     * @return the artefact type
     */
    @Override
    public String getArtefactType() {
        return HDBScalarFunction.ARTEFACT_TYPE;
    }

}
