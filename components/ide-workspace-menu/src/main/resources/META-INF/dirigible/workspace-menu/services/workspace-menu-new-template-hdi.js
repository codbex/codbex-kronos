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
		"name": "hdi",
		"label": "HDI File",
		"extension": "hdi",
		"data": JSON.stringify({
			"configuration": "/sample/config.hdiconfig",
			"users": ["KRONOS_SAMPLE_USER"],
			"group": "KRONOS_HDI_SAMPLE_GROUP",
			"container": "KRONOS_HDI_SAMPLE",
			"deploy": [
				"/sample/Sample.hdbsynonym",
				"/sample/DEMO.hdbcalculationview"
			],
			"undeploy": []
		})
	};
};
