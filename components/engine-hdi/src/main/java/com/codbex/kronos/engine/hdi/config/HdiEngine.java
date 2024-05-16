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
package com.codbex.kronos.engine.hdi.config;

import org.eclipse.dirigible.components.base.artefact.Engine;
import org.springframework.stereotype.Component;

/**
 * The Class HdiEngine.
 */
@Component
public class HdiEngine implements Engine {

    /**
     * Gets the name.
     *
     * @return the name
     */
    @Override
    public String getName() {
        return "HDI";
    }

    /**
     * Gets the provider.
     *
     * @return the provider
     */
    @Override
    public String getProvider() {
        return "codbex Kronos";
    }

}
