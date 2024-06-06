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

import com.codbex.kronos.engine.hdb.domain.HDBSequence;
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
 * The Class HDBSequenceDropProcessor.
 */
public class HDBSequenceDropProcessor extends AbstractHDBProcessor<HDBSequence> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(HDBSequenceDropProcessor.class);

    private static final int ERR_SQL_CANT_DROP_WITH_RESTRICT = 419; // can't drop with RESTRICT specification

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
        logger.info("Processing Drop Sequence: [{}]", hdbSequenceName);

        if (!SqlFactory.getNative(connection)
                       .exists(connection, sequenceModel.getSchema(), sequenceModel.getName(), DatabaseArtifactTypes.SEQUENCE)) {
            logger.warn("Sequence [{}] does not exists during the drop process", sequenceModel.getName());
            return;
        }
        String sql = null;
        if (sequenceModel.isClassic()) {
            sql = getDatabaseSpecificSQL(connection, hdbSequenceName);
        } else {
            ISqlDialect dialect = SqlFactory.deriveDialect(connection);
            if (!dialect.getClass()
                        .equals(HanaSqlDialect.class)) {
                String errorMessage = String.format("Sequences are not supported for %s", dialect.getDatabaseName(connection));
                logDropError(errorMessage, sequenceModel);
                throw new IllegalStateException(errorMessage);
            }
            sql = Constants.HDBSEQUENCE_DROP + sequenceModel.getContent();
        }

        try {
            executeSql(sql, connection);
            logger.info("Drop sequence [{}}] successfully", sequenceModel.getName());
        } catch (SQLException ex) {
            if (ERR_SQL_CANT_DROP_WITH_RESTRICT == ex.getErrorCode()) {
                try {
                    logger.warn("Sequence [{}] cannot be dropped with sql [{}]. Will try to drop it without drop option. ",
                            sequenceModel.getName(), sql, ex);
                    sql = SqlFactory.getNative(connection)
                                    .drop()
                                    .sequence(hdbSequenceName)
                                    .unsetDropOption() // try without restrict
                                    .build();
                    executeSql(sql, connection);
                    logger.info("Sequence [{}] has been dropped", sequenceModel.getName());
                    return;
                } catch (SQLException e) {
                    logDropError(String.format("Drop sequence [%s] skipped due to an error: %s", sequenceModel.getName(), e.getMessage()),
                            sequenceModel);
                    throw e;
                }
            }
            logDropError(String.format("Drop sequence [%s] skipped due to an error: %s", sequenceModel.getName(), ex.getMessage()),
                    sequenceModel);
            throw ex;
        }
    }

    private void logDropError(String errorMessage, HDBSequence sequenceModel) {
        CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, sequenceModel.getLocation(),
                CommonsConstants.HDB_SEQUENCE_PARSER);
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
