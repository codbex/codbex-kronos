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

import com.codbex.kronos.hdbti.model.TableImportArtifact;

/**
 * The Interface ITableImportArtifactDao.
 */
public interface ITableImportArtifactDao {

    /**
     * Creates the table import artifact.
     *
     * @param tableImportArtifact the table import artifact
     * @return the table import artifact
     * @throws TableImportException the table import exception
     */
    TableImportArtifact createTableImportArtifact(TableImportArtifact tableImportArtifact) throws TableImportException;

    /**
     * Update table import artifact.
     *
     * @param artifact the artifact
     * @throws TableImportException the table import exception
     */
    void updateTableImportArtifact(TableImportArtifact artifact) throws TableImportException;

    /**
     * Gets the table import artifact.
     *
     * @param location the location
     * @return the table import artifact
     * @throws TableImportException the table import exception
     */
    TableImportArtifact getTableImportArtifact(String location) throws TableImportException;

    /**
     * Removes the table import artifact.
     *
     * @param location the location
     */
    void removeTableImportArtifact(String location);

    /**
     * Gets the table import artifacts.
     *
     * @return the table import artifacts
     * @throws TableImportException the table import exception
     */
    List<TableImportArtifact> getTableImportArtifacts() throws TableImportException;

    /**
     * Exists table import artifact.
     *
     * @param location the location
     * @return true, if successful
     * @throws TableImportException the table import exception
     */
    boolean existsTableImportArtifact(String location) throws TableImportException;
}
