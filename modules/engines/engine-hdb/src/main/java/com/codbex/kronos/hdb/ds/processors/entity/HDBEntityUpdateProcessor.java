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

import com.codbex.kronos.hdb.ds.api.IHDBProcessor;
import com.codbex.kronos.hdb.ds.model.hdbdd.DataStructureEntityModel;
import com.codbex.kronos.hdb.ds.processors.AbstractHDBProcessor;
import com.codbex.kronos.utils.HDBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class HDBEntityUpdateProcessor.
 */
public class HDBEntityUpdateProcessor extends AbstractHDBProcessor<DataStructureEntityModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBEntityUpdateProcessor.class);

  /** The entity drop processor. */
  private IHDBProcessor<DataStructureEntityModel> entityDropProcessor = new HDBEntityDropProcessor();
  
  /** The entity create processor. */
  private IHDBProcessor<DataStructureEntityModel> entityCreateProcessor = new HDBEntityCreateProcessor();

  /**
   * Execute the corresponding statement.
   *
   * @param connection  the connection
   * @param entityModel the entity model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public boolean execute(Connection connection, DataStructureEntityModel entityModel)
      throws SQLException {
    String tableName = HDBUtils.escapeArtifactName(HDBUtils.getTableName(entityModel));
    logger.info("Processing Update Entity: {}", tableName);
    if (SqlFactory.getNative(connection).exists(connection, tableName)) {
      if (SqlFactory.getNative(connection).count(connection, tableName) == 0) {
        return (entityDropProcessor.execute(connection, entityModel) && entityCreateProcessor.execute(connection, entityModel));
      } else {
        // EntityAlterProcessor.execute(connection, entityModel);
    	  logger.error(tableName + " not empty");
    	  return false;
      }
    } else {
      return entityCreateProcessor.execute(connection, entityModel);
    }
  }
}
