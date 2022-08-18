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
import org.apache.olingo.odata2.api.exception.ODataException;
import org.eclipse.dirigible.core.test.AbstractDirigibleTest;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.eclipse.dirigible.engine.odata2.sql.api.SQLStatement;
import org.eclipse.dirigible.engine.odata2.sql.api.SQLStatementParam;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLContext;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLDeleteBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLInsertBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLSelectBuilder;
import org.eclipse.dirigible.engine.odata2.sql.builder.SQLUpdateBuilder;
import org.h2.tools.Csv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.eclipse.dirigible.engine.odata2.sql.builder.SQLContext.DatabaseProduct.HANA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class AbstractKronosOData2EventHandlerTest extends AbstractDirigibleTest {
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
  public void testCreateTemporaryTableAsSelect() throws ODataException, SQLException {
    SQLContext sqlContext = new SQLContext(HANA);
    SQLSelectBuilder selectBuilder = Mockito.mock(SQLSelectBuilder.class, Mockito.CALLS_REAL_METHODS);
    Mockito.doReturn("").when(selectBuilder).buildSelect(sqlContext);
    Mockito.doReturn(Arrays.asList()).when(selectBuilder).getStatementParams();
    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
    doReturn(preparedStatement).when(connection).prepareStatement(any());
    doReturn(true).when(preparedStatement).execute();
    AbstractKronosOData2EventHandler abstractKronosOData2EventHandler = Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
    doReturn("CREATE LOCAL TEMPORARY TABLE #TEST_TEMP_TABLE AS (SELECT * FROM test-table WHERE ID = '123')").when(abstractKronosOData2EventHandler).buildCreateTemporaryTableAsSelect(any(), any(), any(), any());
    abstractKronosOData2EventHandler.createTemporaryTableAsSelect(connection, "#TEMP_TABLE_TEST", selectBuilder, sqlContext);
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

  @Test
  public void testInsertIntoTemporaryTable() throws SQLException, ODataException {
    SQLContext sqlContext = new SQLContext(HANA);
    String temporaryTableName = "#TEMP_TEST_TABLE";
    SQLInsertBuilder insertBuilder = Mockito.mock(SQLInsertBuilder.class, Mockito.CALLS_REAL_METHODS);
    SQLStatement sqlStatement = Mockito.mock(SQLStatement.class);
    Mockito.doReturn(sqlStatement).when(insertBuilder).build(sqlContext);
    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
    doReturn(preparedStatement).when(connection).prepareStatement(any());
    doReturn(0).when(preparedStatement).executeUpdate();
    AbstractKronosOData2EventHandler abstractKronosOData2EventHandler = Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
    abstractKronosOData2EventHandler.insertIntoTemporaryTable(connection, insertBuilder, temporaryTableName, sqlContext);
  }

  @Test
  public void testUpdateIntoTemporaryTable() throws SQLException, ODataException {
    SQLContext sqlContext = new SQLContext(HANA);
    String temporaryTableName = "#TEMP_TEST_TABLE";
    SQLUpdateBuilder updateBuilder = Mockito.mock(SQLUpdateBuilder.class, Mockito.CALLS_REAL_METHODS);
    SQLStatement sqlStatement = Mockito.mock(SQLStatement.class);
    Mockito.doReturn(sqlStatement).when(updateBuilder).build(sqlContext);
    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
    doReturn(preparedStatement).when(connection).prepareStatement(any());
    doReturn(0).when(preparedStatement).executeUpdate();
    AbstractKronosOData2EventHandler abstractKronosOData2EventHandler = Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
    abstractKronosOData2EventHandler.updateTemporaryTable(connection, updateBuilder, temporaryTableName, sqlContext);
  }

  @Test
  public void testBatchDropTemporaryTables() throws SQLException {
    String temporaryTableName = "#TEMP_TEST_TABLE";
    Statement statement = Mockito.mock(Statement.class);
    sqlFactory.when(() -> SqlFactory.deriveDialect(any())).thenReturn(new HanaSqlDialect());
    doReturn(statement).when(connection).createStatement();
    doReturn(new int[]{1}).when(statement).executeBatch();
    AbstractKronosOData2EventHandler abstractKronosOData2EventHandler = Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
    abstractKronosOData2EventHandler.batchDropTemporaryTables(connection, temporaryTableName);
  }

  @Test
  public void testReadEntryMap() throws SQLException, IOException {
    String tableName = "test-table";
    sqlFactory.when(() -> SqlFactory.deriveDialect(any())).thenReturn(new HanaSqlDialect());
    PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
    doReturn(preparedStatement).when(connection).prepareStatement(any());
    String csvResults = "1, John Doe";
    ResultSet resultSet = new Csv().read(new StringReader(csvResults), new String[] {"id", "name"});
    doReturn(resultSet).when(preparedStatement).executeQuery();
    AbstractKronosOData2EventHandler abstractKronosOData2EventHandler = Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
    Map<String, Object> entryMap = abstractKronosOData2EventHandler.readEntryMap(connection, tableName);
    assertTrue(!entryMap.isEmpty());
  }

}
