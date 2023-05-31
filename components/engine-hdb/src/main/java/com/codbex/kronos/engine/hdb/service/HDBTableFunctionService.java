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
package com.codbex.kronos.engine.hdb.service;

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

import com.codbex.kronos.engine.hdb.domain.HDBTableFunction;
import com.codbex.kronos.engine.hdb.repository.HDBTableFunctionRepository;

/**
 * The Class HDBTableFunctionService.
 */
@Service
@Transactional
public class HDBTableFunctionService implements ArtefactService<HDBTableFunction>, InitializingBean {
	
	/** The instance. */
	private static HDBTableFunctionService INSTANCE;
	
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
	 * @return the HDBTableFunction service
	 */
	public static HDBTableFunctionService get() {
        return INSTANCE;
    }
	
	/** The HDBTableFunction repository. */
    @Autowired
    private HDBTableFunctionRepository hdbTableFunctionRepository;

    /**
     * Gets the all.
     *
     * @return the all
     */
    @Override
    public List<HDBTableFunction> getAll() {
        return hdbTableFunctionRepository.findAll();
    }

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<HDBTableFunction> getPages(Pageable pageable) {
        return hdbTableFunctionRepository.findAll(pageable);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the HDBTableFunction
     */
    @Override
    public HDBTableFunction findById(Long id) {
        Optional<HDBTableFunction> hdbTableFunction = hdbTableFunctionRepository.findById(id);
        if (hdbTableFunction.isPresent()) {
            return hdbTableFunction.get();
        } else {
            throw new IllegalArgumentException("HDBTableFunction with id does not exist: " + id);
        }
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the HDBTableFunction
     */
    @Override
    public HDBTableFunction findByName(String name) {
    	HDBTableFunction filter = new HDBTableFunction();
        filter.setName(name);
        Example<HDBTableFunction> example = Example.of(filter);
        Optional<HDBTableFunction> hdbTableFunction = hdbTableFunctionRepository.findOne(example);
        if (hdbTableFunction.isPresent()) {
            return hdbTableFunction.get();
        } else {
            throw new IllegalArgumentException("HDBTableFunction with name does not exist: " + name);
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
    public List<HDBTableFunction> findByLocation(String location) {
    	HDBTableFunction filter = new HDBTableFunction();
        filter.setName(location);
        Example<HDBTableFunction> example = Example.of(filter);
        List<HDBTableFunction> list = hdbTableFunctionRepository.findAll(example);
        return list;
    }
    
    /**
     * Find by key.
     *
     * @param key the key
     * @return the HDBTableFunction
     */
    @Override
    @Transactional(readOnly = true)
    public HDBTableFunction findByKey(String key) {
    	HDBTableFunction filter = new HDBTableFunction();
        filter.setKey(key);
        Example<HDBTableFunction> example = Example.of(filter);
        Optional<HDBTableFunction> hdbTableFunction = hdbTableFunctionRepository.findOne(example);
        if (hdbTableFunction.isPresent()) {
            return hdbTableFunction.get();
        }
        return null;
    }

    /**
     * Save.
     *
     * @param hdbTableFunction the HDBTableFunction
     * @return the HDBTableFunction
     */
    @Override
    public HDBTableFunction save(HDBTableFunction hdbTableFunction) {
        return hdbTableFunctionRepository.saveAndFlush(hdbTableFunction);
    }

    /**
     * Delete.
     *
     * @param hdbTableFunction the HDBTableFunction
     */
    @Override
    public void delete(HDBTableFunction hdbTableFunction) {
    	hdbTableFunctionRepository.delete(hdbTableFunction);
    }

}
