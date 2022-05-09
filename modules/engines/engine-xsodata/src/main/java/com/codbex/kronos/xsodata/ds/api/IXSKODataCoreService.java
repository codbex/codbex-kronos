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

import com.codbex.kronos.xsodata.ds.model.XSKODataModel;

/**
 * The XSK OData Core Service interface.
 */
public interface IXSKODataCoreService {
    /**
     * Creates the odata.
     *
     * @param location the location
     * @param name     the name
     * @param hash     the hash
     * @return the data structure odata model
     * @throws XSKODataException the data structures exception
     */
    XSKODataModel createOData(String location, String name, String hash) throws XSKODataException;

    /**
     * Gets the odata.
     *
     * @param location the location
     * @return the odata
     * @throws XSKODataException the data structures exception
     */
    XSKODataModel getOData(String location) throws XSKODataException;

    /**
     * Gets the odata by name.
     *
     * @param name the name
     * @return the odata by name
     * @throws XSKODataException the data structures exception
     */
    XSKODataModel getODataByName(String name) throws XSKODataException;

    /**
     * Exists odata.
     *
     * @param location the location
     * @return true, if successful
     * @throws XSKODataException the data structures exception
     */
    boolean existsOData(String location) throws XSKODataException;

    /**
     * Removes the odata.
     *
     * @param location the location
     * @throws XSKODataException the data structures exception
     */
    void removeOData(String location) throws XSKODataException;

    /**
     * Update odata.
     *
     * @param location the location
     * @param name     the name
     * @param hash     the hash
     * @throws XSKODataException the data structures exception
     */
    void updateOData(String location, String name, String hash) throws XSKODataException;

    /**
     * Gets all odata records.
     *
     * @return list of odata
     * @throws XSKODataException the data structures exception
     */
    List<XSKODataModel> getAllODataRecords() throws XSKODataException;

    /**
     * Parses the odata.
     *
     * @param location the location
     * @param content  the content
     * @return the data structure odata model
     * @throws Exception - throws exception
     */
    XSKODataModel parseOData(String location, String content) throws Exception;


}
