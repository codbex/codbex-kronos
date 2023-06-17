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
import org.eclipse.dirigible.components.odata.api.ODataHandlerMethods;
import org.eclipse.dirigible.components.odata.api.ODataHandlerTypes;
import org.eclipse.dirigible.components.odata.domain.ODataHandler;
import org.eclipse.dirigible.components.odata.handler.ScriptingOData2EventHandler;
import org.eclipse.dirigible.components.odata.service.ODataHandlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class KronosOData2EventHandler.
 */
public class KronosOData2EventHandler extends ScriptingOData2EventHandler {

  /** The Constant LOGGER. */
  public static final Logger LOGGER = LoggerFactory.getLogger(KronosOData2EventHandler.class);

  /** The Constant ODATA2_EVENT_HANDLER_NAME. */
  private static final String ODATA2_EVENT_HANDLER_NAME = "kronos-odata-event-handler";

  /** The Constant HANDLER. */
  private static final String HANDLER = "handler";

  /**
	 * The Constant
	 * MORE_THAN_ONE_HANDLER_PRESENT_FOR_BEFORE_CREATE_ENTITY_EVENT_ERROR.
	 */
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_BEFORE_CREATE_ENTITY_EVENT_ERROR = "More than one handler present for before create entity event";
  
  /**
	 * The Constant
	 * MORE_THAN_ONE_HANDLER_PRESENT_FOR_AFTER_CREATE_ENTITY_EVENT_ERROR.
	 */
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_AFTER_CREATE_ENTITY_EVENT_ERROR = "More than one handler present for after create entity event";
  
  /**
	 * The Constant MORE_THAN_ONE_HANDLER_PRESENT_FOR_ON_CREATE_ENTITY_EVENT_ERROR.
	 */
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_ON_CREATE_ENTITY_EVENT_ERROR = "More than one handler present for on create entity event";
  
  /**
	 * The Constant
	 * MORE_THAN_ONE_HANDLER_PRESENT_FOR_BEFORE_UPDATE_ENTITY_EVENT_ERROR.
	 */
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_BEFORE_UPDATE_ENTITY_EVENT_ERROR = "More than one handler present for before update entity event";
  
  /**
	 * The Constant
	 * MORE_THAN_ONE_HANDLER_PRESENT_FOR_AFTER_UPDATE_ENTITY_EVENT_ERROR.
	 */
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_AFTER_UPDATE_ENTITY_EVENT_ERROR = "More than one handler present for after update entity event";
  
  /**
	 * The Constant MORE_THAN_ONE_HANDLER_PRESENT_FOR_ON_UPDATE_ENTITY_EVENT_ERROR.
	 */
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_ON_UPDATE_ENTITY_EVENT_ERROR = "More than one handler present for on update entity event";
  
  /**
	 * The Constant
	 * MORE_THAN_ONE_HANDLER_PRESENT_FOR_BEFORE_DELETE_ENTITY_EVENT_ERROR.
	 */
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_BEFORE_DELETE_ENTITY_EVENT_ERROR = "More than one handler present for before delete entity event";
  
  /**
	 * The Constant
	 * MORE_THAN_ONE_HANDLER_PRESENT_FOR_AFTER_DELETE_ENTITY_EVENT_ERROR.
	 */
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_AFTER_DELETE_ENTITY_EVENT_ERROR = "More than one handler present for after delete entity event";
  
  /**
	 * The Constant MORE_THAN_ONE_HANDLER_PRESENT_FOR_ON_DELETE_ENTITY_EVENT_ERROR.
	 */
  private static final String MORE_THAN_ONE_HANDLER_PRESENT_FOR_ON_DELETE_ENTITY_EVENT_ERROR = "More than one handler present for on delete entity event";

  /** The procedure handler. */
  private KronosProcedureOData2EventHandler procedureHandler;
  
  /** The scripting handler. */
  private KronosScriptingOData2EventHandler scriptingHandler;

  /**
	 * Instantiates a new kronos O data 2 event handler.
	 */
  public KronosOData2EventHandler() {
    this(new KronosProcedureOData2EventHandler(), new KronosScriptingOData2EventHandler());
  }

  /**
	 * Instantiates a new kronos O data 2 event handler.
	 *
	 * @param procedureHandler the procedure handler
	 * @param scriptingHandler the scripting handler
	 */
  public KronosOData2EventHandler(KronosProcedureOData2EventHandler procedureHandler,
      KronosScriptingOData2EventHandler scriptingHandler) {
    this.procedureHandler = procedureHandler;
    this.scriptingHandler = scriptingHandler;
  }

  /**
	 * Before create entity.
	 *
	 * @param uriInfo            the uri info
	 * @param requestContentType the request content type
	 * @param contentType        the content type
	 * @param entry              the entry
	 * @param context            the context
	 * @return the o data response
	 * @throws ODataException the o data exception
	 */
  @Override
  public ODataResponse beforeCreateEntity(PostUriInfo uriInfo, String requestContentType, String contentType, ODataEntry entry,
      Map<Object, Object> context)
      throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.create.name();
      String type = ODataHandlerTypes.before.name();
      List<ODataHandler> handlers = ODataHandlerService.get().getByNamespaceNameMethodAndKind(namespace, name, method, type);
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

  /**
	 * After create entity.
	 *
	 * @param uriInfo            the uri info
	 * @param requestContentType the request content type
	 * @param contentType        the content type
	 * @param entry              the entry
	 * @param context            the context
	 * @return the o data response
	 * @throws ODataException the o data exception
	 */
  @Override
  public ODataResponse afterCreateEntity(PostUriInfo uriInfo, String requestContentType, String contentType, ODataEntry entry,
      Map<Object, Object> context)
      throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.create.name();
      String type = ODataHandlerTypes.after.name();
      List<ODataHandler> handlers = ODataHandlerService.get().getByNamespaceNameMethodAndKind(namespace, name, method, type);
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

  /**
	 * On create entity.
	 *
	 * @param uriInfo            the uri info
	 * @param content            the content
	 * @param requestContentType the request content type
	 * @param contentType        the content type
	 * @param context            the context
	 * @return the o data response
	 * @throws ODataException the o data exception
	 */
  @Override
  public ODataResponse onCreateEntity(PostUriInfo uriInfo, InputStream content, String requestContentType, String contentType,
      Map<Object, Object> context)
      throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.create.name();
      String type = ODataHandlerTypes.on.name();
      List<ODataHandler> handlers = ODataHandlerService.get().getByNamespaceNameMethodAndKind(namespace, name, method, type);
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

  /**
	 * Before update entity.
	 *
	 * @param uriInfo            the uri info
	 * @param requestContentType the request content type
	 * @param merge              the merge
	 * @param contentType        the content type
	 * @param entry              the entry
	 * @param context            the context
	 * @return the o data response
	 * @throws ODataException the o data exception
	 */
  @Override
  public ODataResponse beforeUpdateEntity(PutMergePatchUriInfo uriInfo, String requestContentType, boolean merge, String contentType,
      ODataEntry entry, Map<Object, Object> context) throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.update.name();
      String type = ODataHandlerTypes.before.name();
      List<ODataHandler> handlers = ODataHandlerService.get().getByNamespaceNameMethodAndKind(namespace, name, method, type);
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

  /**
	 * After update entity.
	 *
	 * @param uriInfo            the uri info
	 * @param requestContentType the request content type
	 * @param merge              the merge
	 * @param contentType        the content type
	 * @param entry              the entry
	 * @param context            the context
	 * @return the o data response
	 * @throws ODataException the o data exception
	 */
  @Override
  public ODataResponse afterUpdateEntity(PutMergePatchUriInfo uriInfo, String requestContentType, boolean merge, String contentType,
      ODataEntry entry, Map<Object, Object> context) throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.update.name();
      String type = ODataHandlerTypes.after.name();
      List<ODataHandler> handlers = ODataHandlerService.get().getByNamespaceNameMethodAndKind(namespace, name, method, type);
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

  /**
	 * On update entity.
	 *
	 * @param uriInfo            the uri info
	 * @param content            the content
	 * @param requestContentType the request content type
	 * @param merge              the merge
	 * @param contentType        the content type
	 * @param context            the context
	 * @return the o data response
	 * @throws ODataException the o data exception
	 */
  @Override
  public ODataResponse onUpdateEntity(PutMergePatchUriInfo uriInfo, InputStream content, String requestContentType, boolean merge,
      String contentType, Map<Object, Object> context) throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.update.name();
      String type = ODataHandlerTypes.on.name();
      List<ODataHandler> handlers = ODataHandlerService.get().getByNamespaceNameMethodAndKind(namespace, name, method, type);
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

  /**
	 * Before delete entity.
	 *
	 * @param uriInfo     the uri info
	 * @param contentType the content type
	 * @param context     the context
	 * @return the o data response
	 * @throws ODataException the o data exception
	 */
  @Override
  public ODataResponse beforeDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.delete.name();
      String type = ODataHandlerTypes.before.name();
      List<ODataHandler> handlers = ODataHandlerService.get().getByNamespaceNameMethodAndKind(namespace, name, method, type);
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

  /**
	 * After delete entity.
	 *
	 * @param uriInfo     the uri info
	 * @param contentType the content type
	 * @param context     the context
	 * @return the o data response
	 * @throws ODataException the o data exception
	 */
  @Override
  public ODataResponse afterDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.delete.name();
      String type = ODataHandlerTypes.after.name();
      List<ODataHandler> handlers = ODataHandlerService.get().getByNamespaceNameMethodAndKind(namespace, name, method, type);
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

  /**
	 * On delete entity.
	 *
	 * @param uriInfo     the uri info
	 * @param contentType the content type
	 * @param context     the context
	 * @return the o data response
	 * @throws ODataException the o data exception
	 */
  @Override
  public ODataResponse onDeleteEntity(DeleteUriInfo uriInfo, String contentType, Map<Object, Object> context) throws ODataException {
    try {
      String namespace = uriInfo.getTargetType().getNamespace();
      String name = uriInfo.getTargetType().getName();
      String method = ODataHandlerMethods.delete.name();
      String type = ODataHandlerTypes.on.name();
      List<ODataHandler> handlers = ODataHandlerService.get().getByNamespaceNameMethodAndKind(namespace, name, method, type);
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

  /**
	 * Gets the name.
	 *
	 * @return the name
	 */
  @Override
  public String getName() {
    return ODATA2_EVENT_HANDLER_NAME;
  }

  /**
	 * Determine event handler.
	 *
	 * @param handler the handler
	 * @return the abstract kronos O data 2 event handler
	 */
  private AbstractKronosOData2EventHandler determineEventHandler(ODataHandler handler) {
    if (handler.getHandler().contains(".xsjslib::")) {
      return scriptingHandler;
    } else {
      return procedureHandler;
    }
  }
}