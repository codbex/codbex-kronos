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
import com.codbex.kronos.hdb.ds.artefacts.HDBProcedureSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.parser.hdbprocedure.HDBProcedureLogger;
import com.codbex.kronos.hdb.ds.parser.hdbprocedure.HDBProcedureParser;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

public class HDBProcedureParserTest {

    TestContentProvider contentProvider = new TestContentProvider();
    TestProcedureParser procedureParser = new TestProcedureParser();
    private HDBProcedureParser parser;

    @Before
    public void setup() {
        DataStructuresSynchronizer dataStructuresSynchronizerMock = Mockito.mock(DataStructuresSynchronizer.class);
        doNothing().when(dataStructuresSynchronizerMock).applyArtefactState(any(), any(), any(), any(), any());
        HDBProcedureLogger hdbProcedureLogger = Mockito.mock(HDBProcedureLogger.class);
        doNothing().when(hdbProcedureLogger).logError(any(), any(), any());

        HDBProcedureSynchronizationArtefactType hdbProcedureSynchronizationArtefactType = new HDBProcedureSynchronizationArtefactType();
        parser = new HDBProcedureParser(dataStructuresSynchronizerMock, hdbProcedureSynchronizationArtefactType, hdbProcedureLogger);
    }

    @Test
    public void checkModel() throws IOException, DataStructuresException, ArtifactParserException {
        String location = "/OrderProcedure.hdbprocedure";
        String content = contentProvider.getTestContent(location);


        var model = procedureParser.parseProcedure(parser, location, content);
        assertEquals("Unexpected hdbprocedure schema.", "MYSCHEMA", model.getSchema());
        assertEquals("Unexpected hdbprocedure name.", "hdb_view::OrderProcedure", model.getName());
        assertEquals("Unexpected hdbprocedure content.", content, model.getRawContent());
        assertEquals("Unexpected hdbprocedure raw content.", content, model.getRawContent());
        assertEquals("Unexpected hdbprocedure location.", location, model.getLocation());
        assertEquals("Unexpected hdbprocedure type.", "HDBPROC", model.getType());
        assertEquals("Unexpected hdbprocedure dependencies.", 0, model.getDependencies().size());
        assertNotNull("Null value for hdbprocedure createdAt", model.getCreatedAt());
        assertNotNull("Null value for hdbprocedure createdBy", model.getCreatedBy());
        assertEquals("Cant access data structure model class", "class com.codbex.kronos.hdb.ds.model.hdbprocedure.DataStructureHDBProcedureModel", parser.getDataStructureClass().toString());
    }


}
