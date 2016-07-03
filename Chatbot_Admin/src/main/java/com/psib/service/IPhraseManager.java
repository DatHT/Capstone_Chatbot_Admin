/**
 * 
 */
package com.psib.service;

import java.util.List;

import com.psib.model.Phrase;

/**
 * @author DatHT
 * Jun 29, 2016
 * @Email: datht0601@gmail.com
 */
public interface IPhraseManager {

	int insertPhraseToDatabase(Phrase phrase);
	
	Phrase checkExist(String name);
	
	List<Phrase> getAll();
}
