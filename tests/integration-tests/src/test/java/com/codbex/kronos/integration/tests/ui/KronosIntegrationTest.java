package com.codbex.kronos.integration.tests.ui;

import org.eclipse.dirigible.tests.TestConfigurations;
import org.eclipse.dirigible.tests.UserInterfaceIntegrationTest;
import org.springframework.context.annotation.Import;

@Import(TestConfigurations.class)
public abstract class KronosIntegrationTest extends UserInterfaceIntegrationTest {
}
