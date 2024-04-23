/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.integration.tests.core.hdb.utils;

import org.eclipse.dirigible.commons.config.Configuration;

public class TestConstants {

  private TestConstants() {
  }
  
  public static final String HANA_DRIVER = "com.sap.db.jdbc.Driver";
  public static final String HANA_URL = Configuration.get("hana.url");
  public static final String HANA_USERNAME = Configuration.get("hana.username");
  public static final String HANA_PASSWORD = Configuration.get("hana.password");

}
