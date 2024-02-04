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
package com.codbex.kronos.engine.hdb.parser.hdbschema;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.codbex.kronos.engine.hdb.domain.HDBSchema;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.exceptions.ArtifactParserException;

import jakarta.transaction.Transactional;

/**
 * The Class HDBSchemaDataStructureParserTest.
 */
@SpringBootTest(classes = {HDBDataStructureModelFactory.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBSchemaDataStructureParserTest {

  /**
	 * Parses the hdbschema file successfully.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void parseHdbschemaFileSuccessfully() throws Exception {
    String hdbschemaSample = org.apache.commons.io.IOUtils
        .toString(HDBSchemaDataStructureParserTest.class.getResourceAsStream("/Myschema.hdbschema"), StandardCharsets.UTF_8);
    HDBSchema model = HDBDataStructureModelFactory.parseSchema("/Myschema.hdbschema", hdbschemaSample);
    assertEquals("MySchema", model.getSchema());
  }

  /**
	 * Parses the hana XS classic content with lexer error fail.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void parseHanaXSClassicContentWithLexerErrorFail() throws Exception { 
	ArtifactParserException exception = Assertions.assertThrows(ArtifactParserException.class, () -> {
		  String content = "schema_name='';";
		  HDBDataStructureModelFactory.parseSchema("db/test.hdbschema", content);
    });
    Assertions.assertEquals("Wrong format of HDB HDB Schema: [db/test.hdbschema] during parsing. Ensure you are using the correct format for the correct compatibility version.", exception.getMessage());
  }

  /**
	 * Parses the hana XS classic content with syntax error fail.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void parseHanaXSClassicContentWithSyntaxErrorFail() throws Exception {
	ArtifactParserException exception = Assertions.assertThrows(ArtifactParserException.class, () -> {
		String content = "schema_name=";
	    HDBDataStructureModelFactory.parseSchema("db/test.hdbschema", content);
    });
    Assertions.assertEquals("Wrong format of HDB HDB Schema: [db/test.hdbschema] during parsing. Ensure you are using the correct format for the correct compatibility version.", exception.getMessage());
  }

}