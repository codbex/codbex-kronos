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
package com.codbex.kronos.engine.hdb.parser;

import java.io.IOException;

import org.jetbrains.annotations.NotNull;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.domain.HDBTableFunction;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureParser;
import com.codbex.kronos.engine.hdb.parser.HDBParameters;
import com.codbex.kronos.exceptions.ArtifactParserException;

/**
 * The Class TestTableFunctionParser.
 */
public class TestTableFunctionParser {
	
    /** The test content provider. */
    TestContentProvider testContentProvider = new TestContentProvider();
    
    /**
	 * Parses the table function.
	 *
	 * @param parser   the parser
	 * @param location the location
	 * @param content  the content
	 * @return the HDB table function
	 * @throws DataStructuresException the data structures exception
	 * @throws ArtifactParserException the artifact parser exception
	 * @throws IOException             Signals that an I/O exception has occurred.
	 */
    public HDBTableFunction parseTableFunction(@NotNull HDBDataStructureParser<HDBTableFunction> parser,
        String location, String content)
            throws DataStructuresException, ArtifactParserException, IOException {
        HDBParameters parametersModel =
                testContentProvider.getParametersModel(null, location, content, null);
        return parser.parse(parametersModel);
    }
}
