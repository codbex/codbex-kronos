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
package com.codbex.kronos.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class XSKUtils {

  private XSKUtils() {

  }

  public static byte[] objectToByteArray(Object object) throws IOException {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); ObjectOutput out = new ObjectOutputStream(bos)) {
      out.writeObject(object);
      out.flush();

      return bos.toByteArray();
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T byteArrayToObject(byte[] byteArray) throws IOException, ClassNotFoundException {
    try (ByteArrayInputStream bis = new ByteArrayInputStream(byteArray);
        ObjectInput in = new ObjectInputStream(bis)) {
      return (T) in.readObject();
    }
  }

  public static String convertToFullPath(String filePath) {
    if (!filePath.startsWith("/registry/public")) {
      return "/registry/public" + filePath;
    }
    return filePath;
  }


}
