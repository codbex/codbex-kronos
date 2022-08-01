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

import com.google.gson.annotations.SerializedName;
import com.codbex.kronos.parser.hdbtable.constants.HdbtablePropertiesConstants;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableMissingPropertyException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HDBTableDefinitionModel {
    private String schemaName;
    private String tableType;

    @SerializedName(value = HdbtablePropertiesConstants.HDBTABLE_PROPERTY_PKCOLUMNS)
    private List<String> pkColumns = new ArrayList<>();

    private String indexType;
    private List<HDBTableColumnsModel> columns = new ArrayList<>();
    private List<HDBTableIndexesModel> indexes = new ArrayList<>();
    private String description;
    private Boolean publicProp;
    private String loggingType;
    private Boolean temporary;

  public String getSchemaName() {
    return schemaName;
  }

  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }

  public String getTableType() {
    return tableType;
  }

  public void setTableType(String tableType) {
    this.tableType = tableType;
  }

  public List<String> getPkColumns() {
    return pkColumns;
  }

  public void setPkColumns(List<String> pkColumns) {
    this.pkColumns = pkColumns;
  }

  public String getIndexType() {
    return indexType;
  }

  public void setIndexType(String indexType) {
    this.indexType = indexType;
  }

  public List<HDBTableColumnsModel> getColumns() {
    return columns;
  }

  public void setColumns(List<HDBTableColumnsModel> columns) {
    this.columns = columns;
  }

  public List<HDBTableIndexesModel> getIndexes() {
    return indexes;
  }

  public void setIndexes(List<HDBTableIndexesModel> indexes) {
    this.indexes = indexes;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean isPublic() {
    return publicProp;
  }

  public void setPublic(Boolean publicProp) {
    this.publicProp = publicProp;
  }

  public String getLoggingType() {
    return loggingType;
  }

  public void setLoggingType(String loggingType) {
    this.loggingType = loggingType;
  }

  public Boolean getTemporary() {
    return temporary;
  }

  public void setTemporary(Boolean temporary) {
    this.temporary = temporary;
  }

  public void checkForAllMandatoryFieldsPresence() {
        checkPresence(schemaName, "schemaName");
        checkPresence(columns, "columns");
    }

    private <T> void checkPresence(T field, String fieldName) {
        if (Objects.isNull(field)) {
            throw new HDBTableMissingPropertyException(String.format("Missing mandatory field %s!", fieldName));
        }
        if ((field instanceof ArrayList) && ((ArrayList) field).isEmpty()) {
            throw new HDBTableMissingPropertyException(String.format("Missing mandatory field %s!", fieldName));
        }
    }
}
