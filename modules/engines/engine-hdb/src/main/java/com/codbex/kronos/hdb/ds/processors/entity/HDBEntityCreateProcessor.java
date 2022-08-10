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
package com.codbex.kronos.hdb.ds.processors.entity;

import com.codbex.kronos.hdb.ds.artefacts.HDBDDEntitySynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbdd.DataStructureEntityModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableColumnModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableConstraintForeignKeyModel;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.sql.DataType;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.builders.table.CreateTableBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Entity Create Processor.
 */
public class HDBEntityCreateProcessor extends AbstractHDBProcessor<DataStructureEntityModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBEntityCreateProcessor.class);
  
  /** The Constant ENTITY_ARTEFACT. */
  private static final HDBDDEntitySynchronizationArtefactType ENTITY_ARTEFACT = new HDBDDEntitySynchronizationArtefactType();

  /**
   * Execute the corresponding statement.
   *
   * @param connection  the connection
   * @param entityModel the entity model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public boolean execute(Connection connection, DataStructureEntityModel entityModel) throws SQLException {
    String tableName = HDBUtils.escapeArtifactName(HDBUtils.getTableName(entityModel));
    logger.info("Processing Create Table: {}", tableName);
    CreateTableBuilder createTableBuilder = SqlFactory.getNative(connection).create().table(tableName);
    List<DataStructureHDBTableColumnModel> columns = entityModel.getColumns();
    List<String> primaryKeyColumns = new ArrayList<>();
    for (DataStructureHDBTableColumnModel columnModel : columns) {
      String name = HDBUtils.escapeArtifactName(columnModel.getName());
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
        if (type.equals(DataType.VARCHAR) || type.equals(DataType.CHAR)
            || columnModel.getType().equalsIgnoreCase("NVARCHAR")
            || columnModel.getType().equalsIgnoreCase("ALPHANUM")
            || columnModel.getType().equalsIgnoreCase("SHORTTEXT")) {
          args = ISqlKeywords.OPEN + length + ISqlKeywords.CLOSE;
        }
      } else if ((precision != null) && (scale != null)) {
        if (type.equals(DataType.DECIMAL)) {
          args = ISqlKeywords.OPEN + precision + "," + scale + ISqlKeywords.CLOSE;
        }
      }
      if (defaultValue != null) {
        if ("".equals(defaultValue)) {
          if ((type.equals(DataType.VARCHAR) || type.equals(DataType.CHAR))) {
            args += " DEFAULT '" + defaultValue + "' ";
          }
        } else {
          args += " DEFAULT " + defaultValue + " ";
        }

      }
      if (isPrimaryKey) {
        primaryKeyColumns.add(name);
      }
      createTableBuilder.column(name, type, false, isNullable, isUnique, args);
    }
    if (entityModel.getConstraints() != null) {
      if (!primaryKeyColumns.isEmpty()) {
        createTableBuilder.primaryKey(primaryKeyColumns.toArray(new String[]{}));
      } else if (entityModel.getConstraints().getPrimaryKey() != null) {
        createTableBuilder
            .primaryKey(entityModel.getConstraints().getPrimaryKey().getName(), entityModel.getConstraints().getPrimaryKey().getColumns());
      }
      if (entityModel.getConstraints().getForeignKeys() != null) {
        for (DataStructureHDBTableConstraintForeignKeyModel foreignKey : entityModel.getConstraints().getForeignKeys()) {
          String foreignKeyName = "FK_" + foreignKey.getName();
          String[] fkColumns = foreignKey.getColumns();
          String referencedTable = HDBUtils
              .escapeArtifactName(HDBUtils.getTableName(entityModel, foreignKey.getReferencedTable()));
          String[] referencedColumns = foreignKey.getReferencedColumns();
          foreignKeyName = HDBUtils.escapeArtifactName(foreignKeyName);
          for (int i = 0; i < fkColumns.length; i++) {
            fkColumns[i] = HDBUtils.escapeArtifactName(fkColumns[i]);
          }
          for (int i = 0; i < referencedColumns.length; i++) {
            referencedColumns[i] = HDBUtils.escapeArtifactName(referencedColumns[i]);
          }
          createTableBuilder.foreignKey(foreignKeyName, fkColumns, referencedTable, referencedColumns);
        }
      }
    }

    String sql = createTableBuilder.build();
    try {
      executeSql(sql, connection);
      String message = String.format("Create entity %s successfully", entityModel.getName());
      applyArtefactState(entityModel.getName(), entityModel.getLocation(), ENTITY_ARTEFACT, ArtefactState.SUCCESSFUL_CREATE, message);
      return true;
    } catch (SQLException ex) {
      String message = String.format("Create entity [%s] skipped due to an error: %s", entityModel, ex.getMessage());
      CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, entityModel.getLocation(),
          CommonsConstants.ENTITY_PROCESSOR);
      applyArtefactState(entityModel.getName(), entityModel.getLocation(), ENTITY_ARTEFACT, ArtefactState.FAILED_CREATE, message);
      return false;
    }
  }

}
