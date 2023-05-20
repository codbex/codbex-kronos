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
import com.codbex.kronos.hdb.ds.processors.sequence.SequenceCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.sequence.SequenceDropProcessor;
import com.codbex.kronos.hdb.ds.processors.sequence.SequenceUpdateProcessor;

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
 * The Class HDBSequenceManagerService.
 */
public class HDBSequenceManagerService extends AbstractDataStructureManagerService<DataStructureHDBSequenceModel> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBSequenceManagerService.class);

  /**
   * The data structure sequence models.
   */
  private final Map<String, DataStructureHDBSequenceModel> sequenceModels;

  /**
   * The sequences synchronized.
   */
  private final List<String> sequencesSynchronized;

  /**
   * The hdb sequence drop processor.
   */
  private IHDBProcessor sequenceDropProcessor = new SequenceDropProcessor();

  /**
   * The hdb sequence create processor.
   */
  private IHDBProcessor sequenceCreateProcessor = new SequenceCreateProcessor();

  /**
   * The hdb sequence update processor.
   */
  private IHDBProcessor sequenceUpdateProcessor = new SequenceUpdateProcessor();

  /**
   * Instantiates a new HDB sequence manager service.
   */
  public HDBSequenceManagerService() {
    sequenceModels = new LinkedHashMap<>();
    sequencesSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  /**
   * Gets the data structure models.
   *
   * @return the data structure models
   */
  @Override
  public Map<String, DataStructureHDBSequenceModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.sequenceModels);
  }

  /**
   * Synchronize runtime metadata.
   *
   * @param sequenceModel the hdb sequence model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBSequenceModel sequenceModel) throws DataStructuresException {
    if (!getDataStructuresCoreService().existsDataStructure(sequenceModel.getLocation(),
        sequenceModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(sequenceModel.getLocation(), sequenceModel.getName(),
              sequenceModel.getHash(),
              sequenceModel.getType());
      sequenceModels.put(sequenceModel.getName(), sequenceModel);
      logger.info("Synchronized a new HDB Sequence file [{}] from location: {}", sequenceModel.getName(),
          sequenceModel.getLocation());
    } else {
      DataStructureHDBSequenceModel existing = getDataStructuresCoreService()
          .getDataStructure(sequenceModel.getLocation(), sequenceModel.getType());
      if (!sequenceModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(sequenceModel.getLocation(), sequenceModel.getName(),
                sequenceModel.getHash(),
                sequenceModel.getType());
        sequenceModels.put(sequenceModel.getName(), sequenceModel);
        logger.info("Synchronized a modified HDB Sequence file [{}] from location: {}", sequenceModel.getName(),
            sequenceModel.getLocation());
      }
    }
    if (!sequencesSynchronized.contains(sequenceModel.getLocation())) {
      sequencesSynchronized.add(sequenceModel.getLocation());
    }
  }

  /**
   * Creates the data structure.
   *
   * @param connection       the connection
   * @param hdbSequenceModel the hdb sequence model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean createDataStructure(Connection connection, DataStructureHDBSequenceModel hdbSequenceModel)
      throws SQLException {
    return this.sequenceCreateProcessor.execute(connection, hdbSequenceModel);
  }

  /**
   * Drop data structure.
   *
   * @param connection       the connection
   * @param hdbSequenceModel the hdb sequence model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean dropDataStructure(Connection connection, DataStructureHDBSequenceModel hdbSequenceModel)
      throws SQLException {
    return this.sequenceDropProcessor.execute(connection, hdbSequenceModel);
  }

  /**
   * Update data structure.
   *
   * @param connection       the connection
   * @param hdbSequenceModel the hdb sequence model
   * @return true, if successful
   * @throws SQLException                   the SQL exception
   * @throws OperationNotSupportedException the operation not supported exception
   */
  @Override
  public boolean updateDataStructure(Connection connection, DataStructureHDBSequenceModel hdbSequenceModel)
      throws SQLException, OperationNotSupportedException {
    return this.sequenceUpdateProcessor.execute(connection, hdbSequenceModel);
  }

  /**
   * Gets the data structure synchronized.
   *
   * @return the data structure synchronized
   */
  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.sequencesSynchronized);
  }

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_HDB_SEQUENCE;
  }

  /**
   * Clear cache.
   */
  @Override
  public void clearCache() {
    sequenceModels.clear();
  }
}
