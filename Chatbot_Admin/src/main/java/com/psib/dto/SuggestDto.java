/**
 * @author: DatHT
 * Jul 30, 2016
 * @Email: datht0601@gmail.com
 */
package com.psib.dto;

import java.io.Serializable;

public class SuggestDto implements Serializable {
	private String Lexical;
	private String words;
	
	public SuggestDto() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param lexical
	 * @param words
	 */
	public SuggestDto(String lexical, String words) {
		super();
		Lexical = lexical;
		this.words = words;
	}

	public String getLexical() {
		return Lexical;
	}

	public void setLexical(String lexical) {
		Lexical = lexical;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}
	
	
}
