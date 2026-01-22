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
import { user as securityUser } from '@aerokit/sdk/security';

export const authType = securityUser.getAuthType();
export const language = securityUser.getLanguage();

export function getUsername() {
    return securityUser.getName();
}

export function hasAppPrivilege(privilegeName) {
    return securityUser.isInRole(privilegeName);
}

export function hasSystemPrivilege(privilegeName) {
    return securityUser.isInRole(privilegeName);
}

export function assertSystemPrivilege(privilegeName) {
    var hasPrivilege = securityUser.isInRole(privilegeName)
    if (!hasPrivilege) {
        throw new NoSuchPrivilegeException(privilegeName);
    }
}

export function assertAppPrivilege(privilegeName) {
    var hasPrivilege = securityUser.isInRole(privilegeName)
    if (!hasPrivilege) {
        throw new NoSuchPrivilegeException(privilegeName);
    }
}

export function getTimeout() {
  return securityUser.getTimeout()
}

export function getSecurityToken() {
  return securityUser.getSecurityToken()
}

export function getInvocationCount() {
  return securityUser.getInvocationCount()
}

function NoSuchPrivilegeException(privilegeToCheck) {
    this.privilege = privilegeToCheck;
}