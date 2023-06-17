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
package com.codbex.kronos.engine.xssecurity.service;

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

import com.codbex.kronos.engine.xssecurity.domain.XSAccess;
import com.codbex.kronos.engine.xssecurity.repository.XSAccessRepository;


/**
 * The Class XSAccessService.
 */
@Service
@Transactional
public class XSAccessService implements ArtefactService<XSAccess>, InitializingBean {
	
	/** The instance. */
	private static XSAccessService INSTANCE;
	
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
	 * @return the XSAccess service
	 */
	public static XSAccessService get() {
        return INSTANCE;
    }
	
	/** The XSAccess repository. */
    @Autowired
    private XSAccessRepository xsaccessRepository;

    /**
     * Gets the all.
     *
     * @return the all
     */
    @Override
    public List<XSAccess> getAll() {
        return xsaccessRepository.findAll();
    }

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<XSAccess> getPages(Pageable pageable) {
        return xsaccessRepository.findAll(pageable);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the XSAccess
     */
    @Override
    public XSAccess findById(Long id) {
        Optional<XSAccess> xsaccess = xsaccessRepository.findById(id);
        if (xsaccess.isPresent()) {
            return xsaccess.get();
        } else {
            throw new IllegalArgumentException("XSAccess with id does not exist: " + id);
        }
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the XSAccess
     */
    @Override
    public XSAccess findByName(String name) {
    	XSAccess filter = new XSAccess();
        filter.setName(name);
        Example<XSAccess> example = Example.of(filter);
        Optional<XSAccess> xsaccess = xsaccessRepository.findOne(example);
        if (xsaccess.isPresent()) {
            return xsaccess.get();
        } else {
            throw new IllegalArgumentException("XSAccess with name does not exist: " + name);
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
    public List<XSAccess> findByLocation(String location) {
    	XSAccess filter = new XSAccess();
        filter.setName(location);
        Example<XSAccess> example = Example.of(filter);
        List<XSAccess> list = xsaccessRepository.findAll(example);
        return list;
    }
    
    /**
     * Find by key.
     *
     * @param key the key
     * @return the XSAccess
     */
    @Override
    @Transactional(readOnly = true)
    public XSAccess findByKey(String key) {
    	XSAccess filter = new XSAccess();
        filter.setKey(key);
        Example<XSAccess> example = Example.of(filter);
        Optional<XSAccess> xsaccess = xsaccessRepository.findOne(example);
        if (xsaccess.isPresent()) {
            return xsaccess.get();
        }
        return null;
    }

    /**
     * Save.
     *
     * @param xsaccess the XSAccess
     * @return the XSAccess
     */
    @Override
    public XSAccess save(XSAccess xsaccess) {
        return xsaccessRepository.saveAndFlush(xsaccess);
    }

    /**
     * Delete.
     *
     * @param xsaccess the XSAccess
     */
    @Override
    public void delete(XSAccess xsaccess) {
    	xsaccessRepository.delete(xsaccess);
    }

}
