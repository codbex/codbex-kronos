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

import com.codbex.kronos.hdb.ds.api.IXSKDataStructureModel;
import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;
import com.codbex.kronos.hdb.ds.model.hdbdd.XSKDataStructureCdsModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.XSKDataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.processors.table.XSKTableAlterProcessor;
import com.codbex.kronos.hdb.ds.processors.table.XSKTableCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.table.XSKTableDropProcessor;
import com.codbex.kronos.utils.XSKHDBUtils;

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

public class XSKEntityManagerService extends AbstractDataStructureManagerService<XSKDataStructureCdsModel> {

  private static final Logger logger = LoggerFactory.getLogger(XSKEntityManagerService.class);

  private final Map<String, XSKDataStructureCdsModel> dataStructureEntitiesModel;
  private final List<String> entitiesSynchronized;

  private XSKTableDropProcessor xskTableDropProcessor = new XSKTableDropProcessor();

  private XSKTableAlterProcessor xskTableAlterProcessor = new XSKTableAlterProcessor();

  private XSKTableCreateProcessor xskTableCreateProcessor = new XSKTableCreateProcessor();

  public XSKEntityManagerService() {
    dataStructureEntitiesModel = Collections.synchronizedMap(new LinkedHashMap<>());
    entitiesSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public void synchronizeRuntimeMetadata(XSKDataStructureCdsModel entitiesModel) throws XSKDataStructuresException {
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
  public boolean createDataStructure(Connection connection, XSKDataStructureCdsModel entitiesModel)
      throws SQLException {
    if (entitiesModel != null) {
      for (XSKDataStructureHDBTableModel entityModel : entitiesModel.getTableModels()) {
        String tableName = XSKHDBUtils.escapeArtifactName(entityModel.getName(), entityModel.getSchema());
        if (!SqlFactory.getNative(connection).exists(connection, tableName)) {
          if (!this.xskTableCreateProcessor.execute(connection, entityModel)) {
        	  return false;
          }
        } else {
          if (!this.xskTableAlterProcessor.execute(connection, entityModel)) {
        	  return false;
          }
        }
      }
    }
    return true;
  }

  @Override
  public boolean dropDataStructure(Connection connection, XSKDataStructureCdsModel entitiesModel) throws SQLException {
	  return true;
  }

  @Override
  public boolean updateDataStructure(Connection connection, XSKDataStructureCdsModel entitiesModel)
      throws SQLException, OperationNotSupportedException {
	  return true;
  }

  @Override
  public Map<String, XSKDataStructureCdsModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureEntitiesModel);
  }

  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.entitiesSynchronized);
  }

  @Override
  public String getDataStructureType() {
    return IXSKDataStructureModel.FILE_EXTENSION_ENTITIES;
  }

  @Override
  public void clearCache() {
    dataStructureEntitiesModel.clear();
  }
}
