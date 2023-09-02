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
package com.codbex.kronos.parser.parser.custom;

import com.codbex.kronos.parser.hana.custom.HanaProcedureListener;
import com.codbex.kronos.parser.hana.models.ProcedureDefinitionModel;
import com.codbex.kronos.parser.hana.core.HanaLexer;
import com.codbex.kronos.parser.hana.core.HanaParser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HanaProcedureListenerTest {

  @Test
  public void testParseProcedure() throws Exception {
    String hdbProcedureSample = getSample("/sample.hdbprocedure");
    ProcedureDefinitionModel procedureDefinitionModel = parseProcedureModel(hdbProcedureSample);
    assertModel(procedureDefinitionModel, "KRONOS_TEST_APP", "kronos_test_app::usersProcedure");
  }

  @Test
  public void testParseProcedureWithComment() throws Exception {
    String hdbProcedureSample = getSample("/sample_with_comment.hdbprocedure");
    ProcedureDefinitionModel procedureDefinitionModel = parseProcedureModel(hdbProcedureSample);
    assertModel(procedureDefinitionModel, "KRONOS_SAMPLES_HDB_PROCEDURE_SIMPLE", "hdb-procedure-simple.db::ItemProcedure");
  }

  private String getSample(String sampleName) throws IOException {
    return org.apache.commons.io.IOUtils
        .toString(Objects.requireNonNull(HanaProcedureListenerTest.class.getResourceAsStream(sampleName)), StandardCharsets.UTF_8);
  }

  private ProcedureDefinitionModel parseProcedureModel(String sample) {
    CharStream inputStream = CharStreams.fromString(sample);
    HanaLexer lexer = new HanaLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HanaParser hanaParser = new HanaParser(tokenStream);
    hanaParser.setBuildParseTree(true);
    ParseTree parseTree = hanaParser.sql_script();

    HanaProcedureListener listener = new HanaProcedureListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(listener, parseTree);

    return listener.getModel();
  }

  private void assertModel(ProcedureDefinitionModel model, String expectedSchema, String expectedName) {
    assertNotNull(model);
    model.checkForAllMandatoryFieldsPresence();
    assertEquals("Unexpected schema name. ", expectedSchema, model.getSchema());
    assertEquals("Unexpected procedure name. ", expectedName, model.getName());
  }
}
