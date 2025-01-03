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
/*
 * Generated by Eclipse Dirigible based on model and template.
 *
 * Do not modify the content as it may be re-generated again.
 */
export function getTemplate() {
	return {
		"name": "HDB Table",
		"description": "HDB Table Template",
		"sources": [
		{
			"location": "/template-hdbtable/hdb.schema.template", 
			"action": "generate",
			"rename": "{{schemaName}}.hdbschema"
		},{
			"location": "/template-hdbtable/hdb.table.template", 
			"action": "generate",
			"rename": "{{tableName}}.hdbtable"
		}],
		"parameters": [
		{
			"name": "schemaName",
			"label": "Schema Name"
		},{
			"name": "tableName",
			"label": "Table Name"
		}],
		"order": 1400
	};
};
