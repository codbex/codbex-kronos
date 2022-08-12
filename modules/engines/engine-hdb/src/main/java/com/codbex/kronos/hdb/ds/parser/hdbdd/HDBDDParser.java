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
package com.codbex.kronos.hdb.ds.parser.hdbdd;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.artefacts.HDBDDSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbdd.DataStructureHDBDDModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.DataStructureHDBTableModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.DataStructureHDBTableTypeModel;
import com.codbex.kronos.hdb.ds.model.hdbview.DataStructureHDBViewModel;
import com.codbex.kronos.hdb.ds.module.HDBModule;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.hdb.ds.transformer.hdbdd.HdbddTransformer;
import com.codbex.kronos.parser.hdbdd.core.CdsLexer;
import com.codbex.kronos.parser.hdbdd.core.CdsParser;
import com.codbex.kronos.parser.hdbdd.custom.ArtifactDefinitionListener;
import com.codbex.kronos.parser.hdbdd.custom.ReferenceResolvingListener;
import com.codbex.kronos.parser.hdbdd.custom.HDBDDErrorListener;
import com.codbex.kronos.parser.hdbdd.exception.CDSRuntimeException;
import com.codbex.kronos.parser.hdbdd.symbols.SymbolTable;
import com.codbex.kronos.parser.hdbdd.symbols.entity.EntitySymbol;
import com.codbex.kronos.parser.hdbdd.symbols.type.custom.StructuredDataTypeSymbol;
import com.codbex.kronos.parser.hdbdd.symbols.view.ViewSymbol;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.Constants;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.repository.api.IRepository;
import org.eclipse.dirigible.repository.api.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class HDBDDParser.
 */
public class HDBDDParser implements DataStructureParser {

  /**
   * The Constant logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(HDBDDParser.class);

  /**
   * The hdbdd transformer.
   */
  private HdbddTransformer hdbddTransformer = new HdbddTransformer();

  /**
   * The repository.
   */
  private IRepository repository = (IRepository) StaticObjects.get(StaticObjects.REPOSITORY);

  /**
   * The symbol table.
   */
  private SymbolTable symbolTable = new SymbolTable();

  /**
   * The dependency structure.
   */
  private Map<String, Set<String>> dependencyStructure = new HashMap<>();

  /**
   * The parsed nodes.
   */
  private Set<String> parsedNodes = new HashSet<>();

  /**
   * The entity artefact.
   */
  private HDBDDSynchronizationArtefactType HDBDD_ARTEFACT = new HDBDDSynchronizationArtefactType();

  /**
   * The data structures synchronizer.
   */
  private DataStructuresSynchronizer dataStructuresSynchronizer = new DataStructuresSynchronizer();

  /**
   * Parses the hdbdd file.
   *
   * @param parametersModel the parameters model
   * @return the data structure model
   * @throws DataStructuresException the data structures exception
   * @throws IOException             Signals that an I/O exception has occurred.
   */
  @Override
  public DataStructureModel parse(DataStructureParametersModel parametersModel) throws DataStructuresException, IOException {
    for (String fileLocation : this.getFilesToProcess(parametersModel.getLocation())) {
      IResource loadedResource = this.repository.getResource(parametersModel.getWorkspacePath() + fileLocation);
      String fileContent = new String(loadedResource.getContent());
      try {
        parseHDBDD(fileLocation, fileContent);
      } catch (CDSRuntimeException | ArtifactParserException e) {
        this.symbolTable.clearSymbolsByFullName();
        this.symbolTable.clearEntityGraph();
        this.symbolTable.clearViewGraph();
        throw new DataStructuresException(e);
      }
    }

    DataStructureHDBDDModel hdbddModel;
    try {
      hdbddModel = populateDataStructureCdsModel(parametersModel.getLocation(), parametersModel.getContent());
    } catch (CDSRuntimeException e) {
      throw new DataStructuresException("Failed to populate HDBDD model of file: " + parametersModel.getLocation(), e);
    } finally {
      this.symbolTable.clearSymbolsByFullName();
      this.symbolTable.clearEntityGraph();
      this.symbolTable.clearViewGraph();
      parsedNodes.clear();
    }

    return hdbddModel;
  }


  /**
   * Parses the hdbdd.
   *
   * @param location the location
   * @param content  the content
   * @throws IOException             Signals that an I/O exception has occurred.
   * @throws ArtifactParserException the artifact parser exception
   */
  private void parseHDBDD(String location, String content) throws IOException, ArtifactParserException {
    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    CdsLexer hdbddLexer = new CdsLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbddLexer);

    HDBDDErrorListener lexerErrorListener = new HDBDDErrorListener();
    hdbddLexer.removeErrorListeners(); // Remove the ConsoleErrorListener
    hdbddLexer.addErrorListener(lexerErrorListener);

    CdsParser hdbddParser = new CdsParser(tokenStream);
    hdbddParser.setBuildParseTree(true);

    HDBDDErrorListener parserErrorListener = new HDBDDErrorListener();
    hdbddParser.removeErrorListeners();
    hdbddParser.addErrorListener(parserErrorListener);

    ParseTree parseTree = hdbddParser.cdsFile();

    CommonsUtils.logParserErrors(parserErrorListener.getErrors(), CommonsConstants.PARSER_ERROR, location,
        CommonsConstants.HDBDD_PARSER);
    CommonsUtils.logParserErrors(lexerErrorListener.getErrors(), CommonsConstants.LEXER_ERROR, location,
        CommonsConstants.HDBDD_PARSER);

    ArtifactDefinitionListener artifactDefinitionListener = new ArtifactDefinitionListener();
    artifactDefinitionListener.setSymbolTable(symbolTable);
    artifactDefinitionListener.setFileLocation(location);

    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    try {
      parseTreeWalker.walk(artifactDefinitionListener, parseTree);
    } catch (CDSRuntimeException e) {
      CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", e.getMessage(),
          "", CommonsConstants.HDBDD_PARSER, CommonsConstants.MODULE_PARSERS,
          CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
      dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(location), location, HDBDD_ARTEFACT,
          ArtefactState.FAILED_CREATE, e.getMessage());
      throw new CDSRuntimeException(String.format("Failed to parse file: %s. %s", location, e.getMessage()));
    }

    artifactDefinitionListener.getPackagesUsed().forEach(p -> {
      String fileLocation = getFileLocation(p);
      addFileToDependencyTree(fileLocation, location);
      if (!parsedNodes.isEmpty() && parsedNodes.contains(fileLocation)) {
        return;
      }

      try {
        IResource loadedResource = this.repository.getResource("/registry/public/" + fileLocation);
        String loadedResourceContent = new String(loadedResource.getContent());
        parseHDBDD(fileLocation, loadedResourceContent);
        parsedNodes.add(fileLocation);
        synchronizeNodeMetadataFromRoot(fileLocation, loadedResourceContent);
      } catch (IOException | ArtifactParserException | DataStructuresException e) {
        CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", e.getMessage(),
            "", CommonsConstants.HDBDD_PARSER, CommonsConstants.MODULE_PARSERS,
            CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
        dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(location), location, HDBDD_ARTEFACT,
            ArtefactState.FAILED_CREATE, e.getMessage());
      }
    });

    ReferenceResolvingListener referenceResolvingListener = new ReferenceResolvingListener();
    referenceResolvingListener.setCdsFileScope(artifactDefinitionListener.getCdsFileScope());
    referenceResolvingListener.setSymbolTable(symbolTable);
    referenceResolvingListener.setEntityElements(artifactDefinitionListener.getEntityElements());
    referenceResolvingListener.setTypeables(artifactDefinitionListener.getTypeables());
    referenceResolvingListener.setAssociations(artifactDefinitionListener.getAssociations());

    try {
      parseTreeWalker.walk(referenceResolvingListener, parseTree);
    } catch (CDSRuntimeException e) {
      CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", e.getMessage(),
          "", CommonsConstants.HDBDD_PARSER, CommonsConstants.MODULE_PARSERS,
          CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
      dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(location), location, HDBDD_ARTEFACT,
          ArtefactState.FAILED_CREATE, e.getMessage());
      throw new CDSRuntimeException(String.format("Failed to parse file: %s. %s", location, e.getMessage()));
    }
  }

  /**
   * Adds the file to dependency tree.
   *
   * @param nodeFile the node file
   * @param rootFile the root file
   */
  private void addFileToDependencyTree(String nodeFile, String rootFile) {
    Set<String> rootFiles = dependencyStructure.get(nodeFile);
    if (rootFiles == null) {
      rootFiles = new HashSet<>();
    }

    rootFiles.add(rootFile);
    this.dependencyStructure.put(nodeFile, rootFiles);
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public String getType() {
    return IDataStructureModel.TYPE_HDBDD;
  }

  /**
   * Gets the data structure class.
   *
   * @return the data structure class
   */
  @Override
  public Class getDataStructureClass() {
    return DataStructureHDBDDModel.class;
  }

  /**
   * Gets the files to process.
   *
   * @param fileLocation the file location
   * @return the files to process
   */
  private List<String> getFilesToProcess(String fileLocation) {
    List<String> rootFiles = new ArrayList<>();
    getRootFiles(fileLocation, rootFiles);

    return rootFiles;
  }

  /**
   * Gets the root files.
   *
   * @param usedFileName the used file name
   * @param rootFiles    the root files
   * @return the root files
   */
  private void getRootFiles(String usedFileName, List<String> rootFiles) {
    Set<String> userFiles = dependencyStructure.get(usedFileName);
    if (userFiles == null) {
      rootFiles.add(usedFileName);
      return;
    }

    dependencyStructure.get(usedFileName).forEach(f -> {
      getRootFiles(f, rootFiles);
    });
  }

  /**
   * Populate data structure cds model.
   *
   * @param location the location
   * @param content  the content
   * @return the data structure cds model
   */
  private DataStructureHDBDDModel populateDataStructureCdsModel(String location, String content) {
    DataStructureHDBDDModel cdsModel = getCdsModelBaseData(location, content);
    getCdsModelWithParsedData(cdsModel);

    return cdsModel;
  }

  /**
   * Gets the cds model base data.
   *
   * @param location the location
   * @param content  the content
   * @return the cds model base data
   */
  private DataStructureHDBDDModel getCdsModelBaseData(String location, String content) {
    DataStructureHDBDDModel cdsModel = new DataStructureHDBDDModel();
    cdsModel.setName(location);
    cdsModel.setLocation(location);
    cdsModel.setType(getType());
    cdsModel.setCreatedBy(UserFacade.getName());
    cdsModel.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
    cdsModel.setHash(DigestUtils.md5Hex(content));
    cdsModel.setDbContentType(DBContentType.XS_CLASSIC);

    return cdsModel;
  }

  /**
   * Gets the cds model with parsed data.
   *
   * @param cdsModel the cds model
   * @return the cds model with parsed data
   */
  private void getCdsModelWithParsedData(DataStructureHDBDDModel cdsModel) {
    List<EntitySymbol> parsedEntities = this.symbolTable.getSortedEntities();
    List<ViewSymbol> parsedViews = this.symbolTable.getSortedViews();
    List<StructuredDataTypeSymbol> parsedStructuredDataTypes = this.symbolTable.getTableTypes();

    List<DataStructureHDBTableModel> tableModels = new ArrayList<>();
    List<DataStructureHDBTableTypeModel> tableTypeModels = new ArrayList<>();
    List<DataStructureHDBViewModel> viewModels = new ArrayList<>();

    parsedEntities.forEach(e -> {
      tableModels.add(this.hdbddTransformer.transformEntitySymbolToTableModel(e, cdsModel.getLocation()));

      // One HDBDD file has only one schema, which applies to all entities inside the hdbdd file
      cdsModel.setSchema(e.getSchema());
    });

    parsedStructuredDataTypes.forEach(sdt -> {
      if (!(sdt.getAnnotations().containsKey("GenerateTableType")) || (sdt.getAnnotation("GenerateTableType").getKeyValuePairs()
          .get("booleanValue").getValue()).equals("true")) {
        tableTypeModels.add(this.hdbddTransformer.transformStructuredDataTypeToHdbTableType(sdt));
      }
    });

    parsedViews.forEach(v ->
        viewModels.add(this.hdbddTransformer.transformViewSymbolToHdbViewModel(v, cdsModel.getLocation()))
    );

    cdsModel.setTableModels(tableModels);
    cdsModel.setTableTypeModels(tableTypeModels);
    cdsModel.setViewModels(viewModels);
  }

  /**
   * Gets the file location.
   *
   * @param fullPackagePath the full package path
   * @return the file location
   */
  private String getFileLocation(String fullPackagePath) {
    String[] splitPackagePath = fullPackagePath.split("::");
    String directory = splitPackagePath[0];
    String topLevelCdsObject = splitPackagePath[1].split("\\.")[0];
    String fileLocation = directory + "." + topLevelCdsObject;
    fileLocation = fileLocation.replace('.', Constants.UNIX_SEPARATOR);
    fileLocation = fileLocation + ".hdbdd";

    return Constants.UNIX_SEPARATOR + fileLocation;
  }

  /**
   * Synchronize node metadata from root.
   *
   * @param location the location
   * @param content  the content
   * @throws DataStructuresException the data structures exception
   */
  private void synchronizeNodeMetadataFromRoot(String location, String content) throws DataStructuresException {
    DataStructureHDBDDModel nodeCdsModel = getCdsModelBaseData(location, content);
    HDBModule.getManagerServices().get(getType()).synchronizeParsedByRootMetadata(nodeCdsModel);
  }
}