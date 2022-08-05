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

import static java.text.MessageFormat.format;

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableColumnModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.DataStructureHDBTableTypeModel;
import com.codbex.kronos.hdb.ds.module.HDBModule;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;
import com.codbex.kronos.hdb.ds.service.manager.IDataStructureManager;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.Constants;
import com.codbex.kronos.utils.HDBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.eclipse.dirigible.database.sql.DataType;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.builders.tableType.CreateTableTypeBuilder;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableTypeCreateProcessor extends AbstractHDBProcessor<DataStructureHDBTableTypeModel> {

  private static final Logger logger = LoggerFactory.getLogger(TableTypeCreateProcessor.class);
  private Map<String, IDataStructureManager> managerServices = HDBModule.getManagerServices();

  /**
   * Execute the corresponding statement.
   * The method will create a table type and a public synonym in order this table type to be accessed from other schemas.
   *
   * @param connection     the connection
   * @param tableTypeModel the table type model
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean execute(Connection connection, DataStructureHDBTableTypeModel tableTypeModel)
      throws SQLException {
    logger.info("Processing Create Table Type: " + tableTypeModel.getName());
    
    boolean success = false;

    String tableTypeNameWithoutSchema = tableTypeModel.getName();
    String tableTypeNameWithSchema = HDBUtils.escapeArtifactName(tableTypeNameWithoutSchema, tableTypeModel.getSchema());
    List<DataStructureHDBTableColumnModel> columns = tableTypeModel.getColumns();

    if (!SqlFactory.getNative(connection)
        .exists(connection, tableTypeModel.getSchema(), tableTypeNameWithoutSchema, DatabaseArtifactTypes.TABLE_TYPE)) {
      String sql = null;
      CreateTableTypeBuilder createTableTypeBuilder = SqlFactory.getNative(connection).create().tableType(tableTypeNameWithSchema);

      for (DataStructureHDBTableColumnModel columnModel : columns) {
        String name = HDBUtils.escapeArtifactName(columnModel.getName());
        DataType type = DataType.valueOf(columnModel.getType());
        createTableTypeBuilder
            .column(name, type, columnModel.isPrimaryKey(), columnModel.isNullable(), this.getColumnModelArgs(columnModel));
      }

      switch (tableTypeModel.getDBContentType()) {
        case XS_CLASSIC: {
          sql = createTableTypeBuilder.build();
          break;
        }
        case OTHERS: {
          ISqlDialect dialect = SqlFactory.deriveDialect(connection);
          if (dialect.getClass().equals(HanaSqlDialect.class)) {
            sql = Constants.HDBTABLETYPE_CREATE + tableTypeModel.getRawContent();
            break;
          } else {
            String errorMessage = String.format("Table Types are not supported for %s !", dialect.getDatabaseName(connection));
            CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableTypeModel.getLocation(),
                CommonsConstants.HDB_TABLE_TYPE_PARSER);
            throw new IllegalStateException(errorMessage);
          }
        }
      }
      
      try {
        executeSql(sql, connection);
        success = true;
      } catch (SQLException ex) {
    	logger.error(format("Table Type [{0}] failed during the create process", tableTypeNameWithoutSchema));
        CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, tableTypeModel.getLocation(),
            CommonsConstants.HDB_TABLE_TYPE_PARSER);
      }
    } else {
      logger.warn(format("Table Type [{0}] already exists during the create process", tableTypeNameWithoutSchema));
    }

    //Create public synonym only if the table type exist
    if (SqlFactory.getNative(connection)
        .exists(connection, tableTypeModel.getSchema(), tableTypeNameWithoutSchema, DatabaseArtifactTypes.TABLE_TYPE)) {
      HDBUtils.createPublicSynonymForArtifact(managerServices
          .get(IDataStructureModel.TYPE_HDB_SYNONYM), tableTypeNameWithoutSchema, tableTypeModel.getSchema(), connection);
    }
    return success;
  }

  private String getColumnModelArgs(DataStructureHDBTableColumnModel columnModel) {
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
