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
package com.codbex.kronos.xssecurestore.ds.synchronizer;

import static java.text.MessageFormat.format;

import com.codbex.kronos.xssecurestore.ds.api.ISecureStoreModel;
import com.codbex.kronos.xssecurestore.ds.api.SecureStoreException;
import com.codbex.kronos.xssecurestore.ds.model.SecureStore;
import com.codbex.kronos.xssecurestore.ds.service.SecureStoreCoreService;
import java.util.List;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;
import org.eclipse.dirigible.core.scheduler.api.SynchronizationException;
import org.eclipse.dirigible.repository.api.IRepository;
import org.eclipse.dirigible.repository.api.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecureStoreSynchronizer extends AbstractSynchronizer {

  private static final Logger logger = LoggerFactory.getLogger(SecureStoreSynchronizer.class);
  private final String SYNCHRONIZER_NAME = this.getClass().getCanonicalName();
  private SecureStoreCoreService secureStoreCoreService = new SecureStoreCoreService();

  /**
   * Force synchronization.
   */
  public static final void forceSynchronization() {
    SecureStoreSynchronizer synchronizer = new SecureStoreSynchronizer();
    synchronizer.setForcedSynchronization(true);
    try {
      synchronizer.synchronize();
    } finally {
      synchronizer.setForcedSynchronization(false);
    }
  }

  @Override
  public void synchronize() {
    synchronized (SecureStoreSynchronizer.class) {
      if (beforeSynchronizing()) {
        logger.trace("Synchronizing Secure Stores ...");
        try {
          startSynchronization(SYNCHRONIZER_NAME);
          synchronizeRegistry();
          cleanup();
          successfulSynchronization(SYNCHRONIZER_NAME, format("Passed successfully."));
        } catch (Exception e) {
          logger.error("Synchronizing process for Secure Stores failed.", e);
          try {
            failedSynchronization(SYNCHRONIZER_NAME, e.getMessage());
          } catch (SchedulerException e1) {
            logger.error("Synchronizing process for Secure Stores files failed in registering the state log.", e);
          }
        }
        logger.trace("Done synchronizing Secure Stores.");
        afterSynchronizing();
      }
    }
  }

  @Override
  protected void synchronizeResource(IResource resource) throws SynchronizationException {
    String resourceName = resource.getName();
    String location = resource.getPath();
    String content = new String(resource.getContent());

    try {
      if (resourceName.endsWith(ISecureStoreModel.FILE_EXTENSION_XSSECURESTORE) &&
          !secureStoreCoreService.existsSecureStore(location)) {
        secureStoreCoreService.createSecureStore(location, content);
      }
    } catch (SecureStoreException e) {
      throw new SynchronizationException(e);
    }
  }

  @Override
  protected void cleanup() throws SynchronizationException {
    logger.trace("Cleaning up Secure Stores");
    IRepository repository = getRepository();

    try {
      List<SecureStore> secureStores = secureStoreCoreService.getSecureStores();
      for (SecureStore secureStore : secureStores) {
        String secureStoreLocation = secureStore.getLocation();
        if (!repository.hasResource(secureStoreLocation)) {
          secureStoreCoreService.removeSecureStore(secureStoreLocation);
          secureStoreCoreService.deleteSecureStoreValuesByStoreId(secureStoreLocation);
          logger.warn("Cleaned up Secure Stores from location: {}", secureStoreLocation);
        }
      }

    } catch (SecureStoreException e) {
      throw new SynchronizationException(e);
    }
  }

  @Override
  protected void synchronizeRegistry() throws SynchronizationException {
    logger.trace("Synchronizing Secure Store from Registry...");

    super.synchronizeRegistry();

    logger.trace("Done synchronizing Secure Store from Registry.");
  }
}
