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
package com.codbex.kronos.hdb.ds.processors.hdbstructure;

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

public class TableTypeDropProcessor extends AbstractHDBProcessor<DataStructureHDBTableTypeModel> {

  private static final Logger logger = LoggerFactory.getLogger(TableTypeDropProcessor.class);

  private final HDBSynonymRemover synonymRemover;

  public TableTypeDropProcessor(HDBSynonymRemover synonymRemover) {
    this.synonymRemover = synonymRemover;
  }

  @Override
  public boolean execute(Connection connection, DataStructureHDBTableTypeModel tableTypeModel) throws SQLException {
    synonymRemover.removePublicSynonym(connection, tableTypeModel.getSchema(), tableTypeModel.getName());

    if (tableTypeDoesNotExist(connection, tableTypeModel)) {
      logger.debug("Table Type [{}] in schema [{}] does not exists during the drop process", tableTypeModel.getName(),
          tableTypeModel.getSchema());
      return true;
    }

    String tableTypeName = escapeTableTypeName(connection, tableTypeModel);
    try {
      String sql = getDropTableTypeSQL(connection, tableTypeName);
      executeSql(sql, connection);
      return true;
    } catch (SQLException | IllegalStateException ex) {
      processException(tableTypeModel, ex);
      return false;
    }
  }

  void processException(DataStructureHDBTableTypeModel tableTypeModel, Exception ex) {
    logger.error("Failed to drop table type [{}] in schema [{}]", tableTypeModel.getName(), tableTypeModel.getSchema(), ex);
    CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, tableTypeModel.getLocation(),
        CommonsConstants.HDB_TABLE_TYPE_PARSER);
  }

  String escapeTableTypeName(Connection connection, DataStructureHDBTableTypeModel tableTypeModel) {
    return HDBUtils.escapeArtifactName(tableTypeModel.getName(), tableTypeModel.getSchema());
  }

  String getDropTableTypeSQL(Connection connection, String tableTypeName) throws IllegalStateException {
    return SqlFactory.getNative(connection).drop().tableType(tableTypeName).build();
  }

  boolean tableTypeDoesNotExist(Connection connection, DataStructureHDBTableTypeModel tableTypeModel) throws SQLException {
    return !SqlFactory.getNative(connection)
        .exists(connection, tableTypeModel.getSchema(), tableTypeModel.getName(), DatabaseArtifactTypes.TABLE_TYPE);
  }
}
