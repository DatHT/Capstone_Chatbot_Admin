package com.psib.service;

public interface IForceParseManager {
	public String dynamicParse(String numPage, int numOfPage, String url);
	public String staticParse(String numPage, String noPage, String url);

}
