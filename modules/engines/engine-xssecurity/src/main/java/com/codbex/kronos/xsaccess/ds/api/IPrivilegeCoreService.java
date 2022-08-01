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

import com.codbex.kronos.xsaccess.ds.model.privilege.PrivilegeDefinition;
import java.util.List;

public interface IPrivilegeCoreService {

  String FILE_EXTENSION_PRIVILEGE = ".xsprivileges";

  String PRIVILEGES_TABLE_NAME = "KRONOS_PRIVILEGES";

  PrivilegeDefinition createPrivilege(String name, String description) throws PrivilegeException;

  PrivilegeDefinition updatePrivileges(String name, String description) throws PrivilegeException;

  List<PrivilegeDefinition> getPrivileges() throws PrivilegeException;

  void removePrivilegeByName(String name) throws PrivilegeException;

  PrivilegeDefinition getPrivilegeByName(String name) throws PrivilegeException;

  boolean privilegeExists(String name) throws PrivilegeException;
}