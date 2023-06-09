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
package com.codbex.kronos.integration.tests.hdb.ds.itest.hdbsequence;

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

public class HDBSequenceHanaITest extends AbstractHDBITest {

    @Before
    public void setUpBeforeTest() throws SQLException {
        HanaITestUtils.clearDataFromDataStructure(systemDatasource, Arrays.asList( //
            "'/hdbsequence-itest/SampleSequence_HanaXSClassic.hdbsequence'", //
            "'/hdbsequence-itest/SampleSequence_HanaXSClassicDiffSchema.hdbsequence'" //
        ));
        Configuration.set(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "true");
        facade.clearCache();
    }

    @Test
    public void testHDBSequenceCreateSameSchema() throws Exception {
        try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

            String userSchema = Configuration.get("hana.username");
            String artifactName = "hdbsequence-itest::SampleSequence_HanaXSClassic";

            String fileContent = String.format("schema= \"%s\";\n" + "increment_by=1;\n" + "start_with= 10;\n"
                + "maxvalue= 30;\n" + "nomaxvalue=false;\n" + "nominvalue= false;\n" + "minvalue= 10;\n"
                + "cycles=false;\n" + "public=false;", userSchema);
            LocalResource resource = HDBTestModule.getResourceFromString( //
                "/usr/local/target/dirigible/repository/root", //
                "/registry/public/hdbsequence-itest/SampleSequence_HanaXSClassic.hdbsequence", //
                fileContent);
            try {
                facade.handleResourceSynchronization(resource);
                facade.updateEntities();
                assertTrue(HanaITestUtils.checkExistOfSequence(stmt, artifactName, userSchema));
            } finally {
                HanaITestUtils.dropSequence(stmt, artifactName, userSchema);
            }
        }
    }

    @Test
    public void testHDBSequenceCreateDifferentSchema() throws Exception {
        try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

            HanaITestUtils.createSchema(stmt, TEST_SCHEMA);

            String artifactName = "hdbsequence-itest::SampleSequence_HanaXSClassicDiffSchema";

            LocalResource resource = HDBTestModule.getResources("/usr/local/target/dirigible/repository/root", //
                "/registry/public/hdbsequence-itest/SampleSequence_HanaXSClassicDiffSchema.hdbsequence", //
                "/registry/public/hdbsequence-itest/SampleSequence_HanaXSClassicDiffSchema.hdbsequence" //
            );

            try {
                facade.handleResourceSynchronization(resource);
                facade.updateEntities();
                assertTrue(HanaITestUtils.checkExistOfSequence(stmt, artifactName, TEST_SCHEMA));
            } finally {
                HanaITestUtils.dropSequence(stmt, artifactName, TEST_SCHEMA);
                HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
            }
        }
    }
}
