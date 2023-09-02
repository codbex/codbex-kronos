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
package com.codbex.kronos.engine.hdb.parser.hdbsequence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.codbex.kronos.engine.hdb.domain.HDBSequence;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.hdbsequence.exceptions.HDBSequenceDuplicatePropertyException;
import com.codbex.kronos.parser.hdbsequence.exceptions.HDBSequenceMissingPropertyException;

@Disabled
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBSequenceDataStructureParserTest {

  @Test
  public void parseHanaXSClassicContent() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/SampleSequence_HanaXSClassic.hdbsequence"), StandardCharsets.UTF_8);
    HDBSequence model = HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/SampleSequence_HanaXSClassic.hdbsequence", content);

    assertEquals("MYSCHEMA", model.getSchema());
    assertEquals(Integer.valueOf(10), model.getStartWith());
    assertEquals(Integer.valueOf(30), model.getMaxValue());
    assertEquals(false, model.getNoMaxValue());
    assertEquals(true, model.getNoMinValue());
    assertEquals(false, model.getCycles());
    assertEquals(true, model.isClassic());
  }

  @Test
  public void parseDefaultValues() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/SchemaOnlySequence.hdbsequence"), StandardCharsets.UTF_8);
    HDBSequence model = HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/SchemaOnlySequence.hdbsequence", content);
    assertFalse(model.getIsPublic());
    assertEquals(Integer.valueOf(1), model.getStartWith());
    assertEquals(Integer.valueOf(1), model.getIncrementBy());
    assertEquals(Integer.valueOf(1), model.getMinValue());
  }

  @Test
  public void parseDependsOnContent() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/DependsOnSequence.hdbsequence"), StandardCharsets.UTF_8);
    HDBSequence model = HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/DependsOnSequence.hdbsequence", content);
    assertEquals("sap.ino.db.iam::t_identity", model.getDependsOnTable());
    assertEquals("sap.ino.db.iam::t_view", model.getDependsOnView());
  }

  @Test
  public void parseHanaXSAdvancedContent() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/CustomerId_HanaXSAdvanced.hdbsequence"), StandardCharsets.UTF_8);
    HDBSequence model = HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/CustomerId_HanaXSAdvanced.hdbsequence", content);
    assertEquals(false, model.isClassic());
    assertEquals(content, model.getContent());
  }


  @Test
  public void parseGrammarUnreadableContent() throws Exception {
    String content = "Some invalid content.";
    assertThrows(ArtifactParserException.class, () -> HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/InvalidContent.hdbsequence", content));
  }


  @Test
  public void parseRepetitiveProperties() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/RepetitivePropsSequence.hdbsequence"), StandardCharsets.UTF_8);
    assertThrows(HDBSequenceDuplicatePropertyException.class, () -> HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/RepetitivePropsSequence.hdbsequence", content));
  }

  @Test
  public void parseRandomlyOrderedContent() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/RandomlyOrderedSequence.hdbsequence"),
            StandardCharsets.UTF_8);
    HDBSequence model = HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/RandomlyOrderedSequence.hdbsequence", content);

    assertEquals("USR_9TCD6SXS67DP7AFLFE420B8EB", model.getSchema());
    assertEquals(Integer.valueOf(10), model.getStartWith());
    assertEquals(Integer.valueOf(30), model.getMaxValue());
    assertEquals(false, model.getNoMaxValue());
    assertEquals(true, model.getNoMinValue());
    assertEquals(false, model.getCycles());
    assertFalse(model.getIsPublic());
    assertEquals(Integer.valueOf(-2), model.getIncrementBy());
    assertEquals(true, model.isClassic());
  }


  @Test
  public void parseMissingMandatoryProperty() throws Exception {
    String content = org.apache.commons.io.IOUtils
        .toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/MissingPropSequence.hdbsequence"),
            StandardCharsets.UTF_8);

    assertThrows(HDBSequenceMissingPropertyException.class, () -> HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/MissingPropSequence.hdbsequence", content));
  }

  @Test
  public void parseNonQuotedSeq() throws Exception {
    String content = "  SEQUENCE seq";
    HDBSequence model = HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/someFileName.hdbsequence", content);
    assertEquals(false, model.isClassic());
  }

  @Test
  public void parseHanaXSClassicContentWithLexerErrorFail() throws Exception {
    String content = "start_with= 10;\n" + "nomaxvalue=dddddfalse;";
    assertThrows(ArtifactParserException.class, () -> HDBDataStructureModelFactory.parseSequence("db/test.hdbsequence", content));
  }

  @Test
  public void parseHanaXSClassicContentWithSyntaxErrorFail() throws Exception {
    String content = "start_with= 10;\n" + "nomaxvalue=";
    assertThrows(ArtifactParserException.class, () -> HDBDataStructureModelFactory.parseSequence("db/test.hdbsequence", content));
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
