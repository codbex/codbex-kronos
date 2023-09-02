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
package com.codbex.kronos.parser.hdbschema.custom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.charset.StandardCharsets;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import com.codbex.kronos.parser.hdbschema.core.HDBSchemaLexer;
import com.codbex.kronos.parser.hdbschema.core.HDBSchemaParser;
import com.codbex.kronos.parser.hdbschema.models.HDBSchemaDefinitionModel;

public class HDBSchemaDefinitionListenerTest {

  @Test
  public void testParseHDBSchemaFileWithoutErrorsSuccessfully() throws Exception {
    String hdbSchemaSample = org.apache.commons.io.IOUtils
        .toString(HDBSchemaDefinitionListenerTest.class.getResourceAsStream("/sample.hdbschema"), StandardCharsets.UTF_8);

    ANTLRInputStream inputStream = new ANTLRInputStream(hdbSchemaSample);
    HDBSchemaLexer hdbSchemaLexer = new HDBSchemaLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbSchemaLexer);
    HDBSchemaParser hdbSchemaParser = new HDBSchemaParser(tokenStream);
    hdbSchemaParser.setBuildParseTree(true);
    ParseTree parseTree = hdbSchemaParser.hdbSchemaDefinition();

    HDBSchemaDefinitionListener hdbSchemaDefinitionListener = new HDBSchemaDefinitionListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbSchemaDefinitionListener, parseTree);

    HDBSchemaDefinitionModel model = hdbSchemaDefinitionListener.getModel();
    assertNotNull(model);
    assertEquals(hdbSchemaParser.getNumberOfSyntaxErrors(), 0);
    assertEquals("MYSCHEMA", model.getSchemaName());
  }

  @Test
  public void testParseHDBSchemaFileWithSyntaxErrorExceptionThrown() throws Exception {
    String hdbSchemaSample = org.apache.commons.io.IOUtils
        .toString(HDBSchemaDefinitionListenerTest.class.getResourceAsStream("/sample_with_errors.hdbschema"), StandardCharsets.UTF_8);

    ANTLRInputStream inputStream = new ANTLRInputStream(hdbSchemaSample);
    HDBSchemaLexer hdbSchemaLexer = new HDBSchemaLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbSchemaLexer);
    HDBSchemaParser hdbSchemaParser = new HDBSchemaParser(tokenStream);
    hdbSchemaParser.setBuildParseTree(true);
    ParseTree parseTree = hdbSchemaParser.hdbSchemaDefinition();

    HDBSchemaDefinitionListener hdbSchemaDefinitionListener = new HDBSchemaDefinitionListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbSchemaDefinitionListener, parseTree);

    System.out.println(hdbSchemaParser.getNumberOfSyntaxErrors());
    assertEquals(hdbSchemaParser.getNumberOfSyntaxErrors(), 1);
  }
}