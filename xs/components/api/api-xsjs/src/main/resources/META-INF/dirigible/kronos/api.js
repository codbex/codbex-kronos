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

import * as db from 'kronos/db/db';
import * as hdb from 'kronos/hdb/hdb';
import * as net from 'kronos/net/net';
import * as trace from 'kronos/trace/trace';
import * as util from 'kronos/util/util';
import * as jobs from 'kronos/jobs/jobs';
import * as web from 'kronos/web/web';
import * as session from 'kronos/session/session';
import * as security from 'kronos/security/security';
import * as importUtils from 'kronos/import/import';

export const $ = {
    db: db,
    hdb: hdb,
    net: net,
    trace: trace,
    util: util,
    jobs: jobs,
    web: web,
    session: session,
    security: security,
    request: new web.WebRequest(),
    response: new web.WebResponse(),
    import: importUtils.importModule,
};
