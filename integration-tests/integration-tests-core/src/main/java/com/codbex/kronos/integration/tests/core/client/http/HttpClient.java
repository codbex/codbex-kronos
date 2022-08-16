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
package com.codbex.kronos.integration.tests.core.client.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import java.net.URI;
import java.util.concurrent.Future;

public abstract class HttpClient {

  protected final CloseableHttpAsyncClient httpClient;
  private final URI baseHost;

  protected HttpClient(CloseableHttpAsyncClient httpClient, URI baseHost) {
    this.httpClient = httpClient;
    this.baseHost = baseHost;
  }

  public URI getBaseHost() {
    return baseHost;
  }

  public Future<HttpResponse> executeRequestAsync(HttpUriRequest request) {
    return httpClient.execute(request, null);
  }

  public HttpClientFuture executeRequestAsyncWithCallbackFuture(HttpUriRequest request) {
    var future = new HttpClientFuture(request.getURI());
    httpClient.execute(request, future);
    return future;
  }
}
