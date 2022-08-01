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
/*
 * HANA XS Classic Bridge
 */

var $ = {};

const API_MODULES = {
    db: "kronos/db/db",
    hdb: "kronos/hdb/hdb",
    net: "kronos/net/net",
    trace: "kronos/trace/trace",
    util: "kronos/util/util",
    jobs: "kronos/jobs/jobs",
    web: "kronos/web/web",
    session: "kronos/session/session",
    security: "kronos/security/security"
};

for (next in API_MODULES) {
    loadApi(next, API_MODULES[next]);
}

try {
    $.import = require("kronos/import/import").import;
    $.request = new $.web.WebRequest();
    $.response = new $.web.WebResponse();
} catch (e) {
    console.error(`Error occurred while loading API [import], [request] or [response]: ` + e.message);
}

function loadApi(api, module) {
    try {
        $[api] = require(module);
    } catch (e) {
        // $.trace.warning("Caught exception. Api.js is being used by kronos job.")
        console.error(`Error occurred while loading API [${api}] from module: [${module}]: ` + e.message);
    }
}

module.exports = $;
