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
package com.codbex.kronos.hdb.ds.parser;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.apache.commons.io.IOUtils;

import com.codbex.kronos.engine.hdb.parser.HDBParameters;

public class TestContentProvider {

    public String getTestContent(String location) throws IOException {
        InputStream in = TestContentProvider.class.getResourceAsStream(location);
        return IOUtils.toString(Objects.requireNonNull(in), StandardCharsets.UTF_8);
    }

    HDBParameters getParametersModel(String type, String location, String content, String workspacePath) {
        return new HDBParameters(type, location, content, workspacePath);
    }
}
