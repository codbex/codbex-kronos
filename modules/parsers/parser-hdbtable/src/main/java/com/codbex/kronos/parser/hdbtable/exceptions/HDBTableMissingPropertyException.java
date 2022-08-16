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
package com.codbex.kronos.parser.hdbtable.exceptions;

/**
 * The Class HDBTableMissingPropertyException.
 */
public class HDBTableMissingPropertyException extends RuntimeException {
    
    /**
     * Instantiates a new HDB table missing property exception.
     *
     * @param message the message
     */
    public HDBTableMissingPropertyException(String message) {
        super(message);
    }
}
