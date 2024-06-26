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

import com.codbex.kronos.engine.hdb.domain.HDBProcedure;
import com.codbex.kronos.engine.hdb.parser.Constants;
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
 * The Class HDBProcedureCreateProcessor.
 */
public class HDBProcedureCreateProcessor extends AbstractHDBProcessor<HDBProcedure> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HDBProcedureCreateProcessor.class);

    /**
     * Execute.
     *
     * @param connection the connection
     * @param procedureModel the hdb procedure
     * @throws SQLException the SQL exception
     */
    @Override
    public void execute(Connection connection, HDBProcedure procedureModel) throws SQLException {
        logger.info("Processing Create Procedure: " + procedureModel.getName());
        String procedureNameWithoutSchema = CommonsUtils.extractArtifactNameWhenSchemaIsProvided(procedureModel.getName())[1];
        procedureModel.setSchema(CommonsUtils.extractArtifactNameWhenSchemaIsProvided(procedureModel.getName())[0]);

        if (!SqlFactory.getNative(connection)
                       .exists(connection, procedureNameWithoutSchema, DatabaseArtifactTypes.PROCEDURE)) {
            ISqlDialect dialect = SqlFactory.deriveDialect(connection);
            if (!(dialect.getClass()
                         .equals(HanaSqlDialect.class))) {
                String errorMessage = String.format("Procedures are not supported for %s", dialect.getDatabaseName(connection));
                CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, procedureModel.getLocation(),
                        CommonsConstants.HDB_PROCEDURE_PARSER);
                throw new IllegalStateException(errorMessage);
            }
            String sql = Constants.HDBPROCEDURE_CREATE + procedureModel.getContent();
            try {
                String message = String.format("Create procedure [%s] successfully", procedureModel.getName());
                executeSql(sql, connection);
                logger.info(message);
            } catch (SQLException ex) {
                String errorMessage = String.format("Create procedure [%s] skipped due to an error: [%s]. Used sql: [%s]", procedureModel,
                        ex.getMessage(), sql);
                CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, procedureModel.getLocation(),
                        CommonsConstants.HDB_PROCEDURE_PARSER);
                throw new SQLException(errorMessage, ex);
            }
        } else {
            String warningMessage = String.format("Procedure [%s] already exists during the create process", procedureModel.getName());
            logger.warn(warningMessage);
        }
    }
}
