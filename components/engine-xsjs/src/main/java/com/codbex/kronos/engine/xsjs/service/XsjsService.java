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
package com.codbex.kronos.engine.xsjs.service;

import java.util.Map;

import org.eclipse.dirigible.graalium.core.JavascriptSourceProvider;
import org.eclipse.dirigible.repository.api.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codbex.kronos.engine.KronosSourceProvider;

@Service
public class XsjsService implements InitializingBean {
	
	/** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(XsjsService.class);

    /** The dirigible source provider. */
    private KronosSourceProvider sourceProvider;
    
    /** The instance. */
    private static XsjsService INSTANCE;
    
	/** The repository. */
	private final IRepository repository;
	
	/** The handler. */
    private XsjsHandler handler;
	
	@Autowired
	public XsjsService(IRepository repository) {
		this.repository = repository;
		this.sourceProvider = new KronosSourceProvider();
    	this.handler = new XsjsHandler(getRepository(), this.sourceProvider);
	}
    
    /**
     * After properties set.
     *
     * @throws Exception the exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;
    }

    /**
     * Gets the.
     *
     * @return the javascript service
     */
    public static XsjsService get() {
        return INSTANCE;
    }
    
    public IRepository getRepository() {
		return repository;
	}
    
	/**
	 * Gets the source provider.
	 *
	 * @return the source provider
	 */
	public JavascriptSourceProvider getSourceProvider() {
		return sourceProvider;
	}

	/**
	 * Handle request.
	 *
	 * @param projectName the project name
	 * @param projectFilePath the project file path
	 * @param projectFilePathParam the project file path param
	 * @param parameters the parameters
	 * @param debug the debug
	 * @return the object
	 */
	public Object handleRequest(String projectName, String projectFilePath, String projectFilePathParam, Map<Object, Object> parameters, boolean debug) {
		return handler.handleRequest(projectName, projectFilePath, projectFilePathParam, parameters, debug);
	}
	
	/**
     * Handle callback.
     *
     * @param filePath the file path
     * @param parameters the parameters
     * @return the object
     */
    public Object handleCallback(String filePath, Map<Object, Object> parameters) {
    	return handler.handleCallback(filePath, parameters);
    }
    
}
