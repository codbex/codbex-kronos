package com.codbex.kronos.integration.tests.config;

import org.eclipse.dirigible.tests.WelcomeView;
import org.eclipse.dirigible.tests.Workbench;
import org.eclipse.dirigible.tests.framework.Browser;
import org.eclipse.dirigible.tests.framework.HtmlElementType;

class OldUIWorkbench extends Workbench {

    private final Browser browser;

    OldUIWorkbench(Browser browser) {
        super(browser);
        this.browser = browser;
    }

    @Override
    public WelcomeView openWelcomeView() {
        focusOnOpenedFile("Welcome");
        return new OldUIWelcomeView(this.browser);
    }

    @Override
    public WelcomeView focusOnOpenedFile(String fileName) {
        this.browser.clickOnElementContainingText(HtmlElementType.ANCHOR, fileName);
        return new OldUIWelcomeView(this.browser);
    }

}
