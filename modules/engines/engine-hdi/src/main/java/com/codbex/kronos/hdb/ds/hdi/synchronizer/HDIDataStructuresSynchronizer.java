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

import com.codbex.kronos.hdb.ds.api.HDBDataStructureModel;
import com.codbex.kronos.hdb.ds.api.IDataStructuresCoreService;
import com.codbex.kronos.hdb.ds.api.IEnvironmentVariables;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdi.HDIDataStructureModel;
import com.codbex.kronos.hdb.ds.processors.hdi.HDIContainerCreateProcessor;
import com.codbex.kronos.hdb.ds.processors.hdi.HDIContainerDropProcessor;
import com.codbex.kronos.hdb.ds.service.DataStructuresCoreService;
import com.codbex.kronos.hdb.ds.service.parser.ICoreParserService;
import com.codbex.kronos.hdb.ds.service.parser.CoreParserService;
import com.codbex.kronos.utils.CommonsConstants;

/**
 * The HDI Data Structures Synchronizer.
 */
public class HDIDataStructuresSynchronizer extends AbstractSynchronizer implements IOrderedSynchronizerContribution {

  private static final Logger logger = LoggerFactory.getLogger(HDIDataStructuresSynchronizer.class);

  private static final Map<String, HDIDataStructureModel> HDI_PREDELIVERED = Collections
      .synchronizedMap(new HashMap<>());

  private static final List<String> HDI_SYNCHRONIZED = Collections.synchronizedList(new ArrayList<String>());

  private static final Map<String, HDIDataStructureModel> DATA_STRUCTURE_HDI_MODELS = new LinkedHashMap<String, HDIDataStructureModel>();
  private final String SYNCHRONIZER_NAME = this.getClass().getCanonicalName();
  private ICoreParserService coreParserService = new CoreParserService();
  private IDataStructuresCoreService dataStructuresCoreService = new DataStructuresCoreService();
  private HDIContainerCreateProcessor hdiContainerCreateProcessor = new HDIContainerCreateProcessor();
  private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.DATASOURCE);

  /**
   * Force synchronization.
   */
  public static final void forceSynchronization() {
    HDIDataStructuresSynchronizer synchronizer = new HDIDataStructuresSynchronizer();
    synchronizer.setForcedSynchronization(true);
    try {
      synchronizer.synchronize();
    } finally {
      synchronizer.setForcedSynchronization(false);
    }
  }

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

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.dirigible.core.scheduler.api.ISynchronizer#synchronize()
   */
  @Override
  public void synchronize() {
    synchronized (HDIDataStructuresSynchronizer.class) {
      if (beforeSynchronizing()) {
        logger.trace("Synchronizing Kronos HDI Data Structures...");
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
        logger.trace("Done synchronizing Kronos HDI Data Structures.");
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
        new DataStructureParametersModel(HDBDataStructureModel.TYPE_HDI, contentPath, data, CommonsConstants.REGISTRY_PUBLIC);
    HDIDataStructureModel model = (HDIDataStructureModel) coreParserService
        .parseDataStructure(parametersModel);
    HDI_PREDELIVERED.put(contentPath, model);
  }

  private String loadResourceContent(String modelPath) throws IOException {
    InputStream in = HDIDataStructuresSynchronizer.class.getResourceAsStream(modelPath);
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

    logger.trace("Synchronizing predelivered Kronos HDI Data Structures...");

    logger.trace("Synchronizing predelivered HDI Containers ...");
    for (HDIDataStructureModel hdi : HDI_PREDELIVERED.values()) {
      try {
        synchronizeHDI(hdi);
      } catch (Exception e) {
        logger.error(format("Update hdi [{0}] skipped due to an error: {1}", hdi, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered HDI Containers.");

    logger.trace("Done synchronizing predelivered Kronos HDI Data Structures.");
  }

  /**
   * Synchronize HDI files
   *
   * @param hdi the view model
   * @throws SynchronizationException the synchronization exception
   */
  private void synchronizeHDI(HDIDataStructureModel hdi) throws SynchronizationException {
    try {
      if (!dataStructuresCoreService.existsDataStructure(hdi.getLocation(), hdi.getType())) {
        dataStructuresCoreService
            .createDataStructure(hdi.getLocation(), hdi.getName(), hdi.getHash(), hdi.getType());
        DATA_STRUCTURE_HDI_MODELS.put(hdi.getName(), hdi);
        logger.info("Synchronized a new HDI file [{}] from location: {}", hdi.getName(), hdi.getLocation());
      } else {
        HDIDataStructureModel existing = dataStructuresCoreService.getDataStructure(hdi.getLocation(), hdi.getType());
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

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer#
   * synchronizeRegistry()
   */
  @Override
  protected void synchronizeRegistry() throws SynchronizationException {
    logger.trace("Synchronizing Kronos HDI Data Structures from Registry...");

    super.synchronizeRegistry();

    logger.trace("Done synchronizing Kronos HDI Data Structures from Registry.");
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
    String resourceName = resource.getName();
    String registryPath = getRegistryPath(resource);

    boolean hdiSupported = Boolean.parseBoolean(Configuration.get(IEnvironmentVariables.KRONOS_HDI_SUPPORTED, "true"));
    if (hdiSupported) {
      if (resourceName.endsWith(HDBDataStructureModel.FILE_EXTENSION_HDI)) {
        String contentAsString = getContent(resource);
        HDIDataStructureModel hdi;
        try {
          DataStructureParametersModel parametersModel =
              new DataStructureParametersModel(HDBDataStructureModel.TYPE_HDI, registryPath, contentAsString, CommonsConstants.REGISTRY_PUBLIC);
          hdi = (HDIDataStructureModel) coreParserService
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

  /*
   * (non-Javadoc)
   *
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer#cleanup()
   */
  @Override
  protected void cleanup() throws SynchronizationException {
    logger.trace("Cleaning up Kronos HDI Data Structures...");

    try {
      Connection connection = null;
      try {
        connection = dataSource.getConnection();

        // HDI
        List<HDIDataStructureModel> hdiForDrop = new ArrayList<>();
        List<HDIDataStructureModel> hdiModels = dataStructuresCoreService.getDataStructuresByType(HDBDataStructureModel.TYPE_HDI);
        for (HDIDataStructureModel hdiModel : hdiModels) {
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

    logger.trace("Done cleaning up Kronos HDI Data Structures.");
  }

  /**
   * Update entities.
   */
  private void updateEntities() {

    if (DATA_STRUCTURE_HDI_MODELS.isEmpty()) {
      logger.trace("No Kronos HDI Data Structures to update.");
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
          List<HDIDataStructureModel> hdiModels = new ArrayList<HDIDataStructureModel>();
          for (int i = sorted.size() - 1; i >= 0; i--) {
            String dsName = sorted.get(i);
            HDIDataStructureModel model = DATA_STRUCTURE_HDI_MODELS.get(dsName);
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

  private void executeHDI(Connection connection, List<HDIDataStructureModel> hdiModels)
      throws SQLException {
    hdiModels.forEach(hdiModel -> {
      this.hdiContainerCreateProcessor.execute(connection, hdiModel);
    });
  }

	@Override
	public int getPriority() {
		return 270;
	}

}
