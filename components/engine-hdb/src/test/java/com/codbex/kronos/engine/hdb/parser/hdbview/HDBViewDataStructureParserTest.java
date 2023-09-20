/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.hdb.parser.hdbview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.codbex.kronos.engine.hdb.domain.HDBView;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.exceptions.ArtifactParserException;

/**
 * The Class HDBViewDataStructureParserTest.
 */
@SpringBootTest(classes = {HDBDataStructureModelFactory.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBViewDataStructureParserTest {

  /**
	 * Parses the hana XS classic content successfully.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void parseHanaXSClassicContentSuccessfully() throws Exception {
    String hdbviewSample = org.apache.commons.io.IOUtils
        .toString(HDBViewDataStructureParserTest.class.getResourceAsStream("/ItemsByOrderHANAv1.hdbview"), StandardCharsets.UTF_8);
    HDBView model = HDBDataStructureModelFactory.parseView("/ItemsByOrderHANAv1.hdbview", hdbviewSample);
    assertEquals("MYSCHEMA", model.getSchema());
    assertEquals(2, model.getDependsOn().size());
    assertEquals("acme.com.test.tables::MY_TABLE1", model.getDependsOn().get(0));
    assertEquals("acme.com.test.views::MY_VIEW1", model.getDependsOn().get(1));
    assertEquals(1, model.getDependsOnTable().size());
    assertEquals("acme.com.test.tables::MY_TABLE1", model.getDependsOnTable().get(0));
    assertEquals(1, model.getDependsOnView().size());
    assertEquals("acme.com.test.views::MY_VIEW1", model.getDependsOnView().get(0));
    assertEquals(
        "SELECT T1.\"Column2\" FROM \"MYSCHEMA\".\"acme.com.test.tables::MY_TABLE1\" AS T1 LEFT JOIN \"acme.com.test.views::MY_VIEW1\" AS T2 ON T1.\"Column1\" = T2.\"Column1\"",
        model.getQuery());
    assertEquals(true, model.isClassic());
//    assertEquals(4, model.getDependencies().size());
//    assertEquals("acme.com.test.tables::MY_TABLE1", model.getDependencies().get(0).getName());
//    assertEquals("none", model.getDependencies().get(0).getType());
//    assertEquals("acme.com.test.views::MY_VIEW1", model.getDependencies().get(1).getName());
//    assertEquals("none", model.getDependencies().get(1).getType());
//    assertEquals("acme.com.test.tables::MY_TABLE1", model.getDependencies().get(2).getName());
//    assertEquals("TABLE", model.getDependencies().get(2).getType());
//    assertEquals("acme.com.test.views::MY_VIEW1", model.getDependencies().get(3).getName());
//    assertEquals("VIEW", model.getDependencies().get(3).getType());
  }

  /**
	 * Parses the hana XS advanced content successfully.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void parseHanaXSAdvancedContentSuccessfully() throws Exception {
    String hdbviewSample = org.apache.commons.io.IOUtils
        .toString(HDBViewDataStructureParserTest.class.getResourceAsStream("/ItemsByOrderHANAv2.hdbview"), StandardCharsets.UTF_8);
    HDBView model = HDBDataStructureModelFactory.parseView("hdb_view/db/ItemsByOrderHANAv2.hdbview", hdbviewSample);
    assertEquals(false, model.isClassic());
    assertEquals(hdbviewSample, model.getContent());
  }

  /**
	 * Parses the hana XS advanced content no quotes successfully.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void parseHanaXSAdvancedContentNoQuotesSuccessfully() throws Exception {
    String content = " view toni AS SELECT OD_NAME FROM KRONOS_ODATA";
    HDBView model = HDBDataStructureModelFactory.parseView("hdb_view/db/ItemsByOrderHANAv2.hdbview", content);
    assertEquals(false, model.isClassic());
    assertEquals(content, model.getContent());
  }

  /**
	 * Parses the hana XS classic content with lexer error fail.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void parseHanaXSClassicContentWithLexerErrorFail() throws Exception {
    String content = "schema = \"MY_SCHEMA\";\n" +
        "query = \"\";" +
        "depends_on_table = [SAEWQ.\"sap.test.db.basis::t_table_name\"];";
    assertThrows(ArtifactParserException.class, () -> HDBDataStructureModelFactory.parseView("hdb_view/db/test.hdbview", content));
  }

  /**
	 * Parses the hana XS classic content with syntax error fail.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void parseHanaXSClassicContentWithSyntaxErrorFail() throws Exception {
    String content = "schema = ;\n" +
        "query = \"\";" +
        "depends_on_table = [\"sap.test.db.basis::t_table_name\"];";
    assertThrows(ArtifactParserException.class, () -> HDBDataStructureModelFactory.parseView("hdb_view/db/test.hdbview", content));
  }
	
}