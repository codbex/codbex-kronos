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
package com.codbex.kronos.hdb.ds.processors.hdbstructure;

import com.codbex.kronos.hdb.ds.service.manager.IXSKDataStructureManager;
import com.codbex.kronos.hdb.ds.service.manager.XSKSynonymManagerService;
import com.codbex.kronos.utils.XSKHDBUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class HDBSynonymRemover {

  private final IXSKDataStructureManager synonymManagerService;

  public HDBSynonymRemover(XSKSynonymManagerService synonymManagerService) {
    this.synonymManagerService = synonymManagerService;
  }

  public void removePublicSynonym(Connection connection, String targetObjectSchema, String targetObjectName) throws SQLException {
    XSKHDBUtils.dropPublicSynonymForArtifact(synonymManagerService, targetObjectSchema, targetObjectName,
        connection);
  }

}
