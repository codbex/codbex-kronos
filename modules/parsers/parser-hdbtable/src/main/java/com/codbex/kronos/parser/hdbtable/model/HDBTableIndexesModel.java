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
package com.codbex.kronos.parser.hdbtable.model;

import com.codbex.kronos.parser.hdbtable.constants.HdbtablePropertiesConstants;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableMissingPropertyException;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

public class HDBTableIndexesModel {

  @SerializedName(value = HdbtablePropertiesConstants.INDEX_PROPERTY_NAME)
  private String indexName;

  private boolean unique;
  private String order;
  private Set<String> indexColumns;
  private String indexType;

  public String getIndexName() {
    return indexName;
  }

  public void setIndexName(String name) {
    this.indexName = indexName;
  }

  public boolean isUnique() {
    return unique;
  }

  public void setUnique(boolean unique) {
    this.unique = unique;
  }

  public String getOrder() {
    return order;
  }

  public void setOrder(String order) {
    this.order = order;
  }

  public Set<String> getIndexColumns() {
    return indexColumns;
  }

  public void setIndexColumns(Set<String> indexColumns) {
    this.indexColumns = indexColumns;
  }

  public String getIndexType() {
    return indexType;
  }

  public void setIndexType(String indexType) {
    this.indexType = indexType;
  }

  public void checkForAllIndexMandatoryFieldsPresence() {
    checkPresence(indexName, "name");
    checkPresence(unique, "unique");
    checkPresence(indexColumns, "indexColumns");
  }

  private <T> void checkPresence(T field, String fieldName) {
    if (Objects.isNull(field)) {
      throw new HDBTableMissingPropertyException(String.format("Missing mandatory field %s!", fieldName));
    }
    if ((field instanceof ArrayList) && ((ArrayList) field).isEmpty()) {
      throw new HDBTableMissingPropertyException(String.format("Missing mandatory field %s!", fieldName));
    }
  }
}
