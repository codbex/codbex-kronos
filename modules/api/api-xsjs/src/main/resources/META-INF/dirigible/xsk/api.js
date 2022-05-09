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

try {
  $.db = require('xsk/db/db');
  $.hdb = require('xsk/hdb/hdb');
  $.net = require('xsk/net/net');
  $.import = require("xsk/import/import").import;
  $.trace = require('xsk/trace/trace');
  $.util = require('xsk/util/util');
  $.jobs = require('xsk/jobs/jobs');
  $.web = require('xsk/web/web');
  $.session = require('xsk/session/session');
  $.security = require('xsk/security/security');
  $.request = new $.web.WebRequest();
  $.response = new $.web.WebResponse();
} catch (e) {
  // $.trace.warning("Caught exception. Api.js is being used by xsk job.")
  console.error(e.message);
}

module.exports = $;
