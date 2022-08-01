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
import com.codbex.kronos.hdb.ds.api.HDBDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.artefacts.HDBProcedureSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.DataStructureModelBuilder;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbprocedure.HDBProcedureDataStructureModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;
import custom.HanaProcedureListener;
import models.HDBProcedureDefinitionModel;
import models.HDBProcedureMissingPropertyException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.dirigible.api.v3.security.UserFacade;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType;

public class HDBProcedureParser implements DataStructureParser<HDBProcedureDataStructureModel> {

    private final DataStructuresSynchronizer dataStructuresSynchronizer;
    private final HDBProcedureSynchronizationArtefactType procedureSynchronizationArtefactType;
    private final HDBProcedureLogger procedureLogger;


    public HDBProcedureParser(DataStructuresSynchronizer dataStructuresSynchronizer,
                                 HDBProcedureSynchronizationArtefactType procedureSynchronizationArtefactType,
                                 HDBProcedureLogger procedureLogger) {
        this.dataStructuresSynchronizer = dataStructuresSynchronizer;
        this.procedureSynchronizationArtefactType = procedureSynchronizationArtefactType;
        this.procedureLogger = procedureLogger;
    }

    @Override
    public HDBProcedureDataStructureModel parse(DataStructureParametersModel parametersModel)
            throws DataStructuresException, ArtifactParserException {

        String location = parametersModel.getLocation();

        ParseTree parseTree = HDBUtils.getParsedThree(parametersModel);

        HanaProcedureListener listener = new HanaProcedureListener();

        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        parseTreeWalker.walk(listener, parseTree);

        HDBProcedureDefinitionModel antlr4Model = listener.getModel();
        validateAntlrModel(antlr4Model, location);

        return createModel(antlr4Model, parametersModel);
    }

    private HDBProcedureDataStructureModel createModel(HDBProcedureDefinitionModel antlrModel,
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

      return new HDBProcedureDataStructureModel(builder);

    }

    private void validateAntlrModel(HDBProcedureDefinitionModel antlrModel, String location) throws DataStructuresException {
        try {
            antlrModel.checkForAllMandatoryFieldsPresence();
        } catch (HDBProcedureMissingPropertyException e) {
            procedureLogger.logError(location, CommonsConstants.EXPECTED_FIELDS, e.getMessage());
            dataStructuresSynchronizer.applyArtefactState(CommonsUtils.getRepositoryBaseObjectName(location),
                    location,
                    procedureSynchronizationArtefactType,
                    ISynchronizerArtefactType.ArtefactState.FAILED_CREATE,
                    e.getMessage());

            throw new DataStructuresException("Wrong format of HDB Procedure: " + location + " during parsing. ", e);
        }
    }

    @Override
    public String getType() {
        return HDBDataStructureModel.TYPE_HDB_PROCEDURE;
    }

    @Override
    public Class<HDBProcedureDataStructureModel> getDataStructureClass() {
        return HDBProcedureDataStructureModel.class;
    }

}
