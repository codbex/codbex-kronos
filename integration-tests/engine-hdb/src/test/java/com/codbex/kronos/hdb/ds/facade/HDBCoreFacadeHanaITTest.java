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
package com.codbex.kronos.hdb.ds.facade;

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

import com.codbex.kronos.hdb.ds.itest.AbstractHDBITTest;
import com.codbex.kronos.hdb.ds.itest.module.HDBTestModule;
import com.codbex.kronos.hdb.ds.itest.utils.HanaITestUtils;

public class HDBCoreFacadeHanaITTest extends AbstractHDBITTest {

	@Before
	public void setUpBeforeTest() throws SQLException {
		HanaITestUtils.clearDataFromDataStructure(systemDatasource, Arrays.asList( //
				"'/acme/com/test/views/MY_VIEW1.hdbview'", //
				"'/acme/com/test/views/MY_VIEW2.hdbview'", //
				"'/acme/com/test/tables/W_TABLE1.hdbtable'", //
				"'/acme/com/test/tables/W_VIEW_3.hdbview'", //
				"'/acme/com/test/tables/MY_TABLE2.hdbtable'" //
		));
		Configuration.set(IDataStructureModel.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "true");
		facade.clearCache();
	}

	@Test
	public void testUpdateEntities() throws Exception {
		try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {

			HanaITestUtils.createSchema(stmt, TEST_SCHEMA);
			LocalResource view1Resource = HDBTestModule.getResources( //
					"/usr/local/target/dirigible/repository/root", //
					"/registry/public/acme/com/test/views/MY_VIEW1.hdbview", //
					"/hdbview-itest/MY_VIEW1.hdbview" //
			);
			LocalResource view2Resource = HDBTestModule.getResources( //
					"/usr/local/target/dirigible/repository/root", //
					"/registry/public/acme/com/test/views/MY_VIEW2.hdbview", //
					"/hdbview-itest/MY_VIEW2.hdbview" //
			);

			LocalResource table1Resource = HDBTestModule.getResources( //
					"/usr/local/target/dirigible/repository/root", //
					"/registry/public/acme/com/test/tables/W_TABLE1.hdbtable", //
					"/hdbview-itest/W_TABLE1.hdbtable" //
			);
			LocalResource table2Resource = HDBTestModule.getResources( //
					"/usr/local/target/dirigible/repository/root", //
					"/registry/public/acme/com/test/tables/MY_TABLE2.hdbtable", //
					"/hdbview-itest/MY_TABLE2.hdbtable" //
			);
			LocalResource view3Resource = HDBTestModule.getResources("/usr/local/target/dirigible/repository/root", //
					"/registry/public/acme/com/test/tables/W_VIEW_3.hdbview", //
					"/hdbview-itest/W_VIEW_3.hdbview" //
			);

			try {
				facade.handleResourceSynchronization(view3Resource);
				facade.handleResourceSynchronization(view2Resource);
				facade.handleResourceSynchronization(view1Resource);
				facade.handleResourceSynchronization(table1Resource);
				facade.handleResourceSynchronization(table2Resource);

				facade.updateEntities();
				assertTrue(HanaITestUtils.checkExistOfView(connection, "acme.com.test.views::MY_VIEW1", TEST_SCHEMA));
				assertTrue(HanaITestUtils.checkExistOfPublicSynonym(connection, "acme.com.test.views::MY_VIEW1"));
			} finally {
				HanaITestUtils.dropView(connection, stmt, "acme.com.test.views::MY_VIEW1", TEST_SCHEMA);
				HanaITestUtils.dropView(connection, stmt, "acme.com.test.views::MY_VIEW2", TEST_SCHEMA);
				HanaITestUtils.dropView(connection, stmt, "acme.com.test.tables::W_TABLE1", TEST_SCHEMA);
				HanaITestUtils.dropView(connection, stmt, "acme.com.test.tables::MY_TABLE2", TEST_SCHEMA);
				HanaITestUtils.dropView(connection, stmt, "acme.com.test.views::MY_VIEW3", TEST_SCHEMA);
				HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
			}
		}
	}
}