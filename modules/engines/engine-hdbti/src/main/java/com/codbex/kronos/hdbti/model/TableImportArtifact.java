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

public class TableImportArtifact extends DataStructureModel {

  private List<TableImportConfigurationDefinition> importConfigurationDefinition;
  private List<TableImportToCsvRelation> tableImportToCsvRelations;

  public List<TableImportConfigurationDefinition> getImportConfigurationDefinition() {
    return importConfigurationDefinition;
  }

  public void setImportConfigurationDefinition(List<TableImportConfigurationDefinition> importConfigurationDefinition) {
    this.importConfigurationDefinition = importConfigurationDefinition;
  }

  public List<TableImportToCsvRelation> getTableImportToCsvRelations() {
    return tableImportToCsvRelations;
  }

  public void setTableImportToCsvRelations(List<TableImportToCsvRelation> tableImportToCsvRelations) {
    this.tableImportToCsvRelations = tableImportToCsvRelations;
  }
}
