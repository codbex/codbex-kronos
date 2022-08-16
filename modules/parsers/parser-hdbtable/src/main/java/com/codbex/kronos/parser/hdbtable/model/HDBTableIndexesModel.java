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

import com.codbex.kronos.parser.hdbtable.constants.HDBTablePropertiesConstants;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableMissingPropertyException;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

/**
 * The Class HDBTableIndexesModel.
 */
public class HDBTableIndexesModel {

  /** The index name. */
  @SerializedName(value = HDBTablePropertiesConstants.INDEX_PROPERTY_NAME)
  private String indexName;

  /** The unique. */
  private boolean unique;
  
  /** The order. */
  private String order;
  
  /** The index columns. */
  private Set<String> indexColumns;
  
  /** The index type. */
  private String indexType;

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
   * @param name the new index name
   */
  public void setIndexName(String name) {
    this.indexName = indexName;
  }

  /**
   * Checks if is unique.
   *
   * @return true, if is unique
   */
  public boolean isUnique() {
    return unique;
  }

  /**
   * Sets the unique.
   *
   * @param unique the new unique
   */
  public void setUnique(boolean unique) {
    this.unique = unique;
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

  /**
   * Gets the index columns.
   *
   * @return the index columns
   */
  public Set<String> getIndexColumns() {
    return indexColumns;
  }

  /**
   * Sets the index columns.
   *
   * @param indexColumns the new index columns
   */
  public void setIndexColumns(Set<String> indexColumns) {
    this.indexColumns = indexColumns;
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
   * Check for all index mandatory fields presence.
   */
  public void checkForAllIndexMandatoryFieldsPresence() {
    checkPresence(indexName, "name");
    checkPresence(unique, "unique");
    checkPresence(indexColumns, "indexColumns");
  }

  /**
   * Check presence.
   *
   * @param <T> the generic type
   * @param field the field
   * @param fieldName the field name
   */
  private <T> void checkPresence(T field, String fieldName) {
    if (Objects.isNull(field)) {
      throw new HDBTableMissingPropertyException(String.format("Missing mandatory field %s!", fieldName));
    }
    if ((field instanceof ArrayList) && ((ArrayList) field).isEmpty()) {
      throw new HDBTableMissingPropertyException(String.format("Missing mandatory field %s!", fieldName));
    }
  }
}
