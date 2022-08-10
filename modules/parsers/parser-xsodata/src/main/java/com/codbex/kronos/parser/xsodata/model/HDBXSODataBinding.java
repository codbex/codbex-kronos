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

/**
 * The Class HDBXSODataBinding.
 */
public class HDBXSODataBinding {

  /** The entity set name. */
  private String entitySetName;
  
  /** The binding role. */
  private HDBXSODataBindingRole bindingRole;
  
  /** The multiplicity type. */
  private HDBXSODataMultiplicityType multiplicityType;

  /**
   * Gets the entity set name.
   *
   * @return the entity set name
   */
  public String getEntitySetName() {
    return entitySetName;
  }

  /**
   * Sets the entity set name.
   *
   * @param entitySetName the new entity set name
   */
  public void setEntitySetName(String entitySetName) {
    this.entitySetName = entitySetName;
  }

  /**
   * Gets the binding role.
   *
   * @return the binding role
   */
  public HDBXSODataBindingRole getBindingRole() {
    return bindingRole;
  }

  /**
   * Sets the binding role.
   *
   * @param bindingRole the new binding role
   */
  public void setBindingRole(HDBXSODataBindingRole bindingRole) {
    this.bindingRole = bindingRole;
  }

  /**
   * Gets the multiplicity type.
   *
   * @return the multiplicity type
   */
  public HDBXSODataMultiplicityType getMultiplicityType() {
    return multiplicityType;
  }

  /**
   * Sets the multiplicity type.
   *
   * @param multiplicityType the new multiplicity type
   */
  public void setMultiplicityType(HDBXSODataMultiplicityType multiplicityType) {
    this.multiplicityType = multiplicityType;
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
    HDBXSODataBinding that = (HDBXSODataBinding) o;
    return Objects.equals(entitySetName, that.entitySetName) && Objects.equals(bindingRole, that.bindingRole)
        && multiplicityType == that.multiplicityType;
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(entitySetName, bindingRole, multiplicityType);
  }
}
