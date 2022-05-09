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

import static java.lang.String.format;

import com.codbex.kronos.hdb.ds.api.XSKDataStructuresException;
import com.codbex.kronos.hdbti.api.IXSKImportedCSVRecordDao;
import com.codbex.kronos.hdbti.model.XSKImportedCSVRecordModel;
import com.codbex.kronos.hdbti.processors.XSKHDBTIProcessor;

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

public class XSKImportedCSVRecordDao implements IXSKImportedCSVRecordDao {
    private static final Logger logger = LoggerFactory.getLogger(XSKHDBTIProcessor.class);

    private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);

    private PersistenceManager<XSKImportedCSVRecordModel> persistenceManager = new PersistenceManager<XSKImportedCSVRecordModel>();

    @Override
    public XSKImportedCSVRecordModel save(XSKImportedCSVRecordModel importedRowModel) throws XSKDataStructuresException {
        try (Connection connection = dataSource.getConnection()) {
            importedRowModel.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
            persistenceManager.insert(connection, importedRowModel);
            ConsoleFacade.log("Entity with rowId: "+importedRowModel.getRowId()+" and tableName: "+importedRowModel.getTableName()+" was SAVED successfully in XSK_IMPORTED_CSV_RECORDS");
            return importedRowModel;
        } catch (SQLException e) {
            throw new XSKDataStructuresException(e);
        }


    }

    @Override
    public XSKImportedCSVRecordModel update(XSKImportedCSVRecordModel importedRowModel) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            persistenceManager.update(connection, importedRowModel);
            ConsoleFacade.log("Entity with rowId: "+importedRowModel.getRowId()+" and tableName: "+importedRowModel.getTableName()+ " was UPDATED successfully in XSK_IMPORTED_CSV_RECORDS.");
            return importedRowModel;
        }
    }


    @Override
    public void deleteAll(List<XSKImportedCSVRecordModel> importedCSVRecordModels) throws SQLException {
        if (importedCSVRecordModels.isEmpty()) {
            return;
        }

        try (Connection connection = dataSource.getConnection()) {
            importedCSVRecordModels.forEach(record -> {
                persistenceManager.delete(connection, XSKImportedCSVRecordModel.class, record.getId());
            });

            List<String> ids = importedCSVRecordModels.stream().map(record -> record.getId().toString()).collect(Collectors.toList());
            ConsoleFacade.log("Entities with ids: "+ String.join(", ", ids)+ " were DELETED successfully in XSK_IMPORTED_CSV_RECORDS.");
        }

    }

    @Override
    public List<XSKImportedCSVRecordModel> getImportedCSVRecordsByTableAndCSVLocation(String tableName, String csvLocation) throws XSKDataStructuresException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = SqlFactory.getNative(connection).select().column("*").from("XSK_IMPORTED_CSV_RECORDS")
                    .where("TABLE_NAME = ? AND CSV_LOCATION = ?").toString();
            return persistenceManager.query(connection, XSKImportedCSVRecordModel.class, sql, tableName, csvLocation);
        } catch (SQLException e) {
            throw new XSKDataStructuresException(e);
        }
    }

    @Override
    public List<XSKImportedCSVRecordModel> getImportedCSVsByHdbtiLocation(String hdbtiLocation) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = SqlFactory.getNative(connection).select().column("*").from("XSK_IMPORTED_CSV_RECORDS")
                    .where("HDBTI_LOCATION = ?").toString();
            return persistenceManager.query(connection, XSKImportedCSVRecordModel.class, sql, hdbtiLocation);
        } catch (SQLException e) {
            logger.error("Error occurred while trying to retrieve imported csv records by HDBTI file location");
        }

        return new ArrayList<>();
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public PersistenceManager<XSKImportedCSVRecordModel> getPersistenceManager() {
        return persistenceManager;
    }
}
