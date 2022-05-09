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

import com.codbex.kronos.xsaccess.ds.model.access.XSKAccessDefinition;

public interface IXSKAccessCoreService {

  String XSK_FILE_EXTENSION_ACCESS = ".xsaccess";

  XSKAccessDefinition createXSKAccessDefinition(String path, List<String> authenticationMethodAsList, String hash, boolean exposed,
      List<String> authorizationRolesAsList) throws XSKAccessException;

  XSKAccessDefinition updateXSKAccessDefinition(String path, List<String> authenticationMethodAsList, String hash, boolean exposed,
      List<String> authorizationRolesAsList) throws XSKAccessException;

  XSKAccessDefinition getXSKAccessDefinition(String id) throws XSKAccessException;

  List<XSKAccessDefinition> getAccessXSKDefinitions() throws XSKAccessException;

  void removeXSKAccessDefinition(String path) throws XSKAccessException;

  boolean existsXSKAccessDefinition(String path) throws XSKAccessException;

  XSKAccessDefinition parseXSAccessArtifact(byte[] json);

  void clearCache();
}