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
import com.codbex.kronos.engine.hdb.domain.HDBSequence;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBSequenceCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBSequenceDropProcessor;
import com.codbex.kronos.engine.hdb.service.HDBSequenceService;
import com.codbex.kronos.exceptions.ArtifactParserException;

/**
 * The Class HDBSequencesSynchronizer.
 *
 * @param <A> the generic type
 */
@Component
@Order(205)
public class HDBSequencesSynchronizer<A extends Artefact> implements Synchronizer<HDBSequence> {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(HDBSequencesSynchronizer.class);
	
	/** The Constant FILE_EXTENSION_HDBSEQUENCE. */
	private static final String FILE_EXTENSION_HDBSEQUENCE = ".hdbsequence";
	
	/** The sequence service. */
	private HDBSequenceService sequenceService;
	
	/** The datasources manager. */
	private DataSourcesManager datasourcesManager;
	
	/** The synchronization callback. */
	private SynchronizerCallback callback;
	
	/**
	 * Instantiates a new sequence synchronizer.
	 *
	 * @param sequenceService the sequence service
	 * @param datasourcesManager the datasources manager
	 */
	@Autowired
	public HDBSequencesSynchronizer(HDBSequenceService sequenceService, DataSourcesManager datasourcesManager) {
		this.sequenceService = sequenceService;
		this.datasourcesManager = datasourcesManager;
	}
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	@Override
	public ArtefactService<HDBSequence> getService() {
		return sequenceService;
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
		return HDBSequence.ARTEFACT_TYPE.equals(type);
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
	public List<HDBSequence> parse(String location, byte[] content) throws ParseException {
		HDBSequence sequence;
		try {
			sequence = HDBDataStructureModelFactory.parseSequence(location, content);
		} catch (DataStructuresException | IOException | ArtifactParserException e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("hdbtable: {}", location);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
		
//		Configuration.configureObject(sequence);
		sequence.setLocation(location);
		sequence.setType(HDBSequence.ARTEFACT_TYPE);
		sequence.updateKey();
		
		try {
			HDBSequence maybe = getService().findByKey(sequence.getKey());
			if (maybe != null) {
				sequence.setId(maybe.getId());
			}
			sequence = getService().save(sequence);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("sequence: {}", sequence);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
		return List.of(sequence);
	}
	
	/**
	 * Retrieve.
	 *
	 * @param location the location
	 * @return the list
	 */
	@Override
	public List<HDBSequence> retrieve(String location) {
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
		getService().save((HDBSequence) artefact);
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
		
			HDBSequence sequence = null;
			if (wrapper.getArtefact() instanceof HDBSequence) {
				sequence = (HDBSequence) wrapper.getArtefact();
			} else {
				throw new UnsupportedOperationException(String.format("Trying to process %s as HDBSequence", wrapper.getArtefact().getClass()));
			}
			
			switch (flow) {
			case CREATE:
				if (ArtefactLifecycle.NEW.equals(sequence.getLifecycle())) {
					if (!SqlFactory.getNative(connection).exists(connection, sequence.getName())) {
						executeSequenceCreate(connection, sequence);
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
					} else {
						if (logger.isWarnEnabled()) {logger.warn(String.format("HDBSequence [%s] already exists during the update process", sequence.getName()));}
						executeSequenceUpdate(connection, sequence);
						callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
					}
				}
				break;
			case UPDATE:
				if (ArtefactLifecycle.MODIFIED.equals(sequence.getLifecycle())) {
					executeSequenceUpdate(connection, sequence);
					callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
				}
				break;
			case DELETE:
				if (ArtefactLifecycle.CREATED.equals(sequence.getLifecycle())) {
					if (SqlFactory.getNative(connection).exists(connection, sequence.getName())) {
						executeSequenceDrop(connection, sequence);
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
	 * @param sequence the sequence
	 */
	@Override
	public void cleanup(HDBSequence sequence) {
		try (Connection connection = datasourcesManager.getDefaultDataSource().getConnection()) {
			getService().delete(sequence);
			callback.registerState(this, sequence, ArtefactLifecycle.DELETED, "");
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, sequence, ArtefactLifecycle.FAILED, e.getMessage());
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
	 * Execute sequence update.
	 *
	 * @param connection
	 *            the connection
	 * @param sequenceModel
	 *            the sequence model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeSequenceUpdate(Connection connection, HDBSequence sequenceModel) throws SQLException {
		if (logger.isInfoEnabled()) {logger.info("Processing Update Sequence: " + sequenceModel.getName());}
		if (SqlFactory.getNative(connection).exists(connection, sequenceModel.getName())) {
			executeSequenceDrop(connection, sequenceModel);
			executeSequenceCreate(connection, sequenceModel);
		} else {
			executeSequenceCreate(connection, sequenceModel);
		}
	}

	/**
	 * Execute sequence create.
	 *
	 * @param connection
	 *            the connection
	 * @param sequenceModel
	 *            the sequence model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeSequenceCreate(Connection connection, HDBSequence sequenceModel) throws SQLException {
		new HDBSequenceCreateProcessor().execute(connection, sequenceModel);
	}
	
	/**
	 * Execute sequence drop.
	 *
	 * @param connection
	 *            the connection
	 * @param sequenceModel
	 *            the sequence model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeSequenceDrop(Connection connection, HDBSequence sequenceModel) throws SQLException {
		new HDBSequenceDropProcessor().execute(connection, sequenceModel);
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return the file extension
	 */
	@Override
	public String getFileExtension() {
		return FILE_EXTENSION_HDBSEQUENCE;
	}

	/**
	 * Gets the artefact type.
	 *
	 * @return the artefact type
	 */
	@Override
	public String getArtefactType() {
		return HDBSequence.ARTEFACT_TYPE;
	}

}
