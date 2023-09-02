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

import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.repository.HDBTableRepository;

/**
 * Processing the HDB Tables Service incoming requests.
 */
@Service
@Transactional
public class HDBTableService implements ArtefactService<HDBTable> {
	
	/** The table repository. */
	@Autowired 
	private HDBTableRepository hdbtableRepository;

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	@Transactional(readOnly = true)
	public List<HDBTable> getAll() {
		return hdbtableRepository.findAll();
	}
	
	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<HDBTable> getPages(Pageable pageable) {
		return hdbtableRepository.findAll(pageable);
	}
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the table
	 */
	@Override
	@Transactional(readOnly = true)
	public HDBTable findById(Long id) {
		Optional<HDBTable> table = hdbtableRepository.findById(id);
		if (table.isPresent()) {
			return table.get();
		} else {
			throw new IllegalArgumentException("HDBTable with id does not exist: " + id);
		}
	}
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the table
	 */
	@Override
	@Transactional(readOnly = true)
	public HDBTable findByName(String name) {
		HDBTable filter = new HDBTable();
		filter.setName(name);
		Example<HDBTable> example = Example.of(filter);
		Optional<HDBTable> table = hdbtableRepository.findOne(example);
		if (table.isPresent()) {
			return table.get();
		} else {
			throw new IllegalArgumentException("HDBTable with name does not exist: " + name);
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
    public List<HDBTable> findByLocation(String location) {
    	HDBTable filter = new HDBTable();
        filter.setName(location);
        Example<HDBTable> example = Example.of(filter);
        List<HDBTable> list = hdbtableRepository.findAll(example);
        return list;
    }
	
	/**
     * Find by key.
     *
     * @param key the key
     * @return the table
     */
    @Override
    @Transactional(readOnly = true)
    public HDBTable findByKey(String key) {
    	HDBTable filter = new HDBTable();
        filter.setKey(key);
        Example<HDBTable> example = Example.of(filter);
        Optional<HDBTable> table = hdbtableRepository.findOne(example);
        if (table.isPresent()) {
            return table.get();
        }
        return null;
    }
	
	/**
	 * Save.
	 *
	 * @param table the table
	 * @return the table
	 */
	@Override
	public HDBTable save(HDBTable table) {
		return hdbtableRepository.saveAndFlush(table);
	}
	
	/**
	 * Delete.
	 *
	 * @param table the table
	 */
	@Override
	public void delete(HDBTable table) {
		hdbtableRepository.delete(table);
	}

}
