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
import java.util.List;
import java.util.Objects;

public class HDBXSODataEntity {

  private HDBXSODataRepositoryObject repositoryObject;
  private String alias;

  private List<String> withPropertyProjections = new ArrayList<>();
  private List<String> withoutPropertyProjections = new ArrayList<>();

  private List<String> keyList = new ArrayList<>();
  private String keyGenerated;

  private List<HDBXSODataNavigation> navigates = new ArrayList<>();

  private List<HDBXSODataAggregation> aggregations = new ArrayList<>();
  private HDBXSODataAggregationType aggregationType;

  private HDBXSODataParameterType parameterType;
  private HDBXSODataParameter parameterEntitySet;

  private List<HDBXSODataModification> modifications = new ArrayList<>();

  private boolean concurrencyToken;
  private List<String> eTags = new ArrayList<>();

  public HDBXSODataRepositoryObject getRepositoryObject() {
    return repositoryObject;
  }

  public void setRepositoryObject(HDBXSODataRepositoryObject repositoryObject) {
    this.repositoryObject = repositoryObject;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public List<String> getWithPropertyProjections() {
    return withPropertyProjections;
  }

  public void setWithPropertyProjections(List<String> withPropertyProjections) {
    this.withPropertyProjections = withPropertyProjections;
  }

  public List<String> getWithoutPropertyProjections() {
    return withoutPropertyProjections;
  }

  public void setWithoutPropertyProjections(List<String> withoutPropertyProjections) {
    this.withoutPropertyProjections = withoutPropertyProjections;
  }

  public List<String> getKeyList() {
    return keyList;
  }

  public void setKeyList(List<String> keyList) {
    this.keyList = keyList;
  }

  public String getKeyGenerated() {
    return keyGenerated;
  }

  public void setKeyGenerated(String keyGenerated) {
    this.keyGenerated = keyGenerated;
  }

  public List<HDBXSODataNavigation> getNavigates() {
    return navigates;
  }

  public void setNavigates(List<HDBXSODataNavigation> navigates) {
    this.navigates = navigates;
  }

  public List<HDBXSODataAggregation> getAggregations() {
    return aggregations;
  }

  public void setAggregations(List<HDBXSODataAggregation> aggregations) {
    this.aggregations = aggregations;
  }

  public HDBXSODataAggregationType getAggregationType() {
    return aggregationType;
  }

  public void setAggregationType(HDBXSODataAggregationType aggregationType) {
    this.aggregationType = aggregationType;
  }

  public HDBXSODataParameterType getParameterType() {
    return parameterType;
  }

  public void setParameterType(HDBXSODataParameterType parameterType) {
    this.parameterType = parameterType;
  }

  public HDBXSODataParameter getParameterEntitySet() {
    return parameterEntitySet;
  }

  public void setParameterEntitySet(HDBXSODataParameter parameterEntitySet) {
    this.parameterEntitySet = parameterEntitySet;
  }

  public List<HDBXSODataModification> getModifications() {
    return modifications;
  }

  public void setModifications(List<HDBXSODataModification> modifications) {
    this.modifications = modifications;
  }

  public boolean isConcurrencyToken() {
    return concurrencyToken;
  }

  public void setConcurrencyToken(boolean concurrencyToken) {
    this.concurrencyToken = concurrencyToken;
  }

  public List<String> getETags() {
    return eTags;
  }

  public void setETags(List<String> eTags) {
    this.eTags = eTags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    HDBXSODataEntity that = (HDBXSODataEntity) o;
    return concurrencyToken == that.concurrencyToken && Objects.equals(repositoryObject, that.repositoryObject) && Objects.equals(alias,
        that.alias) && Objects.equals(withPropertyProjections, that.withPropertyProjections) && Objects.equals(withoutPropertyProjections,
        that.withoutPropertyProjections) && Objects.equals(keyList, that.keyList) && Objects.equals(keyGenerated, that.keyGenerated)
        && Objects.equals(navigates, that.navigates) && Objects.equals(aggregations, that.aggregations)
        && aggregationType == that.aggregationType && parameterType == that.parameterType && Objects.equals(parameterEntitySet,
        that.parameterEntitySet) && Objects.equals(modifications, that.modifications) && Objects.equals(eTags, that.eTags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(repositoryObject, alias, withPropertyProjections, withoutPropertyProjections, keyList, keyGenerated, navigates,
        aggregations, aggregationType, parameterType, parameterEntitySet, modifications, concurrencyToken, eTags);
  }
}
