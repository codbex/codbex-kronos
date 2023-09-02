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
package com.codbex.kronos.engine.xsjs.service;

import java.util.List;
import java.util.Optional;

import org.eclipse.dirigible.components.base.artefact.ArtefactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codbex.kronos.engine.xsjs.domain.Xsjslib;
import com.codbex.kronos.engine.xsjs.repository.XsjslibRepository;

/**
 * The Class XsjslibService.
 */
@Service
@Transactional
public class XsjslibService implements ArtefactService<Xsjslib> {
	
	/** The xsjslib repository. */
	@Autowired 
	private XsjslibRepository xsjslibRepository;

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Xsjslib> getAll() {
		return xsjslibRepository.findAll();
	}
	
	/**
	 * Find all.
	 *
	 * @param pageable the pageable
	 * @return the page
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<Xsjslib> getPages(Pageable pageable) {
		return xsjslibRepository.findAll(pageable);
	}
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the xsjslib
	 */
	@Override
	@Transactional(readOnly = true)
	public Xsjslib findById(Long id) {
		Optional<Xsjslib> xsjslib = xsjslibRepository.findById(id);
		if (xsjslib.isPresent()) {
			return xsjslib.get();
		} else {
			throw new IllegalArgumentException("Xsjslib with id does not exist: " + id);
		}
	}
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the xsjslib
	 */
	@Override
	@Transactional(readOnly = true)
	public Xsjslib findByName(String name) {
		Xsjslib filter = new Xsjslib();
		filter.setName(name);
		Example<Xsjslib> example = Example.of(filter);
		Optional<Xsjslib> xsjslib = xsjslibRepository.findOne(example);
		if (xsjslib.isPresent()) {
			return xsjslib.get();
		} else {
			throw new IllegalArgumentException("Xsjslib with name does not exist: " + name);
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
    public List<Xsjslib> findByLocation(String location) {
    	Xsjslib filter = new Xsjslib();
        filter.setName(location);
        Example<Xsjslib> example = Example.of(filter);
        List<Xsjslib> list = xsjslibRepository.findAll(example);
        return list;
    }
	
	/**
     * Find by key.
     *
     * @param key the key
     * @return the xsjslib point
     */
    @Override
    @Transactional(readOnly = true)
    public Xsjslib findByKey(String key) {
    	Xsjslib filter = new Xsjslib();
        filter.setKey(key);
        Example<Xsjslib> example = Example.of(filter);
        Optional<Xsjslib> xsjslib = xsjslibRepository.findOne(example);
        if (xsjslib.isPresent()) {
            return xsjslib.get();
        }
        return null;
    }
    
	/**
	 * Save.
	 *
	 * @param xsjslib the xsjslib
	 * @return the xsjslib
	 */
	@Override
	public Xsjslib save(Xsjslib xsjslib) {
		return xsjslibRepository.saveAndFlush(xsjslib);
	}
	
	/**
	 * Delete.
	 *
	 * @param xsjslib the xsjslib
	 */
	@Override
	public void delete(Xsjslib xsjslib) {
		xsjslibRepository.delete(xsjslib);
	}

}
