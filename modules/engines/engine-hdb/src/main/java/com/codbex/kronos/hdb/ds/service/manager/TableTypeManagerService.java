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
import com.codbex.kronos.hdb.ds.model.hdbtabletype.HDBTableTypeDataStructureModel;
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

public class TableTypeManagerService extends AbstractDataStructureManagerService<HDBTableTypeDataStructureModel> {

  private static final Logger logger = LoggerFactory.getLogger(TableTypeManagerService.class);

  private final Map<String, HDBTableTypeDataStructureModel> dataStructureHDBTableTypeModels;
  private final List<String> tableTypesSynchronized;

  private final IHDBProcessor tableTypeCreateProcessor;
  private final IHDBProcessor tableTypeDropProcessor;

  public TableTypeManagerService(HDBSynonymRemover synonymRemover) {
    this.tableTypeCreateProcessor = new TableTypeCreateProcessor();
    this.tableTypeDropProcessor = new TableTypeDropProcessor(synonymRemover);
    dataStructureHDBTableTypeModels = new LinkedHashMap<>();
    tableTypesSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public Map<String, HDBTableTypeDataStructureModel> getDataStructureModels() {
    return dataStructureHDBTableTypeModels;
  }

  @Override
  public void synchronizeRuntimeMetadata(HDBTableTypeDataStructureModel tableTypeModel) throws DataStructuresException {

    if (!getDataStructuresCoreService().existsDataStructure(tableTypeModel.getLocation(), tableTypeModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(tableTypeModel.getLocation(), tableTypeModel.getName(), tableTypeModel.getHash(), tableTypeModel.getType());
      dataStructureHDBTableTypeModels.put(tableTypeModel.getName(), tableTypeModel);
      logger.info("Synchronized a new Structure file [{}] from location: {}", tableTypeModel.getName(), tableTypeModel.getLocation());
    } else {
      HDBTableTypeDataStructureModel existing = getDataStructuresCoreService()
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

  @Override
  public boolean createDataStructure(Connection connection, HDBTableTypeDataStructureModel structureModel)
      throws SQLException {
	return this.tableTypeCreateProcessor.execute(connection, structureModel);
  }

  @Override
  public boolean dropDataStructure(Connection connection, HDBTableTypeDataStructureModel tableTypeModel)
      throws SQLException {
	return this.tableTypeDropProcessor.execute(connection, tableTypeModel);
  }

  @Override
  public boolean updateDataStructure(Connection connection, HDBTableTypeDataStructureModel tableTypeModel)
      throws SQLException, OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.tableTypesSynchronized);
  }

  @Override
  public String getDataStructureType() {
    return HDBDataStructureModel.FILE_EXTENSION_STRUCTURE;
  }

  @Override
  public void clearCache() {
    dataStructureHDBTableTypeModels.clear();
  }
}
