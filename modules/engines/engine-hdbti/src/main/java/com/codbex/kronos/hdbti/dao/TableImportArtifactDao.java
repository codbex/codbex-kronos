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
import java.util.List;
import javax.sql.DataSource;

import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.PersistenceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdbti.api.ITableImportArtifactDao;
import com.codbex.kronos.hdbti.api.TableImportException;
import com.codbex.kronos.hdbti.model.TableImportArtifact;

public class TableImportArtifactDao implements ITableImportArtifactDao {

    private static final Logger logger = LoggerFactory.getLogger(TableImportArtifactDao.class);

    private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);

    private PersistenceManager<TableImportArtifact> persistenceManager = new PersistenceManager<TableImportArtifact>();

    @Override
    public TableImportArtifact createTableImportArtifact(TableImportArtifact tableImportArtifact) throws TableImportException {

        try (Connection connection = dataSource.getConnection()) {
            persistenceManager.insert(connection, tableImportArtifact);
            return tableImportArtifact;
        } catch (SQLException e) {
            throw new TableImportException(e);
        }
    }

    @Override
    public void updateTableImportArtifact(TableImportArtifact artifact) throws TableImportException {
        try (Connection connection = dataSource.getConnection()) {
            TableImportArtifact tableImportArtifact = getTableImportArtifact(artifact.getLocation());
            tableImportArtifact.setName(artifact.getName());
            tableImportArtifact.setHash(artifact.getHash());
            persistenceManager.update(connection, tableImportArtifact);
        } catch (SQLException e) {
            throw new TableImportException(e);
        }
    }

    @Override
    public TableImportArtifact getTableImportArtifact(String location) throws TableImportException {
        try (Connection connection = dataSource.getConnection()) {
            return persistenceManager.find(connection, TableImportArtifact.class, location);
        } catch (SQLException e) {
            throw new TableImportException(e);
        }
    }

    @Override
    public void removeTableImportArtifact(String location) {
        try (Connection connection = dataSource.getConnection()) {
            persistenceManager.delete(connection, TableImportArtifact.class, location);
        } catch (SQLException e) {
            logger.error("Error cleaning up and HDBTI file from DB", e);
        }
    }

    @Override
    public List<TableImportArtifact> getTableImportArtifacts() throws TableImportException {
        try (Connection connection = dataSource.getConnection()) {
            return persistenceManager.findAll(connection, TableImportArtifact.class);
        } catch (SQLException e) {
            logger.error("Error getting the HDBTI artifacts from DB");
            throw new TableImportException(e);
        }
    }

    @Override
    public boolean existsTableImportArtifact(String location) throws TableImportException {
        return getTableImportArtifact(location) != null;
    }
}
