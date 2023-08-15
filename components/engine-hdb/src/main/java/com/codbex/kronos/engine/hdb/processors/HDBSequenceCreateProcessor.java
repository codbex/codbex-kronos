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

import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.engine.hdb.domain.HDBSequence;
import com.codbex.kronos.engine.hdb.parser.Constants;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The Class HDBSequenceCreateProcessor.
 */
public class HDBSequenceCreateProcessor extends AbstractHDBProcessor<HDBSequence> {


  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBSequenceCreateProcessor.class);
  
  /**
   * Execute.
   *
   * @param connection the connection
   * @param sequenceModel the hdb sequence model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public void execute(Connection connection, HDBSequence sequenceModel) throws SQLException {
    String hdbSequenceName = HDBUtils.escapeArtifactName(sequenceModel.getName(), sequenceModel.getSchema());
    logger.info("Processing Create Sequence: " + hdbSequenceName);
    String sql = null;
    
    if (sequenceModel.isClassic()) {
        sql = getDatabaseSpecificSQL(connection, sequenceModel, hdbSequenceName);
    } else {
        ISqlDialect dialect = SqlFactory.deriveDialect(connection);
        if (dialect.getClass().equals(HanaSqlDialect.class)) {
          sql = Constants.HDBSEQUENCE_CREATE + sequenceModel.getContent();
        } else {
          String errorMessage = String.format("Sequences are not supported for %s", dialect.getDatabaseName(connection));
          CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, sequenceModel.getLocation(), CommonsConstants.HDB_SEQUENCE_PARSER);
          throw new IllegalStateException(errorMessage);
        }
    }
    
    try {
      executeSql(sql, connection);
      String message = String.format("Create sequence [%s] successfully", sequenceModel.getName());
      logger.info(message);
    } catch (SQLException ex) {
      String errorMessage = String.format("Create sequence [%s] skipped due to an error: %s", sequenceModel.getName(), ex.getMessage());
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, sequenceModel.getLocation(), CommonsConstants.HDB_SEQUENCE_PARSER);
      throw ex;
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
  private String getDatabaseSpecificSQL(Connection connection, HDBSequence sequenceModel,
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
