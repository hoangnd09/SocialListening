package com.jp.asilla.social.listening.dao.solr.entities;

import org.apache.solr.client.solrj.beans.Field;

import com.jp.asilla.social.listening.dao.entities.SLPlace;
import com.jp.asilla.social.listening.dao.solr.SLSolrConstants;

public class SLPlaceEntity extends SLPlace {

	@Field
	private String id;

	@Field
	private String itemType;

	public SLPlaceEntity() {
	}

	public SLPlaceEntity(SLPlace place) {
		super(place);
		this.setId(place.getNetwork() + "-" + place.getPlaceId());
		this.setItemType(SLSolrConstants.SOLR_COLUMN_TYPE_PLACE);
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
