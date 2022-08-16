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
package com.codbex.kronos.integration.tests.hdb.ds.itest.hdbtabletype;

import com.codbex.kronos.integration.tests.core.hdb.module.HDBTestModule;
import com.codbex.kronos.integration.tests.core.hdb.utils.HanaITestUtils;
import com.codbex.kronos.integration.tests.hdb.ds.AbstractHDBITest;
import com.codbex.kronos.utils.Constants;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.database.ds.model.IDataStructureModel;
import org.eclipse.dirigible.repository.local.LocalResource;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

public class HDBTableTypeHanaITest extends AbstractHDBITest {

  @Before
  public void setUpBeforeTest() throws SQLException {
    HanaITestUtils.clearDataFromDataStructure(systemDatasource, Arrays.asList( //
        "'/hdbtabletype-itest/testOnUserSchema.hdbtabletype'", //
        "'/hdbtabletype-itest/testOnDiffSchema.hdbtabletype'" //
    ));
    Configuration.set(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "true");
    facade.clearCache();
  }

  @Test
  public void testHDBTableTypeCreateOnSameSchema() throws Exception {
    try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

      String userSchema = Configuration.get("hana.username");

      LocalResource resource = HDBTestModule.getResources( //
          "/usr/local/target/dirigible/repository/root", //
          "/registry/public/hdbtabletype-itest/testOnUserSchema.hdbtabletype", //
          "/registry/public/hdbtabletype-itest/testOnUserSchema.hdbtabletype" //
      );

      try {
        facade.handleResourceSynchronization(resource);
        facade.updateEntities();
        assertTrue(HanaITestUtils.checkExistOfTableType(stmt, "TEST", userSchema));
        assertTrue(HanaITestUtils.checkExistOfPublicSynonym(connection, "TEST"));
      } finally {
        HanaITestUtils.dropTableType(connection, stmt, "TEST", userSchema);
      }
    }
  }

  @Test
  public void testHDBTableTypeCreateOnDiffSchemas() throws Exception {
    try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

      HanaITestUtils.createSchema(stmt, TEST_SCHEMA);

      LocalResource resource = HDBTestModule.getResources( //
          "/usr/local/target/dirigible/repository/root", //
          "/registry/public/hdbtabletype-itest/testOnDiffSchema.hdbtabletype", //
          "/registry/public/hdbtabletype-itest/testOnDiffSchema.hdbtabletype" //
      );

      try {
        facade.handleResourceSynchronization(resource);
        facade.updateEntities();
        assertTrue(HanaITestUtils.checkExistOfTableType(stmt, "TEST", TEST_SCHEMA));
        assertTrue(HanaITestUtils.checkExistOfPublicSynonym(connection, "TEST"));
      } finally {
        HanaITestUtils.dropTableType(connection, stmt, "TEST", TEST_SCHEMA);
        HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
      }
    }
  }

  @Test
  public void testHDBTableTypeCreateOnDiffSchemasWithExistingSynonym() throws Exception {
    try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

      HanaITestUtils.createSchema(stmt, TEST_SCHEMA);

      stmt.executeUpdate("CREATE TYPE \"TEST_SCHEMA\".\"TEST\" AS TABLE (\n"
          + "  \"CATEGORY_ID\" INTEGER,\n"
          + "  \"NAME\" VARCHAR (255),\n"
          + "  \"TYPES\" VARCHAR (220) NOT NULL PRIMARY KEY\n"
          + ")");

      stmt.executeUpdate(String.format(
          "CREATE SYNONYM \"%s\".\"TEST\" FOR \"%s\".\"TEST\"",
          Constants.SYNONYM_PUBLIC_SCHEMA, TEST_SCHEMA));

      stmt.executeUpdate(String.format("DROP TYPE \"%s\".\"TEST\"", TEST_SCHEMA));

      LocalResource resource = HDBTestModule.getResources( //
          "/usr/local/target/dirigible/repository/root", //
          "/registry/public/hdbtabletype-itest/testOnDiffSchema.hdbtabletype", //
          "/registry/public/hdbtabletype-itest/testOnDiffSchema.hdbtabletype" //
      );

      try {
        facade.handleResourceSynchronization(resource);
        facade.updateEntities();
        assertTrue(HanaITestUtils.checkExistOfTableType(stmt, "TEST", TEST_SCHEMA));
        assertTrue(HanaITestUtils.checkExistOfPublicSynonym(connection, "TEST"));
      } finally {
        HanaITestUtils.dropTableType(connection, stmt, "TEST", TEST_SCHEMA);
        HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
      }
    }
  }

}