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

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.DataStructureHDBTableFunctionModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;

import com.codbex.kronos.hdb.ds.parser.TestContentProvider;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class TestTableFunctionParser {
    TestContentProvider testContentProvider = new TestContentProvider();
    public DataStructureHDBTableFunctionModel parseTableFunction(@NotNull DataStructureParser<DataStructureHDBTableFunctionModel> parser,
        String location, String content)
            throws DataStructuresException, ArtifactParserException, IOException {
        DataStructureParametersModel parametersModel =
                testContentProvider.getParametersModel(null, location, content, null);
        return parser.parse(parametersModel);
    }
}
