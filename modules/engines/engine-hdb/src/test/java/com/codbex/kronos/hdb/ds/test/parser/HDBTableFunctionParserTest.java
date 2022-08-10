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
package com.codbex.kronos.hdb.ds.test.parser;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableFunctionSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.DataStructureHDBTableFunctionModel;
import com.codbex.kronos.hdb.ds.parser.hdbtablefunction.HDBTableFunctionLogger;
import com.codbex.kronos.hdb.ds.parser.hdbtablefunction.HDBTableFunctionParser;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.core.test.AbstractDirigibleTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HDBTableFunctionParserTest extends AbstractDirigibleTest {

   TestContentProvider contentProvider = new TestContentProvider();
   TestTableFunctionParser tableFunctionParser = new TestTableFunctionParser();
    private HDBTableFunctionParser parser;

    private DataStructuresSynchronizer dataStructuresSynchronizerMock;
    private HDBTableFunctionLogger tableFunctionLoggerMock;
    private HDBTableFunctionSynchronizationArtefactType tableFunctionSynchronizationArtefactType;

    @Before
    public void setup() {
        dataStructuresSynchronizerMock = Mockito.mock(DataStructuresSynchronizer.class);
        doNothing().when(dataStructuresSynchronizerMock).applyArtefactState(any(), any(), any(), any(), any());
        tableFunctionLoggerMock = Mockito.mock(HDBTableFunctionLogger.class);
        doNothing().when(tableFunctionLoggerMock).logError(any(), any(), any());

        tableFunctionSynchronizationArtefactType = new HDBTableFunctionSynchronizationArtefactType();
        parser = new HDBTableFunctionParser(dataStructuresSynchronizerMock, tableFunctionSynchronizationArtefactType, tableFunctionLoggerMock);
    }

    @Test
    public void parseTableFunction() throws IOException, DataStructuresException, ArtifactParserException {
        String location = "/OrderTableFunction.hdbtablefunction";
        String content = contentProvider.getTestContent(location);

        DataStructureHDBTableFunctionModel model = tableFunctionParser.parseTableFunction(parser, location, content);
        assertEquals("Unexpected tablefunction schema.", "MYSCHEMA", model.getSchema());
        assertEquals("Unexpected tablefunction name.", "hdb_view::OrderTableFunction", model.getName());
        assertEquals("Unexpected tablefunction content.", content, model.getRawContent());
        assertEquals("Unexpected tablefunction raw content.", content, model.getRawContent());
        assertEquals("Unexpected tablefunction location.", location, model.getLocation());
        assertEquals("Unexpected tablefunction type.", "HDBTABLEFUNC", model.getType());
        assertEquals("Unexpected tablefunction dependencies.", 0, model.getDependencies().size());
        assertNotNull("Null value for tablefunction createdAt", model.getCreatedAt());
        assertNotNull("Null value for tablefunction createdBy", model.getCreatedBy());
        assertEquals("Cant access data structure model class", "class com.codbex.kronos.hdb.ds.model.hdbtablefunction.DataStructureHDBTableFunctionModel", parser.getDataStructureClass().toString());
    }

    @Test
    public void parseNoSchemaTableFunction() throws IOException, DataStructuresException, ArtifactParserException {
        String location = "/OrderTableFunctionNoSchema.hdbtablefunction";
        String content = contentProvider.getTestContent(location);

        DataStructureHDBTableFunctionModel model = tableFunctionParser.parseTableFunction(parser, location, content);
        assertNull("Unexpected tablefunction schema.", model.getSchema());
        assertEquals("Unexpected tablefunction name.", "hdb_view::OrderTableFunction", model.getName());
        assertEquals("Unexpected tablefunction content.", content, model.getRawContent());
        assertEquals("Unexpected tablefunction raw content.", content, model.getRawContent());
        assertEquals("Unexpected tablefunction location.", location, model.getLocation());
        assertEquals("Unexpected tablefunction type.", "HDBTABLEFUNC", model.getType());
        assertEquals("Unexpected tablefunction dependencies.", 0, model.getDependencies().size());
        assertNotNull("Null value for tablefunction createdAt", model.getCreatedAt());
        assertNotNull("Null value for tablefunction createdBy", model.getCreatedBy());
    }

    @Test
    public void parseNoSchemaNoNameTableFunction() throws IOException {
        String location = "/OrderTableFunctionNoSchemaNoName.hdbtablefunction";
        String content = contentProvider.getTestContent(location);

        DataStructuresException caughtException = assertThrows(
                "Unexpected missing exception",
                DataStructuresException.class,
                () -> tableFunctionParser.parseTableFunction(parser, location, content));
        Throwable caughtExceptionCause = caughtException.getCause();

        assertEquals("Unexpected exception message", "Wrong format of HDB Table Function: " + location + " during parsing. ",
                caughtException.getMessage());
        verify(tableFunctionLoggerMock).logError(location, CommonsConstants.EXPECTED_FIELDS, caughtExceptionCause.getMessage());
        verify(dataStructuresSynchronizerMock).applyArtefactState(
                CommonsUtils.getRepositoryBaseObjectName(location),
                location,
                tableFunctionSynchronizationArtefactType,
                ArtefactState.FAILED_CREATE,
                caughtExceptionCause.getMessage()
        );
    }

}
