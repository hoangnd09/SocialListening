package com.jp.asilla.social.listening.twitter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Status;

import com.jp.asilla.social.listening.dao.entities.SLPlace;
import com.jp.asilla.social.listening.dao.entities.SLSocialNetwork;
import com.jp.asilla.social.listening.dao.entities.SLStatus;

public class TwitterUtil {
	public static SLStatus convertStatus(Status status) {
		SLStatus clStatus = new SLStatus();
		clStatus.setNetwork(SLSocialNetwork.TWITTER.getValue());
		clStatus.setStatusId(status.getId());
		clStatus.setText(status.getText());
		clStatus.setSource(status.getSource());
		clStatus.setIsTruncated(status.isTruncated());
		clStatus.setInReplyToStatusId(status.getInReplyToStatusId());
		clStatus.setInReplyToUserId(status.getInReplyToUserId());
		clStatus.setIsFavorited(status.isFavorited());
		clStatus.setFavoriteCount(status.getFavoriteCount());
		if (status.getGeoLocation() != null){
			clStatus.setLatitude(status.getGeoLocation().getLatitude());
			clStatus.setLongitude(status.getGeoLocation().getLongitude());
		}
		clStatus.setIsPossiblySensitive(status.isPossiblySensitive());
		clStatus.setLanguage(status.getLang());
		if (status.getUser() != null) {
			clStatus.setUserId(status.getUser().getId());
		}
		if (status.getPlace() != null) {
			clStatus.setPlaceId(status.getPlace().getId());
		}
		clStatus.setCreatedDate(status.getCreatedAt());
		
		return clStatus;
	}

	public static SLPlace convertPlace(Place place) {
		SLPlace clPlace = new SLPlace();
		clPlace.setNetwork(SLSocialNetwork.TWITTER.getValue());
		clPlace.setPlaceId(place.getId());
		clPlace.setName(place.getName());
		clPlace.setFullName(place.getFullName());
		clPlace.setCountryCode(place.getCountryCode());
		clPlace.setCountry(place.getCountry());
		clPlace.setStreetAddress(place.getStreetAddress());
		clPlace.setPlaceType(place.getPlaceType());
		clPlace.setURL(place.getURL());
		
		GeoLocation[][] boundingBox = place.getBoundingBoxCoordinates();
		if (boundingBox != null && boundingBox.length > 0) {
			List<String> polygonArr = new ArrayList<String>();
			for (GeoLocation[] polygon : boundingBox) {
				List<String> pointArr = new ArrayList<String>();
				for (GeoLocation point : polygon) {
					pointArr.add(point.getLatitude() + " " + point.getLongitude());
				}
				pointArr.add(pointArr.get(0));
				polygonArr.add("POLYGON((" + StringUtils.join(pointArr, ",") + "))");
			}
			clPlace.setBoundaryText("GEOMETRYCOLLECTION(" + StringUtils.join(polygonArr, ",") + ")");
		}
		//clPlace.setBoundaryText("GeometryCollection(POLYGON((-74 -40,-74 -45,-84 -45,-84 -40,-74 -40)),POLYGON((74 40,74 45,84 45,84 40,74 40)))");
		return clPlace;
	}
}
