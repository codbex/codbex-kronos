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
import com.codbex.kronos.hdb.ds.model.hdbsequence.DataStructureHDBSequenceModel;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.Constants;
import com.codbex.kronos.utils.HDBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class HDBSequenceUpdateProcessor.
 */
public class HDBSequenceUpdateProcessor extends AbstractHDBProcessor<DataStructureHDBSequenceModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBSequenceUpdateProcessor.class);
  
  /** The Constant SEQUENCE_ARTEFACT. */
  private static final HDBSequenceSynchronizationArtefactType SEQUENCE_ARTEFACT = new HDBSequenceSynchronizationArtefactType();

  /**
   * Execute.
   *
   * @param connection the connection
   * @param hdbSequenceModel the hdb sequence model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean execute(Connection connection, DataStructureHDBSequenceModel hdbSequenceModel) throws SQLException {
    String hdbSequenceName = HDBUtils.escapeArtifactName(hdbSequenceModel.getName(), hdbSequenceModel.getSchema());
    logger.info("Processing Update HdbSequence: " + hdbSequenceName);

    String sql = null;
    switch (hdbSequenceModel.getDBContentType()) {
      case XS_CLASSIC: {
        sql = getDatabaseSpecificSQL(connection, hdbSequenceModel, hdbSequenceName);
        break;
      }
      case OTHERS: {
        ISqlDialect dialect = SqlFactory.deriveDialect(connection);
        if (dialect.getClass().equals(HanaSqlDialect.class)) {
          sql = Constants.HDBSEQUENCE_ALTER + hdbSequenceModel.getRawContent();
          break;
        } else {
          String errorMessage = String.format("Sequences are not supported for %s !", dialect.getDatabaseName(connection));
          CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, hdbSequenceModel.getLocation(),
              CommonsConstants.HDB_SEQUENCE_PARSER);
          applyArtefactState(hdbSequenceModel.getName(), hdbSequenceModel.getLocation(), SEQUENCE_ARTEFACT, ArtefactState.FAILED_UPDATE,
              errorMessage);
          throw new IllegalStateException(errorMessage);
        }
      }
    }
    try {
      executeSql(sql, connection);
      String message = String.format("Update sequence %s successfully", hdbSequenceModel.getName());
      applyArtefactState(hdbSequenceModel.getName(), hdbSequenceModel.getLocation(), SEQUENCE_ARTEFACT, ArtefactState.SUCCESSFUL_UPDATE,
          message);
      return true;
    } catch (SQLException ex) {
      String message = String.format("Update sequence [%s] skipped due to an error: %s", hdbSequenceModel.getName(), ex.getMessage());
      CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, hdbSequenceModel.getLocation(),
          CommonsConstants.HDB_SEQUENCE_PARSER);
      applyArtefactState(hdbSequenceModel.getName(), hdbSequenceModel.getLocation(), SEQUENCE_ARTEFACT, ArtefactState.FAILED_UPDATE,
          message);
      return false;
    }
  }

  /**
   * Gets the database specific SQL.
   *
   * @param connection the connection
   * @param hdbSequenceModel the hdb sequence model
   * @param hdbSequenceName the hdb sequence name
   * @return the database specific SQL
   */
  private String getDatabaseSpecificSQL(Connection connection, DataStructureHDBSequenceModel hdbSequenceModel,
      String hdbSequenceName) {
    return SqlFactory.getNative(connection).alter().sequence(hdbSequenceName)
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
