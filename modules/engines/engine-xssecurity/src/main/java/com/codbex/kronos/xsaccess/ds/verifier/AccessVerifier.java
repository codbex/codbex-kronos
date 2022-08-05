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
package com.codbex.kronos.xsaccess.ds.verifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.xsaccess.ds.api.IAccessCoreService;
import com.codbex.kronos.xsaccess.ds.api.AccessException;
import com.codbex.kronos.xsaccess.ds.model.access.AccessDefinition;

public class AccessVerifier {

  private static final Logger logger = LoggerFactory.getLogger(AccessVerifier.class);

  /**
   * Checks whether the URI is secured via the *.access file or not
   *
   * @param accessCoreService the security core service
   * @param path                  the path
   * @param method                the method
   * @return all the most specific AccessDefinition entry matching the URI if any
   * @throws AccessException the access exception
   */
  public static AccessDefinition getMatchingAccessDefinitions(IAccessCoreService accessCoreService, String path, String method)
      throws AccessException {
    AccessDefinition current = null;
    for (AccessDefinition accessDefinition : accessCoreService.getAccessDefinitions()) {
      if (path.startsWith(accessDefinition.getPath())) {
        logger.debug(String.format("URI [%s] with HTTP method [%s] is secured because of definition: %s", path, method,
            accessDefinition.getPath()));
        if ((current == null) || (accessDefinition.getPath().length() > current.getPath().length())) {
          current = accessDefinition;
        }
      }
    }
    if (current == null) {
      logger.trace(String.format("URI [%s] with HTTP method [%s] is NOT secured", path, method));
    }

    return current;
  }
}
