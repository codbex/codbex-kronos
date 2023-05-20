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
import com.codbex.kronos.hdb.ds.model.hdbstructure.DataStructureHDBStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.DataStructureHDBTableFunctionModel;
import com.codbex.kronos.utils.HDBSynonymRemover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The Class ScalarFunctionManagerService.
 */
public class HDBScalarFunctionManagerService extends HDBTableFunctionManagerService {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBScalarFunctionManagerService.class);

  /**
   * The data structure scalar functions models.
   */
  private final Map<String, DataStructureHDBTableFunctionModel> scalarFunctionModels;

  /**
   * The scalar functions synchronized.
   */
  private final List<String> scalarFunctionsSynchronized;

  /**
   * Instantiates a new hdb scalar function manager service.
   */
  public HDBScalarFunctionManagerService() {
    this(new LinkedHashMap<>(), Collections.synchronizedList(new ArrayList<>()));
  }

  /**
   * Instantiates a new hdb scalar function manager service.
   *
   * @param dataStructureScalarFunctionModelsModels
   * @param scalarFunctionsSynchronized
   */
  public HDBScalarFunctionManagerService(Map<String, DataStructureHDBTableFunctionModel> dataStructureScalarFunctionModelsModels,
      List<String> scalarFunctionsSynchronized) {
    super(dataStructureScalarFunctionModelsModels, scalarFunctionsSynchronized);
    this.scalarFunctionModels = dataStructureScalarFunctionModelsModels;
    this.scalarFunctionsSynchronized = scalarFunctionsSynchronized;
  }

  /**
   * Synchronize runtime metadata.
   *
   * @param scalarFunctionModel the hdb scalar function model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBTableFunctionModel scalarFunctionModel) throws DataStructuresException {
    // TODO: Ommit double calling of finding the hdbScalarFunction by extracting it in

    // String schemaNameConcatTableFunctionName = scalarFunctionModel.getSchemaName() + "." + scalarFunctionModel.getName();

    if (!getDataStructuresCoreService().existsDataStructure(scalarFunctionModel.getLocation(), scalarFunctionModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(scalarFunctionModel.getLocation(), scalarFunctionModel.getName(), scalarFunctionModel.getHash(),
              scalarFunctionModel.getType());
      scalarFunctionModels.put(scalarFunctionModel.getName(), scalarFunctionModel);
      logger.info("Synchronized a new HDB Table Function file [{}] from location: {}", scalarFunctionModel.getName(),
          scalarFunctionModel.getLocation());
    } else {
      DataStructureHDBTableFunctionModel existing = getDataStructuresCoreService()
          .getDataStructure(scalarFunctionModel.getLocation(), scalarFunctionModel.getType());
      if (!scalarFunctionModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(scalarFunctionModel.getLocation(), scalarFunctionModel.getName(), scalarFunctionModel.getHash(),
                scalarFunctionModel.getType());
        scalarFunctionModels.put(scalarFunctionModel.getName(), scalarFunctionModel);
        logger.info("Synchronized a modified HDB Table Function file [{}] from location: {}", scalarFunctionModel.getName(),
            scalarFunctionModel.getLocation());
      }
    }
    if (!scalarFunctionsSynchronized.contains(scalarFunctionModel.getLocation())) {
      scalarFunctionsSynchronized.add(scalarFunctionModel.getLocation());
    }
  }

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_HDB_SCALAR_FUNCTION;
  }

}

