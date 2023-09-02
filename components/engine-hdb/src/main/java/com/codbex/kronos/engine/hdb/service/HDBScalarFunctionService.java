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

import com.codbex.kronos.engine.hdb.domain.HDBScalarFunction;
import com.codbex.kronos.engine.hdb.repository.HDBScalarFunctionRepository;

/**
 * The Class HDBScalarFunctionService.
 */
@Service
@Transactional
public class HDBScalarFunctionService implements ArtefactService<HDBScalarFunction>, InitializingBean {
	
	/** The instance. */
	private static HDBScalarFunctionService INSTANCE;
	
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
	 * @return the HDBScalarFunction service
	 */
	public static HDBScalarFunctionService get() {
        return INSTANCE;
    }
	
	/** The HDBScalarFunction repository. */
    @Autowired
    private HDBScalarFunctionRepository hdbScalarFunctionRepository;

    /**
     * Gets the all.
     *
     * @return the all
     */
    @Override
    public List<HDBScalarFunction> getAll() {
        return hdbScalarFunctionRepository.findAll();
    }

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<HDBScalarFunction> getPages(Pageable pageable) {
        return hdbScalarFunctionRepository.findAll(pageable);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the HDBScalarFunction
     */
    @Override
    public HDBScalarFunction findById(Long id) {
        Optional<HDBScalarFunction> hdbScalarFunction = hdbScalarFunctionRepository.findById(id);
        if (hdbScalarFunction.isPresent()) {
            return hdbScalarFunction.get();
        } else {
            throw new IllegalArgumentException("HDBScalarFunction with id does not exist: " + id);
        }
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the HDBScalarFunction
     */
    @Override
    public HDBScalarFunction findByName(String name) {
    	HDBScalarFunction filter = new HDBScalarFunction();
        filter.setName(name);
        Example<HDBScalarFunction> example = Example.of(filter);
        Optional<HDBScalarFunction> hdbScalarFunction = hdbScalarFunctionRepository.findOne(example);
        if (hdbScalarFunction.isPresent()) {
            return hdbScalarFunction.get();
        } else {
            throw new IllegalArgumentException("HDBScalarFunction with name does not exist: " + name);
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
    public List<HDBScalarFunction> findByLocation(String location) {
    	HDBScalarFunction filter = new HDBScalarFunction();
        filter.setName(location);
        Example<HDBScalarFunction> example = Example.of(filter);
        List<HDBScalarFunction> list = hdbScalarFunctionRepository.findAll(example);
        return list;
    }
    
    /**
     * Find by key.
     *
     * @param key the key
     * @return the HDBScalarFunction
     */
    @Override
    @Transactional(readOnly = true)
    public HDBScalarFunction findByKey(String key) {
    	HDBScalarFunction filter = new HDBScalarFunction();
        filter.setKey(key);
        Example<HDBScalarFunction> example = Example.of(filter);
        Optional<HDBScalarFunction> hdbScalarFunction = hdbScalarFunctionRepository.findOne(example);
        if (hdbScalarFunction.isPresent()) {
            return hdbScalarFunction.get();
        }
        return null;
    }

    /**
     * Save.
     *
     * @param hdbScalarFunction the HDBScalarFunction
     * @return the HDBScalarFunction
     */
    @Override
    public HDBScalarFunction save(HDBScalarFunction hdbScalarFunction) {
        return hdbScalarFunctionRepository.saveAndFlush(hdbScalarFunction);
    }

    /**
     * Delete.
     *
     * @param hdbScalarFunction the HDBScalarFunction
     */
    @Override
    public void delete(HDBScalarFunction hdbScalarFunction) {
    	hdbScalarFunctionRepository.delete(hdbScalarFunction);
    }

}
