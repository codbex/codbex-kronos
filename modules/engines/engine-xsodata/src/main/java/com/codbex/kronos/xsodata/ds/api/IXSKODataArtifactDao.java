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

public interface IXSKODataArtifactDao {

    XSKODataModel createXSKODataArtifact(XSKODataModel tableModel) throws XSKODataException;
    XSKODataModel getXSKODataArtifact(String location) throws XSKODataException;
    XSKODataModel getXSKODataArtifactByName(String name) throws XSKODataException;
    void removeXSKODataArtifact(String location) throws XSKODataException;
    void updateXSKODataArtifact(String location, String name, String hash) throws XSKODataException;
    List<XSKODataModel> getAllXSKODataArtifacts() throws XSKODataException;

}
