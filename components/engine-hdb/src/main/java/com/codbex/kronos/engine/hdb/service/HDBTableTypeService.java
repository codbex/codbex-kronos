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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codbex.kronos.engine.hdb.domain.HDBTableType;
import com.codbex.kronos.engine.hdb.repository.HDBTableTypeRepository;

/**
 * Processing the Table Type Service incoming requests.
 */
@Service
@Transactional
public class HDBTableTypeService implements ArtefactService<HDBTableType> {
	
	/** The tabletype repository. */
	@Autowired 
	private HDBTableTypeRepository hdbtabletypeRepository;

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	@Transactional(readOnly = true)
	public List<HDBTableType> getAll() {
		return hdbtabletypeRepository.findAll();
	}
	
	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<HDBTableType> getPages(Pageable pageable) {
		return hdbtabletypeRepository.findAll(pageable);
	}
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the tabletype
	 */
	@Override
	@Transactional(readOnly = true)
	public HDBTableType findById(Long id) {
		Optional<HDBTableType> tabletype = hdbtabletypeRepository.findById(id);
		if (tabletype.isPresent()) {
			return tabletype.get();
		} else {
			throw new IllegalArgumentException("HDBTableType with id does not exist: " + id);
		}
	}
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the tabletype
	 */
	@Override
	@Transactional(readOnly = true)
	public HDBTableType findByName(String name) {
		HDBTableType filter = new HDBTableType();
		filter.setName(name);
		Example<HDBTableType> example = Example.of(filter);
		Optional<HDBTableType> tabletype = hdbtabletypeRepository.findOne(example);
		if (tabletype.isPresent()) {
			return tabletype.get();
		} else {
			throw new IllegalArgumentException("HDBTableType with name does not exist: " + name);
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
    public List<HDBTableType> findByLocation(String location) {
    	HDBTableType filter = new HDBTableType();
        filter.setName(location);
        Example<HDBTableType> example = Example.of(filter);
        List<HDBTableType> list = hdbtabletypeRepository.findAll(example);
        return list;
    }
	
	/**
     * Find by key.
     *
     * @param key the key
     * @return the tabletype
     */
    @Override
    @Transactional(readOnly = true)
    public HDBTableType findByKey(String key) {
    	HDBTableType filter = new HDBTableType();
        filter.setKey(key);
        Example<HDBTableType> example = Example.of(filter);
        Optional<HDBTableType> tabletype = hdbtabletypeRepository.findOne(example);
        if (tabletype.isPresent()) {
            return tabletype.get();
        }
        return null;
    }
	
	/**
	 * Save.
	 *
	 * @param tabletype the tabletype
	 * @return the tabletype
	 */
	@Override
	public HDBTableType save(HDBTableType tabletype) {
		return hdbtabletypeRepository.saveAndFlush(tabletype);
	}
	
	/**
	 * Delete.
	 *
	 * @param tabletype the tabletype
	 */
	@Override
	public void delete(HDBTableType tabletype) {
		hdbtabletypeRepository.delete(tabletype);
	}

}
