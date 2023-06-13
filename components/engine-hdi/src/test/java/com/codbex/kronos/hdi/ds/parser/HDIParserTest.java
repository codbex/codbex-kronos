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
package com.codbex.kronos.hdi.ds.parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.parser.HDBParameters;
import com.codbex.kronos.engine.hdi.domain.HDI;
import com.codbex.kronos.engine.hdi.parser.HDIParser;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

public class HDIParserTest {

  @Test
  public void parseValidContent() throws IOException, DataStructuresException {
    String location = "/ValidHDIContent.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(HDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);

    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    HDI model = new HDIParser().parse(parametersModel);
    assertEquals("/hdi-ext/config.hdiconfig", model.getConfiguration());
    assertArrayEquals(new String[]{"DBADMIN"}, model.getUsers());
    assertEquals("/hdi-ext/config.hdiconfig", model.getConfiguration());
    assertEquals("KRONOS_HDI_EXT_GROUP", model.getGroup());
    assertEquals("KRONOS_HDI_EXT", model.getContainer());
    assertArrayEquals(new String[]{"/hdi-ext/Customers.hdbsynonym", "/hdi-ext/CustomersCalcView.hdbcalculationview"}, model.getDeploy());
    assertTrue(model.getUndeploy().length == 0);

  }

  @Test
  public void parseNonStringProperties() throws IOException {
    String location = "/NonStringProperties.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(HDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    assertThrows(JsonSyntaxException.class, () -> new HDIParser().parse(parametersModel));
  }

  @Test
  public void parseMissingMandatoryProperties() throws IOException {
    String location = "/MissingMandatoryProperty.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(HDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    assertThrows(JsonParseException.class, () -> new HDIParser().parse(parametersModel));
  }

  @Test
  public void parseSameDeploymentFile() throws IOException {
    String location = "/SameDeploymentFile.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(HDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    assertThrows(JsonParseException.class, () -> new HDIParser().parse(parametersModel));
  }

  @Test
  public void parseMissingDeploymentFile() throws IOException {
    String location = "/NoDeploymentFiles.hdi";
    String content = org.apache.commons.io.IOUtils
        .toString(HDIParserTest.class.getResourceAsStream(location), StandardCharsets.UTF_8);
    HDBParameters parametersModel =
        new HDBParameters(null, location, content, null);
    assertThrows(JsonParseException.class, () -> new HDIParser().parse(parametersModel));
  }
}
