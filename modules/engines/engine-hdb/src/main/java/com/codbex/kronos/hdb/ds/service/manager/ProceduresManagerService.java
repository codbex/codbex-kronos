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
import com.codbex.kronos.hdb.ds.model.hdbprocedure.DataStructureHDBProcedureModel;
import com.codbex.kronos.hdb.ds.processors.hdbprocedure.HDBProcedureCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.hdbprocedure.HDBProcedureDropProcessor;

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

public class ProceduresManagerService extends AbstractDataStructureManagerService<DataStructureHDBProcedureModel> {

  private static final Logger logger = LoggerFactory.getLogger(ProceduresManagerService.class);


  private final Map<String, DataStructureHDBProcedureModel> dataStructureProceduresModels;
  private final List<String> proceduresSynchronized;

  private IHDBProcessor hdbProcedureDropProcessor = new HDBProcedureDropProcessor();
  private IHDBProcessor hdbProcedureCreateProcessor = new HDBProcedureCreateProcessor();

  public ProceduresManagerService() {
    dataStructureProceduresModels = new LinkedHashMap<>();
    proceduresSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBProcedureModel hdbProcedureModel) throws DataStructuresException {
    // TODO: ommit double calling of finding the hdbProcedure by extracting it in
    // variable
    // String schemaNameConcatProcedureName = hdbProcedure.getSchemaName() + "." +
    // hdbProcedure.getName();
    if (!getDataStructuresCoreService().existsDataStructure(hdbProcedureModel.getLocation(), hdbProcedureModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(hdbProcedureModel.getLocation(), hdbProcedureModel.getName(), hdbProcedureModel.getHash(),
              hdbProcedureModel.getType());
      dataStructureProceduresModels.put(hdbProcedureModel.getName(), hdbProcedureModel);
      logger.info("Synchronized a new HDB Procedure file [{}] from location: {}", hdbProcedureModel.getName(),
          hdbProcedureModel.getLocation());
    } else {
      DataStructureHDBProcedureModel existing = getDataStructuresCoreService()
          .getDataStructure(hdbProcedureModel.getLocation(), hdbProcedureModel.getType());
      if (!hdbProcedureModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(hdbProcedureModel.getLocation(), hdbProcedureModel.getName(), hdbProcedureModel.getHash(),
                hdbProcedureModel.getType());
        dataStructureProceduresModels.put(hdbProcedureModel.getName(), hdbProcedureModel);
        logger.info("Synchronized a modified HDB Procedure file [{}] from location: {}", hdbProcedureModel.getName(),
            hdbProcedureModel.getLocation());
      }
    }
    if (!proceduresSynchronized.contains(hdbProcedureModel.getLocation())) {
      proceduresSynchronized.add(hdbProcedureModel.getLocation());
    }
  }

  @Override
  public boolean createDataStructure(Connection connection, DataStructureHDBProcedureModel viewModel)
      throws SQLException {
	return this.hdbProcedureCreateProcessor.execute(connection, viewModel);
  }

  @Override
  public boolean dropDataStructure(Connection connection, DataStructureHDBProcedureModel viewModel)
      throws SQLException {
	return this.hdbProcedureDropProcessor.execute(connection, viewModel);
  }

  @Override
  public boolean updateDataStructure(Connection connection, DataStructureHDBProcedureModel viewModel)
      throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.proceduresSynchronized);
  }

  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_HDBPROCEDURE;
  }

  @Override
  public void clearCache() {
    dataStructureProceduresModels.clear();
  }

  @Override
  public Map<String, DataStructureHDBProcedureModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureProceduresModels);
  }
}
