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
package com.codbex.kronos.hdb.ds.processors.hdbsequence;

import com.codbex.kronos.hdb.ds.artefacts.HDBSequenceSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbsequence.XSKDataStructureHDBSequenceModel;
import com.codbex.kronos.hdb.ds.processors.AbstractXSKProcessor;
import com.codbex.kronos.utils.XSKCommonsConstants;
import com.codbex.kronos.utils.XSKCommonsUtils;
import com.codbex.kronos.utils.XSKConstants;
import com.codbex.kronos.utils.XSKHDBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XSKHDBSequenceCreateProcessor extends AbstractXSKProcessor<XSKDataStructureHDBSequenceModel> {


  private static final Logger logger = LoggerFactory.getLogger(XSKHDBSequenceCreateProcessor.class);
  private static final HDBSequenceSynchronizationArtefactType SEQUENCE_ARTEFACT = new HDBSequenceSynchronizationArtefactType();

  @Override
  public boolean execute(Connection connection, XSKDataStructureHDBSequenceModel hdbSequenceModel) throws SQLException {
    String hdbSequenceName = XSKHDBUtils.escapeArtifactName(hdbSequenceModel.getName(), hdbSequenceModel.getSchema());
    logger.info("Processing Create HdbSequence: " + hdbSequenceName);
    String sql = null;
    switch (hdbSequenceModel.getDBContentType()) {
      case XS_CLASSIC: {
        sql = getDatabaseSpecificSQL(connection, hdbSequenceModel, hdbSequenceName);
        break;
      }
      case OTHERS: {
        ISqlDialect dialect = SqlFactory.deriveDialect(connection);
        if (dialect.getClass().equals(HanaSqlDialect.class)) {
          sql = XSKConstants.XSK_HDBSEQUENCE_CREATE + hdbSequenceModel.getRawContent();
          break;
        } else {
          String errorMessage = String.format("Sequences are not supported for %s !", dialect.getDatabaseName(connection));
          XSKCommonsUtils.logProcessorErrors(errorMessage, XSKCommonsConstants.PROCESSOR_ERROR, hdbSequenceModel.getLocation(),
              XSKCommonsConstants.HDB_SEQUENCE_PARSER);
          applyArtefactState(hdbSequenceModel.getName(), hdbSequenceModel.getLocation(), SEQUENCE_ARTEFACT, ArtefactState.FAILED_CREATE,
              errorMessage);
          throw new IllegalStateException(errorMessage);
        }
      }
    }
    try {
      executeSql(sql, connection);
      String message = String.format("Create sequence %s successfully", hdbSequenceModel.getName());
      applyArtefactState(hdbSequenceModel.getName(), hdbSequenceModel.getLocation(), SEQUENCE_ARTEFACT, ArtefactState.SUCCESSFUL_CREATE,
          message);
      return true;
    } catch (SQLException ex) {
      String message = String.format("Create sequence [%s] skipped due to an error: %s", hdbSequenceModel.getName(), ex.getMessage());
      XSKCommonsUtils.logProcessorErrors(ex.getMessage(), XSKCommonsConstants.PROCESSOR_ERROR, hdbSequenceModel.getLocation(),
          XSKCommonsConstants.HDB_SEQUENCE_PARSER);
      applyArtefactState(hdbSequenceModel.getName(), hdbSequenceModel.getLocation(), SEQUENCE_ARTEFACT, ArtefactState.FAILED_CREATE,
          message);
      return false;
    }
  }

  private String getDatabaseSpecificSQL(Connection connection, XSKDataStructureHDBSequenceModel hdbSequenceModel,
      String hdbSequenceName) {
    return SqlFactory.getNative(connection).create().sequence(hdbSequenceName)
        .start(hdbSequenceModel.getStartWith())
        .increment(hdbSequenceModel.getIncrementBy())
        .maxvalue(hdbSequenceModel.getMaxValue())
        .nomaxvalue(hdbSequenceModel.getNoMaxValue())
        .minvalue(hdbSequenceModel.getMinValue())
        .nominvalue(hdbSequenceModel.getNoMinValue())
        .cycles(hdbSequenceModel.getCycles())
        .resetBy(hdbSequenceModel.getResetBy()).build();
  }
}
