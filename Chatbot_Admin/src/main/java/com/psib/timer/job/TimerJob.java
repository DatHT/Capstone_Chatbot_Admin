package com.psib.timer.job;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.psib.timer.task.TimerTask;

public class TimerJob extends QuartzJobBean {

	// @Autowired
	// private TimerTask timerTask;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("[executeInternal] Start");

		if (System.getProperty("timerActive") != null) {
			boolean isTimerActive = Boolean.parseBoolean(System.getProperty("timerActive"));
			if (isTimerActive) {
				TimerTask timerTask = new TimerTask();
				timerTask.doTimer();
			}
		}
		System.out.println("[executeInternal] End");
	}

}
