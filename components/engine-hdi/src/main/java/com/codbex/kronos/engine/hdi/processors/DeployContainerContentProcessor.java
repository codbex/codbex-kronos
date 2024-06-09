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
package com.codbex.kronos.engine.hdi.processors;

import com.codbex.kronos.engine.hdi.ds.util.Constants;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class DeployContainerContentProcessor.
 */
public class DeployContainerContentProcessor extends HDIAbstractProcessor {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DeployContainerContentProcessor.class);

    /**
     * The Constant ERROR_LOCATION.
     */
    private static final String ERROR_LOCATION = "-";

    /**
     * The Constant SQL_SELECT_FROM_DEPLOY_PATHS.
     */
    private static final String SQL_SELECT_FROM_DEPLOY_PATHS = "SELECT * FROM #DEPLOY_PATHS";

    /**
     * The Constant SQL_SELECT_FROM_UNDEPLOY_PATHS.
     */
    private static final String SQL_SELECT_FROM_UNDEPLOY_PATHS = "SELECT * FROM #UNDEPLOY_PATHS";

    /**
     * The Constant FIRST_OUTPUT_PARAMETER_INDEX.
     */
    private static final int FIRST_OUTPUT_PARAMETER_INDEX = 1;

    // /** The Constant CALCULATION_VIEW_SYNCHRONIZATION_ARTEFACT_TYPE. */
    // private static final CalculationViewSynchronizationArtefactType
    // CALCULATION_VIEW_SYNCHRONIZATION_ARTEFACT_TYPE = new
    // CalculationViewSynchronizationArtefactType();
    //
    // /** The Constant TABLE_SYNCHRONIZATION_ARTEFACT_TYPE. */
    // private static final HDBTableSynchronizationArtefactType TABLE_SYNCHRONIZATION_ARTEFACT_TYPE =
    // new HDBTableSynchronizationArtefactType();
    //
    // /** The Constant PROCEDURE_SYNCHRONIZATION_ARTEFACT_TYPE. */
    // private static final HDBProcedureSynchronizationArtefactType
    // PROCEDURE_SYNCHRONIZATION_ARTEFACT_TYPE = new HDBProcedureSynchronizationArtefactType();
    //
    // /** The Constant SCHEMA_SYNCHRONIZATION_ARTEFACT_TYPE. */
    // private static final HDBSchemaSynchronizationArtefactType SCHEMA_SYNCHRONIZATION_ARTEFACT_TYPE =
    // new HDBSchemaSynchronizationArtefactType();
    //
    // /** The Constant SEQUENCE_SYNCHRONIZATION_ARTEFACT_TYPE. */
    // private static final HDBSequenceSynchronizationArtefactType
    // SEQUENCE_SYNCHRONIZATION_ARTEFACT_TYPE = new HDBSequenceSynchronizationArtefactType();
    //
    // /** The Constant SYNONYM_SYNCHRONIZATION_ARTEFACT_TYPE. */
    // private static final HDBSynonymSynchronizationArtefactType SYNONYM_SYNCHRONIZATION_ARTEFACT_TYPE
    // = new HDBSynonymSynchronizationArtefactType();
    //
    // /** The Constant TABLE_FUNCTION_SYNCHRONIZATION_ARTEFACT_TYPE. */
    // private static final HDBTableFunctionSynchronizationArtefactType
    // TABLE_FUNCTION_SYNCHRONIZATION_ARTEFACT_TYPE = new HDBTableFunctionSynchronizationArtefactType();
    //
    // /** The Constant TABLE_TYPE_SYNCHRONIZATION_ARTEFACT_TYPE. */
    // private static final HDBTableTypeSynchronizationArtefactType
    // TABLE_TYPE_SYNCHRONIZATION_ARTEFACT_TYPE = new HDBTableTypeSynchronizationArtefactType();
    //
    // /** The Constant VIEW_SYNCHRONIZATION_ARTEFACT_TYPE. */
    // private static final HDBViewSynchronizationArtefactType VIEW_SYNCHRONIZATION_ARTEFACT_TYPE = new
    // HDBViewSynchronizationArtefactType();

    /**
     * Execute.
     *
     * @param connection the connection
     * @param container the container
     * @param deployPaths the deploy paths
     * @param undeployPaths the undeploy paths
     * @throws SQLException the SQL exception
     */
    public final void execute(Connection connection, String container, String[] deployPaths, String[] undeployPaths) throws SQLException {
        executeUpdate(connection, "CREATE LOCAL TEMPORARY COLUMN TABLE #DEPLOY_PATHS LIKE _SYS_DI.TT_FILESFOLDERS;");
        executeUpdate(connection, "INSERT INTO #DEPLOY_PATHS (PATH) VALUES ('.hdiconfig');");
        for (String next : deployPaths) {
            executeUpdate(connection, "INSERT INTO #DEPLOY_PATHS (PATH) VALUES (?);", next.substring(1));
        }
        executeUpdate(connection, "CREATE LOCAL TEMPORARY COLUMN TABLE #UNDEPLOY_PATHS LIKE _SYS_DI.TT_FILESFOLDERS;");
        for (String next : undeployPaths) {
            executeUpdate(connection, "INSERT INTO #UNDEPLOY_PATHS (PATH) VALUES (?);", next.substring(1));
        }
        executeUpdate(connection, "CREATE LOCAL TEMPORARY COLUMN TABLE #PATH_PARAMETERS LIKE _SYS_DI.TT_FILESFOLDERS_PARAMETERS;");

        executeCall(connection,
                "CALL " + container + "#DI.MAKE(#DEPLOY_PATHS, #UNDEPLOY_PATHS, #PATH_PARAMETERS, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?);");

        executeUpdate(connection, "DROP TABLE #DEPLOY_PATHS;");
        executeUpdate(connection, "DROP TABLE #UNDEPLOY_PATHS;");
        executeUpdate(connection, "DROP TABLE #PATH_PARAMETERS;");
    }

    /**
     * Execute call.
     *
     * @param connection the connection
     * @param sql the sql
     */
    public void executeCall(Connection connection, String sql) {
        LOGGER.info("Executing [{}]", sql);
        try (CallableStatement statement = connection.prepareCall(sql)) {
            statement.registerOutParameter(FIRST_OUTPUT_PARAMETER_INDEX, Types.INTEGER);

            try (ResultSet resultSet = statement.executeQuery()) {
                int returnCode = statement.getInt(FIRST_OUTPUT_PARAMETER_INDEX); // 1st output parameter (Return_Code)
                parseResultSet(resultSet);
                // checkPaths(connection, returnCode, SQL_SELECT_FROM_DEPLOY_PATHS);
                // checkPaths(connection,returnCode, SQL_SELECT_FROM_UNDEPLOY_PATHS);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to execute SQL statement - " + sql, e);
            CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.PROCESSOR_ERROR, ERROR_LOCATION,
                    CommonsConstants.HDI_PROCESSOR);
        }
    }

    // /**
    // * Check paths.
    // *
    // * @param connection the connection
    // * @param returnCode the return code
    // * @param sql the sql
    // * @throws SQLException the SQL exception
    // */
    // private void checkPaths(Connection connection, int returnCode, String sql) throws SQLException {
    // try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
    // try (ResultSet rs = prepareStatement.executeQuery()) {
    // while (rs.next()) {
    // String path = rs.getString(1);
    // if (path.indexOf(Constants.UNIX_SEPARATOR) != -1) {
    // String fileExtension = getExtension(path);
    // String fileName = path.substring(path.lastIndexOf(Constants.UNIX_SEPARATOR) + 1);
    // if (returnCode == RETURN_CODE_SUCCESS || returnCode == RETURN_CODE_WARNING) {
    // if(sql.equals(SQL_SELECT_FROM_DEPLOY_PATHS)) {
    // applyState(fileExtension, fileName, ArtefactState.SUCCESSFUL_CREATE, path);
    // } else {
    // applyState(fileExtension, fileName, ArtefactState.SUCCESSFUL_DELETE, path);
    // }
    // } else {
    // if(sql.equals(SQL_SELECT_FROM_DEPLOY_PATHS)) {
    // applyState(fileExtension, fileName, ArtefactState.FAILED_CREATE, path);
    // } else {
    // applyState(fileExtension, fileName, ArtefactState.FAILED_DELETE, path);
    // }
    // }
    // }
    // }
    // }
    // }
    // }

    // /**
    // * Apply state.
    // *
    // * @param fileExtension the file extension
    // * @param fileName the file name
    // * @param artefactState the artefact state
    // * @param location the location
    // */
    // protected void applyState(String fileExtension, String fileName, ArtefactState artefactState,
    // String location) {
    // switch (fileExtension) {
    // case Constants.CALCULATION_VIEW_EXTENSION:
    // applyArtefactState(fileName, location,
    // CALCULATION_VIEW_SYNCHRONIZATION_ARTEFACT_TYPE, artefactState, "");
    // break;
    // case Constants.TABLE_EXTENSION:
    // applyArtefactState(fileName, location,
    // TABLE_SYNCHRONIZATION_ARTEFACT_TYPE, artefactState, "");
    // break;
    // case Constants.SCHEMA_EXTENSION:
    // applyArtefactState(fileName, location,
    // SCHEMA_SYNCHRONIZATION_ARTEFACT_TYPE, artefactState, "");
    // break;
    // case Constants.PROCEDURE_EXTENSION:
    // applyArtefactState(fileName, location,
    // PROCEDURE_SYNCHRONIZATION_ARTEFACT_TYPE, artefactState, "");
    // break;
    // case Constants.SYNONYM_EXTENSION:
    // applyArtefactState(fileName, location,
    // SYNONYM_SYNCHRONIZATION_ARTEFACT_TYPE, artefactState, "");
    // break;
    // case Constants.SEQUENCE_EXTENSION:
    // applyArtefactState(fileName, location,
    // SEQUENCE_SYNCHRONIZATION_ARTEFACT_TYPE, artefactState, "");
    // break;
    // case Constants.TABLE_FUNCTION_EXTENSION:
    // applyArtefactState(fileName, location,
    // TABLE_FUNCTION_SYNCHRONIZATION_ARTEFACT_TYPE, artefactState, "");
    // break;
    // case Constants.TABLE_TYPE_EXTENSION:
    // applyArtefactState(fileName, location,
    // TABLE_TYPE_SYNCHRONIZATION_ARTEFACT_TYPE, artefactState, "");
    // break;
    // case Constants.VIEW_EXTENSIONS:
    // applyArtefactState(fileName, location,
    // VIEW_SYNCHRONIZATION_ARTEFACT_TYPE, artefactState, "");
    // break;
    // default:
    // break;
    // }
    // }

    /**
     * Gets the extension.
     *
     * @param filename the filename
     * @return the extension
     */
    private String getExtension(String filename) {
        String extension = "";
        if (filename.indexOf(Constants.DOT) != -1) {
            int indexOfLastDot = filename.lastIndexOf(Constants.DOT);
            int indexOfLastSeparator = filename.lastIndexOf(Constants.UNIX_SEPARATOR);
            if (indexOfLastDot > indexOfLastSeparator) {
                extension = filename.substring(indexOfLastDot + 1);
            }
        }
        return extension;
    }

}
