package com.codbex.kronos.integration.tests;

import org.eclipse.dirigible.integration.tests.api.SecurityIT;
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
        SecurityIT.class, //
        TerminalIT.class//
})
public class DirigibileCommonTestSuiteIT {
}
