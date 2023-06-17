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
package com.codbex.kronos.parser.hana.models;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class FromClauseDefinitionModel.
 */
public class FromClauseDefinitionModel {

  /** The join clauses. */
  private List<JoinClauseDefinitionModel> joinClauses;

  /** The table references. */
  private List<TableReferenceModel> tableReferences = new ArrayList<>();

  /**
   * Instantiates a new from clause definition model.
   */
  public FromClauseDefinitionModel() {
    this.joinClauses = new ArrayList<>();
  }

  /**
   * Gets the join clauses.
   *
   * @return the join clauses
   */
  public List<JoinClauseDefinitionModel> getJoinClauses() {
    return joinClauses;
  }

  /**
   * Adds the join clause.
   *
   * @param joinClause the join clause
   */
  public void addJoinClause(JoinClauseDefinitionModel joinClause) {
    this.joinClauses.add(joinClause);
  }

  /**
   * Gets the table references.
   *
   * @return the table references
   */
  public List<TableReferenceModel> getTableReferences() {
    return tableReferences;
  }

  /**
   * Adds the table reference.
   *
   * @param tableReference the table reference
   */
  public void addTableReference(TableReferenceModel tableReference) {
    this.tableReferences.add(tableReference);
  }
}
