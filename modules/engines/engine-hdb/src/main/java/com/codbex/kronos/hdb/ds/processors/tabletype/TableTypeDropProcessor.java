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
package com.codbex.kronos.hdb.ds.processors.tabletype;

import com.codbex.kronos.hdb.ds.model.hdbtabletype.DataStructureHDBTableTypeModel;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class TableTypeDropProcessor.
 */
public class TableTypeDropProcessor extends AbstractHDBProcessor<DataStructureHDBTableTypeModel> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(TableTypeDropProcessor.class);

  /**
   * The synonym remover.
   */
  private final HDBSynonymRemover synonymRemover;

  /**
   * Instantiates a new table type drop processor.
   *
   * @param synonymRemover the synonym remover
   */
  public TableTypeDropProcessor(HDBSynonymRemover synonymRemover) {
    this.synonymRemover = synonymRemover;
  }

  /**
   * Execute.
   *
   * @param connection     the connection
   * @param tableTypeModel the table type model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean execute(Connection connection, DataStructureHDBTableTypeModel tableTypeModel) throws SQLException {
    synonymRemover.removePublicSynonym(connection, tableTypeModel.getSchema(), tableTypeModel.getName());

    if (tableTypeDoesNotExist(connection, tableTypeModel)) {
      logger.debug("Table Type [{}] in schema [{}] does not exists during the drop process", tableTypeModel.getName(),
          tableTypeModel.getSchema());
      return true;
    }

    String tableTypeParser = null;

    switch (tableTypeModel.getType()) {
      case "HDBTABLETYPE":
        tableTypeParser = CommonsConstants.HDB_TABLE_TYPE_PARSER;
        break;
      case "HDBSTRUCTURE":
        tableTypeParser = CommonsConstants.HDB_STRUCTURE_PARSER;
        break;
    }

    String tableTypeName = escapeTableTypeName(connection, tableTypeModel);
    try {
      String sql = getDropTableTypeSQL(connection, tableTypeName);
      executeSql(sql, connection);
      return true;
    } catch (SQLException | IllegalStateException ex) {
      processException(tableTypeModel, tableTypeParser, ex);
      return false;
    }
  }

  /**
   * Process exception.
   *
   * @param tableTypeModel the table type model
   * @param ex             the ex
   */
  void processException(DataStructureHDBTableTypeModel tableTypeModel, String tableTypeParser, Exception ex) {
    logger.error("Failed to drop table type [{}] in schema [{}]", tableTypeModel.getName(), tableTypeModel.getSchema(), ex);
    CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, tableTypeModel.getLocation(),
        tableTypeParser);
  }

  /**
   * Escape table type name.
   *
   * @param connection     the connection
   * @param tableTypeModel the table type model
   * @return the string
   */
  String escapeTableTypeName(Connection connection, DataStructureHDBTableTypeModel tableTypeModel) {
    return HDBUtils.escapeArtifactName(tableTypeModel.getName(), tableTypeModel.getSchema());
  }

  /**
   * Gets the drop table type SQL.
   *
   * @param connection    the connection
   * @param tableTypeName the table type name
   * @return the drop table type SQL
   * @throws IllegalStateException the illegal state exception
   */
  String getDropTableTypeSQL(Connection connection, String tableTypeName) throws IllegalStateException {
    return SqlFactory.getNative(connection).drop().tableType(tableTypeName).build();
  }

  /**
   * Table type does not exist.
   *
   * @param connection     the connection
   * @param tableTypeModel the table type model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  boolean tableTypeDoesNotExist(Connection connection, DataStructureHDBTableTypeModel tableTypeModel) throws SQLException {
    return !SqlFactory.getNative(connection)
        .exists(connection, tableTypeModel.getSchema(), tableTypeModel.getName(), DatabaseArtifactTypes.TABLE_TYPE);
  }
}