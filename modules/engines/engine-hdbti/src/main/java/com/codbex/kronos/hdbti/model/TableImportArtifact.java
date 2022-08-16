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

import java.util.List;

import com.codbex.kronos.hdb.ds.model.DataStructureModel;

/**
 * The Class TableImportArtifact.
 */
public class TableImportArtifact extends DataStructureModel {

  /** The import configuration definition. */
  private List<TableImportConfigurationDefinition> importConfigurationDefinition;
  
  /** The table import to csv relations. */
  private List<TableImportToCsvRelation> tableImportToCsvRelations;

  /**
   * Gets the import configuration definition.
   *
   * @return the import configuration definition
   */
  public List<TableImportConfigurationDefinition> getImportConfigurationDefinition() {
    return importConfigurationDefinition;
  }

  /**
   * Sets the import configuration definition.
   *
   * @param importConfigurationDefinition the new import configuration definition
   */
  public void setImportConfigurationDefinition(List<TableImportConfigurationDefinition> importConfigurationDefinition) {
    this.importConfigurationDefinition = importConfigurationDefinition;
  }

  /**
   * Gets the table import to csv relations.
   *
   * @return the table import to csv relations
   */
  public List<TableImportToCsvRelation> getTableImportToCsvRelations() {
    return tableImportToCsvRelations;
  }

  /**
   * Sets the table import to csv relations.
   *
   * @param tableImportToCsvRelations the new table import to csv relations
   */
  public void setTableImportToCsvRelations(List<TableImportToCsvRelation> tableImportToCsvRelations) {
    this.tableImportToCsvRelations = tableImportToCsvRelations;
  }
}
