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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdbti.model.TableImportConfigurationDefinition;

public class CSVDefinitionsTopologicalSorter {

  private static final Logger logger = LoggerFactory.getLogger(CSVDefinitionsTopologicalSorter.class);

  public static void sort(List<TableImportConfigurationDefinition> configurationDefinitions,
      List<TableImportConfigurationDefinition> sortedConfigurationDefinitions, Connection connection) {
    Map<String, TableImportConfigurationDefinition> mappedConfigurationDefinititions = new HashMap<>();

    for (TableImportConfigurationDefinition configurationDefinition : configurationDefinitions) {
      mappedConfigurationDefinititions.put(configurationDefinition.getTable(), configurationDefinition);
    }

    Set<TableImportConfigurationDefinition> visitedConfigurationDefinitions = new HashSet<>();

    try {
      DatabaseMetaData metaData = connection.getMetaData();

      for (Entry<String, TableImportConfigurationDefinition> entry : mappedConfigurationDefinititions.entrySet()) {
        Set<TableImportConfigurationDefinition> cyclingDependencySet = new HashSet<>();
        visitConfigurationDefinition(entry.getValue(), visitedConfigurationDefinitions, sortedConfigurationDefinitions,
            metaData, mappedConfigurationDefinititions, cyclingDependencySet);
        if (!sortedConfigurationDefinitions.contains(entry.getValue())) {
          sortedConfigurationDefinitions.add(entry.getValue());
        }
      }
    } catch (SQLException exception) {
      logger.error(String.format("An error occurred while trying to get metadata. %s", exception.getMessage()), exception);
    }
  }

  private static void visitConfigurationDefinition(TableImportConfigurationDefinition configurationDefinition,
      Set<TableImportConfigurationDefinition> visitedConfigurationDefinitions,
      List<TableImportConfigurationDefinition> sortedConfigurationDefinitions, DatabaseMetaData metaData,
      Map<String, TableImportConfigurationDefinition> mappedConfigurationDefinititions,
      Set<TableImportConfigurationDefinition> cyclingDependencySet)
      throws SQLException {

    if (mappedConfigurationDefinititions.containsKey(configurationDefinition.getTable())) {
      if (!visitedConfigurationDefinitions.contains(configurationDefinition)) {
        visitedConfigurationDefinitions.add(configurationDefinition);
        if (!cyclingDependencySet.contains(configurationDefinition)) {
          cyclingDependencySet.add(configurationDefinition);
          try {
            ResultSet foreignKeys = metaData.getImportedKeys(null, configurationDefinition.getSchema(), configurationDefinition.getTable());
            while (foreignKeys.next()) {
              String pk_table = foreignKeys.getString("PKTABLE_NAME");
              TableImportConfigurationDefinition dependencyConfigDefinition = mappedConfigurationDefinititions.get(pk_table);
              if (!visitedConfigurationDefinitions.contains(dependencyConfigDefinition)) {
                visitConfigurationDefinition(dependencyConfigDefinition, visitedConfigurationDefinitions, sortedConfigurationDefinitions,
                    metaData, mappedConfigurationDefinititions, cyclingDependencySet);
                if(!sortedConfigurationDefinitions.contains(mappedConfigurationDefinititions.get(pk_table))) {
                  sortedConfigurationDefinitions.add(mappedConfigurationDefinititions.get(pk_table));
                }
              }
              if(!sortedConfigurationDefinitions.contains(configurationDefinition)){
                sortedConfigurationDefinitions.add(configurationDefinition);
              }
            }
          } catch (SQLException exception) {
            logger.error(String.format("An error occurred while trying to get metadata. %s", exception.getMessage()), exception);
          }
        }else {
          throw new SQLException(String.format("Cyclic dependency in %s ", configurationDefinition.getTable()));
        }
      }
    }
  }
}
