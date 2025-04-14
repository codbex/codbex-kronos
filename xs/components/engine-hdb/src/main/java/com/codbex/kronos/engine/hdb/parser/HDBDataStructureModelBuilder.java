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

/**
 * The Class HDBDataStructureModelBuilder.
 */
public class HDBDataStructureModelBuilder {

    /** The location. */
    private String location;

    /** The name. */
    private String name;

    /** The type. */
    private String type;

    /** The schema. */
    private String schema;

    /** The raw content. */
    private String content;

    /** The db content type. */
    private Boolean classic = true;

    /**
     * With name.
     *
     * @param name the name
     * @return the data structure model builder
     */
    public HDBDataStructureModelBuilder withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * With location.
     *
     * @param location the location
     * @return the data structure model builder
     */
    public HDBDataStructureModelBuilder withLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * With type.
     *
     * @param type the type
     * @return the data structure model builder
     */
    public HDBDataStructureModelBuilder withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * With schema.
     *
     * @param schema the schema
     * @return the data structure model builder
     */
    public HDBDataStructureModelBuilder withSchema(String schema) {
        this.schema = schema;
        return this;
    }

    /**
     * Raw content.
     *
     * @param content the raw content
     * @return the data structure model builder
     */
    public HDBDataStructureModelBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    /**
     * Whether is classic.
     *
     * @param classic the classic
     * @return the data structure model builder
     */
    public HDBDataStructureModelBuilder asClassic(boolean classic) {
        this.classic = classic;
        return this;
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
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
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
     * Gets the schema.
     *
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Gets the raw content.
     *
     * @return the raw content
     */
    public String getRawContent() {
        return content;
    }

    /**
     * Gets the db content type.
     *
     * @return the db content type
     */
    public Boolean isClassic() {
        return classic;
    }

}
