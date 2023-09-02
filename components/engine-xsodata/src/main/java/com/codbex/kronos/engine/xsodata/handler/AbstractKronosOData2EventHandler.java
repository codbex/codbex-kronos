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
package com.codbex.kronos.engine.xsodata.handler;

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
import org.eclipse.dirigible.components.data.structures.domain.Table;
import org.eclipse.dirigible.components.odata.handler.ScriptingOData2EventHandler;
import org.eclipse.dirigible.components.odata.transformers.ODataDatabaseMetadataUtil;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.engine.odata2.sql.api.SQLStatement;
import org.eclipse.dirigible.engine.odata2.sql.api.SQLStatementParam;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLContext;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLDeleteBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLInsertBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLSelectBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLUpdateBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLUtils;
import org.eclipse.dirigible.engine.odata2.sql.processor.ExpandCallBack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.engine.xsodata.transformers.TableMetadataProvider;

/**
 * The Class AbstractKronosOData2EventHandler.
 */
public abstract class AbstractKronosOData2EventHandler extends ScriptingOData2EventHandler {

  /** The Constant LOGGER. */
  protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractKronosOData2EventHandler.class);
  
  /** The Constant UNABLE_TO_HANDLE_BEFORE_CREATE_ENTITY_EVENT_ERROR. */
  protected static final String UNABLE_TO_HANDLE_BEFORE_CREATE_ENTITY_EVENT_ERROR = "Unable to handle beforeCreateEntity event";
  
  /** The Constant UNABLE_TO_HANDLE_AFTER_CREATE_ENTITY_EVENT_ERROR. */
  protected static final String UNABLE_TO_HANDLE_AFTER_CREATE_ENTITY_EVENT_ERROR = "Unable to handle afterCreateEntity event";
  
  /** The Constant UNABLE_TO_HANDLE_ON_CREATE_ENTITY_EVENT_ERROR. */
  protected static final String UNABLE_TO_HANDLE_ON_CREATE_ENTITY_EVENT_ERROR = "Unable to handle onCreateEntity event";
  
  /** The Constant UNABLE_TO_HANDLE_BEFORE_UPDATE_ENTITY_EVENT_ERROR. */
  protected static final String UNABLE_TO_HANDLE_BEFORE_UPDATE_ENTITY_EVENT_ERROR = "Unable to handle beforeUpdateEntity event";
  
  /** The Constant UNABLE_TO_HANDLE_AFTER_UPDATE_ENTITY_EVENT_ERROR. */
  protected static final String UNABLE_TO_HANDLE_AFTER_UPDATE_ENTITY_EVENT_ERROR = "Unable to handle afterUpdateEntity event";
  
  /** The Constant UNABLE_TO_HANDLE_ON_UPDATE_ENTITY_EVENT_ERROR. */
  protected static final String UNABLE_TO_HANDLE_ON_UPDATE_ENTITY_EVENT_ERROR = "Unable to handle onUpdateEntity event";
  
  /** The Constant UNABLE_TO_HANDLE_BEFORE_DELETE_ENTITY_EVENT_ERROR. */
  protected static final String UNABLE_TO_HANDLE_BEFORE_DELETE_ENTITY_EVENT_ERROR = "Unable to handle beforeDeleteEntity event";
  
  /** The Constant UNABLE_TO_HANDLE_AFTER_DELETE_ENTITY_EVENT_ERROR. */
  protected static final String UNABLE_TO_HANDLE_AFTER_DELETE_ENTITY_EVENT_ERROR = "Unable to handle afterDeleteEntity event";
  
  /** The Constant UNABLE_TO_HANDLE_ON_DELETE_ENTITY_EVENT_ERROR. */
  protected static final String UNABLE_TO_HANDLE_ON_DELETE_ENTITY_EVENT_ERROR = "Unable to handle onDeleteEntity event";
  
  /** The Constant UNABLE_TO_DROP_TEMPORARY_TABLES_ERROR. */
  protected static final String UNABLE_TO_DROP_TEMPORARY_TABLES_ERROR = "Unable to drop temporary tables";
  
  /** The Constant UNABLE_TO_CLOSE_CONNECTION_ERROR. */
  protected static final String UNABLE_TO_CLOSE_CONNECTION_ERROR = "Unable to close connection";

  /** The Constant SQL_BUILDER_CONTEXT_KEY. */
  protected static final String SQL_BUILDER_CONTEXT_KEY = "sqlBuilder";
  
  /** The Constant SQL_CONTEXT_CONTEXT_KEY. */
  protected static final String SQL_CONTEXT_CONTEXT_KEY = "sqlContext";
  
  /** The Constant DATASOURCE_CONTEXT_KEY. */
  protected static final String DATASOURCE_CONTEXT_KEY = "datasource";
  
  /** The Constant ODATA_CONTEXT_CONTEXT_KEY. */
  protected static final String ODATA_CONTEXT_CONTEXT_KEY = "oDataContext";
  
  /** The Constant MAPPED_KEYS_CONTEXT_KEY. */
  protected static final String MAPPED_KEYS_CONTEXT_KEY = "mappedKeys";

  /** The Constant CONNECTION_CONTEXT_KEY. */
  protected static final String CONNECTION_CONTEXT_KEY = "connection";
  
  /** The Constant BEFORE_TABLE_NAME_CONTEXT_KEY. */
  protected static final String BEFORE_TABLE_NAME_CONTEXT_KEY = "beforeTableName";
  
  /** The Constant AFTER_TABLE_NAME_CONTEXT_KEY. */
  protected static final String AFTER_TABLE_NAME_CONTEXT_KEY = "afterTableName";
  
  /** The Constant ENTRY_CONTEXT_KEY. */
  protected static final String ENTRY_CONTEXT_KEY = "entry";
  
  /** The Constant ENTRY_JSON_CONTEXT_KEY. */
  protected static final String ENTRY_JSON_CONTEXT_KEY = "entryJSON";
  
  /** The Constant HANDLER_CONTEXT_KEY. */
  protected static final String HANDLER_CONTEXT_KEY = "handler";

  /** The Constant HTTP_STATUS_CODE_PROCEDURE_RESULT_SET. */
  private static final String HTTP_STATUS_CODE_PROCEDURE_RESULT_SET = "HTTP_STATUS_CODE";
  
  /** The Constant ERROR_MESSAGE_PROCEDURE_RESULT_SET. */
  private static final String ERROR_MESSAGE_PROCEDURE_RESULT_SET = "ERROR_MESSAGE";
  
  /** The Constant ERROR_RESPONSE_ERROR_DETAIL. */
  private static final String ERROR_RESPONSE_ERROR_DETAIL = "errordetail";

  /**
	 * Gets the SQL insert builder target table.
	 *
	 * @param insertBuilder the insert builder
	 * @param sqlContext    the sql context
	 * @return the SQL insert builder target table
	 * @throws ODataException the o data exception
	 */
  protected String getSQLInsertBuilderTargetTable(SQLInsertBuilder insertBuilder, SQLContext sqlContext) throws ODataException {
    SQLStatement sqlStatement = insertBuilder.build(sqlContext);
    sqlStatement.sql();
    return insertBuilder.getTargetTableName();
  }

  /**
	 * Gets the SQL update builder target table.
	 *
	 * @param updateBuilder the update builder
	 * @param sqlContext    the sql context
	 * @return the SQL update builder target table
	 * @throws ODataException the o data exception
	 */
  protected String getSQLUpdateBuilderTargetTable(SQLUpdateBuilder updateBuilder, SQLContext sqlContext) throws ODataException {
    SQLStatement sqlStatement = updateBuilder.build(sqlContext);
    sqlStatement.sql();
    return updateBuilder.getTargetTableName();
  }

  /**
	 * Gets the SQL delete builder target table.
	 *
	 * @param deleteBuilder the delete builder
	 * @param sqlContext    the sql context
	 * @return the SQL delete builder target table
	 * @throws ODataException the o data exception
	 */
  protected String getSQLDeleteBuilderTargetTable(SQLDeleteBuilder deleteBuilder, SQLContext sqlContext) throws ODataException {
    SQLStatement sqlStatement = deleteBuilder.build(sqlContext);
    sqlStatement.sql();
    return deleteBuilder.getTargetTableName();
  }

  /**
	 * Creates the temporary table like table.
	 *
	 * @param connection         the connection
	 * @param temporaryTableName the temporary table name
	 * @param likeTableName      the like table name
	 * @throws SQLException the SQL exception
	 */
  protected void createTemporaryTableLikeTable(Connection connection, String temporaryTableName, String likeTableName)
      throws SQLException {
    String normalizedTableName = ODataDatabaseMetadataUtil.normalizeTableName(likeTableName);
    TableMetadataProvider tableMetadataProvider = new TableMetadataProvider();
    Table persistenceTableModel = tableMetadataProvider.getPersistenceTableModel(normalizedTableName);
    String odataArtifactTypeSchema = persistenceTableModel.getSchema();
    String artifactType = persistenceTableModel.getKind();
    String sql = buildCreateTemporaryTableLikeTableSql(connection, artifactType, odataArtifactTypeSchema, temporaryTableName,
        likeTableName);

    try (PreparedStatement preparedStatement = createPreparedStatement(connection, sql)) {
      preparedStatement.execute();
    }
  }

  /**
	 * Creates the temporary table as select.
	 *
	 * @param connection         the connection
	 * @param temporaryTableName the temporary table name
	 * @param selectBuilder      the select builder
	 * @param sqlContext         the sql context
	 * @throws SQLException   the SQL exception
	 * @throws ODataException the o data exception
	 */
  protected void createTemporaryTableAsSelect(Connection connection, String temporaryTableName, SQLSelectBuilder selectBuilder,
      SQLContext sqlContext) throws SQLException, ODataException {
    String sql = buildCreateTemporaryTableAsSelect(connection, temporaryTableName, selectBuilder.buildSelect(sqlContext),
        selectBuilder.getStatementParams());
    try (PreparedStatement preparedStatement = createPreparedStatement(connection, sql)) {
      preparedStatement.execute();
    }
  }

  /**
	 * Builds the create temporary table like table sql.
	 *
	 * @param connection              the connection
	 * @param artifactType            the artifact type
	 * @param odataArtifactTypeSchema the odata artifact type schema
	 * @param temporaryTableName      the temporary table name
	 * @param likeTableName           the like table name
	 * @return the string
	 */
  protected String buildCreateTemporaryTableLikeTableSql(Connection connection, String artifactType, String odataArtifactTypeSchema,
      String temporaryTableName, String likeTableName) {
    String sql;
    if (artifactType.equals(ISqlKeywords.METADATA_TABLE)) {
      sql = SqlFactory.getNative(connection).create().temporaryTable(temporaryTableName)
          .setLikeTable(odataArtifactTypeSchema + "." + likeTableName).build();
    } else {
      String normalizedTableName = ODataDatabaseMetadataUtil.normalizeTableName(likeTableName);
      String selectWildcardFromViewSQL = SqlFactory.getNative(connection).select().column("*")
          .from(odataArtifactTypeSchema + "." + normalizedTableName).build();
      sql = SqlFactory.getNative(connection).create().temporaryTable(temporaryTableName)
          .setAsSelectQuery(selectWildcardFromViewSQL).setSelectWithNoData(true).build();
    }

    return sql;
  }

  /**
	 * Builds the create temporary table as select.
	 *
	 * @param connection         the connection
	 * @param temporaryTableName the temporary table name
	 * @param asSelectQuery      the as select query
	 * @param parameters         the parameters
	 * @return the string
	 */
  protected String buildCreateTemporaryTableAsSelect(Connection connection, String temporaryTableName, String asSelectQuery,
      List<SQLStatementParam> parameters) {
    String sql = SqlFactory.getNative(connection).create().temporaryTable(temporaryTableName)
        .setAsSelectQuery(asSelectQuery).build();
    String sqlWithoutAliases = sql.replaceAll("_T[0-9]+\"", "\"");
    return replaceSqlParameters(sqlWithoutAliases, parameters);
  }

  /**
	 * Replace sql parameters.
	 *
	 * @param sql        the sql
	 * @param parameters the parameters
	 * @return the string
	 */
  protected String replaceSqlParameters(String sql, List<SQLStatementParam> parameters) {
    for (SQLStatementParam p : parameters) {
      sql = sql.replaceFirst("\\?", "'" + p.getValue() + "'");
    }

    return sql;
  }

  /**
	 * Insert into temporary table.
	 *
	 * @param connection         the connection
	 * @param insertBuilder      the insert builder
	 * @param temporaryTableName the temporary table name
	 * @param sqlContext         the sql context
	 * @throws ODataException the o data exception
	 * @throws SQLException   the SQL exception
	 */
  protected void insertIntoTemporaryTable(Connection connection, SQLInsertBuilder insertBuilder, String temporaryTableName,
      SQLContext sqlContext)
      throws ODataException, SQLException {
    insertBuilder.setTableName("\"" + temporaryTableName + "\"");
    executeSQLStatement(connection, insertBuilder.build(sqlContext));
  }

  /**
	 * Update temporary table.
	 *
	 * @param connection         the connection
	 * @param updateBuilder      the update builder
	 * @param temporaryTableName the temporary table name
	 * @param sqlContext         the sql context
	 * @throws ODataException the o data exception
	 * @throws SQLException   the SQL exception
	 */
  protected void updateTemporaryTable(Connection connection, SQLUpdateBuilder updateBuilder, String temporaryTableName,
      SQLContext sqlContext)
      throws ODataException, SQLException {
    updateBuilder.setTableName("\"" + temporaryTableName + "\"");
    executeSQLStatement(connection, updateBuilder.build(sqlContext));
  }

  /**
	 * Batch drop temporary tables.
	 *
	 * @param connection          the connection
	 * @param temporaryTableNames the temporary table names
	 */
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

  /**
	 * Execute SQL statement.
	 *
	 * @param connection the connection
	 * @param statement  the statement
	 * @throws SQLException   the SQL exception
	 * @throws ODataException the o data exception
	 */
  protected void executeSQLStatement(Connection connection, SQLStatement statement) throws SQLException, ODataException {
    try (PreparedStatement preparedStatement = createPreparedStatement(connection, statement.sql())) {
      SQLUtils.setParamsOnStatement(preparedStatement, statement.getStatementParams());
      preparedStatement.executeUpdate();
    }
  }

  /**
	 * Creates the prepared statement.
	 *
	 * @param connection the connection
	 * @param sql        the sql
	 * @return the prepared statement
	 * @throws SQLException the SQL exception
	 */
  protected PreparedStatement createPreparedStatement(Connection connection, String sql) throws SQLException {
    LOGGER.debug("Preparing temporary table statement: {}", sql);
    return connection.prepareStatement(sql);
  }

  /**
	 * Read entry map.
	 *
	 * @param connection the connection
	 * @param tableName  the table name
	 * @return the map
	 * @throws SQLException the SQL exception
	 */
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

  /**
	 * Convert result set map.
	 *
	 * @param resultSet the result set
	 * @return the map
	 * @throws SQLException the SQL exception
	 */
  protected Map<String, Object> convertResultSetMap(ResultSet resultSet) throws SQLException {
    ResultSetMetaData resultSetMetadata = resultSet.getMetaData();
    int columnCount = resultSetMetadata.getColumnCount();
    HashMap<String, Object> entry = new HashMap<>(columnCount);
    for (int i = 1; i <= columnCount; i++) {
      entry.put(resultSetMetadata.getColumnName(i), resultSet.getObject(i));
    }

    return entry;
  }

  /**
	 * Close connection.
	 *
	 * @param connection the connection
	 */
  protected void closeConnection(Connection connection) {
    try {
      if (connection != null && !connection.isClosed()) {
        connection.close();
      }
    } catch (SQLException e) {
      LOGGER.error(UNABLE_TO_CLOSE_CONNECTION_ERROR, e);
    }
  }

  /**
	 * Generate temporary table name.
	 *
	 * @param targetTypeName the target type name
	 * @return the string
	 */
  protected String generateTemporaryTableName(String targetTypeName) {
    return "#" + targetTypeName + UUID.randomUUID().toString().replace("-", "");
  }

  /**
	 * Creates the O data error response.
	 *
	 * @param resultSet the result set
	 * @return the o data response
	 * @throws SQLException the SQL exception
	 */
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

  /**
	 * Creates the O data response.
	 *
	 * @param resultSet      the result set
	 * @param uriInfo        the uri info
	 * @param oDataContext   the o data context
	 * @param entryMap       the entry map
	 * @param contentType    the content type
	 * @param httpStatusCode the http status code
	 * @return the o data response
	 * @throws SQLException   the SQL exception
	 * @throws ODataException the o data exception
	 */
  protected ODataResponse createODataResponse(ResultSet resultSet, UriInfo uriInfo, ODataContext oDataContext,
      Map<String, Object> entryMap, String contentType, HttpStatusCodes httpStatusCode) throws SQLException, ODataException {
    ODataResponse response = createODataErrorResponse(resultSet);
    if (response != null) {
      return response;
    } else {
      return buildResponse(uriInfo, oDataContext, entryMap, contentType, httpStatusCode);
    }
  }

  /**
	 * Builds the response.
	 *
	 * @param uriInfo        the uri info
	 * @param oDataContext   the o data context
	 * @param entryMap       the entry map
	 * @param contentType    the content type
	 * @param httpStatusCode the http status code
	 * @return the o data response
	 * @throws ODataException the o data exception
	 */
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
