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

import com.codbex.kronos.engine.hdb.domain.HDBSchema;
import com.codbex.kronos.engine.hdb.repository.HDBSchemaRepository;

/**
 * The Class HDBSchemaService.
 */
@Service
@Transactional
public class HDBSchemaService implements ArtefactService<HDBSchema>, InitializingBean {
	
	/** The instance. */
	private static HDBSchemaService INSTANCE;
	
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
	 * @return the HDBSchema service
	 */
	public static HDBSchemaService get() {
        return INSTANCE;
    }
	
	/** The HDBSchema repository. */
    @Autowired
    private HDBSchemaRepository hdbSchemaRepository;

    /**
     * Gets the all.
     *
     * @return the all
     */
    @Override
    public List<HDBSchema> getAll() {
        return hdbSchemaRepository.findAll();
    }

    /**
     * Find all.
     *
     * @param pageable the pageable
     * @return the page
     */
    @Override
    public Page<HDBSchema> getPages(Pageable pageable) {
        return hdbSchemaRepository.findAll(pageable);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the HDBSchema
     */
    @Override
    public HDBSchema findById(Long id) {
        Optional<HDBSchema> hdbSchema = hdbSchemaRepository.findById(id);
        if (hdbSchema.isPresent()) {
            return hdbSchema.get();
        } else {
            throw new IllegalArgumentException("HDBSchema with id does not exist: " + id);
        }
    }

    /**
     * Find by name.
     *
     * @param name the name
     * @return the HDBSchema
     */
    @Override
    public HDBSchema findByName(String name) {
    	HDBSchema filter = new HDBSchema();
        filter.setName(name);
        Example<HDBSchema> example = Example.of(filter);
        Optional<HDBSchema> hdbSchema = hdbSchemaRepository.findOne(example);
        if (hdbSchema.isPresent()) {
            return hdbSchema.get();
        } else {
            throw new IllegalArgumentException("HDBSchema with name does not exist: " + name);
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
    public List<HDBSchema> findByLocation(String location) {
    	HDBSchema filter = new HDBSchema();
        filter.setName(location);
        Example<HDBSchema> example = Example.of(filter);
        List<HDBSchema> list = hdbSchemaRepository.findAll(example);
        return list;
    }
    
    /**
     * Find by key.
     *
     * @param key the key
     * @return the HDBSchema
     */
    @Override
    @Transactional(readOnly = true)
    public HDBSchema findByKey(String key) {
    	HDBSchema filter = new HDBSchema();
        filter.setKey(key);
        Example<HDBSchema> example = Example.of(filter);
        Optional<HDBSchema> hdbSchema = hdbSchemaRepository.findOne(example);
        if (hdbSchema.isPresent()) {
            return hdbSchema.get();
        }
        return null;
    }

    /**
     * Save.
     *
     * @param hdbSchema the HDBSchema
     * @return the HDBSchema
     */
    @Override
    public HDBSchema save(HDBSchema hdbSchema) {
        return hdbSchemaRepository.saveAndFlush(hdbSchema);
    }

    /**
     * Delete.
     *
     * @param hdbSchema the HDBSchema
     */
    @Override
    public void delete(HDBSchema hdbSchema) {
    	hdbSchemaRepository.delete(hdbSchema);
    }

}
