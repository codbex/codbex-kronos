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

public class HDBXSODataStorage {

  private HDBXSODataStorageType storageType;
  private List<HDBXSODataModification> modifications = new ArrayList<>();

  public HDBXSODataStorageType getStorageType() {
    return storageType;
  }

  public HDBXSODataStorage setStorageType(HDBXSODataStorageType storageType) {
    this.storageType = storageType;
    return this;
  }

  public List<HDBXSODataModification> getModifications() {
    return modifications;
  }

  public HDBXSODataStorage setModifications(List<HDBXSODataModification> modifications) {
    this.modifications = modifications;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    HDBXSODataStorage that = (HDBXSODataStorage) o;
    return storageType == that.storageType && Objects.equals(modifications, that.modifications);
  }

  @Override
  public int hashCode() {
    return Objects.hash(storageType, modifications);
  }
}
