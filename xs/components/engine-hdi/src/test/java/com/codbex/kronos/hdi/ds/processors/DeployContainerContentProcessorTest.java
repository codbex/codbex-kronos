/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.hdi.ds.processors;

import com.codbex.kronos.engine.hdi.ds.util.Message;
import com.codbex.kronos.engine.hdi.processors.DeployContainerContentProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.sql.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * The Class DeployContainerContentProcessorTest.
 */
@ExtendWith(MockitoExtension.class)
public class DeployContainerContentProcessorTest {

    /**
     * The mock connection.
     */
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Connection mockConnection;

    /**
     * The result set mock.
     */
    @Mock
    private ResultSet resultSetMock;

    /**
     * The result set from select mock.
     */
    @Mock
    private ResultSet resultSetFromSelectMock;

    /**
     * The prepared statement mock.
     */
    @Mock
    private PreparedStatement preparedStatementMock;

    /**
     * The callable statement mock.
     */
    @Mock
    private CallableStatement callableStatementMock;

    /**
     * The Class MockRow.
     */
    static class MockRow {

        /**
         * The row data.
         */
        String[] rowData;

        /**
         * Sets the current row data.
         *
         * @param rowData the new current row data
         */
        public void setCurrentRowData(String[] rowData) {
            this.rowData = rowData;
        }

        /**
         * Gets the column.
         *
         * @param idx the idx
         * @return the column
         */
        public String getColumn(int idx) {
            return rowData[idx - 1];
        }
    }

    /**
     * Test execute call.
     *
     * @throws SQLException the SQL exception
     */
    @Test
    public void testExecuteCall() throws SQLException {

        DeployContainerContentProcessor processorSpy = spy(DeployContainerContentProcessor.class);
        String container = "testContainer";

        String sqlMakeCall =
                "CALL " + container + "#DI.MAKE(#DEPLOY_PATHS, #UNDEPLOY_PATHS, #PATH_PARAMETERS, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?);";
        String sqlSelectFromDeployPaths = "SELECT * FROM #DEPLOY_PATHS";

        when(mockConnection.prepareCall(sqlMakeCall)).thenReturn(callableStatementMock);
        when(callableStatementMock.executeQuery()).thenReturn(resultSetMock);
        when(mockConnection.prepareStatement(sqlSelectFromDeployPaths)).thenReturn(preparedStatementMock);
        // when(preparedStatementMock.executeQuery()).thenReturn(resultSetFromSelectMock);

        String[][] result = {
                {"25", "1", "5", "HDI", "12", "12", "/test_Deploy_Container/testCalcView.hdbcalculationview", "INFO", "7868", "Success",
                        "0:0", "path", "5/12/2022"},
                {"REQUEST_ID", "ROW_ID", "LEVEL", "TYPE", "LIBRARY_ID", "PLUGIN_ID", "PATH", "SEVERITY", "MESSAGE_CODE", "MESSAGE",
                        "LOCATION", "LOCATION_PATH", "TIMESTAMP_UTC"}};
        final AtomicInteger idx = new AtomicInteger(0);
        final MockRow row = new MockRow();
        doAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                int index = idx.getAndIncrement();
                if (result.length <= index) {
                    return false;
                }
                String[] current = result[index];
                row.setCurrentRowData(current);
                return true;
            }

        }).when(resultSetMock)
          .next();

        doAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                int idx = ((Integer) args[0]).intValue();
                return row.getColumn(idx);
            }

        }).when(resultSetMock)
          .getString(anyInt());

        when(resultSetMock.next()).thenReturn(true)
                                  .thenReturn(false);
        // when(resultSetFromSelectMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        new Message(resultSetMock);
        when(callableStatementMock.getInt(1)).thenReturn(1);
        // when(resultSetFromSelectMock.getString(1)).thenReturn("test_Deploy_Container/testCalcView.hdbcalculationview");
        // Mockito.doNothing().when(processorSpy).applyArtefactState(any(), any(), any(), any(), any());

        processorSpy.executeCall(mockConnection, sqlMakeCall);
        verify(processorSpy, times(1)).executeCall(mockConnection, sqlMakeCall);
        verify(processorSpy, times(1)).parseResultSet(resultSetMock, sqlMakeCall);
        // verify(processorSpy,times(2)).applyState(any(), any(), any(), any());

    }
}
