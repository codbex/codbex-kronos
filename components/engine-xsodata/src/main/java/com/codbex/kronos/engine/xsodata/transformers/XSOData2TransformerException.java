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
package com.codbex.kronos.engine.xsodata.transformers;

/**
 * The {@link XSOData2TransformerException} is thrown in situations when there is an issue when
 * transforming odata file.
 */
public class XSOData2TransformerException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new transformer exception.
     */
    public XSOData2TransformerException() {
        super();
    }

    /**
     * Instantiates a new transformer exception.
     *
     * @param message the message
     */
    public XSOData2TransformerException(String message) {
        super(message);
    }

    /**
     * Instantiates a new transformer exception.
     *
     * @param ex the ex
     */
    public XSOData2TransformerException(Throwable ex) {
        super(ex);
    }

    /**
     * Instantiates a new transformer exception.
     *
     * @param message the message
     * @param ex the ex
     */
    public XSOData2TransformerException(String message, Throwable ex) {
        super(message, ex);
    }

}
