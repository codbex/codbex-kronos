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
package com.codbex.kronos.xssecurestore.ds.facade;

import com.codbex.kronos.xssecurestore.ds.api.ISecureStoreModel;
import com.codbex.kronos.xssecurestore.ds.api.SecureStoreException;
import com.codbex.kronos.xssecurestore.ds.model.SecureStoreContent;
import com.codbex.kronos.xssecurestore.ds.service.SecureStoreCoreService;
import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.commons.api.scripting.IScriptingFacade;

public class SecureStoreFacade implements IScriptingFacade {

  private static final SecureStoreCoreService secureStoreCoreService = new SecureStoreCoreService();

  public SecureStoreFacade() {
  }

  public static final void store(String storeId, String dataId, String value) throws SecureStoreException {
    secureStoreCoreService.createSecureStoreValue(storeId, ISecureStoreModel.VALUE_APP_USER, dataId, value);
  }

  public static final boolean existsStore(String storeId) throws SecureStoreException {
    return secureStoreCoreService.existsSecureStore(storeId);
  }

  public static final String read(String storeId, String dataId) throws SecureStoreException {
    SecureStoreContent secureStoreContent = secureStoreCoreService
        .findSecureStoreContent(storeId, ISecureStoreModel.VALUE_APP_USER, dataId);

    if (secureStoreContent == null) {
      return null;
    }

    return new String(secureStoreContent.getDataValue());
  }

  public static final void remove(String storeId, String dataId) throws SecureStoreException {
    secureStoreCoreService.deleteSecureStoreValue(storeId, ISecureStoreModel.VALUE_APP_USER, dataId);
  }

  public static final String readForUser(String storeId, String dataId) throws SecureStoreException {
    SecureStoreContent secureStoreContent = secureStoreCoreService.findSecureStoreContent(storeId, UserFacade.getName(), dataId);

    if (secureStoreContent == null) {
      return null;
    }

    return new String(secureStoreContent.getDataValue());
  }

  public static final void storeForUser(String storeId, String dataId, String value) throws SecureStoreException {
    secureStoreCoreService.createSecureStoreValue(storeId, UserFacade.getName(), dataId, value);
  }

  public static final void removeForUser(String storeId, String dataId) throws SecureStoreException {
    secureStoreCoreService.deleteSecureStoreValue(storeId, UserFacade.getName(), dataId);
  }
}
