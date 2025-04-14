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
package com.codbex.kronos.integration.tests.applications.utils;

import org.eclipse.dirigible.commons.config.Configuration;

import com.codbex.kronos.integration.tests.core.client.http.HttpClient;
import com.codbex.kronos.integration.tests.core.client.http.kyma.KymaHttpClient;
import com.codbex.kronos.integration.tests.core.client.http.local.LocalHttpClient;

import static com.codbex.kronos.integration.tests.applications.deployment.ProjectDeploymentConstants.KYMA_HOST;
import static com.codbex.kronos.integration.tests.applications.deployment.ProjectDeploymentConstants.LOCAL_BASE_HOST;

import java.net.URI;

public class HttpClientFactory {

  public HttpClient createHttpClient() {
    String kymaHost = Configuration.get(KYMA_HOST);

    if (kymaHost != null && !kymaHost.isEmpty()) {
      URI kymaUri = URI.create(kymaHost);
      return KymaHttpClient.create(kymaUri);
    } else {
      URI localUri = URI.create(LOCAL_BASE_HOST);
      return LocalHttpClient.create(localUri);
    }
  }
}
