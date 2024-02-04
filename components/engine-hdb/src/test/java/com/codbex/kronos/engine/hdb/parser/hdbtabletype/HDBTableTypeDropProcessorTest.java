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
package com.codbex.kronos.engine.hdb.parser.hdbtabletype;

import static com.codbex.kronos.engine.hdb.api.IDataStructureModel.TYPE_HDB_TABLE_TYPE;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.database.persistence.utils.DatabaseMetadataUtil;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.codbex.kronos.engine.hdb.domain.HDBTableType;
import com.codbex.kronos.engine.hdb.parser.HDBSynonymRemover;
import com.codbex.kronos.engine.hdb.processors.HDBTableTypeDropProcessor;

import jakarta.transaction.Transactional;

/**
 * The Class HDBTableTypeDropProcessorTest.
 */
@SpringBootTest(classes = {HDBTableTypeDropProcessor.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
@EntityScan(value = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
@Transactional
public class HDBTableTypeDropProcessorTest {

	/** The Constant TABLE_TYPE_NAME. */
	private static final String TABLE_TYPE_NAME = "TableType";
	
	/** The Constant SCHEMA_NAME. */
	private static final String SCHEMA_NAME = "SchemaName";
	
	/** The Constant ESCAPED_TABLE_TYPE_NAME. */
	private static final String ESCAPED_TABLE_TYPE_NAME = "ESCAPED_TABLE_TYPE_NAME";
	
	/** The Constant SQL_TO_DROP_TABLE_TYPE. */
	private static final String SQL_TO_DROP_TABLE_TYPE = "SQL TO DROP TABLE TYPE";
	
	/** The drop processor spy. */
	private HDBTableTypeDropProcessor dropProcessorSpy;

	/** The synonym remover mock. */
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private HDBSynonymRemover synonymRemoverMock;

	/** The connection mock. */
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private Connection connectionMock;

	/** The mock sql factory. */
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private SqlFactory mockSqlFactory;

	/** The mock statement. */
	@Mock
	private PreparedStatement mockStatement;

	/**
	 * Open mocks.
	 */
	@BeforeEach
	public void openMocks() {
		MockitoAnnotations.openMocks(this);
	}

	/** The model. */
	private HDBTableType model;

	/**
	 * Sets the up.
	 */
	@BeforeEach
	public void setUp() {
		dropProcessorSpy = Mockito.spy(new HDBTableTypeDropProcessor());

		model = new HDBTableType();
		model.setName(TABLE_TYPE_NAME);
		model.setSchema(SCHEMA_NAME);
		model.setType(TYPE_HDB_TABLE_TYPE);
		
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test execute table type does not exist.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	public void testExecuteTableTypeDoesNotExist() throws SQLException {
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			sqlFactory.when(() -> SqlFactory.getNative(connectionMock)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.deriveDialect(connectionMock)).thenReturn(new HanaSqlDialect());
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");
			Mockito.doReturn(true).when(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
	
			dropProcessorSpy.execute(connectionMock, model);
			Mockito.verify(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
//			Mockito.verify(synonymRemoverMock).removePublicSynonym(connectionMock, model.getSchema(), model.getName());
			// Mockito.verifyNoInteractions(connectionMock);
			Mockito.verifyNoMoreInteractions(synonymRemoverMock);
		}
	}

	/**
	 * Test execute table type.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	public void testExecuteTableType() throws SQLException {
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			sqlFactory.when(() -> SqlFactory.getNative(connectionMock)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.deriveDialect(connectionMock)).thenReturn(new HanaSqlDialect());
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");
			Mockito.doReturn(false).when(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
			Mockito.doReturn(ESCAPED_TABLE_TYPE_NAME).when(dropProcessorSpy).escapeTableTypeName(connectionMock, model);
			Mockito.doReturn(SQL_TO_DROP_TABLE_TYPE).when(dropProcessorSpy).getDropTableTypeSQL(connectionMock,
					ESCAPED_TABLE_TYPE_NAME);
			Mockito.doNothing().when(dropProcessorSpy).executeSql(SQL_TO_DROP_TABLE_TYPE, connectionMock);

			dropProcessorSpy.execute(connectionMock, model);

			Mockito.verify(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
			Mockito.verify(dropProcessorSpy).escapeTableTypeName(connectionMock, model);
			Mockito.verify(dropProcessorSpy).executeSql(SQL_TO_DROP_TABLE_TYPE, connectionMock);
			Mockito.verify(dropProcessorSpy).getDropTableTypeSQL(connectionMock, ESCAPED_TABLE_TYPE_NAME);
//			Mockito.verify(synonymRemoverMock).removePublicSynonym(connectionMock, model.getSchema(), model.getName());
			// Mockito.verifyNoMoreInteractions(connectionMock);
		}
	}

	/**
	 * Test execute no table type support with SQL exception.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	public void testExecuteNoTableTypeSupportWithSQLException() throws SQLException {
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			sqlFactory.when(() -> SqlFactory.getNative(connectionMock)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.deriveDialect(connectionMock)).thenReturn(new HanaSqlDialect());
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");
			Mockito.doReturn(false).when(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
			Mockito.doReturn(ESCAPED_TABLE_TYPE_NAME).when(dropProcessorSpy).escapeTableTypeName(connectionMock, model);
			Mockito.doReturn(SQL_TO_DROP_TABLE_TYPE).when(dropProcessorSpy).getDropTableTypeSQL(connectionMock,
					ESCAPED_TABLE_TYPE_NAME);
			SQLException sqlException = new SQLException();
			Mockito.doThrow(sqlException).when(dropProcessorSpy).executeSql(SQL_TO_DROP_TABLE_TYPE, connectionMock);
			// Mockito.doNothing().when(dropProcessorSpy).processException(model, CommonsConstants.HDB_TABLE_TYPE_PARSER, sqlException);

			try {
				dropProcessorSpy.execute(connectionMock, model);
				fail("Expected to throw SQLException");
			} catch(SQLException e) {
				// Do nothing
			}
	
	
			Mockito.verify(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
			Mockito.verify(dropProcessorSpy).escapeTableTypeName(connectionMock, model);
			Mockito.verify(dropProcessorSpy).executeSql(SQL_TO_DROP_TABLE_TYPE, connectionMock);
			Mockito.verify(dropProcessorSpy).getDropTableTypeSQL(connectionMock, ESCAPED_TABLE_TYPE_NAME);
			// Mockito.verify(dropProcessorSpy).processException(model, CommonsConstants.HDB_TABLE_TYPE_PARSER, sqlException);
//			Mockito.verify(synonymRemoverMock).removePublicSynonym(connectionMock, model.getSchema(), model.getName());
			// Mockito.verifyNoMoreInteractions(connectionMock);
		}
	}

	/**
	 * Test execute no table type support in DB.
	 *
	 * @throws SQLException the SQL exception
	 */
	@Test
	public void testExecuteNoTableTypeSupportInDB() throws SQLException {
		try (MockedStatic<SqlFactory> sqlFactory = Mockito.mockStatic(SqlFactory.class);
				MockedStatic<Configuration> configuration = Mockito.mockStatic(Configuration.class)) {
			sqlFactory.when(() -> SqlFactory.getNative(connectionMock)).thenReturn(mockSqlFactory);
			sqlFactory.when(() -> SqlFactory.deriveDialect(connectionMock)).thenReturn(new HanaSqlDialect());
			configuration.when(
					() -> Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"))
					.thenReturn("true");
			Mockito.doReturn(false).when(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
			Mockito.doReturn(ESCAPED_TABLE_TYPE_NAME).when(dropProcessorSpy).escapeTableTypeName(connectionMock, model);
	
			IllegalStateException exception = new IllegalStateException();
			Mockito.doThrow(exception).when(dropProcessorSpy).getDropTableTypeSQL(connectionMock, ESCAPED_TABLE_TYPE_NAME);
			// Mockito.doNothing().when(dropProcessorSpy).processException(model, CommonsConstants.HDB_TABLE_TYPE_PARSER, exception);
	
			try {
				dropProcessorSpy.execute(connectionMock, model);
				fail("Expected to throw IllegalStateException");
			} catch (IllegalStateException e) {
				// Do nothing
			}
	
			Mockito.verify(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
			Mockito.verify(dropProcessorSpy).escapeTableTypeName(connectionMock, model);
			Mockito.verify(dropProcessorSpy, Mockito.times(0)).executeSql(Mockito.any(String.class),
					Mockito.any(Connection.class));
			Mockito.verify(dropProcessorSpy).getDropTableTypeSQL(connectionMock, ESCAPED_TABLE_TYPE_NAME);
			// Mockito.verify(dropProcessorSpy).processException(model, CommonsConstants.HDB_TABLE_TYPE_PARSER, exception);
//			Mockito.verify(synonymRemoverMock).removePublicSynonym(connectionMock, model.getSchema(), model.getName());
			// Mockito.verifyNoMoreInteractions(connectionMock);
		}
	}

}