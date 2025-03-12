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
var jobFunctionName = __context.get("kronos-job-function");
var jobDescription = __context.get("jobDescription");

function appendJobException(message) {
  return "[JobExecution]" + "[" + jobDescription + "]" + "[" + jobFunctionName + "]" + "[Exception] " + message;
}

export function debug(message) {
  if (this.isDebugEnabled()) {
    console.debug((jobFunctionName !== 0 || jobDescription !== 0) ? appendJobException(message) : message);
  }
}

export function error(message) {
  if (this.isErrorEnabled()) {
    console.error((jobFunctionName !== 0 || jobDescription !== 0) ? appendJobException(message) : message);
  }
}

export function fatal(message) {
  if (this.isFatalEnabled()) {
    console.error((jobFunctionName !== 0 || jobDescription !== 0) ? appendJobException(message) : message);
  }
}

export function info(message) {
  if (this.isInfoEnabled()) {
    console.info((jobFunctionName !== 0 || jobDescription !== 0) ? appendJobException(message) : message);
  }
}

export function warning(message) {
  if (this.isWarningEnabled()) {
    console.warn((jobFunctionName !== 0 || jobDescription !== 0) ? appendJobException(message) : message);
  }
}

export function isDebugEnabled() {
  return com.codbex.kronos.api.TraceFacade.isDebugEnabled();
}

export function isErrorEnabled() {
  return com.codbex.kronos.api.TraceFacade.isErrorEnabled();
}

export function isFatalEnabled() {
  return com.codbex.kronos.api.TraceFacade.isFatalEnabled();
}

export function isInfoEnabled() {
  return com.codbex.kronos.api.TraceFacade.isInfoEnabled();
}

export function isWarningEnabled() {
  return com.codbex.kronos.api.TraceFacade.isWarningEnabled();
}
