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
package com.codbex.kronos.integration.tests.core.client;

import com.codbex.kronos.integration.tests.core.client.http.HttpClient;
import com.codbex.kronos.integration.tests.core.client.http.HttpClientException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class PublisherClient {

  private final URI publisherServiceUri;
  private final HttpClient httpClient;

  public PublisherClient(HttpClient httpClient) {
    this.httpClient = httpClient;
    this.publisherServiceUri = httpClient.getBaseHost().resolve("/services/v4/ide/publisher/request/");
  }

  public CompletableFuture<HttpResponse> publishProjectInWorkspace(String workspace, String projectName) {
    try {
      var uri = publisherServiceUri.resolve(workspace + "/").resolve(projectName);
      HttpUriRequest request = RequestBuilder.post(uri).build();
      return httpClient.executeRequestAsync(request);
    } catch (HttpClientException e) {
      String errorMessage = "Publishing project " + projectName + " to workspace: " + workspace + " failed.";
      throw new ClientException(errorMessage, e);
    }

  }

  public CompletableFuture<HttpResponse> unpublishProjectFromWorkspace(String workspace, String projectName) {
    try {
      var uri = publisherServiceUri.resolve(workspace + "/").resolve(projectName);
      HttpUriRequest request = RequestBuilder.delete(uri).build();
      return httpClient.executeRequestAsync(request);
    } catch (HttpClientException e) {
      String errorMessage = "Unpublishing project " + projectName + " from workspace: " + workspace + " failed.";
      throw new ClientException(errorMessage, e);

    }

  }


}
