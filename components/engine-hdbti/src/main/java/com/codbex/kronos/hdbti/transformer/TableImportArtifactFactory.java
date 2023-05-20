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
package com.codbex.kronos.hdbti.transformer;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdbti.api.IHDBTICoreService;
import com.codbex.kronos.hdbti.api.ITableImportArtifactFactory;
import com.codbex.kronos.hdbti.model.TableImportArtifact;
import com.codbex.kronos.hdbti.model.TableImportConfigurationDefinition;
import com.codbex.kronos.hdbti.model.TableImportToCsvRelation;
import com.codbex.kronos.hdbti.service.HDBTICoreService;
import com.codbex.kronos.parser.hdbti.custom.IHDBTIParser;
import com.codbex.kronos.hdbti.parser.HDBTIDataStructureParser;
import com.codbex.kronos.parser.hdbti.exception.HDBTISyntaxErrorException;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportConfigModel;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.repository.api.IRepository;
import org.eclipse.dirigible.repository.api.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A factory for creating TableImportArtifact objects.
 */
public class TableImportArtifactFactory implements ITableImportArtifactFactory {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(TableImportArtifactFactory.class);

  /** The repository. */
  private final IRepository repository = (IRepository) StaticObjects.get(StaticObjects.REPOSITORY);

  /** The hdbti core service. */
  private final IHDBTICoreService hdbtiCoreService = new HDBTICoreService();

  /** The hdbti parser. */
  private final IHDBTIParser hdbtiParser = new HDBTIDataStructureParser();

  /**
   * Parses the table import.
   *
   * @param content the content
   * @param location the location
   * @return the table import artifact
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws HDBTISyntaxErrorException the HDBTI syntax error exception
   * @throws ArtifactParserException the artifact parser exception
   */
  @Override
  public TableImportArtifact parseTableImport(String content, String location)
      throws IOException, HDBTISyntaxErrorException, ArtifactParserException {
    TableImportArtifact tableImportArtifact = new TableImportArtifact();
    List<TableImportConfigurationDefinition> importConfigurationDefinitions = new ArrayList<>();
    List<TableImportToCsvRelation> tableImportToCsvRelations = new ArrayList<>();

    tableImportArtifact.setImportConfigurationDefinition(importConfigurationDefinitions);
    tableImportArtifact.setTableImportToCsvRelations(tableImportToCsvRelations);

    HDBTIImportModel importObject = hdbtiParser.parse(location, content);

    for (HDBTIImportConfigModel configuration : importObject.getConfigModels()) {
      addHdbtiToCsvRelation(tableImportArtifact, configuration, location);
      addTableImportConfiguration(tableImportArtifact, configuration);
    }

    return tableImportArtifact;
  }

  /**
   * Gets the repository.
   *
   * @return the repository
   */
  @Override
  public IRepository getRepository() {
    return repository;
  }

  /**
   * Gets the HDBTI core service.
   *
   * @return the HDBTI core service
   */
  @Override
  public IHDBTICoreService getHDBTICoreService() {
    return hdbtiCoreService;
  }

  /**
   * Gets the HDBTI parser.
   *
   * @return the HDBTI parser
   */
  @Override
  public IHDBTIParser getHDBTIParser() {
    return hdbtiParser;
  }

  /**
   * Adds the table import configuration.
   *
   * @param tableImportArtifact the table import artifact
   * @param configuration the configuration
   */
  private void addTableImportConfiguration(TableImportArtifact tableImportArtifact, HDBTIImportConfigModel configuration) {
    TableImportConfigurationDefinition tableImportConfigurationDefinition = new TableImportConfigurationDefinition();
    tableImportConfigurationDefinition.setTable(configuration.getTableName());
    tableImportConfigurationDefinition.setSchema(configuration.getSchemaName());
    tableImportConfigurationDefinition.setFile(configuration.getFileName());
    tableImportConfigurationDefinition.setHeader(configuration.getHeader());
    tableImportConfigurationDefinition.setUseHeaderNames(configuration.getUseHeaderNames());
    tableImportConfigurationDefinition.setDelimField(configuration.getDelimField());
    tableImportConfigurationDefinition.setDelimEnclosing(configuration.getDelimEnclosing());
    tableImportConfigurationDefinition.setDistinguishEmptyFromNull(configuration.getDistinguishEmptyFromNull());
    tableImportConfigurationDefinition.setKeysAsMap(handleKeyValuePairs(configuration.getKeys()));
    tableImportArtifact.getImportConfigurationDefinition().add(tableImportConfigurationDefinition);
  }

  /**
   * Adds the hdbti to csv relation.
   *
   * @param tableImportArtifact the table import artifact
   * @param configuration the configuration
   * @param hdbtiLocation the hdbti location
   */
  private void addHdbtiToCsvRelation(TableImportArtifact tableImportArtifact, HDBTIImportConfigModel configuration,
      String hdbtiLocation) {
    String csvParsedFilePath = hdbtiCoreService.convertToActualFileName(configuration.getFileName());
    TableImportToCsvRelation tableImportToCsvRelation = new TableImportToCsvRelation();
    IResource csvFile = repository.getResource(csvParsedFilePath);
    tableImportToCsvRelation.setCsv(csvParsedFilePath);
    tableImportToCsvRelation.setHdbti(hdbtiLocation);
    tableImportToCsvRelation.setCsvHash(DigestUtils.md5Hex(getContentFromResource(csvFile)));
    tableImportArtifact.getTableImportToCsvRelations().add(tableImportToCsvRelation);

  }

  /**
   * Handle key value pairs.
   *
   * @param pairs the pairs
   * @return the map
   */
  private Map<String, ArrayList<String>> handleKeyValuePairs(List<HDBTIImportConfigModel.Pair> pairs) {
    if (pairs == null) {
      return new HashMap<>();
    }

    return pairs.stream().collect(Collectors.toMap(HDBTIImportConfigModel.Pair::getColumn, HDBTIImportConfigModel.Pair::getValues));
  }

  /**
   * Gets the content from resource.
   *
   * @param resource the resource
   * @return the content from resource
   */
  private String getContentFromResource(IResource resource) {
    byte[] content = resource.getContent();
    String contentAsString = null;
    try {
      contentAsString = IOUtils
          .toString(new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8));
    } catch (IOException e) {
      String errMsg = "Error occurred while reading the content from CSV File. ";
      CommonsUtils.logProcessorErrors(errMsg + e.getMessage(), CommonsConstants.PARSER_ERROR, resource.getPath(), CommonsConstants.HDBTI_PARSER);
      logger.error(errMsg, e);
    }
    return contentAsString;
  }
}
