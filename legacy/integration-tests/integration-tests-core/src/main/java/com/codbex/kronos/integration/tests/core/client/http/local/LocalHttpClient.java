/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.integration.tests.core.client.http.local;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import com.codbex.kronos.integration.tests.core.client.http.HttpClient;
import com.codbex.kronos.integration.tests.core.client.http.HttpClientException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class LocalHttpClient extends HttpClient {

  private LocalHttpClient(CloseableHttpAsyncClient httpClient, URI baseHost) {
    super(httpClient, baseHost);
  }

  public static HttpClient create(URI baseHost) {
    CloseableHttpAsyncClient closeableHttpClient = HttpAsyncClients.createDefault();
    closeableHttpClient.start();
    LocalHttpClient httpClient = new LocalHttpClient(closeableHttpClient, baseHost);

    try {
      httpClient.login().get();
    } catch (InterruptedException | ExecutionException executionException) {
      throw new HttpClientException("Login to local deployed Kronos failed.", executionException);
    }

    return httpClient;
  }

  private CompletableFuture<HttpResponse> login() {
    try {
      var uri = new URI("http://localhost:8080/services/v4/web/ide/");
      HttpUriRequest request = RequestBuilder.get(uri).build();
      return this.executeRequestAsyncWithCallbackFuture(request)
          .thenCompose(x -> {
            try {
              var jsecurityUri = new URI("http://localhost:8080/services/v4/web/ide/j_security_check");
              HttpUriRequest loginRequest = RequestBuilder
                  .post(jsecurityUri)
                  .addParameter("j_username", "dirigible")
                  .addParameter("j_password", "dirigible")
                  .build();
              return this.executeRequestAsyncWithCallbackFuture(loginRequest);
            } catch (URISyntaxException e) {
              throw new RuntimeException(e);
            }
          });
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

}
