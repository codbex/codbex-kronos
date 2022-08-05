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
package com.codbex.kronos.hdb.ds.facade;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.IEnvironmentVariables;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.artefacts.HDBProcedureSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBScalarFunctionSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBSchemaSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBSequenceSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBSynonymSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableFunctionSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableTypeSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBViewSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbdd.DataStructureCdsModel;
import com.codbex.kronos.hdb.ds.model.hdbprocedure.DataStructureHDBProcedureModel;
import com.codbex.kronos.hdb.ds.model.hdbschema.DataStructureHDBSchemaModel;
import com.codbex.kronos.hdb.ds.model.hdbsequence.DataStructureHDBSequenceModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.DataStructureHDBSynonymModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.DataStructureHDBTableFunctionModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.DataStructureHDBTableTypeModel;
import com.codbex.kronos.hdb.ds.model.hdbview.DataStructureHDBViewModel;
import com.codbex.kronos.hdb.ds.module.HDBModule;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.service.manager.IDataStructureManager;
import com.codbex.kronos.hdb.ds.service.parser.ICoreParserService;
import com.codbex.kronos.hdb.ds.service.parser.CoreParserService;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.utils.CommonsConstants;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.commons.api.topology.TopologicalDepleter;
import org.eclipse.dirigible.commons.api.topology.TopologicalSorter;
import org.eclipse.dirigible.commons.config.Configuration;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizationArtefactType;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.core.scheduler.api.SynchronizationException;
import org.eclipse.dirigible.repository.api.IResource;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HDBCoreSynchronizationFacade implements IHDBCoreSynchronizationFacade {

    private static final Logger logger = LoggerFactory.getLogger(HDBCoreSynchronizationFacade.class);
    private static final DataStructuresSynchronizer DATA_STRUCTURES_SYNCHRONIZER = new DataStructuresSynchronizer();
    private final DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.DATASOURCE);
    private final Map<String, IDataStructureManager> managerServices = HDBModule.getManagerServices();
    private final Map<String, DataStructureParser> parserServices = HDBModule.getParserServices();
    private final Map<String, String> parserTypes = HDBModule.getParserTypes();
    private final ICoreParserService coreParserService = new CoreParserService();

    /**
     * Concatenate list of strings.
     *
     * @param list the list
     * @return the string
     */
    private static String concatenateListOfStrings(List<String> list) {
        StringBuilder buff = new StringBuilder();
        for (String s : list) {
            buff.append(s).append("\n---\n");
        }
        return buff.toString();
    }

    public DataStructureModel parseDataStructureModel(String fileName, String path, String content, String workspace) throws SynchronizationException {
        String[] splitResourceName = fileName.split("\\.");
        String resourceExtension = "." + splitResourceName[splitResourceName.length - 1];
        String registryPath = path;
        String contentAsString = content;

        DataStructureModel dataStructureModel = null;
        try {
            if (parserServices.containsKey(resourceExtension) && !isParsed(registryPath, contentAsString, resourceExtension)) {
                DataStructureParametersModel parametersModel = new DataStructureParametersModel(resourceExtension, registryPath, contentAsString, workspace);
                dataStructureModel = coreParserService.parseDataStructure(parametersModel);
                dataStructureModel.setLocation(registryPath);
            }
        } catch (ReflectiveOperationException e) {
            logger.error("Preparse hash check failed for path " + registryPath + ". " + e.getMessage(), e);
        } catch (DataStructuresException e) {
            logger.error("Synchronized artifact with path " + registryPath + " is not valid. " + e.getMessage(), e);
        } catch (ArtifactParserException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            throw new SynchronizationException(e);
        }
        return dataStructureModel;
    }

    public DataStructureModel parseDataStructureModel(IResource resource) throws SynchronizationException {
        return this.parseDataStructureModel(resource.getName(), getRegistryPath(resource), getContent(resource), CommonsConstants.REGISTRY_PUBLIC);
    }

    @Override
    public void handleResourceSynchronization(IResource resource) throws SynchronizationException, DataStructuresException {
        DataStructureModel dataStructureModel = parseDataStructureModel(resource);
        if (dataStructureModel != null) {
            managerServices.get(dataStructureModel.getType()).synchronizeRuntimeMetadata(dataStructureModel); // 4. we synchronize the metadata
        }
    }

    @Override
    public void handleResourceSynchronization(String fileExtension, DataStructureModel dataStructureModel) throws DataStructuresException {
        managerServices.get(dataStructureModel.getType()).synchronizeRuntimeMetadata(dataStructureModel);
    }

    @Override
    public void updateEntities() {
        List<String> errors = new ArrayList<>();
        try {
            try (Connection connection = dataSource.getConnection()) {
                boolean hdiOnly = Boolean.parseBoolean(Configuration.get(IEnvironmentVariables.KRONOS_HDI_ONLY, "false"));
                if (!hdiOnly) {
                    //Process artefacts for phase 1
                    final List<TopologyDataStructureModelWrapper> listSchemaWrappers = constructListOfSchemaModelWrappers(connection);
                    createArtefactsOnPhaseOne(errors, listSchemaWrappers);

                    //Process artefacts for phase two
                    final List<TopologyDataStructureModelWrapper> listOfWrappersPhaseTwo = new ArrayList<>();
                    Map<String, TopologyDataStructureModelWrapper> wrappersPhaseTwo = new HashMap<>();
                    listOfWrappersPhaseTwo.addAll(constructListOfCdsModelWrappers(connection, wrappersPhaseTwo));
                    listOfWrappersPhaseTwo.addAll(constructListOfTableModelWrappers(connection, wrappersPhaseTwo));
                    listOfWrappersPhaseTwo.addAll(constructListOfSequenceModelWrapper(connection, wrappersPhaseTwo));
                    listOfWrappersPhaseTwo.addAll(constructListOfTableTypesModelWrappers(connection, wrappersPhaseTwo));
                    createArtefactsOnPhaseTwo(errors, listOfWrappersPhaseTwo);

                    //Process artefacts for phase three
                    Map<String, TopologyDataStructureModelWrapper> wrappersPhaseThree = new HashMap<>();
                    final List<TopologyDataStructureModelWrapper> listOfWrappersPhaseThree = new ArrayList<>();
                    listOfWrappersPhaseThree.addAll(constructListOfViewModelWrappers(connection, wrappersPhaseThree));
                    listOfWrappersPhaseThree.addAll(constructListOfProcedureModelWrappers(connection, wrappersPhaseThree));
                    listOfWrappersPhaseThree.addAll(constructListOfTableFunctionModelWrappers(connection, wrappersPhaseThree));
                    listOfWrappersPhaseThree.addAll(constructListOfScalarFunctionModelWrappers(connection, wrappersPhaseThree));
                    listOfWrappersPhaseThree.addAll(constructListOfSynonymModelWrappers(connection, wrappersPhaseThree));
                    listOfWrappersPhaseThree.addAll(constructListOfCdsViewWrappers(connection, wrappersPhaseThree));
                    createArtefactsOnPhaseThree(errors, listOfWrappersPhaseThree);
                }
            }
        } catch (SQLException e) {
            logger.error(concatenateListOfStrings(errors), e);
        }
    }

    @NotNull
    private List<TopologyDataStructureModelWrapper> constructListOfCdsModelWrappers(Connection connection, Map<String, TopologyDataStructureModelWrapper> wrappers) {
        final Map<String, DataStructureModel> dataStructureCdsModels = managerServices.get(IDataStructureModel.TYPE_HDBDD).getDataStructureModels();
        final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
        dataStructureCdsModels.values().forEach(cdsStructure -> {
            final IDataStructureManager<DataStructureHDBTableModel> tableManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_TABLE);
            ((DataStructureCdsModel) cdsStructure).getTableModels().forEach(tableModel -> {
                HDBTableSynchronizationArtefactType artefactType = new HDBTableSynchronizationArtefactType();
                TopologyDataStructureModelWrapper<DataStructureHDBTableModel> tableWrapper = new TopologyDataStructureModelWrapper(connection, tableManagerService, tableModel,
                    artefactType, wrappers);
                listOfWrappers.add(tableWrapper);
            });

            final IDataStructureManager<DataStructureHDBTableTypeModel> tableTypeManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_TABLE_TYPE);
            ((DataStructureCdsModel) cdsStructure).getTableTypeModels().forEach(tableTypeModel -> {
                HDBTableTypeSynchronizationArtefactType artefactType = new HDBTableTypeSynchronizationArtefactType();
                TopologyDataStructureModelWrapper<DataStructureHDBTableTypeModel> tableTypeWrapper = new TopologyDataStructureModelWrapper(connection, tableTypeManagerService,
                    tableTypeModel, artefactType, wrappers);
                listOfWrappers.add(tableTypeWrapper);
            });
        });
        return listOfWrappers;
    }

    @NotNull
    private List<TopologyDataStructureModelWrapper> constructListOfCdsViewWrappers(Connection connection, Map<String, TopologyDataStructureModelWrapper> wrappers) {
      final Map<String, DataStructureModel> dataStructureCdsModels = managerServices.get(IDataStructureModel.TYPE_HDBDD).getDataStructureModels();
      final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
      dataStructureCdsModels.values().forEach(cdsStructure -> {
        final IDataStructureManager<DataStructureHDBViewModel> viewManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_VIEW);
        ((DataStructureCdsModel) cdsStructure).getViewModels().forEach(viewModel -> {
          HDBTableSynchronizationArtefactType artefactType = new HDBTableSynchronizationArtefactType();
          TopologyDataStructureModelWrapper<DataStructureHDBViewModel> tableWrapper = new TopologyDataStructureModelWrapper(connection, viewManagerService, viewModel,
              artefactType, wrappers);
          listOfWrappers.add(tableWrapper);
        });
      });
      return listOfWrappers;
    }

    private void createArtefactsOnPhaseThree(List<String> errors, List<TopologyDataStructureModelWrapper> listOfWrappersPhaseThree) {
        TopologicalDepleter<TopologyDataStructureModelWrapper> depleter = new TopologicalDepleter<>();
        TopologicalSorter<TopologyDataStructureModelWrapper> sorter = new TopologicalSorter<>();
        List<TopologyDataStructureModelWrapper> sortedListOfWrappers = sorter.sort(listOfWrappersPhaseThree);
        try {
            List<TopologyDataStructureModelWrapper> results = depleter.deplete(sortedListOfWrappers, "");
            printErrors(errors, results, "Executing phase three of DB artefact creation.", ArtefactState.FAILED);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            errors.add(e.getMessage());
        }
    }

    @NotNull
    private List<TopologyDataStructureModelWrapper> constructListOfSynonymModelWrappers(Connection connection, Map<String, TopologyDataStructureModelWrapper> wrappersPhaseThree) {
        final Map<String, DataStructureModel> dataStructureSynonymModels = managerServices.get(IDataStructureModel.TYPE_HDB_SYNONYM).getDataStructureModels();
        final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
        final IDataStructureManager<DataStructureHDBSynonymModel> synonymManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_SYNONYM);
        dataStructureSynonymModels.values().forEach(synonymModel -> {
            HDBSynonymSynchronizationArtefactType artefactType = new HDBSynonymSynchronizationArtefactType();
            TopologyDataStructureModelWrapper<DataStructureHDBSynonymModel> synonymModelWrapper = new TopologyDataStructureModelWrapper(connection, synonymManagerService, synonymModel,
                artefactType, wrappersPhaseThree);
            listOfWrappers.add(synonymModelWrapper);
        });
        return listOfWrappers;
    }

    @NotNull
    private List<TopologyDataStructureModelWrapper> constructListOfScalarFunctionModelWrappers(Connection connection, Map<String, TopologyDataStructureModelWrapper> wrappers) {
        final Map<String, DataStructureModel> dataStructureScalarFunctionsModels = managerServices.get(IDataStructureModel.TYPE_HDB_SCALAR_FUNCTION).getDataStructureModels();
        final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
        final IDataStructureManager<DataStructureHDBTableFunctionModel> tableFunctionManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_TABLE_FUNCTION);
        dataStructureScalarFunctionsModels.values().forEach(scalarFunctionModel -> {
            HDBScalarFunctionSynchronizationArtefactType artefactType = new HDBScalarFunctionSynchronizationArtefactType();
            TopologyDataStructureModelWrapper<DataStructureHDBTableFunctionModel> scalarFunctionWrapper = new TopologyDataStructureModelWrapper(connection, tableFunctionManagerService,
                scalarFunctionModel, artefactType, wrappers);
            listOfWrappers.add(scalarFunctionWrapper);
        });
        return listOfWrappers;
    }

    @NotNull
    private List<TopologyDataStructureModelWrapper> constructListOfTableFunctionModelWrappers(Connection connection, Map<String, TopologyDataStructureModelWrapper> wrappers) {
        final Map<String, DataStructureModel> dataStructureTableFunctionsModels = managerServices.get(IDataStructureModel.TYPE_HDB_TABLE_FUNCTION).getDataStructureModels();
        final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
        final IDataStructureManager<DataStructureHDBTableFunctionModel> tableFunctionManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_TABLE_FUNCTION);
        dataStructureTableFunctionsModels.values().forEach(tableFunctionModel -> {
            HDBTableFunctionSynchronizationArtefactType artefactType = new HDBTableFunctionSynchronizationArtefactType();
            TopologyDataStructureModelWrapper<DataStructureHDBTableFunctionModel> tableFunctionWrapper = new TopologyDataStructureModelWrapper(connection, tableFunctionManagerService,
                tableFunctionModel, artefactType, wrappers);
            listOfWrappers.add(tableFunctionWrapper);
        });
        return listOfWrappers;
    }

    @NotNull
    private List<TopologyDataStructureModelWrapper> constructListOfProcedureModelWrappers(Connection connection, Map<String, TopologyDataStructureModelWrapper> wrappers) {
        final Map<String, DataStructureModel> dataStructureProceduresModels = managerServices.get(IDataStructureModel.TYPE_HDB_PROCEDURE).getDataStructureModels();
        final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
        final IDataStructureManager<DataStructureHDBProcedureModel> proceduresManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_PROCEDURE);
        dataStructureProceduresModels.values().forEach(procedureModel -> {
            HDBProcedureSynchronizationArtefactType artefactType = new HDBProcedureSynchronizationArtefactType();
            TopologyDataStructureModelWrapper<DataStructureHDBProcedureModel> procedureWrapper = new TopologyDataStructureModelWrapper(connection, proceduresManagerService, procedureModel,
                artefactType, wrappers);
            listOfWrappers.add(procedureWrapper);
        });
        return listOfWrappers;
    }

    @NotNull
    private List<TopologyDataStructureModelWrapper> constructListOfViewModelWrappers(Connection connection, Map<String, TopologyDataStructureModelWrapper> wrappers) {
        final Map<String, DataStructureModel> dataStructureViewsModels = managerServices.get(IDataStructureModel.TYPE_HDB_VIEW).getDataStructureModels();
        final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
        final IDataStructureManager<DataStructureHDBViewModel> viewManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_VIEW);
        dataStructureViewsModels.values().forEach(viewModel -> {
            HDBViewSynchronizationArtefactType artefactType = new HDBViewSynchronizationArtefactType();
            TopologyDataStructureModelWrapper<DataStructureHDBViewModel> viewWrapper = new TopologyDataStructureModelWrapper(connection, viewManagerService, viewModel, artefactType,
                wrappers);
            listOfWrappers.add(viewWrapper);
        });
        return listOfWrappers;
    }

    private void createArtefactsOnPhaseTwo(List<String> errors, List<TopologyDataStructureModelWrapper> listOfWrappers) {
        TopologicalDepleter<TopologyDataStructureModelWrapper> depleter = new TopologicalDepleter<>();
        TopologicalSorter<TopologyDataStructureModelWrapper> sorter = new TopologicalSorter<>();

        List<TopologyDataStructureModelWrapper> sortListOfWrapper = sorter.sort(listOfWrappers);
        try {
            List<TopologyDataStructureModelWrapper> results = depleter.deplete(sortListOfWrapper, "");
            printErrors(errors, results, "Executing phase two of DB artefact creation.", ArtefactState.FAILED);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            errors.add(e.getMessage());
        }
    }

    private List<TopologyDataStructureModelWrapper> constructListOfTableTypesModelWrappers(Connection connection, Map<String, TopologyDataStructureModelWrapper> wrappers) {
        final Map<String, DataStructureModel> dataStructureTableTypesModels = managerServices.get(IDataStructureModel.TYPE_HDB_TABLE_TYPE).getDataStructureModels();
        final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
        final IDataStructureManager<DataStructureHDBTableTypeModel> tableTypeManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_TABLE_TYPE);
        dataStructureTableTypesModels.values().forEach(tableTypeModel -> {
            HDBTableTypeSynchronizationArtefactType artefactType = new HDBTableTypeSynchronizationArtefactType();
            TopologyDataStructureModelWrapper<DataStructureHDBTableTypeModel> tableTypeWrapper = new TopologyDataStructureModelWrapper(connection, tableTypeManagerService, tableTypeModel,
                artefactType, wrappers);
            listOfWrappers.add(tableTypeWrapper);
        });

        return listOfWrappers;
    }

    private List<TopologyDataStructureModelWrapper> constructListOfSequenceModelWrapper(Connection connection, Map<String, TopologyDataStructureModelWrapper> wrappers) {
        final Map<String, DataStructureModel> dataStructureSequencesModels = managerServices.get(IDataStructureModel.TYPE_HDB_SEQUENCE).getDataStructureModels();
        final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
        final IDataStructureManager<DataStructureHDBSequenceModel> sequenceManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_SEQUENCE);
        dataStructureSequencesModels.values().forEach(sequenceModel -> {
            HDBSequenceSynchronizationArtefactType artefactType = new HDBSequenceSynchronizationArtefactType();
            TopologyDataStructureModelWrapper<DataStructureHDBSequenceModel> sequenceWrapper = new TopologyDataStructureModelWrapper(connection, sequenceManagerService, sequenceModel,
                artefactType, wrappers);
            listOfWrappers.add(sequenceWrapper);
        });

        return listOfWrappers;
    }

    private List<TopologyDataStructureModelWrapper> constructListOfTableModelWrappers(Connection connection, Map<String, TopologyDataStructureModelWrapper> wrappers) {
        final Map<String, DataStructureModel> dataStructureTablesModels = managerServices.get(IDataStructureModel.TYPE_HDB_TABLE).getDataStructureModels();
        final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
        final IDataStructureManager<DataStructureHDBTableModel> tableManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_TABLE);
        dataStructureTablesModels.values().forEach(tableModel -> {
            HDBTableSynchronizationArtefactType artefactType = new HDBTableSynchronizationArtefactType();
            TopologyDataStructureModelWrapper<DataStructureHDBTableModel> tableWrapper = new TopologyDataStructureModelWrapper(connection, tableManagerService, tableModel, artefactType,
                wrappers);
            listOfWrappers.add(tableWrapper);
        });

        return listOfWrappers;
    }

    private void createArtefactsOnPhaseOne(List<String> errors, List<TopologyDataStructureModelWrapper> listSchemaWrappers) {
        TopologicalDepleter<TopologyDataStructureModelWrapper> depleter = new TopologicalDepleter<>();
        TopologicalSorter<TopologyDataStructureModelWrapper> sorter = new TopologicalSorter<>();

        List<TopologyDataStructureModelWrapper> sortedSchemaModelWrappers = sorter.sort(listSchemaWrappers);
        try {
            List<TopologyDataStructureModelWrapper> results = depleter.deplete(sortedSchemaModelWrappers, "");
            printErrors(errors, results, "Executing phase one of DB artefact creation.", ArtefactState.FAILED);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            errors.add(e.getMessage());
        }
    }

    @NotNull
    private List<TopologyDataStructureModelWrapper> constructListOfSchemaModelWrappers(Connection connection) {
        Map<String, DataStructureModel> dataStructureSchemasModels = managerServices.get(IDataStructureModel.TYPE_HDB_SCHEMA).getDataStructureModels();
        final List<TopologyDataStructureModelWrapper> listSchemaWrappers = new ArrayList<>();
        Map<String, TopologyDataStructureModelWrapper> wrappers = new HashMap<>();
        IDataStructureManager schemaModelManager = managerServices.get(IDataStructureModel.TYPE_HDB_SCHEMA);
        dataStructureSchemasModels.values().forEach(schemaModel -> {
            TopologyDataStructureModelWrapper<DataStructureHDBSchemaModel> schemaWrapper = new TopologyDataStructureModelWrapper<>(connection, schemaModelManager, schemaModel,
                new HDBSchemaSynchronizationArtefactType(), wrappers);
            listSchemaWrappers.add(schemaWrapper);
        });

        return listSchemaWrappers;
    }

    @Override
    public void cleanup() throws DataStructuresException {
        for (IDataStructureManager dataStructureManager : managerServices.values()) {
            dataStructureManager.cleanup();
        }

        logger.trace("Done cleaning up Kronos Data Structures.");
    }

    @Override
    public void clearCache() {
        this.managerServices.values().forEach(IDataStructureManager::clearCache);
    }

    private String getRegistryPath(IResource resource) {
        String resourcePath = resource.getPath();
        return resourcePath.startsWith("/registry/public") ? resourcePath.substring("/registry/public".length()) : resourcePath;
    }

    private String getContent(IResource resource) throws SynchronizationException {
        byte[] content = resource.getContent();
        String contentAsString;
        try {
            contentAsString = IOUtils.toString(new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new SynchronizationException(e);
        }
        return contentAsString;
    }

    private String getType(String resourceExtension) {
        return parserTypes.get(resourceExtension);
    }

    private boolean isParsed(String location, String content, String resourceExtension)
        throws DataStructuresException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        String modelType = getType(resourceExtension);
        if (modelType == null) {
            return false;
        }

        Class<DataStructureModel> clazz = coreParserService.getDataStructureClass(modelType);
        DataStructureModel baseDataStructureModel = clazz.getDeclaredConstructor().newInstance();
        baseDataStructureModel.setLocation(location);
        baseDataStructureModel.setType(modelType);
        baseDataStructureModel.setHash(DigestUtils.md5Hex(content));

        return managerServices.get(baseDataStructureModel.getType()).existsArtifactMetadata(baseDataStructureModel);
    }

    private void printErrors(List<String> errors, List<TopologyDataStructureModelWrapper> results, String flow, ISynchronizerArtefactType.ArtefactState state) {
        if (results.size() > 0) {
            for (TopologyDataStructureModelWrapper result : results) {
                String errorMessage = String.format("Undepleted: %s in operation: %s", result.getId(), flow);
                logger.error(errorMessage);
                errors.add(errorMessage);
                applyArtefactState(result.getModel().getName(), result.getModel().getLocation(), result.getArtefactType(), state, errorMessage);
            }
        }
    }

    public void applyArtefactState(String artefactName, String artefactLocation, AbstractSynchronizationArtefactType type, ISynchronizerArtefactType.ArtefactState state, String message) {
        DATA_STRUCTURES_SYNCHRONIZER.applyArtefactState(artefactName, artefactLocation, type, state, message);
    }
}
