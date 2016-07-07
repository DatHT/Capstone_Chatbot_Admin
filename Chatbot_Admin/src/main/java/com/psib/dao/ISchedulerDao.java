package com.psib.dao;

import java.util.List;

import com.psib.model.Scheduler;

public interface ISchedulerDao {

	public Scheduler getByName(String name);

	public void updateShedulerStatus(Scheduler scheduler);

	public List<Scheduler> getSchedulers();
}
