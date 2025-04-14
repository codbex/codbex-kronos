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
package com.codbex.kronos.parser.hdbti.models;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class HDBTIImportConfigModelTest {

    @Test
    public void testToStringSuccessfully() {
        HDBTIImportConfigModel impModel = new HDBTIImportConfigModel();
        impModel.setFileName("/sap/ti2/demo/myData.csv");
        impModel.setDelimEnclosing("\\\"");
        impModel.setDelimField(";");
        impModel.setHeader(false);
        impModel.setSchemaName("mySchema");
        impModel.setDistinguishEmptyFromNull(true);
        impModel.setTableName("myTable");
        impModel.setUseHeaderNames(false);
        HDBTIImportConfigModel.Pair pair =
                new HDBTIImportConfigModel.Pair("GROUP_TYPE", new ArrayList<>((Collections.singletonList("BW_CUBE"))));
        impModel.getKeys()
                .add(pair);
        pair = new HDBTIImportConfigModel.Pair("MAIN", new ArrayList<>((Collections.singletonList("BW_PSA"))));
        impModel.getKeys()
                .add(pair);
        HDBTIImportModel model = new HDBTIImportModel();
        model.setConfigModels(Collections.singletonList(impModel));

        String expectedString = "import = [\n" + "{\n" + "\tdelimEnclosing=\"\\\"\";\n" + "\tschema = \"mySchema\";\n"
                + "\tdistinguishEmptyFromNull = true;\n" + "\theader = false;\n" + "\ttable = \"myTable\";\n"
                + "\tuseHeaderNames = false;\n" + "\tdelimField = \";\";\n"
                + "\tkeys = [\"GROUP_TYPE\":\"BW_CUBE\", \"MAIN\":\"BW_PSA\"];\n" + "\tfile = \"/sap/ti2/demo/myData.csv\";\n" + "}];";
        assertEquals(expectedString, model.toString());
    }

}
