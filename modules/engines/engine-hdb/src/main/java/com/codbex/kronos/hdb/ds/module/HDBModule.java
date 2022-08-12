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
package com.codbex.kronos.hdb.ds.module;

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.artefacts.HDBProcedureSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableFunctionSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.parser.hdbdd.HDBDDParser;
import com.codbex.kronos.hdb.ds.parser.hdbprocedure.HDBProcedureLogger;
import com.codbex.kronos.hdb.ds.parser.hdbprocedure.HDBProcedureParser;
import com.codbex.kronos.hdb.ds.parser.hdbscalarfunction.HDBScalarFunctionParser;
import com.codbex.kronos.hdb.ds.parser.hdbschema.HDBSchemaParser;
import com.codbex.kronos.hdb.ds.parser.hdbsequence.HDBSequenceParser;
import com.codbex.kronos.hdb.ds.parser.hdbstructure.HDBStructureParser;
import com.codbex.kronos.hdb.ds.parser.hdbsynonym.HDBSynonymParser;
import com.codbex.kronos.hdb.ds.parser.hdbtable.HDBTableParser;
import com.codbex.kronos.hdb.ds.parser.hdbtablefunction.HDBTableFunctionLogger;
import com.codbex.kronos.hdb.ds.parser.hdbtablefunction.HDBTableFunctionParser;
import com.codbex.kronos.hdb.ds.parser.hdbtabletype.HDBTableTypeParser;
import com.codbex.kronos.hdb.ds.parser.hdbview.HDBViewParser;
import com.codbex.kronos.utils.HDBSynonymRemover;
import com.codbex.kronos.hdb.ds.service.manager.HDBStructureManagerService;
import com.codbex.kronos.hdb.ds.service.manager.IDataStructureManager;
import com.codbex.kronos.hdb.ds.service.manager.HDBDDManagerService;
import com.codbex.kronos.hdb.ds.service.manager.HDBSequenceManagerService;
import com.codbex.kronos.hdb.ds.service.manager.HDBProceduresManagerService;
import com.codbex.kronos.hdb.ds.service.manager.HDBScalarFunctionManagerService;
import com.codbex.kronos.hdb.ds.service.manager.HDBSchemaManagerService;
import com.codbex.kronos.hdb.ds.service.manager.HDBSynonymManagerService;
import com.codbex.kronos.hdb.ds.service.manager.HDBTableFunctionManagerService;
import com.codbex.kronos.hdb.ds.service.manager.HDBTableManagerService;
import com.codbex.kronos.hdb.ds.service.manager.HDBTableTypeManagerService;
import com.codbex.kronos.hdb.ds.service.manager.HDBViewManagerService;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dirigible.commons.api.module.AbstractDirigibleModule;

/**
 * The Class HDBModule.
 */
public class HDBModule extends AbstractDirigibleModule {

  /** The manager services. */
  private static Map<String, IDataStructureManager> managerServices;

  /** The parser services. */
  private static Map<String, DataStructureParser> parserServices;

  /** The parser types. */
  private static Map<String, String> parserTypes;

  // Do not initialize classes that may use the database or other Dirigible modules as the static initializer is called too early

  /**
   * Gets the manager services.
   *
   * @return the manager services
   */
  public static synchronized Map<String, IDataStructureManager> getManagerServices() {
    if (managerServices == null) {
      managerServices = new HashMap<String, IDataStructureManager>();
      bindManagerServicesToFileExtensions(managerServices);
    }
    return managerServices;
  }

  /**
   * Gets the parser services.
   *
   * @return the parser services
   */
  public static synchronized Map<String, DataStructureParser> getParserServices() {
    if (parserServices == null) {
      parserServices = new HashMap<String, DataStructureParser>();
      bindParsersToFileExtension(parserServices);
    }
    return parserServices;
  }

  /**
   * Gets the parser types.
   *
   * @return the parser types
   */
  public static synchronized Map<String, String> getParserTypes() {
    if (parserTypes == null) {
      parserTypes = new HashMap<String, String>();
      bindParserTypeToFileExtension(parserTypes);
    }
    return parserTypes;
  }

  /**
   * Configure.
   */
  @Override
  public void configure() {
  }

  /**
   * Bind manager services to file extensions.
   *
   * @param managerServices the manager services
   */
  private static void bindManagerServicesToFileExtensions(Map<String, IDataStructureManager> managerServices) {
    HDBSynonymManagerService synonymManagerService = new HDBSynonymManagerService();

    managerServices.put(IDataStructureModel.TYPE_HDBDD, new HDBDDManagerService());
    managerServices.put(IDataStructureModel.TYPE_HDB_TABLE, new HDBTableManagerService());
    managerServices.put(IDataStructureModel.TYPE_HDB_VIEW, new HDBViewManagerService());
    managerServices.put(IDataStructureModel.TYPE_HDB_SYNONYM, synonymManagerService);
    managerServices.put(IDataStructureModel.TYPE_HDB_TABLE_FUNCTION, new HDBTableFunctionManagerService());
    managerServices.put(IDataStructureModel.TYPE_HDB_SCHEMA, new HDBSchemaManagerService());
    managerServices.put(IDataStructureModel.TYPE_HDB_PROCEDURE, new HDBProceduresManagerService());
    managerServices.put(IDataStructureModel.TYPE_HDB_SEQUENCE, new HDBSequenceManagerService());
    managerServices.put(IDataStructureModel.TYPE_HDB_SCALAR_FUNCTION, new HDBScalarFunctionManagerService());
    managerServices.put(IDataStructureModel.TYPE_HDB_TABLE_TYPE, new HDBTableTypeManagerService(new HDBSynonymRemover(synonymManagerService)));
    managerServices.put(IDataStructureModel.TYPE_HDB_STRUCTURE, new HDBStructureManagerService(new HDBSynonymRemover(synonymManagerService)));
  }

  /**
   * Bind parsers to file extension.
   *
   * @param parserServices the parser services
   */
  private static void bindParsersToFileExtension(Map<String, DataStructureParser> parserServices) {
    DataStructuresSynchronizer dataStructuresSynchronizer = new DataStructuresSynchronizer();

    HDBTableFunctionParser hdbTableFunctionParser = new HDBTableFunctionParser(
        dataStructuresSynchronizer,
        new HDBTableFunctionSynchronizationArtefactType(),
        new HDBTableFunctionLogger()
    );

    HDBProcedureParser hdbProcedureParser = new HDBProcedureParser(
            dataStructuresSynchronizer,
            new HDBProcedureSynchronizationArtefactType(),
            new HDBProcedureLogger()
    );

    parserServices.put(IDataStructureModel.FILE_EXTENSION_HDBDD, new HDBDDParser());
    parserServices.put(IDataStructureModel.FILE_EXTENSION_HDB_TABLE, new HDBTableParser());
    parserServices.put(IDataStructureModel.FILE_EXTENSION_HDB_VIEW, new HDBViewParser());
    parserServices.put(IDataStructureModel.FILE_EXTENSION_HDB_SYNONYM, new HDBSynonymParser());
    parserServices.put(IDataStructureModel.FILE_EXTENSION_HDB_TABLE_FUNCTION, hdbTableFunctionParser);
    parserServices.put(IDataStructureModel.FILE_EXTENSION_HDB_SCHEMA, new HDBSchemaParser());
    parserServices.put(IDataStructureModel.FILE_EXTENSION_HDB_PROCEDURE, hdbProcedureParser);
    parserServices.put(IDataStructureModel.FILE_EXTENSION_HDB_SEQUENCE, new HDBSequenceParser());
    parserServices.put(IDataStructureModel.FILE_EXTENSION_HDB_SCALAR_FUNCTION, new HDBScalarFunctionParser());
    parserServices.put(IDataStructureModel.FILE_EXTENSION_HDB_STRUCTURE, new HDBStructureParser());
    parserServices.put(IDataStructureModel.FILE_EXTENSION_HDB_TABLE_TYPE, new HDBTableTypeParser());

    parserServices.put(IDataStructureModel.TYPE_HDBDD, new HDBDDParser());
    parserServices.put(IDataStructureModel.TYPE_HDB_TABLE, new HDBTableParser());
    parserServices.put(IDataStructureModel.TYPE_HDB_VIEW, new HDBViewParser());
    parserServices.put(IDataStructureModel.TYPE_HDB_SYNONYM, new HDBSynonymParser());
    parserServices.put(IDataStructureModel.TYPE_HDB_TABLE_FUNCTION, hdbTableFunctionParser);
    parserServices.put(IDataStructureModel.TYPE_HDB_SCALAR_FUNCTION, new HDBScalarFunctionParser());
    parserServices.put(IDataStructureModel.TYPE_HDB_SCHEMA, new HDBSchemaParser());
    parserServices.put(IDataStructureModel.TYPE_HDB_PROCEDURE, hdbProcedureParser);
    parserServices.put(IDataStructureModel.TYPE_HDB_SEQUENCE, new HDBSequenceParser());
    parserServices.put(IDataStructureModel.TYPE_HDB_TABLE_TYPE, new HDBTableTypeParser());
    parserServices.put(IDataStructureModel.TYPE_HDB_STRUCTURE, new HDBStructureParser());

  }

  /**
   * Bind parser type to file extension.
   *
   * @param parserTypes the parser types
   */
  private static void bindParserTypeToFileExtension(Map<String, String> parserTypes) {
    parserTypes.put(IDataStructureModel.FILE_EXTENSION_HDBDD, IDataStructureModel.TYPE_HDBDD);
    parserTypes.put(IDataStructureModel.FILE_EXTENSION_HDB_TABLE, IDataStructureModel.TYPE_HDB_TABLE);
    parserTypes.put(IDataStructureModel.FILE_EXTENSION_HDB_VIEW, IDataStructureModel.TYPE_HDB_VIEW);
    parserTypes.put(IDataStructureModel.FILE_EXTENSION_HDB_SYNONYM, IDataStructureModel.TYPE_HDB_SYNONYM);
    parserTypes.put(IDataStructureModel.FILE_EXTENSION_HDB_TABLE_FUNCTION, IDataStructureModel.TYPE_HDB_TABLE_FUNCTION);
    parserTypes.put(IDataStructureModel.FILE_EXTENSION_HDB_SCHEMA, IDataStructureModel.TYPE_HDB_SCHEMA);
    parserTypes.put(IDataStructureModel.FILE_EXTENSION_HDB_PROCEDURE, IDataStructureModel.TYPE_HDB_PROCEDURE);
    parserTypes.put(IDataStructureModel.FILE_EXTENSION_HDB_SEQUENCE, IDataStructureModel.TYPE_HDB_SEQUENCE);
    parserTypes.put(IDataStructureModel.FILE_EXTENSION_HDB_SCALAR_FUNCTION, IDataStructureModel.TYPE_HDB_SCALAR_FUNCTION);
    parserTypes.put(IDataStructureModel.FILE_EXTENSION_HDB_STRUCTURE, IDataStructureModel.TYPE_HDB_STRUCTURE);
    parserTypes.put(IDataStructureModel.FILE_EXTENSION_HDB_TABLE_TYPE, IDataStructureModel.TYPE_HDB_TABLE_TYPE);
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return "Kronos HDB Module";
  }
}
