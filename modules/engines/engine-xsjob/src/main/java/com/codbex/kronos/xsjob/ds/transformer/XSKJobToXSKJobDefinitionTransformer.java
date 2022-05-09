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
package com.codbex.kronos.xsjob.ds.transformer;

import com.codbex.kronos.utils.XSKCommonsConstants;
import com.codbex.kronos.utils.XSKCommonsUtils;
import com.codbex.kronos.xsjob.ds.model.XSKJobArtifact;
import com.codbex.kronos.xsjob.ds.model.XSKJobDefinition;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XSKJobToXSKJobDefinitionTransformer {

  private XSKCronToQuartzCronTransformer xskCronToQuartzCronTransformer = new XSKCronToQuartzCronTransformer();

  public ArrayList<XSKJobDefinition> transform(XSKJobArtifact xskJobArtifact) throws ParseException {
    ArrayList<XSKJobDefinition> jobDefinitions = new ArrayList<>();
    String[] parseAction = xskJobArtifact.getAction().split("::");
    final int PARTS_OF_ACTION_PROPERTY = 2;

    if(parseAction.length == PARTS_OF_ACTION_PROPERTY){
      String filePath = parseAction[0];
      String functionName = parseAction[1];
      filePath = xskPathToDirigiblePath(filePath);

      for (int i = 0; i < xskJobArtifact.getSchedules().size(); i++) {
        XSKJobDefinition xskJobDefinition = new XSKJobDefinition();
        String xskJobDefinitionName = xskJobArtifact.getAction() + "-" + i;
        String xskCronExpression = xskJobArtifact.getSchedules().get(i).getXscron();
        String quartzCronExpression = xskCronToQuartzCronTransformer.transform(xskCronExpression);

        xskJobDefinition.setName(xskJobDefinitionName);
        xskJobDefinition.setParametersAsMap(xskJobArtifact.getSchedules().get(i).getParameter());
        xskJobDefinition.setDescription(xskJobArtifact.getDescription() + " " + xskJobArtifact.getSchedules().get(i).getDescription());
        xskJobDefinition.setCronExpression(quartzCronExpression);
        xskJobDefinition.setModule(filePath);
        xskJobDefinition.setFunction(functionName);
        jobDefinitions.add(xskJobDefinition);
      }
    }else {
      XSKCommonsUtils.logProcessorErrors("Invalid xsjob artifact definition!", XSKCommonsConstants.PROCESSOR_ERROR, xskJobArtifact.getDescription(), XSKCommonsConstants.XSK_JOB_PARSER);
      throw new IllegalStateException("Invalid xsjob artifact definition!");
    }

    return jobDefinitions;
  }

  public String xskPathToDirigiblePath(String xskFilePath) {
    String[] splitXscFilePath = xskFilePath.split(":");
    List<String> splitPackage = Arrays.asList(splitXscFilePath[0].split("\\."));

    return String.join("/", splitPackage) + "/" + splitXscFilePath[1];
  }
}
