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
package com.codbex.kronos.integration.tests;

import org.eclipse.dirigible.tests.WelcomeView;
import org.eclipse.dirigible.tests.Workbench;
import org.eclipse.dirigible.tests.restassured.RestAssuredExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

class ABAPStarterTemplateIT extends KronosIntegrationTest {

    private static final String ABAP_TEMPLATE_TITLE = "ABAP Starter";
    private static final String TEST_PROJECT = ABAPStarterTemplateIT.class.getSimpleName();

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

        workbench.publishAll(true);

        assertGeneratedJSWorks();
    }

    private void assertGeneratedJSWorks() {
        await().atMost(30, TimeUnit.SECONDS) // Wait at most 10 seconds
               .pollInterval(1, TimeUnit.SECONDS) // Check every second
               .until(() -> {
                   try {
                       ide.assertJSHttpResponse(TEST_PROJECT, "dist/run.mjs", 200, "Hello world!");
                       return true;
                   } catch (AssertionError err) {
                       return false;
                   }
               });
    }
}
