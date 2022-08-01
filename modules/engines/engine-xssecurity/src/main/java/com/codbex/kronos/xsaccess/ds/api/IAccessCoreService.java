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

import com.codbex.kronos.xsaccess.ds.model.access.AccessDefinition;
import java.util.List;

public interface IAccessCoreService {

  String FILE_EXTENSION_ACCESS = ".xsaccess";

  AccessDefinition createAccessDefinition(String path, List<String> authenticationMethodAsList, String hash, boolean exposed,
      List<String> authorizationRolesAsList) throws AccessException;

  AccessDefinition updateAccessDefinition(String path, List<String> authenticationMethodAsList, String hash, boolean exposed,
      List<String> authorizationRolesAsList) throws AccessException;

  AccessDefinition getAccessDefinition(String id) throws AccessException;

  List<AccessDefinition> getAccessDefinitions() throws AccessException;

  void removeAccessDefinition(String path) throws AccessException;

  boolean existsAccessDefinition(String path) throws AccessException;

  AccessDefinition parseAccessArtifact(byte[] json);

  void clearCache();
}