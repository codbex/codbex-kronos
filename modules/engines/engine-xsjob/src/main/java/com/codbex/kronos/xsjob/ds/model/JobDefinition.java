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
package com.codbex.kronos.xsjob.ds.model;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class JobDefinition.
 */
@Table(name = "KRONOS_JOBS")
public class JobDefinition {

  /** The name. */
  @Id
  @Column(name = "JOB_NAME", columnDefinition = "VARCHAR", nullable = false, length = 255)
  private String name;

  /** The group. */
  @Column(name = "JOB_GROUP", columnDefinition = "VARCHAR", nullable = false, length = 255)
  private String group;

  /** The description. */
  @Column(name = "JOB_DESCRIPTION", columnDefinition = "VARCHAR", nullable = false, length = 1024)
  private String description;

  /** The module. */
  @Column(name = "MODULE", columnDefinition = "VARCHAR", nullable = false, length = 255)
  private String module;

  /** The function. */
  @Column(name = "JOB_FUNCTION", columnDefinition = "VARCHAR", nullable = false, length = 255)
  private String function;

  /** The cron expression. */
  @Column(name = "CRON_EXPRESSION", columnDefinition = "VARCHAR", nullable = false, length = 255)
  private String cronExpression;

  /** The created by. */
  @Column(name = "JOB_CREATED_BY", columnDefinition = "VARCHAR", nullable = false, length = 32)
  private String createdBy;

  /** The created at. */
  @Column(name = "JOB_CREATED_AT", columnDefinition = "TIMESTAMP", nullable = false)
  private Timestamp createdAt;

  /** The start at. */
  @Column(name = "JOB_START_AT", columnDefinition = "TIMESTAMP", nullable = true)
  private Timestamp startAt;

  /** The end at. */
  @Column(name = "JOB_END_AT", columnDefinition = "TIMESTAMP", nullable = true)
  private Timestamp endAt;

  /** The parameters. */
  @Column(name = "PARAMETERS", columnDefinition = "BLOB", nullable = false, length = 2000)
  private byte[] parameters;

  /** The parameters as map. */
  private Map<String, String> parametersAsMap;

  /**
   * Instantiates a new job definition.
   */
  public JobDefinition() {
  }

  /**
   * Gets the name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the group.
   *
   * @return the group
   */
  public String getGroup() {
    return group;
  }

  /**
   * Sets the group.
   *
   * @param group the new group
   */
  public void setGroup(String group) {
    this.group = group;
  }

  /**
   * Gets the description.
   *
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   *
   * @param description the new description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the module.
   *
   * @return the module
   */
  public String getModule() {
    return module;
  }

  /**
   * Sets the module.
   *
   * @param module the new module
   */
  public void setModule(String module) {
    this.module = module;
  }

  /**
   * Gets the function.
   *
   * @return the function
   */
  public String getFunction() {
    return function;
  }

  /**
   * Sets the function.
   *
   * @param function the new function
   */
  public void setFunction(String function) {
    this.function = function;
  }

  /**
   * Gets the created by.
   *
   * @return the created by
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * Sets the created by.
   *
   * @param createdBy the new created by
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * Gets the created at.
   *
   * @return the created at
   */
  public Timestamp getCreatedAt() {
    return createdAt;
  }

  /**
   * Sets the created at.
   *
   * @param createdAt the new created at
   */
  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * Gets the start at.
   *
   * @return the start at
   */
  public Timestamp getStartAt() {
    return startAt;
  }

  /**
   * Sets the start at.
   *
   * @param startAt the new start at
   */
  public void setStartAt(Timestamp startAt) {
    this.startAt = startAt;
  }

  /**
   * Gets the end at.
   *
   * @return the end at
   */
  public Timestamp getEndAt() {
    return endAt;
  }

  /**
   * Sets the end at.
   *
   * @param endAt the new end at
   */
  public void setEndAt(Timestamp endAt) {
    this.endAt = endAt;
  }

  /**
   * Gets the parameters.
   *
   * @return the parameters
   */
  public byte[] getParameters() {
    return parameters;
  }

  /**
   * Sets the parameters.
   *
   * @param parameters the new parameters
   */
  public void setParameters(byte[] parameters) {
    this.parameters = parameters;
  }

  /**
   * Gets the parameters as map.
   *
   * @return the parameters as map
   */
  public Map<String, String> getParametersAsMap() {
    return parametersAsMap;
  }

  /**
   * Sets the parameters as map.
   *
   * @param parametersAsMap the parameters as map
   */
  public void setParametersAsMap(Map<String, String> parametersAsMap) {
    this.parametersAsMap = parametersAsMap;
  }

  /**
   * Gets the cron expression.
   *
   * @return the cron expression
   */
  public String getCronExpression() {
    return cronExpression;
  }

  /**
   * Sets the cron expression.
   *
   * @param cronExpression the new cron expression
   */
  public void setCronExpression(String cronExpression) {
    this.cronExpression = cronExpression;
  }

  /**
   * Hash code.
   *
   * @return the int
   */
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((cronExpression == null) ? 0 : cronExpression.hashCode());
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + ((function == null) ? 0 : function.hashCode());
    result = prime * result + ((group == null) ? 0 : group.hashCode());
    result = prime * result + ((module == null) ? 0 : module.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + Arrays.hashCode(parameters);
    return result;
  }

  /**
   * Equals.
   *
   * @param obj the obj
   * @return true, if successful
   */
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    JobDefinition other = (JobDefinition) obj;
    if (cronExpression == null) {
      if (other.cronExpression != null) {
        return false;
      }
    } else if (!cronExpression.equals(other.cronExpression)) {
      return false;
    }
    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
      return false;
    }
    if (function == null) {
      if (other.function != null) {
        return false;
      }
    } else if (!function.equals(other.function)) {
      return false;
    }
    if (group == null) {
      if (other.group != null) {
        return false;
      }
    } else if (!group.equals(other.group)) {
      return false;
    }
    if (module == null) {
      if (other.module != null) {
        return false;
      }
    } else if (!module.equals(other.module)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    if (!Arrays.equals(parameters, other.parameters)) {
      return false;
    }
    return true;
  }

}
