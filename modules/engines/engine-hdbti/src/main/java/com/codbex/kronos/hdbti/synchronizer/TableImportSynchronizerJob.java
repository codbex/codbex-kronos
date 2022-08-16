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
package com.codbex.kronos.hdbti.synchronizer;

import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizerJob;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizer;

/**
 * The Class TableImportSynchronizerJob.
 */
public class TableImportSynchronizerJob extends AbstractSynchronizerJob {

  /** The table import synchronizer. */
  private TableImportSynchronizer tableImportSynchronizer = new TableImportSynchronizer();

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
    return tableImportSynchronizer;
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
    return TableImportSynchronizerJobDefinitionProvider.TABLE_IMPORT_SYNCHRONIZER_JOB;
  }
}
