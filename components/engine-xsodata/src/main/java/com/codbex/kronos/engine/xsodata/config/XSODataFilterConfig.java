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
package com.codbex.kronos.engine.xsodata.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codbex.kronos.engine.xsodata.filter.XSODataForwardFilter;

/**
 * The Class XSODataFilterConfig.
 */
@Configuration
public class XSODataFilterConfig {

    /**
     * Security filter registration bean.
     *
     * @param xsodataFilter the security filter
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean<XSODataForwardFilter> xsodataFilterRegistrationBean(XSODataForwardFilter xsodataFilter) {
        FilterRegistrationBean<XSODataForwardFilter> filterRegistrationBean = new FilterRegistrationBean<>(xsodataFilter);

        filterRegistrationBean.setFilter(xsodataFilter);
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }

}
