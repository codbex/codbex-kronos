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
package com.codbex.kronos.parser.xsodata.model;

import java.util.Objects;

/**
 * Representing association property of a service. For example:
 * <pre>{@code
 * service {
 *      "namespace.name::customer" as "Customers"
 *          navigates ("Orders_Customers" as "HisOrders");
 *      "namespace.name::order" as "Orders";
 *      association "Orders_Customers" with referential constraint
 *           dependent  "Customers"("OrderID") multiplicity "*"
 *           principal "Orders"("ID") multiplicity "1";
 *  }
 * }</pre>
 * In the example, the association is "Orders_Customers" and the alias is "HisOrders".
 */

public class XSODataNavigation {

  /** The association. */
  private String association;
  
  /** The alias navigation. */
  private String aliasNavigation;
  
  /** The from binding type. */
  private XSODataBindingType fromBindingType;

  /**
   * Gets the association.
   *
   * @return the association
   */
  public String getAssociation() {
    return association;
  }

  /**
   * Sets the association.
   *
   * @param association the association
   * @return the HDBXSO data navigation
   */
  public XSODataNavigation setAssociation(String association) {
    this.association = association;
    return this;
  }

  /**
   * Gets the alias navigation.
   *
   * @return the alias navigation
   */
  public String getAliasNavigation() {
    return aliasNavigation;
  }

  /**
   * Sets the alias navigation.
   *
   * @param aliasNavigation the alias navigation
   * @return the HDBXSO data navigation
   */
  public XSODataNavigation setAliasNavigation(String aliasNavigation) {
    this.aliasNavigation = aliasNavigation;
    return this;
  }

  /**
   * Gets the from binding type.
   *
   * @return the from binding type
   */
  public XSODataBindingType getFromBindingType() {
    return fromBindingType;
  }

  /**
   * Sets the from binding type.
   *
   * @param fromBindingType the from binding type
   * @return the HDBXSO data navigation
   */
  public XSODataNavigation setFromBindingType(XSODataBindingType fromBindingType) {
    this.fromBindingType = fromBindingType;
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
    XSODataNavigation that = (XSODataNavigation) o;
    return Objects.equals(association, that.association) && Objects.equals(aliasNavigation, that.aliasNavigation)
        && fromBindingType == that.fromBindingType;
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(association, aliasNavigation, fromBindingType);
  }
}
