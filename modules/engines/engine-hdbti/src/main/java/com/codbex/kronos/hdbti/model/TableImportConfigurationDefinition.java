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
package com.codbex.kronos.hdbti.model;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * The Class TableImportConfigurationDefinition.
 */
public class TableImportConfigurationDefinition {

  /** The table. */
  private String table;
  
  /** The schema. */
  private String schema;
  
  /** The file. */
  private String file;
  
  /** The hdbti file name. */
  private String hdbtiFileName;
  
  /** The header. */
  private Boolean header = Boolean.FALSE;
  
  /** The use header names. */
  private Boolean useHeaderNames = Boolean.FALSE;
  
  /** The delim field. */
  private String delimField;
  
  /** The delim enclosing. */
  private String delimEnclosing;
  
  /** The distinguish empty from null. */
  private Boolean distinguishEmptyFromNull = Boolean.TRUE;
  
  /** The keys as map. */
  private Map<String, ArrayList<String>> keysAsMap;

  /**
   * Gets the table.
   *
   * @return the table
   */
  public String getTable() {
    return table;
  }

  /**
   * Sets the table.
   *
   * @param table the new table
   */
  public void setTable(String table) {
    this.table = table;
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
   * Gets the file.
   *
   * @return the file
   */
  public String getFile() {
    return file;
  }

  /**
   * Sets the file.
   *
   * @param file the new file
   */
  public void setFile(String file) {
    this.file = file;
  }

  /**
   * Gets the hdbti file name.
   *
   * @return the hdbti file name
   */
  public String getHdbtiFileName() {
    return hdbtiFileName;
  }

  /**
   * Sets the hdbti file name.
   *
   * @param hdbtiFileName the new hdbti file name
   */
  public void setHdbtiFileName(String hdbtiFileName) {
    this.hdbtiFileName = hdbtiFileName;
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
   * Gets the keys as map.
   *
   * @return the keys as map
   */
  public Map<String, ArrayList<String>> getKeysAsMap() {
    return keysAsMap;
  }

  /**
   * Sets the keys as map.
   *
   * @param keysAsMap the keys as map
   */
  public void setKeysAsMap(Map<String, ArrayList<String>> keysAsMap) {
    this.keysAsMap = keysAsMap;
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
    TableImportConfigurationDefinition that = (TableImportConfigurationDefinition) o;
    return table.equals(that.table) && schema.equals(that.schema) && file.equals(that.file);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(table, schema, file);
  }
}
