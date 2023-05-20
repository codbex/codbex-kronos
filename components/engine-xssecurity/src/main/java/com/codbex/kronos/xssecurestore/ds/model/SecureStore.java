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
package com.codbex.kronos.xssecurestore.ds.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class SecureStore.
 */
@Table(name = "KRONOS_SECURE_STORE_LIST")
public class SecureStore {

  /** The location. */
  @Id
  @Column(name = "LOCATION", columnDefinition = "VARCHAR", nullable = false, length = 255)
  private String location;

  /** The is active. */
  @Column(name = "IS_ACTIVE", columnDefinition = "BOOLEAN", nullable = false)
  private boolean isActive;

  /**
   * Gets the location.
   *
   * @return the location
   */
  public String getLocation() {
    return location;
  }

  /**
   * Sets the location.
   *
   * @param location the new location
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Checks if is active.
   *
   * @return true, if is active
   */
  public boolean isActive() {
    return isActive;
  }

  /**
   * Sets the active.
   *
   * @param active the new active
   */
  public void setActive(boolean active) {
    isActive = active;
  }

  /**
   * Equals.
   *
   * @param o the o
   * @return true, if successful
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SecureStore that = (SecureStore) o;
    return isActive == that.isActive &&
        location.equals(that.location);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(location, isActive);
  }
}
