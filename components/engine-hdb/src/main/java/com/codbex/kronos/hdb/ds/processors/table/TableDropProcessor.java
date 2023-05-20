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

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableConstraintForeignKeyModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.module.HDBModule;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;
import com.codbex.kronos.hdb.ds.service.manager.IDataStructureManager;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.Constants;
import com.codbex.kronos.utils.HDBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Table Drop Processor.
 */
public class TableDropProcessor extends AbstractHDBProcessor<DataStructureHDBTableModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(TableDropProcessor.class);
  
  /** The Constant TABLE_ARTEFACT. */
  private static final HDBTableSynchronizationArtefactType TABLE_ARTEFACT = new HDBTableSynchronizationArtefactType();

  /** The manager services. */
  private Map<String, IDataStructureManager> managerServices = HDBModule.getManagerServices();

  /**
   * Execute the corresponding statement.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public boolean execute(Connection connection, DataStructureHDBTableModel tableModel)
      throws SQLException {
    logger.info("Processing Drop Table: " + tableModel.getName());

    String tableName = HDBUtils.escapeArtifactName(tableModel.getName(), tableModel.getSchema());
    String tableNameWithoutSchema = tableModel.getName();

    //Drop public synonym
    HDBUtils.dropPublicSynonymForArtifact(managerServices
        .get(IDataStructureModel.TYPE_HDB_SYNONYM), tableNameWithoutSchema, tableModel.getSchema(), connection);

    if (SqlFactory.getNative(connection).exists(connection, tableName)) {
      String sql = null;
      switch (tableModel.getDBContentType()) {
        case XS_CLASSIC: {
          sql = SqlFactory.getNative(connection).select().column("COUNT(*)").from(tableName)
              .build();
          break;
        }
        case OTHERS: {
          ISqlDialect dialect = SqlFactory.deriveDialect(connection);
          if (dialect.getClass().equals(HanaSqlDialect.class)) {
            sql = Constants.HDBTABLE_DROP + tableModel.getRawContent();
            //SqlFactory.getNative(connection).drop().table(tableName).build();
            break;
          } else {
            String errorMessage = String.format("Tables are not supported for %s !", dialect.getDatabaseName(connection));
            CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(),
                CommonsConstants.HDB_TABLE_PARSER);
            applyArtefactState(tableModel.getName(), tableModel.getLocation(), TABLE_ARTEFACT, ArtefactState.FAILED_DELETE, errorMessage);
            throw new IllegalStateException(errorMessage);
          }
        }
      }

      PreparedStatement statement = null;
      try {
        statement = connection.prepareStatement(sql);
        logger.info(sql);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
          int count = resultSet.getInt(1);
          if (count > 0) {
            String errorMessage = String
                .format("Drop operation for the non empty Table %s will not be executed. Delete all the records in the table first.",
                    tableName);
            CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(),
                CommonsConstants.HDB_TABLE_PARSER);
            logger.error(errorMessage);
            applyArtefactState(tableModel.getName(), tableModel.getLocation(), TABLE_ARTEFACT, ArtefactState.FAILED_DELETE, errorMessage);
            return false;
          }
        }
      } catch (SQLException e) {
        String errorMessage = String.format("Drop table[%s] skipped due to an error: {%s}", tableModel, e.getMessage());
        CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(),
            CommonsConstants.HDB_TABLE_PARSER);
        logger.error(sql);
        logger.error(e.getMessage(), e);
        applyArtefactState(tableModel.getName(), tableModel.getLocation(), TABLE_ARTEFACT, ArtefactState.FAILED_DELETE, errorMessage);
        return false;
      } finally {
        if (statement != null) {
          statement.close();
        }
      }

      if (tableModel.getConstraints().getForeignKeys() != null) {
        for (DataStructureHDBTableConstraintForeignKeyModel foreignKeyModel : tableModel.getConstraints().getForeignKeys()) {
          sql = SqlFactory.getNative(connection).drop().constraint(foreignKeyModel.getName()).fromTable(tableName).build();
          executeUpdate(connection, sql, tableModel);
        }
      }

      sql = SqlFactory.getNative(connection).drop().table(tableName).build();
      return executeUpdate(connection, sql, tableModel);
    }
    return true;
  }

  /**
   * Execute update.
   *
   * @param connection the connection
   * @param sql the sql
   * @param tableModel the table model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  private boolean executeUpdate(Connection connection, String sql, DataStructureHDBTableModel tableModel)
      throws SQLException {
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(sql);
      logger.info(sql);
      statement.executeUpdate();
      String message = String.format("Drop table [%s] successfully", tableModel.getName());
      applyArtefactState(tableModel.getName(), tableModel.getLocation(), TABLE_ARTEFACT, ArtefactState.SUCCESSFUL_DELETE, message);
      return true;
    } catch (SQLException e) {
      String message = String.format("Drop table[%s] skipped due to an error: {%s}", tableModel, e.getMessage());
      CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(),
          CommonsConstants.HDB_TABLE_PARSER);
      logger.error(sql);
      logger.error(e.getMessage(), e);
      applyArtefactState(tableModel.getName(), tableModel.getLocation(), TABLE_ARTEFACT, ArtefactState.FAILED_DELETE, message);
      return false;
    } finally {
      if (statement != null) {
        statement.close();
      }
    }
  }

}
