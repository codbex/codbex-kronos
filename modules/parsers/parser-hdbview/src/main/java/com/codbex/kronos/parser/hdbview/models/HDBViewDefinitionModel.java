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
package com.codbex.kronos.parser.hdbview.models;

import java.util.List;
import java.util.Objects;

import com.codbex.kronos.parser.hdbview.exceptions.HDBViewMissingPropertyException;

/**
 * The Class HDBViewDefinitionModel.
 */
public class HDBViewDefinitionModel {

  /** The public prop. */
  private boolean publicProp = true;
  
  /** The schema. */
  private String schema;
  
  /** The query. */
  private String query;
  
  /** The depends on. */
  private List<String> dependsOn;
  
  /** The depends on table. */
  private List<String> dependsOnTable;
  
  /** The depends on view. */
  private List<String> dependsOnView;

  /**
   * Checks if is public.
   *
   * @return true, if is public
   */
  public boolean isPublic() {
    return publicProp;
  }

  /**
   * Sets the public.
   *
   * @param publicProp the new public
   */
  public void setPublic(boolean publicProp) {
    this.publicProp = publicProp;
  }

  /**
   * Gets the schema.
   *
   * @return the schema
   */
  public String getSchema() {
    return schema;
  }

  /**
   * Sets the schema.
   *
   * @param schema the new schema
   */
  public void setSchema(String schema) {
    this.schema = schema;
  }

  /**
   * Gets the query.
   *
   * @return the query
   */
  public String getQuery() {
    return query;
  }

  /**
   * Sets the query.
   *
   * @param query the new query
   */
  public void setQuery(String query) {
    this.query = query;
  }

  /**
   * Gets the depends on.
   *
   * @return the depends on
   */
  public List<String> getDependsOn() {
    return dependsOn;
  }

  /**
   * Sets the depends on.
   *
   * @param dependsOn the new depends on
   */
  public void setDependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
  }

  /**
   * Gets the depends on table.
   *
   * @return the depends on table
   */
  public List<String> getDependsOnTable() {
    return dependsOnTable;
  }

  /**
   * Sets the depends on table.
   *
   * @param dependsOnTable the new depends on table
   */
  public void setDependsOnTable(List<String> dependsOnTable) {
    this.dependsOnTable = dependsOnTable;
  }

  /**
   * Gets the depends on view.
   *
   * @return the depends on view
   */
  public List<String> getDependsOnView() {
    return dependsOnView;
  }

  /**
   * Sets the depends on view.
   *
   * @param dependsOnView the new depends on view
   */
  public void setDependsOnView(List<String> dependsOnView) {
    this.dependsOnView = dependsOnView;
  }

  /**
   * Equals.
   *
   * @param o the o
   * @return true, if successful
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    HDBViewDefinitionModel that = (HDBViewDefinitionModel) o;
    return publicProp == that.publicProp && Objects.equals(schema, that.schema) && Objects.equals(query, that.query) && Objects.equals(
        dependsOn, that.dependsOn) && Objects.equals(dependsOnTable, that.dependsOnTable) && Objects.equals(dependsOnView,
        that.dependsOnView);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(publicProp, schema, query, dependsOn, dependsOnTable, dependsOnView);
  }

  /**
   * Check for all mandatory fields presence.
   */
  public void checkForAllMandatoryFieldsPresence() {
    checkPresence(schema, "schema");
    checkPresence(query, "query");
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
      throw new HDBViewMissingPropertyException(String.format("Missing mandatory field %s!", fieldName));
    }
  }
}
