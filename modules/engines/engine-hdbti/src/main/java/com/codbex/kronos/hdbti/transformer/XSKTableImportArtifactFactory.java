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

import com.codbex.kronos.exceptions.XSKArtifactParserException;
import com.codbex.kronos.hdbti.api.IXSKHDBTICoreService;
import com.codbex.kronos.hdbti.api.IXSKTableImportArtifactFactory;
import com.codbex.kronos.hdbti.model.XSKTableImportArtifact;
import com.codbex.kronos.hdbti.model.XSKTableImportConfigurationDefinition;
import com.codbex.kronos.hdbti.model.XSKTableImportToCsvRelation;
import com.codbex.kronos.hdbti.service.XSKHDBTICoreService;
import com.codbex.kronos.parser.hdbti.custom.IXSKHDBTIParser;
import com.codbex.kronos.parser.hdbti.custom.XSKHDBTIParser;
import com.codbex.kronos.parser.hdbti.exception.XSKHDBTISyntaxErrorException;
import com.codbex.kronos.parser.hdbti.models.XSKHDBTIImportConfigModel;
import com.codbex.kronos.parser.hdbti.models.XSKHDBTIImportModel;
import com.codbex.kronos.utils.XSKCommonsConstants;
import com.codbex.kronos.utils.XSKCommonsUtils;

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

public class XSKTableImportArtifactFactory implements IXSKTableImportArtifactFactory {

  private static final Logger logger = LoggerFactory.getLogger(XSKTableImportArtifactFactory.class);

  private final IRepository repository = (IRepository) StaticObjects.get(StaticObjects.REPOSITORY);

  private final IXSKHDBTICoreService xskHdbtiCoreService = new XSKHDBTICoreService();

  private final IXSKHDBTIParser xskHdbtiParser = new XSKHDBTIParser();

  @Override
  public XSKTableImportArtifact parseTableImport(String content, String location)
      throws IOException, XSKHDBTISyntaxErrorException, XSKArtifactParserException {
    XSKTableImportArtifact tableImportArtifact = new XSKTableImportArtifact();
    List<XSKTableImportConfigurationDefinition> importConfigurationDefinitions = new ArrayList<>();
    List<XSKTableImportToCsvRelation> tableImportToCsvRelations = new ArrayList<>();

    tableImportArtifact.setImportConfigurationDefinition(importConfigurationDefinitions);
    tableImportArtifact.setTableImportToCsvRelations(tableImportToCsvRelations);

    XSKHDBTIImportModel importObject = xskHdbtiParser.parse(location, content);

    for (XSKHDBTIImportConfigModel configuration : importObject.getConfigModels()) {
      addHdbtiToCsvRelation(tableImportArtifact, configuration, location);
      addTableImportConfiguration(tableImportArtifact, configuration);
    }

    return tableImportArtifact;
  }

  @Override
  public IRepository getRepository() {
    return repository;
  }

  @Override
  public IXSKHDBTICoreService getXskHdbtiCoreService() {
    return xskHdbtiCoreService;
  }

  @Override
  public IXSKHDBTIParser getXskHdbtiParser() {
    return xskHdbtiParser;
  }

  private void addTableImportConfiguration(XSKTableImportArtifact tableImportArtifact, XSKHDBTIImportConfigModel configuration) {
    XSKTableImportConfigurationDefinition tableImportConfigurationDefinition = new XSKTableImportConfigurationDefinition();
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

  private void addHdbtiToCsvRelation(XSKTableImportArtifact tableImportArtifact, XSKHDBTIImportConfigModel configuration,
      String hdbtiLocation) {
    String csvParsedFilePath = xskHdbtiCoreService.convertToActualFileName(configuration.getFileName());
    XSKTableImportToCsvRelation tableImportToCsvRelation = new XSKTableImportToCsvRelation();
    IResource csvFile = repository.getResource(csvParsedFilePath);
    tableImportToCsvRelation.setCsv(csvParsedFilePath);
    tableImportToCsvRelation.setHdbti(hdbtiLocation);
    tableImportToCsvRelation.setCsvHash(DigestUtils.md5Hex(getContentFromResource(csvFile)));
    tableImportArtifact.getTableImportToCsvRelations().add(tableImportToCsvRelation);

  }

  private Map<String, ArrayList<String>> handleKeyValuePairs(List<XSKHDBTIImportConfigModel.Pair> pairs) {
    if (pairs == null) {
      return new HashMap<>();
    }

    return pairs.stream().collect(Collectors.toMap(XSKHDBTIImportConfigModel.Pair::getColumn, XSKHDBTIImportConfigModel.Pair::getValues));
  }

  private String getContentFromResource(IResource resource) {
    byte[] content = resource.getContent();
    String contentAsString = null;
    try {
      contentAsString = IOUtils
          .toString(new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8));
    } catch (IOException e) {
      String errMsg = "Error occurred while reading the content from CSV File. ";
      XSKCommonsUtils.logProcessorErrors(errMsg + e.getMessage(), XSKCommonsConstants.PARSER_ERROR, resource.getPath(), XSKCommonsConstants.HDBTI_PARSER);
      logger.error(errMsg, e);
    }
    return contentAsString;
  }
}
