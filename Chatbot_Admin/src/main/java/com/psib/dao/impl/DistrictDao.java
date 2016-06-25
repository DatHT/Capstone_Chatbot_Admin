package com.psib.dao.impl;

import com.psib.dao.IDistrictDao;
import com.psib.model.District;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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
    public District getDistrictById(long id) {
        LOG.info("[getDistrictById] Start: id = " + id);
        LOG.info("[getDistrictById] End");
        return getById(id);
    }

}
