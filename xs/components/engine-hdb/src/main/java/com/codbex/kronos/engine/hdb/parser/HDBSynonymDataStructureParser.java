/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.hdb.parser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.api.IDataStructureModel;
import com.codbex.kronos.engine.hdb.domain.HDBSynonym;
import com.codbex.kronos.engine.hdb.domain.HDBSynonymGroup;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * The Class HDBSynonymParser.
 */
@Component
public class HDBSynonymDataStructureParser implements HDBDataStructureParser {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(HDBSynonymDataStructureParser.class);

    /**
     * Parses the hdbsynonym file.
     *
     * @param parametersModel the parameters model
     * @return the data structure HDB synonym model
     * @throws DataStructuresException the data structures exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public HDBSynonymGroup parse(HDBParameters parametersModel) throws DataStructuresException, IOException {
        HDBSynonymGroup hdbSynonymModel = new HDBSynonymGroup();
        HDBUtils.populateDataStructureModel(parametersModel.getLocation(), parametersModel.getContent(), hdbSynonymModel,
                IDataStructureModel.TYPE_HDB_SYNONYM, true);

        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(parametersModel.getContent())
                                          .getAsJsonObject();

        Map<String, HDBSynonym> synonymDefinitions = new HashMap<>();
        for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            HDBSynonym definitionModel = gson.fromJson(entry.getValue(), HDBSynonym.class);
            definitionModel.setName(entry.getKey());
            try {
                definitionModel.checkForAllMandatoryFieldsPresence();
                synonymDefinitions.put(entry.getKey(), definitionModel);
                // aligned with HANA XS Classic, where the synonym name must match the artifact name and multiple
                // synonym definitions are not supported
                // synonymDefinitions.put(hdbSynonymModel.getName(), definitionModel);
            } catch (HDBSynonymMissingPropertyException exception) {
                CommonsUtils.logCustomErrors(parametersModel.getLocation(), CommonsConstants.PARSER_ERROR, "", "",
                        String.format("Missing mandatory field for synonym %s!", entry.getKey()), CommonsConstants.EXPECTED_FIELDS,
                        CommonsConstants.HDB_SYNONYM_PARSER, CommonsConstants.MODULE_PARSERS, CommonsConstants.SOURCE_PUBLISH_REQUEST,
                        CommonsConstants.PROGRAM_KRONOS);
                // dataStructuresSynchronizer.applyArtefactState(entry.getKey(),parametersModel.getLocation(),SYNONYM_ARTEFACT,
                // ArtefactState.FAILED_CREATE, exception.getMessage());
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
        return HDBSynonymGroup.class;
    }
}
