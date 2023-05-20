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
 * The OData Core Service interface.
 */
public interface IODataCoreService {
    /**
     * Creates the odata.
     *
     * @param location the location
     * @param name     the name
     * @param hash     the hash
     * @return the data structure odata model
     * @throws ODataException the data structures exception
     */
    ODataModel createOData(String location, String name, String hash) throws ODataException;

    /**
     * Gets the odata.
     *
     * @param location the location
     * @return the odata
     * @throws ODataException the data structures exception
     */
    ODataModel getOData(String location) throws ODataException;

    /**
     * Gets the odata by name.
     *
     * @param name the name
     * @return the odata by name
     * @throws ODataException the data structures exception
     */
    ODataModel getODataByName(String name) throws ODataException;

    /**
     * Exists odata.
     *
     * @param location the location
     * @return true, if successful
     * @throws ODataException the data structures exception
     */
    boolean existsOData(String location) throws ODataException;

    /**
     * Removes the odata.
     *
     * @param location the location
     * @throws ODataException the data structures exception
     */
    void removeOData(String location) throws ODataException;

    /**
     * Update odata.
     *
     * @param location the location
     * @param name     the name
     * @param hash     the hash
     * @throws ODataException the data structures exception
     */
    void updateOData(String location, String name, String hash) throws ODataException;

    /**
     * Gets all odata records.
     *
     * @return list of odata
     * @throws ODataException the data structures exception
     */
    List<ODataModel> getAllODataRecords() throws ODataException;

    /**
     * Parses the odata.
     *
     * @param location the location
     * @param content  the content
     * @return the data structure odata model
     * @throws Exception - throws exception
     */
    ODataModel parseOData(String location, String content) throws Exception;


}
