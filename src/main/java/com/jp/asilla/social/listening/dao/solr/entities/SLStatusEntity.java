package com.jp.asilla.social.listening.dao.solr.entities;

import org.apache.solr.client.solrj.beans.Field;

import com.jp.asilla.social.listening.dao.entities.SLStatus;
import com.jp.asilla.social.listening.dao.solr.SLSolrConstants;

public class SLStatusEntity extends SLStatus {
	
	@Field private String id;

	@Field private String itemType;

	public SLStatusEntity() {}
	
	public SLStatusEntity(SLStatus status) {
		super(status);
		this.setId(status.getNetwork() + "-" + status.getStatusId());
		this.setItemType(SLSolrConstants.SOLR_COLUMN_TYPE_STATUS);
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

}
