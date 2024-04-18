/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.hdb.repository;

import com.codbex.kronos.engine.hdb.domain.HDBProcedure;
import org.eclipse.dirigible.components.base.artefact.ArtefactRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Interface HDBProcedureRepository.
 */
@Repository("hdbprocedureRepository")
public interface HDBProcedureRepository extends ArtefactRepository<HDBProcedure, Long> {

  @Override
  @Modifying
  @Transactional
  @Query(value = "UPDATE HDBProcedure SET running = :running")
  void setRunningToAll(@Param("running") boolean running);

}
