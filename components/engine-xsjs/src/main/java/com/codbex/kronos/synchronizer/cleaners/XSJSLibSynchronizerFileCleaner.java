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
package com.codbex.kronos.synchronizer.cleaners;

import org.eclipse.dirigible.repository.api.IRepository;
import org.eclipse.dirigible.repository.api.IResource;

/**
 * The Class XSJSLibSynchronizerFileCleaner.
 */
public class XSJSLibSynchronizerFileCleaner implements XSJSLibSynchronizerCleaner {
  
  /** The repository. */
  private final IRepository repository;

  /**
   * Instantiates a new XSJS lib synchronizer file cleaner.
   *
   * @param repository the repository
   */
  public XSJSLibSynchronizerFileCleaner(IRepository repository) {
    this.repository = repository;
  }

  /**
   * Cleanup.
   *
   * @param registryPath the registry path
   */
  public void cleanup(String registryPath) {

    IResource resource = repository.getResource(registryPath + ".generated_exports");
    if(resource.exists()) {
      resource.delete();
    }
  }
}
