/**
 * 
 */
package com.psib.dao;

import com.psib.model.LexicalCategory;

/**
 * @author DatHT Jun 29, 2016
 * @Email: datht0601@gmail.com
 */
public interface ILexicalCategoryDao {

	public long insertLexical(LexicalCategory lexical);

	public int checkExistName(String name);

}
