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

import com.codbex.kronos.xsaccess.ds.model.privilege.PrivilegeDefinition;

/**
 * The Interface IPrivilegeCoreService.
 */
public interface IPrivilegeCoreService {

  /** The file extension privilege. */
  String FILE_EXTENSION_PRIVILEGE = ".xsprivileges";

  /** The privileges table name. */
  String PRIVILEGES_TABLE_NAME = "KRONOS_PRIVILEGES";

  /**
   * Creates the privilege.
   *
   * @param name the name
   * @param description the description
   * @return the privilege definition
   * @throws PrivilegeException the privilege exception
   */
  PrivilegeDefinition createPrivilege(String name, String description) throws PrivilegeException;

  /**
   * Update privileges.
   *
   * @param name the name
   * @param description the description
   * @return the privilege definition
   * @throws PrivilegeException the privilege exception
   */
  PrivilegeDefinition updatePrivileges(String name, String description) throws PrivilegeException;

  /**
   * Gets the privileges.
   *
   * @return the privileges
   * @throws PrivilegeException the privilege exception
   */
  List<PrivilegeDefinition> getPrivileges() throws PrivilegeException;

  /**
   * Removes the privilege by name.
   *
   * @param name the name
   * @throws PrivilegeException the privilege exception
   */
  void removePrivilegeByName(String name) throws PrivilegeException;

  /**
   * Gets the privilege by name.
   *
   * @param name the name
   * @return the privilege by name
   * @throws PrivilegeException the privilege exception
   */
  PrivilegeDefinition getPrivilegeByName(String name) throws PrivilegeException;

  /**
   * Privilege exists.
   *
   * @param name the name
   * @return true, if successful
   * @throws PrivilegeException the privilege exception
   */
  boolean privilegeExists(String name) throws PrivilegeException;
}