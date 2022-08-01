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
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.api.v3.problems.ProblemsFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.codbex.kronos.xsjob.ds.model.JobArtifact;
import com.codbex.kronos.xsjob.ds.model.JobDefinition;
import com.codbex.kronos.xsjob.ds.service.JobCoreService;
import com.codbex.kronos.xsjob.ds.transformer.JobToKronosJobDefinitionTransformer;

@RunWith(MockitoJUnitRunner.class)
public class JobTransformerTest {

  private final JobCoreService jobCoreService = new JobCoreService();
  private final JobToKronosJobDefinitionTransformer jobToKronosJobDefinitionTransformer = new JobToKronosJobDefinitionTransformer();

	@Test
	public void executeTransformSuccessfully() throws Exception {
    String expectedCronExpressionEveryFiveSeconds = "*/5 * * * * ? *";
    String xsjobSample = IOUtils.toString(JobTransformerTest.class.getResourceAsStream("/TestJobTransformSuccess.xsjob"), StandardCharsets.UTF_8);
		JobArtifact jobArtifact = jobCoreService.parseJob(xsjobSample);
		Map<String, String> parametersAsMap = jobArtifact.getSchedules().get(0).getParameter();
		ArrayList<JobDefinition> jobDefinitions = jobToKronosJobDefinitionTransformer.transform(jobArtifact);

		assertEquals("XSJOB:bugXsjob.xsjs::logFunc-0", jobDefinitions.get(0).getName());
		assertEquals("XSJOB/bugXsjob.xsjs", jobDefinitions.get(0).getModule());
		assertEquals("logFunc", jobDefinitions.get(0).getFunction());
		assertEquals("My Job configuration My Schedule configuration for execution every second", jobDefinitions.get(0).getDescription());
    assertEquals(expectedCronExpressionEveryFiveSeconds, jobDefinitions.get(0).getCronExpression());
		assertEquals(parametersAsMap, jobDefinitions.get(0).getParametersAsMap());
		assertEquals(parametersAsMap.size(), 1);
		assertEquals(jobDefinitions.size(), 1);
	}

	@Test(expected = IllegalStateException.class)
	public void executeTransformFailed() throws Exception {
		try (MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
			problemsFacade.when(() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any())).thenAnswer((Answer<Void>) invocation -> null);
			String xsjobSample = IOUtils.toString(JobTransformerTest.class.getResourceAsStream("/TestJobTransformFailure.xsjob"), StandardCharsets.UTF_8);
			JobArtifact jobArtifact = jobCoreService.parseJob(xsjobSample);
			jobToKronosJobDefinitionTransformer.transform(jobArtifact);
		}
	}

	@Test
	public void executeTransformWithEmptySchedule() throws Exception {
		String xsjobSample = IOUtils.toString(JobTransformerTest.class.getResourceAsStream("/TestJobTransformWithEmptySchedule.xsjob"), StandardCharsets.UTF_8);
		JobArtifact jobArtifact = jobCoreService.parseJob(xsjobSample);
		ArrayList<JobDefinition> jobDefinitions = jobToKronosJobDefinitionTransformer.transform(jobArtifact);
		assertEquals(jobDefinitions.size(), 0);
	}

  @Test
  public void testTransformEachDayOfWeekAndMonth() throws Exception {
    String expectedCronExpressionEveryWeekDay = "0 30 22 ? * MON,TUE,WED,THU,FRI *";
    String xsjobSample = IOUtils.toString(JobTransformerTest.class.getResourceAsStream("/TestJobTransformerCronExpression.xsjob"), StandardCharsets.UTF_8);
    JobArtifact jobArtifact = jobCoreService.parseJob(xsjobSample);
    ArrayList<JobDefinition> jobDefinitions = jobToKronosJobDefinitionTransformer.transform(jobArtifact);
    assertEquals(jobDefinitions.size(), 1);
    assertEquals(jobDefinitions.get(0).getCronExpression(), expectedCronExpressionEveryWeekDay);
  }
}
