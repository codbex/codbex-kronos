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
let xsDefaultTemplate = {
     "exposed": false,
     "authentication": {
          "method": "Form"
     },
     "authorization": [
          "sap.xse.test::Execute",
          "sap.xse.test::Admin"
     ],
     "anonymous_connection": null,
     "default_connection": "",
     "cache_control": "no-cache, no-store",
     "cors": {
          "enabled": false
     },
     "default_file": "index.html",
     "enable_etags": false,
     "force_ssl": true,
     "mime_mapping": [
          {
               "extension": "jpg",
               "mimetype": "image/jpeg"
          }
     ],
     "prevent_xsrf": true,
     "rewrite_rules": [],
     "headers": {
          "enabled": false
     }
};

export function getTemplate() {
     return {
          "name": "xsaccess",
          "label": "Application-access file (.xsaccess)",
          "extension": "xsaccess",
          "oncePerFolder": true,
          "staticName": true,
          "nameless": true,
          "isModel": true,
          "data": JSON.stringify(xsDefaultTemplate)
     };
};
