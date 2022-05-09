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
package com.codbex.kronos.xsaccess.ds.api;

import java.util.List;

import com.codbex.kronos.xsaccess.ds.model.privilege.XSKPrivilegeDefinition;

public interface IXSKPrivilegeCoreService {

  String XSK_FILE_EXTENSION_PRIVILEGE = ".xsprivileges";

  String XSK_PRIVILEGES_TABLE_NAME = "XSK_PRIVILEGES";

  XSKPrivilegeDefinition createXSKPrivilege(String name, String description) throws XSKPrivilegeException;

  XSKPrivilegeDefinition updateXSKPrivileges(String name, String description) throws XSKPrivilegeException;

  List<XSKPrivilegeDefinition> getXSKPrivileges() throws XSKPrivilegeException;

  void removeXSKPrivilegeByName(String name) throws XSKPrivilegeException;

  XSKPrivilegeDefinition getXSKPrivilegeByName(String name) throws XSKPrivilegeException;

  boolean xskPrivilegeExists(String name) throws XSKPrivilegeException;
}