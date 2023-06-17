/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.parser.hdbsequence.models;

import com.codbex.kronos.parser.hdbsequence.utils.HDBSequenceConstants;
import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The Class HDBSequenceModel.
 */
public class HDBSequenceModel {

  /** The missing props. */
  private Set<String> missingProps = new HashSet<>();

  /** The schema. */
  @SerializedName(value = HDBSequenceConstants.SCHEMA_PROPERTY)
  private String schema;

  /** The increment by. */
  @SerializedName(value = HDBSequenceConstants.INCREMENT_BY_PROPERTY)
  private Integer incrementBy = HDBSequenceConstants.INCREMENT_BY_DEFAULT_VALUE;

  /** The start with. */
  @SerializedName(value = HDBSequenceConstants.START_WITH_PROPERTY)
  private Integer startWith = HDBSequenceConstants.START_WITH_DEFAULT_VALUE;

  /** The max value. */
  @SerializedName(value = HDBSequenceConstants.MAXVALUE_PROPERTY)
  private Integer maxValue;

  /** The no max value. */
  @SerializedName(value = HDBSequenceConstants.NOMAXVALUE_PROPERTY)
  private Boolean noMaxValue;

  /** The min value. */
  @SerializedName(value = HDBSequenceConstants.MINVALUE_PROPERTY)
  private Integer minValue = HDBSequenceConstants.MIN_DEFAULT_VALUE;

  /** The no min value. */
  @SerializedName(value = HDBSequenceConstants.NOMINVALUE_PROPERTY)
  private Boolean noMinValue;

  /** The cycles. */
  @SerializedName(value = HDBSequenceConstants.CYCLES_PROPERTY)
  private Boolean cycles;

  /** The reset by. */
  @SerializedName(value = HDBSequenceConstants.RESET_BY_PROPERTY)
  private String resetBy;

  /** The depends on table. */
  @SerializedName(value = HDBSequenceConstants.DEPENDS_ON_TABLE_PROPERTY)
  private String dependsOnTable;

  /** The depends on view. */
  @SerializedName(value = HDBSequenceConstants.DEPENDS_ON_VIEW_PROPERTY)
  private String dependsOnView;

  /** The public prop. */
  @SerializedName(value = HDBSequenceConstants.PUBLIC_PROPERTY)
  private Boolean publicProp = HDBSequenceConstants.PUBLIC_DEFAULT_VALUE;

  /**
   * Gets the missing props.
   *
   * @return the missing props
   */
  public Set<String> getMissingProps() {
    return missingProps;
  }

  /**
   * Sets the missing props.
   *
   * @param missingProps the new missing props
   */
  public void setMissingProps(Set<String> missingProps) {
    this.missingProps = missingProps;
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
   * Gets the increment by.
   *
   * @return the increment by
   */
  public Integer getIncrementBy() {
    return incrementBy;
  }

  /**
   * Sets the increment by.
   *
   * @param incrementBy the new increment by
   */
  public void setIncrementBy(Integer incrementBy) {
    this.incrementBy = incrementBy;
  }

  /**
   * Gets the start with.
   *
   * @return the start with
   */
  public Integer getStartWith() {
    return startWith;
  }

  /**
   * Sets the start with.
   *
   * @param startWith the new start with
   */
  public void setStartWith(Integer startWith) {
    this.startWith = startWith;
  }

  /**
   * Gets the max value.
   *
   * @return the max value
   */
  public Integer getMaxValue() {
    return maxValue;
  }

  /**
   * Sets the max value.
   *
   * @param maxValue the new max value
   */
  public void setMaxValue(Integer maxValue) {
    this.maxValue = maxValue;
  }

  /**
   * Gets the no max value.
   *
   * @return the no max value
   */
  public Boolean getNoMaxValue() {
    return noMaxValue;
  }

  /**
   * Sets the no max value.
   *
   * @param noMaxValue the new no max value
   */
  public void setNoMaxValue(Boolean noMaxValue) {
    this.noMaxValue = noMaxValue;
  }

  /**
   * Gets the min value.
   *
   * @return the min value
   */
  public Integer getMinValue() {
    return minValue;
  }

  /**
   * Sets the min value.
   *
   * @param minValue the new min value
   */
  public void setMinValue(Integer minValue) {
    this.minValue = minValue;
  }

  /**
   * Gets the no min value.
   *
   * @return the no min value
   */
  public Boolean getNoMinValue() {
    return noMinValue;
  }

  /**
   * Sets the no min value.
   *
   * @param noMinValue the new no min value
   */
  public void setNoMinValue(Boolean noMinValue) {
    this.noMinValue = noMinValue;
  }

  /**
   * Gets the cycles.
   *
   * @return the cycles
   */
  public Boolean getCycles() {
    return cycles;
  }

  /**
   * Sets the cycles.
   *
   * @param cycles the new cycles
   */
  public void setCycles(Boolean cycles) {
    this.cycles = cycles;
  }

  /**
   * Gets the reset by.
   *
   * @return the reset by
   */
  public String getResetBy() {
    return resetBy;
  }

  /**
   * Sets the reset by.
   *
   * @param resetBy the new reset by
   */
  public void setResetBy(String resetBy) {
    this.resetBy = resetBy;
  }

  /**
   * Gets the depends on table.
   *
   * @return the depends on table
   */
  public String getDependsOnTable() {
    return dependsOnTable;
  }

  /**
   * Sets the depends on table.
   *
   * @param dependsOnTable the new depends on table
   */
  public void setDependsOnTable(String dependsOnTable) {
    this.dependsOnTable = dependsOnTable;
  }

  /**
   * Gets the depends on view.
   *
   * @return the depends on view
   */
  public String getDependsOnView() {
    return dependsOnView;
  }

  /**
   * Sets the depends on view.
   *
   * @param dependsOnView the new depends on view
   */
  public void setDependsOnView(String dependsOnView) {
    this.dependsOnView = dependsOnView;
  }

  /**
   * Gets the public.
   *
   * @return the public
   */
  public Boolean getPublic() { return this.publicProp; }

  /**
   * Sets the public.
   *
   * @param publicProp the new public
   */
  public void setPublic(Boolean publicProp) {
    this.publicProp = publicProp;
  }

  /**
   * Equals.
   *
   * @param o the o
   * @return true, if successful
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HDBSequenceModel that = (HDBSequenceModel) o;
    return Objects.equals(missingProps, that.missingProps) && Objects.equals(schema, that.schema) && Objects.equals(incrementBy,
        that.incrementBy) && Objects.equals(startWith, that.startWith) && Objects.equals(maxValue, that.maxValue) && Objects.equals(
        noMaxValue, that.noMaxValue) && Objects.equals(minValue, that.minValue) && Objects.equals(noMinValue, that.noMinValue)
        && Objects.equals(cycles, that.cycles) && Objects.equals(resetBy, that.resetBy) && Objects.equals(dependsOnTable,
        that.dependsOnTable) && Objects.equals(dependsOnView, that.dependsOnView) && Objects.equals(publicProp, that.publicProp);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(missingProps, schema, incrementBy, startWith, maxValue, noMaxValue, minValue, noMinValue, cycles, resetBy,
        dependsOnTable, dependsOnView, publicProp);
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
      this.missingProps.add(fieldName);
    }
  }

  /**
   * Checks for mandatory fields missing.
   *
   * @return true, if successful
   */
  public boolean hasMandatoryFieldsMissing() {
    checkPresence(this.schema, HDBSequenceConstants.SCHEMA_PROPERTY);
    return (this.missingProps.size() > 0) ? true : false;
  }
}
