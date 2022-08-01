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

import com.codbex.kronos.hdb.ds.api.HDBDataStructureModel;
import com.codbex.kronos.hdb.ds.artefacts.HDBProcedureSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableFunctionSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.parser.hdbdd.HDBDDParser;
import com.codbex.kronos.hdb.ds.parser.hdbprocedure.HDBProcedureLogger;
import com.codbex.kronos.hdb.ds.parser.hdbprocedure.HDBProcedureParser;
import com.codbex.kronos.hdb.ds.parser.hdbscalarfunction.HDBScalarFunctionParser;
import com.codbex.kronos.hdb.ds.parser.hdbschema.SchemaParser;
import com.codbex.kronos.hdb.ds.parser.hdbsequence.HDBSequenceParser;
import com.codbex.kronos.hdb.ds.parser.hdbsynonym.SynonymParser;
import com.codbex.kronos.hdb.ds.parser.hdbtable.TableParser;
import com.codbex.kronos.hdb.ds.parser.hdbtablefunction.HDBTableFunctionLogger;
import com.codbex.kronos.hdb.ds.parser.hdbtablefunction.HDBTableFunctionParser;
import com.codbex.kronos.hdb.ds.parser.hdbtabletype.TableTypeParser;
import com.codbex.kronos.hdb.ds.parser.hdbview.ViewParser;
import com.codbex.kronos.hdb.ds.processors.hdbstructure.HDBSynonymRemover;
import com.codbex.kronos.hdb.ds.service.manager.IDataStructureManager;
import com.codbex.kronos.hdb.ds.service.manager.EntityManagerService;
import com.codbex.kronos.hdb.ds.service.manager.HDBSequenceManagerService;
import com.codbex.kronos.hdb.ds.service.manager.ProceduresManagerService;
import com.codbex.kronos.hdb.ds.service.manager.ScalarFunctionManagerService;
import com.codbex.kronos.hdb.ds.service.manager.SchemaManagerService;
import com.codbex.kronos.hdb.ds.service.manager.TableFunctionManagerService;
import com.codbex.kronos.hdb.ds.service.manager.TableManagerService;
import com.codbex.kronos.hdb.ds.service.manager.TableTypeManagerService;
import com.codbex.kronos.hdb.ds.service.manager.ViewManagerService;
import com.codbex.kronos.hdb.ds.service.manager.SynonymManagerService;
import java.util.HashMap;
import java.util.Map;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import org.eclipse.dirigible.commons.api.module.AbstractDirigibleModule;

public class HDBModule extends AbstractDirigibleModule {

  private static Map<String, IDataStructureManager> managerServices;

  private static Map<String, DataStructureParser> parserServices;

  private static Map<String, String> parserTypes;

  // Do not initialize classes that may use the database or other Dirigible modules as the static initializer is called too early

  public static synchronized Map<String, IDataStructureManager> getManagerServices() {
    if (managerServices == null) {
      managerServices = new HashMap<String, IDataStructureManager>();
      bindManagerServicesToFileExtensions(managerServices);
    }
    return managerServices;
  }

  public static synchronized Map<String, DataStructureParser> getParserServices() {
    if (parserServices == null) {
      parserServices = new HashMap<String, DataStructureParser>();
      bindParsersToFileExtension(parserServices);
    }
    return parserServices;
  }

  public static synchronized Map<String, String> getParserTypes() {
    if (parserTypes == null) {
      parserTypes = new HashMap<String, String>();
      bindParserTypeToFileExtension(parserTypes);
    }
    return parserTypes;
  }

  @Override
  public void configure() {
  }

  private static void bindManagerServicesToFileExtensions(Map<String, IDataStructureManager> managerServices) {
    managerServices.put(HDBDataStructureModel.TYPE_HDBDD, new EntityManagerService());
    managerServices.put(HDBDataStructureModel.TYPE_HDB_TABLE, new TableManagerService());
    managerServices.put(HDBDataStructureModel.TYPE_HDB_VIEW, new ViewManagerService());
    SynonymManagerService synonymManagerService = new SynonymManagerService();
    managerServices.put(HDBDataStructureModel.TYPE_HDB_SYNONYM, synonymManagerService);
    managerServices.put(HDBDataStructureModel.TYPE_HDB_TABLE_FUNCTION, new TableFunctionManagerService());
    managerServices.put(HDBDataStructureModel.TYPE_HDB_SCHEMA, new SchemaManagerService());
    managerServices.put(HDBDataStructureModel.TYPE_HDB_PROCEDURE, new ProceduresManagerService());
    managerServices.put(HDBDataStructureModel.TYPE_HDB_SEQUENCE, new HDBSequenceManagerService());
    managerServices.put(HDBDataStructureModel.TYPE_HDB_SCALAR_FUNCTION, new ScalarFunctionManagerService());
    managerServices.put(HDBDataStructureModel.TYPE_HDB_TABLE_TYPE, new TableTypeManagerService(new HDBSynonymRemover(synonymManagerService)));
  }

  private static void bindParsersToFileExtension(Map<String, DataStructureParser> parserServices) {
    DataStructuresSynchronizer dataStructuresSynchronizer = new DataStructuresSynchronizer();
    HDBTableFunctionParser tableFunctionParser = new HDBTableFunctionParser(
        dataStructuresSynchronizer,
        new HDBTableFunctionSynchronizationArtefactType(),
        new HDBTableFunctionLogger()
    );

    HDBProcedureParser hdbProcedureParser = new HDBProcedureParser(
            dataStructuresSynchronizer,
            new HDBProcedureSynchronizationArtefactType(),
            new HDBProcedureLogger()
    );

    parserServices.put(HDBDataStructureModel.FILE_EXTENSION_ENTITIES, new HDBDDParser());
    parserServices.put(HDBDataStructureModel.FILE_EXTENSION_TABLE, new TableParser());
    parserServices.put(HDBDataStructureModel.FILE_EXTENSION_VIEW, new ViewParser());
    parserServices.put(HDBDataStructureModel.FILE_EXTENSION_SYNONYM, new SynonymParser());
    parserServices.put(HDBDataStructureModel.FILE_EXTENSION_HDBTABLEFUNCTION, tableFunctionParser);
    parserServices.put(HDBDataStructureModel.FILE_EXTENSION_HDBSCHEMA, new SchemaParser());
    parserServices.put(HDBDataStructureModel.FILE_EXTENSION_HDBPROCEDURE, hdbProcedureParser);
    parserServices.put(HDBDataStructureModel.FILE_EXTENSION_HDBSEQUENCE, new HDBSequenceParser());
    parserServices.put(HDBDataStructureModel.FILE_EXTENSION_HDBSCALARFUNCTION, new HDBScalarFunctionParser());
    parserServices.put(HDBDataStructureModel.FILE_EXTENSION_STRUCTURE, new TableTypeParser());
    parserServices.put(HDBDataStructureModel.FILE_EXTENSION_HDB_TABLE_TYPE, new TableTypeParser());

    parserServices.put(HDBDataStructureModel.TYPE_HDBDD, new HDBDDParser());
    parserServices.put(HDBDataStructureModel.TYPE_HDB_TABLE, new TableParser());
    parserServices.put(HDBDataStructureModel.TYPE_HDB_VIEW, new ViewParser());
    parserServices.put(HDBDataStructureModel.TYPE_HDB_SYNONYM, new SynonymParser());
    parserServices.put(HDBDataStructureModel.TYPE_HDB_TABLE_FUNCTION, tableFunctionParser);
    parserServices.put(HDBDataStructureModel.TYPE_HDB_SCHEMA, new SchemaParser());
    parserServices.put(HDBDataStructureModel.TYPE_HDB_PROCEDURE, hdbProcedureParser);
    parserServices.put(HDBDataStructureModel.TYPE_HDB_SEQUENCE, new HDBSequenceParser());
    parserServices.put(HDBDataStructureModel.TYPE_HDB_SCALAR_FUNCTION, new HDBScalarFunctionParser());
    parserServices.put(HDBDataStructureModel.TYPE_HDB_TABLE_TYPE, new TableTypeParser());
  }

  private static void bindParserTypeToFileExtension(Map<String, String> parserTypes) {
    parserTypes.put(HDBDataStructureModel.FILE_EXTENSION_ENTITIES, HDBDataStructureModel.TYPE_HDBDD);
    parserTypes.put(HDBDataStructureModel.FILE_EXTENSION_TABLE, HDBDataStructureModel.TYPE_HDB_TABLE);
    parserTypes.put(HDBDataStructureModel.FILE_EXTENSION_VIEW, HDBDataStructureModel.TYPE_HDB_VIEW);
    parserTypes.put(HDBDataStructureModel.FILE_EXTENSION_SYNONYM, HDBDataStructureModel.TYPE_HDB_SYNONYM);
    parserTypes.put(HDBDataStructureModel.FILE_EXTENSION_HDBTABLEFUNCTION, HDBDataStructureModel.TYPE_HDB_TABLE_FUNCTION);
    parserTypes.put(HDBDataStructureModel.FILE_EXTENSION_HDBSCHEMA, HDBDataStructureModel.TYPE_HDB_SCHEMA);
    parserTypes.put(HDBDataStructureModel.FILE_EXTENSION_HDBPROCEDURE, HDBDataStructureModel.TYPE_HDB_PROCEDURE);
    parserTypes.put(HDBDataStructureModel.FILE_EXTENSION_HDBSEQUENCE, HDBDataStructureModel.TYPE_HDB_SEQUENCE);
    parserTypes.put(HDBDataStructureModel.FILE_EXTENSION_HDBSCALARFUNCTION, HDBDataStructureModel.TYPE_HDB_SCALAR_FUNCTION);
    parserTypes.put(HDBDataStructureModel.FILE_EXTENSION_STRUCTURE, HDBDataStructureModel.TYPE_HDB_TABLE_TYPE);
  }

  @Override
  public String getName() {
    return "Kronos HDB Module";
  }
}
