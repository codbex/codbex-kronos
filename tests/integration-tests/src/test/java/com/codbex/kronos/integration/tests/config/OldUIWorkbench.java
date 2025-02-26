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
