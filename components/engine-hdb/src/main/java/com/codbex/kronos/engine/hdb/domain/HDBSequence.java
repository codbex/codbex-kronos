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
package com.codbex.kronos.engine.hdb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.codbex.kronos.parser.hdbsequence.models.HDBSequenceModel;

/**
 * The Class View.
 */
@Entity
@Table(name = "KRONOS_SEQUENCES")
public class HDBSequence extends HDBDataStructure {

    /** The Constant ARTEFACT_TYPE. */
    public static final String ARTEFACT_TYPE = "hdbsequence";

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HDBSEQ_ID", nullable = false)
    private Long id;

    /** The increment by. */
    @Column(name = "HDBSEQ_INCREMENT_BY", columnDefinition = "INTEGER DEFAULT 1", nullable = true)
    private Integer incrementBy;

    /** The start with. */
    @Column(name = "HDBSEQ_START_WITH", columnDefinition = "INTEGER DEFAULT 1", nullable = true)
    private Integer startWith;

    /** The max value. */
    @Column(name = "HDBSEQ_MAX_VALUE", columnDefinition = "INTEGER", nullable = true)
    private Integer maxValue;

    /** The no max value. */
    @Column(name = "HDBSEQ_NO_MAX_VALUE", columnDefinition = "BOOLEAN", nullable = true)
    private Boolean noMaxValue;

    /** The min value. */
    @Column(name = "HDBSEQ_MIN_VALUE", columnDefinition = "INTEGER DEFAULT 1", nullable = true)
    private Integer minValue;

    /** The no min value. */
    @Column(name = "HDBSEQ_NO_MIN_VALUE", columnDefinition = "BOOLEAN", nullable = true)
    private Boolean noMinValue;

    /** The cycles. */
    @Column(name = "HDBSEQ_CYCLES", columnDefinition = "BOOLEAN", nullable = true)
    private Boolean cycles;

    /** The reset by. */
    @Column(name = "HDBSEQ_RESET_BY", columnDefinition = "VARCHAR", nullable = true, length = 255)
    private String resetBy;

    /** The depends on table. */
    @Column(name = "HDBSEQ_DEPENDS_ON_TABLE", columnDefinition = "VARCHAR", nullable = true, length = 255)
    private String dependsOnTable;

    /** The depends on view. */
    @Column(name = "HDBSEQ_DEPENDS_ON_VIEW", columnDefinition = "VARCHAR", nullable = true, length = 255)
    private String dependsOnView;

    /** The public prop. */
    @Column(name = "HDBSEQ_IS_PUBLIC", columnDefinition = "BOOLEAN DEFAULT FALSE", nullable = true)
    private Boolean isPublic;

    /**
     * Instantiates a new model.
     *
     * @param location the location
     * @param name the name
     * @param description the description
     * @param dependencies the dependencies
     * @param schema the schema
     * @param content the content
     * @param classic the classic
     * @param id the id
     * @param incrementBy the increment by
     * @param startWith the start with
     * @param maxValue the max value
     * @param noMaxValue the no max value
     * @param minValue the min value
     * @param noMinValue the no min value
     * @param cycles the cycles
     * @param resetBy the reset by
     * @param dependsOnTable the depends on table
     * @param dependsOnView the depends on view
     * @param isPublic the is public
     */
    public HDBSequence(String location, String name, String description, String dependencies, String schema, String content,
            boolean classic, Long id, Integer incrementBy, Integer startWith, Integer maxValue, Boolean noMaxValue, Integer minValue,
            Boolean noMinValue, Boolean cycles, String resetBy, String dependsOnTable, String dependsOnView, Boolean isPublic) {
        super(location, name, ARTEFACT_TYPE, description, dependencies, schema, content, classic);
        this.id = id;
        this.incrementBy = incrementBy;
        this.startWith = startWith;
        this.maxValue = maxValue;
        this.noMaxValue = noMaxValue;
        this.minValue = minValue;
        this.noMinValue = noMinValue;
        this.cycles = cycles;
        this.resetBy = resetBy;
        this.dependsOnTable = dependsOnTable;
        this.dependsOnView = dependsOnView;
        this.isPublic = isPublic;
    }

    /**
     * Instantiates a new HDB sequence.
     *
     * @param model the model
     */
    public HDBSequence(HDBSequenceModel model) {
        this.incrementBy = model.getIncrementBy();
        this.startWith = model.getStartWith();
        this.maxValue = model.getMaxValue();
        this.noMaxValue = model.getNoMaxValue();
        this.noMinValue = model.getNoMinValue();
        this.minValue = model.getMinValue();
        this.cycles = model.getCycles();
        this.resetBy = model.getResetBy();
        this.dependsOnTable = model.getDependsOnTable();
        this.dependsOnView = model.getDependsOnView();
        this.isPublic = model.getPublic();
        this.setSchema(model.getSchema());
    }

    /**
     * Instantiates a new model.
     */
    public HDBSequence() {
        super();
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the increment by.
     *
     * @return the increment by
     */
    public Integer getIncrementBy() {
        return incrementBy;
    }

    /**
     * Sets the increment by.
     *
     * @param incrementBy the new increment by
     */
    public void setIncrementBy(Integer incrementBy) {
        this.incrementBy = incrementBy;
    }

    /**
     * Gets the start with.
     *
     * @return the start with
     */
    public Integer getStartWith() {
        return startWith;
    }

    /**
     * Sets the start with.
     *
     * @param startWith the new start with
     */
    public void setStartWith(Integer startWith) {
        this.startWith = startWith;
    }

    /**
     * Gets the max value.
     *
     * @return the max value
     */
    public Integer getMaxValue() {
        return maxValue;
    }

    /**
     * Sets the max value.
     *
     * @param maxValue the new max value
     */
    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * Gets the no max value.
     *
     * @return the no max value
     */
    public Boolean getNoMaxValue() {
        return noMaxValue;
    }

    /**
     * Sets the no max value.
     *
     * @param noMaxValue the new no max value
     */
    public void setNoMaxValue(Boolean noMaxValue) {
        this.noMaxValue = noMaxValue;
    }

    /**
     * Gets the min value.
     *
     * @return the min value
     */
    public Integer getMinValue() {
        return minValue;
    }

    /**
     * Sets the min value.
     *
     * @param minValue the new min value
     */
    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    /**
     * Gets the no min value.
     *
     * @return the no min value
     */
    public Boolean getNoMinValue() {
        return noMinValue;
    }

    /**
     * Sets the no min value.
     *
     * @param noMinValue the new no min value
     */
    public void setNoMinValue(Boolean noMinValue) {
        this.noMinValue = noMinValue;
    }

    /**
     * Gets the cycles.
     *
     * @return the cycles
     */
    public Boolean getCycles() {
        return cycles;
    }

    /**
     * Sets the cycles.
     *
     * @param cycles the new cycles
     */
    public void setCycles(Boolean cycles) {
        this.cycles = cycles;
    }

    /**
     * Gets the reset by.
     *
     * @return the reset by
     */
    public String getResetBy() {
        return resetBy;
    }

    /**
     * Sets the reset by.
     *
     * @param resetBy the new reset by
     */
    public void setResetBy(String resetBy) {
        this.resetBy = resetBy;
    }

    /**
     * Gets the depends on table.
     *
     * @return the depends on table
     */
    public String getDependsOnTable() {
        return dependsOnTable;
    }

    /**
     * Sets the depends on table.
     *
     * @param dependsOnTable the new depends on table
     */
    public void setDependsOnTable(String dependsOnTable) {
        this.dependsOnTable = dependsOnTable;
    }

    /**
     * Gets the depends on view.
     *
     * @return the depends on view
     */
    public String getDependsOnView() {
        return dependsOnView;
    }

    /**
     * Sets the depends on view.
     *
     * @param dependsOnView the new depends on view
     */
    public void setDependsOnView(String dependsOnView) {
        this.dependsOnView = dependsOnView;
    }

    /**
     * Gets the checks if is public.
     *
     * @return the checks if is public
     */
    public Boolean getIsPublic() {
        return isPublic;
    }

    /**
     * Sets the checks if is public.
     *
     * @param isPublic the new checks if is public
     */
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "HDBSequence [id=" + id + ", incrementBy=" + incrementBy + ", startWith=" + startWith + ", maxValue=" + maxValue
                + ", noMaxValue=" + noMaxValue + ", minValue=" + minValue + ", noMinValue=" + noMinValue + ", cycles=" + cycles
                + ", resetBy=" + resetBy + ", dependsOnTable=" + dependsOnTable + ", dependsOnView=" + dependsOnView + ", isPublic="
                + isPublic + ", location=" + location + ", name=" + name + ", type=" + type + ", description=" + description + ", key="
                + key + ", dependencies=" + dependencies + ", lifecycle=" + lifecycle + ", phase=" + phase + ", error=" + error
                + ", createdBy=" + createdBy + ", createdAt=" + createdAt + ", updatedBy=" + updatedBy + ", updatedAt=" + updatedAt + "]";
    }

}
