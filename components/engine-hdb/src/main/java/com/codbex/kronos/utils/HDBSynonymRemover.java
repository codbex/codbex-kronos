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
package com.codbex.kronos.utils;

import com.codbex.kronos.hdb.ds.service.manager.IDataStructureManager;
import com.codbex.kronos.hdb.ds.service.manager.HDBSynonymManagerService;
import com.codbex.kronos.utils.HDBUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The Class HDBSynonymRemover.
 */
public class HDBSynonymRemover {

  /** The synonym manager service. */
  private final IDataStructureManager synonymManagerService;

  /**
   * Instantiates a new HDB synonym remover.
   *
   * @param synonymManagerService the synonym manager service
   */
  public HDBSynonymRemover(HDBSynonymManagerService synonymManagerService) {
    this.synonymManagerService = synonymManagerService;
  }

  /**
   * Removes the public synonym.
   *
   * @param connection the connection
   * @param targetObjectSchema the target object schema
   * @param targetObjectName the target object name
   * @throws SQLException the SQL exception
   */
  public void removePublicSynonym(Connection connection, String targetObjectSchema, String targetObjectName) throws SQLException {
    HDBUtils.dropPublicSynonymForArtifact(synonymManagerService, targetObjectSchema, targetObjectName,
        connection);
  }

}
