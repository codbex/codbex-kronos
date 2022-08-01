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
package com.codbex.kronos.synchronizer;

import com.codbex.kronos.exceptions.XSJSLibArtefactCleanerSQLException;
import org.eclipse.dirigible.commons.config.StaticObjects;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class XSJSLibSynchronizerArtefactsCleaner {

  private final DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);

  public void cleanup(String targetLocation) {
    try (PreparedStatement deleteStatement =
        dataSource.getConnection().prepareStatement(
            "DELETE FROM \""
                + XSJSLibSynchronizer.XSJSLIB_SYNCHRONIZER_STATE_TABLE_NAME
                + "\" WHERE \"LOCATION\" LIKE ?")
    ) {
      deleteStatement.setString(1, targetLocation + "%");
      deleteStatement.executeUpdate();
    } catch (SQLException e) {
      throw new XSJSLibArtefactCleanerSQLException("Could not cleanup xsjslib synchronizer entries. ", e);
    }
  }
}
