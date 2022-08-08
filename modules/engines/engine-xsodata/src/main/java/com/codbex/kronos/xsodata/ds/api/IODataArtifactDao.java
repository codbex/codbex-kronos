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
package com.codbex.kronos.xsodata.ds.api;

import java.util.List;

import com.codbex.kronos.xsodata.ds.model.ODataModel;

/**
 * The Interface IODataArtifactDao.
 */
public interface IODataArtifactDao {

    /**
     * Creates the O data artifact.
     *
     * @param tableModel the table model
     * @return the o data model
     * @throws ODataException the o data exception
     */
    ODataModel createODataArtifact(ODataModel tableModel) throws ODataException;
    
    /**
     * Gets the o data artifact.
     *
     * @param location the location
     * @return the o data artifact
     * @throws ODataException the o data exception
     */
    ODataModel getODataArtifact(String location) throws ODataException;
    
    /**
     * Gets the o data artifact by name.
     *
     * @param name the name
     * @return the o data artifact by name
     * @throws ODataException the o data exception
     */
    ODataModel getODataArtifactByName(String name) throws ODataException;
    
    /**
     * Removes the O data artifact.
     *
     * @param location the location
     * @throws ODataException the o data exception
     */
    void removeODataArtifact(String location) throws ODataException;
    
    /**
     * Update O data artifact.
     *
     * @param location the location
     * @param name the name
     * @param hash the hash
     * @throws ODataException the o data exception
     */
    void updateODataArtifact(String location, String name, String hash) throws ODataException;
    
    /**
     * Gets the all O data artifacts.
     *
     * @return the all O data artifacts
     * @throws ODataException the o data exception
     */
    List<ODataModel> getAllODataArtifacts() throws ODataException;

}
