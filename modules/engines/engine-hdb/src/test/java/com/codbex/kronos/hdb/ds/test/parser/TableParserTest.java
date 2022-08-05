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
package com.codbex.kronos.hdb.ds.test.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureModelFactory;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableColumnModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.parser.hdbtable.HDBTableParser;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableDuplicatePropertyException;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableMissingPropertyException;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.core.test.AbstractDirigibleTest;
import org.junit.Ignore;
import org.junit.Test;

public class TableParserTest extends AbstractDirigibleTest {

    @Test
    public void parseTable() throws Exception {
        InputStream in = TableParserTest.class.getResourceAsStream("/Sports.hdbtable");
        String contents = IOUtils.toString(in, StandardCharsets.UTF_8);
        DataStructureHDBTableModel model = DataStructureModelFactory.parseTable("/Sports.hdbtable", contents);

        assertEquals(6, model.getColumns().size());
        assertEquals("Sports", model.getName());
        assertEquals("SPORTS", model.getSchema());
        assertEquals("COLUMNSTORE", model.getTableType());
        assertEquals("Team players", model.getDescription());
        assertEquals("/Sports.hdbtable", model.getLocation());
        assertEquals("HDBTABLE", model.getType());
        assertNotNull(model.getCreatedBy());
        assertNotNull(model.getCreatedAt());
        assertEquals(DBContentType.XS_CLASSIC, model.getDBContentType());
        assertEquals(contents, model.getRawContent());

        DataStructureHDBTableColumnModel matchId = model.getColumns().get(0);
        assertEquals("MATCH_ID", matchId.getName());
        assertEquals("NVARCHAR", matchId.getType());
        assertEquals("32", matchId.getLength());
        assertFalse(matchId.isNullable());
        assertTrue(matchId.isPrimaryKey());
        assertEquals("D1", matchId.getDefaultValue());
        assertEquals("test", matchId.getComment());
        assertTrue(matchId.isUnique());

        assertTrue(model.getColumns().get(1).isPrimaryKey());
        assertTrue(model.getColumns().get(2).isPrimaryKey());

        DataStructureHDBTableColumnModel personRate = model.getColumns().get(3);
        assertEquals("PERSON_RATE", personRate.getName());
        assertEquals("DECIMAL", personRate.getType());
        assertFalse(personRate.isNullable());
        assertFalse(personRate.isPrimaryKey());
        assertNull(personRate.getDefaultValue());
        assertEquals("20", personRate.getPrecision());
        assertEquals("3", personRate.getScale());
        assertFalse(personRate.isNullable());
        assertNull(personRate.getComment());

        DataStructureHDBTableColumnModel changedBy = model.getColumns().get(4);
        assertEquals("CHANGED_BY", changedBy.getName());
        assertEquals("NVARCHAR", changedBy.getType());
        assertEquals("256", changedBy.getLength());
        assertTrue(changedBy.isNullable());
        assertFalse(changedBy.isPrimaryKey());
        assertFalse(changedBy.isUnique());

        DataStructureHDBTableColumnModel changedAt = model.getColumns().get(5);
        assertEquals("CHANGED_AT", changedAt.getName());
        assertEquals("TIMESTAMP", changedAt.getType());
        assertTrue(changedAt.isNullable());
        assertFalse(changedAt.isPrimaryKey());
        assertFalse(changedAt.isUnique());

        assertEquals("PK_Sports", model.getConstraints().getPrimaryKey().getName());
        assertEquals(3, model.getConstraints().getPrimaryKey().getColumns().length);
        assertNull(model.getConstraints().getPrimaryKey().getModifiers());
        assertEquals(0, model.getConstraints().getForeignKeys().size());
        assertEquals(1, model.getConstraints().getUniqueIndices().size());
        assertEquals("INDEX1", model.getConstraints().getUniqueIndices().get(0).getIndexName());
        assertEquals(1, model.getConstraints().getUniqueIndices().get(0).getColumns().length);
        assertEquals("MATCH_ID", model.getConstraints().getUniqueIndices().get(0).getColumns()[0]);
        assertEquals(0, model.getConstraints().getChecks().size());
    }

    @Test
    public void failIfParsingRepetitiveProperties() throws Exception {
      InputStream in = TableParserTest.class.getResourceAsStream("/DuplicateTableProperties.hdbtable");
      String content = IOUtils.toString(in, StandardCharsets.UTF_8);
      DataStructureParametersModel parametersModel =
          new DataStructureParametersModel(null, "/DuplicateTableProperties.hdbtable", content, null);
      assertThrows(HDBTableDuplicatePropertyException.class, () -> new HDBTableParser().parse(parametersModel));
    }

    @Ignore
    @Test(expected = HDBTableMissingPropertyException.class)
    public void failIfParsingMissingMandatoryProperties() throws Exception {
      InputStream in = TableParserTest.class.getResourceAsStream("/MissingMandatoryTableProperties.hdbtable");
      String content = IOUtils.toString(in, StandardCharsets.UTF_8);
      DataStructureParametersModel parametersModel =
          new DataStructureParametersModel(null, "/MissingMandatoryTableProperties.hdbtable", content, null);
      new HDBTableParser().parse(parametersModel);
    }

    @Ignore
    @Test
    public void failIfParsingWrongPKDefinition() {
      String content = "table.schemaName = \"SPORTS\";\n" +
              "table.tableType = COLUMNSTORE;\n" +
              "table.columns = [\n" +
              "\t{ name = \"MATCH_ID\";\tsqlType = NVARCHAR;},\n" +
              "\t{ name = \"TEAM_ID\";\t\tsqlType = NVARCHAR;}\n" +
              "];\n" +
              "table.primaryKey.pkcolumns = [\"MATCH_ID_WRONG\", \"TEAM_ID\"];";

      DataStructureParametersModel parametersModel =
          new DataStructureParametersModel(null, "/someFileName.hdbtable", content, null);
      assertThrows(IllegalStateException.class, () -> new HDBTableParser().parse(parametersModel));
    }

    @Ignore
    @Test
    public void failIfParsingWrongIndexDefinition() {
      String content = "table.schemaName = \"SPORTS\";\n" +
              "table.tableType = COLUMNSTORE;\n" +
              "table.columns = [\n" +
              "\t{ name = \"MATCH_ID\";\tsqlType = NVARCHAR;},\n" +
              "\t{ name = \"TEAM_ID\";\t\tsqlType = NVARCHAR;}\n" +
              "];\n" +
              "table.primaryKey.pkcolumns = [\"MATCH_ID\", \"TEAM_ID\"];" +
              "table.indexes = [ {name = \"INDEX1\"; unique = true; indexColumns = [\"MATCH_ID_WRONG\"];}];";

      DataStructureParametersModel parametersModel =
          new DataStructureParametersModel(null, "/someFileName.hdbtable", content, null);
      assertThrows(IllegalStateException.class, () -> new HDBTableParser().parse(parametersModel));
    }

    @Test
    public void parseHanaXSAdvancedContentWithAdditionalSpaces() throws Exception {
        String content = " COLUMN TABLE      KRONOS_HDI_SIMPLE_TABLE COLUMN1 INTEGER )";
        DataStructureHDBTableModel model = DataStructureModelFactory.parseTable("/HdbtableHanaXSAdvancedContent.hdbtable", content);
        assertEquals(DBContentType.OTHERS, model.getDBContentType());
        assertEquals(content, model.getRawContent());
    }

    @Test
    public void parseHanaXSAdvancedContentWithNewLines() throws Exception {
        String content = "COLUMN TABLE \r\n KRONOS_HDI_SIMPLE_TABLE (COLUMN1 INTEGER)";
        DataStructureHDBTableModel model = DataStructureModelFactory.parseTable("/testFileName.hdbtable", content);
        assertEquals(DBContentType.OTHERS, model.getDBContentType());
        assertEquals(content, model.getRawContent());
    }

    @Test
    public void parseHanaXSAdvancedContentWithLowerCase() throws Exception {
		String content = "column table KRONOS_HDI_SIMPLE_TABLE ( COLUMN1 INTEGER )";
        DataStructureHDBTableModel model = DataStructureModelFactory.parseTable("/testFileName.hdbtable", content);
        assertEquals(DBContentType.OTHERS, model.getDBContentType());
        assertEquals(content, model.getRawContent());
    }

    @Test
    public  void parseTableWithoutPK() throws Exception {
        String content = "table.schemaName  = \"SAP_DEMO\";\n" +
                "table.tableType   = COLUMNSTORE;\n" +
                "table.temporary   = true;\n" +
                "table.description = \"nvarchar(4000)\";\n" +
                "table.columns = [\n" +
                "    {name = \"TEXT\"; sqlType = NVARCHAR; length = 4000; nullable = true; comment = \"nvarchar(4000)\";}\n" +
                "];";
        DataStructureHDBTableModel model = DataStructureModelFactory.parseTable("/test.hdbtable", content);
        assertEquals(DBContentType.XS_CLASSIC, model.getDBContentType());
        assertEquals(content, model.getRawContent());
    }

    @Test(expected = ArtifactParserException.class)
    public void parseHanaXSClassicContentWithLexerErrorFail() throws Exception {
        String content = "table.schemaName = \"SPORTS\";\n" +
                "table.tableType = COLUMNSTORE;\n" +
                "table.columns = [dd\n" +
                "\t{ name = \"MATCH_ID\";sqlType = NVARCHAR;\tlength = 32;nullable = false;}\n" +
                "];";
        DataStructureModelFactory.parseView("db/test.hdbtable", content);
    }

    @Test(expected = ArtifactParserException.class)
    public void parseHanaXSClassicContentWithSyntaxErrorFail() throws Exception {
        String content = "table.schemaName = \"SPORTS;\n" +
                "table.tableType = COLUMNSTORE;";
        DataStructureModelFactory.parseView("db/test.hdbtable", content);
    }

    @Test
    public void parseRowTableWithIndexes() throws Exception {
      InputStream in = TableParserTest.class.getResourceAsStream("/ParsingTableWithUniqueAndNoUniqueIndexes.hdbtable");
      String contents = IOUtils.toString(in, StandardCharsets.UTF_8);
      DataStructureHDBTableModel model = DataStructureModelFactory.parseTable("/ParsingTableWithUniqueAndNoUniqueIndexes.hdbtable", contents);

      assertEquals(3, model.getColumns().size());
      assertEquals("ParsingTableWithUniqueAndNoUniqueIndexes", model.getName());
      assertEquals("DBADMIN", model.getSchema());
      assertEquals("ROWSTORE", model.getTableType());
      assertEquals("/ParsingTableWithUniqueAndNoUniqueIndexes.hdbtable", model.getLocation());
      assertEquals("HDBTABLE", model.getType());
      assertNotNull(model.getCreatedBy());
      assertNotNull(model.getCreatedAt());
      assertEquals(DBContentType.XS_CLASSIC, model.getDBContentType());
      assertEquals(contents, model.getRawContent());

      assertEquals(1, model.getConstraints().getUniqueIndices().size());
      assertEquals("l2", model.getConstraints().getUniqueIndices().get(0).getIndexName());
      assertEquals(1, model.getConstraints().getUniqueIndices().get(0).getColumns().length);
      assertEquals("Col1", model.getConstraints().getUniqueIndices().get(0).getColumns()[0]);
      assertEquals("ASC", model.getConstraints().getUniqueIndices().get(0).getOrder());
      assertEquals("BTREE", model.getConstraints().getUniqueIndices().get(0).getIndexType());
      assertEquals(1,model.getIndexes().size());
      assertEquals("l1", model.getIndexes().get(0).getIndexName());
      assertEquals("DESC", model.getIndexes().get(0).getOrder());
      assertEquals("CPBTREE", model.getIndexes().get(0).getIndexType());
      assertEquals(2, model.getIndexes().get(0).getIndexColumns().size());
    }

    @Test
    public void testHdbTableWithEmptyIndexesIsParsedCorrectly() throws IOException, DataStructuresException, ArtifactParserException {
      String hdbTablePath = "/EmptyIndexesTable.hdbtable";
      assertEmptyOrNoIndexesTable(hdbTablePath);
    }

    @Test
    public void testHdbTableWithNoIndexesIsParsedCorrectly() throws DataStructuresException, ArtifactParserException, IOException {
      String hdbTablePath = "/NoIndexesTable.hdbtable";
      assertEmptyOrNoIndexesTable(hdbTablePath);
    }

    private static void assertEmptyOrNoIndexesTable(String hdbTablePath) throws IOException, DataStructuresException, ArtifactParserException {
      InputStream in = TableParserTest.class.getResourceAsStream(hdbTablePath);
      String contents = IOUtils.toString(in, StandardCharsets.UTF_8);

      DataStructureHDBTableModel model = DataStructureModelFactory.parseTable(hdbTablePath, contents);

      assertEquals("Unexpected table schema", "TEST", model.getSchema());
      assertNotNull("Unexpected table indexes is null", model.getIndexes());
      assertEquals("Unexpected table indexes count", 0, model.getIndexes().size());
    }
}
