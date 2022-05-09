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
import com.codbex.kronos.hdb.ds.model.hdbsynonym.XSKDataStructureHDBSynonymModel;
import com.codbex.kronos.hdb.ds.processors.synonym.HDBSynonymCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.synonym.HDBSynonymDropProcessor;

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

public class XSKSynonymManagerService extends AbstractDataStructureManagerService<XSKDataStructureHDBSynonymModel> {

  private static final Logger logger = LoggerFactory.getLogger(XSKSynonymManagerService.class);
  private final Map<String, XSKDataStructureHDBSynonymModel> dataStructureSynonymModels = new LinkedHashMap<>();
  private final List<String> synonymsSynchronized = Collections.synchronizedList(new ArrayList<>());
  private IXSKHdbProcessor xskSynonymCreateProcessor = new HDBSynonymCreateProcessor();
  private IXSKHdbProcessor xskSynonymDropProcessor = new HDBSynonymDropProcessor();

  @Override
  public void synchronizeRuntimeMetadata(XSKDataStructureHDBSynonymModel synonymModel) throws XSKDataStructuresException {
    if (!getDataStructuresCoreService().existsDataStructure(synonymModel.getLocation(), synonymModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(synonymModel.getLocation(), synonymModel.getName(), synonymModel.getHash(), synonymModel.getType());
      dataStructureSynonymModels.put(synonymModel.getName(), synonymModel);
      logger.info("Synchronized a new Synonym file [{}] from location: {}", synonymModel.getName(), synonymModel.getLocation());
    } else {
      XSKDataStructureHDBSynonymModel existing = getDataStructuresCoreService()
          .getDataStructure(synonymModel.getLocation(), synonymModel.getType());
      if (!synonymModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(synonymModel.getLocation(), synonymModel.getName(), synonymModel.getHash(), synonymModel.getType());
        dataStructureSynonymModels.put(synonymModel.getName(), synonymModel);
        logger.info("Synchronized a modified Synonym file [{}] from location: {}", synonymModel.getName(), synonymModel.getLocation());
      }
    }
    if (!synonymsSynchronized.contains(synonymModel.getLocation())) {
      synonymsSynchronized.add(synonymModel.getLocation());
    }
  }

  @Override
  public boolean createDataStructure(Connection connection, XSKDataStructureHDBSynonymModel synonymModel)
      throws SQLException {
	return this.xskSynonymCreateProcessor.execute(connection, synonymModel);
  }

  @Override
  public boolean dropDataStructure(Connection connection, XSKDataStructureHDBSynonymModel synonymModel)
      throws SQLException {
	return this.xskSynonymDropProcessor.execute(connection, synonymModel);
  }

  @Override
  public boolean updateDataStructure(Connection connection, XSKDataStructureHDBSynonymModel synonymModel)
      throws SQLException, OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  @Override
  public Map<String, XSKDataStructureHDBSynonymModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureSynonymModels);
  }

  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.synonymsSynchronized);
  }

  @Override
  public String getDataStructureType() {
    return IXSKDataStructureModel.FILE_EXTENSION_SYNONYM;
  }

  @Override
  public void clearCache() {
    dataStructureSynonymModels.clear();
  }
}
