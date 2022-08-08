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
 * The Data Structure Table Constraint Unique Model.
 */
public class DataStructureHDBTableConstraintUniqueModel extends DataStructureHDBTableConstraintModel {

  /** The index name. */
  private String indexName;
  
  /** The index type. */
  private String indexType;
  
  /** The order. */
  private String order;
  
  /**
   * Instantiates a new data structure HDB table constraint unique model.
   */
  public DataStructureHDBTableConstraintUniqueModel(){
  }

  /**
   * Instantiates a new data structure HDB table constraint unique model.
   *
   * @param indexName the index name
   * @param order the order
   * @param indexColumns the index columns
   */
  public DataStructureHDBTableConstraintUniqueModel(String indexName, String order, String[] indexColumns){
    this.indexName = indexName;
    this.order = order;
    this.setColumns(indexColumns);
  }

  /**
   * Gets the index name.
   *
   * @return the index name
   */
  public String getIndexName() {
    return indexName;
  }

  /**
   * Sets the index name.
   *
   * @param indexName the new index name
   */
  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  /**
   * Gets the index type.
   *
   * @return the index type
   */
  public String getIndexType() {
    return indexType;
  }

  /**
   * Sets the index type.
   *
   * @param indexType the new index type
   */
  public void setIndexType(String indexType) {
    this.indexType = indexType;
  }

  /**
   * Gets the order.
   *
   * @return the order
   */
  public String getOrder() {
    return order;
  }

  /**
   * Sets the order.
   *
   * @param order the new order
   */
  public void setOrder(String order) {
    this.order = order;
  }

}
