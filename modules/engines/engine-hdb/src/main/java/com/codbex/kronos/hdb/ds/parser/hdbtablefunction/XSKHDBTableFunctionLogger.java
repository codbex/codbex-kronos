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
package com.codbex.kronos.hdb.ds.parser.hdbtablefunction;

import com.codbex.kronos.utils.XSKCommonsConstants;
import com.codbex.kronos.utils.XSKCommonsUtils;

public class XSKHDBTableFunctionLogger {

  public void logError(String parsedFileLocation, String problemsFacadeMessage, String exceptionMessage) {
    XSKCommonsUtils.logCustomErrors(parsedFileLocation,
        XSKCommonsConstants.PARSER_ERROR,
        "",
        "",
        exceptionMessage,
        problemsFacadeMessage,
        XSKCommonsConstants.HDB_TABLE_FUNCTION_PARSER,
        XSKCommonsConstants.MODULE_PARSERS,
        XSKCommonsConstants.SOURCE_PUBLISH_REQUEST,
        XSKCommonsConstants.PROGRAM_XSK);
  }
}
