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
package com.codbex.kronos.integration.tests.ui;

import org.eclipse.dirigible.commons.config.DirigibleConfig;
import org.eclipse.dirigible.tests.framework.Browser;
import org.eclipse.dirigible.tests.framework.HtmlAttribute;
import org.eclipse.dirigible.tests.framework.HtmlElementType;
import org.eclipse.dirigible.tests.util.SleepUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
@ComponentScan("org.eclipse.dirigible.tests.framework")
public class Kronos {
    private static final Logger LOGGER = LoggerFactory.getLogger(Kronos.class);

    private static final String LOGIN_PAGE_TITLE = "Please sign in";

    private static final String ROOT_PATH = "/";

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    private static final String USERNAME_FIELD_ID = "username";
    private static final String PASSWORD_FIELD_ID = "password";
    private static final String SUBMIT_TYPE = "submit";

    private static final String SIGN_IN_BUTTON_TEXT = "Sign in";
    private final Browser browser;
    private final String username;
    private final String password;

    @Autowired
    public Kronos(Browser browser) {
        this(browser, DirigibleConfig.BASIC_ADMIN_USERNAME.getFromBase64Value(), DirigibleConfig.BASIC_ADMIN_PASS.getFromBase64Value());
    }

    public Kronos(Browser browser, String username, String password) {
        this.browser = browser;
        this.username = username;
        this.password = password;
    }

    public void openHomePage() {
        browser.openPath(ROOT_PATH);
        login(false);

        SleepUtil.sleepMillis(500);
        browser.reload();
    }

    public void login(boolean forceLogin) {
        if (!forceLogin && !isLoginPageOpened()) {
            LOGGER.info("Already logged in");
            return;
        }
        LOGGER.info("Logging...");
        browser.enterTextInElementByAttributePattern(HtmlElementType.INPUT, HtmlAttribute.ID, USERNAME_FIELD_ID, username);
        browser.enterTextInElementByAttributePattern(HtmlElementType.INPUT, HtmlAttribute.ID, PASSWORD_FIELD_ID, password);
        browser.clickOnElementByAttributePatternAndText(HtmlElementType.BUTTON, HtmlAttribute.TYPE, SUBMIT_TYPE, SIGN_IN_BUTTON_TEXT);
    }

    private boolean isLoginPageOpened() {
        String pageTitle = browser.getPageTitle();
        return LOGIN_PAGE_TITLE.equals(pageTitle);
    }

}
