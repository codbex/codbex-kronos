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
var securityUser = require('sdk/security/user');

exports.authType = securityUser.getAuthType();
exports.language = securityUser.getLanguage();

exports.getUsername = function () {
    return securityUser.getName();
}

exports.hasAppPrivilege = function (privilegeName) {
    return securityUser.isInRole(privilegeName);
}

exports.hasSystemPrivilege = function (privilegeName) {
    return securityUser.isInRole(privilegeName);
}

exports.assertSystemPrivilege = function (privilegeName) {
    var hasPrivilege = securityUser.isInRole(privilegeName)
    if (!hasPrivilege) {
        throw new NoSuchPrivilegeException(privilegeName);
    }
}

exports.assertAppPrivilege = function (privilegeName) {
    var hasPrivilege = securityUser.isInRole(privilegeName)
    if (!hasPrivilege) {
        throw new NoSuchPrivilegeException(privilegeName);
    }
}

exports.getTimeout = function () {
  return securityUser.getTimeout()
}

exports.getSecurityToken = function () {
  return securityUser.getSecurityToken()
}

exports.getInvocationCount = function () {
  return securityUser.getInvocationCount()
}

function NoSuchPrivilegeException(privilegeToCheck) {
    this.privilege = privilegeToCheck;
}