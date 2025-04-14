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
package com.codbex.kronos.engine.hdb.parser.hdbprocedure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import com.codbex.kronos.engine.hdb.domain.HDBProcedure;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;

import jakarta.transaction.Transactional;

/**
 * The Class HDBProcedureDataStructureParserTest.
 */
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@Transactional
@Disabled
public class HDBProcedureDataStructureParserTest {

    /**
     * Check model.
     *
     * @throws Exception the exception
     */
    @Test
    public void checkModel() throws Exception {
        String fileContent = org.apache.commons.io.IOUtils.toString(
                HDBProcedureDataStructureParserTest.class.getResourceAsStream("/registry/public/procedure/OrderProcedure.hdbprocedure"),
                StandardCharsets.UTF_8);

        HDBProcedure parsedModel = HDBDataStructureModelFactory.parseProcedure("procedure/OrderProcedure.hdbprocedure", fileContent);
        assertEquals("MYSCHEMA", parsedModel.getSchema(), "Unexpected hdbprocedure schema.");
        assertEquals("hdb_view::OrderProcedure", parsedModel.getName(), "Unexpected hdbprocedure name.");
        assertEquals("procedure/OrderProcedure.hdbprocedure", parsedModel.getLocation(), "Unexpected hdbprocedure location.");
        assertEquals("HDBPROCEDURE", parsedModel.getType(), "Unexpected hdbprocedure type.");
        // assertEquals("Unexpected hdbprocedure dependencies.", 0, parsedModel.getDependencies().size());
        assertNull(parsedModel.getCreatedAt(), "Null value for hdbprocedure createdAt");
        assertNull(parsedModel.getCreatedBy(), "Null value for hdbprocedure createdBy");
    }

}
