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
import com.codbex.kronos.hdb.ds.model.hdbschema.DataStructureHDBSchemaModel;
import com.codbex.kronos.hdb.ds.processors.schema.SchemaCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.schema.SchemaDropProcessor;

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
 * The Class SchemaManagerService.
 */
public class HDBSchemaManagerService extends AbstractDataStructureManagerService<DataStructureHDBSchemaModel> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBSchemaManagerService.class);

  /**
   * The data structure schemas models.
   */
  private final Map<String, DataStructureHDBSchemaModel> schemaModels;

  /**
   * The schemas synchronized.
   */
  private final List<String> schemasSynchronized;

  /**
   * The hdb schema create processor.
   */
  private IHDBProcessor schemaCreateProcessor = new SchemaCreateProcessor();

  /**
   * The hdb schema drop processor.
   */
  private IHDBProcessor schemaDropProcessor = new SchemaDropProcessor();

  /**
   * Instantiates a new schema manager service.
   */
  public HDBSchemaManagerService() {
    schemaModels = new LinkedHashMap<>();
    schemasSynchronized = Collections.synchronizedList(new ArrayList<>());
  }

  /**
   * Synchronize runtime metadata.
   *
   * @param schemaModel the schema model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void synchronizeRuntimeMetadata(DataStructureHDBSchemaModel schemaModel) throws DataStructuresException {
    // TODO: Ommit double calling of finding the hdbSchema by extracting it in

    // String schemaNameConcatProcedureName = schemaModel.getSchemaName() + "." + schemaModel.getName();

    if (!getDataStructuresCoreService().existsDataStructure(schemaModel.getLocation(),
        schemaModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(schemaModel.getLocation(), schemaModel.getName(),
              schemaModel.getHash(), schemaModel.getType());
      schemaModels.put(schemaModel.getName(), schemaModel);
      logger.info("Synchronized a new HDB Schema file [{}] from location: {}", schemaModel.getName(),
          schemaModel.getLocation());
    } else {
      DataStructureHDBSchemaModel existing = getDataStructuresCoreService()
          .getDataStructure(schemaModel.getLocation(), schemaModel.getType());
      if (!schemaModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(schemaModel.getLocation(), schemaModel.getName(),
                schemaModel.getHash(), schemaModel.getType());
        schemaModels.put(schemaModel.getName(), schemaModel);
        logger.info("Synchronized a modified HDB Schema file [{}] from location: {}", schemaModel.getName(),
            schemaModel.getLocation());
      }
    }
    if (!schemasSynchronized.contains(schemaModel.getLocation())) {
      schemasSynchronized.add(schemaModel.getLocation());
    }
  }

  /**
   * Creates the data structure.
   *
   * @param connection  the connection
   * @param schemaModel the schema model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean createDataStructure(Connection connection, DataStructureHDBSchemaModel schemaModel)
      throws SQLException {
    return this.schemaCreateProcessor.execute(connection, schemaModel);
  }

  /**
   * Drop data structure.
   *
   * @param connection  the connection
   * @param schemaModel the schema model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean dropDataStructure(Connection connection, DataStructureHDBSchemaModel schemaModel)
      throws SQLException {
    return this.schemaDropProcessor.execute(connection, schemaModel);
  }

  /**
   * Update data structure.
   *
   * @param connection  the connection
   * @param schemaModel the schema model
   * @return true, if successful
   * @throws OperationNotSupportedException the operation not supported exception
   */
  @Override
  public boolean updateDataStructure(Connection connection, DataStructureHDBSchemaModel schemaModel)
      throws OperationNotSupportedException {
    throw new OperationNotSupportedException();
  }

  /**
   * Gets the data structure synchronized.
   *
   * @return the data structure synchronized
   */
  @Override
  public List<String> getDataStructureSynchronized() {
    return Collections.unmodifiableList(this.schemasSynchronized);
  }

  /**
   * Gets the data structure type.
   *
   * @return the data structure type
   */
  @Override
  public String getDataStructureType() {
    return IDataStructureModel.FILE_EXTENSION_HDB_SCHEMA;
  }

  /**
   * Clear cache.
   */
  @Override
  public void clearCache() {
    schemaModels.clear();
  }

  /**
   * Gets the data structure models.
   *
   * @return the data structure models
   */
  @Override
  public Map<String, DataStructureHDBSchemaModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.schemaModels);
  }
}
