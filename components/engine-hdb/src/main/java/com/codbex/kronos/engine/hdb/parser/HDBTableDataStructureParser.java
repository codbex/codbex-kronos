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

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.api.IDataStructureModel;
import com.codbex.kronos.engine.hdb.domain.HDBTable;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintPrimaryKey;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraintUnique;
import com.codbex.kronos.engine.hdb.domain.HDBTableConstraints;
import com.codbex.kronos.engine.hdb.domain.HDBTableIndex;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.hdbtable.core.HDBTableLexer;
import com.codbex.kronos.parser.hdbtable.core.HDBTableParser;
import com.codbex.kronos.parser.hdbtable.custom.HDBTableDefinitionVisitor;
import com.codbex.kronos.parser.hdbtable.custom.HDBTableSyntaxErrorListener;
import com.codbex.kronos.parser.hdbtable.exceptions.HDBTableMissingPropertyException;
import com.codbex.kronos.parser.hdbtable.model.HDBTableColumnsModel;
import com.codbex.kronos.parser.hdbtable.model.HDBTableDefinitionModel;
import com.codbex.kronos.parser.hdbtable.model.HDBTableIndexesModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The Class HDBTableParser.
 */
@Component
public class HDBTableDataStructureParser implements HDBDataStructureParser<HDBTable> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(HDBTableDataStructureParser.class);

    private static final Pattern SCHEMA_PATTERN = Pattern.compile(".+TABLE \"([^\"]+)\"\\.\"([^\"]+)\"", Pattern.CASE_INSENSITIVE);

    /**
     * The column model transformer.
     */
    private final HDBTableColumnModelTransformer columnModelTransformer = new HDBTableColumnModelTransformer();

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public String getType() {
        return IDataStructureModel.TYPE_HDB_TABLE;
    }

    /**
     * Gets the data structure class.
     *
     * @return the data structure class
     */
    @Override
    public Class<HDBTable> getDataStructureClass() {
        return HDBTable.class;
    }

    /**
     * Parses the hdbtable file.
     *
     * @param parametersModel the parameters model
     * @return the data structure HDB table model
     * @throws DataStructuresException the data structures exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ArtifactParserException the artifact parser exception
     */
    @Override
    public HDBTable parse(HDBParameters parametersModel) throws DataStructuresException, IOException, ArtifactParserException {
        Pattern pattern = Pattern.compile("^(\\t\\n)*(\\s)*(COLUMN)(\\t\\n)*(\\s)*(TABLE)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(parametersModel.getContent()
                                                         .trim()
                                                         .toUpperCase(Locale.ROOT));
        boolean matchFound = matcher.find();
        return (matchFound) ? parseHanaXSAdvancedContent(parametersModel.getLocation(), parametersModel.getContent())
                : parseHanaXSClassicContent(parametersModel.getLocation(), parametersModel.getContent());
    }

    /**
     * Parses the hana XS classic content.
     *
     * @param location the location
     * @param content the content
     * @return the data structure HDB table model
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ArtifactParserException the artifact parser exception
     */
    private HDBTable parseHanaXSClassicContent(String location, String content) throws IOException, ArtifactParserException {
        logger.debug("Parsing hdbtable as Hana XS Classic format");
        ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes());
        ANTLRInputStream inputStream = new ANTLRInputStream(is);
        HDBTableLexer hdbTableLexer = new HDBTableLexer(inputStream);
        HDBTableSyntaxErrorListener lexerErrorListener = new HDBTableSyntaxErrorListener();
        hdbTableLexer.removeErrorListeners();
        hdbTableLexer.addErrorListener(lexerErrorListener);
        CommonTokenStream tokenStream = new CommonTokenStream(hdbTableLexer);

        HDBTableParser hdbTableParser = new HDBTableParser(tokenStream);
        hdbTableParser.setBuildParseTree(true);
        hdbTableParser.removeErrorListeners();

        HDBTableSyntaxErrorListener parserErrorListener = new HDBTableSyntaxErrorListener();
        hdbTableParser.addErrorListener(parserErrorListener);
        ParseTree parseTree = hdbTableParser.hdbTableDefinition();
        CommonsUtils.logParserErrors(parserErrorListener.getErrors(), CommonsConstants.PARSER_ERROR, location,
                CommonsConstants.HDB_TABLE_PARSER);
        CommonsUtils.logParserErrors(lexerErrorListener.getErrors(), CommonsConstants.LEXER_ERROR, location,
                CommonsConstants.HDB_TABLE_PARSER);

        HDBTableDefinitionVisitor hdbtableCoreVisitor = new HDBTableDefinitionVisitor();

        JsonElement parsedResult = hdbtableCoreVisitor.visit(parseTree);

        Gson gson = new Gson();

        HDBTableDefinitionModel hdbtableDefinitionModel = gson.fromJson(parsedResult, HDBTableDefinitionModel.class);
        String artefactName = CommonsUtils.getRepositoryBaseObjectName(location);
        try {
            hdbtableDefinitionModel.checkForAllMandatoryFieldsPresence();
        } catch (Exception e) {
            CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", e.getMessage(), CommonsConstants.EXPECTED_FIELDS,
                    CommonsConstants.HDB_TABLE_PARSER, CommonsConstants.MODULE_PARSERS, CommonsConstants.SOURCE_PUBLISH_REQUEST,
                    CommonsConstants.PROGRAM_KRONOS);
            // dataStructuresSynchronizer.applyArtefactState(artefactName,location,TABLE_ARTEFACT,
            // ArtefactState.FAILED_CREATE, e.getMessage());
            throw new HDBTableMissingPropertyException(
                    String.format("Wrong format of table definition: [%s]. [%s]", location, e.getMessage()));
        }

        HDBTable hdbTableModel = new HDBTable();

        HDBUtils.populateDataStructureModel(location, content, hdbTableModel, IDataStructureModel.TYPE_HDB_TABLE, true);
        hdbTableModel.setSchema(hdbtableDefinitionModel.getSchemaName());
        hdbTableModel.setDescription(hdbtableDefinitionModel.getDescription());
        hdbTableModel.setLoggingType(hdbtableDefinitionModel.getLoggingType());
        hdbTableModel.setIsPublic(hdbtableDefinitionModel.isPublic());
        hdbTableModel.setIsTemporary(hdbtableDefinitionModel.getTemporary());
        hdbTableModel.setTableType(hdbtableDefinitionModel.getTableType());
        hdbTableModel.setContent(content);
        hdbTableModel.setColumns(columnModelTransformer.transform(hdbtableDefinitionModel, location));
        hdbTableModel.setConstraints(new HDBTableConstraints());

        HDBTableConstraintPrimaryKey primaryKey = new HDBTableConstraintPrimaryKey();
        primaryKey.setColumns(hdbtableDefinitionModel.getPkColumns()
                                                     .toArray(String[]::new));
        primaryKey.setName("PK_" + hdbTableModel.getName());
        hdbTableModel.getConstraints()
                     .setPrimaryKey(primaryKey);

        hdbtableDefinitionModel.getPkColumns()
                               .forEach(key -> {
                                   List<HDBTableColumnsModel> foundMatchKey = hdbtableDefinitionModel.getColumns()
                                                                                                     .stream()
                                                                                                     .filter(x -> x.getName()
                                                                                                                   .equals(key))
                                                                                                     .collect(Collectors.toList());
                                   if (foundMatchKey.size() != 1) {
                                       String errMsg = String.format(
                                               "%s: the column does not have a definition but is specified as a primary key", key);
                                       CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", errMsg, "",
                                               CommonsConstants.HDB_TABLE_PARSER, CommonsConstants.MODULE_PARSERS,
                                               CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
                                       // dataStructuresSynchronizer.applyArtefactState(artefactName,location,TABLE_ARTEFACT,
                                       // ArtefactState.FAILED_CREATE, errMsg);
                                       throw new IllegalStateException(errMsg);
                                   }
                               });

        List<HDBTableConstraintUnique> uniqueIndices = new ArrayList<>();
        List<HDBTableIndex> indexes = new ArrayList<>();

        if (hdbtableDefinitionModel.getIndexes() != null) {
            for (HDBTableIndexesModel index : hdbtableDefinitionModel.getIndexes()) {
                validateIndex(hdbtableDefinitionModel, location, index);
                if (index.isUnique()) {
                    HDBTableConstraintUnique hdbTableConstraintUniqueModel = new HDBTableConstraintUnique();
                    if (index.getIndexName() != null) {
                        hdbTableConstraintUniqueModel.setName(index.getIndexName());
                    }
                    if (index.getIndexType() != null) {
                        hdbTableConstraintUniqueModel.setIndexType(this.convertIfHanaClassicSyntax(index));
                    }
                    if (index.getOrder() != null) {
                        hdbTableConstraintUniqueModel.setOrder(index.getOrder()
                                                                    .equals(Constants.HDBTABLE_INDEX_ORDER_HANA_V1_DSC)
                                                                            ? Constants.HDBTABLE_INDEX_ORDER_HANA_DESC
                                                                            : index.getOrder());
                    }
                    hdbTableConstraintUniqueModel.setColumns(index.getIndexColumns()
                                                                  .toArray(String[]::new));

                    uniqueIndices.add(hdbTableConstraintUniqueModel);
                } else {
                    HDBTableIndex hdbTableIndexModel = new HDBTableIndex();
                    if (index.getIndexName() != null) {
                        hdbTableIndexModel.setName(index.getIndexName());
                    }
                    if (index.getIndexType() != null) {
                        hdbTableIndexModel.setType(convertIfHanaClassicSyntax(index));
                    }
                    hdbTableIndexModel.setUnique(index.isUnique());
                    if (index.getOrder() != null) {
                        hdbTableIndexModel.setOrder(index.getOrder()
                                                         .equals(Constants.HDBTABLE_INDEX_ORDER_HANA_V1_DSC)
                                                                 ? Constants.HDBTABLE_INDEX_ORDER_HANA_DESC
                                                                 : index.getOrder());
                    }
                    hdbTableIndexModel.setColumns(index.getIndexColumns()
                                                       .toArray(String[]::new));
                    indexes.add(hdbTableIndexModel);
                }
            }
            hdbTableModel.getConstraints()
                         .setUniqueIndexes(uniqueIndices);
            hdbTableModel.setIndexes(indexes);
        }

        return hdbTableModel;
    }

    /**
     * Parses the hana XS advanced content.
     *
     * @param location the location
     * @param content the content
     * @return the data structure HDB table model
     */
    private HDBTable parseHanaXSAdvancedContent(String location, String content) {
        logger.debug("Parsing hdbtable as Hana XS Advanced format");
        HDBTable dataStructureHDBTableModel = new HDBTable();
        HDBUtils.populateDataStructureModel(location, content, dataStructureHDBTableModel, IDataStructureModel.TYPE_HDB_TABLE, false);
        dataStructureHDBTableModel.setContent(content);

        Optional<String> schema = extractSchema(content);
        if (schema.isPresent()) {
            dataStructureHDBTableModel.setSchema(schema.get());
        }

        return dataStructureHDBTableModel;
    }

    private Optional<String> extractSchema(String content) {
        Matcher matcher = SCHEMA_PATTERN.matcher(content);
        return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
    }

    /**
     * Validate index.
     *
     * @param hdbtableDefinitionModel the hdbtable definition model
     * @param location the location
     * @param index the index
     */
    private void validateIndex(HDBTableDefinitionModel hdbtableDefinitionModel, String location, HDBTableIndexesModel index) {
        String artefactName = CommonsUtils.getRepositoryBaseObjectName(location);
        try {
            index.checkForAllIndexMandatoryFieldsPresence();
        } catch (Exception e) {
            CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", e.getMessage(), CommonsConstants.EXPECTED_FIELDS,
                    CommonsConstants.HDB_TABLE_PARSER, CommonsConstants.MODULE_PARSERS, CommonsConstants.SOURCE_PUBLISH_REQUEST,
                    CommonsConstants.PROGRAM_KRONOS);
            // dataStructuresSynchronizer.applyArtefactState(artefactName,location,TABLE_ARTEFACT,
            // ArtefactState.FAILED_CREATE, e.getMessage());
            throw new HDBTableMissingPropertyException(
                    String.format("Wrong format of table definition: [%s]. [%s]", location, e.getMessage()));
        }

        index.getIndexColumns()
             .forEach(col -> {
                 List<HDBTableColumnsModel> foundMatch = hdbtableDefinitionModel.getColumns()
                                                                                .stream()
                                                                                .filter(x -> x.getName()
                                                                                              .equals(col))
                                                                                .collect(Collectors.toList());
                 if (foundMatch.size() != 1) {
                     String errMsg = String.format("%s: the column does not have a definition but is specified as an index", col);
                     CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", errMsg, "",
                             CommonsConstants.HDB_TABLE_PARSER, CommonsConstants.MODULE_PARSERS, CommonsConstants.SOURCE_PUBLISH_REQUEST,
                             CommonsConstants.PROGRAM_KRONOS);
                     // dataStructuresSynchronizer.applyArtefactState(artefactName,location,TABLE_ARTEFACT,
                     // ArtefactState.FAILED_CREATE, errMsg);
                     throw new IllegalStateException(errMsg);
                 }
             });
    }

    /**
     * Convert if hana classic syntax.
     *
     * @param index the index
     * @return the string
     */
    private String convertIfHanaClassicSyntax(HDBTableIndexesModel index) {
        if (index.getIndexType()
                 .equals("CPB_TREE")) {
            return "CPBTREE";
        } else if (index.getIndexType()
                        .equals("B_TREE")) {
            return "BTREE";
        } else {
            return index.getIndexType();
        }
    }

}
