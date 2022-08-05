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
package com.codbex.kronos.hdb.ds.model.hdbtable;

/**
 * The Data Structure Table Constraint Check Model.
 */
public class DataStructureHDBTableConstraintCheckModel extends DataStructureHDBTableConstraintModel {

  private String expression;

  /**
   * Gets the expression.
   *
   * @return the expression
   */
  public String getExpression() {
    return expression;
  }

  /**
   * Sets the expression.
   *
   * @param expression the new expression
   */
  public void setExpression(String expression) {
    this.expression = expression;
  }

}
