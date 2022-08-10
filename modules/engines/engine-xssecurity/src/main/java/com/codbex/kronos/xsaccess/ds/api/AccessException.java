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
package com.codbex.kronos.xsaccess.ds.api;

/**
 * The Class AccessException.
 */
public class AccessException extends Exception {

  /**
   * Instantiates a new access exception.
   */
  public AccessException() {
    super();
  }

  /**
   * Instantiates a new access exception.
   *
   * @param cause the cause
   */
  public AccessException(Throwable cause) {
    super(cause);
  }
}