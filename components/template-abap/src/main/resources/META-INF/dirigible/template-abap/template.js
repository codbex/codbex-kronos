/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
/*
 * Generated by Eclipse Dirigible based on model and template.
 *
 * Do not modify the content as it may be re-generated again.
 */
exports.getTemplate = function() {
	return {
		"name": "ABAP Hello World",
		"description": "ABAP Hello World Template",
		"sources": [{
			"location": "/template-abap/abaplint.jsonc", 
			"action": "generate",
			"rename": "abaplint.jsonc"
		},{
			"location": "/template-abap/build.sh", 
			"action": "generate",
			"rename": "build.sh"
		},{
			"location": "/template-abap/package.json", 
			"action": "generate",
			"rename": "package.json"
		},{
			"location": "/template-abap/polyfills/buffer.js", 
			"action": "generate",
			"rename": "polyfills/buffer.js"
		},{
			"location": "/template-abap/polyfills/process.js", 
			"action": "generate",
			"rename": "polyfills/process.js"
		},{
			"location": "/template-abap/project.json", 
			"action": "generate",
			"rename": "project.json"
		},{
			"location": "/template-abap/run.mjs",
			"action": "generate",
			"rename": "run.mjs"
		},{
			"location": "/template-abap/src/zcl_dirigible_http_response.clas.abap", 
			"action": "generate",
			"rename": "src/zcl_dirigible_http_response.clas.abap"
		},{
			"location": "/template-abap/src/zcl_hello_world.clas.abap", 
			"action": "generate",
			"rename": "src/zcl_hello_world.clas.abap"
		}],
		"parameters": [],
		"order": 30
	};
};
