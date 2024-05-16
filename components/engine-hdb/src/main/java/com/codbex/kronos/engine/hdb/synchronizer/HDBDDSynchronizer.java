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
import com.codbex.kronos.engine.hdb.domain.HDBDD;
import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraints;
import com.codbex.kronos.engine.hdb.domain.HDBTableType;
import com.codbex.kronos.engine.hdb.domain.HDBTableTypePrimaryKey;
import com.codbex.kronos.engine.hdb.domain.HDBView;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.engine.hdb.processors.HDBTableAlterProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBTableCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBTableTypeCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBViewCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBViewDropProcessor;
import com.codbex.kronos.engine.hdb.service.HDBDDService;
import com.codbex.kronos.engine.hdb.service.HDBTableService;
import com.codbex.kronos.engine.hdb.service.HDBTableTypeService;
import com.codbex.kronos.engine.hdb.service.HDBViewService;

// TODO: Auto-generated Javadoc
/**
 * The Class HDBDDSynchronizer.
 */
@Component
@Order(210)
public class HDBDDSynchronizer extends BaseSynchronizer<HDBDD, Long> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HDBDDSynchronizer.class);

    /**
     * The Constant FILE_EXTENSION_SCHEMA.
     */
    private static final String FILE_EXTENSION_HDBDD = ".hdbdd";

    /**
     * The schema service.
     */
    private final HDBDDService hdbddService;

    /**
     * The table service.
     */
    private final HDBTableService tableService;

    /**
     * The view service.
     */
    private final HDBViewService viewService;

    /**
     * The table type service.
     */
    private HDBTableTypeService tableTypeService;

    /**
     * The datasources manager.
     */
    private final DataSourcesManager datasourcesManager;

    /**
     * The synchronization callback.
     */
    private SynchronizerCallback callback;

    /**
     * Instantiates a new schema synchronizer.
     *
     * @param hdbddService the hdbdd service
     * @param datasourcesManager the datasources manager
     * @param tableService the table service
     * @param viewService the view service
     */
    @Autowired
    public HDBDDSynchronizer(HDBDDService hdbddService, DataSourcesManager datasourcesManager, HDBTableService tableService,
            HDBViewService viewService) {
        this.hdbddService = hdbddService;
        this.datasourcesManager = datasourcesManager;
        this.tableService = tableService;
        this.viewService = viewService;
    }

    /**
     * Gets the service.
     *
     * @return the service
     */
    @Override
    public HDBDDService getService() {
        return hdbddService;
    }

    /**
     * Gets the table service.
     *
     * @return the table service
     */
    public HDBTableService getTableService() {
        return tableService;
    }

    /**
     * Gets the view service.
     *
     * @return the view service
     */
    public HDBViewService getViewService() {
        return viewService;
    }

    /**
     * Gets the table type service.
     *
     * @return the table type service
     */
    public HDBTableTypeService getTableTypeService() {
        return tableTypeService;
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
        return HDBDD.ARTEFACT_TYPE.equals(type);
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
    public List<HDBDD> parse(String location, byte[] content) throws ParseException {
        HDBDD hdbdd;
        try {
            hdbdd = HDBDataStructureModelFactory.parseHdbdd(location, content);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            if (logger.isErrorEnabled()) {
                logger.error("hdbdd: {}", location);
            }
            if (logger.isErrorEnabled()) {
                logger.error("content: {}", new String(content));
            }
            throw new ParseException(e.getMessage(), 0);
        }
        // Configuration.configureObject(schema);
        hdbdd.setLocation(location);
        if (hdbdd.getName() == null) {
            hdbdd.setName("PUBLIC");
        }
        hdbdd.setType(HDBDD.ARTEFACT_TYPE);
        hdbdd.updateKey();

        hdbdd.getTables()
             .forEach(t -> {
                 t.setHdbdd(hdbdd);
                 t.setLocation(location);
                 if (t.getName() == null) {
                     t.setName("PUBLIC");
                 }
                 t.setType(HDBTable.ARTEFACT_TYPE);
                 t.updateKey();
                 t.setConstraints(new HDBTableConstraints(t));
                 HDBTablesSynchronizer.assignParent(t);
             });
        hdbdd.getViews()
             .forEach(v -> {
                 v.setHdbdd(hdbdd);
                 v.setLocation(location);
                 if (v.getName() == null) {
                     v.setName("PUBLIC");
                 }
                 v.setType(HDBView.ARTEFACT_TYPE);
                 v.updateKey();
             });
        hdbdd.getTableTypes()
             .forEach(tt -> {
                 tt.setHdbdd(hdbdd);
                 tt.setLocation(location);
                 if (tt.getName() == null) {
                     tt.setName("PUBLIC");
                 }
                 tt.setType(HDBTableType.ARTEFACT_TYPE);
                 tt.updateKey();
                 tt.setPrimaryKey(new HDBTableTypePrimaryKey(tt));
                 HDBTableTypesSynchronizer.assignParent(tt);
             });

        try {
            HDBDD maybe = getService().findByKey(hdbdd.getKey());
            if (maybe != null) {
                hdbdd.setId(maybe.getId());
                hdbdd.getTables()
                     .forEach(t -> {
                         HDBTable m = getTableService().findByKey(hdbdd.constructKey(HDBTable.ARTEFACT_TYPE, location, t.getName()));
                         if (m != null) {
                             t.setId(m.getId());
                         }
                         HDBTablesSynchronizer.reassignIds(t, m);
                     });
                hdbdd.getViews()
                     .forEach(v -> {
                         HDBView m = getViewService().findByKey(hdbdd.constructKey(HDBView.ARTEFACT_TYPE, location, v.getName()));
                         if (m != null) {
                             v.setId(m.getId());
                         }
                     });
                hdbdd.getTableTypes()
                     .forEach(tt -> {
                         HDBTableType m =
                                 getTableTypeService().findByKey(hdbdd.constructKey(HDBTableType.ARTEFACT_TYPE, location, tt.getName()));
                         if (m != null) {
                             tt.setId(m.getId());
                         }
                     });
            }
            getService().save(hdbdd);
            return List.of(hdbdd);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            if (logger.isErrorEnabled()) {
                logger.error("hdbdd: {}", hdbdd);
            }
            if (logger.isErrorEnabled()) {
                logger.error("content: {}", new String(content));
            }
            throw new ParseException(e.getMessage(), 0);
        }
    }

    /**
     * Retrieve.
     *
     * @param location the location
     * @return the list
     */
    @Override
    public List<HDBDD> retrieve(String location) {
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
    public void setStatus(HDBDD artefact, ArtefactLifecycle lifecycle, String error) {
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
    public boolean completeImpl(TopologyWrapper<HDBDD> wrapper, ArtefactPhase flow) {

        try (Connection connection = datasourcesManager.getDefaultDataSource()
                                                       .getConnection()) {

            HDBDD hdbdd = wrapper.getArtefact();

            switch (flow) {
                case CREATE:
                    if (ArtefactLifecycle.NEW.equals(hdbdd.getLifecycle())) {
                        executeHDBDDCreate(connection, hdbdd);
                        callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
                    } else if (ArtefactLifecycle.FAILED.equals(hdbdd.getLifecycle())) {
                        executeFailedHDBDDCreate(connection, hdbdd);
                        callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
                        ProblemsFacade.deleteArtefactSynchronizationProblem(hdbdd);
                    }
                    break;
                case UPDATE:
                    if (ArtefactLifecycle.MODIFIED.equals(hdbdd.getLifecycle())) {
                        executeHDBDDUpdate(connection, hdbdd);
                        callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
                        ProblemsFacade.deleteArtefactSynchronizationProblem(hdbdd);
                    }
                    break;
                case DELETE:
                    if (ArtefactLifecycle.CREATED.equals(hdbdd.getLifecycle()) || ArtefactLifecycle.UPDATED.equals(hdbdd.getLifecycle())) {
                        executeHDBDDDrop(connection, hdbdd);
                        callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, "");
                        break;
                    }
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
            callback.registerState(this, wrapper, ArtefactLifecycle.FAILED, e.getMessage());
            ProblemsFacade.upsertArtefactSynchronizationProblem(wrapper.getArtefact(), errorMessage);
            return false;
        }
    }

    /**
     * Cleanup.
     *
     * @param hdbdd the HDBDD
     */
    @Override
    public void cleanupImpl(HDBDD hdbdd) {
        try {
            getService().delete(hdbdd);
            callback.registerState(this, hdbdd, ArtefactLifecycle.DELETED, "");
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
            callback.addError(e.getMessage());
            callback.registerState(this, hdbdd, ArtefactLifecycle.FAILED, e.getMessage());
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
     * Execute HDBDD update.
     *
     * @param connection the connection
     * @param hdbddModel the HDBDD model
     * @throws SQLException the SQL exception
     */
    public void executeHDBDDUpdate(Connection connection, HDBDD hdbddModel) throws SQLException {
        if (logger.isInfoEnabled()) {
            logger.info("Processing Update HDBDD: " + hdbddModel.getName());
        }
        executeHDBDDCreate(connection, hdbddModel);
    }

    /**
     * Execute HDBDD create.
     *
     * @param connection the connection
     * @param hdbddModel the HDBDD model
     * @throws SQLException the SQL exception
     */
    public void executeHDBDDCreate(Connection connection, HDBDD hdbddModel) throws SQLException {

        for (HDBTableType entityModel : hdbddModel.getTableTypes()) {
            new HDBTableTypeCreateProcessor().execute(connection, entityModel);
        }

        for (HDBTable entityModel : hdbddModel.getTables()) {
            String tableName = HDBUtils.escapeArtifactName(entityModel.getName(), entityModel.getSchema());
            if (!SqlFactory.getNative(connection)
                           .exists(connection, tableName, DatabaseArtifactTypes.TABLE)) {
                new HDBTableCreateProcessor().execute(connection, entityModel);
            } else {
                new HDBTableAlterProcessor().execute(connection, entityModel);
            }
        }

        for (HDBView entityModel : hdbddModel.getViews()) {
            String viewName = HDBUtils.escapeArtifactName(entityModel.getName(), entityModel.getSchema());
            if (!SqlFactory.getNative(connection)
                           .exists(connection, viewName, DatabaseArtifactTypes.VIEW)) {
                new HDBViewCreateProcessor().execute(connection, entityModel);
            } else {
                new HDBViewDropProcessor().execute(connection, entityModel);
                new HDBViewCreateProcessor().execute(connection, entityModel);
            }
        }
    }

    /**
     * Execute HDBDD create of FAILED artefacts.
     *
     * @param connection the connection
     * @param hdbddModel the HDBDD model
     * @throws SQLException the SQL exception
     */
    public void executeFailedHDBDDCreate(Connection connection, HDBDD hdbddModel) throws SQLException {

        for (HDBTableType entityModel : hdbddModel.getTableTypes()) {
            try {
                new HDBTableTypeCreateProcessor().execute(connection, entityModel);
            } catch (Exception e) {
                // Do nothing
            }
        }

        for (HDBTable entityModel : hdbddModel.getTables()) {
            try {
                String tableName = HDBUtils.escapeArtifactName(entityModel.getName(), entityModel.getSchema());
                if (!SqlFactory.getNative(connection)
                               .exists(connection, tableName, DatabaseArtifactTypes.TABLE)) {
                    new HDBTableCreateProcessor().execute(connection, entityModel);
                }
            } catch (Exception e) {
                // Do nothing
            }
        }

        for (HDBView entityModel : hdbddModel.getViews()) {
            try {
                String viewName = HDBUtils.escapeArtifactName(entityModel.getName(), entityModel.getSchema());
                if (!SqlFactory.getNative(connection)
                               .exists(connection, viewName, DatabaseArtifactTypes.VIEW)) {
                    new HDBViewCreateProcessor().execute(connection, entityModel);
                }
            } catch (Exception e) {
                // Do nothing
            }
        }
    }

    /**
     * Execute schema drop.
     *
     * @param connection the connection
     * @param hdbddModel the HDBDD model
     * @throws SQLException the SQL exception
     */
    public void executeHDBDDDrop(Connection connection, HDBDD hdbddModel) throws SQLException {
        // HDBDDDropProcessor.execute(connection, hdbddModel);
    }

    /**
     * Gets the file extension.
     *
     * @return the file extension
     */
    @Override
    public String getFileExtension() {
        return FILE_EXTENSION_HDBDD;
    }

    /**
     * Gets the artefact type.
     *
     * @return the artefact type
     */
    @Override
    public String getArtefactType() {
        return HDBDD.ARTEFACT_TYPE;
    }

}
