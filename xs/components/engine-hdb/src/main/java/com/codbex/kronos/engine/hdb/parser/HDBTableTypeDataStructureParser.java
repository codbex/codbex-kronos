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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.api.IDataStructureModel;
import com.codbex.kronos.engine.hdb.domain.HDBTableType;
import com.codbex.kronos.exceptions.ArtifactParserException;

/**
 * The Class HDBTableTypeParser.
 */
@Component
public class HDBTableTypeDataStructureParser implements HDBDataStructureParser<HDBTableType> {

    // TYPE (?:["'](.*)["'].)?["'](.*)["']
    // uses non-capturing group in order to handle the
    // possible case of a table type with only a name and no schema
    /** The Constant TABLE_TYPE_SCHEMA_AND_NAME_PATTERN. */
    private static final Pattern TABLE_TYPE_SCHEMA_AND_NAME_PATTERN = Pattern.compile("TYPE\\s+(?:[\"'](.*)[\"'].)?[\"'](.*)[\"']");

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(HDBTableTypeDataStructureParser.class);

    /**
     * Parses the hdbtabletype file.
     *
     * @param parametersModel the parameters model
     * @return the data structure HDB table type model
     * @throws DataStructuresException the data structures exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ArtifactParserException the artifact parser exception
     */
    @Override
    public HDBTableType parse(HDBParameters parametersModel) throws DataStructuresException, IOException, ArtifactParserException {
        logger.debug("Parsing HDB Table Type as Hana XS Advanced format");
        HDBTableType hdbTableTypeModel = new HDBTableType();

        HDBUtils.populateDataStructureModel(parametersModel.getLocation(), parametersModel.getContent(), hdbTableTypeModel,
                IDataStructureModel.TYPE_HDB_TABLE_TYPE, false);
        Pair<String, String> schemaAndNamePair = extractTableTypeSchemaAndName(parametersModel.getContent());

        hdbTableTypeModel.setSchema(schemaAndNamePair.getLeft());
        hdbTableTypeModel.setName(schemaAndNamePair.getRight());
        hdbTableTypeModel.setType(getType());
        hdbTableTypeModel.setContent(parametersModel.getContent());

        return hdbTableTypeModel;
    }

    /**
     * Extract table type schema and name.
     *
     * @param content the content
     * @return the pair
     */
    private Pair<String, String> extractTableTypeSchemaAndName(String content) {
        String contentWithoutPossibleComments = HDBUtils.removeSqlCommentsFromContent(content);
        Matcher matcher = TABLE_TYPE_SCHEMA_AND_NAME_PATTERN.matcher(contentWithoutPossibleComments);

        if (!matcher.find()) {
            throw new IllegalStateException("Couldn't extract table type schema and name from content: " + content);
        }

        return Pair.of(matcher.group(1), matcher.group(2));
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public String getType() {
        return IDataStructureModel.TYPE_HDB_TABLE_TYPE;
    }

    /**
     * Gets the data structure class.
     *
     * @return the data structure class
     */
    @Override
    public Class<HDBTableType> getDataStructureClass() {
        return HDBTableType.class;
    }
}
