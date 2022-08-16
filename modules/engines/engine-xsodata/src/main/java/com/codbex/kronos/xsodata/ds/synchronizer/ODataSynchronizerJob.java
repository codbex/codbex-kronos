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
package com.codbex.kronos.xsodata.ds.synchronizer;

import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizerJob;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizer;

/**
 * The Data Structures Synchronizer Job.
 */
public class ODataSynchronizerJob extends AbstractSynchronizerJob {

  /** The odata synchronizer. */
  private final ODataSynchronizer odataSynchronizer = new ODataSynchronizer();

  /**
   * Gets the synchronizer.
   *
   * @return the synchronizer
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizerJob#getSynchronizer()
   */
  @Override
  public ISynchronizer getSynchronizer() {
    return odataSynchronizer;
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizerJob#getName()
   */
  @Override
  public String getName() {
    return ODataSynchronizerJobDefinitionProvider.ODATA_SYNCHRONIZER_JOB;
  }

}
