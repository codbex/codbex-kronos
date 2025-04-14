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
import com.codbex.kronos.engine.hdb.domain.*;
import com.codbex.kronos.engine.hdb.processors.HDBSynonymCreateProcessor;
import com.codbex.kronos.engine.hdb.processors.HDBSynonymDropProcessor;
import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.hana.core.HanaLexer;
import com.codbex.kronos.parser.hana.core.HanaParser;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.lang3.math.NumberUtils;
import org.eclipse.dirigible.components.api.security.UserFacade;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.codbex.kronos.utils.CommonsConstants.*;

/**
 * The Class HDBUtils.
 */
public class HDBUtils {

    /**
     * The Constant commentRegex.
     */
    private static final String commentRegex = "(/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/)|(--.*)";

    private static final String SQL_TYPES =
            "ARRAY|DATE|SECONDDATE|TIMESTAMP|TIME|TINYINT|SMALLINT|INTEGER|INT|BIGINT|SMALLDECIMAL|REAL|DOUBLE|TEXT|BINTEXT|VARCHAR|NVARCHAR|ALPHANUM|SHORTTEXT|VARBINARY|DECIMAL|FLOAT|BOOLEAN";
    private static final String COLUMN_NAME_REGEX = "\"?(\\w+)\"?\\s*(" + SQL_TYPES + ")\\s*(?:\\((\\d+)(?:,\\s*(\\d+))?\\))?";
    private static final Pattern COLUMN_PATTERN = Pattern.compile(COLUMN_NAME_REGEX, Pattern.CASE_INSENSITIVE);

    private static final Pattern TABLE_CONTENT_SOURCE_PATTERN = Pattern.compile("\\((.*)\\)", Pattern.DOTALL);
    /**
     * The Constant ESCAPE_SYMBOL.
     */
    private static final String ESCAPE_SYMBOL = "\"";

    /**
     * Instantiates a new HDB utils.
     */
    private HDBUtils() {}

    /**
     * Escape artifact name
     *
     * @param artifactName name of the artifact
     * @param schemaName name of the schema that will be assembled to the artifact name
     * @return escaped in quotes artifact name
     */
    public static String escapeArtifactName(String artifactName, String schemaName) {
        if (!artifactName.startsWith(ESCAPE_SYMBOL)) {
            artifactName = ESCAPE_SYMBOL + artifactName + ESCAPE_SYMBOL;
        }

        if (schemaName != null && !schemaName.trim()
                                             .isEmpty()) {
            if (!schemaName.startsWith(ESCAPE_SYMBOL)) {
                schemaName = ESCAPE_SYMBOL + schemaName + ESCAPE_SYMBOL + ".";
            }
        } else {
            schemaName = "";
        }

        return schemaName + artifactName;
    }

    /**
     * See also {@link #escapeArtifactName(String, String)}.
     *
     * @param artifactName the artifact name
     * @return the string
     */
    public static String escapeArtifactName(String artifactName) {
        return escapeArtifactName(artifactName, null);
    }

    /**
     * Populate data structure model.
     *
     * @param location the location
     * @param content the content
     * @param model the model
     * @param artifactType the artifact type
     * @param classic the classic
     */
    public static void populateDataStructureModel(String location, String content, HDBDataStructure model, String artifactType,
            Boolean classic) {
        model.setName(CommonsUtils.getRepositoryBaseObjectName(location));
        model.setLocation(location);
        model.setType(artifactType);
        model.setCreatedBy(UserFacade.getName());
        model.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
        model.setClassic(classic);
    }

    public static List<HDBTableColumn> extractColumns(String content) {
        String tableContentSource = extractTableContentSource(content);
        Matcher columnMatcher = COLUMN_PATTERN.matcher(tableContentSource);

        return columnMatcher.results()
                            .map(matchResult -> {
                                HDBTableColumn column = new HDBTableColumn();
                                String name = matchResult.group(1);
                                column.setName(name);
                                String type = matchResult.group(2);
                                column.setType(type);
                                String precision = matchResult.group(3);
                                String scale = matchResult.group(4);
                                if (null != precision && null == scale && NumberUtils.isParsable(precision.trim())) {
                                    String length = precision.trim();
                                    column.setLength(length);
                                }
                                return column;
                            })
                            .collect(Collectors.toList());
    }

    private static String extractTableContentSource(String content) {
        Matcher tableContentSourceMatcher = TABLE_CONTENT_SOURCE_PATTERN.matcher(content);
        if (!tableContentSourceMatcher.find()) {
            throw new IllegalArgumentException("Invalid content [" + content + "]. It doesn't match the pattern ["
                    + TABLE_CONTENT_SOURCE_PATTERN + "]. Most probably this is invalid content.");
        }
        return tableContentSourceMatcher.group(1);
    }

    public static HDBTableConstraintPrimaryKey extractPrimayKeyColumns(String content) {
        String tableContentSource = extractTableContentSource(content);
        List<String> lines = splitAndTrimLines(tableContentSource);
        Pattern pattern = Pattern.compile("PRIMARY KEY \\((.+)\\)", Pattern.CASE_INSENSITIVE);

        List<String> primaryKeys = new ArrayList<>(2);
        lines.forEach(line -> {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                String columns = matcher.group(1)
                                        .trim();
                String[] columnsArr = columns.split("\\s*,\\s*");
                for (String column : columnsArr) {
                    primaryKeys.add(column.trim()
                                          .replaceAll("\"", ""));
                }
            }
        });

        HDBTableConstraintPrimaryKey pkConstraint = new HDBTableConstraintPrimaryKey();
        pkConstraint.setColumns(primaryKeys.toArray(new String[0]));
        return pkConstraint;
    }

    public static List<String> splitAndTrimLines(String input) {
        List<String> lines = new ArrayList<>();

        String[] parts = input.split("\\r?\\n");

        for (String part : parts) {
            lines.add(part.trim());
        }

        return lines;
    }

    /**
     * Creates the public synonym for artifact.
     *
     * @param artifactName the artifact name
     * @param artifactSchema the artifact schema
     * @param connection the connection
     * @throws SQLException the SQL exception
     */
    public static void createPublicSynonymForArtifact(String artifactName, String artifactSchema, Connection connection)
            throws SQLException {
        new HDBSynonymCreateProcessor().execute(connection, assemblePublicSynonym(artifactName, artifactSchema));
    }

    /**
     * Drop public synonym for artifact.
     *
     * @param artifactName the artifact name
     * @param artifactSchema the artifact schema
     * @param connection the connection
     * @throws SQLException the SQL exception
     */
    public static void dropPublicSynonymForArtifact(String artifactName, String artifactSchema, Connection connection) throws SQLException {
        new HDBSynonymDropProcessor().execute(connection, assemblePublicSynonym(artifactName, artifactSchema));
    }

    /**
     * Assemble public synonym.
     *
     * @param artifactName the artifact name
     * @param artifactSchema the artifact schema
     * @return the data structure HDB synonym model
     */
    public static HDBSynonymGroup assemblePublicSynonym(String artifactName, String artifactSchema) {
        HDBSynonymGroup model = new HDBSynonymGroup();

        HDBSynonym defModel = new HDBSynonym();
        defModel.setSchema(Constants.SYNONYM_PUBLIC_SCHEMA);
        HDBSynonymTarget target = new HDBSynonymTarget();
        target.setObject(artifactName);
        target.setSchema(artifactSchema);
        defModel.setTarget(target);
        defModel.setGroup(model);

        model.getSynonymDefinitions()
             .put(artifactName, defModel);
        model.setType(IDataStructureModel.TYPE_HDB_SYNONYM);
        model.setCreatedBy(UserFacade.getName());
        model.setCreatedAt(new Timestamp(new java.util.Date().getTime()));
        return model;
    }

    /**
     * Extract procedure name from content.
     *
     * @param content the content
     * @param location the location
     * @return the string
     * @throws DataStructuresException the data structures exception
     */
    public static String extractProcedureNameFromContent(String content, String location) throws DataStructuresException {
        content = removeSqlCommentsFromContent(content);
        int indexOfEndOfProcKeyword = content.toLowerCase()
                                             .indexOf("procedure")
                + "procedure".length();
        int indexOfBracket = content.indexOf('(', indexOfEndOfProcKeyword);
        if (indexOfEndOfProcKeyword < 0 || indexOfBracket < 0) {
            String errorMessage = "HDB Procedure file not correct";
            CommonsUtils.logCustomErrors(location, PARSER_ERROR, "", "", errorMessage, "", HDB_PROCEDURE_PARSER, MODULE_PARSERS,
                    SOURCE_PUBLISH_REQUEST, PROGRAM_KRONOS);
            throw new DataStructuresException(errorMessage);
        }
        String procedureName = content.substring(indexOfEndOfProcKeyword, indexOfBracket);
        return procedureName.replace("\\s", "")
                            .trim();
    }

    /**
     * Extract table function name from content.
     *
     * @param content the content
     * @param location the location
     * @param parser the parser
     * @return the string
     * @throws DataStructuresException the data structures exception
     */
    public static String extractTableFunctionNameFromContent(String content, String location, String parser)
            throws DataStructuresException {
        content = removeSqlCommentsFromContent(content);
        int indexOfBracket = content.indexOf('(');
        int indexOfEndOfProcKeyword = content.toLowerCase()
                                             .indexOf("function")
                + "function".length();
        if (indexOfBracket > -1 && indexOfEndOfProcKeyword > -1) {
            String procNameWithWhiteSymbols = content.substring(indexOfEndOfProcKeyword, indexOfBracket);
            return procNameWithWhiteSymbols.replace("\\s", "")
                                           .replaceAll("\"", "")
                                           .trim();
        }
        String errMsg = "HDB Table Function file not correct";
        CommonsUtils.logCustomErrors(location, CommonsConstants.PARSER_ERROR, "", "", errMsg, "", parser, CommonsConstants.MODULE_PARSERS,
                CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
        throw new DataStructuresException(errMsg);
    }

    /**
     * Removes the sql comments from content.
     *
     * @param content the content
     * @return the string
     */
    public static String removeSqlCommentsFromContent(String content) {
        return content.replaceAll(commentRegex, "")
                      .trim();
    }

    /**
     * Gets the parsed three.
     *
     * @param parametersModel the parameters model
     * @return the parsed three
     * @throws ArtifactParserException the artifact parser exception
     */
    public static ParseTree getParsedThree(HDBParameters parametersModel) throws ArtifactParserException {

        CharStream inputStream;
        try (ByteArrayInputStream is = new ByteArrayInputStream(parametersModel.getContent()
                                                                               .getBytes())) {
            inputStream = CharStreams.fromStream(is);
        } catch (IOException exception) {
            throw new ArtifactParserException("Cannot get parsed tree", exception);
        }

        HanaLexer lexer = new HanaLexer(inputStream);
        lexer.removeErrorListeners();
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        HanaParser parser = new HanaParser(tokenStream);
        parser.setBuildParseTree(true);
        parser.removeErrorListeners();

        return parser.sql_script();

    }

    /**
     * Gets the timestamp.
     *
     * @return the timestamp
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(new java.util.Date().getTime());
    }

}
