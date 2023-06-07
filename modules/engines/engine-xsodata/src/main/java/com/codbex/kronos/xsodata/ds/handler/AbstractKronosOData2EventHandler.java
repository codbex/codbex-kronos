/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.codbex.kronos.xsodata.ds.service.TableMetadataProvider;
import org.apache.olingo.odata2.api.commons.HttpHeaders;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.ep.EntityProvider;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.core.commons.ContentType;
import org.eclipse.dirigible.commons.api.helpers.GsonHelper;
import org.eclipse.dirigible.database.persistence.model.PersistenceTableModel;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.engine.odata2.handler.ScriptingOData2EventHandler;
import org.eclipse.dirigible.engine.odata2.sql.api.SQLStatement;
import org.eclipse.dirigible.engine.odata2.sql.api.SQLStatementParam;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLContext;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLDeleteBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLInsertBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLSelectBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLUpdateBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLUtils;
import org.eclipse.dirigible.engine.odata2.sql.processor.ExpandCallBack;
import org.eclipse.dirigible.engine.odata2.transformers.DBMetadataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractKronosOData2EventHandler extends ScriptingOData2EventHandler {

  protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractKronosOData2EventHandler.class);
  protected static final String UNABLE_TO_HANDLE_BEFORE_CREATE_ENTITY_EVENT_ERROR = "Unable to handle beforeCreateEntity event";
  protected static final String UNABLE_TO_HANDLE_AFTER_CREATE_ENTITY_EVENT_ERROR = "Unable to handle afterCreateEntity event";
  protected static final String UNABLE_TO_HANDLE_ON_CREATE_ENTITY_EVENT_ERROR = "Unable to handle onCreateEntity event";
  protected static final String UNABLE_TO_HANDLE_BEFORE_UPDATE_ENTITY_EVENT_ERROR = "Unable to handle beforeUpdateEntity event";
  protected static final String UNABLE_TO_HANDLE_AFTER_UPDATE_ENTITY_EVENT_ERROR = "Unable to handle afterUpdateEntity event";
  protected static final String UNABLE_TO_HANDLE_ON_UPDATE_ENTITY_EVENT_ERROR = "Unable to handle onUpdateEntity event";
  protected static final String UNABLE_TO_HANDLE_BEFORE_DELETE_ENTITY_EVENT_ERROR = "Unable to handle beforeDeleteEntity event";
  protected static final String UNABLE_TO_HANDLE_AFTER_DELETE_ENTITY_EVENT_ERROR = "Unable to handle afterDeleteEntity event";
  protected static final String UNABLE_TO_HANDLE_ON_DELETE_ENTITY_EVENT_ERROR = "Unable to handle onDeleteEntity event";
  protected static final String UNABLE_TO_DROP_TEMPORARY_TABLES_ERROR = "Unable to drop temporary tables";
  protected static final String UNABLE_TO_CLOSE_CONNECTION_ERROR = "Unable to close connection";

  protected static final String SQL_BUILDER_CONTEXT_KEY = "sqlBuilder";
  protected static final String SQL_CONTEXT_CONTEXT_KEY = "sqlContext";
  protected static final String DATASOURCE_CONTEXT_KEY = "datasource";
  protected static final String ODATA_CONTEXT_CONTEXT_KEY = "oDataContext";
  protected static final String MAPPED_KEYS_CONTEXT_KEY = "mappedKeys";

  protected static final String CONNECTION_CONTEXT_KEY = "connection";
  protected static final String BEFORE_TABLE_NAME_CONTEXT_KEY = "beforeTableName";
  protected static final String AFTER_TABLE_NAME_CONTEXT_KEY = "afterTableName";
  protected static final String ENTRY_CONTEXT_KEY = "entry";
  protected static final String ENTRY_JSON_CONTEXT_KEY = "entryJSON";
  protected static final String HANDLER_CONTEXT_KEY = "handler";

  private static final String HTTP_STATUS_CODE_PROCEDURE_RESULT_SET = "HTTP_STATUS_CODE";
  private static final String ERROR_MESSAGE_PROCEDURE_RESULT_SET = "ERROR_MESSAGE";
  private static final String ERROR_RESPONSE_ERROR_DETAIL = "errordetail";

  protected String getSQLInsertBuilderTargetTable(SQLInsertBuilder insertBuilder, SQLContext sqlContext) throws ODataException {
    SQLStatement sqlStatement = insertBuilder.build(sqlContext);
    sqlStatement.sql();
    return insertBuilder.getTargetTableName();
  }

  protected String getSQLUpdateBuilderTargetTable(SQLUpdateBuilder updateBuilder, SQLContext sqlContext) throws ODataException {
    SQLStatement sqlStatement = updateBuilder.build(sqlContext);
    sqlStatement.sql();
    return updateBuilder.getTargetTableName();
  }

  protected String getSQLDeleteBuilderTargetTable(SQLDeleteBuilder deleteBuilder, SQLContext sqlContext) throws ODataException {
    SQLStatement sqlStatement = deleteBuilder.build(sqlContext);
    sqlStatement.sql();
    return deleteBuilder.getTargetTableName();
  }

  protected void createTemporaryTableLikeTable(Connection connection, String temporaryTableName, String likeTableName)
      throws SQLException {
    String normalizedTableName = DBMetadataUtil.normalizeTableName(likeTableName);
    TableMetadataProvider tableMetadataProvider = new TableMetadataProvider();
    PersistenceTableModel persistenceTableModel = tableMetadataProvider.getPersistenceTableModel(normalizedTableName);
    String odataArtifactTypeSchema = persistenceTableModel.getSchemaName();
    String artifactType = persistenceTableModel.getTableType();
    String sql = buildCreateTemporaryTableLikeTableSql(connection, artifactType, odataArtifactTypeSchema, temporaryTableName,
        likeTableName);

    try (PreparedStatement preparedStatement = createPreparedStatement(connection, sql)) {
      preparedStatement.execute();
    }
  }

  protected void createTemporaryTableAsSelect(Connection connection, String temporaryTableName, SQLSelectBuilder selectBuilder,
      SQLContext sqlContext) throws SQLException, ODataException {
    String sql = buildCreateTemporaryTableAsSelect(connection, temporaryTableName, selectBuilder.buildSelect(sqlContext),
        selectBuilder.getStatementParams());
    try (PreparedStatement preparedStatement = createPreparedStatement(connection, sql)) {
      preparedStatement.execute();
    }
  }

  protected String buildCreateTemporaryTableLikeTableSql(Connection connection, String artifactType, String odataArtifactTypeSchema,
      String temporaryTableName, String likeTableName) {
    String sql;
    if (artifactType.equals(ISqlKeywords.METADATA_TABLE)) {
      sql = SqlFactory.getNative(connection).create().temporaryTable(temporaryTableName)
          .setLikeTable(odataArtifactTypeSchema + "." + likeTableName).build();
    } else {
      String normalizedTableName = DBMetadataUtil.normalizeTableName(likeTableName);
      String selectWildcardFromViewSQL = SqlFactory.getNative(connection).select().column("*")
          .from(odataArtifactTypeSchema + "." + normalizedTableName).build();
      sql = SqlFactory.getNative(connection).create().temporaryTable(temporaryTableName)
          .setAsSelectQuery(selectWildcardFromViewSQL).setSelectWithNoData(true).build();
    }

    return sql;
  }

  protected String buildCreateTemporaryTableAsSelect(Connection connection, String temporaryTableName, String asSelectQuery,
      List<SQLStatementParam> parameters) {
    String sql = SqlFactory.getNative(connection).create().temporaryTable(temporaryTableName)
        .setAsSelectQuery(asSelectQuery).build();
    String sqlWithoutAliases = sql.replaceAll("_T[0-9]+\"", "\"");
    return replaceSqlParameters(sqlWithoutAliases, parameters);
  }

  protected String replaceSqlParameters(String sql, List<SQLStatementParam> parameters) {
    for (SQLStatementParam p : parameters) {
      sql = sql.replaceFirst("\\?", "'" + p.getValue() + "'");
    }

    return sql;
  }

  protected void insertIntoTemporaryTable(Connection connection, SQLInsertBuilder insertBuilder, String temporaryTableName,
      SQLContext sqlContext)
      throws ODataException, SQLException {
    insertBuilder.setTableName("\"" + temporaryTableName + "\"");
    executeSQLStatement(connection, insertBuilder.build(sqlContext));
  }

  protected void updateTemporaryTable(Connection connection, SQLUpdateBuilder updateBuilder, String temporaryTableName,
      SQLContext sqlContext)
      throws ODataException, SQLException {
    updateBuilder.setTableName("\"" + temporaryTableName + "\"");
    executeSQLStatement(connection, updateBuilder.build(sqlContext));
  }

  protected void batchDropTemporaryTables(Connection connection, String... temporaryTableNames) {
    if (connection != null) {
      try (Statement statement = connection.createStatement()) {
        for (String temporaryTableName : temporaryTableNames) {
          if (temporaryTableName != null && SqlFactory.getNative(connection).exists(connection, temporaryTableName)) {
            String sql = SqlFactory.getNative(connection).drop().table(temporaryTableName).build();
            statement.addBatch(sql);
          }
        }
        statement.executeBatch();
      } catch (SQLException e) {
        LOGGER.error(UNABLE_TO_DROP_TEMPORARY_TABLES_ERROR, e);
      }
    } else {
      LOGGER.error("Unable to drop temporary tables - connection is null");
    }
  }

  protected void executeSQLStatement(Connection connection, SQLStatement statement) throws SQLException, ODataException {
    try (PreparedStatement preparedStatement = createPreparedStatement(connection, statement.sql())) {
      SQLUtils.setParamsOnStatement(preparedStatement, statement.getStatementParams());
      preparedStatement.executeUpdate();
    }
  }

  protected PreparedStatement createPreparedStatement(Connection connection, String sql) throws SQLException {
    LOGGER.debug("Preparing temporary table statement: {}", sql);
    return connection.prepareStatement(sql);
  }

  protected Map<String, Object> readEntryMap(Connection connection, String tableName) throws SQLException {
    String selectCreatedEntitySQL = SqlFactory.getNative(connection).select().column("*").from(tableName).build();
    try (PreparedStatement statement = connection.prepareStatement(selectCreatedEntitySQL);
        ResultSet resultSet = statement.executeQuery()) {
      Map<String, Object> currentTargetEntity = new HashMap<>();
      while (resultSet.next()) {
        currentTargetEntity = convertResultSetMap(resultSet);
      }
      return currentTargetEntity;
    }
  }

  protected Map<String, Object> convertResultSetMap(ResultSet resultSet) throws SQLException {
    ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
    int columnCount = resultSetMetadata.getColumnCount();
    HashMap<String, Object> entry = new HashMap<>(columnCount);
    for (int i = 1; i <= columnCount; i++) {
      entry.put(resultSetMetadata.getColumnName(i), resultSet.getObject(i));
    }

    return entry;
  }

  protected void closeConnection(Connection connection) {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
      }
    } catch (SQLException e) {
      LOGGER.error(UNABLE_TO_CLOSE_CONNECTION_ERROR, e);
    }
  }

  protected String generateTemporaryTableName(String targetTypeName) {
    return "#" + targetTypeName + UUID.randomUUID().toString().replace("-", "");
  }

  protected ODataResponse createODataErrorResponse(ResultSet resultSet) throws SQLException {
    if (resultSet.next()) {
      String errorCode = resultSet.getString(HTTP_STATUS_CODE_PROCEDURE_RESULT_SET);
      String message = resultSet.getString(ERROR_MESSAGE_PROCEDURE_RESULT_SET);
      ResultSetMetaData metaData = resultSet.getMetaData();
      Map<String, Map<String, String>> innerError = new HashMap<>();
      innerError.put(ERROR_RESPONSE_ERROR_DETAIL, new HashMap<>());
      for (int i = 1; i <= metaData.getColumnCount(); i++) {
        String columnName = metaData.getColumnName(i);
        if (!columnName.equals(HTTP_STATUS_CODE_PROCEDURE_RESULT_SET) && !columnName.equals(ERROR_MESSAGE_PROCEDURE_RESULT_SET)) {
          innerError.get(ERROR_RESPONSE_ERROR_DETAIL).put(columnName, resultSet.getString(i));
        }
      }

      ODataErrorContext errorContext = new ODataErrorContext();
      String applicationJSONContentTypeString = ContentType.APPLICATION_JSON.toContentTypeString();
      errorContext.setContentType(applicationJSONContentTypeString);
      errorContext.setErrorCode(errorCode);
      errorContext.setHttpStatus(HttpStatusCodes.fromStatusCode(Integer.parseInt(errorCode)));
      errorContext.setMessage(message);
      errorContext.setInnerError(GsonHelper.toJson(innerError));
      ODataResponse response = EntityProvider.writeErrorDocument(errorContext);
      if (!response.containsHeader(HttpHeaders.CONTENT_TYPE)) {
        response = ODataResponse.fromResponse(response).contentHeader(applicationJSONContentTypeString).build();
      }

      return response;
    }
    return null;
  }

  protected ODataResponse createODataResponse(ResultSet resultSet, UriInfo uriInfo, ODataContext oDataContext,
      Map<String, Object> entryMap, String contentType, HttpStatusCodes httpStatusCode) throws SQLException, ODataException {
    ODataResponse response = createODataErrorResponse(resultSet);
    if (response != null) {
      return response;
    } else {
      return buildResponse(uriInfo, oDataContext, entryMap, contentType, httpStatusCode);
    }
  }

  protected ODataResponse buildResponse(UriInfo uriInfo, ODataContext oDataContext, Map<String, Object> entryMap, String contentType,
      HttpStatusCodes httpStatusCode)
      throws ODataException {
    ODataResponse response = ExpandCallBack.writeEntryWithExpand(oDataContext,
        uriInfo,
        entryMap,
        contentType);
    return ODataResponse.fromResponse(response).status(httpStatusCode).contentHeader(contentType)
        .build();
  }
}
