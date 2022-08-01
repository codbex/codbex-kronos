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
package com.codbex.kronos.resources.neo.sdk.synchronizer;

import org.eclipse.dirigible.core.scheduler.api.IJobDefinitionProvider;
import org.eclipse.dirigible.core.scheduler.api.ISchedulerCoreService;
import org.eclipse.dirigible.core.scheduler.service.definition.JobDefinition;

/**
 * The Neo Sdk Resources Synchronizer Job Definition Provider.
 */
public class NeoSdkResourcesSynchronizerJobDefinitionProvider implements IJobDefinitionProvider {

	private static final String KRONOS_NEO_SDK_RESOURCES_SYNCHRONIZER_JOB = "kronos-neo-sdk-resources-synchronizer-job";
	
	static final String KRONOS_NEO_SDK_RESOURCES_SYNCHRONIZER_JOB_NAME = "Kronos Resources Neo SDK Synchronizer Job";

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.core.scheduler.api.IJobDefinitionProvider#getJobDefinition()
	 */
	@Override
	public JobDefinition getJobDefinition() {
		JobDefinition jobDefinition = new JobDefinition();
		jobDefinition.setName(KRONOS_NEO_SDK_RESOURCES_SYNCHRONIZER_JOB);
		jobDefinition.setGroup(ISchedulerCoreService.JOB_GROUP_INTERNAL);
		jobDefinition.setClazz(NeoSdkResourcesSynchronizerJob.class.getCanonicalName());
		jobDefinition.setDescription(KRONOS_NEO_SDK_RESOURCES_SYNCHRONIZER_JOB_NAME);
		jobDefinition.setExpression("");
		jobDefinition.setSingleton(true);
		return jobDefinition;
	}

}