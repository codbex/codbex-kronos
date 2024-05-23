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
package com.codbex.kronos.engine.hdb.parser.hdbtable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.domain.HDBTableColumn;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.parser.HDBParameters;
import com.codbex.kronos.engine.hdb.parser.HDBTableDataStructureParser;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableDuplicatePropertyException;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableMissingPropertyException;

import jakarta.transaction.Transactional;

/**
 * The Class HDBTableDataStructureParserTest.
 */
@SpringBootTest(classes = {HDBDataStructureModelFactory.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBTableDataStructureParserTest {

    /**
     * Parses the table.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseTable() throws Exception {
        InputStream in = HDBTableDataStructureParserTest.class.getResourceAsStream("/teams.hdbtable");
        String contents = IOUtils.toString(in, StandardCharsets.UTF_8);
        HDBTable model = HDBDataStructureModelFactory.parseTable("/teams.hdbtable", contents);

        assertEquals(6, model.getColumns()
                             .size());
        assertEquals("teams", model.getName());
        assertEquals("TEAMS", model.getSchema());
        assertEquals("COLUMNSTORE", model.getTableType());
        assertEquals("Team players", model.getDescription());
        assertEquals("/teams.hdbtable", model.getLocation());
        assertEquals("HDBTABLE", model.getType());
        assertNotNull(model.getCreatedBy());
        assertNotNull(model.getCreatedAt());
        assertEquals(true, model.isClassic());
        assertEquals(contents, model.getContent());

        HDBTableColumn matchId = model.getColumns()
                                      .get(0);
        assertEquals("MATCH_ID", matchId.getName());
        assertEquals("NVARCHAR", matchId.getType());
        assertEquals("32", matchId.getLength());
        assertFalse(matchId.isNullable());
        assertTrue(matchId.isPrimaryKey());
        assertEquals("D1", matchId.getDefaultValue());
        assertEquals("test", matchId.getComment());
        assertTrue(matchId.isUnique());

        assertTrue(model.getColumns()
                        .get(1)
                        .isPrimaryKey());
        assertTrue(model.getColumns()
                        .get(2)
                        .isPrimaryKey());

        HDBTableColumn personRate = model.getColumns()
                                         .get(3);
        assertEquals("PERSON_RATE", personRate.getName());
        assertEquals("DECIMAL", personRate.getType());
        assertFalse(personRate.isNullable());
        assertFalse(personRate.isPrimaryKey());
        assertNull(personRate.getDefaultValue());
        assertEquals("20", personRate.getPrecision());
        assertEquals("3", personRate.getScale());
        assertFalse(personRate.isNullable());
        assertNull(personRate.getComment());

        HDBTableColumn changedBy = model.getColumns()
                                        .get(4);
        assertEquals("CHANGED_BY", changedBy.getName());
        assertEquals("NVARCHAR", changedBy.getType());
        assertEquals("256", changedBy.getLength());
        assertTrue(changedBy.isNullable());
        assertFalse(changedBy.isPrimaryKey());
        assertFalse(changedBy.isUnique());

        HDBTableColumn changedAt = model.getColumns()
                                        .get(5);
        assertEquals("CHANGED_AT", changedAt.getName());
        assertEquals("TIMESTAMP", changedAt.getType());
        assertTrue(changedAt.isNullable());
        assertFalse(changedAt.isPrimaryKey());
        assertFalse(changedAt.isUnique());

        assertEquals("PK_teams", model.getConstraints()
                                      .getPrimaryKey()
                                      .getName());
        assertEquals(3, model.getConstraints()
                             .getPrimaryKey()
                             .getColumns().length);
        assertNull(model.getConstraints()
                        .getPrimaryKey()
                        .getModifiers());
        assertEquals(0, model.getConstraints()
                             .getForeignKeys()
                             .size());
        assertEquals(1, model.getConstraints()
                             .getUniqueIndexes()
                             .size());
        assertEquals("INDEX1", model.getConstraints()
                                    .getUniqueIndexes()
                                    .get(0)
                                    .getName());
        assertEquals(1, model.getConstraints()
                             .getUniqueIndexes()
                             .get(0)
                             .getColumns().length);
        assertEquals("MATCH_ID", model.getConstraints()
                                      .getUniqueIndexes()
                                      .get(0)
                                      .getColumns()[0]);
        assertEquals(0, model.getConstraints()
                             .getChecks()
                             .size());
    }

    /**
     * Fail if parsing repetitive properties.
     *
     * @throws Exception the exception
     */
    @Test
    public void failIfParsingRepetitiveProperties() throws Exception {
        InputStream in = HDBTableDataStructureParserTest.class.getResourceAsStream("/DuplicateTableProperties.hdbtable");
        String content = IOUtils.toString(in, StandardCharsets.UTF_8);
        HDBParameters parametersModel = new HDBParameters(null, "/DuplicateTableProperties.hdbtable", content, null);
        assertThrows(HDBTableDuplicatePropertyException.class, () -> new HDBTableDataStructureParser().parse(parametersModel));
    }

    /**
     * Fail if parsing missing mandatory properties.
     *
     * @throws Exception the exception
     */
    @Test
    public void failIfParsingMissingMandatoryProperties() throws Exception {

        HDBTableMissingPropertyException exception = Assertions.assertThrows(HDBTableMissingPropertyException.class, () -> {
            InputStream in = HDBTableDataStructureParserTest.class.getResourceAsStream("/MissingMandatoryTableProperties.hdbtable");
            String content = IOUtils.toString(in, StandardCharsets.UTF_8);
            HDBParameters parametersModel = new HDBParameters(null, "/MissingMandatoryTableProperties.hdbtable", content, null);
            new HDBTableDataStructureParser().parse(parametersModel);
        });
        Assertions.assertEquals(
                "Wrong format of table definition: [/MissingMandatoryTableProperties.hdbtable]. [Missing mandatory field columns!]",
                exception.getMessage());
    }

    /**
     * Fail if parsing wrong PK definition.
     */
    @Test
    public void failIfParsingWrongPKDefinition() {
        String content = "table.schemaName = \"TEAMS\";\n" + "table.tableType = COLUMNSTORE;\n" + "table.columns = [\n"
                + "\t{ name = \"MATCH_ID\";\tsqlType = NVARCHAR;},\n" + "\t{ name = \"TEAM_ID\";\t\tsqlType = NVARCHAR;}\n" + "];\n"
                + "table.primaryKey.pkcolumns = [\"MATCH_ID_WRONG\", \"TEAM_ID\"];";

        HDBParameters parametersModel = new HDBParameters(null, "/someFileName.hdbtable", content, null);
        assertThrows(IllegalStateException.class, () -> new HDBTableDataStructureParser().parse(parametersModel));
    }

    /**
     * Fail if parsing wrong index definition.
     */
    @Test
    public void failIfParsingWrongIndexDefinition() {
        String content = "table.schemaName = \"TEAMS\";\n" + "table.tableType = COLUMNSTORE;\n" + "table.columns = [\n"
                + "\t{ name = \"MATCH_ID\";\tsqlType = NVARCHAR;},\n" + "\t{ name = \"TEAM_ID\";\t\tsqlType = NVARCHAR;}\n" + "];\n"
                + "table.primaryKey.pkcolumns = [\"MATCH_ID\", \"TEAM_ID\"];"
                + "table.indexes = [ {name = \"INDEX1\"; unique = true; indexColumns = [\"MATCH_ID_WRONG\"];}];";

        HDBParameters parametersModel = new HDBParameters(null, "/someFileName.hdbtable", content, null);
        assertThrows(IllegalStateException.class, () -> new HDBTableDataStructureParser().parse(parametersModel));
    }

    /**
     * Parses the hana XS advanced content with additional spaces.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseHanaXSAdvancedContentWithAdditionalSpaces() throws Exception {
        String content = " COLUMN TABLE      KRONOS_HDI_SIMPLE_TABLE COLUMN1 INTEGER )";
        HDBTable model = HDBDataStructureModelFactory.parseTable("/HdbtableHanaXSAdvancedContent.hdbtable", content);
        assertEquals(false, model.isClassic());
        assertEquals(content, model.getContent());
    }

    /**
     * Parses the hana XS advanced content with new lines.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseHanaXSAdvancedContentWithNewLines() throws Exception {
        String content = "COLUMN TABLE \r\n KRONOS_HDI_SIMPLE_TABLE (COLUMN1 INTEGER)";
        HDBTable model = HDBDataStructureModelFactory.parseTable("/testFileName.hdbtable", content);
        assertEquals(false, model.isClassic());
        assertEquals(content, model.getContent());
    }

    /**
     * Parses the hana XS advanced content with lower case.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseHanaXSAdvancedContentWithLowerCase() throws Exception {
        String content = "column table KRONOS_HDI_SIMPLE_TABLE ( COLUMN1 INTEGER )";
        HDBTable model = HDBDataStructureModelFactory.parseTable("/testFileName.hdbtable", content);
        assertEquals(false, model.isClassic());
        assertEquals(content, model.getContent());
    }

    /**
     * Parses the table without PK.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseTableWithoutPK() throws Exception {
        String content = "table.schemaName  = \"SAP_DEMO\";\n" + "table.tableType   = COLUMNSTORE;\n" + "table.temporary   = true;\n"
                + "table.description = \"nvarchar(4000)\";\n" + "table.columns = [\n"
                + "    {name = \"TEXT\"; sqlType = NVARCHAR; length = 4000; nullable = true; comment = \"nvarchar(4000)\";}\n" + "];";
        HDBTable model = HDBDataStructureModelFactory.parseTable("/test.hdbtable", content);
        assertEquals(true, model.isClassic());
        assertEquals(content, model.getContent());
    }

    /**
     * Parses the hana XS classic content with lexer error fail.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseHanaXSClassicContentWithLexerErrorFail() throws Exception {
        String content = "table.schemaName = \"TEAMS\";\n" + "table.tableType = COLUMNSTORE;\n" + "table.columns = [dd\n"
                + "\t{ name = \"MATCH_ID\";sqlType = NVARCHAR;\tlength = 32;nullable = false;}\n" + "];";
        assertThrows(ArtifactParserException.class, () -> HDBDataStructureModelFactory.parseTable("db/test.hdbtable", content));
    }

    /**
     * Parses the hana XS classic content with syntax error fail.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseHanaXSClassicContentWithSyntaxErrorFail() throws Exception {
        String content = "table.schemaName = \"TEAMS;\n" + "table.tableType = COLUMNSTORE;";
        assertThrows(ArtifactParserException.class, () -> HDBDataStructureModelFactory.parseTable("db/test.hdbtable", content));
    }

    /**
     * Parses the row table with indexes.
     *
     * @throws Exception the exception
     */
    @Test
    public void parseRowTableWithIndexes() throws Exception {
        InputStream in = HDBTableDataStructureParserTest.class.getResourceAsStream("/ParsingTableWithUniqueAndNoUniqueIndexes.hdbtable");
        String contents = IOUtils.toString(in, StandardCharsets.UTF_8);
        HDBTable model = HDBDataStructureModelFactory.parseTable("/ParsingTableWithUniqueAndNoUniqueIndexes.hdbtable", contents);

        assertEquals(3, model.getColumns()
                             .size());
        assertEquals("ParsingTableWithUniqueAndNoUniqueIndexes", model.getName());
        assertEquals("DBADMIN", model.getSchema());
        assertEquals("ROWSTORE", model.getTableType());
        assertEquals("/ParsingTableWithUniqueAndNoUniqueIndexes.hdbtable", model.getLocation());
        assertEquals("HDBTABLE", model.getType());
        assertNotNull(model.getCreatedBy());
        assertNotNull(model.getCreatedAt());
        assertEquals(true, model.isClassic());
        assertEquals(contents, model.getContent());

        assertEquals(1, model.getConstraints()
                             .getUniqueIndexes()
                             .size());
        assertEquals("l2", model.getConstraints()
                                .getUniqueIndexes()
                                .get(0)
                                .getName());
        assertEquals(1, model.getConstraints()
                             .getUniqueIndexes()
                             .get(0)
                             .getColumns().length);
        assertEquals("Col1", model.getConstraints()
                                  .getUniqueIndexes()
                                  .get(0)
                                  .getColumns()[0]);
        assertEquals("ASC", model.getConstraints()
                                 .getUniqueIndexes()
                                 .get(0)
                                 .getOrder());
        assertEquals("BTREE", model.getConstraints()
                                   .getUniqueIndexes()
                                   .get(0)
                                   .getIndexType());
        assertEquals(1, model.getIndexes()
                             .size());
        assertEquals("l1", model.getIndexes()
                                .get(0)
                                .getName());
        assertEquals("DESC", model.getIndexes()
                                  .get(0)
                                  .getOrder());
        assertEquals("CPBTREE", model.getIndexes()
                                     .get(0)
                                     .getType());
        assertEquals(2, model.getIndexes()
                             .get(0)
                             .getColumns().length);
    }

    /**
     * Test hdb table with empty indexes is parsed correctly.
     *
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws DataStructuresException the data structures exception
     * @throws ArtifactParserException the artifact parser exception
     */
    @Test
    public void testHdbTableWithEmptyIndexesIsParsedCorrectly() throws IOException, DataStructuresException, ArtifactParserException {
        String hdbTablePath = "/EmptyIndexesTable.hdbtable";
        assertEmptyOrNoIndexesTable(hdbTablePath);
    }

    /**
     * Test hdb table with no indexes is parsed correctly.
     *
     * @throws DataStructuresException the data structures exception
     * @throws ArtifactParserException the artifact parser exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Test
    public void testHdbTableWithNoIndexesIsParsedCorrectly() throws DataStructuresException, ArtifactParserException, IOException {
        String hdbTablePath = "/NoIndexesTable.hdbtable";
        assertEmptyOrNoIndexesTable(hdbTablePath);
    }

    /**
     * Assert empty or no indexes table.
     *
     * @param hdbTablePath the hdb table path
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws DataStructuresException the data structures exception
     * @throws ArtifactParserException the artifact parser exception
     */
    private static void assertEmptyOrNoIndexesTable(String hdbTablePath)
            throws IOException, DataStructuresException, ArtifactParserException {
        InputStream in = HDBTableDataStructureParserTest.class.getResourceAsStream(hdbTablePath);
        String contents = IOUtils.toString(in, StandardCharsets.UTF_8);

        HDBTable model = HDBDataStructureModelFactory.parseTable(hdbTablePath, contents);

        assertEquals("TEST", model.getSchema(), "Unexpected table schema");
        assertNotNull(model.getIndexes(), "Unexpected table indexes is null");
        assertEquals(0, model.getIndexes()
                             .size(),
                "Unexpected table indexes count");
    }

}
