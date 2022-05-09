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
import com.codbex.kronos.hdb.ds.model.hdbsequence.XSKDataStructureHDBSequenceModel;
import com.codbex.kronos.hdb.ds.processors.hdbsequence.XSKHDBSequenceCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.hdbsequence.XSKHDBSequenceDropProcessor;
import com.codbex.kronos.hdb.ds.processors.hdbsequence.XSKHDBSequenceUpdateProcessor;

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

public class XSKHDBSequenceManagerService extends AbstractDataStructureManagerService<XSKDataStructureHDBSequenceModel> {

  private static final Logger logger = LoggerFactory.getLogger(XSKHDBSequenceManagerService.class);

  private final Map<String, XSKDataStructureHDBSequenceModel> dataStructureSequenceModels;
  private final List<String> sequencesSynchronized;

  private IXSKHdbProcessor xskHdbSequenceDropProcessor = new XSKHDBSequenceDropProcessor();
  private IXSKHdbProcessor xskHdbSequenceCreateProcessor = new XSKHDBSequenceCreateProcessor();
  private IXSKHdbProcessor xskHdbSequenceUpdateProcessor = new XSKHDBSequenceUpdateProcessor();

  public XSKHDBSequenceManagerService() {
    dataStructureSequenceModels = new LinkedHashMap<>();
    sequencesSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public Map<String, XSKDataStructureHDBSequenceModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureSequenceModels);
  }

  @Override
  public void synchronizeRuntimeMetadata(XSKDataStructureHDBSequenceModel hdbSequenceModel) throws XSKDataStructuresException {
    if (!getDataStructuresCoreService().existsDataStructure(hdbSequenceModel.getLocation(), hdbSequenceModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(hdbSequenceModel.getLocation(), hdbSequenceModel.getName(), hdbSequenceModel.getHash(),
              hdbSequenceModel.getType());
      dataStructureSequenceModels.put(hdbSequenceModel.getName(), hdbSequenceModel);
      logger.info("Synchronized a new Hdbsequence file [{}] from location: {}", hdbSequenceModel.getName(), hdbSequenceModel.getLocation());
    } else {
      XSKDataStructureHDBSequenceModel existing = getDataStructuresCoreService()
          .getDataStructure(hdbSequenceModel.getLocation(), hdbSequenceModel.getType());
      if (!hdbSequenceModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(hdbSequenceModel.getLocation(), hdbSequenceModel.getName(), hdbSequenceModel.getHash(),
                hdbSequenceModel.getType());
        dataStructureSequenceModels.put(hdbSequenceModel.getName(), hdbSequenceModel);
        logger.info("Synchronized a modified Hdbsequence file [{}] from location: {}", hdbSequenceModel.getName(),
            hdbSequenceModel.getLocation());
      }
    }
    if (!sequencesSynchronized.contains(hdbSequenceModel.getLocation())) {
      sequencesSynchronized.add(hdbSequenceModel.getLocation());
    }
  }

  @Override
  public boolean createDataStructure(Connection connection, XSKDataStructureHDBSequenceModel hdbSequenceModel)
      throws SQLException {
    return this.xskHdbSequenceCreateProcessor.execute(connection, hdbSequenceModel);
  }

  @Override
  public boolean dropDataStructure(Connection connection, XSKDataStructureHDBSequenceModel hdbSequenceModel)
      throws SQLException {
	return this.xskHdbSequenceDropProcessor.execute(connection, hdbSequenceModel);
  }

  @Override
  public boolean updateDataStructure(Connection connection, XSKDataStructureHDBSequenceModel hdbSequenceModel)
      throws SQLException, OperationNotSupportedException {
	return this.xskHdbSequenceUpdateProcessor.execute(connection, hdbSequenceModel);
  }

  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.sequencesSynchronized);
  }

  @Override
  public String getDataStructureType() {
    return IXSKDataStructureModel.FILE_EXTENSION_HDBSEQUENCE;
  }

  @Override
  public void clearCache() {
    dataStructureSequenceModels.clear();
  }
}
