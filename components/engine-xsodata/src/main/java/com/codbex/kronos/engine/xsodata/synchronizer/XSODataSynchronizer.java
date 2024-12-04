/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.xsodata.synchronizer;

import com.codbex.kronos.commons.StringUtils;
import com.codbex.kronos.engine.xsodata.domain.XSOData;
import com.codbex.kronos.engine.xsodata.service.XSODataService;
import com.codbex.kronos.engine.xsodata.transformers.*;
import com.codbex.kronos.engine.xsodata.utils.ODataUtils;
import com.codbex.kronos.exceptions.ArtifactParserException;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.components.api.platform.ProblemsFacade;
import org.eclipse.dirigible.components.base.artefact.ArtefactLifecycle;
import org.eclipse.dirigible.components.base.artefact.ArtefactPhase;
import org.eclipse.dirigible.components.base.artefact.topology.TopologyWrapper;
import org.eclipse.dirigible.components.base.synchronizer.BaseSynchronizer;
import org.eclipse.dirigible.components.base.synchronizer.SynchronizerCallback;
import org.eclipse.dirigible.components.odata.domain.*;
import org.eclipse.dirigible.components.odata.service.ODataContainerService;
import org.eclipse.dirigible.components.odata.service.ODataHandlerService;
import org.eclipse.dirigible.components.odata.service.ODataMappingService;
import org.eclipse.dirigible.components.odata.service.ODataSchemaService;
import org.eclipse.dirigible.components.odata.transformers.ODataDatabaseMetadataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * The Class XSODataSynchronizer.
 */
@Component
@Order(550)
public class XSODataSynchronizer extends BaseSynchronizer<XSOData, Long> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(XSODataSynchronizer.class);

    /**
     * The Constant FILE_EXTENSION_LISTENER.
     */
    private static final String FILE_EXTENSION_XSODATA = ".xsodata";

    /**
     * The callback.
     */
    private SynchronizerCallback callback;

    /**
     * The XSOData service.
     */
    @Autowired
    private XSODataService odataService;

    /**
     * The OData container service.
     */
    @Autowired
    private ODataContainerService odataContainerService;

    /**
     * The OData handler service.
     */
    @Autowired
    private ODataHandlerService odataHandlerService;

    /**
     * The OData mapping service.
     */
    @Autowired
    private ODataMappingService odataMappingService;

    /**
     * The OData schema service.
     */
    @Autowired
    private ODataSchemaService odataSchemaService;

    /**
     * The odata to odata mappings transformer.
     */
    private final XSOData2ODataMTransformer odata2ODataMTransformer = new XSOData2ODataMTransformer();

    /**
     * The odata to odata schema transformer.
     */
    private final XSOData2ODataXTransformer odata2ODataXTransformer = new XSOData2ODataXTransformer();

    /**
     * The odata to odata handler transformer.
     */
    private final XSOData2ODataHTransformer odata2ODataHTransformer = new XSOData2ODataHTransformer();

    /**
     * Checks if is accepted.
     *
     * @param file the file
     * @param attrs the attrs
     * @return true, if is accepted
     */
    @Override
    public boolean isAccepted(Path file, BasicFileAttributes attrs) {
        return file.toString()
                   .endsWith(FILE_EXTENSION_XSODATA);
    }

    /**
     * Checks if is accepted.
     *
     * @param type the type
     * @return true, if is accepted
     */
    @Override
    public boolean isAccepted(String type) {
        return XSOData.ARTEFACT_TYPE.equals(type);
    }

    /**
     * Load.
     *
     * @param location the location
     * @param content the content
     * @return the list
     * @throws ParseException
     */
    @Override
    protected List<XSOData> parseImpl(String location, byte[] content) throws ParseException {
        try {
            XSOData xsodata = new XSOData();
            xsodata = parseOData(location, StringUtils.toString(content), xsodata);
            XSOData maybe = getService().findByKey(xsodata.getKey());
            if (maybe != null) {
                xsodata.setId(maybe.getId());
            }
            getService().save(xsodata);
            return List.of(xsodata);
        } catch (Exception e) {
            logger.error("Failed to parse [{}]. Content [{}]", location, StringUtils.toString(content), e);
            throw new ParseException(e.getMessage(), 0);
        }
    }

    /**
     * Retrieve.
     *
     * @param location the location
     * @return the list
     */
    @Override
    public List<XSOData> retrieve(String location) {
        List<XSOData> list = getService().getAll();
        for (XSOData xsodata : list) {
            try {
                parseOData(location, xsodata.getContent(), xsodata);
            } catch (IOException | SQLException | ArtifactParserException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return list;
    }

    /**
     * Sets the status.
     *
     * @param artefact the artefact
     * @param lifecycle the lifecycle
     * @param error the error
     */
    @Override
    public void setStatus(XSOData artefact, ArtefactLifecycle lifecycle, String error) {
        artefact.setLifecycle(lifecycle);
        artefact.setError(error);
        getService().save(artefact);
    }

    /**
     * Parses the O data.
     *
     * @param location the location
     * @param content the content
     * @return the o data
     * @throws ArtifactParserException
     * @throws SQLException
     * @throws IOException
     */
    public static XSOData parseOData(String location, String content, XSOData odata)
            throws IOException, SQLException, ArtifactParserException {
        XSODataArtefactParser.get()
                             .parseXSOData(location, content, odata);
        Configuration.configureObject(odata);
        odata.setLocation(location);
        odata.setType(XSOData.ARTEFACT_TYPE);
        odata.setNamespace(odata.getService()
                                .getNamespace());
        odata.setContent(content);
        odata.updateKey();
        odata.getAssociations()
             .forEach(association -> {
                 if (association.getFrom()
                                .getProperty() != null) {
                     association.getFrom()
                                .getProperties()
                                .add(association.getFrom()
                                                .getProperty());
                 }
                 if (association.getTo()
                                .getProperty() != null) {
                     association.getTo()
                                .getProperties()
                                .add(association.getTo()
                                                .getProperty());
                 }
             });
        return odata;
    }

    /**
     * Gets the service.
     *
     * @return the service
     */
    @Override
    public XSODataService getService() {
        return odataService;
    }

    /**
     * Complete.
     *
     * @param wrapper the wrapper
     * @param flow the flow
     * @return true, if successful
     */
    @Override
    protected boolean completeImpl(TopologyWrapper<XSOData> wrapper, ArtefactPhase flow) {

        try {
            XSOData odata = wrapper.getArtefact();
            switch (flow) {
                case CREATE:
                    if (odata.getLifecycle()
                             .equals(ArtefactLifecycle.NEW)) {
                        generateOData(odata);
                        callback.registerState(this, wrapper, ArtefactLifecycle.CREATED);
                    } else if (odata.getLifecycle()
                                    .equals(ArtefactLifecycle.FAILED)) {
                        cleanupOData(odata);
                        generateOData(odata);
                        callback.registerState(this, wrapper, ArtefactLifecycle.CREATED);
                        ProblemsFacade.deleteArtefactSynchronizationProblem(wrapper.getArtefact());
                    }
                    break;
                case UPDATE:
                    if (odata.getLifecycle()
                             .equals(ArtefactLifecycle.MODIFIED)) {
                        cleanupOData(odata);
                        generateOData(odata);
                        callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED);
                        ProblemsFacade.deleteArtefactSynchronizationProblem(odata);
                    }
                    break;
                case DELETE:
                    if (odata.getLifecycle()
                             .equals(ArtefactLifecycle.CREATED)
                            || odata.getLifecycle()
                                    .equals(ArtefactLifecycle.UPDATED)) {
                        cleanupOData(odata);
                        callback.registerState(this, wrapper, ArtefactLifecycle.DELETED);
                    }
                    break;
            }
            return true;
        } catch (Exception e) {
            String errorMessage = String.format("Error occurred while processing [%s]: %s", wrapper.getArtefact()
                                                                                                   .getLocation(),
                    e.getMessage());
            callback.addError(errorMessage);
            callback.registerState(this, wrapper, ArtefactLifecycle.FAILED, errorMessage, e);
            ProblemsFacade.upsertArtefactSynchronizationProblem(wrapper.getArtefact(), errorMessage);
            return false;
        }
    }

    /**
     * Generate O data.
     *
     * @param model the model
     * @throws SQLException the SQL exception
     */
    public void generateOData(XSOData model) throws SQLException {
        XSODataArtefactParser.get()
                             .checkArtefact(model);
        // The xs classic generate the odata properties without prettying them
        String oldValuePretty = Configuration.get(ODataDatabaseMetadataUtil.DIRIGIBLE_GENERATE_PRETTY_NAMES);
        Configuration.set(ODataDatabaseMetadataUtil.DIRIGIBLE_GENERATE_PRETTY_NAMES, "false");

        ODataUtils oDataUtils = new ODataUtils(new TableMetadataProvider());
        OData oDataDefinition = oDataUtils.convertODataModelToODataDefinition(model);

        // METADATA AND MAPPINGS GENERATION LOGIC
        String[] odataxc = odata2ODataXTransformer.transform(oDataDefinition);
        String odatax = odataxc[0];
        String odatac = odataxc[1];
        ODataSchema odataSchema = new ODataSchema(oDataDefinition.getLocation(), oDataDefinition.getName(), null, null, odatax.getBytes());
        odataSchemaService.save(odataSchema);
        ODataContainer odataContainer =
                new ODataContainer(oDataDefinition.getLocation(), oDataDefinition.getName(), null, null, odatac.getBytes());
        odataContainerService.save(odataContainer);

        String[] odataMappings = odata2ODataMTransformer.transform(oDataDefinition);
        int i = 1;
        for (String mapping : odataMappings) {
            ODataMapping odataMapping =
                    new ODataMapping(oDataDefinition.getLocation(), oDataDefinition.getName() + "#" + i++, null, null, mapping.getBytes());
            odataMappingService.save(odataMapping);
        }

        List<ODataHandler> oDataHandlers = odata2ODataHTransformer.transform(oDataDefinition);
        for (ODataHandler handler : oDataHandlers) {

            /*
             * Note: the "forbid" option is also treated by the parser as "handler" -> null
             *
             * "KRONOS"."com.codbex.kronos.model::test.Entity1" as "Entity1" create forbidden update forbidden
             * delete forbidden;
             */
            if (handler.getHandler() != null) {
                ODataHandler odataHandler = new ODataHandler(oDataDefinition.getLocation(), handler.getName() + "#" + i++, null, null,
                        handler.getNamespace(), handler.getMethod(), handler.getKind(), handler.getHandler());
                odataHandlerService.save(odataHandler);
            }
        }

        if (oldValuePretty != null) {
            Configuration.set(ODataDatabaseMetadataUtil.DIRIGIBLE_GENERATE_PRETTY_NAMES, oldValuePretty);
        }
    }

    /**
     * Cleanup O data.
     *
     * @param odata the odata
     */
    public void cleanupOData(XSOData odata) {
        // CLEAN UP LOGIC
        odataSchemaService.removeSchema(odata.getLocation());
        odataContainerService.removeContainer(odata.getLocation());
        odataMappingService.removeMappings(odata.getLocation());
        odataHandlerService.removeHandlers(odata.getLocation());
    }

    /**
     * Cleanup.
     *
     * @param odata the OData
     */
    @Override
    protected void cleanupImpl(XSOData odata) {
        try {
            odataSchemaService.removeSchema(odata.getLocation());
            odataContainerService.removeContainer(odata.getLocation());
            odataMappingService.removeMappings(odata.getLocation());
            odataHandlerService.removeHandlers(odata.getLocation());
            getService().delete(odata);
            callback.registerState(this, odata, ArtefactLifecycle.DELETED);
        } catch (Exception e) {
            callback.addError(e.getMessage());
            callback.registerState(this, odata, ArtefactLifecycle.FAILED, e);
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
     * Gets the file extension.
     *
     * @return the file extension
     */
    @Override
    public String getFileExtension() {
        return FILE_EXTENSION_XSODATA;
    }

    /**
     * Gets the artefact type.
     *
     * @return the artefact type
     */
    @Override
    public String getArtefactType() {
        return XSOData.ARTEFACT_TYPE;
    }

}
