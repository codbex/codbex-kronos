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

import com.codbex.kronos.hdb.ds.model.XSKDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.XSKDataStructureHDBTableColumnModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.XSKDataStructureHDBTableConstraintsModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.XSKDataStructureHDBTableIndexModel;

/**
 * The table model representation.
 */
public class XSKDataStructureEntityModel extends XSKDataStructureModel {

  private List<XSKDataStructureHDBTableColumnModel> columns = new ArrayList<XSKDataStructureHDBTableColumnModel>();

  private XSKDataStructureHDBTableConstraintsModel constraints = new XSKDataStructureHDBTableConstraintsModel();

  private List<XSKDataStructureHDBTableIndexModel> indexes = new ArrayList<>();

  private String namespace;

  private String context;

  /**
   * Getter for the columns.
   *
   * @return the columns
   */
  public List<XSKDataStructureHDBTableColumnModel> getColumns() {
    return columns;
  }

  /**
   * Gets the constraints.
   *
   * @return the constraints
   */
  public XSKDataStructureHDBTableConstraintsModel getConstraints() {
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

  public List<XSKDataStructureHDBTableIndexModel> getIndexes() {
    return indexes;
  }
}
