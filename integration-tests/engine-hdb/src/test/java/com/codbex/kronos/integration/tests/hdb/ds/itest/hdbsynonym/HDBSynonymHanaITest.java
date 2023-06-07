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
package com.codbex.kronos.integration.tests.hdb.ds.itest.hdbsynonym;

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

public class HDBSynonymHanaITest extends AbstractHDBITest {

	@Before
	public void setUpBeforeTest() throws SQLException {
		HanaITestUtils.clearDataFromDataStructure(systemDatasource, Arrays.asList( //
				"'/hdbsynonym-itest/SampleHanaXSClassicSynonym.hdbsynonym'" //
		));
		Configuration.set(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "true");
		facade.clearCache();
	}

	@Test
	public void testHDBSynonymCreate() throws Exception {
		try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

			HanaITestUtils.createSchema(stmt, TEST_SCHEMA);
			HanaITestUtils.createEmptyTable(stmt, "hdbsynonym-itest::SampleHanaTable", TEST_SCHEMA);

			LocalResource resource = HDBTestModule.getResources( //
					"/usr/local/target/dirigible/repository/root", //
					"/registry/public/hdbsynonym-itest/SampleHanaXSClassicSynonym.hdbsynonym", //
					"/registry/public/hdbsynonym-itest/SampleHanaXSClassicSynonym.hdbsynonym" //
			);

			try {
				facade.handleResourceSynchronization(resource);
				facade.updateEntities();
				assertTrue(HanaITestUtils.checkExistOfSynonym(connection, "hdbsynonym-itest::SampleHanaXSClassicSynonym", TEST_SCHEMA));
			} finally {
				HanaITestUtils.dropTable(connection, stmt, "hdbsynonym-itest::SampleHanaTable", TEST_SCHEMA);
				HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
			}
		}
	}
}
