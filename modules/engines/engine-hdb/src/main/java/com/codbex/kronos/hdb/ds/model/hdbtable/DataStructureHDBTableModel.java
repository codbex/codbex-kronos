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

import com.codbex.kronos.hdb.ds.model.DataStructureModel;

/**
 * The table model representation.
 */
public class DataStructureHDBTableModel extends DataStructureModel {

  /** The table type. */
  private String tableType;

  /** The description. */
  private String description;

  /** The columns. */
  private List<DataStructureHDBTableColumnModel> columns = new ArrayList<DataStructureHDBTableColumnModel>();

  /** The constraints. */
  private DataStructureHDBTableConstraintsModel constraints = new DataStructureHDBTableConstraintsModel();

  /** The indexes. */
  private List<DataStructureHDBTableIndexModel> indexes = new ArrayList<>();

  /** The public prop. */
  private Boolean publicProp;

  /** The logging type. */
  private String loggingType;

  /** The temporary. */
  private Boolean temporary;

  /**
   * Getter for the columns.
   *
   * @return the columns
   */
  public List<DataStructureHDBTableColumnModel> getColumns() {
    return columns;
  }

  /**
   * Sets the columns.
   *
   * @param columns the columns to set
   */
  public void setColumns(List<DataStructureHDBTableColumnModel> columns) {
    this.columns = columns;
  }

  /**
   * Gets the constraints.
   *
   * @return the constraints
   */
  public DataStructureHDBTableConstraintsModel getConstraints() {
    return constraints;
  }

  /**
   * Sets the constraints.
   *
   * @param constraints the constraints to set
   */
  public void setConstraints(DataStructureHDBTableConstraintsModel constraints) {
    this.constraints = constraints;
  }

  /**
   * Gets the table type.
   *
   * @return the tableType
   */
  public String getTableType() {
    return tableType;
  }

  /**
   * Sets the table type.
   *
   * @param tableType the tableType to set
   */
  public void setTableType(String tableType) {
    this.tableType = tableType;
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Sets the public prop.
   *
   * @param publicProp the publicProp to set
   */
  public void setPublicProp(Boolean publicProp) {
    this.publicProp = publicProp;
  }

  /**
   * Gets the public prop.
   *
   * @return the public prop
   */
  public Boolean getPublicProp() {
    return publicProp;
  }

  /**
   * Sets the logging type.
   *
   * @param loggingType the loggingType to set
   */
  public void setLoggingType(String loggingType) {
    this.loggingType = loggingType;
  }

  /**
   * Gets the logging type.
   *
   * @return the logging type
   */
  public String getLoggingType() {
    return loggingType;
  }

  /**
   * Sets the temporary.
   *
   * @param temporary the temporary to set
   */
  public void setTemporary(Boolean temporary) {
    this.temporary = temporary;
  }

  /**
   * Gets the temporary.
   *
   * @return the temporary
   */
  public Boolean getTemporary() {
    return temporary;
  }


  /**
   * Gets the indexes.
   *
   * @return the indexes
   */
  public List<DataStructureHDBTableIndexModel> getIndexes() {
    return indexes;
  }

  /**
   * Sets the indexes.
   *
   * @param indexes the new indexes
   */
  public void setIndexes(List<DataStructureHDBTableIndexModel> indexes) {
    this.indexes = indexes;
  }
}
