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

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.google.gson.internal.LinkedTreeMap;
import org.eclipse.dirigible.commons.api.helpers.GsonHelper;

public class AccessArtifact {

  private boolean exposed;
  private List<String> authorization;
  private List<Authentication> authentication;

  public AccessArtifact() {
  }

  public static AccessArtifact parse(byte[] json) {
    LinkedTreeMap artifactAsObject = (LinkedTreeMap) GsonHelper.GSON.fromJson(new InputStreamReader(new ByteArrayInputStream(json), StandardCharsets.UTF_8), Object.class);
    if(artifactAsObject.get("authentication") instanceof LinkedTreeMap) {
      AccessArtifact artifact = new AccessArtifact();
      artifact.setExposed(Boolean.TRUE.equals(artifactAsObject.get("exposed")));
      artifact.setAuthorization((ArrayList) artifactAsObject.get("authorization"));
      LinkedTreeMap authenticationAsObject = (LinkedTreeMap) artifactAsObject.get("authentication");
      Authentication authentication = new Authentication();
      authentication.setMethod(String.valueOf(authenticationAsObject.get("method")));
      ArrayList<Authentication> authenticationAsList = new ArrayList(Arrays.asList(authentication));
      artifact.setAuthentication(authenticationAsList);

      return artifact;
    } else {
      return GsonHelper.GSON.fromJson(new InputStreamReader(new ByteArrayInputStream(json), StandardCharsets.UTF_8),
          AccessArtifact.class);
    }
  }

  public static AccessArtifact parse(String json) {
    return GsonHelper.GSON.fromJson(json, AccessArtifact.class);
  }

  public boolean isExposed() {
    return exposed;
  }

  public void setExposed(boolean exposed) {
    this.exposed = exposed;
  }

  public List<String> getAuthorization() {
    return authorization;
  }

  public void setAuthorization(List<String> authorization) {
    this.authorization = authorization;
  }

  public List<Authentication> getAuthentication() {
    return authentication;
  }

  public void setAuthentication(List<Authentication> authentication) {
    this.authentication = authentication;
  }


  public AccessDefinition toAccessDefinition() {
    AccessDefinition accessDefinition = new AccessDefinition();
    accessDefinition.setAuthorizationRolesAsList(getAuthorization());
    if (getAuthentication() != null) {
      accessDefinition.setAuthenticationMethodsAsList(getAuthentication().stream().map(auth -> auth.getMethod()).collect(Collectors.toList()));
    }
    accessDefinition.setExposed(isExposed());

    return accessDefinition;
  }
}