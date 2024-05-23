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
package com.codbex.kronos.parser.hdbdd.symbols.annotation;

import java.util.ArrayList;
import java.util.List;

import com.codbex.kronos.parser.hdbdd.symbols.Symbol;
import com.codbex.kronos.parser.hdbdd.symbols.context.Scope;

/**
 * The Class EnumSymbol.
 */
public class EnumSymbol extends Symbol {

    /** The members. */
    private List<String> members = new ArrayList<>();

    /**
     * Instantiates a new enum symbol.
     *
     * @param name the name
     */
    public EnumSymbol(String name) {
        super(name);
    }

    /**
     * Instantiates a new enum symbol.
     *
     * @param name the name
     * @param scope the scope
     */
    public EnumSymbol(String name, Scope scope) {
        super(name, scope);
    }

    /**
     * Adds the member.
     *
     * @param member the member
     */
    public void addMember(String member) {
        this.members.add(member);
    }

    /**
     * Gets the members.
     *
     * @return the members
     */
    public List<String> getMembers() {
        return members;
    }
}
