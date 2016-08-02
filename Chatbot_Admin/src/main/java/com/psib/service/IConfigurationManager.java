package com.psib.service;

import java.io.IOException;

import com.psib.dto.configuration.ConfigDTO;
import com.psib.dto.configuration.PageDTO;

public interface IConfigurationManager {
	public String loadConfig() throws IOException;
	public boolean loadHtmlContentFromUrl(String linkPage, String htmlFilePath) throws IOException;
	public String getNextPage(String nextPage,String linkPage, String url);
	public boolean addPageConfig(PageDTO pageDTO, String url) throws IOException;
	public boolean addPageDetails(ConfigDTO configDTO, String url) throws IOException;
}
