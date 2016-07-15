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
                .append(" AND S.synonymId = 0");

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
    public long countSynonymsBySearchPhrase(String searchPhrase, int originId) {
        LOG.info(new StringBuilder("[countSynonymsBySearchPhrase] Start: searchPhrase = ").append(searchPhrase)
                .append(", originId = ").append(originId));
        StringBuilder sql = new StringBuilder("SELECT COUNT(S.Id) FROM ")
                .append(Synonym.class.getSimpleName())
                .append(" S WHERE S.name LIKE :searchPhrase AND synonymId = :synonymId")
                .append(" AND S.synonymId != 0")
                .append(" ORDER BY S.Id DESC");

        Query query = getSession().createQuery(String.valueOf(sql));
        query.setParameter("synonymId", originId);
        query.setParameter("searchPhrase", "%" + searchPhrase + "%");

        Long count = (Long) query.uniqueResult();

        LOG.info("[countSynonymsBySearchPhrase] End");
        return count;
    }

    @Override
    @Transactional
    public List<Synonym> getSynonymsBySearchPhraseAndSort(String searchPhrase, String sortName, int maxResult
            , int skipResult, int originId) {
        LOG.info(new StringBuilder("[getSynonymsBySearchPhraseAndSort] Start: searchPhrase = ").append(searchPhrase)
                .append(" ,sortName = ").append(sortName)
                .append(",maxResult = ").append(maxResult)
                .append(" ,skipResult = ").append(skipResult));

        StringBuilder sql = new StringBuilder("SELECT S.Id, S.name FROM ")
                .append(Synonym.class.getSimpleName())
                .append(" S WHERE S.name LIKE :searchPhrase AND synonymId = :synonymId")
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
        query.setParameter("synonymId", originId);
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

    @Override
    @Transactional
    public void insertWord(Synonym synonym) {
        LOG.info("[insertWord] Start: name = " + synonym.getName());
        insert(synonym);
        LOG.info("[insertWord] End");
    }

    @Override
    @Transactional
    public void updateWord(Synonym synonym) {
        LOG.info("[updateWord] Start: name = " + synonym.getName());
        update(synonym);
        LOG.info("[updateWord] End");
    }

    @Override
    @Transactional
    public void deleteById(Synonym synonym) {
        LOG.info("[deleteById] Start: id = " + synonym.getId());
        String sql = "DELETE FROM " + Synonym.class.getSimpleName() + " S WHERE S.id = :wordId OR S.synonymId = :wordId";
        Query query = getSession().createQuery(sql);
        query.setParameter("wordId", synonym.getId());
        query.executeUpdate();
        LOG.info("[deleteById] End");
    }

    @Override
    @Transactional
    public Synonym checkWordExist(Synonym synonym) {
        LOG.info("[checkWordExist] Start: name = " + synonym.getName());
        String sql = "FROM " + Synonym.class.getSimpleName() + " S WHERE S.name = :wordName";
        Query query = getSession().createQuery(sql);
        query.setParameter("wordName", synonym.getName());
        Synonym result = (Synonym) query.uniqueResult();
        LOG.info("[checkWordExist] End");
        return result;
    }

    @Override
    @Transactional
    public List<Synonym> getSynonymNameSortById(int skip, int limit) {
        LOG.info("[getSynonymNameSortById] Start");
        LOG.info(new StringBuilder("[getProductName] Start: skip = ").append(skip)
                .append(" limit = ").append(limit));
        StringBuilder sql = new StringBuilder("FROM ").append(Synonym.class.getSimpleName())
                .append(" S ORDER BY S.id ASC");
        Query query = getSession().createQuery(String.valueOf(sql));
        query.setFirstResult(skip);
        query.setMaxResults(limit);
        List<Synonym> result = query.list();
        LOG.info("[getSynonymNameSortById] End");
        return result;
    }

    @Override
    @Transactional
    public List<String> getByIdAndSynonymId(int id, int synonymId) {
        LOG.info("[Synonym] Start: id = " + id);
        StringBuilder sql = new StringBuilder("SELECT S.name FROM ").append(Synonym.class.getSimpleName())
                .append(" S WHERE (S.id = :synonymId OR S.synonymId = :id OR (S.synonymId = :synonymId AND S.synonymId != 0) OR S.id = :synonymId)")
                .append(" AND S.id != :id");
        Query query = getSession().createQuery(String.valueOf(sql));
        query.setParameter("id", id);
        query.setParameter("synonymId", synonymId);
        List<String> result = query.list();
        LOG.info("[Synonym] End");
        return result;
    }
}
