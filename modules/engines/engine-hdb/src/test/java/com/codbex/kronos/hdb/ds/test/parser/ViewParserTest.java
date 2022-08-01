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

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureModelFactory;
import com.codbex.kronos.hdb.ds.model.hdbview.HDBViewDataStructureModel;

public class ViewParserTest extends AbstractDirigibleTest {

    @Test
    public void parseHanaXSClassicContentSuccessfully() throws Exception {
        String hdbviewSample = org.apache.commons.io.IOUtils
                .toString(ViewParserTest.class.getResourceAsStream("/ItemsByOrderHANAv1.hdbview"), StandardCharsets.UTF_8);
        HDBViewDataStructureModel model = DataStructureModelFactory.parseView("/ItemsByOrderHANAv1.hdbview", hdbviewSample);
        assertEquals("MYSCHEMA", model.getSchema());
        assertEquals(2, model.getDependsOn().size());
        assertEquals("acme.com.test.tables::MY_TABLE1", model.getDependsOn().get(0));
        assertEquals("acme.com.test.views::MY_VIEW1", model.getDependsOn().get(1));
        assertEquals(1, model.getDependsOnTable().size());
        assertEquals("acme.com.test.tables::MY_TABLE1", model.getDependsOnTable().get(0));
        assertEquals(1, model.getDependsOnView().size());
        assertEquals("acme.com.test.views::MY_VIEW1", model.getDependsOnView().get(0));
        assertEquals(
                "SELECT T1.\"Column2\" FROM \"MYSCHEMA\".\"acme.com.test.tables::MY_TABLE1\" AS T1 LEFT JOIN \"acme.com.test.views::MY_VIEW1\" AS T2 ON T1.\"Column1\" = T2.\"Column1\"",
                model.getQuery());
        assertEquals(DBContentType.XS_CLASSIC, model.getDBContentType());
        assertEquals(4, model.getDependencies().size());
        assertEquals("acme.com.test.tables::MY_TABLE1", model.getDependencies().get(0).getName());
        assertEquals("none", model.getDependencies().get(0).getType());
        assertEquals("acme.com.test.views::MY_VIEW1", model.getDependencies().get(1).getName());
        assertEquals("none", model.getDependencies().get(1).getType());
        assertEquals("acme.com.test.tables::MY_TABLE1", model.getDependencies().get(2).getName());
        assertEquals("TABLE", model.getDependencies().get(2).getType());
        assertEquals("acme.com.test.views::MY_VIEW1", model.getDependencies().get(3).getName());
        assertEquals("VIEW", model.getDependencies().get(3).getType());
    }

    @Test
    public void parseHanaXSAdvancedContentSuccessfully() throws Exception {
        String hdbviewSample = org.apache.commons.io.IOUtils
                .toString(ViewParserTest.class.getResourceAsStream("/ItemsByOrderHANAv2.hdbview"), StandardCharsets.UTF_8);
        HDBViewDataStructureModel model = DataStructureModelFactory.parseView("hdb_view/db/ItemsByOrderHANAv2.hdbview", hdbviewSample);
        assertEquals(DBContentType.OTHERS, model.getDBContentType());
        assertEquals(hdbviewSample, model.getRawContent());
    }

    @Test
    public void parseHanaXSAdvancedContentNoQuotesSuccessfully() throws Exception {
        String content = " view toni AS SELECT OD_NAME FROM KRONOS_ODATA";
        HDBViewDataStructureModel model = DataStructureModelFactory.parseView("hdb_view/db/ItemsByOrderHANAv2.hdbview", content);
        assertEquals(DBContentType.OTHERS, model.getDBContentType());
        assertEquals(content, model.getRawContent());
    }

    @Test(expected = ArtifactParserException.class)
    public void parseHanaXSClassicContentWithLexerErrorFail() throws Exception {
        String content = "schema = \"MY_SCHEMA\";\n" +
                "query = \"\";" +
                "depends_on_table = [SAEWQ.\"sap.test.db.basis::t_table_name\"];";
        DataStructureModelFactory.parseView("hdb_view/db/test.hdbview", content);
    }

    @Test(expected = ArtifactParserException.class)
    public void parseHanaXSClassicContentWithSyntaxErrorFail() throws Exception {
        String content = "schema = ;\n" +
                "query = \"\";" +
                "depends_on_table = [\"sap.test.db.basis::t_table_name\"];";
        DataStructureModelFactory.parseView("hdb_view/db/test.hdbview", content);
    }
}