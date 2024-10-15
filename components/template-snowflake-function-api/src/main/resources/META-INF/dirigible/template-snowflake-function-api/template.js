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
		name: "Snowflake Function API",
		description: "Snowflake Function API Template",
		sources: [{
			location: "/template-snowflake-function-api/project.json",
			action: "generate",
			rename: "project.json"
		},{
			location: "/template-snowflake-function-api/tsconfig.json",
			action: "generate",
			rename: "tsconfig.json"
		},{
			location: "/template-snowflake-function-api/api/function-data-dto.ts",
			action: "generate",
			rename: "api/function-data-dto.ts"
		},{
            location: "/template-snowflake-function-api/api/snowflake-api.ts",
            action: "generate",
            rename: "api/{{fileName}}.ts"
        },{
            location: "/template-snowflake-function-api/security/api-constraints.access.template",
            action: "generate",
              engine: "velocity",
            rename: "security/api-constraints.access"
        }],
		parameters: [],
		order: -200
	};
};
