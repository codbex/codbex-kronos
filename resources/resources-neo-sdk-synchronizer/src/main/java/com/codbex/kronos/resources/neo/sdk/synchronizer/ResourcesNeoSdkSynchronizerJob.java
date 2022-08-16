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

import java.util.concurrent.TimeUnit;

import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizerJob;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizer;

/**
 * The Resources Neo Sdk Synchronizer Job.
 */
public class ResourcesNeoSdkSynchronizerJob extends AbstractSynchronizerJob {

	private static final int TIMEOUT_TIME = 10;

	/** The extensions synchronizer. */
	private ResourcesNeoSdkSynchronizer synchronizer = new ResourcesNeoSdkSynchronizer();

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizerJob#getSynchronizer()
	 */
	@Override
	public ISynchronizer getSynchronizer() {
		return synchronizer;
	}

	@Override
	public String getName() {
		return ResourcesNeoSdkSynchronizerJobDefinitionProvider.RESOURCES_NEO_SDK_SYNCHRONIZER_JOB;
	}

	@Override
	protected int getTimeout() {
		return TIMEOUT_TIME;
	}
	
	@Override
	protected TimeUnit getTimeoutUnit() {
		return TimeUnit.MINUTES;
	}
}