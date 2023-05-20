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
package com.codbex.kronos.modificators;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.codbex.kronos.parser.hana.core.HanaLexer;
import com.codbex.kronos.parser.hana.core.HanaParser;
import com.codbex.kronos.parser.hana.custom.HanaProcedureUpdateStatementListener;
import com.codbex.kronos.parser.hana.models.FromClauseDefinitionModel;
import com.codbex.kronos.parser.hana.models.JoinClauseDefinitionModel;
import com.codbex.kronos.parser.hana.models.ProcedureDefinitionModel;
import com.codbex.kronos.parser.hana.models.TableReferenceModel;
import com.codbex.kronos.parser.hana.models.UpdateSetClauseDefinitionModel;
import com.codbex.kronos.parser.hana.models.UpdateStatementDefinitionModel;
import com.codbex.kronos.parser.hana.models.WhereClauseDefinitionModel;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.dirigible.components.ide.workspace.domain.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ProjectFilesModificator.
 */
public class ProjectFilesModificator {

  /** The Constant logger. */
  private static final Logger logger = LoggerFactory.getLogger(ProjectFilesModificator.class);

  /** The Constant CALC_VIEW_REFERENCE_MATCH_PATTERN. */
  private static final String CALC_VIEW_REFERENCE_MATCH_PATTERN = "\"_SYS_BIC\".\"(.*)\\/(.*)\"";
  
  /** The Constant XSLT_RESOURCE_PATH. */
  private static final String XSLT_RESOURCE_PATH = "META-INF/modificators/xslt/analyticprivilege.xslt";
  
  /** The Constant HDB_PROCEDURE_FILE_EXTENSION. */
  private static final String HDB_PROCEDURE_FILE_EXTENSION = "hdbprocedure";
  
  /** The Constant REPLACE_SESSION_USER_FILE_EXTENSIONS. */
  private static final List<String> REPLACE_SESSION_USER_FILE_EXTENSIONS = List.of("xsjs", "xsjslib", HDB_PROCEDURE_FILE_EXTENSION,
      "hdbtablefunction", "analyticprivilege", "hdbanalyticprivilege");
  
  /** The Constant ANALYTIC_PRIVILEGE_FILE_EXTENSIONS. */
  private static final List<String> ANALYTIC_PRIVILEGE_FILE_EXTENSIONS = List.of("analyticprivilege", "hdbanalyticprivilege");
  
  /** The Constant ROW_DEFINITION. */
  private static final String ROW_DEFINITION = "(?i)\\bin row\\b";
  
  /** The Constant ROW_DEFINITION_REPLACEMENT. */
  private static final String ROW_DEFINITION_REPLACEMENT = "IN row1";
  
  /** The Constant ROW_VALUE. */
  private static final String ROW_VALUE = "(?i)\\:row[^0-9a-z]";
  
  /** The Constant ROW_VALUE_REPLACEMENT. */
  private static final String ROW_VALUE_REPLACEMENT = ":row1;";
  
  /** The Constant MERGE_INTO. */
  private static final String MERGE_INTO = "MERGE INTO ";
  
  /** The Constant AS. */
  private static final String AS = "AS ";
  
  /** The Constant USING. */
  private static final String USING = "USING ";
  
  /** The Constant UPDATE. */
  private static final String UPDATE = "UPDATE ";
  
  /** The Constant ON. */
  private static final String ON = "ON";
  
  /** The Constant AND. */
  private static final String AND = "AND";
  
  /** The Constant ON_TRUE. */
  private static final String ON_TRUE = "ON (1 = 1)";
  
  /** The Constant WHEN_MATCHED_THEN_UPDATE. */
  private static final String WHEN_MATCHED_THEN_UPDATE = "WHEN MATCHED THEN UPDATE";
  
  /** The Constant WHEN_MATCHED. */
  private static final String WHEN_MATCHED = "WHEN MATCHED ";
  
  /** The Constant THEN_UPDATE. */
  private static final String THEN_UPDATE = "THEN UPDATE";
  
  /** The Constant TAB. */
  private static final String TAB = "\t";
  
  /** The Constant NEW_LINE. */
  private static final String NEW_LINE = "\n";
  
  /** The Constant SPACE. */
  private static final String SPACE = " ";
  
  /** The Constant CASE_INSENSITIVE_WHERE. */
  private static final String CASE_INSENSITIVE_WHERE = "(?i)WHERE";
  
  /**
   * Modify a list of delivery unit project files during the migration process.
   *
   * @param projectFiles the list of project files which will be modified
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public void modifyProjectFiles(List<File> projectFiles) throws IOException {
    for (File projectFile : projectFiles) {
      String fileExtension = getProjectFileExtension(projectFile);
      replaceSessionUser(fileExtension, projectFile);
      modifyAnalyticPrivilegeFile(fileExtension, projectFile);
      replaceReservedWordRow(fileExtension, projectFile);
      modifyUpdateFromStatement(fileExtension, projectFile);
    }
  }

  /**
   * Replace SESSION_USER in the content of the specified file.
   *
   * @param fileExtension the extension of the file being modified
   * @param projectFile   the file being modified
   */
  private void replaceSessionUser(String fileExtension, File projectFile) {
    if (REPLACE_SESSION_USER_FILE_EXTENSIONS.contains(fileExtension)) {
      byte[] currentContent = projectFile.getContent();
      projectFile.setContent(new String(currentContent).replace("SESSION_USER", "SESSION_CONTEXT('APPLICATIONUSER')").getBytes());
    }
  }

  /**
   * Make modifications on the modelUri and whereSql elements of an analytic privilege file.
   *
   * @param fileExtension         the extension of the file being modified
   * @param analyticPrivilegeFile the file being modified
   * @throws IOException Signals that an I/O exception has occurred.
   */
  private void modifyAnalyticPrivilegeFile(String fileExtension, File analyticPrivilegeFile) throws IOException {
    if (ANALYTIC_PRIVILEGE_FILE_EXTENSIONS.contains(fileExtension)) {
      byte[] currentContent = analyticPrivilegeFile.getContent();

      if (currentContent.length != 0) {
        try {
          String xslt = IOUtils.toString(
              ProjectFilesModificator.class.getClassLoader().getResourceAsStream(XSLT_RESOURCE_PATH),
              StandardCharsets.UTF_8);

          StringWriter writer = new StringWriter();
          StreamResult result = new StreamResult(writer);

          TransformerFactory factory = TransformerFactory.newInstance();
          Source xsltSource = new StreamSource(new StringReader(xslt));
          Transformer transformer = factory.newTransformer(xsltSource);

          Source contentSource = new StreamSource(new StringReader(new String(currentContent)));
          transformer.transform(contentSource, result);

          String processedAnalyticPrivilege = writer.toString();

          analyticPrivilegeFile.setContent(processedAnalyticPrivilege.getBytes());
        } catch (TransformerException exception) {
          logger.error("Analytic privilege" + analyticPrivilegeFile.getName() + "modification has failed due to an error.", exception);
          CommonsUtils.logCustomErrors(analyticPrivilegeFile.getPath(), CommonsConstants.MIGRATION_ERROR, "", "",
              exception.getMessage(),
              "", CommonsConstants.HDB_ANALYTIC_PRIVILEGE, CommonsConstants.MODULE_PARSERS,
              CommonsConstants.SOURCE_DELIVERY_UNIT_MIGRATION, CommonsConstants.PROGRAM_KRONOS);
        }
      }
    }
  }

  /**
   * Get the extension of the specified file.
   *
   * @param projectFile the file to get extension for
   * @return the file extension string
   */
  private String getProjectFileExtension(File projectFile) {
    return StringUtils.substringAfterLast(projectFile.getName(), ".");
  }

  /**
   * Modify analytic privilege file modelUri element by removing the reference of calculation view for the / syntax.
   *
   * @param value the value of the modelUri element
   * @return the modified value of the modelUri
   */
  public static String processModelUri(String value) {
    boolean uriIsNotWithPackageAndFolder = value.indexOf("/") == -1;

    if (uriIsNotWithPackageAndFolder) {
      return value;
    }

    return value.substring(value.lastIndexOf("/") + 1);
  }

  /**
   * Modify analytic privilege file whereSql element by removing calculation view references.
   *
   * @param value the value of the whereSql element
   * @return the modified value of the whereSql on regex pattern match for the second group
   */
  public static String processWhereSql(String value) {
    return value.replaceAll(CALC_VIEW_REFERENCE_MATCH_PATTERN, "\"$2\"");
  }

  /**
   * Replace reserved word row in the content of the specified file.
   *
   * @param fileExtension the extension of the file being modified
   * @param projectFile   the file being modified
   */
  private void replaceReservedWordRow(String fileExtension, File projectFile) {
    if (fileExtension.equalsIgnoreCase(HDB_PROCEDURE_FILE_EXTENSION)) {
      byte[] currentContent = projectFile.getContent();
      projectFile.setContent(new String(currentContent).replaceAll(ROW_DEFINITION, ROW_DEFINITION_REPLACEMENT)
          .replaceAll(ROW_VALUE, ROW_VALUE_REPLACEMENT).getBytes());
    }
  }

  /**
   * Modifies the update from statement in hdbprocedure files.
   *
   * @param fileExtension the extension of the file being modified
   * @param projectFile   the file being modified
   */
  private void modifyUpdateFromStatement(String fileExtension, File projectFile) {
    if (fileExtension.equalsIgnoreCase(HDB_PROCEDURE_FILE_EXTENSION) && !projectFile.isEmpty()) {
      String hdbprocedureFileContent = new String(projectFile.getContent());
      Map<String, String> modifiedUpdateStatementsMapping = new HashMap<>();
      ProcedureDefinitionModel procedureDefinitionModel = getProcedureModel(hdbprocedureFileContent);
      List<UpdateStatementDefinitionModel> updateStatements = procedureDefinitionModel.getUpdateStatements();

      for (UpdateStatementDefinitionModel updateStatement : updateStatements) {
        String modifiedUpdateStatement;
        FromClauseDefinitionModel fromClause = updateStatement.getFromClause();
        if (fromClause != null) {
          List<JoinClauseDefinitionModel> joinClauses = fromClause.getJoinClauses();
          if (joinClauses.isEmpty()) {
            modifiedUpdateStatement = modifyHdbprocedureUpdateFromWithoutJoinClauses(updateStatement, fromClause);
          } else {
            modifiedUpdateStatement = modifyHdbprocedureUpdateFromWithJoinClauses(updateStatement, fromClause, joinClauses);
          }
          modifiedUpdateStatementsMapping.put(updateStatement.getRawContent(), modifiedUpdateStatement);
        }
      }
      for (Map.Entry<String, String> mapping : modifiedUpdateStatementsMapping.entrySet()) {
        String originalUpdateFromStatement = mapping.getKey();
        String modifiedUpdateFromStatement = mapping.getValue();
        hdbprocedureFileContent = hdbprocedureFileContent.replace(originalUpdateFromStatement + ";", modifiedUpdateFromStatement + ";");
      }
      projectFile.setContent(hdbprocedureFileContent.getBytes());
    }
  }

  /**
   * Parses the procedure model from the given file content.
   *
   * @param fileContent the content of the file being modified
   * @return the procedure model
   */
  private ProcedureDefinitionModel getProcedureModel(String fileContent){
    CharStream inputStream = CharStreams.fromString(fileContent);
    HanaLexer lexer = new HanaLexer(inputStream);
    lexer.removeErrorListeners();
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);

    HanaParser parser = new HanaParser(tokenStream);
    parser.setBuildParseTree(true);
    parser.removeErrorListeners();

    ParseTree parseTree = parser.sql_script();

    HanaProcedureUpdateStatementListener listener = new HanaProcedureUpdateStatementListener();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(listener, parseTree);
    return listener.getProcedureModel();
  }

  /**
   * Modifies the update from statement in hdbprocedure files which are missing join clauses.
   *
   * @param updateStatement the content of update from statement
   * @param fromClause the from clause of the update statement
   * @return the string
   */
  private String modifyHdbprocedureUpdateFromWithoutJoinClauses(UpdateStatementDefinitionModel updateStatement, FromClauseDefinitionModel fromClause) {
    StringBuilder modifiedUpdateStatement = new StringBuilder();
    UpdateSetClauseDefinitionModel updateSetClause = updateStatement.getUpdateSetClause();
    String updateSetClauseRawContent = updateSetClause.getRawContent();
    WhereClauseDefinitionModel whereClause = updateStatement.getWhereClause();
    List<TableReferenceModel> fromClauseTableReferences = fromClause.getTableReferences();

    if (fromClauseTableReferences.size() > 2){
      String errorMessage = "In a HDBProcedures UPDATE FROM statement, we do not support automatic migration of more than two table references inside the FROM clause";
      logger.error(errorMessage);
      CommonsUtils.logCustomErrors("", CommonsConstants.MIGRATION_ERROR, "", "",
          errorMessage, "", CommonsConstants.HDB_PROCEDURE_PARSER, CommonsConstants.MODULE_PARSERS,
          CommonsConstants.SOURCE_DELIVERY_UNIT_MIGRATION, CommonsConstants.PROGRAM_KRONOS);
      modifiedUpdateStatement.append(updateStatement.getRawContent());
    }
    else if (fromClauseTableReferences.size() == 2) {
      TableReferenceModel usingTableReference = getUsingTableReferenceModel(updateStatement.getName(), fromClauseTableReferences);
      TableReferenceModel updatedTableReference = getUpdatedTableReferenceModel(updateStatement.getName(), fromClauseTableReferences);
      mergeIntoWithoutJoinWith2FromStatements(modifiedUpdateStatement, whereClause, updatedTableReference, usingTableReference, updateSetClauseRawContent);
    }
    else {
      mergeIntoWithoutJoin(modifiedUpdateStatement, fromClauseTableReferences.get(0), whereClause, updateSetClauseRawContent);
    }
    return modifiedUpdateStatement.toString();
  }

  /**
   * Modifies the update from statement in hdbprocedure files which have a join clause.
   *
   * @param updateStatement the content of update from statement
   * @param fromClause the from clause of the update statement
   * @param joinClauses the join clause of the update statement
   * @return the string
   */
  private String modifyHdbprocedureUpdateFromWithJoinClauses(UpdateStatementDefinitionModel updateStatement, FromClauseDefinitionModel fromClause,
      List<JoinClauseDefinitionModel> joinClauses) {
    StringBuilder modifiedUpdateStatement = new StringBuilder();
    WhereClauseDefinitionModel whereClause = updateStatement.getWhereClause();
    TableReferenceModel fromClauseTableReference = fromClause.getTableReferences().get(0);
    String fromClauseTableName = fromClauseTableReference.getName();
    String fromClauseTableAlias = fromClauseTableReference.getAlias();
    String updatedTableName = updateStatement.getName();
    String updateSetClauseRawContent = updateStatement.getUpdateSetClause().getRawContent();

    modifiedUpdateStatement.append(MERGE_INTO);
    if (updatedTableName.equals(fromClauseTableName) || updatedTableName.equals(fromClauseTableAlias)) {
      mergeIntoWithUpdateTableName(modifiedUpdateStatement, fromClauseTableReference, joinClauses);
    }
    else {
      String joinClausesRawContent = getJoinClauseRawContentWithoutTableName(updatedTableName, joinClauses);
      JoinClauseDefinitionModel joinClauseForUpdate = getJoinClauseDefinitionModel(updatedTableName, joinClauses);
      mergeIntoWithoutUpdateTableName(modifiedUpdateStatement, fromClauseTableReference, joinClauseForUpdate, joinClausesRawContent);
    }
    modifiedUpdateStatement.append(TAB + WHEN_MATCHED);
    modifiedUpdateStatement.append(whereClause != null ? AND + whereClause.getRawContent().replaceFirst(CASE_INSENSITIVE_WHERE, "") + SPACE : "" );
    modifiedUpdateStatement.append(THEN_UPDATE + NEW_LINE);
    modifiedUpdateStatement.append(TAB).append(updateSetClauseRawContent);
    return modifiedUpdateStatement.toString();
  }

  /**
   * Merge into with update table name.
   *
   * @param modifiedUpdateStatement the modified update statement
   * @param fromClauseTableReference the from clause table reference
   * @param joinClauses the join clauses
   */
  private void mergeIntoWithUpdateTableName(StringBuilder modifiedUpdateStatement, TableReferenceModel fromClauseTableReference, List<JoinClauseDefinitionModel> joinClauses){
    String fromClauseTableName = fromClauseTableReference.getName();
    String fromClauseTableAlias = fromClauseTableReference.getAlias();
    JoinClauseDefinitionModel joinClauseForUpdate = joinClauses.get(0);
    joinClauses.remove(joinClauseForUpdate);

    modifiedUpdateStatement.append(fromClauseTableName).append(SPACE);
    modifiedUpdateStatement.append(addAsTableName(fromClauseTableAlias));
    modifiedUpdateStatement.append(TAB + USING);
    modifiedUpdateStatement.append(joinClauseForUpdate.getName()).append(SPACE);
    modifiedUpdateStatement.append(addAsTableName(joinClauseForUpdate.getAlias()));
    modifiedUpdateStatement.append(TAB).append(getJoinClauseRawContent(joinClauses));
    modifiedUpdateStatement.append(TAB).append(joinClauseForUpdate.getOnPart()).append(NEW_LINE);
  }

  /**
   * Merge into without update table name.
   *
   * @param modifiedUpdateStatement the modified update statement
   * @param fromClauseTableReference the from clause table reference
   * @param joinClauseForUpdate the join clause for update
   * @param joinClausesRawContent the join clauses raw content
   */
  private void mergeIntoWithoutUpdateTableName(StringBuilder modifiedUpdateStatement, TableReferenceModel fromClauseTableReference,
      JoinClauseDefinitionModel joinClauseForUpdate, String joinClausesRawContent){
    String fromClauseTableName = fromClauseTableReference.getName();
    String fromClauseTableAlias = fromClauseTableReference.getAlias();

    modifiedUpdateStatement.append(joinClauseForUpdate.getName()).append(SPACE);
    modifiedUpdateStatement.append(addAsTableName(joinClauseForUpdate.getAlias()));
    modifiedUpdateStatement.append(TAB + USING);
    modifiedUpdateStatement.append(fromClauseTableName).append(SPACE);
    modifiedUpdateStatement.append(addAsTableName(fromClauseTableAlias));
    modifiedUpdateStatement.append(TAB).append(joinClausesRawContent).append(NEW_LINE);
    modifiedUpdateStatement.append(TAB).append(joinClauseForUpdate.getOnPart()).append(NEW_LINE);
  }

  /**
   * Merge into without join with 2 from statements.
   *
   * @param modifiedUpdateStatement the modified update statement
   * @param whereClause the where clause
   * @param updatedTableReference the updated table reference
   * @param usingTableReference the using table reference
   * @param updateSetClauseRawContent the update set clause raw content
   */
  private void mergeIntoWithoutJoinWith2FromStatements(StringBuilder modifiedUpdateStatement, WhereClauseDefinitionModel whereClause,
      TableReferenceModel updatedTableReference, TableReferenceModel usingTableReference, String updateSetClauseRawContent ){
    modifiedUpdateStatement.append(MERGE_INTO).append(updatedTableReference.getName());
    modifiedUpdateStatement.append(addAsTableName(updatedTableReference.getAlias()));
    modifiedUpdateStatement.append(TAB + USING);
    modifiedUpdateStatement.append(usingTableReference.getName()).append(SPACE);
    modifiedUpdateStatement.append(addAsTableName(usingTableReference.getAlias()));
    modifiedUpdateStatement.append(getWhereClause(whereClause));
    modifiedUpdateStatement.append(TAB + WHEN_MATCHED_THEN_UPDATE);
    modifiedUpdateStatement.append(TAB).append(updateSetClauseRawContent);
  }

  /**
   * Merge into without join.
   *
   * @param modifiedUpdateStatement the modified update statement
   * @param fromClauseTableReference the from clause table reference
   * @param whereClause the where clause
   * @param updateSetClauseRawContent the update set clause raw content
   */
  private void mergeIntoWithoutJoin(StringBuilder modifiedUpdateStatement, TableReferenceModel fromClauseTableReference,
    WhereClauseDefinitionModel whereClause, String updateSetClauseRawContent ){
    String fromClauseTableName = fromClauseTableReference.getName();
    String fromClauseTableAlias = fromClauseTableReference.getAlias();

    modifiedUpdateStatement.append(UPDATE);
    modifiedUpdateStatement.append(fromClauseTableName).append(SPACE);
    modifiedUpdateStatement.append(addAsTableName(fromClauseTableAlias));
    modifiedUpdateStatement.append(TAB).append(updateSetClauseRawContent).append(NEW_LINE);
    modifiedUpdateStatement.append(whereClause != null ? TAB + whereClause.getRawContent() : "");
  }

  /**
   * Adds the as table name.
   *
   * @param tableName the table name
   * @return the string
   */
  private String addAsTableName(String tableName){
    return tableName != null ? AS + tableName + NEW_LINE : "";
  }

  /**
   * Gets the where clause.
   *
   * @param whereClause the where clause
   * @return the where clause
   */
  private String getWhereClause(WhereClauseDefinitionModel whereClause){
    return whereClause != null ? ON + whereClause.getRawContent().replaceFirst(CASE_INSENSITIVE_WHERE, "") + SPACE : ON_TRUE;
  }

  /**
   * Gets the updated table reference model.
   *
   * @param updatedTableName the updated table name
   * @param fromClauseTableReferences the from clause table references
   * @return the updated table reference model
   */
  private TableReferenceModel getUpdatedTableReferenceModel(String updatedTableName, List<TableReferenceModel> fromClauseTableReferences){
    TableReferenceModel updatedTableReference = new TableReferenceModel();
    for (TableReferenceModel tableReferenceModel : fromClauseTableReferences) {
      if (updatedTableName.equals(tableReferenceModel.getName()) || updatedTableName.equals(tableReferenceModel.getAlias())) {
        updatedTableReference = tableReferenceModel;
      }
    }
    return updatedTableReference;
  }

  /**
   * Gets the using table reference model.
   *
   * @param updatedTableName the updated table name
   * @param fromClauseTableReferences the from clause table references
   * @return the using table reference model
   */
  private TableReferenceModel getUsingTableReferenceModel(String updatedTableName, List<TableReferenceModel> fromClauseTableReferences){
    TableReferenceModel usingTableReference = new TableReferenceModel();
    for (TableReferenceModel tableReferenceModel : fromClauseTableReferences) {
      if (!updatedTableName.equals(tableReferenceModel.getName()) && !updatedTableName.equals(tableReferenceModel.getAlias())) {
        usingTableReference = tableReferenceModel;
      }
    }
    return usingTableReference;
  }

  /**
   * Gets the join clause definition model.
   *
   * @param updatedTableName the updated table name
   * @param joinClauses the join clauses
   * @return the join clause definition model
   */
  private JoinClauseDefinitionModel getJoinClauseDefinitionModel(String updatedTableName, List<JoinClauseDefinitionModel> joinClauses){
    JoinClauseDefinitionModel updatedJoinClauseModel = new JoinClauseDefinitionModel();
    for (JoinClauseDefinitionModel joinClauseDefinitionModel : joinClauses) {
      if (updatedTableName.equals(joinClauseDefinitionModel.getName()) || updatedTableName.equals(joinClauseDefinitionModel.getAlias())) {
        updatedJoinClauseModel = joinClauseDefinitionModel;
      }
    }
    return updatedJoinClauseModel;
  }

  /**
   * Gets the join clause raw content without table name.
   *
   * @param updatedTableName the updated table name
   * @param joinClauses the join clauses
   * @return the join clause raw content without table name
   */
  private String getJoinClauseRawContentWithoutTableName(String updatedTableName, List<JoinClauseDefinitionModel> joinClauses){
    StringBuilder updatedJoinClauseRawContent = new StringBuilder();
    joinClauses.forEach(joinClauseDefinitionModel -> {
      if (!updatedTableName.equals(joinClauseDefinitionModel.getName()) && !updatedTableName.equals(joinClauseDefinitionModel.getAlias())) {
        updatedJoinClauseRawContent.append(TAB).append(joinClauseDefinitionModel.getRawContent());
      }
    });
    return updatedJoinClauseRawContent.toString();
  }

  /**
   * Gets the join clause raw content.
   *
   * @param joinClauses the join clauses
   * @return the join clause raw content
   */
  private String getJoinClauseRawContent(List<JoinClauseDefinitionModel> joinClauses){
    StringBuilder joinClausesRawContent = new StringBuilder();
    joinClauses.forEach(joinClause -> { joinClausesRawContent.append(TAB).append(joinClause.getRawContent()).append(NEW_LINE);});
    return joinClausesRawContent.toString();
  }
}
