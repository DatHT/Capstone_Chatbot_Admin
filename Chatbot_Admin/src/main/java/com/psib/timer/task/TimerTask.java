package com.psib.timer.task;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TimerTask {

    private static final Logger LOG = Logger.getLogger(TimerTask.class);

    public void doTimer() {
        LOG.info("[doTimer] Start");

        LOG.info("[doTimer] End");
    }
}
