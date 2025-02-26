package com.codbex.kronos.integration.tests.config;

import org.eclipse.dirigible.tests.IDE;
import org.eclipse.dirigible.tests.Workbench;
import org.eclipse.dirigible.tests.framework.Browser;
import org.eclipse.dirigible.tests.restassured.RestAssuredExecutor;
import org.eclipse.dirigible.tests.util.ProjectUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
class OldUiIde extends IDE {

    private final Browser browser;

    OldUiIde(Browser browser, RestAssuredExecutor restAssuredExecutor, ProjectUtil projectUtil) {
        super(browser, restAssuredExecutor, projectUtil);
        this.browser = browser;
    }

    @Override
    public Workbench openWorkbench() {
        openHomePage();

        browser.clickOnElementById("perspective-workbench");

        return new OldUIWorkbench(browser);
    }

}
