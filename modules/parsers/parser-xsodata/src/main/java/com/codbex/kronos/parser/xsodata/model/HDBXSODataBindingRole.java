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

public class HDBXSODataBindingRole {

  private HDBXSODataBindingType bindingType;
  private List<String> keys = new ArrayList<>();

  public HDBXSODataBindingType getBindingType() {
    return bindingType;
  }

  public HDBXSODataBindingRole setBindingType(HDBXSODataBindingType bindingType) {
    this.bindingType = bindingType;
    return this;
  }

  public List<String> getKeys() {
    return keys;
  }

  public HDBXSODataBindingRole setKeys(List<String> keys) {
    this.keys = keys;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    HDBXSODataBindingRole that = (HDBXSODataBindingRole) o;
    return bindingType == that.bindingType && Objects.equals(keys, that.keys);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bindingType, keys);
  }
}
