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
package com.codbex.kronos.parser.hdbdd.symbols.type.field;

import java.util.Map;

import com.codbex.kronos.parser.hdbdd.annotation.metadata.AnnotationObj;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.IdentifierContext;
import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;
import com.codbex.kronos.parser.hdbdd.symbols.type.Type;
import com.codbex.kronos.parser.hdbdd.symbols.type.custom.DataTypeSymbol;

/**
 * The Class FieldSymbol.
 */
public class FieldSymbol extends Symbol implements Typeable {

    /** The type. */
    private Type type;

    /** The reference. */
    private String reference;

    /**
     * Instantiates a new field symbol.
     *
     * @param name the name
     */
    public FieldSymbol(String name) {
        super(name);
    }

    /**
     * Instantiates a new field symbol.
     *
     * @param name the name
     * @param scope the scope
     */
    public FieldSymbol(String name, Scope scope) {
        super(name, scope);
    }

    /**
     * Instantiates a new field symbol.
     *
     * @param type the type
     * @param reference the reference
     * @param name the name
     * @param scope the scope
     * @param idToken the id token
     * @param fullName the full name
     * @param annotations the annotations
     * @param schema the schema
     */
    public FieldSymbol(Type type, String reference, String name, Scope scope, IdentifierContext idToken, String fullName,
            Map<String, AnnotationObj> annotations, String schema) {
        super(name, scope, idToken, fullName, annotations, schema);
        this.type = type;
        this.reference = reference;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public Type getType() {
        if (type instanceof DataTypeSymbol) {
            return ((DataTypeSymbol) type).getType();
        } else {
            return type;
        }
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    @Override
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Gets the reference.
     *
     * @return the reference
     */
    @Override
    public String getReference() {
        return reference;
    }

    /**
     * Sets the reference.
     *
     * @param token the new reference
     */
    @Override
    public void setReference(String token) {
        this.reference = token;
    }
}
