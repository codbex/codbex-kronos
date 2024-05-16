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
package com.codbex.kronos.parser.hana.custom;

import com.codbex.kronos.parser.hana.models.FromClauseDefinitionModel;
import com.codbex.kronos.parser.hana.models.JoinClauseDefinitionModel;
import com.codbex.kronos.parser.hana.models.ProcedureDefinitionModel;
import com.codbex.kronos.parser.hana.models.TableReferenceModel;
import com.codbex.kronos.parser.hana.models.UpdateSetClauseDefinitionModel;
import com.codbex.kronos.parser.hana.models.UpdateStatementDefinitionModel;
import com.codbex.kronos.parser.hana.models.WhereClauseDefinitionModel;
import com.codbex.kronos.parser.hana.core.HanaBaseListener;
import com.codbex.kronos.parser.hana.core.HanaParser.Create_procedure_bodyContext;
import com.codbex.kronos.parser.hana.core.HanaParser.From_clauseContext;
import com.codbex.kronos.parser.hana.core.HanaParser.Join_clauseContext;
import com.codbex.kronos.parser.hana.core.HanaParser.Update_set_clauseContext;
import com.codbex.kronos.parser.hana.core.HanaParser.Update_stmtContext;
import com.codbex.kronos.parser.hana.core.HanaParser.Where_clauseContext;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.misc.Interval;
import org.apache.commons.lang3.StringUtils;

/**
 * The listener interface for receiving hanaProcedureUpdateStatement events. The class that is
 * interested in processing a hanaProcedureUpdateStatement event implements this interface, and the
 * object created with that class is registered with a component using the component's
 * <code>addHanaProcedureUpdateStatementListener</code> method. When the
 * hanaProcedureUpdateStatement event occurs, that object's appropriate method is invoked.
 *
 */
public class HanaProcedureUpdateStatementListener extends HanaBaseListener {

    /** The procedure model. */
    ProcedureDefinitionModel procedureModel;

    /** The from clause model. */
    FromClauseDefinitionModel fromClauseModel;

    /** The update statement model. */
    UpdateStatementDefinitionModel updateStatementModel;

    /** The is update statement scope. */
    boolean isUpdateStatementScope = false;

    /**
     * Enter create procedure body.
     *
     * @param ctx the ctx
     */
    @Override
    public void enterCreate_procedure_body(Create_procedure_bodyContext ctx) {
        String strippedSchema = null;
        String strippedName = null;

        if (ctx.proc_name() != null) {
            if (ctx.proc_name()
                   .id_expression() != null) {
                String maybeQuotedName = ctx.proc_name()
                                            .id_expression()
                                            .getText();
                strippedName = StringUtils.strip(maybeQuotedName, "\"");
            }

            if (ctx.proc_name()
                   .schema_name() != null) {
                String maybeQuotedSchema = ctx.proc_name()
                                              .schema_name()
                                              .getText();
                strippedSchema = StringUtils.strip(maybeQuotedSchema, "\"");
            }
        }
        procedureModel = new ProcedureDefinitionModel(strippedSchema, strippedName);
    }

    /**
     * Enter update stmt.
     *
     * @param ctx the ctx
     */
    @Override
    public void enterUpdate_stmt(Update_stmtContext ctx) {
        isUpdateStatementScope = true;
        updateStatementModel = new UpdateStatementDefinitionModel();
        String tableName = ctx.general_table_ref()
                              .dml_table_expression_clause()
                              .tableview_name()
                              .getText();
        String tableAlias = ctx.general_table_ref()
                               .table_alias() != null ? ctx.general_table_ref()
                                                           .table_alias()
                                                           .getText()
                                       : null;
        updateStatementModel.setName(tableName);
        updateStatementModel.setAlias(tableAlias);
        updateStatementModel.setRawContent(getStringWithSpaces(ctx));
        procedureModel.addUpdateStatement(updateStatementModel);
    }

    /**
     * Exit update stmt.
     *
     * @param ctx the ctx
     */
    @Override
    public void exitUpdate_stmt(Update_stmtContext ctx) {
        isUpdateStatementScope = false;
    }

    /**
     * Enter update set clause.
     *
     * @param ctx the ctx
     */
    @Override
    public void enterUpdate_set_clause(Update_set_clauseContext ctx) {
        if (isUpdateStatementScope && ctx.parent instanceof Update_stmtContext) {
            UpdateSetClauseDefinitionModel updateSetClauseModel = new UpdateSetClauseDefinitionModel();
            updateSetClauseModel.setRawContent(getStringWithSpaces(ctx));
            updateStatementModel.setUpdateSetClause(updateSetClauseModel);
        }
    }

    /**
     * Enter join clause.
     *
     * @param ctx the ctx
     */
    @Override
    public void enterJoin_clause(Join_clauseContext ctx) {
        RuleContext ruleContext = ctx.parent.parent.parent.parent;
        if (isUpdateStatementScope && ruleContext instanceof Update_stmtContext) {
            JoinClauseDefinitionModel joinClauseModel = new JoinClauseDefinitionModel();
            String tableName;

            if (ctx.table_ref_aux()
                   .dml_table_expression_clause() != null) {
                tableName = getStringWithSpaces(ctx.table_ref_aux()
                                                   .dml_table_expression_clause());
            } else {
                tableName = ctx.table_ref_aux()
                               .getChild(0)
                               .getText();
            }

            String tableAlias = ctx.table_ref_aux()
                                   .table_alias() != null ? ctx.table_ref_aux()
                                                               .table_alias()
                                                               .getText()
                                           : null;
            joinClauseModel.setName(tableName);
            joinClauseModel.setAlias(tableAlias);
            joinClauseModel.setOnPart(getStringWithSpaces(ctx.join_on_part(0)));
            joinClauseModel.setRawContent(getStringWithSpaces(ctx));
            fromClauseModel.addJoinClause(joinClauseModel);
        }
    }

    /**
     * Enter where clause.
     *
     * @param ctx the ctx
     */
    @Override
    public void enterWhere_clause(Where_clauseContext ctx) {
        if (isUpdateStatementScope && ctx.parent instanceof Update_stmtContext) {
            WhereClauseDefinitionModel whereClauseModel = new WhereClauseDefinitionModel();
            whereClauseModel.setRawContent(getStringWithSpaces(ctx));
            updateStatementModel.setWhereClause(whereClauseModel);
        }
    }

    /**
     * Enter from clause.
     *
     * @param ctx the ctx
     */
    @Override
    public void enterFrom_clause(From_clauseContext ctx) {
        if (isUpdateStatementScope && ctx.parent instanceof Update_stmtContext) {
            fromClauseModel = new FromClauseDefinitionModel();

            ctx.table_ref_list()
               .table_ref()
               .forEach(tableRef -> {
                   TableReferenceModel tableReferenceModel = new TableReferenceModel();
                   String tableName = tableRef.table_ref_aux()
                                              .getChild(0)
                                              .getText();
                   String tableAlias = tableRef.table_ref_aux()
                                               .table_alias() != null ? tableRef.table_ref_aux()
                                                                                .table_alias()
                                                                                .getText()
                                                       : null;
                   tableReferenceModel.setName(tableName);
                   tableReferenceModel.setAlias(tableAlias);
                   fromClauseModel.addTableReference(tableReferenceModel);
               });

            updateStatementModel.setFromClause(fromClauseModel);
        }
    }

    /**
     * Gets the string with spaces.
     *
     * @param ctx the ctx
     * @return the string with spaces
     */
    private String getStringWithSpaces(ParserRuleContext ctx) {
        int startIndex = ctx.start.getStartIndex();
        int stopIndex = ctx.stop.getStopIndex();
        Interval selectedColumnsRuleSqlInterval = new Interval(startIndex, stopIndex);
        return ctx.start.getInputStream()
                        .getText(selectedColumnsRuleSqlInterval);
    }

    /**
     * Gets the procedure model.
     *
     * @return the procedure model
     */
    public ProcedureDefinitionModel getProcedureModel() {
        return procedureModel;
    }
}
