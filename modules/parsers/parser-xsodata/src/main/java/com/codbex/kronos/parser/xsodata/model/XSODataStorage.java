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
 * The Class HDBXSODataStorage.
 */
public class XSODataStorage {

  /** The storage type. */
  private XSODataStorageType storageType;
  
  /** The modifications. */
  private List<XSODataModification> modifications = new ArrayList<>();

  /**
   * Gets the storage type.
   *
   * @return the storage type
   */
  public XSODataStorageType getStorageType() {
    return storageType;
  }

  /**
   * Sets the storage type.
   *
   * @param storageType the storage type
   * @return the HDBXSO data storage
   */
  public XSODataStorage setStorageType(XSODataStorageType storageType) {
    this.storageType = storageType;
    return this;
  }

  /**
   * Gets the modifications.
   *
   * @return the modifications
   */
  public List<XSODataModification> getModifications() {
    return modifications;
  }

  /**
   * Sets the modifications.
   *
   * @param modifications the modifications
   * @return the HDBXSO data storage
   */
  public XSODataStorage setModifications(List<XSODataModification> modifications) {
    this.modifications = modifications;
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
    XSODataStorage that = (XSODataStorage) o;
    return storageType == that.storageType && Objects.equals(modifications, that.modifications);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(storageType, modifications);
  }
}
