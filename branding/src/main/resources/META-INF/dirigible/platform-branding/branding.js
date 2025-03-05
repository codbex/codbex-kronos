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

if (!top.hasOwnProperty('PlatformBranding')) top.PlatformBranding = {
    name: 'codbex',
    brand: 'codbex',
    brandUrl: 'https://www.codbex.com/',
    icons: {
        faviconIco: '/services/web/platform-branding/images/favicon.ico',
    },
    logo: '/services/web/platform-branding/images/logo.svg',
    keyPrefix: 'codbex'
};

function getBrandingInfo() {
    if (top.hasOwnProperty('PlatformBranding')) return top.PlatformBranding;
    throw Error("PlatformBranding is not set!");
}

function setBrandingInfo({ name, brand, brandUrl, icons, logo, keyPrefix } = {}) {
    if (name) top.PlatformBranding.name = name;
    if (brand) top.PlatformBranding.brand = brand;
    if (brandUrl) top.PlatformBranding.brandUrl = brandUrl;
    if (icons) {
        if (icons['faviconIco']) top.PlatformBranding.icons.faviconIco = icons['faviconIco'];
    }
    if (logo) top.PlatformBranding.logo = logo;
    if (keyPrefix) top.PlatformBranding.keyPrefix = keyPrefix;
}