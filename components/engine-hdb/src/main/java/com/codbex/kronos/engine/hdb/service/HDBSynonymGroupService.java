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

import com.codbex.kronos.engine.hdb.domain.HDBSynonym;
import com.codbex.kronos.engine.hdb.domain.HDBSynonymGroup;
import com.codbex.kronos.engine.hdb.repository.HDBSynonymGroupRepository;

/**
 * The Class HDBSynonymGroupService.
 */
@Service
@Transactional
public class HDBSynonymGroupService implements ArtefactService<HDBSynonymGroup>, InitializingBean {
	
	/** The instance. */
	private static HDBSynonymGroupService INSTANCE;
	
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
	 * @return the HDBSynonym service
	 */
	public static HDBSynonymGroupService get() {
        return INSTANCE;
    }
	
	/** The HDBSynonym repository. */
    @Autowired
    private HDBSynonymGroupRepository hdbSynonymGroupRepository;

    /**
     * Gets the all.
     *
     * @return the all
     */
    @Override
    public List<HDBSynonymGroup> getAll() {
        return hdbSynonymGroupRepository.findAll();
    }

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<HDBSynonymGroup> getPages(Pageable pageable) {
        return hdbSynonymGroupRepository.findAll(pageable);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the HDBSynonymGroup
     */
    @Override
    public HDBSynonymGroup findById(Long id) {
        Optional<HDBSynonymGroup> hdbSynonymGroup = hdbSynonymGroupRepository.findById(id);
        if (hdbSynonymGroup.isPresent()) {
            return hdbSynonymGroup.get();
        } else {
            throw new IllegalArgumentException("HDBSynonymGroup with id does not exist: " + id);
        }
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the HDBSynonymGroup
     */
    @Override
    public HDBSynonymGroup findByName(String name) {
    	HDBSynonymGroup filter = new HDBSynonymGroup();
        filter.setName(name);
        Example<HDBSynonymGroup> example = Example.of(filter);
        Optional<HDBSynonymGroup> hdbSynonymGroup = hdbSynonymGroupRepository.findOne(example);
        if (hdbSynonymGroup.isPresent()) {
            return hdbSynonymGroup.get();
        } else {
            throw new IllegalArgumentException("HDBSynonymGroup with name does not exist: " + name);
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
    public List<HDBSynonymGroup> findByLocation(String location) {
    	HDBSynonymGroup filter = new HDBSynonymGroup();
        filter.setName(location);
        Example<HDBSynonymGroup> example = Example.of(filter);
        List<HDBSynonymGroup> list = hdbSynonymGroupRepository.findAll(example);
        return list;
    }
    
    /**
     * Find by key.
     *
     * @param key the key
     * @return the HDBSynonymGroup
     */
    @Override
    @Transactional(readOnly = true)
    public HDBSynonymGroup findByKey(String key) {
    	HDBSynonymGroup filter = new HDBSynonymGroup();
        filter.setKey(key);
        Example<HDBSynonymGroup> example = Example.of(filter);
        Optional<HDBSynonymGroup> hdbSynonymGroup = hdbSynonymGroupRepository.findOne(example);
        if (hdbSynonymGroup.isPresent()) {
            return hdbSynonymGroup.get();
        }
        return null;
    }

    /**
     * Save.
     *
     * @param hdbSynonymGroup the HDBSynonymGroup
     * @return the HDBSynonymGroup
     */
    @Override
    public HDBSynonymGroup save(HDBSynonymGroup hdbSynonymGroup) {
        return hdbSynonymGroupRepository.saveAndFlush(hdbSynonymGroup);
    }

    /**
     * Delete.
     *
     * @param hdbSynonymGroup the HDBSynonymGroup
     */
    @Override
    public void delete(HDBSynonymGroup hdbSynonymGroup) {
    	hdbSynonymGroupRepository.delete(hdbSynonymGroup);
    }

}
