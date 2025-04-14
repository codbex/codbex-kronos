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
import * as httpUtils from "kronos/http/http";
import { MailClient as mail } from "sdk/mail";

export const http = httpUtils;

export function Mail(mailObject) {
  mailObject = mailObject || {};
  this.to = mailObject.to || [];
  this.cc = mailObject.cc || [];
  this.bcc = mailObject.bcc || [];
  this.parts = mailObject.parts || [];

  this.subject = mailObject.subject;
  this.sender = mailObject.sender || { name: "", address: "", nameEncoding: "" };
  this.subjectEncoding = mailObject.subjectEncoding;

  this.send = function (mailConfig) {
    let mailClient = mail.getClient(mailConfig);

    let recipients = {
      to: this.to.map(e => e.address),
      cc: this.cc.map(e => e.address),
      bcc: this.bcc.map(e => e.address)
    };

    let result = mailClient.sendMultipart(this.sender.address, recipients, this.subject, this.parts);
    return { "finalReply": result.get("finalReply"), "messageId": result.get("messageId") };
  }

  this.Part = function (partObject) {

    this.TYPE_TEXT = "text";
    this.TYPE_ATTACHMENT = "attachment";
    this.TYPE_INLINE = "inline";

    this.alternative = partObject && partObject.alternative;
    this.alternativeContentType = partObject && partObject.alternativeContentType;
    this.contentId = partObject && partObject.contentId;
    this.contentType = partObject && partObject.contentType;
    this.data = partObject && partObject.data;
    this.encoding = partObject && partObject.encoding;
    this.fileName = partObject && partObject.fileName;
    this.fileNameEncoding = partObject && partObject.fileNameEncoding;
    this.text = partObject && partObject.text;
    this.type = partObject && partObject.type;
  }
}

export function Destination(packageName, objectName) {
  let destination = httpUtils.readDestination(packageName, objectName);
  this.host = destination.host || "";
  this.port = destination.port || "";
}

export function SMTPConnection(mailConfig) {
  this.send = function (mailClass) {
    let mailClient = mail.getClient(mailConfig);

    let recipients = {
      to: mailClass.to.map(e => e.address),
      cc: mailClass.cc.map(e => e.address),
      bcc: mailClass.bcc.map(e => e.address)
    };

    let result = mailClient.sendMultipart(mailClass.sender.address, recipients, mailClass.subject, mailClass.parts);
    return { "finalReply": result.get("finalReply"), "messageId": result.get("messageId") };
  }

  this.close = function () {
    // Empty. Called automatically inside MailFacade
  }

  this.isClosed = function () {
    // The connection is being closed automatically
    return true;
  }
}
