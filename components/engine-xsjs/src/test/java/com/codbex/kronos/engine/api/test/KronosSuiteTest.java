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
package com.codbex.kronos.engine.api.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.codbex.kronos.engine.xsjs.service.XsjsService;

@WithMockUser
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
@EntityScan(value = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
public class KronosSuiteTest {
	
	@Autowired
	private XsjsService xsjsService;
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;
    
    @Test
	public void executeDatabaseTest() throws Exception {
		xsjsService.handleRequest("test", "kronos/db/api/db.xsjs", null, null, false);
	}
	
	/**
	 * The Class TestConfiguration.
	 */
	@EnableJpaRepositories(basePackages = "com.codbex.kronos")
	@SpringBootApplication(scanBasePackages = { "com.codbex.kronos", "org.eclipse.dirigible.components" })
	@EnableScheduling
	static class TestConfiguration {
	}

}
