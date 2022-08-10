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

import com.codbex.kronos.xsaccess.ds.model.access.AccessDefinition;

/**
 * The Interface IAccessCoreService.
 */
public interface IAccessCoreService {

  /** The file extension access. */
  String FILE_EXTENSION_ACCESS = ".xsaccess";

  /**
   * Creates the access definition.
   *
   * @param path the path
   * @param authenticationMethodAsList the authentication method as list
   * @param hash the hash
   * @param exposed the exposed
   * @param authorizationRolesAsList the authorization roles as list
   * @return the access definition
   * @throws AccessException the access exception
   */
  AccessDefinition createAccessDefinition(String path, List<String> authenticationMethodAsList, String hash, boolean exposed,
      List<String> authorizationRolesAsList) throws AccessException;

  /**
   * Update access definition.
   *
   * @param path the path
   * @param authenticationMethodAsList the authentication method as list
   * @param hash the hash
   * @param exposed the exposed
   * @param authorizationRolesAsList the authorization roles as list
   * @return the access definition
   * @throws AccessException the access exception
   */
  AccessDefinition updateAccessDefinition(String path, List<String> authenticationMethodAsList, String hash, boolean exposed,
      List<String> authorizationRolesAsList) throws AccessException;

  /**
   * Gets the access definition.
   *
   * @param id the id
   * @return the access definition
   * @throws AccessException the access exception
   */
  AccessDefinition getAccessDefinition(String id) throws AccessException;

  /**
   * Gets the access definitions.
   *
   * @return the access definitions
   * @throws AccessException the access exception
   */
  List<AccessDefinition> getAccessDefinitions() throws AccessException;

  /**
   * Removes the access definition.
   *
   * @param path the path
   * @throws AccessException the access exception
   */
  void removeAccessDefinition(String path) throws AccessException;

  /**
   * Exists access definition.
   *
   * @param path the path
   * @return true, if successful
   * @throws AccessException the access exception
   */
  boolean existsAccessDefinition(String path) throws AccessException;

  /**
   * Parses the access artifact.
   *
   * @param json the json
   * @return the access definition
   */
  AccessDefinition parseAccessArtifact(byte[] json);

  /**
   * Clear cache.
   */
  void clearCache();
}