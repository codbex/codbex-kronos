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
import com.codbex.kronos.engine.hdb.domain.HDBTableFunction;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBTableFunctionCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBTableFunctionDropProcessor;
import com.codbex.kronos.engine.hdb.service.HDBTableFunctionService;
import com.codbex.kronos.exceptions.ArtifactParserException;

/**
 * The Class HDBTableFunctionsSynchronizer.
 *
 * @param <A> the generic type
 */
@Component
@Order(220)
public class HDBTableFunctionsSynchronizer<A extends Artefact> implements Synchronizer<HDBTableFunction> {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(HDBTableFunctionsSynchronizer.class);
	
	/** The Constant FILE_EXTENSION_HDBTABLEFUNCTION. */
	private static final String FILE_EXTENSION_HDBTABLEFUNCTION = ".hdbtablefunction";
	
	/** The TableFunction service. */
	private HDBTableFunctionService tablefunctionService;
	
	/** The datasources manager. */
	private DataSourcesManager datasourcesManager;
	
	/** The synchronization callback. */
	private SynchronizerCallback callback;
	
	/**
	 * Instantiates a new tablefunction synchronizer.
	 *
	 * @param tablefunctionService the tablefunction service
	 * @param datasourcesManager the datasources manager
	 */
	@Autowired
	public HDBTableFunctionsSynchronizer(HDBTableFunctionService tablefunctionService, DataSourcesManager datasourcesManager) {
		this.tablefunctionService = tablefunctionService;
		this.datasourcesManager = datasourcesManager;
	}
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	@Override
	public ArtefactService<HDBTableFunction> getService() {
		return tablefunctionService;
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
		return HDBTableFunction.ARTEFACT_TYPE.equals(type);
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
	public List<HDBTableFunction> parse(String location, byte[] content) throws ParseException {
		HDBTableFunction tablefunction;
		try {
			tablefunction = HDBDataStructureModelFactory.parseTableFunction(location, content);
		} catch (DataStructuresException | ArtifactParserException | IOException e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("hdbtable: {}", location);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
		
//		Configuration.configureObject(tablefunction);
		tablefunction.setLocation(location);
		tablefunction.setType(HDBTableFunction.ARTEFACT_TYPE);
		tablefunction.updateKey();
		
		try {
			HDBTableFunction maybe = getService().findByKey(tablefunction.getKey());
			if (maybe != null) {
				tablefunction.setId(maybe.getId());
			}
			tablefunction = getService().save(tablefunction);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("tablefunction: {}", tablefunction);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
		return List.of(tablefunction);
	}
	
	/**
	 * Retrieve.
	 *
	 * @param location the location
	 * @return the list
	 */
	@Override
	public List<HDBTableFunction> retrieve(String location) {
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
		getService().save((HDBTableFunction) artefact);
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
		
			HDBTableFunction tablefunction = null;
			if (wrapper.getArtefact() instanceof HDBTableFunction) {
				tablefunction = (HDBTableFunction) wrapper.getArtefact();
			} else {
				throw new UnsupportedOperationException(String.format("Trying to process %s as HDBTableFunction", wrapper.getArtefact().getClass()));
			}
			
			switch (flow) {
			case CREATE:
				if (ArtefactLifecycle.NEW.equals(tablefunction.getLifecycle())) {
					if (!SqlFactory.getNative(connection).exists(connection, tablefunction.getName())) {
						executeTableFunctionCreate(connection, tablefunction);
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
					} else {
						if (logger.isWarnEnabled()) {logger.warn(String.format("HDBTableFunction [%s] already exists during the update process", tablefunction.getName()));}
						executeTableFunctionUpdate(connection, tablefunction);
						callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
					}
				}
				break;
			case UPDATE:
				if (ArtefactLifecycle.MODIFIED.equals(tablefunction.getLifecycle())) {
					executeTableFunctionUpdate(connection, tablefunction);
					callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
				}
				break;
			case DELETE:
				if (ArtefactLifecycle.CREATED.equals(tablefunction.getLifecycle())) {
					if (SqlFactory.getNative(connection).exists(connection, tablefunction.getName())) {
						executeTableFunctionDrop(connection, tablefunction);
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
	 * @param tablefunction the TableFunction
	 */
	@Override
	public void cleanup(HDBTableFunction tablefunction) {
		try (Connection connection = datasourcesManager.getDefaultDataSource().getConnection()) {
			getService().delete(tablefunction);
			callback.registerState(this, tablefunction, ArtefactLifecycle.DELETED, "");
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, tablefunction, ArtefactLifecycle.FAILED, e.getMessage());
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
	 * Execute tablefunction update.
	 *
	 * @param connection
	 *            the connection
	 * @param tablefunctionModel
	 *            the TableFunction model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeTableFunctionUpdate(Connection connection, HDBTableFunction tablefunctionModel) throws SQLException {
		if (logger.isInfoEnabled()) {logger.info("Processing Update TableFunction: " + tablefunctionModel.getName());}
		if (SqlFactory.getNative(connection).exists(connection, tablefunctionModel.getName())) {
			executeTableFunctionDrop(connection, tablefunctionModel);
			executeTableFunctionCreate(connection, tablefunctionModel);
		} else {
			executeTableFunctionCreate(connection, tablefunctionModel);
		}
	}

	/**
	 * Execute TableFunction create.
	 *
	 * @param connection
	 *            the connection
	 * @param tablefunctionModel
	 *            the TableFunction model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeTableFunctionCreate(Connection connection, HDBTableFunction tablefunctionModel) throws SQLException {
		new HDBTableFunctionCreateProcessor().execute(connection, tablefunctionModel);
	}
	
	/**
	 * Execute TableFunction drop.
	 *
	 * @param connection
	 *            the connection
	 * @param tablefunctionModel
	 *            the TableFunction model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeTableFunctionDrop(Connection connection, HDBTableFunction tablefunctionModel) throws SQLException {
		new HDBTableFunctionDropProcessor().execute(connection, tablefunctionModel);
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return the file extension
	 */
	@Override
	public String getFileExtension() {
		return FILE_EXTENSION_HDBTABLEFUNCTION;
	}

	/**
	 * Gets the artefact type.
	 *
	 * @return the artefact type
	 */
	@Override
	public String getArtefactType() {
		return HDBTableFunction.ARTEFACT_TYPE;
	}

}
