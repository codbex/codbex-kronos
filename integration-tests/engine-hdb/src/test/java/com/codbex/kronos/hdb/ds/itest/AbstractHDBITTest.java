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
package com.codbex.kronos.hdb.ds.itest;

import static com.codbex.kronos.hdb.ds.itest.utils.TestConstants.HANA_DRIVER;
import static com.codbex.kronos.hdb.ds.itest.utils.TestConstants.HANA_PASSWORD;
import static com.codbex.kronos.hdb.ds.itest.utils.TestConstants.HANA_URL;
import static com.codbex.kronos.hdb.ds.itest.utils.TestConstants.HANA_USERNAME;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.eclipse.dirigible.commons.config.StaticObjects;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.codbex.kronos.hdb.ds.facade.IHDBCoreSynchronizationFacade;
import com.codbex.kronos.hdb.ds.facade.HDBCoreSynchronizationFacade;
import com.codbex.kronos.hdb.ds.itest.model.JDBCModel;
import com.codbex.kronos.hdb.ds.itest.module.HDBTestModule;
import com.codbex.kronos.hdb.ds.itest.utils.HanaITestUtils;

public abstract class AbstractHDBITTest {

	protected static final String TEST_SCHEMA= "TEST_SCHEMA";

	protected static DataSource datasource;
	protected static DataSource systemDatasource;

	protected static IHDBCoreSynchronizationFacade facade;

	@BeforeClass
	public static void setUp() {
		JDBCModel model = new JDBCModel(HANA_DRIVER, HANA_URL, HANA_USERNAME, HANA_PASSWORD);
		HDBTestModule hdbTestModule = new HDBTestModule(model);
		hdbTestModule.configure();
		datasource = (DataSource) StaticObjects.get(StaticObjects.DATASOURCE);
		systemDatasource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);
		facade = new HDBCoreSynchronizationFacade();
		facade.clearCache();
	}

	
	@AfterClass
	public static void tearDown() {
		try (Connection connection = datasource.getConnection(); Statement stmt = connection.createStatement()) {
			HanaITestUtils.dropSchema(stmt, TEST_SCHEMA);
		} catch (SQLException e) {
			// Do nothing, the schema is already dropped
		}
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public IHDBCoreSynchronizationFacade getFacade() {
		return facade;
	}
}
