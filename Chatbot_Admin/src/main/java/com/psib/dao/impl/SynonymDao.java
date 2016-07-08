package com.psib.dao.impl;

import com.psib.dao.ISynonymDao;
import com.psib.model.Synonym;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SynonymDao extends BaseDao<Synonym, Long> implements ISynonymDao {

    private static final Logger LOG = Logger.getLogger(ProductDetailDao.class);

    public SynonymDao(Class<Synonym> clazz) {
        super(clazz);
    }

    public SynonymDao() {
        setClazz(Synonym.class);
    }

    @Override
    @Transactional
    public long countOriginBySearchPhrase(String searchPhrase) {
        LOG.info("[countOriginBySearchPhrase] Start: searchPhrase = " + searchPhrase);

        StringBuilder sql = new StringBuilder("SELECT COUNT(S.Id) FROM ")
                .append(Synonym.class.getSimpleName())
                .append(" S WHERE S.name LIKE :searchPhrase")
                .append(" AND S.synonymId IS NULL")
                .append(" ORDER BY S.Id DESC");

        Query query = getSession().createQuery(String.valueOf(sql));
        query.setParameter("searchPhrase", "%" + searchPhrase + "%");

        Long count = (Long) query.uniqueResult();

        LOG.info("[countOriginBySearchPhrase] End");
        return count;
    }

    @Override
    @Transactional
    public List<Synonym> getOriginBySearchPhraseAndSort(String searchPhrase, String sortName, int maxResult, int skipResult) {
        LOG.info(new StringBuilder("[getOriginBySearchPhraseAndSort] Start: searchPhrase = ").append(searchPhrase)
                .append(" ,sortName = ").append(sortName)
                .append(",maxResult = ").append(maxResult)
                .append(" ,skipResult = ").append(skipResult));

        StringBuilder sql = new StringBuilder("SELECT S.Id, S.name FROM ")
                .append(Synonym.class.getSimpleName())
                .append(" S WHERE S.name LIKE :searchPhrase")
                .append(" AND S.synonymId IS NULL");

        if (sortName != null) {
            if (sortName.equals("asc")) {
                sql.append(" ORDER BY S.name ASC");
                sql.append(",S.Id DESC");
            } else {
                sql.append(" ORDER BY S.name DESC");
                sql.append(",S.Id DESC");
            }
        } else {
            sql.append(" ORDER BY S.Id DESC");
        }

        Query query = getSession().createQuery(String.valueOf(sql));
        query.setParameter("searchPhrase", "%" + searchPhrase + "%");
        query.setFirstResult(skipResult);
        query.setMaxResults(maxResult);

        List<Object[]> rows = query.list();
        List<Synonym> result = new ArrayList<>();

        for (Object[] row : rows) {
            Synonym synonym = new Synonym();
            synonym.setId(Integer.parseInt(String.valueOf(row[0])));
            synonym.setName(String.valueOf(row[1]));
            result.add(synonym);
        }

        LOG.info("[getOriginBySearchPhraseAndSort] End");
        return result;
    }

    @Override
    @Transactional
    public long countSynonymsBySearchPhrase(String searchPhrase) {
        LOG.info("[countSynonymsBySearchPhrase] Start: searchPhrase = " + searchPhrase);

        StringBuilder sql = new StringBuilder("SELECT COUNT(S.Id) FROM ")
                .append(Synonym.class.getSimpleName())
                .append(" S WHERE S.name LIKE :searchPhrase")
                .append(" AND S.synonymId IS NOT NULL")
                .append(" ORDER BY S.Id DESC");

        Query query = getSession().createQuery(String.valueOf(sql));
        query.setParameter("searchPhrase", "%" + searchPhrase + "%");

        Long count = (Long) query.uniqueResult();

        LOG.info("[countSynonymsBySearchPhrase] End");
        return count;
    }

    @Override
    @Transactional
    public List<Synonym> getSynonymsBySearchPhraseAndSort(String searchPhrase, String sortName, int maxResult, int skipResult) {
        LOG.info(new StringBuilder("[getSynonymsBySearchPhraseAndSort] Start: searchPhrase = ").append(searchPhrase)
                .append(" ,sortName = ").append(sortName)
                .append(",maxResult = ").append(maxResult)
                .append(" ,skipResult = ").append(skipResult));

        StringBuilder sql = new StringBuilder("SELECT S.Id, S.name FROM ")
                .append(Synonym.class.getSimpleName())
                .append(" S WHERE S.name LIKE :searchPhrase")
                .append(" AND S.synonymId IS NOT NULL");

        if (sortName != null) {
            if (sortName.equals("asc")) {
                sql.append(" ORDER BY S.name ASC");
                sql.append(",S.Id DESC");
            } else {
                sql.append(" ORDER BY S.name DESC");
                sql.append(",S.Id DESC");
            }
        } else {
            sql.append(" ORDER BY S.Id DESC");
        }

        Query query = getSession().createQuery(String.valueOf(sql));
        query.setParameter("searchPhrase", "%" + searchPhrase + "%");
        query.setFirstResult(skipResult);
        query.setMaxResults(maxResult);

        List<Object[]> rows = query.list();
        List<Synonym> result = new ArrayList<>();

        for (Object[] row : rows) {
            Synonym synonym = new Synonym();
            synonym.setId(Integer.parseInt(String.valueOf(row[0])));
            synonym.setName(String.valueOf(row[1]));
            result.add(synonym);
        }

        LOG.info("[getSynonymsBySearchPhraseAndSort] End");
        return result;
    }
}
