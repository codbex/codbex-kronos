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
package org.eclipse.dirigible.tests;

import org.eclipse.dirigible.tests.framework.Browser;
import org.eclipse.dirigible.tests.framework.HtmlElementType;

class OldUIWorkbench extends Workbench {

    private final Browser browser;
    private final WelcomeViewFactory welcomeViewFactory;

    OldUIWorkbench(Browser browser, WelcomeViewFactory welcomeViewFactory, TerminalFactory terminalFactory) {
        super(browser, welcomeViewFactory, terminalFactory);
        this.browser = browser;
        this.welcomeViewFactory = welcomeViewFactory;
    }

    @Override
    public WelcomeView openWelcomeView() {
        focusOnOpenedFile("Welcome");
        return welcomeViewFactory.create(browser);
    }

    @Override
    public WelcomeView focusOnOpenedFile(String fileName) {
        this.browser.clickOnElementContainingText(HtmlElementType.ANCHOR, fileName);
        return welcomeViewFactory.create(browser);
    }

}
