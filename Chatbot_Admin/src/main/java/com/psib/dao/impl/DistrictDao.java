package com.psib.dao.impl;

import com.psib.dao.IDistrictDao;
import com.psib.model.District;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
public class DistrictDao extends BaseDao<District, Long> implements IDistrictDao {

    private static final Logger LOG = Logger.getLogger(DistrictDao.class);

    public DistrictDao(Class<District> clazz) {
        super(clazz);
    }

    public DistrictDao() {
        setClazz(District.class);
    }

    @Override
    @Transactional
    public List<District> getAllDistrict() {
        LOG.info("[getAllDistrict] Start");
        LOG.info("[getAllDistrict] End");
        return getAll();
    }
    
    @Override
    @Transactional
    public long inserDistrict(District district) {
        LOG.info("[inserAddress] Start: name = " + district.getName());
        insert(district);
        LOG.info("[inserAddress] End");
        return district.getId();
    }

    @Override
    @Transactional
    public District getDistrictById(long id) {
        LOG.info("[getDistrictById] Start: id = " + id);
        LOG.info("[getDistrictById] End");
        return getById(id);
    }

    @Override
    @Transactional
    public District getDistrictByName(String name) {
        String sql = "from " + District.class.getSimpleName() + " where name LIKE :name";
        Query query = getSession().createQuery(sql);
        query.setParameter("name", "%" + name + "%");
        query.setMaxResults(1);
        District district = (District) query.uniqueResult();
        if (district != null) {
            return district;
        }
        return null;
    }
    @Override
    @Transactional
	public boolean checkExitDistrict(String name) {
		// TODO Auto-generated method stub
		String sql = "from District where name =:name";
		Query query = getSession().createQuery(sql);
		query.setParameter("name", name);
		District result = (District) query.setMaxResults(1).uniqueResult();
		if(result!=null){
			return true;			
		}
		return false;
	}
}
