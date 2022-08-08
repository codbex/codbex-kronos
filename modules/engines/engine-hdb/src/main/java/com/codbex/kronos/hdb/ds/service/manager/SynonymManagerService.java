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
import com.codbex.kronos.hdb.ds.model.hdbsynonym.DataStructureHDBSynonymModel;
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

/**
 * The Class SynonymManagerService.
 */
public class SynonymManagerService extends AbstractDataStructureManagerService<DataStructureHDBSynonymModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(SynonymManagerService.class);
  
  /** The data structure synonym models. */
  private final Map<String, DataStructureHDBSynonymModel> dataStructureSynonymModels = new LinkedHashMap<>();
  
  /** The synonyms synchronized. */
  private final List<String> synonymsSynchronized = Collections.synchronizedList(new ArrayList<>());
  
  /** The synonym create processor. */
  private IHDBProcessor synonymCreateProcessor = new HDBSynonymCreateProcessor();
  
  /** The synonym drop processor. */
  private IHDBProcessor synonymDropProcessor = new HDBSynonymDropProcessor();

  /**
   * Synchronize runtime metadata.
   *
   * @param synonymModel the synonym model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBSynonymModel synonymModel) throws DataStructuresException {
    if (!getDataStructuresCoreService().existsDataStructure(synonymModel.getLocation(), synonymModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(synonymModel.getLocation(), synonymModel.getName(), synonymModel.getHash(), synonymModel.getType());
      dataStructureSynonymModels.put(synonymModel.getName(), synonymModel);
      logger.info("Synchronized a new Synonym file [{}] from location: {}", synonymModel.getName(), synonymModel.getLocation());
    } else {
      DataStructureHDBSynonymModel existing = getDataStructuresCoreService()
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

  /**
   * Creates the data structure.
   *
   * @param connection the connection
   * @param synonymModel the synonym model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean createDataStructure(Connection connection, DataStructureHDBSynonymModel synonymModel)
      throws SQLException {
	return this.synonymCreateProcessor.execute(connection, synonymModel);
  }

  /**
   * Drop data structure.
   *
   * @param connection the connection
   * @param synonymModel the synonym model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean dropDataStructure(Connection connection, DataStructureHDBSynonymModel synonymModel)
      throws SQLException {
	return this.synonymDropProcessor.execute(connection, synonymModel);
  }

  /**
   * Update data structure.
   *
   * @param connection the connection
   * @param synonymModel the synonym model
   * @return true, if successful
   * @throws SQLException the SQL exception
   * @throws OperationNotSupportedException the operation not supported exception
   */
  @Override
  public boolean updateDataStructure(Connection connection, DataStructureHDBSynonymModel synonymModel)
      throws SQLException, OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  /**
   * Gets the data structure models.
   *
   * @return the data structure models
   */
  @Override
  public Map<String, DataStructureHDBSynonymModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureSynonymModels);
  }

  /**
   * Gets the data structure synchronized.
   *
   * @return the data structure synchronized
   */
  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.synonymsSynchronized);
  }

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_SYNONYM;
  }

  /**
   * Clear cache.
   */
  @Override
  public void clearCache() {
    dataStructureSynonymModels.clear();
  }
}
