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

import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;
import com.codbex.kronos.hdbti.model.XSKImportedCSVRecordModel;
import com.codbex.kronos.hdbti.model.XSKTableImportArtifact;
import com.codbex.kronos.hdbti.model.XSKTableImportConfigurationDefinition;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.commons.csv.CSVRecord;

public interface IXSKHDBTICoreService {

  void insertCsvRecords(List<CSVRecord> recordsToInsert, List<String> headerNames,
      XSKTableImportConfigurationDefinition tableImportConfigurationDefinition)
      throws SQLException;

  Map<String, XSKImportedCSVRecordModel> getImportedCSVRecordsByTableAndCSVLocation(String tableName, String csvLocation)
      throws XSKDataStructuresException;

  void cleanUpHdbtiRelatedData() throws XSKTableImportException;

  void updateCsvRecords(List<CSVRecord> csvRecords, List<String> headerNames,
      List<XSKImportedCSVRecordModel> importedCsvRecordsToUpdate,
      XSKTableImportConfigurationDefinition tableImportConfigurationDefinition) throws SQLException;

  void removeCsvRecords(List<XSKImportedCSVRecordModel> importedCSVRecordModels, String tableName);

  void refreshCsvRelations(XSKTableImportArtifact tableImportArtifact);

  int getPkIndexForCSVRecord(CSVRecord csvRecord, String tableName, List<String> headerNames);

  String getCSVRecordHash(CSVRecord csvRecord);

  String convertToActualTableName(String tableName);

  String convertToActualFileName(String fileNamePath);
}
