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
package com.codbex.kronos.hdi.ds.processors;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.codbex.kronos.engine.hdi.processors.ConfigureLibrariesProcessor;

/**
 * The Class ConfigureLibrariesProcessorTest.
 */
public class ConfigureLibrariesProcessorTest {

    /** The Constant CONFIGURE_DEFAULT_LIBRARIES_SQL_PATTERN. */
    private static final String CONFIGURE_DEFAULT_LIBRARIES_SQL_PATTERN =
            "CALL %s#DI.CONFIGURE_LIBRARIES(_SYS_DI.T_DEFAULT_LIBRARIES, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?);";

    /** The Constant CREATE_LOCAL_TEMP_TABLE_SQL. */
    private static final String CREATE_LOCAL_TEMP_TABLE_SQL =
            "CREATE LOCAL TEMPORARY COLUMN TABLE #LIBRARY_CONFIGURATION LIKE _SYS_DI.TT_LIBRARY_CONFIGURATION;";

    /** The Constant ADD_PLUGIN_SQL_PATTERN. */
    private static final String ADD_PLUGIN_SQL_PATTERN = "INSERT INTO #LIBRARY_CONFIGURATION(ACTION, LIBRARY_NAME) VALUES('ADD', '%s');";

    /** The Constant ACTIVATE_PLUGINS_SQL_PATTERN. */
    private static final String ACTIVATE_PLUGINS_SQL_PATTERN =
            "CALL %s#DI.CONFIGURE_LIBRARIES(#LIBRARY_CONFIGURATION, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?);";

    /** The Constant DROP_TEMP_TABLE. */
    private static final String DROP_TEMP_TABLE = "DROP TABLE #LIBRARY_CONFIGURATION;";

    /**
     * Test execute with plugin activation with one plugin.
     *
     * @throws SQLException the SQL exception
     */
    @Test
    public void testExecuteWithPluginActivationWithOnePlugin() throws SQLException {
        testExecute(new HashSet<>(List.of("test.plugin")));
    }

    /**
     * Test execute with with no plugin activation.
     *
     * @throws SQLException the SQL exception
     */
    @Test
    public void testExecuteWithWithNoPluginActivation() throws SQLException {
        testExecute(new HashSet<>());
    }

    /**
     * Test execute with plugin activation with more plugins.
     *
     * @throws SQLException the SQL exception
     */
    @Test
    public void testExecuteWithPluginActivationWithMorePlugins() throws SQLException {
        testExecute(new HashSet<>(List.of("test.plugin", "test.plugin2", "test.plugin3")));
    }

    /**
     * Test execute.
     *
     * @param plugginsToBeActivated the pluggins to be activated
     * @throws SQLException the SQL exception
     */
    private void testExecute(Set<String> plugginsToBeActivated) throws SQLException {
        ConfigureLibrariesProcessor processor = Mockito.spy(ConfigureLibrariesProcessor.class);
        Connection connection = Mockito.mock(Connection.class);
        String container = "Container";

        String activateDefaultLibrariesSQL = String.format(CONFIGURE_DEFAULT_LIBRARIES_SQL_PATTERN, container);
        Mockito.doNothing()
               .when(processor)
               .executeQuery(connection, activateDefaultLibrariesSQL);

        if (!plugginsToBeActivated.isEmpty()) {
            Mockito.doNothing()
                   .when(processor)
                   .executeUpdate(connection, CREATE_LOCAL_TEMP_TABLE_SQL);
            for (String plugin : plugginsToBeActivated) {
                Mockito.doNothing()
                       .when(processor)
                       .executeUpdate(connection, String.format(ADD_PLUGIN_SQL_PATTERN, plugin));
            }

            Mockito.doNothing()
                   .when(processor)
                   .executeUpdate(connection, String.format(ACTIVATE_PLUGINS_SQL_PATTERN, container));
            Mockito.doNothing()
                   .when(processor)
                   .executeUpdate(connection, DROP_TEMP_TABLE);
        }

        processor.execute(connection, container, plugginsToBeActivated);

        Mockito.verify(processor)
               .executeQuery(connection, activateDefaultLibrariesSQL);
        if (!plugginsToBeActivated.isEmpty()) {
            Mockito.verify(processor)
                   .executeUpdate(connection, CREATE_LOCAL_TEMP_TABLE_SQL);
            for (String plugin : plugginsToBeActivated) {
                Mockito.verify(processor)
                       .executeUpdate(connection, String.format(ADD_PLUGIN_SQL_PATTERN, plugin));
            }

            Mockito.verify(processor)
                   .executeUpdate(connection, String.format(ACTIVATE_PLUGINS_SQL_PATTERN, container));
            Mockito.verify(processor)
                   .executeUpdate(connection, DROP_TEMP_TABLE);
        }
    }
}
