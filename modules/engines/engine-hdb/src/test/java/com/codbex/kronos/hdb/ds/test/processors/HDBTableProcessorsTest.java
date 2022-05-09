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

import com.codbex.kronos.hdb.ds.model.hdbtable.XSKDataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.processors.table.XSKTableAlterHandler;
import com.codbex.kronos.hdb.ds.processors.table.XSKTableAlterProcessor;

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

@RunWith(MockitoJUnitRunner.class)
public class HDBTableProcessorsTest extends AbstractDirigibleTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private Connection mockConnection;

  @Mock
  private XSKDataStructureHDBTableModel mockModel;

  @Mock
  private XSKTableAlterHandler mockHandler;

  private XSKTableAlterProcessor xskTableAlterProcessor = spy(new XSKTableAlterProcessor());

  @Before
  public void openMocks() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void executeAlterSuccessfully() throws Exception {
    Mockito.doReturn(mockHandler).when(xskTableAlterProcessor).createTableAlterHandler(mockConnection, mockModel);

    doNothing().when(mockHandler).addColumns(mockConnection);
    doNothing().when(mockHandler).removeColumns(mockConnection);
    doNothing().when(mockHandler).updateColumns(mockConnection);
    doNothing().when(mockHandler).rebuildIndeces(mockConnection);
    doNothing().when(mockHandler).ensurePrimaryKeyIsUnchanged(mockConnection);

    xskTableAlterProcessor.execute(mockConnection, mockModel);

    verify(mockHandler, times(1)).addColumns(mockConnection);
    verify(mockHandler, times(1)).removeColumns(mockConnection);
    verify(mockHandler, times(1)).updateColumns(mockConnection);
    verify(mockHandler, times(1)).rebuildIndeces(mockConnection);
    verify(mockHandler, times(1)).ensurePrimaryKeyIsUnchanged(mockConnection);
  }

}
