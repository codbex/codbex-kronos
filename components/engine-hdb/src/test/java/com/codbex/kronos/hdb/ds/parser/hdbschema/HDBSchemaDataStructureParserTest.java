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

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.codbex.kronos.engine.hdb.domain.HDBSchema;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.exceptions.ArtifactParserException;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBSchemaDataStructureParserTest {

  @Test
  public void parseHdbschemaFileSuccessfully() throws Exception {
    String hdbschemaSample = org.apache.commons.io.IOUtils
        .toString(HDBSchemaDataStructureParserTest.class.getResourceAsStream("/Myschema.hdbschema"), StandardCharsets.UTF_8);
    HDBSchema model = HDBDataStructureModelFactory.parseSchema("/Myschema.hdbschema", hdbschemaSample);
    assertEquals("MySchema", model.getSchema());
  }

  @Test
  public void parseHanaXSClassicContentWithLexerErrorFail() throws Exception { 
	ArtifactParserException exception = Assertions.assertThrows(ArtifactParserException.class, () -> {
		  String content = "schema_name='';";
		  HDBDataStructureModelFactory.parseSchema("db/test.hdbschema", content);
    });
    Assertions.assertEquals("Wrong format of HDB HDB Schema: [db/test.hdbschema] during parsing. Ensure you are using the correct format for the correct compatibility version.", exception.getMessage());
  }

  @Test
  public void parseHanaXSClassicContentWithSyntaxErrorFail() throws Exception {
	ArtifactParserException exception = Assertions.assertThrows(ArtifactParserException.class, () -> {
		String content = "schema_name=";
	    HDBDataStructureModelFactory.parseSchema("db/test.hdbschema", content);
    });
    Assertions.assertEquals("Wrong format of HDB HDB Schema: [db/test.hdbschema] during parsing. Ensure you are using the correct format for the correct compatibility version.", exception.getMessage());
  }
  
  /**
   * The Class TestConfiguration.
   */
  @EnableJpaRepositories(basePackages = "com.codbex.kronos")
  @SpringBootApplication(scanBasePackages = {"com.codbex.kronos", "org.eclipse.dirigible.components"})
  @EnableScheduling
  static class TestConfiguration {
  }
	
}