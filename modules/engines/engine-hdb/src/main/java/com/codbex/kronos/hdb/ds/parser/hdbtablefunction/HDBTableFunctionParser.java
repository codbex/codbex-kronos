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
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.hdb.ds.artefacts.HDBTableFunctionSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.DataStructureModelBuilder;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;
import custom.HanaTableFunctionListener;
import models.TableFunctionDefinitionModel;
import models.TableFunctionMissingPropertyException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;
import com.codbex.kronos.hdb.ds.api.HDBDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.model.hdbtablefunction.HDBTableFunctionDataStructureModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;

public class HDBTableFunctionParser implements DataStructureParser<HDBTableFunctionDataStructureModel> {

  private final DataStructuresSynchronizer dataStructuresSynchronizer;
  private final HDBTableFunctionSynchronizationArtefactType tableFunctionSynchronizationArtefactType;
  private final HDBTableFunctionLogger tableFunctionLogger;

  public HDBTableFunctionParser(
      DataStructuresSynchronizer dataStructuresSynchronizer,
      HDBTableFunctionSynchronizationArtefactType tableFunctionSynchronizationArtefactType,
      HDBTableFunctionLogger tableFunctionLogger
  ) {
    this.dataStructuresSynchronizer = dataStructuresSynchronizer;
    this.tableFunctionSynchronizationArtefactType = tableFunctionSynchronizationArtefactType;
    this.tableFunctionLogger = tableFunctionLogger;
  }

  @Override
  public HDBTableFunctionDataStructureModel parse(DataStructureParametersModel parametersModel)
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

  private HDBTableFunctionDataStructureModel createModel(TableFunctionDefinitionModel antlrModel,
      DataStructureParametersModel params) {

    DataStructureModelBuilder builder = new DataStructureModelBuilder()
        .withName(antlrModel.getName())
        .withHash(DigestUtils.md5Hex(params.getContent()))//NOSONAR
        .createdAt(HDBUtils.getTimestamp())
        .createdBy(UserFacade.getName())
        .withLocation(params.getLocation())
        .withType(getType())
        .rawContent(params.getContent())
        .withSchema(antlrModel.getSchema());

    return new HDBTableFunctionDataStructureModel(builder);
  }

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

  @Override
  public String getType() {
    return HDBDataStructureModel.TYPE_HDB_TABLE_FUNCTION;
  }

  @Override
  public Class<HDBTableFunctionDataStructureModel> getDataStructureClass() {
    return HDBTableFunctionDataStructureModel.class;
  }
}
