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
package com.codbex.kronos.hdbti.service;

import com.codbex.kronos.hdbti.processors.HDBTIProcessor;
import com.codbex.kronos.parser.hdbti.models.HDBTIImportConfigModel;

import io.swagger.annotations.*;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.eclipse.dirigible.commons.api.service.AbstractRestService;
import org.eclipse.dirigible.commons.api.service.IRestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * The Class HDBTIRestService.
 */
@Path("/parse")
@Api(value = "HDBTI Engine - HANA XS Classic", authorizations = {@Authorization(value = "basicAuth", scopes = {})})
@ApiResponses({@ApiResponse(code = 401, message = "Unauthorized"), @ApiResponse(code = 403, message = "Forbidden"),
        @ApiResponse(code = 404, message = "Not Found"), @ApiResponse(code = 500, message = "Internal Server Error")})
public class HDBTIRestService extends AbstractRestService implements IRestService {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(HDBTIRestService.class);

    /** The response. */
    @Context
    private HttpServletResponse response;

    /** The hdbti processor. */
    private final HDBTIProcessor hdbtiProcessor = new HDBTIProcessor();

    /**
     * Parses the hdbti to JSON.
     *
     * @param location the location
     * @param file the file
     * @return the response
     */
    @POST
    @Path("/hdbti")
    @ApiOperation("Parse HDBTI file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiResponses({@ApiResponse(code = 200, message = "HDBTI file was parsed")})
    public Response parseHdbtiToJSON(@ApiParam(value = "File registry path", required = true) @QueryParam("location") String location,
                                     @ApiParam(value = "The HDBTI file", required = true)
                                     @Multipart("file") byte[] file) {
        try {
            return Response.ok(hdbtiProcessor.parseHDBTIToJSON(location, file)).build();
        } catch (Throwable e) {
            String message = e.getMessage();
            logger.error(message, e);
            createErrorResponseInternalServerError(message);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
        }
    }

    /**
     * Parses the JSO nto hdbti.
     *
     * @param json the json
     * @return the response
     */
    @POST
    @Path("/csvim")
    @ApiOperation("Parse CSVIM file")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @ApiResponses({@ApiResponse(code = 200, message = "SCVIM file was parsed")})
    public Response parseJSONtoHdbti(ArrayList<HDBTIImportConfigModel> json) {
        try {
            return Response.ok(hdbtiProcessor.parseJSONtoHdbti(json)).build();
        } catch (Throwable e) {
            String message = e.getMessage();
            logger.error(message, e);
            createErrorResponseInternalServerError(message);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
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
        return HDBTIRestService.class;
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
