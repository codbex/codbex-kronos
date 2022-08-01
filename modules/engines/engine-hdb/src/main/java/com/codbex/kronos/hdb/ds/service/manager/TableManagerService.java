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

import com.codbex.kronos.hdb.ds.api.HDBDataStructureModel;
import com.codbex.kronos.hdb.ds.api.IHDBProcessor;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableDataStructureModel;
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

public class TableManagerService extends AbstractDataStructureManagerService<HDBTableDataStructureModel> {

  private static final Logger logger = LoggerFactory.getLogger(TableManagerService.class);

  private final Map<String, HDBTableDataStructureModel> dataStructureTableModels;
  private final List<String> tablesSynchronized;

  private IHDBProcessor tableCreateProcessor = new TableCreateProcessor();
  private IHDBProcessor tableDropProcessor = new TableDropProcessor();
  private IHDBProcessor tableAlterProcessor = new TableAlterProcessor();


  public TableManagerService() {
    dataStructureTableModels = new LinkedHashMap<>();
    tablesSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  public void synchronizeRuntimeMetadata(HDBTableDataStructureModel tableModel)
      throws DataStructuresException {
    if (!getDataStructuresCoreService().existsDataStructure(tableModel.getLocation(), tableModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(tableModel.getLocation(), tableModel.getName(), tableModel.getHash(), tableModel.getType());
      dataStructureTableModels.put(tableModel.getName(), tableModel);
      logger.info("Synchronized a new Table file [{}] from location: {}", tableModel.getName(), tableModel.getLocation());
    } else {
      HDBTableDataStructureModel existing = getDataStructuresCoreService()
          .getDataStructure(tableModel.getLocation(), tableModel.getType());
      if (!tableModel.equals(existing)) {
        getDataStructuresCoreService().updateDataStructure(tableModel.getLocation(), tableModel.getName(), tableModel.getHash(),
            tableModel.getType());
        dataStructureTableModels.put(tableModel.getName(), tableModel);
        logger.info("Synchronized a modified Table file [{}] from location: {}", tableModel.getName(),
            tableModel.getLocation());
      }
    }
    if (!tablesSynchronized.contains(tableModel.getLocation())) {
      tablesSynchronized.add(tableModel.getLocation());
    }
  }

  public boolean createDataStructure(Connection connection, HDBTableDataStructureModel tableModel)
      throws SQLException {
	return this.tableCreateProcessor.execute(connection, tableModel);
  }

  public boolean dropDataStructure(Connection connection, HDBTableDataStructureModel tableModel)
      throws SQLException {
	return this.tableDropProcessor.execute(connection, tableModel);
  }

  public boolean updateDataStructure(Connection connection, HDBTableDataStructureModel tableModel)
      throws SQLException {
    //TODO: Create logic for updating hdb table
    logger.error("Altering of a non-empty table is not implemented yet.");
    // TableAlterProcessor.execute(connection, tableModel);
    return tableAlterProcessor.execute(connection, tableModel);
  }

  public Map<String, HDBTableDataStructureModel> getDataStructureModels() {
    return this.dataStructureTableModels;
  }

  public List<String> getDataStructureSynchronized() {
    return this.tablesSynchronized;
  }

  @Override
  public String getDataStructureType() {
    return HDBDataStructureModel.FILE_EXTENSION_TABLE;
  }

  @Override
  public void clearCache() {
    dataStructureTableModels.clear();
  }
}
