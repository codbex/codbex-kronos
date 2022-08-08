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
package com.codbex.kronos.xssecurestore.ds.api;

/**
 * The Class SecureStoreException.
 */
public class SecureStoreException extends Exception {

  /**
   * Instantiates a new secure store exception.
   */
  public SecureStoreException() {
    super();
  }

  /**
   * Instantiates a new secure store exception.
   *
   * @param message the message
   */
  public SecureStoreException(String message) {
    super(message);
  }

  /**
   * Instantiates a new secure store exception.
   *
   * @param cause the cause
   */
  public SecureStoreException(Throwable cause) {
    super(cause);
  }
}
