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
 * The Class HDBXSODataAssociationTable.
 */
public class HDBXSODataAssociationTable {

  /** The repository object. */
  private String repositoryObject;
  
  /** The principal. */
  HDBXSODataBindingRole principal;
  
  /** The dependent. */
  HDBXSODataBindingRole dependent;
  
  /** The modifications. */
  private List<HDBXSODataModification> modifications = new ArrayList<>();

  /**
   * Gets the repository object.
   *
   * @return the repository object
   */
  public String getRepositoryObject() {
    return repositoryObject;
  }

  /**
   * Sets the repository object.
   *
   * @param repositoryObject the new repository object
   */
  public void setRepositoryObject(String repositoryObject) {
    this.repositoryObject = repositoryObject;
  }

  /**
   * Gets the principal.
   *
   * @return the principal
   */
  public HDBXSODataBindingRole getPrincipal() {
    return principal;
  }

  /**
   * Sets the principal.
   *
   * @param principal the new principal
   */
  public void setPrincipal(HDBXSODataBindingRole principal) {
    this.principal = principal;
  }

  /**
   * Gets the dependent.
   *
   * @return the dependent
   */
  public HDBXSODataBindingRole getDependent() {
    return dependent;
  }

  /**
   * Sets the dependent.
   *
   * @param dependent the new dependent
   */
  public void setDependent(HDBXSODataBindingRole dependent) {
    this.dependent = dependent;
  }

  /**
   * Gets the modifications.
   *
   * @return the modifications
   */
  public List<HDBXSODataModification> getModifications() {
    return modifications;
  }

  /**
   * Sets the modifications.
   *
   * @param modifications the new modifications
   */
  public void setModifications(List<HDBXSODataModification> modifications) {
    this.modifications = modifications;
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
    HDBXSODataAssociationTable that = (HDBXSODataAssociationTable) o;
    return Objects.equals(repositoryObject, that.repositoryObject) && Objects.equals(principal, that.principal) && Objects.equals(dependent,
        that.dependent) && Objects.equals(modifications, that.modifications);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(repositoryObject, principal, dependent, modifications);
  }
}
