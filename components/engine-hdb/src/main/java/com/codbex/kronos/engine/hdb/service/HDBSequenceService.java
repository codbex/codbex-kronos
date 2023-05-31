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

import com.codbex.kronos.engine.hdb.domain.HDBSequence;
import com.codbex.kronos.engine.hdb.repository.HDBSequenceRepository;

/**
 * The Class HDBDDService.
 */
@Service
@Transactional
public class HDBSequenceService implements ArtefactService<HDBSequence>, InitializingBean {
	
	/** The instance. */
	private static HDBSequenceService INSTANCE;
	
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
	 * @return the HDBSequence service
	 */
	public static HDBSequenceService get() {
        return INSTANCE;
    }
	
	/** The HDBSequence repository. */
    @Autowired
    private HDBSequenceRepository hdbSequenceRepository;

    /**
     * Gets the all.
     *
     * @return the all
     */
    @Override
    public List<HDBSequence> getAll() {
        return hdbSequenceRepository.findAll();
    }

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<HDBSequence> getPages(Pageable pageable) {
        return hdbSequenceRepository.findAll(pageable);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the HDBSequence
     */
    @Override
    public HDBSequence findById(Long id) {
        Optional<HDBSequence> hdbSequence = hdbSequenceRepository.findById(id);
        if (hdbSequence.isPresent()) {
            return hdbSequence.get();
        } else {
            throw new IllegalArgumentException("HDBSequence with id does not exist: " + id);
        }
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the HDBSequence
     */
    @Override
    public HDBSequence findByName(String name) {
    	HDBSequence filter = new HDBSequence();
        filter.setName(name);
        Example<HDBSequence> example = Example.of(filter);
        Optional<HDBSequence> hdbSequence = hdbSequenceRepository.findOne(example);
        if (hdbSequence.isPresent()) {
            return hdbSequence.get();
        } else {
            throw new IllegalArgumentException("HDBSequence with name does not exist: " + name);
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
    public List<HDBSequence> findByLocation(String location) {
    	HDBSequence filter = new HDBSequence();
        filter.setName(location);
        Example<HDBSequence> example = Example.of(filter);
        List<HDBSequence> list = hdbSequenceRepository.findAll(example);
        return list;
    }
    
    /**
     * Find by key.
     *
     * @param key the key
     * @return the HDBSequence
     */
    @Override
    @Transactional(readOnly = true)
    public HDBSequence findByKey(String key) {
    	HDBSequence filter = new HDBSequence();
        filter.setKey(key);
        Example<HDBSequence> example = Example.of(filter);
        Optional<HDBSequence> hdbSequence = hdbSequenceRepository.findOne(example);
        if (hdbSequence.isPresent()) {
            return hdbSequence.get();
        }
        return null;
    }

    /**
     * Save.
     *
     * @param hdbSequence the HDBSequence
     * @return the HDBSequence
     */
    @Override
    public HDBSequence save(HDBSequence hdbSequence) {
        return hdbSequenceRepository.saveAndFlush(hdbSequence);
    }

    /**
     * Delete.
     *
     * @param hdbSequence the HDBSequence
     */
    @Override
    public void delete(HDBSequence hdbSequence) {
    	hdbSequenceRepository.delete(hdbSequence);
    }

}
