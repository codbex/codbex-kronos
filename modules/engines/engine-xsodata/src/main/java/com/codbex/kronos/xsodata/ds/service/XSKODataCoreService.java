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

import com.codbex.kronos.parser.xsodata.model.XSKHDBXSODATAAssociation;
import com.codbex.kronos.parser.xsodata.model.XSKHDBXSODATAEntity;
import com.codbex.kronos.xsodata.ds.api.IXSKODataArtifactDao;
import com.codbex.kronos.xsodata.ds.api.IXSKODataCoreService;
import com.codbex.kronos.xsodata.ds.api.IXSKODataParser;
import com.codbex.kronos.xsodata.ds.api.XSKODataException;
import com.codbex.kronos.xsodata.ds.dao.XSKODataArtifactDao;
import com.codbex.kronos.xsodata.ds.model.XSKODataModel;

import org.eclipse.dirigible.api.v3.security.UserFacade;

import java.sql.Timestamp;
import java.util.List;

/**
 * The Data Structure Core Service.
 */
public class XSKODataCoreService implements IXSKODataCoreService {

    private IXSKODataArtifactDao xskODataArtifactDao = new XSKODataArtifactDao();

    private IXSKODataParser xskODataParser = new XSKODataParser();

    public static XSKHDBXSODATAEntity getEntity(XSKODataModel model, String alias, String navigation) {
        if (model != null && model.getService() != null) {
            for (XSKHDBXSODATAEntity entity : model.getService().getEntities()) {
                if (alias != null && alias.equals(entity.getAlias())) {
                    return entity;
                }
            }
        }
        throw new IllegalArgumentException(
                String.format("There is no entity with XSKODataUtilsname: %s, referenced by the navigation: %s", alias, navigation));
    }

    public static XSKHDBXSODATAAssociation getAssociation(XSKODataModel model, String name, String navigation) {
        if (model != null && model.getService() != null) {
            for (XSKHDBXSODATAAssociation association : model.getService().getAssociations()) {
                if (name != null && name.equals(association.getName())) {
                    return association;
                }
            }
        }
        throw new IllegalArgumentException(
                String.format("There is no association with name: %s, referenced by the navigation: %s", name, navigation));
    }

    @Override
    public XSKODataModel createOData(String location, String name, String hash) throws XSKODataException {
        XSKODataModel tableModel = new XSKODataModel();
        tableModel.setLocation(location);
        tableModel.setName(name);
        tableModel.setHash(hash);
        tableModel.setCreatedBy(UserFacade.getName());
        tableModel.setCreatedAt(new Timestamp(new java.util.Date().getTime()));

        return xskODataArtifactDao.createXSKODataArtifact(tableModel);
    }

    @Override
    public XSKODataModel getOData(String location) throws XSKODataException {
        return xskODataArtifactDao.getXSKODataArtifact(location);
    }

    @Override
    public XSKODataModel getODataByName(String name) throws XSKODataException {
        return xskODataArtifactDao.getXSKODataArtifactByName(name);
    }

    @Override
    public void removeOData(String location) throws XSKODataException {
        xskODataArtifactDao.removeXSKODataArtifact(location);
    }

    @Override
    public void updateOData(String location, String name, String hash) throws XSKODataException {
        xskODataArtifactDao.updateXSKODataArtifact(location, name, hash);
    }

    @Override
    public List<XSKODataModel> getAllODataRecords() throws XSKODataException {
        return xskODataArtifactDao.getAllXSKODataArtifacts();
    }

    @Override
    public boolean existsOData(String location) throws XSKODataException {
        return xskODataArtifactDao.getXSKODataArtifact(location) != null;
    }

    @Override
    public XSKODataModel parseOData(String location, String content) throws Exception {
        return xskODataParser.parseXSODataArtifact(location, content);
    }

}
