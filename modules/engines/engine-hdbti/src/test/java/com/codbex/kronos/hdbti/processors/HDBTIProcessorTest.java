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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdbti.api.IHDBTICoreService;
import com.codbex.kronos.hdbti.api.IHDBTIProcessor;
import com.codbex.kronos.hdbti.api.ITableImportModel;
import com.codbex.kronos.hdbti.api.TableImportException;
import com.codbex.kronos.hdbti.model.Student;
import com.codbex.kronos.hdbti.model.ImportedCSVRecordModel;
import com.codbex.kronos.hdbti.model.TableImportArtifact;
import com.codbex.kronos.hdbti.model.TableImportConfigurationDefinition;
import com.codbex.kronos.hdbti.model.TableImportToCsvRelation;
import com.codbex.kronos.hdbti.module.HdbtiTestModule;
import com.codbex.kronos.hdbti.service.HDBTICoreService;
import com.codbex.kronos.parser.hdbti.exception.TablePropertySyntaxException;
import com.codbex.kronos.parser.hdbti.exception.HDBTISyntaxErrorException;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportConfigModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.PersistenceManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HDBTIProcessorTest {

  private static final Logger logger = LoggerFactory.getLogger(HDBTIProcessorTest.class);

  private static final String STUDENTS_CSV_LOCATION = "registry/public/university/students/students.csv";
  public static final String HDBTI_LOCATION = "university/data/Students.hdbti";
  public static final String CSV_FILE_LOCATION = "university.students:students.csv";
  public static final String STUDENTS_TABLE_NAME = "STUDENTS";

  private DataSource datasource;
  private DataSource systemDatasource;
  private IHDBTIProcessor processor;
  private IHDBTICoreService hdbtiCoreService;
  private final PersistenceManager<Student> studentManager = new PersistenceManager<>();
  private final PersistenceManager<ImportedCSVRecordModel> importedCsvRecordsManager = new PersistenceManager<>();
  private final PersistenceManager<TableImportArtifact> importArtifactManager = new PersistenceManager<>();
  private final PersistenceManager<TableImportToCsvRelation> importToCsvRelationManager = new PersistenceManager<>();

  @Before
  public void setUp() throws SQLException {
    HdbtiTestModule hdbtiTestModule = new HdbtiTestModule();
    hdbtiTestModule.configure();
    datasource = (DataSource) StaticObjects.get(StaticObjects.DATASOURCE);
    systemDatasource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);
    processor = new HDBTIProcessor();
    hdbtiCoreService = new HDBTICoreService();
    try (Connection connection = datasource.getConnection()) {
      studentManager.tableCreate(connection, Student.class);
    }
  }

  @After
  public void cleanup() throws SQLException {
    try (Connection connection = datasource.getConnection()) {
      if (studentManager.tableExists(connection, Student.class)) {
        studentManager.tableDrop(connection, Student.class);
      }
    }
    try (Connection connection = systemDatasource.getConnection()) {
      if (importedCsvRecordsManager.tableExists(connection, ImportedCSVRecordModel.class)) {
        importedCsvRecordsManager.tableDrop(connection, ImportedCSVRecordModel.class);
      }
    }
  }

  @Test
  public void testDataImportedCorrectly()
      throws DataStructuresException, SQLException, IOException, TableImportException {
    writeToFile(STUDENTS_CSV_LOCATION, getInitialContent());
    TableImportConfigurationDefinition importConfigurationDefinition = new TableImportConfigurationDefinition();
    importConfigurationDefinition.setFile(CSV_FILE_LOCATION);
    importConfigurationDefinition.setHdbtiFileName(HDBTI_LOCATION);
    importConfigurationDefinition.setTable(STUDENTS_TABLE_NAME);

    try (Connection connection = datasource.getConnection()) {
      processor.process(importConfigurationDefinition, connection);
      List<Student> students = studentManager.findAll(connection, Student.class);
      assertCorrectDataImported(students);
    }
    try (Connection connection = systemDatasource.getConnection()) {
      List<ImportedCSVRecordModel> importedCsvRecords = importedCsvRecordsManager.findAll(connection, ImportedCSVRecordModel.class);
      assertCorrectCsvRecordMetadataImported(importedCsvRecords);
      writeToFile(STUDENTS_CSV_LOCATION, "");
    }
  }

  @Test
  public void testChangeOfDataProperlyReflectedInDb()
      throws DataStructuresException, SQLException, IOException, TableImportException {
    TableImportConfigurationDefinition importConfigurationDefinition = new TableImportConfigurationDefinition();
    importConfigurationDefinition.setFile(CSV_FILE_LOCATION);
    importConfigurationDefinition.setHdbtiFileName(HDBTI_LOCATION);
    importConfigurationDefinition.setTable(STUDENTS_TABLE_NAME);

    try (Connection connection = datasource.getConnection()) {
      writeToFile(STUDENTS_CSV_LOCATION, getInitialContent());
      processor.process(importConfigurationDefinition, connection);

      writeToFile(STUDENTS_CSV_LOCATION, "");
      writeToFile(STUDENTS_CSV_LOCATION, getUpdatedContent());
      processor.process(importConfigurationDefinition, connection);

      List<Student> students1 = studentManager.findAll(connection, Student.class);
      assertEquals("Changed", students1.get(1).getFirstName());
      assertEquals("Changed", students1.get(1).getLastName());
      assertEquals(Timestamp.valueOf("2018-09-21 14:00:12"), students1.get(1).getSigned());
    }
  }

  @Test
  public void testCsvRecordRemovedFromCsvFileShouldRemoveFromDb()
      throws DataStructuresException, SQLException, IOException, TableImportException {
    TableImportConfigurationDefinition importConfigurationDefinitionWithRemovedRecord = new TableImportConfigurationDefinition();
    importConfigurationDefinitionWithRemovedRecord.setFile(CSV_FILE_LOCATION);
    importConfigurationDefinitionWithRemovedRecord.setHdbtiFileName(HDBTI_LOCATION);
    importConfigurationDefinitionWithRemovedRecord.setTable(STUDENTS_TABLE_NAME);

    try (Connection connection = datasource.getConnection()) {
      writeToFile(STUDENTS_CSV_LOCATION, getInitialContent());
      processor.process(importConfigurationDefinitionWithRemovedRecord, connection);

      writeToFile(STUDENTS_CSV_LOCATION, "");
      writeToFile(STUDENTS_CSV_LOCATION, getRemovedContent());
      processor.process(importConfigurationDefinitionWithRemovedRecord, connection);

      List<Student> students = studentManager.findAll(connection, Student.class);
      assertEquals(2, students.size());

      Student deletedStudent = studentManager.find(connection, Student.class, 1L);
      assertNull(deletedStudent);
      List<ImportedCSVRecordModel> importedCSVRecordModel = importedCsvRecordsManager.findAll(connection,
          ImportedCSVRecordModel.class);
      boolean isCsvRecordRemovedFromMetadataTable = importedCSVRecordModel.stream().anyMatch(i -> i.getRowId().equals("1"));
      assertFalse(isCsvRecordRemovedFromMetadataTable);
    }
    try (Connection connection = systemDatasource.getConnection()) {
      List<ImportedCSVRecordModel> importedCsvRecords = importedCsvRecordsManager.findAll(connection, ImportedCSVRecordModel.class);
      assertEquals(2, importedCsvRecords.size());
      writeToFile(STUDENTS_CSV_LOCATION, "");
    }
  }

  @Test
  public void testCsvRecordAddedShouldReflectDataInDb()
      throws DataStructuresException, SQLException, IOException, TableImportException {
    TableImportConfigurationDefinition importConfigurationDefinitionWithRemovedRecord = new TableImportConfigurationDefinition();
    importConfigurationDefinitionWithRemovedRecord.setFile(CSV_FILE_LOCATION);
    importConfigurationDefinitionWithRemovedRecord.setHdbtiFileName(HDBTI_LOCATION);
    importConfigurationDefinitionWithRemovedRecord.setTable(STUDENTS_TABLE_NAME);

    try (Connection connection = datasource.getConnection()) {
      writeToFile(STUDENTS_CSV_LOCATION, getInitialContent());
      processor.process(importConfigurationDefinitionWithRemovedRecord, connection);

      writeToFile(STUDENTS_CSV_LOCATION, "");
      writeToFile(STUDENTS_CSV_LOCATION, getAddedContent());

      processor.process(importConfigurationDefinitionWithRemovedRecord, connection);

      List<Student> students = studentManager.findAll(connection, Student.class);
      assertEquals(4, students.size());
    }
    try (Connection connection = systemDatasource.getConnection()) {
      List<ImportedCSVRecordModel> importedCSVRecordModel = importedCsvRecordsManager.findAll(connection,
          ImportedCSVRecordModel.class);
      assertEquals(4, importedCSVRecordModel.size());
      writeToFile(STUDENTS_CSV_LOCATION, "");
    }
  }

  @Test
  public void testCsvRecordWithCustomDelimFieldDelimEnclosingAndEscapeCharShouldInsertProperData()
      throws DataStructuresException, SQLException, IOException, TableImportException {
    TableImportConfigurationDefinition importConfigurationDefinition = new TableImportConfigurationDefinition();
    importConfigurationDefinition.setFile(CSV_FILE_LOCATION);
    importConfigurationDefinition.setHdbtiFileName(HDBTI_LOCATION);
    importConfigurationDefinition.setTable(STUDENTS_TABLE_NAME);
    importConfigurationDefinition.setDelimEnclosing("'");
    importConfigurationDefinition.setDelimField(";");

    try (Connection connection = datasource.getConnection()) {
      writeToFile(STUDENTS_CSV_LOCATION, getQuoteContent());
      processor.process(importConfigurationDefinition, connection);
      List<Student> students = studentManager.findAll(connection, Student.class);
      assertEquals(1, students.size());
      assertEquals("Georgi, 'Junior'", students.get(0).getFirstName());

      writeToFile(STUDENTS_CSV_LOCATION, "");
    }
  }

  @Test
  public void testInsertCsvRecordWithHeaders()
      throws DataStructuresException, SQLException, IOException, TableImportException {
    TableImportConfigurationDefinition importConfigurationDefinition = new TableImportConfigurationDefinition();
    importConfigurationDefinition.setFile(CSV_FILE_LOCATION);
    importConfigurationDefinition.setHdbtiFileName(HDBTI_LOCATION);
    importConfigurationDefinition.setTable(STUDENTS_TABLE_NAME);
    importConfigurationDefinition.setHeader(true);

    try (Connection connection = datasource.getConnection()) {
      writeToFile(STUDENTS_CSV_LOCATION, getInitialContentWithHeaders());
      processor.process(importConfigurationDefinition, connection);
      List<Student> students = studentManager.findAll(connection, Student.class);
      assertCorrectDataImported(students);

    }
    try (Connection connection = systemDatasource.getConnection()) {
      List<ImportedCSVRecordModel> importedCsvRecords = importedCsvRecordsManager.findAll(connection, ImportedCSVRecordModel.class);
      assertCorrectCsvRecordMetadataImported(importedCsvRecords);
      writeToFile(STUDENTS_CSV_LOCATION, "");
    }
  }

  @Test
  public void testUpdateCsvRecordWithHeadersShouldReflectDbData()
      throws DataStructuresException, SQLException, IOException, TableImportException {
    TableImportConfigurationDefinition importConfigurationDefinition = new TableImportConfigurationDefinition();
    importConfigurationDefinition.setFile(CSV_FILE_LOCATION);
    importConfigurationDefinition.setHdbtiFileName(HDBTI_LOCATION);
    importConfigurationDefinition.setTable(STUDENTS_TABLE_NAME);
    importConfigurationDefinition.setHeader(true);

    try (Connection connection = datasource.getConnection()) {
      writeToFile(STUDENTS_CSV_LOCATION, getInitialContentWithHeaders());
      processor.process(importConfigurationDefinition, connection);

      writeToFile(STUDENTS_CSV_LOCATION, "");
      writeToFile(STUDENTS_CSV_LOCATION, getChangedContentWithHeaders());
      processor.process(importConfigurationDefinition, connection);

      List<Student> students = studentManager.findAll(connection, Student.class);
      assertEquals("Changed", students.get(0).getFirstName());
      assertEquals("Changed", students.get(0).getLastName());
      assertEquals(25L, students.get(0).getAge().longValue());

      assertEquals("Changed", students.get(1).getFirstName());
      assertEquals("Changed", students.get(1).getLastName());
      assertEquals(25L, students.get(1).getAge().longValue());

      assertEquals("Changed", students.get(2).getFirstName());
      assertEquals("Changed", students.get(2).getLastName());
      assertEquals(25L, students.get(2).getAge().longValue());

      writeToFile(STUDENTS_CSV_LOCATION, "");
    }
  }

  @Test
  public void testCleanUpDeletesEverythingRelatedToHdbtiFile()
      throws DataStructuresException, SQLException, IOException, TableImportException {
    TableImportConfigurationDefinition importConfigurationDefinition = new TableImportConfigurationDefinition();
    importConfigurationDefinition.setFile(CSV_FILE_LOCATION);
    importConfigurationDefinition.setHdbtiFileName(HDBTI_LOCATION);
    importConfigurationDefinition.setTable(STUDENTS_TABLE_NAME);

    try (Connection connection = systemDatasource.getConnection()) {
      writeToFile(STUDENTS_CSV_LOCATION, getInitialContent());
      importArtifactManager.insert(connection, getTableImportArtifact());
      importToCsvRelationManager.insert(connection, getTableImportToCsvRelation());
      importToCsvRelationManager.insert(connection, getTableImportToCsvRelation());
    }
    try (Connection connection = datasource.getConnection()) {
      processor.process(importConfigurationDefinition, connection);
      hdbtiCoreService.cleanUpHdbtiRelatedData();
      List<Student> students = studentManager.findAll(connection, Student.class);
      assertEquals(0, students.size());
    }
    try (Connection connection = systemDatasource.getConnection()) {
      List<TableImportToCsvRelation> csvRelations = importToCsvRelationManager.findAll(connection, TableImportToCsvRelation.class);
      List<TableImportArtifact> importArtifacts = importArtifactManager.findAll(connection, TableImportArtifact.class);
      List<ImportedCSVRecordModel> importedCSVRecordModels = importedCsvRecordsManager
          .findAll(connection, ImportedCSVRecordModel.class);

      assertEquals(0, csvRelations.size());
      assertEquals(0, importArtifacts.size());
      assertEquals(0, importedCSVRecordModels.size());
    }
  }

  @Test
  public void testKeysProperlyFilterRecords()
      throws DataStructuresException, SQLException, IOException, TableImportException {
    TableImportConfigurationDefinition importConfigurationDefinition = new TableImportConfigurationDefinition();
    importConfigurationDefinition.setFile(CSV_FILE_LOCATION);
    importConfigurationDefinition.setHdbtiFileName(HDBTI_LOCATION);
    importConfigurationDefinition.setTable(STUDENTS_TABLE_NAME);

    Map<String, ArrayList<String>> keys = Map.of("FIRST_NAME", new ArrayList<String>(Arrays.asList("Georgi")));
    importConfigurationDefinition.setKeysAsMap(keys);

    try (Connection connection = datasource.getConnection()) {
      writeToFile(STUDENTS_CSV_LOCATION, getInitialContent());
      processor.process(importConfigurationDefinition, connection);

      List<Student> students = studentManager.findAll(connection, Student.class);
      assertEquals(1, students.size());
      assertEquals("Georgi", students.get(0).getFirstName());
    }
  }

  private TableImportToCsvRelation getTableImportToCsvRelation() {
    TableImportToCsvRelation csvRelation = new TableImportToCsvRelation();
    csvRelation.setCsv(CSV_FILE_LOCATION);
    csvRelation.setHdbti(HDBTI_LOCATION);
    csvRelation.setCsvHash("H@sh!");
    return csvRelation;
  }

  private TableImportArtifact getTableImportArtifact() {
    TableImportArtifact tableImportArtifact = new TableImportArtifact();
    tableImportArtifact.setLocation(HDBTI_LOCATION);
    tableImportArtifact.setName("DS_NAME");
    tableImportArtifact.setType(ITableImportModel.TYPE_HDBTI);
    tableImportArtifact.setCreatedBy("TestUser");
    tableImportArtifact.setHash("H@sh!");
    tableImportArtifact.setCreatedAt(new Timestamp(new Date().getTime()));
    return tableImportArtifact;
  }

  private void assertCorrectDataImported(List<Student> students) {
    int importedStudentsCount = 3;
    assertEquals(importedStudentsCount, students.size());
    assertEquals(1L, students.get(0).getId().longValue());
    assertEquals("Georgi", students.get(0).getFirstName());
    assertEquals("Georgiev", students.get(0).getLastName());
    assertEquals(27L, students.get(0).getAge().longValue());
    assertEquals(Timestamp.valueOf("2017-09-20 14:00:12"), students.get(0).getSigned());

    assertEquals(2L, students.get(1).getId().longValue());
    assertEquals("Sara", students.get(1).getFirstName());
    assertEquals("Toshkova", students.get(1).getLastName());
    assertEquals(21L, students.get(1).getAge().longValue());
    assertEquals(Timestamp.valueOf("2019-09-21 14:00:12"), students.get(1).getSigned());

    assertEquals(3L, students.get(2).getId().longValue());
    assertEquals("Toshko", students.get(2).getFirstName());
    assertEquals("Ivanov", students.get(2).getLastName());
    assertEquals(25L, students.get(2).getAge().longValue());
    assertEquals(Timestamp.valueOf("2018-09-23 14:00:12"), students.get(2).getSigned());
  }

  private void assertCorrectCsvRecordMetadataImported(List<ImportedCSVRecordModel> importedCsvRecords) {
    assertEquals(3, importedCsvRecords.size());

    assertEquals("1", importedCsvRecords.get(0).getRowId());
    assertEquals("STUDENTS", importedCsvRecords.get(0).getTableName());
    assertEquals("university.students:students.csv", importedCsvRecords.get(0).getCsvLocation());
    assertEquals(HDBTI_LOCATION, importedCsvRecords.get(0).getHdbtiLocation());

    assertEquals("2", importedCsvRecords.get(1).getRowId());
    assertEquals("STUDENTS", importedCsvRecords.get(1).getTableName());
    assertEquals("university.students:students.csv", importedCsvRecords.get(1).getCsvLocation());
    assertEquals(HDBTI_LOCATION, importedCsvRecords.get(1).getHdbtiLocation());

    assertEquals("3", importedCsvRecords.get(2).getRowId());
    assertEquals("STUDENTS", importedCsvRecords.get(2).getTableName());
    assertEquals("university.students:students.csv", importedCsvRecords.get(2).getCsvLocation());
    assertEquals(HDBTI_LOCATION, importedCsvRecords.get(2).getHdbtiLocation());
  }

  private String getInitialContent() {
    return "1,Georgi,Georgiev,27,2017-09-20 14:00:12" + System.lineSeparator()
        + "2,Sara,Toshkova,21,2019-09-21 14:00:12" + System.lineSeparator()
        + "3,Toshko,Ivanov,25,2018-09-23 14:00:12" + System.lineSeparator();
  }

  private String getInitialContentWithHeaders() {
    return "\"LAST_NAME\",\"FIRST_NAME\",\"ID\",\"AGE\",\"SIGNED\"" + System.lineSeparator()
        + "Georgiev,Georgi,1,27,2017-09-20 14:00:12" + System.lineSeparator()
        + "Toshkova,Sara,2,21,2019-09-21 14:00:12" + System.lineSeparator()
        + "Ivanov,Toshko,3,25,2018-09-23 14:00:12" + System.lineSeparator();
  }

  private String getChangedContentWithHeaders() {
    return "\"LAST_NAME\",\"FIRST_NAME\",\"ID\",\"AGE\",\"SIGNED\"" + System.lineSeparator()
        + "Changed,Changed,1,25,2017-09-20 14:00:12" + System.lineSeparator()
        + "Changed,Changed,2,25,2019-09-21 14:00:12" + System.lineSeparator()
        + "Changed,Changed,3,25,2018-09-23 14:00:12" + System.lineSeparator();
  }

  private String getQuoteContent() {
    return "1;'Georgi, \\'Junior\\'';Georgiev;27;2017-09-20 14:00:12" + System.lineSeparator();
  }

  private String getAddedContent() {
    return "1,Georgi,Georgiev,27,2017-09-20 14:00:12" + System.lineSeparator()
        + "2,Sara,Toshkova,21,2019-09-21 14:00:12" + System.lineSeparator()
        + "3,Toshko,Ivanov,25,2018-09-23 14:00:12" + System.lineSeparator()
        + "4,Pesho,Peshev,25,2014-09-23 13:10:12" + System.lineSeparator();
  }

  private String getUpdatedContent() {
    return "1,Georgi,Georgiev,27,2017-09-20 14:00:12" + System.lineSeparator()
        + "2,Changed,Changed,21,2018-09-21 14:00:12" + System.lineSeparator()
        + "3,Toshko,Ivanov,25,2018-09-23 14:00:12" + System.lineSeparator();
  }

  private String getRemovedContent() {
    return "2,Sara,Toshkova,21,2019-09-21 14:00:12" + System.lineSeparator()
        + "3,Toshko,Ivanov,25,2018-09-23 14:00:12" + System.lineSeparator();
  }

  private void writeToFile(String path, String content) {
    File fileToBeModified = getFileFromResource(path);
    try {
      assert fileToBeModified != null;
      try (FileWriter fileWriter = new FileWriter(fileToBeModified)) {
        fileWriter.write(content);
      }
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  private File getFileFromResource(String fileName) {

    ClassLoader classLoader = getClass().getClassLoader();
    URL resource = classLoader.getResource(fileName);
    if (resource == null) {
      throw new IllegalArgumentException("file not found! " + fileName);
    } else {

      // failed if files have whitespaces or special characters
      //return new File(resource.getFile());

      try {
        return new File(resource.toURI());
      } catch (URISyntaxException e) {
        logger.error(e.getMessage(), e);
      }

      return null;
    }

  }

  @Test
  public void testParseHdbtiToJSONSuccessfully()
      throws IOException, ArtifactParserException, HDBTISyntaxErrorException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(HDBTIProcessorTest.class.getResourceAsStream("/randomOrder.hdbti"), StandardCharsets.UTF_8);

    List<HDBTIImportConfigModel> model = processor.parseHdbtiToJSON("randomOrder.hdbti", hdbtiSample.getBytes(StandardCharsets.UTF_8));
    assertEquals(model.size(), 2);

    HDBTIImportConfigModel import1 = model.get(0);
    assertEquals(import1.getDelimEnclosing(), "\"");
    assertEquals(import1.getSchemaName(), "mySchema");
    assertTrue(import1.getDistinguishEmptyFromNull());
    assertFalse(import1.getHeader());
    assertEquals(import1.getTableName(), "myTable");
    assertFalse(import1.getUseHeaderNames());
    assertEquals(import1.getDelimField(), ";");
    assertEquals(import1.getFileName(), "/sap/ti2/demo/myData.csv");
    assertEquals(import1.getKeys().size(), 1);
    assertEquals(import1.getKeys().get(0).getColumn(), "GROUP_TYPE");
    assertEquals(import1.getKeys().get(0).getValues().size(), 3);
    assertEquals(import1.getKeys().get(0).getValues().get(0), "BW_CUBE");
    assertEquals(import1.getKeys().get(0).getValues().get(1), "BW_DSO");
    assertEquals(import1.getKeys().get(0).getValues().get(2), "BW_PSA");

    HDBTIImportConfigModel import2 = model.get(1);
    assertEquals(import2.getDelimEnclosing(), "#");
    assertEquals(import2.getSchemaName(), "mySchema2");
    assertFalse(import2.getDistinguishEmptyFromNull());
    assertFalse(import2.getHeader());
    assertEquals(import2.getTableName(), "myTable2");
    assertFalse(import2.getUseHeaderNames());
    assertEquals(import2.getDelimField(), ";");
    assertEquals(import2.getFileName(), "/sap/ti2/demo/myData2.csv");
    assertEquals(import2.getKeys().size(), 2);
    assertEquals(import2.getKeys().get(0).getColumn(), "MAIN_TYPE");
    assertEquals(import2.getKeys().get(0).getValues().size(), 1);
    assertEquals(import2.getKeys().get(0).getValues().get(0), "BW_CO");
    assertEquals(import2.getKeys().get(1).getColumn(), "SECOND_TYPE");
    assertEquals(import2.getKeys().get(1).getValues().size(), 1);
    assertEquals(import2.getKeys().get(1).getValues().get(0), "BW_PO");
  }

  @Test
  public void testParseHdbtiToJSONSuccessfullyWhenTablePropHasDoubleColon()
      throws IOException, ArtifactParserException, HDBTISyntaxErrorException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(HDBTIProcessorTest.class.getResourceAsStream("/doubleColonTableProp.hdbti"), StandardCharsets.UTF_8);

    List<HDBTIImportConfigModel> model = processor.parseHdbtiToJSON("doubleColonTableProp.hdbti",
        hdbtiSample.getBytes(StandardCharsets.UTF_8));
    assertEquals(model.size(), 1);

    HDBTIImportConfigModel import1 = model.get(0);
    assertEquals(import1.getDelimEnclosing(), "\"");
    assertEquals(import1.getSchemaName(), "mySchema");
    assertTrue(import1.getDistinguishEmptyFromNull());
    assertFalse(import1.getHeader());
    assertEquals(import1.getTableName(), "sap.demo::mytable");
    assertFalse(import1.getUseHeaderNames());
    assertEquals(import1.getDelimField(), ";");
    assertEquals(import1.getFileName(), "/sap/ti2/demo/myData.csv");
    assertEquals(import1.getKeys().size(), 1);
    assertEquals(import1.getKeys().get(0).getColumn(), "GROUP_TYPE");
    assertEquals(import1.getKeys().get(0).getValues().size(), 3);
    assertEquals(import1.getKeys().get(0).getValues().get(0), "BW_CUBE");
    assertEquals(import1.getKeys().get(0).getValues().get(1), "BW_DSO");
    assertEquals(import1.getKeys().get(0).getValues().get(2), "BW_PSA");
  }

  @Test
  public void testParseHdbtiToJSONSuccessfullyWhenMissingSchemaName()
      throws IOException, ArtifactParserException, HDBTISyntaxErrorException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(HDBTIProcessorTest.class.getResourceAsStream("/missingSchemaName.hdbti"), StandardCharsets.UTF_8);

    List<HDBTIImportConfigModel> model = processor.parseHdbtiToJSON("missingSchemaName.hdbti",
        hdbtiSample.getBytes(StandardCharsets.UTF_8));
    assertEquals(model.size(), 1);

    HDBTIImportConfigModel import1 = model.get(0);
    assertEquals(import1.getDelimEnclosing(), "\"");
    assertTrue(import1.getDistinguishEmptyFromNull());
    assertFalse(import1.getHeader());
    assertEquals(import1.getTableName(), "sap.demo::mytable");
    assertFalse(import1.getUseHeaderNames());
    assertEquals(import1.getDelimField(), ";");
    assertEquals(import1.getFileName(), "/sap/ti2/demo/myData.csv");
    assertEquals(import1.getKeys().size(), 1);
    assertEquals(import1.getKeys().get(0).getColumn(), "GROUP_TYPE");
    assertEquals(import1.getKeys().get(0).getValues().size(), 3);
    assertEquals(import1.getKeys().get(0).getValues().get(0), "BW_CUBE");
    assertEquals(import1.getKeys().get(0).getValues().get(1), "BW_DSO");
    assertEquals(import1.getKeys().get(0).getValues().get(2), "BW_PSA");
  }

  @Test(expected = TablePropertySyntaxException.class)
  public void testParseHdbtiToJSONFailWhenTablePropHasSingleColon()
      throws IOException, ArtifactParserException, HDBTISyntaxErrorException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(HDBTIProcessorTest.class.getResourceAsStream("/singleColonTableProp.hdbti"), StandardCharsets.UTF_8);

    processor.parseHdbtiToJSON("singleColonTableProp.hdbti", hdbtiSample.getBytes(StandardCharsets.UTF_8));
  }

  @Test
  public void testParseJSONtoHdbtiSuccessfully()
      throws IOException, ArtifactParserException, HDBTISyntaxErrorException {
    String hdbtiSample = org.apache.commons.io.IOUtils
        .toString(HDBTIProcessorTest.class.getResourceAsStream("/randomOrder.hdbti"), StandardCharsets.UTF_8);

    List<HDBTIImportConfigModel> model = processor.parseHdbtiToJSON("randomOrder.hdbti", hdbtiSample.getBytes(StandardCharsets.UTF_8));
    String actualValue = processor.parseJSONtoHdbti((ArrayList<HDBTIImportConfigModel>) model);
    String expectedValue = "import = [\n" +
        "{\n" +
        "\tdelimEnclosing=\"\\\"\";\n" +
        "\tschema = \"mySchema\";\n" +
        "\tdistinguishEmptyFromNull = true;\n" +
        "\theader = false;\n" +
        "\ttable = \"myTable\";\n" +
        "\tuseHeaderNames = false;\n" +
        "\tdelimField = \";\";\n" +
        "\tkeys = [\"GROUP_TYPE\":\"BW_CUBE\",\"GROUP_TYPE\":\"BW_DSO\",\"GROUP_TYPE\":\"BW_PSA\"];\n" +
        "\tfile = \"sap.ti2.demo:myData.csv\";\n" +
        "}, \n" +
        "{\n" +
        "\tdelimEnclosing=\"#\";\n" +
        "\tschema = \"mySchema2\";\n" +
        "\tdistinguishEmptyFromNull = false;\n" +
        "\theader = false;\n" +
        "\ttable = \"myTable2\";\n" +
        "\tuseHeaderNames = false;\n" +
        "\tdelimField = \";\";\n" +
        "\tkeys = [\"MAIN_TYPE\":\"BW_CO\", \"SECOND_TYPE\":\"BW_PO\"];\n" +
        "\tfile = \"sap.ti2.demo:myData2.csv\";\n" +
        "}];";

    assertEquals(expectedValue, actualValue);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseJSONtoHdbtiFailWithIncorrectFilePath() {
    HDBTIImportConfigModel model = new HDBTIImportConfigModel();
    model.setDelimEnclosing("'");
    model.setSchemaName("schema");
    model.setHeader(true);
    model.setTableName("table");
    model.setFileName("workspace:csv=file-Name_cAN|be+running (งツ)ว");

    List<HDBTIImportConfigModel> list = new ArrayList<>(Arrays.asList(model));
    processor.parseJSONtoHdbti((ArrayList<HDBTIImportConfigModel>) list);
  }

  @Test
  public void testParseJSONtoHdbtiSuccessfullyWithFileNameWithoutPath() {
    HDBTIImportConfigModel model = new HDBTIImportConfigModel();
    model.setDelimEnclosing("'");
    model.setSchemaName("schema");
    model.setHeader(true);
    model.setTableName("table");
    model.setFileName("myData2.csv");

    String expectedValue = "import = [\n" +
        "{\n" +
        "\tdelimEnclosing=\"'\";\n" +
        "\tschema = \"schema\";\n" +
        "\theader = true;\n" +
        "\ttable = \"table\";\n" +
        "\tfile = \"myData2.csv\";\n" +
        "}];";

    String actualResult = processor.parseJSONtoHdbti(new ArrayList<>(Arrays.asList(model)));
    assertEquals(expectedValue, actualResult);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseJSONtoHdbtiFailWithIncorrectSchemaName() {
    HDBTIImportConfigModel model = new HDBTIImportConfigModel();
    model.setDelimEnclosing("'");
    model.setSchemaName("schema-Name_cAN|be\\whatever ¯\\_(ツ)_/¯");
    model.setHeader(true);
    model.setTableName("table");
    model.setFileName("myData2.csv");

    processor.parseJSONtoHdbti(new ArrayList<>(Arrays.asList(model)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseJSONtoHdbtiFailWithIncorrectTableName() {
    HDBTIImportConfigModel model = new HDBTIImportConfigModel();
    model.setDelimEnclosing("'");
    model.setSchemaName("schema");
    model.setHeader(true);
    model.setTableName("table-Name_cAN|be@bear ʕ•ᴥ•ʔ");
    model.setFileName("myData2.csv");

    processor.parseJSONtoHdbti(new ArrayList<>(Arrays.asList(model)));
  }

  @Test
  public void testParseJSONtoHdbtiSuccessfullyWithDoubleColonTableName() {
    HDBTIImportConfigModel model = new HDBTIImportConfigModel();

    model.setDelimEnclosing("'");
    model.setSchemaName("schema");
    model.setHeader(true);
    model.setTableName("kronos.test::c_users");
    model.setFileName("myData2.csv");

    String actualValue = processor.parseJSONtoHdbti(new ArrayList<>(Arrays.asList(model)));

    String expectedValue = "import = [\n" +
        "{\n" +
        "\tdelimEnclosing=\"'\";\n" +
        "\tschema = \"schema\";\n" +
        "\theader = true;\n" +
        "\ttable = \"kronos.test::c_users\";\n" +
        "\tfile = \"myData2.csv\";\n" +
        "}];";

    assertEquals(expectedValue, actualValue);
  }

  @Test
  public void testParseJSONtoHdbtiSuccessfullyWithMissingSchemaName() {
    HDBTIImportConfigModel model = new HDBTIImportConfigModel();

    model.setDelimEnclosing("'");
    model.setHeader(true);
    model.setTableName("kronos.test::c_users");
    model.setFileName("myData2.csv");

    String actualValue = processor.parseJSONtoHdbti(new ArrayList<>(Arrays.asList(model)));

    String expectedValue = "import = [\n" +
        "{\n" +
        "\tdelimEnclosing=\"'\";\n" +
        "\theader = true;\n" +
        "\ttable = \"kronos.test::c_users\";\n" +
        "\tfile = \"myData2.csv\";\n" +
        "}];";

    assertEquals(expectedValue, actualValue);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseJSONtoHdbtiFailWithSingleColonTableName() {
    HDBTIImportConfigModel model = new HDBTIImportConfigModel();

    model.setDelimEnclosing("'");
    model.setSchemaName("schema");
    model.setHeader(true);
    model.setTableName("kronos:i_users");
    model.setFileName("myData2.csv");

    processor.parseJSONtoHdbti(new ArrayList<>(Arrays.asList(model)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseJSONtoHdbtiFailWithMissingSchemaName() {
    HDBTIImportConfigModel model = new HDBTIImportConfigModel();

    model.setDelimEnclosing("'");
    model.setHeader(true);
    model.setTableName("i_users");
    model.setFileName("myData2.csv");

    processor.parseJSONtoHdbti(new ArrayList<>(Arrays.asList(model)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testParseJSONtoHdbtiFailWithIncorrectKeyColumn() {
    HDBTIImportConfigModel model = new HDBTIImportConfigModel();
    model.setDelimEnclosing("'");
    model.setSchemaName("schema");
    model.setHeader(true);
    model.setTableName("table");
    model.setFileName("myData2.csv");
    HDBTIImportConfigModel.Pair pair = new HDBTIImportConfigModel.Pair("%something&\":\"ƪ(ړײ)ƪ\u200B\u200B",
        new ArrayList<>((Collections.singletonList("BW_CUBE"))));
    model.getKeys().add(pair);

    processor.parseJSONtoHdbti(new ArrayList<>(Arrays.asList(model)));
  }
}
