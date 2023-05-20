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
 * The Class GrantPrivilegesContainerAPIProcessor.
 */
public class GrantPrivilegesContainerAPIProcessor extends HDIAbstractProcessor {

  /**
   * Execute.
   *
   * @param connection the connection
   * @param group the group
   * @param container the container
   * @param users the users
   * @throws SQLException the SQL exception
   */
  public final void execute(Connection connection, String group, String container, String[] users) throws SQLException {
    executeUpdate(connection, "CREATE LOCAL TEMPORARY COLUMN TABLE #PRIVILEGES LIKE _SYS_DI.TT_API_PRIVILEGES;");
    for (String user : users) {
      executeUpdate(connection, "INSERT INTO #PRIVILEGES (PRINCIPAL_NAME, PRIVILEGE_NAME, OBJECT_NAME) SELECT ?, PRIVILEGE_NAME, OBJECT_NAME FROM _SYS_DI.T_DEFAULT_CONTAINER_ADMIN_PRIVILEGES; ", user);
    }
    executeQuery(connection,
        "CALL _SYS_DI#" + group + ".GRANT_CONTAINER_API_PRIVILEGES(?, #PRIVILEGES, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?);", container);
    executeUpdate(connection, "DROP TABLE #PRIVILEGES;");
  }

}
