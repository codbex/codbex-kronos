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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dirigible.core.test.AbstractDirigibleTest;
import org.junit.Test;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.hdbti.exception.DuplicateFieldNameException;
import com.codbex.kronos.parser.hdbti.exception.HDBTIMissingPropertyException;
import com.codbex.kronos.parser.hdbti.exception.HDBTISyntaxErrorException;
import com.codbex.kronos.parser.hdbti.exception.TablePropertySyntaxException;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportConfigModel;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportModel;

public class HDBTIParserTest extends AbstractDirigibleTest {

  @Test
  public void testValidInputAllFieldsAssignedProperlyParseSuccessfully()
      throws IOException, HDBTISyntaxErrorException, ArtifactParserException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(HDBTIParserTest.class.getResourceAsStream("/sample.hdbti"), StandardCharsets.UTF_8);

    HDBTIParser hdbtiParser = new HDBTIParser();
    HDBTIImportModel importModel = hdbtiParser.parse("/test/com/codbex/kronos/sample.hdbti", hdbtiSample);
    HDBTIImportConfigModel configModel = importModel.getConfigModels().get(0);

    int expectedConfigsSize = 1;
    assertEquals(expectedConfigsSize, importModel.getConfigModels().size());
    assertEquals(configModel.getTableName(), "myTable");
    assertEquals(configModel.getSchemaName(), "mySchema");
    assertEquals(configModel.getFileName(), "sap.ti2.demo:myData.csv");
    assertEquals(configModel.getHeader(), false);
    assertEquals(configModel.getUseHeaderNames(), false);
    assertEquals(configModel.getDelimField(), ";");
    assertEquals(configModel.getDelimEnclosing(), "\"");
    assertEquals(configModel.getDistinguishEmptyFromNull(), true);
    int expectedKeysSize = 1;
    List<HDBTIImportConfigModel.Pair> keys = configModel.getKeys();
    assertEquals(keys.size(), expectedKeysSize);
    assertEquals(keys.get(0).getColumn(), "GROUP_TYPE");
    assertEquals(keys.get(0).getValues(), new ArrayList<>(Arrays.asList("BW_CUBE", "BW_DSO", "BW_PSA")));
  }

  @Test
  public void testParseConfigObjectContainsMultipleTableDeclarationsShouldThrowProperException() throws IOException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(HDBTIParserTest.class.getResourceAsStream("/multipleTableDeclarations.hdbti"), StandardCharsets.UTF_8);
    HDBTIParser hdbtiParser = new HDBTIParser();

    try {
      hdbtiParser.parse("/test/com/codbex/kronos/duplicateKeys.hdbti", hdbtiSample);
    } catch (DuplicateFieldNameException duplicateFieldNameException) {
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testParseContainsInvalidSyntaxShouldThrowException() throws IOException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(HDBTIParserTest.class.getResourceAsStream("/invalidSyntax.hdbti"), StandardCharsets.UTF_8);
    HDBTIParser hdbtiParser = new HDBTIParser();

    try {
      hdbtiParser.parse("/test/com/codbex/kronos/invalidSyntax.hdbti", hdbtiSample);
    } catch (ArtifactParserException parseErrorException) {
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testParseContainsInvalidTablePropertySyntaxShouldThrowProperException() throws IOException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(HDBTIParserTest.class.getResourceAsStream("/invalidTablePropertySyntax.hdbti"), StandardCharsets.UTF_8);
    HDBTIParser hdbtiParser = new HDBTIParser();

    try {
      hdbtiParser.parse("/test/com/codbex/kronos/invalidTablePropertySyntax.hdbti", hdbtiSample);
    } catch (TablePropertySyntaxException parseErrorException) {
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testParseConfigObjectContainsDuplicateKeysShouldThrowProperException() throws IOException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(HDBTIParserTest.class.getResourceAsStream("/duplicateKeys.hdbti"), StandardCharsets.UTF_8);
    HDBTIParser hdbtiParser = new HDBTIParser();

    try {
      hdbtiParser.parse("/test/com/codbex/kronos/duplicateKeys.hdbti", hdbtiSample);
    } catch (DuplicateFieldNameException duplicateFieldNameException) {
      assertTrue(true);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testParseConfigObjectFieldsRandomOrderShouldPass()
      throws IOException, HDBTISyntaxErrorException, ArtifactParserException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(HDBTIParserTest.class.getResourceAsStream("/randomOrder.hdbti"), StandardCharsets.UTF_8);
    HDBTIParser hdbtiParser = new HDBTIParser();

    HDBTIImportModel model = hdbtiParser.parse("/test/com/codbex/kronos/randomOrder.hdbti", hdbtiSample);
    assertNotNull("The HDBTIImportModel should not be null after parsing", model);
  }

  @Test(expected = ArtifactParserException.class)
  public void parseHDBTIContentWithLexerErrorFail() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(HDBTIParserTest.class.getResourceAsStream("/lexerError.hdbti"), StandardCharsets.UTF_8);
    HDBTIParser hdbtiParser = new HDBTIParser();
    hdbtiParser.parse("/test/com/codbex/kronos/lexerError.hdbti", content);
  }

  @Test(expected = ArtifactParserException.class)
  public void parseHDBTIContentWithSyntaxErrorFail() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(HDBTIParserTest.class.getResourceAsStream("/parserError.hdbti"), StandardCharsets.UTF_8);
    HDBTIParser hdbtiParser = new HDBTIParser();
    hdbtiParser.parse("/test/com/codbex/kronos/syntaxError.hdbti", content);
  }

  @Test(expected = HDBTIMissingPropertyException.class)
  public void parseHDBTIMissingSchemaException() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(HDBTIParserTest.class.getResourceAsStream("/missingSchema.hdbti"), StandardCharsets.UTF_8);
    HDBTIParser hdbtiParser = new HDBTIParser();
    hdbtiParser.parse("/test/com/codbex/kronos/missingSchema.hdbti", content);
  }
}
