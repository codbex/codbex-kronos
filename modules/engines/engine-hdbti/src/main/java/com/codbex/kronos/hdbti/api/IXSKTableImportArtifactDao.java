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
package com.codbex.kronos.hdbti.api;

import java.util.List;

import com.codbex.kronos.hdbti.model.XSKTableImportArtifact;

public interface IXSKTableImportArtifactDao {

    XSKTableImportArtifact createTableImportArtifact(XSKTableImportArtifact xskTableImportArtifact) throws XSKTableImportException;

    void updateTableImportArtifact(XSKTableImportArtifact artifact) throws XSKTableImportException;

    XSKTableImportArtifact getTableImportArtifact(String location) throws XSKTableImportException;

    void removeTableImportArtifact(String location);

    List<XSKTableImportArtifact> getTableImportArtifacts() throws XSKTableImportException;

    boolean existsTableImportArtifact(String location) throws XSKTableImportException;
}
