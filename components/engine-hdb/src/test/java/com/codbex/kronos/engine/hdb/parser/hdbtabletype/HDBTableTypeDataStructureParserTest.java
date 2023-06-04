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
package com.codbex.kronos.engine.hdb.parser.hdbtabletype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.domain.HDBTableType;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.exceptions.ArtifactParserException;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBTableTypeDataStructureParserTest {

  private static String getTableTypeContentFromResources(String tableTypeFile) {
    InputStream tableTypeFileInputStream = HDBTableTypeDataStructureParserTest.class.getResourceAsStream("/table-types/" + tableTypeFile);
    if (tableTypeFileInputStream == null) {
      throw tableTypeFileCouldNotBeRead(tableTypeFile);
    }

    try {
      return IOUtils.toString(tableTypeFileInputStream, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw tableTypeFileCouldNotBeRead(tableTypeFile);
    }
  }

  private static RuntimeException tableTypeFileCouldNotBeRead(String tableTypeFile) {
    return new RuntimeException("Table type file '" + tableTypeFile + "' could not be found.");
  }

  @Test
  public void testParsedXSAdvancedTableTypeSchemaAndNameAreExtracted()
      throws DataStructuresException, ArtifactParserException, IOException {
    String tableTypeFile = "tableTypeWithCommentsAndSchemaAndName.hdbtabletype";
    String tableTypeContent = getTableTypeContentFromResources(tableTypeFile);

    HDBTableType parsedModel = HDBDataStructureModelFactory.parseTableType(tableTypeFile, tableTypeContent);
    assertEquals("KRONOS_SAMPLES_HDI_CUBE", parsedModel.getSchema(), "Unexpected schema found");
    assertEquals("customer_sample::publishers", parsedModel.getName(), "Unexpected name found");
  }
  
  @Test
  public void testParsedXSAdvancedTableTypeSchemaAndNameAreExtracted2()
      throws DataStructuresException, ArtifactParserException, IOException {
    String tableTypeFile = "tableTypeWithSchemaAndName.hdbtabletype";
    String tableTypeContent = getTableTypeContentFromResources(tableTypeFile);

    HDBTableType parsedModel = HDBDataStructureModelFactory.parseTableType(tableTypeFile, tableTypeContent);
    assertEquals("KRONOS_SAMPLES_HDI_CUBE", parsedModel.getSchema(), "Unexpected schema found");
    assertEquals("customer_sample::publishers", parsedModel.getName(), "Unexpected name found");
  }

  @Test
  public void testParsedXSAdvancedTableTypeNameIsExtractedWhenNoSchemaIsSet()
      throws DataStructuresException, ArtifactParserException, IOException {
    String tableTypeFile = "tableTypeWithCommentsAndNoSchema.hdbtabletype";
    String tableTypeContent = getTableTypeContentFromResources(tableTypeFile);

    HDBTableType parsedModel = HDBDataStructureModelFactory.parseTableType(tableTypeFile, tableTypeContent);

    assertNull(parsedModel.getSchema(), "Unexpected schema found");
    assertEquals("customer_sample::publishers", parsedModel.getName(), "Unexpected name found");
  }
  
  @Test
  public void testParsedXSAdvancedTableTypeNameIsExtractedWhenNoSchemaIsSet2()
      throws DataStructuresException, ArtifactParserException, IOException {
    String tableTypeFile = "tableTypeWithNoSchema.hdbtabletype";
    String tableTypeContent = getTableTypeContentFromResources(tableTypeFile);

    HDBTableType parsedModel = HDBDataStructureModelFactory.parseTableType(tableTypeFile, tableTypeContent);

    assertNull(parsedModel.getSchema(), "Unexpected schema found");
    assertEquals("customer_sample::publishers", parsedModel.getName(), "Unexpected name found");
  }

  @Test
  public void testParseWhenTableTypeDefinitionIsCorrect() throws Exception {
    String tableTypeContent = getTableTypeContentFromResources("test.hdbtabletype");

    HDBTableType parsedModel = HDBDataStructureModelFactory.parseTableType("test.hdbtabletype", tableTypeContent);

    assertEquals("CUSTOMERS_STRUCTURE", parsedModel.getName());
    assertEquals("test.hdbtabletype", parsedModel.getLocation());
    assertEquals("HDBTABLETYPE", parsedModel.getType());

    assertNotNull(parsedModel.getCreatedBy());
    assertNotNull(parsedModel.getCreatedAt());
    assertEquals(false, parsedModel.isClassic());
    assertEquals(tableTypeContent, parsedModel.getContent());
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
