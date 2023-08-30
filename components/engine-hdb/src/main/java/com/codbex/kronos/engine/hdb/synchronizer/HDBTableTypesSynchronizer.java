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
import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.domain.HDBTableType;
import com.codbex.kronos.engine.hdb.domain.HDBTableTypeColumn;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBTableTypeCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBTableTypeDropProcessor;
import com.codbex.kronos.engine.hdb.service.HDBTableTypeService;
import com.codbex.kronos.exceptions.ArtifactParserException;

/**
 * The Class HDBTableTypesSynchronizer.
 *
 * @param <A> the generic type
 */
@Component
@Order(205)
public class HDBTableTypesSynchronizer<A extends Artefact> implements Synchronizer<HDBTableType> {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(HDBTableTypesSynchronizer.class);
	
	/** The Constant FILE_EXTENSION_TABLETYPE. */
	private static final String FILE_EXTENSION_HDBTABLETYPE = ".hdbtabletype";
	
	/** The table type service. */
	private HDBTableTypeService tableTypeService;
	
	/** The datasources manager. */
	private DataSourcesManager datasourcesManager;
	
	/** The synchronization callback. */
	private SynchronizerCallback callback;
	
	/**
	 * Instantiates a new table type synchronizer.
	 *
	 * @param tableTypeService the table type service
	 * @param datasourcesManager the datasources manager
	 */
	@Autowired
	public HDBTableTypesSynchronizer(HDBTableTypeService tableTypeService, DataSourcesManager datasourcesManager) {
		this.tableTypeService = tableTypeService;
		this.datasourcesManager = datasourcesManager;
	}
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	@Override
	public ArtefactService<HDBTableType> getService() {
		return tableTypeService;
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
		return HDBTableType.ARTEFACT_TYPE.equals(type);
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
	public List<HDBTableType> parse(String location, byte[] content) throws ParseException {
		HDBTableType tableType;
		try {
			tableType = HDBDataStructureModelFactory.parseTableType(location, content);
		} catch (DataStructuresException | IOException | ArtifactParserException e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("hdbtabletype: {}", location);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
//		Configuration.configureObject(tableType);
		tableType.setLocation(location);
		tableType.setType(HDBTable.ARTEFACT_TYPE);
		tableType.updateKey();
		assignParent(tableType);
		
		try {
			HDBTableType maybe = getService().findByKey(tableType.getKey());
			if (maybe != null) {
				tableType.setId(maybe.getId());
				reassignIds(tableType, maybe);
			}
			getService().save(tableType);
			return List.of(tableType);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("hdbtabletype: {}", tableType);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
	}

	static void assignParent(HDBTableType tableType) {
		tableType.getColumns().forEach(c -> c.setTableType(tableType));
		tableType.getPrimaryKey().setTableType(tableType);
	}

	static void reassignIds(HDBTableType tableType, HDBTableType maybe) {
		tableType.getColumns().forEach(c -> {
			HDBTableTypeColumn m = maybe.getColumn(c.getName());
			if (m != null) {
				c.setId(m.getId());
			}
		});
		tableType.getPrimaryKey().setId(maybe.getPrimaryKey().getId());
	}
	
	/**
	 * Retrieve.
	 *
	 * @param location the location
	 * @return the list
	 */
	@Override
	public List<HDBTableType> retrieve(String location) {
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
		getService().save((HDBTableType) artefact);
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
		
			HDBTableType tableType = null;
			if (wrapper.getArtefact() instanceof HDBTableType) {
				tableType = (HDBTableType) wrapper.getArtefact();
			} else {
				throw new UnsupportedOperationException(String.format("Trying to process %s as HDBTableType", wrapper.getArtefact().getClass()));
			}
			
			switch (flow) {
			case CREATE:
				if (ArtefactLifecycle.NEW.equals(tableType.getLifecycle())) {
					if (!SqlFactory.getNative(connection).exists(connection, tableType.getName())) {
							executeTableTypeCreate(connection, tableType);
							callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
					} else {
						if (logger.isWarnEnabled()) {logger.warn(String.format("HDBTableType [%s] already exists during the update process", tableType.getName()));}
						executeTableTypeAlter(connection, tableType);
						callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
					}
				} else if (ArtefactLifecycle.FAILED.equals(tableType.getLifecycle())) {
					if (!SqlFactory.getNative(connection).exists(connection, tableType.getName())) {
						executeTableTypeCreate(connection, tableType);
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
						ProblemsFacade.deleteArtefactSynchronizationProblem(tableType);
					}
				}
				break;
			case UPDATE:
//				if (tableType.getLifecycle().equals(ArtefactLifecycle.CREATED)) {
//					if (SqlFactory.getNative(connection).exists(connection, tableType.getName())) {
//						executeTableTypeForeignKeysCreate(connection, tableType);
//					}
//				}
				if (ArtefactLifecycle.MODIFIED.equals(tableType.getLifecycle())) {
					executeTableTypeUpdate(connection, tableType);
					callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
				}
				break;
			case DELETE:
				if (ArtefactLifecycle.CREATED.equals(tableType.getLifecycle())
						|| ArtefactLifecycle.UPDATED.equals(tableType.getLifecycle())) { 
					if (SqlFactory.getNative(connection).exists(connection, tableType.getName())) {
						if (SqlFactory.getNative(connection).count(connection, tableType.getName()) == 0) {
							executeTableTypeDrop(connection, tableType);
							callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, "");
						} else {
							String message = String.format("HDBTableType [%s] cannot be deleted during the update process, because it is not empty", tableType.getName());
							if (logger.isWarnEnabled()) {logger.warn(message);}
							callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, message);
						}
					}
				}
				break;
//			case POST_DELETE:
//				if (tableType.getLifecycle().equals(ArtefactLifecycle.DELETED)) {
//					if (SqlFactory.getNative(connection).exists(connection, tableType.getName())) {
//						executeTableTypeForeignKeysDrop(connection, tableType);
//						callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
//					}
//				}
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
	 * @param tableType the HDBTableType
	 */
	@Override
	public void cleanup(HDBTableType tableType) {
		try (Connection connection = datasourcesManager.getDefaultDataSource().getConnection()){
			if (SqlFactory.getNative(connection).exists(connection, tableType.getName())) {
				if (SqlFactory.getNative(connection).count(connection, tableType.getName()) == 0) {
					executeTableTypeDrop(connection, tableType);
					getService().delete(tableType);
					callback.registerState(this, tableType, ArtefactLifecycle.DELETED, "");
				} else {
					String message = String.format("HDBTableType [%s] cannot be deleted during the update process, because it is not empty", tableType.getName());
					if (logger.isWarnEnabled()) {logger.warn(message);}
				}
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, tableType, ArtefactLifecycle.FAILED, e.getMessage());
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
	 * Execute tableType update.
	 *
	 * @param connection
	 *            the connection
	 * @param tableTypeModel
	 *            the table type model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeTableTypeUpdate(Connection connection, HDBTableType tableTypeModel) throws SQLException {
		if (logger.isInfoEnabled()) {logger.info("Processing Update HDBTableType: " + tableTypeModel.getName());}
		if (SqlFactory.getNative(connection).exists(connection, tableTypeModel.getName())) {
//			if (SqlFactory.getNative(connection).count(connection, tableTypeModel.getName()) == 0) {
//				executeTableTypeDrop(connection, tableTypeModel);
//				executeTableTypeCreate(connection, tableTypeModel);
//			} else {
				executeTableTypeAlter(connection, tableTypeModel);
//			}
		} else {
			executeTableTypeCreate(connection, tableTypeModel);
		}
	}

	/**
	 * Execute table type create.
	 *
	 * @param connection
	 *            the connection
	 * @param tableTypeModel
	 *            the table type model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeTableTypeCreate(Connection connection, HDBTableType tableTypeModel) throws SQLException {
		new HDBTableTypeCreateProcessor().execute(connection, tableTypeModel);
	}
	
	/**
	 * Execute table type alter.
	 *
	 * @param connection            the connection
	 * @param tableTypeModel            the table type model
	 * @throws SQLException the SQL exception
	 */
	public void executeTableTypeAlter(Connection connection, HDBTableType tableTypeModel) throws SQLException {
		new HDBTableTypeDropProcessor().execute(connection, tableTypeModel);
		new HDBTableTypeCreateProcessor().execute(connection, tableTypeModel);
	}

	/**
	 * Execute table type drop.
	 *
	 * @param connection
	 *            the connection
	 * @param tableTypeModel
	 *            the table type model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeTableTypeDrop(Connection connection, HDBTableType tableTypeModel) throws SQLException {
		new HDBTableTypeDropProcessor().execute(connection, tableTypeModel);
	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return the file extension
	 */
	@Override
	public String getFileExtension() {
		return FILE_EXTENSION_HDBTABLETYPE;
	}

	/**
	 * Gets the artefact type.
	 *
	 * @return the artefact type
	 */
	@Override
	public String getArtefactType() {
		return HDBTableType.ARTEFACT_TYPE;
	}

}
