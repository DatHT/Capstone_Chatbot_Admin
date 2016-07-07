package com.psib.service;

import java.util.List;

import com.psib.model.Scheduler;

public interface ISchedulerManager {

	public Scheduler getSchedularByName(String name);

	public void updateShedulerStatus(Scheduler scheduler);

	public List<Scheduler> getAllSheduler();
}
