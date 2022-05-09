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
package com.codbex.kronos.parser.hdbdd.symbols.view;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;

public class JoinSymbol extends Symbol {

  String joinType = null;
  String joinArtifactName = null;
  String joinTableAlias = null;
  String joinFields = null;

  public String getJoinType() {
    return joinType;
  }

  public void setJoinType(String joinType) {
    this.joinType = joinType;
  }

  public String getJoinArtifactName() {
    return joinArtifactName;
  }

  public void setJoinArtifactName(String joinArtifactName) {
    this.joinArtifactName = joinArtifactName;
  }

  public String getJoinTableAlias() {
    return joinTableAlias;
  }

  public void setJoinTableAlias(String joinTableAlias) {
    this.joinTableAlias = joinTableAlias;
  }

  public String getJoinFields() {
    return joinFields;
  }

  public void setJoinFields(String joinFields) {
    this.joinFields = joinFields;
  }
}