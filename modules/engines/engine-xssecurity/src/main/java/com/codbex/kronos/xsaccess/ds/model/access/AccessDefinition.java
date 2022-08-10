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
package com.codbex.kronos.xsaccess.ds.model.access;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class AccessDefinition.
 */
@Table(name = "KRONOS_SECURITY_ACCESS")
public class AccessDefinition {

  /** The path. */
  @Id
  @Column(name = "ACCESS_PATH", columnDefinition = "VARCHAR", nullable = false, length = 255)
  private String path;

  /** The authentication methods. */
  @Column(name = "AUTHENTICATION_METHODS", columnDefinition = "BLOB", nullable = false, length = 1000)
  private byte[] authenticationMethods;

  /** The authentication methods as list. */
  private List<String> authenticationMethodsAsList;

  /** The authorization roles. */
  @Column(name = "AUTHORIZATION_ROLES", columnDefinition = "BLOB", nullable = false, length = 1000)
  private byte[] authorizationRoles;

  /** The exposed. */
  @Column(name = "EXPOSED", columnDefinition = "BOOLEAN", nullable = false, length = 1)
  private boolean exposed = true;

  /** The hash. */
  @Column(name = "ACCESS_HASH", columnDefinition = "VARCHAR", nullable = true, length = 32)
  private String hash;

  /** The authorization roles as list. */
  private List<String> authorizationRolesAsList;

  /** The created by. */
  @Column(name = "ACCESS_CREATED_BY", columnDefinition = "VARCHAR", nullable = false, length = 32)
  private String createdBy;

  /** The created at. */
  @Column(name = "ACCESS_CREATED_AT", columnDefinition = "TIMESTAMP", nullable = false)
  private Timestamp createdAt;

  /**
   * Instantiates a new access definition.
   */
  public AccessDefinition() {
  }

  /**
   * Checks if is exposed.
   *
   * @return true, if is exposed
   */
  public boolean isExposed() {
    return exposed;
  }

  /**
   * Sets the exposed.
   *
   * @param exposed the new exposed
   */
  public void setExposed(boolean exposed) {
    this.exposed = exposed;
  }

  /**
   * Gets the hash.
   *
   * @return the hash
   */
  public String getHash() {
    return hash;
  }

  /**
   * Sets the hash.
   *
   * @param hash the new hash
   */
  public void setHash(String hash) {
    this.hash = hash;
  }

  /**
   * Gets the authorization roles.
   *
   * @return the authorization roles
   */
  public byte[] getAuthorizationRoles() {
    return authorizationRoles;
  }

  /**
   * Sets the authorization roles.
   *
   * @param authorizationRoles the new authorization roles
   */
  public void setAuthorizationRoles(byte[] authorizationRoles) {
    this.authorizationRoles = authorizationRoles;
  }

  /**
   * Gets the authorization roles as list.
   *
   * @return the authorization roles as list
   */
  public List<String> getAuthorizationRolesAsList() {
    return authorizationRolesAsList;
  }

  /**
   * Sets the authorization roles as list.
   *
   * @param authorizationRolesAsList the new authorization roles as list
   */
  public void setAuthorizationRolesAsList(List<String> authorizationRolesAsList) {
    this.authorizationRolesAsList = authorizationRolesAsList;
  }

  /**
   * Gets the authentication methods.
   *
   * @return the authentication methods
   */
  public byte[] getAuthenticationMethods() {
    return authenticationMethods;
  }

  /**
   * Sets the authentication methods.
   *
   * @param authenticationMethods the new authentication methods
   */
  public void setAuthenticationMethods(byte[] authenticationMethods) {
    this.authenticationMethods = authenticationMethods;
  }

  /**
   * Gets the authentication methods as list.
   *
   * @return the authentication methods as list
   */
  public List<String> getAuthenticationMethodsAsList() {
    return authenticationMethodsAsList;
  }

  /**
   * Sets the authentication methods as list.
   *
   * @param authenticationMethodsAsList the new authentication methods as list
   */
  public void setAuthenticationMethodsAsList(List<String> authenticationMethodsAsList) {
    this.authenticationMethodsAsList = authenticationMethodsAsList;
  }

  /**
   * Gets the path.
   *
   * @return the path
   */
  public String getPath() {
    return path;
  }

  /**
   * Sets the path.
   *
   * @param path the new path
   */
  public void setPath(String path) {
    this.path = path;
  }

  /**
   * Gets the created by.
   *
   * @return the created by
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * Sets the created by.
   *
   * @param createdBy the new created by
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * Gets the created at.
   *
   * @return the created at
   */
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the created at.
   *
   * @param createdAt the new created at
   */
  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
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
    AccessDefinition that = (AccessDefinition) o;
    return exposed == that.exposed &&
        path.equals(that.path) &&
        authenticationMethodsAsList.equals(that.authenticationMethodsAsList) &&
        Arrays.equals(authorizationRoles, that.authorizationRoles) &&
        hash.equals(that.hash) &&
        authorizationRolesAsList.equals(that.authorizationRolesAsList) &&
        createdBy.equals(that.createdBy) &&
        createdAt.equals(that.createdAt);
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  @Override
  public int hashCode() {
    int result = Objects.hash(path, authenticationMethodsAsList, exposed, hash, authorizationRolesAsList, createdBy, createdAt);
    result = 31 * result + Arrays.hashCode(authorizationRoles);
    return result;
  }
}