package com.codbex.kronos.engine.hdb.domain;

import javax.persistence.Column;
import javax.persistence.Transient;

import org.eclipse.dirigible.components.base.artefact.Artefact;

import com.codbex.kronos.hdb.ds.model.DBContentType;
import com.codbex.kronos.hdb.ds.model.DataStructureModelBuilder;

public class HDBDataStructure extends Artefact {
	
	/** The schema. */
	@Column(name = "HDB_SCHEMA", columnDefinition = "VARCHAR", nullable = false, length = 255)
	  private String schema;

	  /** The raw content. */
	  @Transient
	  private transient String rawContent;

	  /** The db content type. */
	  @Transient
	  private transient DBContentType dbContentType;

	  /**
	   * Instantiates a new data structure model.
	   */
	  public HDBDataStructure() {
		super();
	  }

	  /**
	   * Instantiates a new data structure model.
	   *
	   * @param builder the builder
	   */
	  protected HDBDataStructure(DataStructureModelBuilder builder) {
		super(builder.getLocation(), builder.getName(), builder.getType(), null, null);
	    schema = builder.getSchema();
	    rawContent = builder.getRawContent();
	    dbContentType = builder.getDbContentType();
	  }

}
