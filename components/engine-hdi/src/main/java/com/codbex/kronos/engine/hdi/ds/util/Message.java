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
package com.codbex.kronos.engine.hdi.ds.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * The Class Message.
 */
public class Message {

    /** The request id. */
    public final long requestId;
    
    /** The row id. */
    public final long rowId;
    
    /** The level. */
    public final int level;
    
    /** The type. */
    public final String type;
    
    /** The library. */
    public final String library;
    
    /** The plugin id. */
    public final String pluginId;
    
    /** The path. */
    public final String path;
    
    /** The severity. */
    public final String severity;
    
    /** The message code. */
    public final long messageCode;
    
    /** The message. */
    public final String message;
    
    /** The location. */
    public final String location;
    
    /** The location path. */
    public final String locationPath;
    
    /** The timestamp UTC. */
    public final Timestamp timestampUTC;


    /**
     * Instantiates a new message.
     *
     * @param rs the rs
     * @throws SQLException the SQL exception
     */
    public Message(ResultSet rs) throws SQLException
    {
      this.requestId = rs.getLong(1);
      this.rowId = rs.getLong(2);
      this.level = rs.getInt(3);
      this.type = rs.getString(4);
      this.library = rs.getString(5);
      this.pluginId = rs.getString(6);
      this.path = rs.getString(7);
      this.severity = rs.getString(8);
      this.messageCode = rs.getLong(9);
      this.message = rs.getString(10);
      this.location = rs.getString(11);
      this.locationPath = rs.getString(12);
      this.timestampUTC = rs.getTimestamp(13);
    }

    /**
     * Instantiates a new message.
     *
     * @param requestId the request id
     * @param rowId the row id
     * @param level the level
     * @param type the type
     * @param library the library
     * @param pluginId the plugin id
     * @param path the path
     * @param severity the severity
     * @param messageCode the message code
     * @param message the message
     * @param location the location
     * @param locationPath the location path
     * @param timestampUTC the timestamp UTC
     */
    public Message(long requestId,
    long rowId,
    int level,
    String type,
    String library,
    String pluginId,
    String path,
    String severity,
    long messageCode,
    String message,
    String location,
    String locationPath,
    Timestamp timestampUTC) {
    super();
    this.requestId = requestId;
    this.rowId = rowId;
    this.level = level;
    this.type = type;
    this.library = library;
    this.pluginId = pluginId;
    this.path = path;
    this.severity = severity;
    this.messageCode = messageCode;
    this.message = message;
    this.location = location;
    this.locationPath = locationPath;
    this.timestampUTC = timestampUTC;
  }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString()
    {
      StringBuilder builder = new StringBuilder();
      for (int i = 0; i < level; ++i)
      {
        builder.append("  ");
      }
      String indent = builder.toString();
      builder.setLength(0);
      builder.append('\n');

      if (!library.isEmpty())
      {
        builder.append(indent);
        builder.append("Library:   ");
        builder.append(library);
        builder.append('\n');
      }
      if (!pluginId.isEmpty())
      {
        builder.append(indent);
        builder.append("Plugin:    ");
        builder.append(pluginId);
        builder.append('\n');
      }
      if (!path.isEmpty())
      {
        builder.append(indent);
        builder.append("Path:      ");
        builder.append(path);
        builder.append('\n');
      }

      builder.append(indent);
      builder.append("Severity:  ");
      builder.append(severity);
      builder.append('\n');
      builder.append(indent);
      builder.append("Message:   ");
      builder.append(message);
      builder.append(" (");
      builder.append(messageCode);
      builder.append(")\n");

      if (!location.equals("0:0") || !locationPath.isEmpty())
      {
        builder.append(indent);
        builder.append("Location: ");
        builder.append(location);
        builder.append(" ");
        builder.append(locationPath);
        builder.append('\n');
      }

      builder.append(indent);
      builder.append("Timestamp: ");
      builder.append(timestampUTC);
      builder.append('\n');

      return builder.toString();
    }
}
