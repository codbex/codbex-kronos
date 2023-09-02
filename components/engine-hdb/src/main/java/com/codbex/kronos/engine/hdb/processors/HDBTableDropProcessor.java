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
package com.codbex.kronos.engine.hdb.processors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintForeignKey;
import com.codbex.kronos.engine.hdb.parser.Constants;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The HDBTableDropProcessor.
 */
public class HDBTableDropProcessor extends AbstractHDBProcessor<HDBTable> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBTableDropProcessor.class);
  
  /**
   * Execute the corresponding statement.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public void execute(Connection connection, HDBTable tableModel) throws SQLException {
    logger.info("Processing Drop Table: " + tableModel.getName());

    String tableName = HDBUtils.escapeArtifactName(tableModel.getName(), tableModel.getSchema());
    String tableNameWithoutSchema = tableModel.getName();

    //Drop public synonym
    HDBUtils.dropPublicSynonymForArtifact(tableNameWithoutSchema, tableModel.getSchema(), connection);

    if (SqlFactory.getNative(connection).exists(connection, tableName)) {
      String sql = null;
      if (tableModel.isClassic()) {
          sql = SqlFactory.getNative(connection).select().column("COUNT(*)").from(tableName).build();
      } else {
          ISqlDialect dialect = SqlFactory.deriveDialect(connection);
          if (dialect.getClass().equals(HanaSqlDialect.class)) {
            sql = Constants.HDBTABLE_DROP + tableModel.getContent();
          } else {
            String errorMessage = String.format("Tables are not supported for %s !", dialect.getDatabaseName(connection));
            CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(), CommonsConstants.HDB_TABLE_PARSER);
            throw new IllegalStateException(errorMessage);
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
            String errorMessage = String.format("Drop operation for the non empty Table %s will not be executed. Delete all the records in the table first.", tableName);
            CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(), CommonsConstants.HDB_TABLE_PARSER);
            logger.error(errorMessage);
            throw new SQLException(errorMessage);
          }
        }
      } catch (SQLException ex) {
        String errorMessage = String.format("Drop table[%s] skipped due to an error: {%s}", tableModel, ex.getMessage());
        CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(), CommonsConstants.HDB_TABLE_PARSER);
        throw ex;
      } finally {
        if (statement != null) {
          statement.close();
        }
      }

      if (tableModel.getConstraints().getForeignKeys() != null) {
        for (HDBTableConstraintForeignKey foreignKeyModel : tableModel.getConstraints().getForeignKeys()) {
          sql = SqlFactory.getNative(connection).drop().constraint(foreignKeyModel.getName()).fromTable(tableName).build();
          executeUpdate(connection, sql, tableModel);
        }
      }

      sql = SqlFactory.getNative(connection).drop().table(tableName).build();
      executeUpdate(connection, sql, tableModel);
    }
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
  private void executeUpdate(Connection connection, String sql, HDBTable tableModel) throws SQLException {
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(sql);
      logger.info(sql);
      statement.executeUpdate();
      String message = String.format("Drop table [%s] successfully", tableModel.getName());
      logger.info(message);
    } catch (SQLException ex) {
      String errorMessage = String.format("Drop table[%s] skipped due to an error: {%s}", tableModel, ex.getMessage());
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(), CommonsConstants.HDB_TABLE_PARSER);
      throw ex;
    } finally {
      if (statement != null) {
        statement.close();
      }
    }
  }

}
