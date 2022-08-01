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
package com.codbex.kronos.hdb.ds.model.hdbdd;

import java.util.ArrayList;
import java.util.List;

import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableColumnDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableConstraintsDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableIndexDataStructureModel;

/**
 * The table model representation.
 */
public class DataStructureEntityModel extends DataStructureModel {

  private List<HDBTableColumnDataStructureModel> columns = new ArrayList<HDBTableColumnDataStructureModel>();

  private HDBTableConstraintsDataStructureModel constraints = new HDBTableConstraintsDataStructureModel();

  private List<HDBTableIndexDataStructureModel> indexes = new ArrayList<>();

  private String namespace;

  private String context;

  /**
   * Getter for the columns.
   *
   * @return the columns
   */
  public List<HDBTableColumnDataStructureModel> getColumns() {
    return columns;
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
   * @return the context
   */
  public String getContext() {
    return context;
  }

  /**
   * @param context the context to set
   */
  public void setContext(String context) {
    this.context = context;
  }

  /**
   * @return the namespace
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * @param namespace the namespace to set
   */
  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public List<HDBTableIndexDataStructureModel> getIndexes() {
    return indexes;
  }
}
