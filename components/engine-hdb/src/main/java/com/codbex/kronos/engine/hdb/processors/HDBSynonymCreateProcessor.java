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
 * The Class HDBSynonymCreateProcessor.
 */
public class HDBSynonymCreateProcessor extends AbstractHDBProcessor<HDBSynonymGroup> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HDBSynonymCreateProcessor.class);

    /**
     * The Constant DUPLICATE_SYNONYM_NAME_ERROR_CODE.
     */
    private static final int DUPLICATE_SYNONYM_NAME_ERROR_CODE = 330;

    /**
     * Execute : <code>CREATE [PUBLIC] SYNONYM {synonym_name} FOR {synonym_source_object_name}
     * {synonym_name} ::= [{schema_name}.]{identifier}
     * {synonym_source_object_name}:==[{object_schema_name}.]{object_name}</code>
     *
     * @param connection the connection
     * @param synonymModel the synonym model
     * @throws SQLException the SQL exception
     * @see <a href=
     *      "https://help.sap.com/viewer/4fe29514fd584807ac9f2a04f6754767/1.0.12/en-US/20d5412b75191014bc7ec7e133ce5bf5.html">CREATE
     *      SYNONYM Statement (Data Definition)</a>
     */
    @Override
    public void execute(Connection connection, HDBSynonymGroup synonymModel) throws SQLException {
        for (Map.Entry<String, HDBSynonym> entry : synonymModel.getSynonymDefinitions()
                                                               .entrySet()) {
            if (logger.isInfoEnabled()) {
                logger.info("Processing Create Synonym: " + entry.getKey());
            }

            String synonymName = null;
            boolean isPublicSynonym = "PUBLIC".equals(entry.getValue()
                                                           .getSchema());
            String targetObjectName = HDBUtils.escapeArtifactName(entry.getValue()
                                                                       .getTarget()
                                                                       .getObject(),
                    entry.getValue()
                         .getTarget()
                         .getSchema());

            try {
                if (isPublicSynonym) {
                    synonymName = entry.getKey();
                    String sql = SqlFactory.getNative(connection)
                                           .create()
                                           .publicSynonym(synonymName)
                                           .forSource(targetObjectName)
                                           .build();
                    executeSql(sql, connection);
                    if (logger.isInfoEnabled()) {
                        logger.info(String.format("Create public synonym [%s] successfully", synonymName));
                    }
                } else {
                    synonymName = (entry.getValue()
                                        .getSchema() != null) ? (HDBUtils.escapeArtifactName(entry.getKey(),
                                                entry.getValue()
                                                     .getSchema()))
                                                : (HDBUtils.escapeArtifactName(entry.getKey()));
                    String synonymSchema = null != entry.getValue()
                                                        .getSchema() ? entry.getValue()
                                                                            .getSchema()
                                                                : connection.getMetaData()
                                                                            .getUserName();
                    if (!SqlFactory.getNative(connection)
                                   .exists(connection, entry.getValue()
                                                            .getSchema(),
                                           entry.getValue()
                                                .getName(),
                                           DatabaseArtifactTypes.SYNONYM)) {
                        String sql = SqlFactory.getNative(connection)
                                               .create()
                                               .synonym(synonymName)

                                               .forSource(targetObjectName)
                                               .build();
                        executeSql(sql, connection);
                        if (logger.isInfoEnabled()) {
                            logger.info(String.format("Synonym [%s]  in schema [{}] created successfully", synonymName));
                        }
                    } else {
                        if (logger.isWarnEnabled()) {
                            logger.warn(String.format("Synonym [%s] already exists during the create process", synonymName));
                        }
                    }
                }
            } catch (SQLException e) {
                if (e.getErrorCode() == DUPLICATE_SYNONYM_NAME_ERROR_CODE) {
                    logger.info("Synonym [{}] already exists during the create process and will NOT be created", synonymName);
                } else {
                    String errorMessage = String.format("Create synonym [%s] skipped due to an error: [%s]", synonymName, e.getMessage());
                    CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, synonymModel.getLocation(),
                            CommonsConstants.HDB_SYNONYM_PARSER);
                    throw new SQLException(errorMessage, e);
                }
            }
        }
    }
}
