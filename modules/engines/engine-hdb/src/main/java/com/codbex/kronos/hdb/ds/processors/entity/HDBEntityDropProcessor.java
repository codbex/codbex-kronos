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
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableConstraintForeignKeyModel;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Entity Drop Processor.
 */
public class HDBEntityDropProcessor extends AbstractHDBProcessor<DataStructureEntityModel> {

  private static final Logger logger = LoggerFactory.getLogger(HDBEntityDropProcessor.class);
  private static final HDBDDEntitySynchronizationArtefactType ENTITY_ARTEFACT = new HDBDDEntitySynchronizationArtefactType();

  /**
   * Execute the corresponding statement.
   *
   * @param connection  the connection
   * @param entityModel the table model
   * @throws SQLException the SQL exception
   */
  public boolean execute(Connection connection, DataStructureEntityModel entityModel) throws SQLException {
    String tableName = HDBUtils.escapeArtifactName(HDBUtils.getTableName(entityModel));
    logger.info("Processing Drop Table: {}", tableName);
    if (SqlFactory.getNative(connection).exists(connection, tableName)) {
      String sql = SqlFactory.getNative(connection).select().column("COUNT(*)").from(tableName)
          .build();

      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        logger.info(sql);
        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {
            int count = resultSet.getInt(1);
            if (count > 0) {
              String errorMessage = String
                  .format("Drop operation for the non empty Table %s will not be executed. Delete all the records in the table first.",
                      tableName);
              CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, entityModel.getLocation(),
                  CommonsConstants.HDB_TABLE_PARSER);
              logger.error(errorMessage);
              applyArtefactState(entityModel.getName(), entityModel.getLocation(), ENTITY_ARTEFACT, ArtefactState.FAILED_DELETE,
                  errorMessage);
            }
          }
        }
      } catch (SQLException e) {
        CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, entityModel.getLocation(),
            CommonsConstants.HDB_TABLE_PARSER);
        logger.error(sql);
        logger.error(e.getMessage(), e);
        applyArtefactState(entityModel.getName(), entityModel.getLocation(), ENTITY_ARTEFACT, ArtefactState.FAILED_DELETE, e.getMessage());
      }

      if (entityModel.getConstraints().getForeignKeys() != null) {
        for (DataStructureHDBTableConstraintForeignKeyModel foreignKey : entityModel.getConstraints().getForeignKeys()) {
          String foreignKeyName = "FK_" + foreignKey.getName();
          String[] fkColumns = foreignKey.getColumns();
          String[] referencedColumns = foreignKey.getReferencedColumns();
          foreignKeyName = HDBUtils.escapeArtifactName(foreignKeyName);
          for (int i = 0; i < fkColumns.length; i++) {
            fkColumns[i] = HDBUtils.escapeArtifactName(fkColumns[i]);
          }
          for (int i = 0; i < referencedColumns.length; i++) {
            referencedColumns[i] = HDBUtils.escapeArtifactName(referencedColumns[i]);
          }
          sql = SqlFactory.getNative(connection).drop().constraint(foreignKeyName).fromTable(tableName).build();
          try {
            executeSql(sql, connection);
            String message = String.format("Drop entity %s successfully", entityModel.getName());
            applyArtefactState(entityModel.getName(), entityModel.getLocation(), ENTITY_ARTEFACT, ArtefactState.SUCCESSFUL_DELETE, message);
          } catch (SQLException ex) {
            CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, entityModel.getLocation(),
                CommonsConstants.ENTITY_PROCESSOR);
            String message = String.format("Drop entity [%s] skipped due to an error: %s", entityModel, ex.getMessage());
            applyArtefactState(entityModel.getName(), entityModel.getLocation(), ENTITY_ARTEFACT, ArtefactState.FAILED_DELETE, message);
            return false;
          }
        }
      }

      sql = SqlFactory.getNative(connection).drop().table(tableName).build();
      try {
        executeSql(sql, connection);
        String message = String.format("Drop entity %s successfully", entityModel.getName());
        applyArtefactState(entityModel.getName(), entityModel.getLocation(), ENTITY_ARTEFACT, ArtefactState.SUCCESSFUL_DELETE, message);
        return true;
      } catch (SQLException ex) {
        String message = String.format("Drop entity [%s] skipped due to an error: %s", entityModel, ex.getMessage());
        CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, entityModel.getLocation(),
            CommonsConstants.ENTITY_PROCESSOR);
        applyArtefactState(entityModel.getName(), entityModel.getLocation(), ENTITY_ARTEFACT, ArtefactState.FAILED_DELETE, message);
        return false;
      }
    }
    logger.warn("Trying to delete non existing Table: {}", tableName);
    return true;
  }
}
