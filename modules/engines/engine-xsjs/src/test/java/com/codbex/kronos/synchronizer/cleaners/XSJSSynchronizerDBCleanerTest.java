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
package com.codbex.kronos.synchronizer.cleaners;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.eclipse.dirigible.database.api.wrappers.WrappedDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.codbex.kronos.synchronizer.XSJSLibSynchronizer;
import com.sap.db.jdbcext.wrapper.WrappedPreparedStatement;

@RunWith(MockitoJUnitRunner.class)
public class XSJSSynchronizerDBCleanerTest {

  @Test
  public void cleanDBEntryForResourceTest() throws SQLException {
    PreparedStatement preparedStatementMock = createPreparedStatementMock();
    DataSource dataSourceMock = createDataSourceMock(preparedStatementMock);

    XSJSLibSynchronizerDBCleaner cleaner = new XSJSLibSynchronizerDBCleaner(dataSourceMock);
    cleaner.cleanup("testFolder/abc.xsjslib");

    verify(preparedStatementMock, times(1)).setString(1, "testFolder/abc.xsjslib%");
    verify(preparedStatementMock, times(1)).executeUpdate();
  }

  @Test
  public void cleanDBEntryForCollectionTest() throws SQLException {
    PreparedStatement preparedStatementMock = createPreparedStatementMock();
    DataSource dataSourceMock = createDataSourceMock(preparedStatementMock);

    XSJSLibSynchronizerDBCleaner cleaner = new XSJSLibSynchronizerDBCleaner(dataSourceMock);
    cleaner.cleanup("testFolder/");

    verify(preparedStatementMock, times(1)).setString(1, "testFolder/%");
    verify(preparedStatementMock, times(1)).executeUpdate();
  }

  private PreparedStatement createPreparedStatementMock() {
    return mock(WrappedPreparedStatement.class);
  }

  private DataSource createDataSourceMock(PreparedStatement preparedStatementMock) throws SQLException {
    Connection connectionMock = mock(Connection.class);
    when(connectionMock.prepareStatement("DELETE FROM \""
        + XSJSLibSynchronizer.XSJSLIB_SYNCHRONIZER_STATE_TABLE_NAME
        + "\" WHERE \"LOCATION\" LIKE ?")).thenReturn(preparedStatementMock);

    DataSource dataSourceMock = mock(WrappedDataSource.class);
    when(dataSourceMock.getConnection()).thenReturn(connectionMock);

    return dataSourceMock;
  }
}
