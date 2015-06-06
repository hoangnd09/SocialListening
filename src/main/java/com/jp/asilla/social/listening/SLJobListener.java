package com.jp.asilla.social.listening;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import com.jp.asilla.social.listening.dao.SLDaoFactory;
import com.jp.asilla.social.listening.dao.entities.SLJob;
import com.jp.asilla.social.listening.exception.SLConfigurationException;
import com.jp.asilla.social.listening.exception.SLDaoException;

public class SLJobListener extends QuartzInitializerListener {
	private Logger logger = Logger.getLogger(this.getClass());
	
	private List<Scheduler> schedulers;
	
	public static final String PARAM_JOB_ID = "id";
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		super.contextInitialized(sce);
		if (schedulers == null) {
			schedulers = new ArrayList<Scheduler>();
		}
		try {
			List<SLJob> jobs = SLDaoFactory.getInstance().getJobDao().select();
			
			for (SLJob job : jobs) {
				try {
					Class<?> clazz = getCollectorClass(job.getNetwork());
					
					@SuppressWarnings("unchecked")
					JobDetail jobDetail = JobBuilder.newJob((Class<Job>) clazz)
							.withIdentity(job.getName(), "Social Listening").build();
					
					jobDetail.getJobDataMap().put(PARAM_JOB_ID, job.getJobId());
					
					CronScheduleBuilder schedule = CronScheduleBuilder.cronSchedule(job.getExpression());
					schedule.inTimeZone(TimeZone.getTimeZone(job.getTimezone()));
					CronTrigger cronTrigger = TriggerBuilder.newTrigger().withSchedule(schedule).build();
					Scheduler scheduler = new StdSchedulerFactory().getScheduler();
					scheduler.start();
					scheduler.scheduleJob(jobDetail, cronTrigger);
					schedulers.add(scheduler);
					
				} catch (SchedulerException e) {
					logger.error(e);
				} catch (SLConfigurationException e) {
					logger.error(e);
				}
			}
			// logger.info(String.format("%d scheduler installed. %s", this.schedulers.size(), new Gson().toJson(this.schedulers)));
			
		} catch (SLDaoException e) {
			logger.error(e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		super.contextDestroyed(sce);
		if (this.schedulers == null || this.schedulers.size() == 0) {
			return;
		}
		for (Scheduler scheduler : this.schedulers) {
			try {
				if (scheduler != null) {
					scheduler.shutdown(true);
				}
			} catch (SchedulerException e) {
				logger.error(e);
			}
		}
	}
	
	private static Class<?> getCollectorClass(String network) throws SLConfigurationException {
		String propertyKey = SLSetting.getCollectorKey(network);
		try {
			String className = SLSetting.getSetting(propertyKey);
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new SLConfigurationException(e);
		}

	}

}
