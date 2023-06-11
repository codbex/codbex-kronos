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

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.codbex.kronos.engine.hdi.processors.GrantPrivilegesDefaultRoleProcessor;

@RunWith(MockitoJUnitRunner.class)
public class GrantPrivilegesDefaultRoleProcessorTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private Connection mockConnection;

  private String hdbRolePath = "/package/kronos_technical_privileges.hdbrole";
  private String user = "HANA_USER";
  private String container = "testContainer";

  @Test
  public void testDeployedRole() throws SQLException {
    String[] deploys = { hdbRolePath };
    testGrantPrivilege(deploys, user, container);
  }

  @Test
  public void testNoDeployedRole() throws SQLException {
    String[] deploys = {};
    testGrantPrivilege(deploys, user, container);
  }

  @Test
  public void testNoHanaUserProvided() {
    Exception exception = assertThrows(IllegalStateException.class, () -> {
      String[] deploys = {};
      testGrantPrivilege(deploys, null, container);
    });

    String expectedMessage = "kronos_technical_privileges.hdbrole assignment failed. No user provided.";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }

  private void testGrantPrivilege(String[] deploys, String user, String container) throws SQLException {
    int expectedInvocations = Arrays.asList(deploys).contains(hdbRolePath) ? 1 : 0;

    GrantPrivilegesDefaultRoleProcessor processorSpy = spy(GrantPrivilegesDefaultRoleProcessor.class);;

    processorSpy.execute(mockConnection, container, user, deploys);

    String mockSQLCreate = "CREATE LOCAL TEMPORARY COLUMN TABLE #ROLES LIKE _SYS_DI.TT_SCHEMA_ROLES;";
    String mockSQLInsert = "INSERT INTO #ROLES ( ROLE_NAME, PRINCIPAL_SCHEMA_NAME, PRINCIPAL_NAME ) VALUES ( 'kronos_technical_privileges', '', \'" + user + "\' );";
    String mockSQLCall = "CALL " + container +"#DI.GRANT_CONTAINER_SCHEMA_ROLES(#ROLES, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?);";
    String mockSQLDrop = "DROP TABLE #ROLES;";

    verify(processorSpy, times(expectedInvocations)).executeUpdate(mockConnection, mockSQLCreate, new String[]{});
    verify(processorSpy, times(expectedInvocations)).executeUpdate(mockConnection, mockSQLInsert, new String[]{});
    verify(processorSpy, times(expectedInvocations)).executeQuery(mockConnection, mockSQLCall, new String[]{});
    verify(processorSpy, times(expectedInvocations)).executeUpdate(mockConnection, mockSQLDrop, new String[]{});

  }

}
