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

import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

public class HDBTableFunctionLogger {

  public void logError(String parsedFileLocation, String problemsFacadeMessage, String exceptionMessage) {
    CommonsUtils.logCustomErrors(parsedFileLocation,
        CommonsConstants.PARSER_ERROR,
        "",//line
        "",//column
        exceptionMessage,
        problemsFacadeMessage,
        CommonsConstants.HDB_TABLE_FUNCTION_PARSER,
        CommonsConstants.MODULE_PARSERS,
        CommonsConstants.SOURCE_PUBLISH_REQUEST,
        CommonsConstants.PROGRAM_KRONOS);
  }
}
