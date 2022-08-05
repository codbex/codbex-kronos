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
package com.codbex.kronos.xssecurestore.ds.service;

import org.eclipse.dirigible.api.v3.utils.Base64Facade;

import com.codbex.kronos.xssecurestore.ds.api.ISecureStoreEncryptor;

public class SecureStoreEncryptor implements ISecureStoreEncryptor {

  @Override
  public byte[] encode(byte[] input) {
    return Base64Facade.encodeNative(input);
  }

  @Override
  public byte[] decode(byte[] input) {
    return Base64Facade.decodeNative(input);
  }
}
