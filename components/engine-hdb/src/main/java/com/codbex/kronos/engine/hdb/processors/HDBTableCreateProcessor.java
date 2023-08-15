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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.TableStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.parser.Constants;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The HDBTableCreateProcessor.
 */
public class HDBTableCreateProcessor extends AbstractHDBProcessor<HDBTable> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBTableCreateProcessor.class);
  
  /**
   * Execute the corresponding statement.
   * The method will create a table and a public synonym in order this table to be accessed from other schemas.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public void execute(Connection connection, HDBTable tableModel) throws SQLException {
    logger.info("Processing Create Table: " + tableModel.getName());

    Collection<String> indicesStatements = new ArrayList<>();
    String tableCreateStatement;
    String tableNameWithoutSchema = tableModel.getName();
    String tableNameWithSchema = HDBUtils.escapeArtifactName(tableModel.getName(), tableModel.getSchema());

    if (tableModel.isClassic()) {
        TableStatements tableStatements = new HDBTableBuilder().build(connection, tableModel);
        tableCreateStatement = tableStatements.getCreateTableStatement();
        indicesStatements.addAll(tableStatements.getCreateIndicesStatements());
    } else {
        tableCreateStatement = Constants.HDBTABLE_CREATE + tableModel.getContent();
    }

    processStatements(connection, tableModel, indicesStatements, tableCreateStatement);
    processSynonym(connection, tableModel, tableNameWithoutSchema, tableNameWithSchema);
  }

  /**
   * Process synonym.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @param tableNameWithoutSchema the table name without schema
   * @param tableNameWithSchema the table name with schema
   * @throws SQLException the SQL exception
   */
  private void processSynonym(Connection connection, HDBTable tableModel, String tableNameWithoutSchema, String tableNameWithSchema) throws SQLException {
    boolean shouldCreatePublicSynonym = SqlFactory.getNative(connection).exists(connection, tableNameWithSchema, DatabaseArtifactTypes.TABLE);
    if (shouldCreatePublicSynonym) {
      HDBUtils.createPublicSynonymForArtifact(tableNameWithoutSchema, tableModel.getSchema(), connection);
    }
  }

  /**
   * Process statements.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @param indicesStatements the indices statements
   * @param tableCreateStatement the table create statement
   * @return true, if successful
   */
  private void processStatements(Connection connection, HDBTable tableModel, Collection<String> indicesStatements, String tableCreateStatement) throws SQLException {
    try {
      executeSql(tableCreateStatement, connection);
      if (!indicesStatements.isEmpty()) {
        executeBatch(indicesStatements, connection);
      }
      String message = String.format("Create table [%s] successfully", tableModel.getName());
      logger.info(message);
    } catch (SQLException ex) {
      String errorMessage = String.format("Create table [%s] failed due to an error. Used SQL - create table %s, indices %s", tableModel, tableCreateStatement, String.join("; ", indicesStatements), ex);
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(), CommonsConstants.HDB_TABLE_PARSER);
      throw ex;
    }
  }

  /**
   * Execute batch.
   *
   * @param createStatements the create statements
   * @param connection the connection
   * @throws SQLException the SQL exception
   */
  private void executeBatch(Collection<String> createStatements, Connection connection) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      for (String createSQL : createStatements) {
        logger.debug("Adding SQL statement to the batch - {}", createSQL);
        statement.addBatch(createSQL);
      }
      statement.executeBatch();
    }
  }
}
