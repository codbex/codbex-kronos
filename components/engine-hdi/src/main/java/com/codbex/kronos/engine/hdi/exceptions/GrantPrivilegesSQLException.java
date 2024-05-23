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
package com.codbex.kronos.engine.hdi.exceptions;

/**
 * The Class GrantPrivilegesSQLException.
 */
public class GrantPrivilegesSQLException extends RuntimeException {

    /**
     * Instantiates a new grant privileges SQL exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public GrantPrivilegesSQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
