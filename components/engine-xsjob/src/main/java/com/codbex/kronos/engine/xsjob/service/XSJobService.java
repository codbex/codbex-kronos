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
package com.codbex.kronos.engine.xsjob.service;

import java.util.List;
import java.util.Optional;

import org.eclipse.dirigible.components.base.artefact.ArtefactService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codbex.kronos.engine.xsjob.domain.XSJob;
import com.codbex.kronos.engine.xsjob.repository.XSJobRepository;


/**
 * The Class XSJobService.
 */
@Service
@Transactional
public class XSJobService implements ArtefactService<XSJob>, InitializingBean {
	
	/** The instance. */
	private static XSJobService INSTANCE;
	
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
	 * @return the XSJob service
	 */
	public static XSJobService get() {
        return INSTANCE;
    }
	
	/** The XSJob repository. */
    @Autowired
    private XSJobRepository xsjobRepository;

    /**
     * Gets the all.
     *
     * @return the all
     */
    @Override
    public List<XSJob> getAll() {
        return xsjobRepository.findAll();
    }

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<XSJob> getPages(Pageable pageable) {
        return xsjobRepository.findAll(pageable);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the XSJob
     */
    @Override
    public XSJob findById(Long id) {
        Optional<XSJob> xsjob = xsjobRepository.findById(id);
        if (xsjob.isPresent()) {
            return xsjob.get();
        } else {
            throw new IllegalArgumentException("XSJob with id does not exist: " + id);
        }
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the XSJob
     */
    @Override
    public XSJob findByName(String name) {
    	XSJob filter = new XSJob();
        filter.setName(name);
        Example<XSJob> example = Example.of(filter);
        Optional<XSJob> xsjob = xsjobRepository.findOne(example);
        if (xsjob.isPresent()) {
            return xsjob.get();
        } else {
            throw new IllegalArgumentException("XSJob with name does not exist: " + name);
        }
    }
    
    /**
     * Find by location.
     *
     * @param location the location
     * @return the list
     */
    @Override
    @Transactional(readOnly = true)
    public List<XSJob> findByLocation(String location) {
    	XSJob filter = new XSJob();
        filter.setName(location);
        Example<XSJob> example = Example.of(filter);
        List<XSJob> list = xsjobRepository.findAll(example);
        return list;
    }
    
    /**
     * Find by key.
     *
     * @param key the key
     * @return the XSJob
     */
    @Override
    @Transactional(readOnly = true)
    public XSJob findByKey(String key) {
    	XSJob filter = new XSJob();
        filter.setKey(key);
        Example<XSJob> example = Example.of(filter);
        Optional<XSJob> xsjob = xsjobRepository.findOne(example);
        if (xsjob.isPresent()) {
            return xsjob.get();
        }
        return null;
    }

    /**
     * Save.
     *
     * @param xsjob the XSJob
     * @return the XSJob
     */
    @Override
    public XSJob save(XSJob xsjob) {
        return xsjobRepository.saveAndFlush(xsjob);
    }

    /**
     * Delete.
     *
     * @param xsjob the XSJob
     */
    @Override
    public void delete(XSJob xsjob) {
    	xsjobRepository.delete(xsjob);
    }

}
