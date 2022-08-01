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

public class HDBXSODataAssociationTable {

  private String repositoryObject;
  HDBXSODataBindingRole principal;
  HDBXSODataBindingRole dependent;
  private List<HDBXSODataModification> modifications = new ArrayList<>();

  public String getRepositoryObject() {
    return repositoryObject;
  }

  public void setRepositoryObject(String repositoryObject) {
    this.repositoryObject = repositoryObject;
  }

  public HDBXSODataBindingRole getPrincipal() {
    return principal;
  }

  public void setPrincipal(HDBXSODataBindingRole principal) {
    this.principal = principal;
  }

  public HDBXSODataBindingRole getDependent() {
    return dependent;
  }

  public void setDependent(HDBXSODataBindingRole dependent) {
    this.dependent = dependent;
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
    HDBXSODataAssociationTable that = (HDBXSODataAssociationTable) o;
    return Objects.equals(repositoryObject, that.repositoryObject) && Objects.equals(principal, that.principal) && Objects.equals(dependent,
        that.dependent) && Objects.equals(modifications, that.modifications);
  }

  @Override
  public int hashCode() {
    return Objects.hash(repositoryObject, principal, dependent, modifications);
  }
}
