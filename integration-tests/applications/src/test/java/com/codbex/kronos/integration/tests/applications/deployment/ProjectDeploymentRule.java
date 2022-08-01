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

import java.net.URL;
import java.nio.file.Path;
import java.util.Objects;

public class ProjectDeploymentRule extends ExternalResource {

    private final ProjectDeployer projectPublisher;
    private final String applicationName;

    public ProjectDeploymentRule(String applicationName, ProjectDeploymentType projectDeploymentType) {
        this.applicationName = applicationName;
        this.projectPublisher = new ProjectDeployer(projectDeploymentType);
    }

    @Override
    protected void before() throws Throwable {
        super.before();
        URL resource = getClass().getResource("/test-applications/" + applicationName);
        String resourcePathString = Objects.requireNonNull(resource).getPath();
        Path resourcePath = Path.of(resourcePathString);
        projectPublisher.deploy(applicationName, resourcePath);
    }

    @Override
    protected void after() {
        super.after();
        projectPublisher.undeploy(applicationName, applicationName);
    }
}
