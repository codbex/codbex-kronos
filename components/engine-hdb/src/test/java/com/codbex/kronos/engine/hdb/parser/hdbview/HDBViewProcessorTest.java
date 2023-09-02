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
package com.codbex.kronos.engine.hdb.parser.hdbview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.components.api.platform.ProblemsFacade;
import org.eclipse.dirigible.database.persistence.utils.DatabaseMetadataUtil;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.builders.CreateBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.DropBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.synonym.DropSynonymBuilder;
import org.eclipse.dirigible.database.sql.builders.view.CreateViewBuilder;
import org.eclipse.dirigible.database.sql.builders.view.DropViewBuilder;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.eclipse.dirigible.database.sql.dialects.postgres.PostgresSqlDialect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.codbex.kronos.engine.hdb.domain.HDBView;
import com.codbex.kronos.engine.hdb.parser.Constants;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBViewCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBViewDropProcessor;

@Disabled
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
@EntityScan(value = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
@Transactional
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class HDBViewProcessorTest {

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private Connection mockConnection;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private SqlFactory mockSqlFactory;

	@Mock
	private CreateBranchingBuilder create;

	@Mock
	private CreateViewBuilder mockCreateViewBuilder;

	@Mock
	private DropBranchingBuilder drop;

	@Mock
	private DropViewBuilder mockDropViewBuilder;

	@Mock
	private DropSynonymBuilder mockDropSynonymBuilder;

	@InjectMocks
	private HDBViewCreateProcessor processor = new HDBViewCreateProcessor();

	@BeforeEach
	public void openMocks() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void executeCreateViewHANAv1Successfully() throws Exception {
		HDBViewCreateProcessor processorSpy = spy(HDBViewCreateProcessor.class);
		String sample = IOUtils.toString(HDBViewProcessorTest.class.getResourceAsStream("/ItemsByOrderHANAv1.hdbview"),
				StandardCharsets.UTF_8);

		HDBView model = HDBDataStructureModelFactory.parseView("hdb_view.db.a1/path/ItemsByOrderHANAv1.hdbview",
				sample);
		String mockSQL = "testSQLScript";
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).create()).thenReturn(create);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).create().view(any()))
					.thenReturn(mockCreateViewBuilder);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).create().view(any()).asSelect(any()))
					.thenReturn(mockCreateViewBuilder);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).create().view(any()).asSelect(any()).build())
					.thenReturn(mockSQL);
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");

//      Mockito.doNothing().when(processorSpy).applyArtefactState(any(), any(), any(), any(), any());

			processorSpy.execute(mockConnection, model);
			assertEquals(model.isClassic(), true);
			verify(processorSpy, times(1)).executeSql(mockSQL, mockConnection);
//      verify(processorSpy, times(1)).applyArtefactState(any(), any(), any(), eq(ArtefactState.SUCCESSFUL_CREATE), any());
		}
	}

	@Test
	public void executeCreateViewHANAv2Successfully() throws Exception {
		HDBViewCreateProcessor processorSpy = spy(HDBViewCreateProcessor.class);
		String sample = IOUtils.toString(HDBViewProcessorTest.class.getResourceAsStream("/ItemsByOrderHANAv2.hdbview"),
				StandardCharsets.UTF_8);

		HDBView model = HDBDataStructureModelFactory.parseView("hdb_view.db/ItemsByOrderHANAv2.hdbview", sample);
		model.setQuery(sample);
		String sql = Constants.HDBVIEW_CREATE + model.getQuery();

		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).exists(mockConnection, model.getName(),
					DatabaseArtifactTypes.VIEW)).thenReturn(false);
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");

//      Mockito.doNothing().when(processorSpy).applyArtefactState(any(), any(), any(), any(), any());

			processorSpy.execute(mockConnection, model);
			assertEquals(model.isClassic(), false);
			verify(processorSpy, times(1)).executeSql(sql, mockConnection);
//      verify(processorSpy, times(1)).applyArtefactState(any(), any(), any(), eq(ArtefactState.SUCCESSFUL_CREATE), any());
		}
	}

	@Test
	public void executeCreateViewPostgreSQLFailed() throws Exception {
		IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
			HDBViewCreateProcessor processorSpy = spy(HDBViewCreateProcessor.class);
			String sample = IOUtils.toString(
					HDBViewProcessorTest.class.getResourceAsStream("/ItemsByOrderHANAv2.hdbview"),
					StandardCharsets.UTF_8);

			HDBView model = HDBDataStructureModelFactory.parseView("hdb_view.db/ItemsByOrderHANAv2.hdbview", sample);
			model.setContent(sample);

			try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
					MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
				sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
				sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new PostgresSqlDialect());
//      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
//          .thenAnswer((Answer<Void>) invocation -> null);
//      Mockito.doNothing().when(processorSpy).applyArtefactState(any(), any(), any(), any(), any());
				processorSpy.execute(mockConnection, model);
			}
		});
	}

	@Test
	public void executeDropViewSuccessfully() throws Exception {
		HDBViewDropProcessor processorSpy = spy(HDBViewDropProcessor.class);
		String sample = IOUtils.toString(HDBViewProcessorTest.class.getResourceAsStream("/ItemsByOrderHANAv1.hdbview"),
				StandardCharsets.UTF_8);

		HDBView model = HDBDataStructureModelFactory.parseView("hdb_view/ItemsByOrderHANAv1.hdbview", sample);
		String mockSQL = Constants.HDBVIEW_DROP + model.getName();
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).exists(mockConnection,
					"MYSCHEMA.hdb_view::ItemsByOrderHANAv1", DatabaseArtifactTypes.VIEW)).thenReturn(true);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).drop()).thenReturn(drop);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).drop().view(any()))
					.thenReturn(mockDropViewBuilder);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).drop().publicSynonym(any()))
					.thenReturn(mockDropSynonymBuilder);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).drop().view(any()).build()).thenReturn(mockSQL);
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");
//      Mockito.doNothing().when(processorSpy).applyArtefactState(any(), any(), any(), any(), any());

			processorSpy.execute(mockConnection, model);
			verify(processorSpy, times(1)).executeSql(mockSQL, mockConnection);
//      verify(processorSpy, times(1)).applyArtefactState(any(), any(), any(), eq(ArtefactState.SUCCESSFUL_DELETE), any());
		}
	}

	/**
	 * The Class TestConfiguration.
	 */
	@EnableJpaRepositories(basePackages = "com.codbex.kronos")
	@SpringBootApplication(scanBasePackages = { "com.codbex.kronos", "org.eclipse.dirigible.components" })
	@EnableScheduling
	static class TestConfiguration {
	}

}
