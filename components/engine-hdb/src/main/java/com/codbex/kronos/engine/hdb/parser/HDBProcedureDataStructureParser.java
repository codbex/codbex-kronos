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
package com.codbex.kronos.engine.hdb.parser;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.springframework.stereotype.Component;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.api.IDataStructureModel;
import com.codbex.kronos.engine.hdb.domain.HDBProcedure;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.hana.custom.HanaProcedureListener;
import com.codbex.kronos.parser.hana.exceptions.ProcedureMissingPropertyException;
import com.codbex.kronos.parser.hana.models.ProcedureDefinitionModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

/**
 * The Class HDBProcedureParser.
 */
@Component
public class HDBProcedureDataStructureParser implements HDBDataStructureParser<HDBProcedure> {

  /**
   * Parses the hdbprocedure file.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDB procedure model
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   */
  @Override
  public HDBProcedure parse(HDBParameters parametersModel)
      throws DataStructuresException, ArtifactParserException {

    String location = parametersModel.getLocation();

    ParseTree parseTree = HDBUtils.getParsedThree(parametersModel);

    HanaProcedureListener listener = new HanaProcedureListener();

    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(listener, parseTree);

    ProcedureDefinitionModel antlr4Model = listener.getModel();
    validateAntlrModel(antlr4Model, location);

    return createModel(antlr4Model, parametersModel);
  }

  /**
   * Creates the model.
   *
   * @param antlrModel the antlr model
   * @param params     the params
   * @return the data structure HDB procedure model
   */
  private HDBProcedure createModel(ProcedureDefinitionModel antlrModel, HDBParameters params) {

    HDBDataStructureModelBuilder builder = new HDBDataStructureModelBuilder()
        .withName(antlrModel.getName())
        .withLocation(params.getLocation())
        .withType(getType())
        .withContent(params.getContent())
        .withSchema(antlrModel.getSchema())
        .asClassic(true);

    return new HDBProcedure(builder);

  }

  /**
   * Validate antlr model.
   *
   * @param antlrModel the antlr model
   * @param location   the location
   * @throws DataStructuresException the data structures exception
   */
  private void validateAntlrModel(ProcedureDefinitionModel antlrModel, String location) throws DataStructuresException {
    try {
      antlrModel.checkForAllMandatoryFieldsPresence();
    } catch (ProcedureMissingPropertyException e) {
      logError(location, CommonsConstants.EXPECTED_FIELDS, e.getMessage());
//      dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(location),
//          location,
//          procedureSynchronizationArtefactType,
//          ISynchronizerArtefactType.ArtefactState.FAILED_CREATE,
//          e.getMessage());

      throw new DataStructuresException("Wrong format of HDB Procedure: " + location + " during parsing. ", e);
    }
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public String getType() {
    return IDataStructureModel.TYPE_HDB_PROCEDURE;
  }

  /**
   * Gets the data structure class.
   *
   * @return the data structure class
   */
  @Override
  public Class<HDBProcedure> getDataStructureClass() {
    return HDBProcedure.class;
  }
  
  /**
   * Log error.
   *
   * @param parsedFileLocation the parsed file location
   * @param problemsFacadeMessage the problems facade message
   * @param exceptionMessage the exception message
   */
  public void logError(String parsedFileLocation, String problemsFacadeMessage, String exceptionMessage) {
    CommonsUtils.logCustomErrors(parsedFileLocation,
        CommonsConstants.PARSER_ERROR,
        "", // line
        "", // column
        exceptionMessage,
        problemsFacadeMessage,
        CommonsConstants.HDB_PROCEDURE_PARSER,
        CommonsConstants.MODULE_PARSERS,
        CommonsConstants.SOURCE_PUBLISH_REQUEST,
        CommonsConstants.PROGRAM_KRONOS);
  }

}
