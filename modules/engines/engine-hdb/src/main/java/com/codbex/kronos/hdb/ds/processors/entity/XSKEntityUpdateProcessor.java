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
package com.codbex.kronos.hdb.ds.processors.entity;

import com.codbex.kronos.hdb.ds.api.IXSKHdbProcessor;
import com.codbex.kronos.hdb.ds.model.hdbdd.XSKDataStructureEntityModel;
import com.codbex.kronos.hdb.ds.processors.AbstractXSKProcessor;
import com.codbex.kronos.utils.XSKHDBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XSKEntityUpdateProcessor extends AbstractXSKProcessor<XSKDataStructureEntityModel> {

  private static final Logger logger = LoggerFactory.getLogger(XSKEntityUpdateProcessor.class);

  private IXSKHdbProcessor<XSKDataStructureEntityModel> xskEntityDropProcessor = new XSKEntityDropProcessor();
  private IXSKHdbProcessor<XSKDataStructureEntityModel> xskEntityCreateProcessor = new XSKEntityCreateProcessor();

  /**
   * Execute the corresponding statement.
   *
   * @param connection  the connection
   * @param entityModel the entity model
   * @throws SQLException the SQL exception
   */
  public boolean execute(Connection connection, XSKDataStructureEntityModel entityModel)
      throws SQLException {
    String tableName = XSKHDBUtils.escapeArtifactName(XSKHDBUtils.getTableName(entityModel));
    logger.info("Processing Update Entity: {}", tableName);
    if (SqlFactory.getNative(connection).exists(connection, tableName)) {
      if (SqlFactory.getNative(connection).count(connection, tableName) == 0) {
        return (xskEntityDropProcessor.execute(connection, entityModel) && xskEntityCreateProcessor.execute(connection, entityModel));
      } else {
        // XSKEntityAlterProcessor.execute(connection, entityModel);
    	  logger.error(tableName + " not empty");
    	  return false;
      }
    } else {
      return xskEntityCreateProcessor.execute(connection, entityModel);
    }
  }
}
