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
import com.codbex.kronos.hdb.ds.model.hdbstructure.DataStructureHDBStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.DataStructureHDBTableFunctionModel;
import com.codbex.kronos.hdb.ds.processors.function.FunctionCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.function.FunctionDropProcessor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.naming.OperationNotSupportedException;
import com.codbex.kronos.hdb.ds.processors.tabletype.TableTypeCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.tabletype.TableTypeDropProcessor;
import com.codbex.kronos.utils.HDBSynonymRemover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class TableFunctionManagerService.
 */
public class HDBTableFunctionManagerService extends AbstractDataStructureManagerService<DataStructureHDBTableFunctionModel> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBTableFunctionManagerService.class);

  /**
   * The data structure table functions models.
   */
  private final Map<String, DataStructureHDBTableFunctionModel> dataStructureHDBTableFunctionsModels;

  /**
   * The table functions synchronized.
   */
  private final List<String> tableFunctionsSynchronized;

  /**
   * The hdb table function create processor.
   */
  private IHDBProcessor tableFunctionCreateProcessor = new FunctionCreateProcessor();

  /**
   * The hdb table function drop processor.
   */
  private IHDBProcessor tableFunctionDropProcessor = new FunctionDropProcessor();

  /**
   * Instantiates a new table function manager service.
   */
  public HDBTableFunctionManagerService() {
    dataStructureHDBTableFunctionsModels = new LinkedHashMap<>();
    tableFunctionsSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  /**
   * Instantiates a new hdb structure manager service.
   */
  public HDBTableFunctionManagerService(Map<String, DataStructureHDBTableFunctionModel> dataStructureScalarFunctionModels, List<String> scalarFunctionsSynchronized) {
    this.dataStructureHDBTableFunctionsModels = dataStructureScalarFunctionModels;
    this.tableFunctionsSynchronized = scalarFunctionsSynchronized;
  }

  /**
   * Synchronize runtime metadata.
   *
   * @param tableFunctionModel the hdb table function model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBTableFunctionModel tableFunctionModel) throws DataStructuresException {
    // TODO: Ommit double calling of finding the hdbTableFunction by extracting it in

    // String schemaNameConcatTableFunctionName = hdbTableName.getSchemaName() + "." + hdbProcedure.getName();

    if (!getDataStructuresCoreService().existsDataStructure(tableFunctionModel.getLocation(), tableFunctionModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(tableFunctionModel.getLocation(), tableFunctionModel.getName(), tableFunctionModel.getHash(),
              tableFunctionModel.getType());
      dataStructureHDBTableFunctionsModels.put(tableFunctionModel.getName(), tableFunctionModel);
      logger.info("Synchronized a new HDB Table Function file [{}] from location: {}", tableFunctionModel.getName(),
          tableFunctionModel.getLocation());
    } else {
      DataStructureHDBTableFunctionModel existing = getDataStructuresCoreService()
          .getDataStructure(tableFunctionModel.getLocation(), tableFunctionModel.getType());
      if (!tableFunctionModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(tableFunctionModel.getLocation(), tableFunctionModel.getName(), tableFunctionModel.getHash(),
                tableFunctionModel.getType());
        dataStructureHDBTableFunctionsModels.put(tableFunctionModel.getName(), tableFunctionModel);
        logger.info("Synchronized a modified HDB Table Function file [{}] from location: {}", tableFunctionModel.getName(),
            tableFunctionModel.getLocation());
      }
    }
    if (!tableFunctionsSynchronized.contains(tableFunctionModel.getLocation())) {
      tableFunctionsSynchronized.add(tableFunctionModel.getLocation());
    }
  }

  /**
   * Creates the data structure.
   *
   * @param connection            the connection
   * @param tableFunctionModel the hdb table function model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean createDataStructure(Connection connection, DataStructureHDBTableFunctionModel tableFunctionModel)
      throws SQLException {
    return this.tableFunctionCreateProcessor.execute(connection, tableFunctionModel);
  }

  /**
   * Drop data structure.
   *
   * @param connection            the connection
   * @param tableFunctionModel the hdb table function model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean dropDataStructure(Connection connection, DataStructureHDBTableFunctionModel tableFunctionModel)
      throws SQLException {
    return this.tableFunctionDropProcessor.execute(connection, tableFunctionModel);
  }

  /**
   * Update data structure.
   *
   * @param connection            the connection
   * @param tableFunctionModel the hdb table function model
   * @return true, if successful
   * @throws SQLException                   the SQL exception
   * @throws OperationNotSupportedException the operation not supported exception
   */
  @Override
  public boolean updateDataStructure(Connection connection, DataStructureHDBTableFunctionModel tableFunctionModel)
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
    return IDataStructureModel.FILE_EXTENSION_HDB_TABLE_FUNCTION;
  }

  /**
   * Clear cache.
   */
  @Override
  public void clearCache() {
    dataStructureHDBTableFunctionsModels.clear();
  }

  /**
   * Gets the data structure models.
   *
   * @return the data structure models
   */
  @Override
  public Map<String, DataStructureHDBTableFunctionModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureHDBTableFunctionsModels);
  }
}
