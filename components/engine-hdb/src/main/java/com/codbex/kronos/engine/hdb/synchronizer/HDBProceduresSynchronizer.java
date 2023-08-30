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

import org.eclipse.dirigible.components.api.platform.ProblemsFacade;
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
import com.codbex.kronos.engine.hdb.domain.HDBProcedure;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBProcedureCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBProcedureDropProcessor;
import com.codbex.kronos.engine.hdb.service.HDBProcedureService;
import com.codbex.kronos.exceptions.ArtifactParserException;

/**
 * The Class HDBProceduresSynchronizer.
 *
 * @param <A> the generic type
 */
@Component
@Order(220)
public class HDBProceduresSynchronizer<A extends Artefact> implements Synchronizer<HDBProcedure> {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(HDBProceduresSynchronizer.class);
	
	/** The Constant FILE_EXTENSION_HDBPROCEDURE. */
	private static final String FILE_EXTENSION_HDBPROCEDURE = ".hdbprocedure";
	
	/** The procedure service. */
	private HDBProcedureService procedureService;
	
	/** The datasources manager. */
	private DataSourcesManager datasourcesManager;
	
	/** The synchronization callback. */
	private SynchronizerCallback callback;
	
	/**
	 * Instantiates a new procedure synchronizer.
	 *
	 * @param procedureService the procedure service
	 * @param datasourcesManager the datasources manager
	 */
	@Autowired
	public HDBProceduresSynchronizer(HDBProcedureService procedureService, DataSourcesManager datasourcesManager) {
		this.procedureService = procedureService;
		this.datasourcesManager = datasourcesManager;
	}
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	@Override
	public ArtefactService<HDBProcedure> getService() {
		return procedureService;
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
		return HDBProcedure.ARTEFACT_TYPE.equals(type);
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
	public List<HDBProcedure> parse(String location, byte[] content) throws ParseException {
		HDBProcedure procedure;
		try {
			procedure = HDBDataStructureModelFactory.parseProcedure(location, content);
		} catch (DataStructuresException | ArtifactParserException | IOException e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("hdbtable: {}", location);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
		
//		Configuration.configureObject(procedure);
		procedure.setLocation(location);
		procedure.setType(HDBProcedure.ARTEFACT_TYPE);
		procedure.updateKey();
		
		try {
			HDBProcedure maybe = getService().findByKey(procedure.getKey());
			if (maybe != null) {
				procedure.setId(maybe.getId());
			}
			getService().save(procedure);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("procedure: {}", procedure);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
		return List.of(procedure);
	}
	
	/**
	 * Retrieve.
	 *
	 * @param location the location
	 * @return the list
	 */
	@Override
	public List<HDBProcedure> retrieve(String location) {
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
		getService().save((HDBProcedure) artefact);
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
		
			HDBProcedure procedure = null;
			if (wrapper.getArtefact() instanceof HDBProcedure) {
				procedure = (HDBProcedure) wrapper.getArtefact();
			} else {
				throw new UnsupportedOperationException(String.format("Trying to process %s as HDBProcedure", wrapper.getArtefact().getClass()));
			}
			
			switch (flow) {
			case CREATE:
				if (ArtefactLifecycle.NEW.equals(procedure.getLifecycle())) {
					if (!SqlFactory.getNative(connection).exists(connection, procedure.getName())) {
						executeProcedureCreate(connection, procedure);
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
					} else {
						if (logger.isWarnEnabled()) {logger.warn(String.format("HDBProcedure [%s] already exists during the update process", procedure.getName()));}
						executeProcedureUpdate(connection, procedure);
						callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
					}
				} else if (ArtefactLifecycle.FAILED.equals(procedure.getLifecycle())) {
					if (!SqlFactory.getNative(connection).exists(connection, procedure.getName())) {
						executeProcedureCreate(connection, procedure);
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
						ProblemsFacade.deleteArtefactSynchronizationProblem(procedure);
					}
				}
				break;
			case UPDATE:
				if (ArtefactLifecycle.MODIFIED.equals(procedure.getLifecycle())) {
					executeProcedureUpdate(connection, procedure);
					callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
				}
				break;
			case DELETE:
				if (ArtefactLifecycle.CREATED.equals(procedure.getLifecycle())) {
					if (SqlFactory.getNative(connection).exists(connection, procedure.getName())) {
						executeProcedureDrop(connection, procedure);
						callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, "");
					}
					callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, "");
				}
				break;
			case START:
			case STOP:
			}
			
			return true;
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, wrapper, ArtefactLifecycle.FAILED, e.getMessage());
			return false;
		}
	}

	/**
	 * Cleanup.
	 *
	 * @param procedure the Procedure
	 */
	@Override
	public void cleanup(HDBProcedure procedure) {
		try (Connection connection = datasourcesManager.getDefaultDataSource().getConnection()) {
			getService().delete(procedure);
			callback.registerState(this, procedure, ArtefactLifecycle.DELETED, "");
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, procedure, ArtefactLifecycle.FAILED, e.getMessage());
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
	 * Execute procedure update.
	 *
	 * @param connection
	 *            the connection
	 * @param procedureModel
	 *            the Procedure model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeProcedureUpdate(Connection connection, HDBProcedure procedureModel) throws SQLException {
		if (logger.isInfoEnabled()) {logger.info("Processing Update Procedure: " + procedureModel.getName());}
		if (SqlFactory.getNative(connection).exists(connection, procedureModel.getName())) {
			executeProcedureDrop(connection, procedureModel);
			executeProcedureCreate(connection, procedureModel);
		} else {
			executeProcedureCreate(connection, procedureModel);
		}
	}

	/**
	 * Execute Procedure create.
	 *
	 * @param connection
	 *            the connection
	 * @param procedureModel
	 *            the Procedure model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeProcedureCreate(Connection connection, HDBProcedure procedureModel) throws SQLException {
		new HDBProcedureCreateProcessor().execute(connection, procedureModel);
	}
	
	/**
	 * Execute Procedure drop.
	 *
	 * @param connection
	 *            the connection
	 * @param procedureModel
	 *            the Procedure model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeProcedureDrop(Connection connection, HDBProcedure procedureModel) throws SQLException {
		new HDBProcedureDropProcessor().execute(connection, procedureModel);
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return the file extension
	 */
	@Override
	public String getFileExtension() {
		return FILE_EXTENSION_HDBPROCEDURE;
	}

	/**
	 * Gets the artefact type.
	 *
	 * @return the artefact type
	 */
	@Override
	public String getArtefactType() {
		return HDBProcedure.ARTEFACT_TYPE;
	}

}
