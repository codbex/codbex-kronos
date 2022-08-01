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
package com.codbex.kronos.xsodata.ds.dao;

import com.codbex.kronos.xsodata.ds.api.IODataArtifactDao;
import com.codbex.kronos.xsodata.ds.api.KronosODataException;
import com.codbex.kronos.xsodata.ds.model.ODataModel;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.PersistenceManager;
import org.eclipse.dirigible.database.sql.SqlFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static java.text.MessageFormat.format;

public class ODataArtifactDao implements IODataArtifactDao {

    private final DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);

    private final PersistenceManager<ODataModel> persistenceManager = new PersistenceManager<>();

    @Override
    public ODataModel createODataArtifact(ODataModel tableModel) throws KronosODataException {
        try (Connection connection = dataSource.getConnection()) {
            persistenceManager.insert(connection, tableModel);
            return tableModel;
        } catch (SQLException e) {
            throw new KronosODataException(e);
        }
    }

    @Override
    public ODataModel getODataArtifact(String location) throws KronosODataException {
        try (Connection connection = dataSource.getConnection()) {
            return persistenceManager.find(connection, ODataModel.class, location);
        } catch (SQLException e) {
            throw new KronosODataException(e);
        }
    }

    @Override
    public ODataModel getODataArtifactByName(String name) throws KronosODataException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = SqlFactory.getNative(connection).select().column("*").from("KRONOS_ODATA")
                    .where("OD_NAME = ?").toString();
            List<ODataModel> tableModels = persistenceManager.query(connection, ODataModel.class, sql,
                    Collections.singletonList(name));
            if (tableModels.isEmpty()) {
                return null;
            }
            if (tableModels.size() > 1) {
                throw new KronosODataException(format("There are more that one OData with the same name [{0}] at locations: [{1}] and [{2}].",
                        name, tableModels.get(0).getLocation(), tableModels.get(1).getLocation()));
            }
            return tableModels.get(0);
        } catch (SQLException e) {
            throw new KronosODataException(e);
        }
    }

    @Override
    public void removeODataArtifact(String location) throws KronosODataException {
        try (Connection connection = dataSource.getConnection()) {
            persistenceManager.delete(connection, ODataModel.class, location);
        } catch (SQLException e) {
            throw new KronosODataException(e);
        }
    }

    @Override
    public void updateODataArtifact(String location, String name, String hash) throws KronosODataException {
        try (Connection connection = dataSource.getConnection()) {
            ODataModel tableModel = getODataArtifact(location);
            tableModel.setName(name);
            tableModel.setHash(hash);
            persistenceManager.update(connection, tableModel);
        } catch (SQLException e) {
            throw new KronosODataException(e);
        }
    }

    @Override
    public List<ODataModel> getAllODataArtifacts() throws KronosODataException {
        try (Connection connection = dataSource.getConnection()) {
            String sql = SqlFactory.getNative(connection).select().column("*").from("KRONOS_ODATA").toString();
            return persistenceManager.query(connection, ODataModel.class, sql);
        } catch (SQLException e) {
            throw new KronosODataException(e);
        }
    }

}
