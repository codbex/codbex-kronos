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

import com.codbex.kronos.engine.hdb.domain.HDBDD;
import com.codbex.kronos.engine.hdb.repository.HDBDDRepository;

/**
 * The Class HDBDDService.
 */
@Service
@Transactional
public class HDBDDService implements ArtefactService<HDBDD>, InitializingBean {
	
	/** The instance. */
	private static HDBDDService INSTANCE;
	
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
	 * @return the HDBDD service
	 */
	public static HDBDDService get() {
        return INSTANCE;
    }
	
	/** The HDBDD repository. */
    @Autowired
    private HDBDDRepository hdbddRepository;

    /**
     * Gets the all.
     *
     * @return the all
     */
    @Override
    public List<HDBDD> getAll() {
        return hdbddRepository.findAll();
    }

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<HDBDD> getPages(Pageable pageable) {
        return hdbddRepository.findAll(pageable);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the HDBDD
     */
    @Override
    public HDBDD findById(Long id) {
        Optional<HDBDD> hdbdd = hdbddRepository.findById(id);
        if (hdbdd.isPresent()) {
            return hdbdd.get();
        } else {
            throw new IllegalArgumentException("HDBDD with id does not exist: " + id);
        }
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the HDBDD
     */
    @Override
    public HDBDD findByName(String name) {
    	HDBDD filter = new HDBDD();
        filter.setName(name);
        Example<HDBDD> example = Example.of(filter);
        Optional<HDBDD> hdbdd = hdbddRepository.findOne(example);
        if (hdbdd.isPresent()) {
            return hdbdd.get();
        } else {
            throw new IllegalArgumentException("HDBDD with name does not exist: " + name);
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
    public List<HDBDD> findByLocation(String location) {
    	HDBDD filter = new HDBDD();
        filter.setName(location);
        Example<HDBDD> example = Example.of(filter);
        List<HDBDD> list = hdbddRepository.findAll(example);
        return list;
    }
    
    /**
     * Find by key.
     *
     * @param key the key
     * @return the HDBDD
     */
    @Override
    @Transactional(readOnly = true)
    public HDBDD findByKey(String key) {
    	HDBDD filter = new HDBDD();
        filter.setKey(key);
        Example<HDBDD> example = Example.of(filter);
        Optional<HDBDD> hdbdd = hdbddRepository.findOne(example);
        if (hdbdd.isPresent()) {
            return hdbdd.get();
        }
        return null;
    }

    /**
     * Save.
     *
     * @param hdbdd the HDBDD
     * @return the HDBDD
     */
    @Override
    public HDBDD save(HDBDD hdbdd) {
        return hdbddRepository.saveAndFlush(hdbdd);
    }

    /**
     * Delete.
     *
     * @param hdbdd the HDBDD
     */
    @Override
    public void delete(HDBDD hdbdd) {
    	hdbddRepository.delete(hdbdd);
    }

}
