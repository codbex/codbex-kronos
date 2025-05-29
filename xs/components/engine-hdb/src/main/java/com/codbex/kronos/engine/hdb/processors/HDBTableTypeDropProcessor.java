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

import com.codbex.kronos.engine.hdb.domain.HDBTableType;
import com.codbex.kronos.engine.hdb.parser.HDBSynonymRemover;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The Class HDBTableTypeDropProcessor.
 */
public class HDBTableTypeDropProcessor extends AbstractHDBProcessor<HDBTableType> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HDBTableTypeDropProcessor.class);

    /**
     * The synonym remover.
     */
    private final HDBSynonymRemover synonymRemover = new HDBSynonymRemover();

    /**
     * Execute.
     *
     * @param connection the connection
     * @param tableTypeModel the table type model
     * @throws SQLException the SQL exception
     */
    @Override
    public void execute(Connection connection, HDBTableType tableTypeModel) throws SQLException {
        synonymRemover.removePublicSynonym(connection, tableTypeModel.getSchema(), tableTypeModel.getName());

        if (tableTypeDoesNotExist(connection, tableTypeModel)) {
            logger.debug("Table Type [{}] in schema [{}] does not exists during the drop process", tableTypeModel.getName(),
                    tableTypeModel.getSchema());
            return;
        }

        String tableTypeParser = CommonsConstants.HDB_TABLE_TYPE_PARSER;

        String tableTypeName = escapeTableTypeName(connection, tableTypeModel);
        try {
            String sql = getDropTableTypeSQL(connection, tableTypeName);
            executeSql(sql, connection);
            String message = String.format("Drop table type [%s] successfully", tableTypeName);
            logger.info(message);
        } catch (SQLException ex) {
            String errorMessage =
                    String.format("Failed to drop table type [%s] in schema [%s]", tableTypeModel.getName(), tableTypeModel.getSchema());
            CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableTypeModel.getLocation(), tableTypeParser,
                    ex);
            throw ex;
        }
    }

    /**
     * Escape table type name.
     *
     * @param connection the connection
     * @param tableTypeModel the table type model
     * @return the string
     */
    public String escapeTableTypeName(Connection connection, HDBTableType tableTypeModel) {
        return HDBUtils.escapeArtifactName(tableTypeModel.getName(), tableTypeModel.getSchema());
    }

    /**
     * Gets the drop table type SQL.
     *
     * @param connection the connection
     * @param tableTypeName the table type name
     * @return the drop table type SQL
     * @throws IllegalStateException the illegal state exception
     */
    public String getDropTableTypeSQL(Connection connection, String tableTypeName) throws IllegalStateException {
        return SqlFactory.getNative(connection)
                         .drop()
                         .tableType(tableTypeName)
                         .build();
    }

    /**
     * Table type does not exist.
     *
     * @param connection the connection
     * @param tableTypeModel the table type model
     * @return true, if successful
     * @throws SQLException the SQL exception
     */
    public boolean tableTypeDoesNotExist(Connection connection, HDBTableType tableTypeModel) throws SQLException {
        return !SqlFactory.getNative(connection)
                          .exists(connection, tableTypeModel.getSchema(), tableTypeModel.getName(), DatabaseArtifactTypes.TABLE_TYPE);
    }
}
