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

import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.hdb.ds.api.IDataStructureModel;
import com.codbex.kronos.hdb.ds.api.DataStructuresException;
import com.codbex.kronos.hdb.ds.artefacts.HDBSynonymSynchronizationArtefactType;
import com.codbex.kronos.hdb.ds.exceptions.HDBSynonymMissingPropertyException;
import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureParametersModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.DataStructureHDBSynonymModel;
import com.codbex.kronos.hdb.ds.model.hdbsynonym.HDBSynonymDefinitionModel;
import com.codbex.kronos.hdb.ds.parser.DataStructureParser;
import com.codbex.kronos.hdb.ds.synchronizer.DataStructuresSynchronizer;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.utils.HDBUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class HDBSynonymParser.
 */
public class HDBSynonymParser implements DataStructureParser {
  
  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(HDBSynonymParser.class);
  
  /** The Constant SYNONYM_ARTEFACT. */
  private static final HDBSynonymSynchronizationArtefactType SYNONYM_ARTEFACT = new HDBSynonymSynchronizationArtefactType();
  
  /** The Constant dataStructuresSynchronizer. */
  private static final DataStructuresSynchronizer dataStructuresSynchronizer = new DataStructuresSynchronizer();

  /**
   * Parses the.
   *
   * @param parametersModel the parameters model
   * @return the data structure HDB synonym model
   * @throws DataStructuresException the data structures exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Override
  public DataStructureHDBSynonymModel parse(DataStructureParametersModel parametersModel) throws DataStructuresException, IOException {
    DataStructureHDBSynonymModel hdbSynonymModel = new DataStructureHDBSynonymModel();
    HDBUtils.populateDataStructureModel(parametersModel.getLocation(), parametersModel.getContent(), hdbSynonymModel,
        IDataStructureModel.TYPE_HDB_SYNONYM, DBContentType.XS_CLASSIC);

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

  /**
   * Gets the type.
   *
   * @return the type
   */
  @Override
  public String getType() {
    return IDataStructureModel.TYPE_HDB_SYNONYM;
  }

  /**
   * Gets the data structure class.
   *
   * @return the data structure class
   */
  @Override
  public Class getDataStructureClass() {
    return DataStructureHDBSynonymModel.class;
  }
}
