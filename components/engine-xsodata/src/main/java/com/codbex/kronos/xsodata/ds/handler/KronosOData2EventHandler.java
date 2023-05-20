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
package com.codbex.kronos.xsodata.ds.handler;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.info.DeleteUriInfo;
import org.apache.olingo.odata2.api.uri.info.PostUriInfo;
import org.apache.olingo.odata2.api.uri.info.PutMergePatchUriInfo;
import org.eclipse.dirigible.engine.odata2.definition.ODataHandlerDefinition;
import org.eclipse.dirigible.engine.odata2.definition.ODataHandlerMethods;
import org.eclipse.dirigible.engine.odata2.definition.ODataHandlerTypes;
import org.eclipse.dirigible.engine.odata2.handler.ScriptingOData2EventHandler;
import org.eclipse.dirigible.engine.odata2.service.ODataCoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KronosOData2EventHandler extends ScriptingOData2EventHandler {

  public static final Logger LOGGER = LoggerFactory.getLogger(KronosOData2EventHandler.class);

  private static final String ODATA2_EVENT_HANDLER_NAME = "kronos-odata-event-handler";

  private static final String HANDLER = "handler";

  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_BEFORE_CREATE_ENTITY_EVENT_ERROR = "More than one handler present for before create entity event";
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_AFTER_CREATE_ENTITY_EVENT_ERROR = "More than one handler present for after create entity event";
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_ON_CREATE_ENTITY_EVENT_ERROR = "More than one handler present for on create entity event";
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_BEFORE_UPDATE_ENTITY_EVENT_ERROR = "More than one handler present for before update entity event";
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_AFTER_UPDATE_ENTITY_EVENT_ERROR = "More than one handler present for after update entity event";
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_ON_UPDATE_ENTITY_EVENT_ERROR = "More than one handler present for on update entity event";
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_BEFORE_DELETE_ENTITY_EVENT_ERROR = "More than one handler present for before delete entity event";
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_AFTER_DELETE_ENTITY_EVENT_ERROR = "More than one handler present for after delete entity event";
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_ON_DELETE_ENTITY_EVENT_ERROR = "More than one handler present for on delete entity event";

  private ODataCoreService odataCoreService;
  private KronosProcedureOData2EventHandler procedureHandler;
  private KronosScriptingOData2EventHandler scriptingHandler;

  public KronosOData2EventHandler() {
    this(new ODataCoreService(), new KronosProcedureOData2EventHandler(), new KronosScriptingOData2EventHandler());
  }

  public KronosOData2EventHandler(ODataCoreService odataCoreService, KronosProcedureOData2EventHandler procedureHandler,
      KronosScriptingOData2EventHandler scriptingHandler) {
    this.odataCoreService = odataCoreService;
    this.procedureHandler = procedureHandler;
    this.scriptingHandler = scriptingHandler;
  }

  @Override
  public ODataResponse beforeCreateEntity(PostUriInfo uriInfo, String requestContentType, String contentType, ODataEntry entry,
      Map<Object, Object> context)
      throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.create.name();
      String type = ODataHandlerTypes.before.name();
      List<ODataHandlerDefinition> handlers = odataCoreService.getHandlers(namespace, name, method, type);
      if (!handlers.isEmpty()) {
        AbstractKronosOData2EventHandler eventHandler = determineEventHandler(handlers.get(0));
        context.put(HANDLER, handlers.get(0));
        return eventHandler.beforeCreateEntity(uriInfo, requestContentType, contentType, entry, context);
      } else {
        throw new IllegalStateException(MORE_THAN_ONE_HANDLER_PRESENT_FOR_BEFORE_CREATE_ENTITY_EVENT_ERROR);
      }
    } catch (EdmException e) {
      throw new ODataException(e);
    }
  }

  @Override
  public ODataResponse afterCreateEntity(PostUriInfo uriInfo, String requestContentType, String contentType, ODataEntry entry,
      Map<Object, Object> context)
      throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.create.name();
      String type = ODataHandlerTypes.after.name();
      List<ODataHandlerDefinition> handlers = odataCoreService.getHandlers(namespace, name, method, type);
      if (!handlers.isEmpty()) {
        AbstractKronosOData2EventHandler eventHandler = determineEventHandler(handlers.get(0));
        context.put(HANDLER, handlers.get(0));
        return eventHandler.afterCreateEntity(uriInfo, requestContentType, contentType, entry, context);
      } else {
        throw new IllegalStateException(MORE_THAN_ONE_HANDLER_PRESENT_FOR_AFTER_CREATE_ENTITY_EVENT_ERROR);
      }
    } catch (EdmException e) {
      throw new ODataException(e);
    }
  }

  @Override
  public ODataResponse onCreateEntity(PostUriInfo uriInfo, InputStream content, String requestContentType, String contentType,
      Map<Object, Object> context)
      throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.create.name();
      String type = ODataHandlerTypes.on.name();
      List<ODataHandlerDefinition> handlers = odataCoreService.getHandlers(namespace, name, method, type);
      if (!handlers.isEmpty()) {
        AbstractKronosOData2EventHandler eventHandler = determineEventHandler(handlers.get(0));
        context.put(HANDLER, handlers.get(0));
        return eventHandler.onCreateEntity(uriInfo, content, requestContentType, contentType, context);
      } else {
        throw new IllegalStateException(MORE_THAN_ONE_HANDLER_PRESENT_FOR_ON_CREATE_ENTITY_EVENT_ERROR);
      }
    } catch (EdmException e) {
      throw new ODataException(e);
    }
  }

  @Override
  public ODataResponse beforeUpdateEntity(PutMergePatchUriInfo uriInfo, String requestContentType, boolean merge, String contentType,
      ODataEntry entry, Map<Object, Object> context) throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.update.name();
      String type = ODataHandlerTypes.before.name();
      List<ODataHandlerDefinition> handlers = odataCoreService.getHandlers(namespace, name, method, type);
      if (!handlers.isEmpty()) {
        AbstractKronosOData2EventHandler eventHandler = determineEventHandler(handlers.get(0));
        context.put(HANDLER, handlers.get(0));
        return eventHandler.beforeUpdateEntity(uriInfo, requestContentType, merge, contentType, entry, context);
      } else {
        throw new IllegalStateException(MORE_THAN_ONE_HANDLER_PRESENT_FOR_BEFORE_UPDATE_ENTITY_EVENT_ERROR);
      }
    } catch (EdmException e) {
      throw new ODataException(e);
    }
  }

  @Override
  public ODataResponse afterUpdateEntity(PutMergePatchUriInfo uriInfo, String requestContentType, boolean merge, String contentType,
      ODataEntry entry, Map<Object, Object> context) throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.update.name();
      String type = ODataHandlerTypes.after.name();
      List<ODataHandlerDefinition> handlers = odataCoreService.getHandlers(namespace, name, method, type);
      if (!handlers.isEmpty()) {
        AbstractKronosOData2EventHandler eventHandler = determineEventHandler(handlers.get(0));
        context.put(HANDLER, handlers.get(0));
        return eventHandler.afterUpdateEntity(uriInfo, requestContentType, merge, contentType, entry, context);
      } else {
        throw new IllegalStateException(MORE_THAN_ONE_HANDLER_PRESENT_FOR_AFTER_UPDATE_ENTITY_EVENT_ERROR);
      }
    } catch (EdmException e) {
      throw new ODataException(e);
    }
  }

  @Override
  public ODataResponse onUpdateEntity(PutMergePatchUriInfo uriInfo, InputStream content, String requestContentType, boolean merge,
      String contentType, Map<Object, Object> context) throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.update.name();
      String type = ODataHandlerTypes.on.name();
      List<ODataHandlerDefinition> handlers = odataCoreService.getHandlers(namespace, name, method, type);
      if (!handlers.isEmpty()) {
        AbstractKronosOData2EventHandler eventHandler = determineEventHandler(handlers.get(0));
        context.put(HANDLER, handlers.get(0));
        return eventHandler.onUpdateEntity(uriInfo, content, requestContentType, merge, contentType, context);
      } else {
        throw new IllegalStateException(MORE_THAN_ONE_HANDLER_PRESENT_FOR_ON_UPDATE_ENTITY_EVENT_ERROR);
      }
    } catch (EdmException e) {
      throw new ODataException(e);
    }
  }

  @Override
  public ODataResponse beforeDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.delete.name();
      String type = ODataHandlerTypes.before.name();
      List<ODataHandlerDefinition> handlers = odataCoreService.getHandlers(namespace, name, method, type);
      if (!handlers.isEmpty()) {
        AbstractKronosOData2EventHandler eventHandler = determineEventHandler(handlers.get(0));
        context.put(HANDLER, handlers.get(0));
        return eventHandler.beforeDeleteEntity(uriInfo, contentType, context);
      } else {
        throw new IllegalStateException(MORE_THAN_ONE_HANDLER_PRESENT_FOR_BEFORE_DELETE_ENTITY_EVENT_ERROR);
      }
    } catch (EdmException e) {
      throw new ODataException(e);
    }
  }

  @Override
  public ODataResponse afterDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.delete.name();
      String type = ODataHandlerTypes.after.name();
      List<ODataHandlerDefinition> handlers = odataCoreService.getHandlers(namespace, name, method, type);
      if (!handlers.isEmpty()) {
        AbstractKronosOData2EventHandler eventHandler = determineEventHandler(handlers.get(0));
        context.put(HANDLER, handlers.get(0));
        return eventHandler.afterDeleteEntity(uriInfo, contentType, context);
      } else {
        throw new IllegalStateException(MORE_THAN_ONE_HANDLER_PRESENT_FOR_AFTER_DELETE_ENTITY_EVENT_ERROR);
      }
    } catch (EdmException e) {
      throw new ODataException(e);
    }
  }

  @Override
  public ODataResponse onDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.delete.name();
      String type = ODataHandlerTypes.on.name();
      List<ODataHandlerDefinition> handlers = odataCoreService.getHandlers(namespace, name, method, type);
      if (!handlers.isEmpty()) {
        AbstractKronosOData2EventHandler eventHandler = determineEventHandler(handlers.get(0));
        context.put(HANDLER, handlers.get(0));
        return eventHandler.onDeleteEntity(uriInfo, contentType, context);
      } else {
        throw new IllegalStateException(MORE_THAN_ONE_HANDLER_PRESENT_FOR_ON_DELETE_ENTITY_EVENT_ERROR);
      }
    } catch (EdmException e) {
      throw new ODataException(e);
    }
  }

  @Override
  public String getName() {
    return ODATA2_EVENT_HANDLER_NAME;
  }

  private AbstractKronosOData2EventHandler determineEventHandler(ODataHandlerDefinition handler) {
    if (handler.getHandler().contains(".xsjslib::")) {
      return scriptingHandler;
    } else {
      return procedureHandler;
    }
  }
}