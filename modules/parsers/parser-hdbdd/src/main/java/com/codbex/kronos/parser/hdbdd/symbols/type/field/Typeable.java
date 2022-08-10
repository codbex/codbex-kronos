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
package com.codbex.kronos.parser.hdbdd.symbols.type.field;

import com.codbex.kronos.parser.hdbdd.symbols.type.Type;

/**
 * The Interface Typeable.
 */
public interface Typeable {
    
    /**
     * Gets the type.
     *
     * @return the type
     */
    Type getType();

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    void setType(Type type);

    /**
     * Gets the reference.
     *
     * @return the reference
     */
    String getReference();

    /**
     * Sets the reference.
     *
     * @param token the new reference
     */
    void setReference(String token);
}
