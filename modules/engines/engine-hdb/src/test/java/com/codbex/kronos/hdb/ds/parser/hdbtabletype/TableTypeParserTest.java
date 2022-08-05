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
package com.codbex.kronos.hdb.ds.parser.hdbtabletype;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.DataStructureHDBTableTypeModel;
import com.codbex.kronos.hdb.ds.parser.hdbtabletype.HDBTableTypeParser;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(value = Parameterized.class)
public class TableTypeParserTest {

    private final HDBTableTypeParser tableTypeParser = new HDBTableTypeParser();
    private final boolean isTableTypeWithComments;

    public TableTypeParserTest(boolean isTableTypeWithComments) {
        this.isTableTypeWithComments = isTableTypeWithComments;
    }

    @Parameters(name = "isTableTypeWithComments")
    public static Collection<Object> isTableTypeWithComments() {
        return List.of(true, false);
    }

    private static String getTableTypeContentFromResources(String tableTypeFile) {
        InputStream tableTypeFileInputStream = TableTypeParserTest.class.getResourceAsStream("/table-types/" + tableTypeFile);
        if (tableTypeFileInputStream == null) {
            throw tableTypeFileCouldNotBeRead(tableTypeFile);
        }

        try {
            return IOUtils.toString(tableTypeFileInputStream, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw tableTypeFileCouldNotBeRead(tableTypeFile);
        }
    }

    private static RuntimeException tableTypeFileCouldNotBeRead(String tableTypeFile) {
        return new RuntimeException("Table type file '" + tableTypeFile + "' could not be found.");
    }

    @Test
    public void testParsedXSAdvancedTableTypeSchemaAndNameAreExtracted()
            throws DataStructuresException, ArtifactParserException, IOException {
        String tableTypeFile = (isTableTypeWithComments) ? "tableTypeWithCommentsAndSchemaAndName.hdbtabletype" : "tableTypeWithSchemaAndName.hdbtabletype";
        String tableTypeContent = getTableTypeContentFromResources(tableTypeFile);
        DataStructureParametersModel parametersModel = new DataStructureParametersModel(null, tableTypeFile, tableTypeContent, null);

        DataStructureHDBTableTypeModel parsedModel = tableTypeParser.parse(parametersModel);
        assertEquals("Unexpected schema found", "KRONOS_SAMPLES_HDI_CUBE", parsedModel.getSchema());
        assertEquals("Unexpected name found", "customer_sample::publishers", parsedModel.getName());
    }

    @Test
    public void testParsedXSAdvancedTableTypeNameIsExtractedWhenNoSchemaIsSet()
            throws DataStructuresException, ArtifactParserException, IOException {
        String tableTypeFile =
                (isTableTypeWithComments) ? "tableTypeWithCommentsAndNoSchema.hdbtabletype" : "tableTypeWithNoSchema.hdbtabletype";
        String tableTypeContent = getTableTypeContentFromResources(tableTypeFile);
        DataStructureParametersModel parametersModel = new DataStructureParametersModel(null, tableTypeFile, tableTypeContent, null);

        DataStructureHDBTableTypeModel parsedModel = tableTypeParser.parse(parametersModel);

        assertNull("Unexpected schema found", parsedModel.getSchema());
        assertEquals("Unexpected name found", "customer_sample::publishers", parsedModel.getName());
    }


}
