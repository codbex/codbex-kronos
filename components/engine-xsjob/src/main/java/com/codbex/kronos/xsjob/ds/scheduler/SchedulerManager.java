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
package com.codbex.kronos.xsjob.ds.scheduler;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import com.codbex.kronos.engine.xsjob.domain.XSJob;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.xsjob.ds.scheduler.handler.JobHandler;

import java.util.Date;
import java.util.Set;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The Class SchedulerManager.
 */
@Component
public class SchedulerManager implements InitializingBean {

	/** The kronos defined group. */
	public static String KRONOS_DEFINED_GROUP = "kronos-defined";

	/** The kronos job parameters. */
	public static String KRONOS_JOB_PARAMETERS = "kronos-job-parameters";

	/** The kronos job function. */
	public static String KRONOS_JOB_FUNCTION = "kronos-job-function";

	/** The kronos job module. */
	public static String KRONOS_JOB_MODULE = "kronos-job-module";

	/** The kronos job description. */
	public static String KRONOS_JOB_DESCRIPTION = "jobDescription";

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(SchedulerManager.class);

	/** The scheduler. */

	@Autowired
	private Scheduler scheduler;

	/** The instance. */
	private static SchedulerManager INSTANCE;

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
	public static SchedulerManager get() {
		return INSTANCE;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}

	/**
	 * Schedule job.
	 *
	 * @param jobDefinition the job definition
	 * @throws Exception the scheduler exception
	 */
	public static void scheduleJob(XSJob jobDefinition) throws Exception {
		try {
			JobKey jobKey = new JobKey(jobDefinition.getName(), jobDefinition.getGroup());
			TriggerKey triggerKey = new TriggerKey(jobDefinition.getName(), jobDefinition.getGroup());
			if (!SchedulerManager.get().getScheduler().checkExists(jobKey)
					&& (!SchedulerManager.get().getScheduler().checkExists(triggerKey))) {
				JobDetail job;
				if (KRONOS_DEFINED_GROUP.equals(jobDefinition.getGroup())) {
					// user defined jobs
					job = newJob(JobHandler.class).withIdentity(jobKey).withDescription(jobDefinition.getDescription())
							.build();
					job.getJobDataMap().put(KRONOS_JOB_PARAMETERS, jobDefinition.getParametersAsMap());
					job.getJobDataMap().put(KRONOS_JOB_FUNCTION, jobDefinition.getFunction());
					job.getJobDataMap().put(KRONOS_JOB_MODULE, jobDefinition.getModule());
				} else {
					return;
				}

				TriggerBuilder<CronTrigger> triggerBuilder = newTrigger().withIdentity(triggerKey).withSchedule(
						cronSchedule(jobDefinition.getCronExpression()).withMisfireHandlingInstructionDoNothing());

				if (jobDefinition.getStartAt() != null) {
					triggerBuilder.startAt(new Date(jobDefinition.getStartAt().getTime()));
				}

				if (jobDefinition.getEndAt() != null) {
					triggerBuilder.endAt(new Date(jobDefinition.getEndAt().getTime()));
				}

				SchedulerManager.get().getScheduler().scheduleJob(job, triggerBuilder.build());

				logger.info("Scheduled Job: [{}] of group: [{}] at: [{}]", jobDefinition.getName(),
						jobDefinition.getGroup(), jobDefinition.getCronExpression());
			}
		} catch (ObjectAlreadyExistsException e) {
			logger.warn(e.getMessage());
		} catch (org.quartz.SchedulerException e) {
			CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.SCHEDULER_ERROR, jobDefinition.getName(),
					CommonsConstants.JOB_PARSER);
			throw new Exception(e);
		}
	}

	/**
	 * Unschedule job.
	 *
	 * @param name  the name
	 * @param group the group
	 * @throws Exception the scheduler exception
	 */
	public static void unscheduleJob(String name, String group) throws Exception {
		if (!KRONOS_DEFINED_GROUP.equals(group)) {
			return;
		}
		try {
			JobKey jobKey = new JobKey(name, group);
			TriggerKey triggerKey = new TriggerKey(name, group);
			if (SchedulerManager.get().getScheduler().checkExists(triggerKey)) {
				SchedulerManager.get().getScheduler().unscheduleJob(triggerKey);
				SchedulerManager.get().getScheduler().deleteJob(jobKey);
				logger.info("Unscheduled Job: [{}] of group: [{}]", name, group);
			}
		} catch (ObjectAlreadyExistsException e) {
			logger.warn(e.getMessage());
		} catch (org.quartz.SchedulerException e) {
			CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.SCHEDULER_ERROR, name,
					CommonsConstants.JOB_PARSER);
			throw new Exception(e);
		}
	}

	/**
	 * List jobs.
	 *
	 * @return the sets the
	 * @throws Exception the scheduler exception
	 */
	public static Set<TriggerKey> listJobs() throws Exception {
		try {
			Set<TriggerKey> triggerKeys = SchedulerManager.get().getScheduler().getTriggerKeys(GroupMatcher.anyTriggerGroup());
			return triggerKeys;
		} catch (org.quartz.SchedulerException e) {
			CommonsUtils.logProcessorErrors(e.getMessage(), CommonsConstants.SCHEDULER_ERROR, "-",
					CommonsConstants.JOB_PARSER);
			throw new Exception(e);
		}
	}

	/**
	 * Exists job.
	 *
	 * @param name the name
	 * @return true, if successful
	 * @throws Exception the scheduler exception
	 */
	public static boolean existsJob(String name) throws Exception {
		Set<TriggerKey> triggerKeys = listJobs();
		for (TriggerKey triggerKey : triggerKeys) {
			if (triggerKey.getName().equals(name)
					&& KRONOS_DEFINED_GROUP.equals(triggerKey.getGroup())) {
				return true;
			}
		}
		return false;
	}
}