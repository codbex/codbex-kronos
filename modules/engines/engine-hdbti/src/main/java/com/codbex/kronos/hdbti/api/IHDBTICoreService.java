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
package com.codbex.kronos.hdbti.api;

import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdbti.model.ImportedCSVRecordModel;
import com.codbex.kronos.hdbti.model.TableImportArtifact;
import com.codbex.kronos.hdbti.model.TableImportConfigurationDefinition;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVRecord;

/**
 * The Interface IHDBTICoreService.
 */
public interface IHDBTICoreService {

  /**
   * Insert csv records.
   *
   * @param recordsToInsert the records to insert
   * @param headerNames the header names
   * @param tableImportConfigurationDefinition the table import configuration definition
   * @throws SQLException the SQL exception
   */
  void insertCsvRecords(List<CSVRecord> recordsToInsert, List<String> headerNames,
      TableImportConfigurationDefinition tableImportConfigurationDefinition)
      throws SQLException;

  /**
   * Gets the imported CSV records by table and CSV location.
   *
   * @param tableName the table name
   * @param csvLocation the csv location
   * @return the imported CSV records by table and CSV location
   * @throws DataStructuresException the data structures exception
   */
  Map<String, ImportedCSVRecordModel> getImportedCSVRecordsByTableAndCSVLocation(String tableName, String csvLocation)
      throws DataStructuresException;

  /**
   * Clean up hdbti related data.
   *
   * @throws TableImportException the table import exception
   */
  void cleanUpHdbtiRelatedData() throws TableImportException;

  /**
   * Update csv records.
   *
   * @param csvRecords the csv records
   * @param headerNames the header names
   * @param importedCsvRecordsToUpdate the imported csv records to update
   * @param tableImportConfigurationDefinition the table import configuration definition
   * @throws SQLException the SQL exception
   */
  void updateCsvRecords(List<CSVRecord> csvRecords, List<String> headerNames,
      List<ImportedCSVRecordModel> importedCsvRecordsToUpdate,
      TableImportConfigurationDefinition tableImportConfigurationDefinition) throws SQLException;

  /**
   * Removes the csv records.
   *
   * @param importedCSVRecordModels the imported CSV record models
   * @param tableName the table name
   */
  void removeCsvRecords(List<ImportedCSVRecordModel> importedCSVRecordModels, String tableName);

  /**
   * Refresh csv relations.
   *
   * @param tableImportArtifact the table import artifact
   */
  void refreshCsvRelations(TableImportArtifact tableImportArtifact);

  /**
   * Gets the pk index for CSV record.
   *
   * @param csvRecord the csv record
   * @param tableName the table name
   * @param headerNames the header names
   * @return the pk index for CSV record
   */
  int getPkIndexForCSVRecord(CSVRecord csvRecord, String tableName, List<String> headerNames);

  /**
   * Gets the CSV record hash.
   *
   * @param csvRecord the csv record
   * @return the CSV record hash
   */
  String getCSVRecordHash(CSVRecord csvRecord);

  /**
   * Convert to actual table name.
   *
   * @param tableName the table name
   * @return the string
   */
  String convertToActualTableName(String tableName);

  /**
   * Convert to actual file name.
   *
   * @param fileNamePath the file name path
   * @return the string
   */
  String convertToActualFileName(String fileNamePath);
}
