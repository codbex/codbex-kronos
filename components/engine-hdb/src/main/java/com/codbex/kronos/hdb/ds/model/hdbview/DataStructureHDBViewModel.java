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
package com.codbex.kronos.hdb.ds.model.hdbview;

import java.util.List;

import com.codbex.kronos.hdb.ds.model.DataStructureModel;

/**
 * The view model representation.
 */
public class DataStructureHDBViewModel extends DataStructureModel {

    /** The query. */
    private String query;
    
    /** The public prop. */
    private boolean publicProp;
    
    /** The depends on. */
    private List<String> dependsOn;
    
    /** The depends on table. */
    private List<String> dependsOnTable;
    
    /** The depends on view. */
    private List<String> dependsOnView;

    /**
     * Getter for the query field.
     *
     * @return the SQL query
     */
    public String getQuery() {
        return query;
    }

    /**
     * Setter for the query field.
     *
     * @param query the SQL query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Getter for the 'public' field.
     *
     * @return the 'public' field
     */
    public boolean isPublic() {
        return publicProp;
    }

    /**
     * Setter for the 'public' field.
     *
     * @param publicProp the 'public' field
     */
    public void setPublic(boolean publicProp) {
        this.publicProp = publicProp;
    }

    /**
     * Getter for the 'depends_on' field.
     *
     * @return the 'depends_on' field
     */
    public List<String> getDependsOn() {
        return dependsOn;
    }

    /**
     * Setter for the 'depends_on' field.
     *
     * @param dependsOn the list of 'depends_on'
     */
    public void setDependsOn(List<String> dependsOn) {
        this.dependsOn = dependsOn;
    }

    /**
     * Getter for the 'depends_on_table' field.
     *
     * @return the 'depends_on_table' field
     */
    public List<String> getDependsOnTable() {
        return dependsOnTable;
    }

    /**
     * Setter for the 'depends_on_table' field.
     *
     * @param dependsOnTable the list of 'depends_on_table'
     */
    public void setDependsOnTable(List<String> dependsOnTable) {
        this.dependsOnTable = dependsOnTable;
    }

    /**
     * Getter for the 'depends_on_view' field.
     *
     * @return the 'depends_on_view' field
     */
    public List<String> getDependsOnView() {
        return dependsOnView;
    }

    /**
     * Setter for the 'depends_on_view' field.
     *
     * @param dependsOnView the 'depends_on_view'
     */
    public void setDependsOnView(List<String> dependsOnView) {
        this.dependsOnView = dependsOnView;
    }
}
