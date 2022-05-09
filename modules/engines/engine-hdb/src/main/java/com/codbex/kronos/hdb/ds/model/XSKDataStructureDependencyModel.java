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
package com.codbex.kronos.hdb.ds.model;

import java.util.Objects;

/**
 * Dependency element of the DataStructureModel.
 */
public class XSKDataStructureDependencyModel {

  private String name;

  private String type;

  /**
   * The constructor from fields.
   *
   * @param name the name
   * @param type the type
   */
  public XSKDataStructureDependencyModel(String name, String type) {
    super();
    this.name = name;
    this.type = type;
  }

  /**
   * Getter for the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Setter for the name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter for the type.
   *
   * @return the type
   */
  public String getType() {
    return type;
  }

  /**
   * Setter for the type.
   *
   * @param type the type
   */
  public void setType(String type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    XSKDataStructureDependencyModel that = (XSKDataStructureDependencyModel) o;
    return Objects.equals(name, that.name) && Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type);
  }
}
