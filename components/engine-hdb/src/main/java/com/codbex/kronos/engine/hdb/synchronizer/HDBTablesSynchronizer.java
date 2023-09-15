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
import org.eclipse.dirigible.database.sql.DatabaseArtifactTypes;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.domain.HDBTableColumn;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintCheck;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintForeignKey;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintUnique;
import com.codbex.kronos.engine.hdb.domain.HDBTableIndex;
import com.codbex.kronos.engine.hdb.parser.HDBDataStructureModelFactory;
import com.codbex.kronos.engine.hdb.processors.HDBTableAlterProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBTableCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBTableDropProcessor;
import com.codbex.kronos.engine.hdb.service.HDBTableService;
import com.codbex.kronos.exceptions.ArtifactParserException;

/**
 * The Class HDBTablesSynchronizer.
 *
 * @param <A> the generic type
 */
@Component
@Order(210)
public class HDBTablesSynchronizer<A extends Artefact> implements Synchronizer<HDBTable> {
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(HDBTablesSynchronizer.class);
	
	/** The Constant FILE_EXTENSION_TABLE. */
	private static final String FILE_EXTENSION_HDBTABLE = ".hdbtable";
	
	/** The table service. */
	private HDBTableService tableService;
	
	/** The datasources manager. */
	private DataSourcesManager datasourcesManager;
	
	/** The synchronization callback. */
	private SynchronizerCallback callback;
	
	/**
	 * Instantiates a new table synchronizer.
	 *
	 * @param tableService the table service
	 * @param datasourcesManager the datasources manager
	 */
	@Autowired
	public HDBTablesSynchronizer(HDBTableService tableService, DataSourcesManager datasourcesManager) {
		this.tableService = tableService;
		this.datasourcesManager = datasourcesManager;
	}
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 */
	@Override
	public ArtefactService<HDBTable> getService() {
		return tableService;
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
		return HDBTable.ARTEFACT_TYPE.equals(type);
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
	public List<HDBTable> parse(String location, byte[] content) throws ParseException {
		HDBTable table;
		try {
			table = HDBDataStructureModelFactory.parseTable(location, content);
		} catch (DataStructuresException | IOException | ArtifactParserException e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("hdbtable: {}", location);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
//		Configuration.configureObject(table);
		table.setLocation(location);
		if (table.getTableType() == null) {
			table.setTableType(table.getType());
		}
		table.setType(HDBTable.ARTEFACT_TYPE);
		table.updateKey();
		assignParent(table);
		
		try {
			HDBTable maybe = getService().findByKey(table.getKey());
			if (maybe != null) {
				table.setId(maybe.getId());
				reassignIds(table, maybe);
			}
			getService().save(table);
			return List.of(table);
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			if (logger.isErrorEnabled()) {logger.error("hdbtable: {}", table);}
			if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
			throw new ParseException(e.getMessage(), 0);
		}
	}

	static void assignParent(HDBTable table) {
		table.getColumns().forEach(c -> c.setTable(table));
		if (table.getIndexes() != null) {
			table.getIndexes().forEach(i -> i.setTable(table));
		}
		if (table.getConstraints() != null) {
			table.getConstraints().setTable(table);
			if (table.getConstraints().getPrimaryKey() != null) {
				table.getConstraints().getPrimaryKey().setConstraints(table.getConstraints());
			}
			if (table.getConstraints().getForeignKeys() != null) {
				table.getConstraints().getForeignKeys().forEach(fk -> fk.setConstraints(table.getConstraints()));
			}
			if (table.getConstraints().getUniqueIndexes() != null) {
				table.getConstraints().getUniqueIndexes().forEach(u -> u.setConstraints(table.getConstraints()));
			}
			if (table.getConstraints().getChecks() != null) {
				table.getConstraints().getChecks().forEach(c -> c.setConstraints(table.getConstraints()));
			}
		}
	}

	static void reassignIds(HDBTable table, HDBTable maybe) {
		table.getColumns().forEach(c -> {
			HDBTableColumn m = maybe.getColumn(c.getName());
			if (m != null) {
				c.setId(m.getId());
			}
		});
		if (table.getIndexes() != null) {
			table.getIndexes().forEach(i -> {
				HDBTableIndex m = maybe.getIndex(i.getName());
				if (m != null) {
					i.setId(m.getId());
				}
			});
		}
		if (table.getConstraints() != null) {
			table.getConstraints().setId(maybe.getConstraints().getId());
			if (table.getConstraints().getPrimaryKey() != null
					&& maybe.getConstraints().getPrimaryKey() != null) {
				table.getConstraints().getPrimaryKey().setId(maybe.getConstraints().getPrimaryKey().getId());
			}
			if (table.getConstraints().getForeignKeys() != null
					&& maybe.getConstraints().getForeignKeys() != null) {
				table.getConstraints().getForeignKeys().forEach(fk -> {
					HDBTableConstraintForeignKey m = maybe.getConstraints().getForeignKey(fk.getName());
					if (m != null) {
						fk.setId(m.getId());
					}
				});
			}
			if (table.getConstraints().getUniqueIndexes() != null
					&& maybe.getConstraints().getUniqueIndexes() != null) {
				table.getConstraints().getUniqueIndexes().forEach(ui -> {
					HDBTableConstraintUnique m = maybe.getConstraints().getUniqueIndex(ui.getName());
					if (m != null) {
						ui.setId(m.getId());
					}
				});
			}
			if (table.getConstraints().getChecks() != null
					&& maybe.getConstraints().getChecks() != null) {
				table.getConstraints().getChecks().forEach(ck -> {
					HDBTableConstraintCheck m = maybe.getConstraints().getCheck(ck.getName());
					if (m != null) {
						ck.setId(m.getId());
					}
				});
			}
		}
	}
	
	/**
	 * Retrieve.
	 *
	 * @param location the location
	 * @return the list
	 */
	@Override
	public List<HDBTable> retrieve(String location) {
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
		getService().save((HDBTable) artefact);
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
		
			HDBTable table = null;
			if (wrapper.getArtefact() instanceof HDBTable) {
				table = (HDBTable) wrapper.getArtefact();
			} else {
				throw new UnsupportedOperationException(String.format("Trying to process %s as HDBTable", wrapper.getArtefact().getClass()));
			}
			
			switch (flow) {
			case CREATE:
				if (ArtefactLifecycle.NEW.equals(table.getLifecycle())) {
					if (!SqlFactory.getNative(connection).exists(connection, table.getName(), DatabaseArtifactTypes.TABLE)) {
						executeTableCreate(connection, table);
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
					} else {
						if (logger.isWarnEnabled()) {logger.warn(String.format("HDBTable [%s] already exists during the update process", table.getName()));}
						executeTableAlter(connection, table);
						callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
					}
				} else if (ArtefactLifecycle.FAILED.equals(table.getLifecycle())) {
					if (!SqlFactory.getNative(connection).exists(connection, table.getName(), DatabaseArtifactTypes.TABLE)) {
						executeTableCreate(connection, table);
						callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
						ProblemsFacade.deleteArtefactSynchronizationProblem(table);
					}
				}
				break;
			case UPDATE:
//				if (table.getLifecycle().equals(ArtefactLifecycle.CREATED)) {
//					if (SqlFactory.getNative(connection).exists(connection, table.getName())) {
//						executeTableForeignKeysCreate(connection, table);
//					}
//				}
				if (ArtefactLifecycle.MODIFIED.equals(table.getLifecycle())) {
					executeTableUpdate(connection, table);
					callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
					ProblemsFacade.deleteArtefactSynchronizationProblem(table);
				}
				break;
			case DELETE:
				if (ArtefactLifecycle.CREATED.equals(table.getLifecycle())
						|| ArtefactLifecycle.UPDATED.equals(table.getLifecycle())) { 
					if (SqlFactory.getNative(connection).exists(connection, table.getName(), DatabaseArtifactTypes.TABLE)) {
						if (SqlFactory.deriveDialect(connection).count(connection, table.getName()) == 0) {
							executeTableDrop(connection, table);
							callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, "");
						} else {
							String message = String.format("HDBTable [%s] cannot be deleted during the update process, because it is not empty", table.getName());
							if (logger.isWarnEnabled()) {logger.warn(message);}
							callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, message);
						}
					}
				}
				break;
//			case POST_DELETE:
//				if (table.getLifecycle().equals(ArtefactLifecycle.DELETED)) {
//					if (SqlFactory.getNative(connection).exists(connection, table.getName())) {
//						executeTableForeignKeysDrop(connection, table);
//						callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
//					}
//				}
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
	 * @param table the HDBTable
	 */
	@Override
	public void cleanup(HDBTable table) {
		try (Connection connection = datasourcesManager.getDefaultDataSource().getConnection()){
			if (SqlFactory.getNative(connection).exists(connection, table.getName(), DatabaseArtifactTypes.TABLE)) {
				if (SqlFactory.deriveDialect(connection).count(connection, table.getName()) == 0) {
					executeTableDrop(connection, table);
					getService().delete(table);
					callback.registerState(this, table, ArtefactLifecycle.DELETED, "");
				} else {
					String message = String.format("HDBTable [%s] cannot be deleted during the update process, because it is not empty", table.getName());
					if (logger.isWarnEnabled()) {logger.warn(message);}
				}
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, table, ArtefactLifecycle.FAILED, e.getMessage());
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
	 * Execute table update.
	 *
	 * @param connection
	 *            the connection
	 * @param tableModel
	 *            the table model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeTableUpdate(Connection connection, HDBTable tableModel) throws SQLException {
		if (logger.isInfoEnabled()) {logger.info("Processing Update HDBTable: " + tableModel.getName());}
		if (SqlFactory.getNative(connection).exists(connection, tableModel.getName(), DatabaseArtifactTypes.TABLE)) {
//			if (SqlFactory.getNative(connection).count(connection, tableModel.getName()) == 0) {
//				executeTableDrop(connection, tableModel);
//				executeTableCreate(connection, tableModel);
//			} else {
				executeTableAlter(connection, tableModel);
//			}
		} else {
			executeTableCreate(connection, tableModel);
		}
	}

	/**
	 * Execute table create.
	 *
	 * @param connection
	 *            the connection
	 * @param tableModel
	 *            the table model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeTableCreate(Connection connection, HDBTable tableModel) throws SQLException {
		new HDBTableCreateProcessor().execute(connection, tableModel);
	}
	
//	/**
//	 * Execute table foreign keys create.
//	 *
//	 * @param connection
//	 *            the connection
//	 * @param tableModel
//	 *            the table model
//	 * @throws SQLException
//	 *             the SQL exception
//	 */
//	public void executeTableForeignKeysCreate(Connection connection, HDBTable tableModel) throws SQLException {
//		HDBTableForeignKeysCreateProcessor.execute(connection, tableModel);
//	}

	/**
	 * Execute table alter.
	 *
	 * @param connection            the connection
	 * @param tableModel            the table model
	 * @throws SQLException the SQL exception
	 */
	public void executeTableAlter(Connection connection, HDBTable tableModel) throws SQLException {
		new HDBTableAlterProcessor().execute(connection, tableModel);
	}

	/**
	 * Execute table drop.
	 *
	 * @param connection
	 *            the connection
	 * @param tableModel
	 *            the table model
	 * @throws SQLException
	 *             the SQL exception
	 */
	public void executeTableDrop(Connection connection, HDBTable tableModel) throws SQLException {
		new HDBTableDropProcessor().execute(connection, tableModel);
	}
	
//	/**
//	 * Execute table foreign keys drop.
//	 *
//	 * @param connection
//	 *            the connection
//	 * @param tableModel
//	 *            the table model
//	 * @throws SQLException
//	 *             the SQL exception
//	 */
//	public void executeTableForeignKeysDrop(Connection connection, HDBTable tableModel) throws SQLException {
//		HDBTableForeignKeysDropProcessor.execute(connection, tableModel);
//	}
	
	/**
	 * Gets the file extension.
	 *
	 * @return the file extension
	 */
	@Override
	public String getFileExtension() {
		return FILE_EXTENSION_HDBTABLE;
	}

	/**
	 * Gets the artefact type.
	 *
	 * @return the artefact type
	 */
	@Override
	public String getArtefactType() {
		return HDBTable.ARTEFACT_TYPE;
	}

}
