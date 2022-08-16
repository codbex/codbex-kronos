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
package com.codbex.kronos.parser.hdbtable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.codbex.kronos.parser.hdbtable.core.HdbtableLexer;
import com.codbex.kronos.parser.hdbtable.core.HdbtableParser;
import com.codbex.kronos.parser.hdbtable.custom.HDBTableCoreVisitor;
import com.codbex.kronos.parser.hdbtable.custom.HDBTableSyntaxErrorListener;
import com.codbex.kronos.parser.hdbtable.model.HDBTableDefinitionModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

public class HDBTableCoreVisitorTest {

  @Test
  public void parseHdbtableSuccessfully() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(HDBTableCoreVisitorTest.class.getResourceAsStream("/sample.hdbtable"), StandardCharsets.UTF_8);
    HDBTableDefinitionModel hdbtableDefinitionModel = passeFileContent(content);

    assertNotNull(hdbtableDefinitionModel);
    assertEquals("MYSCHEMA", hdbtableDefinitionModel.getSchemaName());
  }


  @Test
  public void parseHdbtableWithStringLiteralsWithEscapedQuotes() throws IOException {
    String content = "table.schemaName = \"MYSCHEMA\";\n"
        + "table.tableType = COLUMNSTORE;\n"
        + "table.description = \"Some \\\"escapted text\\\"\";\n"
        + "table.columns = [\n"
        + "    {name = \"ID\"; sqlType = NVARCHAR; length = 256; nullable = false; comment = \"Some \\\"escapted text\\\"\";}\n"
        + "];\n"
        + "table.primaryKey.pkcolumns = [\"ID\"];";
    HDBTableDefinitionModel hdbtableDefinitionModel = passeFileContent(content);

    assertNotNull(hdbtableDefinitionModel);
    assertEquals("MYSCHEMA", hdbtableDefinitionModel.getSchemaName());
    assertEquals("Some \"escapted text\"", hdbtableDefinitionModel.getDescription());

  }

  private static HDBTableDefinitionModel passeFileContent(String content) throws IOException {
    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    HdbtableLexer lexer = new HdbtableLexer(inputStream);
    HDBTableSyntaxErrorListener lexerErrorListener = new HDBTableSyntaxErrorListener();
    lexer.removeErrorListeners();
    lexer.addErrorListener(lexerErrorListener);
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HdbtableParser hdbtableParser = new HdbtableParser(tokenStream);
    hdbtableParser.setBuildParseTree(true);
    hdbtableParser.removeErrorListeners();

    HDBTableSyntaxErrorListener parserErrorListener = new HDBTableSyntaxErrorListener();
    hdbtableParser.addErrorListener(parserErrorListener);
    ParseTree parseTree = hdbtableParser.hdbtableDefinition();
    HDBTableCoreVisitor hdbtableCoreVisitor = new HDBTableCoreVisitor();

    JsonElement parsedResult = hdbtableCoreVisitor.visit(parseTree);
    Gson gson = new Gson();
    HDBTableDefinitionModel hdbtableDefinitionModel = gson.fromJson(parsedResult, HDBTableDefinitionModel.class);
    hdbtableDefinitionModel.checkForAllMandatoryFieldsPresence();

    assertEquals(0, parserErrorListener.getErrors().size());
    assertEquals(0, lexerErrorListener.getErrors().size());

    return hdbtableDefinitionModel;
  }

}