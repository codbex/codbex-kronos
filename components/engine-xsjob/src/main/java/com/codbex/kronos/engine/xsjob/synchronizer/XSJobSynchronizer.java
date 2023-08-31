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
package com.codbex.kronos.engine.xsjob.synchronizer;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.dirigible.commons.api.helpers.GsonHelper;
import org.eclipse.dirigible.components.api.platform.ProblemsFacade;
import org.eclipse.dirigible.components.base.artefact.Artefact;
import org.eclipse.dirigible.components.base.artefact.ArtefactLifecycle;
import org.eclipse.dirigible.components.base.artefact.ArtefactPhase;
import org.eclipse.dirigible.components.base.artefact.ArtefactService;
import org.eclipse.dirigible.components.base.artefact.topology.TopologyWrapper;
import org.eclipse.dirigible.components.base.synchronizer.Synchronizer;
import org.eclipse.dirigible.components.base.synchronizer.SynchronizerCallback;
import org.eclipse.dirigible.components.data.sources.manager.DataSourcesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.codbex.kronos.engine.xsjob.domain.XSJob;
import com.codbex.kronos.engine.xsjob.service.XSJobService;
import com.codbex.kronos.xsjob.ds.model.JobArtifact;
import com.codbex.kronos.xsjob.ds.scheduler.SchedulerManager;
import com.codbex.kronos.xsjob.ds.transformer.JobToKronosJobDefinitionTransformer;

/**
 * The Class XSJobSynchronizer.
 *
 * @param <A> the generic type
 */
@Component
@Order(220)
public class XSJobSynchronizer<A extends Artefact> implements Synchronizer<XSJob> {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(XSJobSynchronizer.class);
	
	/** The Constant FILE_EXTENSION_XSJOB. */
	private static final String FILE_EXTENSION_XSJOB = ".xsjob";
	
	/** The xsjob service. */
	private XSJobService xsjobService;
	
	/** The datasources manager. */
	private DataSourcesManager datasourcesManager;
	
	/** The synchronization callback. */
	private SynchronizerCallback callback;
	
	/**
	 * Instantiates a new xsjob synchronizer.
	 *
	 * @param xsjobService the xsjob service
	 * @param datasourcesManager the datasources manager
	 */
	@Autowired
	public XSJobSynchronizer(XSJobService xsjobService, DataSourcesManager datasourcesManager) {
		this.xsjobService = xsjobService;
		this.datasourcesManager = datasourcesManager;
	}
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	@Override
	public ArtefactService<XSJob> getService() {
		return xsjobService;
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
		return XSJob.ARTEFACT_TYPE.equals(type);
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
	public List<XSJob> parse(String location, byte[] content) throws ParseException {
		JobArtifact jobArtifact = GsonHelper.fromJson(new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8), JobArtifact.class);
		JobToKronosJobDefinitionTransformer jobToKronosJobDefinitionTransformer = new JobToKronosJobDefinitionTransformer();
	    ArrayList<XSJob> xsjobs = jobToKronosJobDefinitionTransformer.transform(jobArtifact);
	    for (XSJob xsjob : xsjobs) {
	//		Configuration.configureObject(xsjob);
			xsjob.setLocation(location);
			xsjob.setType(XSJob.ARTEFACT_TYPE);
			xsjob.updateKey();
			try {
				XSJob maybe = getService().findByKey(xsjob.getKey());
				if (maybe != null) {
					xsjob.setId(maybe.getId());
				}
				xsjob = getService().save(xsjob);
			} catch (Exception e) {
				if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
				if (logger.isErrorEnabled()) {logger.error("xsjob: {}", xsjob);}
				if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
				throw new ParseException(e.getMessage(), 0);
			}
	    }
		return xsjobs;
	}
	
	/**
	 * Retrieve.
	 *
	 * @param location the location
	 * @return the list
	 */
	@Override
	public List<XSJob> retrieve(String location) {
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
		getService().save((XSJob) artefact);
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
		
			XSJob xsjob = null;
			if (wrapper.getArtefact() instanceof XSJob) {
				xsjob = (XSJob) wrapper.getArtefact();
			} else {
				throw new UnsupportedOperationException(String.format("Trying to process %s as XSJob", wrapper.getArtefact().getClass()));
			}
			
			switch (flow) {
			case CREATE:
				if (ArtefactLifecycle.NEW.equals(xsjob.getLifecycle())) {
					SchedulerManager.scheduleJob(xsjob);
					xsjob.setRunning(true);
					getService().save(xsjob);
					callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
				} else if (ArtefactLifecycle.FAILED.equals(xsjob.getLifecycle())) {
					SchedulerManager.scheduleJob(xsjob);
					xsjob.setRunning(true);
					getService().save(xsjob);
					callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
					ProblemsFacade.deleteArtefactSynchronizationProblem(xsjob);
				}
				break;
			case UPDATE:
				if (ArtefactLifecycle.MODIFIED.equals(xsjob.getLifecycle())) {
					try {
	            		SchedulerManager.unscheduleJob(xsjob.getName(), xsjob.getGroup());
	            		xsjob.setRunning(false);
	            		getService().save(xsjob);
						SchedulerManager.scheduleJob(xsjob);
						xsjob.setRunning(true);
						getService().save(xsjob);
						callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
					} catch (Exception e) {
						if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			            callback.addError(e.getMessage());
						callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, e.getMessage());
					}
				}
				break;
			case DELETE:
				if (ArtefactLifecycle.CREATED.equals(xsjob.getLifecycle())
						|| ArtefactLifecycle.UPDATED.equals(xsjob.getLifecycle())) {
					try {
	            		SchedulerManager.unscheduleJob(xsjob.getName(), xsjob.getGroup());
	            		xsjob.setRunning(false);
	            		getService().delete(xsjob);
						callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, "");
					} catch (Exception e) {
						if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			            callback.addError(e.getMessage());
						callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, e.getMessage());
					}
				}
				break;
			case START:
				if (xsjob.getRunning() == null || !xsjob.getRunning()) {
					try {
						SchedulerManager.scheduleJob(xsjob);
						xsjob.setRunning(true);
						getService().save(xsjob);
					} catch (Exception e) {
						if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			            callback.addError(e.getMessage());
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
					}
				}
				break;
			case STOP:
				if (xsjob.getRunning()) {
					try {
						SchedulerManager.unscheduleJob(xsjob.getName(), xsjob.getGroup());
						xsjob.setRunning(false);
						getService().save(xsjob);
					} catch (Exception e) {
						if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			            callback.addError(e.getMessage());
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
					}
				}
				break;
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
	 * @param xsjob the xsjob
	 */
	@Override
	public void cleanup(XSJob xsjob) {
		try (Connection connection = datasourcesManager.getDefaultDataSource().getConnection()) {
			SchedulerManager.unscheduleJob(xsjob.getName(), xsjob.getGroup());
    		xsjob.setRunning(false);
			getService().delete(xsjob);
			callback.registerState(this, xsjob, ArtefactLifecycle.DELETED, "");
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, xsjob, ArtefactLifecycle.FAILED, e.getMessage());
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
		return FILE_EXTENSION_XSJOB;
	}

	/**
	 * Gets the artefact type.
	 *
	 * @return the artefact type
	 */
	@Override
	public String getArtefactType() {
		return XSJob.ARTEFACT_TYPE;
	}

}
