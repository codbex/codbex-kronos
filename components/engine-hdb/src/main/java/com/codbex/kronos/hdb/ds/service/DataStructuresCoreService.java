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
package com.codbex.kronos.hdb.ds.service;

import static java.text.MessageFormat.format;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.eclipse.dirigible.components.api.security.UserFacade;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.PersistenceManager;
import org.eclipse.dirigible.database.sql.SqlFactory;

import com.codbex.kronos.hdb.ds.api.IDataStructuresCoreService;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.service.parser.ICoreParserService;
import com.codbex.kronos.hdb.ds.service.parser.CoreParserService;

/**
 * The Class DataStructuresCoreService.
 */
public class DataStructuresCoreService implements IDataStructuresCoreService {

  /** The data source. */
  private DataSource getDataSource() {
	  return (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);
  }

  /** The persistence manager. */
  private PersistenceManager<DataStructureModel> persistenceManager = new PersistenceManager<DataStructureModel>();

  /** The core parser service. */
  private ICoreParserService coreParserService = new CoreParserService();

  /**
   * Creates the data structure.
   *
   * @param location the location
   * @param name the name
   * @param hash the hash
   * @param type the type
   * @return the data structure model
   * @throws DataStructuresException the data structures exception
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.database.ds.api.IDataStructuresCoreService#createDataStructure(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public DataStructureModel createDataStructure(String location, String name, String hash, String type)
      throws DataStructuresException {
    DataStructureModel dataStructure = new DataStructureModel();
    dataStructure.setLocation(location);
    dataStructure.setName(name);
    dataStructure.setType(type);
    dataStructure.setHash(hash);
    dataStructure.setCreatedBy(UserFacade.getName());
    dataStructure.setCreatedAt(new Timestamp(new java.util.Date().getTime()));

    try (Connection connection = getDataSource().getConnection()) {
      persistenceManager.insert(connection, dataStructure);
      return dataStructure;
    } catch (SQLException e) {
      throw new DataStructuresException(e);
    }
  }

  /**
   * Gets the data structure.
   *
   * @param <T> the generic type
   * @param location the location
   * @param type the type
   * @return the data structure
   * @throws DataStructuresException the data structures exception
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.database.ds.api.IDataStructuresCoreService#getDataStructure(java.lang.String, java.lang.String)
   */
  @Override
  public <T extends DataStructureModel> T getDataStructure(String location, String type) throws DataStructuresException {
    try (Connection connection = getDataSource().getConnection()) {
      return (T) persistenceManager.find(connection, coreParserService.getDataStructureClass(type), location);
    } catch (SQLException e) {
      throw new DataStructuresException(e);
    }
  }

  /**
   * Gets the data structure by name.
   *
   * @param <T> the generic type
   * @param name the name
   * @param type the type
   * @return the data structure by name
   * @throws DataStructuresException the data structures exception
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.database.ds.api.IDataStructuresCoreService#getDataStructureByName(java.lang.String, java.lang.String)
   */
  @Override
  public <T extends DataStructureModel> T getDataStructureByName(String name, String type) throws DataStructuresException {
    try (Connection connection = getDataSource().getConnection()) {
      String sql = SqlFactory.getNative(connection).select().column("*").from("KRONOS_DATA_STRUCTURES")
          .where("DS_NAME = ? AND DS_TYPE = ?").toString();
      List<DataStructureModel> dataStructures = persistenceManager.query(connection, DataStructureModel.class, sql,
          Arrays.asList(name, type));
      if (dataStructures.isEmpty()) {
        return null;
      }
      if (dataStructures.size() > 1) {
        throw new DataStructuresException(format("There are more than one data structures with the same name [{0}] at locations: [{1}] and [{2}].",
            name, dataStructures.get(0).getLocation(), dataStructures.get(1).getLocation()));
      }
      return (T) dataStructures.get(0);
    } catch (SQLException e) {
      throw new DataStructuresException(e);
    }
  }

  /**
   * Removes the data structure.
   *
   * @param location the location
   * @throws DataStructuresException the data structures exception
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.database.ds.api.IDataStructuresCoreService#removeDataStructure(java.lang.String)
   */
  @Override
  public void removeDataStructure(String location) throws DataStructuresException {
    try (Connection connection = getDataSource().getConnection()) {
      persistenceManager.delete(connection, DataStructureModel.class, location);
    } catch (SQLException e) {
      throw new DataStructuresException(e);
    }
  }

  /**
   * Update data structure.
   *
   * @param location the location
   * @param name the name
   * @param hash the hash
   * @param type the type
   * @throws DataStructuresException the data structures exception
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.database.ds.api.IDataStructuresCoreService#updateDataStructure(java.lang.String,
   * java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public void updateDataStructure(String location, String name, String hash, String type) throws DataStructuresException {
    try (Connection connection = getDataSource().getConnection()) {
      DataStructureModel dataStructure = getDataStructure(location, type);
      dataStructure.setName(name);
      dataStructure.setHash(hash);
      persistenceManager.update(connection, dataStructure);
    } catch (SQLException e) {
      throw new DataStructuresException(e);
    }
  }

  /**
   * Gets the data structures by type.
   *
   * @param <T> the generic type
   * @param type the type
   * @return the data structures by type
   * @throws DataStructuresException the data structures exception
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.database.ds.api.IDataStructuresCoreService#getDataStructures(java.lang.String)
   */
  @Override
  public <T extends DataStructureModel> List<T> getDataStructuresByType(String type) throws DataStructuresException {
    try (Connection connection = getDataSource().getConnection()) {
      String sql = SqlFactory.getNative(connection).select().column("*").from("KRONOS_DATA_STRUCTURES").where("DS_TYPE = ?").toString();
      return (List<T>) persistenceManager.query(connection, coreParserService.getDataStructureClass(type), sql,
          Arrays.asList(type));
    } catch (SQLException e) {
      throw new DataStructuresException(e);
    }
  }

  /**
   * Exists data structure.
   *
   * @param location the location
   * @param type the type
   * @return true, if successful
   * @throws DataStructuresException the data structures exception
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.database.ds.api.IDataStructuresCoreService#existsDataStructure(java.lang.String, java.lang.String)
   */
  @Override
  public boolean existsDataStructure(String location, String type) throws DataStructuresException {
    return getDataStructure(location, type) != null;
  }

  /**
   * Exists data structure by location and hash.
   *
   * @param location the location
   * @param hash the hash
   * @param type the type
   * @return true, if successful
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public boolean existsDataStructureByLocationAndHash(String location, String hash, String type) throws DataStructuresException {
    try (Connection connection = getDataSource().getConnection()) {
      String sql = SqlFactory.getNative(connection).select().column("*").from("KRONOS_DATA_STRUCTURES")
          .where("DS_LOCATION = ? AND DS_HASH = ?").toString();
      return !persistenceManager.query(connection, coreParserService.getDataStructureClass(type), sql,
          Arrays.asList(location, hash)).isEmpty();
    } catch (SQLException e) {
      throw new DataStructuresException(e);
    }
  }
}
