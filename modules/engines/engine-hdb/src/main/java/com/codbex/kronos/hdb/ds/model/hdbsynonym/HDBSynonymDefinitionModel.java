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

import com.codbex.kronos.hdb.ds.exceptions.HDBSynonymMissingPropertyException;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * The Class HDBSynonymDefinitionModel.
 */
public class HDBSynonymDefinitionModel {

  /** The target. */
  private Target target;
  
  /** The synonym schema. */
  @SerializedName(value = "schema")
  String synonymSchema;

  /**
   * Gets the target.
   *
   * @return the target
   */
  public Target getTarget() {
    return target;
  }

  /**
   * Sets the target.
   *
   * @param target the new target
   */
  public void setTarget(Target target) {
    this.target = target;
  }

  /**
   * Gets the synonym schema.
   *
   * @return the synonym schema
   */
  public String getSynonymSchema() {
    return synonymSchema;
  }

  /**
   * Sets the synonym schema.
   *
   * @param synonymSchema the new synonym schema
   */
  public void setSynonymSchema(String synonymSchema) {
    this.synonymSchema = synonymSchema;
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
    HDBSynonymDefinitionModel that = (HDBSynonymDefinitionModel) o;
    return Objects.equals(target, that.target) && Objects.equals(synonymSchema, that.synonymSchema);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(target, synonymSchema);
  }

  /**
   * The Class Target.
   */
  public static class Target {

    /** The object. */
    String object;
    
    /** The schema. */
    String schema;

    /**
     * Instantiates a new target.
     */
    public Target() {}

    /**
     * Instantiates a new target.
     *
     * @param object the object
     * @param schema the schema
     */
    public Target(String object, String schema) {
      this.object = object;
      this.schema = schema;
    }

    /**
     * Gets the object.
     *
     * @return the object
     */
    public String getObject() {
      return object;
    }

    /**
     * Sets the object.
     *
     * @param object the new object
     */
    public void setObject(String object) {
      this.object = object;
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

  }

  /**
   * Check for all mandatory fields presence.
   */
  public void checkForAllMandatoryFieldsPresence() {
    checkPresence(target, "target");
    checkPresence(this.getTarget().getObject(), "object");
    checkPresence(this.getTarget().getSchema(), "schema");
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
      throw new HDBSynonymMissingPropertyException(String.format("Missing mandatory field %s!", fieldName));
    }
  }
}
