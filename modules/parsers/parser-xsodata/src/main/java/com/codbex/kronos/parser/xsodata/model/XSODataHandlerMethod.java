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
package com.codbex.kronos.parser.xsodata.model;

/**
 * The text should reflect the values from
 * org.eclipse.dirigible.engine.odata2.definition.ODataHandlerMethods
 */
public enum XSODataHandlerMethod {

    /** The create. */
    CREATE("create", "sap:creatable"),

    /** The update. */
    UPDATE("update", "sap:updatable"),

    /** The delete. */
    DELETE("delete", "sap:deletable");

    /** The odata handler type. */
    private final String odataHandlerType;

    /** The odata SAP annotation. */
    private final String odataSAPAnnotation;

    /**
     * Instantiates a new HDBXSO data handler method.
     *
     * @param odataHandlerType the odata handler type
     * @param odataSAPAnnotation the odata SAP annotation
     */
    XSODataHandlerMethod(String odataHandlerType, String odataSAPAnnotation) {
        this.odataHandlerType = odataHandlerType;
        this.odataSAPAnnotation = odataSAPAnnotation;
    }

    /**
     * Gets the odata handler type.
     *
     * @return the odata handler type
     */
    public String getOdataHandlerType() {
        return odataHandlerType;
    }

    /**
     * Gets the odata SAP annotation.
     *
     * @return the odata SAP annotation
     */
    public String getOdataSAPAnnotation() {
        return odataSAPAnnotation;
    }

    /**
     * Value.
     *
     * @return the string
     */
    public String value() {
        return name();
    }
}
