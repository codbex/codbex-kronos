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
import com.codbex.kronos.hdb.ds.api.IXSKHdbProcessor;
import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;
import com.codbex.kronos.hdb.ds.model.hdbview.XSKDataStructureHDBViewModel;
import com.codbex.kronos.hdb.ds.processors.view.XSKViewCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.view.XSKViewDropProcessor;

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

public class XSKViewManagerService extends AbstractDataStructureManagerService<XSKDataStructureHDBViewModel> {

  private static final Logger logger = LoggerFactory.getLogger(XSKViewManagerService.class);

  private final Map<String, XSKDataStructureHDBViewModel> dataStructureViewsModels = new LinkedHashMap<>();
  private final List<String> viewsSynchronized = Collections.synchronizedList(new ArrayList<>());

  private IXSKHdbProcessor xskViewCreateProcessor = new XSKViewCreateProcessor();
  private IXSKHdbProcessor xskViewDropProcessor = new XSKViewDropProcessor();

  @Override
  public void synchronizeRuntimeMetadata(XSKDataStructureHDBViewModel viewModel) throws XSKDataStructuresException {
    if (!getDataStructuresCoreService().existsDataStructure(viewModel.getLocation(), viewModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(viewModel.getLocation(), viewModel.getName(), viewModel.getHash(), viewModel.getType());
      dataStructureViewsModels.put(viewModel.getName(), viewModel);
      logger.info("Synchronized a new View file [{}] from location: {}", viewModel.getName(), viewModel.getLocation());
    } else {
      XSKDataStructureHDBViewModel existing = getDataStructuresCoreService().getDataStructure(viewModel.getLocation(), viewModel.getType());
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
  public boolean createDataStructure(Connection connection, XSKDataStructureHDBViewModel viewModel)
      throws SQLException {
    return this.xskViewCreateProcessor.execute(connection, viewModel);
  }

  @Override
  public boolean dropDataStructure(Connection connection, XSKDataStructureHDBViewModel viewModel)
      throws SQLException {
    return this.xskViewDropProcessor.execute(connection, viewModel);
  }

  @Override
  public boolean updateDataStructure(Connection connection, XSKDataStructureHDBViewModel viewModel)
      throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  @Override
  public Map<String, XSKDataStructureHDBViewModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureViewsModels);
  }

  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.viewsSynchronized);
  }

  @Override
  public String getDataStructureType() {
    return IXSKDataStructureModel.FILE_EXTENSION_VIEW;
  }

  @Override
  public void clearCache() {
    dataStructureViewsModels.clear();
  }
}
