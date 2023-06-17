/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.xsodata.ds.handler;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.eclipse.dirigible.components.data.structures.domain.Table;
import org.eclipse.dirigible.components.odata.api.ODataHandler;
import org.eclipse.dirigible.components.odata.transformers.ODataDatabaseMetadataUtil;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLContext;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLDeleteBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLInsertBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLQueryBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLSelectBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLUpdateBuilder;

import com.codbex.kronos.xsodata.ds.service.TableMetadataProvider;

/**
 * The Class KronosProcedureOData2EventHandler.
 */
public class KronosProcedureOData2EventHandler extends AbstractKronosOData2EventHandler {

  /**
	 * Before create entity.
	 *
	 * @param uriInfo            the uri info
	 * @param requestContentType the request content type
	 * @param contentType        the content type
	 * @param entry              the entry
	 * @param context            the context
	 * @return the o data response
	 */
  @Override
  public ODataResponse beforeCreateEntity(PostUriInfo uriInfo, String requestContentType, String contentType, ODataEntry entry,
      Map<Object, Object> context) {
    SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
    ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
    SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
    DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);
    ODataHandler handler = (ODataHandler) context.get(HANDLER_CONTEXT_KEY);

    String newTableParam = null;
    Connection connection = null;
    try {
      SQLInsertBuilder dummyBuilder = queryBuilder.buildInsertEntityQuery((UriInfo) uriInfo, entry, oDataContext);
      SQLInsertBuilder insertBuilder = queryBuilder.buildInsertEntityQuery((UriInfo) uriInfo, entry, oDataContext);

      String targetTableName = getSQLInsertBuilderTargetTable(dummyBuilder, sqlContext);
      newTableParam = generateTemporaryTableName(uriInfo.getTargetType().getName());

      connection = dataSource.getConnection();
      createTemporaryTableLikeTable(connection, newTableParam, targetTableName);
      insertIntoTemporaryTable(connection, insertBuilder, newTableParam, sqlContext);

      String schema = getODataArtifactTypeSchema(targetTableName);
      try(ResultSet procedureCallResultSet = callProcedure(connection, schema, handler.getHandler(), newTableParam)) {
        return createODataErrorResponse(procedureCallResultSet);
      }
    } catch (ODataException | SQLException e) {
      throw new KronosProcedureOData2EventHandlerException(UNABLE_TO_HANDLE_ON_CREATE_ENTITY_EVENT_ERROR, e);
    } finally {
      batchDropTemporaryTables(connection, newTableParam);
      closeConnection(connection);
    }
  }

  /**
	 * After create entity.
	 *
	 * @param uriInfo            the uri info
	 * @param requestContentType the request content type
	 * @param contentType        the content type
	 * @param entry              the entry
	 * @param context            the context
	 * @return the o data response
	 */
  @Override
  public ODataResponse afterCreateEntity(PostUriInfo uriInfo, String requestContentType, String contentType, ODataEntry entry,
      Map<Object, Object> context) {
    SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
    ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
    SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
    DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);
    ODataHandler handler = (ODataHandler) context.get(HANDLER_CONTEXT_KEY);

    Connection connection = null;
    String newTableParam = null;
    try {
      SQLInsertBuilder dummyBuilder = queryBuilder.buildInsertEntityQuery((UriInfo) uriInfo, entry, oDataContext);
      SQLSelectBuilder selectBuilder = queryBuilder.buildSelectEntityQuery((UriInfo) uriInfo, oDataContext);

      newTableParam = generateTemporaryTableName(uriInfo.getTargetType().getName());

      connection = dataSource.getConnection();
      createTemporaryTableAsSelect(connection, newTableParam, selectBuilder, sqlContext);

      String targetTableName = getSQLInsertBuilderTargetTable(dummyBuilder, sqlContext);
      String schema = getODataArtifactTypeSchema(targetTableName);
      try(ResultSet procedureCallResultSet = callProcedure(connection, schema, handler.getHandler(), newTableParam)) {
        return createODataErrorResponse(procedureCallResultSet);
      }
    } catch (ODataException | SQLException e) {
      throw new KronosProcedureOData2EventHandlerException(UNABLE_TO_HANDLE_AFTER_CREATE_ENTITY_EVENT_ERROR, e);
    } finally {
      batchDropTemporaryTables(connection, newTableParam);
      closeConnection(connection);
    }
  }

  /**
	 * On create entity.
	 *
	 * @param uriInfo            the uri info
	 * @param content            the content
	 * @param requestContentType the request content type
	 * @param contentType        the content type
	 * @param context            the context
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
    ODataHandler handler = (ODataHandler) context.get(HANDLER_CONTEXT_KEY);

    Connection connection = null;
    String newTableParam = null;
    try {
      SQLInsertBuilder dummyBuilder = queryBuilder.buildInsertEntityQuery((UriInfo) uriInfo, entry, oDataContext);
      SQLInsertBuilder insertBuilder = queryBuilder.buildInsertEntityQuery((UriInfo) uriInfo, entry, oDataContext);
      connection = dataSource.getConnection();

      String targetTableName = getSQLInsertBuilderTargetTable(dummyBuilder, sqlContext);
      newTableParam = generateTemporaryTableName(uriInfo.getTargetType().getName());

      createTemporaryTableLikeTable(connection, newTableParam, targetTableName);
      insertIntoTemporaryTable(connection, insertBuilder, newTableParam, sqlContext);

      Map<String, Object> entryMap = readEntryMap(connection, newTableParam);

      String schema = getODataArtifactTypeSchema(targetTableName);
      try (ResultSet procedureCallResultSet = callProcedure(connection, schema, handler.getHandler(), newTableParam)) {
        return createODataResponse(procedureCallResultSet, (UriInfo) uriInfo, oDataContext, entryMap, contentType,
            HttpStatusCodes.CREATED);
      }
    } catch (ODataException | SQLException e) {
      throw new KronosProcedureOData2EventHandlerException(UNABLE_TO_HANDLE_ON_CREATE_ENTITY_EVENT_ERROR, e);
    } finally {
      batchDropTemporaryTables(connection, newTableParam);
      closeConnection(connection);
    }
  }

  /**
	 * Before update entity.
	 *
	 * @param uriInfo            the uri info
	 * @param requestContentType the request content type
	 * @param merge              the merge
	 * @param contentType        the content type
	 * @param entry              the entry
	 * @param context            the context
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
    ODataHandler handler = (ODataHandler) context.get(HANDLER_CONTEXT_KEY);

    Connection connection = null;
    String oldTableParam = null;
    String newTableParam = null;
    try {
      SQLUpdateBuilder dummyBuilder = queryBuilder.buildUpdateEntityQuery((UriInfo) uriInfo, entry, mappedKeys, oDataContext);
      SQLSelectBuilder selectBuilder = queryBuilder.buildSelectEntityQuery((UriInfo) uriInfo, oDataContext);
      SQLUpdateBuilder updateBuilder = queryBuilder.buildUpdateEntityQuery((UriInfo) uriInfo, entry, mappedKeys, oDataContext);

      oldTableParam = generateTemporaryTableName(uriInfo.getTargetType().getName());
      newTableParam = generateTemporaryTableName(uriInfo.getTargetType().getName());
      String beforeUpdateOldTableParam = generateTemporaryTableName(uriInfo.getTargetType().getName());

      connection = dataSource.getConnection();
      createTemporaryTableAsSelect(connection, oldTableParam, selectBuilder, sqlContext);
      createTemporaryTableAsSelect(connection, newTableParam, selectBuilder, sqlContext);
      updateTemporaryTable(connection, updateBuilder, newTableParam, sqlContext);
      createTemporaryTableAsSelect(connection, beforeUpdateOldTableParam, selectBuilder, sqlContext);

      String beforeUpdateEntryJSON = GsonHelper.toJson(readEntryMap(connection, oldTableParam));
      context.put(ENTRY_JSON_CONTEXT_KEY, beforeUpdateEntryJSON);

      String targetTableName = getSQLUpdateBuilderTargetTable(dummyBuilder, sqlContext);
      String schema = getODataArtifactTypeSchema(targetTableName);
      try (ResultSet procedureCallResultSet = callProcedure(connection, schema, handler.getHandler(), oldTableParam, newTableParam)) {
        return createODataErrorResponse(procedureCallResultSet);
      }
    } catch (ODataException | SQLException e) {
      throw new KronosProcedureOData2EventHandlerException(UNABLE_TO_HANDLE_BEFORE_UPDATE_ENTITY_EVENT_ERROR, e);
    } finally {
      batchDropTemporaryTables(connection, oldTableParam, newTableParam);
      closeConnection(connection);
    }
  }

  /**
	 * After update entity.
	 *
	 * @param uriInfo            the uri info
	 * @param requestContentType the request content type
	 * @param merge              the merge
	 * @param contentType        the content type
	 * @param entry              the entry
	 * @param context            the context
	 * @return the o data response
	 */
  @Override
  public ODataResponse afterUpdateEntity(PutMergePatchUriInfo uriInfo, String requestContentType, boolean merge, String contentType,
      ODataEntry entry, Map<Object, Object> context) {
    SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
    ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
    SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
    DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);
    Map<String, Object> mappedKeys = (Map<String, Object>) context.get(MAPPED_KEYS_CONTEXT_KEY);
    ODataEntry entryBeforeUpdate = (ODataEntry) context.get(ENTRY_CONTEXT_KEY);
    ODataHandler handler = (ODataHandler) context.get(HANDLER_CONTEXT_KEY);

    Connection connection = null;
    String oldTableParam = null;
    String newTableParam = null;
    try {
      SQLUpdateBuilder dummyBuilder = queryBuilder.buildUpdateEntityQuery((UriInfo) uriInfo, entry, mappedKeys, oDataContext);
      SQLInsertBuilder insertBuilder = queryBuilder.buildInsertEntityQuery((UriInfo) uriInfo, entryBeforeUpdate, oDataContext);
      SQLSelectBuilder selectBuilder = queryBuilder.buildSelectEntityQuery((UriInfo) uriInfo, oDataContext);

      oldTableParam = generateTemporaryTableName(uriInfo.getTargetType().getName());
      newTableParam = generateTemporaryTableName(uriInfo.getTargetType().getName());

      connection = dataSource.getConnection();
      String targetTableName = getSQLUpdateBuilderTargetTable(dummyBuilder, sqlContext);
      createTemporaryTableLikeTable(connection, oldTableParam, targetTableName);
      insertIntoTemporaryTable(connection, insertBuilder, oldTableParam, sqlContext);
      createTemporaryTableAsSelect(connection, newTableParam, selectBuilder, sqlContext);

      String beforeUpdateEntryJSON = GsonHelper.toJson(readEntryMap(connection, oldTableParam));
      context.put(ENTRY_JSON_CONTEXT_KEY, beforeUpdateEntryJSON);

      String schema = getODataArtifactTypeSchema(targetTableName);
      try (ResultSet procedureCallResultSet = callProcedure(connection, schema, handler.getHandler(), oldTableParam, newTableParam)) {
        return createODataErrorResponse(procedureCallResultSet);
      }
    } catch (ODataException | SQLException e) {
      throw new KronosProcedureOData2EventHandlerException(UNABLE_TO_HANDLE_AFTER_UPDATE_ENTITY_EVENT_ERROR, e);
    } finally {
      batchDropTemporaryTables(connection, oldTableParam, newTableParam);
      closeConnection(connection);
    }
  }

  /**
	 * On update entity.
	 *
	 * @param uriInfo            the uri info
	 * @param content            the content
	 * @param requestContentType the request content type
	 * @param merge              the merge
	 * @param contentType        the content type
	 * @param context            the context
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
    ODataHandler handler = (ODataHandler) context.get(HANDLER_CONTEXT_KEY);

    Connection connection = null;
    String oldTableParam = null;
    String newTableParam = null;
    try {
      SQLUpdateBuilder dummyBuilder = queryBuilder.buildUpdateEntityQuery((UriInfo) uriInfo, entry, mappedKeys, oDataContext);
      SQLSelectBuilder selectBuilder = queryBuilder.buildSelectEntityQuery((UriInfo) uriInfo, oDataContext);
      SQLUpdateBuilder updateBuilder = queryBuilder.buildUpdateEntityQuery((UriInfo) uriInfo, entry, mappedKeys, oDataContext);

      oldTableParam = generateTemporaryTableName(uriInfo.getTargetType().getName());
      newTableParam = generateTemporaryTableName(uriInfo.getTargetType().getName());

      connection = dataSource.getConnection();
      createTemporaryTableAsSelect(connection, oldTableParam, selectBuilder, sqlContext);
      createTemporaryTableAsSelect(connection, newTableParam, selectBuilder, sqlContext);
      updateTemporaryTable(connection, updateBuilder, newTableParam, sqlContext);

      String targetTableName = getSQLUpdateBuilderTargetTable(dummyBuilder, sqlContext);
      String schema = getODataArtifactTypeSchema(targetTableName);
      try (ResultSet procedureCallResultSet = callProcedure(connection, schema, handler.getHandler(), oldTableParam, newTableParam)) {
        return createODataErrorResponse(procedureCallResultSet);
      }
    } catch (ODataException | SQLException e) {
      throw new KronosProcedureOData2EventHandlerException(UNABLE_TO_HANDLE_ON_UPDATE_ENTITY_EVENT_ERROR, e);
    } finally {
      batchDropTemporaryTables(connection, oldTableParam, newTableParam);
      closeConnection(connection);
    }
  }

  /**
	 * Before delete entity.
	 *
	 * @param uriInfo     the uri info
	 * @param contentType the content type
	 * @param context     the context
	 * @return the o data response
	 */
  @Override
  public ODataResponse beforeDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) {
    SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
    ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
    SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
    DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);
    Map<String, Object> mappedKeys = (Map<String, Object>) context.get(MAPPED_KEYS_CONTEXT_KEY);
    ODataHandler handler = (ODataHandler) context.get(HANDLER_CONTEXT_KEY);

    Connection connection = null;
    String oldTableParam = null;
    try {
      SQLDeleteBuilder dummyBuilder = queryBuilder.buildDeleteEntityQuery((UriInfo) uriInfo, mappedKeys, oDataContext);
      SQLSelectBuilder selectBuilder = queryBuilder.buildSelectEntityQuery((UriInfo) uriInfo, oDataContext);

      oldTableParam = generateTemporaryTableName(uriInfo.getTargetType().getName());

      connection = dataSource.getConnection();
      createTemporaryTableAsSelect(connection, oldTableParam, selectBuilder, sqlContext);

      String beforeDeleteEntryJSON = GsonHelper.toJson(readEntryMap(connection, oldTableParam));
      context.put(ENTRY_JSON_CONTEXT_KEY, beforeDeleteEntryJSON);

      String targetTableName = getSQLDeleteBuilderTargetTable(dummyBuilder, sqlContext);
      String schema = getODataArtifactTypeSchema(targetTableName);
      try (ResultSet procedureCallResultSet = callProcedure(connection, schema, handler.getHandler(), oldTableParam)) {
        return createODataErrorResponse(procedureCallResultSet);
      }
    } catch (ODataException | SQLException e) {
      throw new KronosProcedureOData2EventHandlerException(UNABLE_TO_HANDLE_BEFORE_DELETE_ENTITY_EVENT_ERROR, e);
    } finally {
      batchDropTemporaryTables(connection, oldTableParam);
      closeConnection(connection);
    }
  }

  /**
	 * After delete entity.
	 *
	 * @param uriInfo     the uri info
	 * @param contentType the content type
	 * @param context     the context
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
    ODataHandler handler = (ODataHandler) context.get(HANDLER_CONTEXT_KEY);

    Connection connection = null;
    String oldTableParam = null;
    try {
      SQLDeleteBuilder dummyBuilder = queryBuilder.buildDeleteEntityQuery((UriInfo) uriInfo, mappedKeys, oDataContext);
      SQLInsertBuilder insertBuilder = queryBuilder.buildInsertEntityQuery((UriInfo) uriInfo, entryBeforeDelete, oDataContext);

      oldTableParam = generateTemporaryTableName(uriInfo.getTargetType().getName());
      String targetTableName = getSQLDeleteBuilderTargetTable(dummyBuilder, sqlContext);

      connection = dataSource.getConnection();
      createTemporaryTableLikeTable(connection, oldTableParam, targetTableName);
      insertIntoTemporaryTable(connection, insertBuilder, oldTableParam, sqlContext);

      String schema = getODataArtifactTypeSchema(targetTableName);
      try (ResultSet procedureCallResultSet = callProcedure(connection, schema, handler.getHandler(), oldTableParam)) {
        return createODataErrorResponse(procedureCallResultSet);
      }
    } catch (SQLException | ODataException e) {
      throw new KronosProcedureOData2EventHandlerException(UNABLE_TO_HANDLE_AFTER_DELETE_ENTITY_EVENT_ERROR, e);
    } finally {
      batchDropTemporaryTables(connection, oldTableParam);
      closeConnection(connection);
    }
  }

  /**
	 * On delete entity.
	 *
	 * @param uriInfo     the uri info
	 * @param contentType the content type
	 * @param context     the context
	 * @return the o data response
	 */
  @Override
  public ODataResponse onDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) {
    SQLQueryBuilder queryBuilder = (SQLQueryBuilder) context.get(SQL_BUILDER_CONTEXT_KEY);
    ODataContext oDataContext = (ODataContext) context.get(ODATA_CONTEXT_CONTEXT_KEY);
    SQLContext sqlContext = (SQLContext) context.get(SQL_CONTEXT_CONTEXT_KEY);
    DataSource dataSource = (DataSource) context.get(DATASOURCE_CONTEXT_KEY);
    Map<String, Object> mappedKeys = (Map<String, Object>) context.get(MAPPED_KEYS_CONTEXT_KEY);
    ODataHandler handler = (ODataHandler) context.get(HANDLER_CONTEXT_KEY);

    Connection connection = null;
    String oldTableParam = null;
    try {
      SQLDeleteBuilder dummyBuilder = queryBuilder.buildDeleteEntityQuery((UriInfo) uriInfo, mappedKeys, oDataContext);
      SQLSelectBuilder selectBuilder = queryBuilder.buildSelectEntityQuery((UriInfo) uriInfo, oDataContext);

      oldTableParam = generateTemporaryTableName(uriInfo.getTargetType().getName());

      connection = dataSource.getConnection();
      createTemporaryTableAsSelect(connection, oldTableParam, selectBuilder, sqlContext);

      String targetTableName = getSQLDeleteBuilderTargetTable(dummyBuilder, sqlContext);
      String schema = getODataArtifactTypeSchema(targetTableName);
      try(ResultSet procedureCallResultSet = callProcedure(connection, schema, handler.getHandler(), oldTableParam)) {
        return createODataErrorResponse(procedureCallResultSet);
      }
    } catch (ODataException | SQLException e) {
      throw new KronosProcedureOData2EventHandlerException(UNABLE_TO_HANDLE_ON_DELETE_ENTITY_EVENT_ERROR, e);
    } finally {
      batchDropTemporaryTables(connection, oldTableParam);
      closeConnection(connection);
    }
  }

  /**
	 * Call procedure.
	 *
	 * @param connection    the connection
	 * @param schema        the schema
	 * @param procedureName the procedure name
	 * @param newTableParam the new table param
	 * @return the result set
	 * @throws SQLException the SQL exception
	 */
  protected ResultSet callProcedure(Connection connection, String schema, String procedureName, String newTableParam) throws SQLException {
    String callProcedureSQL = "CALL \"" + schema + "\".\"" + procedureName + "\" (\"" + newTableParam + "\", ?)";
    try(PreparedStatement statement = createPreparedStatement(connection, callProcedureSQL)) {
      return statement.executeQuery();
    }
  }

  /**
	 * Call procedure.
	 *
	 * @param connection    the connection
	 * @param schema        the schema
	 * @param procedureName the procedure name
	 * @param oldTableParam the old table param
	 * @param newTableParam the new table param
	 * @return the result set
	 * @throws SQLException the SQL exception
	 */
  protected ResultSet callProcedure(Connection connection, String schema, String procedureName, String oldTableParam, String newTableParam)
      throws SQLException {
    String callProcedureSQL = "CALL \"" + schema + "\".\"" + procedureName + "\" (\"" + newTableParam + "\", \"" + oldTableParam + "\", ?)";
    try(PreparedStatement statement = createPreparedStatement(connection, callProcedureSQL)) {
      return statement.executeQuery();
    }
  }

  /**
	 * Gets the o data artifact type schema.
	 *
	 * @param tableName the table name
	 * @return the o data artifact type schema
	 * @throws SQLException the SQL exception
	 */
  protected String getODataArtifactTypeSchema(String tableName) throws SQLException {
    String normalizedTableName = ODataDatabaseMetadataUtil.normalizeTableName(tableName);
    TableMetadataProvider tableMetadataProvider = new TableMetadataProvider();
    Table persistenceTableModel = tableMetadataProvider.getPersistenceTableModel(normalizedTableName);
    return persistenceTableModel.getSchema();
  }
}
