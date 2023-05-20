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

/**
 * The Data Structure Table Constraint Foreign Key Model.
 */
public class DataStructureHDBTableConstraintForeignKeyModel extends DataStructureHDBTableConstraintModel {

  /** The referenced table. */
  private String referencedTable;

  /** The referenced columns. */
  private String[] referencedColumns;

  /** The referenced table schema. */
  private String referencedTableSchema;

  /**
   * Gets the referenced table.
   *
   * @return the referenced table
   */
  public String getReferencedTable() {
    return referencedTable;
  }

  /**
   * Sets the referenced table.
   *
   * @param referencedTable the new referenced table
   */
  public void setReferencedTable(String referencedTable) {
    this.referencedTable = referencedTable;
  }

  /**
   * Gets the referenced columns.
   *
   * @return the referenced columns
   */
  public String[] getReferencedColumns() {
    return (referencedColumns != null) ? referencedColumns.clone() : null;
  }

  /**
   * Sets the referenced columns.
   *
   * @param referencedColumns the new referenced columns
   */
  public void setReferencedColumns(String[] referencedColumns) {
    this.referencedColumns = referencedColumns;
  }

  /**
   * Gets the referenced table schema.
   *
   * @return the referenced table schema
   */
  public String getReferencedTableSchema() {
    return referencedTableSchema;
  }

  /**
   * Sets the referenced table schema.
   *
   * @param referencedTableSchema the new referenced table schema
   */
  public void setReferencedTableSchema(String referencedTableSchema) {
    this.referencedTableSchema = referencedTableSchema;
  }
}
