/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.integration.tests.core.hdb.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.eclipse.dirigible.database.sql.ISqlKeywords;

import com.codbex.kronos.utils.Constants;
import com.codbex.kronos.utils.HDBUtils;

public class HanaITestUtils {

  public static boolean checkExistOfPublicSynonym(Connection connection, String synonymName) throws SQLException {
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet rs = metaData.getTables(null, Constants.SYNONYM_PUBLIC_SCHEMA, synonymName,
        new String[]{ISqlKeywords.KEYWORD_SYNONYM});
    return rs.next();
  }

  public static boolean checkExistOfSynonym(Connection connection, String synonymName, String synonymSchema) throws SQLException {
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet rs = metaData.getTables(null, synonymSchema, synonymName,
        new String[]{ISqlKeywords.KEYWORD_SYNONYM});
    return rs.next();
  }

  public static boolean checkExistOfTable(Connection connection, String tableName, String tableSchema) throws SQLException {
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet rs = metaData.getTables(null, tableSchema, tableName, new String[]{ISqlKeywords.KEYWORD_TABLE});
    return rs.next();
  }

  public static boolean checkExistOfTableGlobalTemporary(Connection connection, String tableName, String tableSchema) throws SQLException {
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet rs = metaData.getTables(null, tableSchema, tableName, new String[]{ISqlKeywords.METADATA_GLOBAL_TEMPORARY});
    return rs.next();
  }

  public static boolean checkExistOfView(Connection connection, String tableName, String tableSchema) throws SQLException {
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet rs = metaData.getTables(null, tableSchema, tableName, new String[]{ISqlKeywords.KEYWORD_VIEW});
    return rs.next();
  }

  public static boolean checkExistOfSchema(Connection connection, String schemaName) throws SQLException {
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet rs = metaData.getSchemas(null, schemaName);
    return rs.next();
  }

  public static boolean checkExistOfProcedure(Connection connection, String tableName, String tableSchema) throws SQLException {
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet rs = metaData.getProcedures(null, tableSchema, tableName);
    return rs.next();
  }

  public static boolean checkExistOfFunction(Statement stmt, String funcName, String funcSchema) throws SQLException {
    ResultSet rs = stmt.executeQuery(
        "SELECT COUNT(*) as rawsCount FROM SYS.OBJECTS WHERE OBJECT_NAME IN ('" + funcName
            + "') AND OBJECT_TYPE = 'FUNCTION' AND SCHEMA_NAME ='" + funcSchema + "'");
    rs.next();
    return rs.getInt("rawsCount") == 1;
  }

  public static boolean checkExistOfSequence(Statement stmt, String seqName, String seqSchema) throws SQLException {
    ResultSet rs = stmt.executeQuery(String
        .format("SELECT COUNT(*) as rawsCount from Sequences WHERE SEQUENCE_NAME = '%s' AND SCHEMA_NAME = '%s'",
            seqName, seqSchema));
    rs.next();
    return rs.getInt("rawsCount") == 1;
  }

  public static boolean checkExistOfTableType(Statement stmt, String tableTypeName, String schemaName) throws SQLException {
    ResultSet tableType = stmt
        .executeQuery(
            String.format(
                "SELECT IS_USER_DEFINED_TYPE FROM SYS.\"TABLES\" WHERE TABLE_NAME like '%s' AND IS_USER_DEFINED_TYPE like 'TRUE' AND SCHEMA_NAME LIKE '%s'",
                tableTypeName, schemaName));
    return tableType.next();
  }

  public static void dropTable(Connection connection, Statement stmt, String tableName, String tableSchema) throws SQLException {
    if (checkExistOfPublicSynonym(connection, tableName)) {
      stmt.executeUpdate(
          String.format("drop SYNONYM \"%s\".\"%s\"", Constants.SYNONYM_PUBLIC_SCHEMA, tableName));
    }
    if (checkExistOfTable(connection, tableName, tableSchema)) {
      stmt.executeUpdate(String.format("drop TABLE  \"%s\".\"%s\"", tableSchema, tableName));
    }
  }

  public static void dropView(Connection connection, Statement stmt, String tableName, String tableSchema) throws SQLException {
    if (checkExistOfPublicSynonym(connection, tableName)) {
      stmt.executeUpdate(
          String.format("drop SYNONYM \"%s\".\"%s\"", Constants.SYNONYM_PUBLIC_SCHEMA, tableName));
    }
    if (checkExistOfView(connection, tableName, tableSchema)) {
      stmt.executeUpdate(String.format("drop VIEW  \"%s\".\"%s\"", tableSchema, tableName));
    }
  }

  public static void dropTableType(Connection connection, Statement stmt, String tableName, String tableSchema) throws SQLException {
    if (checkExistOfPublicSynonym(connection, tableName)) {
      stmt.executeUpdate(
          String.format("drop SYNONYM \"%s\".\"%s\"", Constants.SYNONYM_PUBLIC_SCHEMA, tableName));
    }
    if (checkExistOfTable(connection, tableName, tableSchema)) {
      stmt.executeUpdate(String.format("drop TYPE  \"%s\".\"%s\"", tableSchema, tableName));
    }
  }

  public static void dropProcedure(Statement stmt, String procName, String procSchema) throws SQLException {
    stmt.executeUpdate(String.format("DROP PROCEDURE %s", HDBUtils
        .escapeArtifactName(procName, procSchema)));
  }

  public static void dropFunction(Statement stmt, String funcName, String funcSchema) throws SQLException {
    stmt.executeUpdate(String.format("DROP FUNCTION %s", HDBUtils
        .escapeArtifactName(funcName, funcSchema)));
  }

  public static void dropSequence(Statement stmt, String seqName, String seqSchema) throws SQLException {
    stmt.executeUpdate(String.format("DROP SEQUENCE %s", HDBUtils
        .escapeArtifactName(seqName, seqSchema)));
  }

  public static void dropSchema(Statement stmt, String schemaName) throws SQLException {
    stmt.executeUpdate(String.format("DROP SCHEMA \"%s\" CASCADE", schemaName));
  }

  public static void dropHDIContainer(Statement stmt, String containerName, String containerGroupName) throws SQLException {
    stmt.execute("CREATE LOCAL TEMPORARY COLUMN TABLE #DROP_CONTAINER_PARAMETERS LIKE _SYS_DI.TT_PARAMETERS");
    stmt.execute("INSERT INTO #DROP_CONTAINER_PARAMETERS ( KEY, VALUE ) VALUES ( 'ignore_work', 'true' )");
    stmt.execute("INSERT INTO #DROP_CONTAINER_PARAMETERS ( KEY, VALUE ) VALUES ( 'ignore_deployed', 'true' )");
    stmt.execute("CALL _SYS_DI#" + containerGroupName + ".DROP_CONTAINER('" + containerName + "', #DROP_CONTAINER_PARAMETERS, ?, ?, ?)");
    stmt.execute("DROP TABLE #DROP_CONTAINER_PARAMETERS");
  }

  public static void dropHDIContainerGroup(Statement stmt, String containerGroupName) throws SQLException {
    stmt.execute("CALL _SYS_DI.DROP_CONTAINER_GROUP('" + containerGroupName + "', _SYS_DI.T_NO_PARAMETERS, ?, ?, ?)");
  }

  public static void createSchema(Statement stmt, String schemaName) throws SQLException {
    stmt.executeUpdate(String.format("CREATE SCHEMA \"%s\"", schemaName));
  }

  public static void clearDataFromDataStructure(DataSource datasource, List<String> resources) throws SQLException {
    try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {
      DatabaseMetaData metaData = connection.getMetaData();
      ResultSet table = metaData.getTables(null, null, "KRONOS_DATA_STRUCTURES", null);
      if (table.next()) {
        stmt.executeUpdate("DELETE FROM \"KRONOS_DATA_STRUCTURES\" WHERE DS_LOCATION IN " + "(" + String.join(",", resources) + ")");
      }
    }
  }

  public static void createEmptyTable(Statement stmt, String tableName, String tableSchema) throws SQLException {
    stmt.executeUpdate(String.format("create table \"%s\".\"%s\"(Id INTEGER,Name NVARCHAR)",
        tableSchema, tableName));
  }

  public static boolean checkCalculatedColumns(Connection connection, String tableName, String tableSchema, String columnName)
      throws SQLException {
    DatabaseMetaData metaData = connection.getMetaData();
    ResultSet rs = metaData.getColumns(null, tableSchema, tableName, columnName);
    return rs.next();
  }
}
