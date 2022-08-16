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

/**
 * The Class HDBXSODataService.
 */
public class HDBXSODataService {

  /** The namespace. */
  private String namespace;
  
  /** The enable O data 4 SAP annotations. */
  private boolean enableOData4SAPAnnotations;
  
  /** The setting. */
  private HDBXSODataSetting setting;
  
  /** The entities. */
  private ArrayList<HDBXSODataEntity> entities = new ArrayList<>();
  
  /** The associations. */
  private ArrayList<HDBXSODataAssociation> associations = new ArrayList<>();

  /**
   * Gets the namespace.
   *
   * @return the namespace
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * Sets the namespace.
   *
   * @param namespace the new namespace
   */
  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  /**
   * Checks if is enable O data 4 SAP annotations.
   *
   * @return true, if is enable O data 4 SAP annotations
   */
  public boolean isEnableOData4SAPAnnotations() {
    return enableOData4SAPAnnotations;
  }

  /**
   * Sets the enable O data 4 SAP annotations.
   *
   * @param enableOData4SAPAnnotations the new enable O data 4 SAP annotations
   */
  public void setEnableOData4SAPAnnotations(boolean enableOData4SAPAnnotations) {
    this.enableOData4SAPAnnotations = enableOData4SAPAnnotations;
  }

  /**
   * Gets the setting.
   *
   * @return the setting
   */
  public HDBXSODataSetting getSetting() {
    return setting;
  }

  /**
   * Sets the setting.
   *
   * @param setting the new setting
   */
  public void setSetting(HDBXSODataSetting setting) {
    this.setting = setting;
  }

  /**
   * Gets the entities.
   *
   * @return the entities
   */
  public ArrayList<HDBXSODataEntity> getEntities() {
    return entities;
  }

  /**
   * Sets the entities.
   *
   * @param entities the new entities
   */
  public void setEntities(ArrayList<HDBXSODataEntity> entities) {
    this.entities = entities;
  }

  /**
   * Gets the associations.
   *
   * @return the associations
   */
  public ArrayList<HDBXSODataAssociation> getAssociations() {
    return associations;
  }

  /**
   * Sets the associations.
   *
   * @param associations the new associations
   */
  public void setAssociations(ArrayList<HDBXSODataAssociation> associations) {
    this.associations = associations;
  }

  /**
   * Equals.
   *
   * @param o the o
   * @return true, if successful
   */
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

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(namespace, enableOData4SAPAnnotations, setting, entities, associations);
  }
}
