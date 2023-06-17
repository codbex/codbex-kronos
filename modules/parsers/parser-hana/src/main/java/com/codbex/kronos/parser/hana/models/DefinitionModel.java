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
package com.codbex.kronos.parser.hana.models;


/**
 * The Class DefinitionModel.
 */
public class DefinitionModel {

  /** The schema. */
  private final String schema;
  
  /** The name. */
  private final String name;

  /**
   * Instantiates a new definition model.
   *
   * @param schema the schema
   * @param name the name
   */
  public DefinitionModel(String schema, String name) {
    this.schema = schema;
    this.name = name;
  }

  /**
   * Gets the schema.
   *
   * @return the schema
   */
  public String getSchema() {
    return schema;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

}
