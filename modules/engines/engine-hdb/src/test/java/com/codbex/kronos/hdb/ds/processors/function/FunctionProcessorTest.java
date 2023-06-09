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
package com.codbex.kronos.hdb.ds.processors.function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.codbex.kronos.hdb.ds.model.DataStructureModelBuilder;
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.DataStructureHDBTableFunctionModel;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.Constants;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.api.v3.problems.ProblemsFacade;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.test.AbstractDirigibleTest;
import org.eclipse.dirigible.database.ds.model.IDataStructureModel;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.eclipse.dirigible.database.sql.dialects.postgres.PostgresSqlDialect;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class FunctionProcessorTest extends AbstractDirigibleTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private Connection mockConnection;

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private SqlFactory mockSqlFactory;

  @Mock
  private PreparedStatement mockStatement;

  @Before
  public void openMocks() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void executeCreateTableFunctionIfDoNotExist() throws IOException, SQLException {
    executeCreateTableFunctionSuccessfully(false, 1);
  }

  @Test
  public void executeCreateTableFunctionIfAlreadyExist() throws IOException, SQLException {
    executeCreateTableFunctionSuccessfully(true, 0);
  }

  public void executeCreateTableFunctionSuccessfully(boolean doExist, int expectedTimesOfInvocation)
      throws IOException, SQLException {
    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
        MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
      configuration.when(() -> Configuration.get(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("true");

      FunctionCreateProcessor processorSpy = spy(FunctionCreateProcessor.class);
      String hdbTableFunctionSample = IOUtils.toString(
          FunctionProcessorTest.class.getResourceAsStream("/OrderTableFunction.hdbtablefunction"),
          StandardCharsets.UTF_8);

      DataStructureModelBuilder builder = new DataStructureModelBuilder()
          .rawContent(hdbTableFunctionSample)
          .withType("HDBTABLEFUNCTION")
          .withName("\"MYSCHEMA\".\"hdb_view::FUNCTION_NAME\"")
          .withSchema("MYSCHEMA");

      DataStructureHDBTableFunctionModel model = new DataStructureHDBTableFunctionModel(builder);
      String sql = Constants.HDBTABLEFUNCTION_CREATE + model.getRawContent();
      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
          .exists(mockConnection, "MYSCHEMA", CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
              DatabaseArtifactTypes.FUNCTION)).thenReturn(doExist);

      when(mockConnection.prepareStatement(sql)).thenReturn(mockStatement);
      processorSpy.execute(mockConnection, model);

      verify(processorSpy, times(expectedTimesOfInvocation)).executeSql(sql, mockConnection);
    }
  }

  @Test(expected = IllegalStateException.class)
  public void executeCreateTableFunctionPostgresSQLFailed() throws Exception {
    FunctionCreateProcessor processorSpy = spy(FunctionCreateProcessor.class);

    DataStructureModelBuilder builder = new DataStructureModelBuilder()
        .withName("\"MYSCHEMA\".\"hdb_view::FUNCTION_NAME\"")
        .withType("HDBTABLEFUNCTION");
    DataStructureHDBTableFunctionModel model = new DataStructureHDBTableFunctionModel(builder);

    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
        MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new PostgresSqlDialect());
      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
          .thenAnswer((Answer<Void>) invocation -> null);

      processorSpy.execute(mockConnection, model);
    }
  }

  @Test
  public void executeDropTableFunctionIfDoNotExist() throws IOException, SQLException {
    executeDropTableFunctionSuccessfully(false, 0);
  }

  @Test
  public void executeDropTableFunctionIfAlreadyExist() throws IOException, SQLException {
    executeDropTableFunctionSuccessfully(true, 1);
  }

  public void executeDropTableFunctionSuccessfully(boolean doExist, int expectedTimesOfInvocation)
      throws SQLException {
    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
        MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
      configuration.when(() -> Configuration.get(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("true");

      FunctionDropProcessor processorSpy = spy(FunctionDropProcessor.class);

      DataStructureModelBuilder builder = new DataStructureModelBuilder()
          .withName("\"MYSCHEMA\".\"hdb_view::FUNCTION_NAME\"")
          .withType("HDBTABLEFUNCTION");
      DataStructureHDBTableFunctionModel model = new DataStructureHDBTableFunctionModel(builder);
      String sql = Constants.HDBTABLEFUNCTION_DROP + model.getName();
      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
          .exists(mockConnection, "MYSCHEMA", CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
              DatabaseArtifactTypes.FUNCTION)).thenReturn(doExist);

      when(mockConnection.prepareStatement(sql)).thenReturn(mockStatement);
      processorSpy.execute(mockConnection, model);

      verify(processorSpy, times(expectedTimesOfInvocation)).executeSql(sql, mockConnection);
    }
  }

  @Test(expected = IllegalStateException.class)
  public void executeDropTableFunctionFailed() throws Exception {
    FunctionDropProcessor processorSpy = spy(FunctionDropProcessor.class);

    DataStructureModelBuilder builder = new DataStructureModelBuilder()
        .withName("\"MYSCHEMA\".\"hdb_view::FUNCTION_NAME\"")
        .withType("HDBTABLEFUNCTION");
    DataStructureHDBTableFunctionModel model = new DataStructureHDBTableFunctionModel(builder);

    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
        MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new PostgresSqlDialect());
      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
          .exists(mockConnection, "MYSCHEMA", CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
              DatabaseArtifactTypes.FUNCTION)).thenReturn(true);
      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
          .thenAnswer((Answer<Void>) invocation -> null);

      processorSpy.execute(mockConnection, model);
    }
  }
}
