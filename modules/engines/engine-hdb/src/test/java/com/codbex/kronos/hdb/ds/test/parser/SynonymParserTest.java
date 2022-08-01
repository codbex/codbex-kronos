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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;

import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import org.eclipse.dirigible.api.v3.problems.ProblemsFacade;
import org.eclipse.dirigible.core.test.AbstractDirigibleTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.codbex.kronos.hdb.ds.model.DataStructureModelFactory;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDefinitionModel;
import com.codbex.kronos.hdb.ds.parser.hdbsynonym.SynonymParser;

public class SynonymParserTest extends AbstractDirigibleTest {

  @Before
  public void openMocks() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void parseHdbsynonymFileWithoutErrorsSuccessfully() throws Exception {
    String hdbsynonymSample = org.apache.commons.io.IOUtils
        .toString(SynonymParserTest.class.getResourceAsStream("/MySynonym.hdbsynonym"), StandardCharsets.UTF_8);

    HDBSynonymDataStructureModel model = DataStructureModelFactory.parseSynonym("hdb_view/MySynonym.hdbsynonym", hdbsynonymSample);

    HDBSynonymDefinitionModel definitionModel = model.getSynonymDefinitions().get("SY_DUMMY");
    assertEquals("hdb_view::MySynonym", model.getName());
    assertEquals("LL", definitionModel.getSynonymSchema());
    assertEquals("TABLE::CUSTOMERS", definitionModel.getTarget().getObject());
    assertEquals("DBADMIN", definitionModel.getTarget().getSchema());
  }

  @Ignore
  @Test
  public void parseHdbsynonymFileMissingTargetSchemaFailed() throws Exception {
    ProblemsFacade.clearAllProblems();
    String content = "{\n"
        + "    \"SY_DUMMY\": {\n"
        + "        \"target\": {\n"
        + "            \"object\": \"TABLE::CUSTOMERS\"\n"
        + "        },\n"
        + "        \"schema\": \"LL\"\n"
        + "    }\n"
        + "}";
    String errorMessage = "Missing mandatory field for synonym SY_DUMMY!";
    SynonymParser parser = new SynonymParser();
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, "hdb_view/MySynonym.hdbsynonym", content, null);
    parser.parse(parametersModel);
    assertTrue(ProblemsFacade.fetchAllProblems().contains(errorMessage));
  }
}