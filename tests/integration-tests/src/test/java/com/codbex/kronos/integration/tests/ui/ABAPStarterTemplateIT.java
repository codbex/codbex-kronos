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

        ide.assertPublishingProjectMessage(TEST_PROJECT);
        ide.assertPublishedProjectMessage(TEST_PROJECT);

        assertGeneratedJSWorks();
    }

    private void assertGeneratedJSWorks() {
        ide.assertJSHttpResponse(TEST_PROJECT, "dist/run.mjs", 200, "Hello world!");
    }
}
