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
migrationLaunchView.controller("MigrationStatisticsController", [
    "$scope",
    "$http",
    function ($scope, $http) {
        let body = { migrations: "empty" };
        let defaultErrorTitle = "Error loading migrations information.";
        $http
            .post("/services/v4/js/ide-migration/server/migration/api/migration-rest-api.mjs/migrationsTrack", JSON.stringify(body), {
                headers: { "Content-Type": "application/json" },
            })
            .then(
                function (response) {
                    $scope.migrations = JSON.parse(JSON.stringify(response.data));
                    $scope.hideTable = $scope.migrations === "empty";
                },
                function (response) {
                    $messageHub.announceAlertError(defaultErrorTitle, response.data.error.message);
                    console.error(response);
                }
            );
    },
]);
