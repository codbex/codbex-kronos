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
package com.codbex.kronos.parser.xsodata.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The Class HDBXSODataSetting.
 */
public class XSODataSetting {

    /** The enable support null. */
    private boolean enableSupportNull;

    /** The content cache control. */
    private List<String> contentCacheControl = new ArrayList<>();

    /** The metadata cache control. */
    private List<String> metadataCacheControl = new ArrayList<>();

    /** The hints. */
    private List<String> hints = new ArrayList<>();

    /** The max records. */
    private String maxRecords;

    /** The max expanded records. */
    private String maxExpandedRecords;

    /**
     * Checks if is enable support null.
     *
     * @return true, if is enable support null
     */
    public boolean isEnableSupportNull() {
        return enableSupportNull;
    }

    /**
     * Sets the enable support null.
     *
     * @param enableSupportNull the enable support null
     * @return the HDBXSO data setting
     */
    public XSODataSetting setEnableSupportNull(boolean enableSupportNull) {
        this.enableSupportNull = enableSupportNull;
        return this;
    }

    /**
     * Gets the content cache control.
     *
     * @return the content cache control
     */
    public List<String> getContentCacheControl() {
        return contentCacheControl;
    }

    /**
     * Sets the content cache control.
     *
     * @param contentCacheControl the content cache control
     * @return the HDBXSO data setting
     */
    public XSODataSetting setContentCacheControl(List<String> contentCacheControl) {
        this.contentCacheControl = contentCacheControl;
        return this;
    }

    /**
     * Gets the metadata cache control.
     *
     * @return the metadata cache control
     */
    public List<String> getMetadataCacheControl() {
        return metadataCacheControl;
    }

    /**
     * Sets the metadata cache control.
     *
     * @param metadataCacheControl the metadata cache control
     * @return the HDBXSO data setting
     */
    public XSODataSetting setMetadataCacheControl(List<String> metadataCacheControl) {
        this.metadataCacheControl = metadataCacheControl;
        return this;
    }

    /**
     * Gets the hints.
     *
     * @return the hints
     */
    public List<String> getHints() {
        return hints;
    }

    /**
     * Sets the hints.
     *
     * @param hints the hints
     * @return the HDBXSO data setting
     */
    public XSODataSetting setHints(List<String> hints) {
        this.hints = hints;
        return this;
    }

    /**
     * Gets the max records.
     *
     * @return the max records
     */
    public String getMaxRecords() {
        return maxRecords;
    }

    /**
     * Sets the max records.
     *
     * @param maxRecords the max records
     * @return the HDBXSO data setting
     */
    public XSODataSetting setMaxRecords(String maxRecords) {
        this.maxRecords = maxRecords;
        return this;
    }

    /**
     * Gets the max expanded records.
     *
     * @return the max expanded records
     */
    public String getMaxExpandedRecords() {
        return maxExpandedRecords;
    }

    /**
     * Sets the max expanded records.
     *
     * @param maxExpandedRecords the max expanded records
     * @return the HDBXSO data setting
     */
    public XSODataSetting setMaxExpandedRecords(String maxExpandedRecords) {
        this.maxExpandedRecords = maxExpandedRecords;
        return this;
    }

    /**
     * Equals.
     *
     * @param o the o
     * @return true, if successful
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        XSODataSetting that = (XSODataSetting) o;
        return enableSupportNull == that.enableSupportNull && Objects.equals(contentCacheControl, that.contentCacheControl)
                && Objects.equals(metadataCacheControl, that.metadataCacheControl) && Objects.equals(hints, that.hints)
                && Objects.equals(maxRecords, that.maxRecords) && Objects.equals(maxExpandedRecords, that.maxExpandedRecords);
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(enableSupportNull, contentCacheControl, metadataCacheControl, hints, maxRecords, maxExpandedRecords);
    }
}
