/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
let extensions = require("core/extensions");
let response = require("http/response");

let mainmenu = [];
let menuExtensions = extensions.getExtensions("ide-migration-menu");

for (const menuExtensionModule of menuExtensions) {
    let menuExtension = require(menuExtensionModule);
    let menu = menuExtension.getMenu();
    mainmenu.push(menu);
}

mainmenu.sort(function (p, n) {
    return parseInt(p.order) - parseInt(n.order);
});
response.println(JSON.stringify(mainmenu));
