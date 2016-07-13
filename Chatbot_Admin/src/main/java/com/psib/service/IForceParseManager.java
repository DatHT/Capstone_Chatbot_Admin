package com.psib.service;

import java.io.IOException;

public interface IForceParseManager {
	public String dynamicParse(String numPage, int numOfPage, String url);
	public String staticParse(String numPage, String noPage, String url);
	public String getPageConfigFilePath() throws IOException;
	public String getParserConfigFilePath() throws IOException;

}
