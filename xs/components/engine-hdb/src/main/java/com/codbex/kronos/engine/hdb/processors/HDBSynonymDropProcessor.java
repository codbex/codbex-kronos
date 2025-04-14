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

import com.codbex.kronos.engine.hdb.domain.HDBSynonym;
import com.codbex.kronos.engine.hdb.domain.HDBSynonymGroup;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class HDBSynonymDropProcessor.
 */
public class HDBSynonymDropProcessor extends AbstractHDBProcessor<HDBSynonymGroup> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HDBSynonymDropProcessor.class);

    /**
     * Execute : <code>DROP SYNONYM {synonym_name} [{drop_option}]</code> If {drop_option} is not
     * specified, then a non-cascaded drop is performed which only drops the specified synonym.
     * Dependent objects of the synonym are invalidated but not dropped.
     *
     * @param connection the connection
     * @param synonymModel the synonym model
     * @see <a href=
     *      "https://help.sap.com/viewer/4fe29514fd584807ac9f2a04f6754767/1.0.12/en-US/20d7e172751910148bccb49de92d9859.html">DROP
     *      SYNONYM Statement (Data Definition)</a>
     */
    @Override
    public void execute(Connection connection, HDBSynonymGroup synonymModel) throws SQLException {
        for (Map.Entry<String, HDBSynonym> entry : synonymModel.getSynonymDefinitions()
                                                               .entrySet()) {
            if (logger.isInfoEnabled()) {
                logger.info("Processing Drop Synonym: " + entry.getKey());
            }

            String synonymName = null;
            boolean isPublicSynonym = "PUBLIC".equals(entry.getValue()
                                                           .getSchema());

            try {
                if (isPublicSynonym) {
                    synonymName = entry.getKey();
                    try {
                        String sql = SqlFactory.getNative(connection)
                                               .drop()
                                               .publicSynonym(synonymName)
                                               .build();
                        executeSql(sql, connection);
                        if (logger.isInfoEnabled()) {
                            logger.info(String.format("Drop public synonym [%s] successfully", synonymName));
                        }
                    } catch (SQLException e) {
                        if (logger.isWarnEnabled()) {
                            logger.warn(String.format("Public synonym [%s] does not exists during the drop process", synonymName));
                        }
                    }
                } else {
                    synonymName = (entry.getValue()
                                        .getSchema() != null) ? HDBUtils.escapeArtifactName(entry.getKey(), entry.getValue()
                                                                                                                 .getSchema())
                                                : HDBUtils.escapeArtifactName(entry.getKey());
                    if (SqlFactory.getNative(connection)
                                  .exists(connection, entry.getValue()
                                                           .getSchema(),
                                          entry.getKey(), DatabaseArtifactTypes.SYNONYM)) {
                        String sql = SqlFactory.getNative(connection)
                                               .drop()
                                               .synonym(synonymName)
                                               .build();
                        executeSql(sql, connection);
                        if (logger.isInfoEnabled()) {
                            logger.info(String.format("Drop synonym [%s] successfully", synonymName));
                        }
                    } else {
                        if (logger.isWarnEnabled()) {
                            logger.warn(String.format("Synonym [%s] does not exists during the drop process", synonymName));
                        }
                    }
                }
            } catch (SQLException ex) {
                String errorMessage = String.format("Drop synonym [%s] skipped due to an error: [%s]", synonymName, ex.getMessage());
                CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, synonymModel.getLocation(),
                        CommonsConstants.HDB_SYNONYM_PARSER);
                throw new SQLException(errorMessage, ex);
            }
        }
    }
}
