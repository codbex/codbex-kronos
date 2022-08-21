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

/**
 * The Class TableImportArtifactDao.
 */
public class TableImportArtifactDao implements ITableImportArtifactDao {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(TableImportArtifactDao.class);

    /** The data source. */
    public DataSource getDataSource() {
    	return (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);
    }

    /** The persistence manager. */
    private PersistenceManager<TableImportArtifact> persistenceManager = new PersistenceManager<TableImportArtifact>();

    /**
     * Creates the table import artifact.
     *
     * @param tableImportArtifact the table import artifact
     * @return the table import artifact
     * @throws TableImportException the table import exception
     */
    @Override
    public TableImportArtifact createTableImportArtifact(TableImportArtifact tableImportArtifact) throws TableImportException {

        try (Connection connection = getDataSource().getConnection()) {
            persistenceManager.insert(connection, tableImportArtifact);
            return tableImportArtifact;
        } catch (SQLException e) {
            throw new TableImportException(e);
        }
    }

    /**
     * Update table import artifact.
     *
     * @param artifact the artifact
     * @throws TableImportException the table import exception
     */
    @Override
    public void updateTableImportArtifact(TableImportArtifact artifact) throws TableImportException {
        try (Connection connection = getDataSource().getConnection()) {
            TableImportArtifact tableImportArtifact = getTableImportArtifact(artifact.getLocation());
            tableImportArtifact.setName(artifact.getName());
            tableImportArtifact.setHash(artifact.getHash());
            persistenceManager.update(connection, tableImportArtifact);
        } catch (SQLException e) {
            throw new TableImportException(e);
        }
    }

    /**
     * Gets the table import artifact.
     *
     * @param location the location
     * @return the table import artifact
     * @throws TableImportException the table import exception
     */
    @Override
    public TableImportArtifact getTableImportArtifact(String location) throws TableImportException {
        try (Connection connection = getDataSource().getConnection()) {
            return persistenceManager.find(connection, TableImportArtifact.class, location);
        } catch (SQLException e) {
            throw new TableImportException(e);
        }
    }

    /**
     * Removes the table import artifact.
     *
     * @param location the location
     */
    @Override
    public void removeTableImportArtifact(String location) {
        try (Connection connection = getDataSource().getConnection()) {
            persistenceManager.delete(connection, TableImportArtifact.class, location);
        } catch (SQLException e) {
            logger.error("Error cleaning up and HDBTI file from DB", e);
        }
    }

    /**
     * Gets the table import artifacts.
     *
     * @return the table import artifacts
     * @throws TableImportException the table import exception
     */
    @Override
    public List<TableImportArtifact> getTableImportArtifacts() throws TableImportException {
        try (Connection connection = getDataSource().getConnection()) {
            return persistenceManager.findAll(connection, TableImportArtifact.class);
        } catch (SQLException e) {
            logger.error("Error getting the HDBTI artifacts from DB");
            throw new TableImportException(e);
        }
    }

    /**
     * Exists table import artifact.
     *
     * @param location the location
     * @return true, if successful
     * @throws TableImportException the table import exception
     */
    @Override
    public boolean existsTableImportArtifact(String location) throws TableImportException {
        return getTableImportArtifact(location) != null;
    }
}
