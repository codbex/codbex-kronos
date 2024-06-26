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
package com.codbex.kronos.engine.hdb.parser.hdbtable;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.sql.Connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.processors.HDBTableAlterHandler;
import com.codbex.kronos.engine.hdb.processors.HDBTableAlterProcessor;

import jakarta.transaction.Transactional;

/**
 * The Class HDBTableProcessorsTest.
 */
@SpringBootTest(classes = {HDBTableAlterProcessor.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
@Disabled
public class HDBTableProcessorsTest {

    /** The mock connection. */
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Connection mockConnection;

    /** The mock model. */
    @Mock
    private HDBTable mockModel;

    /** The mock handler. */
    @Mock
    private HDBTableAlterHandler mockHandler;

    /** The table alter processor. */
    private HDBTableAlterProcessor tableAlterProcessor = spy(new HDBTableAlterProcessor());

    /**
     * Open mocks.
     */
    @BeforeEach
    public void openMocks() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Execute alter successfully.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeAlterSuccessfully() throws Exception {
        Mockito.doReturn(mockHandler)
               .when(tableAlterProcessor)
               .createTableAlterHandler(mockConnection, mockModel);

        doNothing().when(mockHandler)
                   .addColumns(mockConnection);
        doNothing().when(mockHandler)
                   .removeColumns(mockConnection);
        doNothing().when(mockHandler)
                   .updateColumns(mockConnection);
        doNothing().when(mockHandler)
                   .rebuildIndeces(mockConnection);
        doNothing().when(mockHandler)
                   .ensurePrimaryKeyIsUnchanged(mockConnection);

        tableAlterProcessor.execute(mockConnection, mockModel);

        verify(mockHandler, times(1)).addColumns(mockConnection);
        verify(mockHandler, times(1)).removeColumns(mockConnection);
        verify(mockHandler, times(1)).updateColumns(mockConnection);
        verify(mockHandler, times(1)).rebuildIndeces(mockConnection);
        verify(mockHandler, times(1)).ensurePrimaryKeyIsUnchanged(mockConnection);
    }

}
