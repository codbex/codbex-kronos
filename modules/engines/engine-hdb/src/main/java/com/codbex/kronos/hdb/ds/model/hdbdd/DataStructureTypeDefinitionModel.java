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
package com.codbex.kronos.hdb.ds.model.hdbdd;

/**
 * The Class DataStructureTypeDefinitionModel.
 */
public class DataStructureTypeDefinitionModel {

  /** The name. */
  private String name;

  /** The type. */
  private String type;

  /** The length. */
  private int length;

  /**
   * Instantiates a new data structure type definition model.
   *
   * @param name the name
   * @param type the type
   * @param length the length
   */
  public DataStructureTypeDefinitionModel(String name, String type, int length) {
    super();
    this.name = name;
    this.type = type;
    this.length = length;
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
   * Sets the name.
   *
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
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
   * Sets the type.
   *
   * @param type the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * Gets the length.
   *
   * @return the length
   */
  public int getLength() {
    return length;
  }

  /**
   * Sets the length.
   *
   * @param length the length to set
   */
  public void setLength(int length) {
    this.length = length;
  }


}
