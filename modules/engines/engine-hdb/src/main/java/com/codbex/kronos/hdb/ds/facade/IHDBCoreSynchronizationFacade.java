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
package com.codbex.kronos.hdb.ds.facade;

import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;

import org.eclipse.dirigible.core.scheduler.api.SynchronizationException;
import org.eclipse.dirigible.repository.api.IResource;

/**
 * The Interface IHDBCoreSynchronizationFacade.
 */
public interface IHDBCoreSynchronizationFacade {

  /**
   * Handle resource synchronization.
   *
   * @param resource the resource
   * @throws SynchronizationException the synchronization exception
   * @throws DataStructuresException the data structures exception
   */
  void handleResourceSynchronization(IResource resource) throws SynchronizationException, DataStructuresException;

  /**
   * Handle resource synchronization.
   *
   * @param fileExtension the file extension
   * @param dataStructureModel the data structure model
   * @throws SynchronizationException the synchronization exception
   * @throws DataStructuresException the data structures exception
   */
  void handleResourceSynchronization(String fileExtension, DataStructureModel dataStructureModel)
      throws SynchronizationException, DataStructuresException;

  /**
   * Update entities.
   */
  void updateEntities();

  /**
   * Cleanup.
   *
   * @throws DataStructuresException the data structures exception
   */
  void cleanup() throws DataStructuresException;

  /**
   * Clear cache.
   */
  void clearCache();
}
