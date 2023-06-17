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
package com.codbex.kronos.engine.xsodata.synchronizer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.components.base.artefact.Artefact;
import org.eclipse.dirigible.components.base.artefact.ArtefactLifecycle;
import org.eclipse.dirigible.components.base.artefact.ArtefactPhase;
import org.eclipse.dirigible.components.base.artefact.ArtefactService;
import org.eclipse.dirigible.components.base.artefact.topology.TopologyWrapper;
import org.eclipse.dirigible.components.base.synchronizer.Synchronizer;
import org.eclipse.dirigible.components.base.synchronizer.SynchronizerCallback;
import org.eclipse.dirigible.components.odata.domain.OData;
import org.eclipse.dirigible.components.odata.domain.ODataContainer;
import org.eclipse.dirigible.components.odata.domain.ODataHandler;
import org.eclipse.dirigible.components.odata.domain.ODataMapping;
import org.eclipse.dirigible.components.odata.domain.ODataSchema;
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

import com.codbex.kronos.engine.xsodata.domain.XSOData;
import com.codbex.kronos.engine.xsodata.service.XSODataService;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.xsodata.ds.service.TableMetadataProvider;
import com.codbex.kronos.xsodata.ds.service.XSOData2ODataHTransformer;
import com.codbex.kronos.xsodata.ds.service.XSOData2ODataMTransformer;
import com.codbex.kronos.xsodata.ds.service.XSOData2ODataXTransformer;
import com.codbex.kronos.xsodata.ds.service.XSODataArtefactParser;
import com.codbex.kronos.xsodata.utils.ODataUtils;

/**
 * The Class XSODataSynchronizer.
 *
 * @param <A> the generic type
 */
@Component
@Order(550)
public class XSODataSynchronizer<A extends Artefact> implements Synchronizer<XSOData> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(XSODataSynchronizer.class);

    /** The Constant FILE_EXTENSION_LISTENER. */
    private static final String FILE_EXTENSION_XSODATA = ".xsodata";

    /** The callback. */
    private SynchronizerCallback callback;

    /** The XSOData service. */
    @Autowired
    private XSODataService odataService;
    
    /** The OData container service. */
    @Autowired
    private ODataContainerService odataContainerService;
    
    /** The OData handler service. */
    @Autowired
    private ODataHandlerService odataHandlerService;
    
    /** The OData mapping service. */
    @Autowired
    private ODataMappingService odataMappingService;
    
    /** The OData schema service. */
    @Autowired
    private ODataSchemaService odataSchemaService;

    /**
     * Checks if is accepted.
     *
     * @param file the file
     * @param attrs the attrs
     * @return true, if is accepted
     */
    @Override
    public boolean isAccepted(Path file, BasicFileAttributes attrs) {
        return file.toString().endsWith(FILE_EXTENSION_XSODATA);
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
    public List<XSOData> parse(String location, byte[] content) throws ParseException {
        try {
        	XSOData xsodata = new XSOData();
        	xsodata = parseOData(location, new String(content, StandardCharsets.UTF_8), xsodata);
        	XSOData maybe = getService().findByKey(xsodata.getKey());
			if (maybe != null) {
				xsodata.setId(maybe.getId());
			}
			getService().save(xsodata);
			return List.of(xsodata);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
            if (logger.isErrorEnabled()) {logger.error("odata: {}", location);}
            if (logger.isErrorEnabled()) {logger.error("content: {}", new String(content));}
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
				if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
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
	public void setStatus(Artefact artefact, ArtefactLifecycle lifecycle, String error) {
		artefact.setLifecycle(lifecycle);
		artefact.setError(error);
		getService().save((XSOData) artefact);
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
	public static XSOData parseOData(String location, String content, XSOData odata) throws IOException, SQLException, ArtifactParserException {
		XSODataArtefactParser.get().parseXSOData(location, content, odata);
        Configuration.configureObject(odata);
        odata.setLocation(location);
        odata.setType(XSOData.ARTEFACT_TYPE);
        odata.setNamespace(odata.getService().getNamespace());
        odata.setContent(content);
        odata.updateKey();
        odata.getAssociations().forEach(association -> {
			if (association.getFrom().getProperty() != null) {
				association.getFrom().getProperties().add(association.getFrom().getProperty());
			}
			if (association.getTo().getProperty() != null) {
				association.getTo().getProperties().add(association.getTo().getProperty());
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
    public ArtefactService<XSOData> getService() {
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
    public boolean complete(TopologyWrapper<Artefact> wrapper, ArtefactPhase flow) {
        
        try {
			XSOData odata = null;
			if (wrapper.getArtefact() instanceof XSOData) {
				odata = (XSOData) wrapper.getArtefact();
			} else {
				throw new UnsupportedOperationException(String.format("Trying to process %s as XSOData", wrapper.getArtefact().getClass()));
			}
			
			switch (flow) {
			case CREATE:
				if (odata.getLifecycle().equals(ArtefactLifecycle.NEW)) {
					generateOData(odata);
					callback.registerState(this, wrapper, ArtefactLifecycle.CREATED, "");
				}
				break;
			case UPDATE:
				if (odata.getLifecycle().equals(ArtefactLifecycle.MODIFIED)) {
					cleanupOData(odata);
					generateOData(odata);
					callback.registerState(this, wrapper, ArtefactLifecycle.UPDATED, "");
				}
				break;
			case DELETE:
				if (odata.getLifecycle().equals(ArtefactLifecycle.CREATED)
						|| odata.getLifecycle().equals(ArtefactLifecycle.UPDATED)) {
					cleanupOData(odata);
					callback.registerState(this, wrapper, ArtefactLifecycle.DELETED, "");
				}
				break;
			}
			return true;
		} catch (SQLException e) {
			if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
			callback.addError(e.getMessage());
			callback.registerState(this, wrapper, ArtefactLifecycle.FAILED, e.getMessage());
			return false;
		}
    }

	/**
	 * Generate O data.
	 *
	 * @param odata the odata
	 * @throws SQLException the SQL exception
	 */
	public void generateOData(XSOData model) throws SQLException {
		
		//The xs classic generate the odata properties without prettying them
        String oldValuePretty = Configuration.get(ODataDatabaseMetadataUtil.DIRIGIBLE_GENERATE_PRETTY_NAMES);
        Configuration.set(ODataDatabaseMetadataUtil.DIRIGIBLE_GENERATE_PRETTY_NAMES, "false");
        
        ODataUtils oDataUtils = new ODataUtils(new TableMetadataProvider());
        OData oDataDefinition = oDataUtils.convertODataModelToODataDefinition(model);
        
		// METADATA AND MAPPINGS GENERATION LOGIC
		String[] odataxc = generateODataSchema(oDataDefinition);
		String odatax = odataxc[0];
		String odatac = odataxc[1];
		ODataSchema odataSchema = new ODataSchema(oDataDefinition.getLocation(), oDataDefinition.getName(), null, null, odatax.getBytes());
		odataSchemaService.save(odataSchema);
		ODataContainer odataContainer = new ODataContainer(oDataDefinition.getLocation(), oDataDefinition.getName(), null, null, odatac.getBytes());
		odataContainerService.save(odataContainer);
		
		String[] odatams = generateODataMappings(oDataDefinition);
		int i=1;
		for (String odatam : odatams) {
			ODataMapping odataMapping = new ODataMapping(oDataDefinition.getLocation(), oDataDefinition.getName() + "#" + i++, null, null, odatam.getBytes());
			odataMappingService.save(odataMapping);
		}
		
		List<ODataHandler> odatahs = generateODataHandlers(oDataDefinition);
		for (ODataHandler odatah : odatahs) {
			ODataHandler odataHandler = new ODataHandler(oDataDefinition.getLocation(), odatah.getName() + "#" + i++, null, null, 
					odatah.getNamespace(), odatah.getMethod(), odatah.getKind(), odatah.getHandler());
			odataHandlerService.save(odataHandler);
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
    public void cleanup(XSOData odata) {
        try {
            odataSchemaService.removeSchema(odata.getLocation());
			odataContainerService.removeContainer(odata.getLocation());
			odataMappingService.removeMappings(odata.getLocation());
			odataHandlerService.removeHandlers(odata.getLocation());
			getService().delete(odata);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {logger.error(e.getMessage(), e);}
            callback.addError(e.getMessage());
            callback.registerState(this, odata, ArtefactLifecycle.DELETED, "");
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
	
	/** The odata to odata mappings transformer. */
	private XSOData2ODataMTransformer odata2ODataMTransformer = new XSOData2ODataMTransformer();

	/** The odata to odata schema transformer. */
	private XSOData2ODataXTransformer odata2ODataXTransformer = new XSOData2ODataXTransformer();
	
	/** The odata to odata handler transformer. */
	private XSOData2ODataHTransformer odata2ODataHTransformer = new XSOData2ODataHTransformer();
	
	/**
	 * Generate OData Schema.
	 *
	 * @param model the model
	 * @return the string[]
	 * @throws SQLException the SQL exception
	 */
	private String[] generateODataSchema(OData model) throws SQLException {
		return odata2ODataXTransformer.transform(model);
	}
	
	/**
	 * Generate OData Mappings.
	 *
	 * @param model the model
	 * @return the string[]
	 * @throws SQLException the SQL exception
	 */
	private String[] generateODataMappings(OData model) throws SQLException {
		return odata2ODataMTransformer.transform(model);
	}
	
	/**
	 * Generate OData Handlers.
	 *
	 * @param model the model
	 * @return the list
	 * @throws SQLException the SQL exception
	 */
	private List<ODataHandler> generateODataHandlers(OData model) throws SQLException {
		return odata2ODataHTransformer.transform(model);
	}
	
}
