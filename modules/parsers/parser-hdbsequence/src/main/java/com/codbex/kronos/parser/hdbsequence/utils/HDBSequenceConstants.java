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
package com.codbex.kronos.parser.hdbsequence.utils;

/**
 * The Class HDBSequenceConstants.
 */
public final class HDBSequenceConstants {

    /** The Constant SCHEMA_PROPERTY. */
    public static final String SCHEMA_PROPERTY = "schema";

    /** The Constant INCREMENT_BY_PROPERTY. */
    public static final String INCREMENT_BY_PROPERTY = "increment_by";

    /** The Constant START_WITH_PROPERTY. */
    public static final String START_WITH_PROPERTY = "start_with";

    /** The Constant MAXVALUE_PROPERTY. */
    public static final String MAXVALUE_PROPERTY = "maxvalue";

    /** The Constant NOMAXVALUE_PROPERTY. */
    public static final String NOMAXVALUE_PROPERTY = "nomaxvalue";

    /** The Constant MINVALUE_PROPERTY. */
    public static final String MINVALUE_PROPERTY = "minvalue";

    /** The Constant NOMINVALUE_PROPERTY. */
    public static final String NOMINVALUE_PROPERTY = "nominvalue";

    /** The Constant CYCLES_PROPERTY. */
    public static final String CYCLES_PROPERTY = "cycles";

    /** The Constant RESET_BY_PROPERTY. */
    public static final String RESET_BY_PROPERTY = "reset_by";

    /** The Constant PUBLIC_PROPERTY. */
    public static final String PUBLIC_PROPERTY = "public";

    /** The Constant DEPENDS_ON_TABLE_PROPERTY. */
    public static final String DEPENDS_ON_TABLE_PROPERTY = "depends_on_table";

    /** The Constant DEPENDS_ON_VIEW_PROPERTY. */
    public static final String DEPENDS_ON_VIEW_PROPERTY = "depends_on_view";

    /** The Constant DEPENDS_ON_PROPERTY. */
    public static final String DEPENDS_ON_PROPERTY = "depends_on";

    /** The Constant INCREMENT_BY_DEFAULT_VALUE. */
    public static final int INCREMENT_BY_DEFAULT_VALUE = 1;

    /** The Constant START_WITH_DEFAULT_VALUE. */
    public static final int START_WITH_DEFAULT_VALUE = 1;

    /** The Constant MIN_DEFAULT_VALUE. */
    public static final int MIN_DEFAULT_VALUE = 1;

    /** The Constant NOMAXVALUE_DEFAULT_VALUE. */
    public static final boolean NOMAXVALUE_DEFAULT_VALUE = false;

    /** The Constant NOMINVALUE_DEFAULT_VALUE. */
    public static final boolean NOMINVALUE_DEFAULT_VALUE = false;

    /** The Constant PUBLIC_DEFAULT_VALUE. */
    public static final boolean PUBLIC_DEFAULT_VALUE = false;

    /**
     * Instantiates a new HDB sequence constants.
     */
    private HDBSequenceConstants() {}

}
