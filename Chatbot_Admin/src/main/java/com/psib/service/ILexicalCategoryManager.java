/**
 * 
 */
package com.psib.service;

import java.io.IOException;
import java.util.List;

import com.psib.common.restclient.RestfulException;
import com.psib.constant.StatusCode;
import com.psib.dto.jsonmapper.Entity;
import com.psib.dto.jsonmapper.Entry;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.dto.jsonmapper.LexicalDto;
import com.psib.dto.jsonmapper.ResultDto;
import com.psib.dto.jsonmapper.StatusDto;
import com.psib.model.LexicalCategory;
import com.psib.model.Phrase;

/**
 * @author DatHT Jun 5, 2016
 * @Email: datht0601@gmail.com
 */
public interface ILexicalCategoryManager {
	List<LexicalCategoryDto> getApiLexicals() throws IOException, RestfulException;

	LexicalDto getApiLexicalById(String id) throws IOException, RestfulException;

	StatusCode addPhrase(Entry entry, String id) throws IOException, RestfulException;
	
	StatusCode addPhrases(List<Entry> entries, String id) throws IOException, RestfulException;
	
	StatusCode deletePhrase(List<String> values, String name) throws IOException, RestfulException;
	
	long insertLexicalToDatabase(LexicalCategory lexicalCategory);
	
	int checkExistLexical(String name);
	
	StatusCode updatePhrase(LexicalDto entity, String id) throws IOException, RestfulException;
}
