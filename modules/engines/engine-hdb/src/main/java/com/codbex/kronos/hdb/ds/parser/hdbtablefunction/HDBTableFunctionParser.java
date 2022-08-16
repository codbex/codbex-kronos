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
package com.codbex.kronos.hdb.ds.parser.hdbtablefunction;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableFunctionSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.DataStructureModelBuilder;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.DataStructureHDBTableFunctionModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.parser.hana.core.custom.HanaTableFunctionListener;
import com.codbex.kronos.parser.hana.core.exceptions.TableFunctionMissingPropertyException;
import com.codbex.kronos.parser.hana.core.models.TableFunctionDefinitionModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;

import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;

/**
 * The Class HDBTableFunctionParser.
 */
public class HDBTableFunctionParser implements DataStructureParser<DataStructureHDBTableFunctionModel> {

  /**
   * The data structures synchronizer.
   */
  private final DataStructuresSynchronizer dataStructuresSynchronizer;

  /**
   * The table function synchronization artefact type.
   */
  private final HDBTableFunctionSynchronizationArtefactType tableFunctionSynchronizationArtefactType;

  /**
   * The table function logger.
   */
  private final HDBTableFunctionLogger tableFunctionLogger;

  /**
   * Instantiates a new HDB table function parser.
   *
   * @param dataStructuresSynchronizer               the data structures synchronizer
   * @param tableFunctionSynchronizationArtefactType the table function synchronization artefact type
   * @param tableFunctionLogger                      the table function logger
   */
  public HDBTableFunctionParser(
      DataStructuresSynchronizer dataStructuresSynchronizer,
      HDBTableFunctionSynchronizationArtefactType tableFunctionSynchronizationArtefactType,
      HDBTableFunctionLogger tableFunctionLogger
  ) {
    this.dataStructuresSynchronizer = dataStructuresSynchronizer;
    this.tableFunctionSynchronizationArtefactType = tableFunctionSynchronizationArtefactType;
    this.tableFunctionLogger = tableFunctionLogger;
  }

  /**
   * Parses the hdbtablefunction file.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDB table function model
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   */
  @Override
  public DataStructureHDBTableFunctionModel parse(DataStructureParametersModel parametersModel)
      throws DataStructuresException, ArtifactParserException {

    String location = parametersModel.getLocation();

    ParseTree parseTree = HDBUtils.getParsedThree(parametersModel);

    HanaTableFunctionListener listener = new HanaTableFunctionListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(listener, parseTree);

    TableFunctionDefinitionModel antlr4Model = listener.getModel();
    validateAntlrModel(antlr4Model, location);

    return createModel(antlr4Model, parametersModel);
  }

  /**
   * Creates the model.
   *
   * @param antlrModel the antlr model
   * @param params     the params
   * @return the data structure HDB table function model
   */
  private DataStructureHDBTableFunctionModel createModel(TableFunctionDefinitionModel antlrModel,
      DataStructureParametersModel params) {

    DataStructureModelBuilder builder = new DataStructureModelBuilder()
        .withName(antlrModel.getName())
        .withType(getType())
        .withHash(DigestUtils.md5Hex(params.getContent())) // NOSONAR
        .createdAt(HDBUtils.getTimestamp())
        .createdBy(UserFacade.getName())
        .withLocation(params.getLocation())
        .withType(getType())
        .rawContent(params.getContent())
        .withSchema(antlrModel.getSchema());

    return new DataStructureHDBTableFunctionModel(builder);
  }

  /**
   * Validate antlr model.
   *
   * @param antlrModel the antlr model
   * @param location   the location
   * @throws DataStructuresException the data structures exception
   */
  private void validateAntlrModel(TableFunctionDefinitionModel antlrModel, String location) throws DataStructuresException {
    try {
      antlrModel.checkForAllMandatoryFieldsPresence();
    } catch (TableFunctionMissingPropertyException e) {
      tableFunctionLogger.logError(location, CommonsConstants.EXPECTED_FIELDS, e.getMessage());
      dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(location),
          location,
          tableFunctionSynchronizationArtefactType,
          ArtefactState.FAILED_CREATE,
          e.getMessage());

      throw new DataStructuresException("Wrong format of HDB Table Function: " + location + " during parsing. ", e);
    }
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public String getType() {
    return IDataStructureModel.TYPE_HDB_TABLE_FUNCTION;
  }

  /**
   * Gets the data structure class.
   *
   * @return the data structure class
   */
  @Override
  public Class<DataStructureHDBTableFunctionModel> getDataStructureClass() {
    return DataStructureHDBTableFunctionModel.class;
  }
}
