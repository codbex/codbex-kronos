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
migrationLaunchView.controller("HanaCredentialsViewController", [
    "$scope",
    "$http",
    "$messageHub",
    "migrationDataState",
    "migrationViewState",
    function ($scope, $http, $messageHub, migrationDataState, migrationViewState) {
        $scope.migrationDataState = migrationDataState;
        $scope.passwordVisible = false;
        $scope.dataLoaded = function () {
            return !migrationViewState.getIsDataLoading();
        }
        $scope.databasesDropdownText = "---Please select---";
        $scope.databases = [];
        $scope.databasesList = $scope.databases;

        let descriptionList = ["Please wait while we get all available databases...", "Provide the SAP HANA Credentials"];
        $scope.descriptionText = descriptionList[0];
        let defaultErrorTitle = "Error listing databases";
        let noProcessErrorTitle = "Error starting migration process";
        let noProcessErrorDescription = "Migration process initiation failed! Process ID is null.";
        let defaultErrorDesc = "Please check if the information you provided is correct and try again.";

        function getAvailableHanaDatabases() {
            let body = {
                neo: {
                    hostName: migrationDataState.neoHostName,
                    subaccount: migrationDataState.neoSubaccount,
                    username: migrationDataState.neoUsername,
                    password: migrationDataState.neoPassword,
                },
            };

            $http
                .post("/services/js/ide-migration/server/migration/api/migration-rest-api.mjs/list-databases", JSON.stringify(body), {
                    headers: { "Content-Type": "application/json" },
                })
                .then(function (response) {
                    if (response.data && response.data.failed) {
                        $messageHub.announceAlertError(defaultErrorTitle, defaultErrorDesc);
                        errorOccurred();
                    } else if (response.data.databases && response.data.userJwtToken) {
                        body.databases = response.data.databases;
                        migrationDataState.userJwtToken = response.data.userJwtToken;

                        migrationViewState.setDataLoading(false);
                        $scope.descriptionText = descriptionList[1];
                        $scope.userInput();

                        $scope.databasesDropdownText = "---Please select---";
                        $scope.databases = response.data.databases;
                        $scope.databasesList = $scope.databases;
                    }
                })
                .catch(function (err) {
                    migrationViewState.setDataLoading(false);
                    $messageHub.announceAlertError(defaultErrorTitle, err.message ?? defaultErrorDesc);
                    errorOccurred();
                });
        }

        function errorOccurred() {
            $scope.$parent.previousClicked();
        }

        $scope.userInput = function () {
            if (
                migrationDataState.schemaName &&
                migrationDataState.dbUsername &&
                migrationDataState.dbPassword &&
                !migrationViewState.getIsDataLoading()
            ) {
                $scope.$parent.setNextEnabled(true);
            } else {
                $scope.$parent.setNextEnabled(false);
            }
        };

        $scope.showPassword = function () {
            $scope.passwordVisible = !$scope.passwordVisible;
        };

        $scope.filterDatabases = function () {
            if ($scope.databasesSearch) {
                let filtered = [];
                for (const database of $scope.databases) {
                    if (database.toLowerCase().includes($scope.databasesSearch.toLowerCase())) {
                        filtered.push(database);
                    }
                }
                $scope.databasesList = filtered;
            } else {
                $scope.databasesList = $scope.databases;
            }
        };

        $scope.databaseSelected = function (database) {
            migrationDataState.schemaName = database;
            $scope.databasesDropdownText = database;
            $scope.userInput();
        };


        $messageHub.on(
            "migration.get-databases",
            function () {
                migrationViewState.setDataLoading(true);
                getAvailableHanaDatabases();
            }.bind(this)
        );
    },
]);
