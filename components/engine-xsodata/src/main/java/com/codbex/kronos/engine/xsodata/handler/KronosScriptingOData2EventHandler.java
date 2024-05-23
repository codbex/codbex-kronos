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
package com.codbex.kronos.engine.xsodata.handler;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.eclipse.dirigible.commons.api.helpers.GsonHelper;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLContext;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLDeleteBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLInsertBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLQueryBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLSelectBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLUpdateBuilder;

/**
 * The Class ScriptingOData2EventHandler.
 */
public class KronosScriptingOData2EventHandler extends AbstractKronosOData2EventHandler {

    /**
     * Before create entity.
     *
     * @param uriInfo the uri info
     * @param requestContentType the request content type
     * @param contentType the content type
     * @param entry the entry
     * @param context the context
     * @return the o data response
     */
    @Override
    public ODataResponse beforeCreateEntity(PostUriInfo uriInfo, String requestContentType, String contentType, ODataEntry entry,
            Map<Object, Object> context) {
        SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
        ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
        SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
        DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);

        Connection connection = null;
        try {
            String afterTableName = generateTemporaryTableName(uriInfo.getTargetType()
                                                                      .getName());
            createTempTableBeforeAndOnCreateEntity(queryBuilder, uriInfo, entry, oDataContext, sqlContext, dataSource, afterTableName);
            connection = dataSource.getConnection();

            context.put(CONNECTION_CONTEXT_KEY, connection);
            context.put(AFTER_TABLE_NAME_CONTEXT_KEY, afterTableName);

            callSuperBeforeCreateEntity(uriInfo, requestContentType, contentType, entry, context);
        } catch (ODataException | SQLException e) {
            throw new KronosScriptingOData2EventHandlerException(UNABLE_TO_HANDLE_BEFORE_CREATE_ENTITY_EVENT_ERROR, e);
        } finally {
            batchDropTemporaryTables(connection, (String) context.get(AFTER_TABLE_NAME_CONTEXT_KEY));
            closeConnection(connection);
        }

        return null;
    }

    /**
     * After create entity.
     *
     * @param uriInfo the uri info
     * @param requestContentType the request content type
     * @param contentType the content type
     * @param entry the entry
     * @param context the context
     * @return the o data response
     */
    @Override
    public ODataResponse afterCreateEntity(PostUriInfo uriInfo, String requestContentType, String contentType, ODataEntry entry,
            Map<Object, Object> context) {
        SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
        ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
        SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
        DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);

        Connection connection = null;
        try {
            String afterTableName = generateTemporaryTableName(uriInfo.getTargetType()
                                                                      .getName());
            createTempTableAfterCreateEntity(queryBuilder, uriInfo, oDataContext, sqlContext, dataSource, afterTableName);
            connection = dataSource.getConnection();

            context.put(CONNECTION_CONTEXT_KEY, connection);
            context.put(AFTER_TABLE_NAME_CONTEXT_KEY, afterTableName);

            callSuperAfterCreateEntity(uriInfo, requestContentType, contentType, entry, context);
        } catch (ODataException | SQLException e) {
            throw new KronosScriptingOData2EventHandlerException(UNABLE_TO_HANDLE_AFTER_CREATE_ENTITY_EVENT_ERROR, e);
        } finally {
            batchDropTemporaryTables(connection, (String) context.get(AFTER_TABLE_NAME_CONTEXT_KEY));
            closeConnection(connection);
        }
        return null;
    }

    /**
     * On create entity.
     *
     * @param uriInfo the uri info
     * @param content the content
     * @param requestContentType the request content type
     * @param contentType the content type
     * @param context the context
     * @return the o data response
     */
    @Override
    public ODataResponse onCreateEntity(PostUriInfo uriInfo, InputStream content, String requestContentType, String contentType,
            Map<Object, Object> context) {
        SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
        ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
        SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
        DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);
        ODataEntry entry = (ODataEntry) context.get(ENTRY_CONTEXT_KEY);

        Connection connection = null;
        try {
            String afterTableName = generateTemporaryTableName(uriInfo.getTargetType()
                                                                      .getName());
            createTempTableBeforeAndOnCreateEntity(queryBuilder, uriInfo, entry, oDataContext, sqlContext, dataSource, afterTableName);
            connection = dataSource.getConnection();

            Map<String, Object> entryMap = readEntryMap(connection, afterTableName);

            context.put(CONNECTION_CONTEXT_KEY, connection);
            context.put(AFTER_TABLE_NAME_CONTEXT_KEY, afterTableName);

            callSuperOnCreateEntity(uriInfo, content, requestContentType, contentType, context);
            return buildResponse((UriInfo) uriInfo, oDataContext, entryMap, contentType, HttpStatusCodes.CREATED);
        } catch (ODataException | SQLException e) {
            throw new KronosScriptingOData2EventHandlerException(UNABLE_TO_HANDLE_ON_CREATE_ENTITY_EVENT_ERROR, e);
        } finally {
            batchDropTemporaryTables(connection, (String) context.get(AFTER_TABLE_NAME_CONTEXT_KEY));
            closeConnection(connection);
        }
    }

    /**
     * Creates the temp table and return connection for before and on create entity.
     *
     * @param queryBuilder the query builder
     * @param uriInfo the uri info
     * @param entry the entry
     * @param oDataContext the o data context
     * @param sqlContext the sql context
     * @param dataSource the data source
     * @param afterTableName the after table name
     * @throws ODataException the o data exception
     * @throws SQLException the SQL exception
     */
    private void createTempTableBeforeAndOnCreateEntity(SQLQueryBuilder queryBuilder, PostUriInfo uriInfo, ODataEntry entry,
            ODataContext oDataContext, SQLContext sqlContext, DataSource dataSource, String afterTableName)
            throws ODataException, SQLException {
        try (Connection connectionParam = dataSource.getConnection()) {
            SQLInsertBuilder dummyBuilder = queryBuilder.buildInsertEntityQuery((UriInfo) uriInfo, entry, oDataContext);
            SQLInsertBuilder insertBuilder = queryBuilder.buildInsertEntityQuery((UriInfo) uriInfo, entry, oDataContext);
            String targetTableName = getSQLInsertBuilderTargetTable(dummyBuilder, sqlContext);
            createTemporaryTableLikeTable(connectionParam, afterTableName, targetTableName);
            insertIntoTemporaryTable(connectionParam, insertBuilder, afterTableName, sqlContext);
        }
    }

    /**
     * Creates the temp table and return connection for after create entity.
     *
     * @param queryBuilder the query builder
     * @param uriInfo the uri info
     * @param oDataContext the o data context
     * @param sqlContext the sql context
     * @param dataSource the data source
     * @param afterTableName the after table name
     * @throws ODataException the o data exception
     * @throws SQLException the SQL exception
     */
    private void createTempTableAfterCreateEntity(SQLQueryBuilder queryBuilder, PostUriInfo uriInfo, ODataContext oDataContext,
            SQLContext sqlContext, DataSource dataSource, String afterTableName) throws ODataException, SQLException {
        try (Connection connectionParam = dataSource.getConnection()) {
            SQLSelectBuilder selectBuilder = queryBuilder.buildSelectEntityQuery((UriInfo) uriInfo, oDataContext);
            createTemporaryTableAsSelect(connectionParam, afterTableName, selectBuilder, sqlContext);
        }
    }

    /**
     * Before update entity.
     *
     * @param uriInfo the uri info
     * @param requestContentType the request content type
     * @param merge the merge
     * @param contentType the content type
     * @param entry the entry
     * @param context the context
     * @return the o data response
     */
    @Override
    public ODataResponse beforeUpdateEntity(PutMergePatchUriInfo uriInfo, String requestContentType, boolean merge, String contentType,
            ODataEntry entry, Map<Object, Object> context) {
        SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
        ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
        SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
        DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);
        Map<String, Object> mappedKeys = (Map<String, Object>) context.get(MAPPED_KEYS_CONTEXT_KEY);

        Connection connection = null;
        try {
            String afterTableName = generateTemporaryTableName(uriInfo.getTargetType()
                                                                      .getName());
            String beforeTableName = generateTemporaryTableName(uriInfo.getTargetType()
                                                                       .getName());
            createTempTableBeforeAndOnUpdateEntity(queryBuilder, uriInfo, entry, oDataContext, sqlContext, dataSource, mappedKeys,
                    afterTableName, beforeTableName);
            connection = dataSource.getConnection();

            String beforeUpdateEntryJSON = GsonHelper.toJson(readEntryMap(connection, beforeTableName));

            context.put(CONNECTION_CONTEXT_KEY, connection);
            context.put(BEFORE_TABLE_NAME_CONTEXT_KEY, beforeTableName);
            context.put(AFTER_TABLE_NAME_CONTEXT_KEY, afterTableName);
            context.put(ENTRY_JSON_CONTEXT_KEY, beforeUpdateEntryJSON);

            callSuperBeforeUpdateEntity(uriInfo, requestContentType, merge, contentType, entry, context);
        } catch (ODataException | SQLException e) {
            throw new KronosScriptingOData2EventHandlerException(UNABLE_TO_HANDLE_BEFORE_UPDATE_ENTITY_EVENT_ERROR, e);
        } finally {
            batchDropTemporaryTables(connection, (String) context.get(BEFORE_TABLE_NAME_CONTEXT_KEY),
                    (String) context.get(AFTER_TABLE_NAME_CONTEXT_KEY));
            closeConnection(connection);
        }
        return null;
    }

    /**
     * After update entity.
     *
     * @param uriInfo the uri info
     * @param requestContentType the request content type
     * @param merge the merge
     * @param contentType the content type
     * @param entry the entry
     * @param context the context
     * @return the o data response
     */
    @Override
    public ODataResponse afterUpdateEntity(PutMergePatchUriInfo uriInfo, String requestContentType, boolean merge, String contentType,
            ODataEntry entry, Map<Object, Object> context) {
        SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
        ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
        SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
        DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);
        ODataEntry entryBeforeUpdate = (ODataEntry) context.get(ENTRY_CONTEXT_KEY);
        Map<String, Object> mappedKeys = (Map<String, Object>) context.get(MAPPED_KEYS_CONTEXT_KEY);

        Connection connection = null;
        try {
            String beforeTableName = generateTemporaryTableName(uriInfo.getTargetType()
                                                                       .getName());
            String afterTableName = generateTemporaryTableName(uriInfo.getTargetType()
                                                                      .getName());
            createTempTableAfterUpdateEntity(queryBuilder, uriInfo, entry, oDataContext, sqlContext, dataSource, entryBeforeUpdate,
                    mappedKeys, afterTableName, beforeTableName);
            connection = dataSource.getConnection();

            context.put(CONNECTION_CONTEXT_KEY, connection);
            context.put(BEFORE_TABLE_NAME_CONTEXT_KEY, beforeTableName);
            context.put(AFTER_TABLE_NAME_CONTEXT_KEY, afterTableName);

            callSuperAfterUpdateEntity(uriInfo, requestContentType, merge, contentType, entry, context);
        } catch (ODataException | SQLException e) {
            throw new KronosScriptingOData2EventHandlerException(UNABLE_TO_HANDLE_AFTER_UPDATE_ENTITY_EVENT_ERROR, e);
        } finally {
            batchDropTemporaryTables(connection, (String) context.get(BEFORE_TABLE_NAME_CONTEXT_KEY),
                    (String) context.get(AFTER_TABLE_NAME_CONTEXT_KEY));
            closeConnection(connection);
        }
        return null;
    }

    /**
     * On update entity.
     *
     * @param uriInfo the uri info
     * @param content the content
     * @param requestContentType the request content type
     * @param merge the merge
     * @param contentType the content type
     * @param context the context
     * @return the o data response
     */
    @Override
    public ODataResponse onUpdateEntity(PutMergePatchUriInfo uriInfo, InputStream content, String requestContentType, boolean merge,
            String contentType, Map<Object, Object> context) {
        SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
        ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
        SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
        DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);
        Map<String, Object> mappedKeys = (Map<String, Object>) context.get(MAPPED_KEYS_CONTEXT_KEY);
        ODataEntry entry = (ODataEntry) context.get(ENTRY_CONTEXT_KEY);

        Connection connection = null;
        try {
            String afterTableName = generateTemporaryTableName(uriInfo.getTargetType()
                                                                      .getName());
            String beforeTableName = generateTemporaryTableName(uriInfo.getTargetType()
                                                                       .getName());
            createTempTableBeforeAndOnUpdateEntity(queryBuilder, uriInfo, entry, oDataContext, sqlContext, dataSource, mappedKeys,
                    afterTableName, beforeTableName);
            connection = dataSource.getConnection();

            context.put(CONNECTION_CONTEXT_KEY, connection);
            context.put(BEFORE_TABLE_NAME_CONTEXT_KEY, beforeTableName);
            context.put(AFTER_TABLE_NAME_CONTEXT_KEY, afterTableName);

            callSuperOnUpdateEntity(uriInfo, content, requestContentType, merge, contentType, context);
        } catch (ODataException | SQLException e) {
            throw new KronosScriptingOData2EventHandlerException(UNABLE_TO_HANDLE_ON_UPDATE_ENTITY_EVENT_ERROR, e);
        } finally {
            batchDropTemporaryTables(connection, (String) context.get(BEFORE_TABLE_NAME_CONTEXT_KEY),
                    (String) context.get(AFTER_TABLE_NAME_CONTEXT_KEY));
            closeConnection(connection);
        }
        return null;
    }

    /**
     * Creates the temp table and return connection for before and on update entity.
     *
     * @param queryBuilder the query builder
     * @param uriInfo the uri info
     * @param entry the entry
     * @param oDataContext the o data context
     * @param sqlContext the sql context
     * @param dataSource the data source
     * @param mappedKeys the mapped keys
     * @param afterTableName the after table name
     * @param beforeTableName the before table name
     * @throws ODataException the o data exception
     * @throws SQLException the SQL exception
     */
    private void createTempTableBeforeAndOnUpdateEntity(SQLQueryBuilder queryBuilder, PutMergePatchUriInfo uriInfo, ODataEntry entry,
            ODataContext oDataContext, SQLContext sqlContext, DataSource dataSource, Map<String, Object> mappedKeys, String afterTableName,
            String beforeTableName) throws ODataException, SQLException {
        try (Connection connectionParam = dataSource.getConnection()) {
            SQLSelectBuilder selectBuilder = queryBuilder.buildSelectEntityQuery((UriInfo) uriInfo, oDataContext);
            SQLUpdateBuilder updateBuilder = queryBuilder.buildUpdateEntityQuery((UriInfo) uriInfo, entry, mappedKeys, oDataContext);
            createTemporaryTableAsSelect(connectionParam, beforeTableName, selectBuilder, sqlContext);
            createTemporaryTableAsSelect(connectionParam, afterTableName, selectBuilder, sqlContext);
            updateTemporaryTable(connectionParam, updateBuilder, afterTableName, sqlContext);
        }
    }

    /**
     * Creates the temp table and return connection for after update entity.
     *
     * @param queryBuilder the query builder
     * @param uriInfo the uri info
     * @param entry the entry
     * @param oDataContext the o data context
     * @param sqlContext the sql context
     * @param dataSource the data source
     * @param entryBeforeUpdate the entry before update
     * @param mappedKeys the mapped keys
     * @param afterTableName the after table name
     * @param beforeTableName the before table name
     * @throws ODataException the o data exception
     * @throws SQLException the SQL exception
     */
    private void createTempTableAfterUpdateEntity(SQLQueryBuilder queryBuilder, PutMergePatchUriInfo uriInfo, ODataEntry entry,
            ODataContext oDataContext, SQLContext sqlContext, DataSource dataSource, ODataEntry entryBeforeUpdate,
            Map<String, Object> mappedKeys, String afterTableName, String beforeTableName) throws ODataException, SQLException {
        try (Connection connectionParam = dataSource.getConnection()) {
            SQLUpdateBuilder dummyBuilder = queryBuilder.buildUpdateEntityQuery((UriInfo) uriInfo, entry, mappedKeys, oDataContext);
            SQLInsertBuilder insertBuilder = queryBuilder.buildInsertEntityQuery((UriInfo) uriInfo, entryBeforeUpdate, oDataContext);
            SQLSelectBuilder selectBuilder = queryBuilder.buildSelectEntityQuery((UriInfo) uriInfo, oDataContext);
            String targetTableName = getSQLUpdateBuilderTargetTable(dummyBuilder, sqlContext);
            createTemporaryTableLikeTable(connectionParam, beforeTableName, targetTableName);
            insertIntoTemporaryTable(connectionParam, insertBuilder, beforeTableName, sqlContext);
            createTemporaryTableAsSelect(connectionParam, afterTableName, selectBuilder, sqlContext);
        }
    }

    /**
     * Before delete entity.
     *
     * @param uriInfo the uri info
     * @param contentType the content type
     * @param context the context
     * @return the o data response
     */
    @Override
    public ODataResponse beforeDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) {
        SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
        ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
        SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
        DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);

        Connection connection = null;
        try {
            SQLSelectBuilder selectBuilder = queryBuilder.buildSelectEntityQuery((UriInfo) uriInfo, oDataContext);

            String beforeTableName = generateTemporaryTableName(uriInfo.getTargetType()
                                                                       .getName());

            connection = dataSource.getConnection();
            createTemporaryTableAsSelect(connection, beforeTableName, selectBuilder, sqlContext);

            context.put(CONNECTION_CONTEXT_KEY, connection);
            context.put(BEFORE_TABLE_NAME_CONTEXT_KEY, beforeTableName);
            String beforeDeleteEntryJSON = GsonHelper.toJson(readEntryMap(connection, beforeTableName));
            context.put(ENTRY_JSON_CONTEXT_KEY, beforeDeleteEntryJSON);

            callSuperBeforeDeleteEntity(uriInfo, contentType, context);
        } catch (ODataException | SQLException e) {
            throw new KronosScriptingOData2EventHandlerException(UNABLE_TO_HANDLE_BEFORE_DELETE_ENTITY_EVENT_ERROR, e);
        } finally {
            batchDropTemporaryTables(connection, (String) context.get(BEFORE_TABLE_NAME_CONTEXT_KEY));
            closeConnection(connection);
        }
        return null;
    }

    /**
     * After delete entity.
     *
     * @param uriInfo the uri info
     * @param contentType the content type
     * @param context the context
     * @return the o data response
     */
    @Override
    public ODataResponse afterDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) {
        SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
        ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
        SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
        DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);
        Map<String, Object> mappedKeys = (Map<String, Object>) context.get(MAPPED_KEYS_CONTEXT_KEY);
        ODataEntry entryBeforeDelete = (ODataEntry) context.get(ENTRY_CONTEXT_KEY);

        Connection connection = null;
        try {
            String beforeTableName = generateTemporaryTableName(uriInfo.getTargetType()
                                                                       .getName());
            createTempTableAfterDeleteEntity(queryBuilder, uriInfo, oDataContext, sqlContext, dataSource, entryBeforeDelete, mappedKeys,
                    beforeTableName);
            connection = dataSource.getConnection();

            context.put(CONNECTION_CONTEXT_KEY, connection);
            context.put(BEFORE_TABLE_NAME_CONTEXT_KEY, beforeTableName);

            callSuperAfterDeleteEntity(uriInfo, contentType, context);
        } catch (ODataException | SQLException e) {
            throw new KronosScriptingOData2EventHandlerException(UNABLE_TO_HANDLE_AFTER_DELETE_ENTITY_EVENT_ERROR, e);
        } finally {
            batchDropTemporaryTables(connection, (String) context.get(BEFORE_TABLE_NAME_CONTEXT_KEY));
            closeConnection(connection);
        }
        return null;
    }

    /**
     * On delete entity.
     *
     * @param uriInfo the uri info
     * @param contentType the content type
     * @param context the context
     * @return the o data response
     */
    @Override
    public ODataResponse onDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) {
        SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
        ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
        SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
        DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);

        Connection connection = null;
        try {
            String beforeTableName = generateTemporaryTableName(uriInfo.getTargetType()
                                                                       .getName());
            createTempTableBeforeAndOnDeleteEntity(queryBuilder, uriInfo, oDataContext, sqlContext, dataSource, beforeTableName);
            connection = dataSource.getConnection();

            context.put(CONNECTION_CONTEXT_KEY, connection);
            context.put(BEFORE_TABLE_NAME_CONTEXT_KEY, beforeTableName);

            callSuperOnDeleteEntity(uriInfo, contentType, context);
        } catch (ODataException | SQLException e) {
            throw new KronosScriptingOData2EventHandlerException(UNABLE_TO_HANDLE_ON_DELETE_ENTITY_EVENT_ERROR, e);
        } finally {
            batchDropTemporaryTables(connection, (String) context.get(BEFORE_TABLE_NAME_CONTEXT_KEY));
            closeConnection(connection);
        }
        return null;
    }

    /**
     * Creates the temp table and return connection for before and on delete entity.
     *
     * @param queryBuilder the query builder
     * @param uriInfo the uri info
     * @param oDataContext the o data context
     * @param sqlContext the sql context
     * @param dataSource the data source
     * @param beforeTableName the before table name
     * @throws ODataException the o data exception
     * @throws SQLException the SQL exception
     */
    private void createTempTableBeforeAndOnDeleteEntity(SQLQueryBuilder queryBuilder, DeleteUriInfo uriInfo, ODataContext oDataContext,
            SQLContext sqlContext, DataSource dataSource, String beforeTableName) throws ODataException, SQLException {
        try (Connection connectionParam = dataSource.getConnection()) {
            SQLSelectBuilder selectBuilder = queryBuilder.buildSelectEntityQuery((UriInfo) uriInfo, oDataContext);
            createTemporaryTableAsSelect(connectionParam, beforeTableName, selectBuilder, sqlContext);
        }
    }

    /**
     * Creates the temp table and return connection for after delete entity.
     *
     * @param queryBuilder the query builder
     * @param uriInfo the uri info
     * @param oDataContext the o data context
     * @param sqlContext the sql context
     * @param dataSource the data source
     * @param entryBeforeDelete the entry before delete
     * @param mappedKeys the mapped keys
     * @param beforeTableName the before table name
     * @throws ODataException the o data exception
     * @throws SQLException the SQL exception
     */
    private void createTempTableAfterDeleteEntity(SQLQueryBuilder queryBuilder, DeleteUriInfo uriInfo, ODataContext oDataContext,
            SQLContext sqlContext, DataSource dataSource, ODataEntry entryBeforeDelete, Map<String, Object> mappedKeys,
            String beforeTableName) throws ODataException, SQLException {
        try (Connection connectionParam = dataSource.getConnection()) {
            SQLDeleteBuilder dummyBuilder = queryBuilder.buildDeleteEntityQuery((UriInfo) uriInfo, mappedKeys, oDataContext);
            SQLInsertBuilder insertBuilder = queryBuilder.buildInsertEntityQuery((UriInfo) uriInfo, entryBeforeDelete, oDataContext);
            String targetTableName = getSQLDeleteBuilderTargetTable(dummyBuilder, sqlContext);
            createTemporaryTableLikeTable(connectionParam, beforeTableName, targetTableName);
            insertIntoTemporaryTable(connectionParam, insertBuilder, beforeTableName, sqlContext);
        }
    }

    /**
     * Call super before create entity.
     *
     * @param uriInfo the uri info
     * @param requestContentType the request content type
     * @param contentType the content type
     * @param entry the entry
     * @param context the context
     * @throws ODataException the o data exception
     */
    void callSuperBeforeCreateEntity(PostUriInfo uriInfo, String requestContentType, String contentType, ODataEntry entry,
            Map<Object, Object> context) throws ODataException {
        super.beforeCreateEntity(uriInfo, requestContentType, contentType, entry, context);
    }

    /**
     * Call super after create entity.
     *
     * @param uriInfo the uri info
     * @param requestContentType the request content type
     * @param contentType the content type
     * @param entry the entry
     * @param context the context
     * @throws ODataException the o data exception
     */
    void callSuperAfterCreateEntity(PostUriInfo uriInfo, String requestContentType, String contentType, ODataEntry entry,
            Map<Object, Object> context) throws ODataException {
        super.afterCreateEntity(uriInfo, requestContentType, contentType, entry, context);
    }

    /**
     * Call super on create entity.
     *
     * @param uriInfo the uri info
     * @param content the content
     * @param requestContentType the request content type
     * @param contentType the content type
     * @param context the context
     * @throws ODataException the o data exception
     */
    void callSuperOnCreateEntity(PostUriInfo uriInfo, InputStream content, String requestContentType, String contentType,
            Map<Object, Object> context) throws ODataException {
        super.onCreateEntity(uriInfo, content, requestContentType, contentType, context);
    }

    /**
     * Call super before update entity.
     *
     * @param uriInfo the uri info
     * @param requestContentType the request content type
     * @param merge the merge
     * @param contentType the content type
     * @param entry the entry
     * @param context the context
     * @throws ODataException the o data exception
     */
    void callSuperBeforeUpdateEntity(PutMergePatchUriInfo uriInfo, String requestContentType, boolean merge, String contentType,
            ODataEntry entry, Map<Object, Object> context) throws ODataException {
        super.beforeUpdateEntity(uriInfo, requestContentType, merge, contentType, entry, context);
    }

    /**
     * Call super after update entity.
     *
     * @param uriInfo the uri info
     * @param requestContentType the request content type
     * @param merge the merge
     * @param contentType the content type
     * @param entry the entry
     * @param context the context
     * @throws ODataException the o data exception
     */
    void callSuperAfterUpdateEntity(PutMergePatchUriInfo uriInfo, String requestContentType, boolean merge, String contentType,
            ODataEntry entry, Map<Object, Object> context) throws ODataException {
        super.afterUpdateEntity(uriInfo, requestContentType, merge, contentType, entry, context);
    }

    /**
     * Call super on update entity.
     *
     * @param uriInfo the uri info
     * @param content the content
     * @param requestContentType the request content type
     * @param merge the merge
     * @param contentType the content type
     * @param context the context
     * @throws ODataException the o data exception
     */
    void callSuperOnUpdateEntity(PutMergePatchUriInfo uriInfo, InputStream content, String requestContentType, boolean merge,
            String contentType, Map<Object, Object> context) throws ODataException {
        super.onUpdateEntity(uriInfo, content, requestContentType, merge, contentType, context);
    }

    /**
     * Call super before delete entity.
     *
     * @param uriInfo the uri info
     * @param contentType the content type
     * @param context the context
     * @throws ODataException the o data exception
     */
    void callSuperBeforeDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) throws ODataException {
        super.beforeDeleteEntity(uriInfo, contentType, context);
    }

    /**
     * Call super after delete entity.
     *
     * @param uriInfo the uri info
     * @param contentType the content type
     * @param context the context
     * @throws ODataException the o data exception
     */
    void callSuperAfterDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) throws ODataException {
        super.afterDeleteEntity(uriInfo, contentType, context);
    }

    /**
     * Call super on delete entity.
     *
     * @param uriInfo the uri info
     * @param contentType the content type
     * @param context the context
     * @throws ODataException the o data exception
     */
    void callSuperOnDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) throws ODataException {
        super.onDeleteEntity(uriInfo, contentType, context);
    }

}
