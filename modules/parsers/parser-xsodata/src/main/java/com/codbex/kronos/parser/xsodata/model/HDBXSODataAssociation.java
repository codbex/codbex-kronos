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

public class HDBXSODataAssociation {

  private String name;
  private boolean withReferentialConstraint;
  private HDBXSODataBinding principal;
  private HDBXSODataBinding dependent;
  private HDBXSODataAssociationTable associationTable;
  private HDBXSODataStorage storage;
  private List<HDBXSODataModification> modifications = new ArrayList<>();

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isWithReferentialConstraint() {
    return withReferentialConstraint;
  }

  public void setWithReferentialConstraint(boolean withReferentialConstraint) {
    this.withReferentialConstraint = withReferentialConstraint;
  }

  public HDBXSODataBinding getPrincipal() {
    return principal;
  }

  public void setPrincipal(HDBXSODataBinding principal) {
    this.principal = principal;
  }

  public HDBXSODataBinding getDependent() {
    return dependent;
  }

  public void setDependent(HDBXSODataBinding dependent) {
    this.dependent = dependent;
  }

  public HDBXSODataAssociationTable getAssociationTable() {
    return associationTable;
  }

  public void setAssociationTable(HDBXSODataAssociationTable associationTable) {
    this.associationTable = associationTable;
  }

  public HDBXSODataStorage getStorage() {
    return storage;
  }

  public void setStorage(HDBXSODataStorage storage) {
    this.storage = storage;
  }

  public List<HDBXSODataModification> getModifications() {
    return modifications;
  }

  public void setModifications(List<HDBXSODataModification> modifications) {
    this.modifications = modifications;
  }

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

  @Override
  public int hashCode() {
    return Objects.hash(name, withReferentialConstraint, principal, dependent, associationTable, storage, modifications);
  }
}
