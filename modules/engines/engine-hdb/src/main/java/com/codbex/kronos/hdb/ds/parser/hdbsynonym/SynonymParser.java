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
package com.codbex.kronos.hdb.ds.parser.hdbsynonym;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.codbex.kronos.hdb.ds.artefacts.HDBSynonymSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.codbex.kronos.hdb.ds.api.HDBDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.exceptions.HDBSynonymMissingPropertyException;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDataStructureModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDefinitionModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.utils.HDBUtils;

public class SynonymParser implements DataStructureParser {
  private static final Logger logger = LoggerFactory.getLogger(SynonymParser.class);
  private static final HDBSynonymSynchronizationArtefactType SYNONYM_ARTEFACT = new HDBSynonymSynchronizationArtefactType();
  private static final DataStructuresSynchronizer dataStructuresSynchronizer = new DataStructuresSynchronizer();

  @Override
  public HDBSynonymDataStructureModel parse(DataStructureParametersModel parametersModel) throws DataStructuresException, IOException {
    HDBSynonymDataStructureModel hdbSynonymModel = new HDBSynonymDataStructureModel();
    HDBUtils.populateDataStructureModel(parametersModel.getLocation(), parametersModel.getContent(), hdbSynonymModel,
        HDBDataStructureModel.TYPE_HDB_SYNONYM, DBContentType.XS_CLASSIC);

    Gson gson = new Gson();
    JsonParser jsonParser = new JsonParser();
    JsonObject jsonObject = jsonParser.parse(parametersModel.getContent()).getAsJsonObject();

    Map<String, HDBSynonymDefinitionModel> synonymDefinitions = new HashMap<>();
    for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
      HDBSynonymDefinitionModel definitionModel = gson.fromJson(entry.getValue(),
          HDBSynonymDefinitionModel.class);

      try {
        definitionModel.checkForAllMandatoryFieldsPresence();
        synonymDefinitions.put(entry.getKey(), definitionModel);
        //aligned with HANA XS Classic, where the synonym name must match the artifact name and multiple synonym definitions are not supported
        //synonymDefinitions.put(hdbSynonymModel.getName(), definitionModel);
      } catch (HDBSynonymMissingPropertyException exception) {
        CommonsUtils.logCustomErrors(parametersModel.getLocation(), CommonsConstants.PARSER_ERROR, "", "",
            String.format("Missing mandatory field for synonym %s!", entry.getKey()), CommonsConstants.EXPECTED_FIELDS,
            CommonsConstants.HDB_SYNONYM_PARSER,CommonsConstants.MODULE_PARSERS,CommonsConstants.SOURCE_PUBLISH_REQUEST,
            CommonsConstants.PROGRAM_KRONOS);
        dataStructuresSynchronizer.applyArtefactState(entry.getKey(),parametersModel.getLocation(),SYNONYM_ARTEFACT,
            ArtefactState.FAILED_CREATE, exception.getMessage());
      }
    }
    hdbSynonymModel.setSynonymDefinitions(synonymDefinitions);
    return hdbSynonymModel;
  }

  @Override
  public String getType() {
    return HDBDataStructureModel.TYPE_HDB_SYNONYM;
  }

  @Override
  public Class getDataStructureClass() {
    return HDBSynonymDataStructureModel.class;
  }
}
