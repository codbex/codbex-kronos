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
package com.codbex.kronos.engine.hdb.parser.hdbtabletype;

import java.sql.Connection;
import java.sql.SQLException;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.codbex.kronos.engine.hdb.processors.HDBTableTypeDropProcessor;

import static com.codbex.kronos.engine.hdb.api.IDataStructureModel.TYPE_HDB_TABLE_TYPE;
import static com.codbex.kronos.utils.CommonsConstants.HDB_TABLE_TYPE_PARSER;

//@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBTableTypeDropProcessorTest {

//  private static final String TABLE_TYPE_NAME = "TableType";
//  private static final String SCHEMA_NAME = "SchemaName";
//  private static final String ESCAPED_TABLE_TYPE_NAME = "ESCAPED_TABLE_TYPE_NAME";
//  private static final String SQL_TO_DROP_TABLE_TYPE = "SQL TO DROP TABLE TYPE";
//  private HDBTableTypeDropProcessor dropProcessorSpy;
//
//  @Mock
//  private HDBSynonymRemover synonymRemoverMock;
//
//  @Mock
//  private Connection connectionMock;
//
//  private DataStructureHDBTableTypeModel model;
//
//  @Before
//  public void setUp() {
//    dropProcessorSpy = Mockito.spy(new HDBTableTypeDropProcessor(synonymRemoverMock));
//
//    model = new DataStructureHDBTableTypeModel();
//    model.setName(TABLE_TYPE_NAME);
//    model.setSchema(SCHEMA_NAME);
//    model.setType(TYPE_HDB_TABLE_TYPE);
//  }
//
//  @Test
//  public void testExecuteTableTypeDoesNotExist() throws SQLException {
//    Mockito.doReturn(true).when(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
//
//    dropProcessorSpy.execute(connectionMock, model);
//    Mockito.verify(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
//    Mockito.verify(synonymRemoverMock).removePublicSynonym(connectionMock, model.getSchema(), model.getName());
//    Mockito.verifyNoInteractions(connectionMock);
//    Mockito.verifyNoMoreInteractions(synonymRemoverMock);
//  }
//
//  @Test
//  public void testExecuteTableType() throws SQLException {
//    Mockito.doReturn(false).when(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
//    Mockito.doReturn(ESCAPED_TABLE_TYPE_NAME).when(dropProcessorSpy).escapeTableTypeName(connectionMock, model);
//    Mockito.doReturn(SQL_TO_DROP_TABLE_TYPE).when(dropProcessorSpy).getDropTableTypeSQL(connectionMock, ESCAPED_TABLE_TYPE_NAME);
//    Mockito.doNothing().when(dropProcessorSpy).executeSql(SQL_TO_DROP_TABLE_TYPE, connectionMock);
//
//    dropProcessorSpy.execute(connectionMock, model);
//
//    Mockito.verify(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
//    Mockito.verify(dropProcessorSpy).escapeTableTypeName(connectionMock, model);
//    Mockito.verify(dropProcessorSpy).executeSql(SQL_TO_DROP_TABLE_TYPE, connectionMock);
//    Mockito.verify(dropProcessorSpy).getDropTableTypeSQL(connectionMock, ESCAPED_TABLE_TYPE_NAME);
//    Mockito.verify(synonymRemoverMock).removePublicSynonym(connectionMock, model.getSchema(), model.getName());
//    Mockito.verifyNoMoreInteractions(connectionMock);
//  }
//
//  @Test
//  public void testExecuteNoTableTypeSupportWithSQLException() throws SQLException {
//    Mockito.doReturn(false).when(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
//    Mockito.doReturn(ESCAPED_TABLE_TYPE_NAME).when(dropProcessorSpy).escapeTableTypeName(connectionMock, model);
//    Mockito.doReturn(SQL_TO_DROP_TABLE_TYPE).when(dropProcessorSpy).getDropTableTypeSQL(connectionMock, ESCAPED_TABLE_TYPE_NAME);
//    SQLException sqlException = new SQLException();
//    Mockito.doThrow(sqlException).when(dropProcessorSpy).executeSql(SQL_TO_DROP_TABLE_TYPE, connectionMock);
//    Mockito.doNothing().when(dropProcessorSpy).processException(model, HDB_TABLE_TYPE_PARSER, sqlException);
//
//    dropProcessorSpy.execute(connectionMock, model);
//
//    Mockito.verify(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
//    Mockito.verify(dropProcessorSpy).escapeTableTypeName(connectionMock, model);
//    Mockito.verify(dropProcessorSpy).executeSql(SQL_TO_DROP_TABLE_TYPE, connectionMock);
//    Mockito.verify(dropProcessorSpy).getDropTableTypeSQL(connectionMock, ESCAPED_TABLE_TYPE_NAME);
//    Mockito.verify(dropProcessorSpy).processException(model, HDB_TABLE_TYPE_PARSER, sqlException);
//    Mockito.verify(synonymRemoverMock).removePublicSynonym(connectionMock, model.getSchema(), model.getName());
//    Mockito.verifyNoMoreInteractions(connectionMock);
//  }
//
//  @Test
//  public void testExecuteNoTableTypeSupportInDB() throws SQLException {
//    Mockito.doReturn(false).when(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
//    Mockito.doReturn(ESCAPED_TABLE_TYPE_NAME).when(dropProcessorSpy).escapeTableTypeName(connectionMock, model);
//
//    IllegalStateException exception = new IllegalStateException();
//    Mockito.doThrow(exception).when(dropProcessorSpy).getDropTableTypeSQL(connectionMock, ESCAPED_TABLE_TYPE_NAME);
//    Mockito.doNothing().when(dropProcessorSpy).processException(model, HDB_TABLE_TYPE_PARSER, exception);
//
//    dropProcessorSpy.execute(connectionMock, model);
//
//    Mockito.verify(dropProcessorSpy).tableTypeDoesNotExist(connectionMock, model);
//    Mockito.verify(dropProcessorSpy).escapeTableTypeName(connectionMock, model);
//    Mockito.verify(dropProcessorSpy, Mockito.times(0)).executeSql(Mockito.any(String.class), Mockito.any(Connection.class));
//    Mockito.verify(dropProcessorSpy).getDropTableTypeSQL(connectionMock, ESCAPED_TABLE_TYPE_NAME);
//    Mockito.verify(dropProcessorSpy).processException(model, HDB_TABLE_TYPE_PARSER, exception);
//    Mockito.verify(synonymRemoverMock).removePublicSynonym(connectionMock, model.getSchema(), model.getName());
//    Mockito.verifyNoMoreInteractions(connectionMock);
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