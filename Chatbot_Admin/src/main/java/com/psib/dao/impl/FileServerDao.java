package com.psib.dao.impl;

import com.psib.dao.IFileServerDao;
import com.psib.model.FileServer;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class FileServerDao extends BaseDao<FileServer, Long> implements IFileServerDao {

    private static final Logger LOG = Logger.getLogger(FileServerDao.class);

    public FileServerDao(Class<FileServer> clazz) {
        super(clazz);
    }

    public FileServerDao() {
        setClazz(FileServer.class);
    }

    @Override
    @Transactional
    public FileServer getByName(String name) {

        LOG.info("[getByName] Start: name = " + name);

        String sql = "FROM " + FileServer.class.getSimpleName() + " F WHERE F.name = :name";
        Query query = getSession().createQuery(sql);
        query.setParameter("name", name);
        FileServer result = (FileServer) query.uniqueResult();

        if (result != null) {
            LOG.info("[getByName] End");
            return result;
        }
        LOG.info("[getByName] End");
        return null;
    }
}
