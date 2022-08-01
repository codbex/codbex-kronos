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
import com.codbex.kronos.hdb.ds.api.HDBDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.artefacts.HDBDDEntitySynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureModel;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbdd.CdsDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtable.HDBTableDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbtabletype.HDBTableTypeDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbview.HDBViewDataStructureModel;
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

public class HDBDDParser implements DataStructureParser {

  private static final Logger logger = LoggerFactory.getLogger(HDBDDParser.class);

  private HdbddTransformer hdbddTransformer = new HdbddTransformer();
  private IRepository repository = (IRepository) StaticObjects.get(StaticObjects.REPOSITORY);
  private SymbolTable symbolTable = new SymbolTable();
  private Map<String, Set<String>> dependencyStructure = new HashMap<>();
  private Set<String> parsedNodes = new HashSet<>();
  private HDBDDEntitySynchronizationArtefactType ENTITY_ARTEFACT = new HDBDDEntitySynchronizationArtefactType();
  private DataStructuresSynchronizer dataStructuresSynchronizer = new DataStructuresSynchronizer();

  @Override
  public DataStructureModel parse(DataStructureParametersModel parametersModel) throws DataStructuresException, IOException {
    for (String fileLocation : this.getFilesToProcess(parametersModel.getLocation())) {
      IResource loadedResource = this.repository.getResource(parametersModel.getWorkspacePath() + fileLocation);
      String fileContent = new String(loadedResource.getContent());
      try {
        parseHdbdd(fileLocation, fileContent);
      } catch (CDSRuntimeException | ArtifactParserException e) {
        this.symbolTable.clearSymbolsByFullName();
        this.symbolTable.clearEntityGraph();
        this.symbolTable.clearViewGraph();
        throw new DataStructuresException(e);
      }
    }

    CdsDataStructureModel cdsModel;
    try {
      cdsModel = populateCdsDataStructureModel(parametersModel.getLocation(), parametersModel.getContent());
    } catch (CDSRuntimeException e) {
      throw new DataStructuresException("Failed to populate CDS model of file: " + parametersModel.getLocation(), e);
    } finally {
      this.symbolTable.clearSymbolsByFullName();
      this.symbolTable.clearEntityGraph();
      this.symbolTable.clearViewGraph();
      parsedNodes.clear();
    }

    return cdsModel;
  }


  private void parseHdbdd(String location, String content) throws IOException, ArtifactParserException {
    ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
    ANTLRInputStream inputStream = new ANTLRInputStream(is);
    CdsLexer hdbddLexer = new CdsLexer(inputStream);
    CommonTokenStream tokenStream = new CommonTokenStream(hdbddLexer);

    HDBDDErrorListener lexerErrorListener = new HDBDDErrorListener();
    hdbddLexer.removeErrorListeners();//remove the ConsoleErrorListener
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
      dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(location), location, ENTITY_ARTEFACT,
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
        parseHdbdd(fileLocation, loadedResourceContent);
        parsedNodes.add(fileLocation);
        synchronizeNodeMetadataFromRoot(fileLocation, loadedResourceContent);
      } catch (IOException | ArtifactParserException | DataStructuresException e) {
        CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", e.getMessage(),
            "", CommonsConstants.HDBDD_PARSER, CommonsConstants.MODULE_PARSERS,
            CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
        dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(location), location, ENTITY_ARTEFACT,
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
      dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(location), location, ENTITY_ARTEFACT,
          ArtefactState.FAILED_CREATE, e.getMessage());
      throw new CDSRuntimeException(String.format("Failed to parse file: %s. %s", location, e.getMessage()));
    }
  }

  private void addFileToDependencyTree(String nodeFile, String rootFile) {
    Set<String> rootFiles = dependencyStructure.get(nodeFile);
    if (rootFiles == null) {
      rootFiles = new HashSet<>();
    }

    rootFiles.add(rootFile);
    this.dependencyStructure.put(nodeFile, rootFiles);
  }

  @Override
  public String getType() {
    return HDBDataStructureModel.TYPE_HDBDD;
  }

  @Override
  public Class getDataStructureClass() {
    return CdsDataStructureModel.class;
  }

  private List<String> getFilesToProcess(String fileLocation) {
    List<String> rootFiles = new ArrayList<>();
    getRootFiles(fileLocation, rootFiles);

    return rootFiles;
  }

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

  private CdsDataStructureModel populateCdsDataStructureModel(String location, String content) {
    CdsDataStructureModel cdsModel = getCdsModelBaseData(location, content);
    getCdsModelWithParsedData(cdsModel);

    return cdsModel;
  }

  private CdsDataStructureModel getCdsModelBaseData(String location, String content) {
    CdsDataStructureModel cdsModel = new CdsDataStructureModel();
    cdsModel.setName(location);
    cdsModel.setLocation(location);
    cdsModel.setType(getType());
    cdsModel.setCreatedBy(UserFacade.getName());
    cdsModel.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
    cdsModel.setHash(DigestUtils.md5Hex(content));
    cdsModel.setDbContentType(DBContentType.XS_CLASSIC);

    return cdsModel;
  }

  private void getCdsModelWithParsedData(CdsDataStructureModel cdsModel) {
    List<EntitySymbol> parsedEntities = this.symbolTable.getSortedEntities();
    List<ViewSymbol> parsedViews = this.symbolTable.getSortedViews();

    List<HDBTableDataStructureModel> tableModels = new ArrayList<>();
    parsedEntities.forEach(e -> {
      tableModels.add(this.hdbddTransformer.transformEntitySymbolToTableModel(e, cdsModel.getLocation()));

      //One HDBDD file have only one schema, with apply to all entities inside the hdbdd file
      cdsModel.setSchema(e.getSchema());
    });

    List<StructuredDataTypeSymbol> structuredDataTypes = this.symbolTable.getTableTypes();
    List<HDBTableTypeDataStructureModel> hdbTableTypeModels = new ArrayList<>();
    structuredDataTypes.forEach(sdt -> {
      if (!(sdt.getAnnotations().containsKey("GenerateTableType")) || (sdt.getAnnotation("GenerateTableType").getKeyValuePairs()
          .get("booleanValue").getValue()).equals("true")) {
        hdbTableTypeModels.add(this.hdbddTransformer.transformStructuredDataTypeToHdbTableType(sdt));
      }
    });

    List<HDBViewDataStructureModel> viewModels = new ArrayList<>();
    parsedViews.forEach(v ->
        viewModels.add(this.hdbddTransformer.transformViewSymbolToHdbViewModel(v, cdsModel.getLocation()))
    );

    cdsModel.setTableModels(tableModels);
    cdsModel.setTableTypeModels(hdbTableTypeModels);
    cdsModel.setViewModels(viewModels);
  }

  private String getFileLocation(String fullPackagePath) {
    String[] splitPackagePath = fullPackagePath.split("::");
    String directory = splitPackagePath[0];
    String topLevelCdsObject = splitPackagePath[1].split("\\.")[0];
    String fileLocation = directory + "." + topLevelCdsObject;
    fileLocation = fileLocation.replace('.', Constants.UNIX_SEPARATOR);
    fileLocation = fileLocation + ".hdbdd";

    return Constants.UNIX_SEPARATOR + fileLocation;
  }

  private void synchronizeNodeMetadataFromRoot(String location, String content) throws DataStructuresException {
    CdsDataStructureModel nodeCdsModel = getCdsModelBaseData(location, content);
    HDBModule.getManagerServices().get(getType()).synchronizeParsedByRootMetadata(nodeCdsModel);
  }
}