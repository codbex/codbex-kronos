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
package com.codbex.kronos.engine.xssecurity.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.codbex.kronos.engine.xssecurity.domain.XSAccess;
import com.codbex.kronos.engine.xssecurity.domain.XSAuthentication;
import com.google.gson.internal.LinkedTreeMap;
import org.eclipse.dirigible.commons.api.helpers.GsonHelper;

/**
 * The Class AccessArtifact.
 */
public class XSAccessParser {

  /** The exposed. */
  private boolean exposed;
  
  /** The authorization. */
  private List<String> authorization;
  
  /** The authentication. */
  private List<XSAuthentication> authentication;

  /**
   * Instantiates a new access artifact.
   */
  public XSAccessParser() {
  }

  /**
   * Parses the.
   *
   * @param json the json
   * @return the access artifact
   */
  public static XSAccessParser parse(byte[] json) {
    LinkedTreeMap artifactAsObject = (LinkedTreeMap) GsonHelper.fromJson(new InputStreamReader(new ByteArrayInputStream(json), StandardCharsets.UTF_8), Object.class);
    if(artifactAsObject.get("authentication") instanceof LinkedTreeMap) {
      XSAccessParser artifact = new XSAccessParser();
      artifact.setExposed(Boolean.TRUE.equals(artifactAsObject.get("exposed")));
      artifact.setAuthorization((ArrayList) artifactAsObject.get("authorization"));
      LinkedTreeMap authenticationAsObject = (LinkedTreeMap) artifactAsObject.get("authentication");
      XSAuthentication authentication = new XSAuthentication();
      authentication.setMethod(String.valueOf(authenticationAsObject.get("method")));
      ArrayList<XSAuthentication> authenticationAsList = new ArrayList(Arrays.asList(authentication));
      artifact.setAuthentication(authenticationAsList);

      return artifact;
    } else {
      return GsonHelper.fromJson(new InputStreamReader(new ByteArrayInputStream(json), StandardCharsets.UTF_8),
          XSAccessParser.class);
    }
  }

  /**
   * Parses the.
   *
   * @param json the json
   * @return the access artifact
   */
  public static XSAccessParser parse(String json) {
    return GsonHelper.fromJson(json, XSAccessParser.class);
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
   * Gets the authorization.
   *
   * @return the authorization
   */
  public List<String> getAuthorization() {
    return authorization;
  }

  /**
   * Sets the authorization.
   *
   * @param authorization the new authorization
   */
  public void setAuthorization(List<String> authorization) {
    this.authorization = authorization;
  }

  /**
   * Gets the authentication.
   *
   * @return the authentication
   */
  public List<XSAuthentication> getAuthentication() {
    return authentication;
  }

  /**
   * Sets the authentication.
   *
   * @param authentication the new authentication
   */
  public void setAuthentication(List<XSAuthentication> authentication) {
    this.authentication = authentication;
  }

  /**
   * To access definition.
   *
   * @return the access definition
   */
  public XSAccess toAccessDefinition() {
    XSAccess accessDefinition = new XSAccess();
    accessDefinition.setAuthorizationRoles(getAuthorization());
    if (getAuthentication() != null) {
      accessDefinition.setAuthenticationMethods(getAuthentication().stream().map(auth -> auth.getMethod()).collect(Collectors.toList()));
    }
    accessDefinition.setExposed(isExposed());

    return accessDefinition;
  }
}