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
import com.codbex.kronos.engine.hdb.domain.HDBSequence;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBSequenceCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBSequenceDropProcessor;
import com.codbex.kronos.engine.hdb.service.HDBSequenceService;
import com.codbex.kronos.exceptions.ArtifactParserException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
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

/**
 * The Class HDBSequencesSynchronizer.
 */
@Component
@Order(205)
public class HDBSequencesSynchronizer extends BaseSynchronizer<HDBSequence, Long> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HDBSequencesSynchronizer.class);

    /**
     * The Constant FILE_EXTENSION_HDBSEQUENCE.
     */
    private static final String FILE_EXTENSION_HDBSEQUENCE = ".hdbsequence";

    /**
     * The sequence service.
     */
    private final HDBSequenceService sequenceService;

    /**
     * The datasources manager.
     */
    private final DataSourcesManager datasourcesManager;

    /**
     * The synchronization callback.
     */
    private SynchronizerCallback callback;

    /**
     * Instantiates a new sequence synchronizer.
     *
     * @param sequenceService the sequence service
     * @param datasourcesManager the datasources manager
     */
    @Autowired
    public HDBSequencesSynchronizer(HDBSequenceService sequenceService, DataSourcesManager datasourcesManager) {
        this.sequenceService = sequenceService;
        this.datasourcesManager = datasourcesManager;
    }

    /**
     * Gets the service.
     *
     * @return the service
     */
    @Override
    public HDBSequenceService getService() {
        return sequenceService;
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
        return HDBSequence.ARTEFACT_TYPE.equals(type);
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
    public List<HDBSequence> parse(String location, byte[] content) throws ParseException {
        HDBSequence sequence;
        try {
            sequence = HDBDataStructureModelFactory.parseSequence(location, content);
        } catch (DataStructuresException | IOException | ArtifactParserException e) {
            logger.error("Failed to parse [{}]. Content [{}]", location, StringUtils.toString(content), e);
            throw new ParseException(e.getMessage(), 0);
        }

        // Configuration.configureObject(sequence);
        sequence.setLocation(location);
        sequence.setType(HDBSequence.ARTEFACT_TYPE);
        sequence.updateKey();

        try {
            HDBSequence maybe = getService().findByKey(sequence.getKey());
            if (maybe != null) {
                sequence.setId(maybe.getId());
            }
            getService().save(sequence);
        } catch (Exception e) {
            logger.error("Failed to parse [{}]. Content [{}]", location, StringUtils.toString(content), e);
            throw new ParseException(e.getMessage(), 0);
        }
        return List.of(sequence);
    }

    /**
     * Retrieve.
     *
     * @param location the location
     * @return the list
     */
    @Override
    public List<HDBSequence> retrieve(String location) {
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
    public void setStatus(HDBSequence artefact, ArtefactLifecycle lifecycle, String error) {
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
    public boolean completeImpl(TopologyWrapper<HDBSequence> wrapper, ArtefactPhase flow) {

        try (Connection connection = datasourcesManager.getDefaultDataSource()
                                                       .getConnection()) {

            HDBSequence sequence = null;
            if (!(wrapper.getArtefact() instanceof HDBSequence)) {
                throw new UnsupportedOperationException(String.format("Trying to process %s as HDBSequence", wrapper.getArtefact()
                                                                                                                    .getClass()));
            }
            sequence = wrapper.getArtefact();

            switch (flow) {
                case CREATE:
                    if (ArtefactLifecycle.NEW.equals(sequence.getLifecycle())) {
                        if (!SqlFactory.getNative(connection)
                                       .exists(connection, sequence.getSchema(), sequence.getName(), DatabaseArtifactTypes.SEQUENCE)) {
                            executeSequenceCreate(connection, sequence);
                            callback.registerState(this, wrapper, ArtefactLifecycle.CREATED);
                        } else {
                            if (logger.isWarnEnabled()) {
                                logger.warn(String.format("HDBSequence [%s] already exists during the update process", sequence.getName()));
                            }
                            executeSequenceUpdate(connection, sequence);
                            callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED);
                        }
                    } else if (ArtefactLifecycle.FAILED.equals(sequence.getLifecycle())) {
                        if (!SqlFactory.getNative(connection)
                                       .exists(connection, sequence.getSchema(), sequence.getName(), DatabaseArtifactTypes.SEQUENCE)) {
                            executeSequenceCreate(connection, sequence);
                            callback.registerState(this, wrapper, ArtefactLifecycle.CREATED);
                            ProblemsFacade.deleteArtefactSynchronizationProblem(sequence);
                        }
                    }
                    break;
                case UPDATE:
                    if (ArtefactLifecycle.MODIFIED.equals(sequence.getLifecycle())) {
                        executeSequenceUpdate(connection, sequence);
                        callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED);
                        ProblemsFacade.deleteArtefactSynchronizationProblem(sequence);
                    }
                    break;
                case DELETE:
                    if (ArtefactLifecycle.CREATED.equals(sequence.getLifecycle())) {
                        if (SqlFactory.getNative(connection)
                                      .exists(connection, sequence.getSchema(), sequence.getName(), DatabaseArtifactTypes.SEQUENCE)) {
                            executeSequenceDrop(connection, sequence);
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
     * @param sequence the sequence
     */
    @Override
    public void cleanupImpl(HDBSequence sequence) {
        try (Connection connection = datasourcesManager.getDefaultDataSource()
                                                       .getConnection()) {
            getService().delete(sequence);
            callback.registerState(this, sequence, ArtefactLifecycle.DELETED);
        } catch (Exception e) {
            callback.addError(e.getMessage());
            callback.registerState(this, sequence, ArtefactLifecycle.FAILED, e);
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
     * Execute sequence update.
     *
     * @param connection the connection
     * @param sequenceModel the sequence model
     * @throws SQLException the SQL exception
     */
    public void executeSequenceUpdate(Connection connection, HDBSequence sequenceModel) throws SQLException {
        logger.info("Processing Update Sequence: " + sequenceModel.getName());
        if (SqlFactory.getNative(connection)
                      .exists(connection, sequenceModel.getSchema(), sequenceModel.getName(), DatabaseArtifactTypes.SEQUENCE)) {
            executeSequenceDrop(connection, sequenceModel);
            executeSequenceCreate(connection, sequenceModel);
        } else {
            executeSequenceCreate(connection, sequenceModel);
        }
    }

    /**
     * Execute sequence create.
     *
     * @param connection the connection
     * @param sequenceModel the sequence model
     * @throws SQLException the SQL exception
     */
    public void executeSequenceCreate(Connection connection, HDBSequence sequenceModel) throws SQLException {
        String sequenceName = sequenceModel.getName();
        String schema = sequenceModel.getSchema();
        if (SqlFactory.getNative(connection)
                      .exists(connection, schema, sequenceName, DatabaseArtifactTypes.SEQUENCE)) {
            logger.info("Sequence [{}] in schema [{}] already exists and will NOT be created.", sequenceName, schema);
        } else {
            new HDBSequenceCreateProcessor().execute(connection, sequenceModel);
        }
    }

    /**
     * Execute sequence drop.
     *
     * @param connection the connection
     * @param sequenceModel the sequence model
     * @throws SQLException the SQL exception
     */
    public void executeSequenceDrop(Connection connection, HDBSequence sequenceModel) throws SQLException {
        new HDBSequenceDropProcessor().execute(connection, sequenceModel);
    }

    /**
     * Gets the file extension.
     *
     * @return the file extension
     */
    @Override
    public String getFileExtension() {
        return FILE_EXTENSION_HDBSEQUENCE;
    }

    /**
     * Gets the artefact type.
     *
     * @return the artefact type
     */
    @Override
    public String getArtefactType() {
        return HDBSequence.ARTEFACT_TYPE;
    }

}
