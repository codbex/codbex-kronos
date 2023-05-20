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

import com.codbex.kronos.hdb.ds.model.hdbdd.DataStructureHDBDDModel;
import com.codbex.kronos.hdb.ds.model.hdbscalarfunction.DataStructureHDBScalarFunctionModel;
import com.codbex.kronos.hdb.ds.model.hdbstructure.DataStructureHDBStructureModel;
import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer;
import org.eclipse.dirigible.core.scheduler.api.IOrderedSynchronizerContribution;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;
import org.eclipse.dirigible.core.scheduler.api.SynchronizationException;
import org.eclipse.dirigible.repository.api.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.facade.IHDBCoreSynchronizationFacade;
import com.codbex.kronos.hdb.ds.facade.HDBCoreSynchronizationFacade;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbprocedure.DataStructureHDBProcedureModel;
import com.codbex.kronos.hdb.ds.model.hdbschema.DataStructureHDBSchemaModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.DataStructureHDBSynonymModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.DataStructureHDBTableFunctionModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.DataStructureHDBTableTypeModel;
import com.codbex.kronos.hdb.ds.model.hdbview.DataStructureHDBViewModel;
import com.codbex.kronos.hdb.ds.service.parser.ICoreParserService;
import com.codbex.kronos.hdb.ds.service.parser.CoreParserService;
import com.codbex.kronos.utils.CommonsConstants;

/**
 * The Data Structures Synchronizer.
 */
public class DataStructuresSynchronizer extends AbstractSynchronizer implements IOrderedSynchronizerContribution {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(DataStructuresSynchronizer.class);

  /** The Constant HDBDDS_PREDELIVERED. */
  private static final Map<String, DataStructureHDBDDModel> HDBDDS_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  
  /** The Constant HDBTABLES_PREDELIVERED. */
  private static final Map<String, DataStructureHDBTableModel> HDBTABLES_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  
  /** The Constant HDBVIEWS_PREDELIVERED. */
  private static final Map<String, DataStructureHDBViewModel> HDBVIEWS_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  
  /** The Constant HDBPROCEDURES_PREDELIVERED. */
  private static final Map<String, DataStructureHDBProcedureModel> HDBPROCEDURES_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  
  /** The Constant HDBTABLEFUNCTIONS_PREDELIVERED. */
  private static final Map<String, DataStructureHDBTableFunctionModel> HDBTABLEFUNCTIONS_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());

  /** The Constant HDBSCALARFUNCTIONS_PREDELIVERED. */
  private static final Map<String, DataStructureHDBScalarFunctionModel> HDBSCALARFUNCTIONS_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  
  /** The Constant HDBSCHEMAS_PREDELIVERED. */
  private static final Map<String, DataStructureHDBSchemaModel> HDBSCHEMAS_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  
  /** The Constant HDBSYNONYMS_PREDELIVERED. */
  private static final Map<String, DataStructureHDBSynonymModel> HDBSYNONYMS_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  
  /** The Constant HDBTABLETYPES_PREDELIVERED. */
  private static final Map<String, DataStructureHDBTableTypeModel> HDBTABLETYPES_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());

  /** The Constant HDBSTRUCTURES_PREDELIVERED. */
  private static final Map<String, DataStructureHDBStructureModel> HDBSTRUCTURES_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());
  
  /** The synchronizer name. */
  private final String SYNCHRONIZER_NAME = this.getClass().getCanonicalName();
  
  /** The core parser service. */
  private ICoreParserService coreParserService = new CoreParserService();
  
  /** The hdb core facade. */
  private IHDBCoreSynchronizationFacade hdbCoreFacade = new HDBCoreSynchronizationFacade();

  /**
   * Register predelivered *.hdbdd files.
   *
   * @param contentPath the data path
   * @throws Exception the exception
   */
  public void registerPredeliveredHDBDD(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(IDataStructureModel.FILE_EXTENSION_HDBDD, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    DataStructureHDBDDModel model = (DataStructureHDBDDModel) coreParserService
        .parseDataStructure(parametersModel);
    HDBDDS_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered *.hdbtable files.
   *
   * @param contentPath the data path
   * @throws Exception the exception
   */
  public void registerPredeliveredHDBTable(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(IDataStructureModel.FILE_EXTENSION_HDB_TABLE, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    DataStructureHDBTableModel model = (DataStructureHDBTableModel) coreParserService
        .parseDataStructure(parametersModel);
    HDBTABLES_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered *.hdbview files.
   *
   * @param contentPath the data path
   * @throws Exception the exception
   */
  public void registerPredeliveredHDBView(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(IDataStructureModel.FILE_EXTENSION_HDB_VIEW, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    DataStructureHDBViewModel model = (DataStructureHDBViewModel) coreParserService
        .parseDataStructure(parametersModel);
    HDBVIEWS_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered *.hdbsynonym files.
   *
   * @param contentPath the data path
   * @throws Exception the exception
   */
  public void registerPredeliveredHDBSynonym(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(IDataStructureModel.FILE_EXTENSION_HDB_SYNONYM, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    DataStructureHDBSynonymModel model = (DataStructureHDBSynonymModel) coreParserService
        .parseDataStructure(parametersModel);
    HDBSYNONYMS_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered *.hdbprocedure files.
   *
   * @param contentPath the data path
   * @throws IOException                in case of an error
   * @throws DataStructuresException in case of an error
   * @throws ArtifactParserException the artifact parser exception
   */
  public void registerPredeliveredHDBProcedure(String contentPath)
      throws IOException, DataStructuresException, ArtifactParserException {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(IDataStructureModel.FILE_EXTENSION_HDB_PROCEDURE, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    DataStructureHDBProcedureModel model;
    model = (DataStructureHDBProcedureModel) coreParserService
        .parseDataStructure(parametersModel);
    HDBPROCEDURES_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered *.hdbtablefunction files.
   *
   * @param contentPath the data path
   * @throws IOException in case of an error
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   */
  public void registerPredeliveredHDBTableFunction(String contentPath)
      throws IOException, DataStructuresException, ArtifactParserException {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(IDataStructureModel.FILE_EXTENSION_HDB_TABLE_FUNCTION, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    DataStructureHDBTableFunctionModel model;
    model = (DataStructureHDBTableFunctionModel) coreParserService
        .parseDataStructure(parametersModel);
    HDBTABLEFUNCTIONS_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered *.hdbscalarfunction files.
   *
   * @param contentPath the data path
   * @throws IOException in case of an error
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   */
  public void registerPredeliveredHDBScalarFunction(String contentPath)
      throws IOException, DataStructuresException, ArtifactParserException {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(IDataStructureModel.FILE_EXTENSION_HDB_SCALAR_FUNCTION, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    DataStructureHDBScalarFunctionModel model;
    model = (DataStructureHDBScalarFunctionModel) coreParserService
        .parseDataStructure(parametersModel);
    HDBSCALARFUNCTIONS_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered *.hdbschema files.
   *
   * @param contentPath the data path
   * @throws IOException                in case of an error
   * @throws DataStructuresException in case of an error
   * @throws ArtifactParserException the artifact parser exception
   */
  public void registerPredeliveredHDBSchema(String contentPath)
      throws IOException, DataStructuresException, ArtifactParserException {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(IDataStructureModel.FILE_EXTENSION_HDB_SCHEMA, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    DataStructureHDBSchemaModel model;
    model = (DataStructureHDBSchemaModel) coreParserService
        .parseDataStructure(parametersModel);
    HDBSCHEMAS_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered *.hdbtabletype files.
   *
   * @param contentPath the data path
   * @throws Exception the exception
   */
  public void registerPredeliveredHDBTableType(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(IDataStructureModel.FILE_EXTENSION_HDB_TABLE_TYPE, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    DataStructureHDBTableTypeModel model = (DataStructureHDBTableTypeModel) coreParserService
        .parseDataStructure(parametersModel);
    HDBTABLETYPES_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Register predelivered *.hdbstructure files.
   *
   * @param contentPath the data path
   * @throws Exception the exception
   */
  public void registerPredeliveredHDBStructure(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(IDataStructureModel.FILE_EXTENSION_HDB_STRUCTURE, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    DataStructureHDBStructureModel model = (DataStructureHDBStructureModel) coreParserService
        .parseDataStructure(parametersModel);
    HDBSTRUCTURES_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Load resource content.
   *
   * @param modelPath the model path
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
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

  /**
   * Synchronize.
   */
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

        int immutableHDBDDCount = HDBDDS_PREDELIVERED.size();
        int immutableHDBTablesCount = HDBTABLES_PREDELIVERED.size();
        int immutableHDBViewsCount = HDBVIEWS_PREDELIVERED.size();
        int immutableHDBProceduresCount = HDBPROCEDURES_PREDELIVERED.size();
        int immutableHDBTableFunctionsCount = HDBTABLEFUNCTIONS_PREDELIVERED.size();
        int immutableHDBScalarFunctionsCount = HDBSCALARFUNCTIONS_PREDELIVERED.size();
        int immutableHDBSchemasCount = HDBSCHEMAS_PREDELIVERED.size();
        int immutableHDBSynonymsCount = HDBSYNONYMS_PREDELIVERED.size();
        int immutableHDBTableTypesCount = HDBTABLETYPES_PREDELIVERED.size();
        int immutableHDBStructuresCount = HDBSTRUCTURES_PREDELIVERED.size();

//        int mutableHDBDDCount = HDBDDS_SYNCHRONIZED.size();
//        int mutableHDBTablesCount = HDBTABLES_SYNCHRONIZED.size();
//        int mutableHDBViewsCount = HDBVIEWS_SYNCHRONIZED.size();
//        int mutableHDBProceduresCount = HDBPROCEDURES_SYNCHRONIZED.size();
//        int mutableHDBTableFunctionsCount = HDBTABLEFUNCTIONS_SYNCHRONIZED.size();
//        int mutableHDBScalarFunctionsCount = HDBSCALARFUNCTIONS_SYNCHRONIZED.size();
//        int mutableHDBSchemasCount = HDBSCHEMAS_SYNCHRONIZED.size();
//        int mutableHDBSynonymsCount = HDBSYNONYMS_SYNCHRONIZED.size();
//        int mutableHDBTableTypesCount = HDBTABLETYPES_SYNCHRONIZED.size();
//        int mutableHDBStructuresCount = HDBSTRUCTURES_SYNCHRONIZED.size();

//        cleanup();

        clearCache();

        String immutableDataStructures = format("Immutable: [HDBDD: {0}, HDBTables: {1}, HDBViews: {2}, HDBProcedures: {3}, HDBTableFunctions: {4}, HDBScalarFUnctions {5}, HDBSchemas: {6}, HDBSynonyms: {7}], HDBTableTypes: {8}, HDBStructures: {9}]",
          immutableHDBDDCount,
          immutableHDBTablesCount,
          immutableHDBViewsCount,
          immutableHDBProceduresCount,
          immutableHDBTableFunctionsCount,
          immutableHDBScalarFunctionsCount,
          immutableHDBSchemasCount,
          immutableHDBSynonymsCount,
          immutableHDBTableTypesCount,
          immutableHDBStructuresCount
            );

        String mutableDataStructures = format("Mutable: [HDBDD: {0}, HDBTables: {1}, HDBViews: {2}, HDBProcedures: {3}, HDBTableFunctions: {4}, HDBScalarFUnctions {5}, HDBSchemas: {6}, HDBSynonyms: {7}], HDBTableTypes: {8}, HDBStructures: {9}]"
//            mutableHDBDDCount,
//            mutableHDBTablesCount,
//            mutableHDBViewsCount,
//            mutableHDBProceduresCount,
//            mutableHDBTableFunctionsCount,
//            mutableHDBScalarFunctionsCount,
//            mutableHDBSchemasCount,
//            mutableHDBSynonymsCount,
//            mutableHDBTableTypesCount,
//            mutableHDBStructuresCount
        );

        successfulSynchronization(SYNCHRONIZER_NAME, immutableDataStructures + ", " + mutableDataStructures);
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
    for (DataStructureHDBSchemaModel hdbSchema : HDBSCHEMAS_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(IDataStructureModel.FILE_EXTENSION_HDB_SCHEMA, hdbSchema);
      } catch (Exception e) {
        logger.error(format("Update for HDB Schema [{0}] skipped due to an error: {1}", hdbSchema, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDB Schemas.");

    // HDBDD
    logger.trace("Synchronizing predelivered HDBDD...");
    for (DataStructureHDBDDModel hdbdd : HDBDDS_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(IDataStructureModel.FILE_EXTENSION_HDBDD, hdbdd);
      } catch (Exception e) {
        logger.error(format("Update for HDBDD [{0}] skipped due to an error: {1}", hdbdd, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDBDD.");

    // HDBTables
    logger.trace("Synchronizing predelivered HDB Tables...");
    for (DataStructureHDBTableModel hdbTable : HDBTABLES_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(IDataStructureModel.FILE_EXTENSION_HDB_TABLE, hdbTable);
      } catch (Exception e) {
        logger.error(format("Update for HDB Table [{0}] skipped due to an error: {1}", hdbTable, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered Tables.");

    // HDBViews
    logger.trace("Synchronizing predelivered HDB Views...");
    for (DataStructureHDBViewModel hdbView : HDBVIEWS_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(IDataStructureModel.FILE_EXTENSION_HDB_VIEW, hdbView);
      } catch (Exception e) {
        logger.error(format("Update for HDB View [{0}] skipped due to an error: {1}", hdbView, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDB Views.");

    // HDBProcedures
    logger.trace("Synchronizing predelivered HDB Procedures...");
    for (DataStructureHDBProcedureModel hdbProcedure : HDBPROCEDURES_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(IDataStructureModel.FILE_EXTENSION_HDB_PROCEDURE, hdbProcedure);
      } catch (Exception e) {
        logger.error(format("Update for HDB Procedure [{0}] skipped due to an error: {1}", hdbProcedure, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDB Procedures.");

    // HDBTableFunctions
    logger.trace("Synchronizing predelivered HDB Table Functions...");
    for (DataStructureHDBTableFunctionModel hdbTableFunction : HDBTABLEFUNCTIONS_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(IDataStructureModel.FILE_EXTENSION_HDB_TABLE_FUNCTION, hdbTableFunction);
      } catch (Exception e) {
        logger.error(format("Update hdbtablefunction [{0}] skipped due to an error: {1}", hdbTableFunction, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDB Table Function.");

    // HDBScalarFunctions
    logger.trace("Synchronizing predelivered HDB Scalar Functions...");
    for (DataStructureHDBScalarFunctionModel hdbScalarFunction : HDBSCALARFUNCTIONS_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(IDataStructureModel.FILE_EXTENSION_HDB_SCALAR_FUNCTION, hdbScalarFunction);
      } catch (Exception e) {
        logger.error(format("Update for HDB Scalar Function [{0}] skipped due to an error: {1}", hdbScalarFunction, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDB Scalar Function.");

    // HDBSynonyms
    logger.trace("Synchronizing predelivered HDB Synonyms...");
    for (DataStructureHDBSynonymModel hdbSynonym : HDBSYNONYMS_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(IDataStructureModel.FILE_EXTENSION_HDB_SYNONYM, hdbSynonym);
      } catch (Exception e) {
        logger.error(format("Update for HDB Synonym [{0}] skipped due to an error: {1}", hdbSynonym, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDB Synonyms.");

    // HDBTableTypes
    logger.trace("Synchronizing predelivered HDB Table Types...");
    for (DataStructureHDBTableTypeModel hdbTableType : HDBTABLETYPES_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(IDataStructureModel.FILE_EXTENSION_HDB_TABLE_TYPE, hdbTableType);
      } catch (Exception e) {
        logger.error(format("Update for HDB Table Type [{0}] skipped due to an error: {1}", hdbTableType, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDB Table Types.");

    // HDBStructures
    logger.trace("Synchronizing predelivered HDB Structures...");
    for (DataStructureHDBStructureModel hdbStructure : HDBSTRUCTURES_PREDELIVERED.values()) {
      try {
        hdbCoreFacade.handleResourceSynchronization(IDataStructureModel.FILE_EXTENSION_HDB_STRUCTURE, hdbStructure);
      } catch (Exception e) {
        logger.error(format("Update for HDB Structure [{0}] skipped due to an error: {1}", hdbStructure, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDB Structures.");

    logger.trace("Done synchronizing predelivered Kronos Data Structures.");
  }

  /**
   * Synchronize registry.
   *
   * @throws SynchronizationException the synchronization exception
   */
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

  /**
   * Synchronize resource.
   *
   * @param resource the resource
   * @throws SynchronizationException the synchronization exception
   */
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

  /**
   * Cleanup.
   *
   * @throws SynchronizationException the synchronization exception
   */
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

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	@Override
	public int getPriority() {
		return 250;
	}
}
