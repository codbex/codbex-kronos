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
package com.codbex.kronos.parser.hana.models;

/**
 * The Class UpdateStatementDefinitionModel.
 */
public class UpdateStatementDefinitionModel extends TableReferenceModel {

    /** The raw content. */
    private String rawContent;

    /** The from clause. */
    private FromClauseDefinitionModel fromClause;

    /** The where clause. */
    private WhereClauseDefinitionModel whereClause;

    /** The update set clause. */
    private UpdateSetClauseDefinitionModel updateSetClause;

    /**
     * Gets the raw content.
     *
     * @return the raw content
     */
    public String getRawContent() {
        return rawContent;
    }

    /**
     * Sets the raw content.
     *
     * @param rawContent the new raw content
     */
    public void setRawContent(String rawContent) {
        this.rawContent = rawContent;
    }

    /**
     * Gets the from clause.
     *
     * @return the from clause
     */
    public FromClauseDefinitionModel getFromClause() {
        return fromClause;
    }

    /**
     * Sets the from clause.
     *
     * @param fromClause the new from clause
     */
    public void setFromClause(FromClauseDefinitionModel fromClause) {
        this.fromClause = fromClause;
    }

    /**
     * Gets the where clause.
     *
     * @return the where clause
     */
    public WhereClauseDefinitionModel getWhereClause() {
        return whereClause;
    }

    /**
     * Sets the where clause.
     *
     * @param whereClause the new where clause
     */
    public void setWhereClause(WhereClauseDefinitionModel whereClause) {
        this.whereClause = whereClause;
    }

    /**
     * Gets the update set clause.
     *
     * @return the update set clause
     */
    public UpdateSetClauseDefinitionModel getUpdateSetClause() {
        return updateSetClause;
    }

    /**
     * Sets the update set clause.
     *
     * @param updateSetClause the new update set clause
     */
    public void setUpdateSetClause(UpdateSetClauseDefinitionModel updateSetClause) {
        this.updateSetClause = updateSetClause;
    }
}
