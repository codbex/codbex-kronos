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
import com.codbex.kronos.hdb.ds.model.hdbview.HDBViewDataStructureModel;
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

public class ViewManagerService extends AbstractDataStructureManagerService<HDBViewDataStructureModel> {

  private static final Logger logger = LoggerFactory.getLogger(ViewManagerService.class);

  private final Map<String, HDBViewDataStructureModel> dataStructureViewsModels = new LinkedHashMap<>();
  private final List<String> viewsSynchronized = Collections.synchronizedList(new ArrayList<>());

  private IHDBProcessor viewCreateProcessor = new ViewCreateProcessor();
  private IHDBProcessor viewDropProcessor = new ViewDropProcessor();

  @Override
  public void synchronizeRuntimeMetadata(HDBViewDataStructureModel viewModel) throws DataStructuresException {
    if (!getDataStructuresCoreService().existsDataStructure(viewModel.getLocation(), viewModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(viewModel.getLocation(), viewModel.getName(), viewModel.getHash(), viewModel.getType());
      dataStructureViewsModels.put(viewModel.getName(), viewModel);
      logger.info("Synchronized a new View file [{}] from location: {}", viewModel.getName(), viewModel.getLocation());
    } else {
      HDBViewDataStructureModel existing = getDataStructuresCoreService().getDataStructure(viewModel.getLocation(), viewModel.getType());
      if (!viewModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(viewModel.getLocation(), viewModel.getName(), viewModel.getHash(), viewModel.getType());
        dataStructureViewsModels.put(viewModel.getName(), viewModel);
        logger.info("Synchronized a modified View file [{}] from location: {}", viewModel.getName(), viewModel.getLocation());
      }
    }
    if (!viewsSynchronized.contains(viewModel.getLocation())) {
      viewsSynchronized.add(viewModel.getLocation());
    }
  }

  @Override
  public boolean createDataStructure(Connection connection, HDBViewDataStructureModel viewModel)
      throws SQLException {
    return this.viewCreateProcessor.execute(connection, viewModel);
  }

  @Override
  public boolean dropDataStructure(Connection connection, HDBViewDataStructureModel viewModel)
      throws SQLException {
    return this.viewDropProcessor.execute(connection, viewModel);
  }

  @Override
  public boolean updateDataStructure(Connection connection, HDBViewDataStructureModel viewModel)
      throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  @Override
  public Map<String, HDBViewDataStructureModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureViewsModels);
  }

  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.viewsSynchronized);
  }

  @Override
  public String getDataStructureType() {
    return HDBDataStructureModel.FILE_EXTENSION_VIEW;
  }

  @Override
  public void clearCache() {
    dataStructureViewsModels.clear();
  }
}
