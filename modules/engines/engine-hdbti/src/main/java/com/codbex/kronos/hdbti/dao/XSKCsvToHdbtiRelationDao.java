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

import com.codbex.kronos.hdbti.api.IXSKCsvToHdbtiRelationDao;
import com.codbex.kronos.hdbti.model.XSKTableImportArtifact;
import com.codbex.kronos.hdbti.model.XSKTableImportToCsvRelation;
import com.codbex.kronos.utils.XSKUtils;

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

public class XSKCsvToHdbtiRelationDao implements IXSKCsvToHdbtiRelationDao {

    private static final Logger logger = LoggerFactory.getLogger(XSKCsvToHdbtiRelationDao.class);

    private PersistenceManager<XSKTableImportToCsvRelation> xskTableImportToCsvRelationPersistenceManager = new PersistenceManager<XSKTableImportToCsvRelation>();

    private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);

    @Override
    public void persistNewCsvAndHdbtiRelations(XSKTableImportArtifact tableImportArtifact) {
        for(XSKTableImportToCsvRelation relation : tableImportArtifact.getTableImportToCsvRelations()){
            relation.setHdbti(relation.getHdbti());
            try (Connection connection = dataSource.getConnection()) {
                xskTableImportToCsvRelationPersistenceManager.insert(connection, relation);
            } catch (SQLException sqlException) {
                logger.error(String.format("Something went wrong while trying to insert XSKTableImportArtifact located at: %s", tableImportArtifact.getLocation()), sqlException);
            }
        }
    }

    @Override
    public void deleteCsvAndHdbtiRelations(String hdbtiFileName) {
        String sql = String.format("DELETE FROM \"XSK_TABLE_IMPORT_TO_CSV\" WHERE \"HDBTI_LOCATION\"='%s'", hdbtiFileName);
        try (Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            logger.error("Error occurred while clearing the HdbtiToCsv relations from the DB", e);
        }
    }

    @Override
    public List<XSKTableImportToCsvRelation> getAllHdbtiToCsvRelations() {
        List<XSKTableImportToCsvRelation> listOfcsvToHdbtiRelations = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            listOfcsvToHdbtiRelations = xskTableImportToCsvRelationPersistenceManager.findAll(connection, XSKTableImportToCsvRelation.class);
        } catch (SQLException e) {
            logger.error("Error occured while retrieving the HdbtiToCsv relations from DB", e);
        }
        return listOfcsvToHdbtiRelations;
    }

    @Override
    public boolean hasCsvChanged(XSKTableImportToCsvRelation tableImportToCsvRelation, String csvContent) {
        return !tableImportToCsvRelation.getCsvHash().equals(DigestUtils.md5Hex(csvContent));
    }

    @Override
    public List<XSKTableImportToCsvRelation> getAffectedHdbtiToCsvRelations(String csvFilePath) {
        List<XSKTableImportToCsvRelation> relations = getAllHdbtiToCsvRelations();
        return relations.stream().filter(relation -> relation.getCsv().equals(XSKUtils.convertToFullPath(csvFilePath))).collect(Collectors.toList());
    }

    @Override
    public PersistenceManager<XSKTableImportToCsvRelation> getXskTableImportToCsvRelationPersistenceManager() {
        return xskTableImportToCsvRelationPersistenceManager;
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
