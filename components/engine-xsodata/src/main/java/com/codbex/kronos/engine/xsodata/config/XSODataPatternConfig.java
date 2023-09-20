/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.xsodata.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * The Class XSODataPatternConfig.
 */
@Component
public class XSODataPatternConfig {

	/** The application context. */
    @Autowired
    private ApplicationContext applicationContext;
    
    /**
	 * Xsodata context aware.
	 *
	 * @return the XSO data context aware
	 */
    @Bean
    public XSODataContextAware xsodataContextAware() {
    	XSODataContextAware xsodataContextAware = new XSODataContextAware();
        xsodataContextAware.setApplicationContext(applicationContext);
        return xsodataContextAware;
    }
}
