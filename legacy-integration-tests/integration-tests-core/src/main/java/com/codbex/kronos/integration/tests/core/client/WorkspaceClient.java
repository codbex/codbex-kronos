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
package com.codbex.kronos.integration.tests.core.client;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.codbex.kronos.integration.tests.core.client.http.HttpClient;
import com.codbex.kronos.integration.tests.core.client.http.HttpClientException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class WorkspaceClient {

    private final URI workspaceServiceUri;
    private final URI transportServiceUri;

    private final HttpClient httpClient;

    public WorkspaceClient(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.workspaceServiceUri = httpClient.getBaseHost().resolve("/services/v4/ide/workspaces/");
        this.transportServiceUri = httpClient.getBaseHost().resolve("/services/v4/transport/project/");
    }

    private static byte[] zipProject(String projectName, Path projectFolderPath) {
        var byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            var filePaths = Files.walk(projectFolderPath)
                    .filter(path -> !Files.isDirectory(path))
                    .collect(Collectors.toList());
            for (var filePath : filePaths) {
                Path zipEntryPath = Path.of(projectName, projectFolderPath.relativize(filePath).toString());
                var zipEntry = new ZipEntry((zipEntryPath).toString());
                zipOutputStream.putNextEntry(zipEntry);
                Files.copy(filePath, zipOutputStream);
                zipOutputStream.closeEntry();
            }
        } catch (IOException e) {
            String errorMessage = "Could not zip path: " + projectFolderPath;
            throw new ClientException(errorMessage, e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public CompletableFuture<HttpResponse> createWorkspace(String workspaceName) {
        try {
            var uri = workspaceServiceUri.resolve(workspaceName);
            HttpUriRequest request = RequestBuilder.post(uri).build();
            return httpClient.executeRequestAsyncWithCallbackFuture(request);
        } catch (HttpClientException e) {
            String errorMessage = "Error when creating workspace " + workspaceName;
            throw new ClientException(errorMessage, e);
        }

    }

    public CompletableFuture<HttpResponse> importProjectInWorkspace(String workspaceName, String projectName, Path projectFolderPath) {
        try {
            byte[] projectZip = zipProject(projectName, projectFolderPath);
            var uri = transportServiceUri.resolve(workspaceName);
            HttpEntity multiPartHttpEntity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .addBinaryBody("file", projectZip)
                    .build();

            HttpUriRequest multipartRequest = RequestBuilder.post(uri)
                    .setEntity(multiPartHttpEntity)
                    .build();
            return httpClient.executeRequestAsyncWithCallbackFuture(multipartRequest);
        } catch (HttpClientException e) {
            String errorMessage = "Cannot import project " + projectName + " to workspace " + workspaceName;
            throw new ClientException(errorMessage, e);
        }

    }

    public CompletableFuture<HttpResponse> deleteWorkspace(String workspace) {
        try {
            var uri = workspaceServiceUri.resolve(workspace);
            HttpUriRequest request = RequestBuilder.delete(uri).build();
            return httpClient.executeRequestAsyncWithCallbackFuture(request);
        } catch (HttpClientException e) {
            String errorMessage = "Error deleting workspace " + workspace;
            throw new ClientException(errorMessage, e);
        }

    }

}
