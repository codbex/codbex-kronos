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
package com.codbex.kronos.engine.hdb.parser.hdbsynonym;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.database.persistence.utils.DatabaseMetadataUtil;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.builders.CreateBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.DropBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.synonym.CreateSynonymBuilder;
import org.eclipse.dirigible.database.sql.builders.synonym.DropSynonymBuilder;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.codbex.kronos.engine.hdb.domain.HDBSynonymGroup;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBSynonymCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBSynonymDropProcessor;

@SpringBootTest(classes = {HDBDataStructureModelFactory.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
@EntityScan(value = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
@Transactional
@ExtendWith(MockitoExtension.class)
public class HDBSynonymProcessorTest {

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private Connection mockConnection;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private SqlFactory mockSqlFactory;

	@Mock
	private CreateBranchingBuilder create;

	@Mock
	private CreateSynonymBuilder mockCreateSynonymBuilder;

	@Mock
	private DropBranchingBuilder drop;

	@Mock
	private DropSynonymBuilder mockDropSynonymBuilder;

	@BeforeEach
	public void openMocks() {
		MockitoAnnotations.openMocks(this);
		Configuration.set(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "true");
	}

	@Test
	public void executeCreateSynonymHANAv1Successfully() throws Exception {
		HDBSynonymCreateProcessor processorSpy = spy(HDBSynonymCreateProcessor.class);
		String hdbsynonymSample = IOUtils.toString(
				HDBSynonymProcessorTest.class.getResourceAsStream("/MySynonym.hdbsynonym"), StandardCharsets.UTF_8);

		HDBSynonymGroup model = HDBDataStructureModelFactory.parseSynonym("hdb_view/MySynonym.hdbsynonym",
				hdbsynonymSample);
		model.setName("\"MYSCHEMA\".\"hdb_view::MySynonym\"");
		String mockSQL = "testSQLScript";

		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).create()).thenReturn(create);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).create().synonym(any()))
					.thenReturn(mockCreateSynonymBuilder);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).create().synonym(any()).forSource(any()))
					.thenReturn(mockCreateSynonymBuilder);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).create().synonym(any()).forSource(any()).build())
					.thenReturn(mockSQL);
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");

//      Mockito.doNothing().when(processorSpy).applyArtefactState(any(), any(), any(), any(), any());

			processorSpy.execute(mockConnection, model);
			verify(processorSpy, times(3)).executeSql(mockSQL, mockConnection);
//      verify(processorSpy, times(3)).applyArtefactState(any(), any(), any(), eq(ArtefactState.SUCCESSFUL_CREATE), any());
		}
	}

//	@Test
//	public void executeCreateSynonymPostgresSQLFailed() throws Exception {
//		IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
//			HDBSynonymCreateProcessor processorSpy = spy(HDBSynonymCreateProcessor.class);
//			String hdbsynonymSample = IOUtils.toString(
//					HDBSynonymProcessorTest.class.getResourceAsStream("/MySynonym.hdbsynonym"), StandardCharsets.UTF_8);
//
//			HDBSynonymGroup model = HDBDataStructureModelFactory.parseSynonym("hdb_view/MySynonym.hdbsynonym",
//					hdbsynonymSample);
//			model.setName("\"MYSCHEMA\".\"hdb_view::MySynonym\"");
//
//			try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
////        MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)
//			) {
//				sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
//				sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new PostgresSqlDialect());
////      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
////          .thenAnswer((Answer<Void>) invocation -> null);
////
////      Mockito.doNothing().when(processorSpy).applyArtefactState(any(), any(), any(), any(), any());
//
//				processorSpy.execute(mockConnection, model);
//			}
//		});
//	}

	@Test
	public void executeDropSynonymSuccessfully() throws Exception {
		HDBSynonymDropProcessor processorSpy = spy(HDBSynonymDropProcessor.class);
		String hdsynonymSample = IOUtils.toString(
				HDBSynonymProcessorTest.class.getResourceAsStream("/MySynonym.hdbsynonym"), StandardCharsets.UTF_8);

		HDBSynonymGroup model = HDBDataStructureModelFactory.parseSynonym("hdb_view/MySynonym.hdbsynonym",
				hdsynonymSample);
		model.setName("\"MYSCHEMA\".\"hdb_view::MySynonym\"");
		String mockSQL = "testSQLScript";

		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).exists(mockConnection, "LL", "SY_DUMMY",
					DatabaseArtifactTypes.SYNONYM)).thenReturn(true);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).exists(mockConnection, null,
					"PAL_TRIPLE_EXPSMOOTH", DatabaseArtifactTypes.SYNONYM)).thenReturn(true);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).exists(mockConnection, null, "PROCEDURES",
					DatabaseArtifactTypes.SYNONYM)).thenReturn(true);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).drop()).thenReturn(drop);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).drop().synonym(any()))
					.thenReturn(mockDropSynonymBuilder);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).drop().synonym(any()).build())
					.thenReturn(mockSQL);
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");

//      Mockito.doNothing().when(processorSpy).applyArtefactState(any(), any(), any(), any(), any());

			processorSpy.execute(mockConnection, model);
			verify(processorSpy, times(3)).executeSql(mockSQL, mockConnection);
//      verify(processorSpy, times(3)).applyArtefactState(any(), any(), any(), eq(ArtefactState.SUCCESSFUL_DELETE), any());
		}
	}
}
