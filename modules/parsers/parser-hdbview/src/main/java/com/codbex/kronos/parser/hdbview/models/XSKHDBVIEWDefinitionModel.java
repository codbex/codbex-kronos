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

import com.codbex.kronos.parser.hdbview.exceptions.XSKHDBViewMissingPropertyException;

public class XSKHDBVIEWDefinitionModel {

  private boolean publicProp = true;
  private String schema;
  private String query;
  private List<String> dependsOn;
  private List<String> dependsOnTable;
  private List<String> dependsOnView;

  public boolean isPublic() {
    return publicProp;
  }

  public void setPublic(boolean publicProp) {
    this.publicProp = publicProp;
  }

  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public List<String> getDependsOn() {
    return dependsOn;
  }

  public void setDependsOn(List<String> dependsOn) {
    this.dependsOn = dependsOn;
  }

  public List<String> getDependsOnTable() {
    return dependsOnTable;
  }

  public void setDependsOnTable(List<String> dependsOnTable) {
    this.dependsOnTable = dependsOnTable;
  }

  public List<String> getDependsOnView() {
    return dependsOnView;
  }

  public void setDependsOnView(List<String> dependsOnView) {
    this.dependsOnView = dependsOnView;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    XSKHDBVIEWDefinitionModel that = (XSKHDBVIEWDefinitionModel) o;
    return publicProp == that.publicProp && Objects.equals(schema, that.schema) && Objects.equals(query, that.query) && Objects.equals(
        dependsOn, that.dependsOn) && Objects.equals(dependsOnTable, that.dependsOnTable) && Objects.equals(dependsOnView,
        that.dependsOnView);
  }

  @Override
  public int hashCode() {
    return Objects.hash(publicProp, schema, query, dependsOn, dependsOnTable, dependsOnView);
  }

  public void checkForAllMandatoryFieldsPresence() {
    checkPresence(schema, "schema");
    checkPresence(query, "query");
  }

  private <T> void checkPresence(T field, String fieldName) {
    if (Objects.isNull(field)) {
      throw new XSKHDBViewMissingPropertyException(String.format("Missing mandatory field %s!", fieldName));
    }
  }
}
