/**
 * 
 */
package com.psib.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psib.common.DatabaseException;
import com.psib.dao.IPhraseDao;
import com.psib.model.Phrase;
import com.psib.service.IPhraseManager;

/**
 * @author DatHT Jun 29, 2016
 * @Email: datht0601@gmail.com
 */
@Service
public class PhraseManager implements IPhraseManager {

	@Autowired
	private IPhraseDao dao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.psib.service.IPhraseManager#insertPhraseToDatabase(com.psib.model.
	 * Phrase)
	 */
	@Override
	public int insertPhraseToDatabase(Phrase phrase) {
		try {
			return dao.insertPhrase(phrase);
		} catch (Exception e) {
			throw new DatabaseException("Can not insert to database", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.psib.service.IPhraseManager#checkExist(java.lang.String)
	 */
	@Override
	public Phrase checkExist(String name) {
		return dao.checkExistName(name);
	}

	@Override
	public List<Phrase> getAll() {
		return dao.getAllPhrases();
	}

	@Override
	public void updatePhrase(Phrase phrase) {
		dao.updatePhrase(phrase);
	}

}
