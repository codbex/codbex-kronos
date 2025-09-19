/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.integration.tests;

import org.eclipse.dirigible.integration.tests.api.SecurityIT;
import org.eclipse.dirigible.integration.tests.api.java.db.SchemaExportImportIT;
import org.eclipse.dirigible.integration.tests.api.java.messaging.MessagingFacadeIT;
import org.eclipse.dirigible.integration.tests.api.javascript.cms.CmsSuiteIT;
import org.eclipse.dirigible.integration.tests.api.rest.ODataAPIIT;
import org.eclipse.dirigible.integration.tests.ui.tests.*;
import org.eclipse.dirigible.integration.tests.ui.tests.camel.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({//
        ApproveLeaveRequestBpmIT.class, //
        BPMStarterTemplateIT.class, //
        CamelCronRouteStarterTemplateIT.class, //
        CamelHttpRouteStarterTemplateIT.class, //
        CamelDirigibleJavaScriptComponentCronRouteIT.class, //
        CamelDirigibleJavaScriptComponentHttpRouteIT.class, //
        CamelDirigibleTwoStepsJSInvokerCronRouteIT.class, //
        CamelDirigibleTwoStepsJSInvokerHttpRouteIT.class, //
        CamelExtractTransformLoadJdbcIT.class, //
        CamelExtractTransformLoadTypescriptIT.class, //
        CmsSuiteIT.class, //
        CreateNewProjectIT.class, //
        CsvimIT.class, //
        CustomSecurityIT.class, //
        DatabasePerspectiveIT.class, //
        DeclineLeaveRequestBpmIT.class, //
        GitPerspectiveIT.class, //
        HomepageRedirectIT.class, //
        MailIT.class, //
        MessagingFacadeIT.class, //
        ODataAPIIT.class, //
        SchemaExportImportIT.class, //
        SecurityIT.class, //
        TerminalIT.class//
})
public class DirigibleCommonTestSuiteIT {
}
