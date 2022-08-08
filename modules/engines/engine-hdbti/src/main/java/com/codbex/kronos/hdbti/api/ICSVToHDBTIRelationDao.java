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

import java.util.List;
import javax.sql.DataSource;
import org.eclipse.dirigible.database.persistence.PersistenceManager;

import com.codbex.kronos.hdbti.model.TableImportArtifact;
import com.codbex.kronos.hdbti.model.TableImportToCsvRelation;

/**
 * The Interface ICSVToHDBTIRelationDao.
 */
public interface ICSVToHDBTIRelationDao {

    /**
     * Persist new csv and hdbti relations.
     *
     * @param tableImportArtifact the table import artifact
     */
    void persistNewCsvAndHdbtiRelations(TableImportArtifact tableImportArtifact);

    /**
     * Delete csv and hdbti relations.
     *
     * @param hdbtiFileName the hdbti file name
     */
    void deleteCsvAndHdbtiRelations(String hdbtiFileName);

    /**
     * Gets the all hdbti to csv relations.
     *
     * @return the all hdbti to csv relations
     */
    List<TableImportToCsvRelation> getAllHdbtiToCsvRelations();

    /**
     * Checks for csv changed.
     *
     * @param tableImportToCsvRelation the table import to csv relation
     * @param csvContent the csv content
     * @return true, if successful
     */
    boolean hasCsvChanged(TableImportToCsvRelation tableImportToCsvRelation, String csvContent);

    /**
     * Gets the affected hdbti to csv relations.
     *
     * @param csvFilePath the csv file path
     * @return the affected hdbti to csv relations
     */
    List<TableImportToCsvRelation> getAffectedHdbtiToCsvRelations(String csvFilePath);

    /**
     * Gets the table import to csv relation persistence manager.
     *
     * @return the table import to csv relation persistence manager
     */
    PersistenceManager<TableImportToCsvRelation> getTableImportToCsvRelationPersistenceManager();

    /**
     * Gets the data source.
     *
     * @return the data source
     */
    DataSource getDataSource();
}
