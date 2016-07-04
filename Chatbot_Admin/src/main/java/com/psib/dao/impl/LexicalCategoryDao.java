/**
 * 
 */
package com.psib.dao.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.psib.dao.ILexicalCategoryDao;
import com.psib.model.LexicalCategory;
import com.psib.model.Staff;

/**
 * @author DatHT Jun 29, 2016
 * @Email: datht0601@gmail.com
 */
@Repository
public class LexicalCategoryDao extends BaseDao<LexicalCategory, Integer> implements ILexicalCategoryDao {
	private static final Logger LOG = Logger.getLogger(LexicalCategoryDao.class);
	
	public LexicalCategoryDao(Class<LexicalCategory> clazz) {
		super(clazz);
	}

	public LexicalCategoryDao() {
		setClazz(LexicalCategory.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.psib.dao.ILexicalCategoryDao#insertLexical(com.psib.model.
	 * LexicalCategory)
	 */
	@Transactional
	@Override
	public long insertLexical(LexicalCategory lexical) {
		LOG.info("[insertLexical] Start: name = " + lexical.getName());
        insert(lexical);
        LOG.info("[insertLexical] End");
        return lexical.getId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.psib.dao.ILexicalCategoryDao#checkExistName(java.lang.String)
	 */
	@Transactional
	@Override
	public int checkExistName(String name) {
		LOG.info("[checkExistLexical] Start: name = " + name);
		String sql = "From " + LexicalCategory.class.getSimpleName() + " where name = :name";
		Query query = (Query) getSession().createQuery(sql);
		query.setParameter("name", name);
		LexicalCategory lexiacal = (LexicalCategory) query.uniqueResult();
		if (lexiacal != null) {
			return lexiacal.getId();
		}
		return -1;
	}

}
