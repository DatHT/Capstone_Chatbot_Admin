package com.psib.timer;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.psib.common.restclient.RestfulException;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.model.Scheduler;
import com.psib.service.ILexicalCategoryManager;
import com.psib.service.ISchedulerManager;
import com.psib.timer.task.TimerTask;
import com.psib.util.SpringPropertiesUtil;

public class TimerJob {
	private static final Logger LOG = Logger.getLogger(TimerJob.class);
	
	@Autowired
	TimerTask task;
	
	@Autowired
	ISchedulerManager manager;

	public TimerJob() {
	}

	public void synchronizeJob() {
		Scheduler apiScheduler = manager.getSchedularByName("api");
		if (apiScheduler.isStatus()) {
			boolean isTimerActive = Boolean.parseBoolean(System.getProperty("timerActive"));
			if (isTimerActive) {
				task.synchronizePhraseFromAPItoDB();
				task.synchronizeIntentToBD();
			}
		}
	}

}
