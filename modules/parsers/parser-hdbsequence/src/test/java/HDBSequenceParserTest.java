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
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.codbex.kronos.parser.hdbsequence.core.HdbsequenceBaseVisitor;
import com.codbex.kronos.parser.hdbsequence.core.HdbsequenceLexer;
import com.codbex.kronos.parser.hdbsequence.core.HdbsequenceParser;
import com.codbex.kronos.parser.hdbsequence.custom.HdbsequenceVisitor;
import com.codbex.kronos.parser.hdbsequence.custom.HDBSequenceSyntaxErrorListener;
import com.codbex.kronos.parser.hdbsequence.models.HDBSequenceModel;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HDBSequenceParserTest {
  @Test
  public void parseHDBsequenceFileSuccessfully() throws IOException {
    String content = org.apache.commons.io.IOUtils
        .toString(HDBSequenceParserTest.class.getResourceAsStream("/SampleSequence.hdbsequence"), StandardCharsets.UTF_8);
    HDBSequenceModel model = parseFileContent(content);
    assertNotNull(model);
  }

  private HDBSequenceModel parseFileContent(String content) throws IOException {
    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    HdbsequenceLexer lexer = new HdbsequenceLexer(inputStream);
    HDBSequenceSyntaxErrorListener lexerErrorListener = new HDBSequenceSyntaxErrorListener();
    lexer.removeErrorListeners();
    lexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HdbsequenceParser hdbsequenceParser = new HdbsequenceParser(tokenStream);
    hdbsequenceParser.setBuildParseTree(true);
    hdbsequenceParser.removeErrorListeners();

    HDBSequenceSyntaxErrorListener parserErrorListener = new HDBSequenceSyntaxErrorListener();
    hdbsequenceParser.addErrorListener(parserErrorListener);
    ParseTree parseTree = hdbsequenceParser.hdbsequence();
    HdbsequenceBaseVisitor<JsonElement> visitor = new HdbsequenceVisitor();

    JsonElement parsedResult = visitor.visit(parseTree);
    Gson gson = new Gson();
    HDBSequenceModel hdbsequenceModel = gson.fromJson(parsedResult, HDBSequenceModel.class);

    assertEquals(0, parserErrorListener.getErrors().size());
    assertEquals(0, lexerErrorListener.getErrors().size());

    return hdbsequenceModel;
  }


}
