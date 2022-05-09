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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.codbex.kronos.exceptions.XSKArtifactParserException;
import com.codbex.kronos.parser.hdbti.custom.XSKHDBTIParser;
import com.codbex.kronos.parser.hdbti.exception.DuplicateFieldNameException;
import com.codbex.kronos.parser.hdbti.exception.TablePropertySyntaxException;
import com.codbex.kronos.parser.hdbti.exception.XSKHDBTIMissingPropertyException;
import com.codbex.kronos.parser.hdbti.exception.XSKHDBTISyntaxErrorException;
import com.codbex.kronos.parser.hdbti.models.XSKHDBTIImportConfigModel;
import com.codbex.kronos.parser.hdbti.models.XSKHDBTIImportModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.eclipse.dirigible.core.test.AbstractDirigibleTest;
import org.junit.Test;

public class XSKHDBTIParserTest extends AbstractDirigibleTest {

  @Test
  public void testValidInputAllFieldsAssignedProperlyParseSuccessfully()
      throws IOException, XSKHDBTISyntaxErrorException, XSKArtifactParserException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(XSKHDBTIParserTest.class.getResourceAsStream("/sample.hdbti"), StandardCharsets.UTF_8);

    XSKHDBTIParser xskhdbtiParser = new XSKHDBTIParser();
    XSKHDBTIImportModel importModel = xskhdbtiParser.parse("/test/xsk/com/acme/sample.hdbti", hdbtiSample);
    XSKHDBTIImportConfigModel configModel = importModel.getConfigModels().get(0);

    int expectedConfigsSize = 1;
    assertEquals(expectedConfigsSize, importModel.getConfigModels().size());
    assertEquals(configModel.getTableName(), "myTable");
    assertEquals(configModel.getSchemaName(), "mySchema");
    assertEquals(configModel.getFileName(), "acme.ti2.demo:myData.csv");
    assertEquals(configModel.getHeader(), false);
    assertEquals(configModel.getUseHeaderNames(), false);
    assertEquals(configModel.getDelimField(), ";");
    assertEquals(configModel.getDelimEnclosing(), "\"");
    assertEquals(configModel.getDistinguishEmptyFromNull(), true);
    int expectedKeysSize = 1;
    List<XSKHDBTIImportConfigModel.Pair> keys = configModel.getKeys();
    assertEquals(keys.size(), expectedKeysSize);
    assertEquals(keys.get(0).getColumn(), "GROUP_TYPE");
    assertEquals(keys.get(0).getValues(), new ArrayList<>(Arrays.asList("BW_CUBE", "BW_DSO", "BW_PSA")));
  }

  @Test
  public void testParseConfigObjectContainsMultipleTableDeclarationsShouldThrowProperException() throws IOException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(XSKHDBTIParserTest.class.getResourceAsStream("/multipleTableDeclarations.hdbti"), StandardCharsets.UTF_8);
    XSKHDBTIParser xskhdbtiParser = new XSKHDBTIParser();

    try {
      xskhdbtiParser.parse("/test/xsk/com/acme/duplicateKeys.hdbti", hdbtiSample);
    } catch (DuplicateFieldNameException duplicateFieldNameException) {
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testParseContainsInvalidSyntaxShouldThrowException() throws IOException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(XSKHDBTIParserTest.class.getResourceAsStream("/invalidSyntax.hdbti"), StandardCharsets.UTF_8);
    XSKHDBTIParser xskhdbtiParser = new XSKHDBTIParser();

    try {
      xskhdbtiParser.parse("/test/xsk/com/acme/invalidSyntax.hdbti", hdbtiSample);
    } catch (XSKArtifactParserException parseErrorException) {
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testParseContainsInvalidTablePropertySyntaxShouldThrowProperException() throws IOException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(XSKHDBTIParserTest.class.getResourceAsStream("/invalidTablePropertySyntax.hdbti"), StandardCharsets.UTF_8);
    XSKHDBTIParser xskhdbtiParser = new XSKHDBTIParser();

    try {
      xskhdbtiParser.parse("/test/xsk/com/acme/invalidTablePropertySyntax.hdbti", hdbtiSample);
    } catch (TablePropertySyntaxException parseErrorException) {
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testParseConfigObjectContainsDuplicateKeysShouldThrowProperException() throws IOException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(XSKHDBTIParserTest.class.getResourceAsStream("/duplicateKeys.hdbti"), StandardCharsets.UTF_8);
    XSKHDBTIParser xskhdbtiParser = new XSKHDBTIParser();

    try {
      xskhdbtiParser.parse("/test/xsk/com/acme/duplicateKeys.hdbti", hdbtiSample);
    } catch (DuplicateFieldNameException duplicateFieldNameException) {
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testParseConfigObjectFieldsRandomOrderShouldPass()
      throws IOException, XSKHDBTISyntaxErrorException, XSKArtifactParserException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(XSKHDBTIParserTest.class.getResourceAsStream("/randomOrder.hdbti"), StandardCharsets.UTF_8);
    XSKHDBTIParser xskhdbtiParser = new XSKHDBTIParser();

    XSKHDBTIImportModel model = xskhdbtiParser.parse("/test/xsk/com/acme/randomOrder.hdbti", hdbtiSample);
    assertNotNull("The XSKHDBTIImportModel should not be null after parsing", model);
  }

  @Test(expected = XSKArtifactParserException.class)
  public void parseHDBTIContentWithLexerErrorFail() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(XSKHDBTIParserTest.class.getResourceAsStream("/lexerError.hdbti"), StandardCharsets.UTF_8);
    XSKHDBTIParser xskhdbtiParser = new XSKHDBTIParser();
    xskhdbtiParser.parse("/test/xsk/com/acme/lexerError.hdbti", content);
  }

  @Test(expected = XSKArtifactParserException.class)
  public void parseHDBTIContentWithSyntaxErrorFail() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(XSKHDBTIParserTest.class.getResourceAsStream("/parserError.hdbti"), StandardCharsets.UTF_8);
    XSKHDBTIParser xskhdbtiParser = new XSKHDBTIParser();
    xskhdbtiParser.parse("/test/xsk/com/acme/syntaxError.hdbti", content);
  }

  @Test(expected = XSKHDBTIMissingPropertyException.class)
  public void parseHDBTIMissingSchemaException() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(XSKHDBTIParserTest.class.getResourceAsStream("/missingSchema.hdbti"), StandardCharsets.UTF_8);
    XSKHDBTIParser xskhdbtiParser = new XSKHDBTIParser();
    xskhdbtiParser.parse("/test/xsk/com/acme/missingSchema.hdbti", content);
  }
}
