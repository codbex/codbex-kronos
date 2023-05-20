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
import com.codbex.kronos.hdb.ds.model.hdbview.DataStructureHDBViewModel;
import com.codbex.kronos.hdb.ds.processors.view.ViewCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.view.ViewDropProcessor;

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
 * The Class ViewManagerService.
 */
public class HDBViewManagerService extends AbstractDataStructureManagerService<DataStructureHDBViewModel> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBViewManagerService.class);

  /**
   * The data structure views models.
   */
  private final Map<String, DataStructureHDBViewModel> dataStructureHDBViewsModels = new LinkedHashMap<>();

  /**
   * The views synchronized.
   */
  private final List<String> hdbViewsSynchronized = Collections.synchronizedList(new ArrayList<>());

  /**
   * The view create processor.
   */
  private IHDBProcessor hdbViewCreateProcessor = new ViewCreateProcessor();

  /**
   * The view drop processor.
   */
  private IHDBProcessor hdbViewDropProcessor = new ViewDropProcessor();

  /**
   * Synchronize runtime metadata.
   *
   * @param viewModel the view model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBViewModel viewModel) throws DataStructuresException {
    if (!getDataStructuresCoreService().existsDataStructure(viewModel.getLocation(), viewModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(viewModel.getLocation(), viewModel.getName(), viewModel.getHash(), viewModel.getType());
      dataStructureHDBViewsModels.put(viewModel.getName(), viewModel);
      logger.info("Synchronized a new HDB View file [{}] from location: {}", viewModel.getName(), viewModel.getLocation());
    } else {
      DataStructureHDBViewModel existing = getDataStructuresCoreService().getDataStructure(viewModel.getLocation(), viewModel.getType());
      if (!viewModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(viewModel.getLocation(), viewModel.getName(), viewModel.getHash(), viewModel.getType());
        dataStructureHDBViewsModels.put(viewModel.getName(), viewModel);
        logger.info("Synchronized a modified HDB View file [{}] from location: {}", viewModel.getName(), viewModel.getLocation());
      }
    }
    if (!hdbViewsSynchronized.contains(viewModel.getLocation())) {
      hdbViewsSynchronized.add(viewModel.getLocation());
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
  public boolean createDataStructure(Connection connection, DataStructureHDBViewModel viewModel)
      throws SQLException {
    return this.hdbViewCreateProcessor.execute(connection, viewModel);
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
  public boolean dropDataStructure(Connection connection, DataStructureHDBViewModel viewModel)
      throws SQLException {
    return this.hdbViewDropProcessor.execute(connection, viewModel);
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
  public boolean updateDataStructure(Connection connection, DataStructureHDBViewModel viewModel)
      throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  /**
   * Gets the data structure models.
   *
   * @return the data structure models
   */
  @Override
  public Map<String, DataStructureHDBViewModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureHDBViewsModels);
  }

  /**
   * Gets the data structure synchronized.
   *
   * @return the data structure synchronized
   */
  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.hdbViewsSynchronized);
  }

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_HDB_VIEW;
  }

  /**
   * Clear cache.
   */
  @Override
  public void clearCache() {
    dataStructureHDBViewsModels.clear();
  }
}
