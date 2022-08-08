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
import java.util.Arrays;

import com.codbex.kronos.hdb.ds.exceptions.GrantPrivilegesSQLException;

/**
 * The Class GrantPrivilegesDefaultRoleProcessor.
 */
public class GrantPrivilegesDefaultRoleProcessor extends HDIAbstractProcessor {

  /**
   * Execute.
   *
   * @param connection the connection
   * @param container the container
   * @param user the user
   * @param deployPaths the deploy paths
   */
  public void execute(Connection connection, String container, String user, String[] deployPaths) {
    if (user == null) {
      throw new IllegalStateException("kronos_technical_privileges.hdbrole assignment failed. No user provided.");
    }

    Arrays.stream(deployPaths).filter(deployPath -> deployPath.endsWith("kronos_technical_privileges.hdbrole")).forEach(filePath -> {
      try {
        grantPrivileges(connection, container, user);
      } catch (SQLException exception) {
        throw new GrantPrivilegesSQLException("Failed to grant kronos_technical_privileges.hdbrole to user " + user, exception);
      }
    });

  }

  /**
   * Grant privileges.
   *
   * @param connection the connection
   * @param container the container
   * @param user the user
   * @throws SQLException the SQL exception
   */
  private void grantPrivileges(Connection connection, String container, String user) throws SQLException {
    executeUpdate(connection, "CREATE LOCAL TEMPORARY COLUMN TABLE #ROLES LIKE _SYS_DI.TT_SCHEMA_ROLES;");

    executeUpdate(connection, "INSERT INTO #ROLES ( ROLE_NAME, PRINCIPAL_SCHEMA_NAME, PRINCIPAL_NAME ) VALUES ( 'kronos_technical_privileges', '', \'" + user + "\' );");

    executeQuery(connection, "CALL " + container + "#DI.GRANT_CONTAINER_SCHEMA_ROLES(#ROLES, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?);");
    executeUpdate(connection, "DROP TABLE #ROLES;");

  }

}
