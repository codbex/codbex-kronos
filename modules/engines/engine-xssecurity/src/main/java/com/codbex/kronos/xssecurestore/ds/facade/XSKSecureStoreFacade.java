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

import com.codbex.kronos.xssecurestore.ds.api.IXSKSecureStoreModel;
import com.codbex.kronos.xssecurestore.ds.api.XSKSecureStoreException;
import com.codbex.kronos.xssecurestore.ds.model.XSKSecureStoreContent;
import com.codbex.kronos.xssecurestore.ds.service.XSKSecureStoreCoreService;

import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.commons.api.scripting.IScriptingFacade;

public class XSKSecureStoreFacade implements IScriptingFacade {

  private static final XSKSecureStoreCoreService xskSecureStoreCoreService = new XSKSecureStoreCoreService();

  public XSKSecureStoreFacade() {
  }

  public static final void store(String storeId, String dataId, String value) throws XSKSecureStoreException {
    xskSecureStoreCoreService.createSecureStoreValue(storeId, IXSKSecureStoreModel.VALUE_APP_USER, dataId, value);
  }

  public static final boolean existsStore(String storeId) throws XSKSecureStoreException {
    return xskSecureStoreCoreService.existsSecureStore(storeId);
  }

  public static final String read(String storeId, String dataId) throws XSKSecureStoreException {
    XSKSecureStoreContent xskSecureStoreContent = xskSecureStoreCoreService
        .findSecureStoreContent(storeId, IXSKSecureStoreModel.VALUE_APP_USER, dataId);

    if (xskSecureStoreContent == null) {
      return null;
    }

    return new String(xskSecureStoreContent.getDataValue());
  }

  public static final void remove(String storeId, String dataId) throws XSKSecureStoreException {
    xskSecureStoreCoreService.deleteSecureStoreValue(storeId, IXSKSecureStoreModel.VALUE_APP_USER, dataId);
  }

  public static final String readForUser(String storeId, String dataId) throws XSKSecureStoreException {
    XSKSecureStoreContent xskSecureStoreContent = xskSecureStoreCoreService.findSecureStoreContent(storeId, UserFacade.getName(), dataId);

    if (xskSecureStoreContent == null) {
      return null;
    }

    return new String(xskSecureStoreContent.getDataValue());
  }

  public static final void storeForUser(String storeId, String dataId, String value) throws XSKSecureStoreException {
    xskSecureStoreCoreService.createSecureStoreValue(storeId, UserFacade.getName(), dataId, value);
  }

  public static final void removeForUser(String storeId, String dataId) throws XSKSecureStoreException {
    xskSecureStoreCoreService.deleteSecureStoreValue(storeId, UserFacade.getName(), dataId);
  }
}
