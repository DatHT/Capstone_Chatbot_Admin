/**
 * 
 */
package com.psib.dao.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.psib.dao.IPhraseDao;
import com.psib.model.LexicalCategory;
import com.psib.model.Phrase;

/**
 * @author DatHT
 * Jun 29, 2016
 * @Email: datht0601@gmail.com
 */
@Repository
public class PhraseDao extends BaseDao<Phrase, Integer> implements IPhraseDao {
	private static final Logger LOG = Logger.getLogger(PhraseDao.class);
	
	public PhraseDao(Class<Phrase> clazz) {
		super(clazz);
	}
	
	public PhraseDao() {
		setClazz(Phrase.class);
	}

	/* (non-Javadoc)
	 * @see com.psib.dao.IPhraseDao#insertPhrase(com.psib.model.Phrase)
	 */
	@Transactional
	@Override
	public int insertPhrase(Phrase phrase) {
		LOG.info("[insertPhrase] Start: name = " + phrase.getName());
		phrase.setAsynchronized(true);
        insert(phrase);
        LOG.info("[insertPhrase] End");
        return phrase.getId();
	}

	/* (non-Javadoc)
	 * @see com.psib.dao.IPhraseDao#checkExistName(java.lang.String)
	 */
	@Override
	public Phrase checkExistName(String name) {
		LOG.info("[checkExistPhrase] Start: name = " + name);
		String sql = "From " + Phrase.class.getSimpleName() + " where name = :name";
		Query query = (Query) getSession().createQuery(sql);
		query.setParameter("name", name);
		Phrase phrase = (Phrase) query.uniqueResult();
		if (phrase != null) {
			return phrase;
		}
		return null;
	}

}
