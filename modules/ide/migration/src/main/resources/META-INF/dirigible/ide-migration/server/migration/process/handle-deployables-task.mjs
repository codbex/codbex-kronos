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
import { process } from "@dirigible/bpm";
import { MigrationService } from "../api/migration-service.mjs";
import { MigrationTask } from "./task.mjs";

export class HandleDeployablesTask extends MigrationTask {
    execution = process.getExecutionContext();

    constructor() {
        super("HANDLE_DEPLOYABLES_EXECUTING", "HANDLE_DEPLOYABLES_EXECUTED", "HANDLE_DEPLOYABLES_FAILED");
    }

    run() {
        const userDataJson = process.getVariable(this.execution.getId(), "userData");
        const userData = JSON.parse(userDataJson);

        const migrationService = new MigrationService();
        for (const deliveryUnit of userData.du) {
            const locals = deliveryUnit.locals;
            if (!(locals && locals.length > 0)) {
                continue;
            }
            let deployables = [];
            for (const local of locals) {
                deployables = migrationService.collectDeployables(
                    userData.workspace,
                    local.repositoryPath,
                    local.runLocation,
                    local.projectName,
                    deployables
                );
            }

            // Get names of projects with generated synonyms and add them to deployables
            const projectsWithSynonyms = migrationService.getProjectsWithSynonyms(locals);
            const synonymsPaths = migrationService.checkExistingSynonymTypes(locals)
            if (projectsWithSynonyms) {
                for (const projectName of projectsWithSynonyms) {
                    const projectDeployables = deployables.find((x) => x.projectName === projectName).artifacts;
                    projectDeployables.push(...synonymsPaths);
                }
            }

            deliveryUnit["deployableArtifactsResult"] =
                migrationService.handlePossibleDeployableArtifacts(
                    deliveryUnit.name,
                    userData.workspace,
                    deployables
                );
        }
        process.setVariable(this.execution.getId(), "userData", JSON.stringify(userData));
    }
}
