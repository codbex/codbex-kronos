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

import com.codbex.kronos.engine.hdb.domain.HDBScalarFunction;
import com.codbex.kronos.engine.hdb.parser.Constants;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import java.sql.Connection;
import java.sql.SQLException;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class HDBTableFunctionDropProcessor.
 */
public class HDBScalarFunctionDropProcessor extends AbstractHDBProcessor<HDBScalarFunction> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HDBScalarFunctionDropProcessor.class);

    /**
     * Execute.
     *
     * @param connection the connection
     * @throws SQLException the SQL exception
     */
    @Override
    public void execute(Connection connection, HDBScalarFunction functionModel) throws SQLException {
        logger.info("Processing Drop Function: " + functionModel.getName());

        String funcNameWithoutSchema = CommonsUtils.extractArtifactNameWhenSchemaIsProvided(functionModel.getName())[1];
        functionModel.setSchema(CommonsUtils.extractArtifactNameWhenSchemaIsProvided(functionModel.getName())[0]);

        String functionParser = CommonsConstants.HDB_SCALAR_FUNCTION_PARSER;

        if (SqlFactory.getNative(connection)
                      .exists(connection, functionModel.getSchema(), funcNameWithoutSchema, DatabaseArtifactTypes.FUNCTION)) {
            ISqlDialect dialect = SqlFactory.deriveDialect(connection);
            if (!(dialect.getClass()
                         .equals(HanaSqlDialect.class))) {
                String errorMessage = String.format("Functions are not supported for %s", dialect.getDatabaseName(connection));
                CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, functionModel.getLocation(),
                        functionParser);
                throw new IllegalStateException(errorMessage);
            }
            String sql = Constants.HDBTABLEFUNCTION_DROP + HDBUtils.escapeArtifactName(functionModel.getName(), functionModel.getSchema());
            try {
                executeSql(sql, connection);
                String message = String.format("Drop function [%s] successfully", functionModel.getName());
                logger.info(message);
            } catch (SQLException ex) {
                String errorMessage = String.format("Drop function [%s] skipped due to an error: [%s]. Used sql [%s]",
                        functionModel.getName(), ex.getMessage(), sql);
                CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, functionModel.getLocation(),
                        functionParser);
                throw new SQLException(errorMessage, ex);
            }
        } else {
            String warningMessage = String.format("Function [%s] does not exists during the drop process", functionModel.getName());
            logger.warn(warningMessage);
        }
    }

}
