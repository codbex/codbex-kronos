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
package com.codbex.kronos.parser.hdbti.exception;

/**
 * The Class DuplicateFieldNameException.
 */
public class DuplicateFieldNameException extends RuntimeException {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = -6378311157487445495L;

  /**
   * Instantiates a new duplicate field name exception.
   *
   * @param message the message
   */
  public DuplicateFieldNameException(String message) {
    super(message);
  }
}
