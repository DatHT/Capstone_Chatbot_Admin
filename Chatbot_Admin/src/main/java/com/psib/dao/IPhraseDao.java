/**
 * 
 */
package com.psib.dao;

import com.psib.model.Phrase;

/**
 * @author DatHT
 * Jun 29, 2016
 * @Email: datht0601@gmail.com
 */
public interface IPhraseDao {
	public int insertPhrase(Phrase phrase);
	public Phrase checkExistName(String name);
}
