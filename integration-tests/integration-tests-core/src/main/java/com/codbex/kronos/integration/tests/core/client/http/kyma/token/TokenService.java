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
package com.codbex.kronos.integration.tests.core.client.http.kyma.token;

import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;
import org.eclipse.dirigible.commons.api.helpers.GsonHelper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;

public class TokenService {
    TokenServiceConfiguration tokenServiceConfiguration = new TokenServiceConfiguration();

    public String getToken() {
        try {
            byte[] baseAuthCreds = (tokenServiceConfiguration.getClientId() + ":" + tokenServiceConfiguration.getClientSecret()).getBytes();
            var token64 = Base64.getEncoder().encodeToString(baseAuthCreds);
            URIBuilder queryBuilder = new URIBuilder(tokenServiceConfiguration.getTokenUrl());
            queryBuilder.setParameter("grant_type", tokenServiceConfiguration.getGrantType());
            queryBuilder.setParameter("username", tokenServiceConfiguration.getUsername());
            queryBuilder.setParameter("password", tokenServiceConfiguration.getPassword());
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(queryBuilder.build())
                    .setHeader(HttpHeaders.AUTHORIZATION, "Basic " + token64)
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            TokenServiceResponseBody responseBody = GsonHelper.GSON.fromJson(response.body(), TokenServiceResponseBody.class);
            return responseBody.getAccessToken();
        } catch (RuntimeException | IOException | InterruptedException | URISyntaxException e) {
            throw new TokenServiceException("Can't access JWT Token for kyma instance", e);
        }
    }
}
