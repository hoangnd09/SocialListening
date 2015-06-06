package com.jp.asilla.social.listening.twitter;

import java.text.SimpleDateFormat;
import java.util.List;

import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.Query.Unit;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TwitterApplication {

	private static final double latitude = 21.024499893188;
	private static final double longitude = 105.84117126465;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	
	public static void main(String[] args) {
		TwitterApplication app = new TwitterApplication();
		
		try {
			// app.showTimeLine();
			app.showStatusByLocation(latitude, longitude, "");
			// app.showPlacesNearby(latitude, longitude);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
	
	public void showTimeLine() throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		List<Status> statuses = twitter.getHomeTimeline();
		System.out.println("Showing home timeline.");
		for (Status status : statuses) {
			System.out.println(status.getUser().getName() + ":" + status.getText());
		}
	}
	
	public void showPlacesNearby(double latitude, double longitude) throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		
		GeoQuery query = new GeoQuery(new GeoLocation(latitude, longitude));
		ResponseList<Place> places = twitter.searchPlaces(query);
		
		if (places.size() == 0) {
            System.out.println("No location associated with the specified IP address or lat/lang");
        } else {
            for (Place place : places) {
                System.out.println("id: " + place.getId() + " name: " + place.getFullName());
                for (GeoLocation[] locations : place.getBoundingBoxCoordinates()) {
                	StringBuffer sb = new StringBuffer();
                	for (GeoLocation location : locations) {
                		sb.append(",");
                		sb.append(location.toString());
                	}
                	System.out.println("\t" + sb.toString());
                }
                
                
                Place[] containedWithinArray = place.getContainedWithIn();
                if (containedWithinArray != null && containedWithinArray.length != 0) {
                    System.out.println("  contained within:");
                    for (Place containedWithinPlace : containedWithinArray) {
                        System.out.println(" \t id: " + containedWithinPlace.getId() + " name: " + containedWithinPlace.getFullName());
                        for (GeoLocation[] locations : containedWithinPlace.getBoundingBoxCoordinates()) {
                        	StringBuffer sb = new StringBuffer();
                        	for (GeoLocation location : locations) {
                        		sb.append(",");
                        		sb.append(location.toString());
                        	}
                        	System.out.println("\t\t" + sb.toString());
                        }
                    }
                }
            }
        }
	}

	public void showStatusByLocation(double latitude, double longitude, String keyword) throws TwitterException {
		Twitter twitter = TwitterFactory.getSingleton();
		
		QueryResult result = null;
		Query query = null;
		
		long count = 0;
		
		
		do {
			if (result == null) {
				query = new Query(keyword);
				//query.since("2014-11-19");
				//query.until("2014-12-01");
				// query.setMaxId(597069546197884928L);
				query.setGeoCode(new GeoLocation(latitude, longitude), 100, Unit.km);
				query.setCount(100);
			} else if (result.hasNext()) {
				query = result.nextQuery();
			} else {
				System.out.println("NEVER HAPPENDED");
				break;
			}
			result = twitter.search(query);
			count += result.getTweets().size();
			for (Status status : result.getTweets()) {
				System.out.println(String.format("[%s] [%s@%s] [Location=%s] [place=%s]", 
						sdf.format(status.getCreatedAt()), 
						status.getId(), 
						status.getUser().getScreenName(), 
						status.getGeoLocation() == null ? "null" : status.getGeoLocation().toString(), 
						status.getPlace() == null ? "null" : status.getPlace().getName()
				));
			}
			System.out.println("============================== " + count + " ==============================");
		} while (result.hasNext());
	}
}