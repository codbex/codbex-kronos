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
package com.codbex.kronos.integration.tests.hdb.ds.itest.hdbscalarfunction;

import static org.junit.Assert.assertTrue;

import com.codbex.kronos.integration.tests.hdb.ds.AbstractHDBITest;
import com.codbex.kronos.integration.tests.core.hdb.module.HDBTestModule;
import com.codbex.kronos.integration.tests.core.hdb.utils.HanaITestUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.database.ds.model.IDataStructureModel;
import org.eclipse.dirigible.repository.local.LocalResource;
import org.junit.Before;
import org.junit.Test;

public class HDBScalarFunctionHanaITest extends AbstractHDBITest {

    @Before
    public void setUpBeforeTest() throws SQLException {
        HanaITestUtils.clearDataFromDataStructure(systemDatasource, Arrays.asList( //
            "'/hdbscalarfunction-itest/SampleHanaScalarFunction.hdbscalarfunction'" //
        ));
        Configuration.set(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "true");
        facade.clearCache();
    }

    @Test
    public void testHDBScalarFunctionCreate() throws Exception {
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = datasource.getConnection();
            stmt = connection.createStatement();

            HanaITestUtils.createSchema(stmt, TEST_SCHEMA);

            LocalResource resource = HDBTestModule.getResources( //
                "/usr/local/target/dirigible/repository/root", //
                "/registry/public/hdbscalarfunction-itest/SampleHanaScalarFunction.hdbscalarfunction", //
                "/registry/public/hdbscalarfunction-itest/SampleHanaScalarFunction.hdbscalarfunction" //
            );

            try {
                facade.handleResourceSynchronization(resource);
                facade.updateEntities();
                assertTrue(HanaITestUtils.checkExistOfFunction(stmt, "hdbscalarfunction-itest::SampleHanaScalarFunction", TEST_SCHEMA));
            } finally {
                HanaITestUtils.dropFunction(stmt, "hdbscalarfunction-itest::SampleHanaScalarFunction", TEST_SCHEMA);
            }
        } finally {
            HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
