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
package com.codbex.kronos.parser.hdbti.models;

import com.codbex.kronos.parser.hdbti.exception.HDBTIMissingPropertyException;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Class HDBTIImportConfigModel.
 */
public class HDBTIImportConfigModel {

  /** The table name. */
  @SerializedName("table")
  private String tableName;

  /** The schema name. */
  @SerializedName("schema")
  private String schemaName;

  /** The file name. */
  @SerializedName("file")
  private String fileName;

  /** The header. */
  @SerializedName("header")
  private Boolean header;

  /** The use header names. */
  @SerializedName("useHeaderNames")
  private Boolean useHeaderNames;

  /** The delim field. */
  @SerializedName("delimField")
  private String delimField;

  /** The delim enclosing. */
  @SerializedName("delimEnclosing")
  private String delimEnclosing;

  /** The distinguish empty from null. */
  @SerializedName("distinguishEmptyFromNull")
  private Boolean distinguishEmptyFromNull;

  /** The keys. */
  @SerializedName("keys")
  private List<Pair> keys = new ArrayList<>();

  /**
   * Gets the table name.
   *
   * @return the table name
   */
  public String getTableName() {
    return tableName;
  }

  /**
   * Sets the table name.
   *
   * @param tableName the new table name
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  /**
   * Gets the schema name.
   *
   * @return the schema name
   */
  public String getSchemaName() {
    return schemaName;
  }

  /**
   * Sets the schema name.
   *
   * @param schemaName the new schema name
   */
  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }

  /**
   * Gets the file name.
   *
   * @return the file name
   */
  public String getFileName() {
    return fileName;
  }

  /**
   * Sets the file name.
   *
   * @param fileName the new file name
   */
  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  /**
   * Gets the header.
   *
   * @return the header
   */
  public Boolean getHeader() {
    return header;
  }

  /**
   * Sets the header.
   *
   * @param header the new header
   */
  public void setHeader(Boolean header) {
    this.header = header;
  }

  /**
   * Gets the use header names.
   *
   * @return the use header names
   */
  public Boolean getUseHeaderNames() {
    return useHeaderNames;
  }

  /**
   * Sets the use header names.
   *
   * @param useHeaderNames the new use header names
   */
  public void setUseHeaderNames(Boolean useHeaderNames) {
    this.useHeaderNames = useHeaderNames;
  }

  /**
   * Gets the delim field.
   *
   * @return the delim field
   */
  public String getDelimField() {
    return delimField;
  }

  /**
   * Sets the delim field.
   *
   * @param delimField the new delim field
   */
  public void setDelimField(String delimField) {
    this.delimField = delimField;
  }

  /**
   * Gets the delim enclosing.
   *
   * @return the delim enclosing
   */
  public String getDelimEnclosing() {
    return delimEnclosing;
  }

  /**
   * Sets the delim enclosing.
   *
   * @param delimEnclosing the new delim enclosing
   */
  public void setDelimEnclosing(String delimEnclosing) {
    this.delimEnclosing = delimEnclosing;
  }

  /**
   * Gets the distinguish empty from null.
   *
   * @return the distinguish empty from null
   */
  public Boolean getDistinguishEmptyFromNull() {
    return distinguishEmptyFromNull;
  }

  /**
   * Sets the distinguish empty from null.
   *
   * @param distinguishEmptyFromNull the new distinguish empty from null
   */
  public void setDistinguishEmptyFromNull(Boolean distinguishEmptyFromNull) {
    this.distinguishEmptyFromNull = distinguishEmptyFromNull;
  }

  /**
   * Gets the keys.
   *
   * @return the keys
   */
  public List<Pair> getKeys() {
    return keys;
  }

  /**
   * Sets the keys.
   *
   * @param keys the new keys
   */
  public void setKeys(List<Pair> keys) {
    this.keys = keys;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("\n{\n");
    if (delimEnclosing != null) {
      result.append(delimEnclosing.equals("\"") ? "\tdelimEnclosing=\"\\" + delimEnclosing + "\";\n"
          : "\tdelimEnclosing=\"" + delimEnclosing + "\";\n");
    }
    if (schemaName != null) {
      result.append("\tschema = \"" + schemaName + "\";\n");
    }
    if (distinguishEmptyFromNull != null) {
      result.append("\tdistinguishEmptyFromNull = " + distinguishEmptyFromNull + ";\n");
    }
    if (header != null) {
      result.append("\theader = " + header + ";\n");
    }
    if (tableName != null) {
      result.append("\ttable = \"" + tableName + "\";\n");
    }
    if (useHeaderNames != null) {
      result.append("\tuseHeaderNames = " + useHeaderNames + ";\n");
    }
    if (delimField != null) {
      result.append("\tdelimField = \"" + delimField + "\";\n");
    }
    if (keys.size() > 0) {
      result.append("\tkeys = " + keys.toString() + ";\n");
    }
    if (fileName != null) {
      result.append("\tfile = \"" + fileName + "\";\n");
    }
    result.append("}");
    return result.toString();
  }

  /**
   * The Class Pair.
   */
  public static class Pair {

    /** The column. */
    private String column;
    
    /** The values. */
    private ArrayList<String> values = new ArrayList<>();

    /**
     * Instantiates a new pair.
     *
     * @param column the column
     * @param values the values
     */
    public Pair(String column, ArrayList<String> values) {
      this.column = column;
      this.values = values;
    }

    /**
     * Gets the column.
     *
     * @return the column
     */
    public String getColumn() {
      return column;
    }

    /**
     * Sets the column.
     *
     * @param column the new column
     */
    public void setColumn(String column) {
      this.column = column;
    }

    /**
     * Gets the values.
     *
     * @return the values
     */
    public ArrayList<String> getValues() {
      return values;
    }

    /**
     * Sets the values.
     *
     * @param values the new values
     */
    public void setValues(ArrayList<String> values) {
      this.values = values;
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
      Pair pair = (Pair) o;
      return Objects.equals(column, pair.column) && Objects.equals(values, pair.values);
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
      return Objects.hash(column, values);
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
      StringBuilder result = new StringBuilder();
      for (String value : values) {
        result.append("\"").append(column).append("\":").append("\"").append(value).append("\",");
      }
      return result.substring(0, result.length() - 1);
    }
  }

  /**
   * Check for all mandatory fields presence.
   */
  public void checkForAllMandatoryFieldsPresence() {
    if (tableName != null && !tableName.contains("::")) {
      checkPresence(schemaName, "schemaName");
    }
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
      throw new HDBTIMissingPropertyException(String.format("Missing mandatory field %s!", fieldName));
    }
    if ((field instanceof ArrayList) && ((ArrayList) field).isEmpty()) {
      throw new HDBTIMissingPropertyException(String.format("Missing mandatory field %s!", fieldName));
    }
  }
}
