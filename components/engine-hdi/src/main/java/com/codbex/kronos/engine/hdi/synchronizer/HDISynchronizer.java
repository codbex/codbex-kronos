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
package com.codbex.kronos.engine.hdi.synchronizer;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.dirigible.components.base.artefact.Artefact;
import org.eclipse.dirigible.components.base.artefact.ArtefactLifecycle;
import org.eclipse.dirigible.components.base.artefact.ArtefactPhase;
import org.eclipse.dirigible.components.base.artefact.ArtefactService;
import org.eclipse.dirigible.components.base.artefact.topology.TopologyWrapper;
import org.eclipse.dirigible.components.base.synchronizer.Synchronizer;
import org.eclipse.dirigible.components.base.synchronizer.SynchronizerCallback;
import org.eclipse.dirigible.components.data.sources.manager.DataSourcesManager;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.repository.api.IRepositoryStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.parser.HDBParameters;
import com.codbex.kronos.engine.hdi.domain.HDI;
import com.codbex.kronos.engine.hdi.parser.HDIParser;
import com.codbex.kronos.engine.hdi.processors.HDIContainerCreateProcessor;
import com.codbex.kronos.engine.hdi.processors.HDIContainerDropProcessor;
import com.codbex.kronos.engine.hdi.service.HDIService;

/**
 * The Class HDISynchronizer.
 *
 * @param <A> the generic type
 */
@Component
@Order(220)
public class HDISynchronizer<A extends Artefact> implements Synchronizer<HDI> {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(HDISynchronizer.class);
	
	/** The Constant FILE_EXTENSION_HDI. */
	private static final String FILE_EXTENSION_HDI = ".hdi";
	
	/** Type hdi. */
	public static final String TYPE_HDI = "HDI";
	
	/** The hdi service. */
	private HDIService hdiService;
	
	/** The datasources manager. */
	private DataSourcesManager datasourcesManager;
	
	/** The synchronization callback. */
	private SynchronizerCallback callback;
	
	/** The hdi container create processor. */
	private HDIContainerCreateProcessor hdiContainerCreateProcessor = new HDIContainerCreateProcessor();
	
	/**
	 * Instantiates a new hdi synchronizer.
	 *
	 * @param hdiService the hdi service
	 * @param datasourcesManager the datasources manager
	 */
	@Autowired
	public HDISynchronizer(HDIService hdiService, DataSourcesManager datasourcesManager) {
		this.hdiService = hdiService;
		this.datasourcesManager = datasourcesManager;
	}
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	@Override
	public ArtefactService<HDI> getService() {
		return hdiService;
	}

	/**
	 * Checks if is accepted.
	 *
	 * @param file the file
	 * @param attrs the attrs
	 * @return true, if is accepted
	 */
	@Override
	public boolean isAccepted(Path file, BasicFileAttributes attrs) {
		return file.toString().endsWith(getFileExtension());
	}

	/**
	 * Checks if is accepted.
	 *
	 * @param type the type
	 * @return true, if is accepted
	 */
	@Override
	public boolean isAccepted(String type) {
		return HDI.ARTEFACT_TYPE.equals(type);
	}

	/**
	 * Load.
	 *
	 * @param location the location
	 * @param content the content
	 * @return the list
	 * @throws ParseException 
	 */
	@Override
	public List<HDI> parse(String location, byte[] content) throws ParseException {
		HDBParameters parametersModel =
	              new HDBParameters(TYPE_HDI, location, content, IRepositoryStructure.PATH_REGISTRY_PUBLIC + IRepositoryStructure.SEPARATOR);
		HDI hdi = new HDIParser().parse(parametersModel);
		
//		Configuration.configureObject(hdi);
		hdi.setLocation(location);
		hdi.setType(HDI.ARTEFACT_TYPE);
		hdi.updateKey();
		
		try {
			HDI maybe = getService().findByKey(hdi.getKey());
			if (maybe != null) {
				hdi.setId(maybe.getId());
			}
			hdi = getService().save(hdi);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("hdi: {}", hdi);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
		return List.of(hdi);
	}
	
	/**
	 * Retrieve.
	 *
	 * @param location the location
	 * @return the list
	 */
	@Override
	public List<HDI> retrieve(String location) {
		return getService().getAll();
	}
	
	/**
	 * Sets the status.
	 *
	 * @param artefact the artefact
	 * @param lifecycle the lifecycle
	 * @param error the error
	 */
	@Override
	public void setStatus(Artefact artefact, ArtefactLifecycle lifecycle, String error) {
		artefact.setLifecycle(lifecycle);
		artefact.setError(error);
		getService().save((HDI) artefact);
	}

	/**
	 * Complete.
	 *
	 * @param wrapper the wrapper
	 * @param flow the flow
	 * @return true, if successful
	 */
	@Override
	public boolean complete(TopologyWrapper<Artefact> wrapper, ArtefactPhase flow) {
		
		try (Connection connection = datasourcesManager.getDefaultDataSource().getConnection()) {
		
			HDI hdi = null;
			if (wrapper.getArtefact() instanceof HDI) {
				hdi = (HDI) wrapper.getArtefact();
			} else {
				throw new UnsupportedOperationException(String.format("Trying to process %s as HDI", wrapper.getArtefact().getClass()));
			}
			
			switch (flow) {
			case CREATE:
				if (ArtefactLifecycle.NEW.equals(hdi.getLifecycle())) {
					try {
						hdiContainerCreateProcessor.execute(connection, hdi);
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
					} catch (Exception e) {
						if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, e.getMessage());
					}
				}
				break;
			case UPDATE:
				if (ArtefactLifecycle.MODIFIED.equals(hdi.getLifecycle())) {
					try {
						hdiContainerCreateProcessor.execute(connection, hdi);
						callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
					} catch (DataStructuresException e) {
						if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
						callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, e.getMessage());
					}
				}
				break;
			case DELETE:
				if (ArtefactLifecycle.CREATED.equals(hdi.getLifecycle())) {
					if (SqlFactory.getNative(connection).exists(connection, hdi.getName())) {
						HDIContainerDropProcessor.execute(connection, Arrays.asList(new HDI[] {hdi}));
						callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, "");
					}
					callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, "");
				}
				break;
			case START:
			case STOP:
			}
			
			return true;
		} catch (SQLException e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, wrapper, ArtefactLifecycle.FAILED, e.getMessage());
			return false;
		}
	}

	/**
	 * Cleanup.
	 *
	 * @param hdi the hdi
	 */
	@Override
	public void cleanup(HDI hdi) {
		try (Connection connection = datasourcesManager.getDefaultDataSource().getConnection()) {
			HDIContainerDropProcessor.execute(connection, Arrays.asList(new HDI[] {hdi}));
			getService().delete(hdi);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, hdi, ArtefactLifecycle.DELETED, e.getMessage());
		}
	}
	
	/**
	 * Sets the callback.
	 *
	 * @param callback the new callback
	 */
	@Override
	public void setCallback(SynchronizerCallback callback) {
		this.callback = callback;
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return the file extension
	 */
	@Override
	public String getFileExtension() {
		return FILE_EXTENSION_HDI;
	}

	/**
	 * Gets the artefact type.
	 *
	 * @return the artefact type
	 */
	@Override
	public String getArtefactType() {
		return HDI.ARTEFACT_TYPE;
	}

}
