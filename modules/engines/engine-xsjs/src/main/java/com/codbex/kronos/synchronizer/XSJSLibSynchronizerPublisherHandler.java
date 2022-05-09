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
package com.codbex.kronos.synchronizer;

import org.eclipse.dirigible.core.publisher.api.handlers.MetadataPublisherHandler;

public class XSJSLibSynchronizerPublisherHandler extends MetadataPublisherHandler {

  @Override
  public void afterPublish(String workspaceLocation, String registryLocation) {
    XSJSLibSynchronizer.forceSynchronization(registryLocation);
  }

  @Override
  public void afterUnpublish(String location) {
    XSJSLibSynchronizerArtefactsCleaner cleaner = new XSJSLibSynchronizerArtefactsCleaner();
    cleaner.cleanup(location);
  }
}
