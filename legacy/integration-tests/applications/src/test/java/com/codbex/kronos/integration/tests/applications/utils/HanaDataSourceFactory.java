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
package com.codbex.kronos.integration.tests.applications.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import static com.codbex.kronos.integration.tests.core.hdb.utils.TestConstants.HANA_DRIVER;
import static com.codbex.kronos.integration.tests.core.hdb.utils.TestConstants.HANA_PASSWORD;
import static com.codbex.kronos.integration.tests.core.hdb.utils.TestConstants.HANA_URL;
import static com.codbex.kronos.integration.tests.core.hdb.utils.TestConstants.HANA_USERNAME;

import javax.sql.DataSource;

public class HanaDataSourceFactory {

  private static DataSource dataSource;
  private final static HikariConfig config;

  static {
    config = new HikariConfig();

    config.setDriverClassName(HANA_DRIVER);
    config.setJdbcUrl(HANA_URL);
    config.setUsername(HANA_USERNAME);
    config.setPassword(HANA_PASSWORD);

    config.setLeakDetectionThreshold(10000);

    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
  }

  private HanaDataSourceFactory() {
  }

  public static DataSource getDataSource() {
    if (dataSource == null) {
      synchronized (HanaDataSourceFactory.class) {
        if (dataSource == null) {
          dataSource = new HikariDataSource(config);
        }
      }
    }
    return dataSource;
  }
}
