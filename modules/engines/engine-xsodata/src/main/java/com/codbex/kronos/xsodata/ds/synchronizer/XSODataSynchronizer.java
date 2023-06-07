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
package com.codbex.kronos.xsodata.ds.synchronizer;

import static java.text.MessageFormat.format;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer;
import org.eclipse.dirigible.core.scheduler.api.IOrderedSynchronizerContribution;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;
import org.eclipse.dirigible.core.scheduler.api.SynchronizationException;
import org.eclipse.dirigible.engine.odata2.definition.ODataDefinition;
import org.eclipse.dirigible.engine.odata2.definition.ODataHandlerDefinition;
import org.eclipse.dirigible.engine.odata2.transformers.DBMetadataUtil;
import org.eclipse.dirigible.repository.api.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.xsodata.ds.api.IODataCoreService;
import com.codbex.kronos.xsodata.ds.api.IODataModel;
import com.codbex.kronos.xsodata.ds.model.ODataModel;
import com.codbex.kronos.xsodata.ds.service.OData2ODataHTransformer;
import com.codbex.kronos.xsodata.ds.service.OData2ODataMTransformer;
import com.codbex.kronos.xsodata.ds.service.OData2ODataXTransformer;
import com.codbex.kronos.xsodata.ds.service.ODataCoreService;
import com.codbex.kronos.xsodata.ds.service.TableMetadataProvider;
import com.codbex.kronos.xsodata.utils.ODataUtils;

/**
 * The XSOData Synchronizer.
 */
public class XSODataSynchronizer extends AbstractSynchronizer implements IOrderedSynchronizerContribution {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(XSODataSynchronizer.class);

  /** The Constant ODATA_PREDELIVERED. */
  private static final Map<String, ODataModel> ODATA_PREDELIVERED = Collections
      .synchronizedMap(new HashMap<>());

  /** The Constant ODATA_SYNCHRONIZED. */
  private static final List<String> ODATA_SYNCHRONIZED = Collections.synchronizedList(new ArrayList<>());

  /** The Constant ODATA_MODELS. */
  private static final Map<String, ODataModel> ODATA_MODELS = new LinkedHashMap<>();
  
  /** The synchronizer name. */
  private final String SYNCHRONIZER_NAME = this.getClass().getCanonicalName();

  /** The o data core service. */
  private IODataCoreService oDataCoreService = new ODataCoreService();

  /** The odata core service. */
  private org.eclipse.dirigible.engine.odata2.service.ODataCoreService odataCoreService = new org.eclipse.dirigible.engine.odata2.service.ODataCoreService();

  /** The o data 2 O data M transformer. */
  private OData2ODataMTransformer oData2ODataMTransformer = new OData2ODataMTransformer();

  /** The o data 2 O data X transformer. */
  private OData2ODataXTransformer oData2ODataXTransformer = new OData2ODataXTransformer();

  /** The o data 2 O data H transformer. */
  private OData2ODataHTransformer oData2ODataHTransformer = new OData2ODataHTransformer();

  /**
   * Concatenate list of strings.
   *
   * @param list      the list
   * @param separator the separator
   * @return the string
   */
  private static String concatenateListOfStrings(List<String> list, String separator) {
    StringBuilder buff = new StringBuilder();
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
   * @see org.eclipse.dirigible.core.scheduler.api.ISynchronizer#synchronize()
   */
  @Override
  public void synchronize() {
    synchronized (XSODataSynchronizer.class) {
      if (beforeSynchronizing()) {
        logger.trace("Synchronizing XSOData...");
        try {
          if (isSynchronizerSuccessful("org.eclipse.dirigible.database.ds.synchronizer.DataStructuresSynchronizer")
              && isSynchronizerSuccessful("com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer")) {
            startSynchronization(SYNCHRONIZER_NAME);
            clearCache();
            synchronizePredelivered();
            synchronizeRegistry();
            updateXSOData();
            int immutableCount = ODATA_PREDELIVERED.size();
            int mutableCount = ODATA_SYNCHRONIZED.size();
            cleanup(); // TODO drop tables and views for non-existing models
            clearCache();
            successfulSynchronization(SYNCHRONIZER_NAME, format("Immutable: {0}, Mutable: {1}", immutableCount, mutableCount));
          } else {
            failedSynchronization(SYNCHRONIZER_NAME,
                "Skipped due to dependencies: org.eclipse.dirigible.database.ds.synchronizer.DataStructuresSynchronizer, "
                    + "com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer");
          }
        } catch (Exception e) {
          logger.error("Synchronizing process for XSOData failed.", e);
          try {
            failedSynchronization(SYNCHRONIZER_NAME, e.getMessage());
          } catch (SchedulerException e1) {
            logger.error("Synchronizing process for XSOData files failed in registering the state log.", e);
          }
        }
        logger.trace("Done synchronizing XSOData.");
        afterSynchronizing();
      }
    }
  }

  /**
   * Register predelivered odata files.
   *
   * @param contentPath the data path
   * @throws Exception the exception
   */
  public void registerPredeliveredOData(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    ODataModel model = oDataCoreService.parseOData(contentPath, data);
    ODATA_PREDELIVERED.put(contentPath, model);
  }

  /**
   * Load resource content.
   *
   * @param modelPath the model path
   * @return the string
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private String loadResourceContent(String modelPath) throws IOException {
    try (InputStream in = XSODataSynchronizer.class.getResourceAsStream(modelPath)) {
      return IOUtils.toString(in, StandardCharsets.UTF_8);
    }
  }

  /**
   * Clear cache.
   */
  private void clearCache() {
    ODATA_MODELS.clear();
  }

  /**
   * Synchronize predelivered.
   */
  private void synchronizePredelivered() {
    logger.trace("Synchronizing predelivered XSOData...");

    // XSOData
    for (ODataModel odata : ODATA_PREDELIVERED.values()) {
      try {
        synchronizeOData(odata);
      } catch (Exception e) {
        logger.error(format("Update xsodata [{0}] skipped due to an error: {1}", odata, e.getMessage()), e);
      }
    }
    logger.trace("Done synchronizing predelivered XSOData.");
  }

  /**
   * Synchronize xsodata.
   *
   * @param odataModel the odata model
   * @throws SynchronizationException the synchronization exception
   */
  private void synchronizeOData(ODataModel odataModel) throws SynchronizationException {
    try {
      if (!oDataCoreService.existsOData(odataModel.getLocation())) {
        oDataCoreService.createOData(odataModel.getLocation(), odataModel.getName(), odataModel.getHash());
        ODATA_MODELS.put(odataModel.getName(), odataModel);
        logger.info("Synchronized a new XSOData file [{}] from location: {}", odataModel.getName(), odataModel.getLocation());
      } else {
        ODataModel existing = oDataCoreService.getOData(odataModel.getLocation());
        if (!odataModel.equals(existing)) {
          oDataCoreService.updateOData(odataModel.getLocation(), odataModel.getName(), odataModel.getHash());
          ODATA_MODELS.put(odataModel.getName(), odataModel);
          logger.info("Synchronized a modified XSOData file [{}] from location: {}", odataModel.getName(), odataModel.getLocation());
        }
      }
      ODATA_SYNCHRONIZED.add(odataModel.getLocation());
    } catch (Exception e) {
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
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer#synchronizeRegistry()
   */
  @Override
  protected void synchronizeRegistry() throws SynchronizationException {
    logger.trace("Synchronizing XSOData from Registry...");

    super.synchronizeRegistry();

    logger.trace("Done synchronizing XSOData from Registry.");
  }

  /**
   * Synchronize resource.
   *
   * @param resource the resource
   * @throws SynchronizationException the synchronization exception
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer#synchronizeResource(com.codbex.kronos.hdb.ds.parser.DataStructureParser
   * repository.api.IResource)
   */
  @Override
  protected void synchronizeResource(IResource resource) throws SynchronizationException {
    String resourceName = resource.getName();
    String registryPath = getRegistryPath(resource);
    byte[] content = resource.getContent();
    String contentAsString;
    try {
      contentAsString = IOUtils.toString(new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new SynchronizationException(e);
    }

    if (resourceName.endsWith(IODataModel.FILE_EXTENSION_XSODATA)) {
      ODataModel odataModel;
      try {
        odataModel = oDataCoreService.parseOData(registryPath, contentAsString);
      } catch (Exception e) {
        throw new SynchronizationException(e);
      }
      odataModel.setLocation(registryPath);
      synchronizeOData(odataModel);
    }
  }


  /**
   * Cleanup.
   *
   * @throws SynchronizationException the synchronization exception
   */
  @Override
  protected void cleanup() throws SynchronizationException {
    logger.trace("Cleaning up XSOData...");
    try {
      List<ODataModel> odataModels = oDataCoreService.getAllODataRecords();
      for (ODataModel odataModel : odataModels) {
        if (!ODATA_SYNCHRONIZED.contains(odataModel.getLocation())) {
          oDataCoreService.removeOData(odataModel.getLocation());
          logger.warn("Cleaned up XSOData Data file [{}] from location: {}", odataModel.getName(), odataModel.getLocation());
        }
      }
    } catch (Exception e) {
      throw new SynchronizationException(e);
    }
    logger.trace("Done cleaning up XSOData.");
  }

  /**
   * Update XSO data.
   *
   * @throws ODataException the o data exception
   */
  private void updateXSOData() throws ODataException {
    // Update XSOData

    if (ODATA_MODELS.isEmpty()) {
      logger.trace("No Kronos XSOData to update.");
      return;
    }

    odataCoreService.handlerDefinitionTableCheck();
    List<String> errors = new ArrayList<>();
    // topology sort of dependencies
    List<String> sorted = new ArrayList<>();
    List<String> external = new ArrayList<>();

    if (sorted.isEmpty()) {
      // something wrong happened with the sorting - probably cyclic dependencies
      // we go for the back-up list and try to apply what would succeed
      sorted.addAll(ODATA_MODELS.keySet());
    }

    // drop xsodata in a reverse order
    for (int i = sorted.size() - 1; i >= 0; i--) {
      String dsName = sorted.get(i);
      ODataModel model = ODATA_MODELS.get(dsName);
      try {
        // CLEAN UP LOGIC
        odataCoreService.removeSchema(model.getLocation());
        odataCoreService.removeContainer(model.getLocation());
        odataCoreService.removeMappings(model.getLocation());
        odataCoreService.removeHandlers(model.getLocation());
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        errors.add(e.getMessage());
      }
    }

    // process tables in the proper order
    for (String dsName : sorted) {
      ODataModel model = ODATA_MODELS.get(dsName);
      try {
        // METADATA AND MAPPINGS GENERATION LOGIC

        //The xs classic generate the odata properties without prettying them
        String oldValuePretty = Configuration.get(DBMetadataUtil.DIRIGIBLE_GENERATE_PRETTY_NAMES);
        Configuration.set(DBMetadataUtil.DIRIGIBLE_GENERATE_PRETTY_NAMES, "false");

        ODataUtils oDataUtils = new ODataUtils(new TableMetadataProvider());
        ODataDefinition oDataDefinition = oDataUtils.convertODataModelToODataDefinition(model);

        String[] odataxc = generateODataX(oDataDefinition);
        String odatax = odataxc[0];
        String odatac = odataxc[1];
        odataCoreService.createSchema(model.getLocation(), odatax.getBytes());
        odataCoreService.createContainer(model.getLocation(), odatac.getBytes());

        String[] odatams = generateODataMs(oDataDefinition);
        int i = 1;
        for (String odatam : odatams) {
          odataCoreService.createMapping(model.getLocation() + "#" + i++, odatam.getBytes());
        }

        List<ODataHandlerDefinition> odatahs = generateODataHs(oDataDefinition);
        for (ODataHandlerDefinition odatah : odatahs) {
          odataCoreService.createHandler(model.getLocation(), odatah.getNamespace(), odatah.getName(),
              odatah.getMethod(), odatah.getType(), odatah.getHandler());
        }

        if (oldValuePretty != null) {
          Configuration.set(DBMetadataUtil.DIRIGIBLE_GENERATE_PRETTY_NAMES, oldValuePretty);
        }

      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        errors.add(e.getMessage());
      }
    }

    if (!errors.isEmpty()) {
      logger.error(concatenateListOfStrings(errors, "\n---\n"));
    }
  }

  /**
   * Generate O data X.
   *
   * @param oDataDefinition the o data definition
   * @return the string[]
   * @throws SQLException the SQL exception
   */
  private String[] generateODataX(ODataDefinition oDataDefinition) throws SQLException {
    return oData2ODataXTransformer.transform(oDataDefinition);
  }

  /**
   * Generate O data ms.
   *
   * @param oDataDefinition the o data definition
   * @return the string[]
   * @throws SQLException the SQL exception
   */
  private String[] generateODataMs(ODataDefinition oDataDefinition) throws SQLException {
    return oData2ODataMTransformer.transform(oDataDefinition);
  }

  /**
   * Generate O data hs.
   *
   * @param oDataDefinition the o data definition
   * @return the list
   * @throws SQLException the SQL exception
   */
  private List<ODataHandlerDefinition> generateODataHs(ODataDefinition oDataDefinition) throws SQLException {
    return oData2ODataHTransformer.transform(oDataDefinition);
  }

	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	@Override
	public int getPriority() {
		return 550;
	}

}
