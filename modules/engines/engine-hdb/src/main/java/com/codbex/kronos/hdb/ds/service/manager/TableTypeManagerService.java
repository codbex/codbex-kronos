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
import com.codbex.kronos.hdb.ds.model.hdbtabletype.DataStructureHDBTableTypeModel;
import com.codbex.kronos.hdb.ds.processors.hdbstructure.HDBSynonymRemover;
import com.codbex.kronos.hdb.ds.processors.hdbstructure.TableTypeCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.hdbstructure.TableTypeDropProcessor;

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
 * The Class TableTypeManagerService.
 */
public class TableTypeManagerService extends AbstractDataStructureManagerService<DataStructureHDBTableTypeModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(TableTypeManagerService.class);

  /** The data structure HDB table type models. */
  private final Map<String, DataStructureHDBTableTypeModel> dataStructureHDBTableTypeModels;
  
  /** The table types synchronized. */
  private final List<String> tableTypesSynchronized;

  /** The table type create processor. */
  private final IHDBProcessor tableTypeCreateProcessor;
  
  /** The table type drop processor. */
  private final IHDBProcessor tableTypeDropProcessor;

  /**
   * Instantiates a new table type manager service.
   *
   * @param synonymRemover the synonym remover
   */
  public TableTypeManagerService(HDBSynonymRemover synonymRemover) {
    this.tableTypeCreateProcessor = new TableTypeCreateProcessor();
    this.tableTypeDropProcessor = new TableTypeDropProcessor(synonymRemover);
    dataStructureHDBTableTypeModels = new LinkedHashMap<>();
    tableTypesSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  /**
   * Gets the data structure models.
   *
   * @return the data structure models
   */
  @Override
  public Map<String, DataStructureHDBTableTypeModel> getDataStructureModels() {
    return dataStructureHDBTableTypeModels;
  }

  /**
   * Synchronize runtime metadata.
   *
   * @param tableTypeModel the table type model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBTableTypeModel tableTypeModel) throws DataStructuresException {

    if (!getDataStructuresCoreService().existsDataStructure(tableTypeModel.getLocation(), tableTypeModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(tableTypeModel.getLocation(), tableTypeModel.getName(), tableTypeModel.getHash(), tableTypeModel.getType());
      dataStructureHDBTableTypeModels.put(tableTypeModel.getName(), tableTypeModel);
      logger.info("Synchronized a new Structure file [{}] from location: {}", tableTypeModel.getName(), tableTypeModel.getLocation());
    } else {
      DataStructureHDBTableTypeModel existing = getDataStructuresCoreService()
          .getDataStructure(tableTypeModel.getLocation(), tableTypeModel.getType());
      if (!tableTypeModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(tableTypeModel.getLocation(), tableTypeModel.getName(), tableTypeModel.getHash(),
                tableTypeModel.getType());
        dataStructureHDBTableTypeModels.put(tableTypeModel.getName(), tableTypeModel);
        logger
            .info("Synchronized a modified Structure file [{}] from location: {}", tableTypeModel.getName(), tableTypeModel.getLocation());
      }
    }
    if (!tableTypesSynchronized.contains(tableTypeModel.getLocation())) {
      tableTypesSynchronized.add(tableTypeModel.getLocation());
    }
  }

  /**
   * Creates the data structure.
   *
   * @param connection the connection
   * @param structureModel the structure model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean createDataStructure(Connection connection, DataStructureHDBTableTypeModel structureModel)
      throws SQLException {
	return this.tableTypeCreateProcessor.execute(connection, structureModel);
  }

  /**
   * Drop data structure.
   *
   * @param connection the connection
   * @param tableTypeModel the table type model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean dropDataStructure(Connection connection, DataStructureHDBTableTypeModel tableTypeModel)
      throws SQLException {
	return this.tableTypeDropProcessor.execute(connection, tableTypeModel);
  }

  /**
   * Update data structure.
   *
   * @param connection the connection
   * @param tableTypeModel the table type model
   * @return true, if successful
   * @throws SQLException the SQL exception
   * @throws OperationNotSupportedException the operation not supported exception
   */
  @Override
  public boolean updateDataStructure(Connection connection, DataStructureHDBTableTypeModel tableTypeModel)
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
    return Collections.unmodifiableList(this.tableTypesSynchronized);
  }

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_STRUCTURE;
  }

  /**
   * Clear cache.
   */
  @Override
  public void clearCache() {
    dataStructureHDBTableTypeModels.clear();
  }
}
