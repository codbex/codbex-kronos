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

public class CSVToHDBTIRelationDao implements ICSVToHDBTIRelationDao {

    private static final Logger logger = LoggerFactory.getLogger(CSVToHDBTIRelationDao.class);

    private PersistenceManager<TableImportToCsvRelation> tableImportToCsvRelationPersistenceManager = new PersistenceManager<TableImportToCsvRelation>();

    private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);

    @Override
    public void persistNewCsvAndHdbtiRelations(TableImportArtifact tableImportArtifact) {
        for(TableImportToCsvRelation relation : tableImportArtifact.getTableImportToCsvRelations()){
            relation.setHdbti(relation.getHdbti());
            try (Connection connection = dataSource.getConnection()) {
                tableImportToCsvRelationPersistenceManager.insert(connection, relation);
            } catch (SQLException sqlException) {
                logger.error(String.format("Something went wrong while trying to insert TableImportArtifact located at: %s", tableImportArtifact.getLocation()), sqlException);
            }
        }
    }

    @Override
    public void deleteCsvAndHdbtiRelations(String hdbtiFileName) {
        String sql = String.format("DELETE FROM \"KRONOS_TABLE_IMPORT_TO_CSV\" WHERE \"HDBTI_LOCATION\"='%s'", hdbtiFileName);
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            logger.error("Error occurred while clearing the HdbtiToCsv relations from the DB", e);
        }
    }

    @Override
    public List<TableImportToCsvRelation> getAllHdbtiToCsvRelations() {
        List<TableImportToCsvRelation> listOfcsvToHdbtiRelations = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            listOfcsvToHdbtiRelations = tableImportToCsvRelationPersistenceManager.findAll(connection, TableImportToCsvRelation.class);
        } catch (SQLException e) {
            logger.error("Error occured while retrieving the HdbtiToCsv relations from DB", e);
        }
        return listOfcsvToHdbtiRelations;
    }

    @Override
    public boolean hasCsvChanged(TableImportToCsvRelation tableImportToCsvRelation, String csvContent) {
        return !tableImportToCsvRelation.getCsvHash().equals(DigestUtils.md5Hex(csvContent));
    }

    @Override
    public List<TableImportToCsvRelation> getAffectedHdbtiToCsvRelations(String csvFilePath) {
        List<TableImportToCsvRelation> relations = getAllHdbtiToCsvRelations();
        return relations.stream().filter(relation -> relation.getCsv().equals(Utils.convertToFullPath(csvFilePath))).collect(Collectors.toList());
    }

    @Override
    public PersistenceManager<TableImportToCsvRelation> getTableImportToCsvRelationPersistenceManager() {
        return tableImportToCsvRelationPersistenceManager;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
