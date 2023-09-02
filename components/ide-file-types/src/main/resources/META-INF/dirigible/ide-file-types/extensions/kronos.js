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
exports.getFileTypes = function () {
    return {
        ".xsjs": "javascript",
        ".xsjslib": "javascript",
        ".hdbsynonym": "json",
        ".hdbpublicsynonym": "json",
        ".hdi": "json",
        ".hdiconfig": "json",
        ".hdbcalculationview": "xml",
        ".hdbtable": "sql",
        ".hdbview": "sql",
        ".hdbprocedure": "sql",
        ".hdbtablefunction": "sql",
        ".hdbschema": "sql",
        ".hdbdd": "sql",
        ".hdbflowgraph": "xml",
        ".xsodata": "sql",
        ".xsaccess": "json",
        ".xsprivileges": "json",
        ".csvim": "json",
        ".calculationview": "xml"
    }
};
