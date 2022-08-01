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
package com.codbex.kronos.hdbti.processors;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.model.PersistenceTableColumnModel;
import org.eclipse.dirigible.database.persistence.model.PersistenceTableModel;
import org.eclipse.dirigible.engine.odata2.transformers.DBMetadataUtil;
import org.eclipse.dirigible.repository.api.IRepository;
import org.eclipse.dirigible.repository.api.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdbti.api.IHDBTICoreService;
import com.codbex.kronos.hdbti.api.IHDBTIProcessor;
import com.codbex.kronos.hdbti.api.TableImportException;
import com.codbex.kronos.hdbti.model.ImportedCSVRecordModel;
import com.codbex.kronos.hdbti.model.TableImportConfigurationDefinition;
import com.codbex.kronos.hdbti.service.HDBTICoreService;
import com.codbex.kronos.hdbti.utils.HDBTIUtils;
import com.codbex.kronos.parser.hdbti.custom.HDBTIParser;
import com.codbex.kronos.parser.hdbti.exception.HDBTISyntaxErrorException;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportConfigModel;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportConfigModel.Pair;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

public class HDBTIProcessor implements IHDBTIProcessor {

  private static final Logger logger = LoggerFactory.getLogger(HDBTIProcessor.class);

  private static final String ERROR_MESSAGE_COLUMNS_COUNT = "Error while trying to process csv location [%s]. The number of csv records should be equal to the number of columns of a db entity.";

  private final DBMetadataUtil dbMetadataUtil = new DBMetadataUtil();
  private final IRepository repository = (IRepository) StaticObjects.get(StaticObjects.REPOSITORY);
  private final IHDBTICoreService hdbtiCoreService = new HDBTICoreService();
  private final HDBTIParser hdbtiParser = new HDBTIParser();

  @Override
  public void process(TableImportConfigurationDefinition tableImportConfigurationDefinition, Connection connection)
      throws DataStructuresException, TableImportException, SQLException {
    IResource resource = repository.getResource(hdbtiCoreService.convertToActualFileName(tableImportConfigurationDefinition.getFile()));
    String tableName = hdbtiCoreService.convertToActualTableName(tableImportConfigurationDefinition.getTable());
    CSVParser csvParser = getCsvParser(tableImportConfigurationDefinition, resource);
    PersistenceTableModel tableMetadata = getTableMetadata(tableImportConfigurationDefinition);
    if (tableMetadata == null || csvParser == null) {
      return;
    }

    Map<String, ImportedCSVRecordModel> allImportedRecordsByCsv = hdbtiCoreService.getImportedCSVRecordsByTableAndCSVLocation(tableName, tableImportConfigurationDefinition.getFile());

    List<ImportedCSVRecordModel> importedCSVRecordsToUpdate = new ArrayList<>();
    List<CSVRecord> recordsToInsert = new ArrayList<>();
    List<CSVRecord> recordsToUpdate = new ArrayList<>();

    Integer pkIndexForCSVRecord = null;
    for (CSVRecord csvRecord : csvParser) {
      if (recordShouldBeIncluded(csvRecord, tableMetadata.getColumns(), tableImportConfigurationDefinition.getKeysAsMap())) {
        if (csvRecord.size() != tableMetadata.getColumns().size()) {
          String errorMessage = String.format(ERROR_MESSAGE_COLUMNS_COUNT, tableImportConfigurationDefinition.getFile());
          CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR, tableImportConfigurationDefinition.getHdbtiFileName(), CommonsConstants.HDBTI_PARSER);
          throw new TableImportException(errorMessage);
        }

        if (pkIndexForCSVRecord == null) {
          pkIndexForCSVRecord = hdbtiCoreService.getPkIndexForCSVRecord(csvRecord, tableName, csvParser.getHeaderNames());
        }
        String pkForCSVRecord = csvRecord.get(pkIndexForCSVRecord);
        String csvRecordHash = hdbtiCoreService.getCSVRecordHash(csvRecord);
        if (pkForCSVRecord != null && !allImportedRecordsByCsv.containsKey(pkForCSVRecord)) {
          recordsToInsert.add(csvRecord);
        } else if (pkForCSVRecord != null && !allImportedRecordsByCsv.get(pkForCSVRecord).getHash().equals(csvRecordHash)) {
          recordsToUpdate.add(csvRecord);
          ImportedCSVRecordModel importedCSVRecordModelToUpdate = allImportedRecordsByCsv.get(pkForCSVRecord);
          importedCSVRecordModelToUpdate.setHash(csvRecordHash);
          importedCSVRecordsToUpdate.add(importedCSVRecordModelToUpdate);

          allImportedRecordsByCsv.remove(pkForCSVRecord);
        } else if (pkForCSVRecord != null) {
          allImportedRecordsByCsv.remove(pkForCSVRecord);
        }
      }
    }

    hdbtiCoreService.insertCsvRecords(recordsToInsert, csvParser.getHeaderNames(), tableImportConfigurationDefinition);
    hdbtiCoreService.updateCsvRecords(recordsToUpdate, csvParser.getHeaderNames(), importedCSVRecordsToUpdate,
        tableImportConfigurationDefinition);
    hdbtiCoreService.removeCsvRecords(new ArrayList<>(allImportedRecordsByCsv.values()), tableName);
  }

  private boolean recordShouldBeIncluded(CSVRecord record, List<PersistenceTableColumnModel> columns, Map<String, ArrayList<String>> keys) {
    if (keys == null || keys.isEmpty()) {
      return true;
    }

    boolean match = false;
    for (PersistenceTableColumnModel column : columns) {
      String columnName = column.getName();
      ArrayList<String> values = keys.get(columnName);
      values = keys.get(columnName) == null ? keys.get(columnName.toLowerCase()) : values;
      if (values != null) {
        match = values.contains(record.get(columns.indexOf(column)));
      }
    }

    return match;
  }

  private PersistenceTableModel getTableMetadata(TableImportConfigurationDefinition tableImportConfigurationDefinition) {
    String tableName = hdbtiCoreService.convertToActualTableName(tableImportConfigurationDefinition.getTable());
    try {
      return dbMetadataUtil.getTableMetadata(tableName, tableImportConfigurationDefinition.getSchema());
    } catch (SQLException sqlException) {
      String errorMessage = String.format("Error occurred while trying to read table metadata for table with name: %s", tableName);
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR,
          tableImportConfigurationDefinition.getHdbtiFileName(), CommonsConstants.HDBTI_PARSER);
      logger.error(errorMessage, sqlException);
    }
    return null;
  }

  private CSVParser getCsvParser(TableImportConfigurationDefinition tableImportConfigurationDefinition, IResource resource)
      throws TableImportException {
    try {
      String contentAsString = IOUtils
          .toString(new InputStreamReader(new ByteArrayInputStream(resource.getContent()), StandardCharsets.UTF_8));
      CSVFormat csvFormat = createCSVFormat(tableImportConfigurationDefinition);
      return CSVParser.parse(contentAsString, csvFormat);
    } catch (IOException e) {
      String errorMessage = String.format("Error occurred while trying to parse csv imported from hdbti file: %s",
          tableImportConfigurationDefinition.getHdbtiFileName());
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR,
          tableImportConfigurationDefinition.getHdbtiFileName(), CommonsConstants.HDBTI_PARSER);
      logger.error(errorMessage, e);
    }

    return null;
  }

  private CSVFormat createCSVFormat(TableImportConfigurationDefinition tableImportConfigurationDefinition)
      throws TableImportException {
    if (tableImportConfigurationDefinition.getDelimField() != null && (!tableImportConfigurationDefinition.getDelimField().equals(",")
        && !tableImportConfigurationDefinition.getDelimField().equals(";"))) {
      String errorMessage = "Only ';' or ',' characters are supported as delimiters for csv files.";
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR,
          tableImportConfigurationDefinition.getHdbtiFileName(), CommonsConstants.HDBTI_PARSER);
      throw new TableImportException(errorMessage);
    } else if (tableImportConfigurationDefinition.getDelimEnclosing() != null
        && tableImportConfigurationDefinition.getDelimEnclosing().length() > 1) {
      String errorMessage = "Delim enclosing should only contain one character.";
      CommonsUtils.logProcessorErrors(errorMessage, CommonsConstants.PROCESSOR_ERROR,
          tableImportConfigurationDefinition.getHdbtiFileName(), CommonsConstants.HDBTI_PARSER);
      throw new TableImportException(errorMessage);
    }

    char delimiter = Objects.isNull(tableImportConfigurationDefinition.getDelimField()) ? ','
        : tableImportConfigurationDefinition.getDelimField().charAt(0);
    char quote = Objects.isNull(tableImportConfigurationDefinition.getDelimEnclosing()) ? '"'
        : tableImportConfigurationDefinition.getDelimEnclosing().charAt(0);
    CSVFormat csvFormat = CSVFormat.newFormat(delimiter).withIgnoreEmptyLines().withQuote(quote).withEscape('\\');

    boolean useHeader = !Objects.isNull(tableImportConfigurationDefinition.getHeader()) && tableImportConfigurationDefinition.getHeader();
    if (useHeader) {
      csvFormat = csvFormat.withFirstRecordAsHeader();
    }

    return csvFormat;
  }

  public List<HDBTIImportConfigModel> parseHdbtiToJSON(String location, byte[] file)
      throws ArtifactParserException, IOException, HDBTISyntaxErrorException {
    if (location == null) {
      location = "undefined";
    }
    HDBTIImportModel parsedFile = hdbtiParser.parse(location, new String(file, StandardCharsets.UTF_8));
    parsedFile.getConfigModels().forEach(el -> el.setFileName(HDBTIUtils.convertHDBTIFilePropertyToPath(el.getFileName())));
    return parsedFile.getConfigModels();
  }

  public String parseJSONtoHdbti(ArrayList<HDBTIImportConfigModel> json) {
    for (HDBTIImportConfigModel el : json) {
      try {
        el.setFileName(HDBTIUtils.convertPathToHDBTIFileProperty(el.getFileName()));
      } catch (IllegalArgumentException ex) {
        CommonsUtils.logProcessorErrors(ex.getMessage(), CommonsConstants.PROCESSOR_ERROR, el.getFileName(),
            CommonsConstants.HDBTI_PARSER);
        throw ex;
      }
      if (!el.getTableName().contains("::") && el.getSchemaName() == null) {
        String errMsg = "Missing schema property";
        CommonsUtils.logProcessorErrors(errMsg, CommonsConstants.PROCESSOR_ERROR, el.getFileName(), CommonsConstants.HDBTI_PARSER);
        throw new IllegalArgumentException(errMsg);
      }
      if (el.getSchemaName() != null && !HDBTIUtils.isCorrectPropertySyntax(el.getSchemaName())) {
        String errMsg = "Schema property contains unsupported symbols: " + el.getSchemaName();
        CommonsUtils.logProcessorErrors(errMsg, CommonsConstants.PROCESSOR_ERROR, el.getFileName(), CommonsConstants.HDBTI_PARSER);
        throw new IllegalArgumentException(errMsg);
      }
      if (!HDBTIUtils.isCorrectTablePropertySyntax(el.getTableName())) {
        String errMsg = "Table property contains unsupported symbols: " + el.getTableName();
        CommonsUtils.logProcessorErrors(errMsg, CommonsConstants.PROCESSOR_ERROR, el.getFileName(), CommonsConstants.HDBTI_PARSER);
        throw new IllegalArgumentException(errMsg);
      }
      for (Pair key : el.getKeys()) {
        if (!HDBTIUtils.isCorrectPropertySyntax(key.getColumn())) {
          String errMsg = "key column property contains unsupported symbols: " + key.getColumn();
          CommonsUtils.logProcessorErrors(errMsg, CommonsConstants.PROCESSOR_ERROR, el.getFileName(),
              CommonsConstants.HDBTI_PARSER);
          throw new IllegalArgumentException(errMsg);
        }
      }
    }

    HDBTIImportModel model = new HDBTIImportModel();
    model.setConfigModels(json);
    return model.toString();
  }
}
