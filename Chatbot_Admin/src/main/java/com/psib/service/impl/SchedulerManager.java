package com.psib.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.dao.ISchedulerDao;
import com.psib.model.Scheduler;
import com.psib.service.ISchedulerManager;

/**
 * @author DatHT Jul 22, 2016
 * @Email: datht0601@gmail.com
 */
@Service
public class SchedulerManager implements ISchedulerManager {

	private static final Logger LOG = Logger.getLogger(SchedulerManager.class);

	@Autowired
	private ISchedulerDao dao;

	@Override
	public Scheduler getSchedularByName(String name) {
		LOG.info("[getSchedularByName] Start name: " + name);
		LOG.info("[getSchedularByName] End");
		return dao.getByName(name);
	}

	@Override
	public void updateShedulerStatus(Scheduler scheduler) {
		LOG.info("[updateShedulerStatus] Start name: " + scheduler.getName());
		dao.updateShedulerStatus(scheduler);
		LOG.info("[updateShedulerStatus] End");
	}

	@Override
	public List<Scheduler> getAllSheduler() {
		LOG.info("[getAllSheduler] Start");
		LOG.info("[getAllSheduler] End");
		return dao.getSchedulers();
	}

}
