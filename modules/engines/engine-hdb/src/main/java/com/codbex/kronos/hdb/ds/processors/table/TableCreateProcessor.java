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

import com.codbex.kronos.hdb.ds.api.HDBDataStructureModel;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableDataStructureModel;
import com.codbex.kronos.hdb.ds.module.HDBModule;
import com.codbex.kronos.hdb.ds.processors.AbstractProcessor;
import com.codbex.kronos.hdb.ds.service.manager.IDataStructureManager;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.Constants;
import com.codbex.kronos.utils.HDBUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Table Create Processor.
 */
public class TableCreateProcessor extends AbstractProcessor<HDBTableDataStructureModel> {

  private static final Logger logger = LoggerFactory.getLogger(TableCreateProcessor.class);
  private static final HDBTableSynchronizationArtefactType TABLE_ARTEFACT = new HDBTableSynchronizationArtefactType();

  private final Map<String, IDataStructureManager> managerServices = HDBModule.getManagerServices();

  /**
   * Execute the corresponding statement.
   * The method will create a table and a public synonym in order this table to be accessed from other schemas.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @throws SQLException the SQL exception
   */
  public boolean execute(Connection connection, HDBTableDataStructureModel tableModel)
      throws SQLException {
    logger.info("Processing Create Table: " + tableModel.getName());

    Collection<String> indicesStatements = new ArrayList<>();
    String tableCreateStatement;
    String tableNameWithoutSchema = tableModel.getName();
    String tableNameWithSchema = HDBUtils.escapeArtifactName(tableModel.getName(), tableModel.getSchema());

    switch (tableModel.getDBContentType()) {
      case XS_CLASSIC: {
        Table table = new TableBuilder().build(tableModel);
        tableCreateStatement = table.getCreateTableStatement();
        indicesStatements.addAll(table.getCreateIndicesStatements());
        break;
      }
      case OTHERS: {
        tableCreateStatement = Constants.HDBTABLE_CREATE + tableModel.getRawContent();
        break;
      }
      default:
        throw new IllegalStateException("Unsupported content type: " + tableModel.getDBContentType());
    }

    boolean success = processStatements(connection, tableModel, indicesStatements, tableCreateStatement);
    processSynonym(connection, tableModel, tableNameWithoutSchema, tableNameWithSchema);
    return success;
  }

  private void processSynonym(Connection connection, HDBTableDataStructureModel tableModel, String tableNameWithoutSchema,
      String tableNameWithSchema) throws SQLException {
    boolean shouldCreatePublicSynonym = SqlFactory.getNative(connection)
        .exists(connection, tableNameWithSchema, DatabaseArtifactTypes.TABLE);
    if (shouldCreatePublicSynonym) {
      HDBUtils.createPublicSynonymForArtifact(managerServices
          .get(HDBDataStructureModel.TYPE_HDB_SYNONYM), tableNameWithoutSchema, tableModel.getSchema(), connection);
    }
  }

  private boolean processStatements(Connection connection, HDBTableDataStructureModel tableModel, Collection<String> indicesStatements,
      String tableCreateStatement) {
    try {
      executeSql(tableCreateStatement, connection);

      if (!indicesStatements.isEmpty()) {
        executeBatch(indicesStatements, connection);
      }

      String message = String.format("Create table %s successfully", tableModel.getName());
      applyArtefactState(tableModel.getName(), tableModel.getLocation(), TABLE_ARTEFACT, ArtefactState.SUCCESSFUL_CREATE, message);
      return true;
    } catch (SQLException ex) {
      logger.error("Creation of table failed. Used SQL - create table {}, indices {}", tableCreateStatement,
          String.join("; ", indicesStatements), ex);
      CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, tableModel.getLocation(),
          CommonsConstants.HDB_TABLE_PARSER);
      String message = String.format("Create table [%s] failed due to an error: %s", tableModel, ex.getMessage());
      applyArtefactState(tableModel.getName(), tableModel.getLocation(), TABLE_ARTEFACT, ArtefactState.FAILED_CREATE, message);
      return false;
    }
  }

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
