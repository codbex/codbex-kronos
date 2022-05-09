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
package com.codbex.kronos.hdi.parser;

import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;
import com.codbex.kronos.hdb.ds.model.XSKDataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdi.XSKDataStructureHDIModel;
import com.codbex.kronos.hdb.ds.parser.hdi.XSKHDIParser;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.junit.Test;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class XSKHDIParserTest {

  @Test
  public void parseValidContent() throws IOException, XSKDataStructuresException {
    String location = "/ValidHDIContent.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(XSKHDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);

    XSKDataStructureParametersModel parametersModel =
        new XSKDataStructureParametersModel(null, location, content, null);
    XSKDataStructureHDIModel model = new XSKHDIParser().parse(parametersModel);
    assertEquals("/hdi-ext/config.hdiconfig", model.getConfiguration());
    assertEquals(new String[]{"DBADMIN"}, model.getUsers());
    assertEquals("/hdi-ext/config.hdiconfig", model.getConfiguration());
    assertEquals("XSK_HDI_EXT_GROUP", model.getGroup());
    assertEquals("XSK_HDI_EXT", model.getContainer());
    assertEquals(new String[]{"/hdi-ext/Customers.hdbsynonym", "/hdi-ext/CustomersCalcView.hdbcalculationview"}, model.getDeploy());
    assertTrue(model.getUndeploy().length == 0);

  }

  @Test
  public void parseNonStringProperties() throws IOException {
    String location = "/NonStringProperties.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(XSKHDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);
    XSKDataStructureParametersModel parametersModel =
        new XSKDataStructureParametersModel(null, location, content, null);
    assertThrows(JsonSyntaxException.class, () -> new XSKHDIParser().parse(parametersModel));
  }

  @Test
  public void parseMissingMandatoryProperties() throws IOException {
    String location = "/MissingMandatoryProperty.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(XSKHDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);
    XSKDataStructureParametersModel parametersModel =
        new XSKDataStructureParametersModel(null, location, content, null);
    assertThrows(JsonParseException.class, () -> new XSKHDIParser().parse(parametersModel));
  }

  @Test
  public void parseSameDeploymentFile() throws IOException {
    String location = "/SameDeploymentFile.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(XSKHDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);
    XSKDataStructureParametersModel parametersModel =
        new XSKDataStructureParametersModel(null, location, content, null);
    assertThrows(JsonParseException.class, () -> new XSKHDIParser().parse(parametersModel));
  }

  @Test
  public void parseMissingDeploymentFile() throws IOException {
    String location = "/NoDeploymentFiles.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(XSKHDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);
    XSKDataStructureParametersModel parametersModel =
        new XSKDataStructureParametersModel(null, location, content, null);
    assertThrows(JsonParseException.class, () -> new XSKHDIParser().parse(parametersModel));
  }
}
