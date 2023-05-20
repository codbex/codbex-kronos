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

/**
 * The Class PrivilegeArtifact.
 */
public class PrivilegeArtifact {

  /** The privileges. */
  private List<Privilege> privileges;

  /**
   * Instantiates a new privilege artifact.
   */
  public PrivilegeArtifact() {
  }

  /**
   * Parses the.
   *
   * @param json the json
   * @return the privilege artifact
   */
  public static PrivilegeArtifact parse(byte[] json) {
    return GsonHelper
        .fromJson(new InputStreamReader(new ByteArrayInputStream(json), StandardCharsets.UTF_8), PrivilegeArtifact.class);
  }

  /**
   * Parses the.
   *
   * @param json the json
   * @return the privilege artifact
   */
  public static PrivilegeArtifact parse(String json) {
    return GsonHelper.fromJson(json, PrivilegeArtifact.class);
  }

  /**
   * Gets the privileges.
   *
   * @return the privileges
   */
  public List<Privilege> getPrivileges() {
    return privileges;
  }

  /**
   * Sets the privileges.
   *
   * @param privileges the new privileges
   */
  public void setPrivileges(List<Privilege> privileges) {
    this.privileges = privileges;
  }

  /**
   * Divide.
   *
   * @return the list
   */
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