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

import com.codbex.kronos.engine.hdb.domain.HDBProcedure;
import com.codbex.kronos.engine.hdb.repository.HDBProcedureRepository;

/**
 * The Class HDBProcedureService.
 */
@Service
@Transactional
public class HDBProcedureService implements ArtefactService<HDBProcedure>, InitializingBean {
	
	/** The instance. */
	private static HDBProcedureService INSTANCE;
	
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
	 * @return the HDBProcedure service
	 */
	public static HDBProcedureService get() {
        return INSTANCE;
    }
	
	/** The HDBProcedure repository. */
    @Autowired
    private HDBProcedureRepository hdbProcedureRepository;

    /**
     * Gets the all.
     *
     * @return the all
     */
    @Override
    public List<HDBProcedure> getAll() {
        return hdbProcedureRepository.findAll();
    }

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<HDBProcedure> getPages(Pageable pageable) {
        return hdbProcedureRepository.findAll(pageable);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the HDBProcedure
     */
    @Override
    public HDBProcedure findById(Long id) {
        Optional<HDBProcedure> hdbProcedure = hdbProcedureRepository.findById(id);
        if (hdbProcedure.isPresent()) {
            return hdbProcedure.get();
        } else {
            throw new IllegalArgumentException("HDBProcedure with id does not exist: " + id);
        }
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the HDBProcedure
     */
    @Override
    public HDBProcedure findByName(String name) {
    	HDBProcedure filter = new HDBProcedure();
        filter.setName(name);
        Example<HDBProcedure> example = Example.of(filter);
        Optional<HDBProcedure> hdbProcedure = hdbProcedureRepository.findOne(example);
        if (hdbProcedure.isPresent()) {
            return hdbProcedure.get();
        } else {
            throw new IllegalArgumentException("HDBProcedure with name does not exist: " + name);
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
    public List<HDBProcedure> findByLocation(String location) {
    	HDBProcedure filter = new HDBProcedure();
        filter.setName(location);
        Example<HDBProcedure> example = Example.of(filter);
        List<HDBProcedure> list = hdbProcedureRepository.findAll(example);
        return list;
    }
    
    /**
     * Find by key.
     *
     * @param key the key
     * @return the HDBProcedure
     */
    @Override
    @Transactional(readOnly = true)
    public HDBProcedure findByKey(String key) {
    	HDBProcedure filter = new HDBProcedure();
        filter.setKey(key);
        Example<HDBProcedure> example = Example.of(filter);
        Optional<HDBProcedure> hdbProcedure = hdbProcedureRepository.findOne(example);
        if (hdbProcedure.isPresent()) {
            return hdbProcedure.get();
        }
        return null;
    }

    /**
     * Save.
     *
     * @param hdbProcedure the HDBProcedure
     * @return the HDBProcedure
     */
    @Override
    public HDBProcedure save(HDBProcedure hdbProcedure) {
        return hdbProcedureRepository.saveAndFlush(hdbProcedure);
    }

    /**
     * Delete.
     *
     * @param hdbProcedure the HDBProcedure
     */
    @Override
    public void delete(HDBProcedure hdbProcedure) {
    	hdbProcedureRepository.delete(hdbProcedure);
    }

}
