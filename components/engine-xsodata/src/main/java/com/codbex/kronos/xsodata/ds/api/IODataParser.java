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
package com.codbex.kronos.xsodata.ds.api;

import com.codbex.kronos.xsodata.ds.model.ODataModel;

/**
 * The Interface IODataParser.
 */
public interface IODataParser {
    
    /**
     * Parses the O data artifact.
     *
     * @param location the location
     * @param content the content
     * @return the o data model
     * @throws Exception the exception
     */
    ODataModel parseODataArtifact(String location, String content) throws Exception;
}
