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

public class TableAlterHandler {

  private static final Logger logger = LoggerFactory.getLogger(TableAlterHandler.class);
  private static final String INCOMPATIBLE_CHANGE_OF_TABLE = "Incompatible change of table [%s] by adding a column [%s] which is [%s]"; //$NON-NLS-1$
  private static final HDBTableSynchronizationArtefactType TABLE_ARTEFACT = new HDBTableSynchronizationArtefactType();
  private static final DataStructuresSynchronizer dataStructuresSynchronizer = new DataStructuresSynchronizer();

  DataStructureHDBTableModel tableModel;
  private Map<String, String> dbColumnTypes;
  private List<String> modelColumnNames;

  public TableAlterHandler(Connection connection, DataStructureHDBTableModel tableModel) throws SQLException {
    this.dbColumnTypes = new HashMap<>();

    DatabaseMetaData dmd = connection.getMetaData();
    ResultSet rsColumns = dmd
        .getColumns(null, null, DatabaseMetadataHelper.normalizeTableName(HDBUtils.escapeArtifactName(tableModel.getName())),
            null);
    while (rsColumns.next()) {
      this.dbColumnTypes.put(rsColumns.getString("COLUMN_NAME"), rsColumns.getString("TYPE_NAME"));
    }

    this.modelColumnNames = tableModel.getColumns().stream().map(column -> column.getName()).collect(Collectors.toList());
    this.tableModel = tableModel;
  }

  public void addColumns(Connection connection) throws SQLException {
    String tableName = HDBUtils.escapeArtifactName(this.tableModel.getName(), this.tableModel.getSchema());

    for (DataStructureHDBTableColumnModel columnModel : tableModel.getColumns()) {
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
          CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(),
              CommonsConstants.HDB_TABLE_PARSER);
          throw new SQLException(errorMessage);
        }
        if (isPrimaryKey) {
          String errorMessage = String.format(INCOMPATIBLE_CHANGE_OF_TABLE, tableName, name, "PRIMARY KEY");
          CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(),
              CommonsConstants.HDB_TABLE_PARSER);
          throw new SQLException(errorMessage);
        }

        executeAlterBuilder(connection, alterTableBuilder);

      } else if (!dbColumnTypes.get(name).equals(type.toString())) {
        String errorMessage = String
            .format(INCOMPATIBLE_CHANGE_OF_TABLE, tableName, name, "of type " + dbColumnTypes.get(name) + " to be changed to " + type);
        CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(),
            CommonsConstants.HDB_TABLE_PARSER);
        dataStructuresSynchronizer.applyArtefactState(tableModel.getName(), tableModel.getLocation(), TABLE_ARTEFACT,
            ArtefactState.FAILED_UPDATE, errorMessage);
        throw new SQLException(errorMessage);
      }
    }
  }

  public void removeColumns(Connection connection) throws SQLException {
    boolean caseSensitive = Boolean.parseBoolean(Configuration.get(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"));
    for (String columnName : this.dbColumnTypes.keySet()) {
      if (!modelColumnNames.contains(columnName)) {
        AlterTableBuilder alterTableBuilder = SqlFactory.getNative(connection).alter()
            .table(HDBUtils.escapeArtifactName(tableModel.getName(), tableModel.getSchema()));
        if (caseSensitive) {
          columnName = "\"" + columnName + "\"";
        }
        alterTableBuilder.drop().column(columnName, DataType.BOOLEAN);
        executeAlterBuilder(connection, alterTableBuilder);
      }
    }
  }

  public void updateColumns(Connection connection) throws SQLException {
    String tableName = HDBUtils.escapeArtifactName(this.tableModel.getName(), this.tableModel.getSchema());
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
        CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(),
            CommonsConstants.HDB_TABLE_PARSER);
        throw new SQLException(errorMessage);
      }
      AlterTableBuilder alterTableBuilder = SqlFactory.getNative(connection).alter().table(tableName);
      alterTableBuilder.alter().column(HDBUtils.escapeArtifactName(name), type, isPrimaryKey, isNullable, isUnique, args);
      executeAlterBuilder(connection, alterTableBuilder);
    }
  }

  public void rebuildIndeces(Connection connection) throws SQLException {
    String tableName = HDBUtils.escapeArtifactName(this.tableModel.getName(), this.tableModel.getSchema());
    AlterTableBuilder alterTableBuilder = SqlFactory.getNative(connection).alter().table(tableName);

    DatabaseMetaData dmd = connection.getMetaData();
    ResultSet rsIndeces = dmd.getIndexInfo(null, null, this.tableModel.getName(), false, false);

    try (Statement stmt = connection.createStatement()) {
      Set<String> droppedIndices = new HashSet<>();
      while (rsIndeces.next()) {
        dropExistingIndex(connection, stmt, droppedIndices, rsIndeces);
      }
    }

    TableBuilder tableBuilder = new TableBuilder();
    tableBuilder.addUniqueIndicesToBuilder(alterTableBuilder, tableModel);
    executeAlterBuilder(connection, alterTableBuilder);
  }

  public void ensurePrimaryKeyIsUnchanged(Connection connection) throws SQLException {
    DatabaseMetaData dmd = connection.getMetaData();
    ResultSet rsPrimaryKeys = dmd.getPrimaryKeys(null, null, this.tableModel.getName());
    Set<String> dbPrimaryKeys = new HashSet<>();
    Set<String> modelPrimaryKeys = new HashSet<>();
    if (this.tableModel.getConstraints().getPrimaryKey() != null) {
      modelPrimaryKeys = new HashSet<>(Arrays.asList(this.tableModel.getConstraints().getPrimaryKey().getColumns()));
    }
    while (rsPrimaryKeys.next()) {
      dbPrimaryKeys.add(rsPrimaryKeys.getString("COLUMN_NAME"));
    }
    boolean isPKListUnchanged =
        dbPrimaryKeys.size() == modelPrimaryKeys.size() && dbPrimaryKeys.removeAll(modelPrimaryKeys) && dbPrimaryKeys.isEmpty();
    if (!isPKListUnchanged) {
      String errorMessage = String
          .format("Incompatible change of table [%s] by trying to change its primary key list", this.tableModel.getName());
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(),
          CommonsConstants.HDB_TABLE_PARSER);
      throw new SQLException(errorMessage);
    }
  }

  private List<DataStructureHDBTableColumnModel> getColumnsToUpdate() {
    Set<String> dbColumnNames = this.dbColumnTypes.keySet();
    Set<String> columnsToUpdate = new HashSet<String>(dbColumnNames);
    columnsToUpdate.retainAll(modelColumnNames);
    return this.tableModel.getColumns().stream()
        .filter(column -> columnsToUpdate.contains(column.getName()))
        .collect(Collectors.toList());
  }

  private void dropExistingIndex(Connection connection, Statement stmt, Set<String> droppedIndices, ResultSet rsIndeces)
      throws SQLException {
    if (!droppedIndices.contains(rsIndeces.getString("INDEX_NAME"))) {

      String sql = String.format("DROP INDEX %s.%s",
          HDBUtils.escapeArtifactName(this.tableModel.getSchema()),
          HDBUtils.escapeArtifactName(rsIndeces.getString("INDEX_NAME")));
      logger.info(sql);
      stmt.executeUpdate(sql);
      droppedIndices.add(rsIndeces.getString("INDEX_NAME"));
    }
  }

  private void executeAlterBuilder(Connection connection, AlterTableBuilder alterTableBuilder)
      throws SQLException {
    final String multiSQL = alterTableBuilder.build();
    String[] sqlStatements = multiSQL.split(ISqlKeywords.SEMICOLON);

    for (String sql : sqlStatements) {
      logger.info(sql);
      PreparedStatement statement = connection.prepareStatement(sql);
      try {
        statement.executeUpdate();
        String messageSuccess = String.format("Update table %s successfully", this.tableModel.getName());
        dataStructuresSynchronizer.applyArtefactState(this.tableModel.getName(), this.tableModel.getLocation(), TABLE_ARTEFACT,
            ArtefactState.SUCCESSFUL_UPDATE, messageSuccess);
      } catch (SQLException e) {
        logger.error(sql);
        logger.error(e.getMessage(), e);
        CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, this.tableModel.getLocation(),
            CommonsConstants.HDB_TABLE_PARSER);
        String messageFail = String.format("Update table [%s] skipped due to an error: {%s}", this.tableModel, e.getMessage());
        dataStructuresSynchronizer.applyArtefactState(this.tableModel.getName(), this.tableModel.getLocation(), TABLE_ARTEFACT,
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
