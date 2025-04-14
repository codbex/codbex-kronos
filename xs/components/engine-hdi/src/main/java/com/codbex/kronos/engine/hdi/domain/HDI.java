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
package com.codbex.kronos.engine.hdi.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

import org.eclipse.dirigible.components.base.converters.ArrayOfStringsToCsvConverter;

import com.codbex.kronos.engine.hdb.domain.HDBDataStructure;
import com.google.gson.annotations.Expose;

/**
 * The Class DataStructureHDIModel.
 */
@Entity
@Table(name = "KRONOS_HDI")
public class HDI extends HDBDataStructure {

    /** The Constant ARTEFACT_TYPE. */
    public static final String ARTEFACT_TYPE = "hdi";

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HDI_ID", nullable = false)
    private Long id;

    /** The configuration. */
    @Column(name = "HDI_CONFIG", columnDefinition = "VARCHAR", nullable = true, length = 2000)
    @Nullable
    @Expose
    private String configuration;

    /** The users. */
    @Column(name = "HDI_USERS", columnDefinition = "VARCHAR", nullable = true, length = 2000)
    @Nullable
    @Convert(converter = ArrayOfStringsToCsvConverter.class)
    @Expose
    private String[] users;

    /** The group. */
    @Column(name = "HDI_GROUP", columnDefinition = "VARCHAR", nullable = true, length = 255)
    @Nullable
    @Expose
    private String group;

    /** The container. */
    @Column(name = "HDI_CONTAINER", columnDefinition = "VARCHAR", nullable = true, length = 255)
    @Nullable
    @Expose
    private String container;

    /** The deploy. */
    @Column(name = "HDI_DEPLOY", columnDefinition = "VARCHAR", nullable = true, length = 20000)
    @Nullable
    @Convert(converter = ArrayOfStringsToCsvConverter.class)
    @Expose
    private String[] deploy;

    /** The undeploy. */
    @Column(name = "HDI_UNDEPLOY", columnDefinition = "VARCHAR", nullable = true, length = 20000)
    @Nullable
    @Convert(converter = ArrayOfStringsToCsvConverter.class)
    @Expose
    private String[] undeploy;

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
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the configuration.
     *
     * @return the configuration
     */
    public String getConfiguration() {
        return configuration;
    }

    /**
     * Sets the configuration.
     *
     * @param configuration the new configuration
     */
    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    /**
     * Gets the users.
     *
     * @return the users
     */
    public String[] getUsers() {
        return users;
    }

    /**
     * Sets the users.
     *
     * @param users the new users
     */
    public void setUsers(String[] users) {
        this.users = users;
    }

    /**
     * Gets the group.
     *
     * @return the group
     */
    public String getGroup() {
        return group;
    }

    /**
     * Sets the group.
     *
     * @param group the new group
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Gets the container.
     *
     * @return the container
     */
    public String getContainer() {
        return container;
    }

    /**
     * Sets the container.
     *
     * @param container the new container
     */
    public void setContainer(String container) {
        this.container = container;
    }

    /**
     * Gets the deploy.
     *
     * @return the deploy
     */
    public String[] getDeploy() {
        return deploy;
    }

    /**
     * Sets the deploy.
     *
     * @param deploy the new deploy
     */
    public void setDeploy(String[] deploy) {
        this.deploy = deploy;
    }

    /**
     * Gets the undeploy.
     *
     * @return the undeploy
     */
    public String[] getUndeploy() {
        return undeploy;
    }

    /**
     * Sets the undeploy.
     *
     * @param undeploy the new undeploy
     */
    public void setUndeploy(String[] undeploy) {
        this.undeploy = undeploy;
    }

    /**
     * Checks if is mandatory field missing.
     *
     * @return true, if is mandatory field missing
     */
    public boolean isMandatoryFieldMissing() {

        return Objects.isNull(this.configuration) || Objects.isNull(this.group) || Objects.isNull(this.container);
    }

    /**
     * Checks for deployment file.
     *
     * @return true, if successful
     */
    public boolean hasDeploymentFile() {
        return (Objects.nonNull(this.deploy) && Objects.nonNull(this.undeploy)) && this.deploy.length >= 1 || this.undeploy.length >= 1;
    }

    /**
     * Checks for misused deployment file.
     *
     * @return true, if successful
     */
    public boolean hasMisusedDeploymentFile() {
        if ((Objects.nonNull(this.deploy) && Objects.nonNull(this.undeploy))) {
            Set<String> deploy = new HashSet<>(Arrays.asList(this.deploy));
            Set<String> undeploy = new HashSet<>(Arrays.asList(this.undeploy));
            deploy.retainAll(undeploy);
            return deploy.size() > 0;
        }

        return false;
    }
}
