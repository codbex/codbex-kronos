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
package com.codbex.kronos.engine.xsjs.exceptions;

/**
 * The Class XSJSLibExportsGenerationSourceNotFoundException.
 */
public class XSJSLibExportsGenerationSourceNotFoundException extends RuntimeException {

  /**
   * Instantiates a new XSJS lib exports generation source not found exception.
   *
   * @param message the message
   */
  public XSJSLibExportsGenerationSourceNotFoundException(String message) {
    super(message);
  }

  /**
   * Instantiates a new XSJS lib exports generation source not found exception.
   *
   * @param exception the exception
   */
  public XSJSLibExportsGenerationSourceNotFoundException(Exception exception) {
    super(exception);
  }
}
