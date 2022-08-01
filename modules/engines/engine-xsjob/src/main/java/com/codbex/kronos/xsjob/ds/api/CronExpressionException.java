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
package com.codbex.kronos.xsjob.ds.api;

import java.text.ParseException;

public class CronExpressionException extends ParseException {

  /**
   * Constructs a ParseException with the specified detail message and
   * offset.
   * A detail message is a String that describes this particular exception.
   *
   * @param s           the detail message
   * @param errorOffset the position where the error is found while parsing.
   */
  public CronExpressionException(String s, int errorOffset) {
    super(s, errorOffset);
  }
}
