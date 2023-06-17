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
package com.codbex.kronos.parser.hdbtable.model;

import java.util.Objects;

import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableMissingPropertyException;

/**
 * The Class HDBTableColumnsModel.
 */
public class HDBTableColumnsModel {

  /** The name. */
  private String name;
  
  /** The sql type. */
  private String sqlType;
  
  /** The nullable. */
  private boolean nullable;
  
  /** The unique. */
  private boolean unique;
  
  /** The length. */
  private String length;
  
  /** The comment. */
  private String comment;
  
  /** The default value. */
  private String defaultValue;
  
  /** The precision. */
  private String precision;
  
  /** The scale. */
  private String scale;

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the sql type.
   *
   * @return the sql type
   */
  public String getSqlType() {
    return sqlType;
  }

  /**
   * Sets the sql type.
   *
   * @param sqlType the new sql type
   */
  public void setSqlType(String sqlType) {
    this.sqlType = sqlType;
  }

  /**
   * Checks if is nullable.
   *
   * @return true, if is nullable
   */
  public boolean isNullable() {
    return nullable;
  }

  /**
   * Sets the nullable.
   *
   * @param nullable the new nullable
   */
  public void setNullable(boolean nullable) {
    this.nullable = nullable;
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
   * Gets the length.
   *
   * @return the length
   */
  public String getLength() {
    return length;
  }

  /**
   * Sets the length.
   *
   * @param length the new length
   */
  public void setLength(String length) {
    this.length = length;
  }

  /**
   * Gets the comment.
   *
   * @return the comment
   */
  public String getComment() {
    return comment;
  }

  /**
   * Sets the comment.
   *
   * @param comment the new comment
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  /**
   * Gets the default value.
   *
   * @return the default value
   */
  public String getDefaultValue() {
    return defaultValue;
  }

  /**
   * Sets the default value.
   *
   * @param defaultValue the new default value
   */
  public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }

  /**
   * Gets the precision.
   *
   * @return the precision
   */
  public String getPrecision() {
    return precision;
  }

  /**
   * Sets the precision.
   *
   * @param precision the new precision
   */
  public void setPrecision(String precision) {
    this.precision = precision;
  }

  /**
   * Gets the scale.
   *
   * @return the scale
   */
  public String getScale() {
    return scale;
  }

  /**
   * Sets the scale.
   *
   * @param scale the new scale
   */
  public void setScale(String scale) {
    this.scale = scale;
  }

  /**
   * Check for all mandatory column fields presence.
   */
  public void checkForAllMandatoryColumnFieldsPresence() {
    checkPresence(name, "name");
    checkPresence(sqlType, "sqlType");
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
  }
}
