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
package com.codbex.kronos.engine;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.eclipse.dirigible.commons.api.context.ContextException;
import org.eclipse.dirigible.commons.api.context.ThreadContextFacade;
import org.eclipse.dirigible.commons.api.service.AbstractRestService;
import org.eclipse.dirigible.commons.api.service.IRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class JavascriptEngineRestService.
 */
@Path("/xsjs")
@Api(value = "JavaScript Engine - HANA XS Classic", authorizations = {@Authorization(value = "basicAuth", scopes = {})})
@ApiResponses({@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
    @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Internal Server Error")})
public class JavascriptEngineRestService extends AbstractRestService {

  /** The logger. */
  private Logger logger = LoggerFactory.getLogger(JavascriptEngineRestService.class);

  /** The processor. */
  private JavascriptEngineProcessor processor;

  /**
   * Instantiates a new javascript engine rest service.
   */
  public JavascriptEngineRestService() {
    this.processor = new JavascriptEngineProcessor();
  }

  /**
   * Instantiates a new javascript engine rest service.
   *
   * @param processor the processor
   */
  public JavascriptEngineRestService(JavascriptEngineProcessor processor) {
    this.processor = processor;
  }

  /**
   * Gets the.
   *
   * @param httpServletRequest the http servlet request
   * @param path the path
   * @return the response
   * @throws ContextException the context exception
   */
  @GET
  @Path("/{path:.*}")
  @ApiOperation("Execute Server Side JavaScript HANA XS Classic Resource")
  @ApiResponses({@ApiResponse(code = 200, message = "Execution Result")})
  public Response get(
      @Context HttpServletRequest httpServletRequest,
      @PathParam("path") String path
  ) throws ContextException {
    return executeJS(httpServletRequest, path);
  }

  /**
   * Post.
   *
   * @param httpServletRequest the http servlet request
   * @param path the path
   * @return the response
   * @throws ContextException the context exception
   */
  @POST
  @Path("/{path:.*}")
  @ApiOperation("Execute Server Side JavaScript HANA XS Classic Resource")
  @ApiResponses({@ApiResponse(code = 200, message = "Execution Result")})
  public Response post(
      @Context HttpServletRequest httpServletRequest,
      @PathParam("path") String path
  ) throws ContextException {
    return executeJS(httpServletRequest, path);
  }

  /**
   * Put.
   *
   * @param httpServletRequest the http servlet request
   * @param path the path
   * @return the response
   * @throws ContextException the context exception
   */
  @PUT
  @Path("/{path:.*}")
  @ApiOperation("Execute Server Side JavaScript HANA XS Classic Resource")
  @ApiResponses({@ApiResponse(code = 200, message = "Execution Result")})
  public Response put(
      @Context HttpServletRequest httpServletRequest,
      @PathParam("path") String path
  ) throws ContextException {
    return executeJS(httpServletRequest, path);
  }

  /**
   * Delete.
   *
   * @param httpServletRequest the http servlet request
   * @param path the path
   * @return the response
   * @throws ContextException the context exception
   */
  @DELETE
  @Path("/{path:.*}")
  @ApiOperation("Execute Server Side JavaScript HANA XS Classic Resource")
  @ApiResponses({@ApiResponse(code = 200, message = "Execution Result")})
  public Response delete(
      @Context HttpServletRequest httpServletRequest,
      @PathParam("path") String path
  ) throws ContextException {
    return executeJS(httpServletRequest, path);
  }

  /**
   * Head.
   *
   * @param httpServletRequest the http servlet request
   * @param path the path
   * @return the response
   * @throws ContextException the context exception
   */
  @HEAD
  @Path("/{path:.*}")
  @ApiOperation("Execute Server Side JavaScript HANA XS Classic Resource")
  @ApiResponses({@ApiResponse(code = 200, message = "Execution Result")})
  public Response head(
      @Context HttpServletRequest httpServletRequest,
      @PathParam("path") String path
  ) throws ContextException {
    return executeJS(httpServletRequest, path);
  }

  /**
   * Patch.
   *
   * @param httpServletRequest the http servlet request
   * @param path the path
   * @return the response
   * @throws ContextException the context exception
   */
  @PATCH
  @Path("/{path:.*}")
  @ApiOperation("Execute Server Side JavaScript HANA XS Classic Resource")
  @ApiResponses({@ApiResponse(code = 200, message = "Execution Result")})
  public Response patch(
      @Context HttpServletRequest httpServletRequest,
      @PathParam("path") String path
  ) throws ContextException {
    return executeJS(httpServletRequest, path);
  }

  /**
   * Execute JS.
   *
   * @param httpServletRequest the http servlet request
   * @param path the path
   * @return the response
   * @throws ContextException the context exception
   */
  private Response executeJS(HttpServletRequest httpServletRequest, String path) throws ContextException {
    ThreadContextFacade.setUp();
    try {
      ThreadContextFacade.set(HttpServletRequest.class.getCanonicalName(), httpServletRequest);
      processor.executeService(path);
      return Response.ok().build();
    } finally {
      ThreadContextFacade.tearDown();
    }
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.commons.api.service.IRestService#getType()
   */
  @Override
  public Class<? extends IRestService> getType() {
    return JavascriptEngineRestService.class;
  }

  /**
   * Gets the logger.
   *
   * @return the logger
   */
  /*
   * (non-Javadoc)
   * @see org.eclipse.dirigible.commons.api.service.AbstractRestService#getLogger()
   */
  @Override
  protected Logger getLogger() {
    return logger;
  }
}
