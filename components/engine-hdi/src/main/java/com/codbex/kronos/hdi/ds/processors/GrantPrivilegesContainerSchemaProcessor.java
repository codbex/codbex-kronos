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
package com.codbex.kronos.hdi.ds.processors;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The Class GrantPrivilegesContainerSchemaProcessor.
 */
public class GrantPrivilegesContainerSchemaProcessor extends HDIAbstractProcessor {

  /**
   * Execute.
   *
   * @param connection the connection
   * @param container the container
   * @param users the users
   * @throws SQLException the SQL exception
   */
  public final void execute(Connection connection, String container, String[] users) throws SQLException {
    executeUpdate(connection, "CREATE LOCAL TEMPORARY COLUMN TABLE #PRIVILEGES LIKE _SYS_DI.TT_SCHEMA_PRIVILEGES;");
    for (String user : users) {
      executeUpdate(connection,
          "INSERT INTO #PRIVILEGES(PRIVILEGE_NAME, PRINCIPAL_SCHEMA_NAME, PRINCIPAL_NAME) VALUES ('SELECT', '', ?);", user);
      executeUpdate(connection,
          "INSERT INTO #PRIVILEGES(PRIVILEGE_NAME, PRINCIPAL_SCHEMA_NAME, PRINCIPAL_NAME) VALUES ('INSERT', '', ?);", user);
      executeUpdate(connection,
          "INSERT INTO #PRIVILEGES(PRIVILEGE_NAME, PRINCIPAL_SCHEMA_NAME, PRINCIPAL_NAME) VALUES ('UPDATE', '', ?);", user);
      executeUpdate(connection,
          "INSERT INTO #PRIVILEGES(PRIVILEGE_NAME, PRINCIPAL_SCHEMA_NAME, PRINCIPAL_NAME) VALUES ('DELETE', '', ?);", user);
      executeUpdate(connection,
          "INSERT INTO #PRIVILEGES(PRIVILEGE_NAME, PRINCIPAL_SCHEMA_NAME, PRINCIPAL_NAME) VALUES ('EXECUTE', '', ?);", user);
       executeUpdate(connection,
          "INSERT INTO #PRIVILEGES(PRIVILEGE_NAME, PRINCIPAL_SCHEMA_NAME, PRINCIPAL_NAME) VALUES ('CREATE ANY', '', ?);", user);
    }
    executeQuery(connection, "CALL " + container + "#DI.GRANT_CONTAINER_SCHEMA_PRIVILEGES(#PRIVILEGES, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?);");
    executeUpdate(connection, "DROP TABLE #PRIVILEGES;");
  }

}
