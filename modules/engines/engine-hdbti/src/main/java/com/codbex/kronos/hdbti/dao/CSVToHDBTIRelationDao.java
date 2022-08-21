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

import com.codbex.kronos.hdbti.api.ICSVToHDBTIRelationDao;
import com.codbex.kronos.hdbti.model.TableImportArtifact;
import com.codbex.kronos.hdbti.model.TableImportToCsvRelation;
import com.codbex.kronos.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class CSVToHDBTIRelationDao.
 */
public class CSVToHDBTIRelationDao implements ICSVToHDBTIRelationDao {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(CSVToHDBTIRelationDao.class);

    /** The table import to csv relation persistence manager. */
    private PersistenceManager<TableImportToCsvRelation> tableImportToCsvRelationPersistenceManager = new PersistenceManager<TableImportToCsvRelation>();

    /** The data source. */
    @Override
    public DataSource getDataSource() {
    	return (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);
    }

    /**
     * Persist new csv and hdbti relations.
     *
     * @param tableImportArtifact the table import artifact
     */
    @Override
    public void persistNewCsvAndHdbtiRelations(TableImportArtifact tableImportArtifact) {
        for(TableImportToCsvRelation relation : tableImportArtifact.getTableImportToCsvRelations()){
            relation.setHdbti(relation.getHdbti());
            try (Connection connection = getDataSource().getConnection()) {
                tableImportToCsvRelationPersistenceManager.insert(connection, relation);
            } catch (SQLException sqlException) {
                logger.error(String.format("Something went wrong while trying to insert TableImportArtifact located at: %s", tableImportArtifact.getLocation()), sqlException);
            }
        }
    }

    /**
     * Delete csv and hdbti relations.
     *
     * @param hdbtiFileName the hdbti file name
     */
    @Override
    public void deleteCsvAndHdbtiRelations(String hdbtiFileName) {
        String sql = String.format("DELETE FROM \"KRONOS_TABLE_IMPORT_TO_CSV\" WHERE \"HDBTI_LOCATION\"='%s'", hdbtiFileName);
        try (Connection connection = getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            logger.error("Error occurred while clearing the HdbtiToCsv relations from the DB", e);
        }
    }

    /**
     * Gets the all hdbti to csv relations.
     *
     * @return the all hdbti to csv relations
     */
    @Override
    public List<TableImportToCsvRelation> getAllHdbtiToCsvRelations() {
        List<TableImportToCsvRelation> listOfcsvToHdbtiRelations = new ArrayList<>();
        try (Connection connection = getDataSource().getConnection()) {
            listOfcsvToHdbtiRelations = tableImportToCsvRelationPersistenceManager.findAll(connection, TableImportToCsvRelation.class);
        } catch (SQLException e) {
            logger.error("Error occured while retrieving the HdbtiToCsv relations from DB", e);
        }
        return listOfcsvToHdbtiRelations;
    }

    /**
     * Checks for csv changed.
     *
     * @param tableImportToCsvRelation the table import to csv relation
     * @param csvContent the csv content
     * @return true, if successful
     */
    @Override
    public boolean hasCsvChanged(TableImportToCsvRelation tableImportToCsvRelation, String csvContent) {
        return !tableImportToCsvRelation.getCsvHash().equals(DigestUtils.md5Hex(csvContent));
    }

    /**
     * Gets the affected hdbti to csv relations.
     *
     * @param csvFilePath the csv file path
     * @return the affected hdbti to csv relations
     */
    @Override
    public List<TableImportToCsvRelation> getAffectedHdbtiToCsvRelations(String csvFilePath) {
        List<TableImportToCsvRelation> relations = getAllHdbtiToCsvRelations();
        return relations.stream().filter(relation -> relation.getCsv().equals(Utils.convertToFullPath(csvFilePath))).collect(Collectors.toList());
    }

    /**
     * Gets the table import to csv relation persistence manager.
     *
     * @return the table import to csv relation persistence manager
     */
    @Override
    public PersistenceManager<TableImportToCsvRelation> getTableImportToCsvRelationPersistenceManager() {
        return tableImportToCsvRelationPersistenceManager;
    }
    
}
