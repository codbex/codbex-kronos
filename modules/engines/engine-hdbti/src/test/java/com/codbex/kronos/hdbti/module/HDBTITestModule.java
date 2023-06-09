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
package com.codbex.kronos.hdbti.module;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.eclipse.dirigible.commons.api.module.AbstractDirigibleModule;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.repository.api.IRepository;

import com.codbex.kronos.hdbti.module.HDBTIModule;
import com.codbex.kronos.hdbti.repository.TestRepository;

public class HDBTITestModule extends AbstractDirigibleModule {
    @Override
	public String getName() {
		return "HDBTI Test Module";
	}

	@Override
	public int getPriority() {
		return super.getPriority();
	}

	@Override
    public void configure() {
    	StaticObjects.set(StaticObjects.REPOSITORY, new TestRepository());
    	StaticObjects.set(StaticObjects.DATASOURCE, getDataSource());
    	StaticObjects.set(StaticObjects.SYSTEM_DATASOURCE, getSystemDataSource());
    	
    	HDBTIModule hdbtiModule = new HDBTIModule();
    	hdbtiModule.configure();
    }

    private static DataSource getDataSource() {
        return createDataSource("jdbc:h2:mem:kronos-datasource;DB_CLOSE_DELAY=-1");
    }

    private static DataSource getSystemDataSource() {
    	return createDataSource("jdbc:h2:mem:kronos-system-datasource;DB_CLOSE_DELAY=-1");
    }

	private static DataSource createDataSource(String url) {
		BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.h2.Driver");
		basicDataSource.setUrl(url);
        basicDataSource.setUsername("sa");
        basicDataSource.setPassword("sa");
        basicDataSource.setDefaultAutoCommit(true);
        basicDataSource.setAccessToUnderlyingConnectionAllowed(true);
        return basicDataSource;
	}

}
