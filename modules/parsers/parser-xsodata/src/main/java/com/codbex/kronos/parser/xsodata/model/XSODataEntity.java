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

/**
 * The Class HDBXSODataEntity.
 */
public class XSODataEntity {

  /** The repository object. */
  private XSODataRepositoryObject repositoryObject;
  
  /** The alias. */
  private String alias;

  /** The with property projections. */
  private List<String> withPropertyProjections = new ArrayList<>();
  
  /** The without property projections. */
  private List<String> withoutPropertyProjections = new ArrayList<>();

  /** The key list. */
  private List<String> keyList = new ArrayList<>();
  
  /** The key generated. */
  private String keyGenerated;

  /** The navigates. */
  private List<XSODataNavigation> navigates = new ArrayList<>();

  /** The aggregations. */
  private List<XSODataAggregation> aggregations = new ArrayList<>();
  
  /** The aggregation type. */
  private XSODataAggregationType aggregationType;

  /** The parameter type. */
  private XSODataParameterType parameterType;
  
  /** The parameter entity set. */
  private XSODataParameter parameterEntitySet;

  /** The modifications. */
  private List<XSODataModification> modifications = new ArrayList<>();

  /** The concurrency token. */
  private boolean concurrencyToken;
  
  /** The e tags. */
  private List<String> eTags = new ArrayList<>();

  /**
   * Gets the repository object.
   *
   * @return the repository object
   */
  public XSODataRepositoryObject getRepositoryObject() {
    return repositoryObject;
  }

  /**
   * Sets the repository object.
   *
   * @param repositoryObject the new repository object
   */
  public void setRepositoryObject(XSODataRepositoryObject repositoryObject) {
    this.repositoryObject = repositoryObject;
  }

  /**
   * Gets the alias.
   *
   * @return the alias
   */
  public String getAlias() {
    return alias;
  }

  /**
   * Sets the alias.
   *
   * @param alias the new alias
   */
  public void setAlias(String alias) {
    this.alias = alias;
  }

  /**
   * Gets the with property projections.
   *
   * @return the with property projections
   */
  public List<String> getWithPropertyProjections() {
    return withPropertyProjections;
  }

  /**
   * Sets the with property projections.
   *
   * @param withPropertyProjections the new with property projections
   */
  public void setWithPropertyProjections(List<String> withPropertyProjections) {
    this.withPropertyProjections = withPropertyProjections;
  }

  /**
   * Gets the without property projections.
   *
   * @return the without property projections
   */
  public List<String> getWithoutPropertyProjections() {
    return withoutPropertyProjections;
  }

  /**
   * Sets the without property projections.
   *
   * @param withoutPropertyProjections the new without property projections
   */
  public void setWithoutPropertyProjections(List<String> withoutPropertyProjections) {
    this.withoutPropertyProjections = withoutPropertyProjections;
  }

  /**
   * Gets the key list.
   *
   * @return the key list
   */
  public List<String> getKeyList() {
    return keyList;
  }

  /**
   * Sets the key list.
   *
   * @param keyList the new key list
   */
  public void setKeyList(List<String> keyList) {
    this.keyList = keyList;
  }

  /**
   * Gets the key generated.
   *
   * @return the key generated
   */
  public String getKeyGenerated() {
    return keyGenerated;
  }

  /**
   * Sets the key generated.
   *
   * @param keyGenerated the new key generated
   */
  public void setKeyGenerated(String keyGenerated) {
    this.keyGenerated = keyGenerated;
  }

  /**
   * Gets the navigates.
   *
   * @return the navigates
   */
  public List<XSODataNavigation> getNavigates() {
    return navigates;
  }

  /**
   * Sets the navigates.
   *
   * @param navigates the new navigates
   */
  public void setNavigates(List<XSODataNavigation> navigates) {
    this.navigates = navigates;
  }

  /**
   * Gets the aggregations.
   *
   * @return the aggregations
   */
  public List<XSODataAggregation> getAggregations() {
    return aggregations;
  }

  /**
   * Sets the aggregations.
   *
   * @param aggregations the new aggregations
   */
  public void setAggregations(List<XSODataAggregation> aggregations) {
    this.aggregations = aggregations;
  }

  /**
   * Gets the aggregation type.
   *
   * @return the aggregation type
   */
  public XSODataAggregationType getAggregationType() {
    return aggregationType;
  }

  /**
   * Sets the aggregation type.
   *
   * @param aggregationType the new aggregation type
   */
  public void setAggregationType(XSODataAggregationType aggregationType) {
    this.aggregationType = aggregationType;
  }

  /**
   * Gets the parameter type.
   *
   * @return the parameter type
   */
  public XSODataParameterType getParameterType() {
    return parameterType;
  }

  /**
   * Sets the parameter type.
   *
   * @param parameterType the new parameter type
   */
  public void setParameterType(XSODataParameterType parameterType) {
    this.parameterType = parameterType;
  }

  /**
   * Gets the parameter entity set.
   *
   * @return the parameter entity set
   */
  public XSODataParameter getParameterEntitySet() {
    return parameterEntitySet;
  }

  /**
   * Sets the parameter entity set.
   *
   * @param parameterEntitySet the new parameter entity set
   */
  public void setParameterEntitySet(XSODataParameter parameterEntitySet) {
    this.parameterEntitySet = parameterEntitySet;
  }

  /**
   * Gets the modifications.
   *
   * @return the modifications
   */
  public List<XSODataModification> getModifications() {
    return modifications;
  }

  /**
   * Sets the modifications.
   *
   * @param modifications the new modifications
   */
  public void setModifications(List<XSODataModification> modifications) {
    this.modifications = modifications;
  }

  /**
   * Checks if is concurrency token.
   *
   * @return true, if is concurrency token
   */
  public boolean isConcurrencyToken() {
    return concurrencyToken;
  }

  /**
   * Sets the concurrency token.
   *
   * @param concurrencyToken the new concurrency token
   */
  public void setConcurrencyToken(boolean concurrencyToken) {
    this.concurrencyToken = concurrencyToken;
  }

  /**
   * Gets the e tags.
   *
   * @return the e tags
   */
  public List<String> getETags() {
    return eTags;
  }

  /**
   * Sets the e tags.
   *
   * @param eTags the new e tags
   */
  public void setETags(List<String> eTags) {
    this.eTags = eTags;
  }

  /**
   * Equals.
   *
   * @param o the o
   * @return true, if successful
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    XSODataEntity that = (XSODataEntity) o;
    return concurrencyToken == that.concurrencyToken && Objects.equals(repositoryObject, that.repositoryObject) && Objects.equals(alias,
        that.alias) && Objects.equals(withPropertyProjections, that.withPropertyProjections) && Objects.equals(withoutPropertyProjections,
        that.withoutPropertyProjections) && Objects.equals(keyList, that.keyList) && Objects.equals(keyGenerated, that.keyGenerated)
        && Objects.equals(navigates, that.navigates) && Objects.equals(aggregations, that.aggregations)
        && aggregationType == that.aggregationType && parameterType == that.parameterType && Objects.equals(parameterEntitySet,
        that.parameterEntitySet) && Objects.equals(modifications, that.modifications) && Objects.equals(eTags, that.eTags);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    return Objects.hash(repositoryObject, alias, withPropertyProjections, withoutPropertyProjections, keyList, keyGenerated, navigates,
        aggregations, aggregationType, parameterType, parameterEntitySet, modifications, concurrencyToken, eTags);
  }
}
