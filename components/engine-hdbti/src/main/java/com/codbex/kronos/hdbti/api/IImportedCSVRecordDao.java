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
package com.codbex.kronos.hdbti.api;

import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdbti.model.ImportedCSVRecordModel;

import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.eclipse.dirigible.database.persistence.PersistenceManager;

/**
 * The Interface IImportedCSVRecordDao.
 */
public interface IImportedCSVRecordDao {

    /**
     * Save.
     *
     * @param importedRowModel the imported row model
     * @return the imported CSV record model
     * @throws DataStructuresException the data structures exception
     */
    ImportedCSVRecordModel save(ImportedCSVRecordModel importedRowModel) throws DataStructuresException;

    /**
     * Update.
     *
     * @param importedRowModel the imported row model
     * @return the imported CSV record model
     * @throws SQLException the SQL exception
     */
    ImportedCSVRecordModel update(ImportedCSVRecordModel importedRowModel) throws SQLException;

    /**
     * Delete all.
     *
     * @param importedCSVRecordModels the imported CSV record models
     * @throws SQLException the SQL exception
     */
    void deleteAll(List<ImportedCSVRecordModel> importedCSVRecordModels) throws SQLException;

    /**
     * Gets the imported CSV records by table and CSV location.
     *
     * @param tableName the table name
     * @param csvLocation the csv location
     * @return the imported CSV records by table and CSV location
     * @throws DataStructuresException the data structures exception
     */
    List<ImportedCSVRecordModel> getImportedCSVRecordsByTableAndCSVLocation(String tableName, String csvLocation) throws DataStructuresException;

    /**
     * Gets the imported CS vs by hdbti location.
     *
     * @param hdbtiLocation the hdbti location
     * @return the imported CS vs by hdbti location
     */
    List<ImportedCSVRecordModel> getImportedCSVsByHdbtiLocation(String hdbtiLocation);

    /**
     * Gets the data source.
     *
     * @return the data source
     */
    DataSource getDataSource();

    /**
     * Gets the persistence manager.
     *
     * @return the persistence manager
     */
    PersistenceManager<ImportedCSVRecordModel> getPersistenceManager();
}
