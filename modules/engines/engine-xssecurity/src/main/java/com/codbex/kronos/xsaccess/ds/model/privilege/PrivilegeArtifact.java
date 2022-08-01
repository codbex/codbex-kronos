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
package com.codbex.kronos.xsaccess.ds.model.privilege;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.dirigible.commons.api.helpers.GsonHelper;

public class PrivilegeArtifact {

  private List<Privilege> privileges;

  public PrivilegeArtifact() {
  }

  public static PrivilegeArtifact parse(byte[] json) {
    return GsonHelper.GSON
        .fromJson(new InputStreamReader(new ByteArrayInputStream(json), StandardCharsets.UTF_8), PrivilegeArtifact.class);
  }

  public static PrivilegeArtifact parse(String json) {
    return GsonHelper.GSON.fromJson(json, PrivilegeArtifact.class);
  }

  public List<Privilege> getPrivileges() {
    return privileges;
  }

  public void setPrivileges(List<Privilege> privileges) {
    this.privileges = privileges;
  }

  public List<PrivilegeDefinition> divide() {
    List<PrivilegeDefinition> privilegeDefinitions = new ArrayList<>();
    for (Privilege privilege : privileges) {
      PrivilegeDefinition privilegeDefinition = new PrivilegeDefinition();
      privilegeDefinition.setName(privilege.getName());
      privilegeDefinition.setDescription(privilege.getDescription());

      privilegeDefinitions.add(privilegeDefinition);
    }

    return privilegeDefinitions;
  }
}