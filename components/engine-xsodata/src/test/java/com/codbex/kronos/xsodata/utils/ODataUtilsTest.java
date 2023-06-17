/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.xsodata.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.components.data.structures.domain.Table;
import org.eclipse.dirigible.components.data.structures.domain.TableColumn;
import org.eclipse.dirigible.components.data.structures.domain.TableConstraintForeignKey;
import org.eclipse.dirigible.components.odata.api.ODataEntity;
import org.eclipse.dirigible.components.odata.api.ODataHandlerMethods;
import org.eclipse.dirigible.components.odata.api.ODataHandlerTypes;
import org.eclipse.dirigible.components.odata.domain.OData;
import org.eclipse.dirigible.database.sql.ISqlKeywords;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.codbex.kronos.engine.xsodata.domain.XSOData;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.xsodata.model.XSODataEntity;
import com.codbex.kronos.parser.xsodata.model.XSODataEventType;
import com.codbex.kronos.parser.xsodata.model.XSODataHandlerMethod;
import com.codbex.kronos.utils.CommonsDBUtils;
import com.codbex.kronos.xsodata.ds.model.DBArtifactModel;
import com.codbex.kronos.xsodata.ds.service.TableMetadataProvider;
import com.codbex.kronos.xsodata.ds.service.XSODataArtefactParser;

/**
 * The Class ODataUtilsTest.
 */
@ExtendWith(MockitoExtension.class)
public class ODataUtilsTest {

  /** The mock connection. */
  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private Connection mockConnection;

  /** The mock data source. */
  @Mock
  private DataSource mockDataSource;

  /** The mock result set. */
  @Mock
  private ResultSet mockResultSet;

  /** The mock prepared statement. */
  @Mock
  private PreparedStatement mockPreparedStatement;

  /** The metadata provider. */
  @Mock
  private TableMetadataProvider metadataProvider;

  /** The o data util. */
  @InjectMocks
  private ODataUtils oDataUtil;

  /** The parser. */
  @Spy
  @InjectMocks
  private final XSODataArtefactParser parser = new XSODataArtefactParser();

  /**
	 * Open mocks.
	 */
  @BeforeEach
  public void openMocks() {
    MockitoAnnotations.openMocks(this);
  }

  /**
	 * Test convert multiplicity one to many.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void testConvertMultiplicityOneToMany() throws Exception {
    String content = IOUtils.toString(this.getClass().getResourceAsStream("/entity_multiplicity_one_to_many.xsodata"),
        StandardCharsets.UTF_8);
    XSOData oDataModel = parser.parseXSOData("np/entity_multiplicity_one_to_many.xsodata", content);

    OData oDataDefinition = oDataUtil.convertODataModelToODataDefinition(oDataModel);

    assertEquals("1", oDataDefinition.getAssociations().get(0).getFrom().getMultiplicity());
    assertEquals("*", oDataDefinition.getAssociations().get(0).getTo().getMultiplicity());
  }

  /**
	 * Test convert without set of prop and limited exposed navigations.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void testConvertWithoutSetOfPropAndLimitedExposedNavigations() throws Exception {
    String content = IOUtils.toString(this.getClass().getResourceAsStream("/entity_without_set_of_prop.xsodata"), StandardCharsets.UTF_8);
    XSOData oDataModel = parser.parseXSOData("np/entity_without_set_of_prop.xsodata", content);

    Table model = new Table("kneo.test.helloodata.CompositeKey::employee");
    TableColumn column1 = new TableColumn("COMPANY_ID", "Edm.Int32", "0", false, true, model);
    TableColumn column2 = new TableColumn("EMPLOYEE_NUMBER", "Edm.Int32", "0", false, true, model);
    TableColumn column9 = new TableColumn("ORDER_ID", "Edm.Int32", "0", false, true, model);
    column9.setNullable(true);
    
    model.setKind(ISqlKeywords.METADATA_TABLE);
    when(metadataProvider.getPersistenceTableModel("kneo.test.helloodata.CompositeKey::employee")).thenReturn(model);

    model = new Table("kneo.test.helloodata.CompositeKey::phones");
    TableColumn column3 = new TableColumn("NUMBER", "Edm.Int32", "0", false, true, model);
    TableColumn column4 = new TableColumn("FK_COMPANY_ID", "Edm.Int32", "0", true, false, model);
    TableColumn column5 = new TableColumn("FK_EMPLOYEE_NUMBER", "Edm.Int32", "0", true, false, model);
    TableColumn column6 = new TableColumn("FK_ADDRESS_ID", "Edm.Int32", "0", true, false, model);
    TableConstraintForeignKey rel = new TableConstraintForeignKey("kneo.test.helloodata.CompositeKey::phones", null,
        "kneo.test.helloodata.CompositeKey::employee", "FK_COMPANY_ID", model.getConstraints());
    TableConstraintForeignKey rel2 = new TableConstraintForeignKey("kneo.test.helloodata.CompositeKey::phones", null,
        "kneo.test.helloodata.CompositeKey::employee", "FK_EMPLOYEE_NUMBER", model.getConstraints());
    TableConstraintForeignKey rel3 = new TableConstraintForeignKey("kneo.test.helloodata.CompositeKey::phones", null,
        "kneo.test.helloodata.CompositeKey::address", "FK_ADDRESS_ID", model.getConstraints());
   
    model.setKind(ISqlKeywords.METADATA_TABLE);
    when(metadataProvider.getPersistenceTableModel("kneo.test.helloodata.CompositeKey::phones")).thenReturn(model);

    OData oDataDefinition = oDataUtil.convertODataModelToODataDefinition(oDataModel);

    assertEquals(2, oDataDefinition.getEntities().size());
    assertEquals(1, oDataDefinition.getAssociations().size());
    assertEquals("np", oDataDefinition.getNamespace());
    assertNull(oDataDefinition.getCreatedAt());
    assertEquals("np/entity_without_set_of_prop.xsodata", oDataDefinition.getLocation());

    ODataEntity employeeEntity = oDataDefinition.getEntities().get(0);
    assertEquals("Employees", employeeEntity.getName());
    assertEquals("Employees", employeeEntity.getAlias());
    assertEquals("kneo.test.helloodata.CompositeKey::employee", employeeEntity.getTable());
    assertEquals(2, employeeEntity.getProperties().size());
    assertEquals("COMPANY_ID", employeeEntity.getProperties().get(0).getName());
    assertEquals("COMPANY_ID", employeeEntity.getProperties().get(0).getColumn());
    assertFalse(employeeEntity.getProperties().get(0).isNullable());
    assertEquals("Edm.Int32", employeeEntity.getProperties().get(0).getType());
    assertEquals("EMPLOYEE_NUMBER", employeeEntity.getProperties().get(1).getName());
    assertEquals("EMPLOYEE_NUMBER", employeeEntity.getProperties().get(1).getColumn());
    assertFalse(employeeEntity.getProperties().get(1).isNullable());
    assertEquals("Edm.Int32", employeeEntity.getProperties().get(1).getType());
    assertEquals(0, employeeEntity.getHandlers().size());
    assertEquals(1, employeeEntity.getNavigations().size());
    assertEquals("HisPhones", employeeEntity.getNavigations().get(0).getName());
    assertEquals("Employees_Phones", employeeEntity.getNavigations().get(0).getAssociation());

    ODataEntity phoneEntity = oDataDefinition.getEntities().get(1);
    assertEquals("Phones", phoneEntity.getName());
    assertEquals("Phones", phoneEntity.getAlias());
    assertEquals("kneo.test.helloodata.CompositeKey::phones", phoneEntity.getTable());
    assertEquals(0, phoneEntity.getProperties().size());
    assertEquals(0, phoneEntity.getHandlers().size());
    assertEquals(0, phoneEntity.getNavigations().size());

    assertEquals("Employees_Phones", oDataDefinition.getAssociations().get(0).getName());
    assertEquals("Employees", oDataDefinition.getAssociations().get(0).getFrom().getEntity());
    assertNull(oDataDefinition.getAssociations().get(0).getFrom().getColumn());
    assertEquals("1", oDataDefinition.getAssociations().get(0).getFrom().getMultiplicity());
    assertEquals(2, oDataDefinition.getAssociations().get(0).getFrom().getProperties().size());
    assertEquals("COMPANY_ID", oDataDefinition.getAssociations().get(0).getFrom().getProperties().get(0));
    assertEquals("EMPLOYEE_NUMBER", oDataDefinition.getAssociations().get(0).getFrom().getProperties().get(1));

    assertEquals("Phones", oDataDefinition.getAssociations().get(0).getTo().getEntity());
    assertNull(oDataDefinition.getAssociations().get(0).getTo().getColumn());
    assertEquals("*", oDataDefinition.getAssociations().get(0).getTo().getMultiplicity());
    assertEquals(2, oDataDefinition.getAssociations().get(0).getTo().getProperties().size());
    assertEquals("FK_COMPANY_ID", oDataDefinition.getAssociations().get(0).getTo().getProperties().get(0));
    assertEquals("FK_EMPLOYEE_NUMBER", oDataDefinition.getAssociations().get(0).getTo().getProperties().get(1));
  }

  /**
	 * Test convert with set of prop and limited exposed navigations.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void testConvertWithSetOfPropAndLimitedExposedNavigations() throws Exception {
    String content = IOUtils.toString(this.getClass().getResourceAsStream("/entity_with_set_of_prop.xsodata"), StandardCharsets.UTF_8);
    XSOData oDataModel = parser.parseXSOData("np/entity_with_set_of_prop.xsodata", content);

    Table model = new Table("kneo.test.helloodata.CompositeKey::employee");
    TableColumn column1 = new TableColumn("COMPANY_ID", "Edm.Int32", "0", false, true, model);
    TableColumn column2 = new TableColumn("EMPLOYEE_NUMBER", "Edm.Int32", "0", false, true, model);
    TableColumn column9 = new TableColumn("ORDER_ID", "Edm.Int32", "0", true, false, model);
    column9.setNullable(true);
    TableColumn column10 = new TableColumn("ORDER_ID_2", "Edm.Int32", "0", true, false, model);
    column10.setNullable(true);
    
    model.setKind(ISqlKeywords.METADATA_TABLE);
    when(metadataProvider.getPersistenceTableModel("kneo.test.helloodata.CompositeKey::employee")).thenReturn(model);

    model = new Table("kneo.test.helloodata.CompositeKey::phones");
    TableColumn column3 = new TableColumn("NUMBER", "Edm.Int32", "0", false, true, model);
    TableColumn column4 = new TableColumn("FK_COMPANY_ID", "Edm.Int32", "0", true, false, model);
    TableColumn column5 = new TableColumn("FK_EMPLOYEE_NUMBER", "Edm.Int32", "0", true, false, model);
    TableColumn column6 = new TableColumn("FK_ADDRESS_ID", "Edm.Int32", "0", true, false, model);
    TableConstraintForeignKey rel = new TableConstraintForeignKey("kneo.test.helloodata.CompositeKey::phones", null,
        "kneo.test.helloodata.CompositeKey::employee", "FK_COMPANY_ID", model.getConstraints());
    TableConstraintForeignKey rel2 = new TableConstraintForeignKey("kneo.test.helloodata.CompositeKey::phones", null,
        "kneo.test.helloodata.CompositeKey::employee", "FK_EMPLOYEE_NUMBER", model.getConstraints());
    TableConstraintForeignKey rel3 = new TableConstraintForeignKey("kneo.test.helloodata.CompositeKey::phones", null,
        "kneo.test.helloodata.CompositeKey::address", "FK_ADDRESS_ID", model.getConstraints());
   
    model.setKind(ISqlKeywords.METADATA_TABLE);
    when(metadataProvider.getPersistenceTableModel("kneo.test.helloodata.CompositeKey::phones")).thenReturn(model);

    OData oDataDefinition = oDataUtil.convertODataModelToODataDefinition(oDataModel);
    assertEquals(2, oDataDefinition.getEntities().size());
    assertEquals(1, oDataDefinition.getAssociations().size());
    assertEquals("np", oDataDefinition.getNamespace());
    assertNull(oDataDefinition.getCreatedAt());
    assertEquals("np/entity_with_set_of_prop.xsodata", oDataDefinition.getLocation());

    ODataEntity employeeEntity = oDataDefinition.getEntities().get(0);
    assertEquals("Employees", employeeEntity.getName());
    assertEquals("Employees", employeeEntity.getAlias());
    assertEquals("kneo.test.helloodata.CompositeKey::employee", employeeEntity.getTable());

    assertEquals(3, employeeEntity.getProperties().size());
    assertEquals("COMPANY_ID", employeeEntity.getProperties().get(0).getName());
    assertEquals("COMPANY_ID", employeeEntity.getProperties().get(0).getColumn());
    assertFalse(employeeEntity.getProperties().get(0).isNullable());
    assertEquals("Edm.Int32", employeeEntity.getProperties().get(0).getType());
    assertEquals("EMPLOYEE_NUMBER", employeeEntity.getProperties().get(1).getName());
    assertEquals("EMPLOYEE_NUMBER", employeeEntity.getProperties().get(1).getColumn());
    assertFalse(employeeEntity.getProperties().get(1).isNullable());
    assertEquals("Edm.Int32", employeeEntity.getProperties().get(1).getType());
    assertEquals("ORDER_ID", employeeEntity.getProperties().get(2).getName());
    assertEquals("ORDER_ID", employeeEntity.getProperties().get(2).getColumn());
    assertTrue(employeeEntity.getProperties().get(2).isNullable());
    assertEquals("Edm.Int32", employeeEntity.getProperties().get(2).getType());

    assertEquals(0, employeeEntity.getHandlers().size());
    assertEquals(1, employeeEntity.getNavigations().size());
    assertEquals("HisPhones", employeeEntity.getNavigations().get(0).getName());
    assertEquals("Employees_Phones", employeeEntity.getNavigations().get(0).getAssociation());

    ODataEntity phoneEntity = oDataDefinition.getEntities().get(1);
    assertEquals("Phones", phoneEntity.getName());
    assertEquals("Phones", phoneEntity.getAlias());
    assertEquals("kneo.test.helloodata.CompositeKey::phones", phoneEntity.getTable());
    assertEquals(0, phoneEntity.getProperties().size());
    assertEquals(0, phoneEntity.getHandlers().size());
    assertEquals(0, phoneEntity.getNavigations().size());

    assertEquals("Employees_Phones", oDataDefinition.getAssociations().get(0).getName());
    assertEquals("Employees", oDataDefinition.getAssociations().get(0).getFrom().getEntity());
    assertNull(oDataDefinition.getAssociations().get(0).getFrom().getColumn());
    assertEquals("1", oDataDefinition.getAssociations().get(0).getFrom().getMultiplicity());
    assertEquals(2, oDataDefinition.getAssociations().get(0).getFrom().getProperties().size());
    assertEquals("COMPANY_ID", oDataDefinition.getAssociations().get(0).getFrom().getProperties().get(0));
    assertEquals("EMPLOYEE_NUMBER", oDataDefinition.getAssociations().get(0).getFrom().getProperties().get(1));

    assertEquals("Phones", oDataDefinition.getAssociations().get(0).getTo().getEntity());
    assertNull(oDataDefinition.getAssociations().get(0).getTo().getColumn());
    assertEquals("*", oDataDefinition.getAssociations().get(0).getTo().getMultiplicity());
    assertEquals(2, oDataDefinition.getAssociations().get(0).getTo().getProperties().size());
    assertEquals("FK_COMPANY_ID", oDataDefinition.getAssociations().get(0).getTo().getProperties().get(0));
    assertEquals("FK_EMPLOYEE_NUMBER", oDataDefinition.getAssociations().get(0).getTo().getProperties().get(1));
  }

  /**
	 * Test convert of events.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void testConvertOfEvents() throws Exception {
    String content = IOUtils.toString(this.getClass().getResourceAsStream("/entity_with_events.xsodata"), StandardCharsets.UTF_8);
    XSOData oDataModel = parser.parseXSOData("np/entity_with_events.xsodata", content);

    mockTableMetadataInvocations("sample.odata::table1");
    mockTableMetadataInvocations("sample.odata::table2");
    mockTableMetadataInvocations("sample.odata::table3");
    mockTableMetadataInvocations("sample.odata::table4");

    OData oDataDefinition = oDataUtil.convertODataModelToODataDefinition(oDataModel);
    assertEquals(4, oDataDefinition.getEntities().size());

    ODataEntity entity1 = oDataDefinition.getEntities().get(0);
    assertEquals(3, entity1.getHandlers().size());
    assertEquals(ODataHandlerMethods.update.name(), entity1.getHandlers().get(0).getMethod());
    assertEquals(ODataHandlerTypes.before.name(), entity1.getHandlers().get(0).getType());
    assertEquals("sample.odata::beforeMethod", entity1.getHandlers().get(0).getHandler());
    assertEquals(ODataHandlerMethods.delete.name(), entity1.getHandlers().get(1).getMethod());
    assertEquals(ODataHandlerTypes.after.name(), entity1.getHandlers().get(1).getType());
    assertEquals("sample.odata::afterMethod", entity1.getHandlers().get(1).getHandler());
    assertEquals(ODataHandlerMethods.create.name(), entity1.getHandlers().get(2).getMethod());
    assertEquals(ODataHandlerTypes.forbid.name(), entity1.getHandlers().get(2).getType());
    assertNull(entity1.getHandlers().get(2).getHandler());
    assertEquals("false", entity1.getAnnotationsEntitySet().get(XSODataHandlerMethod.CREATE.getOdataSAPAnnotation()));
    assertNull(entity1.getAnnotationsEntitySet().get(XSODataHandlerMethod.UPDATE.getOdataSAPAnnotation()));
    assertNull(entity1.getAnnotationsEntitySet().get(XSODataHandlerMethod.DELETE.getOdataSAPAnnotation()));

    ODataEntity entity2 = oDataDefinition.getEntities().get(1);
    assertEquals(4, entity2.getHandlers().size());
    assertEquals(ODataHandlerMethods.create.name(), entity2.getHandlers().get(0).getMethod());
    assertEquals(ODataHandlerTypes.after.name(), entity2.getHandlers().get(0).getType());
    assertEquals("sample.odata::afterMethod", entity2.getHandlers().get(0).getHandler());
    assertEquals(ODataHandlerMethods.create.name(), entity2.getHandlers().get(1).getMethod());
    assertEquals(ODataHandlerTypes.on.name(), entity2.getHandlers().get(1).getType());
    assertEquals("sample.odata::createMethod", entity2.getHandlers().get(1).getHandler());
    assertEquals(ODataHandlerMethods.update.name(), entity2.getHandlers().get(2).getMethod());
    assertEquals(ODataHandlerTypes.on.name(), entity2.getHandlers().get(2).getType());
    assertEquals("sample.odata::updateMethod", entity2.getHandlers().get(2).getHandler());
    assertEquals(ODataHandlerMethods.delete.name(), entity2.getHandlers().get(3).getMethod());
    assertEquals(ODataHandlerTypes.on.name(), entity2.getHandlers().get(3).getType());
    assertEquals("sample.odata::deleteMethod", entity2.getHandlers().get(3).getHandler());
    assertNull(entity2.getAnnotationsEntitySet().get(XSODataHandlerMethod.CREATE.getOdataSAPAnnotation()));
    assertNull(entity2.getAnnotationsEntitySet().get(XSODataHandlerMethod.UPDATE.getOdataSAPAnnotation()));
    assertNull(entity2.getAnnotationsEntitySet().get(XSODataHandlerMethod.DELETE.getOdataSAPAnnotation()));

    ODataEntity entity3 = oDataDefinition.getEntities().get(2);
    assertEquals(2, entity3.getHandlers().size());
    assertEquals(ODataHandlerMethods.create.name(), entity3.getHandlers().get(0).getMethod());
    assertEquals(ODataHandlerTypes.on.name(), entity3.getHandlers().get(0).getType());
    assertEquals("sample.odata::createMethod", entity3.getHandlers().get(0).getHandler());
    assertEquals(ODataHandlerMethods.delete.name(), entity3.getHandlers().get(1).getMethod());
    assertEquals(ODataHandlerTypes.forbid.name(), entity3.getHandlers().get(1).getType());
    assertNull(entity3.getHandlers().get(1).getHandler());
    assertNull(entity3.getAnnotationsEntitySet().get(XSODataHandlerMethod.CREATE.getOdataSAPAnnotation()));
    assertNull(entity3.getAnnotationsEntitySet().get(XSODataHandlerMethod.UPDATE.getOdataSAPAnnotation()));
    assertEquals("false", entity3.getAnnotationsEntitySet().get(XSODataHandlerMethod.DELETE.getOdataSAPAnnotation()));

    ODataEntity entity4 = oDataDefinition.getEntities().get(3);
    assertEquals(3, entity4.getHandlers().size());
    assertEquals(ODataHandlerMethods.create.name(), entity4.getHandlers().get(0).getMethod());
    assertEquals(ODataHandlerTypes.forbid.name(), entity4.getHandlers().get(0).getType());
    assertNull(entity4.getHandlers().get(0).getHandler());
    assertEquals(ODataHandlerMethods.update.name(), entity4.getHandlers().get(1).getMethod());
    assertEquals(ODataHandlerTypes.forbid.name(), entity4.getHandlers().get(1).getType());
    assertNull(entity4.getHandlers().get(1).getHandler());
    assertEquals(ODataHandlerMethods.delete.name(), entity4.getHandlers().get(2).getMethod());
    assertEquals(ODataHandlerTypes.forbid.name(), entity4.getHandlers().get(2).getType());
    assertNull(entity4.getHandlers().get(2).getHandler());
    assertEquals("false", entity4.getAnnotationsEntitySet().get(XSODataHandlerMethod.CREATE.getOdataSAPAnnotation()));
    assertEquals("false", entity4.getAnnotationsEntitySet().get(XSODataHandlerMethod.UPDATE.getOdataSAPAnnotation()));
    assertEquals("false", entity4.getAnnotationsEntitySet().get(XSODataHandlerMethod.DELETE.getOdataSAPAnnotation()));
  }

  /**
	 * Mock table metadata invocations.
	 *
	 * @param tableName the table name
	 * @throws SQLException the SQL exception
	 */
  private void mockTableMetadataInvocations(String tableName) throws SQLException {
	  Table model = new Table(tableName);
    when(metadataProvider.getPersistenceTableModel(tableName)).thenReturn(model);
  }

//  /**
//	 * Test validate edm multiplicity failed.
//	 */
//  @Test(expected = XSOData2TransformerException.class)
//  public void testValidateEdmMultiplicityFailed() {
//    oDataUtil.validateEdmMultiplicity("1..*", "ass_name");
//  }

  /**
	 * Test validate edm multiplicity successfully.
	 */
  @Test
  public void testValidateEdmMultiplicitySuccessfully() {
    oDataUtil.validateEdmMultiplicity("0..1", "ass_name");
    oDataUtil.validateEdmMultiplicity("*", "ass_name");
    oDataUtil.validateEdmMultiplicity("1", "ass_name");
  }

  /**
	 * Test validate handler type failed.
	 */
  @Test
  public void testValidateHandlerTypeFailed() {
    assertFalse(oDataUtil.validateHandlerType(XSODataEventType.POSTCOMMIT));
    assertFalse(oDataUtil.validateHandlerType(XSODataEventType.PRECOMMIT));
  }

  /**
	 * Test validate handler type successfully.
	 */
  @Test
  public void testValidateHandlerTypeSuccessfully() {
    assertTrue(oDataUtil.validateHandlerType(XSODataEventType.AFTER));
    assertTrue(oDataUtil.validateHandlerType(XSODataEventType.BEFORE));
  }

  /**
	 * Test calc view.
	 *
	 * @throws Exception the exception
	 */
  //@Test
  public void testCalcView() throws Exception {
    String content = IOUtils.toString(this.getClass().getResourceAsStream("/entity_calc_view.xsodata"), StandardCharsets.UTF_8);
    XSOData oDataModel = parser.parseXSOData("np/entity_calc_view.xsodata", content);

    Table model = new Table("kneo.test.calcviews::calc");
    TableColumn column1 = new TableColumn("COLUMN1", "Edm.Int32", "0", true, false, model);
    TableColumn column2 = new TableColumn("COLUMN2", "Edm.Int32", "0", true, false, model);
    TableColumn column3 = new TableColumn("COLUMN3", "Edm.Int32", "0", true, false, model);
    
    model.setKind("CALC VIEW");

    when(metadataProvider.getPersistenceTableModel("kneo.test.calcviews::calc")).thenReturn(model);
    when(mockDataSource.getConnection()).thenReturn(mockConnection);
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

    OData oDataDefinition = oDataUtil.convertODataModelToODataDefinition(oDataModel);

    assertEquals(3, oDataDefinition.getEntities().get(0).getProperties().size());
    assertEquals(1, oDataDefinition.getEntities().get(1).getProperties().size());
    assertEquals(2, oDataDefinition.getEntities().get(2).getProperties().size());
  }

  /**
	 * Test calc view with input parameters.
	 *
	 * @throws Exception the exception
	 */
  //@Test
  public void testCalcViewWithInputParameters() throws Exception {
    String content = IOUtils.toString(this.getClass().getResourceAsStream("/entity_calc_view_with_input_parameters.xsodata"),
        StandardCharsets.UTF_8);

    DBArtifactModel dbArtifactModelCalcView = new DBArtifactModel(ISqlKeywords.METADATA_CALC_VIEW, ISqlKeywords.METADATA_CALC_VIEW,
        ISqlKeywords.METADATA_CALC_VIEW);

    when(mockDataSource.getConnection()).thenReturn(mockConnection);
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

    doReturn(List.of(dbArtifactModelCalcView)).when(parser).getDBArtifactsByName(anyString());

    XSOData oDataModel = parser.parseXSOData("np/entity_calc_view_with_input_parameters.xsodata", content);

    Table model = new Table("kneo.test.calcviews::calc");
    TableColumn column1 = new TableColumn("COLUMN1", "Edm.Int32", "0", true, false, model);
    TableColumn column2 = new TableColumn("COLUMN2", "Edm.Int32", "0", true, false, model);
    TableColumn column3 = new TableColumn("COLUMN3", "Edm.Int32", "0", true, false, model);
    
    model.setKind("CALC VIEW");

    when(metadataProvider.getPersistenceTableModel("kneo.test.calcviews::calc")).thenReturn(model);
    when(mockDataSource.getConnection()).thenReturn(mockConnection);
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true).thenReturn(false).thenReturn(true).thenReturn(false);
    when(mockResultSet.getString("VARIABLE_NAME")).thenReturn("CurrentUserId");
    when(mockResultSet.getString("COLUMN_SQL_TYPE")).thenReturn("INTEGER");
    when(mockResultSet.getString("MANDATORY")).thenReturn("0");

    OData oDataDefinition = oDataUtil.convertODataModelToODataDefinition(oDataModel);

    assertEquals(3, oDataDefinition.getEntities().get(0).getProperties().size());
    assertEquals(1, oDataDefinition.getEntities().get(0).getParameters().size());

    assertEquals(0, oDataDefinition.getEntities().get(1).getProperties().size());
    assertEquals(1, oDataDefinition.getEntities().get(1).getParameters().size());
    assertEquals(1, oDataDefinition.getEntities().get(1).getNavigations().size());

    assertEquals(3, oDataDefinition.getEntities().get(2).getProperties().size());
    assertEquals(1, oDataDefinition.getEntities().get(2).getParameters().size());

    assertEquals(0, oDataDefinition.getEntities().get(3).getProperties().size());
    assertEquals(1, oDataDefinition.getEntities().get(3).getParameters().size());
    assertEquals(1, oDataDefinition.getEntities().get(3).getNavigations().size());

    assertEquals(2, oDataDefinition.getAssociations().size());
  }

  /**
	 * Test synonym.
	 *
	 * @throws Exception the exception
	 */
  //@Test
  public void testSynonym() throws Exception {
    try (MockedStatic<CommonsDBUtils> commonsDBUtils = Mockito.mockStatic(CommonsDBUtils.class)) {

      String content = IOUtils.toString(this.getClass().getResourceAsStream("/entity_synonym.xsodata"), StandardCharsets.UTF_8);
      XSOData oDataModel = parser.parseXSOData("np/entity_synonym.xsodata", content);

      Table calcViewModel = new Table("kneo.test.calcviews::calc");
      TableColumn column1 = new TableColumn("COLUMN1", "Edm.Int32", "0", true, false, calcViewModel);
      TableColumn column2 = new TableColumn("COLUMN2", "Edm.Int32", "0", true, false, calcViewModel);
      TableColumn column3 = new TableColumn("COLUMN3", "Edm.Int32", "0", true, false, calcViewModel);

      calcViewModel.setKind(ISqlKeywords.METADATA_CALC_VIEW);

      when(metadataProvider.getPersistenceTableModel("TestCalcView")).thenReturn(calcViewModel);
      when(mockDataSource.getConnection()).thenReturn(mockConnection);
      when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
      when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

      OData oDataDefinition = oDataUtil.convertODataModelToODataDefinition(oDataModel);

      assertEquals(3, oDataDefinition.getEntities().get(0).getProperties().size());
      assertEquals(1, oDataDefinition.getEntities().get(1).getProperties().size());
      assertEquals(2, oDataDefinition.getEntities().get(2).getProperties().size());
    }

  }

  /**
	 * Test proper navigation construction.
	 *
	 * @throws IOException             Signals that an I/O exception has occurred.
	 * @throws ArtifactParserException the artifact parser exception
	 * @throws SQLException            the SQL exception
	 */
  @Test
  public void testProperNavigationConstruction() throws IOException, ArtifactParserException, SQLException {
    String content = IOUtils.toString(this.getClass().getResourceAsStream("/entity_with_3_navigations.xsodata"), StandardCharsets.UTF_8);
    XSOData oDataModel = parser.parseXSOData("np/entity_with_3_navigations.xsodata", content);

    OData oDataDefinitionModel = new OData();
    for (XSODataEntity entity : oDataModel.getService().getEntities()) {
      ODataEntity oDataEntityDefinition = new ODataEntity();
      entity.getNavigates().forEach(oDataUtil.processNavigation(oDataModel, oDataDefinitionModel, oDataEntityDefinition));

      assertEquals(entity.getNavigates().size(),
          oDataEntityDefinition.getNavigations().size(), "Unexpected number of navigations for entity: " + entity.getAlias());
    }

  }

  /**
	 * Test aggregations construction.
	 *
	 * @throws Exception the exception
	 */
  @Test
  public void testAggregationsConstruction() throws Exception {
    XSODataArtefactParser parser = new XSODataArtefactParser();
    String content = IOUtils.toString(this.getClass().getResourceAsStream("/entity_with_aggregations_for_conversion.xsodata"),
        StandardCharsets.UTF_8);
    XSOData oDataModel = parser.parseXSOData("np/entity_with_aggregations_for_conversion.xsodata", content);

    Table model = new Table("TEST_VIEW");
    TableColumn column1 = new TableColumn("USER_ID", "Edm.Int32", "0", false, true, model);
    TableColumn column2 = new TableColumn("USER_PAYMENT", "Edm.Int32", "0", true, false, model);
    TableColumn column3 = new TableColumn("ROLE_NUMBER", "Edm.Int32", "0", true, false, model);
    
    model.setKind("CALC VIEW");
    when(metadataProvider.getPersistenceTableModel("TEST_VIEW")).thenReturn(model);

    OData oDataDefinition = oDataUtil.convertODataModelToODataDefinition(oDataModel);

    assertTrue(oDataDefinition.getEntities().get(0).getAggregationsTypeAndColumn().containsKey("USER_PAYMENT"));
    assertTrue(oDataDefinition.getEntities().get(0).getAggregationsTypeAndColumn().containsValue("SUM"));
    assertTrue(oDataDefinition.getEntities().get(0).getAnnotationsEntityType().containsKey("sap:semantics"));
    assertTrue(oDataDefinition.getEntities().get(0).getAnnotationsEntityType().containsValue("aggregate"));
  }
}
