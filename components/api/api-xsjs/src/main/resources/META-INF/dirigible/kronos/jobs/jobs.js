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
var registry = require("sdk/platform/registry");

exports.Job = function Job(constructJob) {
  if (!constructJob.uri) throw "URI not specified";

  this.schedules = com.codbex.kronos.xsjob.ds.facade.JobFacade.newJob(registry.getText(constructJob.uri));

  this.activate = function () {
    com.codbex.kronos.xsjob.ds.facade.JobFacade.activate(this.schedules);
  }

  this.deactivate = function () {
    com.codbex.kronos.xsjob.ds.facade.JobFacade.deactivate(this.schedules);
  }

  this.configure = function (config) {
    if(!config.start_time) throw "Start time must be provided";

    com.codbex.kronos.xsjob.ds.facade.JobFacade.configure(this.schedules, config.status, parseDate(config.start_time), config.end_time ? parseDate(config.end_time) : null);
  }

  this.getConfiguration = function () {
    let configuration = com.codbex.kronos.xsjob.ds.facade.JobFacade.getConfiguration(this.schedules[0]);
    let startAtTimestamp = configuration.getStartAt();
    let endAtTimestamp = configuration.getEndAt();

    return {
      status: com.codbex.kronos.xsjob.ds.facade.JobFacade.isActive(this.schedules[0]),
      start_time: startAtTimestamp ? new Date(startAtTimestamp.getTime()) : null,
      end_time: endAtTimestamp ? new Date(endAtTimestamp.getTime()) : null
    };
  }
}

function parseDate(dateObj) {
  if (dateObj instanceof Date) {
    return new java.sql.Timestamp(dateObj.getTime());
  } else {
    var timestamp = Date.parse(dateObj.value);

    if (!timestamp) throw "Invalid date format";

    return new java.sql.Timestamp(timestamp);
  }
}
