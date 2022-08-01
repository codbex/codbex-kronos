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
package com.codbex.kronos.hdb.ds.itest.hdbprocedure;

import static org.junit.Assert.assertTrue;

import com.codbex.kronos.hdb.ds.itest.AbstractHDBITTest;
import com.codbex.kronos.hdb.ds.itest.module.HDBTestModule;
import com.codbex.kronos.hdb.ds.itest.utils.HanaITestUtils;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.database.ds.model.IDataStructureModel;
import org.eclipse.dirigible.repository.local.LocalResource;
import org.junit.Before;
import org.junit.Test;

public class HDBProcedureParserHanaITTest extends AbstractHDBITTest {

    @Before
    public void setUpBeforeTest() throws SQLException {
        HanaITestUtils.clearDataFromDataStructure(systemDatasource, Arrays.asList( //
            "'/hdbprocedure-itest/SampleHanaProcedure.hdbprocedure'" //
        ));
        Configuration.set(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "true");
        facade.clearCache();
    }

    @Test
    public void testHDBTableFunctionCreate() throws Exception {
        try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

            try {
                HanaITestUtils.createSchema(stmt, TEST_SCHEMA);
                HanaITestUtils.createEmptyTable(stmt, "hdbprocedure-itest::SampleHanaTable", TEST_SCHEMA);

                LocalResource resource = HDBTestModule.getResources( //
                    "/usr/local/target/dirigible/repository/root", //
                    "/registry/public/hdbprocedure-itest/SampleHanaProcedure.hdbprocedure", //
                    "/hdbprocedure-itest/SampleHanaProcedure.hdbprocedure" //
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
    public void testHDBTableFunctionCreateIfAlreadyExist() throws Exception {
        try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

            try {
                HanaITestUtils.createSchema(stmt, TEST_SCHEMA);
                HanaITestUtils.createEmptyTable(stmt, "hdbprocedure-itest::SampleHanaTable", TEST_SCHEMA);

                String hdbprocedureSample = org.apache.commons.io.IOUtils
                    .toString(
                        HDBProcedureParserHanaITTest.class
                            .getResourceAsStream("/hdbprocedure-itest/SampleHanaProcedure.hdbprocedure"),
                        StandardCharsets.UTF_8);
                stmt.executeUpdate("CREATE " + hdbprocedureSample);

                LocalResource resource = HDBTestModule.getResources( //
                    "/usr/local/target/dirigible/repository/root", //
                    "/registry/public/hdbprocedure-itest/SampleHanaProcedure.hdbprocedure", //
                    "/hdbprocedure-itest/SampleHanaProcedure.hdbprocedure" //
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
