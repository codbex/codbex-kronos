package com.codbex.kronos;

import static org.mockito.Mockito.when;

import org.eclipse.dirigible.components.base.tenant.DefaultTenant;
import org.eclipse.dirigible.components.base.tenant.Tenant;
import org.eclipse.dirigible.components.base.tenant.TenantContext;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

  @Bean
  TenantContext createTenantContext() {
    TenantContext context = Mockito.mock(TenantContext.class);
    when(context.isInitialized()).thenReturn(false);
    when(context.isNotInitialized()).thenReturn(true);
    return context;
  }

  @MockBean
  @DefaultTenant
  private Tenant defaultTenant;
}
