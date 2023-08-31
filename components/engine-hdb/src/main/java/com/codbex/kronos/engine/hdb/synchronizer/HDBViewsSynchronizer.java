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
import com.codbex.kronos.engine.hdb.domain.HDBView;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBViewCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBViewDropProcessor;
import com.codbex.kronos.engine.hdb.service.HDBViewService;
import com.codbex.kronos.exceptions.ArtifactParserException;

/**
 * The Class HDBViewsSynchronizer.
 *
 * @param <A> the generic type
 */
@Component
@Order(220)
public class HDBViewsSynchronizer<A extends Artefact> implements Synchronizer<HDBView> {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(HDBViewsSynchronizer.class);
	
	/** The Constant FILE_EXTENSION_HDBVIEW. */
	private static final String FILE_EXTENSION_HDBVIEW = ".hdbview";
	
	/** The view service. */
	private HDBViewService viewService;
	
	/** The datasources manager. */
	private DataSourcesManager datasourcesManager;
	
	/** The synchronization callback. */
	private SynchronizerCallback callback;
	
	/**
	 * Instantiates a new view synchronizer.
	 *
	 * @param viewService the view service
	 * @param datasourcesManager the datasources manager
	 */
	@Autowired
	public HDBViewsSynchronizer(HDBViewService viewService, DataSourcesManager datasourcesManager) {
		this.viewService = viewService;
		this.datasourcesManager = datasourcesManager;
	}
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	@Override
	public ArtefactService<HDBView> getService() {
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
		return HDBView.ARTEFACT_TYPE.equals(type);
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
	public List<HDBView> parse(String location, byte[] content) throws ParseException {
		HDBView view;
		try {
			view = HDBDataStructureModelFactory.parseView(location, content);
		} catch (DataStructuresException | IOException | ArtifactParserException e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("hdbtable: {}", location);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
		
//		Configuration.configureObject(view);
		view.setLocation(location);
		view.setType(HDBView.ARTEFACT_TYPE);
		view.updateKey();
		
		try {
			HDBView maybe = getService().findByKey(view.getKey());
			if (maybe != null) {
				view.setId(maybe.getId());
			}
			getService().save(view);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("view: {}", view);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
		return List.of(view);
	}
	
	/**
	 * Retrieve.
	 *
	 * @param location the location
	 * @return the list
	 */
	@Override
	public List<HDBView> retrieve(String location) {
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
		getService().save((HDBView) artefact);
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
		
			HDBView view = null;
			if (wrapper.getArtefact() instanceof HDBView) {
				view = (HDBView) wrapper.getArtefact();
			} else {
				throw new UnsupportedOperationException(String.format("Trying to process %s as HDBView", wrapper.getArtefact().getClass()));
			}
			
			switch (flow) {
			case CREATE:
				if (ArtefactLifecycle.NEW.equals(view.getLifecycle())) {
					if (!SqlFactory.getNative(connection).exists(connection, view.getName())) {
						executeViewCreate(connection, view);
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
					} else {
						if (logger.isWarnEnabled()) {logger.warn(String.format("HDBView [%s] already exists during the update process", view.getName()));}
						executeViewUpdate(connection, view);
						callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
					}
				} else if (ArtefactLifecycle.FAILED.equals(view.getLifecycle())) {
					if (!SqlFactory.getNative(connection).exists(connection, view.getName())) {
						executeViewCreate(connection, view);
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
						ProblemsFacade.deleteArtefactSynchronizationProblem(view);
					}
				}
				break;
			case UPDATE:
				if (ArtefactLifecycle.MODIFIED.equals(view.getLifecycle())) {
					executeViewUpdate(connection, view);
					callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
				}
				break;
			case DELETE:
				if (ArtefactLifecycle.CREATED.equals(view.getLifecycle())) {
					if (SqlFactory.getNative(connection).exists(connection, view.getName())) {
						executeViewDrop(connection, view);
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
			String errorMessage = String.format("Error occurred while processing [%s]: %s", wrapper.getArtefact().getLocation(), e.getMessage());
			if (logger.isErrorEnabled()) {logger.error(errorMessage, e);}
			callback.addError(errorMessage);
			callback.registerState(this, wrapper, ArtefactLifecycle.FAILED, errorMessage);
			ProblemsFacade.upsertArtefactSynchronizationProblem(wrapper.getArtefact(), errorMessage);
			return false;
		}
	}

	/**
	 * Cleanup.
	 *
	 * @param view the view
	 */
	@Override
	public void cleanup(HDBView view) {
		try (Connection connection = datasourcesManager.getDefaultDataSource().getConnection()) {
			getService().delete(view);
			callback.registerState(this, view, ArtefactLifecycle.DELETED, "");
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, view, ArtefactLifecycle.FAILED, e.getMessage());
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
	 * Execute view update.
	 *
	 * @param connection
	 *            the connection
	 * @param viewModel
	 *            the view model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeViewUpdate(Connection connection, HDBView viewModel) throws SQLException {
		if (logger.isInfoEnabled()) {logger.info("Processing Update View: " + viewModel.getName());}
		if (SqlFactory.getNative(connection).exists(connection, viewModel.getName())) {
			executeViewDrop(connection, viewModel);
			executeViewCreate(connection, viewModel);
		} else {
			executeViewCreate(connection, viewModel);
		}
	}

	/**
	 * Execute view create.
	 *
	 * @param connection
	 *            the connection
	 * @param viewModel
	 *            the view model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeViewCreate(Connection connection, HDBView viewModel) throws SQLException {
		new HDBViewCreateProcessor().execute(connection, viewModel);
	}
	
	/**
	 * Execute view drop.
	 *
	 * @param connection
	 *            the connection
	 * @param viewModel
	 *            the view model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeViewDrop(Connection connection, HDBView viewModel) throws SQLException {
		new HDBViewDropProcessor().execute(connection, viewModel);
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return the file extension
	 */
	@Override
	public String getFileExtension() {
		return FILE_EXTENSION_HDBVIEW;
	}

	/**
	 * Gets the artefact type.
	 *
	 * @return the artefact type
	 */
	@Override
	public String getArtefactType() {
		return HDBView.ARTEFACT_TYPE;
	}

}
