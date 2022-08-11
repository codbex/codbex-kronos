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
package com.codbex.kronos.hdb.ds.processors.function;

import java.sql.Connection;
import java.sql.SQLException;

import com.codbex.kronos.hdb.ds.artefacts.HDBScalarFunctionSynchronizationArtefactType;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizationArtefactType;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.artefacts.HDBTableFunctionSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.DataStructureHDBTableFunctionModel;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.Constants;

/**
 * The Class HDBTableFunctionCreateProcessor.
 */
public class FunctionCreateProcessor extends AbstractHDBProcessor<DataStructureHDBTableFunctionModel> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(FunctionCreateProcessor.class);

  /**
   * The Constant TABLE_FUNCTION_ARTEFACT.
   */
  private static final HDBTableFunctionSynchronizationArtefactType HDB_TABLE_FUNCTION_ARTEFACT = new HDBTableFunctionSynchronizationArtefactType();

  /**
   * The Constant SCALAR_FUNCTION_ARTEFACT.
   */
  private static final HDBScalarFunctionSynchronizationArtefactType HDB_SCALAR_FUNCTION_ARTEFACT = new HDBScalarFunctionSynchronizationArtefactType();

  /**
   * Execute.
   *
   * @param connection    the connection
   * @param functionModel the hdb table function
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public boolean execute(Connection connection, DataStructureHDBTableFunctionModel functionModel)
      throws SQLException {
    logger.info("Processing Create Function: " + functionModel.getName());

    String funcNameWithoutSchema = CommonsUtils.extractArtifactNameWhenSchemaIsProvided(functionModel.getName())[1];
    functionModel.setSchema(CommonsUtils.extractArtifactNameWhenSchemaIsProvided(functionModel.getName())[0]);

    AbstractSynchronizationArtefactType functionArtefact = null;
    String functionParser = null;

    switch (functionModel.getType()) {
      case "HDBTABLEFUNCTION":
        functionParser = CommonsConstants.HDB_TABLE_FUNCTION_PARSER;
        functionArtefact = HDB_TABLE_FUNCTION_ARTEFACT;
        break;
      case "HDBSCALARFUNCTION":
        functionParser = CommonsConstants.HDB_SCALAR_FUNCTION_PARSER;
        functionArtefact = HDB_SCALAR_FUNCTION_ARTEFACT;
        break;
    }

    if (!SqlFactory.getNative(connection)
        .exists(connection, functionModel.getSchema(), funcNameWithoutSchema, DatabaseArtifactTypes.FUNCTION)) {
      ISqlDialect dialect = SqlFactory.deriveDialect(connection);
      if (!(dialect.getClass().equals(HanaSqlDialect.class))) {
        String errorMessage = String.format("Functions are not supported for %s", dialect.getDatabaseName(connection));
        CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, functionModel.getLocation(), functionParser);
        applyArtefactState(functionModel.getName(), functionModel.getLocation(), functionArtefact, ArtefactState.FAILED_CREATE,
            errorMessage);
        throw new IllegalStateException(errorMessage);
      } else {
        String sql = Constants.HDBTABLEFUNCTION_CREATE + functionModel.getRawContent();
        try {
          executeSql(sql, connection);
          String message = String.format("Create table function [%s] successfully", functionModel.getName());
          applyArtefactState(functionModel.getName(), functionModel.getLocation(), functionArtefact, ArtefactState.SUCCESSFUL_CREATE,
              message);
          return true;
        } catch (SQLException ex) {
          String errorMessage = String.format("Create table function [%s] skipped due to an error: %s", functionModel.getName(),
              ex.getMessage());
          CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, functionModel.getLocation(), functionParser);
          applyArtefactState(functionModel.getName(), functionModel.getLocation(), functionArtefact, ArtefactState.FAILED_CREATE,
              errorMessage);
          return false;
        }
      }
    } else {
      String warningMessage = String.format("Function [%s] already exists during the create process.", functionModel.getName());
      logger.warn(warningMessage);
      applyArtefactState(functionModel.getName(), functionModel.getLocation(), functionArtefact, ArtefactState.FAILED_CREATE,
          warningMessage);
      return false;
    }
  }
}
