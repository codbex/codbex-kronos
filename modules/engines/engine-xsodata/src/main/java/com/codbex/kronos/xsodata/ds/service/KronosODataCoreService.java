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

import com.codbex.kronos.parser.xsodata.model.HDBXSODataAssociation;
import com.codbex.kronos.parser.xsodata.model.HDBXSODataEntity;
import com.codbex.kronos.xsodata.ds.api.IODataArtifactDao;
import com.codbex.kronos.xsodata.ds.api.IODataCoreService;
import com.codbex.kronos.xsodata.ds.api.IODataParser;
import com.codbex.kronos.xsodata.ds.api.KronosODataException;
import com.codbex.kronos.xsodata.ds.dao.ODataArtifactDao;
import com.codbex.kronos.xsodata.ds.model.ODataModel;
import org.eclipse.dirigible.api.v3.security.UserFacade;

import java.sql.Timestamp;
import java.util.List;

/**
 * The Data Structure Core Service.
 */
public class KronosODataCoreService implements IODataCoreService {

    private IODataArtifactDao oDataArtifactDao = new ODataArtifactDao();

    private IODataParser oDataParser = new ODataParser();

    public static HDBXSODataEntity getEntity(ODataModel model, String alias, String navigation) {
        if (model != null && model.getService() != null) {
            for (HDBXSODataEntity entity : model.getService().getEntities()) {
                if (alias != null && alias.equals(entity.getAlias())) {
                    return entity;
                }
            }
        }
        throw new IllegalArgumentException(
                String.format("There is no entity with ODataUtils name: %s, referenced by the navigation: %s", alias, navigation));
    }

    public static HDBXSODataAssociation getAssociation(ODataModel model, String name, String navigation) {
        if (model != null && model.getService() != null) {
            for (HDBXSODataAssociation association : model.getService().getAssociations()) {
                if (name != null && name.equals(association.getName())) {
                    return association;
                }
            }
        }
        throw new IllegalArgumentException(
                String.format("There is no association with name: %s, referenced by the navigation: %s", name, navigation));
    }

    @Override
    public ODataModel createOData(String location, String name, String hash) throws KronosODataException {
        ODataModel tableModel = new ODataModel();
        tableModel.setLocation(location);
        tableModel.setName(name);
        tableModel.setHash(hash);
        tableModel.setCreatedBy(UserFacade.getName());
        tableModel.setCreatedAt(new Timestamp(new java.util.Date().getTime()));

        return oDataArtifactDao.createODataArtifact(tableModel);
    }

    @Override
    public ODataModel getOData(String location) throws KronosODataException {
        return oDataArtifactDao.getODataArtifact(location);
    }

    @Override
    public ODataModel getODataByName(String name) throws KronosODataException {
        return oDataArtifactDao.getODataArtifactByName(name);
    }

    @Override
    public void removeOData(String location) throws KronosODataException {
        oDataArtifactDao.removeODataArtifact(location);
    }

    @Override
    public void updateOData(String location, String name, String hash) throws KronosODataException {
        oDataArtifactDao.updateODataArtifact(location, name, hash);
    }

    @Override
    public List<ODataModel> getAllODataRecords() throws KronosODataException {
        return oDataArtifactDao.getAllODataArtifacts();
    }

    @Override
    public boolean existsOData(String location) throws KronosODataException {
        return oDataArtifactDao.getODataArtifact(location) != null;
    }

    @Override
    public ODataModel parseOData(String location, String content) throws Exception {
        return oDataParser.parseXSODataArtifact(location, content);
    }

}
