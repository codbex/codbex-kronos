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

import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import java.util.ArrayList;
import java.util.List;

/**
 * The table model representation.
 */
public class HDBTableDataStructureModel extends DataStructureModel {

  private String tableType;

  private String description;

  private List<HDBTableColumnDataStructureModel> columns = new ArrayList<HDBTableColumnDataStructureModel>();

  private HDBTableConstraintsDataStructureModel constraints = new HDBTableConstraintsDataStructureModel();

  private List<HDBTableIndexDataStructureModel> indexes = new ArrayList<>();

  private Boolean publicProp;

  private String loggingType;

  private Boolean temporary;

  /**
   * Getter for the columns.
   *
   * @return the columns
   */
  public List<HDBTableColumnDataStructureModel> getColumns() {
    return columns;
  }

  /**
   * @param columns the columns to set
   */
  public void setColumns(List<HDBTableColumnDataStructureModel> columns) {
    this.columns = columns;
  }

  /**
   * Gets the constraints.
   *
   * @return the constraints
   */
  public HDBTableConstraintsDataStructureModel getConstraints() {
    return constraints;
  }

  /**
   * @param constraints the constraints to set
   */
  public void setConstraints(HDBTableConstraintsDataStructureModel constraints) {
    this.constraints = constraints;
  }

  /**
   * @return the tableType
   */
  public String getTableType() {
    return tableType;
  }

  /**
   * @param tableType the tableType to set
   */
  public void setTableType(String tableType) {
    this.tableType = tableType;
  }

  /**
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @param publicProp the publicProp to set
   */
  public void setPublicProp(Boolean publicProp) {
    this.publicProp = publicProp;
  }

  public Boolean getPublicProp() {
    return publicProp;
  }

  /**
   * @param loggingType the loggingType to set
   */
  public void setLoggingType(String loggingType) {
    this.loggingType = loggingType;
  }

  public String getLoggingType() {
    return loggingType;
  }

  /**
   * @param temporary the temporary to set
   */
  public void setTemporary(Boolean temporary) {
    this.temporary = temporary;
  }

  public Boolean getTemporary() {
    return temporary;
  }


  public List<HDBTableIndexDataStructureModel> getIndexes() {
    return indexes;
  }

  public void setIndexes(List<HDBTableIndexDataStructureModel> indexes) {
    this.indexes = indexes;
  }
}
