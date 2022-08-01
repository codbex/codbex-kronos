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

import java.util.Objects;

public class HDBXSODataBinding {

  private String entitySetName;
  private HDBXSODataBindingRole bindingRole;
  private HDBXSODataMultiplicityType multiplicityType;

  public String getEntitySetName() {
    return entitySetName;
  }

  public void setEntitySetName(String entitySetName) {
    this.entitySetName = entitySetName;
  }

  public HDBXSODataBindingRole getBindingRole() {
    return bindingRole;
  }

  public void setBindingRole(HDBXSODataBindingRole bindingRole) {
    this.bindingRole = bindingRole;
  }

  public HDBXSODataMultiplicityType getMultiplicityType() {
    return multiplicityType;
  }

  public void setMultiplicityType(HDBXSODataMultiplicityType multiplicityType) {
    this.multiplicityType = multiplicityType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    HDBXSODataBinding that = (HDBXSODataBinding) o;
    return Objects.equals(entitySetName, that.entitySetName) && Objects.equals(bindingRole, that.bindingRole)
        && multiplicityType == that.multiplicityType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(entitySetName, bindingRole, multiplicityType);
  }
}
