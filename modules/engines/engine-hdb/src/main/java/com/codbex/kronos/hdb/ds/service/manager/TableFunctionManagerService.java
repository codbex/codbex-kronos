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
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.HDBTableFunctionDataStructureModel;
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

public class TableFunctionManagerService extends AbstractDataStructureManagerService<HDBTableFunctionDataStructureModel> {

  private static final Logger logger = LoggerFactory.getLogger(TableFunctionManagerService.class);

  private final Map<String, HDBTableFunctionDataStructureModel> dataStructureTableFunctionsModels;
  private final List<String> tableFunctionsSynchronized;

  private IHDBProcessor hdbTableFunctionCreateProcessor = new HDBTableFunctionCreateProcessor();
  private IHDBProcessor hdbTableFunctionDropProcessor = new HDBTableFunctionDropProcessor();

  public TableFunctionManagerService() {
    dataStructureTableFunctionsModels = new LinkedHashMap<>();
    tableFunctionsSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public void synchronizeRuntimeMetadata(HDBTableFunctionDataStructureModel hdbTableFunctionModel) throws DataStructuresException {
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
      HDBTableFunctionDataStructureModel existing = getDataStructuresCoreService()
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

  @Override
  public boolean createDataStructure(Connection connection, HDBTableFunctionDataStructureModel hdbTableFunctionModel)
      throws SQLException {
	return this.hdbTableFunctionCreateProcessor.execute(connection, hdbTableFunctionModel);
  }

  @Override
  public boolean dropDataStructure(Connection connection, HDBTableFunctionDataStructureModel hdbTableFunctionModel)
      throws SQLException {
	return this.hdbTableFunctionDropProcessor.execute(connection, hdbTableFunctionModel);
  }

  @Override
  public boolean updateDataStructure(Connection connection, HDBTableFunctionDataStructureModel hdbTableFunctionModel)
      throws SQLException, OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.tableFunctionsSynchronized);
  }

  @Override
  public String getDataStructureType() {
    return HDBDataStructureModel.FILE_EXTENSION_HDBTABLEFUNCTION;
  }

  @Override
  public void clearCache() {
    dataStructureTableFunctionsModels.clear();
  }

  @Override
  public Map<String, HDBTableFunctionDataStructureModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureTableFunctionsModels);
  }
}
