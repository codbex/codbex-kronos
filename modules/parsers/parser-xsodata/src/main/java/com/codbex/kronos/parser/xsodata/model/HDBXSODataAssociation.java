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
 * The Class HDBXSODataAssociation.
 */
public class HDBXSODataAssociation {

  /** The name. */
  private String name;
  
  /** The with referential constraint. */
  private boolean withReferentialConstraint;
  
  /** The principal. */
  private HDBXSODataBinding principal;
  
  /** The dependent. */
  private HDBXSODataBinding dependent;
  
  /** The association table. */
  private HDBXSODataAssociationTable associationTable;
  
  /** The storage. */
  private HDBXSODataStorage storage;
  
  /** The modifications. */
  private List<HDBXSODataModification> modifications = new ArrayList<>();

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
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Checks if is with referential constraint.
   *
   * @return true, if is with referential constraint
   */
  public boolean isWithReferentialConstraint() {
    return withReferentialConstraint;
  }

  /**
   * Sets the with referential constraint.
   *
   * @param withReferentialConstraint the new with referential constraint
   */
  public void setWithReferentialConstraint(boolean withReferentialConstraint) {
    this.withReferentialConstraint = withReferentialConstraint;
  }

  /**
   * Gets the principal.
   *
   * @return the principal
   */
  public HDBXSODataBinding getPrincipal() {
    return principal;
  }

  /**
   * Sets the principal.
   *
   * @param principal the new principal
   */
  public void setPrincipal(HDBXSODataBinding principal) {
    this.principal = principal;
  }

  /**
   * Gets the dependent.
   *
   * @return the dependent
   */
  public HDBXSODataBinding getDependent() {
    return dependent;
  }

  /**
   * Sets the dependent.
   *
   * @param dependent the new dependent
   */
  public void setDependent(HDBXSODataBinding dependent) {
    this.dependent = dependent;
  }

  /**
   * Gets the association table.
   *
   * @return the association table
   */
  public HDBXSODataAssociationTable getAssociationTable() {
    return associationTable;
  }

  /**
   * Sets the association table.
   *
   * @param associationTable the new association table
   */
  public void setAssociationTable(HDBXSODataAssociationTable associationTable) {
    this.associationTable = associationTable;
  }

  /**
   * Gets the storage.
   *
   * @return the storage
   */
  public HDBXSODataStorage getStorage() {
    return storage;
  }

  /**
   * Sets the storage.
   *
   * @param storage the new storage
   */
  public void setStorage(HDBXSODataStorage storage) {
    this.storage = storage;
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
    HDBXSODataAssociation that = (HDBXSODataAssociation) o;
    return withReferentialConstraint == that.withReferentialConstraint && Objects.equals(name, that.name) && Objects.equals(principal,
        that.principal) && Objects.equals(dependent, that.dependent) && Objects.equals(associationTable, that.associationTable)
        && Objects.equals(storage, that.storage) && Objects.equals(modifications, that.modifications);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, withReferentialConstraint, principal, dependent, associationTable, storage, modifications);
  }
}
