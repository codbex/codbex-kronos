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

import java.util.ArrayList;
import java.util.List;

import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableColumnModel;

/**
 * The Class DataStructureHDBTableTypeModel.
 */
public class DataStructureHDBTableTypeModel extends DataStructureModel {


  /** The columns. */
  private List<DataStructureHDBTableColumnModel> columns = new ArrayList<>();
  
  /** The primary key. */
  private DataStructureHDBTableTypePrimaryKeyModel primaryKey = new DataStructureHDBTableTypePrimaryKeyModel();
  
  /** The public prop. */
  private Boolean publicProp;

  /**
   * Gets the columns.
   *
   * @return the columns
   */
  public List<DataStructureHDBTableColumnModel> getColumns() {
    return columns;
  }

  /**
   * Sets the columns.
   *
   * @param columns the new columns
   */
  public void setColumns(List<DataStructureHDBTableColumnModel> columns) {
    this.columns = columns;
  }

  /**
   * Gets the primary key.
   *
   * @return the primary key
   */
  public DataStructureHDBTableTypePrimaryKeyModel getPrimaryKey() {
    return primaryKey;
  }

  /**
   * Sets the primary key.
   *
   * @param primaryKey the new primary key
   */
  public void setPrimaryKey(DataStructureHDBTableTypePrimaryKeyModel primaryKey) {
    this.primaryKey = primaryKey;
  }

  /**
   * Checks if is public prop.
   *
   * @return the boolean
   */
  public Boolean isPublicProp() {
    return publicProp;
  }

  /**
   * Sets the public prop.
   *
   * @param publicProp the new public prop
   */
  public void setPublicProp(Boolean publicProp) {
    this.publicProp = publicProp;
  }
}
