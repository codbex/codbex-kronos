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
package com.codbex.kronos.hdb.ds.processors.table;

import java.sql.Connection;
import java.sql.SQLException;

import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;

/**
 * The Class TableAlterProcessor.
 */
public class TableAlterProcessor extends AbstractHDBProcessor<DataStructureHDBTableModel> {

  /**
   * Execute the corresponding statement.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean execute(Connection connection, DataStructureHDBTableModel tableModel) throws SQLException {
    TableAlterHandler handler = createTableAlterHandler(connection, tableModel);
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
  public TableAlterHandler createTableAlterHandler(Connection connection, DataStructureHDBTableModel tableModel)
      throws SQLException {
    return new TableAlterHandler(connection, tableModel);
  }
}
