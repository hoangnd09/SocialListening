package com.jp.asilla.social.listening.dao.entities;

public enum SLSocialNetwork {
	
	FACEBOOK("facebook"), TWITTER("twitter");
	
	private String value;
	
	SLSocialNetwork(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public boolean equals(String value) {
		return this.value.equals(value);
	}
}
