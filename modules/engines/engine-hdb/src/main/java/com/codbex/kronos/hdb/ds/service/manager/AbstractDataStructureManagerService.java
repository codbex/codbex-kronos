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

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.api.IDataStructuresCoreService;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.service.DataStructuresCoreService;

/**
 * The Class AbstractDataStructureManagerService.
 *
 * @param <T> the generic type
 */
public abstract class AbstractDataStructureManagerService<T extends DataStructureModel> implements IDataStructureManager<T> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(AbstractDataStructureManagerService.class);

  /** The data structures core service. */
  private IDataStructuresCoreService dataStructuresCoreService = new DataStructuresCoreService();

  /**
   * Cleanup.
   *
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void cleanup() throws DataStructuresException {
    List<String> dtLocations = dataStructuresCoreService.getDataStructuresByType(getDataStructureType()).stream()
        .map(DataStructureModel::getLocation)
        .filter(location -> !this.getDataStructureSynchronized().contains(location))
        .collect(Collectors.toList());

    for (String dtLocation :
        dtLocations) {
      dataStructuresCoreService.removeDataStructure(dtLocation);
    }
  }

  /**
   * Gets the data structures core service.
   *
   * @return the data structures core service
   */
  public IDataStructuresCoreService getDataStructuresCoreService() {
    return dataStructuresCoreService;
  }

  /**
   * Sets the data structures core service.
   *
   * @param dataStructuresCoreService the new data structures core service
   */
  public void setDataStructuresCoreService(IDataStructuresCoreService dataStructuresCoreService) {
    this.dataStructuresCoreService = dataStructuresCoreService;
  }

  /**
   * Synchronize parsed by root metadata.
   *
   * @param dsModel the data structure model
   * @throws DataStructuresException the data structures exception
   */
  public void synchronizeParsedByRootMetadata(T dsModel) throws DataStructuresException {
    if (!getDataStructuresCoreService().existsDataStructure(dsModel.getLocation(), dsModel.getType())) {
      dataStructuresCoreService
          .createDataStructure(dsModel.getLocation(), dsModel.getName(), dsModel.getHash(), dsModel.getType());
      logger.info("Root artifact synchronized a new data structure file [{}] from location: {}", dsModel.getName(), dsModel.getLocation());
    }
  }

  /**
   * Exists artifact metadata.
   *
   * @param dsModel the data structure model
   * @return true, if successful
   * @throws DataStructuresException the data structures exception
   */
  public boolean existsArtifactMetadata(T dsModel) throws DataStructuresException {
    return dataStructuresCoreService.existsDataStructureByLocationAndHash(dsModel.getLocation(), dsModel.getHash(), dsModel.getType());
  }
}
