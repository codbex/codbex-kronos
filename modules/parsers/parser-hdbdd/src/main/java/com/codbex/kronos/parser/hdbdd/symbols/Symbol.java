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
package com.codbex.kronos.parser.hdbdd.symbols;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.codbex.kronos.parser.hdbdd.annotation.metadata.AnnotationObj;
import com.codbex.kronos.parser.hdbdd.core.CdsParser.IdentifierContext;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;

/***
 * Excerpted from "Language Implementation Patterns", published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, courses, books,
 * articles, and the like. Contact us if you are in doubt. We make no guarantees that this code is
 * fit for any purpose. Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book
 * information.
 ***/
public class Symbol { // A generic programming language symbol

    /** The full name. */
    private String fullName;

    /** The name. */
    private String name; // All com.codbex.kronos.parser.hdbdd.symbols at least have a name

    /** The scope. */
    private Scope scope;

    /** The id token. */
    private IdentifierContext idToken;

    /** The schema. */
    private String schema;

    /** The annotations. */
    private Map<String, AnnotationObj> annotations;

    /** The alias. */
    String alias;

    /**
     * Instantiates a new symbol.
     *
     * @param symbol the symbol
     */
    public Symbol(Symbol symbol) {
        this(symbol.getName(), symbol.getScope());
        this.idToken = symbol.getIdToken();
        this.schema = symbol.getSchema();
    }

    /**
     * Instantiates a new symbol.
     *
     * @param name the name
     */
    public Symbol(String name) {
        this.name = name;
        this.annotations = new HashMap<>();
    }

    /**
     * Instantiates a new symbol.
     *
     * @param name the name
     * @param scope the scope
     */
    public Symbol(String name, Scope scope) {
        this(name);
        this.scope = scope;
    }

    /**
     * Instantiates a new symbol.
     */
    public Symbol() {}

    /**
     * Instantiates a new symbol.
     *
     * @param name the name
     * @param scope the scope
     * @param idToken the id token
     * @param fullName the full name
     * @param annotations the annotations
     * @param schema the schema
     */
    public Symbol(String name, Scope scope, IdentifierContext idToken, String fullName, Map<String, AnnotationObj> annotations,
            String schema) {
        this.name = name;
        this.scope = scope;
        this.idToken = idToken;
        this.fullName = fullName;
        this.annotations = annotations;
        this.schema = schema;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the scope.
     *
     * @return the scope
     */
    public Scope getScope() {
        return scope;
    }

    /**
     * Sets the scope.
     *
     * @param scope the new scope
     */
    public void setScope(Scope scope) {
        this.scope = scope;
    }

    /**
     * Gets the id token.
     *
     * @return the id token
     */
    public IdentifierContext getIdToken() {
        return idToken;
    }

    /**
     * Sets the id token.
     *
     * @param idToken the new id token
     */
    public void setIdToken(IdentifierContext idToken) {
        this.idToken = idToken;
    }

    /**
     * Gets the full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the full name.
     *
     * @param fullName the new full name
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets the schema.
     *
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets the schema.
     *
     * @param schema the new schema
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * Gets the annotations.
     *
     * @return the annotations
     */
    public Map<String, AnnotationObj> getAnnotations() {
        return annotations;
    }

    /**
     * Adds the annotation.
     *
     * @param name the name
     * @param annotationObj the annotation obj
     */
    public void addAnnotation(String name, AnnotationObj annotationObj) {
        this.annotations.put(name, annotationObj);
    }

    /**
     * Gets the annotation.
     *
     * @param name the name
     * @return the annotation
     */
    public AnnotationObj getAnnotation(String name) {
        return this.annotations.get(name);
    }

    /**
     * Gets the alias.
     *
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Sets the alias.
     *
     * @param alias the new alias
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Equals.
     *
     * @param o the o
     * @return true, if successful
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Symbol symbol = (Symbol) o;
        return name.equals(symbol.name) && scope.equals(symbol.scope);
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, scope);
    }
}
