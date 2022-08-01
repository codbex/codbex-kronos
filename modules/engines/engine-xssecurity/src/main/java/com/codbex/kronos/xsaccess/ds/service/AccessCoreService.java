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

import static com.codbex.kronos.utils.Utils.objectToByteArray;

import com.codbex.kronos.utils.Utils;
import com.codbex.kronos.xsaccess.ds.api.IAccessCoreService;
import com.codbex.kronos.xsaccess.ds.api.AccessException;
import com.codbex.kronos.xsaccess.ds.api.PrivilegeException;
import com.codbex.kronos.xsaccess.ds.model.access.AccessArtifact;
import com.codbex.kronos.xsaccess.ds.model.access.AccessDefinition;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.database.persistence.PersistenceManager;

public class AccessCoreService implements IAccessCoreService {

  private static final List<AccessDefinition> CACHE = Collections.synchronizedList(new ArrayList<>());
  private DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.SYSTEM_DATASOURCE);
  private PersistenceManager<AccessDefinition> persistenceManager = new PersistenceManager<AccessDefinition>();
  private PrivilegeCoreService privilegeCoreService = new PrivilegeCoreService();

  @Override
  public AccessDefinition createAccessDefinition(String path, List<String> authenticationMethodsAsList, String hash, boolean exposed,
      List<String> authorizationRolesAsList) throws AccessException {
    try {
      AccessDefinition accessDefinition = new AccessDefinition();
      accessDefinition.setPath(path);

      if (authenticationMethodsAsList == null) {
        authenticationMethodsAsList = new ArrayList<String>();
        accessDefinition.setAuthenticationMethodsAsList(new ArrayList<String>());
      }
      accessDefinition.setAuthenticationMethods(objectToByteArray(authenticationMethodsAsList));

      accessDefinition.setHash(hash);
      accessDefinition.setExposed(exposed);
      if (authorizationRolesAsList == null) {
        authorizationRolesAsList = new ArrayList<String>();
        accessDefinition.setAuthorizationRolesAsList(new ArrayList<String>());
      }

      validatePrivileges(authorizationRolesAsList);
      accessDefinition.setAuthorizationRoles(objectToByteArray(authorizationRolesAsList));

      accessDefinition.setCreatedBy(UserFacade.getName());
      accessDefinition.setCreatedAt(new Timestamp(new java.util.Date().getTime()));

      Connection connection = null;
      try {
        connection = dataSource.getConnection();
        persistenceManager.insert(connection, accessDefinition);
        clearCache();
        return accessDefinition;
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException | IOException | PrivilegeException е) {
      throw new AccessException(е);
    }
  }

  @Override
  public AccessDefinition updateAccessDefinition(String path, List<String> authenticationMethodsAsList, String hash, boolean exposed,
      List<String> authorizationRolesAsList) throws AccessException {
    try {
      AccessDefinition accessDefinition = getAccessDefinition(path);
      if (authenticationMethodsAsList != null && !authenticationMethodsAsList.isEmpty()) {
        accessDefinition.setAuthenticationMethods(objectToByteArray(authenticationMethodsAsList));
      }
      accessDefinition.setHash(hash);
      accessDefinition.setExposed(exposed);
      if (authorizationRolesAsList != null && !authorizationRolesAsList.isEmpty()) {
        validatePrivileges(authorizationRolesAsList);
        accessDefinition.setAuthorizationRoles(objectToByteArray(authorizationRolesAsList));
      }

      Connection connection = null;
      try {
        connection = dataSource.getConnection();
        persistenceManager.update(connection, accessDefinition);
        clearCache();
        return accessDefinition;
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException | IOException | PrivilegeException е) {
      throw new AccessException(е);
    }
  }

  @Override
  public AccessDefinition getAccessDefinition(String id) throws AccessException {
    try {
      Connection connection = null;
      try {
        connection = dataSource.getConnection();
        AccessDefinition accessDefinition = persistenceManager.find(connection, AccessDefinition.class, id);
        if (accessDefinition == null) {
          return null;
        }

        List<String> authenticationMethods = Utils.byteArrayToObject(accessDefinition.getAuthenticationMethods());
        accessDefinition.setAuthenticationMethodsAsList(authenticationMethods);

        List<String> authorizationRoles = Utils.byteArrayToObject(accessDefinition.getAuthorizationRoles());
        accessDefinition.setAuthorizationRolesAsList(authorizationRoles);
        return accessDefinition;
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException | IOException | ClassNotFoundException e) {
      throw new AccessException(e);
    }
  }

  @Override
  public List<AccessDefinition> getAccessDefinitions() throws AccessException {
    if (!CACHE.isEmpty()) {
      return Collections.unmodifiableList(CACHE);
    }

    try {
      Connection connection = null;
      try {
        connection = dataSource.getConnection();
        List<AccessDefinition> accessDefinitions = persistenceManager
            .findAll(connection, AccessDefinition.class);
        for (AccessDefinition accessDefinition : accessDefinitions) {
          if (accessDefinition.getAuthorizationRoles() != null) {
            List<String> authorizationRoles = Utils.byteArrayToObject(accessDefinition.getAuthorizationRoles());
            accessDefinition.setAuthorizationRolesAsList(authorizationRoles);
          } else {
            accessDefinition.setAuthorizationRolesAsList(new ArrayList<String>());
          }
        }
        CACHE.addAll(accessDefinitions);
        return Collections.unmodifiableList(accessDefinitions);
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException | IOException | ClassNotFoundException e) {
      throw new AccessException(e);
    }
  }

  @Override
  public void removeAccessDefinition(String path) throws AccessException {
    try {
      Connection connection = null;
      try {
        connection = dataSource.getConnection();
        persistenceManager.delete(connection, AccessDefinition.class, path);
      } finally {
        if (connection != null) {
          connection.close();
        }
      }
    } catch (SQLException e) {
      throw new AccessException(e);
    }
  }


  @Override
  public boolean existsAccessDefinition(String path) throws AccessException {
    return getAccessDefinition(path) != null;
  }

  @Override
  public AccessDefinition parseAccessArtifact(byte[] json) {
    AccessArtifact accessArtifact = AccessArtifact.parse(json);
    return accessArtifact.toAccessDefinition();
  }

  @Override
  public void clearCache() {
    CACHE.clear();
  }

  private void validatePrivileges(List<String> privileges) throws PrivilegeException {
    for (String privilege : privileges) {
      if (!privilegeCoreService.privilegeExists(privilege)) {
        throw new PrivilegeException(String.format("No Kronos Privilege found with name: {%s}", privilege));
      }
    }
  }
}