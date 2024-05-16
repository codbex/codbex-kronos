/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
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
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.builders.tableType.CreateTableTypeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.codbex.kronos.engine.hdb.domain.HDBTableType;
import com.codbex.kronos.engine.hdb.domain.HDBTableTypeColumn;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The Class HDBTableTypeCreateProcessor.
 */
public class HDBStructureCreateProcessor extends AbstractHDBProcessor<HDBTableType> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HDBStructureCreateProcessor.class);

    /**
     * Execute the corresponding statement. The method will create a structure and a public synonym in
     * order this structure to be accessed from other schemas.
     *
     * @param connection the connection
     * @param tableTypeModel the structure model
     * @throws SQLException the SQL exception
     */
    @Override
    public void execute(Connection connection, HDBTableType tableTypeModel) throws SQLException {
        logger.info("Processing Create Structure: " + tableTypeModel.getName());

        String tableTypeNameWithoutSchema = tableTypeModel.getName();
        String tableTypeNameWithSchema = HDBUtils.escapeArtifactName(tableTypeNameWithoutSchema, tableTypeModel.getSchema());

        List<HDBTableTypeColumn> columns = tableTypeModel.getColumns();

        if (!SqlFactory.getNative(connection)
                       .exists(connection, tableTypeModel.getSchema(), tableTypeNameWithoutSchema, DatabaseArtifactTypes.TABLE_TYPE)) {
            String sql = null;
            CreateTableTypeBuilder createTableTypeBuilder = SqlFactory.getNative(connection)
                                                                      .create()
                                                                      .tableType(tableTypeNameWithSchema);

            for (HDBTableTypeColumn columnModel : columns) {
                String name = HDBUtils.escapeArtifactName(columnModel.getName());
                DataType type = DataType.valueOf(columnModel.getType());
                createTableTypeBuilder.column(name, type, columnModel.isPrimaryKey(), columnModel.isNullable(),
                        this.getColumnModelArgs(columnModel));
            }

            String tableTypeParser = CommonsConstants.HDB_STRUCTURE_PARSER;

            sql = createTableTypeBuilder.build();

            try {
                executeSql(sql, connection);
                String message = String.format("Create structure [%s] successfully", tableTypeModel.getName());
                logger.info(message);
                logger.info(message);
            } catch (SQLException ex) {
                String errorMessage = format("Structure [{0}] failed during the create process", tableTypeNameWithoutSchema);
                CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableTypeModel.getLocation(),
                        tableTypeParser);
                throw ex;
            }
        } else {
            logger.warn(format("Structure [{0}] already exists during the create process", tableTypeNameWithoutSchema));
        }

        // Create public synonym only if the structure exist
        if (SqlFactory.getNative(connection)
                      .exists(connection, tableTypeModel.getSchema(), tableTypeNameWithoutSchema, DatabaseArtifactTypes.TABLE_TYPE)) {
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
            if (type.equals(DataType.VARCHAR) || type.equals(DataType.CHAR) || columnModel.getType()
                                                                                          .equalsIgnoreCase("NVARCHAR")
                    || columnModel.getType()
                                  .equalsIgnoreCase("ALPHANUM")
                    || columnModel.getType()
                                  .equalsIgnoreCase("SHORTTEXT")) {
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
