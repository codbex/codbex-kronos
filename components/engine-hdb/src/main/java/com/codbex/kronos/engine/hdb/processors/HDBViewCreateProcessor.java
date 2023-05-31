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
package com.codbex.kronos.engine.hdb.processors;

import java.sql.Connection;
import java.sql.SQLException;

import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.engine.hdb.domain.HDBView;
import com.codbex.kronos.engine.hdb.parser.Constants;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The HDBViewCreateProcessor.
 */
public class HDBViewCreateProcessor extends AbstractHDBProcessor<HDBView> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBViewCreateProcessor.class);
  
  /**
   * Execute the corresponding statement.
   *
   * @param connection the connection
   * @param viewModel  the view model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public boolean execute(Connection connection, HDBView viewModel)
      throws SQLException {
    logger.info("Processing Create View: " + viewModel.getName());
    
    boolean success = false;
    
    String viewNameWithSchema = HDBUtils.escapeArtifactName(viewModel.getName(), viewModel.getSchema());

    if (!SqlFactory.getNative(connection).exists(connection, viewNameWithSchema, DatabaseArtifactTypes.VIEW)) {
      String sql = null;
      if (viewModel.isClassic()) {
          sql = SqlFactory.getNative(connection).create().view(viewNameWithSchema).asSelect(viewModel.getQuery()).build();
      } else {
          ISqlDialect dialect = SqlFactory.deriveDialect(connection);
          if (dialect.getClass().equals(HanaSqlDialect.class)) {
            sql = Constants.HDBVIEW_CREATE + viewModel.getContent();
          } else {
            String errorMessage = String.format("Views are not supported for %s", dialect.getDatabaseName(connection));
            CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, viewModel.getLocation(),
                CommonsConstants.HDB_VIEW_PARSER);
//            applyArtefactState(viewModel.getName(), viewModel.getLocation(), VIEW_ARTEFACT, ArtefactState.FAILED_CREATE, errorMessage);
            throw new IllegalStateException(errorMessage);
          }
      }
      
      try {
        executeSql(sql, connection);
        String message = String.format("Create view [%s] successfully", viewModel.getName());
//        applyArtefactState(viewModel.getName(), viewModel.getLocation(), VIEW_ARTEFACT, ArtefactState.SUCCESSFUL_CREATE, message);
        success = true;
      } catch (SQLException ex) {
        String errorMessage = String.format("Create view [%s] skipped due to an error: %s", viewModel.getName(), ex.getMessage());
        CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, viewModel.getLocation(), CommonsConstants.HDB_VIEW_PARSER);
//        applyArtefactState(viewModel.getName(), viewModel.getLocation(), VIEW_ARTEFACT, ArtefactState.FAILED_CREATE, errorMessage);
      }
    } else {
      String warningMessage = String.format("View [%s] already exists during the create process", viewModel.getName());
      logger.warn(warningMessage);
//      applyArtefactState(viewModel.getName(), viewModel.getLocation(), VIEW_ARTEFACT, ArtefactState.FAILED_CREATE, warningMessage);
    }

    // Create public synonym
    if (SqlFactory.getNative(connection).exists(connection, viewNameWithSchema, DatabaseArtifactTypes.VIEW)) {
        HDBUtils.createPublicSynonymForArtifact(viewModel.getName(), viewModel.getSchema(), connection);
    }
    return success;
  }

}
