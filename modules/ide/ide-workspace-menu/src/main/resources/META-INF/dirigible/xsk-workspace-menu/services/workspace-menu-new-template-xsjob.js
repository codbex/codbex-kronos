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
exports.getTemplate = function () {
	return {
		"name": "xsjob",
		"label": " XS Scheduled Job",
		"extension": "xsjob",
		"data": JSON.stringify(JSON.parse('{\n' +
			'    "description": "Read stock value",\n' +
			'    "action": "yahoo:yahoo.xsjs::readStock",\n' +
			'    "schedules": [\n' +
			'       {\n' +
			'          "description": "Read current stock value",\n' +
			'          "xscron": "* * * * * * 59",\n' +
			'          "parameter": {\n' +
			'             "stock": "SAP.DE"\n' +
			'             }\n' +
			'       }\n' +
			'    ]\n' +
			'}'), null, 2)	};
};
