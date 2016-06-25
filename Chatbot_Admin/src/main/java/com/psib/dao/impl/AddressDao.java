package com.psib.dao.impl;

import com.psib.dao.IAddressDao;
import com.psib.model.Address;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class AddressDao extends BaseDao<Address, Long> implements IAddressDao {

    private static final Logger LOG = Logger.getLogger(AddressDao.class);

    public AddressDao(Class<Address> clazz) {
        super(clazz);
    }

    public AddressDao() {
        setClazz(Address.class);
    }

    @Override
    @Transactional
    public long inserAddress(Address address) {
        LOG.info("[inserAddress] Start: name = " + address.getName());
        insert(address);
        LOG.info("[inserAddress] End");
        return address.getId();
    }

    @Override
    @Transactional
    public long checkAddressExist(Address address) {
        LOG.info("[checkAddressExist] Start: name = " + address.getName());

        String sql = "FROM " + Address.class.getSimpleName() + " A WHERE A.name = :name";
        Query query = getSession().createQuery(sql);
        query.setParameter("name", address.getName());
        Address result = (Address) query.uniqueResult();
        if (result != null) {
            LOG.info("[checkAddressExist] End");
            return result.getId();
        }
        LOG.info("[checkAddressExist] End");
        return 0;
    }
}
