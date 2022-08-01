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

public interface IHDBTICoreService {

  void insertCsvRecords(List<CSVRecord> recordsToInsert, List<String> headerNames,
      TableImportConfigurationDefinition tableImportConfigurationDefinition)
      throws SQLException;

  Map<String, ImportedCSVRecordModel> getImportedCSVRecordsByTableAndCSVLocation(String tableName, String csvLocation)
      throws DataStructuresException;

  void cleanUpHdbtiRelatedData() throws TableImportException;

  void updateCsvRecords(List<CSVRecord> csvRecords, List<String> headerNames,
      List<ImportedCSVRecordModel> importedCsvRecordsToUpdate,
      TableImportConfigurationDefinition tableImportConfigurationDefinition) throws SQLException;

  void removeCsvRecords(List<ImportedCSVRecordModel> importedCSVRecordModels, String tableName);

  void refreshCsvRelations(TableImportArtifact tableImportArtifact);

  int getPkIndexForCSVRecord(CSVRecord csvRecord, String tableName, List<String> headerNames);

  String getCSVRecordHash(CSVRecord csvRecord);

  String convertToActualTableName(String tableName);

  String convertToActualFileName(String fileNamePath);
}
