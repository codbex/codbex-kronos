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
package com.codbex.kronos.xsaccess.ds.filter;

import org.eclipse.dirigible.components.api.http.HttpRequestFacade;
import org.eclipse.dirigible.components.api.utils.EscapeFacade;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.repository.api.IRepositoryStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.xsaccess.ds.api.IAccessCoreService;
import com.codbex.kronos.xsaccess.ds.api.AccessException;
import com.codbex.kronos.xsaccess.ds.model.access.AccessDefinition;
import com.codbex.kronos.xsaccess.ds.service.AccessCoreService;
import com.codbex.kronos.xsaccess.ds.verifier.AccessVerifier;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Class SecurityFilter.
 */
@WebFilter(urlPatterns = {

        "/services/js/*",
        "/services/public/*",
        "/services/web/*",
        "/services/wiki/*",
        "/services/command/*",
        "/services/kronos/*",

        "/public/js/*",
        "/public/public/*",
        "/public/web/*",
        "/public/wiki/*",
        "/public/command/*",
        "/public/kronos/*",

        "/odata/v2/*"

}, filterName = "Kronos SecurityFilter", description = "Check all the URIs for access permissions")
public class SecurityFilter implements Filter {

    /** The Constant PATH_WEB_RESOURCES. */
    private static final String PATH_WEB_RESOURCES = "/web/resources";

    /** The Constant ROLE_PUBLIC. */
    private static final String ROLE_PUBLIC = "Public";

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);
    
    /** The Constant SECURED_PREFIXES. */
    private static final Set<String> SECURED_PREFIXES = new HashSet<>();
    
    /** The Constant accessCoreService. */
    private static final IAccessCoreService accessCoreService = new AccessCoreService();

    /**
     * Inits the.
     *
     * @param filterConfig the filter config
     * @throws ServletException the servlet exception
     */
    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SECURED_PREFIXES.add("/js");
        SECURED_PREFIXES.add("/rhino");
        SECURED_PREFIXES.add("/nashorn");
        SECURED_PREFIXES.add("/v8");
        SECURED_PREFIXES.add("/public");
        SECURED_PREFIXES.add("/web");
        SECURED_PREFIXES.add("/wiki");
        SECURED_PREFIXES.add("/command");
        SECURED_PREFIXES.add("/kronos");
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
    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            String path = httpServletRequest.getPathInfo() != null ? httpServletRequest.getPathInfo() : IRepositoryStructure.SEPARATOR;
            if (!path.startsWith(PATH_WEB_RESOURCES)) {
                for (String prefix : SECURED_PREFIXES) {
                    if (path.startsWith(prefix)) {
                        path = path.substring(prefix.length());
                        break;
                    }
                }
                String method = httpServletRequest.getMethod();

                boolean isInRole = false;
                Principal principal = httpServletRequest.getUserPrincipal();

                AccessDefinition accessDefinition = AccessVerifier.getMatchingAccessDefinitions(accessCoreService, path, method);

                if (accessDefinition != null) {
                    List<String> accessRoles = accessDefinition.getAuthorizationRolesAsList();
                    if (accessRoles.isEmpty()) {
                        chain.doFilter(request, response);
                        return;
                    }
                    if (principal == null && !Configuration.isJwtModeEnabled()) {
                        // white list check
                        for (String role : accessRoles) {
                            if (ROLE_PUBLIC.equalsIgnoreCase(role)) {
                                isInRole = true;
                                break;
                            }
                        }

                        if (!isInRole) {
                            forbidden(path, "No logged in user", httpServletResponse);
                            return;
                        }
                    } else {
                        for (String role : accessRoles) {
                            if (ROLE_PUBLIC.equalsIgnoreCase(role) || HttpRequestFacade.isUserInRole(role)) {
                                isInRole = true;
                                break;
                            }
                        }
                        if (!isInRole) {
                            forbidden(path, "The logged in user does not have any of the required roles for the requested URI", httpServletResponse);
                            return;
                        }
                    }
                } else {
                    if (!Configuration.isAnonymousModeEnabled() && principal == null && !Configuration.isJwtModeEnabled()) {
                        forbidden(path, "No logged in user and no white list constraints", httpServletResponse);
                        return;
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            throw new ServletException(e);
        } catch (AccessException e) {
            throw new ServletException(e);
        }

        chain.doFilter(request, response);
    }

    /**
     * Forbidden.
     *
     * @param uri      the uri
     * @param message  the message
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void forbidden(String uri, String message, HttpServletResponse response) throws IOException {
        String error = String.format("Requested URI [%s] is forbidden: %s", uri, message);
        logger.warn(error);
        error = EscapeFacade.escapeHtml4(error);
        error = EscapeFacade.escapeJavascript(error);
        response.sendError(HttpServletResponse.SC_FORBIDDEN, error);
    }

    /**
     * Destroy.
     */
    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        // Not Used
    }

}
