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
package com.codbex.kronos.integration.tests.hdb.ds.itest.hdbschema;

import static org.junit.Assert.assertTrue;

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

public class HDBSchemaHanaITest extends AbstractHDBITest {

    @Before
    public void setUpBeforeTest() throws SQLException {
        HanaITestUtils.clearDataFromDataStructure(systemDatasource, Arrays.asList( //
                "'/hdbschema-itest/SampleHANAXSClassicSchema.hdbschema'" //
        ));
        facade.clearCache();
    }

    @Test
    public void testHDBSchemaCreate() throws Exception {
        try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

            String schemaName = "MySchema";

            try {
                LocalResource resource = HDBTestModule.getResources( //
                        "/usr/local/target/dirigible/repository/root", //
                        "/registry/public/hdbschema-itest/SampleHANAXSClassicSchema.hdbschema", //
                        "/registry/public/hdbschema-itest/SampleHANAXSClassicSchema.hdbschema" //
                );
                facade.handleResourceSynchronization(resource);
                facade.updateEntities();
                assertTrue(HanaITestUtils.checkExistOfSchema(connection, schemaName));
            } finally {
                HanaITestUtils.dropSchema(stmt, schemaName);
            }
        }
    }

    @Test
    public void testHDBSchemaCreateIfSchemaAlreadyExist() throws Exception {
        try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

            String schemaName = "MySchema";

            try {
                HanaITestUtils.createSchema(stmt, schemaName);

                LocalResource resource = HDBTestModule.getResources( //
                        "/usr/local/target/dirigible/repository/root", //
                        "/registry/public/hdbschema-itest/SampleHANAXSClassicSchema.hdbschema", //
                        "/registry/public/hdbschema-itest/SampleHANAXSClassicSchema.hdbschema" //
                );

                facade.handleResourceSynchronization(resource);
                facade.updateEntities();
                assertTrue(HanaITestUtils.checkExistOfSchema(connection, schemaName));
            } finally {
                HanaITestUtils.dropSchema(stmt, schemaName);
            }
        }
    }
}
