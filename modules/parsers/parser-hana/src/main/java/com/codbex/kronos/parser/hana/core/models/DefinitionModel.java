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
package com.codbex.kronos.parser.hana.core.models;


public class DefinitionModel {

  private final String schema;
  private final String name;

  public DefinitionModel(String schema, String name) {
    this.schema = schema;
    this.name = name;
  }

  public String getSchema() {
    return schema;
  }

  public String getName() {
    return name;
  }

}
