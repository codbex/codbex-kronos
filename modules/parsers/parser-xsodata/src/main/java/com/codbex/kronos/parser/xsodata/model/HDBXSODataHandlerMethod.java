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
package com.codbex.kronos.parser.xsodata.model;

/**
 * The text should reflect the values from org.eclipse.dirigible.engine.odata2.definition.ODataHandlerMethods
 */
public enum HDBXSODataHandlerMethod {
    CREATE("create", "sap:creatable"),
    UPDATE("update", "sap:updatable"),
    DELETE("delete", "sap:deletable");

    private final String odataHandlerType;

    private final String odataSAPAnnotation;

    HDBXSODataHandlerMethod(String odataHandlerType, String odataSAPAnnotation) {
        this.odataHandlerType = odataHandlerType;
        this.odataSAPAnnotation = odataSAPAnnotation;
    }

    public String getOdataHandlerType() {
        return odataHandlerType;
    }

    public String getOdataSAPAnnotation() {
        return odataSAPAnnotation;
    }

    public String value() {
        return name();
    }
}