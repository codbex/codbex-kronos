/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
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

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.codbex.kronos.engine.hdb.domain.HDBSequence;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.hdbsequence.exceptions.HDBSequenceDuplicatePropertyException;
import com.codbex.kronos.parser.hdbsequence.exceptions.HDBSequenceMissingPropertyException;

import jakarta.transaction.Transactional;

/**
 * The Class HDBSequenceDataStructureParserTest.
 */
@SpringBootTest(classes = {HDBDataStructureModelFactory.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBSequenceDataStructureParserTest {

    /**
     * Parses the hana XS classic content.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseHanaXSClassicContent() throws Exception {
        String content = org.apache.commons.io.IOUtils.toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream(
                "/test/com/codbex/kronos/SampleSequence_HanaXSClassic.hdbsequence"), StandardCharsets.UTF_8);
        HDBSequence model =
                HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/SampleSequence_HanaXSClassic.hdbsequence", content);

        assertEquals("MYSCHEMA", model.getSchema());
        assertEquals(Integer.valueOf(10), model.getStartWith());
        assertEquals(Integer.valueOf(30), model.getMaxValue());
        assertEquals(false, model.getNoMaxValue());
        assertEquals(true, model.getNoMinValue());
        assertEquals(false, model.getCycles());
        assertEquals(true, model.isClassic());
    }

    /**
     * Parses the default values.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseDefaultValues() throws Exception {
        String content = org.apache.commons.io.IOUtils.toString(
                HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/SchemaOnlySequence.hdbsequence"),
                StandardCharsets.UTF_8);
        HDBSequence model = HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/SchemaOnlySequence.hdbsequence", content);
        assertFalse(model.getIsPublic());
        assertEquals(Integer.valueOf(1), model.getStartWith());
        assertEquals(Integer.valueOf(1), model.getIncrementBy());
        assertEquals(Integer.valueOf(1), model.getMinValue());
    }

    /**
     * Parses the depends on content.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseDependsOnContent() throws Exception {
        String content = org.apache.commons.io.IOUtils.toString(
                HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/DependsOnSequence.hdbsequence"),
                StandardCharsets.UTF_8);
        HDBSequence model = HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/DependsOnSequence.hdbsequence", content);
        assertEquals("sap.ino.db.iam::t_identity", model.getDependsOnTable());
        assertEquals("sap.ino.db.iam::t_view", model.getDependsOnView());
    }

    /**
     * Parses the hana XS advanced content.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseHanaXSAdvancedContent() throws Exception {
        String content = org.apache.commons.io.IOUtils.toString(HDBSequenceDataStructureParserTest.class.getResourceAsStream(
                "/test/com/codbex/kronos/CustomerId_HanaXSAdvanced.hdbsequence"), StandardCharsets.UTF_8);
        HDBSequence model =
                HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/CustomerId_HanaXSAdvanced.hdbsequence", content);
        assertEquals(false, model.isClassic());
        assertEquals(content, model.getContent());
    }

    /**
     * Parses the grammar unreadable content.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseGrammarUnreadableContent() throws Exception {
        String content = "Some invalid content.";
        assertThrows(ArtifactParserException.class,
                () -> HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/InvalidContent.hdbsequence", content));
    }

    /**
     * Parses the repetitive properties.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseRepetitiveProperties() throws Exception {
        String content = org.apache.commons.io.IOUtils.toString(
                HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/RepetitivePropsSequence.hdbsequence"),
                StandardCharsets.UTF_8);
        assertThrows(HDBSequenceDuplicatePropertyException.class,
                () -> HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/RepetitivePropsSequence.hdbsequence", content));
    }

    /**
     * Parses the randomly ordered content.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseRandomlyOrderedContent() throws Exception {
        String content = org.apache.commons.io.IOUtils.toString(
                HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/RandomlyOrderedSequence.hdbsequence"),
                StandardCharsets.UTF_8);
        HDBSequence model =
                HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/RandomlyOrderedSequence.hdbsequence", content);

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

    /**
     * Parses the missing mandatory property.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseMissingMandatoryProperty() throws Exception {
        String content = org.apache.commons.io.IOUtils.toString(
                HDBSequenceDataStructureParserTest.class.getResourceAsStream("/test/com/codbex/kronos/MissingPropSequence.hdbsequence"),
                StandardCharsets.UTF_8);

        assertThrows(HDBSequenceMissingPropertyException.class,
                () -> HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/MissingPropSequence.hdbsequence", content));
    }

    /**
     * Parses the non quoted seq.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseNonQuotedSeq() throws Exception {
        String content = "  SEQUENCE seq";
        HDBSequence model = HDBDataStructureModelFactory.parseSequence("/test/com/codbex/kronos/someFileName.hdbsequence", content);
        assertEquals(false, model.isClassic());
    }

    /**
     * Parses the hana XS classic content with lexer error fail.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseHanaXSClassicContentWithLexerErrorFail() throws Exception {
        String content = "start_with= 10;\n" + "nomaxvalue=dddddfalse;";
        assertThrows(ArtifactParserException.class, () -> HDBDataStructureModelFactory.parseSequence("db/test.hdbsequence", content));
    }

    /**
     * Parses the hana XS classic content with syntax error fail.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseHanaXSClassicContentWithSyntaxErrorFail() throws Exception {
        String content = "start_with= 10;\n" + "nomaxvalue=";
        assertThrows(ArtifactParserException.class, () -> HDBDataStructureModelFactory.parseSequence("db/test.hdbsequence", content));
    }

}
