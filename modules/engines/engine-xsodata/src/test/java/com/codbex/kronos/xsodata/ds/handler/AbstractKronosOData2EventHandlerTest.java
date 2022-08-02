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
package com.codbex.kronos.xsodata.ds.handler;

import org.apache.olingo.odata2.api.edm.EdmType;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.eclipse.dirigible.engine.odata2.sql.api.SQLStatementParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class AbstractKronosOData2EventHandlerTest {
  @Mock
  private Connection connection;

  @Mock
  private EdmType edmType;

  private static MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class, Mockito.CALLS_REAL_METHODS);

  private KronosProcedureOData2EventHandler handler = new KronosProcedureOData2EventHandler();

  @Test
  public void testBuildCreateTemporaryTableLikeTableSql() {
    sqlFactory.when(() -> SqlFactory.deriveDialect(any())).thenReturn(new HanaSqlDialect());

    assertEquals("CREATE LOCAL TEMPORARY TABLE #TEST_TEMP_TABLE LIKE TEST_SCHEMA.test-table WITH NO DATA",
        handler.buildCreateTemporaryTableLikeTableSql(connection, ISqlKeywords.METADATA_TABLE, "TEST_SCHEMA",
            "#TEST_TEMP_TABLE", "test-table"));

    assertEquals("CREATE LOCAL TEMPORARY TABLE #TEST_TEMP_TABLE AS (SELECT * FROM TEST_SCHEMA.test-table) WITH NO DATA",
        handler.buildCreateTemporaryTableLikeTableSql(connection, ISqlKeywords.METADATA_CALC_VIEW, "TEST_SCHEMA",
            "#TEST_TEMP_TABLE", "test-table"));
  }

  @Test
  public void testBuildCreateTemporaryTableAsSelect() {
    sqlFactory.when(() -> SqlFactory.deriveDialect(any())).thenReturn(new HanaSqlDialect());

    List<SQLStatementParam> parameters = List.of(new SQLStatementParam("123", edmType, null));
    assertEquals("CREATE LOCAL TEMPORARY TABLE #TEST_TEMP_TABLE AS (SELECT * FROM test-table WHERE ID = '123')",
        handler.buildCreateTemporaryTableAsSelect(connection, "#TEST_TEMP_TABLE",
            "SELECT * FROM test-table WHERE ID = ?",
            parameters));
  }
}
