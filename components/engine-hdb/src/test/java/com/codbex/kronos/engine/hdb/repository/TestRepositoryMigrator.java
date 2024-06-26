/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.hdb.repository;

import java.io.IOException;

import org.eclipse.dirigible.repository.api.IRepository;
import org.eclipse.dirigible.repository.api.IRepositoryStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Class TestRepositoryMigrator.
 */
@Component
public class TestRepositoryMigrator {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(TestRepositoryMigrator.class);

    /** The repository. */
    @Autowired
    private IRepository repository;

    /**
     * Migrate.
     *
     * @param source the source
     */
    public void migrate(String source) {
        try {
            byte[] content = TestRepositoryMigrator.class
                                                         .getResourceAsStream(IRepositoryStructure.PATH_REGISTRY_PUBLIC
                                                                 + IRepositoryStructure.SEPARATOR + source)
                                                         .readAllBytes();
            repository.createResource(IRepositoryStructure.PATH_REGISTRY_PUBLIC + IRepositoryStructure.SEPARATOR + source, content);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * Clean up.
     */
    public void cleanUp() {
        repository.removeCollection(IRepositoryStructure.PATH_REGISTRY_PUBLIC + IRepositoryStructure.SEPARATOR);
    }
}
