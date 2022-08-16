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
package com.codbex.kronos.parser.hdbti.custom;


import com.codbex.kronos.parser.hdbti.core.HDBTILexer;
import com.codbex.kronos.parser.hdbti.core.HDBTIParser;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportModel;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HDBTIDefinitionListenerTest {

  @Test
  public void testParseHDBHDBTIFileWithoutErrorsSuccessfully() throws Exception {
    String hdbSchemaSample = org.apache.commons.io.IOUtils
        .toString(HDBTIDefinitionListenerTest.class.getResourceAsStream("/sample.hdbti"), StandardCharsets.UTF_8);

    ANTLRInputStream inputStream = new ANTLRInputStream(hdbSchemaSample);
    HDBTILexer hdbtiLexer = new HDBTILexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbtiLexer);
    HDBTIParser hdbtiParser = new HDBTIParser(tokenStream);
    hdbtiParser.setBuildParseTree(true);
    ParseTree parseTree = hdbtiParser.importArr();

    HDBTIDefinitionListener hdbtiDefinitionListener = new HDBTIDefinitionListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbtiDefinitionListener, parseTree);

    HDBTIImportModel model = hdbtiDefinitionListener.getImportModel();
    assertNotNull(model);
    assertEquals(hdbtiParser.getNumberOfSyntaxErrors(), 0);
  }

  @Test
  public void testParseHDBTIFileWithSyntaxErrorExceptionThrown() throws Exception {
    String hdbSchemaSample = org.apache.commons.io.IOUtils
        .toString(HDBTIDefinitionListenerTest.class.getResourceAsStream("/sample_with_errors.hdbti"), StandardCharsets.UTF_8);

    ANTLRInputStream inputStream = new ANTLRInputStream(hdbSchemaSample);
    HDBTILexer hdbtiLexer = new HDBTILexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbtiLexer);
    HDBTIParser hdbtiParser = new HDBTIParser(tokenStream);
    hdbtiParser.setBuildParseTree(true);
    ParseTree parseTree = hdbtiParser.importArr();

    HDBTIDefinitionListener hdbtiDefinitionListener = new HDBTIDefinitionListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbtiDefinitionListener, parseTree);

    System.out.println(hdbtiParser.getNumberOfSyntaxErrors());
    assertEquals(hdbtiParser.getNumberOfSyntaxErrors(), 3);
  }
}