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
package com.codbex.kronos.hdb.ds.parser.hdbschema;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;

import org.eclipse.dirigible.core.test.AbstractDirigibleTest;
import org.junit.Test;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.model.DataStructureModelFactory;
import com.codbex.kronos.hdb.ds.model.hdbschema.DataStructureHDBSchemaModel;

public class HDBSchemaDataStructureParserTest extends AbstractDirigibleTest {

  @Test
  public void parseHdbschemaFileSuccessfully() throws Exception {
    String hdbschemaSample = org.apache.commons.io.IOUtils
        .toString(HDBSchemaDataStructureParserTest.class.getResourceAsStream("/Myschema.hdbschema"), StandardCharsets.UTF_8);
    DataStructureHDBSchemaModel model = DataStructureModelFactory.parseSchema("/Myschema.hdbschema", hdbschemaSample);
    assertEquals("MySchema", model.getSchema());
  }

  @Test(expected = ArtifactParserException.class)
  public void parseHanaXSClassicContentWithLexerErrorFail() throws Exception {
    String content = "schema_name='';";
    DataStructureModelFactory.parseView("db/test.hdbschema", content);
  }

  @Test(expected = ArtifactParserException.class)
  public void parseHanaXSClassicContentWithSyntaxErrorFail() throws Exception {
    String content = "schema_name=";
    DataStructureModelFactory.parseView("db/test.hdbschema", content);
  }
}