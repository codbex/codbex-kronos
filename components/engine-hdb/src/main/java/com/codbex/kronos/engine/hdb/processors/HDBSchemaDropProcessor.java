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

import java.sql.Connection;
import java.sql.SQLException;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.codbex.kronos.engine.hdb.domain.HDBSchema;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The Class HDBSchemaDropProcessor.
 */
public class HDBSchemaDropProcessor extends AbstractHDBProcessor<HDBSchema> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(HDBSchemaDropProcessor.class);

    /**
     * Execute.
     *
     * @param connection the connection
     * @param schemaModel the hdb schema
     * @throws SQLException the SQL exception
     */
    @Override
    public void execute(Connection connection, HDBSchema schemaModel) throws SQLException {
        logger.info("Processing Drop Schema: " + schemaModel.getSchema());

        ISqlDialect dialect = SqlFactory.deriveDialect(connection);
        if (SqlFactory.getNative(connection)
                      .exists(connection, schemaModel.getSchema(), DatabaseArtifactTypes.SCHEMA)) {
            String schemaName = HDBUtils.escapeArtifactName(schemaModel.getSchema());
            String sql = SqlFactory.getNative(connection)
                                   .drop()
                                   .schema(schemaName)
                                   .build();
            try {
                executeSql(sql, connection);
                String message = String.format("Drop schema [%s] successfully", schemaModel.getName());
                logger.info(message);
            } catch (SQLException ex) {
                String errorMessage = String.format("Drop schema[%s] skipped due to an error: %s", schemaModel, ex.getMessage());
                CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, schemaModel.getLocation(),
                        CommonsConstants.HDB_SCHEMA_PARSER);
                throw ex;
            }
        } else {
            String warningMessage = String.format("Schema [%s] does not exists during the drop process", schemaModel.getSchema());
            logger.warn(warningMessage);
        }
    }
}
