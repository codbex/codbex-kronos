package org.eclipse.dirigible.tests;

import org.eclipse.dirigible.tests.framework.Browser;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@Lazy
class OldUiWelcomeViewFactory extends WelcomeViewFactory {
    private final Browser defaultBrowser;

    OldUiWelcomeViewFactory(Browser defaultBrowser) {
        super(defaultBrowser);
        this.defaultBrowser = defaultBrowser;
    }

    @Override
    public WelcomeView create() {
        return create(defaultBrowser);
    }

    @Override
    public WelcomeView create(Browser browser) {
        return new OldUIWelcomeView(browser);
    }
}
