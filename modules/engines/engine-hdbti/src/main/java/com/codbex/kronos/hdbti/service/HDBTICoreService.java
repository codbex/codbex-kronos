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
package com.codbex.kronos.hdbti.service;

import static java.lang.String.format;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.model.PersistenceTableColumnModel;
import org.eclipse.dirigible.database.persistence.model.PersistenceTableModel;
import org.eclipse.dirigible.database.persistence.utils.DatabaseMetadataUtil;
import org.eclipse.dirigible.engine.odata2.transformers.DBMetadataUtil;
import org.eclipse.dirigible.repository.api.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdbti.api.ICSVRecordDao;
import com.codbex.kronos.hdbti.api.ICSVToHDBTIRelationDao;
import com.codbex.kronos.hdbti.api.IHDBTICoreService;
import com.codbex.kronos.hdbti.api.IImportedCSVRecordDao;
import com.codbex.kronos.hdbti.api.ITableImportArtifactDao;
import com.codbex.kronos.hdbti.api.ITableImportModel;
import com.codbex.kronos.hdbti.api.TableImportException;
import com.codbex.kronos.hdbti.dao.CSVRecordDao;
import com.codbex.kronos.hdbti.dao.CSVToHDBTIRelationDao;
import com.codbex.kronos.hdbti.dao.ImportedCSVRecordDao;
import com.codbex.kronos.hdbti.dao.TableImportArtifactDao;
import com.codbex.kronos.hdbti.model.ImportedCSVRecordModel;
import com.codbex.kronos.hdbti.model.TableImportArtifact;
import com.codbex.kronos.hdbti.model.TableImportConfigurationDefinition;
import com.codbex.kronos.hdbti.utils.CSVRecordMetadata;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsDBUtils;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.Utils;

/**
 * The Class HDBTICoreService.
 */
public class HDBTICoreService implements IHDBTICoreService {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBTICoreService.class);

  /** The csv record dao. */
  private ICSVRecordDao csvRecordDao = new CSVRecordDao();
  
  /** The imported CSV record dao. */
  private IImportedCSVRecordDao importedCSVRecordDao = new ImportedCSVRecordDao();
  
  /** The table import artifact dao. */
  private ITableImportArtifactDao tableImportArtifactDao = new TableImportArtifactDao();
  
  /** The csv to hdbti relation dao. */
  private ICSVToHDBTIRelationDao csvToHdbtiRelationDao = new CSVToHDBTIRelationDao();
  
  /** The repository. */
  private IRepository repository = (IRepository) StaticObjects.get(StaticObjects.REPOSITORY);
  
  /** The db metadata util. */
  private DBMetadataUtil dbMetadataUtil = new DBMetadataUtil();
  
  /** The data source. */
  private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.DATASOURCE);

  /**
   * Insert csv records.
   *
   * @param recordsToInsert the records to insert
   * @param headerNames the header names
   * @param tableImportConfigurationDefinition the table import configuration definition
   * @throws SQLException the SQL exception
   */
  @Override
  public void insertCsvRecords(List<CSVRecord> recordsToInsert, List<String> headerNames,
      TableImportConfigurationDefinition tableImportConfigurationDefinition)
      throws SQLException {
    String tableName = convertToActualTableName(tableImportConfigurationDefinition.getTable());
    String schema = tableImportConfigurationDefinition.getSchema();
    PersistenceTableModel tableModel = dbMetadataUtil.getTableMetadata(tableName, schema);
    if(null == tableModel) {
      logTableNotFoundError(tableName, schema, tableImportConfigurationDefinition);
      return;
    }
    for (CSVRecord csvRecord : recordsToInsert) {
      try {
        CSVRecordMetadata csvRecordMetadata = new CSVRecordMetadata(csvRecord, tableModel, headerNames,
            tableImportConfigurationDefinition.getDistinguishEmptyFromNull());
        csvRecordDao.save(csvRecordMetadata);

        ImportedCSVRecordModel importedCSVRecordModel = new ImportedCSVRecordModel();
        importedCSVRecordModel.setCsvLocation(tableImportConfigurationDefinition.getFile());
        importedCSVRecordModel.setHash(getCSVRecordHash(csvRecord));
        importedCSVRecordModel.setHdbtiLocation(tableImportConfigurationDefinition.getHdbtiFileName());
        importedCSVRecordModel.setRowId(csvRecordMetadata.getCsvRecordPkValue());
        importedCSVRecordModel.setTableName(tableName);
        importedCSVRecordDao.save(importedCSVRecordModel);
      } catch (SQLException e) {
        logger.error(csvRecord.toString());
        logger.error("Error occurred while inserting the csv values in the table pointed by HDBTI file", e);
        logger.error(format("Error occurred while processing %s for table %s at record %d",
            tableImportConfigurationDefinition.getFile(), tableImportConfigurationDefinition.getTable(),
            csvRecord.getRecordNumber()), e);
      } catch (Exception e) {
        logger.error(csvRecord.toString());
        logger.error(format("Error occurred while processing %s for table %s at record %d",
            tableImportConfigurationDefinition.getFile(), tableImportConfigurationDefinition.getTable(),
            csvRecord.getRecordNumber()), e);
      }
    }
  }

  /**
   * Gets the imported CSV records by table and CSV location.
   *
   * @param tableName the table name
   * @param csvLocation the csv location
   * @return the imported CSV records by table and CSV location
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public Map<String, ImportedCSVRecordModel> getImportedCSVRecordsByTableAndCSVLocation(String tableName, String csvLocation)
      throws DataStructuresException {
    return importedCSVRecordDao.getImportedCSVRecordsByTableAndCSVLocation(tableName, csvLocation)
        .stream()
        .collect(Collectors.toMap(ImportedCSVRecordModel::getRowId, importModel -> importModel));
  }

  /**
   * Clean up hdbti related data.
   *
   * @throws TableImportException the table import exception
   */
  @Override
  public void cleanUpHdbtiRelatedData() throws TableImportException {
    List<TableImportArtifact> tableImportArtifacts = tableImportArtifactDao.getTableImportArtifacts();

    for (TableImportArtifact tableImportArtifact : tableImportArtifacts) {
      if (tableImportArtifact.getType().equals(ITableImportModel.TYPE_HDBTI)
          && !repository.hasResource(Utils.convertToFullPath(tableImportArtifact.getLocation()))) {
        tableImportArtifactDao.removeTableImportArtifact(tableImportArtifact.getLocation());
        csvToHdbtiRelationDao.deleteCsvAndHdbtiRelations(tableImportArtifact.getLocation());
        removeCSVRecordsFromDb(tableImportArtifact.getLocation());
        logger
            .warn("Cleaned up HDBTI file [{}] from location: {}", tableImportArtifact.getName(),
                tableImportArtifact.getLocation());
      }
    }
  }

  /**
   * Update csv records.
   *
   * @param csvRecords the csv records
   * @param headerNames the header names
   * @param importedCsvRecordsToUpdate the imported csv records to update
   * @param tableImportConfigurationDefinition the table import configuration definition
   * @throws SQLException the SQL exception
   */
  @Override
  public void updateCsvRecords(List<CSVRecord> csvRecords, List<String> headerNames,
      List<ImportedCSVRecordModel> importedCsvRecordsToUpdate,
      TableImportConfigurationDefinition tableImportConfigurationDefinition) throws SQLException {
    String tableName = convertToActualTableName(tableImportConfigurationDefinition.getTable());
    String schema = CommonsDBUtils.getTableSchema(dataSource, tableName);
    PersistenceTableModel tableModel = dbMetadataUtil.getTableMetadata(tableName, schema);
    if(null == tableModel) {
      logTableNotFoundError(tableName, schema, tableImportConfigurationDefinition);
      return;
    }

    for (CSVRecord csvRecord : csvRecords) {
      try {
        CSVRecordMetadata csvRecordMetadata = new CSVRecordMetadata(csvRecord, tableModel, headerNames,
            tableImportConfigurationDefinition.getDistinguishEmptyFromNull());
        csvRecordDao.update(csvRecordMetadata);
        for (ImportedCSVRecordModel importedCSVRecordModel : importedCsvRecordsToUpdate) {
          importedCSVRecordDao.update(importedCSVRecordModel);
        }
      } catch (SQLException throwable) {
        logger.error(throwable.getMessage(), throwable);
      }
    }
  }

  /**
   * Log table not found error.
   *
   * @param tableName the table name
   * @param schema the schema
   * @param tableImportConfigurationDefinition the table import configuration definition
   */
  private void logTableNotFoundError(String tableName, String schema, TableImportConfigurationDefinition tableImportConfigurationDefinition) {
    String errorMsg = "Error occurred while processing csv file."
        + " Table with name [" + tableName + "] was not found in schema [" + schema + "].";
    logger.error(errorMsg);
    CommonsUtils.logProcessorErrors(errorMsg, CommonsConstants.PROCESSOR_ERROR,
        tableImportConfigurationDefinition.getHdbtiFileName(), CommonsConstants.HDBTI_PARSER);
  }

  /**
   * Removes the csv records.
   *
   * @param importedCSVRecordModels the imported CSV record models
   * @param tableName the table name
   */
  @Override
  public void removeCsvRecords(List<ImportedCSVRecordModel> importedCSVRecordModels, String tableName) {
    List<String> idsToRemove = importedCSVRecordModels.stream().map(ImportedCSVRecordModel::getRowId).collect(Collectors.toList());

    try {
      csvRecordDao.deleteAll(idsToRemove, tableName);
      importedCSVRecordDao.deleteAll(importedCSVRecordModels);
    } catch (SQLException sqlException) {
      logger.error(
          String.format("An error occurred while trying to delete removed CSVs with ids: %s from table %s", String.join(",", idsToRemove),
              tableName));
    }
  }

  /**
   * Refresh csv relations.
   *
   * @param tableImportArtifact the table import artifact
   */
  @Override
  public void refreshCsvRelations(TableImportArtifact tableImportArtifact) {
    csvToHdbtiRelationDao.deleteCsvAndHdbtiRelations(Utils.convertToFullPath(tableImportArtifact.getLocation()));
    csvToHdbtiRelationDao.persistNewCsvAndHdbtiRelations(tableImportArtifact);
  }

  /**
   * Gets the pk index for CSV record.
   *
   * @param csvRecord the csv record
   * @param tableName the table name
   * @param headerNames the header names
   * @return the pk index for CSV record
   */
  @Override
  public int getPkIndexForCSVRecord(CSVRecord csvRecord, String tableName, List<String> headerNames) {
	  PersistenceTableModel tableMetadata = getTableMetadata(tableName);
	    if(tableMetadata == null){
	      String errorMessage = "Error occurred while processing csv file. Table with name [" + tableName + "] was not found.";
		logger.error(errorMessage);
		throw new IllegalArgumentException(errorMessage);
	    }
	    List<PersistenceTableColumnModel> columnModels = tableMetadata.getColumns();
	    if (headerNames.size() > 0) {
	      String pkColumnName = columnModels.stream().filter(PersistenceTableColumnModel::isPrimaryKey).findFirst().get().getName();
	      return headerNames.indexOf(pkColumnName);
	    }

	    for (int i = 0; i < csvRecord.size(); i++) {
	     if (columnModels.get(i).isPrimaryKey()) {
	        return i;
	      }
	    }

	    return 0;
  }

  /**
   * Gets the CSV record hash.
   *
   * @param csvRecord the csv record
   * @return the CSV record hash
   */
  @Override
  public String getCSVRecordHash(CSVRecord csvRecord) {
    StringBuilder joinedValues = new StringBuilder();

    for (int i = 0; i < csvRecord.size(); i++) {
      joinedValues.append(csvRecord.get(i));
      if (i != csvRecord.size() - 1) {
        joinedValues.append(",");
      }
    }

    return DigestUtils.md5Hex(joinedValues.toString());
  }

  /**
   * Convert to actual table name.
   *
   * @param tableName the table name
   * @return the string
   */
  @Override
  public String convertToActualTableName(String tableName) {
    boolean caseSensitive = Boolean.parseBoolean(Configuration.get(DatabaseMetadataUtil.DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE, "false"));
    if (caseSensitive) {
      tableName = "\"" + tableName + "\"";
    }
    return tableName;
  }

  /**
   * Convert to actual file name.
   *
   * @param fileNamePath the file name path
   * @return the string
   */
  @Override
  public String convertToActualFileName(String fileNamePath) {
    String fileName = fileNamePath.substring(fileNamePath.lastIndexOf(':') + 1);

    return "/registry/public/" + fileNamePath.substring(0, fileNamePath.indexOf(':')).replaceAll("\\.", "/") + "/" + fileName;
  }

  /**
   * Gets the table metadata.
   *
   * @param tableName the table name
   * @return the table metadata
   */
  private PersistenceTableModel getTableMetadata(String tableName) {
    try {
      return dbMetadataUtil.getTableMetadata(tableName, CommonsDBUtils.getTableSchema(dataSource, tableName));
    } catch (SQLException sqlException) {
      logger.error(String.format("Error occurred while trying to read table metadata for table with name: %s", tableName), sqlException);
    }
    return null;
  }

  /**
   * Removes the CSV records from db.
   *
   * @param hdbtiLocation the hdbti location
   */
  private void removeCSVRecordsFromDb(String hdbtiLocation) {
    List<ImportedCSVRecordModel> csvRecordsToRemove = importedCSVRecordDao.getImportedCSVsByHdbtiLocation(hdbtiLocation);
    csvRecordsToRemove.sort(Comparator.comparing(ImportedCSVRecordModel::getCreatedAt, Comparator.reverseOrder()));
    try {
      for (ImportedCSVRecordModel csvRecord :
          csvRecordsToRemove) {
        csvRecordDao.delete(csvRecord.getRowId(), csvRecord.getTableName());
      }

      importedCSVRecordDao.deleteAll(csvRecordsToRemove);
    } catch (SQLException sqlException) {
      logger.error(
          String.format("Error occurred while trying to remove imported csv records after a deletion of an hdbti file with location: %s",
              hdbtiLocation), sqlException);
    }
  }
}
