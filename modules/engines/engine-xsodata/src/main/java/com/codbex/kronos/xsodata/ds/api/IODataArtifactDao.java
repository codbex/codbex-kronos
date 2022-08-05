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

public interface IODataArtifactDao {

    ODataModel createODataArtifact(ODataModel tableModel) throws ODataException;
    ODataModel getODataArtifact(String location) throws ODataException;
    ODataModel getODataArtifactByName(String name) throws ODataException;
    void removeODataArtifact(String location) throws ODataException;
    void updateODataArtifact(String location, String name, String hash) throws ODataException;
    List<ODataModel> getAllODataArtifacts() throws ODataException;

}
