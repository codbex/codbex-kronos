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

import com.codbex.kronos.xsodata.ds.service.TableMetadataProvider;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.eclipse.dirigible.core.test.AbstractDirigibleTest;
import org.eclipse.dirigible.database.persistence.model.PersistenceTableModel;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.eclipse.dirigible.engine.odata2.sql.api.SQLStatement;
import org.eclipse.dirigible.engine.odata2.sql.api.SQLStatementParam;
import org.eclipse.dirigible.engine.odata2.sql.binding.EdmTableBindingProvider;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLContext;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLDeleteBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLInsertBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLUpdateBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.eclipse.dirigible.engine.odata2.sql.builder.SQLContext.DatabaseProduct.HANA;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

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

  @Test
  public void testGetSQLInsertBuilderTargetTable() throws ODataException {
    SQLContext sqlContext = new SQLContext(HANA);
    SQLInsertBuilder insertBuilder = Mockito.mock(SQLInsertBuilder.class, Mockito.CALLS_REAL_METHODS);
    SQLStatement sqlStatement = Mockito.mock(SQLStatement.class);
    Mockito.doReturn(sqlStatement).when(insertBuilder).build(sqlContext);
    Mockito.doReturn("test-table").when(insertBuilder).getTargetTableName();
    AbstractKronosOData2EventHandler abstractKronosOData2EventHandler = Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
    String targetTableName = abstractKronosOData2EventHandler.getSQLInsertBuilderTargetTable(insertBuilder, sqlContext);
    assertEquals("test-table", targetTableName);
  }

  @Test
  public void testGetSQLUpdateBuilderTargetTable() throws ODataException {
    SQLContext sqlContext = new SQLContext(HANA);
    SQLUpdateBuilder updateBuilder = Mockito.mock(SQLUpdateBuilder.class, Mockito.CALLS_REAL_METHODS);
    SQLStatement sqlStatement = Mockito.mock(SQLStatement.class);
    Mockito.doReturn(sqlStatement).when(updateBuilder).build(sqlContext);
    Mockito.doReturn("test-table").when(updateBuilder).getTargetTableName();
    AbstractKronosOData2EventHandler abstractKronosOData2EventHandler = Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
    String targetTableName = abstractKronosOData2EventHandler.getSQLUpdateBuilderTargetTable(updateBuilder, sqlContext);
    assertEquals("test-table", targetTableName);
  }
  @Test
  public void testGetSQLDeleteBuilderTargetTable() throws ODataException {
    SQLContext sqlContext = new SQLContext(HANA);
    SQLDeleteBuilder deleteBuilder = Mockito.mock(SQLDeleteBuilder.class, Mockito.CALLS_REAL_METHODS);
    SQLStatement sqlStatement = Mockito.mock(SQLStatement.class);
    Mockito.doReturn(sqlStatement).when(deleteBuilder).build(sqlContext);
    Mockito.doReturn("test-table").when(deleteBuilder).getTargetTableName();
    AbstractKronosOData2EventHandler abstractKronosOData2EventHandler = Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
    String targetTableName = abstractKronosOData2EventHandler.getSQLDeleteBuilderTargetTable(deleteBuilder, sqlContext);
    assertEquals("test-table", targetTableName);
  }

  @Test
  public void testConvertResultSetMap() throws SQLException {
    ResultSet resultSet = Mockito.mock(ResultSet.class);
    ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
    doReturn(resultSetMetaData).when(resultSet).getMetaData();
    doReturn(1).when(resultSetMetaData).getColumnCount();
    AbstractKronosOData2EventHandler abstractKronosOData2EventHandler = Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
    Map<String, Object> convertedRSMap = abstractKronosOData2EventHandler.convertResultSetMap(resultSet);
    assertEquals(1, convertedRSMap.size());
  }

  @Test
  public void testExecuteSQLStatement() throws SQLException, ODataException {
    SQLStatement sqlStatement = Mockito.mock(SQLStatement.class);
    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
    doReturn(preparedStatement).when(connection).prepareStatement(any());
    doReturn(0).when(preparedStatement).executeUpdate();
    AbstractKronosOData2EventHandler abstractKronosOData2EventHandler = Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
    abstractKronosOData2EventHandler.executeSQLStatement(connection, sqlStatement);
  }

}
