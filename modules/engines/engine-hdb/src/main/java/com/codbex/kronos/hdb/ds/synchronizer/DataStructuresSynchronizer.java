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
package com.codbex.kronos.hdb.ds.synchronizer;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer;
import org.eclipse.dirigible.core.scheduler.api.IOrderedSynchronizerContribution;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;
import org.eclipse.dirigible.core.scheduler.api.SynchronizationException;
import org.eclipse.dirigible.repository.api.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.HDBDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.facade.IHDBCoreSynchronizationFacade;
import com.codbex.kronos.hdb.ds.facade.HDBCoreSynchronizationFacade;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbdd.EntitiesDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbprocedure.HDBProcedureDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbschema.HDBSchemaDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.HDBTableFunctionDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.HDBTableTypeDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbview.HDBViewDataStructureModel;
import com.codbex.kronos.hdb.ds.service.parser.ICoreParserService;
import com.codbex.kronos.hdb.ds.service.parser.CoreParserService;
import com.codbex.kronos.utils.CommonsConstants;

/**
 * The Data Structures Synchronizer.
 */
public class DataStructuresSynchronizer extends AbstractSynchronizer implements IOrderedSynchronizerContribution {

  private static final Logger logger = LoggerFactory.getLogger(DataStructuresSynchronizer.class);

  private static final Map<String, EntitiesDataStructureModel> ENTITIES_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  private static final Map<String, HDBTableDataStructureModel> TABLES_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  private static final Map<String, HDBViewDataStructureModel> VIEWS_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  private static final Map<String, HDBProcedureDataStructureModel> PROCEDURES_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  private static final Map<String, HDBTableFunctionDataStructureModel> TABLEFUNCTIONS_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  private static final Map<String, HDBSchemaDataStructureModel> SCHEMAS_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  private static final Map<String, HDBSynonymDataStructureModel> SYNONYMS_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  private static final Map<String, HDBTableTypeDataStructureModel> TABLE_TYPES_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  private final String SYNCHRONIZER_NAME = this.getClass().getCanonicalName();
  private ICoreParserService coreParserService = new CoreParserService();
  private IHDBCoreSynchronizationFacade hdbCoreFacade = new HDBCoreSynchronizationFacade();

  /**
   * Force synchronization.
   */
  public static final void forceSynchronization() {
    DataStructuresSynchronizer dataStructureSynchronizer = new DataStructuresSynchronizer();
    dataStructureSynchronizer.synchronize();
  }

  /**
   * Register predelivered entities files.
   *
   * @param contentPath the data path
   * @throws Exception
   */
  public void registerPredeliveredEntities(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(HDBDataStructureModel.FILE_EXTENSION_ENTITIES, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    EntitiesDataStructureModel model = (EntitiesDataStructureModel) coreParserService
        .parseDataStructure(parametersModel);
    ENTITIES_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered table files.
   *
   * @param contentPath the data path
   * @throws Exception
   */
  public void registerPredeliveredTable(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(HDBDataStructureModel.FILE_EXTENSION_TABLE, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    HDBTableDataStructureModel model = (HDBTableDataStructureModel) coreParserService
        .parseDataStructure(parametersModel);
    TABLES_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered view files.
   *
   * @param contentPath the data path
   * @throws Exception
   */
  public void registerPredeliveredView(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(HDBDataStructureModel.FILE_EXTENSION_VIEW, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    HDBViewDataStructureModel model = (HDBViewDataStructureModel) coreParserService
        .parseDataStructure(parametersModel);
    VIEWS_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered view files.
   *
   * @param contentPath the data path
   * @throws Exception
   */
  public void registerPredeliveredSynonym(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(HDBDataStructureModel.FILE_EXTENSION_SYNONYM, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    HDBSynonymDataStructureModel model = (HDBSynonymDataStructureModel) coreParserService
        .parseDataStructure(parametersModel);
    SYNONYMS_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered .hdbprocedure files.
   *
   * @param contentPath the data path
   * @throws IOException                in case of an error
   * @throws DataStructuresException in case of an error
   */
  public void registerPredeliveredHDBProcedure(String contentPath)
      throws IOException, DataStructuresException, ArtifactParserException {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel = new DataStructureParametersModel(HDBDataStructureModel.FILE_EXTENSION_HDBPROCEDURE, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    HDBProcedureDataStructureModel model = (HDBProcedureDataStructureModel) coreParserService.parseDataStructure(parametersModel);
    PROCEDURES_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered .hdbtablefunction files.
   *
   * @param contentPath the data path
   * @throws IOException in case of an error
   */
  public void registerPredeliveredHDBTableFunction(String contentPath)
      throws IOException, DataStructuresException, ArtifactParserException {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel = new DataStructureParametersModel(HDBDataStructureModel.FILE_EXTENSION_HDBTABLEFUNCTION, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    HDBTableFunctionDataStructureModel model = (HDBTableFunctionDataStructureModel) coreParserService.parseDataStructure(parametersModel);
    TABLEFUNCTIONS_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered *.hdbschema files.
   *
   * @param contentPath the data path
   * @throws IOException                in case of an error
   * @throws DataStructuresException in case of an error
   */
  public void registerPredeliveredHDBSchema(String contentPath)
      throws IOException, DataStructuresException, ArtifactParserException {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel = new DataStructureParametersModel(HDBDataStructureModel.FILE_EXTENSION_HDBSCHEMA, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    HDBSchemaDataStructureModel model = (HDBSchemaDataStructureModel) coreParserService.parseDataStructure(parametersModel);
    SCHEMAS_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered *.hdbstructure files.
   *
   * @param contentPath the data path
   * @throws Exception
   */
  public void registerPredeliveredHDBStructure(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(HDBDataStructureModel.FILE_EXTENSION_STRUCTURE, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    HDBTableTypeDataStructureModel model = (HDBTableTypeDataStructureModel) coreParserService
        .parseDataStructure(parametersModel);
    TABLE_TYPES_PREDELIVERED.put(contentPath, model);
  }

  private String loadResourceContent(String modelPath) throws IOException {
    InputStream in = DataStructuresSynchronizer.class.getResourceAsStream(modelPath);
    try {
      String content = IOUtils.toString(in, StandardCharsets.UTF_8);
      return content;
    } finally {
      if (in != null) {
        in.close();
      }
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.dirigible.core.scheduler.api.ISynchronizer#synchronize()
   */
  @Override
  public void synchronize() {
    synchronized (DataStructuresSynchronizer.class) {
      logger.trace("Synchronizing Kronos Data Structures...");
      try {
        startSynchronization(SYNCHRONIZER_NAME);
        clearCache();
        synchronizePredelivered();
        synchronizeRegistry();
        hdbCoreFacade.updateEntities();

        int immutableEntitiesCount = ENTITIES_PREDELIVERED.size();
        int immutableTablesCount = TABLES_PREDELIVERED.size();
        int immutableViewsCount = VIEWS_PREDELIVERED.size();
        int immutableProceduresCount = PROCEDURES_PREDELIVERED.size();
        int immutableFunctionsCount = TABLEFUNCTIONS_PREDELIVERED.size();
        int immutableSchemasCount = SCHEMAS_PREDELIVERED.size();
        int immutableSynonymsCount = SYNONYMS_PREDELIVERED.size();
        int immutableTableTypesCount = TABLE_TYPES_PREDELIVERED.size();

//				int mutableEntitiesCount = ENTITIES_SYNCHRONIZED.size();
//				int mutableTablesCount = TABLES_SYNCHRONIZED.size();
//				int mutableViewsCount = VIEWS_SYNCHRONIZED.size();
//				int mutableProceduresCount = PROCEDURES_SYNCHRONIZED.size();
//				int mutableFunctionsCount = TABLEFUNCTIONS_SYNCHRONIZED.size();
//				int mutableSchemasCount = SCHEMAS_SYNCHRONIZED.size();
//				int mutableHDICount = HDI_SYNCHRONIZED.size();

//                cleanup();
        clearCache();

        successfulSynchronization(SYNCHRONIZER_NAME,
            format("Immutable: [Entities: {0}, Tables: {1}, Views: {2}, Procedures: {3}, Functions: {4}, Schemas: {5}, Synonyms: {6}], Table Types: {7}],"
                    + "Mutable: [Entities: {7}, Tables: {8}, Views: {9}, Procedures: {10}, Functions: {11}, Schemas: {12}, HDI: {13}, Synonyms: {13}]",
                immutableEntitiesCount, immutableTablesCount, immutableViewsCount, immutableProceduresCount, immutableFunctionsCount,
                immutableSchemasCount));
//						mutableEntitiesCount, mutableTablesCount, mutableViewsCount, mutableProceduresCount, mutableFunctionsCount, mutableSchemasCount, mutableHDICount, immutableSynonymsCount));
      } catch (Exception e) {
        logger.error("Synchronizing process for Data Structures failed.", e);
        try {
          failedSynchronization(SYNCHRONIZER_NAME, e.getMessage());
        } catch (SchedulerException e1) {
          logger.error("Synchronizing process for HDB Data Structures files failed in registering the state log.", e);
        }
      }
      logger.trace("Done synchronizing Kronos Data Structures.");
    }
  }

  /**
   * Clear cache.
   */
  private void clearCache() {
    hdbCoreFacade.clearCache();
  }

  /**
   * Synchronize predelivered.
   *
   * @throws SynchronizationException the synchronization exception
   */
  private void synchronizePredelivered() throws SynchronizationException {

    logger.trace("Synchronizing predelivered Kronos Data Structures...");

    // HDBSchemas
    logger.trace("Synchronizing predelivered HDB Schemas...");
    for (HDBSchemaDataStructureModel hdbSchema : SCHEMAS_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(HDBDataStructureModel.FILE_EXTENSION_HDBSCHEMA, hdbSchema);
      } catch (Exception e) {
        logger.error(format("Update hdbschema [{0}] skipped due to an error: {1}", hdbSchema, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDB Schemas.");

    // Entities
    logger.trace("Synchronizing predelivered Entities...");
    for (EntitiesDataStructureModel entity : ENTITIES_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(HDBDataStructureModel.FILE_EXTENSION_ENTITIES, entity);
      } catch (Exception e) {
        logger.error(format("Update entities [{0}] skipped due to an error: {1}", entity, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered Entities.");

    // Tables
    logger.trace("Synchronizing predelivered Tables...");
    for (HDBTableDataStructureModel table : TABLES_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(HDBDataStructureModel.FILE_EXTENSION_TABLE, table);
      } catch (Exception e) {
        logger.error(format("Update tables [{0}] skipped due to an error: {1}", table, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered Tables.");

    // Views
    logger.trace("Synchronizing predelivered Views...");
    for (HDBViewDataStructureModel view : VIEWS_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(HDBDataStructureModel.FILE_EXTENSION_VIEW, view);
      } catch (Exception e) {
        logger.error(format("Update views [{0}] skipped due to an error: {1}", view, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered Views.");

    // HDBProcedures
    logger.trace("Synchronizing predelivered HDB Procedures...");
    for (HDBProcedureDataStructureModel hdbProcedure : PROCEDURES_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(HDBDataStructureModel.FILE_EXTENSION_HDBPROCEDURE, hdbProcedure);
      } catch (Exception e) {
        logger.error(format("Update hdbprocedure [{0}] skipped due to an error: {1}", hdbProcedure, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDB Procedures.");

    // HDBTableFunctions
    logger.trace("Synchronizing predelivered HDB Table Functions...");
    for (HDBTableFunctionDataStructureModel hdbTableFunction : TABLEFUNCTIONS_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(HDBDataStructureModel.FILE_EXTENSION_HDBTABLEFUNCTION, hdbTableFunction);
      } catch (Exception e) {
        logger.error(format("Update hdbtablefunction [{0}] skipped due to an error: {1}", hdbTableFunction, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDB Table Function.");

    // Synonyms
    logger.trace("Synchronizing predelivered Synonyms...");
    for (HDBSynonymDataStructureModel synonym : SYNONYMS_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(HDBDataStructureModel.FILE_EXTENSION_SYNONYM, synonym);
      } catch (Exception e) {
        logger.error(format("Update synonyms [{0}] skipped due to an error: {1}", synonym, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered Synonyms.");

    // Table Types
    logger.trace("Synchronizing predelivered Table Types...");
    for (HDBTableTypeDataStructureModel tableType : TABLE_TYPES_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(HDBDataStructureModel.FILE_EXTENSION_STRUCTURE, tableType);
      } catch (Exception e) {
        logger.error(format("Update tableType [{0}] skipped due to an error: {1}", tableType, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered Table Types.");

    logger.trace("Done synchronizing predelivered Kronos Data Structures.");
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer#
   * synchronizeRegistry()
   */
  @Override
  protected void synchronizeRegistry() throws SynchronizationException {
    logger.trace("Synchronizing Kronos Data Structures from Registry...");

    super.synchronizeRegistry();

    logger.trace("Done synchronizing Kronos Data Structures from Registry.");
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer#
   * synchronizeResource(com.codbex.kronos.hdb.ds.parser.DataStructureParser
   * repository.api.IResource)
   */
  @Override
  protected void synchronizeResource(IResource resource) throws SynchronizationException {
    try {
      hdbCoreFacade.handleResourceSynchronization(resource);
    } catch (DataStructuresException e) {
      logger.error(e.getMessage(), e);
    }
  }

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer#cleanup()
   */
  @Override
  protected void cleanup() throws SynchronizationException {
    try {
      this.hdbCoreFacade.cleanup();
    } catch (DataStructuresException e) {
      logger.error(e.getMessage(), e);
    }
  }

	@Override
	public int getPriority() {
		return 250;
	}
}
