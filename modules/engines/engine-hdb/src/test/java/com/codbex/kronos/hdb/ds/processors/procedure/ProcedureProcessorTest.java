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
package com.codbex.kronos.hdb.ds.processors.procedure;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.codbex.kronos.hdb.ds.model.DataStructureModelBuilder;
import com.codbex.kronos.hdb.ds.model.hdbprocedure.DataStructureHDBProcedureModel;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.Constants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.api.v3.problems.ProblemsFacade;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.test.AbstractDirigibleTest;
import org.eclipse.dirigible.database.ds.model.IDataStructureModel;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.DefaultSqlDialect;
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
public class ProcedureProcessorTest extends AbstractDirigibleTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private Connection mockConnection;

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private SqlFactory mockSqlFactory;

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private DefaultSqlDialect mockSqlDialect;

  @Mock
  private PreparedStatement mockStatement;

  @Before
  public void openMocks() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void executeCreateProcedureIfDoNotExist() throws IOException, SQLException {
    executeCreateProcedureSuccessfully(false, 1);
  }

  @Test
  public void executeCreateProcedureIfAlreadyExist() throws IOException, SQLException {
    executeCreateProcedureSuccessfully(true, 0);
  }

  public void executeCreateProcedureSuccessfully(boolean doExist, int expectedTimesOfInvocation)
      throws IOException, SQLException {
    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
        MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
      configuration.when(() -> Configuration.get(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("true");

      ProcedureCreateProcessor processorSpy = spy(ProcedureCreateProcessor.class);
      String hdbProcedureSample = IOUtils.toString(ProcedureProcessorTest.class.getResourceAsStream("/OrderProcedure.hdbprocedure"),
          StandardCharsets.UTF_8);

      DataStructureModelBuilder builder = new DataStructureModelBuilder()
          .rawContent(hdbProcedureSample)
          .withName("\"MYSCHEMA\".\"hdb_view::OrderProcedure\"");

      DataStructureHDBProcedureModel model = new DataStructureHDBProcedureModel(builder);
      String sql = Constants.HDBPROCEDURE_CREATE + model.getRawContent();
      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
          .exists(mockConnection, CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
              DatabaseArtifactTypes.PROCEDURE)).thenReturn(doExist);

      when(mockConnection.prepareStatement(sql)).thenReturn(mockStatement);
      processorSpy.execute(mockConnection, model);

      verify(processorSpy, times(expectedTimesOfInvocation)).executeSql(sql, mockConnection);
    }
  }

  @Test(expected = IllegalStateException.class)
  public void executeCreateProcedurePostgresFailed() throws Exception {
    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
        MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new PostgresSqlDialect());

      ProcedureCreateProcessor processorSpy = spy(ProcedureCreateProcessor.class);
      String hdbProcedureSample = IOUtils.toString(ProcedureProcessorTest.class.getResourceAsStream("/OrderProcedure.hdbprocedure"),
          StandardCharsets.UTF_8);

      DataStructureModelBuilder builder = new DataStructureModelBuilder()
          .rawContent(hdbProcedureSample)
          .withName("\"MYSCHEMA\".\"hdb_view::OrderProcedure\"");
      DataStructureHDBProcedureModel model = new DataStructureHDBProcedureModel(builder);
      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
          .exists(mockConnection, CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
              DatabaseArtifactTypes.PROCEDURE)).thenReturn(false);
      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
          .thenAnswer((Answer<Void>) invocation -> null);
      processorSpy.execute(mockConnection, model);
    }
  }

  @Test
  public void executeDropProcedureIfDoNotExist() throws IOException, SQLException {
    executeDropProcedureSuccessfully(false, 0);
  }

  @Test
  public void executeDropProcedureIfAlreadyExist() throws IOException, SQLException {
    executeDropProcedureSuccessfully(true, 1);
  }

  public void executeDropProcedureSuccessfully(boolean doExist, int expectedTimesOfInvocation) throws SQLException {
    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
        MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
      configuration.when(() -> Configuration.get(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("true");

      ProcedureDropProcessor processorSpy = spy(ProcedureDropProcessor.class);

      DataStructureModelBuilder builder = new DataStructureModelBuilder()
          .withName("\"MYSCHEMA\".\"hdb_view::OrderProcedure\"");
      DataStructureHDBProcedureModel model = new DataStructureHDBProcedureModel(builder);
      String sql = Constants.HDBPROCEDURE_DROP + model.getName();
      when(SqlFactory.getNative(mockConnection)
          .exists(mockConnection, CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
              DatabaseArtifactTypes.PROCEDURE)).thenReturn(doExist);

      when(mockConnection.prepareStatement(sql)).thenReturn(mockStatement);
      processorSpy.execute(mockConnection, model);

      verify(processorSpy, times(expectedTimesOfInvocation)).executeSql(sql, mockConnection);
    }
  }

  @Test(expected = IllegalStateException.class)
  public void executeDropProcedurePostgresFailed() throws Exception {
    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
        MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new PostgresSqlDialect());

      ProcedureDropProcessor processorSpy = spy(ProcedureDropProcessor.class);

      DataStructureModelBuilder builder = new DataStructureModelBuilder()
          .withName("\"MYSCHEMA\".\"hdb_view::OrderProcedure\"");
      DataStructureHDBProcedureModel model = new DataStructureHDBProcedureModel(builder);

      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
          .exists(mockConnection, CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
              DatabaseArtifactTypes.PROCEDURE)).thenReturn(true);

      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
          .thenAnswer((Answer<Void>) invocation -> null);
      processorSpy.execute(mockConnection, model);
    }
  }
}
