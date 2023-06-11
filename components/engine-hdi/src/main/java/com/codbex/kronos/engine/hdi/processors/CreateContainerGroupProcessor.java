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
package com.codbex.kronos.engine.hdi.processors;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The Class CreateContainerGroupProcessor.
 */
public class CreateContainerGroupProcessor extends HDIAbstractProcessor {

  /**
   * Execute.
   *
   * @param connection the connection
   * @param group the group
   * @throws SQLException the SQL exception
   */
  public final void execute(Connection connection, String group) throws SQLException {
    executeQuery(connection, "CALL _SYS_DI.CREATE_CONTAINER_GROUP(?, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?);", group);
  }
}
