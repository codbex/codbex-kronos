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
package com.codbex.kronos.hdb.ds.test.parser;

import static org.junit.Assert.assertEquals;

import java.nio.charset.StandardCharsets;

import org.eclipse.dirigible.core.test.AbstractDirigibleTest;
import org.junit.Test;

import com.codbex.kronos.exceptions.XSKArtifactParserException;
import com.codbex.kronos.hdb.ds.model.XSKDataStructureModelFactory;
import com.codbex.kronos.hdb.ds.model.hdbschema.XSKDataStructureHDBSchemaModel;

public class XSKSchemaParserTest extends AbstractDirigibleTest {

    @Test
    public void parseHdbschemaFileSuccessfully() throws Exception {
        String hdbschemaSample = org.apache.commons.io.IOUtils
                .toString(XSKSchemaParserTest.class.getResourceAsStream("/Myschema.hdbschema"), StandardCharsets.UTF_8);
        XSKDataStructureHDBSchemaModel model = XSKDataStructureModelFactory.parseSchema("/Myschema.hdbschema", hdbschemaSample);
        assertEquals("MySchema", model.getSchema());
    }

    @Test(expected = XSKArtifactParserException.class)
    public void parseHanaXSClassicContentWithLexerErrorFail() throws Exception {
        String content = "schema_name='';";
        XSKDataStructureModelFactory.parseView("db/test.hdbschema", content);
    }

    @Test(expected = XSKArtifactParserException.class)
    public void parseHanaXSClassicContentWithSyntaxErrorFail() throws Exception {
        String content = "schema_name=";
        XSKDataStructureModelFactory.parseView("db/test.hdbschema", content);
    }
}