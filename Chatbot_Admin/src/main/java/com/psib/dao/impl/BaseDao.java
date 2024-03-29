package com.psib.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.psib.dao.IBaseDao;

/**
 * @author DatHT Jun 4, 2016
 */
@Repository
public class BaseDao<Model, Id> implements IBaseDao<Model, Id> {

    private static final Logger log = Logger.getLogger(BaseDao.class.getName());
    private Class<Model> clazz;

    @Autowired
    private SessionFactory factory;

    public BaseDao(Class<Model> clazz) {
        this.clazz = clazz;
    }

    public void setClazz(Class<Model> clazz) {
        this.clazz = clazz;
    }

    public BaseDao() {
        // TODO Auto-generated constructor stub
    }

    protected Session getSession() {
		if (factory == null) {
			factory = (SessionFactory) new LocalSessionFactoryBean();
		}
		return factory.getCurrentSession();
	}

    @Override
    public Model getById(Id id) {
        // TODO Auto-generated method stub
        return (Model) getSession().get(clazz, (Serializable) id);
    }

    @Override
    public void insert(Model model) {
        // TODO Auto-generated method stub
        getSession().save(model);
    }

    @Override
    public void update(Model model) {
        getSession().update(model);
    }

    @Override
    public void delete(Model model) {
        getSession().delete(model);
    }

    @Override

    public List<Model> getAll() {
        // TODO Auto-generated method stub
        String sql = "select e from " + clazz.getSimpleName() + " e";
        Query query = getSession().createQuery(sql);
        List<Model> list = query.list();
        return list;
    }

	private static SessionFactory buildSessionFactory() {
        // Create Configuration
        Configuration configuration = new Configuration();
 
        // By default, Hibernate will read hibernate.cfg.xml configuration file
        // You can specify the file if you want:
        // configuration.configure("myhiberante.cfg.xml");
        configuration.configure();
 
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
 
        // Create SessionFactory
        SessionFactory sessionFactory = configuration
                .buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }

}
