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
import com.codbex.kronos.hdb.ds.model.hdbstructure.DataStructureHDBStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.DataStructureHDBTableTypeModel;
import com.codbex.kronos.utils.HDBSynonymRemover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class TableTypeManagerService.
 */
public class HDBTableTypeManagerService extends HDBStructureManagerService {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBScalarFunctionManagerService.class);

  /**
   * The data structure HDB table type models.
   */
  private final Map<String, DataStructureHDBStructureModel> dataStructureHDBTableTypeModels;

  /**
   * The table types synchronized.
   */
  private final List<String> tableTypesSynchronized;

  public HDBTableTypeManagerService(HDBSynonymRemover synonymRemover) {
    this(synonymRemover, new LinkedHashMap<>(), Collections.synchronizedList(new ArrayList<>()));
  }

  /**
   * Instantiates a new hdb table type manager service.
   *
   * @param synonymRemover                  the synonym remover
   * @param dataStructureHDBTableTypeModels
   * @param tableTypesSynchronized
   */
  public HDBTableTypeManagerService(HDBSynonymRemover synonymRemover,
      Map<String, DataStructureHDBStructureModel> dataStructureHDBTableTypeModels, List<String> tableTypesSynchronized) {
    super(synonymRemover, dataStructureHDBTableTypeModels, tableTypesSynchronized);
    this.dataStructureHDBTableTypeModels = dataStructureHDBTableTypeModels;
    this.tableTypesSynchronized = tableTypesSynchronized;
  }

  /**
   * Synchronize runtime metadata.
   *
   * @param tableTypeModel the table type model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBStructureModel tableTypeModel) throws DataStructuresException {

    if (!getDataStructuresCoreService().existsDataStructure(tableTypeModel.getLocation(), tableTypeModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(tableTypeModel.getLocation(), tableTypeModel.getName(), tableTypeModel.getHash(), tableTypeModel.getType());
      dataStructureHDBTableTypeModels.put(tableTypeModel.getName(), tableTypeModel);
      logger.info("Synchronized a new HDB Table Type file [{}] from location: {}", tableTypeModel.getName(), tableTypeModel.getLocation());
    } else {
      DataStructureHDBTableTypeModel existing = getDataStructuresCoreService()
          .getDataStructure(tableTypeModel.getLocation(), tableTypeModel.getType());
      if (!tableTypeModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(tableTypeModel.getLocation(), tableTypeModel.getName(), tableTypeModel.getHash(),
                tableTypeModel.getType());
        dataStructureHDBTableTypeModels.put(tableTypeModel.getName(), tableTypeModel);
        logger
            .info("Synchronized a modified HDB Table Type file [{}] from location: {}", tableTypeModel.getName(),
                tableTypeModel.getLocation());
      }
    }
    if (!tableTypesSynchronized.contains(tableTypeModel.getLocation())) {
      tableTypesSynchronized.add(tableTypeModel.getLocation());
    }
  }

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_HDB_TABLE_TYPE;
  }
}
