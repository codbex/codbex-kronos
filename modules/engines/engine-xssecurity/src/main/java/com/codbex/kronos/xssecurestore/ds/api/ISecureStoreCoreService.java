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

import com.codbex.kronos.xssecurestore.ds.model.SecureStore;
import com.codbex.kronos.xssecurestore.ds.model.SecureStoreContent;
import java.util.List;

public interface ISecureStoreCoreService {

  boolean existsSecureStore(String location) throws SecureStoreException;

  SecureStore createSecureStore(String location, String content) throws SecureStoreException;

  SecureStore getSecureStore(String location) throws SecureStoreException;

  List<SecureStore> getSecureStores() throws SecureStoreException;

  void removeSecureStore(String location) throws SecureStoreException;

  void createSecureStoreValue(String storeId, String userId, String dataId, String value) throws SecureStoreException;

  void updateSecureStoreValue(SecureStoreContent secureStoreContent) throws SecureStoreException;

  SecureStoreContent findSecureStoreContent(String storeId, String userId, String dataId) throws SecureStoreException;

  void deleteSecureStoreValue(String storeId, String userId, String dataId) throws SecureStoreException;

  void deleteSecureStoreValuesByStoreId(String storeId) throws SecureStoreException;

  boolean existsSecureStoreContent(String storeId, String userId, String dataId) throws SecureStoreException;
}
