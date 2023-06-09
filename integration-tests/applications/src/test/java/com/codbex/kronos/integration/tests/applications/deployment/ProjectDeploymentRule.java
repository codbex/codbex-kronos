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

import org.junit.rules.ExternalResource;

import static com.codbex.kronos.integration.tests.applications.deployment.ProjectDeploymentConstants.CUSTOM_APPS_DIR_NAME;
import static com.codbex.kronos.integration.tests.applications.deployment.ProjectDeploymentConstants.SAMPLES_DIR_NAME;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class ProjectDeploymentRule extends ExternalResource {

  private final ProjectDeployer projectDeployer;
  private final String applicationName;
  private final ProjectType projectType;

  public ProjectDeploymentRule(String applicationName, ProjectType projectType) {
    this.applicationName = applicationName;
    this.projectType = projectType;
    this.projectDeployer = new ProjectDeployer();
  }

  @Override
  protected void before() throws Throwable {
    super.before();

    Path resourcePath = null;

    switch (projectType) {
      case SAMPLE:
        resourcePath = Path.of(Paths.get("").toAbsolutePath().getParent().getParent() + SAMPLES_DIR_NAME + applicationName);
        break;
      case CUSTOM:
        URL resource = getClass().getResource(CUSTOM_APPS_DIR_NAME + applicationName);
        String resourcePathString = Objects.requireNonNull(resource).getPath();
        resourcePath = Path.of(resourcePathString);
        break;
    }

    projectDeployer.deploy(applicationName, resourcePath);
  }

  @Override
  protected void after() {
    super.after();
    projectDeployer.undeploy(applicationName, applicationName);
  }
}
