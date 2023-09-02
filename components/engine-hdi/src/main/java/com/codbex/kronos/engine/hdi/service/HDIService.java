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
package com.codbex.kronos.engine.hdi.service;

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

import com.codbex.kronos.engine.hdi.domain.HDI;
import com.codbex.kronos.engine.hdi.repository.HDIRepository;

/**
 * The Class HDIService.
 */
@Service
@Transactional
public class HDIService implements ArtefactService<HDI>, InitializingBean {
	
	/** The instance. */
	private static HDIService INSTANCE;
	
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
	 * @return the HDI service
	 */
	public static HDIService get() {
        return INSTANCE;
    }
	
	/** The HDI repository. */
    @Autowired
    private HDIRepository hdiRepository;

    /**
     * Gets the all.
     *
     * @return the all
     */
    @Override
    public List<HDI> getAll() {
        return hdiRepository.findAll();
    }

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<HDI> getPages(Pageable pageable) {
        return hdiRepository.findAll(pageable);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the HDI
     */
    @Override
    public HDI findById(Long id) {
        Optional<HDI> hdi = hdiRepository.findById(id);
        if (hdi.isPresent()) {
            return hdi.get();
        } else {
            throw new IllegalArgumentException("HDI with id does not exist: " + id);
        }
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the HDI
     */
    @Override
    public HDI findByName(String name) {
    	HDI filter = new HDI();
        filter.setName(name);
        Example<HDI> example = Example.of(filter);
        Optional<HDI> hdi = hdiRepository.findOne(example);
        if (hdi.isPresent()) {
            return hdi.get();
        } else {
            throw new IllegalArgumentException("HDI with name does not exist: " + name);
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
    public List<HDI> findByLocation(String location) {
    	HDI filter = new HDI();
        filter.setName(location);
        Example<HDI> example = Example.of(filter);
        List<HDI> list = hdiRepository.findAll(example);
        return list;
    }
    
    /**
     * Find by key.
     *
     * @param key the key
     * @return the HDI
     */
    @Override
    @Transactional(readOnly = true)
    public HDI findByKey(String key) {
    	HDI filter = new HDI();
        filter.setKey(key);
        Example<HDI> example = Example.of(filter);
        Optional<HDI> hdi = hdiRepository.findOne(example);
        if (hdi.isPresent()) {
            return hdi.get();
        }
        return null;
    }

    /**
     * Save.
     *
     * @param hdi the HDI
     * @return the HDI
     */
    @Override
    public HDI save(HDI hdi) {
        return hdiRepository.saveAndFlush(hdi);
    }

    /**
     * Delete.
     *
     * @param hdi the HDI
     */
    @Override
    public void delete(HDI hdi) {
    	hdiRepository.delete(hdi);
    }

}
