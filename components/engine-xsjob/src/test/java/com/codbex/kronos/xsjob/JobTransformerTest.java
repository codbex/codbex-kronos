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
package com.codbex.kronos.xsjob;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.commons.api.helpers.GsonHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.codbex.kronos.engine.xsjob.domain.XSJob;
import com.codbex.kronos.xsjob.ds.model.JobArtifact;
import com.codbex.kronos.xsjob.ds.transformer.JobToKronosJobDefinitionTransformer;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
@EntityScan(value = { "org.eclipse.dirigible.components", "com.codbex.kronos" })
@Transactional
@ExtendWith(MockitoExtension.class)
public class JobTransformerTest {

	private final JobToKronosJobDefinitionTransformer jobToKronosJobDefinitionTransformer = new JobToKronosJobDefinitionTransformer();

	@Test
	public void executeTransformSuccessfully() throws Exception {
		String expectedCronExpressionEveryFiveSeconds = "*/5 * * * * ? *";
		String xsjobSample = IOUtils.toString(
				JobTransformerTest.class.getResourceAsStream("/TestJobTransformSuccess.xsjob"), StandardCharsets.UTF_8);
		JobArtifact jobArtifact = GsonHelper.fromJson(xsjobSample, JobArtifact.class);
		Map<String, String> parametersAsMap = jobArtifact.getSchedules().get(0).getParameter();
		ArrayList<XSJob> jobDefinitions = jobToKronosJobDefinitionTransformer.transform(jobArtifact);

		assertEquals("XSJOB:bugXsjob.xsjs::logFunc-0", jobDefinitions.get(0).getName());
		assertEquals("XSJOB/bugXsjob.xsjs", jobDefinitions.get(0).getModule());
		assertEquals("logFunc", jobDefinitions.get(0).getFunction());
		assertEquals("My Job configuration My Schedule configuration for execution every second",
				jobDefinitions.get(0).getDescription());
		assertEquals(expectedCronExpressionEveryFiveSeconds, jobDefinitions.get(0).getCronExpression());
		assertEquals(parametersAsMap, jobDefinitions.get(0).getParametersAsMap());
		assertEquals(parametersAsMap.size(), 1);
		assertEquals(jobDefinitions.size(), 1);
	}

//	@Test
//	public void executeTransformFailed() throws Exception {
//
//		IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
//			try (MockedStatic<ProblemsFacade> problemsFacade = Mockito.mockStatic(ProblemsFacade.class)) {
//				problemsFacade.when(
//						() -> ProblemsFacade.save(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
//						.thenAnswer((Answer<Void>) invocation -> null);
//				String xsjobSample = IOUtils.toString(
//						JobTransformerTest.class.getResourceAsStream("/TestJobTransformFailure.xsjob"),
//						StandardCharsets.UTF_8);
//				JobArtifact jobArtifact = GsonHelper.fromJson(xsjobSample, JobArtifact.class);
//				jobToKronosJobDefinitionTransformer.transform(jobArtifact);
//			}
//		});
//
//	}

	@Test
	public void executeTransformWithEmptySchedule() throws Exception {
		String xsjobSample = IOUtils.toString(
				JobTransformerTest.class.getResourceAsStream("/TestJobTransformWithEmptySchedule.xsjob"),
				StandardCharsets.UTF_8);
		JobArtifact jobArtifact = GsonHelper.fromJson(xsjobSample, JobArtifact.class);
		ArrayList<XSJob> jobDefinitions = jobToKronosJobDefinitionTransformer.transform(jobArtifact);
		assertEquals(jobDefinitions.size(), 0);
	}

	@Test
	public void testTransformEachDayOfWeekAndMonth() throws Exception {
		String expectedCronExpressionEveryWeekDay = "0 30 22 ? * MON,TUE,WED,THU,FRI *";
		String xsjobSample = IOUtils.toString(
				JobTransformerTest.class.getResourceAsStream("/TestJobTransformerCronExpression.xsjob"),
				StandardCharsets.UTF_8);
		JobArtifact jobArtifact = GsonHelper.fromJson(xsjobSample, JobArtifact.class);
		ArrayList<XSJob> jobDefinitions = jobToKronosJobDefinitionTransformer.transform(jobArtifact);
		assertEquals(jobDefinitions.size(), 1);
		assertEquals(jobDefinitions.get(0).getCronExpression(), expectedCronExpressionEveryWeekDay);
	}

	/**
	 * The Class TestConfiguration.
	 */
	@EnableJpaRepositories(basePackages = "com.codbex.kronos")
	@SpringBootApplication(scanBasePackages = { "com.codbex.kronos", "org.eclipse.dirigible.components" })
	@EnableScheduling
	static class TestConfiguration {
	}
}
