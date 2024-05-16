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
package com.codbex.kronos.xsjob.ds.model;

import java.util.List;

/**
 * The Class JobArtifact.
 */
public class JobArtifact {

    /** The description. */
    private String description;

    /** The action. */
    private String action;

    /** The schedules. */
    private List<JobSchedule> schedules;

    /**
     * Instantiates a new job artifact.
     */
    public JobArtifact() {}

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the action.
     *
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the action.
     *
     * @param action the new action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Gets the schedules.
     *
     * @return the schedules
     */
    public List<JobSchedule> getSchedules() {
        return schedules;
    }

    /**
     * Sets the schedules.
     *
     * @param schedules the new schedules
     */
    public void setSchedules(List<JobSchedule> schedules) {
        this.schedules = schedules;
    }
}
