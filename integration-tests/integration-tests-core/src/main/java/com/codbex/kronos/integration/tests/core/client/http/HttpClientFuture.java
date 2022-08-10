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
import org.apache.http.concurrent.FutureCallback;
import java.net.URI;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;

public class HttpClientFuture extends CompletableFuture<HttpResponse> implements FutureCallback<HttpResponse> {

  private final URI requestURI;

  public HttpClientFuture(URI requestURI) {
    this.requestURI = requestURI;
  }

  @Override
  public void completed(HttpResponse httpResponse) {
    int statusCode = httpResponse.getStatusLine().getStatusCode();
    if (statusCode > 299 || statusCode < 200) {
      completeExceptionally(new HttpClientException("Unexpected status code: " + statusCode + " for URI: " + requestURI));
    }
    complete(httpResponse);
  }

  @Override
  public void failed(Exception e) {
    completeExceptionally(e);
  }

  @Override
  public void cancelled() {
    completeExceptionally(new CancellationException());
  }
}
