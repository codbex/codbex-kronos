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
package com.codbex.kronos.hdbti.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.eclipse.dirigible.api.v3.core.ConsoleFacade;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.PersistenceManager;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdbti.api.IImportedCSVRecordDao;
import com.codbex.kronos.hdbti.model.ImportedCSVRecordModel;
import com.codbex.kronos.hdbti.processors.HDBTIProcessor;

/**
 * The Class ImportedCSVRecordDao.
 */
public class ImportedCSVRecordDao implements IImportedCSVRecordDao {
    
    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(HDBTIProcessor.class);

    /** The data source. */
    private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);

    /** The persistence manager. */
    private PersistenceManager<ImportedCSVRecordModel> persistenceManager = new PersistenceManager<ImportedCSVRecordModel>();

    /**
     * Save.
     *
     * @param importedRowModel the imported row model
     * @return the imported CSV record model
     * @throws DataStructuresException the data structures exception
     */
    @Override
    public ImportedCSVRecordModel save(ImportedCSVRecordModel importedRowModel) throws DataStructuresException {
        try (Connection connection = dataSource.getConnection()) {
            importedRowModel.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
            persistenceManager.insert(connection, importedRowModel);
            ConsoleFacade.log("Entity with rowId: "+importedRowModel.getRowId()+" and tableName: "+importedRowModel.getTableName()+" was SAVED successfully in KRONOS_IMPORTED_CSV_RECORDS");
            return importedRowModel;
        } catch (SQLException e) {
            throw new DataStructuresException(e);
        }


    }

    /**
     * Update.
     *
     * @param importedRowModel the imported row model
     * @return the imported CSV record model
     * @throws SQLException the SQL exception
     */
    @Override
    public ImportedCSVRecordModel update(ImportedCSVRecordModel importedRowModel) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            persistenceManager.update(connection, importedRowModel);
            ConsoleFacade.log("Entity with rowId: "+importedRowModel.getRowId()+" and tableName: "+importedRowModel.getTableName()+ " was UPDATED successfully in KRONOS_IMPORTED_CSV_RECORDS.");
            return importedRowModel;
        }
    }


    /**
     * Delete all.
     *
     * @param importedCSVRecordModels the imported CSV record models
     * @throws SQLException the SQL exception
     */
    @Override
    public void deleteAll(List<ImportedCSVRecordModel> importedCSVRecordModels) throws SQLException {
        if (importedCSVRecordModels.isEmpty()) {
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            importedCSVRecordModels.forEach(record -> {
                persistenceManager.delete(connection, ImportedCSVRecordModel.class, record.getId());
            });

            List<String> ids = importedCSVRecordModels.stream().map(record -> record.getId().toString()).collect(Collectors.toList());
            ConsoleFacade.log("Entities with ids: "+ String.join(", ", ids)+ " were DELETED successfully in KRONOS_IMPORTED_CSV_RECORDS.");
        }

    }

    /**
     * Gets the imported CSV records by table and CSV location.
     *
     * @param tableName the table name
     * @param csvLocation the csv location
     * @return the imported CSV records by table and CSV location
     * @throws DataStructuresException the data structures exception
     */
    @Override
    public List<ImportedCSVRecordModel> getImportedCSVRecordsByTableAndCSVLocation(String tableName, String csvLocation) throws DataStructuresException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = SqlFactory.getNative(connection).select().column("*").from("KRONOS_IMPORTED_CSV_RECORDS")
                    .where("TABLE_NAME = ? AND CSV_LOCATION = ?").toString();
            return persistenceManager.query(connection, ImportedCSVRecordModel.class, sql, tableName, csvLocation);
        } catch (SQLException e) {
            throw new DataStructuresException(e);
        }
    }

    /**
     * Gets the imported CS vs by hdbti location.
     *
     * @param hdbtiLocation the hdbti location
     * @return the imported CS vs by hdbti location
     */
    @Override
    public List<ImportedCSVRecordModel> getImportedCSVsByHdbtiLocation(String hdbtiLocation) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = SqlFactory.getNative(connection).select().column("*").from("KRONOS_IMPORTED_CSV_RECORDS")
                    .where("HDBTI_LOCATION = ?").toString();
            return persistenceManager.query(connection, ImportedCSVRecordModel.class, sql, hdbtiLocation);
        } catch (SQLException e) {
            logger.error("Error occurred while trying to retrieve imported csv records by HDBTI file location");
        }

        return new ArrayList<>();
    }

    /**
     * Gets the data source.
     *
     * @return the data source
     */
    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    /**
     * Gets the persistence manager.
     *
     * @return the persistence manager
     */
    @Override
    public PersistenceManager<ImportedCSVRecordModel> getPersistenceManager() {
        return persistenceManager;
    }
}
