package com.psib.timer.trigger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.psib.timer.TimerJob;

@Component
public class ScheduleChanger {

	@Autowired
	private TimerJob timerJob;
	
	   public void change(String cron) {
	      timerJob.reset(cron);
	   }
}
