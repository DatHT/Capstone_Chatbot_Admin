/**
 * 
 */
package com.psib.service;

import java.io.IOException;
import java.util.List;

import com.psib.common.restclient.RestfulException;
import com.psib.dto.jsonmapper.LexicalCategoryDto;
import com.psib.dto.jsonmapper.LexicalDto;

/**
 * @author DatHT
 * Jun 5, 2016
 * @Email: datht0601@gmail.com
 */
public interface ILexicalCategoryManager{
	List<LexicalCategoryDto> getApiLexicals()  throws IOException, RestfulException;
	LexicalDto getApiLexicalById(String id)  throws IOException, RestfulException;
}
