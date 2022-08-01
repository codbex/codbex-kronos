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

import com.codbex.kronos.hdbti.model.TableImportArtifact;
import java.util.List;

public interface ITableImportArtifactDao {

    TableImportArtifact createTableImportArtifact(TableImportArtifact tableImportArtifact) throws TableImportException;

    void updateTableImportArtifact(TableImportArtifact artifact) throws TableImportException;

    TableImportArtifact getTableImportArtifact(String location) throws TableImportException;

    void removeTableImportArtifact(String location);

    List<TableImportArtifact> getTableImportArtifacts() throws TableImportException;

    boolean existsTableImportArtifact(String location) throws TableImportException;
}
