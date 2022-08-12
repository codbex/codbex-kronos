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
package com.codbex.kronos.hdb.ds.service.manager;

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.IHDBProcessor;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.processors.table.TableAlterProcessor;
import com.codbex.kronos.hdb.ds.processors.table.TableCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.table.TableDropProcessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class TableManagerService.
 */
public class HDBTableManagerService extends AbstractDataStructureManagerService<DataStructureHDBTableModel> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBTableManagerService.class);

  /**
   * The data structure table models.
   */
  private final Map<String, DataStructureHDBTableModel> dataStructureHDBTableModels;

  /**
   * The tables synchronized.
   */
  private final List<String> hdbTablesSynchronized;

  /**
   * The table create processor.
   */
  private IHDBProcessor hdbTableCreateProcessor = new TableCreateProcessor();

  /**
   * The table drop processor.
   */
  private IHDBProcessor hdbTableDropProcessor = new TableDropProcessor();

  /**
   * The table alter processor.
   */
  private IHDBProcessor hdbTableAlterProcessor = new TableAlterProcessor();


  /**
   * Instantiates a new table manager service.
   */
  public HDBTableManagerService() {
    dataStructureHDBTableModels = new LinkedHashMap<>();
    hdbTablesSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  /**
   * Synchronize runtime metadata.
   *
   * @param tableModel the table model
   * @throws DataStructuresException the data structures exception
   */
  public void synchronizeRuntimeMetadata(DataStructureHDBTableModel tableModel)
      throws DataStructuresException {
    if (!getDataStructuresCoreService().existsDataStructure(tableModel.getLocation(), tableModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(tableModel.getLocation(), tableModel.getName(), tableModel.getHash(), tableModel.getType());
      dataStructureHDBTableModels.put(tableModel.getName(), tableModel);
      logger.info("Synchronized a new HDB Table file [{}] from location: {}", tableModel.getName(), tableModel.getLocation());
    } else {
      DataStructureHDBTableModel existing = getDataStructuresCoreService()
          .getDataStructure(tableModel.getLocation(), tableModel.getType());
      if (!tableModel.equals(existing)) {
        getDataStructuresCoreService().updateDataStructure(tableModel.getLocation(), tableModel.getName(), tableModel.getHash(),
            tableModel.getType());
        dataStructureHDBTableModels.put(tableModel.getName(), tableModel);
        logger.info("Synchronized a modified HDB Table file [{}] from location: {}", tableModel.getName(),
            tableModel.getLocation());
      }
    }
    if (!hdbTablesSynchronized.contains(tableModel.getLocation())) {
      hdbTablesSynchronized.add(tableModel.getLocation());
    }
  }

  /**
   * Creates the data structure.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public boolean createDataStructure(Connection connection, DataStructureHDBTableModel tableModel)
      throws SQLException {
    return this.hdbTableCreateProcessor.execute(connection, tableModel);
  }

  /**
   * Drop data structure.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public boolean dropDataStructure(Connection connection, DataStructureHDBTableModel tableModel)
      throws SQLException {
    return this.hdbTableDropProcessor.execute(connection, tableModel);
  }

  /**
   * Update data structure.
   *
   * @param connection the connection
   * @param tableModel the table model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public boolean updateDataStructure(Connection connection, DataStructureHDBTableModel tableModel)
      throws SQLException {
    //TODO: Create logic for updating hdb table

    logger.error("Altering of a non-empty table is not implemented yet.");
    // TableAlterProcessor.execute(connection, tableModel);
    return hdbTableAlterProcessor.execute(connection, tableModel);
  }

  /**
   * Gets the data structure models.
   *
   * @return the data structure models
   */
  public Map<String, DataStructureHDBTableModel> getDataStructureModels() {
    return this.dataStructureHDBTableModels;
  }

  /**
   * Gets the data structure synchronized.
   *
   * @return the data structure synchronized
   */
  public List<String> getDataStructureSynchronized() {
    return this.hdbTablesSynchronized;
  }

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_HDB_TABLE;
  }

  /**
   * Clear cache.
   */
  @Override
  public void clearCache() {
    dataStructureHDBTableModels.clear();
  }
}
