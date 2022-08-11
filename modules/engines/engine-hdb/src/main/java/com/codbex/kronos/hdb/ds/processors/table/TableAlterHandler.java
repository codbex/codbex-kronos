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
package com.codbex.kronos.hdb.ds.processors.table;

import com.codbex.kronos.hdb.ds.artefacts.HDBTableSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableColumnModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.ds.model.IDataStructureModel;
import org.eclipse.dirigible.database.sql.DataType;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.builders.table.AlterTableBuilder;
import org.eclipse.dirigible.databases.helpers.DatabaseMetadataHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class TableAlterHandler.
 */
public class TableAlterHandler {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(TableAlterHandler.class);
  
  /** The Constant INCOMPATIBLE_CHANGE_OF_TABLE. */
  private static final String INCOMPATIBLE_CHANGE_OF_TABLE = "Incompatible change of table [%s] by adding a column [%s] which is [%s]"; //$NON-NLS-1$
  
  /** The Constant TABLE_ARTEFACT. */
  private static final HDBTableSynchronizationArtefactType TABLE_ARTEFACT = new HDBTableSynchronizationArtefactType();
  
  /** The Constant dataStructuresSynchronizer. */
  private static final DataStructuresSynchronizer dataStructuresSynchronizer = new DataStructuresSynchronizer();

  /** The table model. */
  DataStructureHDBTableModel tableModell;
  
  /** The db column types. */
  private Map<String, String> dbColumnTypes;
  
  /** The model column names. */
  private List<String> modelColumnNames;

  /**
   * Instantiates a new table alter handler.
   *
   * @param connection the connection
   * @param tableModell the table model
   * @throws SQLException the SQL exception
   */
  public TableAlterHandler(Connection connection, DataStructureHDBTableModel tableModell) throws SQLException {
    this.dbColumnTypes = new HashMap<>();

    DatabaseMetaData dmd = connection.getMetaData();
    ResultSet rsColumns = dmd
        .getColumns(null, null, DatabaseMetadataHelper.normalizeTableName(HDBUtils.escapeArtifactName(tableModell.getName())),
            null);
    while (rsColumns.next()) {
      this.dbColumnTypes.put(rsColumns.getString("COLUMN_NAME"), rsColumns.getString("TYPE_NAME"));
    }

    this.modelColumnNames = tableModell.getColumns().stream().map(column -> column.getName()).collect(Collectors.toList());
    this.tableModell = tableModell;
  }

  /**
   * Adds the columns.
   *
   * @param connection the connection
   * @throws SQLException the SQL exception
   */
  public void addColumns(Connection connection) throws SQLException {
    String tableName = HDBUtils.escapeArtifactName(this.tableModell.getName(), this.tableModell.getSchema());

    for (DataStructureHDBTableColumnModel columnModel : tableModell.getColumns()) {
      String name = columnModel.getName();
      DataType type = DataType.valueOf(columnModel.getType());
      String length = columnModel.getLength();
      boolean isNullable = columnModel.isNullable();
      boolean isPrimaryKey = columnModel.isPrimaryKey();
      boolean isUnique = columnModel.isUnique();
      String defaultValue = columnModel.getDefaultValue();
      String precision = columnModel.getPrecision();
      String scale = columnModel.getScale();
      String args = "";
      if (length != null) {
        if (type.equals(DataType.VARCHAR) || type.equals(DataType.CHAR) || type.equals(DataType.NVARCHAR)) {
          args = ISqlKeywords.OPEN + length + ISqlKeywords.CLOSE;
        }
      } else if ((precision != null) && (scale != null)) {
        if (type.equals(DataType.DECIMAL)) {
          args = ISqlKeywords.OPEN + precision + "," + scale + ISqlKeywords.CLOSE;
        }
      }
      if (defaultValue != null) {
        if (type.equals(DataType.VARCHAR) || type.equals(DataType.CHAR) || type.equals(DataType.NVARCHAR)) {
          args += " DEFAULT '" + defaultValue + "' ";
        } else {
          args += " DEFAULT " + defaultValue + " ";
        }
      }

      if (!dbColumnTypes.containsKey(name)) {

        AlterTableBuilder alterTableBuilder = SqlFactory.getNative(connection).alter().table(tableName);

        alterTableBuilder.add().column(HDBUtils.escapeArtifactName(name), type, isPrimaryKey, isNullable, isUnique, args);

        if (!isNullable) {
          String errorMessage = String.format(INCOMPATIBLE_CHANGE_OF_TABLE, tableName, name, "NOT NULL");
          CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModell.getLocation(),
              CommonsConstants.HDB_TABLE_PARSER);
          throw new SQLException(errorMessage);
        }
        if (isPrimaryKey) {
          String errorMessage = String.format(INCOMPATIBLE_CHANGE_OF_TABLE, tableName, name, "PRIMARY KEY");
          CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModell.getLocation(),
              CommonsConstants.HDB_TABLE_PARSER);
          throw new SQLException(errorMessage);
        }

        executeAlterBuilder(connection, alterTableBuilder);

      } else if (!dbColumnTypes.get(name).equals(type.toString())) {
        String errorMessage = String
            .format(INCOMPATIBLE_CHANGE_OF_TABLE, tableName, name, "of type " + dbColumnTypes.get(name) + " to be changed to " + type);
        CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModell.getLocation(),
            CommonsConstants.HDB_TABLE_PARSER);
        dataStructuresSynchronizer.applyArtefactState(tableModell.getName(), tableModell.getLocation(), TABLE_ARTEFACT,
            ArtefactState.FAILED_UPDATE, errorMessage);
        throw new SQLException(errorMessage);
      }
    }
  }

  /**
   * Removes the columns.
   *
   * @param connection the connection
   * @throws SQLException the SQL exception
   */
  public void removeColumns(Connection connection) throws SQLException {
    boolean caseSensitive = Boolean.parseBoolean(Configuration.get(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"));
    for (String columnName : this.dbColumnTypes.keySet()) {
      if (!modelColumnNames.contains(columnName)) {
        AlterTableBuilder alterTableBuilder = SqlFactory.getNative(connection).alter()
            .table(HDBUtils.escapeArtifactName(tableModell.getName(), tableModell.getSchema()));
        if (caseSensitive) {
          columnName = "\"" + columnName + "\"";
        }
        alterTableBuilder.drop().column(columnName, DataType.BOOLEAN);
        executeAlterBuilder(connection, alterTableBuilder);
      }
    }
  }

  /**
   * Update columns.
   *
   * @param connection the connection
   * @throws SQLException the SQL exception
   */
  public void updateColumns(Connection connection) throws SQLException {
    String tableName = HDBUtils.escapeArtifactName(this.tableModell.getName(), this.tableModell.getSchema());
    List<DataStructureHDBTableColumnModel> columns = this.getColumnsToUpdate();
    for (DataStructureHDBTableColumnModel columnModel : columns) {
      String name = columnModel.getName();
      DataType type = DataType.valueOf(columnModel.getType());
      String length = columnModel.getLength();
      boolean isNullable = columnModel.isNullable();
      boolean isPrimaryKey = columnModel.isPrimaryKey();
      boolean isUnique = columnModel.isUnique();
      String defaultValue = columnModel.getDefaultValue();
      String precision = columnModel.getPrecision();
      String scale = columnModel.getScale();
      String args = "";
      if (length != null) {
        if (type.equals(DataType.VARCHAR) || type.equals(DataType.CHAR) || type.equals(DataType.NVARCHAR)) {
          args = ISqlKeywords.OPEN + length + ISqlKeywords.CLOSE;
        }
      } else if ((precision != null) && (scale != null)) {
        if (type.equals(DataType.DECIMAL)) {
          args = ISqlKeywords.OPEN + precision + "," + scale + ISqlKeywords.CLOSE;
        }
      }
      if (defaultValue != null) {
        if (type.equals(DataType.VARCHAR) || type.equals(DataType.CHAR) || type.equals(DataType.NVARCHAR)) {
          args += " DEFAULT '" + defaultValue + "' ";
        } else {
          args += " DEFAULT " + defaultValue + " ";
        }
      }

      if (!dbColumnTypes.get(name).equals(type.toString())) {
        String errorMessage = String
            .format(INCOMPATIBLE_CHANGE_OF_TABLE, tableName, name, "of type " + dbColumnTypes.get(name) + " to be changed to" + type);
        CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModell.getLocation(),
            CommonsConstants.HDB_TABLE_PARSER);
        throw new SQLException(errorMessage);
      }
      AlterTableBuilder alterTableBuilder = SqlFactory.getNative(connection).alter().table(tableName);
      alterTableBuilder.alter().column(HDBUtils.escapeArtifactName(name), type, isPrimaryKey, isNullable, isUnique, args);
      executeAlterBuilder(connection, alterTableBuilder);
    }
  }

  /**
   * Rebuild indeces.
   *
   * @param connection the connection
   * @throws SQLException the SQL exception
   */
  public void rebuildIndeces(Connection connection) throws SQLException {
    String tableName = HDBUtils.escapeArtifactName(this.tableModell.getName(), this.tableModell.getSchema());
    AlterTableBuilder alterTableBuilder = SqlFactory.getNative(connection).alter().table(tableName);

    DatabaseMetaData dmd = connection.getMetaData();
    ResultSet rsIndeces = dmd.getIndexInfo(null, null, this.tableModell.getName(), false, false);

    try (Statement stmt = connection.createStatement()) {
      Set<String> droppedIndices = new HashSet<>();
      while (rsIndeces.next()) {
        dropExistingIndex(connection, stmt, droppedIndices, rsIndeces);
      }
    }

    TableBuilder hdbTableBuilder = new TableBuilder();
    hdbTableBuilder.addUniqueIndicesToBuilder(alterTableBuilder, tableModell);
    executeAlterBuilder(connection, alterTableBuilder);
  }

  /**
   * Ensure primary key is unchanged.
   *
   * @param connection the connection
   * @throws SQLException the SQL exception
   */
  public void ensurePrimaryKeyIsUnchanged(Connection connection) throws SQLException {
    DatabaseMetaData dmd = connection.getMetaData();
    ResultSet rsPrimaryKeys = dmd.getPrimaryKeys(null, null, this.tableModell.getName());
    Set<String> dbPrimaryKeys = new HashSet<>();
    Set<String> modelPrimaryKeys = new HashSet<>();
    if (this.tableModell.getConstraints().getPrimaryKey() != null) {
      modelPrimaryKeys = new HashSet<>(Arrays.asList(this.tableModell.getConstraints().getPrimaryKey().getColumns()));
    }
    while (rsPrimaryKeys.next()) {
      dbPrimaryKeys.add(rsPrimaryKeys.getString("COLUMN_NAME"));
    }
    boolean isPKListUnchanged =
        dbPrimaryKeys.size() == modelPrimaryKeys.size() && dbPrimaryKeys.removeAll(modelPrimaryKeys) && dbPrimaryKeys.isEmpty();
    if (!isPKListUnchanged) {
      String errorMessage = String
          .format("Incompatible change of table [%s] by trying to change its primary key list", this.tableModell.getName());
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModell.getLocation(),
          CommonsConstants.HDB_TABLE_PARSER);
      throw new SQLException(errorMessage);
    }
  }

  /**
   * Gets the columns to update.
   *
   * @return the columns to update
   */
  private List<DataStructureHDBTableColumnModel> getColumnsToUpdate() {
    Set<String> dbColumnNames = this.dbColumnTypes.keySet();
    Set<String> columnsToUpdate = new HashSet<String>(dbColumnNames);
    columnsToUpdate.retainAll(modelColumnNames);
    return this.tableModell.getColumns().stream()
        .filter(column -> columnsToUpdate.contains(column.getName()))
        .collect(Collectors.toList());
  }

  /**
   * Drop existing index.
   *
   * @param connection the connection
   * @param stmt the stmt
   * @param droppedIndices the dropped indices
   * @param rsIndeces the rs indeces
   * @throws SQLException the SQL exception
   */
  private void dropExistingIndex(Connection connection, Statement stmt, Set<String> droppedIndices, ResultSet rsIndeces)
      throws SQLException {
    if (!droppedIndices.contains(rsIndeces.getString("INDEX_NAME"))) {

      String sql = String.format("DROP INDEX %s.%s",
          HDBUtils.escapeArtifactName(this.tableModell.getSchema()),
          HDBUtils.escapeArtifactName(rsIndeces.getString("INDEX_NAME")));
      logger.info(sql);
      stmt.executeUpdate(sql);
      droppedIndices.add(rsIndeces.getString("INDEX_NAME"));
    }
  }

  /**
   * Execute alter builder.
   *
   * @param connection the connection
   * @param alterTableBuilder the alter table builder
   * @throws SQLException the SQL exception
   */
  private void executeAlterBuilder(Connection connection, AlterTableBuilder alterTableBuilder)
      throws SQLException {
    final String multiSQL = alterTableBuilder.build();
    String[] sqlStatements = multiSQL.split(ISqlKeywords.SEMICOLON);

    for (String sql : sqlStatements) {
      logger.info(sql);
      PreparedStatement statement = connection.prepareStatement(sql);
      try {
        statement.executeUpdate();
        String messageSuccess = String.format("Update table [%s] successfully", this.tableModell.getName());
        dataStructuresSynchronizer.applyArtefactState(this.tableModell.getName(), this.tableModell.getLocation(), TABLE_ARTEFACT,
            ArtefactState.SUCCESSFUL_UPDATE, messageSuccess);
      } catch (SQLException e) {
        logger.error(sql);
        logger.error(e.getMessage(), e);
        CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, this.tableModell.getLocation(),
            CommonsConstants.HDB_TABLE_PARSER);
        String messageFail = String.format("Update table [%s] skipped due to an error: {%s}", this.tableModell, e.getMessage());
        dataStructuresSynchronizer.applyArtefactState(this.tableModell.getName(), this.tableModell.getLocation(), TABLE_ARTEFACT,
            ArtefactState.FAILED_UPDATE, messageFail);
        throw new SQLException(e.getMessage(), e);
      } finally {
        if (statement != null) {
          statement.close();
        }
      }
    }

  }
}
