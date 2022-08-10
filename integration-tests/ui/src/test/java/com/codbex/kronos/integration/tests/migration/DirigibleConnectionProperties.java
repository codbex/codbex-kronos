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
package com.codbex.kronos.integration.tests.migration;

import java.net.URI;

class DirigibleConnectionProperties {

  static final String HOST = "localhost";
  static final String PORT = "8080";
  static final String IDE_URI = "/services/v4/web/ide/";
  static final String AUTH_USERNAME = "dirigible";
  static final String AUTH_PASSWORD = "dirigible";
  static final String AUTH = AUTH_USERNAME + ":" + AUTH_PASSWORD;
  static final String BASE_URL = "http://" + AUTH + "@" + HOST + ":" + PORT + IDE_URI;
  static final URI LOCALHOST_URI = URI.create("http://localhost:8080");
}
