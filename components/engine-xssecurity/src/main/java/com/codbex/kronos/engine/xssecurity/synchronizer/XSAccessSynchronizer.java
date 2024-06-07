/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
package com.codbex.kronos.engine.xssecurity.synchronizer;

import com.codbex.kronos.engine.xssecurity.domain.XSAccess;
import com.codbex.kronos.engine.xssecurity.parser.XSAccessParser;
import com.codbex.kronos.engine.xssecurity.service.XSAccessService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.util.List;
import org.eclipse.dirigible.components.base.artefact.ArtefactLifecycle;
import org.eclipse.dirigible.components.base.artefact.ArtefactPhase;
import org.eclipse.dirigible.components.base.artefact.topology.TopologyWrapper;
import org.eclipse.dirigible.components.base.synchronizer.BaseSynchronizer;
import org.eclipse.dirigible.components.base.synchronizer.SynchronizerCallback;
import org.eclipse.dirigible.components.base.synchronizer.SynchronizersOrder;
import org.eclipse.dirigible.components.security.domain.Access;
import org.eclipse.dirigible.components.security.service.AccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * The Class AccessSynchronizer.
 */

@Component
@Order(SynchronizersOrder.ACCESS)
public class XSAccessSynchronizer extends BaseSynchronizer<XSAccess, Long> {

    /**
     * The Constant logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(XSAccessSynchronizer.class);

    /**
     * The Constant FILE_EXTENSION_SECURITY_ACCESS.
     */
    private static final String FILE_EXTENSION_SECURITY_ACCESS = ".xsaccess";

    /**
     * The security access service.
     */
    private final XSAccessService xssecurityAccessService;

    /**
     * The security access service.
     */
    private final AccessService securityAccessService;

    /**
     * The synchronization callback.
     */
    private SynchronizerCallback callback;

    /**
     * Instantiates a new security access synchronizer.
     *
     * @param xssecurityAccessService the xssecurity access service
     * @param securityAccessService the security access service
     */
    @Autowired
    public XSAccessSynchronizer(XSAccessService xssecurityAccessService, AccessService securityAccessService) {
        this.xssecurityAccessService = xssecurityAccessService;
        this.securityAccessService = securityAccessService;
    }

    /**
     * Gets the service.
     *
     * @return the service
     */
    @Override
    public XSAccessService getService() {
        return xssecurityAccessService;
    }

    /**
     * Gets the service.
     *
     * @return the service
     */
    public AccessService getAccessService() {
        return securityAccessService;
    }

    /**
     * Checks if is accepted.
     *
     * @param file the file
     * @param attrs the attrs
     * @return true, if is accepted
     */
    @Override
    public boolean isAccepted(Path file, BasicFileAttributes attrs) {
        return file.toString()
                   .endsWith(getFileExtension());
    }

    /**
     * Checks if is accepted.
     *
     * @param type the artefact
     * @return true, if is accepted
     */
    @Override
    public boolean isAccepted(String type) {
        return XSAccess.ARTEFACT_TYPE.equals(type);
    }

    /**
     * Load.
     *
     * @param location the location
     * @param content the content
     * @return the list
     * @throws ParseException the parse exception
     */
    @Override
    public List<XSAccess> parse(String location, byte[] content) throws ParseException {
        XSAccess xsaccess = XSAccessParser.parse(content)
                                          .toAccessDefinition();
        xsaccess.setLocation(location);
        xsaccess.setName(Paths.get(location)
                              .getFileName()
                              .toString());
        xsaccess.setType(XSAccess.ARTEFACT_TYPE);
        xsaccess.setPath(removeXscFileFromPath(location));
        xsaccess.updateKey();

        XSAccess maybe = getService().findByKey(xsaccess.getKey());
        if (maybe != null) {
            xsaccess.setId(maybe.getId());
        }
        xsaccess = getService().save(xsaccess);

        Integer keyIndex = 1;

        for (String role : xsaccess.getAuthorizationRoles()) {
            Access access = new Access();
            access.setLocation(xsaccess.getLocation());
            access.setName(keyIndex.toString());
            access.setType(Access.ARTEFACT_TYPE);
            access.updateKey();
            access.setPath(xsaccess.getPath());
            if (xsaccess.getAuthenticationMethods() != null) {
                access.setMethod(xsaccess.getAuthenticationMethods()
                                         .get(0)); // used for HTTP Method as Authentication Method is not supported anyway
            } else {
                access.setMethod("*");
            }
            access.setRole(role);
            keyIndex++;

            Access maybeAccess = getAccessService().findByKey(access.getKey());
            if (maybeAccess != null) {
                access.setId(maybeAccess.getId());
            }
            access = getAccessService().save(access);
        }

        return List.of(xsaccess);
    }

    /**
     * Retrieve.
     *
     * @param location the location
     * @return the list
     */
    @Override
    public List<XSAccess> retrieve(String location) {
        return getService().getAll();
    }

    /**
     * Sets the status.
     *
     * @param artefact the artefact
     * @param lifecycle the lifecycle
     * @param error the error
     */
    @Override
    public void setStatus(XSAccess artefact, ArtefactLifecycle lifecycle, String error) {
        artefact.setLifecycle(lifecycle);
        artefact.setError(error);
        getService().save(artefact);
    }

    /**
     * Complete.
     *
     * @param wrapper the wrapper
     * @param flow the flow
     * @return true, if successful
     */
    @Override
    public boolean completeImpl(TopologyWrapper<XSAccess> wrapper, ArtefactPhase flow) {
        callback.registerState(this, wrapper, ArtefactLifecycle.CREATED);
        return true;
    }

    /**
     * Cleanup.
     *
     * @param xsaccess the xsaccess
     */
    @Override
    public void cleanupImpl(XSAccess xsaccess) {
        try {
            getService().delete(xsaccess);
            Access access = new Access();
            access.setLocation(xsaccess.getLocation());
            access.setName(xsaccess.getName());
            access.setType(Access.ARTEFACT_TYPE);
            access.updateKey();
            getAccessService().delete(access);
            callback.registerState(this, xsaccess, ArtefactLifecycle.DELETED);
        } catch (Exception e) {
            callback.addError(e.getMessage());
            callback.registerState(this, xsaccess, ArtefactLifecycle.FAILED, e);
        }
    }

    /**
     * Sets the callback.
     *
     * @param callback the new callback
     */
    @Override
    public void setCallback(SynchronizerCallback callback) {
        this.callback = callback;
    }

    /**
     * Gets the file extension.
     *
     * @return the file extension
     */
    @Override
    public String getFileExtension() {
        return FILE_EXTENSION_SECURITY_ACCESS;
    }

    /**
     * Gets the artefact type.
     *
     * @return the artefact type
     */
    @Override
    public String getArtefactType() {
        return XSAccess.ARTEFACT_TYPE;
    }

    /**
     * Removes the xsc file from path.
     *
     * @param path the path
     * @return the string
     */
    private String removeXscFileFromPath(String path) {
        return path.split("\\.")[0];
    }
}
