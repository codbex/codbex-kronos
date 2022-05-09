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
package com.codbex.kronos.xssecurestore.ds.api;

import java.util.List;

import com.codbex.kronos.xssecurestore.ds.model.XSKSecureStore;
import com.codbex.kronos.xssecurestore.ds.model.XSKSecureStoreContent;

public interface IXSKSecureStoreCoreService {

  boolean existsSecureStore(String location) throws XSKSecureStoreException;

  XSKSecureStore createSecureStore(String location, String content) throws XSKSecureStoreException;

  XSKSecureStore getSecureStore(String location) throws XSKSecureStoreException;

  List<XSKSecureStore> getXSKSecureStores() throws XSKSecureStoreException;

  void removeXSKSecureStore(String location) throws XSKSecureStoreException;

  void createSecureStoreValue(String storeId, String userId, String dataId, String value) throws XSKSecureStoreException;

  void updateSecureStoreValue(XSKSecureStoreContent xskSecureStoreContent) throws XSKSecureStoreException;

  XSKSecureStoreContent findSecureStoreContent(String storeId, String userId, String dataId) throws XSKSecureStoreException;

  void deleteSecureStoreValue(String storeId, String userId, String dataId) throws XSKSecureStoreException;

  void deleteSecureStoreValuesByStoreId(String storeId) throws XSKSecureStoreException;

  boolean existsSecureStoreContent(String storeId, String userId, String dataId) throws XSKSecureStoreException;
}
