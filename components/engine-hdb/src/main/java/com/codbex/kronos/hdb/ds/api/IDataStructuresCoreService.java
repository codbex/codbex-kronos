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
package com.codbex.kronos.hdb.ds.api;

import java.util.List;

import org.eclipse.dirigible.commons.api.service.ICoreService;

import com.codbex.kronos.hdb.ds.model.DataStructureModel;

/**
 * The Data Structures Core Service interface.
 */
public interface IDataStructuresCoreService extends ICoreService {

  /**
   * Creates the DataStructure.
   *
   * @param location the location
   * @param name     the name
   * @param hash     the hash
   * @param type     the type of the DataStructure
   * @return the data structure model
   * @throws DataStructuresException the data structures exception
   */
  public DataStructureModel createDataStructure(String location, String name, String hash, String type)
      throws DataStructuresException;

  /**
   * Gets the DataStructure.
   *
   * @param <T> the generic type
   * @param location the location
   * @param type     the type of the DataStructure
   * @return the DataStructure
   * @throws DataStructuresException the data structures exception
   */
  public <T extends DataStructureModel> T getDataStructure(String location, String type) throws DataStructuresException;

  /**
   * Gets the DataStructure by name.
   *
   * @param <T> the generic type
   * @param name the name
   * @param type the type
   * @return the DataStructure by name
   * @throws DataStructuresException the data structures exception
   */
  public <T extends DataStructureModel> T getDataStructureByName(String name, String type) throws DataStructuresException;

  /**
   * Checks if DataStructure exists.
   *
   * @param location the location
   * @param type     the type of the DataStructure
   * @return true, if successful
   * @throws DataStructuresException the data structures exception
   */
  public boolean existsDataStructure(String location, String type) throws DataStructuresException;

  /**
   * Removes the DataStructure.
   *
   * @param location the location
   * @throws DataStructuresException the data structures exception
   */
  public void removeDataStructure(String location) throws DataStructuresException;

  /**
   * Update DataStructure.
   *
   * @param location the location
   * @param name     the name
   * @param hash     the hash
   * @param type     the type of the DataStructure
   * @throws DataStructuresException the data structures exception
   */
  public void updateDataStructure(String location, String name, String hash, String type) throws DataStructuresException;

  /**
   * Gets the DataStructures.
   *
   * @param <T> the generic type
   * @param type the type
   * @return the tables
   * @throws DataStructuresException the data structures exception
   * the type of the DataStructure
   */
  public <T extends DataStructureModel> List<T> getDataStructuresByType(String type) throws DataStructuresException;

  /**
   * Checks if DataStructure with given location and hash exists.
   *
   * @param location the location
   * @param hash     the hash of the file contents
   * @param type     the type of the DataStructure
   * @return true, if the DataStructure exists
   * @throws DataStructuresException the data structures exception
   */
  public boolean existsDataStructureByLocationAndHash(String location, String hash, String type) throws DataStructuresException;
}
