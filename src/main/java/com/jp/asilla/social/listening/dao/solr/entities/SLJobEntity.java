package com.jp.asilla.social.listening.dao.solr.entities;

import java.util.List;

import org.apache.solr.client.solrj.beans.Field;

import com.jp.asilla.social.listening.dao.entities.SLJob;
import com.jp.asilla.social.listening.dao.solr.SLSolrConstants;

public class SLJobEntity extends SLJob {
	@Field(value="id")
	private String id;

	@Field(value="itemType")
	private String itemType;
	
	public SLJobEntity() {
	}

	public SLJobEntity(SLJob model) {
		super(model);
		if (this.id == null) {
			this.setId(this.getJobId());
		}
		this.setItemType(SLSolrConstants.SOLR_COLUMN_TYPE_JOB);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setItemType(List<?> itemType) {
		this.itemType = itemType.toString();
	}
	
}
