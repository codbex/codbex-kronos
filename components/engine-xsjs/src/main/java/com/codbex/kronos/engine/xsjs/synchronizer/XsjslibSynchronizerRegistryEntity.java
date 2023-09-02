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
package com.codbex.kronos.engine.xsjs.synchronizer;

import org.eclipse.dirigible.repository.api.ICollection;
import org.eclipse.dirigible.repository.api.IEntity;
import org.eclipse.dirigible.repository.api.IRepository;
import org.eclipse.dirigible.repository.api.IResource;

/**
 * The Class XSJSLibSynchronizerRegistryEntity.
 */
public class XsjslibSynchronizerRegistryEntity {
  
  /** The Constant XSJSLIB_FILE_EXTENSION. */
  private static final String XSJSLIB_FILE_EXTENSION = ".xsjslib";

  /** The repository. */
  private final IRepository repository;
  
  /** The is collection. */
  private boolean isCollection = false;
  
  /** The entity. */
  private IEntity entity = null;

  /**
   * Instantiates a new XSJS lib synchronizer registry entity.
   *
   * @param registryPath the registry path
   * @param repository the repository
   */
  public XsjslibSynchronizerRegistryEntity(String registryPath, IRepository repository) {
    this(registryPath, repository,false);
  }

  /**
   * Instantiates a new XSJS lib synchronizer registry entity.
   *
   * @param registryPath the registry path
   * @param repository the repository
   * @param resolveWithCollectionFirst the resolve with collection first
   */
  public XsjslibSynchronizerRegistryEntity(String registryPath, IRepository repository, boolean resolveWithCollectionFirst) {
    this.repository = repository;

    if(resolveWithCollectionFirst) {
      resolveWithCollectionFirst(registryPath);
    } else {
      resolveWithResourceFirst(registryPath);
    }
  }

  /**
   * Resolve with resource first.
   *
   * @param registryPath the registry path
   */
  private void resolveWithResourceFirst(String registryPath) {
    IResource resource = repository.getResource(registryPath);

    if(resource.exists()) {
      if(registryPath.endsWith(XSJSLIB_FILE_EXTENSION)) {
        entity = resource;
        isCollection = false;
      }
    }
    else {
      ICollection collection = repository.getCollection(registryPath);

      if(collection.exists()) {
        entity = collection;
        isCollection = true;
      }
    }
  }

  /**
   * Resolve with collection first.
   *
   * @param registryPath the registry path
   */
  private void resolveWithCollectionFirst(String registryPath) {
    ICollection collection = repository.getCollection(registryPath);

    if(collection.exists()) {
      entity = collection;
      isCollection = true;
    }
    else {
      IResource resource = repository.getResource(registryPath);

      if(resource.exists()) {
        if(registryPath.endsWith(XSJSLIB_FILE_EXTENSION)) {
          entity = resource;
          isCollection =false;
        }
      }
    }
  }

  /**
   * Checks if is collection.
   *
   * @return true, if is collection
   */
  public boolean isCollection() {
    return isCollection;
  }

  /**
   * Checks if is synchronizable.
   *
   * @return true, if is synchronizable
   */
  public boolean isSynchronizable() {
    return entity != null;
  }

  /**
   * Gets the entity.
   *
   * @return the entity
   */
  public IEntity getEntity() {
    return entity;
  }
}
