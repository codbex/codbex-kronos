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

public interface IImportedCSVRecordDao {
    ImportedCSVRecordModel save(ImportedCSVRecordModel importedRowModel) throws DataStructuresException;

    ImportedCSVRecordModel update(ImportedCSVRecordModel importedRowModel) throws SQLException;

    void deleteAll(List<ImportedCSVRecordModel> importedCSVRecordModels) throws SQLException;

    List<ImportedCSVRecordModel> getImportedCSVRecordsByTableAndCSVLocation(String tableName, String csvLocation) throws DataStructuresException;

    List<ImportedCSVRecordModel> getImportedCSVsByHdbtiLocation(String hdbtiLocation);

    DataSource getDataSource();

    PersistenceManager<ImportedCSVRecordModel> getPersistenceManager();
}
