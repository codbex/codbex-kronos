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
package com.codbex.kronos.hdb.ds.test.processors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import org.eclipse.dirigible.api.v3.problems.ProblemsFacade;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.test.AbstractDirigibleTest;
import org.eclipse.dirigible.database.ds.model.IDataStructureModel;
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.builders.AlterBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.CreateBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.DropBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.sequence.AlterSequenceBuilder;
import org.eclipse.dirigible.database.sql.builders.sequence.CreateSequenceBuilder;
import org.eclipse.dirigible.database.sql.builders.sequence.DropSequenceBuilder;
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

import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.hdbsequence.DataStructureHDBSequenceModel;
import com.codbex.kronos.hdb.ds.processors.hdbsequence.HDBSequenceCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.hdbsequence.HDBSequenceDropProcessor;
import com.codbex.kronos.hdb.ds.processors.hdbsequence.HDBSequenceUpdateProcessor;

@RunWith(MockitoJUnitRunner.class)
public class HDBSequenceProcessorsTest extends AbstractDirigibleTest {

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private Connection mockConnection;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private SqlFactory mockSqlFactory;

	@Mock
	private CreateBranchingBuilder create;

	@Mock
	private AlterBranchingBuilder alter;

	@Mock
	private DropBranchingBuilder drop;

	@Mock
	private CreateSequenceBuilder mockCreateSequenceBuilder;

	@Mock
	private AlterSequenceBuilder mockAlterSequenceBuilder;

	@Mock
	private DropSequenceBuilder mockDropSequenceBuilder;

	@Before
	public void openMocks() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void executeCreateSuccessfully() throws Exception {
		HDBSequenceCreateProcessor spyProccessor = spy(HDBSequenceCreateProcessor.class);

		DataStructureHDBSequenceModel mockModel = mock(DataStructureHDBSequenceModel.class);
		String sql = "TestExecuteCreateSuccessfully";
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class)) {
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
			sqlFactory.when(() -> mockModel.getName()).thenReturn("\"MYSCHEMA\".\"hdb_sequence::SampleSequence_HanaXSClassic\"");
			sqlFactory.when(() -> mockModel.getDBContentType()).thenReturn(DBContentType.XS_CLASSIC);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).create()).thenReturn(create);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).create().sequence(any())).thenReturn(mockCreateSequenceBuilder);
			when(mockModel.getResetBy()).thenReturn("LL");
			when(mockCreateSequenceBuilder.start(anyInt())).thenReturn(mockCreateSequenceBuilder);
			when(mockCreateSequenceBuilder.start(anyInt()).increment(anyInt())).thenReturn(mockCreateSequenceBuilder);
			when(mockCreateSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt())).thenReturn(mockCreateSequenceBuilder);
			when(mockCreateSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt()).nomaxvalue(anyBoolean())).thenReturn(mockCreateSequenceBuilder);
			when(mockCreateSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt()).nomaxvalue(anyBoolean()).minvalue(anyInt())).thenReturn(mockCreateSequenceBuilder);
			when(mockCreateSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt()).nomaxvalue(anyBoolean()).minvalue(anyInt()).nominvalue(anyBoolean())).thenReturn(mockCreateSequenceBuilder);
			when(mockCreateSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt()).nomaxvalue(anyBoolean()).minvalue(anyInt()).nominvalue(anyBoolean()).cycles(anyBoolean())).thenReturn(mockCreateSequenceBuilder);
			when(mockCreateSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt()).nomaxvalue(anyBoolean()).minvalue(anyInt()).nominvalue(anyBoolean()).cycles(anyBoolean()).resetBy(anyString())).thenReturn(mockCreateSequenceBuilder);
			when(mockCreateSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt()).nomaxvalue(anyBoolean()).minvalue(anyInt()).nominvalue(anyBoolean()).cycles(anyBoolean()).resetBy(anyString()).build()).thenReturn(sql);
			spyProccessor.execute(mockConnection, mockModel);

			verify(spyProccessor, times(1)).executeSql(sql, mockConnection);
		}

	}

	@Test
	public void executeUpdateSuccessfully() throws SQLException {
		HDBSequenceUpdateProcessor spyProccessor = spy(HDBSequenceUpdateProcessor.class);
		DataStructureHDBSequenceModel mockModel = mock(DataStructureHDBSequenceModel.class);
		String sql = "TestExecuteUpdateSuccessfully";

		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class)) {
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter()).thenReturn(alter);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().sequence(any())).thenReturn(mockAlterSequenceBuilder);
			when(mockModel.getName()).thenReturn("\"MYSCHEMA\".\"hdb_sequence::SampleSequence_HanaXSClassic\"");
			when(mockModel.getDBContentType()).thenReturn(DBContentType.XS_CLASSIC);
			when(mockModel.getResetBy()).thenReturn("LL");
			when(mockAlterSequenceBuilder.start(anyInt())).thenReturn(mockAlterSequenceBuilder);
			when(mockAlterSequenceBuilder.start(anyInt()).increment(anyInt())).thenReturn(mockAlterSequenceBuilder);
			when(mockAlterSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt())).thenReturn(mockAlterSequenceBuilder);
			when(mockAlterSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt()).nomaxvalue(anyBoolean())).thenReturn(mockAlterSequenceBuilder);
			when(mockAlterSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt()).nomaxvalue(anyBoolean()).minvalue(anyInt())).thenReturn(mockAlterSequenceBuilder);
			when(mockAlterSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt()).nomaxvalue(anyBoolean()).minvalue(anyInt()).nominvalue(anyBoolean())).thenReturn(mockAlterSequenceBuilder);
			when(mockAlterSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt()).nomaxvalue(anyBoolean()).minvalue(anyInt()).nominvalue(anyBoolean()).cycles(anyBoolean())).thenReturn(mockAlterSequenceBuilder);
			when(mockAlterSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt()).nomaxvalue(anyBoolean()).minvalue(anyInt()).nominvalue(anyBoolean()).cycles(anyBoolean()).resetBy(anyString())).thenReturn(mockAlterSequenceBuilder);
			when(mockAlterSequenceBuilder.start(anyInt()).increment(anyInt()).maxvalue(anyInt()).nomaxvalue(anyBoolean()).minvalue(anyInt()).nominvalue(anyBoolean()).cycles(anyBoolean()).resetBy(anyString()).build()).thenReturn(sql);
			spyProccessor.execute(mockConnection, mockModel);

			verify(spyProccessor, times(1)).executeSql(sql, mockConnection);
		}
	}

	@Test
	public void executeDropSuccessfully() throws SQLException {
		HDBSequenceDropProcessor spyProccessor = spy(HDBSequenceDropProcessor.class);
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			DataStructureHDBSequenceModel mockModel = mock(DataStructureHDBSequenceModel.class);
			String sql = "TestExecuteDropSuccessfully";
			configuration.when(() -> Configuration.get(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("false");
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);

			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
			when(mockModel.getName()).thenReturn("\"MYSCHEMA\".\"hdb_sequence::SampleSequence_HanaXSClassic\"");
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).exists(mockConnection, mockModel.getName(), DatabaseArtifactTypes.SEQUENCE)).thenReturn(true);
			when(mockModel.getDBContentType()).thenReturn(DBContentType.XS_CLASSIC);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).drop()).thenReturn(drop);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).drop().sequence(any())).thenReturn(mockDropSequenceBuilder);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).drop().sequence(any()).build()).thenReturn(sql);

			spyProccessor.execute(mockConnection, mockModel);

			verify(spyProccessor, times(1)).executeSql(sql, mockConnection);
		}

	}

	@Test(expected = IllegalStateException.class)
	public void executeCreateFailed() throws Exception {
		HDBSequenceCreateProcessor spyProccessor = spy(HDBSequenceCreateProcessor.class);

		DataStructureHDBSequenceModel mockModel = mock(DataStructureHDBSequenceModel.class);
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
			when(mockModel.getName()).thenReturn("\"MYSCHEMA\".\"hdb_sequence::SampleSequence_HanaXSClassic\"");
			when(mockModel.getDBContentType()).thenReturn(DBContentType.OTHERS);
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new PostgresSqlDialect());
			problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenAnswer((Answer<Void>) invocation -> null);
			spyProccessor.execute(mockConnection, mockModel);
		}
	}

	@Test(expected = IllegalStateException.class)
	public void executeUpdateFailed() throws Exception {
		HDBSequenceUpdateProcessor spyProccessor = spy(HDBSequenceUpdateProcessor.class);
		DataStructureHDBSequenceModel mockModel = mock(DataStructureHDBSequenceModel.class);
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
			when(mockModel.getName()).thenReturn("\"MYSCHEMA\".\"hdb_sequence::SampleSequence_HanaXSClassic\"");
			when(mockModel.getDBContentType()).thenReturn(DBContentType.OTHERS);
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new PostgresSqlDialect());
			problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenAnswer((Answer<Void>) invocation -> null);
			spyProccessor.execute(mockConnection, mockModel);
		}
	}

	@Test(expected = IllegalStateException.class)
	public void executeDropFailed() throws Exception {
		HDBSequenceDropProcessor spyProccessor = spy(HDBSequenceDropProcessor.class);
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class);
				MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {

			DataStructureHDBSequenceModel mockModel = mock(DataStructureHDBSequenceModel.class);
			when(mockModel.getName()).thenReturn("\"MYSCHEMA\".\"hdb_sequence::SampleSequence_HanaXSClassic\"");
			configuration.when(() -> Configuration.get(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false")).thenReturn("false");
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).exists(mockConnection, mockModel.getName(), DatabaseArtifactTypes.SEQUENCE)).thenReturn(true);
			when(mockModel.getDBContentType()).thenReturn(DBContentType.OTHERS);
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new PostgresSqlDialect());
			problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenAnswer((Answer<Void>) invocation -> null);
			spyProccessor.execute(mockConnection, mockModel);
		}
	}

}
