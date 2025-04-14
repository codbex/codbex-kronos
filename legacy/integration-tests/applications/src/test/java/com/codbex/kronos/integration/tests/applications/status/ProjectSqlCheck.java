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
package com.codbex.kronos.integration.tests.applications.status;

public class ProjectSqlCheck {

  private final String schemaName;
  private final String tableName;
  private final boolean tableExistsCheck;
  private final boolean tableHasRecordsCheck;

  public ProjectSqlCheck(String schemaName, String tableName, boolean tableExistsCheck, boolean tableHasRecordsCheck) {
    this.schemaName = schemaName;
    this.tableName = tableName;
    this.tableExistsCheck = tableExistsCheck;
    this.tableHasRecordsCheck = tableHasRecordsCheck;
  }

  public String getSchemaName() {
    return schemaName;
  }

  public String getTableName() {
    return tableName;
  }

  public boolean tableExistsCheck() {
    return tableExistsCheck;
  }

  public boolean tableHasRecordsCheck() {
    return tableHasRecordsCheck;
  }
}