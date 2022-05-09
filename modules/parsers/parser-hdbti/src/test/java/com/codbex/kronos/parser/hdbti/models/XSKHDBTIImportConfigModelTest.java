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
package com.codbex.kronos.parser.hdbti.models;

import org.junit.Test;

import com.codbex.kronos.parser.hdbti.models.XSKHDBTIImportConfigModel;
import com.codbex.kronos.parser.hdbti.models.XSKHDBTIImportModel;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class XSKHDBTIImportConfigModelTest {

    @Test
    public void testToStringSuccessfully() {
        XSKHDBTIImportConfigModel impModel = new XSKHDBTIImportConfigModel();
        impModel.setFileName("/acme/ti2/demo/myData.csv");
        impModel.setDelimEnclosing("\\\"");
        impModel.setDelimField(";");
        impModel.setHeader(false);
        impModel.setSchemaName("mySchema");
        impModel.setDistinguishEmptyFromNull(true);
        impModel.setTableName("myTable");
        impModel.setUseHeaderNames(false);
        XSKHDBTIImportConfigModel.Pair pair = new XSKHDBTIImportConfigModel.Pair("GROUP_TYPE", new ArrayList<>((Collections.singletonList("BW_CUBE"))));
        impModel.getKeys().add(pair);
        pair = new XSKHDBTIImportConfigModel.Pair("MAIN", new ArrayList<>((Collections.singletonList("BW_PSA"))));
        impModel.getKeys().add(pair);
        XSKHDBTIImportModel model = new XSKHDBTIImportModel();
        model.setConfigModels(Collections.singletonList(impModel));

        String expectedString = "import = [\n" +
                "{\n" +
                "\tdelimEnclosing=\"\\\"\";\n" +
                "\tschema = \"mySchema\";\n" +
                "\tdistinguishEmptyFromNull = true;\n" +
                "\theader = false;\n" +
                "\ttable = \"myTable\";\n" +
                "\tuseHeaderNames = false;\n" +
                "\tdelimField = \";\";\n" +
                "\tkeys = [\"GROUP_TYPE\":\"BW_CUBE\", \"MAIN\":\"BW_PSA\"];\n" +
                "\tfile = \"/acme/ti2/demo/myData.csv\";\n" +
                "}];";
        assertEquals(expectedString, model.toString());
    }

}
