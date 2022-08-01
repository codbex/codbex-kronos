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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.codbex.kronos.parser.hdbschema.core.HdbschemaLexer;
import com.codbex.kronos.parser.hdbschema.core.HdbschemaParser;
import com.codbex.kronos.parser.hdbschema.custom.HDBSchemaCoreListener;
import com.codbex.kronos.parser.hdbschema.models.HDBSchemaDefinitionModel;
import java.nio.charset.StandardCharsets;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

public class HDBSchemaCoreListenerTest {

  @Test
  public void parseHdbschemaFileWithoutErrorsSuccessfully() throws Exception {
    String hdbschemaSample = org.apache.commons.io.IOUtils
        .toString(HDBSchemaCoreListenerTest.class.getResourceAsStream("/sample.hdbschema"), StandardCharsets.UTF_8);

    ANTLRInputStream inputStream = new ANTLRInputStream(hdbschemaSample);
    HdbschemaLexer hdbschemaLexer = new HdbschemaLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbschemaLexer);
    HdbschemaParser hdbschemaParser = new HdbschemaParser(tokenStream);
    hdbschemaParser.setBuildParseTree(true);
    ParseTree parseTree = hdbschemaParser.hdbschemaDefinition();

    HDBSchemaCoreListener hdbschemaCoreListener = new HDBSchemaCoreListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbschemaCoreListener, parseTree);

    HDBSchemaDefinitionModel model = hdbschemaCoreListener.getModel();
    assertNotNull(model);
    assertEquals(hdbschemaParser.getNumberOfSyntaxErrors(), 0);
    assertEquals("MYSCHEMA", model.getSchemaName());
  }

  @Test
  public void parseHdbschemaFileWithSyntaxErrorExceptionThrown() throws Exception {
    String hdbschemaSample = org.apache.commons.io.IOUtils
        .toString(HDBSchemaCoreListenerTest.class.getResourceAsStream("/sample_with_errors.hdbschema"), StandardCharsets.UTF_8);

    ANTLRInputStream inputStream = new ANTLRInputStream(hdbschemaSample);
    HdbschemaLexer hdbschemaLexer = new HdbschemaLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbschemaLexer);
    HdbschemaParser hdbschemaParser = new HdbschemaParser(tokenStream);
    hdbschemaParser.setBuildParseTree(true);
    ParseTree parseTree = hdbschemaParser.hdbschemaDefinition();

    HDBSchemaCoreListener hdbschemaCoreListener = new HDBSchemaCoreListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbschemaCoreListener, parseTree);

    System.out.println(hdbschemaParser.getNumberOfSyntaxErrors());
    assertEquals(hdbschemaParser.getNumberOfSyntaxErrors(), 1);
  }
}