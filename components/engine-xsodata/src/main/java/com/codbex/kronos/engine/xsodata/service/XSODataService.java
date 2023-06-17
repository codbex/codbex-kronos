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
package com.codbex.kronos.engine.xsodata.service;

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

import com.codbex.kronos.engine.xsodata.domain.XSOData;
import com.codbex.kronos.engine.xsodata.repository.XSODataRepository;

/**
 * The Class XSODataService.
 */
@Service
@Transactional
public class XSODataService implements ArtefactService<XSOData>, InitializingBean {
	
	/** The instance. */
	private static XSODataService INSTANCE;
	
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
	 * @return the XSOData service
	 */
	public static XSODataService get() {
        return INSTANCE;
    }
	
	/** The XSOData repository. */
    @Autowired
    private XSODataRepository odataRepository;

    /**
     * Gets the all.
     *
     * @return the all
     */
    @Override
    public List<XSOData> getAll() {
        return odataRepository.findAll();
    }

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<XSOData> getPages(Pageable pageable) {
        return odataRepository.findAll(pageable);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the OData
     */
    @Override
    public XSOData findById(Long id) {
        Optional<XSOData> odata = odataRepository.findById(id);
        if (odata.isPresent()) {
            return odata.get();
        } else {
            throw new IllegalArgumentException("XSOData with id does not exist: " + id);
        }
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the XSOData
     */
    @Override
    public XSOData findByName(String name) {
    	XSOData filter = new XSOData();
        filter.setName(name);
        Example<XSOData> example = Example.of(filter);
        Optional<XSOData> odata = odataRepository.findOne(example);
        if (odata.isPresent()) {
            return odata.get();
        } else {
            throw new IllegalArgumentException("XSOData with name does not exist: " + name);
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
    public List<XSOData> findByLocation(String location) {
    	XSOData filter = new XSOData();
        filter.setName(location);
        Example<XSOData> example = Example.of(filter);
        List<XSOData> list = odataRepository.findAll(example);
        return list;
    }
    
    /**
     * Find by key.
     *
     * @param key the key
     * @return the XSOData
     */
    @Override
    @Transactional(readOnly = true)
    public XSOData findByKey(String key) {
    	XSOData filter = new XSOData();
        filter.setKey(key);
        Example<XSOData> example = Example.of(filter);
        Optional<XSOData> odata = odataRepository.findOne(example);
        if (odata.isPresent()) {
            return odata.get();
        }
        return null;
    }

    /**
     * Save.
     *
     * @param odata the XSOData
     * @return the XSOData
     */
    @Override
    public XSOData save(XSOData odata) {
        return odataRepository.saveAndFlush(odata);
    }

    /**
     * Delete.
     *
     * @param odata the XSOData
     */
    @Override
    public void delete(XSOData odata) {
    	odataRepository.delete(odata);
    }

}
