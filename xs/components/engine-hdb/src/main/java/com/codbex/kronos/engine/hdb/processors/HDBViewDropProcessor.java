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

import com.codbex.kronos.engine.hdb.domain.HDBView;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import java.sql.Connection;
import java.sql.SQLException;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The HDBViewDropProcessor.
 */
public class HDBViewDropProcessor extends AbstractHDBProcessor<HDBView> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HDBViewDropProcessor.class);

    /**
     * Execute the corresponding statement.
     *
     * @param connection the connection
     * @param viewModel the view model
     * @throws SQLException the SQL exception
     */
    @Override
    public void execute(Connection connection, HDBView viewModel) throws SQLException {
        logger.info("Processing Drop View: [{}]", viewModel.getName());

        // Drop public synonym
        HDBUtils.dropPublicSynonymForArtifact(viewModel.getName(), viewModel.getSchema(), connection);

        // Drop view
        if (SqlFactory.getNative(connection)
                      .exists(connection, viewModel.getSchema(), viewModel.getName(), DatabaseArtifactTypes.VIEW)) {
            String viewNameWithSchema = HDBUtils.escapeArtifactName(viewModel.getName(), viewModel.getSchema());
            String sql = SqlFactory.getNative(connection)
                                   .drop()
                                   .view(viewNameWithSchema)
                                   .build();
            try {
                executeSql(sql, connection);
                String message = String.format("Drop view [%s] successfully", viewModel.getName());
                logger.info(message);
            } catch (SQLException ex) {
                String errorMessage = String.format("Drop view [%s] skipped due to an error: [%s]. Used sql: [%s]", viewModel.getName(),
                        ex.getMessage(), sql);
                CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, viewModel.getLocation(),
                        CommonsConstants.HDB_VIEW_PARSER);
                throw new SQLException(errorMessage, ex);
            }
        } else {
            String warningMessage = String.format("View [%s] does not exist during the drop process", viewModel.getName());
            logger.warn(warningMessage);
        }
    }

}
