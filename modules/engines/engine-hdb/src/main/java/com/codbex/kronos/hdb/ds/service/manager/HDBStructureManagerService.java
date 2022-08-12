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

import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.IHDBProcessor;
import com.codbex.kronos.hdb.ds.model.hdbstructure.DataStructureHDBStructureModel;
import com.codbex.kronos.hdb.ds.processors.tabletype.TableTypeCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.tabletype.TableTypeDropProcessor;
import com.codbex.kronos.utils.HDBSynonymRemover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.naming.OperationNotSupportedException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class HDBStructureManagerService.
 */
public class HDBStructureManagerService extends AbstractDataStructureManagerService<DataStructureHDBStructureModel> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBStructureManagerService.class);

  /**
   * The data structure HDB structure models.
   */
  private final Map<String, DataStructureHDBStructureModel> dataStructureHDBStructureModels;

  /**
   * The hdb structures synchronized.
   */
  private final List<String> structuresSynchronized;

  /**
   * The table type create processor.
   */
  private final IHDBProcessor tableTypeCreateProcessor;

  /**
   * The table type drop processor.
   */
  private final IHDBProcessor tableTypeDropProcessor;

  /**
   * Instantiates a new hdb structure manager service.
   *
   * @param synonymRemover the synonym remover
   */
  public HDBStructureManagerService(HDBSynonymRemover synonymRemover) {
    this.tableTypeCreateProcessor = new TableTypeCreateProcessor();
    this.tableTypeDropProcessor = new TableTypeDropProcessor(synonymRemover);
    dataStructureHDBStructureModels = new LinkedHashMap<>();
    structuresSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  /**
   * Instantiates a new hdb structure manager service.
   *
   * @param synonymRemover the synonym remover
   */
  public HDBStructureManagerService(HDBSynonymRemover synonymRemover,
      Map<String, DataStructureHDBStructureModel> dataStructureHDBTableTypeModels, List<String> tableTypesSynchronized) {
    this.tableTypeCreateProcessor = new TableTypeCreateProcessor();
    this.tableTypeDropProcessor = new TableTypeDropProcessor(synonymRemover);
    this.dataStructureHDBStructureModels = dataStructureHDBTableTypeModels;
    this.structuresSynchronized = tableTypesSynchronized;
  }

  /**
   * Gets the data structure models.
   *
   * @return the data structure models
   */
  @Override
  public Map<String, DataStructureHDBStructureModel> getDataStructureModels() {
    return dataStructureHDBStructureModels;
  }

  /**
   * Synchronize runtime metadata.
   *
   * @param structureModel the table type model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBStructureModel structureModel) throws DataStructuresException {

    if (!getDataStructuresCoreService().existsDataStructure(structureModel.getLocation(), structureModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(structureModel.getLocation(), structureModel.getName(), structureModel.getHash(), structureModel.getType());
      dataStructureHDBStructureModels.put(structureModel.getName(), structureModel);
      logger.info("Synchronized a new HDB Structure file [{}] from location: {}", structureModel.getName(), structureModel.getLocation());
    } else {
      DataStructureHDBStructureModel existing = getDataStructuresCoreService()
          .getDataStructure(structureModel.getLocation(), structureModel.getType());
      if (!structureModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(structureModel.getLocation(), structureModel.getName(), structureModel.getHash(),
                structureModel.getType());
        dataStructureHDBStructureModels.put(structureModel.getName(), structureModel);
        logger
            .info("Synchronized a modified Structure file [{}] from location: {}", structureModel.getName(),
                structureModel.getLocation());
      }
    }
    if (!structuresSynchronized.contains(structureModel.getLocation())) {
      structuresSynchronized.add(structureModel.getLocation());
    }
  }

  /**
   * Creates the data structure.
   *
   * @param connection     the connection
   * @param structureModel the structure model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean createDataStructure(Connection connection, DataStructureHDBStructureModel structureModel)
      throws SQLException {
    return this.tableTypeCreateProcessor.execute(connection, structureModel);
  }

  /**
   * Drop data structure.
   *
   * @param connection     the connection
   * @param structureModel the hdb structure model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean dropDataStructure(Connection connection, DataStructureHDBStructureModel structureModel)
      throws SQLException {
    return this.tableTypeDropProcessor.execute(connection, structureModel);
  }

  /**
   * Update data structure.
   *
   * @param connection     the connection
   * @param structureModel the hdb structure model
   * @return true, if successful
   * @throws SQLException                   the SQL exception
   * @throws OperationNotSupportedException the operation not supported exception
   */
  @Override
  public boolean updateDataStructure(Connection connection, DataStructureHDBStructureModel structureModel)
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
    return Collections.unmodifiableList(this.structuresSynchronized);
  }

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_HDB_STRUCTURE;
  }

  /**
   * Clear cache.
   */
  @Override
  public void clearCache() {
    dataStructureHDBStructureModels.clear();
  }
}
