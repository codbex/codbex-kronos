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

import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.commons.api.scripting.IScriptingFacade;

import com.codbex.kronos.xssecurestore.ds.api.ISecureStoreModel;
import com.codbex.kronos.xssecurestore.ds.api.SecureStoreException;
import com.codbex.kronos.xssecurestore.ds.model.SecureStoreContent;
import com.codbex.kronos.xssecurestore.ds.service.SecureStoreCoreService;

/**
 * The Class SecureStoreFacade.
 */
public class SecureStoreFacade implements IScriptingFacade {

  /** The Constant secureStoreCoreService. */
  private static final SecureStoreCoreService secureStoreCoreService = new SecureStoreCoreService();

  /**
   * Instantiates a new secure store facade.
   */
  public SecureStoreFacade() {
  }

  /**
   * Store.
   *
   * @param storeId the store id
   * @param dataId the data id
   * @param value the value
   * @throws SecureStoreException the secure store exception
   */
  public static final void store(String storeId, String dataId, String value) throws SecureStoreException {
    secureStoreCoreService.createSecureStoreValue(storeId, ISecureStoreModel.VALUE_APP_USER, dataId, value);
  }

  /**
   * Exists store.
   *
   * @param storeId the store id
   * @return true, if successful
   * @throws SecureStoreException the secure store exception
   */
  public static final boolean existsStore(String storeId) throws SecureStoreException {
    return secureStoreCoreService.existsSecureStore(storeId);
  }

  /**
   * Read.
   *
   * @param storeId the store id
   * @param dataId the data id
   * @return the string
   * @throws SecureStoreException the secure store exception
   */
  public static final String read(String storeId, String dataId) throws SecureStoreException {
    SecureStoreContent secureStoreContent = secureStoreCoreService
        .findSecureStoreContent(storeId, ISecureStoreModel.VALUE_APP_USER, dataId);

    if (secureStoreContent == null) {
      return null;
    }

    return new String(secureStoreContent.getDataValue());
  }

  /**
   * Removes the.
   *
   * @param storeId the store id
   * @param dataId the data id
   * @throws SecureStoreException the secure store exception
   */
  public static final void remove(String storeId, String dataId) throws SecureStoreException {
    secureStoreCoreService.deleteSecureStoreValue(storeId, ISecureStoreModel.VALUE_APP_USER, dataId);
  }

  /**
   * Read for user.
   *
   * @param storeId the store id
   * @param dataId the data id
   * @return the string
   * @throws SecureStoreException the secure store exception
   */
  public static final String readForUser(String storeId, String dataId) throws SecureStoreException {
    SecureStoreContent secureStoreContent = secureStoreCoreService.findSecureStoreContent(storeId, UserFacade.getName(), dataId);

    if (secureStoreContent == null) {
      return null;
    }

    return new String(secureStoreContent.getDataValue());
  }

  /**
   * Store for user.
   *
   * @param storeId the store id
   * @param dataId the data id
   * @param value the value
   * @throws SecureStoreException the secure store exception
   */
  public static final void storeForUser(String storeId, String dataId, String value) throws SecureStoreException {
    secureStoreCoreService.createSecureStoreValue(storeId, UserFacade.getName(), dataId, value);
  }

  /**
   * Removes the for user.
   *
   * @param storeId the store id
   * @param dataId the data id
   * @throws SecureStoreException the secure store exception
   */
  public static final void removeForUser(String storeId, String dataId) throws SecureStoreException {
    secureStoreCoreService.deleteSecureStoreValue(storeId, UserFacade.getName(), dataId);
  }
}
