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

import org.eclipse.dirigible.tests.framework.HtmlElementType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class HomePageIT extends UserInterfaceIntegrationTest {

    @Autowired
    private Kronos kronos;

    @Test
    void testOpenHomepage() {
        kronos.openHomePage();

        browser.assertElementExistsByTypeAndText(HtmlElementType.SPAN, "codbex");
        browser.assertElementExistsByTypeAndText(HtmlElementType.HEADER3, "Welcome to Kronos");
    }
}
