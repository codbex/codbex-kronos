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
package com.codbex.kronos.integration.tests.applications.deployment;

import com.codbex.kronos.integration.tests.core.client.PublisherClient;
import com.codbex.kronos.integration.tests.core.client.WorkspaceClient;
import com.codbex.kronos.integration.tests.core.client.http.HttpClient;
import com.codbex.kronos.integration.tests.core.client.http.kyma.KymaHttpClient;
import com.codbex.kronos.integration.tests.core.client.http.local.LocalHttpClient;
import org.apache.http.HttpResponse;
import org.eclipse.dirigible.commons.config.Configuration;

import java.net.URI;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ProjectDeployer {

  private final WorkspaceClient workspaceClient;
  private final PublisherClient publisherClient;

  public ProjectDeployer(ProjectDeploymentType projectDeploymentType) {
    HttpClient httpClient = createHttpClient(projectDeploymentType);
    this.workspaceClient = new WorkspaceClient(httpClient);
    this.publisherClient = new PublisherClient(httpClient);
  }

  public HttpClient createHttpClient(ProjectDeploymentType projectDeploymentType) {
    if (projectDeploymentType == com.codbex.kronos.integration.tests.applications.deployment.ProjectDeploymentType.KYMA) {
      String host = Configuration.get("KYMA_HOST");
      var uri = URI.create(host);
      return KymaHttpClient.create(uri);
    } else {
      var uri = URI.create("http://localhost:8080");
      return LocalHttpClient.create(uri);
    }
  }

  public void deploy(String applicationName, Path applicationFolderPath) throws ProjectDeploymentException {
    try {
      deployAsync(applicationName, applicationFolderPath).get();
    } catch (InterruptedException | ExecutionException e) {
      String errorMessage = "Publish " + applicationName + " to " + applicationFolderPath + " failed";
      throw new ProjectDeploymentException(errorMessage, e);
    }
  }

  public CompletableFuture<HttpResponse> deployAsync(String projectName, Path projectFolderPath) throws ProjectDeploymentException {
    return workspaceClient.createWorkspace(projectName)
        .thenCompose(x -> workspaceClient.importProjectInWorkspace(projectName, projectName, projectFolderPath))
        .thenCompose(x -> publisherClient.publishProjectInWorkspace(projectName, projectName));
  }

  public void undeploy(String applicationName, String applicationFolderPath) {
    try {
      undeployAsync(applicationName).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new ProjectDeploymentException("Unpublish " + applicationName + " from " + applicationFolderPath + " failed", e);
    }
  }

  public CompletableFuture<HttpResponse> undeployAsync(String projectName) throws ProjectDeploymentException {
    return publisherClient.unpublishProjectFromWorkspace(projectName, projectName)
        .thenCompose(x -> workspaceClient.deleteWorkspace(projectName));

  }


}
