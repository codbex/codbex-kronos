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
package com.codbex.kronos.xsodata.ds.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * The Class XSODataForwardFilter.
 */
@WebFilter(urlPatterns = {"/*"}, filterName = "XSODataForwardFilter")
public class XSODataForwardFilter implements Filter {

    /**
     * Inits the.
     *
     * @param filterConfig the filter config
     * @throws ServletException the servlet exception
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //
    }

    /**
     * Do filter.
     *
     * @param request the request
     * @param response the response
     * @param chain the chain
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        //only on production case
        if (httpServletRequest.getHeader("Dirigible-Editor") == null) {
            String uri = httpServletRequest.getRequestURI();
            int index = uri.indexOf(".xsodata");
            if (index > 0) {
                String parameters = "";
                if (uri.length() > index + (".xsodata".length() - 1)) {
                    parameters = uri.substring(index + ".xsodata".length());
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("/odata/v2/" + parameters);
                dispatcher.forward(request, response);
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * Destroy.
     */
    @Override
    public void destroy() {
        //
    }

}
