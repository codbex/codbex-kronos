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

public class XSKHDBXSODATABinding {

  private String entitySetName;
  private XSKHDBXSODATABindingRole bindingRole;
  private XSKHDBXSODATAMultiplicityType multiplicityType;

  public String getEntitySetName() {
    return entitySetName;
  }

  public void setEntitySetName(String entitySetName) {
    this.entitySetName = entitySetName;
  }

  public XSKHDBXSODATABindingRole getBindingRole() {
    return bindingRole;
  }

  public void setBindingRole(XSKHDBXSODATABindingRole bindingRole) {
    this.bindingRole = bindingRole;
  }

  public XSKHDBXSODATAMultiplicityType getMultiplicityType() {
    return multiplicityType;
  }

  public void setMultiplicityType(XSKHDBXSODATAMultiplicityType multiplicityType) {
    this.multiplicityType = multiplicityType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    XSKHDBXSODATABinding that = (XSKHDBXSODATABinding) o;
    return Objects.equals(entitySetName, that.entitySetName) && Objects.equals(bindingRole, that.bindingRole)
        && multiplicityType == that.multiplicityType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(entitySetName, bindingRole, multiplicityType);
  }
}
