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
package com.codbex.kronos.parser.custom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import com.codbex.kronos.parser.hana.core.HanaLexer;
import com.codbex.kronos.parser.hana.core.HanaParser;
import com.codbex.kronos.parser.hana.custom.HanaTableFunctionListener;
import com.codbex.kronos.parser.hana.models.TableFunctionDefinitionModel;

public class HanaTableFunctionListenerTest {

  @Test
  public void parseTableFunction() throws Exception {
    String tableFunctionSample = getSample("/sample.hdbtablefunction");
    TableFunctionDefinitionModel model = parseModel(tableFunctionSample);
    assertModel(model, "_SYS_BIC", "customer_sample::SAMPLE_FUNCTION");
  }

  @Test
  public void parseLongTableFunction() throws Exception {
    String tableFunctionSample = getSample("/sample_long.hdbtablefunction");
    TableFunctionDefinitionModel model = parseModel(tableFunctionSample);
    assertModel(model, "_SYS_BIC", "Z.VIEWS::TFD");
  }

  @Test
  public void parseTableFunctionWithComment() throws Exception {
    String tableFunctionSample = getSample("/sample_with_comment.hdbtablefunction");
    TableFunctionDefinitionModel model = parseModel(tableFunctionSample);
    assertModel(model, null, "customer_sample::SAMPLE_FUNCTION");
  }

  private String getSample(String sampleName) throws IOException {
    return org.apache.commons.io.IOUtils
        .toString(HanaTableFunctionListenerTest.class.getResourceAsStream(sampleName), StandardCharsets.UTF_8);
  }

  private TableFunctionDefinitionModel parseModel(String sample) {
    CharStream inputStream = CharStreams.fromString(sample);
    HanaLexer lexer = new HanaLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HanaParser parser = new HanaParser(tokenStream);
    parser.setBuildParseTree(true);
    ParseTree parseTree = parser.sql_script();

    HanaTableFunctionListener listener = new HanaTableFunctionListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(listener, parseTree);

    return listener.getModel();
  }

  private void assertModel(TableFunctionDefinitionModel model, String expectedSchema, String expectedName) {
    assertNotNull(model);
    model.checkForAllMandatoryFieldsPresence();
    assertEquals("Unexpected schema name. ", expectedSchema, model.getSchema());
    assertEquals("Unexpected tablefunction name. ", expectedName, model.getName());
  }
  
}
