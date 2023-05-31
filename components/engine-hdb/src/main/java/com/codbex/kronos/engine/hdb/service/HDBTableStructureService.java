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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codbex.kronos.engine.hdb.domain.HDBTableStructure;
import com.codbex.kronos.engine.hdb.repository.HDBTableStructureRepository;

/**
 * Processing the Table Type Service incoming requests.
 */
@Service
@Transactional
public class HDBTableStructureService implements ArtefactService<HDBTableStructure> {
	
	/** The tablestructure repository. */
	@Autowired 
	private HDBTableStructureRepository hdbtablestructureRepository;

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	@Transactional(readOnly = true)
	public List<HDBTableStructure> getAll() {
		return hdbtablestructureRepository.findAll();
	}
	
	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<HDBTableStructure> getPages(Pageable pageable) {
		return hdbtablestructureRepository.findAll(pageable);
	}
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the tablestructure
	 */
	@Override
	@Transactional(readOnly = true)
	public HDBTableStructure findById(Long id) {
		Optional<HDBTableStructure> tablestructure = hdbtablestructureRepository.findById(id);
		if (tablestructure.isPresent()) {
			return tablestructure.get();
		} else {
			throw new IllegalArgumentException("HDBTableStructure with id does not exist: " + id);
		}
	}
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the tablestructure
	 */
	@Override
	@Transactional(readOnly = true)
	public HDBTableStructure findByName(String name) {
		HDBTableStructure filter = new HDBTableStructure();
		filter.setName(name);
		Example<HDBTableStructure> example = Example.of(filter);
		Optional<HDBTableStructure> tablestructure = hdbtablestructureRepository.findOne(example);
		if (tablestructure.isPresent()) {
			return tablestructure.get();
		} else {
			throw new IllegalArgumentException("HDBTableStructure with name does not exist: " + name);
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
    public List<HDBTableStructure> findByLocation(String location) {
    	HDBTableStructure filter = new HDBTableStructure();
        filter.setName(location);
        Example<HDBTableStructure> example = Example.of(filter);
        List<HDBTableStructure> list = hdbtablestructureRepository.findAll(example);
        return list;
    }
	
	/**
     * Find by key.
     *
     * @param key the key
     * @return the tablestructure
     */
    @Override
    @Transactional(readOnly = true)
    public HDBTableStructure findByKey(String key) {
    	HDBTableStructure filter = new HDBTableStructure();
        filter.setKey(key);
        Example<HDBTableStructure> example = Example.of(filter);
        Optional<HDBTableStructure> tablestructure = hdbtablestructureRepository.findOne(example);
        if (tablestructure.isPresent()) {
            return tablestructure.get();
        }
        return null;
    }
	
	/**
	 * Save.
	 *
	 * @param tablestructure the tablestructure
	 * @return the tablestructure
	 */
	@Override
	public HDBTableStructure save(HDBTableStructure tablestructure) {
		return hdbtablestructureRepository.saveAndFlush(tablestructure);
	}
	
	/**
	 * Delete.
	 *
	 * @param tablestructure the tablestructure
	 */
	@Override
	public void delete(HDBTableStructure tablestructure) {
		hdbtablestructureRepository.delete(tablestructure);
	}

}
