/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.integration.tests.core.hdb.repository;

import java.io.IOException;
import org.eclipse.dirigible.repository.api.IResource;
import org.eclipse.dirigible.repository.api.RepositoryReadException;

public class TestRepository extends AbstractTestRepository {

    @Override
    public IResource getResource(String s) {
        try {
            byte[] content = TestRepository.class.getResourceAsStream(s).readAllBytes();
            TestResource resource = new TestResource();
            resource.setContent(content);
            return resource;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteLinkedPath(String repositoryPath) {
    }

    @Override
    public boolean hasResource(String s) throws RepositoryReadException {
        return false;
    }
}
