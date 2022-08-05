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

import java.util.Set;

public class DataStructureHDBTableIndexModel {

  private String indexName;
  private String indexType;
  private String order;
  private boolean unique;
  private Set<String> indexColumns;

  public DataStructureHDBTableIndexModel() {
  }

  public DataStructureHDBTableIndexModel(String indexName, String order, Set<String> indexColumns, boolean isUnique) {
    this.indexName = indexName;
    this.order = order;
    this.indexColumns = indexColumns;
    this.unique = isUnique;
  }

  public String getIndexName() {
    return indexName;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  public String getIndexType() {
    return indexType;
  }

  public void setIndexType(String indexType) {
    this.indexType = indexType;
  }

  public String getOrder() {
    return order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public boolean isUnique() {
    return unique;
  }

  public void setUnique(boolean unique) {
    this.unique = unique;
  }

  public Set<String> getIndexColumns() {
    return indexColumns;
  }

  public void setIndexColumns(Set<String> indexColumns) {
    this.indexColumns = indexColumns;
  }
}
