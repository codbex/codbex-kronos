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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Connection;
import org.eclipse.dirigible.core.test.AbstractDirigibleTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.processors.table.TableAlterHandler;
import com.codbex.kronos.hdb.ds.processors.table.TableAlterProcessor;

@RunWith(MockitoJUnitRunner.class)
public class HDBTableProcessorsTest extends AbstractDirigibleTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private Connection mockConnection;

  @Mock
  private DataStructureHDBTableModel mockModel;

  @Mock
  private TableAlterHandler mockHandler;

  private TableAlterProcessor tableAlterProcessor = spy(new TableAlterProcessor());

  @Before
  public void openMocks() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void executeAlterSuccessfully() throws Exception {
    Mockito.doReturn(mockHandler).when(tableAlterProcessor).createTableAlterHandler(mockConnection, mockModel);

    doNothing().when(mockHandler).addColumns(mockConnection);
    doNothing().when(mockHandler).removeColumns(mockConnection);
    doNothing().when(mockHandler).updateColumns(mockConnection);
    doNothing().when(mockHandler).rebuildIndeces(mockConnection);
    doNothing().when(mockHandler).ensurePrimaryKeyIsUnchanged(mockConnection);

    tableAlterProcessor.execute(mockConnection, mockModel);

    verify(mockHandler, times(1)).addColumns(mockConnection);
    verify(mockHandler, times(1)).removeColumns(mockConnection);
    verify(mockHandler, times(1)).updateColumns(mockConnection);
    verify(mockHandler, times(1)).rebuildIndeces(mockConnection);
    verify(mockHandler, times(1)).ensurePrimaryKeyIsUnchanged(mockConnection);
  }

}
