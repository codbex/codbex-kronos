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
package com.codbex.kronos.hdb.ds.hdi.synchronizer;

import static java.text.MessageFormat.format;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer;
import org.eclipse.dirigible.core.scheduler.api.IOrderedSynchronizerContribution;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;
import org.eclipse.dirigible.core.scheduler.api.SynchronizationException;
import org.eclipse.dirigible.repository.api.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.IDataStructuresCoreService;
import com.codbex.kronos.hdb.ds.api.IEnvironmentVariables;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdi.DataStructureHDIModel;
import com.codbex.kronos.hdb.ds.processors.hdi.HDIContainerCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.hdi.HDIContainerDropProcessor;
import com.codbex.kronos.hdb.ds.service.DataStructuresCoreService;
import com.codbex.kronos.hdb.ds.service.parser.ICoreParserService;
import com.codbex.kronos.hdb.ds.service.parser.CoreParserService;
import com.codbex.kronos.utils.CommonsConstants;

/**
 * The Data Structures HDI Synchronizer.
 */
public class DataStructuresHDISynchronizer extends AbstractSynchronizer implements IOrderedSynchronizerContribution {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(DataStructuresHDISynchronizer.class);

  /** The Constant HDI_PREDELIVERED. */
  private static final Map<String, DataStructureHDIModel> HDI_PREDELIVERED = Collections
      .synchronizedMap(new HashMap<>());

  /** The Constant HDI_SYNCHRONIZED. */
  private static final List<String> HDI_SYNCHRONIZED = Collections.synchronizedList(new ArrayList<String>());

  /** The Constant DATA_STRUCTURE_HDI_MODELS. */
  private static final Map<String, DataStructureHDIModel> DATA_STRUCTURE_HDI_MODELS = new LinkedHashMap<String, DataStructureHDIModel>();
  
  /** The synchronizer name. */
  private final String SYNCHRONIZER_NAME = this.getClass().getCanonicalName();
  
  /** The core parser service. */
  private ICoreParserService coreParserService = new CoreParserService();
  
  /** The data structures core service. */
  private IDataStructuresCoreService dataStructuresCoreService = new DataStructuresCoreService();
  
  /** The hdi container create processor. */
  private HDIContainerCreateProcessor hdiContainerCreateProcessor = new HDIContainerCreateProcessor();
  
  /** The data source. */
  private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.DATASOURCE);



  /**
   * Concatenate list of strings.
   *
   * @param list      the list
   * @param separator the separator
   * @return the string
   */
  private static String concatenateListOfStrings(List<String> list, String separator) {
    StringBuffer buff = new StringBuffer();
    for (String s : list) {
      buff.append(s).append(separator);
    }
    return buff.toString();
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
    synchronized (DataStructuresHDISynchronizer.class) {
      if (beforeSynchronizing()) {
        logger.trace("Synchronizing Kronos Data Structures HDI...");
        try {
          if (isSynchronizerSuccessful("org.eclipse.dirigible.database.ds.synchronizer.DataStructuresSynchronizer")
              && isSynchronizerSuccessful("com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer")
              && isSynchronizerSuccessful("com.codbex.kronos.hdbti.synchronizer.TableImportSynchronizer")) {
            startSynchronization(SYNCHRONIZER_NAME);
            clearCache();
            synchronizePredelivered();
            synchronizeRegistry();
            updateEntities();

            int immutableHDICount = HDI_PREDELIVERED.size();

            int mutableHDICount = HDI_SYNCHRONIZED.size();

            //                cleanup();
            clearCache();

            successfulSynchronization(SYNCHRONIZER_NAME, format("Immutable: {0}, Mutable: {1}", immutableHDICount, mutableHDICount));
          } else {
            failedSynchronization(SYNCHRONIZER_NAME,
                "Skipped due to dependencies: org.eclipse.dirigible.database.ds.synchronizer.DataStructuresSynchronizer, "
                    + "com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer, "
                    + "com.codbex.kronos.hdbti.synchronizer.TableImportSynchronizer");
          }
        } catch (Exception e) {
          logger.error("Synchronizing process for Data Structures HDI failed.", e);
          try {
            failedSynchronization(SYNCHRONIZER_NAME, e.getMessage());
          } catch (SchedulerException e1) {
            logger.error("Synchronizing process for HDB Data Structures HDI files failed in registering the state log.", e);
          }
        }
        logger.trace("Done synchronizing Kronos Data Structures HDI.");
        afterSynchronizing();
      }
    }
  }

  /**
   * Register predelivered *.hdi files.
   *
   * @param contentPath the data path
   * @throws Exception in case of an error
   */
  public void registerPredeliveredHDI(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    DataStructureParametersModel parametersModel =
        new DataStructureParametersModel(IDataStructureModel.TYPE_HDI, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    DataStructureHDIModel model = (DataStructureHDIModel) coreParserService
        .parseDataStructure(parametersModel);
    HDI_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Load resource content.
   *
   * @param modelPath the model path
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private String loadResourceContent(String modelPath) throws IOException {
    InputStream in = DataStructuresHDISynchronizer.class.getResourceAsStream(modelPath);
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
   * Clear cache.
   */
  private void clearCache() {
    DATA_STRUCTURE_HDI_MODELS.clear();
  }

  /**
   * Synchronize predelivered.
   *
   * @throws SynchronizationException the synchronization exception
   */
  private void synchronizePredelivered() throws SynchronizationException {

    logger.trace("Synchronizing predelivered Kronos Data Structures HDI...");

    logger.trace("Synchronizing predelivered HDI Containers ...");
    for (DataStructureHDIModel hdi : HDI_PREDELIVERED.values()) {
      try {
        synchronizeHDI(hdi);
      } catch (Exception e) {
        logger.error(format("Update hdi [{0}] skipped due to an error: {1}", hdi, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDI Containers.");

    logger.trace("Done synchronizing predelivered Kronos Data Structures HDI.");
  }

  /**
   * Synchronize HDI files.
   *
   * @param hdi the view model
   * @throws SynchronizationException the synchronization exception
   */
  private void synchronizeHDI(DataStructureHDIModel hdi) throws SynchronizationException {
    try {
      if (!dataStructuresCoreService.existsDataStructure(hdi.getLocation(), hdi.getType())) {
        dataStructuresCoreService
            .createDataStructure(hdi.getLocation(), hdi.getName(), hdi.getHash(), hdi.getType());
        DATA_STRUCTURE_HDI_MODELS.put(hdi.getName(), hdi);
        logger.info("Synchronized a new HDI file [{}] from location: {}", hdi.getName(), hdi.getLocation());
      } else {
        DataStructureHDIModel existing = dataStructuresCoreService.getDataStructure(hdi.getLocation(), hdi.getType());
        if (!hdi.equals(existing)) {
          dataStructuresCoreService
              .updateDataStructure(hdi.getLocation(), hdi.getName(), hdi.getHash(), hdi.getType());
          DATA_STRUCTURE_HDI_MODELS.put(hdi.getName(), hdi);
          logger.info("Synchronized a modified HDI file [{}] from location: {}", hdi.getName(), hdi.getLocation());
        }
      }
      if (!HDI_SYNCHRONIZED.contains(hdi.getLocation())) {
        HDI_SYNCHRONIZED.add(hdi.getLocation());
      }
    } catch (DataStructuresException e) {
      throw new SynchronizationException(e);
    }
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
    logger.trace("Synchronizing Kronos Data Structures HDI from Registry...");

    super.synchronizeRegistry();

    logger.trace("Done synchronizing Kronos Data Structures HDI from Registry.");
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
    String resourceName = resource.getName();
    String registryPath = getRegistryPath(resource);

    boolean hdiSupported = Boolean.parseBoolean(Configuration.get(IEnvironmentVariables.KRONOS_HDI_SUPPORTED, "true"));
    if (hdiSupported) {
      if (resourceName.endsWith(IDataStructureModel.FILE_EXTENSION_HDI)) {
        String contentAsString = getContent(resource);
        DataStructureHDIModel hdi;
        try {
          DataStructureParametersModel parametersModel =
              new DataStructureParametersModel(IDataStructureModel.TYPE_HDI, registryPath, contentAsString, CommonsConstants.REGISTRY_PUBLIC);
          hdi = (DataStructureHDIModel) coreParserService
              .parseDataStructure(parametersModel);
        } catch (DataStructuresException e) {
          logger.error("Synchronized hdi artifact is not valid");
          logger.error(e.getMessage());
          return;
        } catch (Exception e) {
          throw new SynchronizationException(e);
        }
        hdi.setLocation(registryPath);
        synchronizeHDI(hdi);
        return;
      }
    }

  }

  /**
   * Gets the content.
   *
   * @param resource the resource
   * @return the content
   * @throws SynchronizationException the synchronization exception
   */
  private String getContent(IResource resource) throws SynchronizationException {
    byte[] content = resource.getContent();
    String contentAsString;
    try {
      contentAsString = IOUtils.toString(new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new SynchronizationException(e);
    }
    return contentAsString;
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
    logger.trace("Cleaning up Kronos Data Structures HDI...");

    try {
      Connection connection = null;
      try {
        connection = dataSource.getConnection();

        // HDI
        List<DataStructureHDIModel> hdiForDrop = new ArrayList<>();
        List<DataStructureHDIModel> hdiModels = dataStructuresCoreService.getDataStructuresByType(IDataStructureModel.TYPE_HDI);
        for (DataStructureHDIModel hdiModel : hdiModels) {
          if (!HDI_SYNCHRONIZED.contains(hdiModel.getLocation())) {
            dataStructuresCoreService.removeDataStructure(hdiModel.getLocation());
            //DROP Deleted HDI
            hdiForDrop.add(hdiModel);
            logger.warn("Cleaned up HDI Container file [{}] from location: {}", hdiModel.getName(), hdiModel.getLocation());
          }
        }
        HDIContainerDropProcessor.execute(connection, hdiForDrop);

      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (DataStructuresException | SQLException e) {
      throw new SynchronizationException(e);
    }

    logger.trace("Done cleaning up Kronos Data Structures HDI.");
  }

  /**
   * Update entities.
   */
  private void updateEntities() {

    if (DATA_STRUCTURE_HDI_MODELS.isEmpty()) {
      logger.trace("No Kronos Data Structures HDI to update.");
      return;
    }

    List<String> errors = new ArrayList<String>();
    try {
      Connection connection = null;
      try {
        connection = dataSource.getConnection();

        List<String> sorted = new ArrayList<String>();
        if (sorted.isEmpty()) {
          sorted.addAll(DATA_STRUCTURE_HDI_MODELS.keySet());
        }

        boolean hdiSupported = Boolean.parseBoolean(Configuration.get(IEnvironmentVariables.KRONOS_HDI_SUPPORTED, "true"));
        if (hdiSupported) {
          // HDI Containers
          List<DataStructureHDIModel> hdiModels = new ArrayList<DataStructureHDIModel>();
          for (int i = sorted.size() - 1; i >= 0; i--) {
            String dsName = sorted.get(i);
            DataStructureHDIModel model = DATA_STRUCTURE_HDI_MODELS.get(dsName);
            if (model != null) {
              hdiModels.add(model);
            }
          }
          executeHDI(connection, hdiModels);
        }

      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      logger.error(concatenateListOfStrings(errors, "\n---\n"), e);
    }
  }

  /**
   * Execute HDI.
   *
   * @param connection the connection
   * @param hdiModels the hdi models
   * @throws SQLException the SQL exception
   */
  private void executeHDI(Connection connection, List<DataStructureHDIModel> hdiModels)
      throws SQLException {
    hdiModels.forEach(hdiModel -> {
      this.hdiContainerCreateProcessor.execute(connection, hdiModel);
    });
  }

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	@Override
	public int getPriority() {
		return 270;
	}

}
