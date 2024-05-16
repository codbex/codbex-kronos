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
package com.codbex.kronos.engine.hdb.parser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.apache.commons.io.IOUtils;

/**
 * The Class TestContentProvider.
 */
public class TestContentProvider {

    /**
     * Gets the test content.
     *
     * @param location the location
     * @return the test content
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public String getTestContent(String location) throws IOException {
        InputStream in = TestContentProvider.class.getResourceAsStream(location);
        return IOUtils.toString(Objects.requireNonNull(in), StandardCharsets.UTF_8);
    }

    /**
     * Gets the parameters model.
     *
     * @param type the type
     * @param location the location
     * @param content the content
     * @param workspacePath the workspace path
     * @return the parameters model
     */
    HDBParameters getParametersModel(String type, String location, String content, String workspacePath) {
        return new HDBParameters(type, location, content, workspacePath);
    }
}
