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
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.hdbdd.CdsDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableDataStructureModel;
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

public class EntityManagerService extends AbstractDataStructureManagerService<CdsDataStructureModel> {

  private static final Logger logger = LoggerFactory.getLogger(EntityManagerService.class);

  private final Map<String, CdsDataStructureModel> dataStructureEntitiesModel;
  private final List<String> entitiesSynchronized;

  private TableDropProcessor tableDropProcessor = new TableDropProcessor();

  private TableAlterProcessor tableAlterProcessor = new TableAlterProcessor();

  private TableCreateProcessor tableCreateProcessor = new TableCreateProcessor();

  public EntityManagerService() {
    dataStructureEntitiesModel = Collections.synchronizedMap(new LinkedHashMap<>());
    entitiesSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public void synchronizeRuntimeMetadata(CdsDataStructureModel entitiesModel) throws DataStructuresException {
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
    if (!entitiesSynchronized.contains(entitiesModel.getLocation())) {
      entitiesSynchronized.add(entitiesModel.getLocation());
    }
  }

  @Override
  public boolean createDataStructure(Connection connection, CdsDataStructureModel entitiesModel)
      throws SQLException {
    if (entitiesModel != null) {
      for (HDBTableDataStructureModel entityModel : entitiesModel.getTableModels()) {
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

  @Override
  public boolean dropDataStructure(Connection connection, CdsDataStructureModel entitiesModel) throws SQLException {
	  return true;
  }

  @Override
  public boolean updateDataStructure(Connection connection, CdsDataStructureModel entitiesModel)
      throws SQLException, OperationNotSupportedException {
	  return true;
  }

  @Override
  public Map<String, CdsDataStructureModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureEntitiesModel);
  }

  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.entitiesSynchronized);
  }

  @Override
  public String getDataStructureType() {
    return HDBDataStructureModel.FILE_EXTENSION_ENTITIES;
  }

  @Override
  public void clearCache() {
    dataStructureEntitiesModel.clear();
  }
}
