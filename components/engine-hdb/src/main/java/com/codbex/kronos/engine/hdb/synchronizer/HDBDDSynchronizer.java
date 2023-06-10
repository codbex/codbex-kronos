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
package com.codbex.kronos.engine.hdb.synchronizer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.api.IDataStructureModel;
import com.codbex.kronos.engine.hdb.domain.HDBDD;
import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraints;
import com.codbex.kronos.engine.hdb.domain.HDBView;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.parser.HDBParameters;
import com.codbex.kronos.engine.hdb.parser.HDBUtils;
import com.codbex.kronos.engine.hdb.processors.HDBTableAlterProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBTableCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBViewCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBViewDropProcessor;
import com.codbex.kronos.engine.hdb.service.HDBDDService;
import com.codbex.kronos.engine.hdb.service.HDBTableService;
import com.codbex.kronos.engine.hdb.service.HDBViewService;
import com.codbex.kronos.utils.CommonsConstants;

/**
 * The Class HDBDDSynchronizer.
 *
 * @param <A> the generic type
 */
@Component
@Order(230)
public class HDBDDSynchronizer<A extends Artefact> implements Synchronizer<HDBDD> {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(HDBDDSynchronizer.class);
	
	/** The Constant FILE_EXTENSION_SCHEMA. */
	private static final String FILE_EXTENSION_HDBDD = ".hdbdd";
	
	/** The schema service. */
	private HDBDDService hdbddService;
	
	/** The table service. */
	private HDBTableService tableService;
	
	/** The view service. */
	private HDBViewService viewService;
	
	/** The datasources manager. */
	private DataSourcesManager datasourcesManager;
	
	/** The synchronization callback. */
	private SynchronizerCallback callback;
	
	/**
	 * Instantiates a new schema synchronizer.
	 *
	 * @param schemaService the schema service
	 * @param datasourcesManager the datasources manager
	 * @param tableService the table service
	 * @param viewService the view service
	 */
	@Autowired
	public HDBDDSynchronizer(HDBDDService hdbddService, DataSourcesManager datasourcesManager, HDBTableService tableService, HDBViewService viewService) {
		this.hdbddService = hdbddService;
		this.datasourcesManager = datasourcesManager;
		this.tableService = tableService;
		this.viewService = viewService;
	}
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	@Override
	public ArtefactService<HDBDD> getService() {
		return hdbddService;
	}
	
	/**
	 * Gets the table service.
	 *
	 * @return the table service
	 */
	public HDBTableService getTableService() {
		return tableService;
	}
	
	/**
	 * Gets the view service.
	 *
	 * @return the view service
	 */
	public HDBViewService getViewService() {
		return viewService;
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
		return HDBDD.ARTEFACT_TYPE.equals(type);
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
	public List<HDBDD> parse(String location, byte[] content) throws ParseException {
		HDBDD hdbdd;
		try {
			hdbdd = HDBDataStructureModelFactory.parseHdbdd(location, content);
		} catch (DataStructuresException | IOException e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("hdbdd: {}", location);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
//		Configuration.configureObject(schema);
		hdbdd.setLocation(location);
		if (hdbdd.getName() == null) {
			hdbdd.setName("PUBLIC");
		}
		hdbdd.setType(HDBDD.ARTEFACT_TYPE);
		hdbdd.updateKey();
		
		hdbdd.getTables().forEach(t -> {
				t.setHdbdd(hdbdd);
				t.setConstraints(new HDBTableConstraints(t));
				HDBTablesSynchronizer.assignParent(t);
			});
		hdbdd.getViews().forEach(v -> v.setHdbdd(hdbdd));
		
		try {
			HDBDD maybe = getService().findByKey(hdbdd.getKey());
			if (maybe != null) {
				hdbdd.setId(maybe.getId());
				hdbdd.getTables().forEach(t -> {
					HDBTable m = getTableService().findByKey(hdbdd.constructKey(HDBTable.ARTEFACT_TYPE, location, t.getName()));
					if (m != null) {
						t.setId(m.getId());
					}
					HDBTablesSynchronizer.reassignIds(t, m);
				});
				hdbdd.getViews().forEach(v -> {
					HDBView m = getViewService().findByKey(hdbdd.constructKey(HDBView.ARTEFACT_TYPE, location, v.getName()));
					if (m != null) {
						v.setId(m.getId());
					}
				});
			}
			HDBDD result = getService().save(hdbdd);
			return List.of(result);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("hdbdd: {}", hdbdd);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
	}
	
	/**
	 * Retrieve.
	 *
	 * @param location the location
	 * @return the list
	 */
	@Override
	public List<HDBDD> retrieve(String location) {
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
		getService().save((HDBDD) artefact);
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
		
			HDBDD hdbdd = null;
			if (wrapper.getArtefact() instanceof HDBDD) {
				hdbdd = (HDBDD) wrapper.getArtefact();
			} else {
				throw new UnsupportedOperationException(String.format("Trying to process %s as HDBDD", wrapper.getArtefact().getClass()));
			}
			
			switch (flow) {
			case CREATE:
				if (ArtefactLifecycle.NEW.equals(hdbdd.getLifecycle())) {
					try {
						executeHDBDDCreate(connection, hdbdd);
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
					} catch (Exception e) {
						if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, e.getMessage());
					}
				}
				break;
			case UPDATE:
				if (ArtefactLifecycle.MODIFIED.equals(hdbdd.getLifecycle())) {
					executeHDBDDUpdate(connection, hdbdd);
					callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
				}
				break;
			case DELETE:
				if (ArtefactLifecycle.CREATED.equals(hdbdd.getLifecycle())) {
					executeHDBDDDrop(connection, hdbdd);
					callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, "");
					break;
				}
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
	 * @param hdbdd the HDBDD
	 */
	@Override
	public void cleanup(HDBDD hdbdd) {
		try {
			getService().delete(hdbdd);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, hdbdd, ArtefactLifecycle.DELETED, e.getMessage());
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
	 * Execute HDBDD update.
	 *
	 * @param connection
	 *            the connection
	 * @param hdbddModel
	 *            the HDBDD model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeHDBDDUpdate(Connection connection, HDBDD hdbddModel) throws SQLException {
		if (logger.isInfoEnabled()) {logger.info("Processing Update HDBDD: " + hdbddModel.getName());}
		executeHDBDDCreate(connection, hdbddModel);
	}

	/**
	 * Execute HDBDD create.
	 *
	 * @param connection
	 *            the connection
	 * @param hdbddModel
	 *            the HDBDD model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeHDBDDCreate(Connection connection, HDBDD hdbddModel) throws SQLException {
		
		for (HDBTable entityModel : hdbddModel.getTables()) {
	        String tableName = HDBUtils.escapeArtifactName(entityModel.getName(), entityModel.getSchema());
	        if (!SqlFactory.getNative(connection).exists(connection, tableName)) {
	            new HDBTableCreateProcessor().execute(connection, entityModel);
	        } else {
	            new HDBTableAlterProcessor().execute(connection, entityModel);
	        }
	    }
		
		for (HDBView entityModel : hdbddModel.getViews()) {
	        String viewName = HDBUtils.escapeArtifactName(entityModel.getName(), entityModel.getSchema());
	        if (!SqlFactory.getNative(connection).exists(connection, viewName)) {
	            new HDBViewCreateProcessor().execute(connection, entityModel);
	        } else {
	        	new HDBViewDropProcessor().execute(connection, entityModel);
	        	new HDBViewCreateProcessor().execute(connection, entityModel);
	        }
	    }
	}
	
	/**
	 * Execute schema drop.
	 *
	 * @param connection
	 *            the connection
	 * @param hdbddModel
	 *            the HDBDD model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeHDBDDDrop(Connection connection, HDBDD hdbddModel) throws SQLException {
//		HDBDDDropProcessor.execute(connection, hdbddModel);
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return the file extension
	 */
	@Override
	public String getFileExtension() {
		return FILE_EXTENSION_HDBDD;
	}

	/**
	 * Gets the artefact type.
	 *
	 * @return the artefact type
	 */
	@Override
	public String getArtefactType() {
		return HDBDD.ARTEFACT_TYPE;
	}

}
