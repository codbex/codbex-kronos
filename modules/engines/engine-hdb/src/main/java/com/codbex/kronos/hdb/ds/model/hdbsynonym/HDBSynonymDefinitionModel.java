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
package com.codbex.kronos.hdb.ds.model.hdbsynonym;

import com.google.gson.annotations.SerializedName;
import com.codbex.kronos.hdb.ds.exceptions.HDBSynonymMissingPropertyException;
import java.util.Objects;

public class HDBSynonymDefinitionModel {

  private Target target;
  @SerializedName(value = "schema")
  String synonymSchema;

  public Target getTarget() {
    return target;
  }

  public void setTarget(Target target) {
    this.target = target;
  }

  public String getSynonymSchema() {
    return synonymSchema;
  }

  public void setSynonymSchema(String synonymSchema) {
    this.synonymSchema = synonymSchema;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    HDBSynonymDefinitionModel that = (HDBSynonymDefinitionModel) o;
    return Objects.equals(target, that.target) && Objects.equals(synonymSchema, that.synonymSchema);
  }

  @Override
  public int hashCode() {
    return Objects.hash(target, synonymSchema);
  }

  public static class Target {

    String object;
    String schema;

    public Target() {}

    public Target(String object, String schema) {
      this.object = object;
      this.schema = schema;
    }

    public String getObject() {
      return object;
    }

    public void setObject(String object) {
      this.object = object;
    }

    public String getSchema() {
      return schema;
    }

    public void setSchema(String schema) {
      this.schema = schema;
    }

  }

  public void checkForAllMandatoryFieldsPresence() {
    checkPresence(target, "target");
    checkPresence(this.getTarget().getObject(), "object");
    checkPresence(this.getTarget().getSchema(), "schema");
  }

  private <T> void checkPresence(T field, String fieldName) {
    if (Objects.isNull(field)) {
      throw new HDBSynonymMissingPropertyException(String.format("Missing mandatory field %s!", fieldName));
    }
  }
}
