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
package com.codbex.kronos.integration.tests.hdb.ds.itest.hdbprocedure;

import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.database.persistence.utils.DatabaseMetadataUtil;
import org.eclipse.dirigible.repository.local.LocalResource;
import org.junit.Before;
import org.junit.Test;

import com.codbex.kronos.integration.tests.core.hdb.module.HDBTestModule;
import com.codbex.kronos.integration.tests.core.hdb.utils.HanaITestUtils;
import com.codbex.kronos.integration.tests.hdb.ds.AbstractHDBITest;

public class HDBProcedureHanaITest extends AbstractHDBITest {

    @Before
    public void setUpBeforeTest() throws SQLException {
        HanaITestUtils.clearDataFromDataStructure(systemDatasource, Arrays.asList( //
            "'/hdbprocedure-itest/SampleHanaProcedure.hdbprocedure'" //
        ));
        Configuration.set(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "true");
        facade.clearCache();
    }

    @Test
    public void testHDBProcedureCreate() throws Exception {
        try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

            try {
                HanaITestUtils.createSchema(stmt, TEST_SCHEMA);
                HanaITestUtils.createEmptyTable(stmt, "hdbprocedure-itest::SampleHanaTable", TEST_SCHEMA);

                LocalResource resource = HDBTestModule.getResources( //
                    "/usr/local/target/dirigible/repository/root", //
                    "/registry/public/hdbprocedure-itest/SampleHanaProcedure.hdbprocedure", //
                    "/registry/public/hdbprocedure-itest/SampleHanaProcedure.hdbprocedure" //
                );

                facade.handleResourceSynchronization(resource);
                facade.updateEntities();

                assertTrue(HanaITestUtils.checkExistOfProcedure(connection, "hdbprocedure-itest::SampleHanaProcedure",
                    TEST_SCHEMA));
            } finally {
                HanaITestUtils.dropProcedure(stmt, "hdbprocedure-itest::SampleHanaProcedure", TEST_SCHEMA);
                HanaITestUtils.dropTable(connection, stmt, "hdbprocedure-itest::SampleHanaTable", TEST_SCHEMA);
                HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
            }
        }
    }

    @Test
    public void testHDBProcedureCreateIfAlreadyExist() throws Exception {
        try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

            try {
                HanaITestUtils.createSchema(stmt, TEST_SCHEMA);
                HanaITestUtils.createEmptyTable(stmt, "hdbprocedure-itest::SampleHanaTable", TEST_SCHEMA);

                String hdbprocedureSample = org.apache.commons.io.IOUtils
                    .toString(
                        HDBProcedureHanaITest.class
                            .getResourceAsStream("/registry/public/hdbprocedure-itest/SampleHanaProcedure.hdbprocedure"),
                        StandardCharsets.UTF_8);
                stmt.executeUpdate("CREATE " + hdbprocedureSample);

                LocalResource resource = HDBTestModule.getResources( //
                    "/usr/local/target/dirigible/repository/root", //
                    "/registry/public/hdbprocedure-itest/SampleHanaProcedure.hdbprocedure", //
                    "/registry/public/hdbprocedure-itest/SampleHanaProcedure.hdbprocedure" //
                );

                facade.handleResourceSynchronization(resource);
                facade.updateEntities();

                assertTrue(HanaITestUtils.checkExistOfProcedure(connection, "hdbprocedure-itest::SampleHanaProcedure",
                    TEST_SCHEMA));
            } finally {
                HanaITestUtils.dropProcedure(stmt, "hdbprocedure-itest::SampleHanaProcedure", TEST_SCHEMA);
                HanaITestUtils.dropTable(connection, stmt, "hdbprocedure-itest::SampleHanaTable", TEST_SCHEMA);
                HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
            }
        }
    }

}
