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
package com.codbex.kronos.hdbti.synchronizer;

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
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer;
import org.eclipse.dirigible.core.scheduler.api.IOrderedSynchronizerContribution;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;
import org.eclipse.dirigible.core.scheduler.api.SynchronizationException;
import org.eclipse.dirigible.repository.api.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.hdbti.api.ICSVToHDBTIRelationDao;
import com.codbex.kronos.hdbti.api.IHDBTICoreService;
import com.codbex.kronos.hdbti.api.IHDBTIProcessor;
import com.codbex.kronos.hdbti.api.ITableImportArtifactDao;
import com.codbex.kronos.hdbti.api.ITableImportModel;
import com.codbex.kronos.hdbti.api.ITableImportParser;
import com.codbex.kronos.hdbti.api.TableImportException;
import com.codbex.kronos.hdbti.dao.CSVToHDBTIRelationDao;
import com.codbex.kronos.hdbti.dao.TableImportArtifactDao;
import com.codbex.kronos.hdbti.model.TableImportArtifact;
import com.codbex.kronos.hdbti.model.TableImportConfigurationDefinition;
import com.codbex.kronos.hdbti.model.TableImportToCsvRelation;
import com.codbex.kronos.hdbti.processors.HDBTIProcessor;
import com.codbex.kronos.hdbti.service.CSVDefinitionsTopologicalSorter;
import com.codbex.kronos.hdbti.service.HDBTICoreService;
import com.codbex.kronos.hdbti.service.TableImportParser;
import com.codbex.kronos.parser.hdbti.exception.HDBTISyntaxErrorException;
import com.codbex.kronos.utils.Utils;

public class TableImportSynchronizer extends AbstractSynchronizer implements IOrderedSynchronizerContribution {

  private static final Logger logger = LoggerFactory.getLogger(TableImportSynchronizer.class);

  private static final Map<String, TableImportArtifact> HDBTI_PREDELIVERED = Collections
      .synchronizedMap(new HashMap<>()); //ones which already exist in the JAR

  private static final List<String> HDBTI_SYNCHRONIZED = Collections
      .synchronizedList(new ArrayList<>()); // used for leaving only the correct files after the sync

  private static final Map<String, TableImportArtifact> HDBTI_MODELS = new LinkedHashMap<>(); // used for collecting all created/updated models and later for the actual execution of the query ( import/ alter etc)

  private final String SYNCHRONIZER_NAME = this.getClass().getCanonicalName();

  private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.DATASOURCE);
  private ITableImportParser tableImportParser = new TableImportParser();
  private IHDBTIProcessor hdbtiProcessor = new HDBTIProcessor();
  private ICSVToHDBTIRelationDao csvToHDBTIRelationDao = new CSVToHDBTIRelationDao();
  private ITableImportArtifactDao tableImportArtifactDao = new TableImportArtifactDao();
  private IHDBTICoreService hdbtiCoreService = new HDBTICoreService();

  @Override
  public void synchronize() {
    synchronized (DataStructuresSynchronizer.class) {
      if (beforeSynchronizing()) {
        logger.trace("Synchronizing HDBTI files ...");
        try {
          if (isSynchronizerSuccessful("org.eclipse.dirigible.database.ds.synchronizer.DataStructuresSynchronizer")
              && isSynchronizerSuccessful("com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer")) {
            startSynchronization(SYNCHRONIZER_NAME);
            clearCache();
            synchronizePredelivered();
            synchronizeRegistry();
            processTableImports();
            int immutableCount = HDBTI_PREDELIVERED.size();
            int mutableCount = HDBTI_SYNCHRONIZED.size();
            cleanup();
            clearCache();
            successfulSynchronization(SYNCHRONIZER_NAME, format("Immutable: {0}, Mutable: {1}", immutableCount, mutableCount));
          } else {
            failedSynchronization(SYNCHRONIZER_NAME,
                "Skipped due to dependencies: org.eclipse.dirigible.database.ds.synchronizer.DataStructuresSynchronizer, "
                    + "com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer");
          }
        } catch (Exception e) {
          logger.error("Error during HDBTI synchronization", e);
          try {
            failedSynchronization(SYNCHRONIZER_NAME, e.getMessage());
          } catch (SchedulerException e1) {
            logger.error("Synchronizing process for HDBTI files failed in registering the state log.", e);
          }
        }
        logger.trace("Done synchronizing HDBTI files.");
        afterSynchronizing();
      }
    }
  }

  /**
   * Force synchronization.
   */
  public static final void forceSynchronization() {
    TableImportSynchronizer synchronizer = new TableImportSynchronizer();
    synchronizer.setForcedSynchronization(true);
    try {
      synchronizer.synchronize();
    } finally {
      synchronizer.setForcedSynchronization(false);
    }
  }

  @Override
  protected void synchronizeResource(IResource resource) throws SynchronizationException {
    String resourceName = resource.getName();
    String registryPath = getRegistryPath(resource);
    String contentAsString = getContentFromResource(resource);

    if (resourceName.endsWith(ITableImportModel.FILE_EXTENSION_TABLE_IMPORT)) {
      try {
        TableImportArtifact tableImportArtifact = tableImportParser.parseTableImportArtifact(registryPath, contentAsString);
        synchronizeTableImport(tableImportArtifact);
      } catch (IOException e) {
        throw new SynchronizationException();
      } catch (HDBTISyntaxErrorException syntaxErrorException) {
        logger.error(syntaxErrorException.getMessage(), syntaxErrorException);
      } catch (ArtifactParserException parserException) {
        logger.error(parserException.getMessage());
      } catch (SQLException throwables) {
        logger.error(throwables.getMessage(), throwables);
      }
    } else if (resourceName.endsWith(ITableImportModel.FILE_EXTENSION_CSV)) {
      List<TableImportToCsvRelation> affectedHdbtiToCsvRelations = csvToHDBTIRelationDao
          .getAffectedHdbtiToCsvRelations(getRegistryPath(resource));
      if (!affectedHdbtiToCsvRelations.isEmpty()) {
        if (csvToHDBTIRelationDao.hasCsvChanged(affectedHdbtiToCsvRelations.get(0), contentAsString)) {
          affectedHdbtiToCsvRelations.forEach(relation -> reimportAffectedHdbtiFiles(relation.getHdbti()));
        }
      }
    }
  }

  private void reimportAffectedHdbtiFiles(String hdbtiFilePath) {
    IResource hdbtiResource = getRepository().getResource(Utils.convertToFullPath(hdbtiFilePath));
    try (Connection connection = dataSource.getConnection()) {
      TableImportArtifact tableImportArtifact = tableImportParser.parseTableImportArtifact(hdbtiFilePath, getContentFromResource(hdbtiResource));
      executeTableImport(tableImportArtifact, connection);
    } catch (IOException | SynchronizationException | SQLException e) {
      logger.error("Error during the force reimport of an HDBTI file due to a linked csv file change", e);
    } catch (HDBTISyntaxErrorException syntaxErrorException) {
      logger.error(syntaxErrorException.getMessage());
    } catch (ArtifactParserException parserException) {
      logger.error(parserException.getMessage());
    }

  }

  private String getContentFromResource(IResource resource) throws SynchronizationException {
    byte[] content = resource.getContent();
    String contentAsString;

    try {
      contentAsString = IOUtils
          .toString(new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8));
    } catch (IOException e) {
      throw new SynchronizationException(e);
    }
    return contentAsString;
  }

  private void synchronizeTableImport(TableImportArtifact tableImportArtifact) throws SynchronizationException {
    try {
      if (!tableImportArtifactDao.existsTableImportArtifact(tableImportArtifact.getLocation())) {
        tableImportArtifactDao.createTableImportArtifact(tableImportArtifact);
        HDBTI_MODELS.put(tableImportArtifact.getName(), tableImportArtifact);
        logger
            .info("Synchronized a new HDBTI file [{}] from location: {}", tableImportArtifact.getName(),
                tableImportArtifact.getLocation());
      } else {
        DataStructureModel existing = tableImportArtifactDao.getTableImportArtifact(tableImportArtifact.getLocation());
        if (!tableImportArtifact.equals(existing)) {
          tableImportArtifactDao.updateTableImportArtifact(tableImportArtifact);
          HDBTI_MODELS.put(tableImportArtifact.getName(), tableImportArtifact);
          logger
              .info("Synchronized a modified HDBTI file [{}] from location: {}",
                  tableImportArtifact.getName(), tableImportArtifact.getLocation());
        }
      }
      if (!HDBTI_SYNCHRONIZED.contains(tableImportArtifact.getLocation())) {
        HDBTI_SYNCHRONIZED.add(tableImportArtifact.getLocation());
      }
    } catch (TableImportException e) {
      logger.error(e.getMessage(), e);
    }
  }

  @Override
  protected void cleanup() throws SynchronizationException {
    try {
      hdbtiCoreService.cleanUpHdbtiRelatedData();
    } catch (TableImportException e) {
      throw new SynchronizationException(e);
    }
  }

  private void processTableImports() {
    try (Connection connection = dataSource.getConnection()) {
      for (String tableImportArtifact : HDBTI_MODELS.keySet()) {
        executeTableImport(HDBTI_MODELS.get(tableImportArtifact), connection);
      }
    } catch (SQLException e) {
      logger.error("Error occurred while importing the data from HDBTI files", e);
    }
  }

  private void executeTableImport(TableImportArtifact tableImportArtifact, Connection connection) {
    List<TableImportConfigurationDefinition> configurationDefinitions = tableImportArtifact.getImportConfigurationDefinition();

    List<TableImportConfigurationDefinition> sortedConfigurationDefinitions = new ArrayList<>();

    CSVDefinitionsTopologicalSorter.sort(configurationDefinitions, sortedConfigurationDefinitions, connection);

    hdbtiCoreService.refreshCsvRelations(tableImportArtifact);
    for (TableImportConfigurationDefinition configurationDefinition : sortedConfigurationDefinitions) {
      try {
        hdbtiProcessor.process(configurationDefinition, connection);
      } catch (DataStructuresException | SQLException | TableImportException | IOException e) {
        logger.error(String.format("An error occurred while trying to execute import. %s", e.getMessage()), e);
      }
    }
  }

  private void clearCache() {
    HDBTI_MODELS.clear();
    HDBTI_SYNCHRONIZED.clear();
  }

  private void synchronizePredelivered() {
    HDBTI_PREDELIVERED.values().forEach(tableImport -> {
      try {
        synchronizeTableImport(tableImport);
      } catch (SynchronizationException e) {
        logger.error("Error while synchronizing the predelivered HDBTI artifacts", e);
      }
    });
  }

  public void registerPredeliveredTableImports(String contentPath) throws Exception {
    String data = loadResourceContent(contentPath);
    TableImportArtifact tableImportArtifact = tableImportParser.parseTableImportArtifact(contentPath, data);
    HDBTI_PREDELIVERED.put(contentPath, tableImportArtifact);
  }

  private String loadResourceContent(String modelPath) throws IOException {
    try (InputStream in = DataStructuresSynchronizer.class.getResourceAsStream(modelPath)) {
      return IOUtils.toString(in, StandardCharsets.UTF_8);
    }
  }

	@Override
	public int getPriority() {
		return 450;
	}
}
