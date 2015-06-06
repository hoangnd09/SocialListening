package com.jp.asilla.social.listening;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;

import com.google.gson.Gson;
import com.jp.asilla.social.listening.dao.SLDaoFactory;
import com.jp.asilla.social.listening.dao.SLTransaction;
import com.jp.asilla.social.listening.dao.entities.SLJob;
import com.jp.asilla.social.listening.dao.entities.SLPlace;
import com.jp.asilla.social.listening.dao.entities.SLStatus;
import com.jp.asilla.social.listening.exception.SLDaoException;
import com.jp.asilla.social.listening.exception.SLException;

@DisallowConcurrentExecution
public abstract class SLStatusCollector implements InterruptableJob {
	
	private Logger logger = Logger.getLogger(this.getClass());
	private boolean isInterrupted = false;
	
	public abstract void collect(SLJob job, boolean isInterrupted) throws SLException;
	
	@Override
	public void interrupt() throws UnableToInterruptJobException {
		logger.debug("start Interrupt current job");
		isInterrupted  = true;
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// logger.debug(String.format("CRON JOB started. Job Detail: %s", new Gson().toJson(context.getJobDetail())));
		
		logger.debug(String.format("CRON JOB started"));
		
		String jobId = context.getJobDetail().getJobDataMap().getString(SLJobListener.PARAM_JOB_ID);
		
		try {
			SLJob job = SLDaoFactory.getInstance().getJobDao().get(jobId);
			logger.debug(String.format("Job Entity: %s", new Gson().toJson(job)));
			collect(job, isInterrupted);
		} catch (SLException e) {
			logger.error(e);
		} finally {
			logger.debug(String.format("CRON JOB terminated"));
			// logger.debug(String.format("CRON JOB terminated. Job Detail: %s", new Gson().toJson(context.getJobDetail())));
		}
		
	}
	
	protected void storeStatus(List<SLStatus> statuses, SLTransaction transaction) throws SLDaoException {
		if (statuses != null && statuses.size() > 0) {
			SLDaoFactory.getInstance().getStatusDao().insert(statuses, transaction);
		}
	}
	
	protected void storePlaces(List<SLPlace> places, SLTransaction transaction) throws SLDaoException {
		if (places != null && places.size() > 0) {
			SLDaoFactory.getInstance().getPlaceDao().insert(places, transaction);
		}
	}
	
	protected void updateJobState(String jobID, String state, SLTransaction transaction) throws SLDaoException {
		SLDaoFactory.getInstance().getJobDao().updateState(jobID, state, transaction);
	}
	
}
