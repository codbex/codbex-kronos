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
import com.codbex.kronos.hdb.ds.processors.hdbstructure.HDBSynonymRemover;
import com.codbex.kronos.hdb.ds.service.manager.IDataStructureManager;
import com.codbex.kronos.hdb.ds.service.manager.EntityManagerService;
import com.codbex.kronos.hdb.ds.service.manager.HDBSequenceManagerService;
import com.codbex.kronos.hdb.ds.service.manager.ProceduresManagerService;
import com.codbex.kronos.hdb.ds.service.manager.ScalarFunctionManagerService;
import com.codbex.kronos.hdb.ds.service.manager.SchemaManagerService;
import com.codbex.kronos.hdb.ds.service.manager.SynonymManagerService;
import com.codbex.kronos.hdb.ds.service.manager.TableFunctionManagerService;
import com.codbex.kronos.hdb.ds.service.manager.TableManagerService;
import com.codbex.kronos.hdb.ds.service.manager.TableTypeManagerService;
import com.codbex.kronos.hdb.ds.service.manager.ViewManagerService;
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
        managerServices.put(IDataStructureModel.TYPE_HDBDD, new EntityManagerService());
        managerServices.put(IDataStructureModel.TYPE_HDB_TABLE, new TableManagerService());
        managerServices.put(IDataStructureModel.TYPE_HDB_VIEW, new ViewManagerService());
        SynonymManagerService synonymManagerService = new SynonymManagerService();
        managerServices.put(IDataStructureModel.TYPE_HDB_SYNONYM, synonymManagerService);
        managerServices.put(IDataStructureModel.TYPE_HDB_TABLE_FUNCTION, new TableFunctionManagerService());
        managerServices.put(IDataStructureModel.TYPE_HDB_SCHEMA, new SchemaManagerService());
        managerServices.put(IDataStructureModel.TYPE_HDB_PROCEDURE, new ProceduresManagerService());
        managerServices.put(IDataStructureModel.TYPE_HDB_SEQUENCE, new HDBSequenceManagerService());
        managerServices.put(IDataStructureModel.TYPE_HDB_SCALAR_FUNCTION, new ScalarFunctionManagerService());
        managerServices.put(IDataStructureModel.TYPE_HDB_TABLE_TYPE, new TableTypeManagerService(new HDBSynonymRemover(synonymManagerService)));
    }
}
