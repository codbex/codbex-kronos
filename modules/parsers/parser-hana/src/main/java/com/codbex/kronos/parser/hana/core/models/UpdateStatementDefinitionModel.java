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
package com.codbex.kronos.parser.hana.core.models;

public class UpdateStatementDefinitionModel extends TableReferenceModel{

  private String rawContent;

  private FromClauseDefinitionModel fromClause;
  private WhereClauseDefinitionModel whereClause;
  private UpdateSetClauseDefinitionModel updateSetClause;

  public String getRawContent() {
    return rawContent;
  }

  public void setRawContent(String rawContent) {
    this.rawContent = rawContent;
  }

  public FromClauseDefinitionModel getFromClause() {
    return fromClause;
  }

  public void setFromClause(FromClauseDefinitionModel fromClause) {
    this.fromClause = fromClause;
  }

  public WhereClauseDefinitionModel getWhereClause() {
    return whereClause;
  }

  public void setWhereClause(WhereClauseDefinitionModel whereClause) {
    this.whereClause = whereClause;
  }

  public UpdateSetClauseDefinitionModel getUpdateSetClause() {
    return updateSetClause;
  }

  public void setUpdateSetClause(UpdateSetClauseDefinitionModel updateSetClause) {
    this.updateSetClause = updateSetClause;
  }
}
