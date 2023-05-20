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

/**
 * The Class XSJSLibSynchronizerDBCleaner.
 */
public class XSJSLibSynchronizerDBCleaner implements XSJSLibSynchronizerCleaner {
  
  /** The data source. */
  private final DataSource dataSource;

  /**
   * Instantiates a new XSJS lib synchronizer DB cleaner.
   *
   * @param dataSource the data source
   */
  public XSJSLibSynchronizerDBCleaner(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  /**
   * Cleanup.
   *
   * @param registryPath the registry path
   */
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
