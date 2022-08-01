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
package com.codbex.kronos.xsodata.ds.model;

public class DBArtifactModel {

  private final String name;
  private final String type;
  private final String schema;

  public DBArtifactModel(String name, String type, String schema) {
    this.name = name;
    this.type = type;
    this.schema = schema;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getSchema() {
    return schema;
  }

  @Override
  public String toString() {
    return "DBArtifact{" +
        "name='" + name + '\'' +
        ", type='" + type + '\'' +
        ", schema='" + schema + '\'' +
        '}';
  }
}
