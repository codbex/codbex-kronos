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

import com.codbex.kronos.engine.hdb.domain.HDBView;
import com.codbex.kronos.engine.hdb.repository.HDBViewRepository;

/**
 * Processing the Views Service incoming requests.
 */
@Service
@Transactional
public class HDBViewService implements ArtefactService<HDBView> {
	
	/** The view repository. */
	@Autowired 
	private HDBViewRepository hdbviewRepository;

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	@Transactional(readOnly = true)
	public List<HDBView> getAll() {
		return hdbviewRepository.findAll();
	}
	
	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<HDBView> getPages(Pageable pageable) {
		return hdbviewRepository.findAll(pageable);
	}
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the view
	 */
	@Override
	@Transactional(readOnly = true)
	public HDBView findById(Long id) {
		Optional<HDBView> view = hdbviewRepository.findById(id);
		if (view.isPresent()) {
			return view.get();
		} else {
			throw new IllegalArgumentException("HDBView with id does not exist: " + id);
		}
	}
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the view
	 */
	@Override
	@Transactional(readOnly = true)
	public HDBView findByName(String name) {
		HDBView filter = new HDBView();
		filter.setName(name);
		Example<HDBView> example = Example.of(filter);
		Optional<HDBView> view = hdbviewRepository.findOne(example);
		if (view.isPresent()) {
			return view.get();
		} else {
			throw new IllegalArgumentException("HDBView with name does not exist: " + name);
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
    public List<HDBView> findByLocation(String location) {
    	HDBView filter = new HDBView();
        filter.setName(location);
        Example<HDBView> example = Example.of(filter);
        List<HDBView> list = hdbviewRepository.findAll(example);
        return list;
    }
	
	/**
     * Find by key.
     *
     * @param key the key
     * @return the view
     */
    @Override
    @Transactional(readOnly = true)
    public HDBView findByKey(String key) {
    	HDBView filter = new HDBView();
        filter.setKey(key);
        Example<HDBView> example = Example.of(filter);
        Optional<HDBView> view = hdbviewRepository.findOne(example);
        if (view.isPresent()) {
            return view.get();
        }
        return null;
    }
	
	/**
	 * Save.
	 *
	 * @param view the view
	 * @return the view
	 */
	@Override
	public HDBView save(HDBView view) {
		return hdbviewRepository.saveAndFlush(view);
	}
	
	/**
	 * Delete.
	 *
	 * @param view the view
	 */
	@Override
	public void delete(HDBView view) {
		hdbviewRepository.delete(view);
	}

}
