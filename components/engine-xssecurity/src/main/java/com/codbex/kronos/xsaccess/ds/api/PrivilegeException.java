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
 * The Class PrivilegeException.
 */
public class PrivilegeException extends Exception {

  /**
   * Instantiates a new privilege exception.
   */
  public PrivilegeException() {
    super();
  }

  /**
   * Instantiates a new privilege exception.
   *
   * @param message the message
   */
  public PrivilegeException(String message) {
    super(message);
  }

  /**
   * Instantiates a new privilege exception.
   *
   * @param cause the cause
   */
  public PrivilegeException(Throwable cause) {
    super(cause);
  }
}