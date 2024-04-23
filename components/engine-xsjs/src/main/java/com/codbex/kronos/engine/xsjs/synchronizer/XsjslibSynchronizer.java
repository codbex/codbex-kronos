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

import com.codbex.kronos.engine.xsjs.domain.Xsjslib;
import com.codbex.kronos.engine.xsjs.service.XsjslibService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.dirigible.components.api.platform.ProblemsFacade;
import org.eclipse.dirigible.components.base.artefact.ArtefactLifecycle;
import org.eclipse.dirigible.components.base.artefact.ArtefactPhase;
import org.eclipse.dirigible.components.base.artefact.topology.TopologyWrapper;
import org.eclipse.dirigible.components.base.synchronizer.BaseSynchronizer;
import org.eclipse.dirigible.components.base.synchronizer.SynchronizerCallback;
import org.eclipse.dirigible.components.engine.javascript.service.JavascriptService;
import org.eclipse.dirigible.repository.api.IRepository;
import org.eclipse.dirigible.repository.api.IRepositoryStructure;
import org.eclipse.dirigible.repository.api.IResource;
import org.eclipse.dirigible.repository.api.RepositoryPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * The Class XsjslibSynchronizer.
 */
@Component
@Order(80)
public class XsjslibSynchronizer extends BaseSynchronizer<Xsjslib, Long> {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(XsjslibSynchronizer.class);

  /**
   * The Constant FILE_EXTENSION_XSJSLIB.
   */
  public static final String FILE_EXTENSION_XSJSLIB = ".xsjslib";

  /**
   * The Constant XSJSLIB_GENERATION_RUNNER_LOCATION.
   */
  private static final String XSJSLIB_GENERATION_RUNNER_LOCATION = "/exports/XSJSLibRunner.mjs";

  /**
   * The Constant XSJSLIB_SYNCHRONIZER_STATE_TABLE_NAME.
   */
  public static final String XSJSLIB_SYNCHRONIZER_STATE_TABLE_NAME = "CODBEX_KRONOS_XSJSLIB";

  /**
   * The xsjslib service.
   */
  private final XsjslibService xsjslibService;

  /**
   * The IRepository.
   */
  private final IRepository repository;

  /**
   * The synchronization callback.
   */
  private SynchronizerCallback callback;

  /**
   * The javascript service.
   */
  private final JavascriptService javascriptService;

  /**
   * Instantiates a new xsjslib synchronizer.
   *
   * @param xsjslibService the xsjslib service
   */
  @Autowired
  public XsjslibSynchronizer(XsjslibService xsjslibService, IRepository repository,
      JavascriptService javascriptService) {
    this.xsjslibService = xsjslibService;
    this.repository = repository;
    this.javascriptService = javascriptService;
  }

  /**
   * Gets the service.
   *
   * @return the service
   */
  @Override
  public XsjslibService getService() {
    return xsjslibService;
  }

  /**
   * Gets the repository.
   *
   * @return the repository
   */
  public IRepository getRepository() {
    return repository;
  }

  /**
   * Checks if is accepted.
   *
   * @param file  the file
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
   * @param type the artefact
   * @return true, if is accepted
   */
  @Override
  public boolean isAccepted(String type) {
    return Xsjslib.ARTEFACT_TYPE.equals(type);
  }

  /**
   * Load.
   *
   * @param location the location
   * @param content  the content
   * @return the list
   * @throws ParseException
   */
  @Override
  public List<Xsjslib> parse(String location, byte[] content) throws ParseException {
    Xsjslib xsjslib = new Xsjslib();
    xsjslib.setLocation(location);
    xsjslib.setName(Paths.get(location).getFileName().toString());
    xsjslib.setType(Xsjslib.ARTEFACT_TYPE);
    xsjslib.updateKey();
    xsjslib.setContent(content);
    try {
      Xsjslib maybe = getService().findByKey(xsjslib.getKey());
      if (maybe != null) {
        xsjslib.setId(maybe.getId());
      }
      xsjslib = getService().save(xsjslib);
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(e.getMessage(), e);
      }
      if (logger.isErrorEnabled()) {
        logger.error("xsjslib: {}", xsjslib);
      }
      if (logger.isErrorEnabled()) {
        logger.error("content: {}", new String(content));
      }
      throw new ParseException(e.getMessage(), 0);
    }
    return List.of(xsjslib);
  }

  /**
   * Retrieve.
   *
   * @param location the location
   * @return the list
   */
  @Override
  public List<Xsjslib> retrieve(String location) {
    return getService().getAll();
  }

  /**
   * Sets the status.
   *
   * @param artefact  the artefact
   * @param lifecycle the lifecycle
   * @param error     the error
   */
  @Override
  public void setStatus(Xsjslib artefact, ArtefactLifecycle lifecycle, String error) {
    artefact.setLifecycle(lifecycle);
    artefact.setError(error);
    getService().save(artefact);
  }

  /**
   * Complete.
   *
   * @param wrapper the wrapper
   * @param flow    the flow
   * @return true, if successful
   */
  @Override
  public boolean completeImpl(TopologyWrapper<Xsjslib> wrapper, ArtefactPhase flow) {

    try {
      Xsjslib xsjslib = wrapper.getArtefact();

      switch (flow) {
        case CREATE:
          if (xsjslib.getLifecycle().equals(ArtefactLifecycle.NEW)) {
            synchronizeXSJSLibs(xsjslib);
            callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
          } else if (xsjslib.getLifecycle().equals(ArtefactLifecycle.FAILED)) {
            synchronizeXSJSLibs(xsjslib);
            callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
            ProblemsFacade.deleteArtefactSynchronizationProblem(xsjslib);
          }
          break;
        case UPDATE:
          if (xsjslib.getLifecycle().equals(ArtefactLifecycle.MODIFIED)) {
            synchronizeXSJSLibs(xsjslib);
            callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
            ProblemsFacade.deleteArtefactSynchronizationProblem(xsjslib);
          }
          break;
        case DELETE:
          if (xsjslib.getLifecycle().equals(ArtefactLifecycle.CREATED)
              || xsjslib.getLifecycle().equals(ArtefactLifecycle.UPDATED)) {
            callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, "");
          }
          break;
      }
      return true;
    } catch (Exception e) {
      String errorMessage = String.format("Error occurred while processing [%s]: %s", wrapper.getArtefact().getLocation(), e.getMessage());
      if (logger.isErrorEnabled()) {
        logger.error(errorMessage, e);
      }
      callback.addError(errorMessage);
      callback.registerState(this, wrapper, ArtefactLifecycle.FAILED, errorMessage);
      ProblemsFacade.upsertArtefactSynchronizationProblem(wrapper.getArtefact(), errorMessage);
      return false;
    }
  }

  /**
   * Cleanup.
   *
   * @param xsjslib the xsjslib
   */
  @Override
  public void cleanupImpl(Xsjslib xsjslib) {
    try {
      IResource resource = repository.getResource(
          IRepositoryStructure.PATH_REGISTRY_PUBLIC + xsjslib.getLocation() + "_generated_exports.js");
      if (resource.exists()) {
        resource.delete();
      }
      getService().delete(xsjslib);
      callback.registerState(this, xsjslib, ArtefactLifecycle.DELETED, "");
    } catch (Exception e) {
      if (logger.isErrorEnabled()) {
        logger.error(e.getMessage(), e);
      }
      callback.addError(e.getMessage());
      callback.registerState(this, xsjslib, ArtefactLifecycle.FAILED, e.getMessage());
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
   * Gets the file xsjslib.
   *
   * @return the file xsjslib
   */
  @Override
  public String getFileExtension() {
    return FILE_EXTENSION_XSJSLIB;
  }

  /**
   * Gets the artefact type.
   *
   * @return the artefact type
   */
  @Override
  public String getArtefactType() {
    return Xsjslib.ARTEFACT_TYPE;
  }

  /**
   * Synchronize XSJS libs.
   */
  public void synchronizeXSJSLibs(Xsjslib xsjslib) {
    logger.trace("Synchronizing XSJSLibs...");

    Map<Object, Object> context = buildContext(xsjslib);
    RepositoryPath path = new RepositoryPath(XSJSLIB_GENERATION_RUNNER_LOCATION);
    javascriptService.handleRequest(path.getSegments()[0], path.constructPathFrom(1), null, context, false);

    logger.trace("Done synchronizing XSJSLibs.");
  }

  /**
   * Builds the context.
   *
   * @return the map
   */
  private Map<Object, Object> buildContext(Xsjslib xsjslib) {
    Map<Object, Object> context = new HashMap<>();
    XsjslibSynchronizerRegistryEntity synchronizerTarget = new XsjslibSynchronizerRegistryEntity(
        IRepositoryStructure.PATH_REGISTRY_PUBLIC + xsjslib.getLocation(), repository);
    context.put("synchronizerTarget", synchronizerTarget);
    context.put("stateTableName", XSJSLIB_SYNCHRONIZER_STATE_TABLE_NAME);
    return context;
  }

}
