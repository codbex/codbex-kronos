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
package com.codbex.kronos.engine.hdb.processors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.database.persistence.utils.DatabaseMetadataUtil;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.builders.AlterBranchingBuilder;
import org.eclipse.dirigible.database.sql.builders.table.AlterTableBuilder;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.domain.HDBTableColumn;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintPrimaryKey;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraints;

/**
 * The Class HDBTableAlterHandlerTest.
 */
@Disabled
@SpringBootTest(classes = {HDBTableAlterHandler.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
@EntityScan(value = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
@Transactional
@ExtendWith(MockitoExtension.class)
public class HDBTableAlterHandlerTest {

	/** The mock connection. */
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private Connection mockConnection;

	/** The mock sql factory. */
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private SqlFactory mockSqlFactory;

	/** The alter. */
	@Mock
	private AlterBranchingBuilder alter;

	/** The alter table builder. */
	@Mock
	private AlterTableBuilder alterTableBuilder;

	/** The database meta data. */
	@Mock
	private DatabaseMetaData databaseMetaData;

	/** The result set. */
	@Mock
	private ResultSet resultSet;

	/** The primary key. */
	private HDBTableConstraintPrimaryKey primaryKey = new HDBTableConstraintPrimaryKey();
	
	/** The constraints model. */
	private HDBTableConstraints constraintsModel = new HDBTableConstraints();
	
	/** The table model. */
	private HDBTable tableModel = new HDBTable();

	/**
	 * Open mocks.
	 */
	@BeforeEach
	public void openMocks() {
		MockitoAnnotations.openMocks(this);
	}

/**
 * Adds the columns successfully.
 *
 * @throws Exception the exception
 */
//  @Ignore("Missing verifyPrivate() in Mockito, test needs to be refactored")
	@Test
	public void addColumnsSuccessfully() throws Exception {
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());

			List<HDBTableColumn> columns = new ArrayList<>();
			columns.add(new HDBTableColumn("Id", "NVARCHAR", "32", true, false, null, null, false, tableModel));
			columns.add(new HDBTableColumn("Name", "NVARCHAR", "32", true, false, null, null, false, tableModel));
			tableModel.setColumns(columns);
			tableModel.setConstraints(constraintsModel);
			tableModel.setName("hdb_table::SampleHdbdd");
			tableModel.setSchema("MYSCHEMA");

			HDBTableAlterHandler tableAlterHandler = new HDBTableAlterHandler(mockConnection, tableModel);
			HDBTableAlterHandler handlerSpy = spy(tableAlterHandler);

			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter()).thenReturn(alter);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any()))
					.thenReturn(alterTableBuilder);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any()).add())
					.thenReturn(alterTableBuilder);
			when(alterTableBuilder.build()).thenReturn("sql");

			handlerSpy.addColumns(mockConnection);
//			verifyPrivate(handlerSpy, times(2)).invoke("executeAlterBuilder", mockConnection, alterTableBuilder);
		}
	}

	/**
	 * Adds the columns failed when primary key.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void addColumnsFailedWhenPrimaryKey() throws Exception {
		SQLException exception = Assertions.assertThrows(SQLException.class, () -> {
			try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
					MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class);
//        MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)
			) {
				configuration.when(
						() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
						.thenReturn("true");
				sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
				sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());

				List<HDBTableColumn> columns = new ArrayList<>();
				columns.add(new HDBTableColumn("Age", "INTEGER", "32", true, true, null, null, false, tableModel));
				tableModel.setColumns(columns);
				tableModel.setConstraints(constraintsModel);
				tableModel.setName("hdb_table::SampleHdbdd");
				tableModel.setSchema("MYSCHEMA");

				HDBTableAlterHandler tableAlterHandler = new HDBTableAlterHandler(mockConnection, tableModel);
				HDBTableAlterHandler handlerSpy = spy(tableAlterHandler);

				sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter()).thenReturn(alter);
				sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any()))
						.thenReturn(alterTableBuilder);
				sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any()).add())
						.thenReturn(alterTableBuilder);
//      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
//          .thenAnswer((Answer<Void>) invocation -> null);
				handlerSpy.addColumns(mockConnection);
			}
		});
	}

/**
 * Removes the columns successfully.
 *
 * @throws Exception the exception
 */
//  @Ignore("Missing verifyPrivate() in Mockito, test needs to be refactored")
	@Test
	public void removeColumnsSuccessfully() throws Exception {

		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());

			List<HDBTableColumn> columns = new ArrayList<>();
			columns.add(new HDBTableColumn("Id", "NVARCHAR", "32", true, false, null, null, false, tableModel));
			columns.add(new HDBTableColumn("Name", "NVARCHAR", "32", true, false, null, null, false, tableModel));
			tableModel.setColumns(columns);
			tableModel.setConstraints(constraintsModel);
			tableModel.setName("hdb_table::SampleHdbdd");
			tableModel.setSchema("MYSCHEMA");

			when(mockConnection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getColumns(any(), any(), any(), any())).thenReturn(resultSet);
			when(resultSet.next()).thenReturn(true).thenReturn(false);
			when(resultSet.getString("COLUMN_NAME")).thenReturn("Test");
			when(resultSet.getString("TYPE_NAME")).thenReturn("NVARCHAR");

			HDBTableAlterHandler tableAlterHandler = new HDBTableAlterHandler(mockConnection, tableModel);
			HDBTableAlterHandler handlerSpy = spy(tableAlterHandler);

			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter()).thenReturn(alter);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any()))
					.thenReturn(alterTableBuilder);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any()).drop())
					.thenReturn(alterTableBuilder);
			when(alterTableBuilder.build()).thenReturn("sql");

			handlerSpy.removeColumns(mockConnection);
			// TODO: Refactor -> Mockito doesn't have support for verifyPrivate()
			// verifyPrivate(handlerSpy, times(1)).invoke("executeAlterBuilder",
			// mockConnection, alterTableBuilder);
		}
	}

/**
 * Update columns successfully.
 *
 * @throws Exception the exception
 */
//  @Ignore("Missing verifyPrivate() in Mockito, test needs to be refactored")
	@Test
	public void updateColumnsSuccessfully() throws Exception {
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());

			List<HDBTableColumn> columns = new ArrayList<>();
			columns.add(new HDBTableColumn("Id", "NVARCHAR", "32", true, false, null, null, false, tableModel));
			columns.add(new HDBTableColumn("Name", "NVARCHAR", "32", true, false, null, null, false, tableModel));
			tableModel.setColumns(columns);
			tableModel.setConstraints(constraintsModel);
			tableModel.setName("hdb_table::SampleHdbdd");
			tableModel.setSchema("MYSCHEMA");

			when(mockConnection.getMetaData()).thenReturn(databaseMetaData);
			when(databaseMetaData.getColumns(any(), any(), any(), any())).thenReturn(resultSet);
			when(resultSet.next()).thenReturn(true).thenReturn(false);
			when(resultSet.getString("COLUMN_NAME")).thenReturn("Name");
			when(resultSet.getString("TYPE_NAME")).thenReturn("NVARCHAR");

			HDBTableAlterHandler tableAlterHandler = new HDBTableAlterHandler(mockConnection, tableModel);
			HDBTableAlterHandler handlerSpy = spy(tableAlterHandler);

			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter()).thenReturn(alter);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any()))
					.thenReturn(alterTableBuilder);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any()).alter())
					.thenReturn(alterTableBuilder);
			when(alterTableBuilder.build()).thenReturn("sql");

			handlerSpy.updateColumns(mockConnection);
			// TODO: Refactor -> Mockito doesn't have support for verifyPrivate()
			// verifyPrivate(handlerSpy, times(1)).invoke("executeAlterBuilder",
			// mockConnection, alterTableBuilder);
		}
	}

/**
 * Rebuild indeces successfully.
 *
 * @throws Exception the exception
 */
//  @Ignore("Missing verifyPrivate() in Mockito, test needs to be refactored")
	@Test
	public void rebuildIndecesSuccessfully() throws Exception {
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());
			List<HDBTableColumn> columns = new ArrayList<>();
			columns.add(new HDBTableColumn("Id", "NVARCHAR", "32", true, false, null, null, false, tableModel));
			columns.add(new HDBTableColumn("Name", "NVARCHAR", "32", true, false, null, null, false, tableModel));
			tableModel.setColumns(columns);
			tableModel.setConstraints(constraintsModel);
			tableModel.setName("hdb_table::SampleHdbdd");
			tableModel.setSchema("MYSCHEMA");

			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter()).thenReturn(alter);
			sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter().table(any()))
					.thenReturn(alterTableBuilder);
			when(alterTableBuilder.build()).thenReturn("sql");

			HDBTableAlterHandler tableAlterHandler = new HDBTableAlterHandler(mockConnection, tableModel);
			HDBTableAlterHandler handlerSpy = spy(tableAlterHandler);

			handlerSpy.rebuildIndeces(mockConnection);
			// TODO: Refactor -> Mockito doesn't have support for verifyPrivate()
			// verifyPrivate(handlerSpy, times(1)).invoke("executeAlterBuilder",
			// mockConnection, alterTableBuilder);
		}
	}

	/**
	 * Ensure primary key is unchanged successfully.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void ensurePrimaryKeyIsUnchangedSuccessfully() throws Exception {
		SQLException exception = Assertions.assertThrows(SQLException.class, () -> {
			try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
					MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class);
//        MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)
			) {
				configuration.when(
						() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
						.thenReturn("true");
				sqlFactory.when(() -> SqlFactory.getNative(mockConnection)).thenReturn(mockSqlFactory);
				sqlFactory.when(() -> SqlFactory.deriveDialect(mockConnection)).thenReturn(new HanaSqlDialect());

				List<HDBTableColumn> columns = new ArrayList<>();
				columns.add(new HDBTableColumn("Id", "NVARCHAR", "32", true, true, null, null, false, tableModel));
				columns.add(new HDBTableColumn("Name", "NVARCHAR", "32", true, false, null, null, false, tableModel));
				tableModel.setColumns(columns);
				tableModel.setConstraints(constraintsModel);
				primaryKey.setColumns(new String[] { "Id" });
				constraintsModel.setPrimaryKey(primaryKey);
				tableModel.setName("hdb_table::SampleHdbdd");
				tableModel.setSchema("MYSCHEMA");

				sqlFactory.when(() -> SqlFactory.getNative(mockConnection).alter()).thenReturn(alter);
//      problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
//          .thenAnswer((Answer<Void>) invocation -> null);
				HDBTableAlterHandler tableAlterHandler = new HDBTableAlterHandler(mockConnection, tableModel);
				HDBTableAlterHandler handlerSpy = spy(tableAlterHandler);

				handlerSpy.ensurePrimaryKeyIsUnchanged(mockConnection);
			}
		});
	}

}
