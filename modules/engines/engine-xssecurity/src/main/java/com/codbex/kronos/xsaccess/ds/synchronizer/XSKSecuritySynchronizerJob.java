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
package com.codbex.kronos.xsaccess.ds.synchronizer;

import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizerJob;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizer;

public class XSKSecuritySynchronizerJob extends AbstractSynchronizerJob {

  /**
   * The extensions synchronizer.
   */
  private XSKSecuritySynchronizer extensionsSynchronizer = new XSKSecuritySynchronizer();

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizerJob#getSynchronizer()
   */
  @Override
  public ISynchronizer getSynchronizer() {
    return extensionsSynchronizer;
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizerJob#getName()
   */
  @Override
  public String getName() {
    return XSKSecuritySynchronizerJobDefinitionProvider.XSK_PRIVILEGES_AND_ACCESS_SYNCHRONIZER_JOB;
  }
}
