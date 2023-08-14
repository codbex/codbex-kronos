/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
let migrationView = angular.module('migration-app', ['ideUI', 'ideView', 'ideWorkspace', 'ideTransport', 'angularFileUpload']);

migrationView.config(["messageHubProvider", function (messageHubProvider) {
    messageHubProvider.eventIdPrefix = 'migration';
}]);

migrationView.controller('MigrationViewController', ['$scope', '$http', '$window', 'messageHub', 'workspaceApi', 'transportApi', 'FileUploader', function ($scope, $http, $window, messageHub, workspaceApi, transportApi, FileUploader) {
    let noProcessErrorTitle = "Error starting migration process";
    let noProcessErrorDescription = "Migration process initiation failed! Process ID is null.";
    $scope.TRANSPORT_PROJECT_URL = transportApi.getProjectImportUrl();
    $scope.selectedWorkspace = { name: 'workspace' }; // Default
    $scope.workspaceNames = [];
    $scope.headerLabel = "Migration";
    $scope.state = {
        isBusy: false,
        error: false,
        busyText: "Loading...",
        activePage: "history" // history, zip and live
    };
    $scope.zipMessagePanel = {
        icon: "sap-icon--upload-to-cloud",
        title: "",
        subtitle: '',
    };
    $scope.zipPaths = [];
    $scope.inputAccept = '.zip';
    $scope.migrationFinished = false;
    $scope.duNamesFormatted = '"DEMO1", "DEMO2"';

    $scope.goToWorkspace = function () {
        workspaceApi.setWorkspace($scope.selectedWorkspace.name);
        messageHub.openPerspective("../ide/");
    };

    $scope.reloadWorkspaceList = function () {
        let userSelected = JSON.parse(localStorage.getItem('DIRIGIBLE.workspace') || '{}');
        if (!userSelected.name) {
            $scope.selectedWorkspace.name = 'workspace'; // Default
        } else {
            $scope.selectedWorkspace.name = userSelected.name;
        }
        workspaceApi.listWorkspaceNames().then(function (response) {
            if (response.status === 200)
                $scope.workspaceNames = response.data;
            else messageHub.setStatusError('Unable to load workspace list');
        });
    };

    $scope.getSimplifiedStatus = function (status) {
        if (status.endsWith("_FAILED")) return 'fail';
        else if (status === "POPULATING_PROJECTS_EXECUTED") return 'done';
        else return 'executing';
    };

    $scope.continueClicked = function (migrationEntry) {
        if (migrationEntry.STATUS === "POPULATING_PROJECTS_EXECUTED") {
            // TODO
        }
    };

    $scope.showZipMigration = function () {
        $scope.zipMessagePanel.icon = "sap-icon--upload-to-cloud";
        $scope.zipMessagePanel.title = "Upload Delivery Unit(s)";
        $scope.zipMessagePanel.subtitle = 'Drop zip file(s) here, or use the "+" button.';
        $scope.headerLabel = "Migrate Delivery Unit(s) from Zip";
        $scope.removeAllZips();
        $scope.state.activePage = "zip";
    };

    $scope.showLiveMigration = function () {
        $scope.headerLabel = "Live Migration";
        $scope.state.activePage = "live";
    };

    $scope.showHistory = function () {
        $scope.headerLabel = "Migration";
        $scope.state.activePage = "history";
        $scope.migrationFinished = false;
    };

    $scope.projectFromZipPath = function (zipname = "") {
        return $scope.selectedWorkspace.name + "/" + zipname.split(".").slice(0, -1).join(".");
    };

    $scope.workspacePath = function () {
        return $scope.selectedWorkspace.name + "/";
    };

    $scope.addFiles = function () {
        $window.document.getElementById('input').click();
    };

    $scope.removeFileToUpload = function (item) {
        item.remove();
    };

    // FILE UPLOADER

    $scope.uploader = uploader = new FileUploader({
        filters: [],
        url: $scope.TRANSPORT_PROJECT_URL,
    });

    // UPLOADER FILTERS

    $scope.uploader.filters.push({
        name: "customFilter",
        fn: function (item /*{File|FileLikeObject}*/, options) {
            return this.queue.length < 100;
        },
    });

    // UPLOADER CALLBACKS

    $scope.uploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) { };
    $scope.uploader.onAfterAddingFile = function (fileItem) { };
    $scope.uploader.onAfterAddingAll = function (addedFileItems) { };
    $scope.uploader.onBeforeUploadItem = function (item) {
        item.url = $scope.TRANSPORT_PROJECT_URL + "?path=" + encodeURI($scope.workspacePath());
    };
    $scope.uploader.onProgressItem = function (fileItem, progress) { };
    $scope.uploader.onProgressAll = function (progress) { };
    $scope.uploader.onSuccessItem = function (fileItem, response, status, headers) { };
    $scope.uploader.onErrorItem = function (fileItem, response, status, headers) {
        messageHub.showAlertError('Item error', response.err.message);
    };
    $scope.uploader.onCancelItem = function (fileItem, response, status, headers) { };
    $scope.uploader.onCompleteItem = function (fileItem, response, status, headers) {
        $scope.zipPaths.push($scope.projectFromZipPath(fileItem.file.name));
    };

    $scope.startZipMigration = function () {
        if (!$scope.uploader.queue || !$scope.uploader.queue.length) return false;
        let zipPaths = [];

        messageHub.updateLoadingDialog(
            "migration.zip.loading",
            'Configuration processing...'
        );

        for (const uploadedFile of $scope.uploader.queue) {
            const fileName = uploadedFile.file.name;
            zipPaths.push($scope.projectFromZipPath(fileName));
        }

        let body = {
            workspace: $scope.selectedWorkspace.name,
            zipPath: zipPaths,
        };

        $http.post("/services/js/ide-migration/server/migration/api/migration-rest-api.mjs/start-process-from-zip", body, {
            headers: { "Content-Type": "application/json" },
        }).then(
            function (response) {
                messageHub.hideLoadingDialog("migration.zip.loading");
                if (response.data.processInstanceId) {
                    $scope.migrationFinished = true;
                    $scope.zipMessagePanel.icon = "sap-icon--message-success";
                    $scope.zipMessagePanel.title = "Migration successful";
                    $scope.zipMessagePanel.subtitle = `Migrated project(s). You can now publish them from the workspace "${$scope.selectedWorkspace.name}".`;
                    $scope.migrationFinished = true;
                } else {
                    $scope.migrationFinished = false;
                    messageHub.showAlertError(noProcessErrorTitle, noProcessErrorDescription);
                    $scope.removeAllZips();
                }
            },
            function (response) {
                messageHub.hideLoadingDialog("migration.zip.loading");
                $scope.migrationFinished = false;
                messageHub.showAlertError("Migration failed", response.error.message);
                $scope.removeAllZips();
            }
        );
    };

    $scope.uploader.onCompleteAll = function () {
        $scope.startZipMigration();
    };

    $scope.removeAllZips = function () {
        $scope.uploader.clearQueue();
        $scope.zipPaths = [];
    };

    $scope.uploadAndMigrate = function () {
        messageHub.showLoadingDialog(
            "migration.zip.loading",
            "Migration in progress",
            'Uploading zip(s)...'
        );
        $scope.uploader.uploadAll();
    };

    // Live migration

    $scope.migrationWizard = {
        currentStep: 1,
        completedSteps: 0,
        stepsCount: 4,
        hanaLoading: false,
        migrating: false
    };

    $scope.duCombobox = {
        allSelected: false,
        selectedModelValue: null,
        selectedModelValues: [],
        onCBChange: function () {
            console.log($scope.duCombobox.selectedModelValue);
        },
        onMCBChange: function () {
            console.log($scope.duCombobox.selectedModelValues);
        },
        selectAll: function () {
            $scope.duCombobox.selectedModelValues.length = 0;
            if ($scope.duCombobox.allSelected) {
                for (let i = 0; i < $scope.deliveryUnits.length; i++) {
                    $scope.duCombobox.selectedModelValues.push($scope.deliveryUnits[i].value);
                }
            }
        },
    };

    $scope.deliveryUnits = [
        { value: 1, text: 'DU1' },
        { value: 2, text: 'DU2' },
        { value: 3, text: 'DU3' },
        { value: 4, text: 'DU4' },
    ];

    $scope.pwVisible = false;
    $scope.isDiffViewSplit = true;

    $scope.comboboxItems = [
        { value: 1, text: 'Item 1' },
        { value: 2, text: 'Item 2' },
        { value: 3, text: 'Item 3' },
        { value: 4, text: 'Item 4' },
        { value: 5, text: 'Item 5' }
    ];

    $scope.changes = [{
        id: 'm-1',
        path: '/workspace/example/example.js',
        type: "application/json",
        original: "var a = 0",
        modified: "let a = 1;",
    },
    {
        id: 'm-2',
        path: '/workspace/example/example2.js',
        type: "application/json",
        original: "var a = 0",
        modified: "let a = 1;",
    }];

    $scope.setDiffDataParams = function (file) {
        return JSON.stringify(file, (key, value) => {
            if (key === "id" || key === "path") {
                return undefined;
            }
            return value;
        });
    };

    $scope.changeDiffViewType = function (isSplit) {
        $scope.isDiffViewSplit = isSplit;
        if (isSplit) {
            messageHub.postMessage("git.diff.view.type", { type: "split" }, true);
        } else messageHub.postMessage("git.diff.view.type", { type: "unified" }, true);
    };

    $scope.createWorkspace = function () {
        messageHub.showFormDialog(
            'migrationCreateWorkspaceForm',
            'Create workspace',
            [
                {
                    id: "mwci1",
                    type: "input",
                    submitOnEnterId: "b1",
                    label: "Name",
                    required: true,
                    placeholder: "workspace name",
                    inputRules: {
                        excluded: $scope.workspaceNames,
                        patterns: ['^[^/:]*$'],
                    },
                },
            ],
            [{
                id: 'b1',
                type: 'emphasized',
                label: 'Create',
                whenValid: true,
            },
            {
                id: 'b2',
                type: 'transparent',
                label: 'Cancel',
            }],
            'migration.create.workspace',
            'Creating...',
        );
    };

    messageHub.onDidReceiveMessage(
        'migration.create.workspace',
        function (msg) {
            if (msg.data.buttonId === "b1") {
                workspaceApi.createWorkspace(msg.data.formData[0].value).then(function (response) {
                    messageHub.hideFormDialog('migrationCreateWorkspaceForm');
                    if (response.status !== 201) {
                        messageHub.showAlertError(
                            'Failed to create workspace',
                            `An unexpected error has occurred while trying create a workspace named '${msg.data.formData[0].value}'`
                        );
                        messageHub.setStatusError(`Unable to create workspace '${msg.data.formData[0].value}'`);
                    } else {
                        $scope.reloadWorkspaceList();
                        messageHub.setStatusMessage(`Created workspace '${msg.data.formData[0].value}'`);
                        messageHub.announceWorkspacesModified();
                    }
                });
            } else messageHub.hideFormDialog('migrationCreateWorkspaceForm');
        },
        true
    );

    $scope.pwVisibilityToggle = function () {
        $scope.neoPwVisible = !$scope.neoPwVisible;
    };

    $scope.revert = function (completedStepsCount) {
        $scope.migrationWizard.completedSteps = completedStepsCount;
    };

    $scope.gotoNextStep = function () {
        if ($scope.migrationWizard.currentStep > $scope.migrationWizard.completedSteps) {
            $scope.migrationWizard.migrating = true;
            $scope.migrationWizard.completedSteps = $scope.migrationWizard.currentStep;
        }

        if ($scope.migrationWizard.currentStep <= $scope.migrationWizard.stepsCount) {
            if ($scope.migrationWizard.currentStep === 2) {
                $scope.migrationWizard.hanaLoading = true;
                setTimeout(function () {
                    $scope.$apply(() => {
                        $scope.migrationWizard.hanaLoading = false;
                        $scope.gotoStep($scope.migrationWizard.currentStep + 1);
                    });
                }, 1000);
            } else $scope.gotoStep($scope.migrationWizard.currentStep + 1);
        }
    };

    $scope.gotoPreviousStep = function () {
        if ($scope.migrationWizard.currentStep > 1) {
            $scope.gotoStep($scope.migrationWizard.currentStep - 1);
        }
    };

    $scope.gotoStep = function (step) {
        $scope.migrationWizard.currentStep = step;
    };

    $scope.getIndicatorGlyph = function (step) {
        return step <= $scope.migrationWizard.completedSteps ? 'sap-icon--accept' : undefined;
    };

    $scope.isLastStep = function () {
        return $scope.migrationWizard.currentStep === $scope.migrationWizard.stepsCount;
    };

    $scope.allStepsCompleted = function () {
        return $scope.migrationWizard.completedSteps >= $scope.migrationWizard.stepsCount;
    };

    // History

    $scope.migrations = [];
    // Init
    $http.post("/services/js/ide-migration/server/migration/api/migration-rest-api.mjs/migrationsTrack", '{ "migrations": "empty" }', {
        headers: { "Content-Type": "application/json" },
    }).then(
        function (response) {
            $scope.migrations = response.data;
            $scope.hideTable = $scope.migrations === "empty";
            $scope.state.isBusy = false;
        },
        function (response) {
            if (response.data && response.data.error) {
                $scope.state.error = response.data.error.message;
            } else {
                $scope.state.error = "Unknown error. See console.";
            }
            $scope.state.error = true;
            $scope.state.isBusy = false;
            console.error(response);
        }
    );
    $scope.reloadWorkspaceList();
}]);