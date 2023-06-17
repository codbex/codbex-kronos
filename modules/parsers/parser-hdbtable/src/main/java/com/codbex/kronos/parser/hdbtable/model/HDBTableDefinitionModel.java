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

import com.codbex.kronos.parser.hdbtable.constants.HDBTablePropertiesConstants;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableMissingPropertyException;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Class HDBTableDefinitionModel.
 */
public class HDBTableDefinitionModel {
    
    /** The schema name. */
    private String schemaName;
    
    /** The table type. */
    private String tableType;

    /** The pk columns. */
    @SerializedName(value = HDBTablePropertiesConstants.HDBTABLE_PROPERTY_PKCOLUMNS)
    private List<String> pkColumns = new ArrayList<>();

    /** The index type. */
    private String indexType;
    
    /** The columns. */
    private List<HDBTableColumnsModel> columns = new ArrayList<>();
    
    /** The indexes. */
    private List<HDBTableIndexesModel> indexes = new ArrayList<>();
    
    /** The description. */
    private String description;
    
    /** The public prop. */
    private Boolean publicProp;
    
    /** The logging type. */
    private String loggingType;
    
    /** The temporary. */
    private Boolean temporary;

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
   * Gets the table type.
   *
   * @return the table type
   */
  public String getTableType() {
    return tableType;
  }

  /**
   * Sets the table type.
   *
   * @param tableType the new table type
   */
  public void setTableType(String tableType) {
    this.tableType = tableType;
  }

  /**
   * Gets the pk columns.
   *
   * @return the pk columns
   */
  public List<String> getPkColumns() {
    return pkColumns;
  }

  /**
   * Sets the pk columns.
   *
   * @param pkColumns the new pk columns
   */
  public void setPkColumns(List<String> pkColumns) {
    this.pkColumns = pkColumns;
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
   * Gets the columns.
   *
   * @return the columns
   */
  public List<HDBTableColumnsModel> getColumns() {
    return columns;
  }

  /**
   * Sets the columns.
   *
   * @param columns the new columns
   */
  public void setColumns(List<HDBTableColumnsModel> columns) {
    this.columns = columns;
  }

  /**
   * Gets the indexes.
   *
   * @return the indexes
   */
  public List<HDBTableIndexesModel> getIndexes() {
    return indexes;
  }

  /**
   * Sets the indexes.
   *
   * @param indexes the new indexes
   */
  public void setIndexes(List<HDBTableIndexesModel> indexes) {
    this.indexes = indexes;
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description the new description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Checks if is public.
   *
   * @return the boolean
   */
  public Boolean isPublic() {
    return publicProp;
  }

  /**
   * Sets the public.
   *
   * @param publicProp the new public
   */
  public void setPublic(Boolean publicProp) {
    this.publicProp = publicProp;
  }

  /**
   * Gets the logging type.
   *
   * @return the logging type
   */
  public String getLoggingType() {
    return loggingType;
  }

  /**
   * Sets the logging type.
   *
   * @param loggingType the new logging type
   */
  public void setLoggingType(String loggingType) {
    this.loggingType = loggingType;
  }

  /**
   * Gets the temporary.
   *
   * @return the temporary
   */
  public Boolean getTemporary() {
    return temporary;
  }

  /**
   * Sets the temporary.
   *
   * @param temporary the new temporary
   */
  public void setTemporary(Boolean temporary) {
    this.temporary = temporary;
  }

  /**
   * Check for all mandatory fields presence.
   */
  public void checkForAllMandatoryFieldsPresence() {
        checkPresence(schemaName, "schemaName");
        checkPresence(columns, "columns");
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
