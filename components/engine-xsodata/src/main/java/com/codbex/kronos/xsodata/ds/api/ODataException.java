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
package com.codbex.kronos.xsodata.ds.api;

/**
 * The Data Structures Exception.
 */
public class ODataException extends Exception {

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 5800180600419241248L;

  /**
   * Instantiates a new data structures exception.
   */
  public ODataException() {
    super();
  }

  /**
   * Instantiates a new data structures exception.
   *
   * @param message            the message
   * @param cause              the cause
   * @param enableSuppression  the enable suppression
   * @param writableStackTrace the writable stack trace
   */
  public ODataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  /**
   * Instantiates a new data structures exception.
   *
   * @param message the message
   * @param cause   the cause
   */
  public ODataException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Instantiates a new data structures exception.
   *
   * @param message the message
   */
  public ODataException(String message) {
    super(message);
  }

  /**
   * Instantiates a new data structures exception.
   *
   * @param cause the cause
   */
  public ODataException(Throwable cause) {
    super(cause);
  }

}
