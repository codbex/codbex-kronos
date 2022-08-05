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
package com.codbex.kronos.synchronizer;

import com.codbex.kronos.XSJSTest;
import com.codbex.kronos.engine.JavascriptEngineExecutor;
import com.codbex.kronos.exceptions.XSJSLibSynchronizerDBCleanerSQLException;
import com.codbex.kronos.synchronizer.XSJSLibSynchronizer;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.eclipse.dirigible.commons.api.scripting.ScriptingException;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.engine.js.graalvm.processor.GraalVMJavascriptEngineExecutor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(JUnitParamsRunner.class)
public class XSJSLibSynchronizerTest extends XSJSTest {

  @Before
  public void beforeTest() {
    cleanup();
  }

  @After
  public void afterTest() {
    cleanup();
  }

  private void cleanup() {
    dropTableIfExists(XSJSLibSynchronizer.XSJSLIB_SYNCHRONIZER_STATE_TABLE_NAME);
    dropTableIfExists("XSJSLIB_EXPORT_TEST_TABLE");
  }

  private void dropTableIfExists(String tableName) {
    DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);
    try (Connection connection = dataSource.getConnection();
        PreparedStatement dropStatement = connection.prepareStatement(
            "DROP TABLE \"" + tableName + "\""
        )
    ) {
      if (tableExists(tableName, connection)) {
        dropStatement.executeUpdate();
      }
    } catch (SQLException e) {
      throw new XSJSLibSynchronizerDBCleanerSQLException("Could not drop table after test", e);
    }
  }

  private boolean tableExists(String tableName, Connection connection) throws SQLException {
    ResultSet resultSet = connection.getMetaData().getTables(
        null,
        null,
        tableName,
        null
    );
    return resultSet.next();
  }

  @Test
  public void synchronizerGetPriorityTest() {
    XSJSLibSynchronizer synchronizer = new XSJSLibSynchronizer();
    assertEquals("Unexpected XSJSLibSynchronizer Priority",
        666, synchronizer.getPriority());
  }

  @Test
  public void synchronizeTest() throws ScriptingException {
    XSJSLibSynchronizer.forceSynchronization("../../test/kronos/import/"); // look two directories back as the test resources are outside the repository root

    Map<Object, Object> context = new HashMap<>();
    JavascriptEngineExecutor javascriptEngineExecutor = new JavascriptEngineExecutor();
    Object result = javascriptEngineExecutor.executeServiceModule(
        "/test/kronos/import/import.xsjs",
        context
    );

    assertNull("Unexpected xsjs execution result for import.xsjs", result);
  }

  @Test
  @Parameters({
      "/test/kronos/exports/tests/XSJSLibStateTableWriteTest.mjs",
      "/test/kronos/exports/tests/XSJSLibStateTableUpdateTest.mjs",
      "/test/kronos/exports/tests/XSJSLibStateTableFindTest.mjs",
      "/test/kronos/exports/tests/XSJSLibExportsGeneratorIsContentChangedTest.mjs",
      "/test/kronos/exports/tests/XSJSLibCompilerTest.mjs",
      "/test/kronos/exports/tests/XSJSLibExportGeneratorSingleFileTest.mjs",
      "/test/kronos/exports/tests/XSJSLibExportGeneratorSingleFileModifyTest.mjs",
      "/test/kronos/exports/tests/XSJSLibExportGeneratorMultiFileTest.mjs",
  })
  public void exportsGenerationJsTest(String testModule) throws ScriptingException {
    runJsTest(testModule);
  }

  private void runJsTest(String testModule) {
    Object executionResult = runJs(testModule);
    assertNull(
        "XSJSLib Export js test unexpected js execution result for " + testModule,
        executionResult
    );
  }

  private Object runJs(String testModule) throws ScriptingException {
    Map<Object, Object> context = new HashMap<>();
    GraalVMJavascriptEngineExecutor graalVMJavascriptEngineExecutor = new GraalVMJavascriptEngineExecutor();
    return graalVMJavascriptEngineExecutor.executeService(
        testModule,
        context,
        true,
        false
    );
  }
}
