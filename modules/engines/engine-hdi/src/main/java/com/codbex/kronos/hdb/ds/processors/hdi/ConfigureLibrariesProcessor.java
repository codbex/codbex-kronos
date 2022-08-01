/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.hdb.ds.processors.hdi;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class ConfigureLibrariesProcessor extends HDIAbstractProcessor {

  public final void execute(Connection connection, String container, Set<String> pluginsToBeActivated) throws SQLException {
    executeQuery(connection,
        "CALL " + container + "#DI.CONFIGURE_LIBRARIES(_SYS_DI.T_DEFAULT_LIBRARIES, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?);");

    if (!pluginsToBeActivated.isEmpty()) {
      executeUpdate(connection, "CREATE LOCAL TEMPORARY COLUMN TABLE #LIBRARY_CONFIGURATION LIKE _SYS_DI.TT_LIBRARY_CONFIGURATION;");

      for (String plugin : pluginsToBeActivated) {
        executeUpdate(connection, "INSERT INTO #LIBRARY_CONFIGURATION(ACTION, LIBRARY_NAME) VALUES('ADD', '" + plugin + "');");
      }

      executeUpdate(connection, "CALL " + container + "#DI.CONFIGURE_LIBRARIES(#LIBRARY_CONFIGURATION, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?);");
      executeUpdate(connection, "DROP TABLE #LIBRARY_CONFIGURATION;");
    }
  }

}
