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
package com.codbex.kronos.parser.parser.custom;

import com.codbex.kronos.parser.hana.core.custom.HanaProcedureUpdateStatementListener;
import com.codbex.kronos.parser.hana.core.models.ProcedureDefinitionModel;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HanaProcedureUpdateStatementListenerTest {

  @Test
  public void parseHDBProcedureUpdateStatements() throws Exception {
    String tableFunctionSample = getSample("/sample_with_update_statements.hdbprocedure");
    ProcedureDefinitionModel model = parseHDBPProcedureModel(tableFunctionSample);
    assertModel(model, "TEST", "testProcedure");
  }

  private String getSample(String sampleName) throws IOException {
    return org.apache.commons.io.IOUtils
        .toString(HanaTableFunctionListenerTest.class.getResourceAsStream(sampleName), StandardCharsets.UTF_8);
  }

  private ProcedureDefinitionModel parseHDBPProcedureModel(String sample) {
    CharStream inputStream = CharStreams.fromString(sample);
    HanaLexer lexer = new HanaLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HanaParser parser = new HanaParser(tokenStream);
    parser.setBuildParseTree(true);
    ParseTree parseTree = parser.sql_script();

    HanaProcedureUpdateStatementListener listener = new HanaProcedureUpdateStatementListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(listener, parseTree);

    return listener.getProcedureModel();
  }

  private void assertModel(ProcedureDefinitionModel model, String expectedSchema, String expectedName) {
    assertNotNull(model);
    model.checkForAllMandatoryFieldsPresence();
    assertEquals("Unexpected schema name. ", expectedSchema, model.getSchema());
    assertEquals("Unexpected procedure name. ", expectedName, model.getName());
    assertEquals(6, model.getUpdateStatements().size());
  }
}


