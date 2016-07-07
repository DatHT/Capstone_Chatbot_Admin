package com.psib.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.psib.dao.ISchedulerDao;
import com.psib.model.Scheduler;

/**
 * @author DatHT Jul 7, 2016
 * @Email: datht0601@gmail.com
 */
@Repository
public class SchedulerDao extends BaseDao<Scheduler, Integer> implements ISchedulerDao {

	private static final Logger LOG = Logger.getLogger(SchedulerDao.class);

	public SchedulerDao(Class<Scheduler> clazz) {
		super(clazz);
	}

	public SchedulerDao() {
		setClazz(Scheduler.class);
	}

	@Override
	@Transactional
	public Scheduler getByName(String name) {
		LOG.info("[getByName] Start: name = " + name);
		String sql = "from " + Scheduler.class.getSimpleName() + " where name = :name";
		Query query = getSession().createQuery(sql);
		query.setParameter("name", name);
		Scheduler scheduler = (Scheduler) query.uniqueResult();
		if (scheduler != null) {
			LOG.info("[getByName] End");
			return scheduler;
		}
		LOG.info("[getByName] End");
		return null;
	}

	@Override
	@Transactional
	public void updateShedulerStatus(Scheduler scheduler) {
		LOG.info("[updateShedulerStatus] Start: name = " + scheduler.getName());
		update(scheduler);
		LOG.info("[updateShedulerStatus] End");
	}

	@Override
	@Transactional
	public List<Scheduler> getSchedulers() {
		LOG.info("[getSchedulers] Start");
		LOG.info("[getSchedulers] End");
		return getAll();
	}

}
