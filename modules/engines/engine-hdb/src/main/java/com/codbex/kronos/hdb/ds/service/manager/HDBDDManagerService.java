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
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.hdbdd.DataStructureHDBDDModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.processors.table.TableAlterProcessor;
import com.codbex.kronos.hdb.ds.processors.table.TableCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.table.TableDropProcessor;
import com.codbex.kronos.utils.HDBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.naming.OperationNotSupportedException;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class EntityManagerService.
 */
public class HDBDDManagerService extends AbstractDataStructureManagerService<DataStructureHDBDDModel> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBDDManagerService.class);

  /**
   * The data structure entities model.
   */
  private final Map<String, DataStructureHDBDDModel> dataStructureEntitiesModel;

  /**
   * The hdbdd synchronized.
   */
  private final List<String> hdbddSynchronized;

  /**
   * The table drop processor.
   */
  private TableDropProcessor tableDropProcessor = new TableDropProcessor();

  /**
   * The table alter processor.
   */
  private TableAlterProcessor tableAlterProcessor = new TableAlterProcessor();

  /**
   * The table create processor.
   */
  private TableCreateProcessor tableCreateProcessor = new TableCreateProcessor();

  /**
   * Instantiates a new entity manager service.
   */
  public HDBDDManagerService() {
    dataStructureEntitiesModel = Collections.synchronizedMap(new LinkedHashMap<>());
    hdbddSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  /**
   * Synchronize runtime metadata.
   *
   * @param entitiesModel the entities model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBDDModel entitiesModel) throws DataStructuresException {
    if (!getDataStructuresCoreService().existsDataStructure(entitiesModel.getLocation(), entitiesModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(entitiesModel.getLocation(), entitiesModel.getName(), entitiesModel.getHash(), entitiesModel.getType());
      dataStructureEntitiesModel.put(entitiesModel.getName(), entitiesModel);
      logger.info("Synchronized a new Entities file [{}] from location: {}", entitiesModel.getName(), entitiesModel.getLocation());
    } else {
      getDataStructuresCoreService()
          .updateDataStructure(entitiesModel.getLocation(), entitiesModel.getName(), entitiesModel.getHash(), entitiesModel.getType());
      dataStructureEntitiesModel.put(entitiesModel.getName(), entitiesModel);
      logger.info("Synchronized a modified Entities file [{}] from location: {}", entitiesModel.getName(), entitiesModel.getLocation());
    }
    if (!hdbddSynchronized.contains(entitiesModel.getLocation())) {
      hdbddSynchronized.add(entitiesModel.getLocation());
    }
  }

  /**
   * Creates the data structure.
   *
   * @param connection    the connection
   * @param entitiesModel the entities model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean createDataStructure(Connection connection, DataStructureHDBDDModel entitiesModel)
      throws SQLException {
    if (entitiesModel != null) {
      for (DataStructureHDBTableModel entityModel : entitiesModel.getTableModels()) {
        String tableName = HDBUtils.escapeArtifactName(entityModel.getName(), entityModel.getSchema());
        if (!SqlFactory.getNative(connection).exists(connection, tableName)) {
          if (!this.tableCreateProcessor.execute(connection, entityModel)) {
            return false;
          }
        } else {
          if (!this.tableAlterProcessor.execute(connection, entityModel)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Drop data structure.
   *
   * @param connection    the connection
   * @param entitiesModel the entities model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean dropDataStructure(Connection connection, DataStructureHDBDDModel entitiesModel) throws SQLException {
    return true;
  }

  /**
   * Update data structure.
   *
   * @param connection    the connection
   * @param entitiesModel the entities model
   * @return true, if successful
   * @throws SQLException                   the SQL exception
   * @throws OperationNotSupportedException the operation not supported exception
   */
  @Override
  public boolean updateDataStructure(Connection connection, DataStructureHDBDDModel entitiesModel)
      throws SQLException, OperationNotSupportedException {
    return true;
  }

  /**
   * Gets the data structure models.
   *
   * @return the data structure models
   */
  @Override
  public Map<String, DataStructureHDBDDModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureEntitiesModel);
  }

  /**
   * Gets the data structure synchronized.
   *
   * @return the data structure synchronized
   */
  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.hdbddSynchronized);
  }

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_HDBDD;
  }

  /**
   * Clear cache.
   */
  @Override
  public void clearCache() {
    dataStructureEntitiesModel.clear();
  }
}
