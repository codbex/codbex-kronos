package com.codbex.kronos.integration.tests.config;

import org.eclipse.dirigible.tests.IDE;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfigurations {

    // TODO: remove this bean and all OldUI* classes once the project is adapted to the new UI
    @Lazy
    @Primary
    @Bean
    IDE provideIDE(OldUiIde oldUiIde) {
        return oldUiIde;
    }
}
