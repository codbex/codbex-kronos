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
package com.codbex.kronos.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codbex.kronos.exceptions.ArtifactParserException;
import com.codbex.kronos.parser.models.BaseParserErrorsModel;

/**
 * The Class CommonsUtils.
 */
public class CommonsUtils {

    /**
     * Instantiates a new commons utils.
     */
    private CommonsUtils() {}

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(CommonsUtils.class);

    /**
     * Extract the object base name from catalog name. Catalog name includes the package path, the
     * separating dots, and the object base name. For example: sap.test.hana.db::myObject
     *
     * @param catalogName string in format PACKAGE_PATH::OBJECT_BASE_NAME
     * @return string representing the OBJECT_BASE_NAME
     * @see <a href=
     *      "https://help.sap.com/viewer/52715f71adba4aaeb480d946c742d1f6/2.0.03/en-US/016a60fe929a4e9e89bbb3b6f7aad409.html">SAP
     *      HANA Repository Packages and Namespaces</a>
     */
    public static String extractBaseObjectNameFromCatalogName(String catalogName) {
        String[] extractedName = catalogName.split("::");
        if (extractedName.length == 2) {
            return extractedName[1];
        }
        return catalogName;
    }

    /**
     * Assemble the catalog name of a Repository Base Object(e.g hdbtable, hdbview, hdbsequence,
     * hdbstructure, hdbprocedure) The catalog name includes the package path, the separating dots, and
     * the object base name, as NAMESPACE::OBJECT_BASE_NAME For example: Given location
     * "/project_name/com/sap/hana/example/ItemsByOrder.hdbview", the method will return
     * "com.sap.hana.example::ItemsByOrder"
     *
     * @param location String representing file location path
     * @return String representing assemble catalog name in format "NAMESPACE::OBJECT_BASE_NAME"
     * @see <a href=
     *      "https://help.sap.com/viewer/52715f71adba4aaeb480d946c742d1f6/2.0.03/en-US/016a60fe929a4e9e89bbb3b6f7aad409.html">SAP
     *      HANA Repository Packages and Namespaces</a>
     */
    public static String getRepositoryBaseObjectName(String location) {
        String objBaseName = FilenameUtils.getBaseName(location);
        String namespace = getRepositoryNamespace(location);
        if (namespace.length() > 0) {
            return new StringBuilder().append(namespace)
                                      .append("::")
                                      .append(objBaseName)
                                      .toString();
        }
        return objBaseName;
    }

    /**
     * Assemble Repository Package name from file location. For example "com.sap.test.hana.db"
     *
     * @param location String representing file location path
     * @return String representing assemble repository package
     * @see <a href=
     *      "https://help.sap.com/viewer/52715f71adba4aaeb480d946c742d1f6/2.0.03/en-US/016a60fe929a4e9e89bbb3b6f7aad409.html">SAP
     *      HANA Repository Packages and Namespaces</a>
     */
    public static String getRepositoryNamespace(String location) {
        String namespacePart = FilenameUtils.getFullPathNoEndSeparator(location);
        namespacePart = namespacePart.replace(CommonsConstants.UNIX_SEPARATOR, '.');
        namespacePart = namespacePart.replace(CommonsConstants.WINDOWS_SEPARATOR, '.');
        if (namespacePart.startsWith(".")) {
            namespacePart = namespacePart.substring(1);
        }
        return namespacePart;
    }

    /**
     * Extract correct RepositoryBaseObject definition from content, by removing tabs or spaces between
     * the syntax word and the Object name. For example "VIEW "hdb_view::ItemsByOrderHANAv2" should be
     * return as "VIEW "hdb_view::ItemsByOrderHANAv2"
     *
     * @param repositoryObjectSyntax syntax as VIEW, TABLE ..etc
     * @param content String representing file content of a Repository Object
     * @return normalized definition of a Repository Object, consisting of 'SYNTAX_WORD
     *         "NAMESPACE::OBJECT_BASE_NAME"'
     */
    public static String extractRepositoryBaseObjectNameFromContent(String repositoryObjectSyntax, String content) {
        Pattern pattern = Pattern.compile("(" + repositoryObjectSyntax + ")(\\t\\n)*(\\s)*(\".+\")", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content.trim());
        boolean matchFound = matcher.find();
        if (matchFound) {
            return matcher.group(1) + matcher.group(4);
        } else {
            return "";
        }
    }

    /**
     * Use to log errors from antlr4 parsers.
     *
     * @param errorList the error list
     * @param errorType the error type
     * @param location the location
     * @param artifactType the artifact type
     * @throws ArtifactParserException the artifact parser exception
     */
    public static void logParserErrors(ArrayList<BaseParserErrorsModel> errorList, String errorType, String location, String artifactType)
            throws ArtifactParserException {
        if (errorList.size() > 0) {
            for (BaseParserErrorsModel errorModel : errorList) {
                try {
                    logger.error("Parser error at file: {}, line: {}, position: {}, symbol: {}", location, errorModel.getLine(),
                            errorModel.getCharPositionInLine(), errorModel.getOffendingSymbol());
                    logger.error(errorModel.getMsg());
                    // ProblemsFacade.save(location, errorType, Integer.toString(errorModel.getLine()),
                    // Integer.toString(errorModel.getCharPositionInLine()), errorModel.getOffendingSymbol(),
                    // errorModel.getMsg(), artifactType, CommonsConstants.MODULE_PARSERS,
                    // CommonsConstants.SOURCE_PUBLISH_REQUEST, CommonsConstants.PROGRAM_KRONOS);
                } catch (Exception e) {
                    logger.error("There is an issue with logging of the Errors.");
                    logger.error(e.getMessage());
                }
            }
            throw new ArtifactParserException(String.format(
                    "Wrong format of HDB %s: [%s] during parsing. Ensure you are using the correct format for the correct compatibility version.",
                    artifactType, location));
        }
    }

    /**
     * Log errors with all custom fields. Used in parser errors outside antlr4.
     *
     * @param location the location
     * @param errorType the error type
     * @param line the line
     * @param column the column
     * @param errorMessage the error message
     * @param expected the expected
     * @param category the category
     * @param module the module
     * @param source the source
     * @param program the program
     */
    public static void logCustomErrors(String location, String errorType, String line, String column, String errorMessage, String expected,
            String category, String module, String source, String program) {
        try {
            logger.error("Parser error at file: {}, type: {}, line: {}, column: {}, module: {}", location, errorType, line, column, module);
            logger.error(errorMessage);
            // ProblemsFacade.save(location, errorType, line, column, errorMessage, expected, category, module,
            // source, program);
        } catch (Exception e) {
            logger.error("There is an issue with logging of the Errors.");
            logger.error(e.getMessage());
        }
    }

    /**
     * Use to log errors from artifact processing.
     *
     * @param errorMessage the error message
     * @param errorType the error type
     * @param location the location
     * @param artifactType the artifact type
     */
    public static void logProcessorErrors(String errorMessage, String errorType, String location, String artifactType) {
        try {
            logger.error("Parser error at file: {}, type: {}, artefacts: {}", location, errorType, artifactType);
            logger.error(errorMessage);
            // ProblemsFacade.save(location, errorType, "", "", errorMessage, "", artifactType,
            // CommonsConstants.MODULE_PROCESSORS, CommonsConstants.SOURCE_PUBLISH_REQUEST,
            // CommonsConstants.PROGRAM_KRONOS);
        } catch (Exception e) {
            logger.error("There is an issue with logging of the Errors.");
            logger.error(e.getMessage());
        }
    }

    /**
     * Extract artifact name when schema is provided.
     *
     * @param artifactName the artifact name
     * @return the string[]
     */
    public static String[] extractArtifactNameWhenSchemaIsProvided(String artifactName) {
        Pattern pattern = Pattern.compile("\"?(.*?)\"?\\.\"(.*?)\"", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(artifactName.trim());
        boolean matchFound = matcher.find();
        if (matchFound) {
            return new String[] {matcher.group(1), matcher.group(2)};
        } else {
            return new String[] {null, artifactName};
        }
    }
}
