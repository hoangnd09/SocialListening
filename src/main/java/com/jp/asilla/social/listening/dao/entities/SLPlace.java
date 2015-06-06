package com.jp.asilla.social.listening.dao.entities;

import org.apache.solr.client.solrj.beans.Field;

public class SLPlace {
	@Field private String network;
	@Field private String placeId;
	@Field private String name;
	@Field private String fullName;
	@Field private String countryCode;
	@Field private String country;
	@Field private String streetAddress;
	@Field private String placeType;
	
	@Field private String boundaryText;
	@Field private String URL;
	
	public SLPlace() {
		
	}
	
	public SLPlace(SLPlace place) {
		this.network = place.network;
		this.placeId = place.placeId;
		this.name = place.name;
		this.fullName = place.fullName;
		this.countryCode = place.countryCode;
		this.country = place.country;
		this.streetAddress = place.streetAddress;
		this.placeType = place.placeType;
		this.boundaryText = place.boundaryText;
		this.URL = place.URL;
	}
	
	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String id) {
		this.placeId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public String getBoundaryText() {
		return boundaryText;
	}

	public void setBoundaryText(String boundaryText) {
		this.boundaryText = boundaryText;
	}

	public String getURL() {
		return this.URL;
	}
	
	public void setURL(String url) {
		this.URL = url;
	}

}
