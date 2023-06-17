/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.hdb.processors;

import java.sql.Connection;
import java.sql.SQLException;

import com.codbex.kronos.engine.hdb.domain.HDBTable;

/**
 * The Class HDBTableAlterProcessor.
 */
public class HDBTableAlterProcessor extends AbstractHDBProcessor<HDBTable> {

  /**
   * Execute the corresponding statement.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean execute(Connection connection, HDBTable tableModel) throws SQLException {
    HDBTableAlterHandler handler = createTableAlterHandler(connection, tableModel);
    handler.addColumns(connection);
    handler.removeColumns(connection);
    handler.updateColumns(connection);
    handler.rebuildIndeces(connection);
    handler.ensurePrimaryKeyIsUnchanged(connection);
    return true;
  }

  /**
   * Creates the table alter handler.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @return the table alter handler
   * @throws SQLException the SQL exception
   */
  public HDBTableAlterHandler createTableAlterHandler(Connection connection, HDBTable tableModel)
      throws SQLException {
    return new HDBTableAlterHandler(connection, tableModel);
  }
}
