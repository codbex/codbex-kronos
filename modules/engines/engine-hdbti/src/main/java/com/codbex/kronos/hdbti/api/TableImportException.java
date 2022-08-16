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
package com.codbex.kronos.hdbti.api;

/**
 * The Class TableImportException.
 */
public class TableImportException extends Exception {

  /**
   * Instantiates a new table import exception.
   */
  public TableImportException() {
  }

  /**
   * Instantiates a new table import exception.
   *
   * @param message the message
   */
  public TableImportException(String message) {
    super(message);
  }

  /**
   * Instantiates a new table import exception.
   *
   * @param message the message
   * @param cause the cause
   */
  public TableImportException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Instantiates a new table import exception.
   *
   * @param cause the cause
   */
  public TableImportException(Throwable cause) {
    super(cause);
  }
}