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

public class XSKHDBXSODATAStorage {

  private XSKHDBXSODATAStorageType storageType;
  private List<XSKHDBXSODATAModification> modifications = new ArrayList<>();

  public XSKHDBXSODATAStorageType getStorageType() {
    return storageType;
  }

  public XSKHDBXSODATAStorage setStorageType(XSKHDBXSODATAStorageType storageType) {
    this.storageType = storageType;
    return this;
  }

  public List<XSKHDBXSODATAModification> getModifications() {
    return modifications;
  }

  public XSKHDBXSODATAStorage setModifications(List<XSKHDBXSODATAModification> modifications) {
    this.modifications = modifications;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    XSKHDBXSODATAStorage that = (XSKHDBXSODATAStorage) o;
    return storageType == that.storageType && Objects.equals(modifications, that.modifications);
  }

  @Override
  public int hashCode() {
    return Objects.hash(storageType, modifications);
  }
}
