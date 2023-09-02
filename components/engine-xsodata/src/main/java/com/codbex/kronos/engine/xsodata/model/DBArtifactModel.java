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
package com.codbex.kronos.engine.xsodata.model;

/**
 * The Class DBArtifactModel.
 */
public class DBArtifactModel {

  /** The name. */
  private final String name;
  
  /** The type. */
  private final String type;
  
  /** The schema. */
  private final String schema;

  /**
   * Instantiates a new DB artifact model.
   *
   * @param name the name
   * @param type the type
   * @param schema the schema
   */
  public DBArtifactModel(String name, String type, String schema) {
    this.name = name;
    this.type = type;
    this.schema = schema;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public String getType() {
    return type;
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
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "DBArtifact{" +
        "name='" + name + '\'' +
        ", type='" + type + '\'' +
        ", schema='" + schema + '\'' +
        '}';
  }
}
