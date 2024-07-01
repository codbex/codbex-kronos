/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.integration.tests.ui;

import org.eclipse.dirigible.tests.IDE;
import org.eclipse.dirigible.tests.WelcomeView;
import org.eclipse.dirigible.tests.Workbench;
import org.eclipse.dirigible.tests.restassured.RestAssuredExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ABAPStarterTemplateIT extends UserInterfaceIntegrationTest {

    private static final String ABAP_TEMPLATE_TITLE = "ABAP Starter";
    private static final String TEST_PROJECT = "kronos-test-project";

    @Autowired
    private IDE ide;

    @Autowired
    private RestAssuredExecutor restAssuredExecutor;

    @Test
    void testCreateProjectFromTemplate() {
        ide.openHomePage();
        Workbench workbench = ide.openWorkbench();

        WelcomeView welcomeView = workbench.openWelcomeView();
        welcomeView.searchForTemplate(ABAP_TEMPLATE_TITLE);
        welcomeView.selectTemplate(ABAP_TEMPLATE_TITLE);

        welcomeView.typeProjectName(TEST_PROJECT);
        welcomeView.typeFileName(TEST_PROJECT);
        welcomeView.confirmTemplate();

        workbench.clickPublishAll();

        ide.assertPublishedProjectMessage(TEST_PROJECT);

        assertGeneratedJSWorks();
    }

    private void assertGeneratedJSWorks() {
        ide.assertJSHttpResponse(TEST_PROJECT, "dist/run.mjs", 200, "Hello world!");
    }
}
