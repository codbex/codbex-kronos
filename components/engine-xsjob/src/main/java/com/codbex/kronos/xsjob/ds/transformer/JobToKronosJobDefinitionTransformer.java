/*
 * Copyright (c) 2022-2023 codbex or an codbex affiliate company and contributors
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

import com.codbex.kronos.engine.xsjob.domain.XSJob;
import com.codbex.kronos.utils.CommonsConstants;
import com.codbex.kronos.utils.CommonsUtils;
import com.codbex.kronos.xsjob.ds.model.JobArtifact;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Class JobToKronosJobDefinitionTransformer.
 */
public class JobToKronosJobDefinitionTransformer {

  /** The cron to quartz cron transformer. */
  private CronToQuartzCronTransformer cronToQuartzCronTransformer = new CronToQuartzCronTransformer();

  /**
   * Transform.
   *
   * @param jobArtifact the job artifact
   * @return the array list
   * @throws ParseException the parse exception
   */
  public ArrayList<XSJob> transform(JobArtifact jobArtifact) throws ParseException {
    ArrayList<XSJob> jobDefinitions = new ArrayList<>();
    String[] parseAction = jobArtifact.getAction().split("::");
    final int PARTS_OF_ACTION_PROPERTY = 2;

    if(parseAction.length == PARTS_OF_ACTION_PROPERTY){
      String filePath = parseAction[0];
      String functionName = parseAction[1];
      filePath = kronosPathToDirigiblePath(filePath);

      for (int i = 0; i < jobArtifact.getSchedules().size(); i++) {
        XSJob jobDefinition = new XSJob();
        String jobDefinitionName = jobArtifact.getAction() + "-" + i;
        String cronExpression = jobArtifact.getSchedules().get(i).getXscron();
        String quartzCronExpression = cronToQuartzCronTransformer.transform(cronExpression);

        jobDefinition.setName(jobDefinitionName);
        jobDefinition.setParametersAsMap(jobArtifact.getSchedules().get(i).getParameter());
        jobDefinition.setDescription(jobArtifact.getDescription() + " " + jobArtifact.getSchedules().get(i).getDescription());
        jobDefinition.setCronExpression(quartzCronExpression);
        jobDefinition.setModule(filePath);
        jobDefinition.setFunction(functionName);
        jobDefinitions.add(jobDefinition);
      }
    }else {
      CommonsUtils.logProcessorErrors("Invalid xsjob artifact definition!", CommonsConstants.PROCESSOR_ERROR, jobArtifact.getDescription(), CommonsConstants.JOB_PARSER);
      throw new IllegalStateException("Invalid xsjob artifact definition!");
    }

    return jobDefinitions;
  }

  /**
   * Kronos path to dirigible path.
   *
   * @param filePath the file path
   * @return the string
   */
  public String kronosPathToDirigiblePath(String filePath) {
    String[] splitXscFilePath = filePath.split(":");
    List<String> splitPackage = Arrays.asList(splitXscFilePath[0].split("\\."));

    return String.join("/", splitPackage) + "/" + splitXscFilePath[1];
  }
}
