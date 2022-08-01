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
package com.codbex.kronos.xsaccess.ds.synchronizer;

import com.codbex.kronos.xsaccess.ds.api.IAccessCoreService;
import com.codbex.kronos.xsaccess.ds.api.IPrivilegeCoreService;
import com.codbex.kronos.xsaccess.ds.api.AccessException;
import com.codbex.kronos.xsaccess.ds.api.PrivilegeException;
import com.codbex.kronos.xsaccess.ds.model.access.AccessArtifact;
import com.codbex.kronos.xsaccess.ds.model.access.AccessDefinition;
import com.codbex.kronos.xsaccess.ds.model.privilege.PrivilegeArtifact;
import com.codbex.kronos.xsaccess.ds.model.privilege.PrivilegeDefinition;
import com.codbex.kronos.xsaccess.ds.service.AccessCoreService;
import com.codbex.kronos.xsaccess.ds.service.PrivilegeCoreService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer;
import org.eclipse.dirigible.core.scheduler.api.SchedulerException;
import org.eclipse.dirigible.core.scheduler.api.SynchronizationException;
import org.eclipse.dirigible.core.security.synchronizer.SecuritySynchronizer;
import org.eclipse.dirigible.repository.api.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KronosSecuritySynchronizer extends AbstractSynchronizer {

  private static final Logger logger = LoggerFactory.getLogger(SecuritySynchronizer.class);

  private static final Map<String, List<PrivilegeDefinition>> PRIVILEGES_PREDELIVERED = Collections.synchronizedMap(new HashMap<>());

  private static final Map<String, AccessDefinition> ACCESS_PREDELIVERED = Collections
      .synchronizedMap(new HashMap<>());

  private static final Set<PrivilegeDefinition> PRIVILEGES_TO_BE_PROCESSED = Collections.synchronizedSet(
      new HashSet<>());

  private static final Set<AccessDefinition> ACCESS_TO_BE_PROCESSED = Collections
      .synchronizedSet(new HashSet<>());

  private static final Set<String> PRIVILEGES_SYNCHRONIZED = Collections.synchronizedSet(new HashSet<>());

  private static final Set<String> ACCESS_SYNCHRONIZED = Collections.synchronizedSet(new HashSet<>());
  private final String SYNCHRONIZER_NAME = this.getClass().getCanonicalName();

  private AccessCoreService accessCoreService = new AccessCoreService();

  private PrivilegeCoreService privilegeCoreService = new PrivilegeCoreService();

  /**
   * Force synchronization.
   */
  public static final void forceSynchronization() {
    SecuritySynchronizer synchronizer = new SecuritySynchronizer();
    synchronizer.setForcedSynchronization(true);
    try {
      synchronizer.synchronize();
    } finally {
      synchronizer.setForcedSynchronization(false);
    }
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.ISynchronizer#synchronize()
   */
  @Override
  public void synchronize() {
    synchronized (SecuritySynchronizer.class) {
      if (beforeSynchronizing()) {
        logger.trace("Synchronizing Privileges and Access artifacts...");
        try {
          startSynchronization(SYNCHRONIZER_NAME);
          clearCache();
          synchronizePredelivered();
          synchronizeRegistry();
          int immutablePrivilegesCount = PRIVILEGES_PREDELIVERED.size();
          int immutableAccessCount = ACCESS_PREDELIVERED.size();

          int mutablePrivilegesCount = PRIVILEGES_SYNCHRONIZED.size();
          int mutableAccessCount = ACCESS_SYNCHRONIZED.size();
          processArtifacts();
          cleanup();
          clearCache();
          successfulSynchronization(SYNCHRONIZER_NAME,
              String.format("Immutable: [Privileges: {0}, Access: {1}], Mutable: [Privileges: {2}, Access: {3}]",
                  immutablePrivilegesCount, immutableAccessCount, mutablePrivilegesCount, mutableAccessCount));
        } catch (Exception e) {
          logger.error("Synchronizing process for Privileges and Access artifacts failed.", e);
          try {
            failedSynchronization(SYNCHRONIZER_NAME, e.getMessage());
          } catch (SchedulerException e1) {
            logger.error("Synchronizing process for Privileges and Access artifacts failed in registering the state log.", e);
          }
        }
        logger.trace("Done synchronizing Privileges and Access artifacts.");
        afterSynchronizing();
      }
    }
  }

  /**
   * Register pre-delivered privileges.
   *
   * @param privilegesPath the privileges path
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void registerPredeliveredPrivileges(String privilegesPath) throws IOException {
    InputStream in = SecuritySynchronizer.class.getResourceAsStream(privilegesPath);
    try {
      String json = IOUtils.toString(in, StandardCharsets.UTF_8);
      List<PrivilegeDefinition> privilegeDefinitions = PrivilegeArtifact.parse(json).divide();

      PRIVILEGES_PREDELIVERED.put(privilegesPath, privilegeDefinitions);
    } finally {
      if (in != null) {
        in.close();
      }
    }
  }

  /**
   * Register pre-delivered access.
   *
   * @param accessPath the access path
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void registerPredeliveredAccess(String accessPath) throws IOException {
    InputStream in = SecuritySynchronizer.class.getResourceAsStream(accessPath);
    try {
      String json = IOUtils.toString(in, StandardCharsets.UTF_8);
      AccessDefinition accessDefinition = AccessArtifact.parse(json).toAccessDefinition();

      accessDefinition.setPath(accessPath);

      ACCESS_PREDELIVERED.put(accessPath, accessDefinition);
    } finally {
      if (in != null) {
        in.close();
      }
    }
  }

  /**
   * Clear cache.
   */
  private void clearCache() {
    PRIVILEGES_SYNCHRONIZED.clear();
    PRIVILEGES_TO_BE_PROCESSED.clear();
    ACCESS_SYNCHRONIZED.clear();
    ACCESS_TO_BE_PROCESSED.clear();
    accessCoreService.clearCache();
  }

  /**
   * Synchronize predelivered.
   *
   * @throws SynchronizationException the synchronization exception
   */
  private void synchronizePredelivered() throws SynchronizationException {
    logger.trace("Synchronizing predelivered Privileges and Access artifacts...");

    // Privileges
    for (List<PrivilegeDefinition> privilegeDefinitions : PRIVILEGES_PREDELIVERED.values()) {
      for (PrivilegeDefinition privilegeDefinition : privilegeDefinitions) {
        synchronizePrivilege(privilegeDefinition);
      }
    }

    // Access
    for (AccessDefinition accessDefinition : ACCESS_PREDELIVERED.values()) {
      accessDefinition.setHash("" + accessDefinition.hashCode());
      synchronizeAccess(accessDefinition);
    }

    logger.trace("Done synchronizing predelivered Privileges and Access artifacts.");
  }

  /**
   * Process the privilege and access artifacts.
   *
   * @throws SynchronizationException the synchronization exception
   */
  private void processArtifacts() throws SynchronizationException {
    for (PrivilegeDefinition privilegeDefinition : PRIVILEGES_TO_BE_PROCESSED) {
      synchronizePrivilege(privilegeDefinition);
    }

    for (AccessDefinition accessDefinition : ACCESS_TO_BE_PROCESSED) {
      synchronizeAccess(accessDefinition);
    }
  }

  /**
   * Synchronize privileges.
   *
   * @param privilegeDefinition the privilege definition
   * @throws SynchronizationException the synchronization exception
   */
  private void synchronizePrivilege(PrivilegeDefinition privilegeDefinition) throws SynchronizationException {
    try {
      if (!privilegeCoreService.privilegeExists(privilegeDefinition.getName())) {
        privilegeCoreService.createPrivilege(privilegeDefinition.getName(), privilegeDefinition.getDescription());
        logger.info("Synchronized a new Kronos Privilege [{}]", privilegeDefinition.getName());
      } else {
        PrivilegeDefinition existingXscPrivilegeDefinition = privilegeCoreService.getPrivilegeByName(privilegeDefinition.getName());
        if (!existingXscPrivilegeDefinition.getDescription().equals(privilegeDefinition.getDescription())) {
          privilegeCoreService.updatePrivileges(privilegeDefinition.getName(), privilegeDefinition.getDescription());
          logger.info("Synchronized a modified Kronos Privilege [{}]", privilegeDefinition.getName());
        }

      }

      PRIVILEGES_SYNCHRONIZED.add(privilegeDefinition.getName());
    } catch (PrivilegeException e) {
      throw new SynchronizationException(e);
    }
  }

  /**
   * Synchronize access.
   *
   * @param accessDefinition the access definition
   * @throws SynchronizationException the synchronization exception
   */
  private void synchronizeAccess(AccessDefinition accessDefinition) throws SynchronizationException {
    try {
      AccessDefinition existingAccessDefinition = accessCoreService.getAccessDefinition(accessDefinition.getPath());
      if (existingAccessDefinition == null) {
        accessCoreService.createAccessDefinition(accessDefinition.getPath(), accessDefinition.getAuthenticationMethodsAsList(),
            accessDefinition.getHash(),
            accessDefinition.isExposed(), accessDefinition.getAuthorizationRolesAsList());
        logger.info("Synchronized a new Kronos Access definition [[{}]-[{}]] from location: {}",
            accessDefinition.getAuthenticationMethodsAsList(), String.join(",",
                (accessDefinition.getAuthorizationRolesAsList() != null ? accessDefinition.getAuthorizationRolesAsList()
                    : new ArrayList())), accessDefinition.getPath());
      } else if (!existingAccessDefinition.getHash().equals(accessDefinition.getHash())) {
        accessCoreService.updateAccessDefinition(accessDefinition.getPath(), accessDefinition.getAuthenticationMethodsAsList(),
            accessDefinition.getHash(),
            accessDefinition.isExposed(), accessDefinition.getAuthorizationRolesAsList());
        logger.info("Synchronized a modified Kronos Access definition [[{}]-[{}]] from location: {}",
            accessDefinition.getAuthenticationMethodsAsList(), String.join(",",
                (accessDefinition.getAuthorizationRolesAsList() != null ? accessDefinition.getAuthorizationRolesAsList()
                    : new ArrayList())), accessDefinition.getPath());
      }

      ACCESS_SYNCHRONIZED.add(accessDefinition.getPath());
    } catch (AccessException e) {
      throw new SynchronizationException(e);
    }
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer#synchronizeRegistry()
   */
  @Override
  protected void synchronizeRegistry() throws SynchronizationException {
    logger.trace("Synchronizing Extension Points and Extensions from Registry...");

    super.synchronizeRegistry();

    logger.trace("Done synchronizing Extension Points and Extensions from Registry.");
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer#synchronizeResource(com.codbex.kronos.hdb.ds.parser.DataStructureParser
   * repository.api.IResource)
   */
  @Override
  protected void synchronizeResource(IResource resource) throws SynchronizationException {
    String resourceName = resource.getName();
    if (resourceName.equals(IPrivilegeCoreService.FILE_EXTENSION_PRIVILEGE)) {
      PrivilegeArtifact privilegeArtifact = PrivilegeArtifact.parse(resource.getContent());

      // set name to use "path.to::Privilege" syntax
      String[] splitPath = resource.getPath().split("/");
      String path = String.join(".", Arrays.copyOfRange(splitPath, 3, splitPath.length - 1)); // remove /registry/public

      for (PrivilegeDefinition privilege : privilegeArtifact.divide()) {
        privilege.setName(path + "::" + privilege.getName());
        PRIVILEGES_TO_BE_PROCESSED.add(privilege);
      }
    }
    if (resourceName.equals(IAccessCoreService.FILE_EXTENSION_ACCESS)) {
      AccessDefinition accessDefinition = accessCoreService.parseAccessArtifact(resource.getContent());

      accessDefinition.setPath(removeXscFileFromPath(getRegistryPath(resource)));
      String hash = DigestUtils.md5Hex(resource.getContent());
      accessDefinition.setHash(hash);

      ACCESS_TO_BE_PROCESSED.add(accessDefinition);
    }
  }

  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer#cleanup()
   */
  @Override
  protected void cleanup() throws SynchronizationException {
    logger.trace("Cleaning up Privileges and Access artifacts...");

    try {
      List<PrivilegeDefinition> privilegeDefinitions = privilegeCoreService.getPrivileges();
      for (PrivilegeDefinition privilegeDefinition : privilegeDefinitions) {
        if (!PRIVILEGES_SYNCHRONIZED.contains(privilegeDefinition.getName())) {

          privilegeCoreService.removePrivilegeByName(privilegeDefinition.getName());
          logger.warn("Cleaned up Kronos Privilege [{}]", privilegeDefinition.getName());
        }
      }

      List<AccessDefinition> accessDefinitions = accessCoreService.getAccessDefinitions();
      for (AccessDefinition accessDefinition : accessDefinitions) {
        if (!ACCESS_SYNCHRONIZED.contains(accessDefinition.getPath())) {
          accessCoreService.removeAccessDefinition(accessDefinition.getPath());
          logger.warn("Cleaned up Kronos Access definition [[{}]-[{}]-[{}]] from location: {}",
              accessDefinition.getAuthenticationMethodsAsList(),
              String.join(",", accessDefinition.getAuthorizationRolesAsList()), accessDefinition.getPath());
        }
      }
    } catch (AccessException | PrivilegeException e) {
      throw new SynchronizationException(e);
    }

    logger.trace("Done cleaning up Kronos Privileges and Kronos Access artifacts.");
  }

  private String removeXscFileFromPath(String path) {
    return path.split("\\.")[0];
  }
}
