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

import com.codbex.kronos.hdb.ds.model.hdbdd.DataStructureEntityModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableConstraintForeignKeyModel;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.builders.table.AlterTableBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Entity Create Processor.
 */
public class HDBEntityForeignKeysProcessor extends AbstractHDBProcessor<DataStructureEntityModel> {

  private static final Logger logger = LoggerFactory.getLogger(HDBEntityForeignKeysProcessor.class);

  /**
   * Execute the corresponding statement.
   *
   * @param connection  the connection
   * @param entityModel the entity model
   * @throws SQLException the SQL exception
   */
  public boolean execute(Connection connection, DataStructureEntityModel entityModel)
      throws SQLException {
    String tableName = HDBUtils.getTableName(entityModel);
    logger.info("Processing Foreign Keys to the Table: {}", tableName);
//		CreateTableBuilder createTableBuilder = SqlFactory.getNative(connection).create().table(tableName);

    List<DataStructureHDBTableConstraintForeignKeyModel> foreignKeys = entityModel.getConstraints().getForeignKeys();
    for (DataStructureHDBTableConstraintForeignKeyModel foreignKeyModel : foreignKeys) {

      String sourceTable = HDBUtils.getTableName(entityModel);
      String name = "FK_" + sourceTable + "_" + tableName;
      sourceTable = HDBUtils.escapeArtifactName(sourceTable);

      boolean existing = SqlFactory.getNative(connection).exists(connection, sourceTable);
      if (existing) {
        AlterTableBuilder alterTableBuilder = SqlFactory.getNative(connection).alter().table(sourceTable);
        alterTableBuilder.add().foreignKey(name, foreignKeyModel.getColumns(), tableName, foreignKeyModel.getReferencedColumns());

        String sql = alterTableBuilder.build();
        try {
          executeSql(sql, connection);
        } catch (SQLException ex) {
          CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, entityModel.getLocation(),
              CommonsConstants.ENTITY_PROCESSOR);
          return false;
        }
      } else {
        String reason = "Table does not exist - " + sourceTable;
        CommonsUtils.logProcessorErrors(reason, CommonsConstants.PROCESSOR_ERROR, entityModel.getLocation(),
            CommonsConstants.HDB_ENTITY_PROCESSOR);
        logger.error(reason);
        throw new SQLException(reason);
      }
    }
    return true;
  }

}
