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

import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.engine.hdb.domain.HDBTableType;
import com.codbex.kronos.engine.hdb.parser.HDBSynonymRemover;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The Class HDBTableTypeDropProcessor.
 */
public class HDBStructureDropProcessor extends AbstractHDBProcessor<HDBTableType> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBStructureDropProcessor.class);

  /**
   * The synonym remover.
   */
  private final HDBSynonymRemover synonymRemover = new HDBSynonymRemover();

  /**
   * Execute.
   *
   * @param connection     the connection
   * @param tableTypeModel the structure model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public void execute(Connection connection, HDBTableType tableTypeModel) throws SQLException {
    synonymRemover.removePublicSynonym(connection, tableTypeModel.getSchema(), tableTypeModel.getName());

    if (tableTypeDoesNotExist(connection, tableTypeModel)) {
      logger.debug("Structure [{}] in schema [{}] does not exists during the drop process", tableTypeModel.getName(), tableTypeModel.getSchema());
      return;
    }

    String tableTypeParser = CommonsConstants.HDB_STRUCTURE_PARSER;

    String tableTypeName = escapeTableTypeName(connection, tableTypeModel);
    try {
      String sql = getDropTableTypeSQL(connection, tableTypeName);
      executeSql(sql, connection);
      String message = String.format("Drop structure [%s] successfully", tableTypeModel.getName());
      logger.info(message);
    } catch (SQLException ex) {
      String errorMessage = String.format("Failed to drop structure [%s] in schema [%s]", tableTypeModel.getName(), tableTypeModel.getSchema(), ex);
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableTypeModel.getLocation(), tableTypeParser);
      throw ex;
    }
  }


  /**
   * Escape structure name.
   *
   * @param connection     the connection
   * @param tableTypeModel the structure model
   * @return the string
   */
  String escapeTableTypeName(Connection connection, HDBTableType tableTypeModel) {
    return HDBUtils.escapeArtifactName(tableTypeModel.getName(), tableTypeModel.getSchema());
  }

  /**
   * Gets the drop structure SQL.
   *
   * @param connection    the connection
   * @param tableTypeName the structure name
   * @return the drop structure SQL
   * @throws IllegalStateException the illegal state exception
   */
  String getDropTableTypeSQL(Connection connection, String tableTypeName) throws IllegalStateException {
    return SqlFactory.getNative(connection).drop().tableType(tableTypeName).build();
  }

  /**
   * Structure does not exist.
   *
   * @param connection     the connection
   * @param tableTypeModel the structure model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  boolean tableTypeDoesNotExist(Connection connection, HDBTableType tableTypeModel) throws SQLException {
    return !SqlFactory.getNative(connection)
        .exists(connection, tableTypeModel.getSchema(), tableTypeModel.getName(), DatabaseArtifactTypes.TABLE_TYPE);
  }
}