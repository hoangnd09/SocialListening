package com.jp.asilla.social.listening.twitter;

import com.jp.asilla.social.listening.SLStatusCollector;
import com.jp.asilla.social.listening.dao.SLDaoFactory;
import com.jp.asilla.social.listening.dao.SLJobDao;
import com.jp.asilla.social.listening.dao.entities.SLJob;
import com.jp.asilla.social.listening.dao.entities.SLSocialNetwork;
import com.jp.asilla.social.listening.exception.SLException;

public class SLStatusMain {
	
	private static final String TEST_JOB_ID = "0db3d264-e80e-44fc-97f1-b3cff62eab1c";
	
	private SLJobDao jobDao;
	
	public SLStatusMain() {
		jobDao = SLDaoFactory.getInstance().getJobDao();
	}
	
	public void collect() {
		
		try {
			SLJob job = jobDao.get(TEST_JOB_ID);
			
			if (SLSocialNetwork.TWITTER.equals(job.getNetwork())) {
				SLStatusCollector collector = new SLTwitterCollector();
				collector.collect(job, false);
			} else {
				System.err.println("unsupported social network");
			}
		} catch (SLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		SLStatusMain listener = new SLStatusMain();
		listener.collect();
	}
	
}
