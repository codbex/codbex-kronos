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

/**
 * The Class JoinSymbol.
 */
public class JoinSymbol extends Symbol {

  /** The join type. */
  String joinType = null;
  
  /** The join artifact name. */
  String joinArtifactName = null;
  
  /** The join table alias. */
  String joinTableAlias = null;
  
  /** The join fields. */
  String joinFields = null;

  /**
   * Gets the join type.
   *
   * @return the join type
   */
  public String getJoinType() {
    return joinType;
  }

  /**
   * Sets the join type.
   *
   * @param joinType the new join type
   */
  public void setJoinType(String joinType) {
    this.joinType = joinType;
  }

  /**
   * Gets the join artifact name.
   *
   * @return the join artifact name
   */
  public String getJoinArtifactName() {
    return joinArtifactName;
  }

  /**
   * Sets the join artifact name.
   *
   * @param joinArtifactName the new join artifact name
   */
  public void setJoinArtifactName(String joinArtifactName) {
    this.joinArtifactName = joinArtifactName;
  }

  /**
   * Gets the join table alias.
   *
   * @return the join table alias
   */
  public String getJoinTableAlias() {
    return joinTableAlias;
  }

  /**
   * Sets the join table alias.
   *
   * @param joinTableAlias the new join table alias
   */
  public void setJoinTableAlias(String joinTableAlias) {
    this.joinTableAlias = joinTableAlias;
  }

  /**
   * Gets the join fields.
   *
   * @return the join fields
   */
  public String getJoinFields() {
    return joinFields;
  }

  /**
   * Sets the join fields.
   *
   * @param joinFields the new join fields
   */
  public void setJoinFields(String joinFields) {
    this.joinFields = joinFields;
  }
}