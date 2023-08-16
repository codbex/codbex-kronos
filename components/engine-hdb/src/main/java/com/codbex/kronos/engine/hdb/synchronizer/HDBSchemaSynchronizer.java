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
import com.codbex.kronos.engine.hdb.domain.HDBSchema;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBSchemaCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBSchemaDropProcessor;
import com.codbex.kronos.engine.hdb.service.HDBSchemaService;
import com.codbex.kronos.exceptions.ArtifactParserException;

/**
 * The Class HDBSchemasSynchronizer.
 *
 * @param <A> the generic type
 */
@Component
@Order(200)
public class HDBSchemaSynchronizer<A extends Artefact> implements Synchronizer<HDBSchema> {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(HDBSchemaSynchronizer.class);
	
	/** The Constant FILE_EXTENSION_HDBSCHEMA. */
	private static final String FILE_EXTENSION_HDBSCHEMA = ".hdbschema";
	
	/** The schema service. */
	private HDBSchemaService schemaService;
	
	/** The datasources manager. */
	private DataSourcesManager datasourcesManager;
	
	/** The synchronization callback. */
	private SynchronizerCallback callback;
	
	/**
	 * Instantiates a new schema synchronizer.
	 *
	 * @param schemaService the schema service
	 * @param datasourcesManager the datasources manager
	 */
	@Autowired
	public HDBSchemaSynchronizer(HDBSchemaService schemaService, DataSourcesManager datasourcesManager) {
		this.schemaService = schemaService;
		this.datasourcesManager = datasourcesManager;
	}
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	@Override
	public ArtefactService<HDBSchema> getService() {
		return schemaService;
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
		return HDBSchema.ARTEFACT_TYPE.equals(type);
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
	public List<HDBSchema> parse(String location, byte[] content) throws ParseException {
		HDBSchema schema;
		try {
			schema = HDBDataStructureModelFactory.parseSchema(location, content);
		} catch (DataStructuresException | IOException | ArtifactParserException e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("hdbtable: {}", location);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
		
//		Configuration.configureObject(schema);
		schema.setLocation(location);
		schema.setType(HDBSchema.ARTEFACT_TYPE);
		schema.updateKey();
		
		try {
			HDBSchema maybe = getService().findByKey(schema.getKey());
			if (maybe != null) {
				schema.setId(maybe.getId());
			}
			schema = getService().save(schema);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("schema: {}", schema);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
		return List.of(schema);
	}
	
	/**
	 * Retrieve.
	 *
	 * @param location the location
	 * @return the list
	 */
	@Override
	public List<HDBSchema> retrieve(String location) {
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
		getService().save((HDBSchema) artefact);
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
		
			HDBSchema schema = null;
			if (wrapper.getArtefact() instanceof HDBSchema) {
				schema = (HDBSchema) wrapper.getArtefact();
			} else {
				throw new UnsupportedOperationException(String.format("Trying to process %s as HDBSchema", wrapper.getArtefact().getClass()));
			}
			
			switch (flow) {
			case CREATE:
				if (ArtefactLifecycle.NEW.equals(schema.getLifecycle())) {
					if (!SqlFactory.getNative(connection).exists(connection, schema.getName())) {
						executeSchemaCreate(connection, schema);
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
					} else {
						if (logger.isWarnEnabled()) {logger.warn(String.format("HDBSchema [%s] already exists during the update process", schema.getName()));}
						executeSchemaUpdate(connection, schema);
						callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
					}
				}
				break;
			case UPDATE:
				if (ArtefactLifecycle.MODIFIED.equals(schema.getLifecycle())) {
					executeSchemaUpdate(connection, schema);
					callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
				}
				break;
			case DELETE:
				if (ArtefactLifecycle.CREATED.equals(schema.getLifecycle())) {
					if (SqlFactory.getNative(connection).exists(connection, schema.getName())) {
						executeSchemaDrop(connection, schema);
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
	 * @param schema the schema
	 */
	@Override
	public void cleanup(HDBSchema schema) {
		try (Connection connection = datasourcesManager.getDefaultDataSource().getConnection()) {
			getService().delete(schema);
			callback.registerState(this, schema, ArtefactLifecycle.DELETED, "");
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, schema, ArtefactLifecycle.FAILED, e.getMessage());
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
	 * Execute schema update.
	 *
	 * @param connection
	 *            the connection
	 * @param schemaModel
	 *            the schema model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeSchemaUpdate(Connection connection, HDBSchema schemaModel) throws SQLException {
		if (logger.isInfoEnabled()) {logger.info("Processing Update Schema: " + schemaModel.getName());}
		if (SqlFactory.getNative(connection).exists(connection, schemaModel.getName())) {
			executeSchemaDrop(connection, schemaModel);
			executeSchemaCreate(connection, schemaModel);
		} else {
			executeSchemaCreate(connection, schemaModel);
		}
	}

	/**
	 * Execute schema create.
	 *
	 * @param connection
	 *            the connection
	 * @param schemaModel
	 *            the schema model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeSchemaCreate(Connection connection, HDBSchema schemaModel) throws SQLException {
		new HDBSchemaCreateProcessor().execute(connection, schemaModel);
	}
	
	/**
	 * Execute schema drop.
	 *
	 * @param connection
	 *            the connection
	 * @param schemaModel
	 *            the schema model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeSchemaDrop(Connection connection, HDBSchema schemaModel) throws SQLException {
		new HDBSchemaDropProcessor().execute(connection, schemaModel);
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return the file extension
	 */
	@Override
	public String getFileExtension() {
		return FILE_EXTENSION_HDBSCHEMA;
	}

	/**
	 * Gets the artefact type.
	 *
	 * @return the artefact type
	 */
	@Override
	public String getArtefactType() {
		return HDBSchema.ARTEFACT_TYPE;
	}

}
