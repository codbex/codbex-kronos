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
import org.eclipse.dirigible.tests.restassured.RestAssuredExecutor;
import org.eclipse.dirigible.tests.util.ProjectUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
class OldUiIde extends IDE {

    private final WorkbenchFactory workbenchFactory;
    private final WelcomeViewFactory welcomeViewFactory;

    OldUiIde(Browser browser, RestAssuredExecutor restAssuredExecutor, ProjectUtil projectUtil, WorkbenchFactory workbenchFactory,
            WelcomeViewFactory welcomeViewFactory) {
        super(browser, restAssuredExecutor, projectUtil, workbenchFactory);
        this.workbenchFactory = workbenchFactory;
        this.welcomeViewFactory = welcomeViewFactory;
    }

    @Override
    public Workbench openWorkbench() {
        openHomePage();

        getBrowser().clickOnElementById("perspective-workbench");

        return new OldUIWorkbench(getBrowser(), welcomeViewFactory);
    }

}
