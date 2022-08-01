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
package com.codbex.kronos.utils;

public final class CommonsConstants {
    /**
     * The Unix separator character.
     */
    public static final char UNIX_SEPARATOR = '/';
    /**
     * The Windows separator character.
     */
    public static final char WINDOWS_SEPARATOR = '\\';
    /**
     * Problems facade constants
     */
    public static final String MODULE_PARSERS = "Parsers";
    public static final String MODULE_PROCESSORS = "Processors";
    public static final String SOURCE_PUBLISH_REQUEST = "Publish Request";
    public static final String SOURCE_DELIVERY_UNIT_MIGRATION = "Delivery Unit Migration";
    public static final String PROGRAM_KRONOS = "Kronos";
    /**
     * Problems facade error type constants
     */
    public static final String PARSER_ERROR = "PARSER";
    public static final String PROCESSOR_ERROR = "PROCESSOR";
    public static final String LEXER_ERROR = "LEXER";
    public static final String SCHEDULER_ERROR = "SCHEDULER";
    public static final String MIGRATION_ERROR = "MIGRATION";
    /**
     * Problems facade error message constants
     */
    public static final String EXPECTED_FIELDS = "Missing mandatory fields";
    /**
     * Problems facade parser and processor type constants
     */
    public static final String HDBTI_PARSER = "HDBTI";
    public static final String HDB_TABLE_PARSER = "HDB Table";
    public static final String HDB_TABLE_TYPE_PARSER = "HDB Table Type";
    public static final String HDB_SYNONYM_PARSER = "HDB Synonym";
    public static final String HDBDD_PARSER = "HDBDD";
    public static final String HDB_PROCEDURE_PARSER = "HDB Procedure";
    public static final String HDB_TABLE_FUNCTION_PARSER = "HDB Table Function";
    public static final String HDB_SCALAR_FUNCTION_PARSER = "HDB Scalar Function";
    public static final String HDB_SCHEMA_PARSER = "HDB Schema";
    public static final String HDB_SEQUENCE_PARSER = "HDB Sequence";
    public static final String HDB_VIEW_PARSER = "HDB View";
    public static final String KRONOS_JOB_PARSER = "Kronos Job";
    public static final String KRONOS_ODATA_PARSER = "XSOData";
    public static final String HDB_ENTITY_PROCESSOR = "HDB Entity";
    public static final String KRONOS_ENTITY_PROCESSOR = "Kronos Entity";
    public static final String HDI_PROCESSOR = "HDI";
    public static final String HDB_ANALYTIC_PRIVILEGE = "HDB Analytic Privilege";
    public static final String REGISTRY_PUBLIC = "/registry/public/";
}
