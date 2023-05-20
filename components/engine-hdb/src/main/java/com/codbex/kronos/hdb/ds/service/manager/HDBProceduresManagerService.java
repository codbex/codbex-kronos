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
import com.codbex.kronos.hdb.ds.processors.procedure.ProcedureCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.procedure.ProcedureDropProcessor;

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
 * The Class ProceduresManagerService.
 */
public class HDBProceduresManagerService extends AbstractDataStructureManagerService<DataStructureHDBProcedureModel> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBProceduresManagerService.class);


  /**
   * The data structure procedures models.
   */
  private final Map<String, DataStructureHDBProcedureModel> procedureModels;

  /**
   * The procedures synchronized.
   */
  private final List<String> proceduresSynchronized;

  /**
   * The hdb procedure drop processor.
   */
  private IHDBProcessor procedureDropProcessor = new ProcedureDropProcessor();

  /**
   * The hdb procedure create processor.
   */
  private IHDBProcessor procedureCreateProcessor = new ProcedureCreateProcessor();

  /**
   * Instantiates a new procedures manager service.
   */
  public HDBProceduresManagerService() {
    procedureModels = new LinkedHashMap<>();
    proceduresSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  /**
   * Synchronize runtime metadata.
   *
   * @param procedureModel the hdb procedure model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBProcedureModel procedureModel) throws DataStructuresException {
    // TODO: Ommit double calling of finding the hdbProcedure by extracting it in

    // String schemaNameConcatProcedureName = procedureModel.getSchemaName() + "." + procedureModel.getName();

    if (!getDataStructuresCoreService().existsDataStructure(procedureModel.getLocation(),
        procedureModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(procedureModel.getLocation(), procedureModel.getName(),
              procedureModel.getHash(),
              procedureModel.getType());
      procedureModels.put(procedureModel.getName(), procedureModel);
      logger.info("Synchronized a new HDB Procedure file [{}] from location: {}", procedureModel.getName(),
          procedureModel.getLocation());
    } else {
      DataStructureHDBProcedureModel existing = getDataStructuresCoreService()
          .getDataStructure(procedureModel.getLocation(), procedureModel.getType());
      if (!procedureModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(procedureModel.getLocation(), procedureModel.getName(),
                procedureModel.getHash(),
                procedureModel.getType());
        procedureModels.put(procedureModel.getName(), procedureModel);
        logger.info("Synchronized a modified HDB Procedure file [{}] from location: {}", procedureModel.getName(),
            procedureModel.getLocation());
      }
    }
    if (!proceduresSynchronized.contains(procedureModel.getLocation())) {
      proceduresSynchronized.add(procedureModel.getLocation());
    }
  }

  /**
   * Creates the data structure.
   *
   * @param connection the connection
   * @param viewModel  the view model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean createDataStructure(Connection connection, DataStructureHDBProcedureModel viewModel)
      throws SQLException {
    return this.procedureCreateProcessor.execute(connection, viewModel);
  }

  /**
   * Drop data structure.
   *
   * @param connection the connection
   * @param viewModel  the view model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean dropDataStructure(Connection connection, DataStructureHDBProcedureModel viewModel)
      throws SQLException {
    return this.procedureDropProcessor.execute(connection, viewModel);
  }

  /**
   * Update data structure.
   *
   * @param connection the connection
   * @param viewModel  the view model
   * @return true, if successful
   * @throws OperationNotSupportedException the operation not supported exception
   */
  @Override
  public boolean updateDataStructure(Connection connection, DataStructureHDBProcedureModel viewModel)
      throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  /**
   * Gets the data structure synchronized.
   *
   * @return the data structure synchronized
   */
  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.proceduresSynchronized);
  }

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_HDB_PROCEDURE;
  }

  /**
   * Clear cache.
   */
  @Override
  public void clearCache() {
    procedureModels.clear();
  }

  /**
   * Gets the data structure models.
   *
   * @return the data structure models
   */
  @Override
  public Map<String, DataStructureHDBProcedureModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.procedureModels);
  }
}
