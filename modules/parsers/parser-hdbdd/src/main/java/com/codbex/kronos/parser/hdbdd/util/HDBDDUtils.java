/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.parser.hdbdd.util;

/**
 * The Class HdbddUtils.
 */
public class HDBDDUtils {

  /**
   * Process escaped symbol name.
   *
   * @param symbolName the symbol name
   * @return the string
   */
  public static String processEscapedSymbolName(String symbolName) {
      if (symbolName.charAt(0) == '"') {
        return symbolName.substring(1, symbolName.length() - 1);
      }

      return symbolName;
  }
}
