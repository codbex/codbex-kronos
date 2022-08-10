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
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.DataStructureHDBTableFunctionModel;
import com.codbex.kronos.hdb.ds.processors.hdbtablefunction.HDBTableFunctionCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.hdbtablefunction.HDBTableFunctionDropProcessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.naming.OperationNotSupportedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class TableFunctionManagerService.
 */
public class TableFunctionManagerService extends AbstractDataStructureManagerService<DataStructureHDBTableFunctionModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(TableFunctionManagerService.class);

  /** The data structure table functions models. */
  private final Map<String, DataStructureHDBTableFunctionModel> dataStructureTableFunctionsModels;
  
  /** The table functions synchronized. */
  private final List<String> tableFunctionsSynchronized;

  /** The hdb table function create processor. */
  private IHDBProcessor hdbTableFunctionCreateProcessor = new HDBTableFunctionCreateProcessor();
  
  /** The hdb table function drop processor. */
  private IHDBProcessor hdbTableFunctionDropProcessor = new HDBTableFunctionDropProcessor();

  /**
   * Instantiates a new table function manager service.
   */
  public TableFunctionManagerService() {
    dataStructureTableFunctionsModels = new LinkedHashMap<>();
    tableFunctionsSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  /**
   * Synchronize runtime metadata.
   *
   * @param hdbTableFunctionModel the hdb table function model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBTableFunctionModel hdbTableFunctionModel) throws DataStructuresException {
    // TODO: ommit double calling of finding the hdbTableFunction by extracting it in
    // variable
    // String schemaNameConcatTableFunctionName = hdbTableName.getSchemaName() + "." +
    // hdbProcedure.getName();
    if (!getDataStructuresCoreService().existsDataStructure(hdbTableFunctionModel.getLocation(), hdbTableFunctionModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(hdbTableFunctionModel.getLocation(), hdbTableFunctionModel.getName(), hdbTableFunctionModel.getHash(),
              hdbTableFunctionModel.getType());
      dataStructureTableFunctionsModels.put(hdbTableFunctionModel.getName(), hdbTableFunctionModel);
      logger.info("Synchronized a new HDB Table Function file [{}] from location: {}", hdbTableFunctionModel.getName(),
          hdbTableFunctionModel.getLocation());
    } else {
      DataStructureHDBTableFunctionModel existing = getDataStructuresCoreService()
          .getDataStructure(hdbTableFunctionModel.getLocation(), hdbTableFunctionModel.getType());
      if (!hdbTableFunctionModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(hdbTableFunctionModel.getLocation(), hdbTableFunctionModel.getName(), hdbTableFunctionModel.getHash(),
                hdbTableFunctionModel.getType());
        dataStructureTableFunctionsModels.put(hdbTableFunctionModel.getName(), hdbTableFunctionModel);
        logger.info("Synchronized a modified HDB Table Function file [{}] from location: {}", hdbTableFunctionModel.getName(),
            hdbTableFunctionModel.getLocation());
      }
    }
    if (!tableFunctionsSynchronized.contains(hdbTableFunctionModel.getLocation())) {
      tableFunctionsSynchronized.add(hdbTableFunctionModel.getLocation());
    }
  }

  /**
   * Creates the data structure.
   *
   * @param connection the connection
   * @param hdbTableFunctionModel the hdb table function model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean createDataStructure(Connection connection, DataStructureHDBTableFunctionModel hdbTableFunctionModel)
      throws SQLException {
	return this.hdbTableFunctionCreateProcessor.execute(connection, hdbTableFunctionModel);
  }

  /**
   * Drop data structure.
   *
   * @param connection the connection
   * @param hdbTableFunctionModel the hdb table function model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean dropDataStructure(Connection connection, DataStructureHDBTableFunctionModel hdbTableFunctionModel)
      throws SQLException {
	return this.hdbTableFunctionDropProcessor.execute(connection, hdbTableFunctionModel);
  }

  /**
   * Update data structure.
   *
   * @param connection the connection
   * @param hdbTableFunctionModel the hdb table function model
   * @return true, if successful
   * @throws SQLException the SQL exception
   * @throws OperationNotSupportedException the operation not supported exception
   */
  @Override
  public boolean updateDataStructure(Connection connection, DataStructureHDBTableFunctionModel hdbTableFunctionModel)
      throws SQLException, OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  /**
   * Gets the data structure synchronized.
   *
   * @return the data structure synchronized
   */
  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.tableFunctionsSynchronized);
  }

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_HDBTABLEFUNCTION;
  }

  /**
   * Clear cache.
   */
  @Override
  public void clearCache() {
    dataStructureTableFunctionsModels.clear();
  }

  /**
   * Gets the data structure models.
   *
   * @return the data structure models
   */
  @Override
  public Map<String, DataStructureHDBTableFunctionModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureTableFunctionsModels);
  }
}
