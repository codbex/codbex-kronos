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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.codbex.kronos.hdbti.model.TableImportConfigurationDefinition;

public class CSVDefinitionsTopologicalSorterTest {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private Connection mockConnection;

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private DatabaseMetaData mockDatabaseMetadata;

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private ResultSet mockResultSet;

  @Before
  public void openMocks() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testSort() throws SQLException {

    List<TableImportConfigurationDefinition> configurationDefinitions = new ArrayList<>();
    Map<String, TableImportConfigurationDefinition> mappedConfigurationDefinitions = new HashMap<String, TableImportConfigurationDefinition>();

    TableImportConfigurationDefinition ordersTableConfigurationDefinition = new TableImportConfigurationDefinition();
    ordersTableConfigurationDefinition.setTable("products::Orders");
    ordersTableConfigurationDefinition.setFile("Orders.csv");
    ordersTableConfigurationDefinition.setSchema("DBADMIN");

    configurationDefinitions.add(ordersTableConfigurationDefinition);
    mappedConfigurationDefinitions.put("products::Orders", ordersTableConfigurationDefinition);

    TableImportConfigurationDefinition countryTableConfigurationDefinition = new TableImportConfigurationDefinition();
    countryTableConfigurationDefinition.setTable("products::Countries");
    countryTableConfigurationDefinition.setFile("Countries.csv");
    countryTableConfigurationDefinition.setSchema("DBADMIN");

    configurationDefinitions.add(countryTableConfigurationDefinition);
    mappedConfigurationDefinitions.put("products::Countries", countryTableConfigurationDefinition);

    TableImportConfigurationDefinition itemTableConfigurationDefinition = new TableImportConfigurationDefinition();
    itemTableConfigurationDefinition.setTable("products::Items");
    itemTableConfigurationDefinition.setFile("Items.csv");
    itemTableConfigurationDefinition.setSchema("DBADMIN");

    configurationDefinitions.add(itemTableConfigurationDefinition);
    mappedConfigurationDefinitions.put("products::Items", itemTableConfigurationDefinition);

    for (Entry<String, TableImportConfigurationDefinition> entry : mappedConfigurationDefinitions.entrySet()) {
      System.out.println(entry.getKey());
    }

    List<TableImportConfigurationDefinition> sortedConfigurationDefinitions = new ArrayList<>();

    when(mockConnection.getMetaData()).thenReturn(mockDatabaseMetadata);
    when(mockDatabaseMetadata.getImportedKeys(null,"DBADMIN","products::Orders")).thenReturn(mockResultSet);
    when(mockResultSet.next()).thenReturn(true,false,false);
    when(mockResultSet.getString("PKTABLE_NAME")).thenReturn("products::Countries");
    when(mockDatabaseMetadata.getImportedKeys(null,"DBADMIN","products::Countries")).thenReturn(mockResultSet);
    when(mockDatabaseMetadata.getImportedKeys(null,"DBADMIN","products::Items")).thenReturn(mockResultSet);

    CSVDefinitionsTopologicalSorter.sort(configurationDefinitions,sortedConfigurationDefinitions,mockConnection);

    for (TableImportConfigurationDefinition configurationDefinition : sortedConfigurationDefinitions) {
      System.out.println(configurationDefinition.getTable());
    }

    assertEquals(sortedConfigurationDefinitions.get(0).getTable(), "products::Countries");
    assertEquals(sortedConfigurationDefinitions.get(1).getTable(), "products::Orders");
    assertEquals(sortedConfigurationDefinitions.get(2).getTable(), "products::Items");
  }
}
