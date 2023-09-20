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
package com.codbex.kronos.engine.hdb.parser.hdbsynonym;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.codbex.kronos.engine.hdb.domain.HDBSynonym;
import com.codbex.kronos.engine.hdb.domain.HDBSynonymGroup;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.parser.HDBParameters;
import com.codbex.kronos.engine.hdb.parser.HDBSynonymDataStructureParser;

/**
 * The Class HDBSynonymDataStructureParserTest.
 */
@SpringBootTest(classes = {HDBDataStructureModelFactory.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBSynonymDataStructureParserTest {

  /**
	 * Open mocks.
	 */
  @BeforeEach
  public void openMocks() {
    MockitoAnnotations.initMocks(this);
  }

  /**
	 * Parses the hdbsynonym file without errors successfully.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void parseHdbsynonymFileWithoutErrorsSuccessfully() throws Exception {
    String hdbsynonymSample = org.apache.commons.io.IOUtils
        .toString(HDBSynonymDataStructureParserTest.class.getResourceAsStream("/MySynonym.hdbsynonym"), StandardCharsets.UTF_8);

    HDBSynonymGroup model = HDBDataStructureModelFactory.parseSynonym("hdb_view/MySynonym.hdbsynonym", hdbsynonymSample);

    HDBSynonym definitionModel = model.getSynonymDefinitions().get("SY_DUMMY");
    assertEquals("hdb_view::MySynonym", model.getName());
    assertEquals("LL", definitionModel.getSchema());
    assertEquals("TABLE::CUSTOMERS", definitionModel.getTarget().getObject());
    assertEquals("DBADMIN", definitionModel.getTarget().getSchema());
  }

/**
 * Parses the hdbsynonym file missing target schema failed.
 *
 * @throws Exception the exception
 */
//  @Ignore
  @Test
  public void parseHdbsynonymFileMissingTargetSchemaFailed() throws Exception {
//    ProblemsFacade.clearAllProblems();
    String content = "{\n"
        + "    \"SY_DUMMY\": {\n"
        + "        \"target\": {\n"
        + "            \"object\": \"TABLE::CUSTOMERS\"\n"
        + "        },\n"
        + "        \"schema\": \"LL\"\n"
        + "    }\n"
        + "}";
    String errorMessage = "Missing mandatory field for synonym SY_DUMMY!";
    HDBSynonymDataStructureParser parser = new HDBSynonymDataStructureParser();
    HDBParameters parametersModel =
        new HDBParameters(null, "hdb_view/MySynonym.hdbsynonym", content, null);
    parser.parse(parametersModel);
//    assertTrue(ProblemsFacade.fetchAllProblems().contains(errorMessage));
  }
	
}