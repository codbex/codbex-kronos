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
package com.codbex.kronos.api.destination;

import com.sap.cloud.sdk.cloudplatform.CloudPlatformFacade;
import io.vavr.control.Try;
import jakarta.annotation.Nonnull;

/**
 * The Class CloudPlatformKymaFacade.
 */
public class CloudPlatformKymaFacade implements CloudPlatformFacade {

    /** The cloud platform. */
    private Try<com.sap.cloud.sdk.cloudplatform.CloudPlatform> cloudPlatform;

    /**
     * Try get cloud platform.
     *
     * @return the try
     */
    @Nonnull
    public Try<com.sap.cloud.sdk.cloudplatform.CloudPlatform> tryGetCloudPlatform() {
        if (this.cloudPlatform == null) {
            this.cloudPlatform = Try.of(CloudPlatform::new);
        }

        return this.cloudPlatform;
    }
}
