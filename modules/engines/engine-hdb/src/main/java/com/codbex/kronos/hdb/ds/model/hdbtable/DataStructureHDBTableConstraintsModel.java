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
package com.codbex.kronos.hdb.ds.model.hdbtable;

import java.util.ArrayList;
import java.util.List;

/**
 * The Data Structure Table Constraints Model.
 */
public class DataStructureHDBTableConstraintsModel {

  /** The primary key. */
  private DataStructureHDBTableConstraintPrimaryKeyModel primaryKey;

  /** The foreign keys. */
  private List<DataStructureHDBTableConstraintForeignKeyModel> foreignKeys = new ArrayList<DataStructureHDBTableConstraintForeignKeyModel>();

  /** The unique indices. */
  private List<DataStructureHDBTableConstraintUniqueModel> uniqueIndices = new ArrayList<DataStructureHDBTableConstraintUniqueModel>();

  /** The checks. */
  private List<DataStructureHDBTableConstraintCheckModel> checks = new ArrayList<DataStructureHDBTableConstraintCheckModel>();

  /**
   * Gets the primary key.
   *
   * @return the primary key
   */
  public DataStructureHDBTableConstraintPrimaryKeyModel getPrimaryKey() {
    return primaryKey;
  }

  /**
   * Sets the primary key.
   *
   * @param primaryKey the new primary key
   */
  public void setPrimaryKey(DataStructureHDBTableConstraintPrimaryKeyModel primaryKey) {
    this.primaryKey = primaryKey;
  }

  /**
   * Gets the foreign keys.
   *
   * @return the foreign keys
   */
  public List<DataStructureHDBTableConstraintForeignKeyModel> getForeignKeys() {
    return foreignKeys;
  }

  /**
   * Gets the unique indices.
   *
   * @return the unique indices
   */
  public List<DataStructureHDBTableConstraintUniqueModel> getUniqueIndices() {
    return uniqueIndices;
  }

  /**
   * Sets the unique indices.
   *
   * @param uniqueIndices the new unique indices
   */
  public void setUniqueIndices(List<DataStructureHDBTableConstraintUniqueModel> uniqueIndices) {
    this.uniqueIndices = uniqueIndices;
  }

  /**
   * Gets the checks.
   *
   * @return the checks
   */
  public List<DataStructureHDBTableConstraintCheckModel> getChecks() {
    return checks;
  }

}
