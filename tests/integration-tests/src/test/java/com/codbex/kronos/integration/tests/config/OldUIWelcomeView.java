package com.codbex.kronos.integration.tests.config;

import org.eclipse.dirigible.tests.WelcomeView;
import org.eclipse.dirigible.tests.framework.Browser;
import org.eclipse.dirigible.tests.framework.HtmlElementType;

class OldUIWelcomeView extends WelcomeView {
    private final Browser browser;

    OldUIWelcomeView(Browser browser) {
        super(browser);
        this.browser = browser;
    }

    @Override
    public void confirmTemplate() {
        this.browser.clickOnElementContainingText(HtmlElementType.BUTTON, "Ok");
    }
}
