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
package com.codbex.kronos.xsaccess.ds.model.access;

/**
 * The Class Authentication.
 */
public class Authentication {

  /** The method. */
  private String method;

  /**
   * Instantiates a new authentication.
   */
  public Authentication() {
  }

  /**
   * Gets the method.
   *
   * @return the method
   */
  public String getMethod() {
    return method;
  }

  /**
   * Sets the method.
   *
   * @param method the new method
   */
  public void setMethod(String method) {
    this.method = method;
  }
}