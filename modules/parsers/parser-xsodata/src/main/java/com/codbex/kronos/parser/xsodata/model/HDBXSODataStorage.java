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
public class HDBXSODataStorage {

  /** The storage type. */
  private HDBXSODataStorageType storageType;
  
  /** The modifications. */
  private List<HDBXSODataModification> modifications = new ArrayList<>();

  /**
   * Gets the storage type.
   *
   * @return the storage type
   */
  public HDBXSODataStorageType getStorageType() {
    return storageType;
  }

  /**
   * Sets the storage type.
   *
   * @param storageType the storage type
   * @return the HDBXSO data storage
   */
  public HDBXSODataStorage setStorageType(HDBXSODataStorageType storageType) {
    this.storageType = storageType;
    return this;
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
   * @param modifications the modifications
   * @return the HDBXSO data storage
   */
  public HDBXSODataStorage setModifications(List<HDBXSODataModification> modifications) {
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
    HDBXSODataStorage that = (HDBXSODataStorage) o;
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
