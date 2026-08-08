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
import { repository } from "sdk/platform";

export function getMenu() {
	const hasOpenApi = repository.getResource('/registry/public/view-swagger/project.json').exists();
	const config = {
		systemMenu: true,
		id: 'help',
		menu: {
			translation: {
				key: 'common:help',
			},
			label: 'Help',
			items: [
				{
					translation: {
						key: 'menu-help:portal',
					},
					label: 'Help Portal',
					action: 'open',
					link: 'https://www.codbex.com/documentation/tooling/',
					separator: false
				},
				{
					translation: {
						key: 'menu-help:support',
					},
					label: 'Contact Support',
					action: 'open',
					link: 'https://www.codbex.com/contact/',
					separator: false
				},
				{
					translation: {
						key: 'menu-help:pricing',
					},
					label: 'Pricing Plans',
					action: 'open',
					link: 'https://www.codbex.com/pricing/',
					separator: false
				},
				{
					translation: {
						key: 'menu-help:whatsNew',
					},
					label: 'What\'s New',
					action: 'open',
					link: 'https://www.codbex.com/technology',
					separator: false
				},
				{
					translation: {
						key: 'menu-help:updates',
					},
					label: 'Check for Updates',
					action: 'open',
					link: 'https://www.codbex.com/products/',
					separator: true
				},
				{
					id: 'about',
					translation: {
						key: 'menu-help:about',
					},
					label: 'About',
					action: 'openWindow',
					separator: false
				}
			]
		}
	};
	if (hasOpenApi) {
		config.menu.items.splice(5, 0, {
			label: 'OpenAPI',
			action: 'open',
			link: '/services/web/view-swagger/ui/',
			separator: true
		});
	}
	return config;
};
