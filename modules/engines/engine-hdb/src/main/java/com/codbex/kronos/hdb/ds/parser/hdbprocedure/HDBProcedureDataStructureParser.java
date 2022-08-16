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
package com.codbex.kronos.hdb.ds.parser.hdbprocedure;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.artefacts.HDBProcedureSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.DataStructureModelBuilder;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbprocedure.DataStructureHDBProcedureModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.parser.hana.custom.HanaProcedureListener;
import com.codbex.kronos.parser.hana.exceptions.ProcedureMissingPropertyException;
import com.codbex.kronos.parser.hana.models.ProcedureDefinitionModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType;

/**
 * The Class HDBProcedureParser.
 */
public class HDBProcedureDataStructureParser implements DataStructureParser<DataStructureHDBProcedureModel> {

  /**
   * The data structures synchronizer.
   */
  private final DataStructuresSynchronizer dataStructuresSynchronizer;

  /**
   * The procedure synchronization artefact type.
   */
  private final HDBProcedureSynchronizationArtefactType procedureSynchronizationArtefactType;

  /**
   * The procedure logger.
   */
  private final HDBProcedureLogger procedureLogger;

  /**
   * Instantiates a new HDB procedure parser.
   *
   * @param dataStructuresSynchronizer           the data structures synchronizer
   * @param procedureSynchronizationArtefactType the procedure synchronization artefact type
   * @param procedureLogger                      the procedure logger
   */
  public HDBProcedureDataStructureParser(DataStructuresSynchronizer dataStructuresSynchronizer,
      HDBProcedureSynchronizationArtefactType procedureSynchronizationArtefactType,
      HDBProcedureLogger procedureLogger) {
    this.dataStructuresSynchronizer = dataStructuresSynchronizer;
    this.procedureSynchronizationArtefactType = procedureSynchronizationArtefactType;
    this.procedureLogger = procedureLogger;
  }

  /**
   * Parses the hdbprocedure file.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDB procedure model
   * @throws DataStructuresException the data structures exception
   * @throws ArtifactParserException the artifact parser exception
   */
  @Override
  public DataStructureHDBProcedureModel parse(DataStructureParametersModel parametersModel)
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
  private DataStructureHDBProcedureModel createModel(ProcedureDefinitionModel antlrModel,
      DataStructureParametersModel params) {

    DataStructureModelBuilder builder = new DataStructureModelBuilder()
        .withName(antlrModel.getName())
        .withHash(DigestUtils.md5Hex(params.getContent())) // NOSONAR
        .createdAt(HDBUtils.getTimestamp())
        .createdBy(UserFacade.getName())
        .withLocation(params.getLocation())
        .withType(getType())
        .rawContent(params.getContent())
        .withSchema(antlrModel.getSchema());

    return new DataStructureHDBProcedureModel(builder);

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
      procedureLogger.logError(location, CommonsConstants.EXPECTED_FIELDS, e.getMessage());
      dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(location),
          location,
          procedureSynchronizationArtefactType,
          ISynchronizerArtefactType.ArtefactState.FAILED_CREATE,
          e.getMessage());

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
  public Class<DataStructureHDBProcedureModel> getDataStructureClass() {
    return DataStructureHDBProcedureModel.class;
  }

}
