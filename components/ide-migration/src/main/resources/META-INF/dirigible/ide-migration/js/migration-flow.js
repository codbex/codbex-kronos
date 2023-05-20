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
migrationLaunchView.factory("migrationFlow", ["$messageHub", function ($messageHub) {
    let currentStepIndex = 0;

    let activeFlow = null;

    function setActiveFlow(type) {
        activeFlow = type;
    }

    function getActiveFlow() {
        return activeFlow;
    }

    function goForward() {
        currentStepIndex++;
    }

    function goBack() {
        currentStepIndex--;
    }

    function goToStep(index, flowType, step, data) {
        currentStepIndex = index;
        activeFlow = flowType;
        $messageHub.message(step.onLoad, data);
    }

    function getCurrentStepIndex() {
        return currentStepIndex;
    }

    return {
        setActiveFlow,
        getActiveFlow,
        goForward,
        goBack,
        goToStep,
        getCurrentStepIndex,
    }

}]);
