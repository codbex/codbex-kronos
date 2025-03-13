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

import com.codbex.kronos.KronosIntegrationTest;
import com.codbex.kronos.engine.xsjs.service.XsjsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Class KronosSuiteTest.
 */
class KronosSuiteTest extends KronosIntegrationTest {

    /** The xsjs service. */
    @Autowired
    private XsjsService xsjsService;

    /**
     * Execute database test.
     */
    @Test
    public void executeDatabaseTest() {
        xsjsService.handleRequest("test", "kronos/db/api/db.xsjs", null, null, false);
    }

    /**
     * Execute connection test.
     */
    @Test
    public void executeConnectionTest() {
        xsjsService.handleRequest("test", "kronos/db/api/connection.xsjs", null, null, false);
    }

    /**
     * Execute parameter metadata test.
     */
    @Test
    public void executeParameterMetadataTest() {
        xsjsService.handleRequest("test", "kronos/db/api/parameter-metadata.xsjs", null, null, false);
    }

    /**
     * Execute callable statement test.
     */
    @Test
    public void executeCallableStatementTest() {
        xsjsService.handleRequest("test", "kronos/db/api/callable-statement.xsjs", null, null, false);
    }

    /**
     * Execute prepared statement test.
     */
    @Test
    public void executePreparedStatementTest() {
        xsjsService.handleRequest("test", "kronos/db/api/prepared-statement.xsjs", null, null, false);
    }

    /**
     * Execute result set test.
     */
    @Test
    public void executeResultSetTest() {
        xsjsService.handleRequest("test", "kronos/db/api/result-set.xsjs", null, null, false);
    }

    /**
     * Execute result set metadata test.
     */
    @Test
    public void executeResultSetMetadataTest() {
        xsjsService.handleRequest("test", "kronos/db/api/resultset-metadata.xsjs", null, null, false);
    }

    /**
     * Execute response test.
     *
     * @throws Exception the exception
     */
    @Test
    public void executeResponseTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/response/response.xsjs", null, null, false);
    }

    /**
     * Execute session test.
     */
    @Test
    public void executeSessionTest() {
        xsjsService.handleRequest("test", "kronos/session/session.xsjs", null, null, false);
    }

    /**
     * Execute trace test.
     */
    @Test
    public void executeTraceTest() {
        xsjsService.handleRequest("test", "kronos/trace/trace.xsjs", null, null, false);
    }

    /**
     * Execute util test.
     */
    @Test
    public void executeUtilTest() {
        xsjsService.handleRequest("test", "kronos/util/util.xsjs", null, null, false);
    }

    /**
     * Execute util codec test.
     */
    @Test
    public void executeUtilCodecTest() {
        xsjsService.handleRequest("test", "kronos/util/codec/codec.xsjs", null, null, false);
    }

    /**
     * Execute http test.
     */
    @Test
    public void executeHttpTest() {
        xsjsService.handleRequest("test", "kronos/http/http.xsjs", null, null, false);
    }

    /**
     * Execute net test.
     */
    @Test
    public void executeNetTest() {
        xsjsService.handleRequest("test", "kronos/net/net.xsjs", null, null, false);
    }

    /**
     * Execute HDB column metadata test.
     */
    @Test
    public void executeHDBColumnMetadataTest() {
        xsjsService.handleRequest("test", "kronos/hdb/api/column-metadata.xsjs", null, null, false);
    }

    /**
     * Execute HDB connection execute query test.
     */
    @Test
    public void executeHDBConnectionExecuteQueryTest() {
        xsjsService.handleRequest("test", "kronos/hdb/connection-execute-query.xsjs", null, null, false);
    }

    /**
     * Execute HDB connection execute update test.
     */
    @Test
    public void executeHDBConnectionExecuteUpdateTest() throws Exception {
        xsjsService.handleRequest("test", "kronos/hdb/connection-execute-update.xsjs", null, null, false);
    }

    /**
     * Execute HDB result set test.
     */
    @Test
    public void executeHDBResultSetTest() {
        xsjsService.handleRequest("test", "kronos/hdb/result-set.xsjs", null, null, false);
    }

    /**
     * Execute HDB result set metadata test.
     */
    @Test
    public void executeHDBResultSetMetadataTest() {
        xsjsService.handleRequest("test", "kronos/hdb/resultset-metadata.xsjs", null, null, false);
    }
}
