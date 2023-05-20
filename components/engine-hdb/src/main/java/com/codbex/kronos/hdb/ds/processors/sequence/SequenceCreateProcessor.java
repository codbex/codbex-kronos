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
package com.codbex.kronos.hdb.ds.processors.sequence;

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
 * The Class HDBSequenceCreateProcessor.
 */
public class SequenceCreateProcessor extends AbstractHDBProcessor<DataStructureHDBSequenceModel> {


  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(SequenceCreateProcessor.class);
  
  /** The Constant SEQUENCE_ARTEFACT. */
  private static final HDBSequenceSynchronizationArtefactType SEQUENCE_ARTEFACT = new HDBSequenceSynchronizationArtefactType();

  /**
   * Execute.
   *
   * @param connection the connection
   * @param sequenceModel the hdb sequence model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean execute(Connection connection, DataStructureHDBSequenceModel sequenceModel) throws SQLException {
    String hdbSequenceName = HDBUtils.escapeArtifactName(sequenceModel.getName(), sequenceModel.getSchema());
    logger.info("Processing Create Sequence: " + hdbSequenceName);
    String sql = null;
    switch (sequenceModel.getDBContentType()) {
      case XS_CLASSIC: {
        sql = getDatabaseSpecificSQL(connection, sequenceModel, hdbSequenceName);
        break;
      }
      case OTHERS: {
        ISqlDialect dialect = SqlFactory.deriveDialect(connection);
        if (dialect.getClass().equals(HanaSqlDialect.class)) {
          sql = Constants.HDBSEQUENCE_CREATE + sequenceModel.getRawContent();
          break;
        } else {
          String errorMessage = String.format("Sequences are not supported for %s", dialect.getDatabaseName(connection));
          CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, sequenceModel.getLocation(),
              CommonsConstants.HDB_SEQUENCE_PARSER);
          applyArtefactState(sequenceModel.getName(), sequenceModel.getLocation(), SEQUENCE_ARTEFACT, ArtefactState.FAILED_CREATE,
              errorMessage);
          throw new IllegalStateException(errorMessage);
        }
      }
    }
    try {
      executeSql(sql, connection);
      String message = String.format("Create sequence [%s] successfully", sequenceModel.getName());
      applyArtefactState(sequenceModel.getName(), sequenceModel.getLocation(), SEQUENCE_ARTEFACT, ArtefactState.SUCCESSFUL_CREATE,
          message);
      return true;
    } catch (SQLException ex) {
      String message = String.format("Create sequence [%s] skipped due to an error: %s", sequenceModel.getName(), ex.getMessage());
      CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, sequenceModel.getLocation(),
          CommonsConstants.HDB_SEQUENCE_PARSER);
      applyArtefactState(sequenceModel.getName(), sequenceModel.getLocation(), SEQUENCE_ARTEFACT, ArtefactState.FAILED_CREATE,
          message);
      return false;
    }
  }

  /**
   * Gets the database specific SQL.
   *
   * @param connection the connection
   * @param sequenceModel the hdb sequence model
   * @param hdbSequenceName the hdb sequence name
   * @return the database specific SQL
   */
  private String getDatabaseSpecificSQL(Connection connection, DataStructureHDBSequenceModel sequenceModel,
      String hdbSequenceName) {
    return SqlFactory.getNative(connection).create().sequence(hdbSequenceName)
        .start(sequenceModel.getStartWith())
        .increment(sequenceModel.getIncrementBy())
        .maxvalue(sequenceModel.getMaxValue())
        .nomaxvalue(sequenceModel.getNoMaxValue())
        .minvalue(sequenceModel.getMinValue())
        .nominvalue(sequenceModel.getNoMinValue())
        .cycles(sequenceModel.getCycles())
        .resetBy(sequenceModel.getResetBy()).build();
  }
}
