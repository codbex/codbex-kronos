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
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class HDBProcedureDropProcessor.
 */
public class HDBProcedureDropProcessor extends AbstractHDBProcessor<HDBProcedure> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(HDBProcedureDropProcessor.class);

    private static final Pattern SCHEMA_PATTERN = Pattern.compile(".*PROCEDURE \"([^\"]+)\"\\.\"([^\"]+)\".*", Pattern.CASE_INSENSITIVE);

    /**
     * Execute.
     *
     * @param connection the connection
     * @param procedureModel the hdb procedure
     * @throws SQLException the SQL exception
     */
    @Override
    public void execute(Connection connection, HDBProcedure procedureModel) throws SQLException {
        logger.info("Processing Drop Procedure: " + procedureModel.getName());
        String procedureNameWithoutSchema = CommonsUtils.extractArtifactNameWhenSchemaIsProvided(procedureModel.getName())[1];
        procedureModel.setSchema(extractSchema(procedureModel.getContent()).orElse(null));

        if (!SqlFactory.getNative(connection)
                       .exists(connection, procedureModel.getSchema(), procedureNameWithoutSchema, DatabaseArtifactTypes.PROCEDURE)) {
            logger.warn("Procedure [{}}] in schema [{}] does not exists during the drop process", procedureModel.getName(),
                    procedureModel.getSchema());
            return;
        }
        ISqlDialect dialect = SqlFactory.deriveDialect(connection);
        if (!(dialect.getClass()
                     .equals(HanaSqlDialect.class))) {
            String errorMessage = String.format("Procedures are not supported for %s", dialect.getDatabaseName(connection));
            CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, procedureModel.getLocation(),
                    CommonsConstants.HDB_PROCEDURE_PARSER);
            throw new IllegalStateException(errorMessage);
        }
        String sql = null == procedureModel.getSchema()
                ? (Constants.HDBPROCEDURE_DROP + dialect.getEscapeSymbol() + procedureModel.getName() + dialect.getEscapeSymbol())
                : (Constants.HDBPROCEDURE_DROP + dialect.getEscapeSymbol() + procedureModel.getSchema() + dialect.getEscapeSymbol() + "."
                        + dialect.getEscapeSymbol() + procedureModel.getName() + dialect.getEscapeSymbol());
        try {
            executeSql(sql, connection);
            logger.info("Drop procedure [{}] in schema [{}] successfully", procedureModel.getName(), procedureModel.getSchema());
        } catch (SQLException ex) {
            String message = String.format("Drop procedure[%s] skipped due to an error: %s", procedureModel, ex.getMessage());
            CommonsUtils.logProcessorErrors(message, CommonsConstants.PROCESSOR_ERROR, procedureModel.getLocation(),
                    CommonsConstants.HDB_PROCEDURE_PARSER);
            throw ex;
        }
    }

    private Optional<String> extractSchema(String content) {
        Matcher matcher = SCHEMA_PATTERN.matcher(content);
        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }

    public static void main(String[] args) {
        String str = "PROCEDURE \"AF477A06D\".\"com.apotest.jpk.backend.customizing.procedures::updateP_13_3\" (\n" + "    \n"
                + "    IN new_ \"com.apotest.jpk.backend.customizing.model::custTables.P_13_3_tab\",\n"
                + "    IN old_ \"com.apotest.jpk.backend.customizing.model::custTables.P_13_3_tab\",\n"
                + "    OUT error \"com.apotest.jpk.backend.customizing.model::custTypes.tt_error\"\n" + "    \n" + ")\n"
                + "   LANGUAGE SQLSCRIPT\n" + "   SQL SECURITY INVOKER\n" + "   DEFAULT SCHEMA AF477A06D\n" + "   AS\n" + "BEGIN\n"
                + "\tdeclare lv_P_13_3 string;\n" + "\tdeclare lv_idxInvoiceConf Integer;\n" + "\tdeclare lv_idx Integer;\n" + "\t\n"
                + "\t select \"idx\", \"idxInvoiceConf\", \"taxCode\"\n" + "        into lv_idx, lv_idxInvoiceConf, lv_P_13_3\n"
                + "        from :new_;\n" + "        \n" + "\n" + "        \n" + "    if lv_P_13_3 != '' \n"
                + "    and lv_idxInvoiceConf != '' \n" + "    and lv_idx != '' \n" + "    then    \n" + "        \n"
                + "        update \"com.apotest.jpk.backend.customizing.model::custTables.P_13_3_tab\"\n"
                + "            set \"idxInvoiceConf\" = :lv_idxInvoiceConf, \"taxCode\" = :lv_P_13_3\n"
                + "            where \"idx\" = :lv_idx;\n" + "\n" + "    else\n" + "    \n"
                + "        error = SELECT 400 as HTTP_STATUS_CODE,  \n" + "          'MANDT invalid' as ERROR_MESSAGE,  \n"
                + "          'MANDT cannot be empty' as DETAIL FROM dummy;  \n" + "    \n" + "    end if;\n" + "    \n" + "END ";

        System.out.println(CommonsUtils.extractArtifactNameWhenSchemaIsProvided(str)[0]);
    }
}
