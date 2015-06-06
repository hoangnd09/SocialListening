package com.jp.asilla.social.listening.dao.entities;

import java.util.UUID;

import org.apache.solr.client.solrj.beans.Field;

public class SLJob {
	
	@Field private String jobId;
	@Field private String network;

	@Field private String name;
	@Field private String expression;
	@Field private String timezone;

	@Field private double latitude;
	@Field private double longitude;
	@Field private double radius;
	
	@Field private String state;

	public SLJob() {
		generateID();
	}
	
	public SLJob(SLJob job) {
		this.jobId = job.jobId;
		this.network = job.network;
		this.name = job.name;
		this.expression = job.expression;
		this.timezone = job.timezone;
		this.latitude = job.latitude;
		this.longitude = job.longitude;
		this.radius = job.radius;
		this.state = job.state;
	}
	
	public void generateID() {
		jobId = UUID.randomUUID().toString();
	}
	

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String id) {
		this.jobId = id;
	}

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
