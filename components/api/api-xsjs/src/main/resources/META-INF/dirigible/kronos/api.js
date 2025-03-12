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

import { response } from 'sdk/http';
import * as db from 'kronos/db/db';
import * as hdb from 'kronos/hdb/hdb';
import * as net from 'kronos/net/net';

const API_MODULES = {
    trace: "kronos/trace/trace",
    util: "kronos/util/util",
    jobs: "kronos/jobs/jobs",
    web: "kronos/web/web",
    session: "kronos/session/session",
    security: "kronos/security/security"
};

export const $ = {
    response: response,
    db: db,
    hdb: hdb,
    net: net,
};

// try {
//     $.import = require("kronos/import/import").import;
//     $.request = new $.web.WebRequest();
//     $.response = new $.web.WebResponse();
// } catch (e) {
//     console.error(`Error occurred while loading API [import], [request] or [response]: ` + e.message);
// }