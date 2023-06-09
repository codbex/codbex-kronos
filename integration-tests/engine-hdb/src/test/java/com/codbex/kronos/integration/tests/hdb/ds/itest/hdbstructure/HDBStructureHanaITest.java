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
package com.codbex.kronos.integration.tests.hdb.ds.itest.hdbstructure;

import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.database.ds.model.IDataStructureModel;
import org.eclipse.dirigible.repository.local.LocalResource;
import org.junit.Before;
import org.junit.Test;

import com.codbex.kronos.integration.tests.hdb.ds.AbstractHDBITest;
import com.codbex.kronos.integration.tests.core.hdb.module.HDBTestModule;
import com.codbex.kronos.integration.tests.core.hdb.utils.HanaITestUtils;

public class HDBStructureHanaITest extends AbstractHDBITest {

  @Before
  public void setUpBeforeTest() throws SQLException {
    HanaITestUtils.clearDataFromDataStructure(systemDatasource, Arrays.asList( //
        "'/hdbstructure-itest/str1.hdbstructure'", //
        "'/hdbstructure-itest/str2.hdbstructure'" //
    ));
    Configuration.set(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "true");
    facade.clearCache();
  }

  @Test
  public void testHDBStructureCreateOnSameSchema() throws Exception {
    try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

      String userSchema = Configuration.get("hana.username");

      String fileContent = String.format("table.schemaName = \"%s\";\n"
          + "table.description = \"Business event table type\";\n" + "table.temporary   = true;\n"
          + "table.columns = [\n"
          + "    {name = \"ID\";        sqlType = INTEGER;              comment = \"Object identifier\";},\n"
          + "    {name = \"BIZ_EVENT\"; sqlType = VARCHAR; length = 60; comment = \"Object type code\";}\n"
          + "];", userSchema);

      LocalResource resource = HDBTestModule.getResourceFromString( //
          "/usr/local/target/dirigible/repository/root", //
          "/registry/public/hdbstructure-itest/str1.hdbstructure", //
          fileContent
      );

      try {
        facade.handleResourceSynchronization(resource);
        facade.updateEntities();
        assertTrue(HanaITestUtils.checkExistOfTableType(stmt, "hdbstructure-itest::str1", userSchema));
        assertTrue(HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbstructure-itest::str1"));
      } finally {
        HanaITestUtils.dropTableType(connection, stmt, "hdbstructure-itest::str1", userSchema);
      }
    }
  }

  @Test
  public void testHDBStructureCreateOnDiffSchemas() throws Exception {
    try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

      HanaITestUtils.createSchema(stmt, TEST_SCHEMA);

      LocalResource resource = HDBTestModule.getResources( //
          "/usr/local/target/dirigible/repository/root", //
          "/registry/public/hdbstructure-itest/str2.hdbstructure", //
          "/registry/public/hdbstructure-itest/str2.hdbstructure" //
      );

      try {
        facade.handleResourceSynchronization(resource);
        facade.updateEntities();
        assertTrue(HanaITestUtils.checkExistOfTableType(stmt, "hdbstructure-itest::str2", TEST_SCHEMA));
        assertTrue(HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbstructure-itest::str2"));
      } finally {
        HanaITestUtils.dropTableType(connection, stmt, "hdbstructure-itest::str2", TEST_SCHEMA);
        HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
      }
    }
  }

}