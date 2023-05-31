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
package com.codbex.kronos.engine.hdb.parser;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The Class HDBSynonymRemover.
 */
public class HDBSynonymRemover {

  /**
   * Removes the public synonym.
   *
   * @param connection the connection
   * @param targetObjectSchema the target object schema
   * @param targetObjectName the target object name
   * @throws SQLException the SQL exception
   */
  public void removePublicSynonym(Connection connection, String targetObjectSchema, String targetObjectName) throws SQLException {
    HDBUtils.dropPublicSynonymForArtifact(targetObjectSchema, targetObjectName, connection);
  }

}
