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
