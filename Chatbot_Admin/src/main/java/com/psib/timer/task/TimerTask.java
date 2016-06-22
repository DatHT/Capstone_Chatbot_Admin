package com.psib.timer.task;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TimerTask {

	public void doTimer() {
		System.out.println("[doTimer] Start");
		System.out.println("[doTimer] Do Something here");
		System.out.println("[doTimer] End");
	}
}
