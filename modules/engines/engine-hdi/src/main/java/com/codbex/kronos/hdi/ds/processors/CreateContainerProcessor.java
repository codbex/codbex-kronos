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
package com.codbex.kronos.hdi.ds.processors;

import java.sql.Connection;
import java.sql.SQLException;

public class CreateContainerProcessor extends HDIAbstractProcessor {

  public final void execute(Connection connection, String group, String container) throws SQLException {
    executeQuery(connection, "CALL _SYS_DI#" + group + ".CREATE_CONTAINER(?, _SYS_DI.T_NO_PARAMETERS, ?, ?, ?);", container);
  }

}
