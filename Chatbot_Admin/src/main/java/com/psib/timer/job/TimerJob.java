package com.psib.timer.job;

import com.psib.timer.task.TimerTask;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TimerJob extends QuartzJobBean {

    private static final Logger LOG = Logger.getLogger(TimerJob.class);

    // @Autowired
    // private TimerTask timerTask;

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        LOG.info("[executeInternal] Start");

        if (System.getProperty("timerActive") != null) {
            boolean isTimerActive = Boolean.parseBoolean(System.getProperty("timerActive"));
            if (isTimerActive) {
                TimerTask timerTask = new TimerTask();
                timerTask.doTimer();
            }
        }
        LOG.info("[executeInternal] End");
    }

}
