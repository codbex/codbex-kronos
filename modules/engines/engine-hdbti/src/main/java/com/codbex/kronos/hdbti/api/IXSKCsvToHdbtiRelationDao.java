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
import javax.sql.DataSource;
import org.eclipse.dirigible.database.persistence.PersistenceManager;

import com.codbex.kronos.hdbti.model.XSKTableImportArtifact;
import com.codbex.kronos.hdbti.model.XSKTableImportToCsvRelation;

public interface IXSKCsvToHdbtiRelationDao {
    void persistNewCsvAndHdbtiRelations(XSKTableImportArtifact tableImportArtifact);

    void deleteCsvAndHdbtiRelations(String hdbtiFileName);

    List<XSKTableImportToCsvRelation> getAllHdbtiToCsvRelations();

    boolean hasCsvChanged(XSKTableImportToCsvRelation tableImportToCsvRelation, String csvContent);

    List<XSKTableImportToCsvRelation> getAffectedHdbtiToCsvRelations(String csvFilePath);

    PersistenceManager<XSKTableImportToCsvRelation> getXskTableImportToCsvRelationPersistenceManager();

    DataSource getDataSource();
}
