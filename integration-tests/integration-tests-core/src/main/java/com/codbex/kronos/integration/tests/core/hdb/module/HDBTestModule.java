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
package com.codbex.kronos.integration.tests.core.hdb.module;

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.module.HDBModule;
import com.codbex.kronos.hdb.ds.service.manager.HDBStructureManagerService;
import com.codbex.kronos.utils.HDBSynonymRemover;
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
import com.codbex.kronos.integration.tests.core.hdb.model.JDBCModel;
import com.codbex.kronos.integration.tests.core.hdb.repository.TestRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import javax.sql.DataSource;
import org.eclipse.dirigible.commons.api.module.AbstractDirigibleModule;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.api.IDatabase;
import org.eclipse.dirigible.database.custom.CustomDatabase;
import org.eclipse.dirigible.database.h2.H2Database;
import org.eclipse.dirigible.repository.api.RepositoryPath;
import org.eclipse.dirigible.repository.fs.FileSystemRepository;
import org.eclipse.dirigible.repository.local.LocalRepository;
import org.eclipse.dirigible.repository.local.LocalResource;

public class HDBTestModule extends AbstractDirigibleModule {

  private JDBCModel model;

  public HDBTestModule(JDBCModel model) {
    this.model = model;
  }

  public DataSource getDataSource() {
    Configuration.set(IDatabase.DIRIGIBLE_DATABASE_PROVIDER, "custom");
    Configuration.set(IDatabase.DIRIGIBLE_DATABASE_CUSTOM_DATASOURCES, "HANA");
    Configuration.set(IDatabase.DIRIGIBLE_DATABASE_DATASOURCE_NAME_DEFAULT, "HANA");
    Configuration.set(IDatabase.DIRIGIBLE_DATABASE_DEFAULT_MAX_CONNECTIONS_COUNT, "32");
    Configuration.set("DIRIGIBLE_DATABASE_NAMES_CASE_SENSITIVE", "true");
    Configuration.set(IDatabase.DIRIGIBLE_DATABASE_DEFAULT_SET_AUTO_COMMIT, "true");

    Configuration.set("HANA_URL", model.getJdbcUrl());
    Configuration.set("HANA_DRIVER", model.getDriverClassName());
    Configuration.set("HANA_USERNAME", model.getUsername());
    Configuration.set("HANA_PASSWORD", model.getPassword());

    CustomDatabase customDatabase = new CustomDatabase();
    return customDatabase.getDataSource();
  }

  public DataSource getSystemDataSource() {
    H2Database h2Database = new H2Database();
    return h2Database.getDataSource();
  }

  public static LocalResource getResources(String rootFolder, String repoPath, String relativeResourcePath) throws IOException {
    FileSystemRepository fileRepo = new LocalRepository(rootFolder);
    RepositoryPath path = new RepositoryPath(repoPath);
    byte[] content = HDBTestModule.class.getResourceAsStream(relativeResourcePath).readAllBytes();

    LocalResource resource = new LocalResource(fileRepo, path);
    resource.setContent(content);
    return resource;
  }

  public static LocalResource getResourceFromString(String rootFolder, String repoPath, String fileContent) throws IOException {
    FileSystemRepository fileRepo = new LocalRepository(rootFolder);
    RepositoryPath path = new RepositoryPath(repoPath);
    byte[] content = fileContent.getBytes(StandardCharsets.UTF_8);
    LocalResource resource = new LocalResource(fileRepo, path);
    resource.setContent(content);
    return resource;
  }

  @Override
  public String getName() {
    return "HDB Test Module";
  }

  @Override
  public void configure() {
    StaticObjects.set(StaticObjects.DATASOURCE, getDataSource());
    StaticObjects.set(StaticObjects.SYSTEM_DATASOURCE, getSystemDataSource());
    StaticObjects.set(StaticObjects.REPOSITORY, new TestRepository());

    // when we run all integration tests at once, the first run test will determine
    // the datasource of DataStructuresCoreService.
    // this can lead to issue, for example running MYSQL test which is accessing the
    // HANA db, and leading to inconsistent test results
    // that is why we are clearing and reentering the managerServices
    // see:HDBModule.bindManagerServicesToFileExtensions()

    Map<String, IDataStructureManager> managerServices = HDBModule.getManagerServices();
    managerServices.clear();

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
}
