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
package com.codbex.kronos.xsaccess.ds.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.sql.DataSource;
import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.PersistenceManager;

import com.codbex.kronos.xsaccess.ds.api.IPrivilegeCoreService;
import com.codbex.kronos.xsaccess.ds.api.PrivilegeException;
import com.codbex.kronos.xsaccess.ds.model.privilege.PrivilegeDefinition;

/**
 * The Class PrivilegeCoreService.
 */
public class PrivilegeCoreService implements IPrivilegeCoreService {

  /** The data source. */
  public DataSource getDataSource() {
  	return (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);
  }

  /** The persistence manager. */
  private PersistenceManager<PrivilegeDefinition> persistenceManager = new PersistenceManager<PrivilegeDefinition>();

  /**
   * Creates the privilege.
   *
   * @param name the name
   * @param description the description
   * @return the privilege definition
   * @throws PrivilegeException the privilege exception
   */
  @Override
  public PrivilegeDefinition createPrivilege(String name, String description) throws PrivilegeException {
    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();
        PrivilegeDefinition privilegeDefinition = new PrivilegeDefinition();
        privilegeDefinition.setName(name);
        privilegeDefinition.setDescription(description);
        privilegeDefinition.setCreatedBy(UserFacade.getName());
        privilegeDefinition.setCreatedAt(new Timestamp(new java.util.Date().getTime()));

        persistenceManager.insert(connection, privilegeDefinition);

        return privilegeDefinition;
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new PrivilegeException(e);
    }
  }

  /**
   * Update privileges.
   *
   * @param name the name
   * @param description the description
   * @return the privilege definition
   * @throws PrivilegeException the privilege exception
   */
  @Override
  public PrivilegeDefinition updatePrivileges(String name, String description) throws PrivilegeException {
    PrivilegeDefinition foundXscPrivilegeDefinition = getPrivilegeByName(name);
    if (foundXscPrivilegeDefinition == null) {
      throw new PrivilegeException("Kronos Privilege not found");
    }

    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();
        foundXscPrivilegeDefinition.setName(name);
        foundXscPrivilegeDefinition.setDescription(description);
        persistenceManager.update(connection, foundXscPrivilegeDefinition);

        return foundXscPrivilegeDefinition;
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new PrivilegeException(e);
    }
  }


  /**
   * Gets the privileges.
   *
   * @return the privileges
   * @throws PrivilegeException the privilege exception
   */
  @Override
  public List<PrivilegeDefinition> getPrivileges() throws PrivilegeException {
    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();
        return persistenceManager.findAll(connection, PrivilegeDefinition.class);
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new PrivilegeException(e);
    }
  }

  /**
   * Removes the privilege by name.
   *
   * @param name the name
   * @throws PrivilegeException the privilege exception
   */
  @Override
  public void removePrivilegeByName(String name) throws PrivilegeException {
    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();

        persistenceManager.delete(connection, PrivilegeDefinition.class, name);
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new PrivilegeException(e);
    }
  }

  /**
   * Gets the privilege by name.
   *
   * @param name the name
   * @return the privilege by name
   * @throws PrivilegeException the privilege exception
   */
  @Override
  public PrivilegeDefinition getPrivilegeByName(String name) throws PrivilegeException {
    try {
      Connection connection = null;
      try {
        connection = getDataSource().getConnection();

        PrivilegeDefinition privilegeDefinition = persistenceManager
            .find(connection, PrivilegeDefinition.class, name);

        return privilegeDefinition;
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new PrivilegeException(e);
    }
  }

  /**
   * Privilege exists.
   *
   * @param name the name
   * @return true, if successful
   * @throws PrivilegeException the privilege exception
   */
  @Override
  public boolean privilegeExists(String name) throws PrivilegeException {
    return getPrivilegeByName(name) != null;
  }
}