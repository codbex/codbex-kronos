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

import java.nio.charset.StandardCharsets;

/**
 * The Class HDBParameters.
 */
public class HDBParameters {

    /** The type. */
    private String type;

    /** The location. */
    private String location;

    /** The content. */
    private String content;

    /** The workspace path. */
    private String workspacePath;

    /**
     * Instantiates a new data structure parameters model.
     */
    public HDBParameters() {}

    /**
     * Instantiates a new data structure parameters model.
     *
     * @param type the type
     * @param location the location
     * @param content the content
     * @param workspacePath the workspace path
     */
    public HDBParameters(String type, String location, String content, String workspacePath) {
        this.type = type;
        this.location = location;
        this.content = content;
        this.workspacePath = workspacePath;
    }

    /**
     * Instantiates a new data structure parameters model.
     *
     * @param type the type
     * @param location the location
     * @param content the content
     * @param workspacePath the workspace path
     */
    public HDBParameters(String type, String location, byte[] content, String workspacePath) {
        this.type = type;
        this.location = location;
        this.content = new String(content, StandardCharsets.UTF_8);
        this.workspacePath = workspacePath;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location.
     *
     * @param location the new location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content.
     *
     * @param content the new content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets the workspace path.
     *
     * @return the workspace path
     */
    public String getWorkspacePath() {
        return workspacePath;
    }

    /**
     * Sets the workspace path.
     *
     * @param workspacePath the new workspace path
     */
    public void setWorkspacePath(String workspacePath) {
        this.workspacePath = workspacePath;
    }
}
