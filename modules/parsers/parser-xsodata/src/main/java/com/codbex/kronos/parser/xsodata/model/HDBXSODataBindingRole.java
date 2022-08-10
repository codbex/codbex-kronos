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
package com.codbex.kronos.parser.xsodata.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Class HDBXSODataBindingRole.
 */
public class HDBXSODataBindingRole {

  /** The binding type. */
  private HDBXSODataBindingType bindingType;
  
  /** The keys. */
  private List<String> keys = new ArrayList<>();

  /**
   * Gets the binding type.
   *
   * @return the binding type
   */
  public HDBXSODataBindingType getBindingType() {
    return bindingType;
  }

  /**
   * Sets the binding type.
   *
   * @param bindingType the binding type
   * @return the HDBXSO data binding role
   */
  public HDBXSODataBindingRole setBindingType(HDBXSODataBindingType bindingType) {
    this.bindingType = bindingType;
    return this;
  }

  /**
   * Gets the keys.
   *
   * @return the keys
   */
  public List<String> getKeys() {
    return keys;
  }

  /**
   * Sets the keys.
   *
   * @param keys the keys
   * @return the HDBXSO data binding role
   */
  public HDBXSODataBindingRole setKeys(List<String> keys) {
    this.keys = keys;
    return this;
  }

  /**
   * Equals.
   *
   * @param o the o
   * @return true, if successful
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    HDBXSODataBindingRole that = (HDBXSODataBindingRole) o;
    return bindingType == that.bindingType && Objects.equals(keys, that.keys);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(bindingType, keys);
  }
}
