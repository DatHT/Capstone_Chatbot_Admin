package com.psib.timer;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;

public class TimerJob implements Trigger {
	private static final Logger LOG = Logger.getLogger(TimerJob.class);

	private TaskScheduler scheduler;
	private Runnable runnableTask;
	private ScheduledFuture<?> future;
	private String cron;

	public TimerJob(TaskScheduler scheduler, Runnable task, String cron) {
		this.scheduler = scheduler;
		this.runnableTask = task;
		reset(cron);
	}

	@Override
	public Date nextExecutionTime(TriggerContext arg0) {
		return null;
	}

	public void reset(String cron) {
		if (future != null) {
			System.out.println("Cancelling task...");
			future.cancel(true);
		}
		this.cron = cron;
		System.out.println("Starting task...");
		future = scheduler.schedule(runnableTask, new CronTrigger(cron));
	}

}
