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
package com.codbex.kronos.integration.tests.core.client.http.kyma;

import org.apache.http.HttpHeaders;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicHeader;

import com.codbex.kronos.integration.tests.core.client.http.HttpClient;
import com.codbex.kronos.integration.tests.core.client.http.kyma.token.TokenService;

import java.net.URI;
import java.util.List;

public class KymaHttpClient extends HttpClient {

  private KymaHttpClient(CloseableHttpAsyncClient httpClient, URI baseHost) {
    super(httpClient, baseHost);
  }

  public static KymaHttpClient create(URI baseHost) {
    CloseableHttpAsyncClient httpClient;
    TokenService tokenService = new TokenService();
    String kymaToken = tokenService.getToken();
    BasicHeader authHeader = new BasicHeader(HttpHeaders.AUTHORIZATION, "Bearer " + kymaToken);
    httpClient = HttpAsyncClients.custom().setDefaultHeaders(List.of(authHeader)).build();
    httpClient.start();
    return new KymaHttpClient(httpClient, baseHost);
  }

}
