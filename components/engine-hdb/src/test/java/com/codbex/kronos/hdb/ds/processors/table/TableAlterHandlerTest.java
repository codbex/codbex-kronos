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
package com.codbex.kronos.hdb.ds.processors.table;

//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.spy;
//import static org.mockito.Mockito.when;
//
//import java.sql.Connection;
//import java.sql.DatabaseMetaData;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.eclipse.dirigible.api.v3.problems.ProblemsFacade;
//import org.eclipse.dirigible.commons.config.Configuration;
//import org.eclipse.dirigible.database.persistence.utils.DatabaseMetadataUtil;
//import org.eclipse.dirigible.database.sql.SqlFactory;
//import org.eclipse.dirigible.database.sql.builders.AlterBranchingBuilder;
//import org.eclipse.dirigible.database.sql.builders.table.AlterTableBuilder;
//import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Answers;
//import org.mockito.Mock;
//import org.mockito.MockedStatic;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.stubbing.Answer;
//
//import com.codbex.kronos.engine.hdb.processors.HDBTableAlterHandler;
//import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableColumnModel;
//import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableConstraintPrimaryKeyModel;
//import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableConstraintsModel;
//import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;

//@RunWith(MockitoJUnitRunner.class)
public class TableAlterHandlerTest {

//  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
//  private Connection mockConnection;
//
//  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
//  private SqlFactory mockSqlFactory;
//
//  @Mock
//  private AlterBranchingBuilder alter;
//
//  @Mock
//  private AlterTableBuilder alterTableBuilder;
//
//  @Mock
//  private DatabaseMetaData databaseMetaData;
//
//  @Mock
//  private ResultSet resultSet;
//
//  private DataStructureHDBTableConstraintPrimaryKeyModel primaryKey = new DataStructureHDBTableConstraintPrimaryKeyModel();
//  private DataStructureHDBTableConstraintsModel constraintsModel = new DataStructureHDBTableConstraintsModel();
//  private DataStructureHDBTableModel tableModel = new DataStructureHDBTableModel();
//
//  @Before
//  public void openMocks() {
//    MockitoAnnotations.openMocks(this);
//  }
//
//  @Ignore("Missing verifyPrivate() in Mockito, test needs to be refactored")
//  @Test
//  public void addColumnsSuccessfully() throws Exception {
//    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
//        MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
//      configuration.when(() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("true");
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
//      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
//
//      List<DataStructureHDBTableColumnModel> columns = new ArrayList<>();
//      columns.add(new DataStructureHDBTableColumnModel("Id", "NVARCHAR", "32", true, false, null, false, null, null, true, null));
//      columns.add(new DataStructureHDBTableColumnModel("Name", "NVARCHAR", "32", true, false, null, false, null, null, false, null));
//      tableModel.setColumns(columns);
//      tableModel.setConstraints(constraintsModel);
//      tableModel.setName("hdb_table::SampleHdbdd");
//      tableModel.setSchema("MYSCHEMA");
//
//      HDBTableAlterHandler tableAlterHandler = new HDBTableAlterHandler(mockConnection, tableModel);
//      HDBTableAlterHandler handlerSpy = spy(tableAlterHandler);
//
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter()).thenReturn(alter);
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any())).thenReturn(alterTableBuilder);
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any()).add()).thenReturn(alterTableBuilder);
//      when(alterTableBuilder.build()).thenReturn("sql");
//
//      handlerSpy.addColumns(mockConnection);
////			verifyPrivate(handlerSpy, times(2)).invoke("executeAlterBuilder", mockConnection, alterTableBuilder);
//    }
//  }
//
//  @Test(expected = SQLException.class)
//  public void addColumnsFailedWhenPrimaryKey() throws Exception {
//    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
//        MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class);
//        MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
//      configuration.when(() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("true");
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
//      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
//
//      List<DataStructureHDBTableColumnModel> columns = new ArrayList<>();
//      columns.add(new DataStructureHDBTableColumnModel("Age", "INTEGER", "32", true, true, null, false, null, null, true, null));
//      tableModel.setColumns(columns);
//      tableModel.setConstraints(constraintsModel);
//      tableModel.setName("hdb_table::SampleHdbdd");
//      tableModel.setSchema("MYSCHEMA");
//
//      HDBTableAlterHandler tableAlterHandler = new HDBTableAlterHandler(mockConnection, tableModel);
//      HDBTableAlterHandler handlerSpy = spy(tableAlterHandler);
//
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter()).thenReturn(alter);
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any())).thenReturn(alterTableBuilder);
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any()).add()).thenReturn(alterTableBuilder);
//      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
//          .thenAnswer((Answer<Void>) invocation -> null);
//      handlerSpy.addColumns(mockConnection);
//    }
//  }
//
//  @Ignore("Missing verifyPrivate() in Mockito, test needs to be refactored")
//  @Test
//  public void removeColumnsSuccessfully() throws Exception {
//
//    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
//        MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
//      configuration.when(() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("true");
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
//      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
//
//      List<DataStructureHDBTableColumnModel> columns = new ArrayList<>();
//      columns.add(new DataStructureHDBTableColumnModel("Id", "NVARCHAR", "32", true, false, null, false, null, null, true, null));
//      columns.add(new DataStructureHDBTableColumnModel("Name", "NVARCHAR", "32", true, false, null, false, null, null, false, null));
//      tableModel.setColumns(columns);
//      tableModel.setConstraints(constraintsModel);
//      tableModel.setName("hdb_table::SampleHdbdd");
//      tableModel.setSchema("MYSCHEMA");
//
//      when(mockConnection.getMetaData()).thenReturn(databaseMetaData);
//      when(databaseMetaData.getColumns(any(), any(), any(), any())).thenReturn(resultSet);
//      when(resultSet.next()).thenReturn(true).thenReturn(false);
//      when(resultSet.getString("COLUMN_NAME")).thenReturn("Test");
//      when(resultSet.getString("TYPE_NAME")).thenReturn("NVARCHAR");
//
//      HDBTableAlterHandler tableAlterHandler = new HDBTableAlterHandler(mockConnection, tableModel);
//      HDBTableAlterHandler handlerSpy = spy(tableAlterHandler);
//
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter()).thenReturn(alter);
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any())).thenReturn(alterTableBuilder);
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any()).drop()).thenReturn(alterTableBuilder);
//      when(alterTableBuilder.build()).thenReturn("sql");
//
//      handlerSpy.removeColumns(mockConnection);
//      // TODO: Refactor -> Mockito doesn't have support for verifyPrivate()
//      // verifyPrivate(handlerSpy, times(1)).invoke("executeAlterBuilder", mockConnection, alterTableBuilder);
//    }
//  }
//
//  @Ignore("Missing verifyPrivate() in Mockito, test needs to be refactored")
//  @Test
//  public void updateColumnsSuccessfully() throws Exception {
//    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
//        MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
//      configuration.when(() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("true");
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
//      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
//
//      List<DataStructureHDBTableColumnModel> columns = new ArrayList<>();
//      columns.add(new DataStructureHDBTableColumnModel("Id", "NVARCHAR", "32", true, false, null, false, null, null, true, null));
//      columns.add(new DataStructureHDBTableColumnModel("Name", "NVARCHAR", "32", true, false, null, false, null, null, false, null));
//      tableModel.setColumns(columns);
//      tableModel.setConstraints(constraintsModel);
//      tableModel.setName("hdb_table::SampleHdbdd");
//      tableModel.setSchema("MYSCHEMA");
//
//      when(mockConnection.getMetaData()).thenReturn(databaseMetaData);
//      when(databaseMetaData.getColumns(any(), any(), any(), any())).thenReturn(resultSet);
//      when(resultSet.next()).thenReturn(true).thenReturn(false);
//      when(resultSet.getString("COLUMN_NAME")).thenReturn("Name");
//      when(resultSet.getString("TYPE_NAME")).thenReturn("NVARCHAR");
//
//      HDBTableAlterHandler tableAlterHandler = new HDBTableAlterHandler(mockConnection, tableModel);
//      HDBTableAlterHandler handlerSpy = spy(tableAlterHandler);
//
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter()).thenReturn(alter);
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any())).thenReturn(alterTableBuilder);
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any()).alter()).thenReturn(alterTableBuilder);
//      when(alterTableBuilder.build()).thenReturn("sql");
//
//      handlerSpy.updateColumns(mockConnection);
//      // TODO: Refactor -> Mockito doesn't have support for verifyPrivate()
//      // verifyPrivate(handlerSpy, times(1)).invoke("executeAlterBuilder", mockConnection, alterTableBuilder);
//    }
//  }
//
//  @Ignore("Missing verifyPrivate() in Mockito, test needs to be refactored")
//  @Test
//  public void rebuildIndecesSuccessfully() throws Exception {
//    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
//        MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
//      configuration.when(() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("true");
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
//      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
//      List<DataStructureHDBTableColumnModel> columns = new ArrayList<>();
//      columns.add(new DataStructureHDBTableColumnModel("Id", "NVARCHAR", "32", true, false, null, false, null, null, true, null));
//      columns.add(new DataStructureHDBTableColumnModel("Name", "NVARCHAR", "32", true, false, null, false, null, null, false, null));
//      tableModel.setColumns(columns);
//      tableModel.setConstraints(constraintsModel);
//      tableModel.setName("hdb_table::SampleHdbdd");
//      tableModel.setSchema("MYSCHEMA");
//
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter()).thenReturn(alter);
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any())).thenReturn(alterTableBuilder);
//      when(alterTableBuilder.build()).thenReturn("sql");
//
//      HDBTableAlterHandler tableAlterHandler = new HDBTableAlterHandler(mockConnection, tableModel);
//      HDBTableAlterHandler handlerSpy = spy(tableAlterHandler);
//
//      handlerSpy.rebuildIndeces(mockConnection);
//      // TODO: Refactor -> Mockito doesn't have support for verifyPrivate()
//      // verifyPrivate(handlerSpy, times(1)).invoke("executeAlterBuilder", mockConnection, alterTableBuilder);
//    }
//  }
//
//  @Test(expected = SQLException.class)
//  public void ensurePrimaryKeyIsUnchangedSuccessfully() throws Exception {
//    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
//        MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class);
//        MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
//      configuration.when(() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("true");
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
//      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
//
//      List<DataStructureHDBTableColumnModel> columns = new ArrayList<>();
//      columns.add(new DataStructureHDBTableColumnModel("Id", "NVARCHAR", "32", true, true, null, false, null, null, true, null));
//      columns.add(new DataStructureHDBTableColumnModel("Name", "NVARCHAR", "32", true, false, null, false, null, null, false, null));
//      tableModel.setColumns(columns);
//      tableModel.setConstraints(constraintsModel);
//      primaryKey.setColumns(new String[]{"Id"});
//      constraintsModel.setPrimaryKey(primaryKey);
//      tableModel.setName("hdb_table::SampleHdbdd");
//      tableModel.setSchema("MYSCHEMA");
//
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter()).thenReturn(alter);
//      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
//          .thenAnswer((Answer<Void>) invocation -> null);
//      HDBTableAlterHandler tableAlterHandler = new HDBTableAlterHandler(mockConnection, tableModel);
//      HDBTableAlterHandler handlerSpy = spy(tableAlterHandler);
//
//      handlerSpy.ensurePrimaryKeyIsUnchanged(mockConnection);
//    }
//  }

}
