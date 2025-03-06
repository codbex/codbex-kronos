package com.codbex.kronos.integration.tests.ui;

import org.eclipse.dirigible.tests.UserInterfaceIntegrationTest;
import org.springframework.context.annotation.Import;

@Import(TestCfgs.class)
public abstract class KronosIntegrationTest extends UserInterfaceIntegrationTest {
}
