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
package com.codbex.kronos.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * The Class Utils.
 */
public class Utils {

    /**
     * Instantiates a new utils.
     */
    private Utils() {

    }

    /**
     * Object to byte array.
     *
     * @param object the object
     * @return the byte[]
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static byte[] objectToByteArray(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            out.flush();

            return bos.toByteArray();
        }
    }

    /**
     * Byte array to object.
     *
     * @param <T> the generic type
     * @param byteArray the byte array
     * @return the t
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T byteArrayToObject(byte[] byteArray) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(byteArray); ObjectInput in = new ObjectInputStream(bis)) {
            return (T) in.readObject();
        }
    }

    /**
     * Convert to full path.
     *
     * @param filePath the file path
     * @return the string
     */
    public static String convertToFullPath(String filePath) {
        if (!filePath.startsWith("/registry/public")) {
            return "/registry/public" + filePath;
        }
        return filePath;
    }

}
