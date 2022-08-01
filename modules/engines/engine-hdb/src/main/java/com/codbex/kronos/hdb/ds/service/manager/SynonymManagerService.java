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
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDataStructureModel;
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

public class SynonymManagerService extends AbstractDataStructureManagerService<HDBSynonymDataStructureModel> {

  private static final Logger logger = LoggerFactory.getLogger(SynonymManagerService.class);
  private final Map<String, HDBSynonymDataStructureModel> dataStructureSynonymModels = new LinkedHashMap<>();
  private final List<String> synonymsSynchronized = Collections.synchronizedList(new ArrayList<>());
  private IHDBProcessor synonymCreateProcessor = new HDBSynonymCreateProcessor();
  private IHDBProcessor synonymDropProcessor = new HDBSynonymDropProcessor();

  @Override
  public void synchronizeRuntimeMetadata(HDBSynonymDataStructureModel synonymModel) throws DataStructuresException {
    if (!getDataStructuresCoreService().existsDataStructure(synonymModel.getLocation(), synonymModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(synonymModel.getLocation(), synonymModel.getName(), synonymModel.getHash(), synonymModel.getType());
      dataStructureSynonymModels.put(synonymModel.getName(), synonymModel);
      logger.info("Synchronized a new Synonym file [{}] from location: {}", synonymModel.getName(), synonymModel.getLocation());
    } else {
      HDBSynonymDataStructureModel existing = getDataStructuresCoreService()
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
  public boolean createDataStructure(Connection connection, HDBSynonymDataStructureModel synonymModel)
      throws SQLException {
	return this.synonymCreateProcessor.execute(connection, synonymModel);
  }

  @Override
  public boolean dropDataStructure(Connection connection, HDBSynonymDataStructureModel synonymModel)
      throws SQLException {
	return this.synonymDropProcessor.execute(connection, synonymModel);
  }

  @Override
  public boolean updateDataStructure(Connection connection, HDBSynonymDataStructureModel synonymModel)
      throws SQLException, OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  @Override
  public Map<String, HDBSynonymDataStructureModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureSynonymModels);
  }

  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.synonymsSynchronized);
  }

  @Override
  public String getDataStructureType() {
    return HDBDataStructureModel.FILE_EXTENSION_SYNONYM;
  }

  @Override
  public void clearCache() {
    dataStructureSynonymModels.clear();
  }
}
