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
package com.codbex.kronos.engine.hdb.parser.hdbtablefunction;

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
import org.eclipse.dirigible.components.api.platform.ProblemsFacade;
import org.eclipse.dirigible.database.persistence.utils.DatabaseMetadataUtil;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.eclipse.dirigible.database.sql.dialects.postgres.PostgresSqlDialect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.codbex.kronos.engine.hdb.domain.HDBTableFunction;
import com.codbex.kronos.engine.hdb.parser.Constants;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelBuilder;
import com.codbex.kronos.engine.hdb.processors.HDBTableFunctionCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBTableFunctionDropProcessor;
import com.codbex.kronos.utils.CommonsUtils;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
@EntityScan(value = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
@Transactional
@ExtendWith(MockitoExtension.class)
public class HDBTableFunctionProcessorTest {

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private Connection mockConnection;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private SqlFactory mockSqlFactory;

	@Mock
	private PreparedStatement mockStatement;

	@BeforeEach
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
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");

			HDBTableFunctionCreateProcessor processorSpy = spy(HDBTableFunctionCreateProcessor.class);
			String hdbTableFunctionSample = IOUtils.toString(
					HDBTableFunctionProcessorTest.class
							.getResourceAsStream("/registry/public/tablefunction/OrderTableFunction.hdbtablefunction"),
					StandardCharsets.UTF_8);

			HDBDataStructureModelBuilder builder = new HDBDataStructureModelBuilder()
					.withContent(hdbTableFunctionSample).withType("HDBTABLEFUNCTION")
					.withName("\"MYSCHEMA\".\"hdb_view::FUNCTION_NAME\"").withSchema("MYSCHEMA");

			HDBTableFunction model = new HDBTableFunction(builder);
			String sql = Constants.HDBTABLEFUNCTION_CREATE + model.getContent();
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).exists(mockConnection, "MYSCHEMA",
					CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
					DatabaseArtifactTypes.FUNCTION)).thenReturn(doExist);

			when(mockConnection.prepareStatement(sql)).thenReturn(mockStatement);
			processorSpy.execute(mockConnection, model);

			verify(processorSpy, times(expectedTimesOfInvocation)).executeSql(sql, mockConnection);
		}
	}

	@Test
	public void executeCreateTableFunctionPostgresSQLFailed() throws Exception {
		IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
			HDBTableFunctionCreateProcessor processorSpy = spy(HDBTableFunctionCreateProcessor.class);

			HDBDataStructureModelBuilder builder = new HDBDataStructureModelBuilder()
					.withName("\"MYSCHEMA\".\"hdb_view::FUNCTION_NAME\"").withType("HDBTABLEFUNCTION");
			HDBTableFunction model = new HDBTableFunction(builder);

			try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
					MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
				sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
				sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new PostgresSqlDialect());
//      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
//          .thenAnswer((Answer<Void>) invocation -> null);

				processorSpy.execute(mockConnection, model);
			}
		});
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
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");

			HDBTableFunctionDropProcessor processorSpy = spy(HDBTableFunctionDropProcessor.class);

			HDBDataStructureModelBuilder builder = new HDBDataStructureModelBuilder()
					.withName("\"MYSCHEMA\".\"hdb_view::FUNCTION_NAME\"").withType("HDBTABLEFUNCTION");
			HDBTableFunction model = new HDBTableFunction(builder);
			String sql = Constants.HDBTABLEFUNCTION_DROP + model.getName();
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).exists(mockConnection, "MYSCHEMA",
					CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
					DatabaseArtifactTypes.FUNCTION)).thenReturn(doExist);

			when(mockConnection.prepareStatement(sql)).thenReturn(mockStatement);
			processorSpy.execute(mockConnection, model);

			verify(processorSpy, times(expectedTimesOfInvocation)).executeSql(sql, mockConnection);
		}
	}

//  @Test // (expected = IllegalStateException.class)
//  public void executeDropTableFunctionFailed() throws Exception {
//    HDBTableFunctionDropProcessor processorSpy = spy(HDBTableFunctionDropProcessor.class);
//
//    HDBDataStructureModelBuilder builder = new HDBDataStructureModelBuilder()
//        .withName("\"MYSCHEMA\".\"hdb_view::FUNCTION_NAME\"")
//        .withType("HDBTABLEFUNCTION");
//    HDBTableFunction model = new HDBTableFunction(builder);
//
//    try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
//        MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
//      sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new PostgresSqlDialect());
//      sqlFactory.when(() -> SqlFactory.getNative(mockConnection)
//          .exists(mockConnection, "MYSCHEMA", CommonsUtils.extractArtifactNameWhenSchemaIsProvided(model.getName())[1],
//              DatabaseArtifactTypes.FUNCTION)).thenReturn(true);
//      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
//          .thenAnswer((Answer<Void>) invocation -> null);
//
//      processorSpy.execute(mockConnection, model);
//    }
//  }

	/**
	 * The Class TestConfiguration.
	 */
	@EnableJpaRepositories(basePackages = "com.codbex.kronos")
	@SpringBootApplication(scanBasePackages = { "com.codbex.kronos", "org.eclipse.dirigible.components" })
	@EnableScheduling
	static class TestConfiguration {
	}

}
