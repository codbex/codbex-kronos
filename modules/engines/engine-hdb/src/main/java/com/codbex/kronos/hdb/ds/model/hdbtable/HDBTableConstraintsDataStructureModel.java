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
public class HDBTableConstraintsDataStructureModel {

  private HDBTableConstraintPrimaryKeyDataStructureModel primaryKey;

  private List<HDBTableConstraintForeignKeyDataStructureModel> foreignKeys = new ArrayList<HDBTableConstraintForeignKeyDataStructureModel>();

  private List<HDBTableConstraintUniqueDataStructureModel> uniqueIndices = new ArrayList<HDBTableConstraintUniqueDataStructureModel>();

  private List<HDBTableConstraintCheckDataStructureModel> checks = new ArrayList<HDBTableConstraintCheckDataStructureModel>();

  /**
   * Gets the primary key.
   *
   * @return the primary key
   */
  public HDBTableConstraintPrimaryKeyDataStructureModel getPrimaryKey() {
    return primaryKey;
  }

  /**
   * Sets the primary key.
   *
   * @param primaryKey the new primary key
   */
  public void setPrimaryKey(HDBTableConstraintPrimaryKeyDataStructureModel primaryKey) {
    this.primaryKey = primaryKey;
  }

  /**
   * Gets the foreign keys.
   *
   * @return the foreign keys
   */
  public List<HDBTableConstraintForeignKeyDataStructureModel> getForeignKeys() {
    return foreignKeys;
  }

  /**
   * Gets the unique indices.
   *
   * @return the unique indices
   */
  public List<HDBTableConstraintUniqueDataStructureModel> getUniqueIndices() {
    return uniqueIndices;
  }

  public void setUniqueIndices(List<HDBTableConstraintUniqueDataStructureModel> uniqueIndices) {
    this.uniqueIndices = uniqueIndices;
  }

  /**
   * Gets the checks.
   *
   * @return the checks
   */
  public List<HDBTableConstraintCheckDataStructureModel> getChecks() {
    return checks;
  }

}
