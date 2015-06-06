package com.jp.asilla.social.listening.dao.entities;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

public class SLStatus {
	@Field private String network;
	@Field private long statusId;

	@Field private String text;
	@Field private String source;
	@Field private Boolean isTruncated;
	@Field private Long inReplyToStatusId;
	@Field private Long inReplyToUserId;
	@Field private Boolean isFavorited;
	@Field private int favoriteCount;

	@Field private Double longitude;
	@Field private Double latitude;

	@Field private Boolean isPossiblySensitive;
	@Field private String language;
	@Field private long userId;
	@Field private String placeId;

	@Field private Date createdDate;

	public SLStatus() {}
	
	public SLStatus(SLStatus status) {
		this.network = status.network;
		this.statusId = status.statusId;
		this.text = status.text;
		this.source = status.source;
		this.isTruncated = status.isTruncated;
		this.inReplyToStatusId = status.inReplyToStatusId;
		this.inReplyToUserId = status.inReplyToUserId;
		this.isFavorited = status.isFavorited;
		this.favoriteCount = status.favoriteCount;
		this.longitude = status.longitude;
		this.latitude = status.latitude;
		this.isPossiblySensitive = status.isPossiblySensitive;
		this.language = status.language;
		this.userId = status.userId;
		this.placeId = status.placeId;
		this.createdDate = status.createdDate;
	}
	
	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long id) {
		this.statusId = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Boolean getIsTruncated() {
		return isTruncated;
	}

	public void setIsTruncated(Boolean isTruncated) {
		this.isTruncated = isTruncated;
	}

	public Long getInReplyToStatusId() {
		return inReplyToStatusId;
	}

	public void setInReplyToStatusId(Long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}

	public Long getInReplyToUserId() {
		return inReplyToUserId;
	}

	public void setInReplyToUserId(Long inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}

	public Boolean getIsFavorited() {
		return isFavorited;
	}

	public void setIsFavorited(Boolean isFavorited) {
		this.isFavorited = isFavorited;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Boolean getIsPossiblySensitive() {
		return isPossiblySensitive;
	}

	public void setIsPossiblySensitive(Boolean isPossiblySensitive) {
		this.isPossiblySensitive = isPossiblySensitive;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}

