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
package com.codbex.kronos.engine.xsodata.handler;

/**
 * The Scripting OData2 Event Handler Exception.
 */
public class KronosScriptingOData2EventHandlerException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1560571830962118349L;

    /**
     * Instantiates a new scripting OData2 event handler exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public KronosScriptingOData2EventHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new scripting OData2 event handler exception.
     *
     * @param cause the cause
     */
    public KronosScriptingOData2EventHandlerException(Throwable cause) {
        super(cause);
    }

}
