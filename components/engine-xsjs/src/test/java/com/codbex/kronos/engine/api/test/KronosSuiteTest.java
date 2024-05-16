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
package com.codbex.kronos.engine.api.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.codbex.kronos.engine.xsjs.service.XsjsService;

/**
 * The Class KronosSuiteTest.
 */
@WithMockUser
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
@EntityScan(value = {"org.eclipse.dirigible.components", "com.codbex.kronos"})
public class KronosSuiteTest {

    /** The xsjs service. */
    @Autowired
    private XsjsService xsjsService;

    /** The mock mvc. */
    @Autowired
    private MockMvc mockMvc;

    /** The wac. */
    @Autowired
    protected WebApplicationContext wac;

    /**
     * Execute database test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeDatabaseTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/db/api/db.xsjs", null, null, false);
    }

    /**
     * Execute connection test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeConnectionTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/db/api/connection.xsjs", null, null, false);
    }

    /**
     * Execute parameter metadata test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeParameterMetadataTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/db/api/parameter-metadata.xsjs", null, null, false);
    }

    /**
     * Execute callable statement test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeCallableStatementTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/db/api/callable-statement.xsjs", null, null, false);
    }

    /**
     * Execute prepared statement test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executePreparedStatementTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/db/api/prepared-statement.xsjs", null, null, false);
    }

    /**
     * Execute result set test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeResultSetTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/db/api/result-set.xsjs", null, null, false);
    }

    /**
     * Execute result set metadata test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeResultSetMetadataTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/db/api/resultset-metadata.xsjs", null, null, false);
    }

    /**
     * Execute response test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeResponseTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/response/api/response.xsjs", null, null, false);
    }

    /**
     * Execute session test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeSessionTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/session/api/session.xsjs", null, null, false);
    }

    /**
     * Execute trace test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeTraceTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/trace/api/trace.xsjs", null, null, false);
    }

    /**
     * Execute util test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeUtilTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/util/api/util.xsjs", null, null, false);
    }

    /**
     * Execute util codec test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeUtilCodecTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/util/codec/api/codec.xsjs", null, null, false);
    }

    /**
     * Execute http test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeHttpTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/http/api/http.xsjs", null, null, false);
    }

    /**
     * Execute net test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeNetTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/net/api/net.xsjs", null, null, false);
    }

    /**
     * Execute HDB column metadata test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeHDBColumnMetadataTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/hdb/api/column-metadata.xsjs", null, null, false);
    }

    /**
     * Execute HDB connection execute query test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeHDBConnectionExecuteQueryTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/hdb/api/connection-execute-query.xsjs", null, null, false);
    }

    /**
     * Execute HDB connection execute update test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeHDBConnectionExecuteUpdateTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/hdb/api/connection-execute-update.xsjs", null, null, false);
    }

    /**
     * Execute HDB result set test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeHDBResultSetTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/hdb/api/result-set.xsjs", null, null, false);
    }

    /**
     * Execute HDB result set metadata test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeHDBResultSetMetadataTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/hdb/api/resultset-metadata.xsjs", null, null, false);
    }

    /**
     * The Class TestConfiguration.
     */
    @EnableJpaRepositories(basePackages = "com.codbex.kronos")
    @SpringBootApplication(scanBasePackages = {"com.codbex.kronos", "org.eclipse.dirigible.components"})
    @EnableScheduling
    static class TestConfiguration {

    }
}
