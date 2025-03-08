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
