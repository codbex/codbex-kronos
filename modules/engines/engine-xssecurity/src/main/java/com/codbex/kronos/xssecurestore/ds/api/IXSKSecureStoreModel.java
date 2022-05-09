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
package com.codbex.kronos.xssecurestore.ds.api;

public interface IXSKSecureStoreModel {

  String FILE_EXTENSION_XSSECURESTORE = ".xssecurestore";

  String VALUE_APP_USER = "APP";

  String SECURE_STORE_CONTENT_TABLE_NAME = "XSK_SECURE_STORE";

  String SECURE_STORE_VALUE_FIND_STATEMENT = "SELECT * FROM " + SECURE_STORE_CONTENT_TABLE_NAME + "\n" +
      "WHERE STORE_ID=?\n" +
      "AND USER_ID=?\n" +
      "AND DATA_ID=?;";

  String SECURE_STORE_VALUE_DELETE_STATEMENT = "DELETE FROM " + SECURE_STORE_CONTENT_TABLE_NAME + "\n" +
      "WHERE STORE_ID=?\n" +
      "AND USER_ID=?\n" +
      "AND DATA_ID=?;";

  String SECURE_STORE_DELETE_BY_STORE_ID = "DELETE FROM " + SECURE_STORE_CONTENT_TABLE_NAME + "\n" +
      "WHERE STORE_ID=?;";
}
