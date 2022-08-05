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
package com.codbex.kronos.synchronizer.cleaners;

import com.codbex.kronos.exceptions.XSJSLibSynchronizerDBCleanerSQLException;
import com.codbex.kronos.synchronizer.XSJSLibSynchronizer;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class XSJSLibSynchronizerDBCleaner implements XSJSLibSynchronizerCleaner {
  private final DataSource dataSource;

  public XSJSLibSynchronizerDBCleaner(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void cleanup(String registryPath) {
    try (PreparedStatement deleteStatement =
        dataSource.getConnection().prepareStatement(
            "DELETE FROM \""
                + XSJSLibSynchronizer.XSJSLIB_SYNCHRONIZER_STATE_TABLE_NAME
                + "\" WHERE \"LOCATION\" LIKE ?")
    ) {
      deleteStatement.setString(1, registryPath + "%");
      deleteStatement.executeUpdate();
    } catch (SQLException e) {
      throw new XSJSLibSynchronizerDBCleanerSQLException("Could not cleanup xsjslib synchronizer entries. ", e);
    }
  }
}
