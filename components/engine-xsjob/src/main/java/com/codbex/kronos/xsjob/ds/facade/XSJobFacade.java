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
package com.codbex.kronos.xsjob.ds.facade;

import com.codbex.kronos.engine.xsjob.domain.XSJob;
import com.codbex.kronos.engine.xsjob.service.XSJobService;
import com.codbex.kronos.xsjob.ds.model.JobArtifact;
import com.codbex.kronos.xsjob.ds.scheduler.SchedulerManager;
import com.codbex.kronos.xsjob.ds.transformer.JobToKronosJobDefinitionTransformer;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import org.eclipse.dirigible.commons.api.helpers.GsonHelper;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Class JobFacade.
 */
@Component
public class XSJobFacade implements InitializingBean {

    /**
     * The job service.
     */
    @Autowired
    private XSJobService jobService;

    /**
     * The instance.
     */
    private static XSJobFacade INSTANCE;

    /**
     * After properties set.
     *
     * @throws Exception the exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        INSTANCE = this;
    }

    /**
     * Gets the.
     *
     * @return the HDI service
     */
    public static XSJobFacade get() {
        return INSTANCE;
    }

    /**
     * Gets the job service.
     *
     * @return the job service
     */
    public XSJobService getJobService() {
        return jobService;
    }

    /**
     * New job.
     *
     * @param job the job
     * @return the array list
     * @throws ParseException the parse exception
     */
    public static final ArrayList<String> newJob(String job) throws ParseException {
        JobArtifact jobArtifact = GsonHelper.fromJson(job, JobArtifact.class);
        JobToKronosJobDefinitionTransformer jobToKronosJobDefinitionTransformer = new JobToKronosJobDefinitionTransformer();
        ArrayList<XSJob> jobDefinitions = jobToKronosJobDefinitionTransformer.transform(jobArtifact);
        ArrayList<String> scheduleNames = new ArrayList<>();
        for (XSJob jobDefinition : jobDefinitions) {
            if (XSJobFacade.get()
                           .getJobService()
                           .findByName(jobDefinition.getName()) == null) {
                XSJobFacade.get()
                           .getJobService()
                           .save(jobDefinition);
            }
            scheduleNames.add(jobDefinition.getName());
        }

        return scheduleNames;
    }

    /**
     * Activate.
     *
     * @param names the names
     * @throws Exception the exception
     */
    public static final void activate(ArrayList<String> names) throws Exception {
        for (String name : names) {
            XSJob jobDefinition = XSJobFacade.get()
                                             .getJobService()
                                             .findByName(name);

            SchedulerManager.scheduleJob(jobDefinition);
        }
    }

    /**
     * Deactivate.
     *
     * @param names the names
     * @throws Exception the exception
     */
    public static final void deactivate(ArrayList<String> names) throws Exception {
        for (String name : names) {
            XSJob jobDefinition = XSJobFacade.get()
                                             .getJobService()
                                             .findByName(name);

            SchedulerManager.unscheduleJob(name, jobDefinition.getGroup());
        }
    }

    /**
     * Configure.
     *
     * @param names the names
     * @param status the status
     * @param startAt the start at
     * @param endAt the end at
     * @throws Exception the exception
     */
    public static final void configure(ArrayList<String> names, boolean status, Timestamp startAt, Timestamp endAt) throws Exception {
        deactivate(names);

        for (String name : names) {
            XSJob jobDefinition = XSJobFacade.get()
                                             .getJobService()
                                             .findByName(name);

            jobDefinition.setStartAt(startAt);
            jobDefinition.setEndAt(endAt);

            XSJobFacade.get()
                       .getJobService()
                       .save(jobDefinition);
        }

        if (status) {
            activate(names);
        }
    }

    /**
     * Gets the configuration.
     *
     * @param name the name
     * @return the configuration
     * @throws SchedulerException the scheduler exception
     */
    public static final XSJob getConfiguration(String name) throws SchedulerException {
        return XSJobFacade.get()
                          .getJobService()
                          .findByName(name);
    }

    /**
     * Checks if is active.
     *
     * @param name the name
     * @return true, if is active
     * @throws Exception the exception
     */
    public static final boolean isActive(String name) throws Exception {
        return SchedulerManager.existsJob(name);
    }

}
