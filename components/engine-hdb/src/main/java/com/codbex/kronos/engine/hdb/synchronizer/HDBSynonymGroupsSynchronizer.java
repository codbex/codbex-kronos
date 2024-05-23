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

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.domain.HDBSynonym;
import com.codbex.kronos.engine.hdb.domain.HDBSynonymGroup;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBSynonymCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBSynonymDropProcessor;
import com.codbex.kronos.engine.hdb.service.HDBSynonymGroupService;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
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
 * The Class HDBSynonymGroupSynchronizer.
 */
@Component
@Order(220)
public class HDBSynonymGroupsSynchronizer extends BaseSynchronizer<HDBSynonymGroup, Long> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HDBSynonymGroupsSynchronizer.class);

    /**
     * The Constant FILE_EXTENSION_HDBSYNONYM.
     */
    private static final String FILE_EXTENSION_HDBSYNONYM = ".hdbsynonym";

    /**
     * The synonym group service.
     */
    private final HDBSynonymGroupService synonymGroupService;

    /**
     * The datasources manager.
     */
    private final DataSourcesManager datasourcesManager;

    /**
     * The synchronization callback.
     */
    private SynchronizerCallback callback;

    /**
     * Instantiates a new synonym group synchronizer.
     *
     * @param synonymGroupService the synonym group service
     * @param datasourcesManager the datasources manager
     */
    @Autowired
    public HDBSynonymGroupsSynchronizer(HDBSynonymGroupService synonymGroupService, DataSourcesManager datasourcesManager) {
        this.synonymGroupService = synonymGroupService;
        this.datasourcesManager = datasourcesManager;
    }

    /**
     * Gets the service.
     *
     * @return the service
     */
    @Override
    public HDBSynonymGroupService getService() {
        return synonymGroupService;
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
        return HDBSynonymGroup.ARTEFACT_TYPE.equals(type);
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
    public List<HDBSynonymGroup> parse(String location, byte[] content) throws ParseException {
        HDBSynonymGroup synonymGroup;
        try {
            synonymGroup = HDBDataStructureModelFactory.parseSynonym(location, content);
        } catch (DataStructuresException | IOException e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            if (logger.isErrorEnabled()) {
                logger.error("hdbtable: {}", location);
            }
            if (logger.isErrorEnabled()) {
                logger.error("content: {}", new String(content));
            }
            throw new ParseException(e.getMessage(), 0);
        }

        // Configuration.configureObject(synonymGroup);
        synonymGroup.setLocation(location);
        synonymGroup.setName(FilenameUtils.getBaseName(location) + "_group");
        synonymGroup.setType(HDBSynonymGroup.ARTEFACT_TYPE);
        synonymGroup.updateKey();
        assignParenAndLocation(synonymGroup, location);

        try {
            HDBSynonymGroup maybe = getService().findByKey(synonymGroup.getKey());
            if (maybe != null) {
                synonymGroup.setId(maybe.getId());
                reassignIds(synonymGroup, maybe);
            }
            getService().save(synonymGroup);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            if (logger.isErrorEnabled()) {
                logger.error("synonym: {}", synonymGroup);
            }
            if (logger.isErrorEnabled()) {
                logger.error("content: {}", new String(content));
            }
            throw new ParseException(e.getMessage(), 0);
        }
        return List.of(synonymGroup);
    }

    /**
     * Assign paren and location.
     *
     * @param synonymGroup the synonym group
     * @param location the location
     */
    static void assignParenAndLocation(HDBSynonymGroup synonymGroup, String location) {
        synonymGroup.getSynonymDefinitions()
                    .values()
                    .forEach(s -> {
                        s.setGroup(synonymGroup);
                        s.getTarget()
                         .setSynonym(s);
                        s.setLocation(location);
                        s.setType(HDBSynonym.ARTEFACT_TYPE);
                        s.updateKey();
                    });
    }

    /**
     * Reassign ids.
     *
     * @param synonymGroup the synonym group
     * @param maybe the maybe
     */
    static void reassignIds(HDBSynonymGroup synonymGroup, HDBSynonymGroup maybe) {
        synonymGroup.getSynonymDefinitions()
                    .values()
                    .forEach(s -> {
                        HDBSynonym m = maybe.getSynonymDefinitions()
                                            .get(s.getName());
                        if (m != null) {
                            s.setId(m.getId());
                            s.getTarget()
                             .setId(m.getTarget()
                                     .getId());
                        }
                    });
    }

    /**
     * Retrieve.
     *
     * @param location the location
     * @return the list
     */
    @Override
    public List<HDBSynonymGroup> retrieve(String location) {
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
    public void setStatus(HDBSynonymGroup artefact, ArtefactLifecycle lifecycle, String error) {
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
    public boolean completeImpl(TopologyWrapper<HDBSynonymGroup> wrapper, ArtefactPhase flow) {

        try (Connection connection = datasourcesManager.getDefaultDataSource()
                                                       .getConnection()) {

            HDBSynonymGroup synonymGroup = wrapper.getArtefact();

            switch (flow) {
                case CREATE:
                    if (ArtefactLifecycle.NEW.equals(synonymGroup.getLifecycle())) {
                        if (!SqlFactory.getNative(connection)
                                       .exists(connection, synonymGroup.getName(), DatabaseArtifactTypes.SYNONYM)) {
                            executeSynonymGroupCreate(connection, synonymGroup);
                            callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
                        } else {
                            if (logger.isWarnEnabled()) {
                                logger.warn(String.format("HDBSynonymGroup [%s] already exists during the update process",
                                        synonymGroup.getName()));
                            }
                            executeSynonymGroupUpdate(connection, synonymGroup);
                            callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
                        }
                    } else if (ArtefactLifecycle.FAILED.equals(synonymGroup.getLifecycle())) {
                        if (!SqlFactory.getNative(connection)
                                       .exists(connection, synonymGroup.getName(), DatabaseArtifactTypes.SYNONYM)) {
                            executeSynonymGroupCreate(connection, synonymGroup);
                            callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
                            ProblemsFacade.deleteArtefactSynchronizationProblem(synonymGroup);
                        }
                    }
                    break;
                case UPDATE:
                    if (ArtefactLifecycle.MODIFIED.equals(synonymGroup.getLifecycle())) {
                        executeSynonymGroupUpdate(connection, synonymGroup);
                        callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
                        ProblemsFacade.deleteArtefactSynchronizationProblem(synonymGroup);
                    }
                    break;
                case DELETE:
                    if (ArtefactLifecycle.CREATED.equals(synonymGroup.getLifecycle())) {
                        if (SqlFactory.getNative(connection)
                                      .exists(connection, synonymGroup.getName(), DatabaseArtifactTypes.SYNONYM)) {
                            executeSynonymGroupDrop(connection, synonymGroup);
                            callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, "");
                        }
                        callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, "");
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
            if (logger.isErrorEnabled()) {
                logger.error(errorMessage, e);
            }
            callback.addError(errorMessage);
            callback.registerState(this, wrapper, ArtefactLifecycle.FAILED, errorMessage);
            ProblemsFacade.upsertArtefactSynchronizationProblem(wrapper.getArtefact(), errorMessage);
            return false;
        }
    }

    /**
     * Cleanup.
     *
     * @param synonymGroup the SynonymGroup
     */
    @Override
    public void cleanupImpl(HDBSynonymGroup synonymGroup) {
        try (Connection connection = datasourcesManager.getDefaultDataSource()
                                                       .getConnection()) {
            getService().delete(synonymGroup);
            callback.registerState(this, synonymGroup, ArtefactLifecycle.DELETED, "");
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            callback.addError(e.getMessage());
            callback.registerState(this, synonymGroup, ArtefactLifecycle.FAILED, e.getMessage());
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
     * Execute SynonymGroup update.
     *
     * @param connection the connection
     * @param synonymGroupModel the SynonymGroup model
     * @throws SQLException the SQL exception
     */
    public void executeSynonymGroupUpdate(Connection connection, HDBSynonymGroup synonymGroupModel) throws SQLException {
        if (logger.isInfoEnabled()) {
            logger.info("Processing Update Synonym: " + synonymGroupModel.getName());
        }
        if (SqlFactory.getNative(connection)
                      .exists(connection, synonymGroupModel.getName(), DatabaseArtifactTypes.SYNONYM)) {
            executeSynonymGroupDrop(connection, synonymGroupModel);
            executeSynonymGroupCreate(connection, synonymGroupModel);
        } else {
            executeSynonymGroupCreate(connection, synonymGroupModel);
        }
    }

    /**
     * Execute SynonymGroup create.
     *
     * @param connection the connection
     * @param synonymGroupModel the SynonymGroup model
     * @throws SQLException the SQL exception
     */
    public void executeSynonymGroupCreate(Connection connection, HDBSynonymGroup synonymGroupModel) throws SQLException {
        new HDBSynonymCreateProcessor().execute(connection, synonymGroupModel);
    }

    /**
     * Execute SynonymGroup drop.
     *
     * @param connection the connection
     * @param synonymGroupModel the SynonymGroup model
     * @throws SQLException the SQL exception
     */
    public void executeSynonymGroupDrop(Connection connection, HDBSynonymGroup synonymGroupModel) throws SQLException {
        new HDBSynonymDropProcessor().execute(connection, synonymGroupModel);
    }

    /**
     * Gets the file extension.
     *
     * @return the file extension
     */
    @Override
    public String getFileExtension() {
        return FILE_EXTENSION_HDBSYNONYM;
    }

    /**
     * Gets the artefact type.
     *
     * @return the artefact type
     */
    @Override
    public String getArtefactType() {
        return HDBSynonymGroup.ARTEFACT_TYPE;
    }

}
