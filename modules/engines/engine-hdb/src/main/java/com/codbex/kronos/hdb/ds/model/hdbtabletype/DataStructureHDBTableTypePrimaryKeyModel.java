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
package com.codbex.kronos.hdb.ds.model.hdbtabletype;

public class DataStructureHDBTableTypePrimaryKeyModel {

  private String name;

  private String[] primaryKeyColumns;


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
   * Gets the primary key columns.
   *
   * @return the primary key columns
   */
  public String[] getPrimaryKeyColumns() {
    return (primaryKeyColumns != null) ? primaryKeyColumns.clone() : null;
  }

  /**
   * Sets the primary key columns.
   *
   * @param primaryKeyColumns the new primary key columns
   */
  public void setPrimaryKeyColumns(String[] primaryKeyColumns) {
    this.primaryKeyColumns = primaryKeyColumns;
  }

}
