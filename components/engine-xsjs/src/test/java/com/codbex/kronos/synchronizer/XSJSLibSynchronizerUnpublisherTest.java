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
package com.codbex.kronos.synchronizer;

//import org.eclipse.dirigible.repository.api.IEntity;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;
//
//import com.codbex.kronos.synchronizer.XSJSLibSynchronizerRegistryEntity;
//import com.codbex.kronos.synchronizer.XSJSLibSynchronizerUnpublisher;
//import com.codbex.kronos.synchronizer.cleaners.XSJSLibSynchronizerCleaner;
//import com.codbex.kronos.synchronizer.cleaners.XSJSLibSynchronizerDBCleaner;
//import com.codbex.kronos.synchronizer.cleaners.XSJSLibSynchronizerFileCleaner;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
/**
 * The Class XSJSLibSynchronizerUnpublisherTest.
 */
//@RunWith(MockitoJUnitRunner.class)
public class XSJSLibSynchronizerUnpublisherTest {
//  @Test
//  public void unpublishResourceTest() {
//    String testPath = "test/testResource.xsjslib";
//
//    IEntity entityMock = mock(IEntity.class);
//    when(entityMock.getPath()).thenReturn(testPath);
//    XSJSLibSynchronizerRegistryEntity xsjsEntityMock = mock(XSJSLibSynchronizerRegistryEntity.class);
//    when(xsjsEntityMock.getEntity()).thenReturn(entityMock);
//    when(xsjsEntityMock.isSynchronizable()).thenReturn(true);
//    when(xsjsEntityMock.isCollection()).thenReturn(false);
//
//    XSJSLibSynchronizerCleaner fileCleanerMock = mock(XSJSLibSynchronizerFileCleaner.class);
//    XSJSLibSynchronizerCleaner dbCleanerMock = mock(XSJSLibSynchronizerDBCleaner.class);
//
//    XSJSLibSynchronizerUnpublisher unpublisher = new XSJSLibSynchronizerUnpublisher(fileCleanerMock, dbCleanerMock);
//    unpublisher.unpublish(xsjsEntityMock);
//
//    verify(fileCleanerMock, times(1)).cleanup(testPath);
//    verify(dbCleanerMock, times(1)).cleanup(testPath);
//  }
//
//  @Test
//  public void unpublishCollectionTest() {
//    String testPath = "test/";
//
//    IEntity entityMock = mock(IEntity.class);
//    when(entityMock.getPath()).thenReturn(testPath);
//    XSJSLibSynchronizerRegistryEntity xsjsEntityMock = mock(XSJSLibSynchronizerRegistryEntity.class);
//    when(xsjsEntityMock.getEntity()).thenReturn(entityMock);
//    when(xsjsEntityMock.isSynchronizable()).thenReturn(true);
//    when(xsjsEntityMock.isCollection()).thenReturn(true);
//
//    XSJSLibSynchronizerCleaner fileCleanerMock = mock(XSJSLibSynchronizerFileCleaner.class);
//    XSJSLibSynchronizerCleaner dbCleanerMock = mock(XSJSLibSynchronizerDBCleaner.class);
//
//    XSJSLibSynchronizerUnpublisher unpublisher = new XSJSLibSynchronizerUnpublisher(fileCleanerMock, dbCleanerMock);
//    unpublisher.unpublish(xsjsEntityMock);
//
//    verify(fileCleanerMock, times(0)).cleanup(testPath);
//    verify(dbCleanerMock, times(1)).cleanup(testPath);
//  }
//
//  @Test
//  public void unpublishNonExistentResourceTest() {
//    String testPath = "non/existent/resource.xsjslib";
//
//    XSJSLibSynchronizerRegistryEntity xsjsEntityMock = mock(XSJSLibSynchronizerRegistryEntity.class);
//    when(xsjsEntityMock.isSynchronizable()).thenReturn(false);
//
//    XSJSLibSynchronizerCleaner fileCleanerMock = mock(XSJSLibSynchronizerFileCleaner.class);
//    XSJSLibSynchronizerCleaner dbCleanerMock = mock(XSJSLibSynchronizerDBCleaner.class);
//
//    XSJSLibSynchronizerUnpublisher unpublisher = new XSJSLibSynchronizerUnpublisher(fileCleanerMock, dbCleanerMock);
//    unpublisher.unpublish(xsjsEntityMock);
//
//    verify(fileCleanerMock, times(0)).cleanup(testPath);
//    verify(dbCleanerMock, times(0)).cleanup(testPath);
//  }
}
