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
package com.codbex.kronos.hdb.ds.processors.procedure;

import java.sql.Connection;
import java.sql.SQLException;

import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.artefacts.HDBProcedureSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbprocedure.DataStructureHDBProcedureModel;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.Constants;

/**
 * The Class HDBProcedureDropProcessor.
 */
public class ProcedureDropProcessor extends AbstractHDBProcessor<DataStructureHDBProcedureModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(ProcedureDropProcessor.class);
  
  /** The Constant PROCEDURE_ARTEFACT. */
  private static final HDBProcedureSynchronizationArtefactType PROCEDURE_ARTEFACT = new HDBProcedureSynchronizationArtefactType();

  /**
   * Execute.
   *
   * @param connection the connection
   * @param procedureModel the hdb procedure
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public boolean execute(Connection connection, DataStructureHDBProcedureModel procedureModel)
      throws SQLException {
    logger.info("Processing Drop Procedure: " + procedureModel.getName());
    String procedureNameWithoutSchema = CommonsUtils.extractArtifactNameWhenSchemaIsProvided(procedureModel.getName())[1];
    procedureModel.setSchema(CommonsUtils.extractArtifactNameWhenSchemaIsProvided(procedureModel.getName())[0]);

    if (SqlFactory.getNative(connection).exists(connection, procedureNameWithoutSchema, DatabaseArtifactTypes.PROCEDURE)) {
      ISqlDialect dialect = SqlFactory.deriveDialect(connection);
      if (!(dialect.getClass().equals(HanaSqlDialect.class))) {
        String errorMessage = String.format("Procedures are not supported for %s", dialect.getDatabaseName(connection));
        CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, procedureModel.getLocation(), CommonsConstants.HDB_PROCEDURE_PARSER);
        applyArtefactState(procedureModel.getName(), procedureModel.getLocation(), PROCEDURE_ARTEFACT, ArtefactState.FAILED_DELETE, errorMessage);
        throw new IllegalStateException(errorMessage);
      } else {
        String sql = Constants.HDBPROCEDURE_DROP + procedureModel.getName();
        try {
          executeSql(sql, connection);
          String message = String.format("Drop procedure [%s] successfully", procedureModel.getName());
          applyArtefactState(procedureModel.getName(), procedureModel.getLocation(), PROCEDURE_ARTEFACT, ArtefactState.SUCCESSFUL_DELETE, message);
          return true;
        } catch (SQLException ex) {
          String message = String.format("Drop procedure[%s] skipped due to an error: %s", procedureModel, ex.getMessage());
          CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, procedureModel.getLocation(), CommonsConstants.HDB_PROCEDURE_PARSER);
          applyArtefactState(procedureModel.getName(), procedureModel.getLocation(), PROCEDURE_ARTEFACT, ArtefactState.FAILED_DELETE, message);
          return false;
        }
      }
    } else {
      String warningMessage = String.format("Procedure [%s] does not exists during the drop process", procedureModel.getName());
      logger.warn(warningMessage);
      applyArtefactState(procedureModel.getName(), procedureModel.getLocation(), PROCEDURE_ARTEFACT, ArtefactState.FAILED_DELETE, warningMessage);
      return true;
    }
  }
}
