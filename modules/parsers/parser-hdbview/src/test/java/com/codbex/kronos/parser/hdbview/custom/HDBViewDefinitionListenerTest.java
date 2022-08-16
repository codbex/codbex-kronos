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
package com.codbex.kronos.parser.hdbview.custom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import com.codbex.kronos.parser.hdbview.core.HDBViewLexer;
import com.codbex.kronos.parser.hdbview.core.HDBViewParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import com.codbex.kronos.parser.hdbview.exceptions.HDBViewMissingPropertyException;
import com.codbex.kronos.parser.hdbview.models.HDBViewDefinitionModel;

public class HDBViewDefinitionListenerTest {

  @Test
  public void testParseHDBViewFileSuccessfully() throws Exception {
    String hdbViewSample = org.apache.commons.io.IOUtils
        .toString(HDBViewDefinitionListenerTest.class.getResourceAsStream("/sample.hdbview"), StandardCharsets.UTF_8);

    ANTLRInputStream inputStream = new ANTLRInputStream(hdbViewSample);
    HDBViewLexer hdbViewLexer = new HDBViewLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbViewLexer);
    HDBViewParser hdbViewParser = new HDBViewParser(tokenStream);
    hdbViewParser.setBuildParseTree(true);
    ParseTree parseTree = hdbViewParser.hdbViewDefinition();

    HDBViewDefinitionListener hdbViewDefinitionListener = new HDBViewDefinitionListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbViewDefinitionListener, parseTree);

    HDBViewDefinitionModel model = hdbViewDefinitionListener.getModel();
    model.checkForAllMandatoryFieldsPresence();
    assertNotNull(model);
    assertEquals(hdbViewParser.getNumberOfSyntaxErrors(), 0);
    assertTrue(model.isPublic());
    assertEquals("MYSCHEMA", model.getSchema());
    assertEquals(2, model.getDependsOn().size());
    assertEquals("acme.com.test.tables::MY_TABLE1", model.getDependsOn().get(0));
    assertEquals("acme.com.test.views::MY_VIEW1", model.getDependsOn().get(1));
    assertEquals(2, model.getDependsOnTable().size());
    assertEquals("acme.com.test.tables::MY_TABLE1", model.getDependsOnTable().get(0));
    assertEquals("acme.com.test.views::MY_TABLE2", model.getDependsOnTable().get(1));
    assertEquals(2, model.getDependsOnView().size());
    assertEquals("acme.com.test.tables::MY_VIEW1", model.getDependsOnView().get(0));
    assertEquals("acme.com.test.views::MY_VIEW2", model.getDependsOnView().get(1));
    assertEquals(
        "SELECT T1.\"Column2\" FROM \"MYSCHEMA\".\"acme.com.test.tables::MY_TABLE1\" AS T1 LEFT JOIN \"MYSCHEMA\".\"acme.com.test.views::MY_VIEW1\" AS T2 ON T1.\"Column1\" = T2.\"Column1\"",
        model.getQuery());
  }

  @Test(expected = HDBViewMissingPropertyException.class)
  public void testParseHDBviewFileWithSyntaxErrorExceptionThrown() throws Exception {
    String hdbViewSample = org.apache.commons.io.IOUtils
        .toString(HDBViewDefinitionListenerTest.class.getResourceAsStream("/sample_with_errors.hdbview"), StandardCharsets.UTF_8);

    ANTLRInputStream inputStream = new ANTLRInputStream(hdbViewSample);
    HDBViewLexer hdbViewLexer = new HDBViewLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbViewLexer);
    HDBViewParser hdbViewParser = new HDBViewParser(tokenStream);
    hdbViewParser.setBuildParseTree(true);
    ParseTree parseTree = hdbViewParser.hdbViewDefinition();

    HDBViewDefinitionListener hdbViewDefinitionListener = new HDBViewDefinitionListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbViewDefinitionListener, parseTree);

    HDBViewDefinitionModel model = hdbViewDefinitionListener.getModel();
    assertEquals(hdbViewParser.getNumberOfSyntaxErrors(), 2);
    model.checkForAllMandatoryFieldsPresence();
    assertFalse(model.isPublic());
  }

  @Test
  public void testParseHDBViewFileWithSingleQuote() throws Exception {
    String hdbViewSample = org.apache.commons.io.IOUtils
        .toString(HDBViewDefinitionListenerTest.class.getResourceAsStream("/sampleWithQuote.hdbview"), StandardCharsets.UTF_8);

    ANTLRInputStream inputStream = new ANTLRInputStream(hdbViewSample);
    HDBViewLexer hdbViewLexer = new HDBViewLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbViewLexer);
    HDBViewParser hdbViewParser = new HDBViewParser(tokenStream);
    hdbViewParser.setBuildParseTree(true);
    ParseTree parseTree = hdbViewParser.hdbViewDefinition();

    HDBViewDefinitionListener hdbViewDefinitionListener = new HDBViewDefinitionListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(hdbViewDefinitionListener, parseTree);

    HDBViewDefinitionModel model = hdbViewDefinitionListener.getModel();
    model.checkForAllMandatoryFieldsPresence();
    assertNotNull(model);
    assertEquals(hdbViewParser.getNumberOfSyntaxErrors(), 0);

    var osLineSeparator = System.lineSeparator();
    assertEquals( osLineSeparator
        + "-- some comment" + osLineSeparator
        + "select distinct code from" + osLineSeparator
        + "(" + osLineSeparator
        + "    select current_status_code as code, status_model_code" + osLineSeparator
        + "    from \"sap.db.status::t_status_model_transition\"" + osLineSeparator
        + "    union all" + osLineSeparator
        + "    select next_status_code as code, status_model_code" + osLineSeparator
        + "    from \"sap.db.status::t_status_model_transition\"" + osLineSeparator
        + ")" + osLineSeparator
        + "where status_model_code = 'sap.config.EVALUATION'" + osLineSeparator, model.getQuery());
  }
}
