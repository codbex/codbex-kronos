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
package com.codbex.kronos.exceptions;

/**
 * The Data Structures Exception.
 */
public class ArtifactParserException extends Exception {

    /**
     * Instantiates a new data structures exception.
     */
    public ArtifactParserException() {
        super();
    }

    /**
     * Instantiates a new data structures exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public ArtifactParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Instantiates a new data structures exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public ArtifactParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new data structures exception.
     *
     * @param message the message
     */
    public ArtifactParserException(String message) {
        super(message);
    }

    /**
     * Instantiates a new data structures exception.
     *
     * @param cause the cause
     */
    public ArtifactParserException(Throwable cause) {
        super(cause);
    }

}
