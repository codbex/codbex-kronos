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
package com.codbex.kronos.engine.xsodata.handler;

import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.eclipse.dirigible.components.database.DatabaseSystem;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.eclipse.dirigible.engine.odata2.sql.api.SQLStatement;
import org.eclipse.dirigible.engine.odata2.sql.api.SQLStatementParam;
import org.eclipse.dirigible.engine.odata2.sql.builder.*;
import org.h2.tools.Csv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.StringReader;
import java.sql.*;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

/**
 * The Class AbstractKronosOData2EventHandlerTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractKronosOData2EventHandlerTest {

    /** The connection. */
    @Mock
    private Connection connection;

    /** The edm type. */
    @Mock
    private EdmType edmType;

    /** The sql factory. */
    private static final MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class, Mockito.CALLS_REAL_METHODS);

    /** The handler. */
    private final KronosProcedureOData2EventHandler handler = new KronosProcedureOData2EventHandler();

    /**
     * Test build create temporary table like table sql.
     */
    @Test
    public void testBuildCreateTemporaryTableLikeTableSql() {
        sqlFactory.when(() -> SqlFactory.deriveDialect(any()))
                  .thenReturn(new HanaSqlDialect());

        assertEquals("CREATE LOCAL TEMPORARY TABLE #TEST_TEMP_TABLE LIKE TEST_SCHEMA.test-table WITH NO DATA",
                handler.buildCreateTemporaryTableLikeTableSql(connection, ISqlKeywords.METADATA_TABLE, "TEST_SCHEMA", "#TEST_TEMP_TABLE",
                        "test-table"));

        assertEquals("CREATE LOCAL TEMPORARY TABLE #TEST_TEMP_TABLE AS (SELECT * FROM TEST_SCHEMA.test-table) WITH NO DATA",
                handler.buildCreateTemporaryTableLikeTableSql(connection, ISqlKeywords.METADATA_CALC_VIEW, "TEST_SCHEMA",
                        "#TEST_TEMP_TABLE", "test-table"));
    }

    /**
     * Test build create temporary table as select.
     */
    @Test
    public void testBuildCreateTemporaryTableAsSelect() {
        sqlFactory.when(() -> SqlFactory.deriveDialect(any()))
                  .thenReturn(new HanaSqlDialect());

        List<SQLStatementParam> parameters = List.of(new SQLStatementParam("123", edmType, null));
        assertEquals("CREATE LOCAL TEMPORARY TABLE #TEST_TEMP_TABLE AS (SELECT * FROM test-table WHERE ID = '123')",
                handler.buildCreateTemporaryTableAsSelect(connection, "#TEST_TEMP_TABLE", "SELECT * FROM test-table WHERE ID = ?",
                        parameters));
    }

    /**
     * Test get SQL insert builder target table.
     *
     * @throws ODataException the o data exception
     */
    @Test
    public void testGetSQLInsertBuilderTargetTable() throws ODataException {
        SQLContext sqlContext = new SQLContext(DatabaseSystem.HANA);
        SQLInsertBuilder insertBuilder = Mockito.mock(SQLInsertBuilder.class, Mockito.CALLS_REAL_METHODS);
        SQLStatement sqlStatement = Mockito.mock(SQLStatement.class);
        Mockito.doReturn(sqlStatement)
               .when(insertBuilder)
               .build(sqlContext);
        Mockito.doReturn("test-table")
               .when(insertBuilder)
               .getTargetTableName();
        AbstractKronosOData2EventHandler abstractKronosOData2EventHandler =
                Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
        String targetTableName = abstractKronosOData2EventHandler.getSQLInsertBuilderTargetTable(insertBuilder, sqlContext);
        assertEquals("test-table", targetTableName);
    }

    /**
     * Test get SQL update builder target table.
     *
     * @throws ODataException the o data exception
     */
    @Test
    public void testGetSQLUpdateBuilderTargetTable() throws ODataException {
        SQLContext sqlContext = new SQLContext(DatabaseSystem.HANA);
        SQLUpdateBuilder updateBuilder = Mockito.mock(SQLUpdateBuilder.class, Mockito.CALLS_REAL_METHODS);
        SQLStatement sqlStatement = Mockito.mock(SQLStatement.class);
        Mockito.doReturn(sqlStatement)
               .when(updateBuilder)
               .build(sqlContext);
        Mockito.doReturn("test-table")
               .when(updateBuilder)
               .getTargetTableName();
        AbstractKronosOData2EventHandler abstractKronosOData2EventHandler =
                Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
        String targetTableName = abstractKronosOData2EventHandler.getSQLUpdateBuilderTargetTable(updateBuilder, sqlContext);
        assertEquals("test-table", targetTableName);
    }

    /**
     * Test get SQL delete builder target table.
     *
     * @throws ODataException the o data exception
     */
    @Test
    public void testGetSQLDeleteBuilderTargetTable() throws ODataException {
        SQLContext sqlContext = new SQLContext(DatabaseSystem.HANA);
        SQLDeleteBuilder deleteBuilder = Mockito.mock(SQLDeleteBuilder.class, Mockito.CALLS_REAL_METHODS);
        SQLStatement sqlStatement = Mockito.mock(SQLStatement.class);
        Mockito.doReturn(sqlStatement)
               .when(deleteBuilder)
               .build(sqlContext);
        Mockito.doReturn("test-table")
               .when(deleteBuilder)
               .getTargetTableName();
        AbstractKronosOData2EventHandler abstractKronosOData2EventHandler =
                Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
        String targetTableName = abstractKronosOData2EventHandler.getSQLDeleteBuilderTargetTable(deleteBuilder, sqlContext);
        assertEquals("test-table", targetTableName);
    }

    /**
     * Test create temporary table as select.
     *
     * @throws ODataException the o data exception
     * @throws SQLException the SQL exception
     */
    @Test
    public void testCreateTemporaryTableAsSelect() throws ODataException, SQLException {
        SQLContext sqlContext = new SQLContext(DatabaseSystem.HANA);
        SQLSelectBuilder selectBuilder = Mockito.mock(SQLSelectBuilder.class, Mockito.CALLS_REAL_METHODS);
        Mockito.doReturn("")
               .when(selectBuilder)
               .buildSelect(sqlContext);
        Mockito.doReturn(List.of())
               .when(selectBuilder)
               .getStatementParams();
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        doReturn(preparedStatement).when(connection)
                                   .prepareStatement(any());
        doReturn(true).when(preparedStatement)
                      .execute();
        AbstractKronosOData2EventHandler abstractKronosOData2EventHandler =
                Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
        doReturn(
                "CREATE LOCAL TEMPORARY TABLE #TEST_TEMP_TABLE AS (SELECT * FROM test-table WHERE ID = '123')").when(
                        abstractKronosOData2EventHandler)
                                                                                                               .buildCreateTemporaryTableAsSelect(
                                                                                                                       any(), any(), any(),
                                                                                                                       any());
        abstractKronosOData2EventHandler.createTemporaryTableAsSelect(connection, "#TEMP_TABLE_TEST", selectBuilder, sqlContext);
    }

    /**
     * Test convert result set map.
     *
     * @throws SQLException the SQL exception
     */
    @Test
    public void testConvertResultSetMap() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        ResultSetMetaData resultSetMetaData = Mockito.mock(ResultSetMetaData.class);
        doReturn(resultSetMetaData).when(resultSet)
                                   .getMetaData();
        doReturn(1).when(resultSetMetaData)
                   .getColumnCount();
        AbstractKronosOData2EventHandler abstractKronosOData2EventHandler =
                Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
        Map<String, Object> convertedRSMap = abstractKronosOData2EventHandler.convertResultSetMap(resultSet);
        assertEquals(1, convertedRSMap.size());
    }

    /**
     * Test execute SQL statement.
     *
     * @throws SQLException the SQL exception
     * @throws ODataException the o data exception
     */
    @Test
    public void testExecuteSQLStatement() throws SQLException, ODataException {
        SQLStatement sqlStatement = Mockito.mock(SQLStatement.class);
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        doReturn(preparedStatement).when(connection)
                                   .prepareStatement(any());
        doReturn(0).when(preparedStatement)
                   .executeUpdate();
        AbstractKronosOData2EventHandler abstractKronosOData2EventHandler =
                Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
        abstractKronosOData2EventHandler.executeSQLStatement(connection, sqlStatement);
    }

    /**
     * Test insert into temporary table.
     *
     * @throws SQLException the SQL exception
     * @throws ODataException the o data exception
     */
    @Test
    public void testInsertIntoTemporaryTable() throws SQLException, ODataException {
        SQLContext sqlContext = new SQLContext(DatabaseSystem.HANA);
        String temporaryTableName = "#TEMP_TEST_TABLE";
        SQLInsertBuilder insertBuilder = Mockito.mock(SQLInsertBuilder.class, Mockito.CALLS_REAL_METHODS);
        SQLStatement sqlStatement = Mockito.mock(SQLStatement.class);
        Mockito.doReturn(sqlStatement)
               .when(insertBuilder)
               .build(sqlContext);
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        doReturn(preparedStatement).when(connection)
                                   .prepareStatement(any());
        doReturn(0).when(preparedStatement)
                   .executeUpdate();
        AbstractKronosOData2EventHandler abstractKronosOData2EventHandler =
                Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
        abstractKronosOData2EventHandler.insertIntoTemporaryTable(connection, insertBuilder, temporaryTableName, sqlContext);
    }

    /**
     * Test update into temporary table.
     *
     * @throws SQLException the SQL exception
     * @throws ODataException the o data exception
     */
    @Test
    public void testUpdateIntoTemporaryTable() throws SQLException, ODataException {
        SQLContext sqlContext = new SQLContext(DatabaseSystem.HANA);
        String temporaryTableName = "#TEMP_TEST_TABLE";
        SQLUpdateBuilder updateBuilder = Mockito.mock(SQLUpdateBuilder.class, Mockito.CALLS_REAL_METHODS);
        SQLStatement sqlStatement = Mockito.mock(SQLStatement.class);
        Mockito.doReturn(sqlStatement)
               .when(updateBuilder)
               .build(sqlContext);
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        doReturn(preparedStatement).when(connection)
                                   .prepareStatement(any());
        doReturn(0).when(preparedStatement)
                   .executeUpdate();
        AbstractKronosOData2EventHandler abstractKronosOData2EventHandler =
                Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
        abstractKronosOData2EventHandler.updateTemporaryTable(connection, updateBuilder, temporaryTableName, sqlContext);
    }

    /**
     * Test batch drop temporary tables.
     *
     * @throws SQLException the SQL exception
     */
    @Test
    public void testBatchDropTemporaryTables() throws SQLException {
        String temporaryTableName = "#TEMP_TEST_TABLE";
        Statement statement = Mockito.mock(Statement.class);
        sqlFactory.when(() -> SqlFactory.deriveDialect(any()))
                  .thenReturn(new HanaSqlDialect());
        doReturn(statement).when(connection)
                           .createStatement();
        doReturn(new int[] {1}).when(statement)
                               .executeBatch();
        AbstractKronosOData2EventHandler abstractKronosOData2EventHandler =
                Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
        abstractKronosOData2EventHandler.batchDropTemporaryTables(connection, temporaryTableName);
    }

    /**
     * Test read entry map.
     *
     * @throws SQLException the SQL exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void testReadEntryMap() throws SQLException, IOException {
        String tableName = "test-table";
        sqlFactory.when(() -> SqlFactory.deriveDialect(any()))
                  .thenReturn(new HanaSqlDialect());
        PreparedStatement preparedStatement = Mockito.mock(PreparedStatement.class);
        doReturn(preparedStatement).when(connection)
                                   .prepareStatement(any());
        String csvResults = "1, John Doe";
        ResultSet resultSet = new Csv().read(new StringReader(csvResults), new String[] {"id", "name"});
        doReturn(resultSet).when(preparedStatement)
                           .executeQuery();
        AbstractKronosOData2EventHandler abstractKronosOData2EventHandler =
                Mockito.mock(AbstractKronosOData2EventHandler.class, Mockito.CALLS_REAL_METHODS);
        Map<String, Object> entryMap = abstractKronosOData2EventHandler.readEntryMap(connection, tableName);
        assertFalse(entryMap.isEmpty());
    }

}
