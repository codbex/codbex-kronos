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
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.codbex.kronos.engine.hdb.domain.HDBSequence;
import com.codbex.kronos.engine.hdb.parser.Constants;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The Class HDBSequenceDropProcessor.
 */
public class HDBSequenceDropProcessor extends AbstractHDBProcessor<HDBSequence> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(HDBSequenceDropProcessor.class);

    /**
     * Execute.
     *
     * @param connection the connection
     * @param sequenceModel the hdb sequence model
     * @throws SQLException the SQL exception
     */
    @Override
    public void execute(Connection connection, HDBSequence sequenceModel) throws SQLException {
        String hdbSequenceName = HDBUtils.escapeArtifactName(sequenceModel.getName(), sequenceModel.getSchema());
        logger.info("Processing Drop Sequence: " + hdbSequenceName);

        if (SqlFactory.getNative(connection)
                      .exists(connection, hdbSequenceName, DatabaseArtifactTypes.SEQUENCE)) {
            String sql = null;
            if (sequenceModel.isClassic()) {
                sql = getDatabaseSpecificSQL(connection, hdbSequenceName);
            } else {
                ISqlDialect dialect = SqlFactory.deriveDialect(connection);
                if (!dialect.getClass()
                            .equals(HanaSqlDialect.class)) {
                    String errorMessage = String.format("Sequences are not supported for %s", dialect.getDatabaseName(connection));
                    CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, sequenceModel.getLocation(),
                            CommonsConstants.HDB_SEQUENCE_PARSER);
                    throw new IllegalStateException(errorMessage);
                }
                sql = Constants.HDBSEQUENCE_DROP + sequenceModel.getContent();
            }

            try {
                executeSql(sql, connection);
                String message = String.format("Drop sequence [%s] successfully", sequenceModel.getName());
                logger.info(message);
            } catch (SQLException ex) {
                String errorMessage =
                        String.format("Drop sequence [%s] skipped due to an error: %s", sequenceModel.getName(), ex.getMessage());
                CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, sequenceModel.getLocation(),
                        CommonsConstants.HDB_SEQUENCE_PARSER);
                throw ex;
            }

        } else {
            String warningMessage = String.format("Sequence [%s] does not exists during the drop process", sequenceModel.getName());
            logger.warn(warningMessage);
        }
    }

    /**
     * Gets the database specific SQL.
     *
     * @param connection the connection
     * @param hdbSequenceName the hdb sequence name
     * @return the database specific SQL
     */
    private String getDatabaseSpecificSQL(Connection connection, String hdbSequenceName) {
        return SqlFactory.getNative(connection)
                         .drop()
                         .sequence(hdbSequenceName)
                         .build();
    }

}
