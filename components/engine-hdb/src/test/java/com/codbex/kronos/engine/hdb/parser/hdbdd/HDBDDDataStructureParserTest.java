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
package com.codbex.kronos.engine.hdb.parser.hdbdd;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.domain.HDBDD;
import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.domain.HDBTableColumn;
import com.codbex.kronos.engine.hdb.domain.HDBTableType;
import com.codbex.kronos.engine.hdb.domain.HDBView;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.repository.TestRepositoryMigrator;

import jakarta.transaction.Transactional;

/**
 * The Class HDBDDDataStructureParserTest.
 */
@SpringBootTest(classes = {HDBDataStructureModelFactory.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
public class HDBDDDataStructureParserTest {

    /** The migrator. */
    @Autowired
    private TestRepositoryMigrator migrator;

    /**
     * Clean up.
     */
    @AfterEach
    public void cleanUp() {
        migrator.cleanUp();
    }

    /**
     * Test parse hana XS classic content with syntax error fail.
     */
    @Test
    public void testParseHanaXSClassicContentWithSyntaxErrorFail() {
        migrator.migrate("gstr2/ITC_EXPIRED_CONFIG.hdbdd");
        DataStructuresException exception = assertThrows(DataStructuresException.class,
                () -> HDBDataStructureModelFactory.parseHdbdd("gstr2/ITC_EXPIRED_CONFIG.hdbdd", ""));
        assertEquals(
                "com.codbex.kronos.parser.hdbdd.exception.CDSRuntimeException: Failed to parse file: gstr2/ITC_EXPIRED_CONFIG.hdbdd. Error at line: 6  - Before an entity element declaration only the 'key' keyword is allowed",
                exception.getMessage());
    }

    /**
     * Test parse hana XS classic content with lexer error fail.
     */
    @Test
    public void testParseHanaXSClassicContentWithLexerErrorFail() {
        migrator.migrate("gstr2/ITC_EXPIRED_CONFIG1.hdbdd");
        DataStructuresException exception = assertThrows(DataStructuresException.class,
                () -> HDBDataStructureModelFactory.parseHdbdd("gstr2/ITC_EXPIRED_CONFIG1.hdbdd", ""));
        assertEquals(
                "com.codbex.kronos.exceptions.ArtifactParserException: Wrong format of HDB HDBDD: [gstr2/ITC_EXPIRED_CONFIG1.hdbdd] during parsing. Ensure you are using the correct format for the correct compatibility version.",
                exception.getMessage());
    }

    /**
     * Test parse HDBDD with managed ass.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithManagedAss() throws Exception {
        migrator.migrate("gstr2/ProductsWithManagedAss.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/ProductsWithManagedAss.hdbdd", "");

        assertEquals(3, parsedModel.getTables()
                                   .size());

        HDBTable orderDataStructure = parsedModel.getTables()
                                                 .get(1);
        assertEquals("gstr2::ProductsWithManagedAss.Orders", orderDataStructure.getName());
        assertEquals("ADMIN", orderDataStructure.getSchema());
        assertEquals(true, orderDataStructure.isClassic());
        assertNotNull(orderDataStructure.getConstraints()
                                        .getPrimaryKey());
        assertEquals(1, orderDataStructure.getConstraints()
                                          .getPrimaryKey()
                                          .getColumns().length);
        assertEquals("Id", orderDataStructure.getConstraints()
                                             .getPrimaryKey()
                                             .getColumns()[0]);
        assertEquals("PK_gstr2::ProductsWithManagedAss.Orders", orderDataStructure.getConstraints()
                                                                                  .getPrimaryKey()
                                                                                  .getName());
        assertNull(orderDataStructure.getConstraints()
                                     .getPrimaryKey()
                                     .getModifiers());
        assertEquals(2, orderDataStructure.getConstraints()
                                          .getForeignKeys()
                                          .size());

        assertEquals("gstr2::ProductsWithManagedAss.Country", orderDataStructure.getConstraints()
                                                                                .getForeignKeys()
                                                                                .get(0)
                                                                                .getReferencedTable());
        assertEquals("gstr2::ProductsWithManagedAss.Orders.Country", orderDataStructure.getConstraints()
                                                                                       .getForeignKeys()
                                                                                       .get(0)
                                                                                       .getName());
        assertNull(orderDataStructure.getConstraints()
                                     .getForeignKeys()
                                     .get(0)
                                     .getModifiers());
        assertEquals(1, orderDataStructure.getConstraints()
                                          .getForeignKeys()
                                          .get(0)
                                          .getReferencedColumns().length);
        assertEquals("Id", orderDataStructure.getConstraints()
                                             .getForeignKeys()
                                             .get(0)
                                             .getReferencedColumns()[0]);
        assertEquals(1, orderDataStructure.getConstraints()
                                          .getForeignKeys()
                                          .get(0)
                                          .getColumns().length);
        assertEquals("Country.Id", orderDataStructure.getConstraints()
                                                     .getForeignKeys()
                                                     .get(0)
                                                     .getColumns()[0]);

        assertEquals("gstr2::ProductsWithManagedAss.City", orderDataStructure.getConstraints()
                                                                             .getForeignKeys()
                                                                             .get(1)
                                                                             .getReferencedTable());
        assertEquals("gstr2::ProductsWithManagedAss.Orders.City", orderDataStructure.getConstraints()
                                                                                    .getForeignKeys()
                                                                                    .get(1)
                                                                                    .getName());
        assertNull(orderDataStructure.getConstraints()
                                     .getForeignKeys()
                                     .get(1)
                                     .getModifiers());
        assertEquals(3, orderDataStructure.getConstraints()
                                          .getForeignKeys()
                                          .get(1)
                                          .getReferencedColumns().length);
        assertEquals(3, orderDataStructure.getConstraints()
                                          .getForeignKeys()
                                          .get(1)
                                          .getColumns().length);
        assertEquals("Id", orderDataStructure.getConstraints()
                                             .getForeignKeys()
                                             .get(1)
                                             .getReferencedColumns()[0]);
        assertEquals("City.Id", orderDataStructure.getConstraints()
                                                  .getForeignKeys()
                                                  .get(1)
                                                  .getColumns()[0]);
        assertEquals("Name", orderDataStructure.getConstraints()
                                               .getForeignKeys()
                                               .get(1)
                                               .getReferencedColumns()[1]);
        assertEquals("City.Name", orderDataStructure.getConstraints()
                                                    .getForeignKeys()
                                                    .get(1)
                                                    .getColumns()[1]);
        assertEquals("PostalCode", orderDataStructure.getConstraints()
                                                     .getForeignKeys()
                                                     .get(1)
                                                     .getReferencedColumns()[2]);
        assertEquals("City.PostalCode", orderDataStructure.getConstraints()
                                                          .getForeignKeys()
                                                          .get(1)
                                                          .getColumns()[2]);

        assertEquals(0, orderDataStructure.getConstraints()
                                          .getUniqueIndexes()
                                          .size());
        assertEquals(0, orderDataStructure.getConstraints()
                                          .getChecks()
                                          .size());
        assertNull(orderDataStructure.getIsPublic());
        assertNull(orderDataStructure.getLoggingType());
        assertNull(orderDataStructure.getIsTemporary());
        assertNotNull(orderDataStructure.getLocation());
        assertNull(orderDataStructure.getType());
        assertNull(orderDataStructure.getTableType());
        assertNull(orderDataStructure.getDescription());

        assertEquals(6, orderDataStructure.getColumns()
                                          .size());

        HDBTableColumn OrderId = orderDataStructure.getColumns()
                                                   .get(0);
        assertEquals("Id", OrderId.getName());
        assertTrue(OrderId.isPrimaryKey());
        assertEquals("32", OrderId.getLength());
        assertTrue(OrderId.isNullable());
        assertNull(OrderId.getDefaultValue());
        assertNull(OrderId.getPrecision());
        assertNull(OrderId.getScale());
        assertFalse(OrderId.isUnique());
        assertNull(OrderId.getComment());

        HDBTableColumn CustomerName = orderDataStructure.getColumns()
                                                        .get(1);
        assertEquals("CustomerName", CustomerName.getName());
        assertFalse(CustomerName.isPrimaryKey());
        assertEquals("500", CustomerName.getLength());
        assertTrue(CustomerName.isNullable());
        assertNull(CustomerName.getDefaultValue());
        assertNull(CustomerName.getPrecision());
        assertNull(CustomerName.getScale());
        assertFalse(CustomerName.isUnique());
        assertNull(CustomerName.getComment());

        HDBTableColumn CountryId = orderDataStructure.getColumns()
                                                     .get(2);
        assertEquals("Country.Id", CountryId.getName());
        assertTrue(CountryId.isPrimaryKey());
        assertEquals("32", CountryId.getLength());
        assertFalse(CountryId.isNullable());
        assertNull(CountryId.getDefaultValue());
        assertNull(CountryId.getPrecision());
        assertNull(CountryId.getScale());
        assertFalse(CountryId.isUnique());
        assertNull(CountryId.getComment());

        HDBTable countryDataStructure = ((HDBDD) parsedModel).getTables()
                                                             .get(0);
        assertEquals("gstr2::ProductsWithManagedAss.Country", countryDataStructure.getName());
        assertEquals("ADMIN", countryDataStructure.getSchema());
        assertEquals(true, countryDataStructure.isClassic());
        assertNotNull(countryDataStructure.getConstraints()
                                          .getPrimaryKey());
        assertEquals(2, countryDataStructure.getConstraints()
                                            .getPrimaryKey()
                                            .getColumns().length);
        assertEquals("Id", countryDataStructure.getConstraints()
                                               .getPrimaryKey()
                                               .getColumns()[0]);
        assertEquals("Id2", countryDataStructure.getConstraints()
                                                .getPrimaryKey()
                                                .getColumns()[1]);
        assertEquals("PK_gstr2::ProductsWithManagedAss.Country", countryDataStructure.getConstraints()
                                                                                     .getPrimaryKey()
                                                                                     .getName());
        assertNull(countryDataStructure.getConstraints()
                                       .getPrimaryKey()
                                       .getModifiers());
        assertEquals(0, countryDataStructure.getConstraints()
                                            .getForeignKeys()
                                            .size());
        assertEquals(0, countryDataStructure.getConstraints()
                                            .getUniqueIndexes()
                                            .size());
        assertEquals(0, countryDataStructure.getConstraints()
                                            .getChecks()
                                            .size());
        assertEquals(3, countryDataStructure.getColumns()
                                            .size());
        CountryId = countryDataStructure.getColumns()
                                        .get(0);
        assertEquals("Id", CountryId.getName());
        assertTrue(CountryId.isPrimaryKey());
        HDBTableColumn CountryId2 = countryDataStructure.getColumns()
                                                        .get(1);
        assertEquals("Id2", CountryId2.getName());
        assertTrue(CountryId2.isPrimaryKey());
        HDBTableColumn CountryName = countryDataStructure.getColumns()
                                                         .get(2);
        assertEquals("Name", CountryName.getName());
        assertFalse(CountryName.isPrimaryKey());

        assertEquals("gstr2/ProductsWithManagedAss.hdbdd", parsedModel.getLocation());
        assertEquals("gstr2/ProductsWithManagedAss.hdbdd", parsedModel.getName());
        assertEquals("HDBDD", parsedModel.getType());
        assertEquals("guest", parsedModel.getCreatedBy());
        // assertEquals(0, parsedModel.getDependencies().length());
        assertEquals("ADMIN", parsedModel.getSchema());
        assertNull(parsedModel.getContent());
        assertEquals(true, parsedModel.isClassic());

    }

    /**
     * Test parse HDBDD with managed ass and alias.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithManagedAssAndAlias() throws Exception {
        migrator.migrate("gstr2/ClientsWithManagedAssAndAlias.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/ClientsWithManagedAssAndAlias.hdbdd", "");

        assertEquals(2, parsedModel.getTables()
                                   .size());

        HDBTable personDataStructure = parsedModel.getTables()
                                                  .get(1);
        assertEquals("gstr2::ClientsWithManagedAssAndAlias.Person", personDataStructure.getName());
        assertEquals("ADMIN", personDataStructure.getSchema());
        assertEquals(true, personDataStructure.isClassic());
        assertNotNull(personDataStructure.getConstraints()
                                         .getPrimaryKey());
        assertEquals(1, personDataStructure.getConstraints()
                                           .getPrimaryKey()
                                           .getColumns().length);
        assertEquals("id", personDataStructure.getConstraints()
                                              .getPrimaryKey()
                                              .getColumns()[0]);
        assertEquals("PK_gstr2::ClientsWithManagedAssAndAlias.Person", personDataStructure.getConstraints()
                                                                                          .getPrimaryKey()
                                                                                          .getName());
        assertNull(personDataStructure.getConstraints()
                                      .getPrimaryKey()
                                      .getModifiers());
        assertEquals(2, personDataStructure.getConstraints()
                                           .getForeignKeys()
                                           .size());

        assertEquals("gstr2::ClientsWithManagedAssAndAlias.Address", personDataStructure.getConstraints()
                                                                                        .getForeignKeys()
                                                                                        .get(0)
                                                                                        .getReferencedTable());
        assertEquals("gstr2::ClientsWithManagedAssAndAlias.Address", personDataStructure.getConstraints()
                                                                                        .getForeignKeys()
                                                                                        .get(1)
                                                                                        .getReferencedTable());
        assertEquals("gstr2::ClientsWithManagedAssAndAlias.Person.address1", personDataStructure.getConstraints()
                                                                                                .getForeignKeys()
                                                                                                .get(0)
                                                                                                .getName());
        assertEquals("gstr2::ClientsWithManagedAssAndAlias.Person.address2", personDataStructure.getConstraints()
                                                                                                .getForeignKeys()
                                                                                                .get(1)
                                                                                                .getName());
        assertNull(personDataStructure.getConstraints()
                                      .getForeignKeys()
                                      .get(0)
                                      .getModifiers());
        assertNull(personDataStructure.getConstraints()
                                      .getForeignKeys()
                                      .get(1)
                                      .getModifiers());
        assertEquals(4, personDataStructure.getConstraints()
                                           .getForeignKeys()
                                           .get(0)
                                           .getReferencedColumns().length);
        assertEquals(4, personDataStructure.getConstraints()
                                           .getForeignKeys()
                                           .get(1)
                                           .getReferencedColumns().length);
        assertEquals("id", personDataStructure.getConstraints()
                                              .getForeignKeys()
                                              .get(0)
                                              .getReferencedColumns()[0]);
        assertEquals("country", personDataStructure.getConstraints()
                                                   .getForeignKeys()
                                                   .get(0)
                                                   .getReferencedColumns()[1]);
        assertEquals("city", personDataStructure.getConstraints()
                                                .getForeignKeys()
                                                .get(0)
                                                .getReferencedColumns()[2]);
        assertEquals("zipCode", personDataStructure.getConstraints()
                                                   .getForeignKeys()
                                                   .get(0)
                                                   .getReferencedColumns()[3]);
        assertEquals("id", personDataStructure.getConstraints()
                                              .getForeignKeys()
                                              .get(1)
                                              .getReferencedColumns()[0]);
        assertEquals("country", personDataStructure.getConstraints()
                                                   .getForeignKeys()
                                                   .get(1)
                                                   .getReferencedColumns()[1]);
        assertEquals("city", personDataStructure.getConstraints()
                                                .getForeignKeys()
                                                .get(1)
                                                .getReferencedColumns()[2]);
        assertEquals("zipCode", personDataStructure.getConstraints()
                                                   .getForeignKeys()
                                                   .get(1)
                                                   .getReferencedColumns()[3]);
        assertEquals(4, personDataStructure.getConstraints()
                                           .getForeignKeys()
                                           .get(0)
                                           .getColumns().length);
        assertEquals(4, personDataStructure.getConstraints()
                                           .getForeignKeys()
                                           .get(1)
                                           .getColumns().length);
        assertEquals("PersonAddressId1", personDataStructure.getConstraints()
                                                            .getForeignKeys()
                                                            .get(0)
                                                            .getColumns()[0]);
        assertEquals("address1.country", personDataStructure.getConstraints()
                                                            .getForeignKeys()
                                                            .get(0)
                                                            .getColumns()[1]);
        assertEquals("address1.city", personDataStructure.getConstraints()
                                                         .getForeignKeys()
                                                         .get(0)
                                                         .getColumns()[2]);
        assertEquals("address1.zipCode", personDataStructure.getConstraints()
                                                            .getForeignKeys()
                                                            .get(0)
                                                            .getColumns()[3]);
        assertEquals("PersonAddressId2", personDataStructure.getConstraints()
                                                            .getForeignKeys()
                                                            .get(1)
                                                            .getColumns()[0]);
        assertEquals("address2.country", personDataStructure.getConstraints()
                                                            .getForeignKeys()
                                                            .get(1)
                                                            .getColumns()[1]);
        assertEquals("address2.city", personDataStructure.getConstraints()
                                                         .getForeignKeys()
                                                         .get(1)
                                                         .getColumns()[2]);
        assertEquals("address2.zipCode", personDataStructure.getConstraints()
                                                            .getForeignKeys()
                                                            .get(1)
                                                            .getColumns()[3]);

        assertEquals(0, personDataStructure.getConstraints()
                                           .getUniqueIndexes()
                                           .size());
        assertEquals(0, personDataStructure.getConstraints()
                                           .getChecks()
                                           .size());
        assertNull(personDataStructure.getIsPublic());
        assertNull(personDataStructure.getLoggingType());
        assertNull(personDataStructure.getIsTemporary());
        assertNotNull(personDataStructure.getLocation());
        assertNull(personDataStructure.getType());
        assertNull(personDataStructure.getTableType());
        assertNull(personDataStructure.getDescription());

        assertEquals(9, personDataStructure.getColumns()
                                           .size());

        HDBTableColumn personId = personDataStructure.getColumns()
                                                     .get(0);
        assertEquals("id", personId.getName());
        assertTrue(personId.isPrimaryKey());
        assertNull(personId.getLength());
        assertTrue(personId.isNullable());
        assertNull(personId.getDefaultValue());
        assertNull(personId.getPrecision());
        assertNull(personId.getScale());
        assertFalse(personId.isUnique());
        assertNull(personId.getComment());

        HDBTableColumn personAddress1Id = personDataStructure.getColumns()
                                                             .get(1);
        assertEquals("PersonAddressId1", personAddress1Id.getName());
        assertFalse(personAddress1Id.isPrimaryKey());
        assertNull(personAddress1Id.getLength());
        assertTrue(personAddress1Id.isNullable());
        assertNull(personAddress1Id.getDefaultValue());
        assertNull(personAddress1Id.getPrecision());
        assertNull(personAddress1Id.getScale());
        assertFalse(personAddress1Id.isUnique());
        assertNull(personAddress1Id.getComment());

        HDBTableColumn personAddress1Country = personDataStructure.getColumns()
                                                                  .get(2);
        assertEquals("address1.country", personAddress1Country.getName());
        assertFalse(personAddress1Country.isPrimaryKey());
        assertEquals("30", personAddress1Country.getLength());
        assertTrue(personAddress1Country.isNullable());
        assertNull(personAddress1Country.getDefaultValue());
        assertNull(personAddress1Country.getPrecision());
        assertNull(personAddress1Country.getScale());
        assertFalse(personAddress1Country.isUnique());
        assertNull(personAddress1Country.getComment());

        HDBTableColumn personAddress1City = personDataStructure.getColumns()
                                                               .get(3);
        assertEquals("address1.city", personAddress1City.getName());
        assertFalse(personAddress1City.isPrimaryKey());
        assertEquals("30", personAddress1City.getLength());
        assertTrue(personAddress1City.isNullable());
        assertNull(personAddress1City.getDefaultValue());
        assertNull(personAddress1City.getPrecision());
        assertNull(personAddress1City.getScale());
        assertFalse(personAddress1City.isUnique());
        assertNull(personAddress1City.getComment());

        HDBTableColumn personAddress1Zip = personDataStructure.getColumns()
                                                              .get(4);
        assertEquals("address1.zipCode", personAddress1Zip.getName());
        assertFalse(personAddress1Zip.isPrimaryKey());
        assertEquals("30", personAddress1Zip.getLength());
        assertTrue(personAddress1Zip.isNullable());
        assertNull(personAddress1Zip.getDefaultValue());
        assertNull(personAddress1Zip.getPrecision());
        assertNull(personAddress1Zip.getScale());
        assertFalse(personAddress1Zip.isUnique());
        assertNull(personAddress1Zip.getComment());

        HDBTableColumn personAddress2Id = personDataStructure.getColumns()
                                                             .get(5);
        assertEquals("PersonAddressId2", personAddress2Id.getName());
        assertFalse(personAddress2Id.isPrimaryKey());
        assertNull(personAddress2Id.getLength());
        assertTrue(personAddress2Id.isNullable());
        assertNull(personAddress2Id.getDefaultValue());
        assertNull(personAddress2Id.getPrecision());
        assertNull(personAddress2Id.getScale());
        assertFalse(personAddress2Id.isUnique());
        assertNull(personAddress2Id.getComment());

        HDBTableColumn personAddress2Country = personDataStructure.getColumns()
                                                                  .get(6);
        assertEquals("address2.country", personAddress2Country.getName());
        assertFalse(personAddress2Country.isPrimaryKey());
        assertEquals("30", personAddress2Country.getLength());
        assertTrue(personAddress2Country.isNullable());
        assertNull(personAddress2Country.getDefaultValue());
        assertNull(personAddress2Country.getPrecision());
        assertNull(personAddress2Country.getScale());
        assertFalse(personAddress2Country.isUnique());
        assertNull(personAddress2Country.getComment());

        HDBTableColumn personAddress2City = personDataStructure.getColumns()
                                                               .get(7);
        assertEquals("address2.city", personAddress2City.getName());
        assertFalse(personAddress2City.isPrimaryKey());
        assertEquals("30", personAddress2City.getLength());
        assertTrue(personAddress2City.isNullable());
        assertNull(personAddress2City.getDefaultValue());
        assertNull(personAddress2City.getPrecision());
        assertNull(personAddress2City.getScale());
        assertFalse(personAddress2City.isUnique());
        assertNull(personAddress2City.getComment());

        HDBTableColumn personAddress2Zip = personDataStructure.getColumns()
                                                              .get(8);
        assertEquals("address2.zipCode", personAddress2Zip.getName());
        assertFalse(personAddress2Zip.isPrimaryKey());
        assertEquals("30", personAddress2Zip.getLength());
        assertTrue(personAddress2Zip.isNullable());
        assertNull(personAddress2Zip.getDefaultValue());
        assertNull(personAddress2Zip.getPrecision());
        assertNull(personAddress2Zip.getScale());
        assertFalse(personAddress2Zip.isUnique());
        assertNull(personAddress2Zip.getComment());

        HDBTable addressDataStructure = parsedModel.getTables()
                                                   .get(0);
        assertEquals("gstr2::ClientsWithManagedAssAndAlias.Address", addressDataStructure.getName());
        assertEquals("ADMIN", addressDataStructure.getSchema());
        assertEquals(true, addressDataStructure.isClassic());
        assertNotNull(addressDataStructure.getConstraints()
                                          .getPrimaryKey());
        assertEquals(4, addressDataStructure.getConstraints()
                                            .getPrimaryKey()
                                            .getColumns().length);
        assertEquals("id", addressDataStructure.getConstraints()
                                               .getPrimaryKey()
                                               .getColumns()[0]);
        assertEquals("country", addressDataStructure.getConstraints()
                                                    .getPrimaryKey()
                                                    .getColumns()[1]);
        assertEquals("city", addressDataStructure.getConstraints()
                                                 .getPrimaryKey()
                                                 .getColumns()[2]);
        assertEquals("zipCode", addressDataStructure.getConstraints()
                                                    .getPrimaryKey()
                                                    .getColumns()[3]);
        assertEquals("PK_gstr2::ClientsWithManagedAssAndAlias.Address", addressDataStructure.getConstraints()
                                                                                            .getPrimaryKey()
                                                                                            .getName());
        assertNull(addressDataStructure.getConstraints()
                                       .getPrimaryKey()
                                       .getModifiers());
        assertEquals(0, addressDataStructure.getConstraints()
                                            .getForeignKeys()
                                            .size());
        assertEquals(0, addressDataStructure.getConstraints()
                                            .getUniqueIndexes()
                                            .size());
        assertEquals(0, addressDataStructure.getConstraints()
                                            .getChecks()
                                            .size());
        assertEquals(4, addressDataStructure.getColumns()
                                            .size());
        HDBTableColumn addressId = addressDataStructure.getColumns()
                                                       .get(0);
        assertEquals("id", addressId.getName());
        assertTrue(addressId.isPrimaryKey());
        HDBTableColumn addressCountry = addressDataStructure.getColumns()
                                                            .get(1);
        assertEquals("country", addressCountry.getName());
        assertTrue(addressCountry.isPrimaryKey());
        HDBTableColumn addressCity = addressDataStructure.getColumns()
                                                         .get(2);
        assertEquals("city", addressCity.getName());
        assertTrue(addressCity.isPrimaryKey());
        HDBTableColumn addressZipCode = addressDataStructure.getColumns()
                                                            .get(3);
        assertEquals("zipCode", addressZipCode.getName());
        assertTrue(addressZipCode.isPrimaryKey());

        assertEquals("gstr2/ClientsWithManagedAssAndAlias.hdbdd", parsedModel.getLocation());
        assertEquals("gstr2/ClientsWithManagedAssAndAlias.hdbdd", parsedModel.getName());
        assertEquals("HDBDD", parsedModel.getType());
        assertEquals("guest", parsedModel.getCreatedBy());
        // assertEquals(0, parsedModel.getDependencies().length());
        assertEquals("ADMIN", parsedModel.getSchema());
        assertNull(parsedModel.getContent());
        assertEquals(true, parsedModel.isClassic());
    }

    /**
     * Test parse HDBDD with un managed ass.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithUnManagedAss() throws Exception {
        migrator.migrate("gstr2/ProductsWithUnManagedAss.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/ProductsWithUnManagedAss.hdbdd", "");

        assertEquals(2, parsedModel.getTables()
                                   .size());

        HDBTable orderDataStructure = parsedModel.getTables()
                                                 .get(0);
        assertEquals("gstr2::ProductsWithUnManagedAss.Orders", orderDataStructure.getName());
        assertEquals("ADMIN", orderDataStructure.getSchema());
        assertEquals(true, orderDataStructure.isClassic());
        assertNotNull(orderDataStructure.getConstraints()
                                        .getPrimaryKey());
        assertEquals(1, orderDataStructure.getConstraints()
                                          .getPrimaryKey()
                                          .getColumns().length);
        assertEquals("Id", orderDataStructure.getConstraints()
                                             .getPrimaryKey()
                                             .getColumns()[0]);
        assertEquals("PK_gstr2::ProductsWithUnManagedAss.Orders", orderDataStructure.getConstraints()
                                                                                    .getPrimaryKey()
                                                                                    .getName());
        assertNull(orderDataStructure.getConstraints()
                                     .getPrimaryKey()
                                     .getModifiers());
        assertEquals(0, orderDataStructure.getConstraints()
                                          .getForeignKeys()
                                          .size());
        assertEquals(0, orderDataStructure.getConstraints()
                                          .getUniqueIndexes()
                                          .size());
        assertEquals(0, orderDataStructure.getConstraints()
                                          .getChecks()
                                          .size());
        assertNull(orderDataStructure.getIsPublic());
        assertNull(orderDataStructure.getLoggingType());
        assertNull(orderDataStructure.getIsTemporary());
        assertNotNull(orderDataStructure.getLocation());
        assertNull(orderDataStructure.getType());
        assertNull(orderDataStructure.getTableType());
        assertNull(orderDataStructure.getDescription());

        assertEquals(2, orderDataStructure.getColumns()
                                          .size());

        HDBTableColumn id = orderDataStructure.getColumns()
                                              .get(0);
        assertEquals("Id", id.getName());
        assertTrue(id.isPrimaryKey());
        assertEquals("32", id.getLength());
        assertTrue(id.isNullable());
        assertNull(id.getDefaultValue());
        assertNull(id.getPrecision());
        assertNull(id.getScale());
        assertFalse(id.isUnique());
        assertNull(id.getComment());

        HDBTableColumn CustomerName = orderDataStructure.getColumns()
                                                        .get(1);
        assertEquals("CustomerName", CustomerName.getName());
        assertFalse(CustomerName.isPrimaryKey());
        assertEquals("500", CustomerName.getLength());
        assertTrue(CustomerName.isNullable());
        assertNull(CustomerName.getDefaultValue());
        assertNull(CustomerName.getPrecision());
        assertNull(CustomerName.getScale());
        assertFalse(CustomerName.isUnique());
        assertNull(CustomerName.getComment());

        HDBTable itemDataStructure = parsedModel.getTables()
                                                .get(1);
        assertEquals("gstr2::ProductsWithUnManagedAss.Item", itemDataStructure.getName());
        assertEquals("ADMIN", itemDataStructure.getSchema());
        assertEquals(true, itemDataStructure.isClassic());
        assertNotNull(itemDataStructure.getConstraints()
                                       .getPrimaryKey());
        assertEquals(1, itemDataStructure.getConstraints()
                                         .getPrimaryKey()
                                         .getColumns().length);
        assertEquals("ItemId", itemDataStructure.getConstraints()
                                                .getPrimaryKey()
                                                .getColumns()[0]);
        assertEquals("PK_gstr2::ProductsWithUnManagedAss.Item", itemDataStructure.getConstraints()
                                                                                 .getPrimaryKey()
                                                                                 .getName());
        assertNull(itemDataStructure.getConstraints()
                                    .getPrimaryKey()
                                    .getModifiers());

        assertEquals(1, itemDataStructure.getConstraints()
                                         .getForeignKeys()
                                         .size());
        assertEquals("gstr2::ProductsWithUnManagedAss.Orders", itemDataStructure.getConstraints()
                                                                                .getForeignKeys()
                                                                                .get(0)
                                                                                .getReferencedTable());
        assertEquals("gstr2::ProductsWithUnManagedAss.Item.OrderId", itemDataStructure.getConstraints()
                                                                                      .getForeignKeys()
                                                                                      .get(0)
                                                                                      .getName());
        assertNull(itemDataStructure.getConstraints()
                                    .getForeignKeys()
                                    .get(0)
                                    .getModifiers());
        assertEquals(1, itemDataStructure.getConstraints()
                                         .getForeignKeys()
                                         .get(0)
                                         .getReferencedColumns().length);
        assertEquals("Id", itemDataStructure.getConstraints()
                                            .getForeignKeys()
                                            .get(0)
                                            .getReferencedColumns()[0]);
        assertEquals(1, itemDataStructure.getConstraints()
                                         .getForeignKeys()
                                         .get(0)
                                         .getColumns().length);
        assertEquals("OrderId", itemDataStructure.getConstraints()
                                                 .getForeignKeys()
                                                 .get(0)
                                                 .getColumns()[0]);

        assertEquals(0, itemDataStructure.getConstraints()
                                         .getUniqueIndexes()
                                         .size());
        assertEquals(0, itemDataStructure.getConstraints()
                                         .getChecks()
                                         .size());
        assertEquals(2, itemDataStructure.getColumns()
                                         .size());
        HDBTableColumn ItemId = itemDataStructure.getColumns()
                                                 .get(0);
        assertEquals("ItemId", ItemId.getName());
        assertTrue(ItemId.isPrimaryKey());
        HDBTableColumn OrderId = itemDataStructure.getColumns()
                                                  .get(1);
        assertEquals("OrderId", OrderId.getName());
        assertFalse(OrderId.isPrimaryKey());

        assertEquals("gstr2/ProductsWithUnManagedAss.hdbdd", parsedModel.getLocation());
        assertEquals("gstr2/ProductsWithUnManagedAss.hdbdd", parsedModel.getName());
        assertEquals("HDBDD", parsedModel.getType());
        assertEquals("guest", parsedModel.getCreatedBy());
        // assertEquals(0, parsedModel.getDependencies().length());
        assertEquals("ADMIN", parsedModel.getSchema());
        assertNull(parsedModel.getContent());
        assertEquals(true, parsedModel.isClassic());
    }

    /**
     * Test parse HDBDD with no key annotation.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithNoKeyAnnotation() throws Exception {
        migrator.migrate("gstr2/NoKeyAnnSample.hdbdd");
        DataStructuresException exception = assertThrows(DataStructuresException.class,
                () -> HDBDataStructureModelFactory.parseHdbdd("gstr2/NoKeyAnnSample.hdbdd", ""));

        assertEquals(
                "com.codbex.kronos.parser.hdbdd.exception.CDSRuntimeException: Failed to parse file: gstr2/NoKeyAnnSample.hdbdd. Error at line: 10 col: 1. Annotation nokey has been specified for entity with keys.",
                exception.getMessage());
    }

    /**
     * Test parse HDBDD with generate table type annotation.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithGenerateTableTypeAnnotation() throws Exception {
        migrator.migrate("gstr2/GenerateTableTypeAnnotationSample.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/GenerateTableTypeAnnotationSample.hdbdd", "");
        assertEquals(1, parsedModel.getTableTypes()
                                   .size());

        HDBTableType tableTypeModel = parsedModel.getTableTypes()
                                                 .get(0);
        assertEquals("gstr2::GenerateTableTypeAnnotationSample.MyNestedStruct", tableTypeModel.getName());
        assertEquals("ADMIN", tableTypeModel.getSchema());
        assertEquals("name", tableTypeModel.getColumns()
                                           .get(0)
                                           .getName());
        assertEquals("NVARCHAR", tableTypeModel.getColumns()
                                               .get(0)
                                               .getType());
        assertEquals("nested.aNumber", tableTypeModel.getColumns()
                                                     .get(1)
                                                     .getName());
        assertEquals("INTEGER", tableTypeModel.getColumns()
                                              .get(1)
                                              .getType());
        assertEquals("nested.someText", tableTypeModel.getColumns()
                                                      .get(2)
                                                      .getName());
        assertEquals("NVARCHAR", tableTypeModel.getColumns()
                                               .get(2)
                                               .getType());
        assertEquals("nested.otherText", tableTypeModel.getColumns()
                                                       .get(3)
                                                       .getName());
        assertEquals("NVARCHAR", tableTypeModel.getColumns()
                                               .get(3)
                                               .getType());
    }

    /**
     * Test parse HDBDD with date time function default value.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithDateTimeFunctionDefaultValue() throws Exception {
        migrator.migrate("gstr2/DefaultValueWithDateTimeFunction.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/DefaultValueWithDateTimeFunction.hdbdd", "");
        assertEquals(1, parsedModel.getTables()
                                   .size());

        HDBTable tableModel = parsedModel.getTables()
                                         .get(0);
        assertEquals("gstr2::DefaultValueWithDateTimeFunction.Orders", tableModel.getName());
        assertEquals("DBADMIN", tableModel.getSchema());
        assertEquals("Id", tableModel.getColumns()
                                     .get(0)
                                     .getName());
        assertEquals("NVARCHAR", tableModel.getColumns()
                                           .get(0)
                                           .getType());
        assertEquals("Date", tableModel.getColumns()
                                       .get(1)
                                       .getName());
        assertEquals("TIMESTAMP", tableModel.getColumns()
                                            .get(1)
                                            .getType());
        assertTrue(tableModel.getColumns()
                             .get(1)
                             .isDefaultValueDateTimeFunction());
    }

    /**
     * Test parse HDBDD with table type annotation column.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithTableTypeAnnotationColumn() throws Exception {
        migrator.migrate("gstr2/TableTypeColumn.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/TableTypeColumn.hdbdd", "");
        assertEquals("COLUMN", parsedModel.getTables()
                                          .get(0)
                                          .getTableType());
        assertEquals("COLUMN", parsedModel.getTables()
                                          .get(1)
                                          .getTableType());
    }

    /**
     * Test parse HDBDD with table type annotation row.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithTableTypeAnnotationRow() throws Exception {
        migrator.migrate("gstr2/TableTypeRow.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/TableTypeRow.hdbdd", "");
        assertEquals("ROW", parsedModel.getTables()
                                       .get(0)
                                       .getTableType());
        assertEquals("ROW", parsedModel.getTables()
                                       .get(1)
                                       .getTableType());
    }

    /**
     * Test parse HDBDD with table type annotation global temporary.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithTableTypeAnnotationGlobalTemporary() throws Exception {
        migrator.migrate("gstr2/TableTypeGlobalTemporary.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/TableTypeGlobalTemporary.hdbdd", "");
        assertEquals("GLOBAL_TEMPORARY", parsedModel.getTables()
                                                    .get(0)
                                                    .getTableType());
    }

    /**
     * Test parse HDBDD with table type annotation global temporary column.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithTableTypeAnnotationGlobalTemporaryColumn() throws Exception {
        migrator.migrate("gstr2/TableTypeGlobalTemporaryColumn.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/TableTypeGlobalTemporaryColumn.hdbdd", "");
        assertEquals("GLOBAL_TEMPORARY_COLUMN", parsedModel.getTables()
                                                           .get(0)
                                                           .getTableType());
    }

    /**
     * When the table type is null a COLUMN table is always created
     */
    @Test
    public void testParseHDBDDWithNoTableTypeAnnotation() throws Exception {
        migrator.migrate("gstr2/NoTableType.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/NoTableType.hdbdd", "");
        String tableType = parsedModel.getTables()
                                      .get(0)
                                      .getTableType();
        assertNull(tableType, "No table type should have been defined for this HDBDD, but table type is specified");
    }

    /**
     * Test parse HDBDD with view definition simple.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithViewDefinitionSimple() throws Exception {
        String expectedRawContent = org.apache.commons.io.IOUtils.toString(
                HDBDDDataStructureParserTest.class.getResourceAsStream("/expected-results/ViewDefinitionSimple.sql"),
                StandardCharsets.UTF_8);
        migrator.migrate("gstr2/ViewDefinitionSimple.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/ViewDefinitionSimple.hdbdd", "");
        HDBView viewModel = parsedModel.getViews()
                                       .get(0);

        assertEquals(expectedRawContent, viewModel.getQuery()
                                                  .trim());
    }

    /**
     * Test parse HDBDD with view definition with join.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithViewDefinitionWithJoin() throws Exception {
        String expectedRawContent = org.apache.commons.io.IOUtils.toString(
                HDBDDDataStructureParserTest.class.getResourceAsStream("/expected-results/ViewDefinitionWithJoin.sql"),
                StandardCharsets.UTF_8);
        migrator.migrate("gstr2/ViewDefinitionWithJoin.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/ViewDefinitionWithJoin.hdbdd", "");
        HDBView viewModel = parsedModel.getViews()
                                       .get(0);

        assertEquals(expectedRawContent, viewModel.getQuery()
                                                  .trim());
    }

    /**
     * Test parse HDBDD with view definition with where.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithViewDefinitionWithWhere() throws Exception {
        String expectedRawContent = org.apache.commons.io.IOUtils.toString(
                HDBDDDataStructureParserTest.class.getResourceAsStream("/expected-results/ViewDefinitionWithWhere.sql"),
                StandardCharsets.UTF_8);
        migrator.migrate("gstr2/ViewDefinitionWithWhere.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/ViewDefinitionWithWhere.hdbdd", "");
        HDBView viewModel = parsedModel.getViews()
                                       .get(0);

        assertEquals(expectedRawContent, viewModel.getQuery()
                                                  .trim());
    }

    /**
     * Test parse HDBDD with view definition with case.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithViewDefinitionWithCase() throws Exception {
        String expectedRawContent = org.apache.commons.io.IOUtils.toString(
                HDBDDDataStructureParserTest.class.getResourceAsStream("/expected-results/ViewDefinitionWithCase.sql"),
                StandardCharsets.UTF_8);
        migrator.migrate("gstr2/ViewDefinitionWithCase.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/ViewDefinitionWithCase.hdbdd", "");
        HDBView viewModel = parsedModel.getViews()
                                       .get(0);

        assertEquals(expectedRawContent, viewModel.getQuery()
                                                  .trim());
    }

    // @Test
    // public void testParseHDBDDWithViewDefinitionWithUnion() throws Exception {
    // String expectedRawContent = org.apache.commons.io.IOUtils
    // .toString(HDBDDDataStructureParserTest.class.getResourceAsStream("/expected-results/ViewDefinitionWithUnion.sql"),
    // StandardCharsets.UTF_8);
    // migrator.migrate("gstr2/ViewDefinitionWithUnion.hdbdd");
    // HDBDD parsedModel =
    // HDBDataStructureModelFactory.parseHdbdd("gstr2/ViewDefinitionWithUnion.hdbdd", "");
    // HDBView viewModel = parsedModel.getViews().get(0);
    //
    // assertEquals(expectedRawContent, viewModel.getContent().trim());
    // }

    /**
     * Test parse HDBDD with nested view definition.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithNestedViewDefinition() throws Exception {
        String expectedRawContent = org.apache.commons.io.IOUtils.toString(
                HDBDDDataStructureParserTest.class.getResourceAsStream("/expected-results/ViewDefinitionNested.sql"),
                StandardCharsets.UTF_8);
        migrator.migrate("gstr2/ViewDefinitionNested.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/ViewDefinitionNested.hdbdd", "");
        HDBView viewModel = parsedModel.getViews()
                                       .get(0);

        assertEquals(expectedRawContent, viewModel.getQuery()
                                                  .trim());
    }

    /**
     * Test parse HDBDD with fuzzy search index.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithFuzzySearchIndex() throws Exception {
        migrator.migrate("gstr2/FuzzySearchIndexEnabled.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/FuzzySearchIndexEnabled.hdbdd", "");
        assertFalse(parsedModel.getTables()
                               .get(0)
                               .getColumns()
                               .get(1)
                               .isFuzzySearchIndex(),
                "Fuzzy search index is expected to be false, but it is true");
        assertTrue(parsedModel.getTables()
                              .get(1)
                              .getColumns()
                              .get(1)
                              .isFuzzySearchIndex(),
                "Fuzzy search index is expected to be true, but it is false");
    }

    /**
     * Test parse HDBDD with fuzzy search index new syntax.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithFuzzySearchIndexNewSyntax() throws Exception {
        migrator.migrate("gstr2/FuzzySearchIndexEnabledNewSyntax.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/FuzzySearchIndexEnabledNewSyntax.hdbdd", "");
        assertTrue(parsedModel.getTables()
                              .get(0)
                              .getColumns()
                              .get(1)
                              .isFuzzySearchIndex(),
                "Fuzzy search index (new syntax) is expected to be true, but it is false");
        assertFalse(parsedModel.getTables()
                               .get(1)
                               .getColumns()
                               .get(1)
                               .isFuzzySearchIndex(),
                "Fuzzy search index (new syntax) is expected to be false, but it is true");
    }

    /**
     * Test parse HDBDD with traverse select statement.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithTraverseSelectStatement() throws Exception {
        migrator.migrate("gstr2/TraverseSelectStatement.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/TraverseSelectStatement.hdbdd", "");
        assertEquals("gstr2::TraverseSelectStatement.category", parsedModel.getViews()
                                                                           .get(0)
                                                                           .getDependsOnTable()
                                                                           .get(0));
    }

    // @Test
    // public void testParseHDBDDWithTraverseSelectStatementDummyTable() throws Exception {
    // HDBDD parsedModel =
    // HDBDataStructureModelFactory.parseHdbdd("gstr2/TraverseSelectStatementDummyTable.hdbdd", "");
    // assertEquals("DUMMY", parsedModel).getViews().get(0).getDependsOnTable().get(0));
    // }

    /**
     * Test parse HDBDD with select distinct.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithSelectDistinct() throws Exception {
        migrator.migrate("gstr2/SelectDistinct.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/SelectDistinct.hdbdd", "");
        assertTrue(parsedModel.getViews()
                              .get(0)
                              .getQuery()
                              .contains("SELECT DISTINCT"),
                "Expected SELECT DISTINCT, but it is not found");
    }

    /**
     * Test parse HDBDD with set sql type.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithSetSqlType() throws Exception {
        migrator.migrate("gstr2/SetSqlType.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/SetSqlType.hdbdd", "");
        assertEquals("NVARCHAR", parsedModel.getTableTypes()
                                            .get(0)
                                            .getColumns()
                                            .get(0)
                                            .getType());
        assertEquals("DECIMAL", parsedModel.getTableTypes()
                                           .get(0)
                                           .getColumns()
                                           .get(1)
                                           .getType());
    }

    /**
     * Test parse HDBDD with set hana type.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithSetHanaType() throws Exception {
        migrator.migrate("gstr2/SetHanaType.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/SetHanaType.hdbdd", "");
        assertEquals("VARCHAR", parsedModel.getTableTypes()
                                           .get(0)
                                           .getColumns()
                                           .get(0)
                                           .getType());
        assertEquals("SMALLINT", parsedModel.getTableTypes()
                                            .get(0)
                                            .getColumns()
                                            .get(1)
                                            .getType());
    }

    /**
     * Test parse HDBDD with structured data type symbol.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithStructuredDataTypeSymbol() throws Exception {
        migrator.migrate("gstr2/StructuredDataTypeSymbol.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/StructuredDataTypeSymbol.hdbdd", "");
        assertEquals("gstr2::StructuredDataTypeSymbol.modifiedType", parsedModel.getTableTypes()
                                                                                .get(0)
                                                                                .getName());
        assertEquals("gstr2::StructuredDataTypeSymbol.newType", parsedModel.getTableTypes()
                                                                           .get(1)
                                                                           .getName());
    }

    /**
     * Test parse HDBDD with table types refs.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithTableTypesRefs() throws Exception {
        migrator.migrate("gstr2/TableTypesRefs.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/TableTypesRefs.hdbdd", "");
        assertEquals("gstr2::TableTypesRefs.tt_FF", parsedModel.getTableTypes()
                                                               .get(0)
                                                               .getName());
    }

    /**
     * Test parse HDBDD with unique catalog index.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithUniqueCatalogIndex() throws Exception {
        migrator.migrate("gstr2/CatalogIndexUnique.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/CatalogIndexUnique.hdbdd", "");
        boolean hasUniqueIndices = parsedModel.getTables()
                                              .get(0)
                                              .getConstraints()
                                              .getUniqueIndexes()
                                              .isEmpty();
        assertFalse(hasUniqueIndices, "Expected value for catalog unique index to be true, but it is false");
    }

    /**
     * Test parse HDBDD with no unique catalog index.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithNoUniqueCatalogIndex() throws Exception {
        migrator.migrate("gstr2/CatalogIndexNonUnique.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/CatalogIndexNonUnique.hdbdd", "");
        boolean hasNoUniqueIndices = parsedModel.getTables()
                                                .get(0)
                                                .getIndexes()
                                                .get(0)
                                                .isUnique();
        assertFalse(hasNoUniqueIndices, "Expected value for catalog unique index to be false, but it is true");
    }

    /**
     * Test parse HDBDD with calculated columns.
     *
     * @throws Exception the exception
     */
    @Test
    public void testParseHDBDDWithCalculatedColumns() throws Exception {
        migrator.migrate("gstr2/CalculatedColumns.hdbdd");
        HDBDD parsedModel = HDBDataStructureModelFactory.parseHdbdd("gstr2/CalculatedColumns.hdbdd", "");
        String expectedCalculatedColumn = "\"firstName\" || \u0027 \u0027 || \"lastName\"";
        assertEquals(expectedCalculatedColumn, parsedModel.getTables()
                                                          .get(0)
                                                          .getColumns()
                                                          .get(2)
                                                          .getStatement());
    }

}
