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
import com.codbex.kronos.hdb.ds.artefacts.HDBStructureSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBSynonymSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableFunctionSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableTypeSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.artefacts.HDBViewSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbdd.DataStructureHDBDDModel;
import com.codbex.kronos.hdb.ds.model.hdbprocedure.DataStructureHDBProcedureModel;
import com.codbex.kronos.hdb.ds.model.hdbschema.DataStructureHDBSchemaModel;
import com.codbex.kronos.hdb.ds.model.hdbsequence.DataStructureHDBSequenceModel;
import com.codbex.kronos.hdb.ds.model.hdbstructure.DataStructureHDBStructureModel;
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

/**
 * The Class HDBCoreSynchronizationFacade.
 */
public class HDBCoreSynchronizationFacade implements IHDBCoreSynchronizationFacade {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBCoreSynchronizationFacade.class);

  /**
   * The Constant DATA_STRUCTURES_SYNCHRONIZER.
   */
  private static final DataStructuresSynchronizer DATA_STRUCTURES_SYNCHRONIZER = new DataStructuresSynchronizer();

  /**
   * The data source.
   */
  private final DataSource dataSource = (DataSource) StaticObjects.get(StaticObjects.DATASOURCE);

  /**
   * The manager services.
   */
  private final Map<String, IDataStructureManager> managerServices = HDBModule.getManagerServices();

  /**
   * The parser services.
   */
  private final Map<String, DataStructureParser> parserServices = HDBModule.getParserServices();

  /**
   * The parser types.
   */
  private final Map<String, String> parserTypes = HDBModule.getParserTypes();

  /**
   * The core parser service.
   */
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

  /**
   * Parses the data structure model.
   *
   * @param fileName  the file name
   * @param path      the path
   * @param content   the content
   * @param workspace the workspace
   * @return the data structure model
   * @throws SynchronizationException the synchronization exception
   */
  public DataStructureModel parseDataStructureModel(String fileName, String path, String content, String workspace)
      throws SynchronizationException {
    String[] splitResourceName = fileName.split("\\.");
    String resourceExtension = "." + splitResourceName[splitResourceName.length - 1];
    String registryPath = path;
    String contentAsString = content;

    DataStructureModel dataStructureModel = null;
    try {
      if (parserServices.containsKey(resourceExtension) && !isParsed(registryPath, contentAsString, resourceExtension)) {
        DataStructureParametersModel parametersModel = new DataStructureParametersModel(resourceExtension, registryPath, contentAsString,
            workspace);
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

  /**
   * Parses the data structure model.
   *
   * @param resource the resource
   * @return the data structure model
   * @throws SynchronizationException the synchronization exception
   */
  public DataStructureModel parseDataStructureModel(IResource resource) throws SynchronizationException {
    return this.parseDataStructureModel(resource.getName(), getRegistryPath(resource), getContent(resource),
        CommonsConstants.REGISTRY_PUBLIC);
  }

  /**
   * Handle resource synchronization.
   *
   * @param resource the resource
   * @throws SynchronizationException the synchronization exception
   * @throws DataStructuresException  the data structures exception
   */
  @Override
  public void handleResourceSynchronization(IResource resource) throws SynchronizationException, DataStructuresException {
    DataStructureModel dataStructureModel = parseDataStructureModel(resource);
    if (dataStructureModel != null) {
      managerServices.get(dataStructureModel.getType()).synchronizeRuntimeMetadata(dataStructureModel); // 4. we synchronize the metadata
    }
  }

  /**
   * Handle resource synchronization.
   *
   * @param fileExtension      the file extension
   * @param dataStructureModel the data structure model
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void handleResourceSynchronization(String fileExtension, DataStructureModel dataStructureModel) throws DataStructuresException {
    managerServices.get(dataStructureModel.getType()).synchronizeRuntimeMetadata(dataStructureModel);
  }

  /**
   * Update entities.
   */
  @Override
  public void updateEntities() {
    List<String> errors = new ArrayList<>();
    try {
      try (Connection connection = dataSource.getConnection()) {
        boolean hdiOnly = Boolean.parseBoolean(Configuration.get(IEnvironmentVariables.KRONOS_HDI_ONLY, "false"));
        if (!hdiOnly) {
          // Process artefacts for phase I
          final List<TopologyDataStructureModelWrapper> listSchemaWrappers = constructListOfSchemaModelWrappers(connection);
          createArtefactsOnPhaseOne(errors, listSchemaWrappers);

          // Process artefacts for phase II
          final List<TopologyDataStructureModelWrapper> listOfWrappersPhaseTwo = new ArrayList<>();
          Map<String, TopologyDataStructureModelWrapper> wrappersPhaseTwo = new HashMap<>();
          listOfWrappersPhaseTwo.addAll(constructListOfCdsTableModelWrappers(connection, wrappersPhaseTwo));
          listOfWrappersPhaseTwo.addAll(constructListOfCdsTableTypeModelWrappers(connection, wrappersPhaseTwo));
          listOfWrappersPhaseTwo.addAll(constructListOfTableModelWrappers(connection, wrappersPhaseTwo));
          listOfWrappersPhaseTwo.addAll(constructListOfSequenceModelWrapper(connection, wrappersPhaseTwo));
          listOfWrappersPhaseTwo.addAll(constructListOfTableTypesModelWrappers(connection, wrappersPhaseTwo));
          listOfWrappersPhaseTwo.addAll(constructListOfStructuresModelWrappers(connection, wrappersPhaseTwo));
          createArtefactsOnPhaseTwo(errors, listOfWrappersPhaseTwo);

          // Process artefacts for phase III
          Map<String, TopologyDataStructureModelWrapper> wrappersPhaseThree = new HashMap<>();
          final List<TopologyDataStructureModelWrapper> listOfWrappersPhaseThree = new ArrayList<>();
          listOfWrappersPhaseThree.addAll(constructListOfViewModelWrappers(connection, wrappersPhaseThree));
          listOfWrappersPhaseThree.addAll(constructListOfProcedureModelWrappers(connection, wrappersPhaseThree));
          listOfWrappersPhaseThree.addAll(constructListOfTableFunctionModelWrappers(connection, wrappersPhaseThree));
          listOfWrappersPhaseThree.addAll(constructListOfScalarFunctionModelWrappers(connection, wrappersPhaseThree));
          listOfWrappersPhaseThree.addAll(constructListOfSynonymModelWrappers(connection, wrappersPhaseThree));
          listOfWrappersPhaseThree.addAll(constructListOfCdsViewModelWrappers(connection, wrappersPhaseThree));
          createArtefactsOnPhaseThree(errors, listOfWrappersPhaseThree);
        }
      }
    } catch (SQLException e) {
      logger.error(concatenateListOfStrings(errors), e);
    }
  }

  /**
   * Construct list of cds table wrappers.
   *
   * @param connection the connection
   * @param wrappers   the wrappers
   * @return the list
   */
  @NotNull
  private List<TopologyDataStructureModelWrapper> constructListOfCdsTableModelWrappers(Connection connection,
      Map<String, TopologyDataStructureModelWrapper> wrappers) {
    final Map<String, DataStructureModel> dataStructureCdsModels = managerServices.get(IDataStructureModel.TYPE_HDBDD)
        .getDataStructureModels();
    final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
    dataStructureCdsModels.values().forEach(cdsStructure -> {
      final IDataStructureManager<DataStructureHDBTableModel> tableManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_TABLE);
      ((DataStructureHDBDDModel) cdsStructure).getTableModels().forEach(tableModel -> {
        HDBTableSynchronizationArtefactType artefactType = new HDBTableSynchronizationArtefactType();
        TopologyDataStructureModelWrapper<DataStructureHDBTableModel> tableWrapper = new TopologyDataStructureModelWrapper(connection,
            tableManagerService, tableModel,
            artefactType, wrappers);
        listOfWrappers.add(tableWrapper);
      });
    });
    return listOfWrappers;
  }

  /**
   * Construct list of cds table type wrappers.
   *
   * @param connection the connection
   * @param wrappers   the wrappers
   * @return the list
   */
  @NotNull
  private List<TopologyDataStructureModelWrapper> constructListOfCdsTableTypeModelWrappers(Connection connection,
      Map<String, TopologyDataStructureModelWrapper> wrappers) {
    final Map<String, DataStructureModel> dataStructureCdsModels = managerServices.get(IDataStructureModel.TYPE_HDBDD)
        .getDataStructureModels();
    final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
    dataStructureCdsModels.values().forEach(cdsStructure -> {
      final IDataStructureManager<DataStructureHDBTableTypeModel> tableTypeManagerService = managerServices.get(
          IDataStructureModel.TYPE_HDB_TABLE_TYPE);
      ((DataStructureHDBDDModel) cdsStructure).getTableTypeModels().forEach(tableTypeModel -> {
        HDBTableTypeSynchronizationArtefactType artefactType = new HDBTableTypeSynchronizationArtefactType();
        TopologyDataStructureModelWrapper<DataStructureHDBTableTypeModel> tableTypeWrapper = new TopologyDataStructureModelWrapper(
            connection, tableTypeManagerService,
            tableTypeModel, artefactType, wrappers);
        listOfWrappers.add(tableTypeWrapper);
      });
    });
    return listOfWrappers;
  }

  /**
   * Construct list of cds view wrappers.
   *
   * @param connection the connection
   * @param wrappers   the wrappers
   * @return the list
   */
  @NotNull
  private List<TopologyDataStructureModelWrapper> constructListOfCdsViewModelWrappers(Connection connection,
      Map<String, TopologyDataStructureModelWrapper> wrappers) {
    final Map<String, DataStructureModel> dataStructureCdsModels = managerServices.get(IDataStructureModel.TYPE_HDBDD)
        .getDataStructureModels();
    final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
    dataStructureCdsModels.values().forEach(cdsStructure -> {
      final IDataStructureManager<DataStructureHDBViewModel> viewManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_VIEW);
      ((DataStructureHDBDDModel) cdsStructure).getViewModels().forEach(viewModel -> {
        HDBTableSynchronizationArtefactType artefactType = new HDBTableSynchronizationArtefactType();
        TopologyDataStructureModelWrapper<DataStructureHDBViewModel> tableWrapper = new TopologyDataStructureModelWrapper(connection,
            viewManagerService, viewModel,
            artefactType, wrappers);
        listOfWrappers.add(tableWrapper);
      });
    });
    return listOfWrappers;
  }

  /**
   * Creates the artefacts on phase three.
   *
   * @param errors                   the errors
   * @param listOfWrappersPhaseThree the list of wrappers phase three
   */
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

  /**
   * Construct list of synonym model wrappers.
   *
   * @param connection         the connection
   * @param wrappersPhaseThree the wrappers phase three
   * @return the list
   */
  @NotNull
  private List<TopologyDataStructureModelWrapper> constructListOfSynonymModelWrappers(Connection connection,
      Map<String, TopologyDataStructureModelWrapper> wrappersPhaseThree) {
    final Map<String, DataStructureModel> dataStructureSynonymModels = managerServices.get(IDataStructureModel.TYPE_HDB_SYNONYM)
        .getDataStructureModels();
    final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
    final IDataStructureManager<DataStructureHDBSynonymModel> synonymManagerService = managerServices.get(
        IDataStructureModel.TYPE_HDB_SYNONYM);
    dataStructureSynonymModels.values().forEach(synonymModel -> {
      HDBSynonymSynchronizationArtefactType artefactType = new HDBSynonymSynchronizationArtefactType();
      TopologyDataStructureModelWrapper<DataStructureHDBSynonymModel> synonymModelWrapper = new TopologyDataStructureModelWrapper(
          connection, synonymManagerService, synonymModel,
          artefactType, wrappersPhaseThree);
      listOfWrappers.add(synonymModelWrapper);
    });
    return listOfWrappers;
  }

  /**
   * Construct list of scalar function model wrappers.
   *
   * @param connection the connection
   * @param wrappers   the wrappers
   * @return the list
   */
  @NotNull
  private List<TopologyDataStructureModelWrapper> constructListOfScalarFunctionModelWrappers(Connection connection,
      Map<String, TopologyDataStructureModelWrapper> wrappers) {
    final Map<String, DataStructureModel> dataStructureScalarFunctionsModels = managerServices.get(
        IDataStructureModel.TYPE_HDB_SCALAR_FUNCTION).getDataStructureModels();
    final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
    final IDataStructureManager<DataStructureHDBTableFunctionModel> tableFunctionManagerService = managerServices.get(
        IDataStructureModel.TYPE_HDB_TABLE_FUNCTION);
    dataStructureScalarFunctionsModels.values().forEach(scalarFunctionModel -> {
      HDBScalarFunctionSynchronizationArtefactType artefactType = new HDBScalarFunctionSynchronizationArtefactType();
      TopologyDataStructureModelWrapper<DataStructureHDBTableFunctionModel> scalarFunctionWrapper = new TopologyDataStructureModelWrapper(
          connection, tableFunctionManagerService,
          scalarFunctionModel, artefactType, wrappers);
      listOfWrappers.add(scalarFunctionWrapper);
    });
    return listOfWrappers;
  }

  /**
   * Construct list of table function model wrappers.
   *
   * @param connection the connection
   * @param wrappers   the wrappers
   * @return the list
   */
  @NotNull
  private List<TopologyDataStructureModelWrapper> constructListOfTableFunctionModelWrappers(Connection connection,
      Map<String, TopologyDataStructureModelWrapper> wrappers) {
    final Map<String, DataStructureModel> dataStructureTableFunctionsModels = managerServices.get(
        IDataStructureModel.TYPE_HDB_TABLE_FUNCTION).getDataStructureModels();
    final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
    final IDataStructureManager<DataStructureHDBTableFunctionModel> tableFunctionManagerService = managerServices.get(
        IDataStructureModel.TYPE_HDB_TABLE_FUNCTION);
    dataStructureTableFunctionsModels.values().forEach(tableFunctionModel -> {
      HDBTableFunctionSynchronizationArtefactType artefactType = new HDBTableFunctionSynchronizationArtefactType();
      TopologyDataStructureModelWrapper<DataStructureHDBTableFunctionModel> tableFunctionWrapper = new TopologyDataStructureModelWrapper(
          connection, tableFunctionManagerService,
          tableFunctionModel, artefactType, wrappers);
      listOfWrappers.add(tableFunctionWrapper);
    });
    return listOfWrappers;
  }

  /**
   * Construct list of procedure model wrappers.
   *
   * @param connection the connection
   * @param wrappers   the wrappers
   * @return the list
   */
  @NotNull
  private List<TopologyDataStructureModelWrapper> constructListOfProcedureModelWrappers(Connection connection,
      Map<String, TopologyDataStructureModelWrapper> wrappers) {
    final Map<String, DataStructureModel> dataStructureProceduresModels = managerServices.get(IDataStructureModel.TYPE_HDB_PROCEDURE)
        .getDataStructureModels();
    final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
    final IDataStructureManager<DataStructureHDBProcedureModel> proceduresManagerService = managerServices.get(
        IDataStructureModel.TYPE_HDB_PROCEDURE);
    dataStructureProceduresModels.values().forEach(procedureModel -> {
      HDBProcedureSynchronizationArtefactType artefactType = new HDBProcedureSynchronizationArtefactType();
      TopologyDataStructureModelWrapper<DataStructureHDBProcedureModel> procedureWrapper = new TopologyDataStructureModelWrapper(connection,
          proceduresManagerService, procedureModel,
          artefactType, wrappers);
      listOfWrappers.add(procedureWrapper);
    });
    return listOfWrappers;
  }

  /**
   * Construct list of view model wrappers.
   *
   * @param connection the connection
   * @param wrappers   the wrappers
   * @return the list
   */
  @NotNull
  private List<TopologyDataStructureModelWrapper> constructListOfViewModelWrappers(Connection connection,
      Map<String, TopologyDataStructureModelWrapper> wrappers) {
    final Map<String, DataStructureModel> dataStructureViewsModels = managerServices.get(IDataStructureModel.TYPE_HDB_VIEW)
        .getDataStructureModels();
    final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
    final IDataStructureManager<DataStructureHDBViewModel> viewManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_VIEW);
    dataStructureViewsModels.values().forEach(viewModel -> {
      HDBViewSynchronizationArtefactType artefactType = new HDBViewSynchronizationArtefactType();
      TopologyDataStructureModelWrapper<DataStructureHDBViewModel> viewWrapper = new TopologyDataStructureModelWrapper(connection,
          viewManagerService, viewModel, artefactType,
          wrappers);
      listOfWrappers.add(viewWrapper);
    });
    return listOfWrappers;
  }

  /**
   * Creates the artefacts on phase two.
   *
   * @param errors         the errors
   * @param listOfWrappers the list of wrappers
   */
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

  /**
   * Construct list of table types model wrappers.
   *
   * @param connection the connection
   * @param wrappers   the wrappers
   * @return the list
   */
  private List<TopologyDataStructureModelWrapper> constructListOfTableTypesModelWrappers(Connection connection,
      Map<String, TopologyDataStructureModelWrapper> wrappers) {
    final Map<String, DataStructureModel> dataStructureTableTypesModels = managerServices.get(IDataStructureModel.TYPE_HDB_TABLE_TYPE)
        .getDataStructureModels();
    final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
    final IDataStructureManager<DataStructureHDBTableTypeModel> tableTypeManagerService = managerServices.get(
        IDataStructureModel.TYPE_HDB_TABLE_TYPE);
    dataStructureTableTypesModels.values().forEach(tableTypeModel -> {
      HDBTableTypeSynchronizationArtefactType artefactType = new HDBTableTypeSynchronizationArtefactType();
      TopologyDataStructureModelWrapper<DataStructureHDBTableTypeModel> tableTypeWrapper = new TopologyDataStructureModelWrapper(connection,
          tableTypeManagerService, tableTypeModel,
          artefactType, wrappers);
      listOfWrappers.add(tableTypeWrapper);
    });

    return listOfWrappers;
  }

  /**
   * Construct list of structures model wrappers.
   *
   * @param connection the connection
   * @param wrappers   the wrappers
   * @return the list
   */
  private List<TopologyDataStructureModelWrapper> constructListOfStructuresModelWrappers(Connection connection,
      Map<String, TopologyDataStructureModelWrapper> wrappers) {
    final Map<String, DataStructureModel> dataStructureStructuresModels = managerServices.get(IDataStructureModel.TYPE_HDB_STRUCTURE)
        .getDataStructureModels();
    final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
    final IDataStructureManager<DataStructureHDBStructureModel> structureManagerService = managerServices.get(
        IDataStructureModel.TYPE_HDB_STRUCTURE);
    dataStructureStructuresModels.values().forEach(tableTypeModel -> {
      HDBStructureSynchronizationArtefactType artefactType = new HDBStructureSynchronizationArtefactType();
      TopologyDataStructureModelWrapper<DataStructureHDBStructureModel> tableTypeWrapper = new TopologyDataStructureModelWrapper(connection,
          structureManagerService, tableTypeModel,
          artefactType, wrappers);
      listOfWrappers.add(tableTypeWrapper);
    });

    return listOfWrappers;
  }

  /**
   * Construct list of sequence model wrapper.
   *
   * @param connection the connection
   * @param wrappers   the wrappers
   * @return the list
   */
  private List<TopologyDataStructureModelWrapper> constructListOfSequenceModelWrapper(Connection connection,
      Map<String, TopologyDataStructureModelWrapper> wrappers) {
    final Map<String, DataStructureModel> dataStructureSequencesModels = managerServices.get(IDataStructureModel.TYPE_HDB_SEQUENCE)
        .getDataStructureModels();
    final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
    final IDataStructureManager<DataStructureHDBSequenceModel> sequenceManagerService = managerServices.get(
        IDataStructureModel.TYPE_HDB_SEQUENCE);
    dataStructureSequencesModels.values().forEach(sequenceModel -> {
      HDBSequenceSynchronizationArtefactType artefactType = new HDBSequenceSynchronizationArtefactType();
      TopologyDataStructureModelWrapper<DataStructureHDBSequenceModel> sequenceWrapper = new TopologyDataStructureModelWrapper(connection,
          sequenceManagerService, sequenceModel,
          artefactType, wrappers);
      listOfWrappers.add(sequenceWrapper);
    });

    return listOfWrappers;
  }

  /**
   * Construct list of table model wrappers.
   *
   * @param connection the connection
   * @param wrappers   the wrappers
   * @return the list
   */
  private List<TopologyDataStructureModelWrapper> constructListOfTableModelWrappers(Connection connection,
      Map<String, TopologyDataStructureModelWrapper> wrappers) {
    final Map<String, DataStructureModel> dataStructureTablesModels = managerServices.get(IDataStructureModel.TYPE_HDB_TABLE)
        .getDataStructureModels();
    final List<TopologyDataStructureModelWrapper> listOfWrappers = new ArrayList<>();
    final IDataStructureManager<DataStructureHDBTableModel> tableManagerService = managerServices.get(IDataStructureModel.TYPE_HDB_TABLE);
    dataStructureTablesModels.values().forEach(tableModel -> {
      HDBTableSynchronizationArtefactType artefactType = new HDBTableSynchronizationArtefactType();
      TopologyDataStructureModelWrapper<DataStructureHDBTableModel> tableWrapper = new TopologyDataStructureModelWrapper(connection,
          tableManagerService, tableModel, artefactType,
          wrappers);
      listOfWrappers.add(tableWrapper);
    });

    return listOfWrappers;
  }

  /**
   * Creates the artefacts on phase one.
   *
   * @param errors             the errors
   * @param listSchemaWrappers the list schema wrappers
   */
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

  /**
   * Construct list of schema model wrappers.
   *
   * @param connection the connection
   * @return the list
   */
  @NotNull
  private List<TopologyDataStructureModelWrapper> constructListOfSchemaModelWrappers(Connection connection) {
    Map<String, DataStructureModel> dataStructureSchemasModels = managerServices.get(IDataStructureModel.TYPE_HDB_SCHEMA)
        .getDataStructureModels();
    final List<TopologyDataStructureModelWrapper> listSchemaWrappers = new ArrayList<>();
    Map<String, TopologyDataStructureModelWrapper> wrappers = new HashMap<>();
    IDataStructureManager schemaModelManager = managerServices.get(IDataStructureModel.TYPE_HDB_SCHEMA);
    dataStructureSchemasModels.values().forEach(schemaModel -> {
      TopologyDataStructureModelWrapper<DataStructureHDBSchemaModel> schemaWrapper = new TopologyDataStructureModelWrapper<>(connection,
          schemaModelManager, schemaModel,
          new HDBSchemaSynchronizationArtefactType(), wrappers);
      listSchemaWrappers.add(schemaWrapper);
    });

    return listSchemaWrappers;
  }

  /**
   * Cleanup.
   *
   * @throws DataStructuresException the data structures exception
   */
  @Override
  public void cleanup() throws DataStructuresException {
    for (IDataStructureManager dataStructureManager : managerServices.values()) {
      dataStructureManager.cleanup();
    }

    logger.trace("Done cleaning up Kronos Data Structures.");
  }

  /**
   * Clear cache.
   */
  @Override
  public void clearCache() {
    this.managerServices.values().forEach(IDataStructureManager::clearCache);
  }

  /**
   * Gets the registry path.
   *
   * @param resource the resource
   * @return the registry path
   */
  private String getRegistryPath(IResource resource) {
    String resourcePath = resource.getPath();
    return resourcePath.startsWith("/registry/public") ? resourcePath.substring("/registry/public".length()) : resourcePath;
  }

  /**
   * Gets the content.
   *
   * @param resource the resource
   * @return the content
   * @throws SynchronizationException the synchronization exception
   */
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

  /**
   * Gets the type.
   *
   * @param resourceExtension the resource extension
   * @return the type
   */
  private String getType(String resourceExtension) {
    return parserTypes.get(resourceExtension);
  }

  /**
   * Checks if is parsed.
   *
   * @param location          the location
   * @param content           the content
   * @param resourceExtension the resource extension
   * @return true, if is parsed
   * @throws DataStructuresException   the data structures exception
   * @throws ClassNotFoundException    the class not found exception
   * @throws InstantiationException    the instantiation exception
   * @throws IllegalAccessException    the illegal access exception
   * @throws NoSuchMethodException     the no such method exception
   * @throws InvocationTargetException the invocation target exception
   */
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

  /**
   * Prints the errors.
   *
   * @param errors  the errors
   * @param results the results
   * @param flow    the flow
   * @param state   the state
   */
  private void printErrors(List<String> errors, List<TopologyDataStructureModelWrapper> results, String flow,
      ISynchronizerArtefactType.ArtefactState state) {
    if (results.size() > 0) {
      for (TopologyDataStructureModelWrapper result : results) {
        String errorMessage = String.format("Undepleted: %s in operation: %s", result.getId(), flow);
        logger.error(errorMessage);
        errors.add(errorMessage);
        applyArtefactState(result.getModel().getName(), result.getModel().getLocation(), result.getArtefactType(), state, errorMessage);
      }
    }
  }

  /**
   * Apply artefact state.
   *
   * @param artefactName     the artefact name
   * @param artefactLocation the artefact location
   * @param type             the type
   * @param state            the state
   * @param message          the message
   */
  public void applyArtefactState(String artefactName, String artefactLocation, AbstractSynchronizationArtefactType type,
      ISynchronizerArtefactType.ArtefactState state, String message) {
    DATA_STRUCTURES_SYNCHRONIZER.applyArtefactState(artefactName, artefactLocation, type, state, message);
  }
}
