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
import com.codbex.kronos.hdb.ds.model.hdbsequence.DataStructureHDBSequenceModel;
import com.codbex.kronos.hdb.ds.processors.hdbsequence.HDBSequenceCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.hdbsequence.HDBSequenceDropProcessor;
import com.codbex.kronos.hdb.ds.processors.hdbsequence.HDBSequenceUpdateProcessor;

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

public class HDBSequenceManagerService extends AbstractDataStructureManagerService<DataStructureHDBSequenceModel> {

  private static final Logger logger = LoggerFactory.getLogger(HDBSequenceManagerService.class);

  private final Map<String, DataStructureHDBSequenceModel> dataStructureSequenceModels;
  private final List<String> sequencesSynchronized;

  private IHDBProcessor hdbSequenceDropProcessor = new HDBSequenceDropProcessor();
  private IHDBProcessor hdbSequenceCreateProcessor = new HDBSequenceCreateProcessor();
  private IHDBProcessor hdbSequenceUpdateProcessor = new HDBSequenceUpdateProcessor();

  public HDBSequenceManagerService() {
    dataStructureSequenceModels = new LinkedHashMap<>();
    sequencesSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  @Override
  public Map<String, DataStructureHDBSequenceModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureSequenceModels);
  }

  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBSequenceModel hdbSequenceModel) throws DataStructuresException {
    if (!getDataStructuresCoreService().existsDataStructure(hdbSequenceModel.getLocation(), hdbSequenceModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(hdbSequenceModel.getLocation(), hdbSequenceModel.getName(), hdbSequenceModel.getHash(),
              hdbSequenceModel.getType());
      dataStructureSequenceModels.put(hdbSequenceModel.getName(), hdbSequenceModel);
      logger.info("Synchronized a new Hdbsequence file [{}] from location: {}", hdbSequenceModel.getName(), hdbSequenceModel.getLocation());
    } else {
      DataStructureHDBSequenceModel existing = getDataStructuresCoreService()
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
  public boolean createDataStructure(Connection connection, DataStructureHDBSequenceModel hdbSequenceModel)
      throws SQLException {
    return this.hdbSequenceCreateProcessor.execute(connection, hdbSequenceModel);
  }

  @Override
  public boolean dropDataStructure(Connection connection, DataStructureHDBSequenceModel hdbSequenceModel)
      throws SQLException {
	return this.hdbSequenceDropProcessor.execute(connection, hdbSequenceModel);
  }

  @Override
  public boolean updateDataStructure(Connection connection, DataStructureHDBSequenceModel hdbSequenceModel)
      throws SQLException, OperationNotSupportedException {
	return this.hdbSequenceUpdateProcessor.execute(connection, hdbSequenceModel);
  }

  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.sequencesSynchronized);
  }

  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_HDBSEQUENCE;
  }

  @Override
  public void clearCache() {
    dataStructureSequenceModels.clear();
  }
}
