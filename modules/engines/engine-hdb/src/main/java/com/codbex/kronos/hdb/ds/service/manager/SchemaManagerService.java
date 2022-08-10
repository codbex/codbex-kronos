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
import com.codbex.kronos.hdb.ds.processors.hdbschema.HDBSchemaCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.hdbschema.HDBSchemaDropProcessor;

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
public class SchemaManagerService extends AbstractDataStructureManagerService<DataStructureHDBSchemaModel> {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(SchemaManagerService.class);
  
  /** The data structure schemas models. */
  private final Map<String, DataStructureHDBSchemaModel> dataStructureSchemasModels;
  
  /** The schemas synchronized. */
  private final List<String> schemasSynchronized;
  
  /** The hdb schema create processor. */
  private IHDBProcessor hdbSchemaCreateProcessor = new HDBSchemaCreateProcessor();
  
  /** The hdb schema drop processor. */
  private IHDBProcessor hdbSchemaDropProcessor = new HDBSchemaDropProcessor();

  /**
   * Instantiates a new schema manager service.
   */
  public SchemaManagerService() {
    dataStructureSchemasModels = new LinkedHashMap<>();
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
    // TODO: ommit double calling of finding the hdbProcedure by extracting it in
    // variable
    // String schemaNameConcatProcedureName = hdbProcedure.getSchemaName() + "." +
    // hdbProcedure.getName();
    //logger.info("here");
    if (!getDataStructuresCoreService().existsDataStructure(schemaModel.getLocation(), schemaModel.getType())) {
      getDataStructuresCoreService()
          .createDataStructure(schemaModel.getLocation(), schemaModel.getName(), schemaModel.getHash(), schemaModel.getType());
      dataStructureSchemasModels.put(schemaModel.getName(), schemaModel);
      logger.info("Synchronized a new HDB Schema file [{}] from location: {}", schemaModel.getName(), schemaModel.getLocation());
    } else {
      DataStructureHDBSchemaModel existing = getDataStructuresCoreService()
          .getDataStructure(schemaModel.getLocation(), schemaModel.getType());
      if (!schemaModel.equals(existing)) {
        getDataStructuresCoreService()
            .updateDataStructure(schemaModel.getLocation(), schemaModel.getName(), schemaModel.getHash(), schemaModel.getType());
        dataStructureSchemasModels.put(schemaModel.getName(), schemaModel);
        logger.info("Synchronized a modified HDB Schema file [{}] from location: {}", schemaModel.getName(), schemaModel.getLocation());
      }
    }
    if (!schemasSynchronized.contains(schemaModel.getLocation())) {
      schemasSynchronized.add(schemaModel.getLocation());
    }
  }

  /**
   * Creates the data structure.
   *
   * @param connection the connection
   * @param schemaModel the schema model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean createDataStructure(Connection connection, DataStructureHDBSchemaModel schemaModel)
      throws SQLException {
	return this.hdbSchemaCreateProcessor.execute(connection, schemaModel);
  }

  /**
   * Drop data structure.
   *
   * @param connection the connection
   * @param schemaModel the schema model
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  @Override
  public boolean dropDataStructure(Connection connection, DataStructureHDBSchemaModel schemaModel)
      throws SQLException {
	return this.hdbSchemaDropProcessor.execute(connection, schemaModel);
  }

  /**
   * Update data structure.
   *
   * @param connection the connection
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
    return IDataStructureModel.FILE_EXTENSION_HDBSCHEMA;
  }

  /**
   * Clear cache.
   */
  @Override
  public void clearCache() {
    dataStructureSchemasModels.clear();
  }

  /**
   * Gets the data structure models.
   *
   * @return the data structure models
   */
  @Override
  public Map<String, DataStructureHDBSchemaModel> getDataStructureModels() {
    return Collections.unmodifiableMap(this.dataStructureSchemasModels);
  }
}
