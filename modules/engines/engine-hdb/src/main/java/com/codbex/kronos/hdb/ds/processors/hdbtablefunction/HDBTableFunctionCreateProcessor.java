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
package com.codbex.kronos.hdb.ds.processors.hdbtablefunction;

import java.sql.Connection;
import java.sql.SQLException;

import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.artefacts.HDBTableFunctionSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.XSKDataStructureHDBTableFunctionModel;
import com.codbex.kronos.hdb.ds.processors.AbstractXSKProcessor;
import com.codbex.kronos.utils.XSKCommonsConstants;
import com.codbex.kronos.utils.XSKCommonsUtils;
import com.codbex.kronos.utils.XSKConstants;

public class HDBTableFunctionCreateProcessor extends AbstractXSKProcessor<XSKDataStructureHDBTableFunctionModel> {

  private static final Logger logger = LoggerFactory.getLogger(HDBTableFunctionCreateProcessor.class);
  private static final HDBTableFunctionSynchronizationArtefactType TABLE_FUNCTION_ARTEFACT = new HDBTableFunctionSynchronizationArtefactType();

  public boolean execute(Connection connection, XSKDataStructureHDBTableFunctionModel hdbTableFunction)
      throws SQLException {
    logger.info("Processing Create TableFunction: " + hdbTableFunction.getName());

    String funcNameWithoutSchema = XSKCommonsUtils.extractArtifactNameWhenSchemaIsProvided(hdbTableFunction.getName())[1];
    hdbTableFunction.setSchema(XSKCommonsUtils.extractArtifactNameWhenSchemaIsProvided(hdbTableFunction.getName())[0]);
    if (!SqlFactory.getNative(connection)
        .exists(connection, hdbTableFunction.getSchema(), funcNameWithoutSchema, DatabaseArtifactTypes.FUNCTION)) {
      ISqlDialect dialect = SqlFactory.deriveDialect(connection);
      if (!(dialect.getClass().equals(HanaSqlDialect.class))) {
        String errorMessage = String.format("TableFunctions are not supported for %s", dialect.getDatabaseName(connection));
        XSKCommonsUtils.logProcessorErrors(errorMessage, XSKCommonsConstants.PROCESSOR_ERROR, hdbTableFunction.getLocation(), XSKCommonsConstants.HDB_TABLE_FUNCTION_PARSER);
        applyArtefactState(hdbTableFunction.getName(),hdbTableFunction.getLocation(),TABLE_FUNCTION_ARTEFACT, ArtefactState.FAILED_CREATE, errorMessage);
        throw new IllegalStateException(errorMessage);
      } else {
        String sql = XSKConstants.XSK_HDBTABLEFUNCTION_CREATE + hdbTableFunction.getContent();
        try {
          executeSql(sql, connection);
          String message = String.format("Create table function %s successfully", hdbTableFunction.getName());
          applyArtefactState(hdbTableFunction.getName(), hdbTableFunction.getLocation(), TABLE_FUNCTION_ARTEFACT, ArtefactState.SUCCESSFUL_CREATE, message);
          return true;
        } catch (SQLException ex) {
          String errorMessage = String.format("Create table function [%s] skipped due to an error: %s", hdbTableFunction.getName(), ex.getMessage());
          XSKCommonsUtils.logProcessorErrors(ex.getMessage(), XSKCommonsConstants.PROCESSOR_ERROR, hdbTableFunction.getLocation(), XSKCommonsConstants.HDB_TABLE_FUNCTION_PARSER);
          applyArtefactState(hdbTableFunction.getName(), hdbTableFunction.getLocation(), TABLE_FUNCTION_ARTEFACT, ArtefactState.FAILED_CREATE, errorMessage);
          return false;
        }
      }
    } else {
      String warningMessage = String.format("TableFunction [%s] already exists during the create process", hdbTableFunction.getName());
      logger.warn(warningMessage);
      applyArtefactState(hdbTableFunction.getName(), hdbTableFunction.getLocation(), TABLE_FUNCTION_ARTEFACT, ArtefactState.FAILED_CREATE, warningMessage);
      return false;
    }
  }
}
