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
package com.codbex.kronos.xsodata.ds.service;

import com.codbex.kronos.parser.xsodata.model.XSODataAssociation;
import com.codbex.kronos.parser.xsodata.model.XSODataEntity;
import com.codbex.kronos.xsodata.ds.api.IODataArtifactDao;
import com.codbex.kronos.xsodata.ds.api.IODataCoreService;
import com.codbex.kronos.xsodata.ds.api.IODataParser;
import com.codbex.kronos.xsodata.ds.api.ODataException;
import com.codbex.kronos.xsodata.ds.dao.ODataArtifactDao;
import com.codbex.kronos.xsodata.ds.model.ODataModel;

import org.eclipse.dirigible.api.v3.security.UserFacade;

import java.sql.Timestamp;
import java.util.List;

/**
 * The Data Structure Core Service.
 */
public class ODataCoreService implements IODataCoreService {

    /** The o data artifact dao. */
    private IODataArtifactDao oDataArtifactDao = new ODataArtifactDao();

    /** The o data parser. */
    private IODataParser oDataParser = new ODataParser();

    /**
     * Gets the entity.
     *
     * @param model the model
     * @param alias the alias
     * @param navigation the navigation
     * @return the entity
     */
    public static XSODataEntity getEntity(ODataModel model, String alias, String navigation) {
        if (model != null && model.getService() != null) {
            for (XSODataEntity entity : model.getService().getEntities()) {
                if (alias != null && alias.equals(entity.getAlias())) {
                    return entity;
                }
            }
        }
        throw new IllegalArgumentException(
                String.format("There is no entity with name: %s, referenced by the navigation: %s", alias, navigation));
    }

    /**
     * Gets the association.
     *
     * @param model the model
     * @param name the name
     * @param navigation the navigation
     * @return the association
     */
    public static XSODataAssociation getAssociation(ODataModel model, String name, String navigation) {
        if (model != null && model.getService() != null) {
            for (XSODataAssociation association : model.getService().getAssociations()) {
                if (name != null && name.equals(association.getName())) {
                    return association;
                }
            }
        }
        throw new IllegalArgumentException(
                String.format("There is no association with name: %s, referenced by the navigation: %s", name, navigation));
    }

    /**
     * Creates the O data.
     *
     * @param location the location
     * @param name the name
     * @param hash the hash
     * @return the o data model
     * @throws ODataException the o data exception
     */
    @Override
    public ODataModel createOData(String location, String name, String hash) throws ODataException {
        ODataModel tableModel = new ODataModel();
        tableModel.setLocation(location);
        tableModel.setName(name);
        tableModel.setHash(hash);
        tableModel.setCreatedBy(UserFacade.getName());
        tableModel.setCreatedAt(new Timestamp(new java.util.Date().getTime()));

        return oDataArtifactDao.createODataArtifact(tableModel);
    }

    /**
     * Gets the o data.
     *
     * @param location the location
     * @return the o data
     * @throws ODataException the o data exception
     */
    @Override
    public ODataModel getOData(String location) throws ODataException {
        return oDataArtifactDao.getODataArtifact(location);
    }

    /**
     * Gets the o data by name.
     *
     * @param name the name
     * @return the o data by name
     * @throws ODataException the o data exception
     */
    @Override
    public ODataModel getODataByName(String name) throws ODataException {
        return oDataArtifactDao.getODataArtifactByName(name);
    }

    /**
     * Removes the O data.
     *
     * @param location the location
     * @throws ODataException the o data exception
     */
    @Override
    public void removeOData(String location) throws ODataException {
        oDataArtifactDao.removeODataArtifact(location);
    }

    /**
     * Update O data.
     *
     * @param location the location
     * @param name the name
     * @param hash the hash
     * @throws ODataException the o data exception
     */
    @Override
    public void updateOData(String location, String name, String hash) throws ODataException {
        oDataArtifactDao.updateODataArtifact(location, name, hash);
    }

    /**
     * Gets the all O data records.
     *
     * @return the all O data records
     * @throws ODataException the o data exception
     */
    @Override
    public List<ODataModel> getAllODataRecords() throws ODataException {
        return oDataArtifactDao.getAllODataArtifacts();
    }

    /**
     * Exists O data.
     *
     * @param location the location
     * @return true, if successful
     * @throws ODataException the o data exception
     */
    @Override
    public boolean existsOData(String location) throws ODataException {
        return oDataArtifactDao.getODataArtifact(location) != null;
    }

    /**
     * Parses the O data.
     *
     * @param location the location
     * @param content the content
     * @return the o data model
     * @throws Exception the exception
     */
    @Override
    public ODataModel parseOData(String location, String content) throws Exception {
        return oDataParser.parseODataArtifact(location, content);
    }

}
