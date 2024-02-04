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
package com.codbex.kronos.engine.hdb.parser.hdbstructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.codbex.kronos.engine.hdb.domain.HDBTableStructure;
import com.codbex.kronos.engine.hdb.domain.HDBTableStructureColumn;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureDataStructureParser;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.parser.HDBParameters;
import com.codbex.kronos.exceptions.ArtifactParserException;

import jakarta.transaction.Transactional;

/**
 * The Class HDBTableStructureDataStructureParserTest.
 */
@SpringBootTest(classes = {HDBDataStructureModelFactory.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBTableStructureDataStructureParserTest {

  /**
	 * Test parse when structure definition is correct test.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void testParseWhenStructureDefinitionIsCorrectTest() throws Exception {
    InputStream in = HDBTableStructureDataStructureParserTest.class.getResourceAsStream("/ParsingHDBStructure.hdbstructure");
    String contents = IOUtils.toString(in, StandardCharsets.UTF_8);
    HDBTableStructure model = HDBDataStructureModelFactory.parseStructure("/ParsingHDBStructure.hdbstructure", contents);

    assertEquals(4, model.getColumns().size());
    assertEquals("ParsingHDBStructure", model.getName());
    assertEquals("DBADMIN", model.getSchema());
    assertEquals("/ParsingHDBStructure.hdbstructure", model.getLocation());
    assertEquals("HDBSTRUCTURE", model.getType());
    assertEquals(false, model.getIsPublic());
    assertNotNull(model.getCreatedBy());
    assertNotNull(model.getCreatedAt());
    assertEquals(true, model.isClassic());
    assertEquals(contents, model.getContent());

    HDBTableStructureColumn scenarioId = model.getColumns().get(0);
    assertEquals("SCENARIO_ID", scenarioId.getName());
    assertEquals("VARCHAR", scenarioId.getType());
    assertEquals("32", scenarioId.getLength());
    assertFalse(scenarioId.isNullable());
    assertTrue(scenarioId.isPrimaryKey());
  }

  /**
	 * Test parse when structure PK is wrong should throw exception.
	 *
	 * @throws DataStructuresException the data structures exception
	 * @throws ArtifactParserException the artifact parser exception
	 * @throws IOException             Signals that an I/O exception has occurred.
	 */
  @Test
  public void testParseWhenStructurePKIsWrongShouldThrowException()
      throws DataStructuresException, ArtifactParserException, IOException {
	  IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class, () -> {
		  HDBDataStructureDataStructureParser parser = new HDBDataStructureDataStructureParser();

		    String content = "table.schemaName = \"DBADMIN\";\n" +
		        "table.columns = [\n" +
		        "\t{ name = \"SCENARIO_ID\";\tsqlType = VARCHAR;},\n" +
		        "\t{ name = \"PERIOD_ID\";\t\tsqlType = DECIMAL;}\n" +
		        "];\n" +
		        "table.primaryKey.pkcolumns = [\"SCENARIO_ID_WRONG\", \"PERIOD_ID\"];";
		    HDBParameters parametersModel =
		        new HDBParameters(null, "/temp.hdbstructure", content, null);
		    parser.parse(parametersModel);
    });
    Assertions.assertEquals("SCENARIO_ID_WRONG: the column does not have a definition but is specified as a primary key", exception.getMessage());
  }

}
