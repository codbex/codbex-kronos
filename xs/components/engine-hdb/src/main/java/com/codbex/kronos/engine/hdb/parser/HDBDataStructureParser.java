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
package com.codbex.kronos.engine.hdb.parser;

import java.io.IOException;

import com.codbex.kronos.engine.hdb.api.DataStructuresException;
import com.codbex.kronos.engine.hdb.domain.HDBDataStructure;
import com.codbex.kronos.exceptions.ArtifactParserException;

/**
 * The Interface DataStructureParser.
 *
 * @param <T> the generic type
 */
public interface HDBDataStructureParser<T extends HDBDataStructure> {

    /**
     * Parses the.
     *
     * @param parametersModel the parameters model
     * @return the t
     * @throws DataStructuresException the data structures exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ArtifactParserException the artifact parser exception
     */
    T parse(HDBParameters parametersModel) throws DataStructuresException, IOException, ArtifactParserException;

    /**
     * Gets the type.
     *
     * @return the type
     */
    String getType();

    /**
     * Gets the data structure class.
     *
     * @return the data structure class
     */
    Class<T> getDataStructureClass();
}
