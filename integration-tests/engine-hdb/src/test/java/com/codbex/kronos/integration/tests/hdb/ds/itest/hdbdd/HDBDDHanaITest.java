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
package com.codbex.kronos.integration.tests.hdb.ds.itest.hdbdd;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.scheduler.api.SynchronizationException;
import org.eclipse.dirigible.database.ds.model.IDataStructureModel;
import org.eclipse.dirigible.repository.local.LocalResource;
import org.junit.Before;
import org.junit.Test;

import com.codbex.kronos.integration.tests.hdb.ds.AbstractHDBITest;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.integration.tests.core.hdb.module.HDBTestModule;
import com.codbex.kronos.integration.tests.core.hdb.utils.HanaITestUtils;

public class HDBDDHanaITest extends AbstractHDBITest {

  @Before
  public void setUpBeforeTest() throws SQLException {
    HanaITestUtils.clearDataFromDataStructure(systemDatasource, Arrays.asList(
        "'/hdbdd-itest/ProductsWithManagedAssItest.hdbdd'",
        "'/hdbdd-itest/Status.hdbdd'",
        "'/hdbdd-itest/ProductsWithManagedAssWithUsingItest.hdbdd'",
        "'/hdbdd-itest/DefaultValueWithDateTimeFunction.hdbdd'",
        "'/hdbdd-itest/CatalogTableTypes.hdbdd'",
        "'/hdbdd-itest/EmployeesWithViewDefinitions.hdbdd'",
        "'/hdbdd-itest/CalculatedColumns.hdbdd'"
    ));
    Configuration.set(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "true");
    facade.clearCache();
  }

  @Test
  public void testHDBDDWithManagedAssOnDiffSchema() throws Exception {
    try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {
      try {
        HanaITestUtils.createSchema(stmt, TEST_SCHEMA);

        LocalResource resource = HDBTestModule.getResources( //
            "/usr/local/target/dirigible/repository/root", //
            "/registry/public/hdbdd-itest/ProductsWithManagedAssItest.hdbdd", //
            "/registry/public/hdbdd-itest/ProductsWithManagedAssItest.hdbdd" //
        );

        facade.handleResourceSynchronization(resource);
        facade.updateEntities();

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTable(connection, "hdbdd-itest::ProductsWithManagedAssItest.Orders", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::ProductsWithManagedAssItest.Orders"));

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTable(connection, "hdbdd-itest::ProductsWithManagedAssItest.Country", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::ProductsWithManagedAssItest.Country"));

      } finally {
        HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
      }
    }
  }

  @Test
  public void testHDBDDWithManagedAssWithUsingOnDiffSchema()
      throws DataStructuresException, SynchronizationException, IOException, SQLException {
    try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {
      String schemaName2 = "TEST_SCHEMA 2";
      try {
        HanaITestUtils.createSchema(stmt, TEST_SCHEMA);
        HanaITestUtils.createSchema(stmt, schemaName2);

        LocalResource resource = HDBTestModule.getResources( //
            "/usr/local/target/dirigible/repository/root", //
            "/registry/public/hdbdd-itest/ProductsWithManagedAssWithUsingItest.hdbdd", //
            "/registry/public/hdbdd-itest/ProductsWithManagedAssWithUsingItest.hdbdd" //
        );

        LocalResource resource2 = HDBTestModule.getResources( //
            "/usr/local/target/dirigible/repository/root", //
            "/registry/public/hdbdd-itest/Status.hdbdd", //
            "/registry/public/hdbdd-itest/Status.hdbdd" //
        );

        facade.handleResourceSynchronization(resource);
        facade.handleResourceSynchronization(resource2);
        facade.updateEntities();

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTable(connection, "hdbdd-itest::ProductsWithManagedAssWithUsingItest.Orders", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::ProductsWithManagedAssWithUsingItest.Orders"));

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTable(connection, "hdbdd-itest::ProductsWithManagedAssWithUsingItest.Country", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::ProductsWithManagedAssWithUsingItest.Country"));

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTable(connection, "hdbdd-itest::Status.StatusEntity", schemaName2));
        assertTrue("Expected synonym was not found!", HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::Status.StatusEntity"));

      } finally {
        HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
        HanaITestUtils.dropSchema(stmt, schemaName2);
      }
    }
  }

  @Test
  public void testHDBDDWithDateTimeFunctionDefaultValue()
      throws DataStructuresException, SynchronizationException, IOException, SQLException {
    try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

      try {
        HanaITestUtils.createSchema(stmt, TEST_SCHEMA);

        LocalResource resource = HDBTestModule.getResources( //
            "/usr/local/target/dirigible/repository/root", //
            "/registry/public/hdbdd-itest/DefaultValueWithDateTimeFunction.hdbdd", //
            "/registry/public/hdbdd-itest/DefaultValueWithDateTimeFunction.hdbdd" //
        );

        facade.handleResourceSynchronization(resource);
        facade.updateEntities();

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTable(connection, "hdbdd-itest::DefaultValueWithDateTimeFunction.Orders", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::DefaultValueWithDateTimeFunction.Orders"));

      } finally {
        HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
      }
    }
  }

  @Test
  public void testHDBDDWithCatalogTableTypeAnnotation()
      throws DataStructuresException, SynchronizationException, IOException, SQLException {
    try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

      try {
        HanaITestUtils.createSchema(stmt, TEST_SCHEMA);

        LocalResource resource = HDBTestModule.getResources( //
            "/usr/local/target/dirigible/repository/root", //
            "/registry/public/hdbdd-itest/CatalogTableTypes.hdbdd", //
            "/registry/public/hdbdd-itest/CatalogTableTypes.hdbdd" //
        );

        facade.handleResourceSynchronization(resource);
        facade.updateEntities();

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTable(connection, "hdbdd-itest::CatalogTableTypes.CONFIGURATION1", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::CatalogTableTypes.CONFIGURATION1"));

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTable(connection, "hdbdd-itest::CatalogTableTypes.CONFIGURATION2", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::CatalogTableTypes.CONFIGURATION2"));

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTable(connection, "hdbdd-itest::CatalogTableTypes.CONFIGURATION3", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::CatalogTableTypes.CONFIGURATION3"));

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTable(connection, "hdbdd-itest::CatalogTableTypes.CONFIGURATION4", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::CatalogTableTypes.CONFIGURATION4"));

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTableGlobalTemporary(connection, "hdbdd-itest::CatalogTableTypes.CONFIGURATION5", TEST_SCHEMA));

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTableGlobalTemporary(connection, "hdbdd-itest::CatalogTableTypes.CONFIGURATION6", TEST_SCHEMA));

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTableGlobalTemporary(connection, "hdbdd-itest::CatalogTableTypes.CONFIGURATION7", TEST_SCHEMA));

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTableGlobalTemporary(connection, "hdbdd-itest::CatalogTableTypes.CONFIGURATION8", TEST_SCHEMA));
      } finally {
        HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
      }
    }
  }

  @Test
  public void testHDBDDWithViewDefinitions()
      throws DataStructuresException, SynchronizationException, IOException, SQLException {
    try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

      try {
        HanaITestUtils.createSchema(stmt, TEST_SCHEMA);

        LocalResource resource = HDBTestModule.getResources( //
            "/usr/local/target/dirigible/repository/root", //
            "/registry/public/hdbdd-itest/EmployeesWithViewDefinitions.hdbdd", //
            "/registry/public/hdbdd-itest/EmployeesWithViewDefinitions.hdbdd" //
        );

        facade.handleResourceSynchronization(resource);
        facade.updateEntities();

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTable(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employees", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employees"));

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTable(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employee_roles", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employee_roles"));

        assertTrue("Expected table was not found!",
            HanaITestUtils.checkExistOfTable(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employee_salaries", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employee_salaries"));

        assertTrue("Expected view was not found!",
            HanaITestUtils.checkExistOfView(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employees_view_basic", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employees_view_basic"));

        assertTrue("Expected view was not found!",
            HanaITestUtils.checkExistOfView(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employees_view_with_join", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employees_view_with_join"));

        assertTrue("Expected view was not found!",
            HanaITestUtils.checkExistOfView(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employees_view_with_where", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employees_view_with_where"));

        assertTrue("Expected view was not found!",
            HanaITestUtils.checkExistOfView(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employees_view_with_union", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::EmployeesWithViewDefinitions.employees_view_with_union"));

        assertTrue("Expected view was not found!",
            HanaITestUtils.checkExistOfView(connection, "hdbdd-itest::EmployeesWithViewDefinitions.embedded_context.embedded_view", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::EmployeesWithViewDefinitions.embedded_context.embedded_view"));

        assertTrue("Expected view was not found!",
            HanaITestUtils.checkExistOfView(connection, "hdbdd-itest::EmployeesWithViewDefinitions.outer_view", TEST_SCHEMA));
        assertTrue("Expected synonym was not found!",
            HanaITestUtils.checkExistOfPublicSynonym(connection, "hdbdd-itest::EmployeesWithViewDefinitions.outer_view"));

      } finally {
        HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
      }
    }
  }

  @Test
  public void testHDBDDWithCalculatedColumns()
      throws DataStructuresException, SynchronizationException, IOException, SQLException {
    try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {
      try {
        HanaITestUtils.createSchema(stmt, TEST_SCHEMA);

        LocalResource resource = HDBTestModule.getResources( //
            "/usr/local/target/dirigible/repository/root", //
            "/registry/public/hdbdd-itest/CalculatedColumns.hdbdd", //
            "/registry/public/hdbdd-itest/CalculatedColumns.hdbdd" //
        );

        facade.handleResourceSynchronization(resource);
        facade.updateEntities();

        String tableName = "hdbdd-itest::CalculatedColumns.Employee";
        String columnUpperCaseName = "UserID_UPPER";
        String columnFullName = "fullName";

        assertTrue("Expected calculated column not found",
            HanaITestUtils.checkCalculatedColumns(connection, tableName, TEST_SCHEMA, columnUpperCaseName));
        assertTrue("Expected calculated column not found",
            HanaITestUtils.checkCalculatedColumns(connection, tableName, TEST_SCHEMA, columnFullName));

      } finally {
        HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
      }
    }
  }
}