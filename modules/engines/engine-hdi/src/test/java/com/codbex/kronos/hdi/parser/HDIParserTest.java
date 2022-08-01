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

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdi.HDIDataStructureModel;
import com.codbex.kronos.hdb.ds.parser.hdi.HDIParser;
import org.junit.Test;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class HDIParserTest {

  @Test
  public void parseValidContent() throws IOException, DataStructuresException {
    String location = "/ValidHDIContent.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(HDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);

    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    HDIDataStructureModel model = new HDIParser().parse(parametersModel);
    assertEquals("/hdi-ext/config.hdiconfig", model.getConfiguration());
    assertEquals(new String[]{"DBADMIN"}, model.getUsers());
    assertEquals("/hdi-ext/config.hdiconfig", model.getConfiguration());
    assertEquals("KRONOS_HDI_EXT_GROUP", model.getGroup());
    assertEquals("KRONOS_HDI_EXT", model.getContainer());
    assertEquals(new String[]{"/hdi-ext/Customers.hdbsynonym", "/hdi-ext/CustomersCalcView.hdbcalculationview"}, model.getDeploy());
    assertTrue(model.getUndeploy().length == 0);

  }

  @Test
  public void parseNonStringProperties() throws IOException {
    String location = "/NonStringProperties.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(HDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    assertThrows(JsonSyntaxException.class, () -> new HDIParser().parse(parametersModel));
  }

  @Test
  public void parseMissingMandatoryProperties() throws IOException {
    String location = "/MissingMandatoryProperty.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(HDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    assertThrows(JsonParseException.class, () -> new HDIParser().parse(parametersModel));
  }

  @Test
  public void parseSameDeploymentFile() throws IOException {
    String location = "/SameDeploymentFile.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(HDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    assertThrows(JsonParseException.class, () -> new HDIParser().parse(parametersModel));
  }

  @Test
  public void parseMissingDeploymentFile() throws IOException {
    String location = "/NoDeploymentFiles.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(HDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(null, location, content, null);
    assertThrows(JsonParseException.class, () -> new HDIParser().parse(parametersModel));
  }
}
