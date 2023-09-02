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
package com.codbex.kronos.parser.hdbsequence.custom;

import com.codbex.kronos.parser.hdbsequence.core.HDBSequenceBaseVisitor;
import com.codbex.kronos.parser.hdbsequence.core.HDBSequenceLexer;
import com.codbex.kronos.parser.hdbsequence.core.HDBSequenceParser;
import com.codbex.kronos.parser.hdbsequence.models.HDBSequenceModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HDBSequenceDefinitionVisitorTest {
  @Test
  public void testParseHDBSequenceFileSuccessfully() throws IOException {
    String content = org.apache.commons.io.IOUtils
        .toString(HDBSequenceDefinitionVisitorTest.class.getResourceAsStream("/SampleSequence.hdbsequence"), StandardCharsets.UTF_8);
    HDBSequenceModel model = parseFileContent(content);
    assertNotNull(model);
  }

  private HDBSequenceModel parseFileContent(String content) throws IOException {
    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    HDBSequenceLexer hdbSequenceLexer = new HDBSequenceLexer(inputStream);
    HDBSequenceSyntaxErrorListener lexerErrorListener = new HDBSequenceSyntaxErrorListener();
    hdbSequenceLexer.removeErrorListeners();
    hdbSequenceLexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbSequenceLexer);

    HDBSequenceParser hdbSequenceParser = new HDBSequenceParser(tokenStream);
    hdbSequenceParser.setBuildParseTree(true);
    hdbSequenceParser.removeErrorListeners();

    HDBSequenceSyntaxErrorListener parserErrorListener = new HDBSequenceSyntaxErrorListener();
    hdbSequenceParser.addErrorListener(parserErrorListener);
    ParseTree parseTree = hdbSequenceParser.hdbSequenceDefinition();
    HDBSequenceBaseVisitor<JsonElement> visitor = new HDBSequenceDefinitionVisitor();

    JsonElement parsedResult = visitor.visit(parseTree);
    Gson gson = new Gson();
    HDBSequenceModel hdbSequenceModel = gson.fromJson(parsedResult, HDBSequenceModel.class);

    assertEquals(0, parserErrorListener.getErrors().size());
    assertEquals(0, lexerErrorListener.getErrors().size());

    return hdbSequenceModel;
  }


}
