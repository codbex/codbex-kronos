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
package com.codbex.kronos.parser.xsodata.model;

import java.util.ArrayList;
import java.util.Objects;

public class HDBXSODataService {

  private String namespace;
  private boolean enableOData4SAPAnnotations;
  private HDBXSODataSetting setting;
  private ArrayList<HDBXSODataEntity> entities = new ArrayList<>();
  private ArrayList<HDBXSODataAssociation> associations = new ArrayList<>();

  public String getNamespace() {
    return namespace;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public boolean isEnableOData4SAPAnnotations() {
    return enableOData4SAPAnnotations;
  }

  public void setEnableOData4SAPAnnotations(boolean enableOData4SAPAnnotations) {
    this.enableOData4SAPAnnotations = enableOData4SAPAnnotations;
  }

  public HDBXSODataSetting getSetting() {
    return setting;
  }

  public void setSetting(HDBXSODataSetting setting) {
    this.setting = setting;
  }

  public ArrayList<HDBXSODataEntity> getEntities() {
    return entities;
  }

  public void setEntities(ArrayList<HDBXSODataEntity> entities) {
    this.entities = entities;
  }

  public ArrayList<HDBXSODataAssociation> getAssociations() {
    return associations;
  }

  public void setAssociations(ArrayList<HDBXSODataAssociation> associations) {
    this.associations = associations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    HDBXSODataService that = (HDBXSODataService) o;
    return enableOData4SAPAnnotations == that.enableOData4SAPAnnotations && Objects.equals(namespace, that.namespace) && Objects.equals(
        setting, that.setting) && Objects.equals(entities, that.entities) && Objects.equals(associations, that.associations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(namespace, enableOData4SAPAnnotations, setting, entities, associations);
  }
}
