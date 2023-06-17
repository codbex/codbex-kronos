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
package com.codbex.kronos.engine.hdb.parser.hdbprocedure;

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

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.database.persistence.utils.DatabaseMetadataUtil;
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
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.codbex.kronos.engine.hdb.processors.HDBProcedureCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBProcedureDropProcessor;
import com.codbex.kronos.utils.CommonsUtils;

//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBProcedureProcessorTest {

//  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
//  private Connection mockConnection;
//
//  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
//  private SqlFactory mockSqlFactory;
//
//  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
//  private DefaultSqlDialect mockSqlDialect;
//
//  @Mock
//  private PreparedStatement mockStatement;
//
//  @Before
//  public void openMocks() {
//    MockitoAnnotations.openMocks(this);
//  }
//
//  @Test
//  public void executeCreateProcedureIfDoNotExist() throws IOException, SQLException {
//    executeCreateProcedureSuccessfully(false, 1);
//  }
//
//  @Test
//  public void executeCreateProcedureIfAlreadyExist() throws IOException, SQLException {
//    executeCreateProcedureSuccessfully(true, 0);
//  }
//
//  public void executeCreateProcedureSuccessfully(boolean doExist, int expectedTimesOfInvocation)
//      throws IOException, SQLException {
//    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
//        MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
//      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
//      configuration.when(() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("true");
//
//      HDBProcedureCreateProcessor processorSpy = spy(HDBProcedureCreateProcessor.class);
//      String hdbProcedureSample = IOUtils.toString(ProcedureProcessorTest.class.getResourceAsStream("/OrderProcedure.hdbprocedure"),
//          StandardCharsets.UTF_8);
//
//      DataStructureModelBuilder builder = new DataStructureModelBuilder()
//          .rawContent(hdbProcedureSample)
//          .withName("\"MYSCHEMA\".\"hdb_view::OrderProcedure\"");
//
//      DataStructureHDBProcedureModel model = new DataStructureHDBProcedureModel(builder);
//      String sql = Constants.HDBPROCEDURE_CREATE + model.getRawContent();
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
//          .exists(mockConnection, CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
//              DatabaseArtifactTypes.PROCEDURE)).thenReturn(doExist);
//
//      when(mockConnection.prepareStatement(sql)).thenReturn(mockStatement);
//      processorSpy.execute(mockConnection, model);
//
//      verify(processorSpy, times(expectedTimesOfInvocation)).executeSql(sql, mockConnection);
//    }
//  }
//
//  @Test(expected = IllegalStateException.class)
//  public void executeCreateProcedurePostgresFailed() throws Exception {
//    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
//        MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
//      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new PostgresSqlDialect());
//
//      HDBProcedureCreateProcessor processorSpy = spy(HDBProcedureCreateProcessor.class);
//      String hdbProcedureSample = IOUtils.toString(ProcedureProcessorTest.class.getResourceAsStream("/OrderProcedure.hdbprocedure"),
//          StandardCharsets.UTF_8);
//
//      DataStructureModelBuilder builder = new DataStructureModelBuilder()
//          .rawContent(hdbProcedureSample)
//          .withName("\"MYSCHEMA\".\"hdb_view::OrderProcedure\"");
//      DataStructureHDBProcedureModel model = new DataStructureHDBProcedureModel(builder);
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
//          .exists(mockConnection, CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
//              DatabaseArtifactTypes.PROCEDURE)).thenReturn(false);
//      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
//          .thenAnswer((Answer<Void>) invocation -> null);
//      processorSpy.execute(mockConnection, model);
//    }
//  }
//
//  @Test
//  public void executeDropProcedureIfDoNotExist() throws IOException, SQLException {
//    executeDropProcedureSuccessfully(false, 0);
//  }
//
//  @Test
//  public void executeDropProcedureIfAlreadyExist() throws IOException, SQLException {
//    executeDropProcedureSuccessfully(true, 1);
//  }
//
//  public void executeDropProcedureSuccessfully(boolean doExist, int expectedTimesOfInvocation) throws SQLException {
//    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
//        MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
//      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
//      configuration.when(() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("true");
//
//      HDBProcedureDropProcessor processorSpy = spy(HDBProcedureDropProcessor.class);
//
//      DataStructureModelBuilder builder = new DataStructureModelBuilder()
//          .withName("\"MYSCHEMA\".\"hdb_view::OrderProcedure\"");
//      DataStructureHDBProcedureModel model = new DataStructureHDBProcedureModel(builder);
//      String sql = Constants.HDBPROCEDURE_DROP + model.getName();
//      when(SqlFactory.getNative(mockConnection)
//          .exists(mockConnection, CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
//              DatabaseArtifactTypes.PROCEDURE)).thenReturn(doExist);
//
//      when(mockConnection.prepareStatement(sql)).thenReturn(mockStatement);
//      processorSpy.execute(mockConnection, model);
//
//      verify(processorSpy, times(expectedTimesOfInvocation)).executeSql(sql, mockConnection);
//    }
//  }
//
//  @Test(expected = IllegalStateException.class)
//  public void executeDropProcedurePostgresFailed() throws Exception {
//    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
//        MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
//      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new PostgresSqlDialect());
//
//      HDBProcedureDropProcessor processorSpy = spy(HDBProcedureDropProcessor.class);
//
//      DataStructureModelBuilder builder = new DataStructureModelBuilder()
//          .withName("\"MYSCHEMA\".\"hdb_view::OrderProcedure\"");
//      DataStructureHDBProcedureModel model = new DataStructureHDBProcedureModel(builder);
//
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
//          .exists(mockConnection, CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
//              DatabaseArtifactTypes.PROCEDURE)).thenReturn(true);
//
//      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
//          .thenAnswer((Answer<Void>) invocation -> null);
//      processorSpy.execute(mockConnection, model);
//    }
//  }
  
  /**
   * The Class TestConfiguration.
   */
  @EnableJpaRepositories(basePackages = "com.codbex.kronos")
  @SpringBootApplication(scanBasePackages = {"com.codbex.kronos", "org.eclipse.dirigible.components"})
  @EnableScheduling
  static class TestConfiguration {
  }
	
}
