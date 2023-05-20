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
package com.codbex.kronos.hdbti.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class HDBTIUtils.
 */
public class HDBTIUtils {

  /**
   * Instantiates a new HDBTI utils.
   */
  private HDBTIUtils() {
  }

  /**
   * Convert fileName in format "workspace.folder:fileName.csv" to a format "workspace.folder:fileName.csv"
   * For example:
   * convert from "sap.ti2.demo:myData.csv" to "/sap/ti2/demo/myData.csv"
   *
   * @param fileNamePath the file name path
   * @return the string
   */
  public static String convertHDBTIFilePropertyToPath(String fileNamePath) {
    String fileName = fileNamePath.substring(fileNamePath.lastIndexOf(':') + 1);
    return "/" + fileNamePath.substring(0, fileNamePath.indexOf(':')).replaceAll("\\.", "/") + "/" + fileName;
  }

  /**
   * Convert fileName in format "workspace/folder/fileName.csv" to a format "workspace.folder:fileName.csv"
   * For example:
   * convert from "/sap/ti2/demo/myData.csv" to "sap.ti2.demo:myData.csv"
   *
   * @param fileNamePath the file name path
   * @return converted string
   * @throws IllegalArgumentException the illegal argument exception
   */
  public static String convertPathToHDBTIFileProperty(String fileNamePath) throws IllegalArgumentException {
    Pattern pattern = Pattern.compile("((?:[^\\/]*\\/)*)(.*)", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(fileNamePath.trim());
    boolean matchFound = matcher.find();
    if (matchFound) {
      String fileName = matcher.group(2);
      if (!isCorrectPropertySyntax(fileName)) {
        throw new IllegalArgumentException("Incorrect format of filePath: " + fileNamePath);
      }
      String filePath = matcher.group(1);
      if (!filePath.equals("")) {
        if (filePath.startsWith("/")) {
          filePath = filePath.substring(1);
        }
        if (filePath.endsWith("/")) {
          filePath = filePath.substring(0, filePath.length() - 1);
        }
        return filePath.replaceAll("\\/", ".") + ":" + fileName;
      }
      return fileName;
    } else {
      throw new IllegalArgumentException("Incorrect format of filePath: " + fileNamePath);
    }
  }

  /**
   * Check if the property support only symbols A-Za-z0-9_-$.
   *
   * @param property the property
   * @return true, if is correct property syntax
   * @throws IllegalArgumentException the illegal argument exception
   */
  public static boolean isCorrectPropertySyntax(String property) throws IllegalArgumentException {
    String regex = "[\\w\\-. $]+$";

    return validateProperty(property, regex);
  }

  /**
   * Check if the table property has proper syntax.
   *
   * @param tableProperty the table property
   * @return true, if is correct table property syntax
   * @throws IllegalArgumentException the illegal argument exception
   */
  public static boolean isCorrectTablePropertySyntax(String tableProperty) throws IllegalArgumentException {
    String regex;

    if (tableProperty.contains("::")) {
      regex = "^[A-Za-z0-9_\\-$.]+::[A-Za-z0-9_\\-$.]+$";
    } else {
      regex = "^[A-Za-z0-9_\\-$.]+$";
    }

    return validateProperty(tableProperty, regex);
  }

  /**
   * Check if property syntax matches regex.
   *
   * @param property the property
   * @param regex the regex
   * @return true, if successful
   * @throws IllegalArgumentException the illegal argument exception
   */
  public static boolean validateProperty(String property, String regex) throws IllegalArgumentException {
    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(property.trim());

    return matcher.find();
  }
}
