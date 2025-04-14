/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.parser.xsodata.custom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import com.codbex.kronos.parser.xsodata.core.XSODataLexer;
import com.codbex.kronos.parser.xsodata.core.XSODataParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import com.codbex.kronos.parser.xsodata.model.XSODataAggregation;
import com.codbex.kronos.parser.xsodata.model.XSODataAggregationType;
import com.codbex.kronos.parser.xsodata.model.XSODataAssociation;
import com.codbex.kronos.parser.xsodata.model.XSODataAssociationTable;
import com.codbex.kronos.parser.xsodata.model.XSODataBinding;
import com.codbex.kronos.parser.xsodata.model.XSODataBindingRole;
import com.codbex.kronos.parser.xsodata.model.XSODataBindingType;
import com.codbex.kronos.parser.xsodata.model.XSODataEntity;
import com.codbex.kronos.parser.xsodata.model.XSODataEvent;
import com.codbex.kronos.parser.xsodata.model.XSODataEventType;
import com.codbex.kronos.parser.xsodata.model.XSODataHandlerMethod;
import com.codbex.kronos.parser.xsodata.model.XSODataModification;
import com.codbex.kronos.parser.xsodata.model.XSODataModificationSpec;
import com.codbex.kronos.parser.xsodata.model.XSODataMultiplicityType;
import com.codbex.kronos.parser.xsodata.model.XSODataNavigation;
import com.codbex.kronos.parser.xsodata.model.XSODataRepositoryObject;
import com.codbex.kronos.parser.xsodata.model.XSODataService;
import com.codbex.kronos.parser.xsodata.model.XSODataSetting;
import com.codbex.kronos.parser.xsodata.model.XSODataStorage;
import com.codbex.kronos.parser.xsodata.model.XSODataStorageType;

public class XSODataDefinitionListenerTest {

    private final XSODataDefinitionListener listener = new XSODataDefinitionListener();

    @Test
    public void parseEmptyServiceSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/empty_service.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertNull(model.getNamespace());
        assertEquals(model.getEntities()
                          .size(),
                0);
        assertFalse(model.isEnableOData4SAPAnnotations());
    }

    @Test
    public void parseEmptyServiceWithNamespaceDefinitionSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/empty_service_with_namespace.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getNamespace(), "my.demo.namespace");
        assertEquals(model.getEntities()
                          .size(),
                0);
        assertFalse(model.isEnableOData4SAPAnnotations());
    }

    @Test
    public void parseEmptyServiceWithAnnotationSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/empty_service_with_annotations.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertNull(model.getNamespace());
        assertEquals(model.getEntities()
                          .size(),
                0);
        assertTrue(model.isEnableOData4SAPAnnotations());
    }

    @Test
    public void parseEmptyServiceWithSettingsSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/empty_service_with_settings.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertNull(model.getNamespace());
        assertEquals(model.getEntities()
                          .size(),
                0);
        assertEquals(model.getAssociations()
                          .size(),
                0);
        assertNotNull(model.getSetting());
        XSODataSetting actualSetting = new XSODataSetting();
        actualSetting.setEnableSupportNull(true);
        actualSetting.setMaxExpandedRecords("30");
        actualSetting.setMaxRecords("10");
        actualSetting.setContentCacheControl(Collections.singletonList("no-store"));
        actualSetting.setMetadataCacheControl(Arrays.asList("no-cache", "no-store", "max-age=86401", "must-revalidate"));
        actualSetting.setHints(Arrays.asList("NO_CALC_VIEW_UNFOLDING", "ABC"));
        assertEquals(model.getSetting(), actualSetting);
    }

    @Test
    public void parseEmptyServiceWithSettingsWithHintsAsNullSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/empty_service_without_hints.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertNull(model.getNamespace());
        assertEquals(model.getEntities()
                          .size(),
                0);
        assertEquals(model.getAssociations()
                          .size(),
                0);
        assertNotNull(model.getSetting());
        XSODataSetting actualSetting = new XSODataSetting();
        actualSetting.setHints(Collections.singletonList("null"));
        assertEquals(model.getSetting(), actualSetting);
    }

    @Test
    public void parseServiceWithObjectsSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/entity_with_object_exposure.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities()
                          .size(),
                2);

        XSODataEntity actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("sample.odata::table")
                                                                      .setCatalogObjectSchema("XSODATA_TEST"));
        actualEntity.setAlias("MyTable");
        assertEquals(model.getEntities()
                          .get(0),
                actualEntity);

        actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("test/AN_AGENCY_REV.analyticView"));
        actualEntity.setAlias("Revenue");
        assertEquals(model.getEntities()
                          .get(1),
                actualEntity);
    }

    @Test
    public void parseServiceWithPropertyProjectionSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/entity_with_prop.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities()
                          .size(),
                2);

        XSODataEntity actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("sample.odata::table"));
        actualEntity.setAlias("MyTable");
        actualEntity.setWithPropertyProjections(Arrays.asList("ID", "Text"));
        assertEquals(model.getEntities()
                          .get(0),
                actualEntity);

        actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("sample.odata::table"));
        actualEntity.setAlias("MyTable");
        actualEntity.setWithoutPropertyProjections(Arrays.asList("Text", "Time"));
        assertEquals(model.getEntities()
                          .get(1),
                actualEntity);
    }

    @Test
    public void parseServiceWithKeysSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/entity_with_keys.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities()
                          .size(),
                4);

        XSODataEntity actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("sample.odata::view"));
        actualEntity.setAlias("MyView");
        actualEntity.setKeyList(Arrays.asList("ID", "Text"));
        assertEquals(model.getEntities()
                          .get(0),
                actualEntity);
        assertEquals(model.getEntities()
                          .get(1),
                actualEntity);

        actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("sample.odata::view"));
        actualEntity.setAlias("MyView");
        actualEntity.setKeyGenerated("GenID");
        assertEquals(model.getEntities()
                          .get(2),
                actualEntity);

        actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("test/AN_AGENCY.analyticView"));
        actualEntity.setAlias("Revenue");
        actualEntity.setKeyGenerated("GENERATED_ID");
        assertEquals(model.getEntities()
                          .get(3),
                actualEntity);
    }

    @Test
    public void parseServiceWithNavigationsSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/entity_with_navigation.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertEquals(5, model.getEntities()
                             .size());

        XSODataEntity actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("sample.odata::customer"));
        actualEntity.setAlias("Customers");
        XSODataNavigation nav1 = new XSODataNavigation().setAssociation("Customer_Orders")
                                                        .setAliasNavigation("HisOrders");
        XSODataNavigation nav2 = new XSODataNavigation().setAssociation("Customer_Recruit")
                                                        .setAliasNavigation("Recruit")
                                                        .setFromBindingType(XSODataBindingType.DEPENDENT);
        actualEntity.setNavigates(Arrays.asList(nav1, nav2));
        assertEquals(model.getEntities()
                          .get(0),
                actualEntity);

        actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("sample.odata::student"));
        actualEntity.setAlias("Students");
        actualEntity.setNavigates(Collections.singletonList(new XSODataNavigation().setAssociation("Students_Grades")
                                                                                   .setAliasNavigation("HisGrades")
                                                                                   .setFromBindingType(XSODataBindingType.PRINCIPAL)));
        assertEquals(model.getEntities()
                          .get(1),
                actualEntity);
    }

    @Test
    public void parseServiceWithAggregationsSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/entity_with_aggregations.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities()
                          .size(),
                3);

        XSODataEntity actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("package::revenues"));
        actualEntity.setAlias("Revenues");
        XSODataAggregation agg1 = new XSODataAggregation().setAggregateColumnName("Amount")
                                                          .setAggregateFunction("SUM");
        XSODataAggregation agg2 = new XSODataAggregation().setAggregateColumnName("Amount")
                                                          .setAggregateFunction("AVG");
        XSODataAggregation agg3 = new XSODataAggregation().setAggregateColumnName("Amount")
                                                          .setAggregateFunction("MIN");
        actualEntity.setAggregations(Arrays.asList(agg1, agg2, agg3));
        actualEntity.setKeyGenerated("ID");
        actualEntity.setAggregationType(XSODataAggregationType.EXPLICIT);
        assertEquals(model.getEntities()
                          .get(0),
                actualEntity);

        actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("package::revenues"));
        actualEntity.setAlias("Revenues");
        actualEntity.setKeyGenerated("ID");
        actualEntity.setAggregations(Collections.singletonList((new XSODataAggregation()).setAggregateColumnName("Amount")
                                                                                         .setAggregateFunction("MAX")));
        actualEntity.setAggregationType(XSODataAggregationType.EXPLICIT);
        assertEquals(model.getEntities()
                          .get(1),
                actualEntity);

        actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("package::PLANNED_ACTUAL_SALES"));
        actualEntity.setAlias("PlannedCalcView");
        actualEntity.setKeyGenerated("ID");
        actualEntity.setAggregationType(XSODataAggregationType.IMPLICIT);
        assertEquals(model.getEntities()
                          .get(2),
                actualEntity);
    }

    @Test
    public void parseServiceWithEventsSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/entity_with_events.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities()
                          .size(),
                4);

        XSODataEntity actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("sample.odata::table"));
        XSODataModification updateModification = new XSODataModification().setMethod(XSODataHandlerMethod.UPDATE);
        updateModification.setSpecification(new XSODataModificationSpec().setEvents(
                Arrays.asList(new XSODataEvent(XSODataEventType.BEFORE, "sample.odata::beforeMethod"),
                        new XSODataEvent(XSODataEventType.PRECOMMIT, "sample.odata::beforeMethod"))));
        XSODataModification deleteModification = new XSODataModification().setMethod(XSODataHandlerMethod.DELETE);
        deleteModification.setSpecification(new XSODataModificationSpec().setEvents(
                Collections.singletonList(new XSODataEvent(XSODataEventType.AFTER, "sample.odata::afterMethod"))));
        XSODataModification createModification = new XSODataModification().setMethod(XSODataHandlerMethod.CREATE);
        createModification.setSpecification(new XSODataModificationSpec().setForbidden(true));
        actualEntity.setModifications(Arrays.asList(updateModification, deleteModification, createModification));
        assertEquals(model.getEntities()
                          .get(0),
                actualEntity);

        actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("sample.odata::table"));
        createModification = new XSODataModification().setMethod(XSODataHandlerMethod.CREATE);
        createModification.setSpecification(new XSODataModificationSpec().setModificationAction("sample.odata::createMethod")
                                                                         .setEvents(Collections.singletonList(new XSODataEvent(
                                                                                 XSODataEventType.AFTER, "sample.odata::afterMethod"))));
        updateModification = new XSODataModification().setMethod(XSODataHandlerMethod.UPDATE);
        updateModification.setSpecification(new XSODataModificationSpec().setModificationAction("sample.odata::updateMethod"));
        deleteModification = new XSODataModification().setMethod(XSODataHandlerMethod.DELETE);
        deleteModification.setSpecification(new XSODataModificationSpec().setModificationAction("sample.odata::deleteMethod"));
        actualEntity.setModifications(Arrays.asList(createModification, updateModification, deleteModification));
        assertEquals(model.getEntities()
                          .get(1),
                actualEntity);

        actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("sample.odata::table"));
        createModification = new XSODataModification().setMethod(XSODataHandlerMethod.CREATE);
        createModification.setSpecification(new XSODataModificationSpec().setModificationAction("sample.odata::createMethod"));
        updateModification = new XSODataModification().setMethod(XSODataHandlerMethod.UPDATE);
        updateModification.setSpecification(new XSODataModificationSpec().setEvents(
                Collections.singletonList(new XSODataEvent(XSODataEventType.PRECOMMIT, "sample.odata::precommitMethod"))));
        deleteModification = new XSODataModification().setMethod(XSODataHandlerMethod.DELETE)
                                                      .setSpecification(new XSODataModificationSpec().setForbidden(true));
        actualEntity.setModifications(Arrays.asList(createModification, updateModification, deleteModification));
        assertEquals(model.getEntities()
                          .get(2),
                actualEntity);

        actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("sample.odata::table"));
        createModification = new XSODataModification().setMethod(XSODataHandlerMethod.CREATE)
                                                      .setSpecification(new XSODataModificationSpec().setForbidden(true));
        updateModification = new XSODataModification().setMethod(XSODataHandlerMethod.UPDATE)
                                                      .setSpecification(new XSODataModificationSpec().setForbidden(true));
        deleteModification = new XSODataModification().setMethod(XSODataHandlerMethod.DELETE)
                                                      .setSpecification(new XSODataModificationSpec().setForbidden(true));
        actualEntity.setModifications(Arrays.asList(createModification, updateModification, deleteModification));
        assertEquals(model.getEntities()
                          .get(3),
                actualEntity);
    }

    @Test
    public void parseServiceWithAssociationsSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/entity_with_associations.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getAssociations()
                          .size(),
                6);

        XSODataAssociation actualAssociation = new XSODataAssociation();
        actualAssociation.setName("complex_atob");
        actualAssociation.setWithReferentialConstraint(false);
        XSODataBinding principal = new XSODataBinding();
        principal.setEntitySetName("complex_a");
        principal.setBindingRole(new XSODataBindingRole().setBindingType(XSODataBindingType.PRINCIPAL)
                                                         .setKeys(Collections.singletonList("ID")));
        principal.setMultiplicityType(XSODataMultiplicityType.MANY);
        actualAssociation.setPrincipal(principal);
        XSODataBinding dependent = new XSODataBinding();
        dependent.setEntitySetName("complex_b");
        dependent.setBindingRole(new XSODataBindingRole().setBindingType(XSODataBindingType.DEPENDENT)
                                                         .setKeys(Collections.singletonList("ID_A")));
        dependent.setMultiplicityType(XSODataMultiplicityType.MANY);
        actualAssociation.setDependent(dependent);
        XSODataAssociationTable table = new XSODataAssociationTable();
        table.setRepositoryObject("xsodata.test.tables::complex_assoc_atob");
        table.setPrincipal(new XSODataBindingRole().setBindingType(XSODataBindingType.PRINCIPAL)
                                                   .setKeys(Collections.singletonList("ID_A")));
        table.setDependent(new XSODataBindingRole().setBindingType(XSODataBindingType.DEPENDENT)
                                                   .setKeys(Collections.singletonList("ID_B")));
        actualAssociation.setAssociationTable(table);
        assertEquals(model.getAssociations()
                          .get(0),
                actualAssociation);

        XSODataModification updateModification = new XSODataModification().setMethod(XSODataHandlerMethod.UPDATE);
        updateModification.setSpecification(new XSODataModificationSpec().setEvents(
                Collections.singletonList(new XSODataEvent(XSODataEventType.BEFORE, "sample.odata::updateMethod"))));
        XSODataModification deleteModification = new XSODataModification().setMethod(XSODataHandlerMethod.DELETE);
        deleteModification.setSpecification(new XSODataModificationSpec().setEvents(
                Collections.singletonList(new XSODataEvent(XSODataEventType.BEFORE, "sample.odata::deleteMethod"))));
        XSODataModification createModification = new XSODataModification().setMethod(XSODataHandlerMethod.CREATE);
        createModification.setSpecification(new XSODataModificationSpec().setEvents(
                Collections.singletonList(new XSODataEvent(XSODataEventType.BEFORE, "sample.odata::createMethod"))));
        table.setModifications(Arrays.asList(deleteModification, createModification, updateModification));
        actualAssociation.setAssociationTable(table);
        assertEquals(model.getAssociations()
                          .get(1),
                actualAssociation);

        actualAssociation = new XSODataAssociation();
        actualAssociation.setName("complex_b_to_c");
        actualAssociation.setWithReferentialConstraint(false);
        principal = new XSODataBinding();
        principal.setEntitySetName("complex_b");
        principal.setBindingRole(new XSODataBindingRole().setBindingType(XSODataBindingType.PRINCIPAL)
                                                         .setKeys(Collections.singletonList("ID")));
        principal.setMultiplicityType(XSODataMultiplicityType.ONE);
        actualAssociation.setPrincipal(principal);
        dependent = new XSODataBinding();
        dependent.setEntitySetName("complex_c");
        dependent.setBindingRole(new XSODataBindingRole().setBindingType(XSODataBindingType.DEPENDENT)
                                                         .setKeys(Collections.singletonList("ID_A")));
        dependent.setMultiplicityType(XSODataMultiplicityType.MANY);
        actualAssociation.setDependent(dependent);
        updateModification = new XSODataModification().setMethod(XSODataHandlerMethod.UPDATE)
                                                      .setSpecification(new XSODataModificationSpec().setModificationAction(
                                                              "sap.test:oDataExtendedRules.xsjslib::associationToConditions"));
        table.setModifications(Arrays.asList(deleteModification, createModification, updateModification));
        actualAssociation.setStorage(new XSODataStorage().setStorageType(XSODataStorageType.STORAGE_ON_DEPENDENT)
                                                         .setModifications(Collections.singletonList(updateModification)));
        assertEquals(model.getAssociations()
                          .get(2),
                actualAssociation);

        actualAssociation = new XSODataAssociation();
        actualAssociation.setName("complex_b_to_c");
        actualAssociation.setWithReferentialConstraint(false);
        principal = new XSODataBinding();
        principal.setEntitySetName("complex_b");
        principal.setBindingRole(new XSODataBindingRole().setBindingType(XSODataBindingType.PRINCIPAL)
                                                         .setKeys(Collections.singletonList("ID")));
        principal.setMultiplicityType(XSODataMultiplicityType.ZERO_TO_ONE);
        actualAssociation.setPrincipal(principal);
        dependent = new XSODataBinding();
        dependent.setEntitySetName("complex_c");
        dependent.setBindingRole(new XSODataBindingRole().setBindingType(XSODataBindingType.DEPENDENT)
                                                         .setKeys(Collections.singletonList("ID_B")));
        dependent.setMultiplicityType(XSODataMultiplicityType.ONE);
        actualAssociation.setDependent(dependent);
        actualAssociation.setStorage(new XSODataStorage().setStorageType(XSODataStorageType.STORAGE_ON_PRINCIPAL));
        assertEquals(model.getAssociations()
                          .get(3),
                actualAssociation);

        actualAssociation = new XSODataAssociation();
        actualAssociation.setName("complex_b_to_c");
        actualAssociation.setWithReferentialConstraint(false);
        principal = new XSODataBinding();
        principal.setEntitySetName("complex_b");
        principal.setBindingRole(new XSODataBindingRole().setBindingType(XSODataBindingType.PRINCIPAL)
                                                         .setKeys(Collections.singletonList("ID")));
        principal.setMultiplicityType(XSODataMultiplicityType.ONE_TO_MANY);
        actualAssociation.setPrincipal(principal);
        dependent = new XSODataBinding();
        dependent.setEntitySetName("complex_c");
        dependent.setBindingRole(new XSODataBindingRole().setBindingType(XSODataBindingType.DEPENDENT)
                                                         .setKeys(Collections.singletonList("ID_B")));
        dependent.setMultiplicityType(XSODataMultiplicityType.MANY);
        actualAssociation.setDependent(dependent);
        actualAssociation.setStorage(new XSODataStorage().setStorageType(XSODataStorageType.NO_STORAGE));
        assertEquals(model.getAssociations()
                          .get(4),
                actualAssociation);

        actualAssociation = new XSODataAssociation();
        actualAssociation.setName("complex_b_to_c");
        actualAssociation.setWithReferentialConstraint(true);
        principal = new XSODataBinding();
        principal.setEntitySetName("complex_b");
        principal.setBindingRole(new XSODataBindingRole().setBindingType(XSODataBindingType.PRINCIPAL)
                                                         .setKeys(Collections.singletonList("ID")));
        principal.setMultiplicityType(XSODataMultiplicityType.ONE);
        actualAssociation.setPrincipal(principal);
        dependent = new XSODataBinding();
        dependent.setEntitySetName("complex_c");
        dependent.setBindingRole(new XSODataBindingRole().setBindingType(XSODataBindingType.DEPENDENT)
                                                         .setKeys(Collections.singletonList("ID_B")));
        dependent.setMultiplicityType(XSODataMultiplicityType.MANY);
        actualAssociation.setDependent(dependent);
        updateModification = new XSODataModification().setMethod(XSODataHandlerMethod.UPDATE);
        updateModification.setSpecification(new XSODataModificationSpec().setEvents(
                Collections.singletonList(new XSODataEvent(XSODataEventType.BEFORE, "sample.odata::updateMethod"))));
        deleteModification = new XSODataModification().setMethod(XSODataHandlerMethod.DELETE);
        deleteModification.setSpecification(new XSODataModificationSpec().setEvents(
                Collections.singletonList(new XSODataEvent(XSODataEventType.BEFORE, "sample.odata::deleteMethod"))));
        createModification = new XSODataModification().setMethod(XSODataHandlerMethod.CREATE);
        createModification.setSpecification(new XSODataModificationSpec().setEvents(
                Collections.singletonList(new XSODataEvent(XSODataEventType.BEFORE, "sample.odata::createMethod"))));
        actualAssociation.setModifications(Arrays.asList(deleteModification, createModification, updateModification));
        assertEquals(model.getAssociations()
                          .get(5),
                actualAssociation);
    }

    @Test
    public void parseServiceWithETagsSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/empty_service_with_etag.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities()
                          .size(),
                4);

        XSODataEntity actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("xsodata.test.tables::all_types"));
        actualEntity.setAlias("all_types_etag");
        actualEntity.setConcurrencyToken(true);
        assertEquals(model.getEntities()
                          .get(0),
                actualEntity);

        actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(
                new XSODataRepositoryObject().setCatalogObjectName("xsodata.test.tables::BusinessPartner.BusinessPartner"));
        actualEntity.setAlias("BusinessPartner");
        actualEntity.setConcurrencyToken(true);
        actualEntity.setWithoutPropertyProjections(Collections.singletonList("isContactPerson"));
        XSODataNavigation nav1 = new XSODataNavigation().setAssociation("BusinessPartner_To_N_BPRole")
                                                        .setAliasNavigation("Roles");
        actualEntity.setNavigates(Collections.singletonList(nav1));
        assertEquals(model.getEntities()
                          .get(1),
                actualEntity);

        actualEntity = new XSODataEntity();
        actualEntity.setRepositoryObject(new XSODataRepositoryObject().setCatalogObjectName("sap.test.odata.db.views::Etag"));
        actualEntity.setAlias("EtagAll");
        actualEntity.setConcurrencyToken(true);
        actualEntity.setKeyList(Arrays.asList("KEY_00", "KEY_01"));
        assertEquals(model.getEntities()
                          .get(2),
                actualEntity);

        actualEntity.setETags(Arrays.asList("NVARCHAR_01", "INTEGER_02"));
        assertEquals(model.getEntities()
                          .get(3),
                actualEntity);
    }

    @Test
    public void parseServiceSuccessfully() throws Exception {
        XSODataParser xsODataParser = parseSampleFile("/entity_with_no_associations.xsodata");

        XSODataService model = listener.getServiceModel();
        assertNotNull(model);
        assertEquals(xsODataParser.getNumberOfSyntaxErrors(), 0);
        assertEquals(model.getEntities()
                          .size(),
                20);
    }

    private XSODataParser parseSampleFile(String sampleFileName) throws Exception {
        String sample = org.apache.commons.io.IOUtils.toString(XSODataDefinitionListenerTest.class.getResourceAsStream(sampleFileName),
                StandardCharsets.UTF_8);

        ANTLRInputStream inputStream = new ANTLRInputStream(sample);
        XSODataLexer xsODataLexer = new XSODataLexer(inputStream);
        CommonTokenStream tokenStream = new CommonTokenStream(xsODataLexer);
        XSODataParser xsODataParser = new XSODataParser(tokenStream);
        xsODataParser.setBuildParseTree(true);
        ParseTree parseTree = xsODataParser.xsOdataDefinition();

        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        parseTreeWalker.walk(listener, parseTree);

        return xsODataParser;
    }

}
