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
var jobFunctionName = __context.get("xsk-job-function");
var jobDescription  = __context.get("jobDescription");

function appendJobException(message){
  return "[JobExecution]" + "[" + jobDescription + "]" + "[" + jobFunctionName + "]" + "[Exception] " + message;
}

exports.debug = function(message) {
  if(this.isDebugEnabled()) {
    console.debug((jobFunctionName !== 0 || jobDescription !==0) ? appendJobException(message) : message);
  }
}

exports.error = function(message) {
  if(this.isErrorEnabled()) {
    console.error((jobFunctionName !== 0 || jobDescription !==0) ? appendJobException(message) : message);
  }
}

exports.fatal = function(message) {
  if(this.isFatalEnabled()) {
    console.error((jobFunctionName !== 0 || jobDescription !==0) ? appendJobException(message) : message);
  }
}

exports.info = function(message) {
  if(this.isInfoEnabled()) {
    console.info((jobFunctionName !== 0 || jobDescription !==0) ? appendJobException(message) : message);
  }
}

exports.warning = function(message) {
  if(this.isWarningEnabled()) {
    console.warn((jobFunctionName !== 0 || jobDescription !==0) ? appendJobException(message) : message);
  }
}

exports.isDebugEnabled = function() {
  return com.codbex.kronos.api.TraceFacade.isDebugEnabled();
}

exports.isErrorEnabled = function() {
  return com.codbex.kronos.api.TraceFacade.isErrorEnabled();
}

exports.isFatalEnabled = function() {
  return com.codbex.kronos.api.TraceFacade.isFatalEnabled();
}

exports.isInfoEnabled = function() {
  return com.codbex.kronos.api.TraceFacade.isInfoEnabled();
}

exports.isWarningEnabled = function() {
  return com.codbex.kronos.api.TraceFacade.isWarningEnabled();
}
