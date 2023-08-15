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

import static java.text.MessageFormat.format;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.eclipse.dirigible.database.sql.DataType;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.builders.tableType.CreateTableTypeBuilder;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.engine.hdb.domain.HDBTableType;
import com.codbex.kronos.engine.hdb.domain.HDBTableTypeColumn;
import com.codbex.kronos.engine.hdb.parser.Constants;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The Class HDBTableTypeCreateProcessor.
 */
public class HDBTableTypeCreateProcessor extends AbstractHDBProcessor<HDBTableType> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBTableTypeCreateProcessor.class);

  /**
   * Execute the corresponding statement.
   * The method will create a table type and a public synonym in order this table type to be accessed from other schemas.
   *
   * @param connection     the connection
   * @param tableTypeModel the table type model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public void execute(Connection connection, HDBTableType tableTypeModel) throws SQLException {
    logger.info("Processing Create Table Type: " + tableTypeModel.getName());

    String tableTypeNameWithoutSchema = tableTypeModel.getName();
    String tableTypeNameWithSchema = HDBUtils.escapeArtifactName(tableTypeNameWithoutSchema, tableTypeModel.getSchema());

    List<HDBTableTypeColumn> columns = tableTypeModel.getColumns();

    if (!SqlFactory.getNative(connection).exists(connection, tableTypeModel.getSchema(), tableTypeNameWithoutSchema, DatabaseArtifactTypes.TABLE_TYPE)) {
      String sql = null;
      CreateTableTypeBuilder createTableTypeBuilder = SqlFactory.getNative(connection).create().tableType(tableTypeNameWithSchema);

      for (HDBTableTypeColumn columnModel : columns) {
        String name = HDBUtils.escapeArtifactName(columnModel.getName());
        DataType type = DataType.valueOf(columnModel.getType());
        createTableTypeBuilder.column(name, type, columnModel.isPrimaryKey(), columnModel.isNullable(), this.getColumnModelArgs(columnModel));
      }

      String tableTypeParser = CommonsConstants.HDB_TABLE_TYPE_PARSER;

      ISqlDialect dialect = SqlFactory.deriveDialect(connection);
      if (dialect.getClass().equals(HanaSqlDialect.class)) {
        sql = Constants.HDBTABLETYPE_CREATE + tableTypeModel.getContent();
      } else {
        String errorMessage = String.format("Table Types are not supported for %s", dialect.getDatabaseName(connection));
        CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableTypeModel.getLocation(), tableTypeParser);
        throw new IllegalStateException(errorMessage);
      }
  

      try {
        executeSql(sql, connection);
        String message = String.format("Create table type [%s] successfully", tableTypeNameWithoutSchema);
        logger.info(message);
      } catch (SQLException ex) {
        String errorMessage = String.format("Table Type [%s] failed during the create process", tableTypeNameWithoutSchema);
        CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableTypeModel.getLocation(), tableTypeParser);
      }
    } else {
      logger.warn(format("Table Type [{0}] already exists during the create process", tableTypeNameWithoutSchema));
    }

    // Create public synonym only if the table type exist
    if (SqlFactory.getNative(connection).exists(connection, tableTypeModel.getSchema(), tableTypeNameWithoutSchema, DatabaseArtifactTypes.TABLE_TYPE)) {
      HDBUtils.createPublicSynonymForArtifact(tableTypeNameWithoutSchema, tableTypeModel.getSchema(), connection);
    }
  }

  /**
   * Gets the column model args.
   *
   * @param columnModel the column model
   * @return the column model args
   */
  private String getColumnModelArgs(HDBTableTypeColumn columnModel) {
    DataType type = DataType.valueOf(columnModel.getType());
    String args = "";
    if (columnModel.getLength() != null) {
      if (type.equals(DataType.VARCHAR) || type.equals(DataType.CHAR)
          || columnModel.getType().equalsIgnoreCase("NVARCHAR")
          || columnModel.getType().equalsIgnoreCase("ALPHANUM")
          || columnModel.getType().equalsIgnoreCase("SHORTTEXT")) {
        args = columnModel.getLength();
      }
    } else if ((columnModel.getPrecision() != null) && (columnModel.getScale() != null)) {
      if (type.equals(DataType.DECIMAL)) {
        args = columnModel.getPrecision() + "," + columnModel.getScale();
      }
    }
    return args;
  }
}