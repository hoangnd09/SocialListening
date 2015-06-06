package com.jp.asilla.social.listening.twitter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.Query.Unit;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.jp.asilla.social.listening.SLStatusCollector;
import com.jp.asilla.social.listening.dao.SLDaoFactory;
import com.jp.asilla.social.listening.dao.SLTransaction;
import com.jp.asilla.social.listening.dao.entities.SLJob;
import com.jp.asilla.social.listening.dao.entities.SLPlace;
import com.jp.asilla.social.listening.dao.entities.SLStatus;
import com.jp.asilla.social.listening.exception.SLDaoException;
import com.jp.asilla.social.listening.exception.SLException;
import com.jp.asilla.social.listening.exception.SLNetworkException;

@DisallowConcurrentExecution
public class SLTwitterCollector extends SLStatusCollector {

	private static final Logger logger = Logger.getLogger(SLTwitterCollector.class);
	
	public void collect(SLJob job, boolean isInterrupted) throws SLException {
		
		TwitterCollectorState collectorState = new TwitterCollectorState(job.getState());
		
		Twitter twitter = TwitterFactory.getSingleton();
		
		QueryResult result = null;
		SLTransaction transaction = SLDaoFactory.getInstance().openTransaction();
		try {
			do {
				if (isInterrupted) {
					logger.debug("Current job is interupted: job.id = " + job.getJobId());
					return;
				}
				// search for tweets
				Query query = buildQuery(job, result, collectorState);
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				
				// calculate current collector's state
				calculateCollectorState(result, collectorState);
				
				// save into database
				storeTweets(tweets, transaction);
				updateJobState(job.getJobId(), collectorState.getState(), transaction);
				
				transaction.commit();
			}
			while (result != null && result.hasNext());
		} catch (TwitterException e) {
			logger.error(e);
			transaction.rollback();
			throw new SLNetworkException("Twitter Search API failure", e);
		} catch (SLDaoException e) {
			logger.error(e);
			transaction.rollback();
			throw e;
		} finally {
			transaction.close();
		}
	}
	
	private Query buildQuery(SLJob job, QueryResult result, TwitterCollectorState collectorState) {
		if (result == null) {
			// first querying, initialize query condition
			Query query = new Query();
			query.setGeoCode(new GeoLocation(job.getLatitude(), job.getLongitude()), job.getRadius(), Unit.km);
			query.setCount(100);
			
			if (collectorState.getUntilDate() != null) {
				query.until(collectorState.getUntilDateString());
				logger.debug("until: " + query.getUntil());
			}
			if (collectorState.getSinceDate() != null) {
				query.since(collectorState.getSinceDateString());
				logger.debug("since: " + query.getSince());
			}
			return query;
		} else {
			return result.nextQuery();
		}
	}
	
	private void calculateCollectorState(QueryResult result, TwitterCollectorState collectorState) {
		List<Status> tweets = result.getTweets();
		
		if (tweets.size() > 0 && !result.hasNext() && collectorState.getTmpUntilDate() == null) {
			// result have only one page.
			Date previousSinceDate = collectorState.getSinceDate();
			Date currentSinceDate = tweets.get(0).getCreatedAt();
			
			if (currentSinceDate != null && currentSinceDate.after(previousSinceDate)) {
				collectorState.setSinceDate(currentSinceDate);
			}
		} else if (tweets.size() > 0 && result.hasNext()) {
			// processing non-last page. => store current paging information, for the next query.
			collectorState.setUntilDate(tweets.get(tweets.size() - 1).getCreatedAt());
			if (collectorState.getTmpUntilDate() == null) {
				collectorState.setTmpUntilDate(tweets.get(0).getCreatedAt());
			}
		} else {
			// processing the last page. => clear paging information.
			// clear UntilDate and store SinceDate
			collectorState.setSinceDate(collectorState.getTmpUntilDate());
			collectorState.setTmpUntilDate(null);
			collectorState.setUntilDate(null);
		}
		
	}
	
	/**
	 * <p>store twitter status</p>
	 * <ul>
	 * <li>if status already existed in system storage, it will be replaced</li>
	 * <li>otherwise, new record will be inserted</li>
	 * </ul>
	 * 
	 * <p>each status include a Place information. we need to store Place also</p>
	 * <ul>
	 * <li>if Place already existed in system storage, it will be replaced</li>
	 * <li>otherwise, new record will be inserted</li>
	 * </ul>
	 * @param tweets list of tweets, which received from twitter network
	 * @throws SLDaoException failure to store status
	 */
	public void storeTweets(List<Status> tweets, SLTransaction transaction) throws SLDaoException {
		List<SLStatus> statuses = new ArrayList<SLStatus>();
		List<SLPlace> places = new ArrayList<SLPlace>();
		
		for (Status status : tweets) {
			if (status.getPlace() != null) {
				Place place = status.getPlace();
				SLPlace slPlace = TwitterUtil.convertPlace(place);
				places.add(slPlace);
			}
			SLStatus clStatus = TwitterUtil.convertStatus(status);
			statuses.add(clStatus);
		}
		storePlaces(places, transaction);
		storeStatus(statuses, transaction);
	}

}
